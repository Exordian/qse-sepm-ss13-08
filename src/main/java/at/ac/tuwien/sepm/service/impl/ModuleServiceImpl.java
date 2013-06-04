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
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @author Markus MUTH
 */
@Service
public class ModuleServiceImpl implements ModuleService {

    Logger logger = LogManager.getLogger(this.getClass().getSimpleName());

    @Autowired
    ModuleDao moduleDao;

    @Autowired
    MetaLVAService metaLVAService;

    @Override
    public boolean create(Module toCreate) throws ServiceException, ValidationException {
        try {
            boolean moduleCreated = moduleDao.create(toCreate);

            boolean metaLvasCreated = true;
            if(toCreate.getMetaLvas() != null) {
                for (MetaLVA m : toCreate.getMetaLvas()) {
                    m.setModule(toCreate.getId());
                    metaLvasCreated = metaLvasCreated && metaLVAService.create(m);
                }
            }

            return moduleCreated && metaLvasCreated;

            /*
            this.validateMetaLVA(toCreate);
            boolean metaLvaCreated = metaLvaDao.create(toCreate);
            toCreate.getLVAs().get(0).setId(toCreate.getId());
            boolean lvaCreated = lvaDao.create(toCreate.getLVAs().get(0));
            int lvaId = toCreate.getLVAs().get(0).getId();

            return metaLvaCreated && lvaCreated; */
        } catch(ServiceException e) {
            logger.error("Exception: "+ e.getMessage());
            throw new ValidationException("Exception: "+ e.getMessage());
        } catch(DataAccessException e) {
            logger.error("Exception: "+ e.getMessage());
            throw new ServiceException("Exception: "+ e.getMessage());
        } catch(IOException e) {
            logger.error("Exception: "+ e.getMessage());
            throw new ServiceException("Exception: "+ e.getMessage());
        } catch (ValidationException e) {
            logger.error("Exception: " + e.getMessage());
            throw new ServiceException("Exception: "+ e.getMessage());
        }
    }
}
