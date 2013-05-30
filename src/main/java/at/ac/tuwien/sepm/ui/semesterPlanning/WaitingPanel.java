package at.ac.tuwien.sepm.ui.semesterPlanning;

import at.ac.tuwien.sepm.entity.LvaDate;
import at.ac.tuwien.sepm.entity.MetaLVA;
import at.ac.tuwien.sepm.service.Semester;
import at.ac.tuwien.sepm.service.semesterPlanning.IntelligentSemesterPlaner;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: Georg Plaz
 */
public class WaitingPanel extends ChainedPanel{
    private IntelligentSemesterPlaner planer = new IntelligentSemesterPlaner();
    private ArrayList<MetaLVA> lvas;
    Logger logger = LogManager.getLogger(this.getClass().getSimpleName());
    //private static final String pleaseWait= "Bitte warten!\n\n Verstrichene Zeit:";
    private JTextArea pleaseWait = new JTextArea();
    //private JTextArea pleaseWait = new JTextArea("Verstrichene Wartezeit: !");
    private JButton backButton = new JButton("zurueck");
    private int secCounter=0;

    public WaitingPanel(List<MetaLVA> forced, List<MetaLVA> pool, float goalECTS, boolean voIntersect, int year, Semester sem, long waiting) {
        for(MetaLVA m:pool){
            logger.debug("lva: "+m);
            logger.debug("isNull: "+(m.getLVA(year,sem)==null));
            logger.debug("isNull: "+(m.getLVA(year,sem).getLectures()==null));
            logger.debug("size: "+m.getLVA(year,sem).getLectures().size());
            for(LvaDate d:m.getLVA(year,sem).getLectures()){
                logger.debug("\tdate: "+d.getTime());
            }
        }
        add(pleaseWait);
        pleaseWait.setEditable(false);
        CalculateSemesterThread t= new CalculateSemesterThread(forced, pool, goalECTS, voIntersect,year,sem);
        TimeCounter counter = new TimeCounter();
        registerThread(counter);
        registerThread(t);

        add(backButton);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                backAndKill();
            }
        });
        t.setWaiting(waiting);

    }
    public void startThreads(){
        for(Thread t:getThreads()){
            t.start();
        }
        //t.start(waiting);
        //EventQueue.invokeLater(counter);

        //EventQueue.invokeLater(t);
    }
    public void setLVAs(ArrayList<MetaLVA> lvas){

        replace(new ShowSemester(lvas));
    }
    public class CalculateSemesterThread extends Thread{
        private IntelligentSemesterPlaner planer = new IntelligentSemesterPlaner();
        private float goalECTS;
        private Semester sem;
        private int year;
        private long simulatedWaitingTime=3000;
        public void setWaiting(long simulatedWaitingTime){
            this.simulatedWaitingTime = simulatedWaitingTime;
        }
        public CalculateSemesterThread(List<MetaLVA> forced, List<MetaLVA> pool,float goalECTS,boolean voIntersect, int year, Semester sem){
            planer.setLVAs(forced, pool);
            this.goalECTS = goalECTS;
            this.sem=sem;
            this.year=year;
        }
        public void start(long simulatedWaitingTime){
            this.simulatedWaitingTime = simulatedWaitingTime;
            super.start();
        }
        @Override
        public void run() {

            ArrayList<MetaLVA> solution = planer.planSemester(goalECTS, year, sem);
            logger.debug("solution: "+solution);
            try {
                logger.debug("sleeping for: " + simulatedWaitingTime);
                sleep(simulatedWaitingTime);
            } catch (InterruptedException ignore) { //simulating long calculation
            }
            setLVAs(solution);

        }
    }
    public class TimeCounter extends Thread{
        boolean running = true;

        @Override
        public void run() {
            while(lvas==null &&running){
                String toDisplay = "Bitte warten!\nDie Berechnung kann einige\nSekunden in Anspruch nehmen.\n\nVerstrichene Zeit: "+(secCounter++)+" Sekunden.";
                logger.debug("display to user: \n"+toDisplay);
                pleaseWait.setText(toDisplay);
                try {
                    this.sleep(1000);
                } catch (InterruptedException ignore) {
                    break;
                }
            }
        }
        @Override
        public void interrupt(){
            running=false;
            super.interrupt();
        }
    }

}