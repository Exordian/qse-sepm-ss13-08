package at.ac.tuwien.sepm.ui.studyProgress;

import at.ac.tuwien.sepm.entity.TissExam;
import at.ac.tuwien.sepm.entity.TissExamState;
import at.ac.tuwien.sepm.ui.UI;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;

@UI
public class ExamTable extends JTable {
    List<TissExam> exams;
    int[] colWidth = new int[]{58,160, 88,94,90};
    int width = colWidth[0]+colWidth[1]+colWidth[2]+colWidth[3]+colWidth[4];
    DefaultTableModel model;

    public ExamTable(int width, boolean pendingTable){
        init(width, pendingTable);
    }

    public ExamTable(List<TissExam> exams, int width, boolean pendingTable){
        this.exams = exams;
        init(width, pendingTable);
        setExams(exams);
    }

    public ExamTable(List<TissExam> exams, boolean pendingTable){
        this.exams = exams;
        init(this.width, pendingTable);
        setExams(exams);
    }

    public int getColWidth(int index){
        return colWidth[index];
    }

    private void init(int width, boolean pendingTable){
        if (pendingTable)
            this.setDefaultRenderer(Object.class, new ExamTableRenderer());
        model = new DefaultTableModel(new String[]{"LVA","Prüfung","Modus","Anmeldebeginn","Anmeldeende", "Status"},0){
            @Override
            public boolean isCellEditable(int row,int col){
                return false;
            }
        };

        setModel(model);
        //colWidth[3] = Math.max(0,colWidth[3]+(width-this.width));
        this.removeColumn(getColumnModel().getColumn(5));


        for(int i=0;i<5;i++){
            getColumnModel().getColumn(i).setPreferredWidth(colWidth[i]);
        }
    }

    public void setExams(List<TissExam> exams){
        for(TissExam e: exams){
            model.addRow(new String[]{e.getLvanr(), e.getName(), e.getMode(), new SimpleDateFormat("dd.MM.yy HH:mm").format(e.getStartRegistration().toDate()), new SimpleDateFormat("dd.MM.yy HH:mm").format(e.getEndRegistration().toDate()), (e.getTissExamState() != null)? e.getTissExamState().toString() : TissExamState.NOT_REGISTERED.toString()});
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

    public class ExamTableRenderer extends DefaultTableCellRenderer {
        public Component getTableCellRendererComponent(JTable table,Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component cell= super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            if(((String)table.getModel().getValueAt(row, 5)).startsWith("NOT")) {
                setForeground(new Color(225, 138, 7));
            } else {
                setForeground(new Color(0, 99, 0));
            }
            return cell;
        }
    }



}
