package at.ac.tuwien.sepm.service;

import org.joda.time.DateTime;
import org.junit.Test;

/**
 * Author: MUTH Markus
 * Date: 6/18/13
 * Time: 10:12 PM
 * Description of class "SemesterDateGeneratorTest":
 */
public class SemesterDateGeneratorTest {

    @Test
    public void testGetTimeFrame() throws Exception {
        TimeFrame s0 = SemesterDateGenerator.getTimeFrame(2009, Semester.S);
        TimeFrame w0 = SemesterDateGenerator.getTimeFrame(2009, Semester.W);
        TimeFrame s1 = SemesterDateGenerator.getTimeFrame(2011, Semester.S);
        TimeFrame w1 = SemesterDateGenerator.getTimeFrame(2011, Semester.W);

        assert(s0.from().equals(new DateTime(2009, 3, 1, 0, 0, 0, 0)) && s0.to().equals(new DateTime(2009, 7, 31, 23, 59, 59, 999)));
        assert(w0.from().equals(new DateTime(2009, 10, 1, 0, 0, 0, 0)) && w0.to().equals(new DateTime(2010, 2, 28, 23, 59, 59, 999)));
        assert(s1.from().equals(new DateTime(2011, 3, 1, 0, 0, 0, 0)) && s1.to().equals(new DateTime(2011, 7, 31, 23, 59, 59, 999)));
        assert(w1.from().equals(new DateTime(2011, 10, 1, 0, 0, 0, 0)) && w1.to().equals(new DateTime(2012, 2, 29, 23, 59, 59, 999)));
    }

    @Test
    public void testGetTimeFrameSemesterIsInvalid() throws Exception {
        TimeFrame t0 = SemesterDateGenerator.getTimeFrame(2009, Semester.W_S);
        TimeFrame t1 = SemesterDateGenerator.getTimeFrame(2009, Semester.UNKNOWN);

        assert(t0==null);
        assert(t1==null);
    }

    @Test
    public void testGetTimeFrameWithDate() throws Exception {
        TimeFrame s0 = SemesterDateGenerator.getTimeFrame(new DateTime(2013, 1, 2, 2, 2, 2, 2));
        TimeFrame w0 = SemesterDateGenerator.getTimeFrame(new DateTime(2013, 2, 2, 2, 2, 2, 2));
        TimeFrame s1 = SemesterDateGenerator.getTimeFrame(new DateTime(2013, 3, 2, 2, 2, 2, 2));
        TimeFrame w1 = SemesterDateGenerator.getTimeFrame(new DateTime(2013, 7, 2, 2, 2, 2, 2));
        TimeFrame s2 = SemesterDateGenerator.getTimeFrame(new DateTime(2011, 11, 2, 2, 2, 2, 2));
        TimeFrame w2 = SemesterDateGenerator.getTimeFrame(new DateTime(2012, 2, 2, 2, 2, 2, 2));

        assert(s0.from().equals(new DateTime(2012, 10, 1, 0, 0, 0, 0)) && s0.to().equals(new DateTime(2013, 2, 28, 23, 59, 59, 999)));
        assert(w0.from().equals(new DateTime(2012, 10, 1, 0, 0, 0, 0)) && w0.to().equals(new DateTime(2013, 2, 28, 23, 59, 59, 999)));
        assert(s1.from().equals(new DateTime(2013, 3, 1, 0, 0, 0, 0)) && s1.to().equals(new DateTime(2013, 7, 31, 23, 59, 59, 999)));
        assert(w1.from().equals(new DateTime(2013, 3, 1, 0, 0, 0, 0)) && w1.to().equals(new DateTime(2013, 7, 31, 23, 59, 59, 999)));
        assert(s2.from().equals(new DateTime(2011, 10, 1, 0, 0, 0, 0)) && s2.to().equals(new DateTime(2012, 2, 29, 23, 59, 59, 999)));
        assert(w2.from().equals(new DateTime(2011, 10, 1, 0, 0, 0, 0)) && w2.to().equals(new DateTime(2012, 2, 29, 23, 59, 59, 999)));
    }

    @Test
    public void testGetDateInTimeFrameDateIsNull() throws Exception {
        TimeFrame t0 = SemesterDateGenerator.getTimeFrame(null);
        assert(t0==null);
    }

    @Test
    public void testGetDateInTimeFrameDateOutOfRange() throws Exception {
        TimeFrame t0 = SemesterDateGenerator.getTimeFrame(new DateTime(2013, 8, 8, 1, 1, 1, 1));
        assert(t0==null);
    }

    @Test
    public void testGetSemester() throws Exception {
        DateTime temp = new DateTime(2013,2,1,1,1);
        assert(SemesterDateGenerator.getSemester(temp) == Semester.W);

        DateTime temp2 = new DateTime(2013,6,1,1,1);
        assert(SemesterDateGenerator.getSemester(temp2) == Semester.S);
    }

    @Test
    public void testGetYear() throws Exception {
        DateTime temp = new DateTime(2013,2,1,1,1);
        assert(SemesterDateGenerator.getYear(temp)==2012);

        DateTime temp2 = new DateTime(2013,3,1,1,1);
        assert(SemesterDateGenerator.getYear(temp2)==2013);
    }
}
