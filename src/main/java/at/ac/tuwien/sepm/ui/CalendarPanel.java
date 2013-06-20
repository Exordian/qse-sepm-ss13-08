package at.ac.tuwien.sepm.ui;

import at.ac.tuwien.sepm.service.ICalendarService;
import at.ac.tuwien.sepm.service.ServiceException;
import at.ac.tuwien.sepm.ui.calender.CalMonthGenerator;
import at.ac.tuwien.sepm.ui.calender.CalWeekGenerator;
import at.ac.tuwien.sepm.ui.calender.CalendarInterface;
import at.ac.tuwien.sepm.ui.calender.todo.TodoPanel;
import at.ac.tuwien.sepm.ui.template.PanelTube;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
//import org.joda.time.DateTime;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;

import javax.activation.MimetypesFileTypeMap;
import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
//import java.util.Locale;

@UI
public class CalendarPanel extends StandardInsidePanel {
    private JButton tab1;
    private JButton tab2;
    private JButton tab3;
    private JButton fwd;
    private JButton bwd;
    private JButton importBtn;
    private JButton exportBtn;
    private JLabel month;
    private JComboBox semester;

    static DefaultTableModel mtblCalendar; //Table model
    static JScrollPane stblCalendar; //The scrollpane
    static JPanel pnlCalendar;
    static JTable tblCalendar;

    private CalMonthGenerator calPanelMonth;
    private CalWeekGenerator calPanelWeek;
    private CalendarInterface activeView;
    private TodoPanel todoPanel;

    private static final String OVERWRITE_FILE_MESSAGE = "Die angegebene Datei existiert bereits.\nSoll diese überschrieben werden?";
    private static final String OVERWRITE_FILE_TITLE = "Soll die Datei überschrieben werden?";
    private static final String[] OVERWRITE_FILE_BUTTON_TEXT = {"Ja", "Nein"};
    private JFileChooser jfc;

    private boolean showTodo = false;

    private Logger log = LogManager.getLogger(this.getClass().getSimpleName());

    @Autowired
    private ICalendarService iCalendarService;

    @Autowired
    public CalendarPanel(CalMonthGenerator calPanelMonth, CalWeekGenerator calPanelWeek, TodoPanel todoPanel) {
        init();
        PanelTube.calendarPanel=this;
        this.calPanelMonth=calPanelMonth;
        this.calPanelWeek=calPanelWeek;
        this.activeView=calPanelWeek;
        this.todoPanel=todoPanel;

        add(calPanelWeek);

        createTabButtons();
        createNavButtons();
        createICalFileChooser();
        createExportButton();
        createImportButton();
        changeImage(1);
        createTop();
        this.revalidate();
        this.repaint();
    }

    @PostConstruct
    public void initGenerators() {
        calPanelWeek.init();
        calPanelMonth.init();
    }

    public void showTodo(boolean show) {
        showTodo = show;
        changeImage(3);
    }

    private void createTop() {
        month = new JLabel(activeView.getTimeIntervalInfo().toUpperCase());
        month.setBounds((int)((size.getWidth()/2)-(image.getWidth(null)/2))+5, (int)(size.getHeight()/2-image.getHeight(null)/2)-31, 290, 30);
        month.setForeground(Color.WHITE);
        month.setFont(standardTitleFont);

        semester = new JComboBox();
        semester.setBounds((int)((size.getWidth()/2)-(image.getWidth(null)/2))+5+295, (int)(size.getHeight()/2-image.getHeight(null)/2)+5, 90, 20);
        semester.setFont(standardButtonFont);
        semester.addItem("WS2011");
        semester.addItem("SS2011");
        semester.addItem("WS2012");
        semester.addItem("SS2012");

        this.add(month);
        this.add(semester);
    }

    private void createICalFileChooser() {
        jfc = new JFileChooser();
        jfc.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                if (f==null) {
                    return false;
                }

                MimetypesFileTypeMap mimetypesFileTypeMap = new MimetypesFileTypeMap();
                mimetypesFileTypeMap.addMimeTypes("text/calendar ics iCal ifb iFBf ical");
                String mimeType = mimetypesFileTypeMap.getContentType(f);

                if (f.isDirectory()) {
                    return true;
                } else if(mimeType.equals("text/calendar")) {
                    return true;
                }

                return false;
            }

            @Override
            public String getDescription() {
                if(System.getProperty("user.language").equals("de")) {
                    return "Kalender Dateien";
                }
                return "Calendar files";
            }
        });
    }

    private int openExistingFileDialog () {
        return JOptionPane.showOptionDialog(new JFrame(),
                OVERWRITE_FILE_MESSAGE,
                OVERWRITE_FILE_TITLE,
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                OVERWRITE_FILE_BUTTON_TEXT,
                OVERWRITE_FILE_BUTTON_TEXT[1]);
    }

    private void createImportButton() {
        importBtn = new JButton("iCalendar importieren");
        importBtn.setBounds(845, 581, 175, 38);
        importBtn.setFont(standardButtonFont);
        importBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                log.debug("import button pressed ...");

                if(jfc.showOpenDialog(CalendarPanel.this) == JFileChooser.APPROVE_OPTION) {
                    File file = jfc.getSelectedFile();
                    try {
                        iCalendarService.icalImport(file);
                    } catch (ServiceException e) {
                        // TODO inform user about this
                    }
                }
                // TODO inform user about successful creation of the calendar
            }
        });

        this.add(importBtn);
    }

    private void createExportButton() {
        exportBtn = new JButton("Als iCalendar speichern");
        exportBtn.setBounds(669, 581, 175, 38);
        exportBtn.setFont(standardButtonFont);
        exportBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                log.debug("export button pressed ...");

                int jfcChosen=-1;
                int dialogChosen=-1;
                boolean existingFileChoosed=false;
                do {
                    existingFileChoosed=false;
                    jfcChosen = jfc.showSaveDialog(CalendarPanel.this);
                    if(jfcChosen == JFileChooser.APPROVE_OPTION) {
                        File file = jfc.getSelectedFile();

                        if(file.exists() && file.isFile()) {
                            existingFileChoosed = true;
                            dialogChosen = openExistingFileDialog();
                        }
                        if (!file.exists() || dialogChosen==0) {
                            try {
                                iCalendarService.icalExport(file);
                            } catch (ServiceException e) {
                                // TODO inform user about this
                            }
                        }
                    }
                } while(jfcChosen == JFileChooser.APPROVE_OPTION && dialogChosen==1);
                // TODO inform user about successful creation of the calendar
            }
        });

        this.add(exportBtn);
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
                try {
                    activeView.last();
                    month.setText(activeView.getTimeIntervalInfo().toUpperCase());
                } catch (ServiceException e) {
                    JOptionPane.showMessageDialog(CalendarPanel.this, "Es ist ein Fehler beim Laden des Kalenders aufgetreten.", "Fehler", JOptionPane.ERROR_MESSAGE);
                    month.setText("ERROR");
                }
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
                try {
                    activeView.next();
                    month.setText(activeView.getTimeIntervalInfo().toUpperCase());
                } catch (ServiceException e) {
                    JOptionPane.showMessageDialog(CalendarPanel.this, "Es ist ein Fehler beim Laden des Kalenders aufgetreten.", "Fehler", JOptionPane.ERROR_MESSAGE);
                    month.setText("ERROR");
                }
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

        tab1.setBounds(97, 63, 142, 36);
        tab1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                changeImage(1);
                remove(calPanelMonth);
                remove(todoPanel);
                add(calPanelWeek);
                calPanelWeek.refresh();
                activeView = calPanelWeek;
                month.setText(activeView.getTimeIntervalInfo().toUpperCase());
                calPanelWeek.revalidate();
                calPanelWeek.repaint();

            }
        });

        tab2.setBounds(97+142,63,142,36);
        calPanelMonth.setBounds(size);
        calPanelWeek.setBounds(size);
        tab2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                changeImage(2);
                remove(calPanelWeek);
                remove(todoPanel);
                add(calPanelMonth);
                calPanelMonth.refresh();
                activeView = calPanelMonth;
                month.setText(activeView.getTimeIntervalInfo().toUpperCase());
                calPanelMonth.revalidate();
                calPanelMonth.repaint();
            }
        });

        tab3.setBounds(878, 63, 142, 36);
        todoPanel.setBounds(110, 110, 900, 450);
        tab3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                changeImage(3);
                remove(calPanelMonth);
                remove(calPanelWeek);
                add(todoPanel);
                revalidate();
                repaint();
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
                    if (showTodo) {
                        image = ImageIO.read(new File("src/main/resources/img/caldt.png"));
                    } else {
                        image = ImageIO.read(new File("src/main/resources/img/caldd.png"));
                    }
                    toggleComponents("hide");
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
                importBtn.setVisible(true);
                exportBtn.setVisible(true);
            } else if (s.equals("hide")) {
                month.setVisible(false);
                semester.setVisible(false);
                fwd.setVisible(false);
                bwd.setVisible(false);
                importBtn.setVisible(false);
                exportBtn.setVisible(false);
            } else {
                //troll out loud
            }
        }
    }

    @Override
    public void refresh() {
        calPanelMonth.refresh();
        calPanelWeek.refresh();
    }

    public void jumpToDate(DateTime anyDateOfWeek) {
        calPanelWeek.goToDay(anyDateOfWeek);
        changeImage(1);
        remove(calPanelMonth);
        remove(todoPanel);
        add(calPanelWeek);
        calPanelWeek.refresh();
        activeView = calPanelWeek;
        month.setText(activeView.getTimeIntervalInfo().toUpperCase());
        calPanelWeek.revalidate();
        calPanelWeek.repaint();
    }
}
