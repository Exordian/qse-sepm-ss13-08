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
public class MainFrame extends JFrame {
    private JButton properties;
    private JButton tab1;
    private JButton tab2;
    private JButton tab3;
    private JButton tab4;
    private JLabel bg;

    public MainFrame() {
        super("Studiums Manager");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //f.setExtendedState(f.getExtendedState() | JFrame.MAXIMIZED_BOTH);

       // this.setLayout(new BorderLayout());
        this.setIconImage(new ImageIcon("src/main/resources/img/icon.jpg").getImage());

        changeBackground(1);
        createPropertiesButton();
        createTabButtons();


        this.setPreferredSize(new Dimension(1200, 728));
        this.setResizable(false);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public void changeBackground(int nmb) {
        try{
            if (bg == null) {
                bg = new JLabel(new ImageIcon(ImageIO.read(new File("src/main/resources/img/cal.jpg"))));
            }
            switch(nmb) {
                case 1:
                    bg.setIcon(new ImageIcon(ImageIO.read(new File("src/main/resources/img/cal.jpg"))));
                    break;
                case 2:
                    bg.setIcon(new ImageIcon(ImageIO.read(new File("src/main/resources/img/tra.jpg"))));
                    break;
                case 3:
                    bg.setIcon(new ImageIcon(ImageIO.read(new File("src/main/resources/img/stud.jpg"))));
                    break;
                case 4:
                    bg.setIcon(new ImageIcon(ImageIO.read(new File("src/main/resources/img/stat.jpg"))));
                    break;
                default:
                    break;
            }
            this.setContentPane(bg);
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
                JOptionPane.showMessageDialog(MainFrame.this, "properties");
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
                MainFrame.this.changeBackground(1);
                //todo
            }
        });

        tab2.setBounds(12,209,55,159);
        tab2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                MainFrame.this.changeBackground(2);
                //todo
            }
        });

        tab3.setBounds(12,369,55,159);
        tab3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                MainFrame.this.changeBackground(3);
                //todo
            }
        });

        tab4.setBounds(12, 526, 55, 159);
        tab4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                MainFrame.this.changeBackground(4);
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
