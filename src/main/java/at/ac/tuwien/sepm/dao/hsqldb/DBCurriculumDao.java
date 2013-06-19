package at.ac.tuwien.sepm.dao.hsqldb;

import at.ac.tuwien.sepm.dao.CurriculumDao;
import at.ac.tuwien.sepm.entity.Curriculum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Markus MUTH
 */
@Repository
public class DBCurriculumDao extends DBBaseDao implements CurriculumDao {
    private static final int MAX_LENGTH_NAME=200;
    private static final int MAX_LENGTH_DESCRIPTION=500;
    private static final int MAX_LENGTH_NUMBER=30;
    private static final int MAX_LENGTH_TITLE=30;

    @Autowired
    DBModuleDao moduleDao;

    @Override
    public boolean create(Curriculum toCreate) throws IOException, DataAccessException {
        if(toCreate == null) {
            return false;
        } if(toCreate.getName()!=null && toCreate.getName().length()>MAX_LENGTH_NAME) {
            throw new IOException(ExceptionMessages.tooLongName(MAX_LENGTH_NAME));
        } if(toCreate.getDescription()!=null && toCreate.getDescription().length()>MAX_LENGTH_DESCRIPTION) {
            throw new IOException(ExceptionMessages.tooLongDescription(MAX_LENGTH_DESCRIPTION));
        } if(toCreate.getStudyNumber()!=null && toCreate.getStudyNumber().length()>MAX_LENGTH_NUMBER) {
            throw new IOException(ExceptionMessages.tooLongStudyNumber(MAX_LENGTH_NUMBER));
        } if(toCreate.getAcademicTitle()!=null && toCreate.getAcademicTitle().length()>MAX_LENGTH_TITLE) {
            throw new IOException(ExceptionMessages.tooLongTitle(MAX_LENGTH_TITLE));
        }

        String stmt = "INSERT INTO Curriculum " +
                "(id,studyNumber,name,description,academicTitle,ectsChoice,ectsFree,ectsSoftskill) " +
                "VALUES (null,?,?,?,?,?,?,?);";
        jdbcTemplate.update(stmt, toCreate.getStudyNumber(), toCreate.getName(), toCreate.getDescription(),
                toCreate.getAcademicTitle(), toCreate.getEctsChoice(), toCreate.getEctsFree(), toCreate.getEctsSoftskill());

        return true;
    }

    @Override
    public List<Curriculum> readAll() throws DataAccessException {
        if(jdbcTemplate.queryForObject("SELECT COUNT(*) FROM curriculum", RowMappers.getIntegerRowMapper()) == 0){
            return new ArrayList<Curriculum>();
        }

        String stmt="SELECT * FROM curriculum";
        List<Curriculum> result = jdbcTemplate.query(stmt, RowMappers.getCurriculumRowMapper());

        for(int i=0; i<result.size(); i++) {
            result.set(i, readById(result.get(i).getId()));
        }

        return result;
    }

    @Override
    public Curriculum readById(int id) throws DataAccessException {
        if(jdbcTemplate.queryForObject("SELECT COUNT(*) FROM curriculum WHERE id=?", RowMappers.getIntegerRowMapper(), id) == 0){
            return null;
        }

        String stmt="SELECT * FROM curriculum WHERE id=?;";
        Curriculum result = jdbcTemplate.queryForObject(stmt, RowMappers.getCurriculumRowMapper(), id);

        result.setModules(moduleDao.readByCurriculum(result.getId()));

        return result;
    }

    @Override
    public Curriculum readByStudynumber(String studyNumber) throws DataAccessException {
        if(jdbcTemplate.queryForObject("SELECT COUNT(*) FROM curriculum WHERE studynumber=?", RowMappers.getIntegerRowMapper(), studyNumber) == 0){
            return null;
        }

        String stmt="SELECT * FROM curriculum WHERE studynumber=?;";
        Curriculum result = jdbcTemplate.queryForObject(stmt, RowMappers.getCurriculumRowMapper(), studyNumber);

        result.setModules(moduleDao.readByCurriculum(result.getId()));

        return result;
    }

    @Override
    public boolean update(Curriculum toUpdate) throws IOException, DataAccessException {
        if(toUpdate == null) {
            return false;
        } if(toUpdate.getName()!=null && toUpdate.getName().length()>MAX_LENGTH_NAME) {
            throw new IOException(ExceptionMessages.tooLongName(MAX_LENGTH_NAME));
        } if(toUpdate.getDescription()!=null && toUpdate.getDescription().length()>MAX_LENGTH_DESCRIPTION) {
            throw new IOException(ExceptionMessages.tooLongDescription(MAX_LENGTH_DESCRIPTION));
        } if(toUpdate.getStudyNumber()!=null && toUpdate.getStudyNumber().length()>MAX_LENGTH_NUMBER) {
            throw new IOException(ExceptionMessages.tooLongStudyNumber(MAX_LENGTH_NUMBER));
        } if(toUpdate.getAcademicTitle()!=null && toUpdate.getAcademicTitle().length()>MAX_LENGTH_TITLE) {
            throw new IOException(ExceptionMessages.tooLongTitle(MAX_LENGTH_TITLE));
        }

        String stmt = "SELECT COUNT(*) FROM curriculum WHERE id=?";
        if(jdbcTemplate.queryForObject(stmt, RowMappers.getIntegerRowMapper(), toUpdate.getId()) == 0) {
            return false;
        }

        String stmtUpdateStudynumber = "UPDATE curriculum SET studynumber=? WHERE id=?";
        String stmtUpdateName = "UPDATE curriculum SET name=? WHERE id=?";
        String stmtUpdateDescription = "UPDATE curriculum SET description=? WHERE id=?";
        String stmtUpdateAcademicTitle = "UPDATE curriculum SET academictitle=? WHERE id=?";
        String stmtUpdateEctsChoice = "UPDATE curriculum SET ectschoice=? WHERE id=?";
        String stmtUpdateEctsFree = "UPDATE curriculum SET ectsfree=? WHERE id=?";
        String stmtUpdateEctsSoftSkill = "UPDATE curriculum SET ectssoftskill=? WHERE id=?";

        if(toUpdate.getStudyNumber() != null) {
            jdbcTemplate.update(stmtUpdateStudynumber, toUpdate.getStudyNumber(), toUpdate.getId());
        }
        if(toUpdate.getName() != null) {
            jdbcTemplate.update(stmtUpdateName, toUpdate.getName(), toUpdate.getId());
        }
        if(toUpdate.getDescription() != null) {
            jdbcTemplate.update(stmtUpdateDescription, toUpdate.getDescription(), toUpdate.getId());
        }
        if(toUpdate.getAcademicTitle() != null) {
            jdbcTemplate.update(stmtUpdateAcademicTitle, toUpdate.getAcademicTitle(), toUpdate.getId());
        }
        if(toUpdate.getEctsChoice() != null) {
            jdbcTemplate.update(stmtUpdateEctsChoice, toUpdate.getEctsChoice(), toUpdate.getId());
        }
        if(toUpdate.getEctsFree() != null) {
            jdbcTemplate.update(stmtUpdateEctsFree, toUpdate.getEctsFree(), toUpdate.getId());
        }
        if(toUpdate.getEctsSoftskill() != null) {
            jdbcTemplate.update(stmtUpdateEctsSoftSkill, toUpdate.getEctsSoftskill(), toUpdate.getId());
        }

        return true;
    }

    @Override
    public boolean delete(int id) throws DataAccessException {
        if(jdbcTemplate.queryForObject("SELECT COUNT(*) FROM curriculum WHERE id=?", RowMappers.getIntegerRowMapper(), id) == 0){
            return false;
        }

        String stmtInCurriculum = "DELETE FROM incurriculum WHERE curriculum=?";
        jdbcTemplate.update(stmtInCurriculum, id);
        String stmtCurriculum="DELETE FROM curriculum WHERE id=?";
        jdbcTemplate.update(stmtCurriculum, id);

        return true;
    }
}