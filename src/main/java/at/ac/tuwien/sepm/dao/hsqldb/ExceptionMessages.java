package at.ac.tuwien.sepm.dao.hsqldb;

/**
 * @author Markus MUTH
 */
public class ExceptionMessages {
    static String tooLongName(int i) {
        return "Der Name ist zu lang (max. " + i + " Zeichen).";
    }

    static String tooLongDescription(int i) {
        return "Die Beschreibung ist zu lang (max. " + i + " Zeichen).";
    }

    static String tooLongRoom(int i) {
        return "Die Bezeichnung des Raumes ist zu lang (max. " + i + " Zeichen).";
    }

    static String tooLongLvaNumber(int i) {
        return "Die LVA-Nummer ist zu lang (max. " + i + " Zeichen).";
    }

    static String tooLongStudyNumber(int i) {
        return "Die Studien-Kennzahl ist zu lang (max. " + i + " Zeichen).";
    }

    static String tooLongTitle(int i) {
        return "Der Titel ist zu lang (max. " + i + " Zeichen).";
    }
}
