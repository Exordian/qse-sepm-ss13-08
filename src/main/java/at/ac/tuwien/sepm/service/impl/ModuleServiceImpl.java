package at.ac.tuwien.sepm.service.impl;

import at.ac.tuwien.sepm.dao.ModuleDao;
import at.ac.tuwien.sepm.entity.MetaLVA;
import at.ac.tuwien.sepm.entity.Module;
import at.ac.tuwien.sepm.service.MetaLVAService;
import at.ac.tuwien.sepm.service.ModuleService;
import at.ac.tuwien.sepm.service.ServiceException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * @author Markus MUTH
 */
@Service
public class ModuleServiceImpl implements ModuleService {

    Logger logger = LogManager.getLogger(this.getClass().getSimpleName());

    MergerImpl merger = new MergerImpl();

    @Autowired
    ModuleDao moduleDao;

    @Autowired
    MetaLVAService metaLVAService;

    public void startMergeSession() {
        merger.reset();
        metaLVAService.startMergeSession();
    }

    public boolean stopMergeSession() {
        if(mergingNecessary()) {
            String logString = "module merger stopped - conflicts at storing following " + merger.getNewModules().size() + " module(s): \n";
            for(Module s : merger.getNewModules()) {
                logString = logString + "\t\t" + s.getName() + "\n";
            }
            logger.info(logString);
            metaLVAService.stopMergeSession();
            return true;
        } else {
            logger.info("module merger stopped - no conflicts");
            metaLVAService.stopMergeSession();
            return false;
        }
    }

    public boolean mergingNecessary() {
        return merger.moduleMergingNecessary() || metaLVAService.mergingNecessary();
    }

    @Override
    public List<Module> getNewModulesWithMergeConflicts() {
        return merger.getNewModules();
    }

    @Override
    public List<MetaLVA> getNewMetaLvasWithMergeConflicts() {
        return metaLVAService.getNewMetaLvasWithMergeConflicts();
    }

    @Override
    public boolean create(Module toCreate) throws ServiceException, ValidationException {
        boolean moduleCreated = false;
        boolean metaLvasCreated = true;

        try {
            moduleCreated = moduleDao.create(toCreate);
        } catch(DuplicateKeyException e) {
            logger.info("The module \"" + toCreate.getName() + "\" is already stored.");
            Module oldModule;
            try {
                oldModule = moduleDao.readByName(toCreate.getName());
            } catch (DataAccessException e1) {
                logger.error("Exception: "+ e1.getMessage());
                throw new ServiceException("Exception: " + e.getMessage(), e1);
            }
            if(oldModule==null) {
                logger.error("newModule == null");
                throw new ServiceException("Internal error");
            }
            merger.add(oldModule, toCreate);
            moduleCreated = false;
        } catch(DataAccessException e) {
            logger.error("Exception: " + e.getClass() + "\t" + e.getMessage());
            throw new ServiceException("Exception: "+ e.getMessage());
        } catch(IOException e) {
            logger.error("Exception: "+ e.getMessage());
            throw new ServiceException("Exception: "+ e.getMessage());
        }

        try {
            if(toCreate.getMetaLvas() != null) {
                int id = moduleDao.readByName(toCreate.getName()).getId();
                //logger.debug("Amount of meta lvas stored in toCreate: " + toCreate.getMetaLvas().size());
                for (MetaLVA m : toCreate.getMetaLvas()) {
                    //logger.debug("Following meta lva is now being created: " + m.getNr() + "\t" + m.getName() + "\t\t\t" + m.toString());
                    m.setModule(id);
                    boolean b = metaLVAService.create(m);
                    metaLvasCreated = metaLvasCreated &&  b;
                    //logger.debug("Following meta lva has being created: "  + b + "\t" + m.getNr() + "\t" + m.getName());
                }
            } else {
                logger.debug("Amount of meta lvas stored in toCreate: 0");
            }
        } catch(ServiceException e) {
            logger.error("Exception: "+ e.getMessage());
            throw new ServiceException("MetaLVA could not be created", e);
        }

        return moduleCreated && metaLvasCreated;
    }

    @Override
    public boolean update(Module toUpdate) throws ServiceException {
        try {
            moduleDao.update(toUpdate);
        } catch (IOException e) {
            throw new ServiceException(e.getMessage(), e);
        } catch (DataAccessException e) {
            throw new ServiceException(e.getMessage(), e);
        }
        return false;
    }

    @Override
    public List<Module> readAll() throws ServiceException {
        try {
            return moduleDao.readAll();
        } catch (DataAccessException e) {
            logger.error("Exception: " + e.getClass() + "\t" + e.getMessage());
            throw new ServiceException("Exception: "+ e.getMessage());
        }
    }
}
