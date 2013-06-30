package at.ac.tuwien.sepm.ui;

import at.ac.tuwien.sepm.service.PropertyService;
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
    private JButton tab4;
    private PlanPanel planningPanel;
    private ViewPanel viewPanel;
    private RegisterExamPanel registerExamPanel;

    private PropertyService propertyService;

    @Autowired
    public StudiesPanel(PropertyService propertyService, PlanPanel planningPanel, ViewPanel viewPanel, RegisterExamPanel registerExamPanel) {
        init();
        this.planningPanel = planningPanel;
        this.viewPanel = viewPanel;
        this.registerExamPanel = registerExamPanel;
        this.propertyService = propertyService;
        add(viewPanel);
        changeImage(1);
        createTabButtons();
        this.repaint();
        this.revalidate();
    }

    private void createTabButtons() {
        tab1 = new JButton();
        tab2 = new JButton();
        tab4 = new JButton();

        ArrayList<JButton> tabs = new ArrayList<JButton>();
        tabs.add(tab1);
        tabs.add(tab2);
        tabs.add(tab4);

        tab1.setBounds(41,30,160,40);
        tab1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                changeImage(1);
                remove(planningPanel);
                remove(registerExamPanel);
                add(viewPanel);
                viewPanel.refresh();
                StudiesPanel.this.validate();
                StudiesPanel.this.repaint();
            }
        });

        tab2.setBounds(41+160,30,160,40);
        tab2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                changeImage(2);
                remove(viewPanel);
                remove(registerExamPanel);
                add(planningPanel);
                planningPanel.refresh();
                StudiesPanel.this.validate();
                StudiesPanel.this.repaint();
            }
        });
        tab4.setBounds(41+160*3+396,30,160,40);
        tab4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(propertyService.getProperty(PropertyService.TISS_USER) == null || propertyService.getProperty(PropertyService.TISS_USER).isEmpty()) {
                    JOptionPane.showMessageDialog(StudiesPanel.this, "Um dieses Modul nutzen zu können müssen Sie die TISS Kennungsdaten unter Einstellungen angeben", "Anmeldungen", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                changeImage(3);
                remove(viewPanel);
                remove(planningPanel);
                add(registerExamPanel);
                registerExamPanel.refresh();
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
                    image = ImageIO.read(ClassLoader.getSystemResource("img/studanz.png"));
                    break;
                case 2:
                    image = ImageIO.read(ClassLoader.getSystemResource("img/studp.png"));
                    break;
                case 3:
                    image = ImageIO.read(ClassLoader.getSystemResource("img/studan.png"));
                    break;
                default:
                    break;
            }
            this.repaint();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void refresh() {
        viewPanel.refresh();
        planningPanel.refresh();
    }
}
