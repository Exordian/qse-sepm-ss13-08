package at.ac.tuwien.sepm.dao;

import at.ac.tuwien.sepm.entity.Curriculum;
import org.springframework.dao.DataAccessException;

import java.io.IOException;

/**
 * Author: MUTH Markus
 * Date: 5/18/13
 * Time: 9:05 PM
 * Description of class "DBCurriculumDao":
 */
public interface CurriculumDao {
    /**
     * Store persistent a new Curriculum. The included modules are not stored.
     * @param toCreate The Curriculum which contains all data of the curriculum.
     * @throws IOException  If the data in Curriculum is invalid, f. e. if a String is too long to be saved
     *                      persistent.
     * @throws org.springframework.dao.DataAccessException If the data from newCurriculum could not be saved because any
     * other error occured.
     */
    public boolean create(Curriculum toCreate) throws IOException, DataAccessException;

    /**
     * Read the curriculum data from the specified curriculum. The modules of this curriculum are also returned. See
     * ModuleDao.readById() for more information.
     * @param id the system internal id for the curriculum.
     * @return A <code>Curriculum</code> filled with the data from the Curriculum or <code>null</code> if there is no curriculum with the given id.
     * @throws org.springframework.dao.DataAccessException If the Curriculum data could not be read because any error occured.
     */
    public Curriculum readById(int id) throws DataAccessException;

    /**
     * Read the curriculum data from the specified curriculum. The modules of this curriculum are also returned. See
     * ModuleDao.readById() for more information.
     * @param studyNumber the study number for the study which is defined by the related curriculum.
     * @return A <code>Curriculum</code> filled with the data from the Curriculum or <code>null</code> if there is no curriculum with the given studyNumber.
     * @throws org.springframework.dao.DataAccessException If the Curriculum data could not be read because any error occured.
     */
    public Curriculum readByStudynumber(String studyNumber) throws DataAccessException;

    /**
     * Update a curriculum. All attributes of the <code>Curriculum</code>, which are not <code>null</code>, are included
     * to the update statements. The curriculum which should be updated is identified by the id. The perhaps containing
     * modules are not updated.
     * @param toUpdate The entity containing all data which should be updated.
     * @return <code>true</code> if there was a curriculum found and was successfully updated and <code>false</code> if
     * there was no curriculum with the specified id of <code>toUpdate==null</code>.
     * @throws IOException If the name, description, academic title or the study number are too long to store.
     * @throws DataAccessException If the data could not be updated because any error occurred.
     */
    public boolean update(Curriculum toUpdate) throws IOException, DataAccessException;

    /**
     * Delete a stored curriculum. The modules of the curriculum are not deleted.
     * @param id the id of the curriculum to be deleted.
     * @return <code>true</code> if a curriculum with the specified id could be found and successfully deleted and
     * <code>false</code> if there is no curriculum with the specified id.
     * @throws DataAccessException If the Curriculum data could not be deleted because any error occurred.
     */
    public boolean delete(int id) throws DataAccessException;
}
