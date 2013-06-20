package at.ac.tuwien.sepm.ui;

import at.ac.tuwien.sepm.entity.*;
import at.ac.tuwien.sepm.service.RoomFinderService;
import at.ac.tuwien.sepm.service.ServiceException;
import at.ac.tuwien.sepm.ui.calender.ImportPanel;
import at.ac.tuwien.sepm.ui.entityViews.*;
import at.ac.tuwien.sepm.ui.template.PanelTube;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@UI
public class BackgroundPanel extends JPanel {
    private JButton properties;
    private JButton tab1;
    private JButton tab2;
    private JButton tab3;

    private StandardInsidePanel calPanel;
    private StandardInsidePanel studPanel;
    private StandardInsidePanel propsPanel;
    private StandardInsidePanel lehrPanel;
    private ViewMerge viewMerge;
    private ViewDate viewDate;
    private ViewDeadline viewDeadline;
    private ViewLvaDate viewLVAdate;
    private ViewTODO viewTodo;
    private ViewLva viewLva;
    private ViewMetaLva viewMetaLva;
    private ViewModule viewModule;
    private Image image;
    private StandardInsidePanel lastComponent;
    private int lastImage;
    private ImportPanel importPanel;
    private SmallInfoPanel smallInfoPanel;

    @Autowired
    RoomFinderService roomFinderService;

    private Logger log = LogManager.getLogger(this.getClass().getSimpleName());

    @Autowired
    public BackgroundPanel(CalendarPanel calPanel, StudiesPanel studPanel, LehrangebotPanel lehrPanel, SettingsPanel propsPanel, ViewDate viewDate,
                           ViewLvaDate viewLVAdate, ViewTODO viewTodo, ViewDeadline viewDeadline, ViewLva viewLva, ViewMetaLva viewMetaLva,
                           ViewModule viewModule, ImportPanel importPanel, SmallInfoPanel smallInfoPanel,ViewMerge viewMerge) {
        this.setLayout(null);
        PanelTube.backgroundPanel=this;
        this.viewMerge = viewMerge;
        this.importPanel=importPanel;
        this.viewMetaLva=viewMetaLva;
        this.viewModule = viewModule;
        this.smallInfoPanel=smallInfoPanel;
        this.viewLva=viewLva;
        this.lehrPanel=lehrPanel;
        this.calPanel = calPanel;
        this.studPanel = studPanel;
        this.propsPanel = propsPanel;
        this.viewDate = viewDate;
        this.viewLVAdate=viewLVAdate;
        this.viewTodo=viewTodo;
        this.viewDeadline=viewDeadline;
        changeImage(1);
        createPropertiesButton();
        createTabButtons();

        properties.doClick();//todo remove when wizard is implemented

        log.info("Background Panel initialized.");
    }

    //wenn neue entity erzeugt werden soll ->  viewDate(null)
    public void viewDate(DateEntity dateEntity, DateTime dateTime) {
        removeAddedPanels();
        viewDate.setDateEntity(dateEntity, dateTime);
        viewDate.setVisible(true);
        this.add(viewDate);
        this.revalidate();
        this.repaint();
    }

    public void viewMerge(List<MetaLVA> oldMetaLVAs, List<MetaLVA> newMetaLVAs) {
        removeAddedPanels();
        viewMerge.setIntersectingMetaLVAs(oldMetaLVAs, newMetaLVAs);
        viewMerge.setVisible(true);
        this.add(viewMerge);
        this.revalidate();
        this.repaint();
    }

    public void viewLvaDate(LvaDate lvaDate, DateTime dateTime) {
        removeAddedPanels();
        viewLVAdate.setLVADateEntity(lvaDate, dateTime);
        viewLVAdate.setVisible(true);
        this.add(viewLVAdate);
        this.revalidate();
        this.repaint();
    }

    public void viewTodo(Todo todo) {
        removeAddedPanels();
        viewTodo.setTodo(todo);
        viewTodo.setVisible(true);
        this.add(viewTodo);
        this.revalidate();
        this.repaint();
    }

    public void viewDeadline(LvaDate deadline) {
        removeAddedPanels();
        viewDeadline.setDeadline(deadline);
        viewDeadline.setVisible(true);
        this.add(viewDeadline);
        this.revalidate();
        this.repaint();
    }

    public void viewLva(LVA lva) {
        removeAddedPanels();
        viewLva.setLva(lva);
        viewLva.setVisible(true);
        this.add(viewLva);
        this.revalidate();
        this.repaint();
    }

    public void viewMetaLva(MetaLVA metalva) {
        removeAddedPanels();
        viewMetaLva.setMetaLva(metalva);
        viewMetaLva.setVisible(true);
        this.add(viewMetaLva);
        this.revalidate();
        this.repaint();
    }
    public void viewModule(Module module) {
        removeAddedPanels();
        viewModule.setModule(module);
        viewModule.setVisible(true);
        this.add(viewModule);
        this.revalidate();
        this.repaint();
    }

    public void viewImport() {
        removeAddedPanels();
        importPanel.setVisible(true);
        this.add(importPanel);
        this.revalidate();
        this.repaint();
    }

    /*
    *   String s = text im infopanel (max. 75 chars)
    *   int nmb = icon
    *   (1 = SmallInfoPanel.Error,
    *   2 = SmallInfoPanel.Info,
    *   3 = SmallInfoPanel.Warning)
    */
    public void viewInfoText(String s, int nmb) {
        smallInfoPanel.setVisible(true);
        smallInfoPanel.setInfoText(s, nmb);
        this.add(smallInfoPanel);
        this.revalidate();
        this.repaint();
        hideInfoText();
    }


    void hideInfoText() {
        Timer timer = new Timer(5000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                smallInfoPanel.setVisible(false);
                BackgroundPanel.this.remove(smallInfoPanel);
            }
        });
        timer.setRepeats(false);
        timer.start();
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
        this.remove(viewDate);
        this.remove(viewLVAdate);
        this.remove(viewTodo);
        this.remove(viewLva);
        this.remove(viewMetaLva);
        this.remove(viewModule);
        this.remove(viewDeadline);
        this.remove(lehrPanel);
        this.remove(importPanel);
    }

    private void addPanel(StandardInsidePanel c) {
        this.add(c);
        lastComponent = c;
    }

    public void showLastComponent() {
        this.add(lastComponent);
        changeImage(lastImage);
        lastComponent.refresh();
    }

    private void changeImage(int nmb) {
        try{
            removeAddedPanels();
            switch(nmb) {
                case 1:
                    image = ImageIO.read(ClassLoader.getSystemResource("img/cal.jpg"));
                    lastImage = 1;
                    this.addPanel(calPanel);
                    break;
                case 2:
                    image = ImageIO.read(ClassLoader.getSystemResource("img/stud.jpg"));
                    lastImage = 2;
                    this.addPanel(studPanel);
                    break;
                case 3:
                    image = ImageIO.read(ClassLoader.getSystemResource("img/lehr.jpg"));
                    lastImage = 3;
                    this.addPanel(lehrPanel);
                    break;
                case 4:
                    image = ImageIO.read(ClassLoader.getSystemResource("img/settings.jpg"));
                    break;
                default:
                    break;
            }
            this.repaint();
        } catch (IOException e) {
            log.error("Error: " + e.getMessage());
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
                changeImage(4);
                propsPanel.setVisible(true);
                BackgroundPanel.this.add(propsPanel);
                BackgroundPanel.this.repaint();
                BackgroundPanel.this.revalidate();
            }
        });
        this.add(properties);
    }

    private void createTabButtons() {
        tab1 = new JButton();
        tab2 = new JButton();
        tab3 = new JButton();

        ArrayList<JButton> tabs = new ArrayList<JButton>();
        tabs.add(tab1);
        tabs.add(tab2);
        tabs.add(tab3);

        tab1.setBounds(12,50,55,159);
        tab1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                changeImage(1);
                refresh();
                repaint();
                revalidate();
            }
        });

        tab2.setBounds(12,209,55,159);
        tab2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                changeImage(2);
                refresh();
                repaint();
                revalidate();
            }
        });

        tab3.setBounds(12,369,55,159);
        tab3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                changeImage(3);
                refresh();
                repaint();
                revalidate();
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

    public boolean openRoomBrowser(String room) {
        try {
            UIHelper.openURL(roomFinderService.getRoomURL(room));
        } catch (ServiceException e) {
            log.error("could not find room");
            return false;
        }
        return true;
    }

    public void refresh(){
        calPanel.refresh();
        studPanel.refresh();
        lehrPanel.refresh();
        propsPanel.refresh();
    }
}
