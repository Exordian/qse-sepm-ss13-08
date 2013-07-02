package at.ac.tuwien.sepm.service.impl;

import at.ac.tuwien.sepm.dao.LvaDao;
import at.ac.tuwien.sepm.entity.LVA;
import at.ac.tuwien.sepm.service.LVAService;
import at.ac.tuwien.sepm.service.PropertyService;
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
 * @author Lena Lenz
 */
@Service
public class LVAServiceImpl implements LVAService {

    Logger logger = LogManager.getLogger(this.getClass().getSimpleName());

    @Autowired
    private PropertyService propertyService;

    @Autowired
    LvaDao lvaDao;

    @Override
    public boolean create(LVA toCreate) throws ServiceException, ValidationException {
        if(toCreate==null){
            throw new ServiceException("must not be null");
        }
        try {
            this.validateLVA(toCreate);
            boolean created = lvaDao.create(toCreate);
            return created;
        } catch(ServiceException e) {
            logger.error("Exception: "+ e.getMessage());
            throw new ValidationException("Exception: "+ e.getMessage());
        } catch(DataAccessException e) {
            logger.error("Exception: "+ e.getMessage());
            throw new ServiceException(e);
        } catch(IOException e) {
            logger.error("Exception: "+ e.getMessage());
            throw new ServiceException(e);
        }
    }

    @Override
    public LVA readById(int id) throws ServiceException, ValidationException {
        try {
            this.validateID(id);
            LVA lva = lvaDao.readById(id);
            return lva;
        } catch(ServiceException e) {
            logger.error("Exception: "+ e.getMessage());
            throw new ValidationException("Exception: "+ e.getMessage());
        } catch(DataAccessException e) {
            logger.error("Exception: "+ e.getMessage());
            throw new ServiceException(e);
        }
    }

    @Override
    public List<LVA> readByMetaLva(int metaLvaId) throws ServiceException, ValidationException {
        try {
            this.validateID(metaLvaId);
            List<LVA> lvaList = lvaDao.readByMetaLva(metaLvaId);
            return lvaList;
        } catch(ServiceException e) {
            logger.error("Exception: "+ e.getMessage());
            throw new ValidationException("Exception: "+ e.getMessage());
        } catch(DataAccessException e) {
            logger.error("Exception: "+ e.getMessage());
            throw new ServiceException(e);
        }
    }

    @Override
    public List<LVA> readUncompletedByYearSemesterStudyProgress(int year, Semester semester, boolean isInStudyProgress) throws ServiceException, ValidationException {
        if(semester==null){
            throw new ServiceException("semester must not be null");
        }
        if(year < 0 || semester == null) {
            logger.error("invalid parameters!");
            throw new ValidationException("invalid parameters!");
        }
        try {
            List<LVA> lvaList = lvaDao.readUncompletedByYearSemesterStudyProgress(year, semester, isInStudyProgress);
            return lvaList;
        } catch(DataAccessException e) {
            logger.error("Exception: "+ e.getMessage());
            throw new ServiceException(e);
        }
    }

    @Override
    public LVA readByIdWithoutLvaDates(int id) throws ServiceException, ValidationException {
        try {
            this.validateID(id);
            LVA lva = lvaDao.readByIdWithoutLvaDates(id);
            return lva;
        } catch(ServiceException e) {
            logger.error("Exception: "+ e.getMessage());
            throw new ValidationException("Exception: "+ e.getMessage());
        } catch(DataAccessException e) {
            logger.error("Exception: "+ e.getMessage());
            throw new ServiceException(e);
        }
    }

    @Override
    public List<LVA> readByYearAndSemester(int year, boolean isWinterSemester) throws ServiceException, ValidationException {
        if(year < 0 ) {
            logger.error("invalid parameter!");
            throw new ValidationException("invalid parameter!");
        }
        try {
            List<LVA> lvaList = lvaDao.readByYearAndSemester(year, isWinterSemester);
            return lvaList;
        } catch(DataAccessException e) {
            logger.error("Exception: "+ e.getMessage());
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean update(LVA toUpdate) throws ServiceException {
        if(toUpdate==null){
            throw new ServiceException("toUpdate must not be null");
        }
        try {
            this.validateID(toUpdate.getId());
            this.validateLVA(toUpdate);
            boolean updated = lvaDao.update(toUpdate);
            return updated;
        } catch(ServiceException e) {
            logger.error("Exception: "+ e.getMessage()+"\nLVA which threw Exception: "+toUpdate);
            throw new ServiceException("Exception: "+ e.getMessage());
        } catch(DataAccessException e) {
            logger.error("Exception: "+ e.getMessage()+"\nLVA which threw Exception: "+toUpdate);
            throw new ServiceException(e);
        } catch(IOException e) {
            logger.error("Exception: "+ e.getMessage()+"\nLVA which threw Exception: "+toUpdate);
            throw new ServiceException(e);
        }
    }

    @Override
    public void validateLVA(LVA toValidate) throws ServiceException {
        if(toValidate==null){
            throw new ServiceException("toValidate must not be null");
        }
        String error_msg = "";
        boolean valid = true;

        if(toValidate.getMetaLVA() == null) {
            valid = false;
            error_msg += "invalid metaLVA!\n";
        }

        if(toValidate.getSemester() == null) {
            valid = false;
            error_msg += "invalid semester!(null)\n";
        }
        if(toValidate.getYear() < -1) {
            valid = false;
            error_msg += "invalid year!(< 0)\n";
        }
        if(valid == false) {
            logger.error("Invalid LVA: "+ error_msg);
            throw new ServiceException("Invalid LVA: "+ error_msg);
        }
    }

    @Override
    public void validateID(int id) throws ServiceException {
        if(id < 0) {
            logger.error("invalid id!");
            throw new ServiceException("invalid id!");
        }
    }

    @Override
    public List<LVA> readAll() throws ServiceException, ValidationException {
        try {
            return lvaDao.readAll();
        } catch(DataAccessException e) {
            logger.error("Exception: "+ e.getMessage());
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean isFirstSemesterAWinterSemester() throws ServiceException {
        String temp = propertyService.getProperty(PropertyService.FIRST_SEMESTER);
        if (temp != null&&!temp.isEmpty()) {
            return temp.equals("WS");
        } else {
            logger.error("Erstes Semester Property File wurde nicht gefunden.");
            throw new ServiceException("Erstes Semester Property File wurde nicht gefunden.");
        }
    }

    @Override
    public int firstYearInStudyProgress() throws ServiceException {
        if (propertyService.getProperty(PropertyService.FIRST_YEAR) != null && !propertyService.getProperty(PropertyService.FIRST_YEAR).isEmpty()) {
            return Integer.parseInt(propertyService.getProperty(PropertyService.FIRST_YEAR));
        } else {
            logger.error("Erstes Jahr Property File wurde nicht gefunden.");
            throw new ServiceException("Erstes Jahr Property File wurde nicht gefunden.");
        }
    }

    @Override
    public int numberOfSemestersInStudyProgress() throws ServiceException {
        try {
            if ((propertyService.getProperty(PropertyService.FIRST_YEAR) != null && !propertyService.getProperty(PropertyService.FIRST_YEAR).isEmpty()) && !readAll().isEmpty()) {
                int beginning = Integer.parseInt(propertyService.getProperty(PropertyService.FIRST_YEAR));
                LVA temp = null;
                for (LVA l : readAll()) {
                    if (temp == null)
                        temp = l;
                    if(l.isInStudyProgress()) {
                        if (l.getYear() >= temp.getYear())
                            if (temp.getSemester() != Semester.UNKNOWN)
                                temp = l;
                    }
                }
                if (!temp.isInStudyProgress()) {
                    logger.info("Sie müssen sich ein Studium planen lassen.");
                    throw new ValidationException("Sie müssen sich ein Studium planen lassen.");
                }
                int x = temp.getYear()-beginning;
                boolean isLastWinterSemester = false;

                if (temp.getSemester() == Semester.S) {
                    isLastWinterSemester=false;
                } else if (temp.getSemester() == Semester.W) {
                    isLastWinterSemester= true;
                } else  if (temp.getSemester() == Semester.W_S) {
                    isLastWinterSemester=false;
                }

                if (x == 0) {
                    if (beginning == 0) {
                         return 0;
                    } else {
                        return isFirstSemesterAWinterSemester()? 2 : 1;
                    }
                } else {
                    if (!isFirstSemesterAWinterSemester()) {
                        if (!isLastWinterSemester) {
                            return (x*2)+1;
                        } else {
                            return (x*2)+2;
                        }
                    } else {
                        if (!isLastWinterSemester) {
                            return x*2;
                        } else {
                            return (x*2)+1;
                        }
                    }
                }
            } else {
                if (readAll().isEmpty()) {
                    logger.info("Sie müssen sich ein Studium planen lassen.");
                    throw new ValidationException("Sie müssen sich ein Studium planen lassen.");
                } else {
                    logger.error("Erstes Jahr Property File wurde nicht gefunden.");
                    throw new ValidationException("Erstes Jahr Property File wurde nicht gefunden.");
                }
            }
        } catch (ValidationException e) {
            logger.info(e.getMessage());
            throw new ServiceException(e.getMessage());
        }
    }
}
