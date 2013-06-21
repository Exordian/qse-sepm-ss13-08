package at.ac.tuwien.sepm.ui.studyProgress;

import at.ac.tuwien.sepm.entity.TissExam;
import at.ac.tuwien.sepm.ui.UI;
import at.ac.tuwien.sepm.ui.template.HintTextField;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

@UI
public class ExamPanel extends JPanel {
    private List<TissExam> allExams;
    private List<TissExam> filteredExams;

    private Logger logger = LogManager.getLogger(this.getClass().getSimpleName());
    private ExamTable table;
    private JTextField searchLvaNr = new HintTextField("LVA");
    private JTextField searchExamName = new HintTextField("Prüfung");
    private JTextField searchMode = new HintTextField("Modus");
    private JTextField searchStartTime = new HintTextField("Anmeldebeginn");
    private JTextField searchEndTime = new HintTextField("Anmeldeende");

    private JPanel searchPanel = new JPanel();

    private JScrollPane pane = new JScrollPane();

    int tWidth;
    public ExamPanel(List<TissExam> exams, int width, int height){
        this.tWidth =width;
        this.allExams = exams;
        filteredExams = exams;
        table = new ExamTable(exams,width);
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger() || (e.getButton() == 3)) {
                    JTable source = ExamPanel.this.getTable();
                    int row = source.rowAtPoint( e.getPoint() );
                    int column = source.columnAtPoint( e.getPoint() );

                    if (! source.isRowSelected(row))
                        source.changeSelection(row, column, false, false);
                }
            }
        });

        int searchHeight = 20;
        add(searchPanel);
        add(pane);

        searchPanel.setLayout(new FlowLayout(0,0,0));//.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
        BoxLayout bl = new BoxLayout(this,BoxLayout.Y_AXIS);
        setLayout(bl);
        searchPanel.add(searchLvaNr);
        searchLvaNr.setPreferredSize(new Dimension(table.getColWidth(0), searchHeight));
        searchPanel.add(searchExamName);
        searchExamName.setPreferredSize(new Dimension(table.getColWidth(1), searchHeight));
        searchPanel.add(searchMode);
        searchMode.setPreferredSize(new Dimension(table.getColWidth(2), searchHeight));
        searchPanel.add(searchStartTime);
        searchStartTime.setPreferredSize(new Dimension(table.getColWidth(3), searchHeight));
        searchPanel.add(searchEndTime);
        searchEndTime.setPreferredSize(new Dimension(table.getColWidth(4), searchHeight));
        pane.setViewportView(table);

        pane.setPreferredSize(new Dimension(table.getPreferredSize().width, height-searchPanel.getHeight()));
        KeyListener listener = new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                logger.debug("searching for: (lvanr: "+ searchLvaNr.getText()+", name:"+ searchExamName.getText()+", mode: " + searchMode.getText()+", start: "+ searchStartTime.getText()+", end: "+ searchEndTime.getText()+")");
                filteredExams = new ArrayList<>();
                for (TissExam m : allExams) {
                    if (m.getLvanr().contains(searchLvaNr.getText()) &&
                            m.getName().contains(searchExamName.getText()) &&
                            m.getMode().contains(searchMode.getText()) &&
                            (m.getStartRegistration().toString().contains(searchStartTime.getText())) &&
                            (m.getEndRegistration().toString().contains(searchEndTime.getText())) ) {
                        filteredExams.add(m);
                    }
                }
                table.refreshExams(filteredExams);
                pane.setViewportView(table);
                revalidate();
                repaint();
            }
        };

        searchLvaNr.addKeyListener(listener);
        searchExamName.addKeyListener(listener);
        searchMode.addKeyListener(listener);
        searchStartTime.addKeyListener(listener);
        searchEndTime.addKeyListener(listener);
    }

    public TissExam getSelectedExam(){
        return table.getSelectedExam();
    }

    public void refresh(List<TissExam> exams) {
        table.refreshExams(exams);
        this.allExams=exams;
    }

    public ExamTable getTable() {
        return table;
    }
}


