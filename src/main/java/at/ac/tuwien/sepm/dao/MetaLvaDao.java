package at.ac.tuwien.sepm.dao;

import at.ac.tuwien.sepm.entity.MetaLVA;
import at.ac.tuwien.sepm.service.Semester;
import org.springframework.dao.DataAccessException;

import java.io.IOException;
import java.util.List;

/**
 * Author: MUTH Markus
 * Date: 5/18/13
 * Time: 9:06 PM
 * Description of class "MetaLvaDao":
 */
public interface MetaLvaDao {

    /**
     * Insert persistent a new MetaLVA. If there are problems, f. e. the
     * associated module does not exist, there will be thrown a DataAccessException.
     * The predecessors and successors of this meta lva are not stored. If there are lvas stored int <code>toCreate</code>,
     * they will not be created by this method. If <code>toCreate.getSemestersOffered()==null</code> it will be stored
     * as <code>Semester.UNKNOWN</code>.
     * The id of the object <code>toCreate</code> will be set.
     * @param toCreate Contains the data which should be saved. The value of the id and the predecessors are ignored.
     * @return <code>true</code>  if the lva could be created and <code>false</code> if there is already an existing
     * meta lva with the same lva number (lva number is unique) or <ocde>toCreate==null</ocde>.
     * The id of the object <code>toCreate</code> will be set.
     * @throws IOException  If the name of the lva number of the meta lva are too long to store.
     * @throws DataAccessException  If the data from toCreate could not be saved because any other error occurred.
     * @throws NullPointerException if <code>toCreate.getType()==null</code>.
     */
    public boolean create(MetaLVA toCreate) throws IOException, DataAccessException;

    /**
     * Set the predecessors of an meta lva.
     * @param lvaId The id of the meta lva.
     * @param predecessorId the id of the predecessor of the lva.
     * @return <code>true</code> if the predecessor could be set.
     * @throws DataAccessException If the data could not be saved because any other error occurred.
     */
    public boolean setPredecessor(int lvaId, int predecessorId) throws DataAccessException;

    /**
     * Unset the predecessors of an meta lva.
     * @param lvaId The id of the meta lva.
     * @param predecessorId the id of the predecessor of the lva.
     * @return <code>true</code> if the predecessor could be unset.
     * @throws DataAccessException If the data could not be saved because any other error occurred.
     */
    public boolean unsetPredecessor(int lvaId, int predecessorId) throws DataAccessException;

    /**
     * Read a meta lva by its id. There are all lvas and predecessors set.
     * @return A <code>MetaLVA</code> containing all stored data from the meta lva identified by the spevified id.
     * @param id The id of the meta lva which should be read.
     * @throws org.springframework.dao.DataAccessException If the meta lva data could not be read because any error occured
     */
    public MetaLVA readById(int id) throws DataAccessException;

    /**
     * Read all predecessors of the specified lva. The lvas and predecessors are not returned.
     * @return A <code>List<MetaLVA></code> containing all stored data from the meta lvas identified by the specified
     * id or a empty list if there is no predecessor of the lva.
     * @param lvaId The id of the meta lva which should be read.
     * @throws DataAccessException If the meta lva data could not be read because any error occured
     */
    public List<MetaLVA> readAllPredecessors(int lvaId) throws DataAccessException;

    /**
     * Read a meta lva by its id. The lvas and predecessors are not returned.
     * @return A <code>MetaLVA</code> containing all stored data from the meta lva identified by the specified id.
     * @param id The id of the meta lva which should be read.
     * @throws org.springframework.dao.DataAccessException If the meta lva data could not be read because any error occured
     */
    public MetaLVA readByIdWithoutLvaAndPrecursor(int id) throws DataAccessException;

    /**
     * Read a meta lva by its lva number. The lvas and predecessors are also returned.
     * @return A <code>MetaLVA</code> containing all stored data from the meta lva identified by the specified lva number.
     * @param lvaNumber The lva number of the meta lva which should be read.
     * @throws DataAccessException If the meta lva data could not be read because any error occured
     */
    public MetaLVA readByLvaNumber(String lvaNumber) throws DataAccessException;

    /**
     * Return all MetaLVAs, which
     * have no completed LVA,
     * have no LVA in the specified year and semester and
     * where the perhaps existing lva from the specified year and semester is included in the study progress or not.
     * @param year The year.
     * @param semester The semester. Must be <code>Semester.S</code> of <code>Semester.W</code>.
     * @param isInStudyProgress <code>true</code> if the returned list should only contain meta lvas which have actual
     *                          lvas which are included to the study progress and <code>false</code>  otherwise.
     * @return A list containing all matching meta lvas, a emtpy list if there was no match and <code>null</code> if
     * <code>!semester.equals(Semester.S) && !semester.equals(Semester.W)</code>.
     * @throws DataAccessException If the data could not be read because any other error occurred.
     * @throws NullPointerException If <code>semester==null</code>.
     */
    public List<MetaLVA> readUncompletedByYearSemesterStudyProgress (int year, Semester semester, boolean isInStudyProgress) throws DataAccessException;
    /**
     * Return all MetaLVAs, which have a LVA in the specified year and semester and where the
     * lva from the specified year and semester is included in the study progress or not.
     * @param year The year.
     * @param semester The semester. Must be <code>Semester.S</code> of <code>Semester.W</code>.
     * @param isInStudyProgress <code>true</code> if the returned list should only contain meta lvas which have actual
     *                          lvas which are included to the study progress and <code>false</code>  otherwise.
     * @return A list containing all matching meta lvas, a emtpy list if there was no match and <code>null</code> if
     * <code>!semester.equals(Semester.S) && !semester.equals(Semester.W)</code>.
     * @throws DataAccessException If the data could not be read because any other error occurred.
     * @throws NullPointerException If <code>semester==null</code>.
     */
    public List<MetaLVA> readByYearSemesterStudyProgress (int year, Semester semester, boolean isInStudyProgress) throws DataAccessException;

    /*
     * Read all meta lvas which have a lva in the specified year and semester and which is either included to study
     * progress or not. The meta lva from the returned lvas from the returned meta lvas does only contain their id, but
     * does contain all lva dates.
     * @param year The year.
     * @param semester The semester.
     * @param inStudyProgress <code>true</code> if the perhaps containing lva should be included to the study progress
     *                        and <code>false</code> otherwise.
     * @return A list containing all meta lvas, which have an lva in the specified year and semester and are included to
     * the study progress or not, a empty list if there was no matching meta lva found and null if
     * <code>!semester.equals(Semester.S) && !semester.equals(Semester.W)</code>.
     * @throws DataAccessException  the data could not be read because any other error occurred.
     * @throws NullPointerException If <code>semester==null</code>.
     */
    //public List<MetaLVA> readNotCompletedByYearSemesterStudProgress(int year, Semester semester, boolean inStudyProgress) throws DataAccessException, NullPointerException;

    /**
     * Update the meta lvanumber,name, semester, type, priority, ects and module. The meta lva is identified by the id.
     * The predecessors and lvas are not updated, for this there are the methods <code>setPredecessor()</code> in
     * <code>MetaLvaDao</code> and <code>create()</code> in LvaDao. The id and nr are both unique.
     * @param toUpdate The <code>MetaLVA</code> containing all values which should be updated. Every attribute of the
     *                 above-mentioned, which is not <code>null</code>, is updated.
     * @return <code>true</code> if there was a MetaLVA found and was successfully updated and <code>false</code> if
     * there was no meta lva with the specified id or <code>toUpdate==null</code>.
     * @throws IOException  If the name of the lva number of the meta lva are too long to store.
     * @throws DataAccessException If the meta lva data could not be updated because any error occurred, especially  if the id is <code>null</code>.
     * @throws NullPointerException If <code>toUpdate.getId()==null</code>.
     */
    public boolean update(MetaLVA toUpdate) throws IOException, DataAccessException;

    /*
     * Delete the with <code>toDelete</code> identified MetaLVA.
     * @param id the identifier of the MetaLVA which should be deleted.
     * @return <code>true</code> if a MetaLVA with the specified id could be found and was successfully deleted and <code>false</code> if there is no MetaLVA with the specified id.
     * @throws org.springframework.dao.DataAccessException If the LVA could not be deleted because any error occured.
     */
    //public boolean delete(int id) throws DataAccessException;

    /**
     * Read all meta lvas from the same module.
     * @return An <code>List<MetaLVA></code> containing all meta lvas, which are contained in the same module.
     * @param module the id of the module, from which the meta lvas should be read.
     * @throws DataAccessException If the meta lva data could not be read because any error occured.
     */
    public List<MetaLVA> readByModule(int module) throws DataAccessException;

    /**
     * Read all metalvas
     * @return A list containing all metalvas.
     * @throws DataAccessException if the date data could not be read because any error occurred.
     */
    public List<MetaLVA> readAll() throws DataAccessException;
}