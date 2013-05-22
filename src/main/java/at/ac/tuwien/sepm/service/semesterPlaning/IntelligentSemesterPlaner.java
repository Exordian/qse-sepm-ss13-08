package at.ac.tuwien.sepm.service.semesterPlaning;

import at.ac.tuwien.sepm.entity.*;
import at.ac.tuwien.sepm.service.Semester;

import java.util.ArrayList;
import java.util.List;


public class IntelligentSemesterPlaner {
	private ArrayList<MetaLVA> forced = new ArrayList<MetaLVA>();
	private ArrayList<MetaLVA> pool = new ArrayList<MetaLVA>();
	private DependenceTree tree;
	
	/*public static IntelligentSemesterPlaner singleton; 
	public static IntelligentSemesterPlaner getPlaner(){
		if (singleton==null){
			singleton = new IntelligentSemesterPlaner();
		}
		return singleton;
	}
	private IntelligentSemesterPlaner(){

	}*/
	//private Persistance pers;
	//public void setPersistance(Persistance pers){
	//	this.pers=pers;
	//}
	public void setLVAs(List<MetaLVA> forced, List<MetaLVA> pool){
		this.forced.clear();
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
	}
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
			System.out.println("new Solution found: " +newSolution);
			System.out.println("value: "+value);
		}else{
			ArrayList<MetaLVA> newSolution = new ArrayList<MetaLVA>();
			for(Integer i:chosen){
				newSolution.add(all.get(i));
			}
			System.out.println("NO new Solution found: " +newSolution);
			System.out.println("value: "+value);
		}
	}
	
}
