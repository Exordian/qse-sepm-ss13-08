package at.ac.tuwien.sepm.service;

import at.ac.tuwien.sepm.entity.LVA;
import at.ac.tuwien.sepm.service.impl.ValidationException;
import org.springframework.dao.DataAccessException;

import java.io.IOException;
import java.util.List;

/**
 * @author Lena Lenz
 */
public interface LVAService {
    /**
     * Create a new lva. Only meta lva, year, semester, description, grade and if the lva is in study progress or not
     * are considered, the id is not considered.
     * @param toCreate The lva to be created.
     * @return <code>false</code> if <code>toCreate==null</code> and <code>true</code> if the data could be stored.
     * @throws ServiceException If the lva could not be updated because any error occurred, especially if
     * <code>getMetaLVA()==null</code> or <code>getSemester()==null</code> or <code>getYear()==null</code>.
     * @throws ValidationException If invalid toCreate
     */
    public boolean create(LVA toCreate) throws ServiceException, ValidationException;

    /**
     * Read a lva by its id. The the meta lva of the returned lva is except for the List <code>lva</code> and <code>precurso</code>
     * completely returned.
     * completely returned.
     * @param id The id of the lva to be read.
     * @return A completely filled LVA-object or <code>null</code> if there is no lva with the specified id.
     * @throws ServiceException if the date data could not be read because any error occurred.
     */
    public LVA readById(int id) throws ServiceException, ValidationException;

    /**
     * Read a lvas with the specified meta lva by its id. The the meta lva of the returned lva is except for the List <code>lva</code> and <code>precurso</code>
     * completely returned.
     * @param metaLvaId The id of the lva to be read.
     * @return A list containing the completely filled LVA-objects or an empty list if there is no lva with the
     * specified meta lva-
     * @throws ServiceException if the date data could not be read because any error occurred.
     */
    public List<LVA> readByMetaLva(int metaLvaId) throws ServiceException, ValidationException;

    /**
     * Return all lvas where no lva grouped by the meta lva exists, which is already completed (1<=grade<=4), the lva is
     * in the specified year and semester and the lva is included to the study progress or not.
     * The returned lvas contain all related lva dates.
     * @param year The year
     * @param semester The semester. <code>semester</code> must be <code>Semester.S</code> or <code>Semester.W</code>
     * @param isInStudyProgress <code>true</code> if the returned list should contain meta lvas which are included to
     *                          the study progress, <code>false</code> otherwise.
     * @return A list containing all matching lvas or an empty list if there was no match.
     * @throws ServiceException If the data could not be read because any error occurred.
     * @throws ValidationException If <code>semester==null</code>.
     */
    public List<LVA> readUncompletedByYearSemesterStudyProgress(int year, Semester semester, boolean isInStudyProgress) throws ServiceException, ValidationException;

    /**
     * Read a lva by its id, the lva dates are not read. The MetaLVA is except for the List <code>ArrayList<LVA>lvas</code>
     * completely returned.
     * @param id The id of the lva to be read.
     * @return A nearly completely filled LVA-object, only the dates of this lva are not returned.
     * @throws ServiceException if the date data could not be read because any error occurred.
     */
    public LVA readByIdWithoutLvaDates(int id) throws ServiceException, ValidationException;

    /**
     * Read all lvas in the specified year and semester. The order of the returned dates is chronological, beginning at
     * the oldest one.
     * @param year The year.
     * @param isWinterSemester <code>true</code> if the lva should be in the winter semester, <code>false</code> otherwise.
     * @return A list containing all lvas in the specified semester.
     * @throws ServiceException if the date data could not be read because any error occurred.
     */
    public List<LVA> readByYearAndSemester(int year, boolean isWinterSemester) throws ServiceException, ValidationException;

    /**
     * Update the meta lva, year, semester, description and grade, set or unset if the lva is in study progress. The lva
     * is identified by the id. The semester must be either Semester.S or Semester.W. Year, grade and
     * study progress are always updated.
     * @param toUpdate The lva containing all data to be updated.
     * @return <code>true</code> if the lva could be successful updated and <code>false</code> if <code>tuUpdate==null</code>
     * or there is no lva with the specified id.
     * @throws ValidationException If invalid toUpdate
     * @throws ServiceException If the lva could not be updated because any error occurred.
     */
    public boolean update(LVA toUpdate) throws ServiceException, ValidationException;

    /**
     * Validate a LVA.
     * @param  toValidate - lva which gets validated
     * @throws ServiceException If lva is invalid
     *
     */
    public void validateLVA(LVA toValidate) throws ServiceException;

    /**
     * Validate ID of a LVA
     * @param  id which gets validated
     * @throws ServiceException If id is invalid (<= 0)
     *
     */
    public void validateID(int id) throws ServiceException;
}
