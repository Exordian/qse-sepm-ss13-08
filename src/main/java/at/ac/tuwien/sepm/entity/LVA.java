package at.ac.tuwien.sepm.entity;

import java.util.ArrayList;
import java.util.List;

import at.ac.tuwien.sepm.dao.LvaDateDao;
import at.ac.tuwien.sepm.service.Semester;
import at.ac.tuwien.sepm.service.TimeFrame;

/**
 * 
 * @author Georg Plaz
 *
 */

public class LVA {

    private int id;
	private MetaLVA metaLVA;
	private String description;
	private int year;
	private Semester semester;
	private int grade;

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
}
