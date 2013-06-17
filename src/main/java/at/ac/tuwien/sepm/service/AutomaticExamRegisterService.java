package at.ac.tuwien.sepm.service;

import at.ac.tuwien.sepm.entity.TissExam;

import java.util.List;

public interface AutomaticExamRegisterService {
    boolean registerForExam(String lvanr, String examname) throws ServiceException;

    List<TissExam> listExamsForLva(String lvanr) throws ServiceException;

    void addRegistration(TissExam tissExam) throws ServiceException;
}
