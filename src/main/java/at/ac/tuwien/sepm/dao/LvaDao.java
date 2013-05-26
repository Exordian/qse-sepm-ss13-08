package at.ac.tuwien.sepm.dao;

import at.ac.tuwien.sepm.entity.LVA;
import org.springframework.dao.DataAccessException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public interface LvaDao {

    /**
     * Create a new lva. Only meta lva, year, semester, description, grade and if the lva is in study progress or not
     * are considered, the id is not considered.
     * @param toCreate The lva to be created.
     * @return <code>false</code> if <code>toCreate==null</code> and <code>true</code> if the data could be stored.
     * @throws IOException If the description is too lon to store.
     * @throws DataAccessException If the lva could not be updated because any error occurred, especially if
     * <code>getMetaLVA()==null</code> or <code>getSemester()==null</code> or <code>getYear()==null</code>.
     * @throws NullPointerException If <code>toCreate.getSemester()==null</code> or <code>toCreate.getMetaLVA()==null</code>.
     */
    public boolean create(LVA toCreate) throws IOException, DataAccessException;

    /**
     * Read a lva by its id. The the meta lva of the returned lva is except for the List <code>ArrayList<LVA> lvas</code>
     * completely returned.
     * @param id The id of the lva to be read.
     * @return A completely filled LVA-object or <code>null</code> if there is no lva with the specified id.
     * @throws DataAccessException if the date data could not be read because any error occurred.
     */
    public LVA readById(int id) throws DataAccessException;

    /**
     * Read a lva by its id, the lva dates are not read. The MetaLVA is except for the List <code>ArrayList<LVA>lvas</code>
     * completely returned.
     * @param id The id of the lva to be read.
     * @return A nearly completely filled LVA-object, only the dates of this lva are not returned.
     * @throws DataAccessException if the date data could not be read because any error occurred.
     */
    public LVA readByIdWithoutLvaDates(int id) throws DataAccessException;

    /**
     * Read all lvas in the specified year and semester. The order of the returned dates is chronological, beginning at
     * the oldest one.
     * @param year The year.
     * @param isWinterSemester <code>true</code> if the lva should be in the winter semester, <code>false</code> otherwise.
     * @return A list containing all lvas in the specified semester.
     * @throws DataAccessException if the date data could not be read because any error occurred.
     */
    public List<LVA> readByYearAndSemester(int year, boolean isWinterSemester) throws DataAccessException;

    /*
     * Read all lvas which are included to the study progress.
     * @return
     * @throws DataAccessException
     */
    //public ArrayList<LVA> readIncludedToStudyprogress() throws DataAccessException;

    /**
     * Update the meta lva, year, semester, description and grade, set or unset if the lva is in study progress. The lva
     * is identified by the id. The semester must be either Semester.S or Semester.W. Year, grade and
     * study progress are always updated.
     * @param toUpdate The lva containing all data to be updated.
     * @return <code>true</code> if the lva could be successful updated and <code>false</code> if <code>tuUpdate==null</code>
     * or there is no lva with the specified id.
     * @throws IOException If the description is too lon to store.
     * @throws DataAccessException If the lva could not be updated because any error occurred.
     */
    public boolean update(LVA toUpdate) throws IOException, DataAccessException;
}