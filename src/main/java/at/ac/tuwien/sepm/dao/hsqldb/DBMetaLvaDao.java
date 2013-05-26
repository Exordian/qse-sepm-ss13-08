package at.ac.tuwien.sepm.dao.hsqldb;

import at.ac.tuwien.sepm.dao.MetaLvaDao;
import at.ac.tuwien.sepm.entity.MetaLVA;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;

/**
 * Author: MUTH Markus
 * Date: 5/26/13
 * Time: 7:17 AM
 * Description of class "DBMetaLvaDao":
 */
@Repository
public class DBMetaLvaDao extends DBBaseDao implements MetaLvaDao {
    @Override
    public boolean create(MetaLVA toCreate) throws IOException, DataAccessException {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean setPredecessor(int lvaId, int predecessorId) throws DataAccessException {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public MetaLVA readById(int id) throws DataAccessException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public MetaLVA readByIdWithoutLvaAndPrecursor(int id) throws DataAccessException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public MetaLVA readByLvaNumber(String lvaNumber) throws DataAccessException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean update(MetaLVA toUpdate) throws IOException, DataAccessException {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<MetaLVA> readByModule(int module) throws DataAccessException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
