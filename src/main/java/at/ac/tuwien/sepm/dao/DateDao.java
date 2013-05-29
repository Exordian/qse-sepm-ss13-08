package at.ac.tuwien.sepm.dao;

import at.ac.tuwien.sepm.entity.DateEntity;
import at.ac.tuwien.sepm.entity.LVA;
import at.ac.tuwien.sepm.service.Semester;
import org.joda.time.DateTime;
import org.springframework.dao.DataAccessException;

import java.io.IOException;
import java.util.List;

/**
 * Author: MUTH Markus
 * Date: 5/18/13
 * Time: 9:05 PM
 * Description of class "DateDao":
 */
public interface DateDao {

    /**
     * Store persistent a new Date. If the id of <code>toCreate</code> is set, it will be ignored. If
     * <code>toCreade.isIntersectable()</code> is undefined, the value will be stored as <code>false</code>.
     * @param toCreate a <code>DateEntity</code> containing all data to be stored persistent.
     * @return <code>false</code> if <code>toCreate==null</code> and <code>true</code> if the data could be stored.
     * @throws java.io.IOException If the data in toCreate is invalid, f. e. if Strings are too long to store.
     * @throws DataAccessException If the date data could not be stored because any error
     * occurred. There is also thrown an DataAccessException  if there are values null, which must not be null, these
     * are: lva.
     * @throws NullPointerException If <code>toCreate.getStart()==null</code> or
     * <code>toCreate.getStop()==null</code>
     *
     */
    public boolean create(DateEntity toCreate) throws IOException, DataAccessException;

    /*
     * Read all dates.
     * @return An ordered <code>ArrayList<DateEntity></code> containing all stored date data. The order is
     * chronological, beginning with oldest date. If there are several dates at the same time, there is no defined order.
     * @throws org.springframework.dao.DataAccessException if the date data could not be read because any error occurred.
     */
    //public ArrayList<DateEntity> readAll() throws DataAccessException;    // in geordneter Reihenfolge

    /**
     * Read the date data which is identified by the specified id.
     * @param id the id of the date which should be read.
     * @return a <code>DateEntity</code>  containing all data of the date and <code>null</code> if there is not date
     * with matching id.
     * @throws DataAccessException if the date data could not be read because any error occurred.
     */
    public DateEntity readById(int id) throws DataAccessException;

    /**
     * Read all dates where the start date between <code>from</code> and <code>till</code>.
     * @param from The start date and time. This moment is included to the search.
     * @param till The stop date and time. This moment is included to the search.
     * @return A <code>List<DateEntry></code> containing all dates within the specified time frame, or a empty list
     * if there was no matching date found. The order is chronological, beginning with oldest date. If there are several
     * dates at the same time, there is no defined order.
     * @throws org.springframework.dao.DataAccessException If the date data could not be read because any error occurred.
     */
    public List<DateEntity> readInTimeframe(DateTime from, DateTime till) throws DataAccessException;

    /**
     * Read all not intersectable dates which are in the specified year and semester. A winter semester starts on 1.
     * October and ends on 31. January, a summer semester starts on 1. March and ends on 30. June.
     * @param year The year.
     * @param semester The semester. Should be <code>Semester.S</code> or <code>Semester.W</code>.
     * @return A <code>List<LVA></code> where all dates are stored as TimeFrame into the field <code>times</code>. If
     * there was no date in the specified year and semester found <code>times.size()==0</code>. If
     * <code>!semester.equals(Semester.S) && !semester.equals(Semester.W)</code> there will be returned <code>null</code>.
     * @throws DataAccessException If the date data could not be read because any error occurred.
     * @throws NullPointerException If <code>semester==null</code>.
     */
    public LVA readNotIntersectableByYearSemester(int year, Semester semester) throws DataAccessException, NullPointerException;

    /*
     * Read all Dates within the specific time frame. It's also possible to specify if the date should be intersectable
     * with other dates. The order is chronological, beginning with oldest date. If there are several dates at the same
     * time, there is no defined order.
     * @param from The start date and time. This moment is included to the search.
     * @param till The stop date and time. This moment is included to the search.
     * @param isIntersectable <code>true</code> if the date should be intersectable with other dates and
     *                        <code>false</code> otherwise.
     * @return A <code>ArrayList<DateEntry></code> containing all dates within the specified time frame, or a empty list
     * if there was no matching date found.
     * @throws org.springframework.dao.DataAccessException If the date data could not be read because any error occurred.
     */
    //public ArrayList<DateEntity> readIntersectableInTimeframe(DateTime from, DateTime till, boolean isIntersectable) throws DataAccessException;

    /**
     * Update a date.
     * @param toUpdate A <code>DateEntity</code> containing all data to be updated. All attributes unequal
     *                 <code>null</code> are included to the update statements. The date to be updated is identified by
     *                 the id, so this should not be null.
     * @return <code>true</code> if there was a date found and was successfully updated and <code>false</code> if there
     * was no date with the specified id found or <code>toUpdate==null</code>.
     * @throws java.io.IOException IOException If the data in toCreate is invalid, f. e. if Strings are too long to
     * store.
     * @throws org.springframework.dao.DataAccessException DataAccessException If the date data could not be updated
     * because any error occurred, f. e. the id is <code>null</code>.
     */
    public boolean update(DateEntity toUpdate) throws IOException, DataAccessException;

    /**
     * Delete a date.
     * @param id the id of the date which should be deleted.
     * @return <code>true</code> if a date with the specified id could be found and was successfully deleted and
     * <code>false</code> if there is no date with the specified id.
     * @throws org.springframework.dao.DataAccessException DataAccessException If the date data could not be deleted
     * because any error occured.
     */
    public boolean delete(int id) throws DataAccessException;
}

