package at.ac.tuwien.sepm.service;

import at.ac.tuwien.sepm.entity.LvaDate;
import at.ac.tuwien.sepm.entity.LvaDateType;
import at.ac.tuwien.sepm.service.impl.ValidationException;

import java.io.IOException;
import java.util.List;

/**
 * @author Lena Lenz
 */
public interface LvaDateService {
    /**
     * Store a new lva date. If <code>getResult()==null</code>, it will be stored with 0.
     * @param toCreate the <code>LvaDate</code> containing the data to be stored.
     * @return <code>false</code> if <code>toCreate==null</code> and <code>true</code> if the data could be stored.
     * @throws ServiceException If the date data could not be stored because any error
     * occurred.
     * @throws ValidationException  if LvaDate toCreate has invalid parameters
     * @throws NullPointerException If <code>toCreate.getStart()==null</code> or  <code>toCreate.getStop()==null</code>.
     */
    public boolean create(LvaDate toCreate) throws ServiceException, ValidationException;

    /**
     * Read the lva date data which is identified by the specified id.
     * @param id the id of the lva date which should be read.
     * @return a <code>DateEntity</code>  containing all data of the lva date.
     * @throws ServiceException if the lva date data could not be read because any error occurred.
     * @throws ValidationException  if LvaDate has invalid parameters
     */
    public LvaDate readById(int id) throws ServiceException, ValidationException;

    /**
     * Read all lva dates from the specifed lva. The order of the returned dates is chronological, beginning at the
     * oldest one.
     * @param lvaId the id of the lva.
     * @return A <code>List<LvaDate></code> containing all stored data or a empty <code>List</code> if there was no lva
     * date from the specified lva found.
     * @throws ServiceException If the lva date data could not be read because any error occurred.
     * @throws ValidationException  if lvaId is invalid
     */
    public List<LvaDate> readByLva(int lvaId) throws ServiceException, ValidationException;

    /**
     * Read all lva dates from the specified type and lva. The order of the returned dates is chronological, beginning
     * at the oldest one.
     * @param lvaId the id of the lva.
     * @param type the type of the lva.
     * @return A <code>LvaDate</code> containing all stored data or <code>null</code> if there was no lva date from the
     * specified lva found.
     * @throws ServiceException, ValidationException If the lva date data could not be read because any error occurred.
     * @throws ValidationException if LvaDate has invalid parameters or invalid lvaId
     */
    public List<LvaDate> readByLvaAndType(int lvaId, LvaDateType type) throws ServiceException, ValidationException;

    /**
     * Update a lva date.
     * @param toUpdate A <code>LvaDate</code> containing all data to be updated. All attributes unequal <code>null</code>
     *                 are included to the update statements. The lva date to be updated is identified by the id,
     * @return <code>true</code> if there was a lva date found and was successfully updated and <code>false</code> if
     * <code>toUpdate.getId()==null</code> or there was no lva date with the specified id found.
     * @throws ServiceException If the date data could not be updated
     * because any error occurred.
     * @throws ValidationException if LvaDate has invalid parameters
     */
    public boolean update(LvaDate toUpdate) throws ServiceException, ValidationException;

    /**
     * Delete a lva date.
     * @param id the id of the lva date which should be deleted.
     * @return <code>true</code> if a lva date with the specified id could be found and was successfully deleted and <code>false</code> if there is no date with the specified id.
     * @throws ServiceException If the lva date data could not be deleted because any error occurred.
     * @throws ValidationException if id is invalid
     */
    public boolean delete(int id) throws ServiceException, ValidationException;

    /**
     * Validate a LvaDate.
     * @param  toValidate - lvaDate which gets validated
     * @throws ServiceException If lvaDate is invalid
     *
     */
    public void validateLvaDate(LvaDate toValidate) throws ServiceException;

    /**
     * Validate ID of a lvaDate
     * @param  id which gets validated
     * @throws ServiceException If id is invalid (<= 0)
     *
     */
    public void validateID(int id) throws ServiceException;
}
