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
public class CalPanel extends StandardInsidePanel {
    private JButton tab1;
    private JButton tab2;
    private JButton tab3;
    private JButton fwd;
    private JButton bwd;
    private JButton importBtn;
    private JLabel month;
    private JComboBox semester;
    private TodoPanel todoPanel;

    @Autowired
    public CalPanel(TodoPanel todoPanel) {
        this.todoPanel = todoPanel;
        todoPanel.setVisible(false);
        this.setLayout(null);
        this.setBounds(size);
        this.setOpaque(false);
        createTabButtons();
        createNavButtons();
        createImportButton();
        createCalendar();
        changeImage(1);
        createTop();
    }

    private void createCalendar() {
        //todo
    }

    private void createTop() {
        month = new JLabel("SEPTEMBER 2012");
        month.setBounds((int)((size.getWidth()/2)-(image.getWidth(null)/2))+5, (int)(size.getHeight()/2-image.getHeight(null)/2)-31, 305, 30);
        month.setForeground(Color.WHITE);
        month.setFont(standardTitleFont);

        semester = new JComboBox();
        semester.setBounds((int)(month.getBounds().getX()+month.getBounds().getWidth()), (int)(size.getHeight()/2-image.getHeight(null)/2)-20, 90, 20);
        semester.setFont(standardButtonFont);
        semester.addItem("WS2011");
        semester.addItem("SS2011");
        semester.addItem("WS2012");
        semester.addItem("SS2012");

        this.add(month);
        this.add(semester);
        this.add(todoPanel);
    }

    private void createImportButton() {
        importBtn = new JButton("Importieren");
        importBtn.setBounds(910, 581, 110, 38);
        importBtn.setFont(standardButtonFont);
        importBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //todo
            }
        });

        this.add(importBtn);
    }

    private void createNavButtons() {
        fwd = new JButton();
        bwd = new JButton();

        try {
            bwd.setIcon(new ImageIcon(ImageIO.read(new File("src/main/resources/img/navleft.png"))));
            fwd.setIcon(new ImageIcon(ImageIO.read(new File("src/main/resources/img/navright.png"))));
        } catch (IOException e) {
            e.printStackTrace();
        }

        bwd.setBounds(65, 160, 40, 40);
        bwd.setOpaque(false);
        bwd.setContentAreaFilled(false);
        bwd.setBorderPainted(false);
        bwd.setCursor(new Cursor(Cursor.HAND_CURSOR));
        bwd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //todo
            }
        });

        fwd.setBounds((int)size.getWidth()-65-40,160, 40, 40);
        fwd.setOpaque(false);
        fwd.setContentAreaFilled(false);
        fwd.setBorderPainted(false);
        fwd.setCursor(new Cursor(Cursor.HAND_CURSOR));
        fwd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //todo
            }
        });

        this.add(fwd);
        this.add(bwd);
    }

    private void createTabButtons() {
        tab1 = new JButton();
        tab2 = new JButton();
        tab3 = new JButton();

        ArrayList<JButton> tabs = new ArrayList<JButton>();
        tabs.add(tab1);
        tabs.add(tab2);
        tabs.add(tab3);

        tab1.setBounds(97,63,142,36);
        tab1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                changeImage(1);
                todoPanel.setVisible(false);
                //todo
            }
        });

        tab2.setBounds(97+142,63,142,36);
        tab2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                changeImage(2);
                todoPanel.setVisible(false);
                //todo
            }
        });

        tab3.setBounds(878,63,142,36);
        todoPanel.setBounds(110,110,900,450);
        tab3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                changeImage(3);
                todoPanel.setVisible(true);
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
                    image = ImageIO.read(new File("src/main/resources/img/calw.png"));
                    toggleComponents("show");
                    break;
                case 2:
                    image = ImageIO.read(new File("src/main/resources/img/calm.png"));
                    toggleComponents("show");
                    break;
                case 3:
                    image = ImageIO.read(new File("src/main/resources/img/cald.png"));
                    toggleComponents("show");
                    break;
                default:
                    break;
            }
            this.repaint();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void toggleComponents(String s) {
        if (month != null && semester != null && fwd != null && bwd != null) {
            if (s.equals("show")) {
                month.setVisible(true);
                semester.setVisible(true);
                fwd.setVisible(true);
                bwd.setVisible(true);
            } else if (s.equals("hide")) {
                month.setVisible(false);
                semester.setVisible(false);
                fwd.setVisible(false);
                bwd.setVisible(false);
            } else {
                //troll out loud
            }
        }
    }
}
