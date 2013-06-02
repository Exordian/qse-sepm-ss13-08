package at.ac.tuwien.sepm.ui;

import at.ac.tuwien.sepm.entity.LVA;
import at.ac.tuwien.sepm.entity.LvaDate;
import at.ac.tuwien.sepm.service.LVAService;
import at.ac.tuwien.sepm.service.LvaDateService;
import at.ac.tuwien.sepm.service.ServiceException;
import at.ac.tuwien.sepm.service.TimeFrame;
import at.ac.tuwien.sepm.service.impl.LVAServiceImpl;
import at.ac.tuwien.sepm.service.impl.LvaDateServiceImpl;
import at.ac.tuwien.sepm.service.impl.ValidationException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Lena
 * Date: 31.05.13
 * Time: 20:36
 * To change this template use File | Settings | File Templates.
 */
@UI
public class DeadlineEditorFrame extends JFrame {
    Logger logger = LogManager.getLogger(this.getClass().getSimpleName());

    private JButton done_editing;
    private JLabel nameLabel;
    private JTextField name;
    private JLabel descriptionLabel;
    private JTextField description;
    private JLabel timeLabel;
    private JTextField time;
    private JCheckBox done;

    private DeadlineTable deadlineTable;
    private LvaDateService service;
    private LvaDate toEdit;
    @Autowired
    private LVAService lvaService;
    private List<LVA> LVAs;
    private JTable displayLVAs;
    private DefaultTableModel model;

    public DeadlineEditorFrame() {
        super("Deadline Bearbeiten");
    }

    public void init(DeadlineTable deadlineTable, LvaDateService service) {
        this.deadlineTable = deadlineTable;
        this.service = service;
        //this.toEdit = toEdit;

        this.init();
        this.initLVAs();
        this.addActionListeners();
    }

    public void openWindow(LvaDate toEdit) {
        this.toEdit = toEdit;
        name.setText(toEdit.getName());
        description.setText(toEdit.getDescription());
        time.setText(toEdit.getStop().toString());
        done.setSelected(toEdit.getWasAttendant());
        setLayout(new FlowLayout());
        setAlwaysOnTop(true);
        setLocation(500,300);
        setSize(600,400);
        setVisible(true);
    }

    public void init() {
        done_editing = new JButton("Änderungen speichern");
        nameLabel = new JLabel("Name");
        name = new JTextField();
        descriptionLabel = new JLabel("Beschreibung");
        description = new JTextField();
        timeLabel = new JLabel("Deadline");
        time = new JTextField();
        done = new JCheckBox("Abgeschlossen");
        //done.setSelected(toEdit.getWasAttendant());

        this.add(done_editing);
        this.add(nameLabel);
        this.add(name);
        this.add(descriptionLabel);
        this.add(description);
        this.add(done);

        //lvaService = new LVAServiceImpl();
    }

    public void initLVAs() {
        try {
            int year = DateTime.now().getYear();
            boolean isWinterSemester = (DateTime.now().getMonthOfYear() > 7);
            LVAs = lvaService.readByYearAndSemester(year, isWinterSemester);
        } catch(ServiceException e) {
            logger.error(e.getMessage());
        } catch(ValidationException e) {
            logger.error(e.getMessage());
        }
        model = new DefaultTableModel(new String[] {"LVAs"},0);
        initLVATable();
        displayLVAs = new JTable(model);
        this.add(displayLVAs);
    }

    public void initLVATable() {
        for(LVA lva : LVAs) {
            model.addRow(new String[] {lva.getMetaLVA().getName()});
        }
    }

    public void refreshLVATable() {
        model.setRowCount(0);

        try {
            int year = DateTime.now().getYear();
            boolean isWinterSemester = (DateTime.now().getMonthOfYear() > 7);
            LVAs = lvaService.readByYearAndSemester(year, isWinterSemester);
        } catch(ServiceException e) {
            logger.error(e.getMessage());
        } catch(ValidationException e) {
            logger.error(e.getMessage());
        }
        this.initLVATable();
    }

    public void addActionListeners() {
        done_editing.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DeadlineEditorFrame.this.editPressed();
            }
        });
    }

    public void editPressed() {
        LVA lva = null;
        int selectedRow = displayLVAs.getSelectedRow();
        if(selectedRow < 0) {
            try {
                lva = lvaService.readById(deadlineTable.getSelectedDeadline().getLva());
            } catch (ValidationException e) {
                logger.error(e.getMessage());
            } catch(ServiceException e) {
                logger.error(e.getMessage());
            }
        }
        else {
            lva = LVAs.get(selectedRow);
        }
        String lvaDate_name = name.getText();
        String lvaDate_description = description.getText();
        DateTime lvaDate_time = DateTime.parse(time.getText());
        boolean lvaDate_done = done.isSelected();

        toEdit.setLva(lva.getId());
        toEdit.setName(lvaDate_name);
        toEdit.setDescription(lvaDate_description);
        toEdit.setTime(new TimeFrame(lvaDate_time, lvaDate_time));
        toEdit.setWasAttendant(lvaDate_done);

        try {
            service.update(toEdit);
            deadlineTable.refreshDeadlines(service.getAllDeadlines());
        } catch (ServiceException e) {
            logger.error(e.getMessage());
        } catch (ValidationException e) {
            logger.error(e.getMessage());
        }

        dispose(); //Fenster schließen
    }
}
