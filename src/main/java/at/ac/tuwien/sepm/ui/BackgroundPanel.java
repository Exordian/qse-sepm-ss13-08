package at.ac.tuwien.sepm.ui;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

@UI
public class BackgroundPanel extends JPanel {
    private JButton properties;
    private JButton tab1;
    private JButton tab2;
    private JButton tab3;
    private JButton tab4;
    private JPanel calPanel;
    private JPanel studPanel;
    private JPanel propsPanel;
    private Image image;
    private Component lastComponent;

    private Logger log = LogManager.getLogger(this.getClass().getSimpleName());

    @Autowired
    public BackgroundPanel(CalendarPanel calPanel, StudiesPanel studPanel, PropertiessPanel propsPanel) {
        this.setLayout(null);
        this.calPanel = calPanel;
        this.propsPanel = propsPanel;
        propsPanel.setBgPanel(this);
        this.studPanel = studPanel;
        changeImage(1);
        createPropertiesButton();
        createTabButtons();
        log.info("Background Panel initialized.");
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            g.drawImage(image, 0, 0, null);
        }
    }

    private void removeAddedPanels() {
        this.remove(propsPanel);
        this.remove(calPanel);
        this.remove(studPanel);
    }

    private void addPanel(Component c) {
        this.add(c);
        lastComponent = c;
    }

    public void showLastComponent() {
        this.add(lastComponent);
    }

    private void changeImage(int nmb) {
        try{
            removeAddedPanels();
            switch(nmb) {
                case 1:
                    image = ImageIO.read(ClassLoader.getSystemResource("img/cal.jpg"));
                    this.addPanel(calPanel);
                    break;
                case 2:
                    image = ImageIO.read(ClassLoader.getSystemResource("img/tra.jpg"));
                    break;
                case 3:
                    image = ImageIO.read(ClassLoader.getSystemResource("img/stud.jpg"));
                    this.addPanel(studPanel);
                    break;
                case 4:
                    image = ImageIO.read(ClassLoader.getSystemResource("img/stat.jpg"));
                    break;
                default:
                    break;
            }
            this.repaint();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createPropertiesButton() {
        properties = new JButton();
        properties.setBounds(1138,13,38,38);
        properties.setOpaque(false);
        properties.setContentAreaFilled(false);
        properties.setBorderPainted(false);
        properties.setCursor(new Cursor(Cursor.HAND_CURSOR));
        properties.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                removeAddedPanels();
                propsPanel.setVisible(true);
                BackgroundPanel.this.add(propsPanel);
                BackgroundPanel.this.repaint();
            }
        });
        this.add(properties);
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

        tab1.setBounds(12,50,55,159);
        tab1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                changeImage(1);
                //todo
            }
        });

        tab2.setBounds(12,209,55,159);
        tab2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                changeImage(2);
                //todo
            }
        });

        tab3.setBounds(12,369,55,159);
        tab3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                changeImage(3);
                //todo
            }
        });

        tab4.setBounds(12, 526, 55, 159);
        tab4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                changeImage(4);
                //todo
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
}
