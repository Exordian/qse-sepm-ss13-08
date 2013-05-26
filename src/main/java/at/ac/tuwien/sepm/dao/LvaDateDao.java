package at.ac.tuwien.sepm.dao;

import at.ac.tuwien.sepm.entity.LvaDate;
import at.ac.tuwien.sepm.entity.LvaDateType;
import at.ac.tuwien.sepm.service.LvaType;
import org.joda.time.DateTime;
import org.springframework.dao.DataAccessException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: MUTH Markus
 * Date: 5/18/13
 * Time: 9:06 PM
 * Description of class "LvaDateDao":
 */
public interface LvaDateDao {

    /**
     * Store a new lva date. If <code>getResult()==null</code>, it will be stored with 0.
     * @param toCreate the <code>LvaDate</code> containing the data to be stored.
     * @return <code>false</code> if <code>toCreate==null</code> and <code>true</code> if the data could be stored.
     * @throws IOException if the Strings <code>getRoom()</code>, <code>getDescription</code> or <code>getName()</code>
     * are too long to save.
     * @throws DataAccessException DataAccessException If the date data could not be stored because any error
     * occurred. There is also thrown an DataAccessException  if there are values null, which must not be null, these
     * are: lva.
     * @throws NullPointerException If <code>toCreate.getStart()==null</code> or  <code>toCreate.getStop()==null</code>.
     */
    public boolean create(LvaDate toCreate) throws IOException, DataAccessException;

    /**
     * Read the lva date data which is identified by the specified id.
     * @param id the id of the lva date which should be read.
     * @return a <code>DateEntity</code>  containing all data of the lva date.
     * @throws org.springframework.dao.DataAccessException if the lva date data could not be read because any error occurred.
     */
    public LvaDate readById(int id) throws DataAccessException;

    /*
     * Read all lva dates within the specific time frame. The order is chronological, beginning with oldest date. If there are several lva dates at the same time, there is no defined order.
     * @param from The start date and time. This moment is included to the search.
     * @param till The stop date and time. This moment is included to the search.
     * @return A <code>ArrayList<LvaDateEntry></code> containing all lva dates within the specified time frame, or a empty list if there was no matching date found.
     * @throws org.springframework.dao.DataAccessException If the lva date data could not be read because any error occurred.
    */
    //public ArrayList<LvaDate> readInTimeframe(DateTime from, DateTime till) throws DataAccessException;

    /*
     * Read all lva dates within the specific time frame. It's also possible to specify if the lva date should be intersectable with other dates. The order is chronological, beginning with oldest date. If there are several dates at the same time, there is no defined order.
     * @param from The start date and time. This moment is included to the search.
     * @param till The stop date and time. This moment is included to the search.
     * @param attendanceRequired <code>true</code> attendance is required and <code>false</code> otherwise.
     * @return A <code>ArrayList<DateEntry></code> containing all lva dates within the specified time frame, or a empty list if there was no matching date found.
     * @throws org.springframework.dao.DataAccessException If the date data could not be read because any error occurred.
     */
    //public ArrayList<LvaDate> readIntersectableInTimeframe(DateTime from, DateTime till, boolean attendanceRequired) throws DataAccessException;

    /*
     * Read all lva dates within the specific time frame. It's also possible to specify if the lva date should be intersectable with other dates and to which lva the date should be related. The order is chronological, beginning with oldest date. If there are several dates at the same time, there is no defined order.
     * @param from The start date and time. This moment is included to the search.
     * @param till The stop date and time. This moment is included to the search.
     * @param attendanceRequired <code>true</code> attendance is required and <code>false</code> otherwise.
     * @param lva the id of the lva to which this date is related.
     * @return A <code>ArrayList<DateEntry></code> containing all lva dates within the specified time frame, or a empty list if there was no matching date found.
     * @throws org.springframework.dao.DataAccessException If the lva date data could not be read because any error occurred.
     */
    //public ArrayList<LvaDate> readIntersectableInTimeframe(DateTime from, DateTime till, boolean attendanceRequired, int lva) throws DataAccessException;

    /*
     * Read all lva dates from the specified semester. It's also possible to specify if the lva date should be intersectable with other dates and to which lva the date should be related. The order is chronological, beginning with oldest date. If there are several dates at the same time, there is no defined order.
     * @param year The year in which the lva date occurs.
     * @param isWinterSemester <code>true</code> if the lva date occurs in winter semester, <code>true</code> otherwise.
     * @param attendanceRequired <code>true</code> attendance is required and <code>false</code> otherwise.
     * @param lva the id of the lva to which this date is related.
     * @return A <code>ArrayList<DateEntry></code> containing all lva dates within the specified time frame, or a empty list if there was no matching date found.
     * @throws org.springframework.dao.DataAccessException If the lva date data could not be read because any error occurred.
     */
    //public ArrayList<LvaDate> readIntersectableInTimeframe(int year, boolean isWinterSemester, boolean attendanceRequired, int lva) throws DataAccessException;

    /*
     * Read all lva dates from the specified semester. It's also possible to specify if the lva date should be intersectable with other dates and to which lva the date should be related. The order is chronological, beginning with oldest date. If there are several dates at the same time, there is no defined order.
     * @param year The year in which the lva date occurs.
     * @param isWinterSemester <code>true</code> if the lva date occurs in winter semester, <code>true</code> otherwise.
     * @param attendanceRequired <code>true</code> attendance is required and <code>false</code> otherwise.
     * @return A <code>ArrayList<DateEntry></code> containing all lva dates within the specified time frame, or a empty list if there was no matching date found.
     * @throws org.springframework.dao.DataAccessException If the lva date data could not be read because any error occurred.
     */
    //public ArrayList<LvaDate> readIntersectableInTimeframe(int year, boolean isWinterSemester, boolean attendanceRequired) throws DataAccessException;+

    /**
     * Read all lva dates from the specifed lva. The order of the returned dates is chronological, beginning at the
     * oldest one.
     * @param lvaId the id of the lva.
     * @return A <code>List<LvaDate></code> containing all stored data or a empty <code>List</code> if there was no lva
     * date from the specified lva found.
     * @throws DataAccessException If the lva date data could not be read because any error occurred.
     */
    public List<LvaDate> readByLva(int lvaId) throws DataAccessException;

    /**
     * Read all lva dates from the specified lva.
     * @param lvaId the id of the lva.
     * @return A <code>LvaDate</code> containing all stored data or <code>null</code> if there was no lva date from the
     * specified lva found.
     * @throws DataAccessException If the lva date data could not be read because any error occurred.
     */
    /**
     * Read all lva dates from the specified type and lva. The order of the returned dates is chronological, beginning
     * at the oldest one.
     * @param lvaId the id of the lva.
     * @param type the type of the lva.
     * @return A <code>LvaDate</code> containing all stored data or <code>null</code> if there was no lva date from the
     * specified lva found.
     * @throws DataAccessException If the lva date data could not be read because any error occurred.
     */
    public List<LvaDate> readByLvaAndType(int lvaId, LvaDateType type) throws DataAccessException;

    /**
     * Update a lva date.
     * @param toUpdate A <code>LvaDate</code> containing all data to be updated. All attributes unequal <code>null</code>
     *                 are included to the update statements. The lva date to be updated is identified by the id,
     * @return <code>true</code> if there was a lva date found and was successfully updated and <code>false</code> if
     * <code>toUpdate.getId()==null</code> or there was no lva date with the specified id found.
     * @throws IOException If the data in toCreate is invalid, f. e. if Strings are too long to store.
     * @throws DataAccessException DataAccessException If the date data could not be updated
     * because any error occurred.
     * @throws NullPointerException If <code>toCreate.getStart()==null</code> or <code>toCreate.getStop()==null</code>
     */
    public boolean update(LvaDate toUpdate) throws IOException, DataAccessException;

    /**
     * Delete a lva date.
     * @param id the id of the lva date which should be deleted.
     * @return <code>true</code> if a lva date with the specified id could be found and was successfully deleted and <code>false</code> if there is no date with the specified id.
     * @throws org.springframework.dao.DataAccessException DataAccessException If the lva date data could not be deleted because any error occurred.
     */
    public boolean delete(int id) throws DataAccessException;
}
