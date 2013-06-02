package at.ac.tuwien.sepm.service.semesterPlanning;

import static org.junit.Assert.*;

import java.util.ArrayList;

import at.ac.tuwien.sepm.entity.LVA;
import at.ac.tuwien.sepm.entity.MetaLVA;
import at.ac.tuwien.sepm.service.Semester;
import at.ac.tuwien.sepm.service.TimeFrame;
import at.ac.tuwien.sepm.service.semesterPlaning.IntelligentSemesterPlaner;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

//TODO bitte fixen...

@Ignore
public class IntelligentSemesterPlanerTest {
	private MetaLVA mLVA0;
	private LVA lva0;
	private MetaLVA mLVA1;
	private LVA lva1;
	private MetaLVA mLVA2;
	private LVA lva2;
	private MetaLVA mLVA3;
	private LVA lva3;
	private MetaLVA mLVA4;
	private LVA lva4;
	private MetaLVA mLVA5;
	private LVA lva5;
	ArrayList<MetaLVA> pool;


    /**
     * performs a simple test, if the planned semester is not empty
     */
	@Test
	public void test() {
		IntelligentSemesterPlaner planer = new IntelligentSemesterPlaner();
		//System.out.println(pool);
		planer.setLVAs(new ArrayList<MetaLVA>(), pool);
		//System.out.println(planer.planSemester(20,2013, Semester.S));
        assertFalse(planer.planSemester(20,2013, Semester.S).isEmpty());
	}
    /**
     * sets up a bunch of MetaLVAs and LVAs, so the Planner can be tested.
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        mLVA0 = new MetaLVA();
        lva0 = new LVA();
        mLVA1 = new MetaLVA();
        lva1 = new LVA();
        mLVA2 = new MetaLVA();
        lva2 = new LVA();
        mLVA3 = new MetaLVA();
        lva3 = new LVA();
        mLVA4 = new MetaLVA();
        lva4 = new LVA();
        mLVA5 = new MetaLVA();
        lva5 = new LVA();

        mLVA0.setNr("0");
        mLVA0.setPriority(5);
        mLVA0.setECTS(8f);
        mLVA0.setSemestersOffered(Semester.W_S);
        mLVA0.setCompleted(false);
        ArrayList<TimeFrame> times0 = new ArrayList<TimeFrame>();
        times0.add(new TimeFrame(new DateTime(2013,05,13,8,0,0),new DateTime(2013,05,13,10,0,0)));
        times0.add(new TimeFrame(new DateTime(2013,05,14,8,0,0),new DateTime(2013,05,14,10,0,0)));
        times0.add(new TimeFrame(new DateTime(2013,05,15,8,0,0),new DateTime(2013,05,15,10,0,0)));
        times0.add(new TimeFrame(new DateTime(2013,05,16,8,0,0),new DateTime(2013,05,16,10,0,0)));
        lva0.setTimes(times0);
        lva0.setYear(2013);
        lva0.setSemester(Semester.S);
        mLVA0.setLVA(lva0);

        mLVA1.setNr("1");
        mLVA1.setPriority(5);
        mLVA1.setECTS(6f);
        mLVA1.setSemestersOffered(Semester.W_S);
        mLVA1.setCompleted(false);
        ArrayList<TimeFrame> times1 = new ArrayList<TimeFrame>();
        times1.add(new TimeFrame(new DateTime(2013,05,13,14,0,0),new DateTime(2013,05,13,18,0,0)));
        times1.add(new TimeFrame(new DateTime(2013,05,16,12,0,0),new DateTime(2013,05,16,14,0,0)));
        lva1.setTimes(times1);
        lva1.setYear(2013);
        lva1.setSemester(Semester.S);
        mLVA1.setLVA(lva1);

        mLVA2.setNr("2");
        mLVA2.setPriority(5);
        mLVA2.setECTS(6f);
        mLVA2.setSemestersOffered(Semester.W_S);
        mLVA2.setCompleted(false);
        ArrayList<TimeFrame> times2 = new ArrayList<TimeFrame>();
        times2.add(new TimeFrame(new DateTime(2013,05,14,12,0,0),new DateTime(2013,05,14,16,0,0)));
        times2.add(new TimeFrame(new DateTime(2013,05,17,16,0,0),new DateTime(2013,05,17,18,0,0)));
        lva2.setTimes(times2);
        lva2.setYear(2013);
        lva2.setSemester(Semester.S);
        mLVA2.setLVA(lva2);

        mLVA3.setNr("3");
        mLVA3.setPriority(5);
        mLVA3.setECTS(4f);
        mLVA3.setSemestersOffered(Semester.W_S);
        mLVA3.setCompleted(false);
        ArrayList<TimeFrame> times3 = new ArrayList<TimeFrame>();
        times3.add(new TimeFrame(new DateTime(2013,05,16,12,0,0),new DateTime(2013,05,16,16,0,0)));
        lva3.setTimes(times3);
        lva3.setYear(2013);
        lva3.setSemester(Semester.S);
        mLVA3.setLVA(lva3);

        mLVA4.setNr("4");
        mLVA4.setPriority(5);
        mLVA4.setECTS(2f);
        mLVA4.setSemestersOffered(Semester.W_S);
        mLVA4.setCompleted(false);
        ArrayList<TimeFrame> times4 = new ArrayList<TimeFrame>();
        times4.add(new TimeFrame(new DateTime(2013,05,15,8,0,0),new DateTime(2013,05,15,10,0,0)));
        lva4.setTimes(times4);
        lva4.setYear(2013);
        lva4.setSemester(Semester.S);
        mLVA4.setLVA(lva4);

        mLVA5.setNr("5");
        mLVA5.setPriority(5);
        mLVA5.setECTS(10f);
        mLVA5.setSemestersOffered(Semester.W_S);
        mLVA5.setCompleted(false);
        ArrayList<TimeFrame> times5 = new ArrayList<TimeFrame>();
        times5.add(new TimeFrame(new DateTime(2013,05,17,10,0,0),new DateTime(2013,05,17,18,0,0)));
        lva5.setTimes(times5);
        lva5.setYear(2013);
        lva5.setSemester(Semester.S);
        mLVA5.setLVA(lva5);

        //ArrayList<MetaLVA> precursor1 = new ArrayList<MetaLVA>();
        //ArrayList<MetaLVA> precursor0 = new ArrayList<MetaLVA>();
        //precursor0.add(mLVA1);
        //precursor1.add(mLVA2);
        //precursor1.add(mLVA3);
        //mLVA0.setPrecursor(precursor0);
        //mLVA1.setPrecursor(precursor1);

        pool = new ArrayList<MetaLVA>();
        pool.add(mLVA0);
        pool.add(mLVA1);
        pool.add(mLVA2);
        pool.add(mLVA3);
        pool.add(mLVA4);
        pool.add(mLVA5);
    }

}
