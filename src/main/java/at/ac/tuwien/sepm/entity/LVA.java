package at.ac.tuwien.sepm.entity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import at.ac.tuwien.sepm.service.Semester;
import at.ac.tuwien.sepm.service.impl.LVAUtil;

/**
 * 
 * @author Georg Plaz
 *
 */

public class LVA {
    private int id;
    private MetaLVA metaLVA=null;
    private String description=null;
    private int year=-1;
    private Semester semester=null;
    private int grade=0;

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


    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }
    @Override
    public String toString(){
        String toReturn="<LVA:";
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
    public String toDetailedString(int indentCount){
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LVA)) return false;

        LVA lva = (LVA) o;

        if (grade != lva.grade) return false;
        if (id != lva.id) return false;
        if (inStudyProgress != lva.inStudyProgress) return false;
        if (year != lva.year) return false;
        if (additionalInfo1 != null ? !additionalInfo1.equals(lva.additionalInfo1) : lva.additionalInfo1 != null)
            return false;
        if (additionalInfo2 != null ? !additionalInfo2.equals(lva.additionalInfo2) : lva.additionalInfo2 != null)
            return false;
        if (content != null ? !content.equals(lva.content) : lva.content != null) return false;
        if (deadlines != null ? !deadlines.equals(lva.deadlines) : lva.deadlines != null) return false;
        if (description != null ? !description.equals(lva.description) : lva.description != null) return false;
        if (exams != null ? !exams.equals(lva.exams) : lva.exams != null) return false;
        if (exercises != null ? !exercises.equals(lva.exercises) : lva.exercises != null) return false;
        if (goals != null ? !goals.equals(lva.goals) : lva.goals != null) return false;
        if (institute != null ? !institute.equals(lva.institute) : lva.institute != null) return false;
        if (language != null ? !language.equals(lva.language) : lva.language != null) return false;
        if (lectures != null ? !lectures.equals(lva.lectures) : lva.lectures != null) return false;
        if (metaLVA != null ? !metaLVA.equals(lva.metaLVA) : lva.metaLVA != null) return false;
        if (performanceRecord != null ? !performanceRecord.equals(lva.performanceRecord) : lva.performanceRecord != null)
            return false;
        if (semester != lva.semester) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (metaLVA != null ? metaLVA.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + year;
        result = 31 * result + (semester != null ? semester.hashCode() : 0);
        result = 31 * result + grade;
        result = 31 * result + (inStudyProgress ? 1 : 0);
        result = 31 * result + (goals != null ? goals.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (additionalInfo1 != null ? additionalInfo1.hashCode() : 0);
        result = 31 * result + (additionalInfo2 != null ? additionalInfo2.hashCode() : 0);
        result = 31 * result + (institute != null ? institute.hashCode() : 0);
        result = 31 * result + (performanceRecord != null ? performanceRecord.hashCode() : 0);
        result = 31 * result + (language != null ? language.hashCode() : 0);
        result = 31 * result + (lectures != null ? lectures.hashCode() : 0);
        result = 31 * result + (exercises != null ? exercises.hashCode() : 0);
        result = 31 * result + (exams != null ? exams.hashCode() : 0);
        result = 31 * result + (deadlines != null ? deadlines.hashCode() : 0);
        return result;
    }
}
