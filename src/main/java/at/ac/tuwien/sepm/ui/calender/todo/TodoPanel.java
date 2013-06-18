package at.ac.tuwien.sepm.ui.calender.todo;

import at.ac.tuwien.sepm.service.LvaDateService;
import at.ac.tuwien.sepm.service.ServiceException;
import at.ac.tuwien.sepm.service.TodoService;
import at.ac.tuwien.sepm.ui.template.PanelTube;
import at.ac.tuwien.sepm.ui.StandardInsidePanel;
import at.ac.tuwien.sepm.ui.UI;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Scheduled;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * @author Lena Lenz
 */

@UI
@Scope("singleton")
public class TodoPanel extends StandardInsidePanel {
    Logger logger = LogManager.getLogger(this.getClass().getSimpleName());
    private TodoService todoService;
    private LvaDateService serviceDeadlines;
    private TodoTable todoTable;
    private DeadlineTable deadlineTable;
    private JButton add;
    private JButton showTODO;
    private JButton showDeadline;
    private boolean showTodo = false;

    private JScrollPane pane = new JScrollPane();

    @Autowired
    public TodoPanel(TodoService todoService, LvaDateService lvaDateService, TodoTable todoTable, DeadlineTable deadlineTable) {
        init();
        this.todoService = todoService;
        this.serviceDeadlines = lvaDateService;
        this.todoTable = todoTable;
        this.deadlineTable = deadlineTable;
        initJTables();
        addButtons();
    }

    public void initJTables() {
        try {
            todoTable.init(todoService.getAllTodos());
            deadlineTable.init(serviceDeadlines.getAllDeadlines());
        } catch(ServiceException e) {
            logger.error(e.getMessage());
        }

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

        this.add(pane);
        pane.setBounds(0, 64, (int) whiteSpaceCalendar.getWidth() - 7, 322);
        todoTable.setBounds(0,64,(int)whiteSpaceCalendar.getWidth()-7,322);

        pane.getViewport().setBackground(Color.WHITE);
        todoTable.getTableHeader().setBackground(Color.WHITE);
        deadlineTable.getTableHeader().setBackground(Color.WHITE);

        deadlineTable.setBounds(0, 64,(int) whiteSpaceCalendar.getWidth()-7, 322);
        pane.setViewportView(deadlineTable);

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
            edit.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {}
                @Override
                public void mousePressed(MouseEvent e) {}

                @Override
                public void mouseReleased(MouseEvent e) {
                    if(showTodo) {
                        PanelTube.backgroundPanel.viewTodo(todoTable.getSelectedTodo());
                    } else {
                        PanelTube.backgroundPanel.viewDeadline(deadlineTable.getSelectedDeadline());
                    }
                }
                @Override
                public void mouseEntered(MouseEvent e) {}
                @Override
                public void mouseExited(MouseEvent e) {}
            });
            add(edit);
        }
    }

    public void addButtons() {
        add = new JButton("Hinzufügen");
        add.setFont(standardButtonFont);
        add.setBounds((int)whiteSpaceCalendar.getWidth()/2-75, todoTable.getY() + todoTable.getHeight() + 20, 150,30);
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (showTodo) {
                    PanelTube.backgroundPanel.viewTodo(null);
                } else {
                    PanelTube.backgroundPanel.viewDeadline(null);
                }
            }
        });
        this.add(add);

        showTODO=new JButton();
        showTODO.setBounds(469,22,122,29);
        showTODO.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                showTodo = true;
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
                showTodo = false;
                changeTables();
            }
        });
        showDeadline.setCursor(new Cursor(Cursor.HAND_CURSOR));
        showDeadline.setOpaque(false);
        showDeadline.setContentAreaFilled(false);
        showDeadline.setBorderPainted(false);
        this.add(showDeadline);
    }

    @Scheduled(fixedDelay = 5000)
    public void refresh() {
        if (showTodo) {
            todoTable.refreshTodos(todoService);
        } else {
            deadlineTable.refreshDeadlines(serviceDeadlines);
        }
    }

    private void changeTables() {
        if (showTodo) {
            pane.setViewportView(todoTable);
            //this.remove(deadlineTable);
            PanelTube.calendarPanel.showTodo(true);

        } else {
           // this.remove(todoTable);
            pane.setViewportView(deadlineTable);
            PanelTube.calendarPanel.showTodo(false);
        }
        this.repaint();
        this.revalidate();
    }
}
