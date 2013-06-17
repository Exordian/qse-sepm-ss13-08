package at.ac.tuwien.sepm.dao;

import at.ac.tuwien.sepm.entity.TissExam;
import at.ac.tuwien.sepm.entity.Track;
import at.ac.tuwien.sepm.service.Semester;
import org.springframework.dao.DataAccessException;

import java.io.IOException;
import java.util.List;

/**
 * @author Markus MUTH
 */
public interface PendingRegistrationDao {
    /**
     * Persists a new Registration
     * @param toCreate A <code>TissExam</code> containing all data to be stored.
     * @throws java.io.IOException If the name or the description are too long to store.
     * @throws org.springframework.dao.DataAccessException If the data could not be stored because any other error occurred.
     * @throws NullPointerException If <code>toCreate.getStart()==null</code> or <code>toCreate.getStop()==null</code>.
     */
    public boolean create(TissExam toCreate) throws IOException, DataAccessException;

    /**
     * Read all stored persisted pending Tiss Exams Registrations
     * @return An <code>List<TissExam></code> containing all pending tiss exams
     * @throws org.springframework.dao.DataAccessException If the data could not be read because any other error occurred.
     */
    public List<TissExam> readAllTissExams() throws DataAccessException;

    /**
     * Delete the pending tiss exam with the given id.
     * @param id The identifier of the tissexam which should be deleted.
     * @return <code>true</code> if a tissexam with the specified id could be found and was successfully deleted and
     * <code>false</code> if there is no tissexam with the specified id.
     * @throws org.springframework.dao.DataAccessException If the data could not be deleted because any other error occurred.
     */
    public boolean delete(int id) throws DataAccessException;
}
