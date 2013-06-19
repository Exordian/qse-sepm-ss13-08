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
    int[] colWidth = new int[]{35,62,30,410,40};
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
        colWidth[3] = Math.max(0,colWidth[3]+(width-this.width));

        for(int i=0;i<5;i++){
            if(i!=3){
                getColumnModel().getColumn(i).setMinWidth(colWidth[i]);
                getColumnModel().getColumn(i).setMaxWidth(colWidth[i]);
            }else{
                getColumnModel().getColumn(i).setPreferredWidth(colWidth[i]);
            }
        }
    }

    public void setExams(List<TissExam> exams){
        //SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yy HH:mm");
        for(TissExam e: exams){
            model.addRow(new String[]{e.getLvanr(), e.getName(), e.getMode(), e.getStartRegistration().toString(),e.getEndRegistration().toString()});
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
