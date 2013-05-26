package at.ac.tuwien.sepm.ui.semesterPlanning;

import javax.swing.*;

/**
 * Author: Georg Plaz
 */
public class ChainedPanel extends JPanel {
    private ChainHead head;
    private ChainedPanel prevPanel;
    public ChainedPanel(){
    }
    public ChainedPanel(ChainHead head){
        this.head=head;
    }
    public void back(){
        head.setTo(prevPanel);
    }
    public void setNext(ChainedPanel nextPanel){
         nextPanel.prevPanel = this;
         nextPanel.head = head;
         head.setTo(nextPanel);
        revalidate();
        repaint();
    }

}
