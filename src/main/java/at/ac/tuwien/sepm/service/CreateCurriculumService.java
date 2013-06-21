package at.ac.tuwien.sepm.service;

import at.ac.tuwien.sepm.entity.Curriculum;
import at.ac.tuwien.sepm.entity.MetaLVA;
import at.ac.tuwien.sepm.entity.Module;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * @author Markus MUTH
 */
public interface CreateCurriculumService {
    /**
     * Read a module by its id.
     * @param id The id of the module.
     * @return The module or <code>null</code> if there was no module found.
     * @throws ServiceException
     */
    public Module readModuleById(int id) throws ServiceException;

    /**
     * Read a module by its name.
     * @param name The name of the module.
     * @return The module or <code>null</code> if there was no matching module found.
     * @throws ServiceException
     */
    public Module readModuleByName(String name) throws ServiceException;

    /**
     * Read all modules.
     * @return A List containing all modules or an empty list if there was no module found.
     * @throws ServiceException If the data could not be read because any error occurred.
     */
    public List<Module> readAllModules() throws ServiceException;

    /**
     * Read all curriculum.
     * @return A List containing all curriculums or an empty list if there was no curriculum found.
     * @throws ServiceException If the data could not be read because any error occurred.
     */
    public List<Curriculum> readAllCurriculum() throws ServiceException;

    /**
     * Read all modules which are included to the specified curriculum.
     * @param id The id of the curriculum
     * @return All modules and if they have to be fully completed.
     * @throws ServiceException If the data could not be read because any error occurred.
     */
    public HashMap<Module, Boolean> readModuleByCurriculum(int id) throws ServiceException;

    /**
     * Add a module to the curriculum.
     * @param moduleId The id of the module.
     * @param curriculumId The id of the curriculum.
     * @param obligatory <code>true</code> if the module have to be completed completed.
     * @return <code>true</code> if the data could be stored and <code>false</code> otherwise.
     * @throws ServiceException If the data could not be stored because any error occurred.
     */
    public boolean addModuleToCurriculum(int moduleId, int curriculumId, boolean obligatory) throws ServiceException;

    /**
     * Delete the specified module from the specified Curriculum.
     * @param moduleId
     * @param curriculumId
     * @return <code>true</code> if the
     * @throws ServiceException If the data could not be deleted because any error occurred.
     */
    public boolean deleteModuleFromCurriculum(int moduleId, int curriculumId) throws ServiceException;

    /**
     * Create a new module.
     * @param m The module entity which contains the data to be stored.
     * @throws ServiceException If the data could not be stored because any error occurred.
     */
    public void createModule(Module m) throws ServiceException;

    /**
     * Create a new curriculum.
     * @param c The curriculum entity which contains the data to be stored.
     * @throws ServiceException If the data could not be stored because any error occurred.
     */
    public void createCurriculum(Curriculum c) throws ServiceException;

    /**
     * Create a new meta lva.
     * @param m The meta lva entity which contains the data to be stored.
     * @throws ServiceException If the data could not be stored because any error occurred.
     */
    public void createMetaLva(MetaLVA m) throws ServiceException;

    /**
     * Validate a module.
     * @param m
     * @throws IOException If
     * <code>m.getName()==null</code>
     * <code>m.getCompleteall()==null</code>
     */
    void validate(Module m) throws IOException;

    /**
     * Validate a curriculum.
     * @param c The curriculum to be validated.
     * @throws IOException If
     * <code>c.getStudyNumber() == null</code>
     * <code>c.getName()==null</code>
     * <code>c.getAcademicTitle()==null</code>
     * <code>c.getEctsChoice()==null</code>
     * <code>c.getEctsFree()==null</code>
     * <code>c.getEctsSoftskill()==null</code>
     * <code>c.getEctsChoice()<0</code>
     * <code>c.getEctsFree()<0</code> or
     * <code>c.getEctsSoftskill()<0</code>
     */
    void validate(Curriculum c) throws IOException;

    /*
     * Validate a MetaLVA
     * @param m
     * @throws IOException
     */
    //void validate(MetaLVA m) throws IOException;
}
