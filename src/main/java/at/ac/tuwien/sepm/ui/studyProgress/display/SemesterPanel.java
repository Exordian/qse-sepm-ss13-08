package at.ac.tuwien.sepm.ui.studyProgress.display;

import at.ac.tuwien.sepm.entity.LVA;
import at.ac.tuwien.sepm.entity.MetaLVA;
import at.ac.tuwien.sepm.ui.StandardInsidePanel;
import at.ac.tuwien.sepm.ui.metaLva.LvaDisplayPanel;
import at.ac.tuwien.sepm.ui.metaLva.MetaLVADisplayPanel;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Flo
 * Date: 02.06.13
 * Time: 19:24
 * To change this template use File | Settings | File Templates.
 */
public class SemesterPanel extends StandardInsidePanel {
    private JLabel semesterTitle;
    private List<LVA> lvas;
    private LvaDisplayPanel pane;
    private int height;

    public SemesterPanel(int x, int y, String title, ArrayList<LVA> lvas) {
        this.setLayout(null);
        this.setOpaque(false);
        loadFonts();
        this.height=250;
        setBounds(x-51, y, (int) whiteSpace.getWidth(), height);
        initSemesterTitle();
        this.lvas = lvas;
        semesterTitle.setText(title);
        initLvaTable();
    }

    private void initLvaTable() {
        pane = new LvaDisplayPanel(lvas, (int) whiteSpace.getWidth()-100, height);
        pane.setBounds(this.getX() + 50, semesterTitle.getY() + semesterTitle.getHeight() + 5, (int) whiteSpace.getWidth() - 100, height - semesterTitle.getHeight() - 5);
        this.add(pane);
    }

    private void initSemesterTitle() {
        semesterTitle = new JLabel();
        semesterTitle.setFont(standardSmallerTitleFont);
        semesterTitle.setBounds(this.getX() + 50, 0, 300, 35);
        this.add(semesterTitle);
    }

    public void setLvas(List<LVA> lvas) {
        this.lvas = lvas;
        remove(pane);
        initLvaTable();
    }

    public void setSemesterTitle(String title) {
        semesterTitle.setText(title);
        this.repaint();
    }
}
