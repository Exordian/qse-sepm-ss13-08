package at.ac.tuwien.sepm.dao.hsqldb;

import at.ac.tuwien.sepm.dao.ModuleDao;
import at.ac.tuwien.sepm.entity.InCurriculum;
import at.ac.tuwien.sepm.entity.MetaLVA;
import at.ac.tuwien.sepm.entity.Module;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * @author Markus MUTH
 */

@Repository
public class DBModuleDao extends DBBaseDao implements ModuleDao {
    private static final int MAX_LENGTH_NAME=200;
    private static final int MAX_LENGTH_DESCRIPTION=5000;

    @Autowired
    DBMetaLvaDao metaLvaDao;

    @Override
    public boolean create(Module toCreate) throws IOException, DataAccessException {
        if(toCreate == null) {
            return false;
        } if(toCreate.getName()!=null && toCreate.getName().length()>MAX_LENGTH_NAME) {
            throw new IOException(ExceptionMessages.tooLongName(MAX_LENGTH_NAME));
        } if(toCreate.getDescription()!=null && toCreate.getDescription().length()>MAX_LENGTH_DESCRIPTION) {
            throw new IOException(ExceptionMessages.tooLongDescription(MAX_LENGTH_DESCRIPTION));
        }

        String stmt = "INSERT INTO Module (id,name,description,completeAll) VALUES (null,?,?,?)";
        jdbcTemplate.update(stmt, toCreate.getName(), toCreate.getDescription(), toCreate.getCompleteall());

        return true;
    }

    @Override
    public Module readById(int id) throws DataAccessException {
        if(jdbcTemplate.queryForObject("SELECT COUNT(*) FROM module WHERE id=?", RowMappers.getIntegerRowMapper(), id) == 0){
            return null;
        }

        String stmt="SELECT * FROM module WHERE id=?;";
        Module result = jdbcTemplate.queryForObject(stmt, RowMappers.getModuleRowMapper(), id);

        result.setMetaLvas(metaLvaDao.readByModule(result.getId()));

        return result;
    }

    @Override
    public Module readByName(String name) throws DataAccessException {
        if(jdbcTemplate.queryForObject("SELECT COUNT(*) FROM module WHERE name=?", RowMappers.getIntegerRowMapper(), name) == 0){
            return null;
        }

        String stmt="SELECT * FROM module WHERE name=?";
        Module result = jdbcTemplate.queryForObject(stmt, RowMappers.getModuleRowMapper(), name);

        result.setMetaLvas(metaLvaDao.readByModule(result.getId()));

        return result;
    }

    @Override
    public HashMap<Module, Boolean> readByCurriculum(int id) throws DataAccessException {
        //SELECT * FROM module  WHERE id IN (SELECT module FROM incurriculum WHERE curriculum =0)
        if(jdbcTemplate.queryForObject("SELECT COUNT(*) FROM incurriculum WHERE curriculum=?", RowMappers.getIntegerRowMapper(), id) == 0){
            return new HashMap<Module, Boolean>();
        }

        String stmtModules="SELECT * FROM module  WHERE id IN (SELECT module FROM incurriculum WHERE curriculum=?)";
        List<Module> resultModules = jdbcTemplate.query(stmtModules, RowMappers.getModuleRowMapper(), id);
        String stmtObligatory = "SELECT * FROM incurriculum WHERE curriculum=?";
        List<InCurriculum> resultObligatory = jdbcTemplate.query(stmtObligatory, RowMappers.getInCurriculumRowMapper(), id);

        HashMap<Module, Boolean> result = new HashMap<Module, Boolean>();
        for(int i=0; i<resultModules.size(); i++){
            Module m = resultModules.get(i);
            m = readById(m.getId());
            for(InCurriculum j : resultObligatory) {
                if(j.getModule().equals(m.getId())) {
                    result.put(m, j.getOlbigatory());
                }
            }
        }

        return result;
    }

    @Override
    public boolean addToCurriculum(int moduleId, int curriculumId, boolean obligatory) throws DataAccessException {
        String stmt = "INSERT INTO incurriculum (curriculum,module,obligatory) VALUES (?,?,?)";
        jdbcTemplate.update(stmt, curriculumId, moduleId, obligatory);
        return true;
    }

    @Override
    public boolean deleteFromCurriculum(int moduleId, int curriculumId) throws DataAccessException {
        String stmt = "DELETE FROM incurriculum WHERE curriculum=? AND module=?";
        jdbcTemplate.update(stmt, curriculumId, moduleId);
        return true;
    }

    @Override
    public boolean update(Module toUpdate) throws IOException, DataAccessException {
        if(toUpdate == null) {
            return false;
        } if(toUpdate.getName()!=null && toUpdate.getName().length()>MAX_LENGTH_NAME) {
            throw new IOException(ExceptionMessages.tooLongLvaNumber(MAX_LENGTH_NAME));
        } if(toUpdate.getDescription()!=null && toUpdate.getDescription().length()>MAX_LENGTH_DESCRIPTION) {
            throw new IOException(ExceptionMessages.tooLongName(MAX_LENGTH_DESCRIPTION));
        }

        String stmt = "SELECT COUNT(*) FROM module WHERE id=?";
        if(jdbcTemplate.queryForObject(stmt, RowMappers.getIntegerRowMapper(), toUpdate.getId()) == 0) {
            return false;
        }

        String stmtUpdateName = "UPDATE module SET name=? WHERE id=?";
        String stmtUpdateDescription = "UPDATE module SET description=? WHERE id=?";
        String stmtUpdateCompleteAll = "UPDATE module SET completeall=? WHERE id=?";

        if(toUpdate.getName() != null) {
            jdbcTemplate.update(stmtUpdateName, toUpdate.getName(), toUpdate.getId());
        }
        if(toUpdate.getDescription() != null) {
            jdbcTemplate.update(stmtUpdateDescription, toUpdate.getDescription(), toUpdate.getId());
        }
        if(toUpdate.getCompleteall() != null) {
            jdbcTemplate.update(stmtUpdateCompleteAll, toUpdate.getCompleteall(), toUpdate.getId());
        }

        return true;
    }
}