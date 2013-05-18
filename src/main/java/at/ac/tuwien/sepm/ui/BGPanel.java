package at.ac.tuwien.sepm.ui;

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
public class BGPanel extends JPanel {
    private JButton properties;
    private JButton tab1;
    private JButton tab2;
    private JButton tab3;
    private JButton tab4;
    private JPanel calPanel;
    private JPanel propsPanel;
    private Image image;

    @Autowired
    public BGPanel(CalPanel calPanel, PropsPanel propsPanel) {
        this.setLayout(null);
        this.calPanel = calPanel;
        this.propsPanel = propsPanel;
        changeImage(1);
        createPropertiesButton();
        createTabButtons();
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
    }

    private void changeImage(int nmb) {
        try{
            removeAddedPanels();
            switch(nmb) {
                case 1:
                    image = ImageIO.read(new File("src/main/resources/img/cal.jpg"));
                    this.add(calPanel);
                    break;
                case 2:
                    image = ImageIO.read(new File("src/main/resources/img/tra.jpg"));
                    break;
                case 3:
                    image = ImageIO.read(new File("src/main/resources/img/stud.jpg"));
                    break;
                case 4:
                    image = ImageIO.read(new File("src/main/resources/img/stat.jpg"));
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
                BGPanel.this.add(propsPanel);
                BGPanel.this.repaint();
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
