package at.ac.tuwien.sepm.service.impl;

import at.ac.tuwien.sepm.dao.MetaLvaDao;
import at.ac.tuwien.sepm.entity.MetaLVA;
import at.ac.tuwien.sepm.service.MetaLVAService;
import at.ac.tuwien.sepm.service.Semester;
import at.ac.tuwien.sepm.service.ServiceException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Lena
 * Date: 01.06.13
 * Time: 09:37
 * To change this template use File | Settings | File Templates.
 */
@Service
public class MetaLVAServiceImpl implements MetaLVAService {

    Logger logger = LogManager.getLogger(this.getClass().getSimpleName());

    @Autowired
    MetaLvaDao metaLvaDao;

    @Override
    public boolean create(MetaLVA toCreate) throws ServiceException, ValidationException {
        try {
            this.validateMetaLVA(toCreate);
            boolean created = metaLvaDao.create(toCreate);
            return created;
        } catch(ServiceException e) {
            logger.error("Exception: "+ e.getMessage());
            throw new ValidationException("Exception: "+ e.getMessage());
        } catch(DataAccessException e) {
            logger.error("Exception: "+ e.getMessage());
            throw new ServiceException("Exception: "+ e.getMessage());
        } catch(IOException e) {
            logger.error("Exception: "+ e.getMessage());
            throw new ServiceException("Exception: "+ e.getMessage());
        }
    }

    @Override
    public boolean setPredecessor(int lvaId, int predecessorId) throws ServiceException, ValidationException {
        try {
            this.validateID(lvaId);
            this.validateID(predecessorId);
            boolean set_predecessor = metaLvaDao.setPredecessor(lvaId, predecessorId);
            return set_predecessor;
        } catch(ServiceException e) {
            logger.error("Exception: "+ e.getMessage());
            throw new ValidationException("Exception: "+ e.getMessage());
        } catch(DataAccessException e) {
            logger.error("Exception: "+ e.getMessage());
            throw new ServiceException("Exception: "+ e.getMessage());
        }
    }

    @Override
    public boolean unsetPredecessor(int lvaId, int predecessorId) throws ServiceException, ValidationException {
        try {
            this.validateID(lvaId);
            this.validateID(predecessorId);
            boolean unset_predecessor = metaLvaDao.unsetPredecessor(lvaId, predecessorId);
            return unset_predecessor;
        } catch(ServiceException e) {
            logger.error("Exception: "+ e.getMessage());
            throw new ValidationException("Exception: "+ e.getMessage());
        } catch(DataAccessException e) {
            logger.error("Exception: "+ e.getMessage());
            throw new ServiceException("Exception: "+ e.getMessage());
        }
    }

    @Override
    public MetaLVA readById(int id) throws ServiceException, ValidationException {
        try {
            this.validateID(id);
            MetaLVA metaLVA = metaLvaDao.readById(id);
            return metaLVA;
        } catch(ServiceException e) {
            logger.error("Exception: "+ e.getMessage());
            throw new ValidationException("Exception: "+ e.getMessage());
        } catch(DataAccessException e) {
            logger.error("Exception: "+ e.getMessage());
            throw new ServiceException("Exception: "+ e.getMessage());
        }
    }

    @Override
    public List<MetaLVA> readAllPredecessors(int lvaId) throws ServiceException, ValidationException {
        try {
            this.validateID(lvaId);
            List<MetaLVA> metaLVAList = metaLvaDao.readAllPredecessors(lvaId);
            return metaLVAList;
        } catch(ServiceException e) {
            logger.error("Exception: "+ e.getMessage());
            throw new ValidationException("Exception: "+ e.getMessage());
        } catch(DataAccessException e) {
            logger.error("Exception: "+ e.getMessage());
            throw new ServiceException("Exception: "+ e.getMessage());
        }
    }

    @Override
    public MetaLVA readByIdWithoutLvaAndPrecursor(int id) throws ServiceException, ValidationException {
        try {
            this.validateID(id);
            MetaLVA metaLVA = metaLvaDao.readByIdWithoutLvaAndPrecursor(id);
            return metaLVA;
        } catch(ServiceException e) {
            logger.error("Exception: "+ e.getMessage());
            throw new ValidationException("Exception: "+ e.getMessage());
        } catch(DataAccessException e) {
            logger.error("Exception: "+ e.getMessage());
            throw new ServiceException("Exception: "+ e.getMessage());
        }
    }

    @Override
    public MetaLVA readByLvaNumber(String lvaNumber) throws ServiceException, ValidationException {
        if(lvaNumber == null) {
            logger.error("invalid parameter!(lvaNumber)");
            throw new ValidationException("invalid parameter!(lvaNumber)");
        }
        try {
            MetaLVA metaLva = metaLvaDao.readByLvaNumber(lvaNumber);
            return metaLva;
        } catch(DataAccessException e) {
            logger.error("Exception: "+ e.getMessage());
            throw new ServiceException("Exception: "+ e.getMessage());
        }
    }

    @Override
    public List<MetaLVA> readUncompletedByYearSemesterStudyProgress(int year, Semester semester, boolean isInStudyProgress) throws ServiceException, ValidationException {
        if(year < 0 || semester == null) {
            logger.error("invalid parameters!");
            throw new ValidationException("invalid parameters!");
        }
        try {
            List<MetaLVA> metaLVAList = metaLvaDao.readUncompletedByYearSemesterStudyProgress(year, semester, isInStudyProgress);
            return metaLVAList;
        } catch(DataAccessException e) {
            logger.error("Exception: "+ e.getMessage());
            throw new ServiceException("Exception: "+ e.getMessage());
        }
    }

    @Override
    public boolean update(MetaLVA toUpdate) throws ServiceException, ValidationException {
        try {
            this.validateMetaLVA(toUpdate);
            boolean updated = metaLvaDao.update(toUpdate);
            return updated;
        } catch(ServiceException e) {
            logger.error("Exception: "+ e.getMessage());
            throw new ValidationException("Exception: "+ e.getMessage());
        } catch(DataAccessException e) {
            logger.error("Exception: "+ e.getMessage());
            throw new ServiceException("Exception: "+ e.getMessage());
        } catch(IOException e) {
            logger.error("Exception: "+ e.getMessage());
            throw new ServiceException("Exception: "+ e.getMessage());
        }
    }

    @Override
    public List<MetaLVA> readByModule(int module) throws ServiceException, ValidationException {
        if(module < 0) {
            logger.error("invalid parameter!");
            throw new ValidationException("invalid parameter!");
        }
        try {
            List<MetaLVA> metaLVAList = metaLvaDao.readByModule(module);
            return metaLVAList;
        }  catch(DataAccessException e) {
            logger.error("Exception: "+ e.getMessage());
            throw new ServiceException("Exception: "+ e.getMessage());
        }
    }

    @Override
    public void validateMetaLVA(MetaLVA toValidate) throws ServiceException {
        String error_msg = "";
        boolean valid = true;

        if(toValidate.getId() <= 0 ) {
            valid = false;
            error_msg += "invalid id!\n";
        }
        if(toValidate.getNr() == null) {
            valid = false;
            error_msg += "invalid Nr.!\n";
        }
        if(toValidate.getName() == null ) {
            valid = false;
            error_msg += "invalid name!\n";
        }
        if(toValidate.getECTS() < 0) {
            valid = false;
            error_msg += "invalid Ects (< 0)!\n";
        }
        if(toValidate.getSemestersOffered() == null) {
            valid = false;
            error_msg += "invalid semester!(null)\n";
        }
        if(valid == false) {
            logger.error("Invalid MetaLVA: "+ error_msg);
            throw new ServiceException("Invalid MetaLVA: "+ error_msg);
        }
    }

    @Override
    public void validateID(int id) throws ServiceException {
        if(id <= 0) {
            logger.error("invalid id!");
            throw new ServiceException("invalid id!");
        }
    }

    @Override
    public void delete(Integer id) throws ServiceException {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
