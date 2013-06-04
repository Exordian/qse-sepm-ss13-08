package at.ac.tuwien.sepm.service.impl;

import at.ac.tuwien.sepm.entity.*;
import at.ac.tuwien.sepm.service.Semester;
import at.ac.tuwien.sepm.service.IntelligentSemesterPlaner;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.*;


public class IntelligentSemesterPlanerImpl implements IntelligentSemesterPlaner {
	private ArrayList<MetaLVA> forced = new ArrayList<MetaLVA>();
	private ArrayList<MetaLVA> pool = new ArrayList<MetaLVA>();
	private DependenceTree tree;
    Logger logger = LogManager.getLogger(this.getClass().getSimpleName());
    private boolean[][] intersecting;


    @Override public void setLVAs(List<MetaLVA> forced, List<MetaLVA> pool){
        if(forced==null){
            forced = new ArrayList<MetaLVA>(0);
        }
        if(pool==null){
            pool = new ArrayList<MetaLVA>(0);
        }
        this.pool.clear();
        this.forced.addAll(forced);
        for(MetaLVA lva:pool){
            if(!lva.isCompleted()){
                this.pool.add(lva);
            }
        }
        for(MetaLVA lva:forced){
            if(!this.pool.contains(lva)){
                this.pool.add(lva);
            }
        }
        logger.debug("forced set:\n"+ LVAUtil.formatShortMetaLVA(this.forced, 1));
        logger.debug("pool set:\n"+LVAUtil.formatShortMetaLVA(this.pool, 1));
    }

    @Override public ArrayList<MetaLVA> planSemester(float goalECTS,int year,Semester sem){
		tree = new DependenceTree(new ArrayList<MetaLVA>(pool));
		ArrayList<MetaLVA> roots = tree.getRoots();
		ArrayList<MetaLVA> toPlan = new ArrayList<MetaLVA>(roots.size());
		for(MetaLVA mLVA :roots){
			if(mLVA.containsLVA(year, sem)){
				toPlan.add(mLVA);
			}
		}
        ArrayList chosen = new ArrayList<Integer>();
        for(MetaLVA mLVA :forced){
            if(!toPlan.contains(mLVA) && mLVA.containsLVA(year, sem)){
                toPlan.add(mLVA);

            }
            if(mLVA.containsLVA(year, sem)){
                chosen.add(toPlan.indexOf(mLVA));
            }
        }
        computeSolution(toPlan,chosen,goalECTS);
        intersectAll(toPlan,year,sem);
		recPlanning(toPlan,0,chosen,goalECTS,0,year,sem);
		if(bestSolution!=null)
		return bestSolution;
        return new ArrayList<MetaLVA>();
	}
	private ArrayList<MetaLVA> bestSolution=null;
	private float solutionValue=Float.NEGATIVE_INFINITY;
	private void recPlanning(ArrayList<MetaLVA> all,int index,ArrayList<Integer> chosen,float goalECTS,float actualECTS,int year, Semester sem){
		for(int i=index;i<all.size();i++){
			boolean intersect=false;
            int lastJ=-1;
			for(Integer j:chosen){
				if(intersect(i,j)){
					intersect=true;
                    lastJ=j;
					break;
				}
			}
			if(!intersect){
				chosen.add(i);
				
				computeSolution(all,chosen,goalECTS);

                recPlanning(all,i+1,chosen, goalECTS,actualECTS+all.get(i).getECTS(),year,sem);
                
                chosen.remove(chosen.size()-1);
            }else{
                //logger.debug("intersecting: "+all.get(lastJ).toShortString()+" with: "+all.get(i).toShortString());
            }
		}
	}
    private void intersectAll(List<MetaLVA> metaLVAs,int year, Semester sem){
        ArrayList<LVA> lvas = new ArrayList<LVA>();
        for(MetaLVA m : metaLVAs){
            lvas.add(m.getLVA(year,sem));
        }
        intersecting = LVAUtil.intersectAll(lvas);

    }
    private boolean intersect(int a, int b){
        if(a>b){
            return intersecting[b][a-b];
        }
        return intersecting[a][b-a];
    }
	private void computeSolution(ArrayList<MetaLVA> all,ArrayList<Integer> chosen,float goalECTS) {
		float ects=0;
		for(Integer i:chosen){
			ects+=all.get(i).getECTS();
		}
		float value=0;
		for(Integer i:chosen){
			value+=all.get(i).getECTS()*tree.getPriority(all.get(i))/ects;
		}
		value-=Math.pow(Math.abs(goalECTS-ects),1.5);
		if(value>solutionValue){
			ArrayList<MetaLVA> newSolution = new ArrayList<MetaLVA>();
			for(Integer i:chosen){
				newSolution.add(all.get(i));
			}
			bestSolution=newSolution;
			solutionValue=value;
            logger.debug("new Solution found: \n" +LVAUtil.formatShortMetaLVA(newSolution, 1)+"\n\tsolution value: "+value);
		}else{
            //active for detailed debugging
			ArrayList<MetaLVA> toDiscard = new ArrayList<MetaLVA>();
			for(Integer i:chosen){
				toDiscard.add(all.get(i));
			}
			//logger.debug("discarding set: " +toDiscard+"\nsolution value: "+value);

		}
	}

    public static class DependenceTree implements Iterable<MetaLVA>{


        ArrayList<Node> pool = new ArrayList<Node>();
        ArrayList<Node> roots = new ArrayList<Node>();
        ArrayList<Node> leaves= new ArrayList<Node>();
        HashMap<Integer,Node> nodes;
        ArrayList<Node> nodesList;
        Logger logger = LogManager.getLogger(this.getClass().getSimpleName());

        public DependenceTree(List<MetaLVA> lvas){
            nodesList = new ArrayList<Node>();
            nodes = new HashMap<Integer,Node>(lvas.size()+(int)(lvas.size()*0.2));
            for(MetaLVA lva: lvas){
                Node tempNode =new Node(lva);
                nodes.put(new Integer(lva.getNr().replace(".","")), tempNode);
                nodesList.add(tempNode);
            }
            for(MetaLVA lva: lvas){
                Node tempNode=nodes.get(new Integer(lva.getNr().replace(".","")));
                List<MetaLVA> tempPres =tempNode.getLVA().getPrecursor();
                for(MetaLVA pre : tempPres){
                    if(nodes.containsKey(new Integer(pre.getNr().replace(".","")))){
                        tempNode.addChild(nodes.get(new Integer(pre.getNr().replace(".", ""))));
                    }
                }
                if(tempNode.getChildren().isEmpty()){
                    roots.add(tempNode);
                }
            }
            for(MetaLVA lva: lvas){
                Node tempNode=nodes.get(new Integer(lva.getNr().replace(".", "")));
                if(tempNode.getParents().isEmpty()){
                    leaves.add(tempNode);
                }
            }
        }
        public ArrayList<MetaLVA> getRoots(){
            Collections.sort(roots);
            ArrayList<MetaLVA> toReturn = new ArrayList<MetaLVA>(roots.size());
            for(Node n:roots){
                toReturn.add(n.getLVA());
            }
            return toReturn;
        }
        public ArrayList<MetaLVA> getLeaves(){
            Collections.sort(leaves);
            ArrayList<MetaLVA> toReturn = new ArrayList<MetaLVA>(leaves.size());
            for(Node n:leaves){
                toReturn.add(n.getLVA());
            }
            return toReturn;
        }
        public void changePriorityByParents(float rate){
            for(Node n:roots){
                n.changePriorityByParents(rate);
            }
        }
        public void changePriorityBySemester(float delta){
            for(Node n:nodesList){
                logger.debug(n.getLVA());
                n.changePriorityBySemester(delta);
            }
        }
        public float getPriority(MetaLVA lva){
            return nodes.get(new Integer(lva.getNr().replace(".", ""))).getPriority();
        }
        private class Node implements Comparable<Node>{
            MetaLVA lva;
            float priority;
            ArrayList<Node> children = new ArrayList<Node>();
            ArrayList<Node> parents = new ArrayList<Node>();
            private Node(MetaLVA lva){
                this.lva=lva;
                this.priority=lva.getPriority();
            }
            public MetaLVA getLVA(){
                return lva;
            }
            private void addChild(Node other){
                children.add(other);
                other.parents.add(this);
            }
            private ArrayList<Node> getChildren(){
                return children;
            }
            private ArrayList<Node> getParents(){
                return parents;
            }
            public float changePriorityByParents(float rate){
                for(Node n:parents){
                    priority+=n.changePriorityByParents(rate);
                }
                return priority*rate;
            }
            public void changePriorityBySemester(float delta){
                Semester temp = lva.getSemestersOffered();
                switch(temp){
                case S:
                case W:
                    priority+=delta;
                    break;
                case W_S:
                    priority-=delta;
                }
            }
            public float getPriority(){
                return priority;
            }
            @Override
            public int compareTo(Node o) {
                return (int) ((priority - o.priority)*128);
            }
        }
        @Override
        public Iterator<MetaLVA> iterator() {
            return new MetaLVAIterator();
        }
        private class MetaLVAIterator implements Iterator<MetaLVA>{
            Iterator<Node> rootsIter;
            private MetaLVAIterator(){
                Collections.sort(roots);
                rootsIter = roots.iterator();
            }
            @Override
            public boolean hasNext() {
                return rootsIter.hasNext();
            }

            @Override
            public MetaLVA next() {
                return rootsIter.next().getLVA();
            }

            @Override
            public void remove() {
            }

        }
    }
}
