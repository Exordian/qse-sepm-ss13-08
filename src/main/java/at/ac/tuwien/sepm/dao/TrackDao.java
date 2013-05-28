package at.ac.tuwien.sepm.dao;

import at.ac.tuwien.sepm.entity.Track;
import at.ac.tuwien.sepm.service.Semester;
import org.springframework.dao.DataAccessException;

import java.io.IOException;
import java.util.List;

/**
 * @author Markus MUTH
 */
public interface TrackDao {
    /**
     * Insert persistent a new track.
     * @param toCreate A <code>Track</code> containing all data to be stored.
     * @throws IOException If the name or the description are too long to store.
     * @throws DataAccessException If the data could not be stored because any other error occurred.
     * @throws NullPointerException If <code>toCreate.getStart()==null</code> or <code>toCreate.getStop()==null</code>.
     */
    public boolean create(Track toCreate) throws IOException, DataAccessException;

    /*
     * Read all stored tracks.
     * @return An <code>ArrayList<Track></code> containing all stored tracks.
     * @throws org.springframework.dao.DataAccessException If the data could not be read because any other error occurred.
     */
    //public ArrayList<Track> readAll() throws DataAccessException;

    /**
     * Read the to do with the specified id.
     * @param id The id of the track.
     * @return A to do containing all stored data.
     * @throws DataAccessException If the data could not be read because any other error occurred.
     */
    public Track readById(int id) throws DataAccessException;

    /**
     * Read all stored tracks from the specified lva.DataAccessException The order of the returned tracks is chronological, beginning with
     * the oldest start date.
     * @param lva The id of the lva from which the tracks should be read.
     * @return An <code>List<Track></code> containing all matching tracks or a empty list if there are no matching
     * tracks.
     * @throws DataAccessException If the data could not be read because any other error occurred.
     */
    public List<Track> readByLva(int lva) throws DataAccessException;


    /**
     * Read all stored tracks from the specified semester. The order of the returned tracks is chronological, beginning
     * with the oldest start date.
     * @param year The year from which the tracks should be read.
     * @param semester The semester. If <code>semester=null</code> there will be
     * @return An <code>List<Track></code> containing all matching tracks or a empty list if there are no matching
     * tracks or <code>null</code> if <code>semester==null</code> or <code>!semester.equals(Semester.W) && !semester.equals(Semester.S)</code>
     * @throws DataAccessException If the data could not be read because any other error occurred.
     */
    public List<Track> readBySemester(int year, Semester semester) throws DataAccessException;

    /**
     * Update the values of a track. Every attribute of toUpdate, which is not null, is included to the
     * update-statements. The track is identified by its id.
     * @param toUpdate The <code>Track</code> containing all values which should be updated.
     * @return <code>true</code> if there was a track found and was successfully updated and <code>false</code> if
     * there was no track with the specified id found or <code>toCreate==null</code>.
     * @throws IOException If the name or the description are too long to store.
     * @throws DataAccessException If the data could not be updated because any other error occurred.
     */
    public boolean update(Track toUpdate) throws IOException, DataAccessException;

    /**
     * Delete the with <code>toDelete</code> identified track.
     * @param id The identifier of the track which should be deleted.
     * @return <code>true</code> if a track with the specified id could be found and was successfully deleted and
     * <code>false</code> if there is no track with the specified id.
     * @throws DataAccessException If the data could not be deleted because any other error occurred.
     */
    public boolean delete(int id) throws DataAccessException;
}
