package at.ac.tuwien.sepm.service;

import at.ac.tuwien.sepm.entity.MetaLVA;
import at.ac.tuwien.sepm.entity.Module;
import at.ac.tuwien.sepm.service.impl.ValidationException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Markus MUTH
 */
public interface ModuleService {
    public void startMergeSession();

    public boolean stopMergeSession();

    public boolean mergingNecessary();

    public List<Module> getNewModulesWithMergeConflicts();

    public List<MetaLVA> getNewMetaLvasWithMergeConflicts();

    public boolean update(Module toUpdate) throws ServiceException;

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

    List<Module> getRequiredModules(String pdfPath) throws ServiceException;

    List<Module> getOptionalModules(String pdfPath) throws ServiceException;
}
