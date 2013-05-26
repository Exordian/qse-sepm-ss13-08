package at.ac.tuwien.sepm.service.semesterPlaning;

import at.ac.tuwien.sepm.entity.*;
import at.ac.tuwien.sepm.service.Semester;

import java.util.ArrayList;
import java.util.List;


public class IntelligentSemesterPlaner {
	private ArrayList<MetaLVA> forced = new ArrayList<MetaLVA>();
	private ArrayList<MetaLVA> pool = new ArrayList<MetaLVA>();
	private DependenceTree tree;
	


    /**
     * sets the LVAs which should be used during the calculation.
     * @param forced these LVAs will be in the result anyway. It will not be tested, if these intersect each other,
     *               but before adding LVAs to the solution, they will be tested on intersection.
     * @param pool all LVAs, which should be considered for the solution. LVAs which are completed will not be considered
     */
	public void setLVAs(List<MetaLVA> forced, List<MetaLVA> pool){
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
        //System.out.println("pool set: "+this.pool);
	}

    /**
     * Plans a semester.
     * Important: setLVAs() must be called first! Otherwise the resulting list will be empty.
     * @param goalECTS the desired ECTS for the semester
     * @param year the year, for which a semester is to plan
     * @param sem the semester (Semester.S OR Semester.W) which is to plan.
     * @return a List with the result
     */
	public ArrayList<MetaLVA> planSemester(float goalECTS,int year,Semester sem){
		tree = new DependenceTree(new ArrayList<MetaLVA>(pool));
		ArrayList<MetaLVA> roots = tree.getRoots();
		ArrayList<MetaLVA> toPlan = new ArrayList<MetaLVA>(roots.size());
		for(MetaLVA mLVA :roots){
			if(mLVA.containsLVA(year, sem)){
				toPlan.add(mLVA);
			}
		}
		recPlanning(toPlan,0,new ArrayList<Integer>(),goalECTS,year,sem);
		
		return bestSolution;
	}
	private ArrayList<MetaLVA> bestSolution=null;
	private float solutionValue=Float.NEGATIVE_INFINITY;
	private void recPlanning(ArrayList<MetaLVA> all,int index,ArrayList<Integer> chosen,float goalECTS,int year, Semester sem){
		for(int i=index;i<all.size();i++){
			boolean intersect=false;
			for(Integer j:chosen){
				if(LVAUtil.intersectAll(all.get(j).getLVA(year, sem),all.get(i).getLVA(year, sem))){
					intersect=true;
					break;
				}
			}
			if(!intersect){
				chosen.add(i);
				
				computeSolution(all,chosen,goalECTS);
				
				recPlanning(all,i+1,chosen, goalECTS,year,sem);
				
				chosen.remove(chosen.size()-1);
			}
		}
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
		value-=(goalECTS-ects)*(goalECTS-ects);
		if(value>solutionValue){
			ArrayList<MetaLVA> newSolution = new ArrayList<MetaLVA>();
			for(Integer i:chosen){
				newSolution.add(all.get(i));
			}
			bestSolution=newSolution;
			solutionValue=value;
			//System.out.println("new Solution found: " +newSolution);
			//System.out.println("value: "+value);
		}else{
			ArrayList<MetaLVA> newSolution = new ArrayList<MetaLVA>();
			for(Integer i:chosen){
				newSolution.add(all.get(i));
			}
			//System.out.println("NO new Solution found: " +newSolution);
			//System.out.println("value: "+value);
		}
	}
	
}
