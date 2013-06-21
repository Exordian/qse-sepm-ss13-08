package at.ac.tuwien.sepm.dao;

import at.ac.tuwien.sepm.entity.Module;
import org.springframework.dao.DataAccessException;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * @author Markus MUTH
 */
public interface ModuleDao {
    /**
     * Insert persistent a new module. The id of <code>toCreate</code> is ignored. The containing meta lvas are not
     * created.
     * @param toCreate A <code>Module</code> containing all data to be stored.
     * @return <code>true</code>  if the module could be created and <code>false</code> if there is already an existing
     * module with the same name (name is unique) or <ocde>toCreate==null</ocde>.
     * @throws IOException If the name or the description are too long to store.
     * @throws DataAccessException If the data from toCreate could not be saved because any other error occurred.
     */
    public boolean create(Module toCreate) throws IOException, DataAccessException;

    /**
     * Read all modules.  All containing meta lvas are also returned. The containing meta lvas contain their
     * predecessors and lvas, they contain their lva dates. The predecessors do not store their predecessors or lvas.
     * @return A list filled with the data from the Module or a empty list if there was no match.
     * @throws DataAccessException If the Curriculum data could not be read because any error occured.
     */
    public List<Module> readAll() throws DataAccessException;

    /**
     * Read a module by its id. All containing meta lvas are also returned. The containing meta lvas contain their
     * predecessors and lvas, they contain their lva dates. The predecessors do not store their predecessors or lvas.
     * @param id The id of the module which should be read.
     * @return A <code>Module</code> containing the data.
     * @throws DataAccessException If the module could not be read because any error occured.
     */
    public Module readById(int id) throws DataAccessException;

    /**
     * Read a module by its name. All containing meta lvas are also returned. The containing meta lvas contain their
     * predecessors and lvas, they contain their lva dates. The module of the meta lva is returned without meta lvas and
     * predecessors.
     * @param name The name of the module which should be read (name is unique).
     * @return A <code>Module</code> containing all module data or <code>null</code> if there is no matching module
     * stored or <code>name==null</code>.
     * @throws DataAccessException If the module could not be read because any error occured.
     */
    public Module readByName(String name) throws DataAccessException;

    /**
     * Read all modules from the specified curriculum. All containing meta lvas are also returned. The containing meta
     * lvas contain their predecessors and lvas, they contain their lva dates. The module of the meta lva is returned
     * without meta lvas and predecessors.
     * @param id The id of the curriculum.
     * @return A <code>HashMap</code> containing all modules and a boolean value if the module is obligatory for the
     * curriculum. If there are no curriculum with the specified id or there is no module in this curriculum, a empty
     * HashMap will be returned.
     * @throws org.springframework.dao.DataAccessException
     */
    public HashMap<Module, Boolean> readByCurriculum(int id) throws DataAccessException;

    /**
     * Add the specified module to the specified curriculum.
     * @param moduleId the id of the module.
     * @param curriculumId the id of the curriculum.
     * @param obligatory <code>true</code> if the module is in the specified curriculum obligatory, <code>false</code>
     *                   otherwise.
     * @return <code>true</code> if the tuple could be stored.
     * @throws DataAccessException If the data could not be deleted because any error occured, i. e. if the module or
     * curriculum does not exist.
     */
    public boolean addToCurriculum(int moduleId, int curriculumId, boolean obligatory) throws DataAccessException;

    /**
     * Delete the specified module from the specified curriculum.
     * @param moduleId the id of the module.
     * @param curriculumId the id of the curriculum.
     * @return <code>true</code> if the tuple could be deleted or there was no matching tuple.
     * @throws DataAccessException If the data could not be deleted because any error occured, i. e. if the module or
     * curriculum does not exist.
     */
    public boolean deleteFromCurriculum(int moduleId, int curriculumId) throws DataAccessException;

    /**
     * Update the values of a Module. Every attribute of toUpdate, which is not null, is included to the
     * update-statements. The Module is identified by its id, so this value should not be null.
     * @param toUpdate The <code>Module</code> containing all values which should be updated.
     * @return <code>true</code> if there was a module found and was successfully updated and <code>false</code> if
     * there was no module with the specified id found.
     * @throws java.io.IOException If the data in toUpdate is invalid, f. e. if a String is too long to be stored persistent.
     * @throws org.springframework.dao.DataAccessException If the module data could not be read because any error occurred, especially  if the
     * id is <code>null</code>.
     */
    public boolean update(Module toUpdate) throws IOException, DataAccessException;

    /*
     * Delete the with <code>toDelete</code> identified module.
     * @param id the identifier of the module which should be deleted.
     * @return <code>true</code> if a module with the specified id could be found and was successfully deleted and
     * <code>false</code> if there is no module with the specified id.
     * @throws org.springframework.dao.DataAccessException If the module could not be deleted because any error occured.
     */
    //public boolean delete(int id) throws DataAccessException;
}
