package at.ac.tuwien.sepm.ui.studyProgress.display;

import at.ac.tuwien.sepm.entity.LVA;
import at.ac.tuwien.sepm.ui.StandardInsidePanel;
import at.ac.tuwien.sepm.ui.metaLva.LvaDisplayPanel;

import javax.swing.*;
import java.awt.*;
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
    private int width;

    public SemesterPanel(int x, int y) {
        this.setLayout(null);
        this.setOpaque(false);
        loadFonts();
        this.height=380;
        this.width=1014-100;
        setBounds(x, y, width, height);
        initSemesterTitle();
        this.lvas = new ArrayList<LVA>();
        semesterTitle.setText("dummy");
        initLvaTable();
    }

    private void initLvaTable() {
        pane = new LvaDisplayPanel(lvas, width, height);
        pane.setBounds(0 , semesterTitle.getY() + semesterTitle.getHeight() + 5, width, height - semesterTitle.getHeight() - 5);
        this.add(pane);
        pane.setBackground(Color.WHITE);
        pane.getTable().getTableHeader().setBackground(Color.WHITE);
    }

    private void initSemesterTitle() {
        semesterTitle = new JLabel();
        semesterTitle.setFont(standardSmallerTitleFont);
        semesterTitle.setBounds(0, 0, 600, 35);
        this.add(semesterTitle);
    }

    public void setLvas(List<LVA> lvas) {
        pane.refresh(lvas);
    }

    public List<LVA> getLvas() {
        return this.lvas;
    }

    public void setSemesterTitle(String title) {
        semesterTitle.setText(title);
        this.repaint();
    }
}
