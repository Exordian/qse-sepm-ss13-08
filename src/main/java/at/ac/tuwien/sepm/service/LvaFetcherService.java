package at.ac.tuwien.sepm.service;

import at.ac.tuwien.sepm.entity.Curriculum;
import at.ac.tuwien.sepm.entity.MetaLVA;
import at.ac.tuwien.sepm.entity.Module;
import at.ac.tuwien.sepm.service.ServiceException;

import java.util.List;

public interface LvaFetcherService {
    /**
     * Get all Academic Programs from an external Service
     * @return List of Curriculums
     * @throws ServiceException if URL could not be loaded
     */
    List<Curriculum> getAcademicPrograms() throws ServiceException;

    /**
     * Get all Academic Programs from an external Service
     * @param recursive if true, fetch modules and lva's too
     * @return List of Curriculums
     * @throws ServiceException if URL could not be loaded
     */
    List<Curriculum> getAcademicPrograms(boolean recursive) throws ServiceException;

    /**
     * Get all Modules from an external Service
     * @param studyNumber study number valid format: '123.123' '123 123' '123123'
     * @return List of Modules
     * @throws ServiceException if study number could not be loaded
     */
    List<Module> getModules(String studyNumber) throws ServiceException;

    /**
     * Get all Modules from an external Service
     * @param studyNumber study number valid format: '123 123'
     * @param recursive if true, fetch lva's too
     * @return List of Modules
     * @throws ServiceException if study number could not be loaded
     */
    List<Module> getModules(String studyNumber, boolean recursive) throws ServiceException;

    /**
     * Get all Modules from an external Service
     * @param studyNumber study number valid format: '123 123'
     * @param recursive if true, fetch lva's too
     * @param semester semester like "2012W" if not current semester
     * @return List of Modules
     * @throws ServiceException if study number could not be loaded
     */
    List<Module> getModules(String studyNumber, boolean recursive, String semester) throws ServiceException;

    /**
     * Get all MetaLVA incl LVA from an external Service
     * @param lvaNr study number valid format: '123.123' '123 123' '123123'
     * @param semester in format '2012W'
     * @return a MetaLVA incl LVA
     * @throws ServiceException if lva could not be loaded
     */
    MetaLVA getLva(String lvaNr, String semester) throws ServiceException;
}
