package at.ac.tuwien.sepm.dao;

import at.ac.tuwien.sepm.entity.MetaLVA;
import org.springframework.dao.DataAccessException;

import java.io.IOException;
import java.util.ArrayList;
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
     * they will not be created by this method.
     * @param toCreate Contains the data which should be saved. The value of the id and the predecessors are ignored.
     * @return <code>true</code>  if the lva could be created and <code>false</code> if there is already an existing
     * meta lva with the same lva number.
     * @throws IOException  If the name of the lva number of the meta lva are too long to store.
     * @throws IOException  If the name of the lva number of the meta lva are too long to store.
     * @throws DataAccessException  If the data from toCreate could not be saved because any other error occurred.
     */
    public boolean create(MetaLVA toCreate) throws IOException, DataAccessException;

    /**
     * Set the predecessors of an meta lva.
     * @param lvaId The id of the meta lva.
     * @param predecessorId the id of the predecessor of the lva.
     * @return <code>true</code> if the predecessor could be set, <code>false</code> if there is already such a
     * metalva-predecessor-tuple.
     * @throws IOException
     * @throws DataAccessException
     */
    public boolean setPredecessor(int lvaId, int predecessorId) throws DataAccessException;

    /**
     * Read a meta lva by its id. There are all lvas and predecessors set.
     * @return A <code>MetaLVA</code> containing all stored data from the meta lva identified by the spevified id.
     * @param id The id of the meta lva which should be read.
     * @throws org.springframework.dao.DataAccessException If the meta lva data could not be read because any error occured
     */
    public MetaLVA readById(int id) throws DataAccessException;

    /**
     * Read a meta lva by its id. The lvas and predecessors are also returned.
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
     * Update the meta lvanumber,name, semester, type, priority, ects and module. The meta lva is identified by the id.
     * The predecessors and lvas are not updated, for this there are the methods <code>setPredecessor()</code> in
     * <code>MetaLvaDao</code> and <code>create()</code> in LvaDao.
     * @param toUpdate The <code>MetaLVA</code> containing all values which should be updated. Every attribute of the
     *                 above-mentioned, which is not <code>null</code>, is updated.
     * @return <code>true</code> if there was a MetaLVA found and was successfully updated and <code>false</code> if
     * there was no meta lva with the specified id or <code>toUpdate==null</code>.
     * @throws IOException  If the name of the lva number of the meta lva are too long to store.
     * @throws DataAccessException If the meta lva data could not be updated because any error occurred, especially  if the id is <code>null</code>.
     * @throws NullPointerException If ...
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
}