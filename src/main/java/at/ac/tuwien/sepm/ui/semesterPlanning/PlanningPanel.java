package at.ac.tuwien.sepm.ui.semesterPlanning;

import at.ac.tuwien.sepm.ui.semesterPlanning.FirstPanel;
import at.ac.tuwien.sepm.ui.UI;

import javax.swing.*;
import java.awt.*;

/**
 * Author: Georg Plaz
 */
@UI
public class PlanningPanel extends JPanel implements ChainHead{
    public JPanel content = new FirstPanel(this);
    public PlanningPanel(){
        add(content);

    }

    @Override
    public void setTo(ChainedPanel panel) {
        remove(content);
        content = panel;

        add(content);
        revalidate();
        repaint();
    }
}
