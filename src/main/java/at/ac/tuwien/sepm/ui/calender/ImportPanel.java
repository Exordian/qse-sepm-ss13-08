package at.ac.tuwien.sepm.ui.calender;

import at.ac.tuwien.sepm.ui.StandardSimpleInsidePanel;
import at.ac.tuwien.sepm.ui.UI;

/**
 * Created with IntelliJ IDEA.
 * User: Flo
 * Date: 19.06.13
 * Time: 09:27
 * To change this template use File | Settings | File Templates.
 */
@UI
public class ImportPanel extends StandardSimpleInsidePanel {
    //todo

    public ImportPanel(){
        init();
        addImage();
        addReturnButton();
        addTitle("Importieren");

        this.repaint();
        this.revalidate();
    }
}
