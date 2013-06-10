package at.ac.tuwien.sepm.ui;

import at.ac.tuwien.sepm.ui.lehrangebot.StudienplanPanel;
import at.ac.tuwien.sepm.ui.studyProgress.*;
import at.ac.tuwien.sepm.ui.studyProgress.display.ViewPanel;
import org.springframework.beans.factory.annotation.Autowired;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

@UI
public class StudiesPanel extends StandardInsidePanel {
    private JButton tab1;
    private JButton tab2;
    private JButton tab3;
    private PlanPanel planningPanel;
    private ExportPanel exportPanel;
    private ViewPanel viewPanel;

    @Autowired
    public StudiesPanel(PlanPanel planningPanel, ExportPanel exportPanel, ViewPanel viewPanel) {
        this.planningPanel = planningPanel;
        this.exportPanel = exportPanel;
        this.viewPanel = viewPanel;
        add(viewPanel);

        init();
        changeImage(1);
        createTabButtons();
        this.repaint();
        this.revalidate();
    }

    private void createTabButtons() {
        tab1 = new JButton();
        tab2 = new JButton();
        tab3 = new JButton();

        ArrayList<JButton> tabs = new ArrayList<JButton>();
        tabs.add(tab1);
        tabs.add(tab2);
        tabs.add(tab3);

        tab1.setBounds(41,30,160,40);
        tab1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                changeImage(1);
                remove(planningPanel);
                remove(exportPanel);
                add(viewPanel);
                StudiesPanel.this.validate();
                StudiesPanel.this.repaint();
            }
        });

        tab2.setBounds(41+160,30,160,40);
        tab2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                changeImage(2);
                remove(exportPanel);
                remove(viewPanel);
                add(planningPanel);
                StudiesPanel.this.validate();
                StudiesPanel.this.repaint();
            }
        });

        tab3.setBounds(41+160*2,30,160,40);
        tab3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                changeImage(3);
                add(exportPanel);
                remove(viewPanel);
                remove(planningPanel);
                StudiesPanel.this.validate();
                StudiesPanel.this.repaint();
            }
        });

        for (int i = 0; i < 3; i++) {
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
                default:
                    break;
            }
            this.repaint();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
