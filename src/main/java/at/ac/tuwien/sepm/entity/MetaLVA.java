package at.ac.tuwien.sepm.entity;

import at.ac.tuwien.sepm.service.LvaType;
import at.ac.tuwien.sepm.service.Semester;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MetaLVA {
	public static final int NR=0;
	public static final int NAME=1;
	public static final int ECTS=2;
	public static final int PRECURSOR=3;
	public static final int TYPE=4;
	public static final int HAS_EXERCISE=5;
	public static final int PRIORITY=6;
	public static final int SEMESTERS_OFFERED=7;
	public static final int LVAS=8;
	public static final int COMPLETED=9;
	
	private boolean[] isSet = new boolean[10];
	
	
	
	private String nr;
	private String name;
	private float ects;
	private ArrayList<MetaLVA> precursor = new ArrayList<MetaLVA>();
	private LvaType type;
	private boolean hasExercise;
	private float priority;
	private Semester semestersOffered;
	
	private ArrayList<LVA> lvas;
	private HashMap<Integer,LVA> lvasMap;
	
	private boolean completed;
	
	public MetaLVA(){
	}
	/**
	 * setPrecursor must be called before calling addPrecursor
	 * @param other the MetaLVA to add
	 */
	public void addPrecursor(MetaLVA other){
		if(!isSet(this.PRECURSOR)){
			throw new RuntimeException("setPrecursor() must be called before addPrecursor.");
		}
		precursor.add(other);
	}
	
	public boolean isSet(int attributeID){
		return isSet[attributeID];
	}
	public void unset(int attributeID){
		isSet[attributeID]=false;
	}
	public String getNr() {
		return nr;
	}

	public void setNr(String nr) {
		this.nr = nr;
		isSet[this.NR]=true;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		isSet[this.NAME]=true;
	}

	public float getEcts() {
		return ects;
	}

	public void setECTS(float ects) {
		this.ects = ects;
		isSet[this.ECTS]=true;
	}

	public ArrayList<MetaLVA> getPrecursor() {
		return precursor;
	}

	public void setPrecursor(List<MetaLVA> precursors) {
		this.precursor = new ArrayList<MetaLVA>(precursors);
		isSet[this.PRECURSOR]=true;
	}

	public LvaType getType() {
		return type;
	}

	public void setType(LvaType type) {
		this.type = type;
		isSet[this.TYPE]=true;
	}

	public boolean hasExercise() {
		return hasExercise;
	}

	public void setHasExercise(boolean hasExercise) {
		this.hasExercise = hasExercise;
		isSet[this.HAS_EXERCISE]=true;
	}

	public float getPriority() {
		return priority;
	}

	public void setPriority(float priority) {
		this.priority = priority;
		isSet[PRIORITY]=true;
	}

	public ArrayList<LVA> getLVAs() {
		return lvas;
	}

	public void setLVAs(List<LVA> lvas) {
		this.lvas = new ArrayList<LVA>(lvas);
		isSet[LVAS]=true;
		lvasMap= new HashMap<Integer,LVA>();
		for(LVA l:lvas){
			int key=l.getYear()*2;
			if(l.getSemester()==Semester.W){
				key++;
			}
			lvasMap.put(key, l);
			if(!l.isSet(LVA.METALVA)){
				l.setMetaLVA(this);
			}
		}
		
	}
	public LVA getLVA(int year, Semester sem){
		int key=year*2;
		if(sem==Semester.W){
			key++;
		}
		return lvasMap.get(key);
	}
	public boolean containsLVA(int year, Semester sem){
		int key=year*2;
		if(sem== Semester.W){
			key++;
		}
		return lvasMap.containsKey(key);
	}
	public void setLVA(LVA lva) {
		ArrayList<LVA> temp = new ArrayList<LVA>(1);
		temp.add(lva);
		setLVAs(temp);
	}
	public Semester getSemestersOffered() {
		return semestersOffered;
	}
	public void setSemestersOffered(Semester semestersOffered) {
		this.semestersOffered = semestersOffered;
		isSet[MetaLVA.SEMESTERS_OFFERED]=true;
	}
	public boolean isCompleted() {
		return completed;
	}
	public void setCompleted(boolean completed) {
		this.completed = completed;
		isSet[MetaLVA.COMPLETED]=true;
	}
	public String toString(){
		String toReturn="<MetaLVA:";
		if(isSet(MetaLVA.NR)){
			toReturn+=" nr:"+nr;
		}
		if(isSet(MetaLVA.NAME)){
			toReturn+=" name:"+name;
		}
		if(isSet(MetaLVA.ECTS)){
			toReturn+=" ects:"+ects;
		}		
		if(isSet(MetaLVA.PRIORITY)){
			toReturn+=" priority:"+priority;
		}
		return toReturn+">";
	}

}
