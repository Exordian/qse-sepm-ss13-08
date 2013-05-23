package at.ac.tuwien.sepm.dao.hsqldb;

/**
 * Author: MUTH Markus
 * Date: 5/23/13
 * Time: 9:16 PM
 * Description of class "NumberGenerator":
 */
public class NumberGenerator {
    private static int i=-1;

    public static String get() {
        i++;
        return i + " ... ";
    }
}
