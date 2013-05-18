package at.ac.tuwien.sepm.ui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

@UI
public class CalPanel extends JPanel {
    private Image image;
    private JButton tab1;
    private JButton tab2;
    private JButton tab3;
    private JButton tab4;
    private JButton fwd;
    private JButton bwd;

    public CalPanel() {
        this.setLayout(null);
        this.setBounds(160, 122, 932, 519);
        this.setOpaque(false);
        createTabButtons();
        createNavButtons();
        createCalendar();
        changeImage(1);
    }

    private void createCalendar() {
        //todo
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

        fwd.setBounds(887,100, 40, 40);
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


        bwd.setBounds(5, 100, 40, 40);
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

        this.add(fwd);
        this.add(bwd);
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

        tab1.setBounds(5,4,142,36);
        tab1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                changeImage(1);
                //todo
            }
        });

        tab2.setBounds(148,4,142,36);
        tab2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                changeImage(2);
                //todo
            }
        });

        tab3.setBounds(290,4,142,36);
        tab3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                changeImage(3);
                //todo
            }
        });

        tab4.setBounds(785,4,142,36);
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

    private void changeImage(int nmb) {
        try{
            switch(nmb) {
                case 1:
                    image = ImageIO.read(new File("src/main/resources/img/calt.png"));
                    fwd.setVisible(true);
                    bwd.setVisible(true);
                    break;
                case 2:
                    image = ImageIO.read(new File("src/main/resources/img/calw.png"));
                    fwd.setVisible(true);
                    bwd.setVisible(true);
                    break;
                case 3:
                    image = ImageIO.read(new File("src/main/resources/img/calm.png"));
                    fwd.setVisible(true);
                    bwd.setVisible(true);
                    break;
                case 4:
                    image = ImageIO.read(new File("src/main/resources/img/cald.png"));
                    fwd.setVisible(false);
                    bwd.setVisible(false);
                    break;
                default:
                    break;
            }
            this.repaint();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            g.drawImage(image, 0, 0, null);
        }
    }
}
