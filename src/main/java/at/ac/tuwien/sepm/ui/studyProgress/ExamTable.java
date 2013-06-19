package at.ac.tuwien.sepm.ui.studyProgress;

import at.ac.tuwien.sepm.entity.TissExam;
import at.ac.tuwien.sepm.ui.UI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.text.SimpleDateFormat;
import java.util.List;

@UI
public class ExamTable extends JTable {
    List<TissExam> exams;
    int[] colWidth = new int[]{58,160, 88,94,90};
    int width = colWidth[0]+colWidth[1]+colWidth[2]+colWidth[3]+colWidth[4];
    DefaultTableModel model;

    public ExamTable(int width){
        init(width);
    }

    public ExamTable(List<TissExam> exams, int width){
        this.exams = exams;
        init(width);
        setExams(exams);
    }

    public ExamTable(List<TissExam> exams){
        this.exams = exams;
        init(this.width);
        setExams(exams);
    }

    public int getColWidth(int index){
        return colWidth[index];
    }

    private void init(int width){
        model = new DefaultTableModel(new String[]{"LVA","Prüfung","Modus","Anmeldebeginn","Anmeldeende"},0){
            @Override
            public boolean isCellEditable(int row,int col){
                return false;
            }
        };

        setModel(model);
        //colWidth[3] = Math.max(0,colWidth[3]+(width-this.width));

        for(int i=0;i<5;i++){
            getColumnModel().getColumn(i).setPreferredWidth(colWidth[i]);
        }
    }

    public void setExams(List<TissExam> exams){
        for(TissExam e: exams){
            model.addRow(new String[]{e.getLvanr(), e.getName(), e.getMode(), new SimpleDateFormat("dd.MM.yy HH:mm").format(e.getStartRegistration().toDate()),new SimpleDateFormat("dd.MM.yy HH:mm").format(e.getEndRegistration().toDate())});
        }
        this.exams=exams;
    }

    public void refreshExams(List<TissExam> list) {
        model.setRowCount(0);
        this.setExams(list);
    }

    public TissExam getSelectedExam(){
        return exams.get(getSelectedRow());
    }
}
