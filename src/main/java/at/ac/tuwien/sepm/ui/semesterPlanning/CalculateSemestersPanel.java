package at.ac.tuwien.sepm.ui.semesterPlanning;

import at.ac.tuwien.sepm.entity.MetaLVA;
import at.ac.tuwien.sepm.service.Semester;
import at.ac.tuwien.sepm.service.semesterPlaning.IntelligentSemesterPlaner;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Author: Georg Plaz
 */
public class CalculateSemestersPanel extends ChainedPanel{
    private IntelligentSemesterPlaner planer = new IntelligentSemesterPlaner();
    private ArrayList<MetaLVA> lvas;
    private MetaLVATable table;
    //private static final String pleaseWait= "Bitte warten!\n\n Verstrichene Zeit:";
    private JTextArea pleaseWait = new JTextArea();
    //private JTextArea pleaseWait = new JTextArea("Verstrichene Wartezeit: !");
    private JButton backButton = new JButton("zurueck");
    private int secCounter;

    public CalculateSemestersPanel(ArrayList<MetaLVA> forced, ArrayList<MetaLVA> pool, float goalECTS, boolean voIntersect, int year, Semester sem, long waiting) {
        add(pleaseWait);
        pleaseWait.setEditable(false);
        CalculateSemesterThread t= new CalculateSemesterThread(forced, pool, goalECTS, voIntersect,year,sem);

        add(backButton);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                back();
            }
        });
        TimeCounter counter = new TimeCounter();
        counter.start();
        t.start(waiting);
    }



    public void setLVAs(ArrayList<MetaLVA> lvas){
        this.lvas = lvas;
        remove(pleaseWait);
        JScrollPane p = new JScrollPane();
        MetaLVATable t = new MetaLVATable(lvas);
        //setPreferredSize(t.getPreferredSize());
        JScrollPane pane = new JScrollPane(t);
        pane.setPreferredSize(new Dimension(200,200));
        add(pane);
        revalidate();
    }
    public class CalculateSemesterThread extends Thread{
        private IntelligentSemesterPlaner planer = new IntelligentSemesterPlaner();
        private float goalECTS;
        private Semester sem;
        private int year;
        private long simulatedWaitingTime=3000;
        public CalculateSemesterThread(ArrayList<MetaLVA> forced, ArrayList<MetaLVA> pool,float goalECTS,boolean voIntersect, int year, Semester sem){
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
            System.out.println("solution: "+solution);
            try {
                System.out.println("sleeping for: "+simulatedWaitingTime);
                sleep(simulatedWaitingTime);
            } catch (InterruptedException ignore) { //simulating long calculation
            }
            CalculateSemestersPanel.this.setLVAs(solution);

        }
    }
    public class TimeCounter extends Thread{


        @Override
        public void run() {
            while(lvas==null){
                pleaseWait.setText("Bitte warten!\nDie Berechnung kann einige\nSekunden in Anspruch nehmen.\n\nVerstrichene Zeit: "+(secCounter++)+" Sekunden.");
                try {
                    this.sleep(1000);
                } catch (InterruptedException ignore) {
                }
            }
        }
    }

}