package at.ac.tuwien.sepm.ui;

import at.ac.tuwien.sepm.service.*;
import at.ac.tuwien.sepm.ui.calender.cal.CalMonthGenerator;
import at.ac.tuwien.sepm.ui.calender.cal.CalWeekGenerator;
import at.ac.tuwien.sepm.ui.calender.cal.CalendarInterface;
import at.ac.tuwien.sepm.ui.calender.todo.TodoPanel;
import at.ac.tuwien.sepm.ui.template.PanelTube;
import at.ac.tuwien.sepm.ui.template.WideComboBox;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;

import javax.activation.MimetypesFileTypeMap;
import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    private JButton todayBtn;
    private JButton deleteAllDatesBtn;
    private JLabel month;
    private WideComboBox semester;
    private DefaultComboBoxModel<SemesterComboBoxItem> semesterCbmdl;

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

    private static final String DELETE_ALL_DATES_MESSAGE = "Es werden alle privaten Termine gelöscht.\nLVA-Termine bleiben gespeichert." +
            "\nMöchten Sie fortfahren?";
    private static final String DELETE_ALL_DATES_TITLE = "Termine wirklich löschen?";
    private static final String[] DELETE_ALL_DATES_BUTTON_TEXT = {"Ja", "Nein"};

    private JFileChooser jfc;


    private LVAService lvaService;

    private DateService dateService;

    private boolean showTodo = false;

    private Logger log = LogManager.getLogger(this.getClass().getSimpleName());

    @Autowired
    private ICalendarService iCalendarService;

    @Autowired
    public CalendarPanel(CalMonthGenerator calPanelMonth, CalWeekGenerator calPanelWeek, TodoPanel todoPanel, LVAService lvaService, DateService dateService) {
        init();
        PanelTube.calendarPanel=this;
        this.lvaService=lvaService;
        this.dateService=dateService;
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
        tab2.doClick();    //trolo
        tab1.doClick();    //trolo more
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
        month.setBounds((int)((size.getWidth()/2)-(image.getWidth(null)/2))+5, (int)(size.getHeight()/2-image.getHeight(null)/2)-31, 500, 30);
        month.setForeground(Color.WHITE);
        month.setFont(standardTitleFont);

        semester = new WideComboBox();
        semesterCbmdl = new DefaultComboBoxModel<>();
        semester.setBounds((int)((size.getWidth()/2)-(image.getWidth(null)/2))+5+295, (int)(size.getHeight()/2-image.getHeight(null)/2)+5, 90, 20);
        semester.setFont(standardTextFont);

        todayBtn = new JButton("Heute");
        todayBtn.setBounds((int)(semester.getX() + semester.getWidth() + 2), (int)(size.getHeight()/2-image.getHeight(null)/2)+5, 90, 20);
        todayBtn.setFont(standardButtonFont);
        todayBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                activeView.goToDay(DateTime.now());
                month.setText(activeView.getTimeIntervalInfo().toUpperCase());
            }
        });

        deleteAllDatesBtn = new JButton("Private Termine löschen");
        deleteAllDatesBtn.setBounds((int)(todayBtn.getX() + todayBtn.getWidth() + 2), (int)(size.getHeight()/2-image.getHeight(null)/2)+5, 180, 20);
        deleteAllDatesBtn.setFont(standardButtonFont);
        deleteAllDatesBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int i = JOptionPane.showOptionDialog(new JFrame(),
                            DELETE_ALL_DATES_MESSAGE,
                            DELETE_ALL_DATES_TITLE,
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            DELETE_ALL_DATES_BUTTON_TEXT,
                            DELETE_ALL_DATES_BUTTON_TEXT[1]);

                    if(i == 0) {
                        dateService.deleteAllDates();
                        PanelTube.backgroundPanel.viewInfoText("Termine erfolgreich gelöscht", SmallInfoPanel.Success);
                        calPanelMonth.refresh();
                        calPanelWeek.refresh();
                    }
                } catch (ServiceException e1) {
                    log.error(e1);
                    PanelTube.backgroundPanel.viewInfoText("Es ist ein Fehler beim löschen aufgetreten", SmallInfoPanel.Error);
                }
            }
        });

        this.add(month);
        this.add(semester);
        this.add(todayBtn);
        this.add(deleteAllDatesBtn);
    }

    @PostConstruct
    private void addSemesterActionListener () {
        semester.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    if( ((SemesterComboBoxItem)semesterCbmdl.getSelectedItem()).getSemester() != null) {
                        activeView.goToDay(SemesterDateGenerator.getTimeFrame(((SemesterComboBoxItem)semesterCbmdl.getSelectedItem()).getYear(), ((SemesterComboBoxItem)semesterCbmdl.getSelectedItem()).getSemester()).from());
                        month.setText(activeView.getTimeIntervalInfo().toUpperCase().toUpperCase());
                        semester.setSelectedIndex(0);
                    }
                }
            }
        });

        semester.setModel(semesterCbmdl);
        activeView.goToDay(DateTime.now());
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
                    return "Kalender Dateien (*.ics, *.ifb, *.iFBf, *.ical)";
                }
                return "Calendar files (*.ics, *.ifb, *.iFBf, *.ical)";
            }
        });
        jfc.setFont(standardTextFont);
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

    private void refreshTop() {
        semester.removeAllItems();
        semesterCbmdl.addElement(new SemesterComboBoxItem(0, null));

        boolean winterSem = true;// lvaService.isFirstSemesterAWinterSemester();
        int semesters=0;// = lvaService.numberOfSemestersInStudyProgress();
        int year=DateTime.now().getYear();// = lvaService.firstYearInStudyProgress();
        try{
            winterSem = lvaService.isFirstSemesterAWinterSemester();
            semesters = lvaService.numberOfSemestersInStudyProgress();
            year = lvaService.firstYearInStudyProgress();
        }catch (ServiceException e) {
            log.error(e);
        }
        for (int x = 0; x < semesters; x++) {
            Semester temp = winterSem ? Semester.W : Semester.S;
            semesterCbmdl.addElement(new SemesterComboBoxItem(year, temp));
            if (winterSem) {
                year++;
            }
            winterSem = !winterSem;
        }

    }

    private void createImportButton() {
        importBtn = new JButton("iCalendar importieren");
        importBtn.setBounds(845, 581, 175, 38);
        importBtn.setFont(standardButtonFont);
        importBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                log.debug("import button pressed ...");

                int datesImported = 0;
                if(jfc.showOpenDialog(CalendarPanel.this) == JFileChooser.APPROVE_OPTION) {
                    File file = jfc.getSelectedFile();
                    try {
                        datesImported = iCalendarService.icalImport(file);
                        activeView.refresh();
                    } catch (ServiceException e) {
                        PanelTube.backgroundPanel.viewInfoText(e.getMessage(), SmallInfoPanel.Error);
                        log.error(e);
                        return;
                    }
                } else {
                    return;
                }
                String date = " Termine ";
                if(datesImported == 1) {
                    date = " Termin ";
                }
                PanelTube.backgroundPanel.viewInfoText(datesImported + date + "erfolgreich exportiert.", SmallInfoPanel.Success);
                // PanelTube.backgroundPanel.viewImport();
            }
        });

        this.add(importBtn);
    }

    private void createExportButton() {
        exportBtn = new JButton("Als iCalendar exportieren");
        exportBtn.setBounds(669, 581, 175, 38);
        exportBtn.setFont(standardButtonFont);
        exportBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                log.debug("export button pressed ...");

                int jfcChosen=-1;
                int breakExport=-1;
                boolean existingFileChoosed=false;
                do {
                    existingFileChoosed=false;
                    jfcChosen = jfc.showSaveDialog(CalendarPanel.this);
                    if(jfcChosen == JFileChooser.APPROVE_OPTION) {
                        File file = jfc.getSelectedFile();

                        if(file.exists() && file.isFile()) {
                            existingFileChoosed = true;
                            breakExport = openExistingFileDialog();
                        }
                        if (!file.exists() || breakExport==0) {
                            breakExport = 0;
                            try {
                                iCalendarService.icalExport(file);
                            } catch (ServiceException e) {
                                PanelTube.backgroundPanel.viewInfoText(e.getMessage(), SmallInfoPanel.Error);
                                log.error(e);
                                return;
                            }
                        }
                    } else {
                        return;
                    }
                } while(jfcChosen == JFileChooser.APPROVE_OPTION && breakExport==1);
                PanelTube.backgroundPanel.viewInfoText("Kalender erfolgreich exportiert.", SmallInfoPanel.Success);
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
                    PanelTube.backgroundPanel.viewInfoText("Fehler beim Laden des Kalenders.", SmallInfoPanel.Error);
                    // TODO use info panel
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
                    PanelTube.backgroundPanel.viewInfoText("Fehler beim Laden des Kalenders.", SmallInfoPanel.Error);
                    // TODO use info panel
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
                    image = ImageIO.read(ClassLoader.getSystemResource("img/calw.png"));
                    toggleComponents("show");
                    break;
                case 2:
                    image = ImageIO.read(ClassLoader.getSystemResource("img/calm.png"));
                    toggleComponents("show");
                    break;
                case 3:
                    if (showTodo) {
                        image = ImageIO.read(ClassLoader.getSystemResource("img/caldt.png"));
                    } else {
                        image = ImageIO.read(ClassLoader.getSystemResource("img/caldd.png"));
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
        activeView.refresh();
        refreshTop();
    }

    public void jumpToDate(DateTime anyDateOfWeek) {
        calPanelWeek.goToDay(anyDateOfWeek);
        tab1.doClick();
    }

    private class SemesterComboBoxItem {
        private int year;
        private Semester sem;

        public SemesterComboBoxItem(int year, Semester sem) {
            this.year = year;
            this.sem = sem;
        }

        public int getYear() {
            return year;
        }

        public Semester getSemester() {
            return sem;
        }

        public String toString() {
            if (sem == null) {
                return "Bitte Semester auswählen";
            }
            return sem.toString() + " " + year;
        }
    }
}
