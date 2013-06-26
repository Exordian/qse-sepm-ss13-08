package at.ac.tuwien.sepm.entity;

import at.ac.tuwien.sepm.service.Grade;
import at.ac.tuwien.sepm.service.Semester;
import at.ac.tuwien.sepm.service.impl.LVAUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 
 * @author Georg Plaz
 *
 */

public class LVA implements Comparable<LVA>{
    private int id;
    private MetaLVA metaLVA=null;
    private String description=null;
    private int year=-1;
    private Semester semester=null;
    private int grade=0;

    private Grade gradeEnum;
    private boolean inStudyProgress;
    private String goals;
    private String content;
    private String additionalInfo1;
    private String additionalInfo2;
    private String institute;
    private String performanceRecord;
    private String language;


    private ArrayList<LvaDate> lectures;
    //private ArrayList<String> rooms;
    //private ArrayList<String> lecturer;
    
    private ArrayList<LvaDate> exercises;
    //private ArrayList<String> roomsUE;
    //private String exerciseInstructor;
    
    private ArrayList<LvaDate> exams;
    //private ArrayList<String> roomsExam;
    //private ArrayList<Integer> examGrade;

    private ArrayList<LvaDate> deadlines;

    public MetaLVA getMetaLVA() {
        return metaLVA;
    }
    /**
     * should only be called by MetaLVA!
     * use MetaLVA.setLVAs(List<LVA>) or MetaLVA.setLVA(LVA) instead
     * @param metaLVA
     */
    public void setMetaLVA(MetaLVA metaLVA){
        this.metaLVA = metaLVA;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public int getYear() {
        return year;
    }
    public void setYear(int year) {
        this.year = year;
    }
    public Semester getSemester() {
        return semester;
    }

    /**
     * sets the Semester.
     * @param semester must either be Semester.W or Semester.S.
     */
    public void setSemester(Semester semester) {
        this.semester = semester;
    }
    public ArrayList<LvaDate> getLectures() {
        return lectures;
    }
    public void setLectures(List<LvaDate> lectures) {
        this.lectures = new ArrayList<LvaDate>(lectures);
    }
    /*
    public ArrayList<String> getRooms() {
        return rooms;
    }

    public void setRooms(List<String> rooms) {
        this.rooms = new ArrayList<String>(rooms);
    }

    public ArrayList<String> getLecturer() {
        return lecturer;
    }

    public void setLecturer(List<String> lecturer) {
        this.lecturer = new ArrayList<String>(lecturer);
    }

    public ArrayList<String> getRoomsUE() {
        return roomsUE;
    }

    public void setRoomsUE(List<String> roomsUE) {
        this.roomsUE = new ArrayList<String>(roomsUE);
    }

    public String getExerciseInstructor() {
        return exerciseInstructor;
    }

    public void setExerciseInstructor(String exerciseInstructor) {
        this.exerciseInstructor = exerciseInstructor;
    }
    */
    public ArrayList<LvaDate> getExams() {
        return exams;
    }

    public void setExams(List<LvaDate> exams) {
        this.exams = new ArrayList<LvaDate>(exams);
    }
    /*
    public ArrayList<String> getRoomsExam() {
        return roomsExam;
    }

    public void setRoomsExam(List<String> roomsExam) {
        this.roomsExam = new ArrayList<String>(roomsExam);
    }

    public ArrayList<Integer> getExamGrade() {
        return examGrade;
    }

    public void setExamGrade(List<Integer> examGrade) {
        this.examGrade = new ArrayList<Integer>(examGrade);
    }
    */
    public List<LvaDate> getDeadlines() {
        return deadlines;
    }

    public void setDeadlines(List<LvaDate> deadlines) {
        this.deadlines = new ArrayList<LvaDate>(deadlines);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isInStudyProgress() {
        return inStudyProgress;
    }

    public void setInStudyProgress(boolean inStudyProgress) {
        this.inStudyProgress = inStudyProgress;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getPerformanceRecord() {
        return performanceRecord;
    }

    public void setPerformanceRecord(String performanceRecord) {
        this.performanceRecord = performanceRecord;
    }

    public String getInstitute() {
        return institute;
    }

    public void setInstitute(String institute) {
        this.institute = institute;
    }

    public String getAdditionalInfo2() {
        return additionalInfo2;
    }

    public void setAdditionalInfo2(String additionalInfo2) {
        this.additionalInfo2 = additionalInfo2;
    }

    public String getAdditionalInfo1() {
        return additionalInfo1;
    }

    public void setAdditionalInfo1(String additionalInfo1) {
        this.additionalInfo1 = additionalInfo1;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getGoals() {
        return goals;
    }

    public void setGoals(String goals) {
        this.goals = goals;
    }

    public ArrayList<LvaDate> getExercises() {
        return exercises;
    }

    public void setExercises(List<LvaDate> exercises) {
        this.exercises = new ArrayList<LvaDate>(exercises);
    }

    public Grade getGradeEnum() {
        return gradeEnum;
    }

    public void setGrade(Grade toSet) {
        this.gradeEnum=toSet;
        grade = toSet.ordinal();
        if(grade>5){
            grade=0;
        }
    }
    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        if(grade>0 && grade <=5){
            gradeEnum = Grade.getGrade(grade);
        }else{
            gradeEnum=Grade.NOT_SET;
        }
        this.grade = grade;
    }
    @Override
    public String toString(){
        String toReturn="<LVA: id:"+id;
        if(metaLVA!=null && metaLVA.getName()!=null){
            toReturn+=" metaLVA:"+metaLVA.getName();
        }
        if(year!=-1){
            toReturn+=" year:"+year;
        }
        if(semester!=null){
            toReturn+=" semester:"+semester;
        }
        return toReturn+">";
    }
    public String toShortString(){
        String toReturn="<LVA:";
        if(metaLVA!=null && metaLVA.getName()!=null){
            if(metaLVA.getName().length()>7){
                toReturn+=" metaLVA:"+metaLVA.getName().substring(0,7)+"..";
            }else{
                toReturn+=" metaLVA:"+metaLVA.getName();
            }
        }
        if(year!=-1){
            toReturn+=" year:"+year;
        }
        if(semester!=null){
            toReturn+=" sem:"+semester;
        }
        return toReturn+">";
    }

    public String toDetailedStringWithTime(int indentCount){
        String toReturn = toString();
        String indent="";
        for(int i=0;i<indentCount;i++){
            indent+="\t";
        }
        List<Integer> considered = new ArrayList<Integer>();
        if(lectures!=null){
            considered.add(LVAUtil.LECTURE_TIMES);
        }
        if(exercises!=null){
            considered.add(LVAUtil.EXERCISES_TIMES);
        }
        if(exams!=null){
            considered.add(LVAUtil.EXAM_TIMES);
        }
        Iterator<LvaDate> iter = LVAUtil.iterator(this);
        int index =0;
        while(iter.hasNext()){
            toReturn+="\n"+indent+(index++)+")"+iter.next();
        }
        return toReturn;
    }
    public String toDetailedStringWithoutTime() {
        return "LVA{" +
                "id=" + id +
                ", metaLVA=" + metaLVA +
                ", description='" + description + '\'' +
                ", year=" + year +
                ", semester=" + semester +
                ", gradeEnum=" + gradeEnum +
                ", inStudyProgress=" + inStudyProgress +
                ", goals='" + goals + '\'' +
                ", content='" + content + '\'' +
                ", additionalInfo1='" + additionalInfo1 + '\'' +
                ", additionalInfo2='" + additionalInfo2 + '\'' +
                ", institute='" + institute + '\'' +
                ", performanceRecord='" + performanceRecord + '\'' +
                ", language='" + language + '\'' +
                '}';
    }
    @Override
    public int compareTo(LVA o) {
        return year-o.year; //todo: include semester and test ordering
    }



    public static boolean equalsForMerge(LVA oldLVA,LVA newLVA) {
        if (oldLVA == newLVA) return true;
        if (oldLVA == null || newLVA == null) return false;


        if (newLVA.year != oldLVA.year) return false;
        if (newLVA.additionalInfo1 != null &&( oldLVA.additionalInfo1== null || !newLVA.additionalInfo1.equals(oldLVA.additionalInfo1)))
            return false;
        if (newLVA.additionalInfo2 != null &&( oldLVA.additionalInfo2== null || !newLVA.additionalInfo2.equals(oldLVA.additionalInfo2)))
            return false;
        if (newLVA.content != null &&( oldLVA.content== null || !newLVA.content.equals(oldLVA.content))) return false;
        if (newLVA.description != null &&( oldLVA.description== null || !newLVA.description.equals(oldLVA.description))) return false;
        if (newLVA.goals != null &&( oldLVA.goals== null || !newLVA.goals.equals(oldLVA.goals))) return false;
        if (newLVA.institute != null &&( oldLVA.institute== null || !newLVA.institute.equals(oldLVA.institute))) return false;
        if (newLVA.language != null &&( oldLVA.language== null || !newLVA.language.equals(oldLVA.language))) return false;
        if (newLVA.performanceRecord != null &&( oldLVA.performanceRecord== null || !newLVA.performanceRecord.equals(oldLVA.performanceRecord)))
            return false;
        if (newLVA.semester != null &&( oldLVA.semester== null || newLVA.semester != oldLVA.semester)) return false;

        return true;
    }

}
