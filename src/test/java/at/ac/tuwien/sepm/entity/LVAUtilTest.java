package at.ac.tuwien.sepm.entity;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Iterator;

import at.ac.tuwien.sepm.service.TimeFrame;
import at.ac.tuwien.sepm.service.semesterPlaning.LVAUtil;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class LVAUtilTest {

	@Before
	public void setUp(){
	}
	@Test
	public void testIteratorHasNext(){
		LVA lva = new LVA();
		assertFalse(LVAUtil.iterator(lva).hasNext());
		ArrayList<TimeFrame> times = new ArrayList<TimeFrame>();
		times.add(new TimeFrame(new DateTime("2000-01-04"),new DateTime("2000-01-06")));
		lva.setTimes(times);
		assertTrue(LVAUtil.iterator(lva).hasNext());
		assertFalse(LVAUtil.iterator(lva,LVAUtil.TIMES_EXAM).hasNext());
	}
	@Test
	public void testIteratorExamTimes(){
		LVA lva = new LVA();
		ArrayList<TimeFrame> times1 = new ArrayList<TimeFrame>();
		ArrayList<TimeFrame> times2 = new ArrayList<TimeFrame>();
		
		
		TimeFrame t0=new TimeFrame(new DateTime("2000-01-04"),new DateTime("2000-01-06"));
		times1.add(t0);
		TimeFrame t1=new TimeFrame(new DateTime("2000-01-25"),new DateTime("2000-01-26"));
		times2.add(t1);
		
		lva.setTimes(times1);
		lva.setTimesUE(times2);
		Iterator<TimeFrame> iter1 = LVAUtil.iterator(lva,LVAUtil.TIMES_EXAM);
		Iterator<TimeFrame> iter2 = LVAUtil.iterator(lva,LVAUtil.TIMES_UE);
		assertFalse(iter1.hasNext());
		assertTrue(iter2.hasNext());
		assertTrue(iter2.next()==t1);
		assertFalse(iter2.hasNext());
	}
	@Test
	public void testIteratorAllTimes(){
		LVA lva = new LVA();
		ArrayList<TimeFrame> times1 = new ArrayList<TimeFrame>();
		ArrayList<TimeFrame> times2 = new ArrayList<TimeFrame>();
		ArrayList<TimeFrame> times3 = new ArrayList<TimeFrame>();
		
		
		TimeFrame t0=new TimeFrame(new DateTime("2000-01-04"),new DateTime("2000-01-06"));
		times1.add(t0);
		TimeFrame t1=new TimeFrame(new DateTime("2000-01-25"),new DateTime("2000-01-26"));
		times1.add(t1);
		
		TimeFrame t2=new TimeFrame(new DateTime("2000-01-01"),new DateTime("2000-01-05"));
		times2.add(t2);
		TimeFrame t3=new TimeFrame(new DateTime("2000-01-10"),new DateTime("2000-01-13"));
		times2.add(t3);
		
		TimeFrame t4=new TimeFrame(new DateTime("2000-01-07"),new DateTime("2000-01-16"));
		times3.add(t4);
		TimeFrame t5=new TimeFrame(new DateTime("2000-01-20"),new DateTime("2000-01-21"));
		times3.add(t5);
		
		lva.setTimes(times1);
		lva.setTimesUE(times2);
		lva.setTimesExam(times3);
		Iterator<TimeFrame> iter = LVAUtil.iterator(lva);
		assertTrue(iter.hasNext());
		assertTrue(iter.next()==t2);
		assertTrue(iter.hasNext());
		assertTrue(iter.next()==t0);
		assertTrue(iter.hasNext());
		assertTrue(iter.next()==t4);
		assertTrue(iter.hasNext());
		assertTrue(iter.next()==t3);
		assertTrue(iter.hasNext());
		assertTrue(iter.next()==t5);
		assertTrue(iter.hasNext());
		assertTrue(iter.next()==t1);
		assertFalse(iter.hasNext());
	}
	/**
	 * tests, if the intersectAll() method works
	 */
	@Test
	public void testIntersectLVAs() {
		LVA lva1 = new LVA();
		LVA lva2 = new LVA();
		LVA lva3 = new LVA();
		LVA lva4 = new LVA();
		
		ArrayList<TimeFrame> times1 = new ArrayList<TimeFrame>();
		ArrayList<TimeFrame> times2 = new ArrayList<TimeFrame>();
		ArrayList<TimeFrame> times3 = new ArrayList<TimeFrame>();
		ArrayList<TimeFrame> times4 = new ArrayList<TimeFrame>();
		ArrayList<TimeFrame> times5 = new ArrayList<TimeFrame>();
		
		times1.add(new TimeFrame(new DateTime("2000-01-01"),new DateTime("2000-01-03")));
		times1.add(new TimeFrame(new DateTime("2000-01-07"),new DateTime("2000-01-09")));
		times1.add(new TimeFrame(new DateTime("2000-01-14"),new DateTime("2000-01-15")));
		
		times2.add(new TimeFrame(new DateTime("2000-01-04"),new DateTime("2000-01-06")));
		times2.add(new TimeFrame(new DateTime("2000-01-10"),new DateTime("2000-01-13")));
		
		times3.add(new TimeFrame(new DateTime("2000-01-14"),new DateTime("2000-01-16")));
		times3.add(new TimeFrame(new DateTime("2000-01-20"),new DateTime("2000-01-21")));
		
		times4.add(new TimeFrame(new DateTime("2000-02-01"),new DateTime("2000-02-03")));
		times4.add(new TimeFrame(new DateTime("2000-02-07"),new DateTime("2000-02-09")));
		
		times5.add(new TimeFrame(new DateTime("2000-02-04"),new DateTime("2000-02-06")));
		
		lva1.setTimes(times1);
		lva1.setTimesUE(times4);
		
		lva2.setTimesUE(times5);
		lva2.setTimes(times2);
		
		lva3.setTimes(times3);
		lva3.setTimesExam(times5);
		
		lva4.setTimesExam(times1);
		
		assertFalse(LVAUtil.intersectAll(lva1,lva2));
		assertFalse(LVAUtil.intersectAll(lva2,lva1));
		
		assertTrue(LVAUtil.intersectAll(lva1,lva3));
		assertTrue(LVAUtil.intersectAll(lva3,lva1));
		
		assertFalse(LVAUtil.intersect(lva1,lva2,LVAUtil.TIMES_UE));
		assertFalse(LVAUtil.intersect(lva2,lva1,LVAUtil.TIMES_UE));
		
		assertFalse(LVAUtil.intersect(lva3,lva4,LVAUtil.TIMES_EXAM));
		assertFalse(LVAUtil.intersect(lva4,lva3,LVAUtil.TIMES_EXAM));
		//fail("Not yet implemented");
	}

}
