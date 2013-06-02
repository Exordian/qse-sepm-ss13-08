package at.ac.tuwien.sepm.service.impl;

import at.ac.tuwien.sepm.dao.CurriculumDao;
import at.ac.tuwien.sepm.dao.MetaLvaDao;
import at.ac.tuwien.sepm.dao.ModuleDao;
import at.ac.tuwien.sepm.dao.hsqldb.DBCurriculumDao;
import at.ac.tuwien.sepm.dao.hsqldb.DBMetaLvaDao;
import at.ac.tuwien.sepm.dao.hsqldb.DBModuleDao;
import at.ac.tuwien.sepm.entity.Curriculum;
import at.ac.tuwien.sepm.entity.MetaLVA;
import at.ac.tuwien.sepm.entity.Module;
import at.ac.tuwien.sepm.service.CreateCurriculumService;
import at.ac.tuwien.sepm.service.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * @author Markus MUTH
 */
@Service
public class CreateCurriculumServiceImpl implements CreateCurriculumService {
    @Autowired
    DBCurriculumDao curriculumDao;

    @Autowired
    DBModuleDao moduleDao;

    @Autowired
    DBMetaLvaDao metaLvaDao;

    public Module readModuleById(int id) throws ServiceException {
       try {
            return moduleDao.readById(id);
        } catch (DataAccessException e) {
            throw new ServiceException("Das Modul konnte nicht gelesen werden.", e);
        }
    }

    @Override
    public Module readModuleByName(String name) throws ServiceException {
       try {
            return moduleDao.readByName(name);
        } catch (DataAccessException e) {
            throw new ServiceException("Das Modul konnte nicht gelesen werden.", e);
        }
    }

    public List<Module> readAllModules() throws ServiceException {
        try {
            return moduleDao.readAll();
        } catch (DataAccessException e) {
            throw new ServiceException("Die Module konnten nicht gelesen werden.", e);
        }
    }

    public List<Curriculum> readAllCurriculum() throws ServiceException {
         try {
            return curriculumDao.readAll();
        } catch (DataAccessException e) {
            throw new ServiceException("Die Curricula konnten nicht gelesen werden.", e);
        }
    }

    @Override
    public HashMap<Module, Boolean> readModuleByCurriculum(int id) throws ServiceException {
        try {
            return moduleDao.readByCurriculum(id);
        } catch (DataAccessException e) {
            throw new ServiceException("Die Daten konnten nicht gelesen werden.", e);
        }
    }

    @Override
    public boolean addModuleToCurriculum(int moduleId, int curriculumId, boolean obligatory) throws ServiceException {
        try {
            return moduleDao.addToCurriculum(moduleId, curriculumId, obligatory);
        } catch (DataAccessException e) {
            throw new ServiceException("Die Daten konnten nicht gespeichert werden.", e);
        }
    }

    @Override
    public boolean deleteModuleFromCurriculum(int moduleId, int curriculumId) throws ServiceException {
        try {
            return moduleDao.deleteFromCurriculum(moduleId, curriculumId);
        } catch (DataAccessException e) {
            throw new ServiceException("Die Daten konnten nicht gelesen werden.", e);
        }
    }

    public void createModule(Module m) throws ServiceException{
        try {
            validate(m);
            moduleDao.create(m);
        } catch (IOException e) {
            throw new ServiceException(e.getMessage(), e);
        } catch (DataAccessException e) {
            throw new ServiceException("Das Modul konnte nicht angelegt werden.", e);
        }
    }

    public void createCurriculum(Curriculum c) throws ServiceException {
        try {
            validate(c);
            curriculumDao.create(c);
        } catch (IOException e) {
            throw new ServiceException(e.getMessage(), e);
        } catch (DataAccessException e) {
            throw new ServiceException("Der Studienplan konnte nicht angelegt werden.", e);
        }
    }

    public void createMetaLva(MetaLVA m) throws ServiceException {
        try {
            validate(m);
            metaLvaDao.create(m);
        } catch (IOException e) {
            throw new ServiceException(e.getMessage(), e);
        } catch (DataAccessException e) {
            throw new ServiceException("Die LVA konnte nicht angelegt werden.", e);
        }
    }

    public void validate(Module m) throws IOException {
        if(m.getName()==null) {
            throw new IOException("Bitte geben Sie einen Namen an.");
        }
        if(m.getCompleteall()==null) {
            throw new IOException("Bitte geben Sie an, ob das Modul komplett abgeschlossen werden muss.");
        }
    }

    public void validate(Curriculum c) throws IOException {
        if(c.getStudyNumber() == null) {
            throw new IOException("Bitte geben Sie eine Studienkennzahl an");
        }
        if(c.getName()==null) {
            throw new IOException("Bitte geben Sie einen Namen an.");
        }
        if(c.getAcademicTitle()==null) {
            throw new IOException("Bitte geben Sie einen Titel an.");
        }
        if(c.getEctsChoice()==null) {
            throw new IOException("Bitte geben Sie die Wahl-ECTS an.");
        }
        if(c.getEctsFree()==null) {
            throw new IOException("Bitte geben Sie die Frei-ECTS an.");
        }
        if(c.getEctsSoftskill()==null){
            throw new IOException("Bitte geben Sie die SoftSkill-ECTS an.");
        }
        if(c.getEctsChoice()<0) {
            throw new IOException("Die Wahl-ECTS müssen größer als 0 sein");
        }
        if(c.getEctsFree()<0) {
            throw new IOException("Die Frei-ECTS müssen größer als 0 sein.");
        }
        if(c.getEctsSoftskill()<0){
            throw new IOException("Die SoftSkill-ECTS müssen größer als 0 sein.");
        }
    }

    public void validate(MetaLVA m) throws IOException {
        // TODO use the meta lva validator at the lva management
    }
}
