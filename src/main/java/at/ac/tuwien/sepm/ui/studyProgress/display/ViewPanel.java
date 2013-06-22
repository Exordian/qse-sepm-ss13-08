package at.ac.tuwien.sepm.ui.studyProgress.display;

import at.ac.tuwien.sepm.entity.LVA;
import at.ac.tuwien.sepm.service.LVAService;
import at.ac.tuwien.sepm.service.PropertyService;
import at.ac.tuwien.sepm.service.ServiceException;
import at.ac.tuwien.sepm.service.impl.ValidationException;
import at.ac.tuwien.sepm.ui.StandardInsidePanel;
import at.ac.tuwien.sepm.ui.UI;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Scheduled;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Flo
 * Date: 01.06.13
 * Time: 19:23
 * To change this template use File | Settings | File Templates.
 */
@UI
@Scope("singleton")
public class ViewPanel extends StandardInsidePanel {
    private JLabel majorName = new JLabel("dummy");
    private JButton fwd;
    private JButton bwd;
    private SemesterPanel semester;
    // private JList semesterList;
    private LVAService service;
    private PropertyService propertyService;

    private SemesterList semesterList;

    //private int semesterAnz = 6;
    //private int currSemester = 1;
    //private int year = 2013;
    //private boolean isWinterSemester = true;

    private Logger log = LogManager.getLogger(this.getClass().getSimpleName());

    @Autowired
    public ViewPanel(LVAService service, PropertyService propertyService) {
        this.propertyService=propertyService;
        this.service=service;
        this.setLayout(null);
        this.setOpaque(false);
        loadFonts();
        setBounds((int) startCoordinateOfWhiteSpace.getX(), (int) startCoordinateOfWhiteSpace.getY(), (int) whiteSpace.getWidth(), (int) whiteSpace.getHeight());
        this.semesterList = new SemesterList();
        setMajorName();
        initMajorName();
        initSemesterPanel();
        initButtons();
        refresh();
        repaint();
        revalidate();
    }

    @Scheduled(fixedDelay = 5000)
    public void refresh() {
        if (getSemesterAnzahl() <= 1) {
            //   bwd.setVisible(false);        //todo remove if bwd and fwd button setcursor is resolved
            //  fwd.setVisible(false);
        } else {
            bwd.setVisible(true);
            fwd.setVisible(true);
        }
        try {
            ArrayList<LVA> temp = new ArrayList<>();
            for (LVA l : service.readByYearAndSemester(semesterList.getCurrentYear(), semesterList.getCurrentSemesterIsWinterSemester())) {
                if (l.isInStudyProgress())
                    temp.add(l);
            }
            semester.setLvas(temp);
            String tempo = semesterList.getCurrentSemesterIsWinterSemester()? "WS" : "SS";
            semester.setSemesterTitle(semesterList.getCurrentSemester() + ". Semester (" + semesterList.getCurrentYear() + ", " + tempo + ")");
        } catch (ServiceException e) {
            log.error(e.getMessage());
        } catch (ValidationException e) {
            log.error(e.getMessage());
        }
    }

    private void initButtons() {
        fwd = new JButton();
        bwd = new JButton();

        try {
            bwd.setIcon(new ImageIcon(ImageIO.read(new File("src/main/resources/img/navleft.png"))));
            fwd.setIcon(new ImageIcon(ImageIO.read(new File("src/main/resources/img/navright.png"))));
        } catch (IOException e) {
            log.error(e.getMessage());
        }

        bwd.setBounds(10, majorName.getY() + majorName.getHeight() + semester.getHeight() / 2, 40, 40);
        bwd.setOpaque(false);
        bwd.setContentAreaFilled(false);
        bwd.setBorderPainted(false);
        bwd.setCursor(new Cursor(Cursor.HAND_CURSOR));
        bwd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                semesterList.last();
                refresh();
            }
        });

        fwd.setBounds((int) whiteSpace.getWidth() - 50,majorName.getY() + majorName.getHeight() +semester.getHeight()/2, 40, 40);
        fwd.setOpaque(false);
        fwd.setContentAreaFilled(false);
        fwd.setBorderPainted(false);
        fwd.setCursor(new Cursor(Cursor.HAND_CURSOR));
        fwd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                semesterList.next();
                refresh();
            }
        });
        this.add(fwd);
        this.add(bwd);
    }

    private void initSemesterPanel() {
        semester = new SemesterPanel(this.getX(), majorName.getY() + majorName.getHeight() + 30, "1. Semester", new ArrayList<LVA>());
        this.add(semester);
    }

    private void initMajorName() {
        majorName.setFont(standardTitleFont);
        majorName.setBounds(20,0,(int) whiteSpace.getWidth() - 10,50);
        this.add(majorName);
    }

    public void setMajorName() {
        if (propertyService.getProperty("user.majorName") != null) {
            majorName.setText(propertyService.getProperty("user.majorName"));
            repaint();
        }
    }

    private int getSemesterAnzahl() {
        try {
            return service.numberOfSemestersInStudyProgress();
        } catch (ServiceException e) {
            log.error(e.getMessage());
            return 0;
        }
    }

    private int getFirstYear() {
        try {
            return service.firstYearInStudyProgress();
        } catch (ServiceException e) {
            log.error(e.getMessage());
            return 0;
        }
    }

    private int getYearNow() {
        try {
            return getFirstYear() + (service.numberOfSemestersInStudyProgress()/2);
        } catch (ServiceException e) {
            log.error(e.getMessage());
            return 0;
        }
    }

    private boolean isFirstSemesterWinter() {
        try {
            return service.isFirstSemesterAWinterSemester();
        } catch (ServiceException e) {
            log.error(e.getMessage());
            return false;
        }
    }

    private class SemesterList {
        private int semesterAnz;
        private boolean currWinterSemester;
        private int currSemester;

        SemesterList() {
            this.semesterAnz = getSemesterAnzahl();
            this.currSemester = semesterAnz;
            this.currWinterSemester = isFirstSemesterWinter();

            for (int i = 1; i  < semesterAnz; i++) {
                this.currWinterSemester =!this.currWinterSemester;
            }
        }

        int getCurrentYear() {
            if (isFirstSemesterWinter()) {
                    return getFirstYear() + (currSemester/2);
            } else {
                if (currWinterSemester) {
                    return getFirstYear() + (currSemester/2) -1;
                } else {
                    return getFirstYear() + (currSemester/2);
                }
            }
        }

        int getCurrentSemester() {
            return currSemester;
        }

        boolean getCurrentSemesterIsWinterSemester() {
            return currWinterSemester;
        }

        void next() {
            if (currSemester < semesterAnz) {
                currSemester++;
                currWinterSemester =!currWinterSemester;
            }
        }

        void last() {
            if (currSemester > 1) {
                currSemester--;
                currWinterSemester =!currWinterSemester;
            }
        }
    }
}
