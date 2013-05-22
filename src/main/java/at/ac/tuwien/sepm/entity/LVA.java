package at.ac.tuwien.sepm.entity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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


	private ArrayList<TimeFrame> times;
	private ArrayList<String> rooms;
	private ArrayList<String> lecturer;
	
	private ArrayList<TimeFrame> timesUE;
	private ArrayList<String> roomsUE;
	private String exerciseInstructor;
	
	private ArrayList<TimeFrame> timesExam;
	private ArrayList<String> roomsExam;
	private ArrayList<Integer> examGrade;


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
	public ArrayList<TimeFrame> getTimes() {
		return times;
	}
	public void setTimes(List<TimeFrame> times) {
		this.times = new ArrayList<TimeFrame>(times);
	}
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

	public ArrayList<TimeFrame> getTimesExam() {
		return timesExam;
	}

	public void setTimesExam(List<TimeFrame> timesExam) {
		this.timesExam = new ArrayList<TimeFrame>(timesExam);
	}

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

    public ArrayList<TimeFrame> getTimesUE() {
        return timesUE;
    }

    public void setTimesUE(ArrayList<TimeFrame> timesUE) {
        this.timesUE = timesUE;
    }


    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }
}
