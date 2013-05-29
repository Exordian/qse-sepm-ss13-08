package at.ac.tuwien.sepm.ui.semesterPlanning;

import org.apache.log4j.LogManager;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: Georg Plaz
 */
public class ChainedPanel extends JPanel {
    private ChainHead head;
    private ChainedPanel prevPanel;
    private ChainedPanel nextPanel;
    private boolean alive = true;
    private ArrayList<Thread> allThreads = new ArrayList<Thread>();
    private org.apache.log4j.Logger logger = LogManager.getLogger(this.getClass().getSimpleName());
    public ChainedPanel(){
        logger.debug("entering class: "+this.getClass().getSimpleName());
    }
    public ChainedPanel(ChainHead head){
        this.head=head;
        setBackground(new Color(0,0,0,0));
    }
    public void setNext(ChainedPanel nextPanel){
        if(this.nextPanel!=null){
            this.nextPanel.kill();
        }
        if(alive){
            this.nextPanel=nextPanel;
            nextPanel.prevPanel = this;
            nextPanel.head = head;
        }
        //revalidate();
        //repaint();
    }
    public void next(){
        if(alive){
            head.setTo(nextPanel);
        }
    }
    public void replace(ChainedPanel newPanel){
        if(alive){
            prevPanel.next(newPanel);
        }
    }
    public void next(ChainedPanel nextPanel){
        if(alive){
            setNext(nextPanel);
            next();
        }
    }
    public void back(){
        if(alive){
            head.setTo(prevPanel);
        }
    }
    public void backAndKill(){
        if(alive){
            back();
        }
        kill();
    }
    private void kill(){
        if(nextPanel!=null){
            nextPanel.kill();
        }
        alive=false;
        prevPanel.nextPanel=null;
        prevPanel=null;
        head=null;
        for(Thread t:allThreads){
            t.interrupt();
        }
    }
    public void registerThread(Thread t){
        allThreads.add(t);
    }
    public List<Thread> getThreads(){
        return allThreads;
    }
}
