package at.ac.tuwien.sepm.ui.todo;

import at.ac.tuwien.sepm.entity.LvaDate;
import at.ac.tuwien.sepm.entity.Todo;
import at.ac.tuwien.sepm.service.LvaDateService;
import at.ac.tuwien.sepm.service.ServiceException;
import at.ac.tuwien.sepm.service.TodoService;
import at.ac.tuwien.sepm.service.impl.ValidationException;
import at.ac.tuwien.sepm.ui.BGPanelHelper;
import at.ac.tuwien.sepm.ui.BackgroundPanel;
import at.ac.tuwien.sepm.ui.EntityViews.ViewTODO;
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
    private TodoService service;
    private LvaDateService serviceDeadlines;
    private TodoTable todoTable;
    private DeadlineTable deadlineTable;
    private JButton add;
    private JButton showTODO;
    private JButton showDeadline;
    private boolean todoTableShown = true;

    @Autowired
    public TodoPanel(TodoService todoService, LvaDateService lvaDateService, TodoTable todoTable, DeadlineTable deadlineTable) {
        init();
        this.service = todoService;
        this.serviceDeadlines = lvaDateService;
        this.todoTable = todoTable;
        this.deadlineTable = deadlineTable;
        initJTables();
        addButtons();
        changeTables();
    }

    public void initJTables() {
        try {
            todoTable.init(service.getAllTodos());
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
                if (e.isPopupTrigger() && e.getComponent() instanceof JTable) {
                    JPopupMenu popup = new PopUpMenu();
                    popup.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });
    }

    private class PopUpMenu extends JPopupMenu {
        private JMenuItem edit;

        public PopUpMenu(){
            edit = new JMenuItem("Bearbeiten");
            add(edit);
            addActionListeners();
        }

        private void addActionListeners() {
            edit.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {}
                @Override
                public void mousePressed(MouseEvent e) {}

                @Override
                public void mouseReleased(MouseEvent e) {
                    if(todoTableShown) {
                        BGPanelHelper.backgroundPanel.viewTodo(todoTable.getSelectedTodo());
                    } else {
                        BGPanelHelper.backgroundPanel.viewDeadline(deadlineTable.getSelectedDeadline());
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
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (todoTableShown) {
                    BGPanelHelper.backgroundPanel.viewTodo(null);
                } else {
                    BGPanelHelper.backgroundPanel.viewDeadline(null);
                }
            }
        });
        this.add(add);

        showTODO=new JButton();
        showTODO.setBounds(469,22,122,29);
        showTODO.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                todoTableShown=true;
                changeTables();
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
                todoTableShown=false;
                changeTables();
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
            this.add(todoTable);
            this.remove(deadlineTable);
        } else {
            this.remove(todoTable);
            this.add(deadlineTable);
        }
        this.repaint();
        this.revalidate();
    }
}
