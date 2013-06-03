package at.ac.tuwien.sepm.service.semesterPlanning;

import at.ac.tuwien.sepm.entity.*;
import at.ac.tuwien.sepm.service.Semester;
import at.ac.tuwien.sepm.service.semesterPlanning.DependenceTree;
import at.ac.tuwien.sepm.service.semesterPlanning.LVAUtil;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;


public class IntelligentSemesterPlaner {
    private ArrayList<MetaLVA> forced = new ArrayList<MetaLVA>();
    private ArrayList<MetaLVA> pool = new ArrayList<MetaLVA>();
    private DependenceTree tree;
    Logger logger = LogManager.getLogger(this.getClass().getSimpleName());
    private boolean[][] intersecting;


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
        logger.debug("forced set:\n"+LVAUtil.formatShort(this.forced,1));
        logger.debug("pool set:\n"+LVAUtil.formatShort(this.pool,1));
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
        ArrayList chosen = new ArrayList<Integer>();
        for(MetaLVA mLVA :forced){
            if(!toPlan.contains(mLVA) && mLVA.containsLVA(year, sem)){
                toPlan.add(mLVA);
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
    private void intersectAll(List<MetaLVA> lva,int year, Semester sem){
        String debug="";
        intersecting = new boolean[lva.size()][];
        for(int i=0;i<lva.size();i++){
            intersecting[i] = new boolean[lva.size()-i];
            for(int j=i;j<lva.size();j++){
                intersecting[i][j-i]=LVAUtil.intersectAll(lva.get(i).getLVA(year, sem),lva.get(j).getLVA(year, sem));
                if(intersecting[i][j-i]){
                    debug+="1 ";
                }else{
                    debug+="0 ";
                }
            }
            debug+="\n";
        }
        logger.debug("intersecting: \n"+debug);

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
            logger.debug("new Solution found: \n" +LVAUtil.formatShort(newSolution,1)+"\n\tsolution value: "+value);
        }else{
            //active for detailed debugging
            ArrayList<MetaLVA> toDiscard = new ArrayList<MetaLVA>();
            for(Integer i:chosen){
                toDiscard.add(all.get(i));
            }
            //logger.debug("discarding set: " +toDiscard+"\nsolution value: "+value);

        }
    }
    
}
