package at.ac.tuwien.sepm.dao.hsqldb;

import at.ac.tuwien.sepm.dao.DeleteWholeDBDao;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Created with IntelliJ IDEA.
 * User: Flo
 * Date: 02.07.13
 * Time: 19:07
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class DBDeleteWholeDBDao extends DBBaseDao implements DeleteWholeDBDao {
    private Logger logger = LogManager.getLogger(this.getClass().getSimpleName());

    @Override
    public void deleteAll() throws DataAccessException {
        String stmnt="";

        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(new File(ClassLoader.getSystemResource("create.sql").toURI()));

            try {
                stmnt = IOUtils.toString(inputStream);
            } catch (IOException e) {
                logger.error(e);
            } finally {
                inputStream.close();
            }

        } catch (IOException e) {
            logger.error(e);
        } catch (URISyntaxException e) {
            logger.error(e);
        }

        jdbcTemplate.execute(stmnt);
    }
}
