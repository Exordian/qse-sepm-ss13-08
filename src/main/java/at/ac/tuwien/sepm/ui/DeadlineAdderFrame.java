package at.ac.tuwien.sepm.ui;

import at.ac.tuwien.sepm.entity.LVA;
import at.ac.tuwien.sepm.entity.LvaDate;
import at.ac.tuwien.sepm.entity.LvaDateType;
import at.ac.tuwien.sepm.entity.Todo;
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
import org.joda.time.format.DateTimeFormat;
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
 * Time: 19:58
 * To change this template use File | Settings | File Templates.
 */
@UI
public class DeadlineAdderFrame extends JFrame {

    Logger logger = LogManager.getLogger(this.getClass().getSimpleName());

    private JButton add;
    private JLabel nameLabel;
    private JTextField name;
    private JLabel descriptionLabel;
    private JTextField description;
    private JLabel timeLabel;
    private JTextField time;
    private JCheckBox done;

    private DeadlineTable deadlineTable;
    private LvaDateService service;
    @Autowired
    private LVAService lvaService;
    private List<LVA> LVAs;
    private JTable displayLVAs;
    private DefaultTableModel model;

    public DeadlineAdderFrame() {
        super("Neue Deadline Hinzufügen");
    }

    public void init(DeadlineTable deadlineTable, LvaDateService service) {
        this.deadlineTable = deadlineTable;
        this.service = service;

        this.init();
        this.initLVAs();
        this.addActionListeners();
    }

    public void openWindow() {
        setLayout(new FlowLayout());
        setAlwaysOnTop(true);
        setLocation(500,300);
        setSize(400,600);
        setVisible(true);
    }

    public void init() {
        add = new JButton("Hinzufügen");
        nameLabel = new JLabel("Name");
        name = new JTextField();
        descriptionLabel = new JLabel("Beschreibung");
        description = new JTextField();
        timeLabel = new JLabel("Deadline \n(Bsp:'01.08.2008')");
        time = new JTextField();
        done = new JCheckBox("Abgeschlossen");

        this.add(add);
        this.add(nameLabel);
        this.add(name);
        this.add(descriptionLabel);
        this.add(description);
        this.add(timeLabel);
        this.add(time);
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
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DeadlineAdderFrame.this.addPressed();
            }
        });
    }

    public void addPressed() {
        LVA lva = LVAs.get(displayLVAs.getSelectedRow());
        String deadline_name = name.getText();
        String deadline_description = description.getText();
        DateTime deadline_time = DateTime.parse(time.getText(), DateTimeFormat.forPattern("dd.MM.yyyy"));
        boolean deadline_done = done.isSelected();

        LvaDate toCreate = new LvaDate();
        toCreate.setLva(lva.getId());
        toCreate.setName(deadline_name);
        toCreate.setDescription(deadline_description);
        toCreate.setTime(new TimeFrame(deadline_time, deadline_time));
        toCreate.setType(LvaDateType.DEADLINE);
        toCreate.setWasAttendant(deadline_done);

        try {
            service.create(toCreate);
        } catch (ServiceException e) {
            logger.error(e.getMessage());
        } catch (ValidationException e) {
            logger.error(e.getMessage());
        }

        deadlineTable.addNewDeadline(toCreate);

        this.reset();

    }

    //Felder leeren
    public void reset() {
        name.setText("");
        description.setText("");
        time.setText("");
        done.setSelected(false);
    }

}
