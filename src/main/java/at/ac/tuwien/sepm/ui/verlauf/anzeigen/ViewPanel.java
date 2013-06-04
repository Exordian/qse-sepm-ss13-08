package at.ac.tuwien.sepm.ui.verlauf.anzeigen;

import at.ac.tuwien.sepm.dao.MetaLvaDao;
import at.ac.tuwien.sepm.entity.MetaLVA;
import at.ac.tuwien.sepm.service.Semester;
import at.ac.tuwien.sepm.service.ServiceException;
import at.ac.tuwien.sepm.ui.StandardInsidePanel;
import at.ac.tuwien.sepm.ui.UI;
import at.ac.tuwien.sepm.ui.verlauf.anzeigen.SemesterPanel;
import org.springframework.beans.factory.annotation.Autowired;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
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
public class ViewPanel extends StandardInsidePanel {
    private JLabel majorName;
    private JButton fwd;
    private JButton bwd;
    private SemesterPanel semester;
    private JList semesterList;
    private MetaLvaDao metaLvaDao;
    @Autowired
    public ViewPanel(MetaLvaDao metaLvaDao) {
        this.metaLvaDao=metaLvaDao;
        this.setLayout(null);
        this.setOpaque(false);
        loadFonts();
        setBounds((int) StudStartCoordinateOfWhiteSpace.getX(), (int) StudStartCoordinateOfWhiteSpace.getY(), (int) whiteSpaceStud.getWidth(), (int) whiteSpaceStud.getHeight());
        initMajorName();
        initSemesterPanel();
        initButtons();
        initSkiplist(6);
        repaint();
        revalidate();
    }

    private void initSkiplist(int anzahl) {
        JLabel semesters = new JLabel("Semester:");
        semesters.setFont(standardSmallerTitleFont);
        semesters.setBounds(50,majorName.getY() + majorName.getHeight() +semester.getHeight()+50,150,25);
        this.add(semesters);

        DefaultListModel listModel = new DefaultListModel();
        for(int i = 1; i <= anzahl; i++) {
            listModel.addElement(i + ". Semester");
        }
        semesterList = new JList(listModel);
        semesterList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                semester.setSemesterTitle((String)semesterList.getSelectedValue());
                //semester.setLvas(metaLvaDao.readByYearSemesterStudyProgress(2013, Semester.S,true));
            }
        });
        semesterList.setBounds(50, semesters.getY() + semesters.getHeight() + 10, 230, (20 * (anzahl)));
        semesterList.setBorder(BorderFactory.createLineBorder(Color.black));
        //man kann semesterlist auch zu einem scrollpanel machen falls benötigt...
        this.add(semesterList);
    }

    private void initButtons() {
        fwd = new JButton();
        bwd = new JButton();

        try {
            bwd.setIcon(new ImageIcon(ImageIO.read(new File("src/main/resources/img/navleft.png"))));
            fwd.setIcon(new ImageIcon(ImageIO.read(new File("src/main/resources/img/navright.png"))));
        } catch (IOException e) {
            e.printStackTrace();
        }

        bwd.setBounds(10, majorName.getY() + majorName.getHeight() + 30+semester.getHeight()/2, 40, 40);
        bwd.setOpaque(false);
        bwd.setContentAreaFilled(false);
        bwd.setBorderPainted(false);
        bwd.setCursor(new Cursor(Cursor.HAND_CURSOR));  //todo change cursor...
        bwd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                semesterList.setSelectedIndex(semesterList.getSelectedIndex()-1);
            }
        });

        fwd.setBounds((int)whiteSpaceStud.getWidth() - 50,majorName.getY() + majorName.getHeight() + 30+semester.getHeight()/2, 40, 40);
        fwd.setOpaque(false);
        fwd.setContentAreaFilled(false);
        fwd.setBorderPainted(false);
        fwd.setCursor(new Cursor(Cursor.HAND_CURSOR));  //todo change cursor...
        fwd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                semesterList.setSelectedIndex(semesterList.getSelectedIndex()+1);
            }
        });
        fwd.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.add(fwd);
        this.add(bwd);
    }

    private void initSemesterPanel() {
        ArrayList<MetaLVA> test = new ArrayList<MetaLVA>();
        semester = new SemesterPanel(this.getX(), majorName.getY() + majorName.getHeight() + 30, "1. Semester", test);
        this.add(semester);
    }

    private void initMajorName() {
        majorName = new JLabel("Bachelor - Teststudium");
        majorName.setFont(standardTitleFont);
        majorName.setBounds(20,0,(int)whiteSpaceStud.getWidth() - 10,50);
        this.add(majorName);
    }
}
