package at.ac.tuwien.sepm.ui.calender.todo;

import at.ac.tuwien.sepm.entity.LvaDate;
import at.ac.tuwien.sepm.service.LVAService;
import at.ac.tuwien.sepm.service.LvaDateService;
import at.ac.tuwien.sepm.service.ServiceException;
import at.ac.tuwien.sepm.service.impl.ValidationException;
import at.ac.tuwien.sepm.ui.UI;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@UI
@Scope("singleton")
public class DeadlineTable extends JTable {
    Logger logger = LogManager.getLogger(this.getClass().getSimpleName());

    @Autowired
    private LVAService lvaService;

    List<LvaDate> deadlineList;
    int[] colWidth = new int[]{80,80,200,80,50};
    int width = colWidth[0] + colWidth[1] + colWidth[2] + colWidth[3] + colWidth[4];
    DefaultTableModel model;

    private Logger log = LogManager.getLogger(this.getClass().getSimpleName());

    public void init(List<LvaDate> list) {
        model = new DefaultTableModel(new String[] {"LVA", "Name", "Beschreibung", "Deadline", "Abgeschlossen"},0);
        this.setModel(model);
        for(int i = 0; i < colWidth.length; i++) {
            getColumnModel().getColumn(i).setPreferredWidth(colWidth[i]);
        }
        this.setDeadlines(list);
    }

    private void setDeadlines(List<LvaDate> list) {
        this.deadlineList = list;
        for(LvaDate deadline: deadlineList) {
            try {
                model.addRow(new String[] {(lvaService.readById(deadline.getLva())).getMetaLVA().getName(), deadline.getName(), deadline.getDescription(), new SimpleDateFormat("dd.MM.yy HH:mm").format(deadline.getStop().toDate()), deadline.getWasAttendant() ? "Ja" : "Nein"});
            } catch(ValidationException e) {
                logger.error(e.getMessage());
            } catch(ServiceException e) {
                logger.error(e.getMessage());
            }
        }
    }

    public LvaDate getSelectedDeadline() {
        return deadlineList.get(getSelectedRow());
    }

    public void refreshDeadlines(LvaDateService lvaDateService) {
        model.setRowCount(0);
        try {
            this.setDeadlines(lvaDateService.getAllDeadlines());
        } catch (ServiceException e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
    }
}
