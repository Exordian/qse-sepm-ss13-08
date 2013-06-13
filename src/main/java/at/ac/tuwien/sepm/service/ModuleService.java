package at.ac.tuwien.sepm.service;

import at.ac.tuwien.sepm.entity.Module;
import at.ac.tuwien.sepm.service.impl.ValidationException;

import java.util.List;

/**
 * @author Markus MUTH
 */
public interface ModuleService {
    /**
     * Create a new Module. All containing MetaLVAs are also created.
     * @param toCreate The entity containing all
     * @return <code>false</code> if all objects could be created and <code>false</code> if not
     * @throws ServiceException If any error occurred.
     */
    boolean create(Module toCreate) throws ServiceException, ValidationException;

    /**
     * reads all modules from the database
     * @return list of all modules, null if no modules exist
     * @throws ServiceException If any error occurred.
     */
    List<Module> readAll() throws ServiceException;
}
