package at.ac.tuwien.sepm.ui;

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

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * @author Lena Lenz
 */
@UI
public class TodoEditorFrame extends JFrame {
    Logger logger = LogManager.getLogger(this.getClass().getSimpleName());

    private JButton done_editing;
    private JLabel nameLabel;
    private JTextField name;
    private JLabel descriptionLabel;
    private JTextField description;
    private JCheckBox done;

    private TodoTable todoTable;
    private TodoService service;
    private Todo toEdit;
    @Autowired
    private LVAService lvaService;
    private List<LVA> LVAs;
    private JTable displayLVAs;
    private DefaultTableModel model;

    public TodoEditorFrame() {
        super("Todo Bearbeiten");
    }

    public void init(TodoTable todoTable, TodoService service) {
        this.todoTable = todoTable;
        this.service = service;

        this.init();
        this.initLVAs();
        this.addActionListeners();
    }

    public void openWindow(Todo toEdit) {
        this.toEdit = toEdit;
        name.setText(toEdit.getName());
        description.setText(toEdit.getDescription());
        done.setSelected(toEdit.getDone());

        setLayout(new FlowLayout());
        setAlwaysOnTop(true);
        setLocation(500,300);
        setSize(400,600);
        setVisible(true);
    }

    public void init() {
        done_editing = new JButton("Änderungen speichern");
        nameLabel = new JLabel("Name");
        name = new JTextField();
        descriptionLabel = new JLabel("Beschreibung");
        description = new JTextField();
        done = new JCheckBox("Abgeschlossen");

        this.add(done_editing);
        this.add(nameLabel);
        this.add(name);
        this.add(descriptionLabel);
        this.add(description);
        this.add(done);
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
                TodoEditorFrame.this.editPressed();
            }
        });
    }

    public void editPressed() {
        LVA lva = null;
        int selectedRow = displayLVAs.getSelectedRow();
        if(selectedRow < 0) {
            lva = todoTable.getSelectedTodo().getLva();
        }
        else {
            lva = toEdit.getLva();
        }
        String todo_name = name.getText();
        String todo_description = description.getText();
        boolean todo_done = done.isSelected();

        toEdit.setLva(lva);
        toEdit.setName(todo_name);
        toEdit.setDescription(todo_description);
        toEdit.setDone(todo_done);

        try {
            service.update(toEdit);
            todoTable.refreshTodos(service.getAllTodos());
        } catch (ServiceException e) {
            logger.error(e.getMessage());
        } catch (ValidationException e) {
            logger.error(e.getMessage());
        }

        dispose(); //Fenster schließen
    }

}
