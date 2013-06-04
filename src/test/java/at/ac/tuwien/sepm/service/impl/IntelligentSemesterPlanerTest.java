package at.ac.tuwien.sepm.service.impl;

import static org.junit.Assert.*;

import java.util.ArrayList;

import at.ac.tuwien.sepm.entity.LVA;
import at.ac.tuwien.sepm.entity.LvaDate;
import at.ac.tuwien.sepm.entity.MetaLVA;
import at.ac.tuwien.sepm.service.IntelligentSemesterPlaner;
import at.ac.tuwien.sepm.service.Semester;
import at.ac.tuwien.sepm.service.TimeFrame;
import at.ac.tuwien.sepm.service.impl.IntelligentSemesterPlanerImpl;
import org.joda.time.DateTime;
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
    public void testPlannedSemesterNotEmpty() {
        IntelligentSemesterPlaner planer = new IntelligentSemesterPlanerImpl();
        planer.setLVAs(new ArrayList<MetaLVA>(), pool);
        assertFalse(planer.planSemester(20,2013, Semester.S).isEmpty());
    }
    /**
     * passes empty lists to the planner
     */
    @Test
    public void testNoLVAS(){
        IntelligentSemesterPlaner planer = new IntelligentSemesterPlanerImpl();
        planer.setLVAs(new ArrayList<MetaLVA>(0), new ArrayList<MetaLVA>(0));
        assertTrue(planer.planSemester(30, 2013, Semester.S).isEmpty());
    }
    /**
     * passes empty lists to the planner
     */
    @Test
    public void testForcedLVA(){
        IntelligentSemesterPlaner planer = new IntelligentSemesterPlanerImpl();
        ArrayList<MetaLVA> forced = new ArrayList<MetaLVA>(1);
        forced.add(pool.get(0));
        planer.setLVAs(forced, pool);
        assertTrue(planer.planSemester(0,2013, Semester.S).size()==1);
        assertTrue(planer.planSemester(0,2013, Semester.S).get(0)==pool.get(0));
    }
    /**
     * plans semester, where the metaLVAs contain no LVA
     */
    @Test
    public void testPlanEmptyYear(){
        IntelligentSemesterPlaner planer = new IntelligentSemesterPlanerImpl();
        planer.setLVAs(new ArrayList<MetaLVA>(), pool);
        assertTrue(planer.planSemester(20,2012, Semester.S).isEmpty());
    }
    /**
     * alters one LVA and performs the test
     * @throws Exception
     */
    @Test
    public void testPlanChangeSemester(){
        IntelligentSemesterPlaner planer = new IntelligentSemesterPlanerImpl();
        planer.setLVAs(new ArrayList<MetaLVA>(), pool);
        LVA toAlter = pool.get(0).getLVA(2013,Semester.S);
        toAlter.setYear(2012);
        pool.get(0).setLVA(toAlter);
        assertTrue(planer.planSemester(20,2012, Semester.S).size()==1);
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
        ArrayList<LvaDate> times0 = new ArrayList<LvaDate>();
        times0.add(new LvaDate(new TimeFrame(new DateTime(2013,05,13,8,0,0),new DateTime(2013,05,13,10,0,0))));
        times0.add(new LvaDate(new TimeFrame(new DateTime(2013,05,14,8,0,0),new DateTime(2013,05,14,10,0,0))));
        times0.add(new LvaDate(new TimeFrame(new DateTime(2013,05,15,8,0,0),new DateTime(2013,05,15,10,0,0))));
        times0.add(new LvaDate(new TimeFrame(new DateTime(2013,05,16,8,0,0),new DateTime(2013,05,16,10,0,0))));
        lva0.setLectures(times0);
        lva0.setYear(2013);
        lva0.setSemester(Semester.S);
        mLVA0.setLVA(lva0);

        mLVA1.setNr("1");
        mLVA1.setPriority(5);
        mLVA1.setECTS(6f);
        mLVA1.setSemestersOffered(Semester.W_S);
        mLVA1.setCompleted(false);
        ArrayList<LvaDate> times1 = new ArrayList<LvaDate>();
        times1.add(new LvaDate(new TimeFrame(new DateTime(2013,05,13,14,0,0),new DateTime(2013,05,13,18,0,0))));
        times1.add(new LvaDate(new TimeFrame(new DateTime(2013,05,16,12,0,0),new DateTime(2013,05,16,14,0,0))));
        lva1.setLectures(times1);
        lva1.setYear(2013);
        lva1.setSemester(Semester.S);
        mLVA1.setLVA(lva1);

        mLVA2.setNr("2");
        mLVA2.setPriority(5);
        mLVA2.setECTS(6f);
        mLVA2.setSemestersOffered(Semester.W_S);
        mLVA2.setCompleted(false);
        ArrayList<LvaDate> times2 = new ArrayList<LvaDate>();
        times2.add(new LvaDate(new TimeFrame(new DateTime(2013,05,14,12,0,0),new DateTime(2013,05,14,16,0,0))));
        times2.add(new LvaDate(new TimeFrame(new DateTime(2013,05,17,16,0,0),new DateTime(2013,05,17,18,0,0))));
        lva2.setLectures(times2);
        lva2.setYear(2013);
        lva2.setSemester(Semester.S);
        mLVA2.setLVA(lva2);

        mLVA3.setNr("3");
        mLVA3.setPriority(5);
        mLVA3.setECTS(4f);
        mLVA3.setSemestersOffered(Semester.W_S);
        mLVA3.setCompleted(false);
        ArrayList<LvaDate> times3 = new ArrayList<LvaDate>();
        times3.add(new LvaDate(new TimeFrame(new DateTime(2013,05,16,12,0,0),new DateTime(2013,05,16,16,0,0))));
        lva3.setLectures(times3);
        lva3.setYear(2013);
        lva3.setSemester(Semester.S);
        mLVA3.setLVA(lva3);

        mLVA4.setNr("4");
        mLVA4.setPriority(5);
        mLVA4.setECTS(2f);
        mLVA4.setSemestersOffered(Semester.W_S);
        mLVA4.setCompleted(false);
        ArrayList<LvaDate> times4 = new ArrayList<LvaDate>();
        times4.add(new LvaDate(new TimeFrame(new DateTime(2013,05,15,8,0,0),new DateTime(2013,05,15,10,0,0))));
        lva4.setLectures(times4);
        lva4.setYear(2013);
        lva4.setSemester(Semester.S);
        mLVA4.setLVA(lva4);

        mLVA5.setNr("5");
        mLVA5.setPriority(5);
        mLVA5.setECTS(10f);
        mLVA5.setSemestersOffered(Semester.W_S);
        mLVA5.setCompleted(false);
        ArrayList<LvaDate> times5 = new ArrayList<LvaDate>();
        times5.add(new LvaDate(new TimeFrame(new DateTime(2013,05,17,10,0,0),new DateTime(2013,05,17,18,0,0))));
        lva5.setLectures(times5);
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
