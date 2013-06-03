package at.ac.tuwien.sepm.ui.todo;

import at.ac.tuwien.sepm.entity.Todo;
import at.ac.tuwien.sepm.ui.UI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

/**
 * @author Lena Lenz
 */
@UI
public class TodoTable extends JTable {
    List<Todo> todoList;
    int[] colWidth = new int[]{80,80,200,50};
    int width = colWidth[0] + colWidth[1] + colWidth[2] + colWidth[3];
    DefaultTableModel model;

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
            model.addRow(new String[] {todo.getLva().getMetaLVA().getName(), todo.getName(), todo.getDescription(), todo.getDone().toString()});
        }
    }

    public Todo getSelectedTodo() {
        return todoList.get(getSelectedRow());
    }

    public void removeSelectedTodo() {
        todoList.remove(getSelectedRow());
        model.removeRow(getSelectedRow());
    }

    public void refreshTodos(List<Todo> list) {
        model.setRowCount(0);
        this.setTodos(list);
    }

    public void addNewTodo(Todo todo) {
        todoList.add(todo);
        model.addRow(new String[] {todo.getLva().getMetaLVA().getName(), todo.getName(), todo.getDescription(), todo.getDone().toString()});
    }

}
