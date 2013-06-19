package at.ac.tuwien.sepm.entity;

/**
 * @author Markus MUTH
 *
 * This enum lists all possible types of lva dates.
 */
public enum LvaDateType {
    LECTURE,
    EXERCISE,
    EXAM,
    DEADLINE;
    public String toString(){
        switch(this){
            case LECTURE:
                return "Vorlesung";
            case EXERCISE:
                return "Uebung";
            case EXAM:
                return "Pruefung";
            case DEADLINE:
                return "Deadline";
            default:
                return "Unbekannt";
        }
    }
}
