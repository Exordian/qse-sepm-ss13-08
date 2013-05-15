package at.ac.tuwien.sepm.service;

import static org.junit.Assert.*;

import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class TimeFrameTest {

	@Test(expected = RuntimeException.class)
	public void testFromAfterTo() {
		new TimeFrame(new DateTime("2000-01-04"),new DateTime("2000-01-01"));
	}
	@Test
	public void testSameTime() {
		new TimeFrame(new DateTime("2000-01-01"),new DateTime("2000-01-01"));
	}
	@Test
	public void testIntersect() {
		TimeFrame tf1 = new TimeFrame(new DateTime("2000-01-01"),new DateTime("2000-01-10"));
		TimeFrame tf2 = new TimeFrame(new DateTime("2000-01-11"),new DateTime("2000-01-20"));
		TimeFrame tf3 = new TimeFrame(new DateTime("2000-01-05"),new DateTime("2000-01-15"));
		
		assertTrue(tf1.intersect(tf3));
		assertTrue(tf3.intersect(tf1));
		
		assertFalse(tf1.intersect(tf2));
		assertFalse(tf2.intersect(tf1));
	}
	@Test
	public void testBeforeAfter() {
		TimeFrame tf1 = new TimeFrame(new DateTime("2000-01-01"),new DateTime("2000-01-10"));
		TimeFrame tf2 = new TimeFrame(new DateTime("2000-01-11"),new DateTime("2000-01-20"));
		
		assertTrue(tf1.before(tf2));
		assertFalse(tf1.after(tf2));
	}

}
