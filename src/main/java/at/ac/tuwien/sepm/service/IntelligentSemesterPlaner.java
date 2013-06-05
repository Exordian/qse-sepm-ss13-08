package at.ac.tuwien.sepm.service;

import at.ac.tuwien.sepm.entity.MetaLVA;
import at.ac.tuwien.sepm.service.Semester;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Georg Plaz
 */
public interface IntelligentSemesterPlaner {
    /**
     * sets the LVAs which should be used during the calculation.
     * @param forced these LVAs will be in the result anyway. It will not be tested, if these intersect each other,
     *               but before adding LVAs to the solution, they will be tested on intersection.
     * @param pool all LVAs, which should be considered for the solution. LVAs which are completed will not be considered
     */
    void setLVAs(List<MetaLVA> forced, List<MetaLVA> pool);

    /**
     * Plans a semester.
     * Important: setLVAs() must be called first! Otherwise the resulting list will be empty.
     * @param goalECTS the desired ECTS for the semester
     * @param year the year, for which a semester is to plan
     * @param sem the semester (Semester.S OR Semester.W) which is to plan.
     * @return a List with the result
     */
    ArrayList<MetaLVA> planSemester(float goalECTS, int year, Semester sem);
}
