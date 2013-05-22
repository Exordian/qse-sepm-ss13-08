package at.ac.tuwien.sepm.service;

import static org.junit.Assert.*;

import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class TimeFrameTest {
    /**
     * Creates a new TimeFrame, where the second Date lies before the first.
     * This should throw an Exception.
     */
	@Test(expected = RuntimeException.class)
	public void testFromAfterTo() {
		new TimeFrame(new DateTime("2000-01-04"),new DateTime("2000-01-01"));
	}

    /**
     * Creates a new TimeFrame, where the first and second time are the same.
     * This is supposed to work
     */
	@Test
	public void testSameTime() {
		new TimeFrame(new DateTime("2000-01-01"),new DateTime("2000-01-01"));
	}

    /**
     * performs some tests, so see if the intersect-method is implemented correctly.
     * tf1 and tf3 should intersect, while tf1 and tf2 should not.
     * Also, symmetry is tested.
     */
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

    /**
     * tests, if the before and after-methods are implemented correctly
     */
	@Test
	public void testBeforeAfter() {
		TimeFrame tf1 = new TimeFrame(new DateTime("2000-01-01"),new DateTime("2000-01-10"));
		TimeFrame tf2 = new TimeFrame(new DateTime("2000-01-11"),new DateTime("2000-01-20"));
		
		assertTrue(tf1.before(tf2));
		assertFalse(tf1.after(tf2));
	}

    /**
     * tests, wether the intersect-method with an additional attribute for overlapping seconds works.
     * also tests symmetry
     */
    @Test
    public void testIntersectWithExtraSeconds(){
        TimeFrame tf1 = new TimeFrame(new DateTime(1999,01,01,0,0,0),new DateTime(2000,01,01,0,0,0));
        TimeFrame tf2 = new TimeFrame(new DateTime(2000,01,01,0,0,20),new DateTime(2001,01,01,0,0,0));

        assertFalse(tf1.intersect(tf2,19));
        assertTrue(tf1.intersect(tf2,21));

        assertFalse(tf2.intersect(tf1,19));
        assertTrue(tf2.intersect(tf1,21));
    }

}
