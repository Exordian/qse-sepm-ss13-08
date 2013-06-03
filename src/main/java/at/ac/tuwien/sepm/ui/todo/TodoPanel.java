package at.ac.tuwien.sepm.ui.todo;

import at.ac.tuwien.sepm.entity.LvaDate;
import at.ac.tuwien.sepm.entity.Todo;
import at.ac.tuwien.sepm.service.LvaDateService;
import at.ac.tuwien.sepm.service.ServiceException;
import at.ac.tuwien.sepm.service.TodoService;
import at.ac.tuwien.sepm.service.impl.ValidationException;
import at.ac.tuwien.sepm.ui.StandardInsidePanel;
import at.ac.tuwien.sepm.ui.UI;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * @author Lena Lenz
 */

@UI
public class TodoPanel extends StandardInsidePanel {

    Logger logger = LogManager.getLogger(this.getClass().getSimpleName());

    @Autowired
    private TodoService service;
    private LvaDateService serviceDeadlines;
    private TodoTable todoTable;
    private DeadlineTable deadlineTable;

    private TodoAdderFrame todoAdderFrame;
    private TodoEditorFrame todoEditorFrame;
    private DeadlineAdderFrame deadlineAdderFrame;
    private DeadlineEditorFrame deadlineEditorFrame;

    private JButton add;

    private JButton showTODO;
    private JButton showDeadline;

    private boolean todoTableShown = false;


    @Autowired
    public TodoPanel(TodoService todoService, LvaDateService lvaDateService, TodoTable todoTable, DeadlineTable deadlineTable, TodoAdderFrame todoAdderFrame, TodoEditorFrame todoEditorFrame, DeadlineAdderFrame deadlineAdderFrame, DeadlineEditorFrame deadlineEditorFrame) {
        init();
        this.service = todoService;
        this.serviceDeadlines = lvaDateService;
        this.todoAdderFrame = todoAdderFrame;
        this.todoEditorFrame = todoEditorFrame;

        this.deadlineAdderFrame = deadlineAdderFrame;
        this.deadlineEditorFrame = deadlineEditorFrame;

        initJTables(todoTable, deadlineTable);
        addButtons();
        addActionListeners();
    }

    public void initJTables(TodoTable todoTable, DeadlineTable deadlineTable) {
        try {
            this.todoTable = todoTable;
            todoTable.init(service.getAllTodos());
            this.deadlineTable = deadlineTable;
            deadlineTable.init(serviceDeadlines.getAllDeadlines());
        } catch(ServiceException e) {
            logger.error(e.getMessage());
        }

        todoTable.setBounds(0,66,(int)whiteSpaceCalendar.getX() + (int)whiteSpaceCalendar.getWidth(),273);
        todoTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                int r = TodoPanel.this.todoTable.rowAtPoint(e.getPoint());
                if (r >= 0 && r < TodoPanel.this.todoTable.getRowCount()) {
                    TodoPanel.this.todoTable.setRowSelectionInterval(r, r);
                } else {
                    TodoPanel.this.todoTable.clearSelection();
                }

                int rowindex = TodoPanel.this.todoTable.getSelectedRow();
                if (rowindex < 0)
                    return;
                if (e.isPopupTrigger() && e.getComponent() instanceof JTable ) {
                    JPopupMenu popup = new PopUpMenu();
                    popup.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });

        this.add(deadlineTable);
        deadlineTable.setBounds(0, 66, (int) whiteSpaceCalendar.getX() + (int) whiteSpaceCalendar.getWidth(), 273);
        deadlineTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                int r = TodoPanel.this.deadlineTable.rowAtPoint(e.getPoint());
                if (r >= 0 && r < TodoPanel.this.deadlineTable.getRowCount()) {
                    TodoPanel.this.deadlineTable.setRowSelectionInterval(r, r);
                } else {
                    TodoPanel.this.deadlineTable.clearSelection();
                }

                int rowindex = TodoPanel.this.deadlineTable.getSelectedRow();
                if (rowindex < 0)
                    return;
                if (e.isPopupTrigger() && e.getComponent() instanceof JTable ) {
                    JPopupMenu popup = new PopUpMenu();
                    popup.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });


        this.todoAdderFrame.init(todoTable, service);
        this.todoEditorFrame.init(todoTable, service);
        this.deadlineAdderFrame.init(deadlineTable, serviceDeadlines);
        this.deadlineEditorFrame.init(deadlineTable, serviceDeadlines);
    }

    private class PopUpMenu extends JPopupMenu {
        private JMenuItem newDate;

        public PopUpMenu(){
            newDate = new JMenuItem("Bearbeiten");
            add(newDate);
            addActionListeners();
        }

        private void addActionListeners() {
            newDate.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {}
                @Override
                public void mousePressed(MouseEvent e) {}

                @Override
                public void mouseReleased(MouseEvent e) {
                    if(todoTableShown) {
                        TodoPanel.this.EditTodoPressed(); //todo insert real edit
                    } else {
                        TodoPanel.this.EditDeadlinePressed();   //todo insert real edit
                    }
                }
                @Override
                public void mouseEntered(MouseEvent e) {}
                @Override
                public void mouseExited(MouseEvent e) {}
            });
        }
    }

    public void addButtons() {
        add = new JButton("Hinzufügen");
        add.setFont(standardButtonFont);
        add.setBounds((int)whiteSpaceCalendar.getWidth()/2-75, todoTable.getY() + todoTable.getHeight() + 20+50, 150,30);
        this.add(add);

        showTODO=new JButton();
        showTODO.setBounds(469,22,122,29);
        showTODO.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                changeTables();
                todoTableShown=true;
            }
        });
        showTODO.setCursor(new Cursor(Cursor.HAND_CURSOR));
        showTODO.setOpaque(false);
        showTODO.setContentAreaFilled(false);
        showTODO.setBorderPainted(false);
        this.add(showTODO);

        showDeadline = new JButton();
        showDeadline.setBounds(264,21,159,29);
        showDeadline.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                changeTables();
                todoTableShown=false;
            }
        });
        showDeadline.setCursor(new Cursor(Cursor.HAND_CURSOR));
        showDeadline.setOpaque(false);
        showDeadline.setContentAreaFilled(false);
        showDeadline.setBorderPainted(false);
        this.add(showDeadline);
    }

    private void changeTables() {
        if (todoTableShown) {
            this.remove(todoTable);
            this.add(deadlineTable);
        } else {
            this.add(todoTable);
            this.remove(deadlineTable);
        }
        this.repaint();
        this.revalidate();
    }

    public void addActionListeners() {
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (todoTableShown) {
                    TodoPanel.this.AddTodoPressed();  //todo insert real create
                } else {
                    TodoPanel.this.AddDeadlinePressed();  //todo insert real create
                }
            }
        });
    }

    public void AddTodoPressed() {
        todoAdderFrame.refreshLVATable();
        todoAdderFrame.openWindow();
        //this.todoAdderFrame.init(todoTable, service);
    }

    public void EditTodoPressed() {
        this.todoEditorFrame.refreshLVATable();
        Todo toEdit = todoTable.getSelectedTodo();
        this.todoEditorFrame.openWindow(toEdit);
    }

    public void DeleteTodoPressed() {
        Todo toDelete = todoTable.getSelectedTodo();
        try {
            service.delete(toDelete.getId());
            logger.debug("deleting todo with id = "+ toDelete.getId());
            todoTable.removeSelectedTodo();
        } catch(ValidationException e) {
            logger.error(e.getMessage());
        } catch(ServiceException e) {
            logger.error(e.getMessage());
        }
    }

    public void AddDeadlinePressed() {
        //this.deadlineAdderFrame.init(deadlineTable, serviceDeadlines);
        this.deadlineAdderFrame.refreshLVATable();
        this.deadlineAdderFrame.openWindow();

    }

    public void EditDeadlinePressed() {
        //LvaDate toEdit = deadlineTable.getSelectedDeadline();
        //this.deadlineEditorFrame.init(deadlineTable, serviceDeadlines, toEdit);
        this.deadlineEditorFrame.refreshLVATable();
        LvaDate toEdit = deadlineTable.getSelectedDeadline();
        this.deadlineEditorFrame.openWindow(toEdit);

    }

    public void DeleteDeadlinePressed() {
        LvaDate toDelete = deadlineTable.getSelectedDeadline();
        try {
            serviceDeadlines.delete(toDelete.getId());
            logger.debug("deleting Deadline with id =" + toDelete.getId());
            deadlineTable.removeSelectedDeadline();
        } catch (ServiceException e) {
            logger.error(e.getMessage());
        } catch (ValidationException e) {
            logger.error(e.getMessage());
        }
    }
}
