package at.ac.tuwien.sepm.ui.studyProgress;

import at.ac.tuwien.sepm.ui.StandardInsidePanel;
import at.ac.tuwien.sepm.ui.UI;

import javax.swing.*;
import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: Flo
 * Date: 01.06.13
 * Time: 19:23
 * To change this template use File | Settings | File Templates.
 */
@UI
public class ExportPanel extends StandardInsidePanel {

    public ExportPanel() {
        this.setLayout(null);
        this.setOpaque(false);
        loadFonts();
        setBounds((int) startCoordinateOfWhiteSpace.getX(), (int) startCoordinateOfWhiteSpace.getY(),(int) whiteSpace.getWidth(),(int) whiteSpace.getHeight());

        JButton test = new JButton("test");
        test.setBounds(100, 100, 40, 40);
        test.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.add(test);
    }
}
