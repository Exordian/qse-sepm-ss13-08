package at.ac.tuwien.sepm.dao.hsqldb;

/**
 * Author: MUTH Markus
 * Date: 5/24/13
 * Time: 1:37 AM
 * Description of class "ExceptionMessages":
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
}
