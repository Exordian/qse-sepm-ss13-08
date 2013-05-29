package at.ac.tuwien.sepm.ui.semesterPlanning;

import at.ac.tuwien.sepm.ui.semesterPlanning.FirstPanel;
import at.ac.tuwien.sepm.ui.UI;
import org.apache.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.*;
import java.awt.*;
import java.util.logging.Logger;

/**
 * Author: Georg Plaz
 */
@UI
public class PlanningPanel extends JPanel implements ChainHead{
    public JPanel content = new FirstPanel(this);
    org.apache.log4j.Logger logger = LogManager.getLogger(this.getClass().getSimpleName());
    public PlanningPanel(){
        add(content);
        setBackground(new Color(0,0,0,0));
    }

    @Override
    public void setTo(ChainedPanel panel) {
        remove(content);
        content = panel;
        add(content);
        getParent().revalidate();
        getParent().repaint();
        logger.debug("display panel: " + panel.getClass().getSimpleName());
    }
}
