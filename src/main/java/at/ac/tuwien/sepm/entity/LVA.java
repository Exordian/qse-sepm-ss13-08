package at.ac.tuwien.sepm.entity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import at.ac.tuwien.sepm.service.Semester;
import at.ac.tuwien.sepm.service.TimeFrame;
import org.joda.time.DateTime;
/**
 * 
 * @author Georg Plaz
 *
 */

public class LVA {
	public static final int METALVA=0;
	//public static final int NAME=1;
	public static final int DESCRIPTION=2;
	public static final int YEAR=3;
	public static final int SEMESTER=4;
	public static final int TIMES=5;
	public static final int ROOMS=7;
	public static final int LECTURER=8;
	//public static final int HASEXERSICE=9;
	public static final int TIMES_UE=10;
	public static final int ROOMSUE=12;
	public static final int EXERSICEINSTRUCTOR=13;
	
	public static final int TIMES_EXAM=14;
	public static final int ROOMSEXAM=15;
	public static final int EXAMGRADE=16;

	private boolean[] isSet = new boolean[17];
	
	private MetaLVA metaLVA;
	private String description;
	private int year;
	private Semester semester;
	
	private ArrayList<TimeFrame> times;
	private ArrayList<String> rooms = new ArrayList<String>();
	private ArrayList<String> lecturer = new ArrayList<String>();
	
	private ArrayList<TimeFrame> timesUE;
	private ArrayList<String> roomsUE = new ArrayList<String>();
	private String excersiceInstructor;
	
	private ArrayList<TimeFrame> timesExam;
	private ArrayList<String> roomsExam = new ArrayList<String>();
	private ArrayList<Integer> examGrade = new ArrayList<Integer>();
	
	
	
	
	public LVA(){
		
	}
	public boolean isSet(int attributeID){
		return isSet[attributeID];
	}
	public boolean intersect(LVA other,int timeID){
		ArrayList<Integer> timeIDs = new ArrayList<Integer>();
		timeIDs.add(timeID);
		return intersect(other,timeIDs,timeIDs);
	}
	public boolean intersectAll(LVA other){
		ArrayList<Integer> timeIDs = new ArrayList<Integer>();
		timeIDs.add(TIMES);
		timeIDs.add(TIMES_UE);
		timeIDs.add(TIMES_EXAM);
		return intersect(other,timeIDs,timeIDs);
	}
	public boolean intersect(LVA other, List<Integer> consideredTimesA,List<Integer> consideredTimesB){
		return intersect(other,0,consideredTimesA,consideredTimesB);
	}
	public boolean intersect(LVA other,int secondsBetween, List<Integer> consideredTimesA,List<Integer> consideredTimesB){
		Iterator<TimeFrame> iterA = iterator(consideredTimesA);
		Iterator<TimeFrame> iterB = other.iterator(consideredTimesB);
		
		
		TimeFrame tfA;
		TimeFrame tfB;
		if(iterA.hasNext()&&iterB.hasNext()){
			tfA=iterA.next();
			tfB=iterB.next();
			while(true){
				if(tfA.after(tfB,secondsBetween)){
					if(!iterB.hasNext()){
						return false;
					}
					tfB=iterB.next();
				}else if(tfA.before(tfB,secondsBetween)){
					if(!iterA.hasNext()){
						return false;
					}
					tfA=iterA.next();
				}else{
					return true;
				}
			}
		}
		/*
		int index1=0;
		int index2=0;
		if(iterA.hasNext()&&iterB.hasNext()){
			tfA=iterA.next();
			tfB=iterB.next();
		
		
			while(index1 < times.size() && index2 < other.times.size()){
				if(times.get(index1).after(other.times.get(index2))){
					index2++;
					System.out.println("index2++");
				}else if(other.times.get(index2).after(times.get(index1))){
					index1++;
	
					System.out.println("index1++");
				}else{
					return true;
				}
			}
		}*/
		return false;
	}
	public Iterator<TimeFrame> iterator(List<Integer> consideredTimesA){
		return new TimeIterator(consideredTimesA);
	}
	public Iterator<TimeFrame> iterator(int timeID){
		ArrayList<Integer> timeIDs = new ArrayList<Integer>();
		timeIDs.add(timeID);
		return new TimeIterator(timeIDs);
	}
	public Iterator<TimeFrame> iterator(){
		ArrayList<Integer> timeIDs = new ArrayList<Integer>();
		timeIDs.add(TIMES);
		timeIDs.add(TIMES_UE);
		timeIDs.add(TIMES_EXAM);
		
		return new TimeIterator(timeIDs);
	}
	private class TimeIterator implements Iterator<TimeFrame>{
		//ArrayList<ArrayList<TimeFrame>> allFrameLists = new ArrayList<ArrayList<TimeFrame>>();
		ArrayList<Iterator<TimeFrame>> allIter= new ArrayList<Iterator<TimeFrame>>();		
		ArrayList<TimeFrame> allLastFrames = new ArrayList<TimeFrame>();
		
		public TimeIterator(List<Integer> consideredTimesA){
			if(times!=null && consideredTimesA.contains(TIMES)){
				//System.out.println("debug0.0: "+times.size());
				allIter.add(times.iterator());
			}
			if(timesUE!=null && consideredTimesA.contains(TIMES_UE)){
				//System.out.println("debug0.1: "+timesUE.size());
				allIter.add(timesUE.iterator());
			}
			if(timesExam!=null && consideredTimesA.contains(TIMES_EXAM)){
				//System.out.println("debug0.2: "+timesExam.size());
				allIter.add(timesExam.iterator());
			}
			for(Iterator<TimeFrame> iter:allIter){
				if(iter.hasNext()){
					//System.out.println("iter==null: "+iter==null);
					allLastFrames.add(iter.next());
				}else{
					allLastFrames.add(null);
				}
			}
		}
		@Override
		public boolean hasNext() {
			//System.out.println("debug2");
			for(TimeFrame frame: allLastFrames){
				//System.out.println("  debug2.0: "+iter.hasNext());
				if(frame!=null){
					return true;
				}
			}
			return false;
		}

		@Override
		public TimeFrame next() {
			TimeFrame firstFrame=null;
			boolean change=true;
			while(change){
				change=false;
				for(TimeFrame frame: allLastFrames){
					if(firstFrame==null){
						firstFrame=frame;
					}else{
						if(frame!=null && frame.from().isBefore(firstFrame.from())){
							change=true;
							firstFrame = frame;
						}
					}
				}
			}
			int index = allLastFrames.indexOf(firstFrame);
			if(allIter.get(index).hasNext()){
				allLastFrames.set(index, allIter.get(index).next());
			}else{
				allLastFrames.set(index, null);
			}
			//System.out.println("debug: "+firstFrame);
			return firstFrame;
		}

		@Override
		public void remove() {
			// TODO Auto-generated method stub
			
		}
		
	}
	/*
	public void addTimes(ArrayList<TimeFrame> times,ArrayList<String> rooms){
		isSet[TIMES]=isSet[ROOMS]=true;
		if(times.size()!=rooms.size()){
			throw new RuntimeException("There are not equally many times, endTimes and rooms.");
		}
		for(int i=0;i<times.size();i++){
			addTime(times.get(i),rooms.get(i));
		}
	}
	
	public void addTime(TimeFrame time, String room){
		if(!times.isEmpty()&&!time.after(times.get(times.size()-1))){
			throw new RuntimeException("TimeFrames must be inserted in order");
		}
		isSet[TIMES]=isSet[ROOMS]=true;
		this.times.add(time);
		this.rooms.add(room);
	}*/
	public MetaLVA getMetaLVA() {
		return metaLVA;
	}
	/**
	 * should only be called by MetaLVA!
	 * use MetaLVA.setLVAs(List<LVA>) or MetaLVA.setLVA(LVA) instead
	 * @param metaLVA
	 */
	public void setMetaLVA(MetaLVA metaLVA) {
		this.metaLVA = metaLVA;
		isSet[METALVA]=true;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
		isSet[DESCRIPTION]=true;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
		isSet[YEAR]=true;
	}
	public Semester getSemester() {
		return semester;
	}
	public void setSemester(Semester semester) {
		this.semester = semester;
		isSet[SEMESTER]=true;
	}
	public ArrayList<TimeFrame> getTimes() {
		return times;
	}
	/**
	 * 
	 * @param times must not be null and the "from" attributes must be ordered ascending.
	 * Per example:
	 * times.get(0).toString() -> "from:2000-01-01T00:00:00.000+01:00, to:2000-01-10T00:00:00.000+01:00"
	 * times.get(1).toString() -> "from:2000-01-03T00:00:00.000+01:00, to:2000-01-08T00:00:00.000+01:00"
	 */
	public void setTimes(List<TimeFrame> times) {
		this.times = new ArrayList<TimeFrame>(times);
		isSet[TIMES]=true;
	}
	public ArrayList<String> getRooms() {
		return rooms;
	}
	public void setRooms(List<String> rooms) {
		this.rooms = new ArrayList<String>(rooms);
		isSet[ROOMS]=true;
	}
	public ArrayList<String> getLecturer() {
		return lecturer;
	}
	public void setLecturer(List<String> lecturer) {
		this.lecturer = new ArrayList<String>(lecturer);
		isSet[LECTURER]=true;
	}
	/*public boolean isHasExersice() {
		return hasExersice;
	}
	public void setHasExersice(boolean hasExersice) {
		this.hasExersice = hasExersice;
		isSet[HASEXERSICE]=true;
	}*/
	public ArrayList<TimeFrame> getTimesUE() {
		return timesUE;
	}
	public void setTimesUE(List<TimeFrame> timesUE) {
		this.timesUE = new ArrayList<TimeFrame>(timesUE);
		isSet[TIMES_UE]=true;
	}
	public ArrayList<String> getRoomsUE() {
		return roomsUE;
	}
	public void setRoomsUE(List<String> roomsUE) {
		this.roomsUE = new ArrayList<String>(roomsUE);
		isSet[ROOMSUE]=true;
	}
	public String getExcersiceInstructor() {
		return excersiceInstructor;
	}
	public void setExcersiceInstructor(String excersiceInstructor) {
		this.excersiceInstructor = excersiceInstructor;
		isSet[EXERSICEINSTRUCTOR]=true;
	}
	public ArrayList<TimeFrame> getTimesExam() {
		return timesExam;
	}
	public void setTimesExam(List<TimeFrame> timesExam) {
		this.timesExam = new ArrayList<TimeFrame>(timesExam);
		isSet[TIMES_EXAM]=true;
	}
	public ArrayList<String> getRoomsExam() {
		return roomsExam;
	}
	public void setRoomsExam(List<String> roomsExam) {
		this.roomsExam = new ArrayList<String>(roomsExam);
		isSet[ROOMSEXAM]=true;
	}
	public ArrayList<Integer> getExamGrade() {
		return examGrade;
	}
	public void setExamGrade(List<Integer> examGrade) {
		this.examGrade = new ArrayList<Integer>(examGrade);
		isSet[EXAMGRADE]=true;
	}
}
