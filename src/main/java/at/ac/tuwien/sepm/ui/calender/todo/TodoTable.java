package at.ac.tuwien.sepm.ui.calender.todo;

import at.ac.tuwien.sepm.entity.Todo;
import at.ac.tuwien.sepm.service.ServiceException;
import at.ac.tuwien.sepm.service.TodoService;
import at.ac.tuwien.sepm.ui.UI;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

/**
 * @author Lena Lenz
 */
@UI
@Scope("singleton")
public class TodoTable extends JTable {
    List<Todo> todoList;
    int[] colWidth = new int[]{80,80,220,30};
    int width = colWidth[0] + colWidth[1] + colWidth[2] + colWidth[3];
    DefaultTableModel model;

    private Logger log = LogManager.getLogger(this.getClass().getSimpleName());

    public void init(List<Todo> list) {
        model = new DefaultTableModel(new String[] {"LVA", "Name", "Beschreibung", "Abgeschlossen"},0);
        this.setModel(model);
        for(int i = 0; i < colWidth.length; i++) {
            getColumnModel().getColumn(i).setPreferredWidth(colWidth[i]);
        }
        this.setTodos(list);
    }

    private void setTodos(List<Todo> list) {
        this.todoList = list;
        for(Todo todo: todoList) {
            model.addRow(new String[] {todo.getLva().getMetaLVA().getName(), todo.getName(), todo.getDescription(), todo.getDone()? "Ja" : "Nein"});
        }
    }

    public Todo getSelectedTodo() {
        return todoList.get(getSelectedRow());
    }

    public void refreshTodos(TodoService todoService) {
        model.setRowCount(0);
        try {
            this.setTodos(todoService.getAllTodos());
        } catch (ServiceException e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
    }
}
