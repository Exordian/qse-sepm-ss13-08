package at.ac.tuwien.sepm.service;

import at.ac.tuwien.sepm.entity.Curriculum;
import at.ac.tuwien.sepm.entity.MetaLVA;
import at.ac.tuwien.sepm.entity.Module;

import java.io.IOException;
import java.util.List;

/**
 * @author Markus MUTH
 */
public interface CreateCurriculumService {
    public Module readModuleById(int id) throws ServiceException;
    public Module readModuleByName(String name) throws ServiceException;
    public List<Module> readAllModules() throws ServiceException;
    public List<Curriculum> readAllCurriculum() throws ServiceException;
    public void createModule(Module m) throws ServiceException;
    public void createCurriculum(Curriculum c) throws ServiceException;
    public void createMetaLva(MetaLVA m) throws ServiceException;
    void validate(Module m) throws IOException;
    void validate(Curriculum c) throws IOException;
    void validate(MetaLVA m) throws IOException;
}
