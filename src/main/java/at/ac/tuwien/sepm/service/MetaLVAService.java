package at.ac.tuwien.sepm.service;

import at.ac.tuwien.sepm.entity.MetaLVA;
import at.ac.tuwien.sepm.service.impl.ValidationException;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Lena
 * Date: 01.06.13
 * Time: 09:29
 * To change this template use File | Settings | File Templates.
 */
public interface MetaLVAService {
    public void startMergeSession();

    public boolean stopMergeSession();

    public boolean mergingNecessary();

    public List<MetaLVA> getNewMetaLvasWithMergeConflicts();

    /**
    * Insert persistent a new MetaLVA. If there are problems, f. e. the
    * associated module does not exist, there will be thrown a ServiceException.
    * The predecessors and successors of this meta lva are not stored. If there are lvas stored in <code>toCreate</code>,
    * they will also created by this method. If <code>toCreate.getSemestersOffered()==null</code> it will be stored
    * as <code>Semester.UNKNOWN</code>.
    * @param toCreate Contains the data which should be saved. The value of the id and the predecessors are ignored.
    * @return <code>true</code>  if the lva could be created and <code>false</code> if there is already an existing
    * meta lva with the same lva number (lva number is unique) or <ocde>toCreate==null</ocde>..
    * @throws ServiceException  If the data from toCreate could not be saved because any other error occurred.
    * @throws ValidationException if invalid toCreate
            */
    public boolean create(MetaLVA toCreate) throws ServiceException, ValidationException;

    /**
     * Set the predecessors of an meta lva.
     * @param lvaId The id of the meta lva.
     * @param predecessorId the id of the predecessor of the lva.
     * @return <code>true</code> if the predecessor could be set.
     * @throws ServiceException If the data could not be saved because any other error occurred.
     * @throws ValidationException if invalid IDs
     */
    public boolean setPredecessor(int lvaId, int predecessorId) throws ServiceException, ValidationException;

    /**
     * Unset the predecessors of an meta lva.
     * @param lvaId The id of the meta lva.
     * @param predecessorId the id of the predecessor of the lva.
     * @return <code>true</code> if the predecessor could be unset.
     * @throws ServiceException If the data could not be saved because any other error occurred.
     */
    public boolean unsetPredecessor(int lvaId, int predecessorId) throws ServiceException, ValidationException;

    /**
     * Read a meta lva by its id. There are all lvas and predecessors set.
     * @return A <code>MetaLVA</code> containing all stored data from the meta lva identified by the spevified id.
     * @param id The id of the meta lva which should be read.
     * @throws ServiceException If the meta lva data could not be read because any error occured
     */
    public MetaLVA readById(int id) throws ServiceException, ValidationException;

    /**
     * Read all predecessors of the specified lva. The lvas and predecessors are not returned.
     * @return A <code>List<MetaLVA></code> containing all stored data from the meta lvas identified by the specified
     * id or a empty list if there is no predecessor of the lva.
     * @param lvaId The id of the meta lva which should be read.
     * @throws ServiceException If the meta lva data could not be read because any error occured
     */
    public List<MetaLVA> readAllPredecessors(int lvaId) throws ServiceException, ValidationException;

    /**
     * Read a meta lva by its id. The lvas and predecessors are not returned.
     * @return A <code>MetaLVA</code> containing all stored data from the meta lva identified by the specified id.
     * @param id The id of the meta lva which should be read.
     * @throws ServiceException If the meta lva data could not be read because any error occured
     */
    public MetaLVA readByIdWithoutLvaAndPrecursor(int id) throws ServiceException, ValidationException;

    /**
     * Read a meta lva by its lva number. The lvas and predecessors are also returned.
     * @return A <code>MetaLVA</code> containing all stored data from the meta lva identified by the specified lva number.
     * @param lvaNumber The lva number of the meta lva which should be read.
     * @throws ServiceException If the meta lva data could not be read because any error occured
     * @throws ValidationException if invalid parameters
     */
    public MetaLVA readByLvaNumber(String lvaNumber) throws ServiceException, ValidationException;

    /**
     * Return all meta lvas, which have no completed lva, have no lva in the specified year and semester and where the
     * perhaps existing lva from the specified year and semester is included to the study progress or not.
     * @param year The year.
     * @param semester The semester. Must be <code>Semester.S</code> of <code>Semester.W</code>.
     * @param isInStudyProgress <code>true</code> if the returned list should only contain meta lvas which have actual
     *                          lvas which are included to the study progress and <code>false</code>  otherwise.
     * @return A list containing all matching meta lvas, a emtpy list if there was no match and <code>null</code> if
     * <code>!semester.equals(Semester.S) && !semester.equals(Semester.W)</code>.
     * @throws ServiceException If the data could not be read because any other error occurred.
     * @throws ValidationException if invalid parameters
     */
    public List<MetaLVA> readUncompletedByYearSemesterStudyProgress (int year, Semester semester, boolean isInStudyProgress) throws ServiceException, ValidationException;

    /**
     * Update the meta lvanumber,name, semester, type, priority, ects and module. The meta lva is identified by the id.
     * The predecessors and lvas are not updated, for this there are the methods <code>setPredecessor()</code> in
     * <code>MetaLvaDao</code> and <code>create()</code> in LvaDao. The id and nr are both unique.
     * @param toUpdate The <code>MetaLVA</code> containing all values which should be updated. Every attribute of the
     *                 above-mentioned, which is not <code>null</code>, is updated.
     * @return <code>true</code> if there was a MetaLVA found and was successfully updated and <code>false</code> if
     * there was no meta lva with the specified id or <code>toUpdate==null</code>.
     * @throws ServiceException If the meta lva data could not be updated because any error occurred, especially  if the id is <code>null</code>.
     * @throws ValidationException if invalid parameters
     */
    public boolean update(MetaLVA toUpdate) throws ServiceException, ValidationException;

    /*
     * Delete the with <code>toDelete</code> identified MetaLVA.
     * @param id the identifier of the MetaLVA which should be deleted.
     * @return <code>true</code> if a MetaLVA with the specified id could be found and was successfully deleted and <code>false</code> if there is no MetaLVA with the specified id.
     * @throws org.springframework.dao.ServiceException If the LVA could not be deleted because any error occured.
     */
    //public boolean delete(int id) throws ServiceException;

    /**
     * Read all meta lvas from the same module.
     * @return An <code>List<MetaLVA></code> containing all meta lvas, which are contained in the same module.
     * @param module the id of the module, from which the meta lvas should be read.
     * @throws ServiceException If the meta lva data could not be read because any error occured.
     * @throws ValidationException if invalid parameters
     */
    public List<MetaLVA> readByModule(int module) throws ServiceException, ValidationException;

    /**
     * Validate a MetaLVA.
     * @param  toValidate - metaLva which gets validated
     * @throws ServiceException If toValidate is invalid
     *
     */
    public void validateMetaLVA(MetaLVA toValidate) throws ServiceException;

    /**
     * Validate ID of a MetaLVA
     * @param  id which gets validated
     * @throws ServiceException If id is invalid (<= 0)
     *
     */
    public void validateID(int id) throws ServiceException;

    /**
     * Read all meta lvas.
     * @return An <code>List<MetaLVA></code> containing all meta lvas.
     * @throws ServiceException If the meta lva data could not be read because any error occured.
     * @throws ValidationException if invalid parameters
     */
    public List<MetaLVA> readAll() throws ServiceException, ValidationException;



    void delete(Integer id) throws ServiceException;
}
