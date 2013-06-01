package at.ac.tuwien.sepm.ui.kalender;

import at.ac.tuwien.sepm.ui.StandardInsidePanel;
import at.ac.tuwien.sepm.ui.UI;
import net.miginfocom.swing.MigLayout;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;

/**
 * @author Markus MUTH
 */
@UI
public class CalPanelMonth extends StandardInsidePanel implements CalendarInterface {
    private JPanel[] dom = new JPanel[35];
    private int dow = Day.WEDNESDAY.ordinal();
    private MigLayout layout;

    private Logger log = LogManager.getLogger(this.getClass().getSimpleName());

    public CalPanelMonth() {
        layout = new MigLayout("", "1[]1[]1[]1", "1[]");
        loadFonts();
        setSize((int)whiteSpaceCalendar.getWidth(),(int)whiteSpaceCalendar.getHeight());
        setLocation(CalStartCoordinateOfWhiteSpace);
        this.setLayout(layout);
        this.setVisible(true);
        initPanel();
        this.repaint();
        this.revalidate();
    }

    // TODO calc the start and stop days

    private void initPanel() {
        int startPre=29;
        int stopPre=30;

        int startAct=1;
        int stopAct=31;

        int startPost=1;
        int stopPost=9;

        int[] days = new int[42];
        boolean[] act = new boolean[42];

        String date1 = "VO - Mathe";
        String date2 = "VO - Mathe";
        String date3 = "VO - Mathe";
        String date4 = "UE - Mathe";
        String date5 = "UE - Mathe";

        int d1 = 2;
        int d2 = 9;
        int d3 = 16;
        int d4 = 9;
        int d5 = 13;


        int o=0;
        while(o<days.length) {
            for(int d=startPre; d<=stopPre; d++) {
                days[o] = d;
                log.info("day: " + days[o]);
                o++;
            }
            for(int d=startAct; d<=stopAct; d++) {
                days[o] = d;
                act[o]=true;
                log.info("day: " + days[o]);
                o++;
            }
            for(int d=startPost; d<=stopPost; d++) {
                days[o] = d;
                log.info("day: " + days[o]);
                o++;
            }
        }

        JLabel mo = new JLabel("Montag");
        mo.setMinimumSize(new Dimension(((int)this.getWidth()/7)-1,17));
        mo.setForeground(Color.BLACK);
        mo.setOpaque(true);
        mo.setBackground(Color.WHITE);
        mo.setVerticalAlignment(JLabel.CENTER);
        mo.setHorizontalAlignment(JLabel.CENTER);
        JLabel tu = new JLabel("Dienstag");
        tu.setMinimumSize(new Dimension(((int)this.getWidth()/7)-1,17));
        tu.setForeground(Color.BLACK);
        tu.setOpaque(true);
        tu.setBackground(Color.WHITE);
        tu.setVerticalAlignment(JLabel.CENTER);

        tu.setHorizontalAlignment(JLabel.CENTER);
        JLabel we = new JLabel("Mittwoch");
        we.setMinimumSize(new Dimension(((int)this.getWidth()/7)-1,17));
        we.setForeground(Color.BLACK);
        we.setOpaque(true);
        we.setBackground(Color.WHITE);
        we.setVerticalAlignment(JLabel.CENTER);
        we.setHorizontalAlignment(JLabel.CENTER);
        JLabel th = new JLabel("Donnerstag");
        th.setMinimumSize(new Dimension(((int)this.getWidth()/7)-1,17));
        th.setForeground(Color.BLACK);
        th.setOpaque(true);
        th.setBackground(Color.WHITE);
        th.setVerticalAlignment(JLabel.CENTER);
        th.setHorizontalAlignment(JLabel.CENTER);
        JLabel fr = new JLabel("Freitag");
        fr.setMinimumSize(new Dimension(((int)this.getWidth()/7)-1,17));
        fr.setForeground(Color.BLACK);
        fr.setOpaque(true);
        fr.setBackground(Color.WHITE);
        fr.setVerticalAlignment(JLabel.CENTER);
        fr.setHorizontalAlignment(JLabel.CENTER);
        JLabel sa = new JLabel("Samstag");
        sa.setMinimumSize(new Dimension(((int)this.getWidth()/7)-1,17));
        sa.setForeground(Color.BLACK);
        sa.setOpaque(true);
        sa.setBackground(Color.WHITE);
        sa.setVerticalAlignment(JLabel.CENTER);
        sa.setHorizontalAlignment(JLabel.CENTER);
        JLabel su = new JLabel("Sonntag");
        su.setMinimumSize(new Dimension(((int)this.getWidth()/7)-1,17));
        su.setForeground(Color.BLACK);
        su.setOpaque(true);
        su.setBackground(Color.WHITE);
        su.setVerticalAlignment(JLabel.CENTER);
        su.setHorizontalAlignment(JLabel.CENTER);

        this.add(mo);
        this.add(tu);
        this.add(we);
        this.add(th);
        this.add(fr);
        this.add(sa);
        this.add(su, "wrap");

        for(int i=0; i<dom.length; i++) {
            dom[i] = new JPanel(new MigLayout());
            dom[i].setMinimumSize(new Dimension((this.getWidth()/7)-1,(int)((this.getHeight()/5)-3.4)));
            if(act[i]){
                dom[i].setBackground(Color.WHITE);
            }
            JLabel l = new JLabel("" + days[i]);
            l.setFont(calendarDaysFont);
            dom[i].add(l, "wrap");
        }

        for (int y=0; y<dom.length; y=y+7) {
            JLabel l = new JLabel();
            for(int x=0; x<6; x++) {
                this.add(dom[x + y]);
                log.info("--> " + (x + y));
            }
            this.add(dom[y + 6], "wrap");
            log.info("--> " + (y + 6));
        }

        JLabel l1 = new JLabel(date1);
        JLabel l2 = new JLabel(date2);
        JLabel l3 = new JLabel(date3);
        JLabel l4 = new JLabel(date4);
        JLabel l5 = new JLabel(date5);

        l1.setFont(calendarDatesFont);
        l2.setFont(calendarDatesFont);
        l3.setFont(calendarDatesFont);
        l4.setFont(calendarDatesFont);
        l5.setFont(calendarDatesFont);

        dom[stopPre-startPre+d1].add(l1,"wrap");
        dom[stopPre-startPre+d2].add(l2,"wrap");
        dom[stopPre-startPre+d3].add(l3,"wrap");
        dom[stopPre-startPre+d4].add(l4,"wrap");
        dom[stopPre-startPre+d5].add(l5,"wrap");

        for(int i=0; i<dom.length; i++) {
            dom[i].revalidate();
            dom[i].repaint();
        }

        this.repaint();
        this.revalidate();
    }

    public void addDate() {
        // todo use the DateEntity or LvaDate

        // JLabel l = new JLabel()

        // todo calc the right array-index.
        //

    }

    public void semester() {
        //todo für die jcombobox in der man das semester auswählen kann in calendarpanel
    }

    public void next() {
        //todo für fwd button
    }

    public void last() {
        //todo für bwd button
    }
}
