package at.ac.tuwien.sepm.service.impl;

import at.ac.tuwien.sepm.dao.LvaDao;
import at.ac.tuwien.sepm.dao.LvaDateDao;
import at.ac.tuwien.sepm.dao.MetaLvaDao;
import at.ac.tuwien.sepm.entity.LvaDate;
import at.ac.tuwien.sepm.entity.MetaLVA;
import at.ac.tuwien.sepm.service.MetaLVAService;
import at.ac.tuwien.sepm.service.Semester;
import at.ac.tuwien.sepm.service.ServiceException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
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

    MergerImpl merger = new MergerImpl();

    private List<MetaLVA> metaLVAsWithPrecessors = new ArrayList<MetaLVA>();

    @Autowired
    MetaLvaDao metaLvaDao;

    @Autowired
    LvaDao lvaDao;

    @Autowired
    LvaDateDao lvaDateDao;

    public void startMergeSession() {
        merger.reset();
    }

    @Override
    public List<MetaLVA> getNewMetaLvasWithMergeConflicts() {
        return merger.getNewMetaLVAs();
    }

    @Override
    public List<MetaLVA> getOldMetaLvasWithMergeConflicts() {
        return merger.getOldMetaLVAs();
    }

    private void storeMetaLVAWithPrecessor(MetaLVA toStore){
        metaLVAsWithPrecessors.add(toStore);
    }
    public boolean writePrecessors(){
        boolean toReturn = true;
        List<MetaLVA> toRemove = new LinkedList<MetaLVA>();
        for(MetaLVA metaLVA :metaLVAsWithPrecessors){
            boolean wroteAllPrecursor = true;
            List<MetaLVA> predecessors = new LinkedList<MetaLVA>();
            for(MetaLVA predecessorTemp :metaLVA.getPrecursor()){
                MetaLVA predecessor = metaLvaDao.readByLvaNumber(predecessorTemp.getNr());
                if(predecessor==null){
                    wroteAllPrecursor=toReturn=false;
                    logger.error("error while reading precursor. storing successor for trying again later. successor: "+metaLVA+", precursor: "+ predecessorTemp);
                    break;
                }else{
                    predecessors.add(predecessor);
                }
            }
            if(wroteAllPrecursor){
                for(MetaLVA predecessor : predecessors){
                    metaLvaDao.setPredecessor(metaLVA.getId(), predecessor.getId());
                    logger.info("predecessor saved. successor: "+metaLVA+", precursor: "+ predecessor);
                }
                toRemove.add(metaLVA);
            }
        }
        metaLVAsWithPrecessors.removeAll(toRemove);
        return toReturn;
    }
    public boolean stopMergeSession() {
        if(mergingNecessary()) {
            String logString = "meta lva merger stopped - conflicts at storing following " + merger.getNewMetaLVAs().size() + " meta lva(s): \n";
            for(MetaLVA s : merger.getNewMetaLVAs()) {
                logString = logString + "\t\t" + s.getNr() + "\t" + s.getName() + "\n";
            }
            logger.info(logString);
            return true;
        } else {
            logger.info("meta lva merger stopped - no conflicts");
            return false;
        }
    }

    public boolean mergingNecessary() {
        return merger.metaLvaMergingNecessary();
    }

    @Override
    public boolean create(MetaLVA toCreate) throws ServiceException {
        try {
            this.validateMetaLVA(toCreate);
            boolean metaLvaCreated = metaLvaDao.create(toCreate);
            if(metaLvaCreated){
                storeMetaLVAWithPrecessor(toCreate);
            }
            boolean lvaCreated=false;
            if(toCreate.getLVAs()!=null && toCreate.getLVAs().size()>0){
                toCreate.getLVAs().get(0).setId(toCreate.getId());

                lvaCreated = lvaDao.create(toCreate.getLVAs().get(0));
                int lvaId = toCreate.getLVAs().get(0).getId();


                boolean datesCreated = true;
                if(toCreate.getLVAs().get(0).getLectures() != null) {
                    for(LvaDate d : toCreate.getLVAs().get(0).getLectures()) {
                        d.setLva(lvaId);
                        datesCreated = datesCreated && lvaDateDao.create(d);
                    }
                }
                if(toCreate.getLVAs().get(0).getExercises() != null) {
                    for(LvaDate d : toCreate.getLVAs().get(0).getExercises()) {
                        d.setLva(lvaId);
                        datesCreated = datesCreated && lvaDateDao.create(d);
                    }
                }
                if(toCreate.getLVAs().get(0).getExams() != null) {
                    for(LvaDate d : toCreate.getLVAs().get(0).getExams()) {
                        d.setLva(lvaId);
                        datesCreated = datesCreated && lvaDateDao.create(d);
                    }
                }
                if(toCreate.getLVAs().get(0).getDeadlines() != null) {
                    for(LvaDate d : toCreate.getLVAs().get(0).getDeadlines()) {
                        d.setLva(lvaId);
                        datesCreated = datesCreated && lvaDateDao.create(d);
                    }
                }
            }
            return metaLvaCreated && lvaCreated;
        } catch(ServiceException e) {
            logger.error("Exception: "+ e.getMessage());
            throw new ServiceException(e);
        } catch(DuplicateKeyException e) {
            MetaLVA oldMetaLva;
            try {
                oldMetaLva = metaLvaDao.readByLvaNumber(toCreate.getNr());
            } catch (DataAccessException e1) {
                logger.error("Exception: "+ e1.getMessage());
                throw new ServiceException(e1);
            }
            if(oldMetaLva==null) {
                throw new ServiceException("Internal error");
            }

            merger.add(oldMetaLva, toCreate);
            logger.info("The meta lva \""  + toCreate.getNr() + " - " + toCreate.getName() + "\" is already stored.\n");
            return false;
        } catch(DataAccessException e) {
            logger.error("Exception " + e.getClass() + ": " + e.getMessage(), e);
            throw new ServiceException(e);
        } catch(IOException e) {
            logger.error("Exception: "+ e.getMessage());
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean setPredecessor(int lvaId, int predecessorId) throws ServiceException {
        try {
            this.validateID(lvaId);
            this.validateID(predecessorId);
            boolean set_predecessor = metaLvaDao.setPredecessor(lvaId, predecessorId);
            return set_predecessor;
        } catch(ServiceException e) {
            logger.error("Exception: "+ e.getMessage());
            throw new ServiceException(e);
        } catch(DataAccessException e) {
            logger.error("Exception: "+ e.getMessage());
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean unsetPredecessor(int lvaId, int predecessorId) throws ServiceException {
        try {
            this.validateID(lvaId);
            this.validateID(predecessorId);
            boolean unset_predecessor = metaLvaDao.unsetPredecessor(lvaId, predecessorId);
            return unset_predecessor;
        } catch(ServiceException e) {
            logger.error("Exception: "+ e.getMessage());
            throw new ServiceException(e);
        } catch(DataAccessException e) {
            logger.error("Exception: "+ e.getMessage());
            throw new ServiceException(e);
        }
    }

    @Override
    public MetaLVA readById(int id) throws ServiceException {
        try {
            this.validateID(id);
            MetaLVA metaLVA = metaLvaDao.readById(id);
            return metaLVA;
        } catch(ServiceException e) {
            logger.error("Exception: "+ e.getMessage());
            throw new ServiceException(e);
        } catch(DataAccessException e) {
            logger.error("Exception: "+ e.getMessage());
            throw new ServiceException(e);
        }
    }

    @Override
    public List<MetaLVA> readAllPredecessors(int lvaId) throws ServiceException {
        try {
            this.validateID(lvaId);
            List<MetaLVA> metaLVAList = metaLvaDao.readAllPredecessors(lvaId);
            return metaLVAList;
        } catch(ServiceException e) {
            logger.error("Exception: "+ e.getMessage());
            throw new ServiceException(e);
        } catch(DataAccessException e) {
            logger.error("Exception: "+ e.getMessage());
            throw new ServiceException(e);
        }
    }

    @Override
    public MetaLVA readByIdWithoutLvaAndPrecursor(int id) throws ServiceException {
        try {
            this.validateID(id);
            MetaLVA metaLVA = metaLvaDao.readByIdWithoutLvaAndPrecursor(id);
            return metaLVA;
        } catch(ServiceException e) {
            logger.error("Exception: "+ e.getMessage());
            throw new ServiceException(e);
        } catch(DataAccessException e) {
            logger.error("Exception: "+ e.getMessage());
            throw new ServiceException(e);
        }
    }

    @Override
    public MetaLVA readByLvaNumber(String lvaNumber) throws ServiceException {
        if(lvaNumber == null) {
            logger.error("invalid parameter!(lvaNumber)");
            throw new ServiceException("invalid parameter!(lvaNumber)");
        }
        try {
            MetaLVA metaLva = metaLvaDao.readByLvaNumber(lvaNumber);
            return metaLva;
        } catch(DataAccessException e) {
            logger.error("Exception: "+ e.getMessage());
            throw new ServiceException(e);
        }
    }

    @Override
    public List<MetaLVA> readUncompletedByYearSemesterStudyProgress(int year, Semester semester, boolean isInStudyProgress) throws ServiceException {
        if(year < 0 || semester == null) {
            logger.error("invalid parameters!");
            throw new ServiceException("invalid parameters!");
        }
        try {
            List<MetaLVA> metaLVAList = metaLvaDao.readUncompletedByYearSemesterStudyProgress(year, semester, isInStudyProgress);
            return metaLVAList;
        } catch(DataAccessException e) {
            logger.error("Exception: "+ e.getMessage());
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean update(MetaLVA toUpdate) throws ServiceException {
        try {
            this.validateMetaLVA(toUpdate);
            boolean updated = metaLvaDao.update(toUpdate);
            return updated;
        } catch(ServiceException e) {
            logger.error("Exception: "+ e.getMessage());
            throw new ServiceException(e);
        } catch(DataAccessException e) {
            logger.error("Exception: "+ e.getMessage());
            throw new ServiceException(e);
        } catch(IOException e) {
            logger.error("Exception: "+ e.getMessage());
            throw new ServiceException(e);
        }
    }

    @Override
    public List<MetaLVA> readByModule(int module) throws ServiceException {
        if(module < 0) {
            logger.error("invalid parameter!");
            throw new ServiceException("invalid parameter!");
        }
        try {
            List<MetaLVA> metaLVAList = metaLvaDao.readByModule(module);
            return metaLVAList;
        }  catch(DataAccessException e) {
            logger.error("Exception: "+ e.getMessage());
            throw new ServiceException(e);
        }
    }

    @Override
    public void validateMetaLVA(MetaLVA toValidate) throws ServiceException {
        String error_msg = "";
        boolean valid = true;

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
        valid=true;
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
    public List<MetaLVA> readAll() throws ServiceException {
        try {
            List<MetaLVA> metaLVAList = metaLvaDao.readAll();
            return metaLVAList;
        }  catch(DataAccessException e) {
            logger.error("Exception: "+ e.getMessage());
            throw new ServiceException(e);
        }
    }

    @Override
    public void delete(Integer id) throws ServiceException {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public void addForMerge(MetaLVA toMerge) throws ServiceException {
        MetaLVA oldMetaLVA;
        try {
            oldMetaLVA = metaLvaDao.readByLvaNumber(toMerge.getNr());
        } catch (DataAccessException e1) {
            logger.error("Exception: "+ e1.getMessage());
            throw new ServiceException(e1.getMessage());
        }
        if(oldMetaLVA==null) {
            throw new ServiceException("Internal error");
        }
        merger.add(oldMetaLVA, toMerge);


    }
}
