package at.ac.tuwien.sepm.ui;

import at.ac.tuwien.sepm.entity.LVA;
import at.ac.tuwien.sepm.entity.LVA;
import at.ac.tuwien.sepm.entity.Todo;
import at.ac.tuwien.sepm.service.LVAService;
import at.ac.tuwien.sepm.service.ServiceException;
import at.ac.tuwien.sepm.service.TodoService;
import at.ac.tuwien.sepm.service.impl.LVAServiceImpl;
import at.ac.tuwien.sepm.service.impl.TodoServiceImpl;
import at.ac.tuwien.sepm.service.impl.ValidationException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.*;
import javax.swing.plaf.basic.DefaultMenuLayout;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * @author Lena Lenz
 */
@UI
public class TodoAdderFrame extends JFrame {

    Logger logger = LogManager.getLogger(this.getClass().getSimpleName());

    private JButton add;
    private JLabel nameLabel;
    private JTextField name;
    private JLabel descriptionLabel;
    private JTextField description;
    private JCheckBox done;
    
    private TodoTable todoTable;
    private TodoService service;
    @Autowired
    private LVAService lvaService;
    private List<LVA> LVAs;
    private JTable displayLVAs;
    private DefaultTableModel model;

    public TodoAdderFrame() {
        super("Neues Todo Hinzufügen");
    }

    public void init(TodoTable todoTable, TodoService service) {
        this.todoTable = todoTable;
        this.service = service;
        //this.lvaService = lvaService;
        
        this.init();
        this.initLVAs();
        this.addActionListeners();
    }

    public void openWindow() {
        setLayout(new FlowLayout());
        setAlwaysOnTop(true);
        setLocation(500,300);
        setSize(600,400);
        setVisible(true);
    }
    
    public void init() {
        add = new JButton("Hinzufügen");
        nameLabel = new JLabel("Name");
        name = new JTextField();
        name.setSize(30,20);
        descriptionLabel = new JLabel("Beschreibung");
        description = new JTextField();
        description.setMinimumSize(new Dimension(60,30));
        done = new JCheckBox("Abgeschlossen");

        this.add(add);
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
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TodoAdderFrame.this.addPressed();
            }
        });
    }
    
    public void addPressed() {
        LVA lva = LVAs.get(displayLVAs.getSelectedRow());
        String todo_name = name.getText();
        String todo_description = description.getText();
        boolean todo_done = done.isSelected();

        Todo toCreate = new Todo();
        toCreate.setLva(lva);
        toCreate.setName(todo_name);
        toCreate.setDescription(todo_description);
        toCreate.setDone(todo_done);

        try {
            service.create(toCreate);
        } catch (ServiceException e) {
            logger.error(e.getMessage());
        } catch (ValidationException e) {
            logger.error(e.getMessage());
        }

        todoTable.addNewTodo(toCreate);

        this.reset();
    }

    //Felder leeren
    public void reset() {
        name.setText("");
        description.setText("");
        done.setSelected(false);
    }

}
