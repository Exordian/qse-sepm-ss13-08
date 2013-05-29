package at.ac.tuwien.sepm.ui;

import at.ac.tuwien.sepm.ui.semesterPlanning.PlanningPanel;
import org.springframework.beans.factory.annotation.Autowired;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

@UI
public class StudPanel extends StandardInsidePanel {
    private JButton tab1;
    private JButton tab2;
    private JButton tab3;
    private JButton tab4;
    private PlanningPanel planningPanel;
    @Autowired
    public StudPanel(PlanningPanel planningPanel) {
        this.planningPanel = planningPanel;
        this.setLayout(null);
        this.setBounds(size);
        this.setOpaque(false);
        changeImage(1);
        createTabButtons();
    }

    private void createTabButtons() {
        tab1 = new JButton();
        tab2 = new JButton();
        tab3 = new JButton();
        tab4 = new JButton();

        ArrayList<JButton> tabs = new ArrayList<JButton>();
        tabs.add(tab1);
        tabs.add(tab2);
        tabs.add(tab3);
        tabs.add(tab4);

        tab1.setBounds(41,30,160,40);
        tab1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                changeImage(1);
                remove(planningPanel);
                //todo
                revalidate();
                repaint();
            }
        });

        tab2.setBounds(41+160,30,160,40);
        planningPanel.setBounds(size.x/4,size.y+100,size.width,size.height);
        tab2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                changeImage(2);
                //todo remove(...)

                add(planningPanel);
                revalidate();
                repaint();
            }
        });

        tab3.setBounds(41+160*2,30,160,40);
        tab3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                changeImage(3);
                remove(planningPanel);
                //todo
                revalidate();
                repaint();
            }
        });

        tab4.setBounds(916,30,160,40);
        tab4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                changeImage(4);
                remove(planningPanel);
                //todo
                revalidate();
                repaint();
            }
        });

        for (int i = 0; i < 4; i++) {
            tabs.get(i).setCursor(new Cursor(Cursor.HAND_CURSOR));
            tabs.get(i).setOpaque(false);
            tabs.get(i).setContentAreaFilled(false);
            tabs.get(i).setBorderPainted(false);
            this.add(tabs.get(i));
        }
    }

    private void changeImage(int nmb) {
        try{
            switch(nmb) {
                case 1:
                    image = ImageIO.read(ClassLoader.getSystemResource("img/studa.png"));
                    break;
                case 2:
                    image = ImageIO.read(ClassLoader.getSystemResource("img/studp.png"));
                    break;
                case 3:
                    image = ImageIO.read(ClassLoader.getSystemResource("img/stude.png"));
                    break;
                case 4:
                    image = ImageIO.read(ClassLoader.getSystemResource("img/studs.png"));
                    break;
                default:
                    break;
            }
            this.repaint();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
