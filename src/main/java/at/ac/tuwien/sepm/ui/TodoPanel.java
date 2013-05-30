package at.ac.tuwien.sepm.ui;

import at.ac.tuwien.sepm.dao.MetaLvaDao;
import at.ac.tuwien.sepm.dao.hsqldb.DBLvaDao;
import at.ac.tuwien.sepm.entity.LVA;
import at.ac.tuwien.sepm.entity.LvaDate;
import at.ac.tuwien.sepm.entity.LvaDateType;
import at.ac.tuwien.sepm.entity.Todo;
import at.ac.tuwien.sepm.service.ServiceException;
import at.ac.tuwien.sepm.service.impl.LvaDateServiceImpl;
import at.ac.tuwien.sepm.service.impl.TodoServiceImpl;
import at.ac.tuwien.sepm.service.impl.ValidationException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;

/**
 * @author Lena Lenz
 */

@UI
public class TodoPanel extends JPanel {

    Logger logger = LogManager.getLogger(this.getClass().getSimpleName());

    private static final int MAX_ROWS = 20;
    private static final int COLUMNS_TODOS = 5;
    private static final int COLUMNS_DEADLINES = 6;

    @Autowired
    private TodoServiceImpl service;
    //@Autowired
    private LvaDateServiceImpl serviceDeadlines;

    private JTable displayTodos;
    private JTable displayDeadlines;
    private JButton add_todo;
    private JButton edit_todo;
    private JButton delete_todo;
    private JButton add_deadline;
    private JButton edit_deadline;
    private JButton delete_deadline;
    private List<LVA> lvaList;

    private DateTime deadline;


    public TodoPanel() {
        serviceDeadlines = new LvaDateServiceImpl();

        initJTables();
        configureColumns();
        addButtons();
        addActionListeners();

    }

    public void initJTables() {

        //JTable displayTodos
        String[][] dataTodos = new String[MAX_ROWS][COLUMNS_TODOS];
        displayTodos = new JTable(dataTodos, new String[] {"Nr", "LVA", "Name", "Beschreibung", "Abgeschlossen"});
        displayTodos.setRowHeight(20);

        JTableHeader headerT = displayTodos.getTableHeader();
        headerT.setBackground(new Color(73, 133, 255));
        headerT.setFont(new Font("Arial",Font.HANGING_BASELINE+Font.BOLD,15));


        //JTable displayDeadlines
        String[][] dataDeadlines = new String[MAX_ROWS][COLUMNS_DEADLINES];
        displayDeadlines = new JTable(dataDeadlines, new String[] {"Nr", "LVA", "Name", "Beschreibung", "Deadline", "Abgeschlossen"});
        displayDeadlines.setRowHeight(20);

        JTableHeader headerD = displayDeadlines.getTableHeader();
        headerD.setBackground(new Color(73, 133, 255));
        headerD.setFont(new Font("Arial",Font.HANGING_BASELINE+Font.BOLD,15));

        this.add(displayTodos);
        this.add(displayDeadlines);
    }

    public void configureColumns() {
        //Todos
        JScrollPane scrollpaneT = new JScrollPane(displayTodos);
        displayTodos.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        TableColumnModel tcmT = displayTodos.getColumnModel();
        TableColumn Tcolumn0 = tcmT.getColumn(0);
        TableColumn Tcolumn1 = tcmT.getColumn(1);
        TableColumn Tcolumn2 = tcmT.getColumn(2);
        TableColumn Tcolumn3 = tcmT.getColumn(3);
        TableColumn Tcolumn4 = tcmT.getColumn(4);
        Tcolumn0.setPreferredWidth(10);
        Tcolumn1.setPreferredWidth(20);
        Tcolumn2.setPreferredWidth(20);
        Tcolumn3.setPreferredWidth(20);
        Tcolumn4.setPreferredWidth(20);


        refreshLVAListByYearAndSemester();

        //ComboBox pro Zelle in Spalte 'LVA' der Tabelle 'Todos'
        String[] lvas = lvaList.toArray(new String[lvaList.size()]);
        Tcolumn1.setCellEditor(new MyComboBoxEditor(lvas));
        Tcolumn1.setCellRenderer(new MyComboBoxRenderer(lvas));

        String[] items = {"Ja", "Nein"};
        //ComboBox pro Zelle in Spalte 'Abgeschlossen' der Tabelle 'Todos'
        Tcolumn4.setCellEditor(new MyComboBoxEditor(items));
        Tcolumn4.setCellRenderer(new MyComboBoxRenderer(items));

        this.add(scrollpaneT);

        //Deadlines
        JScrollPane scrollpaneD = new JScrollPane(displayDeadlines);
        displayDeadlines.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        TableColumnModel tcmD = displayDeadlines.getColumnModel();
        TableColumn Dcolumn0 = tcmD.getColumn(0);
        TableColumn Dcolumn1 = tcmD.getColumn(1);
        TableColumn Dcolumn2 = tcmD.getColumn(2);
        TableColumn Dcolumn3 = tcmD.getColumn(3);
        TableColumn Dcolumn4 = tcmD.getColumn(4);
        TableColumn Dcolumn5 = tcmD.getColumn(5);
        Dcolumn0.setPreferredWidth(10);
        Dcolumn1.setPreferredWidth(20);
        Dcolumn2.setPreferredWidth(20);
        Dcolumn3.setPreferredWidth(20);
        Dcolumn4.setPreferredWidth(20);
        Dcolumn5.setPreferredWidth(20);
        //ComboBox pro Zelle in Spalte 'LVA' in Spalte LVA der Tabelle 'Deadlines'
        Dcolumn1.setCellEditor(new MyComboBoxEditor(lvas));
        Dcolumn1.setCellRenderer(new MyComboBoxRenderer(lvas));
        //ComboBox pro Zelle in Spalte 'Abgeschlossen' der Tabelle 'Deadlines'
        Dcolumn5.setCellEditor(new MyComboBoxEditor(items));
        Dcolumn5.setCellRenderer(new MyComboBoxRenderer(items));

        this.add(scrollpaneD);
    }

    private void refreshLVAListByYearAndSemester() {
        int year = DateTime.now().getYear();
        boolean isWinterSemester = (DateTime.now().getMonthOfYear() < 6)? false : true;
        try {
            this.lvaList = service.getLVAByYearAndSemester(year, isWinterSemester);
        } catch(ServiceException e) {
            logger.error(e.getMessage());
        }
    }

    private class MyComboBoxEditor extends DefaultCellEditor {

        private static final long serialVersionUID = 1L;

        public MyComboBoxEditor(String[] items) {
            super(new JComboBox(items));
        }

    }

    private class MyComboBoxRenderer extends JComboBox implements TableCellRenderer {

        private static final long serialVersionUID = 1L;

        public MyComboBoxRenderer(String[] items) {
            super(items);
        }

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            if(isSelected) {
                setForeground(table.getSelectionForeground());
                super.setBackground(table.getSelectionBackground());
            } else {
                setForeground(table.getForeground());
                setBackground(table.getBackground());
            }
            setSelectedItem(value);
            return this;
        }

    }

    public void addButtons() {
        add_todo = new JButton("Todo Hinzufügen");
        edit_todo = new JButton("Todo Bearbeiten");
        delete_todo = new JButton("Todo Löschen");
        this.add(add_todo);
        this.add(edit_todo);
        this.add(delete_todo);
        add_deadline = new JButton("Deadline Hinzufügen");
        edit_deadline = new JButton("Deadline Bearbeiten");
        delete_deadline = new JButton("Deadline Löschen");
        this.add(add_deadline);
        this.add(edit_deadline);
        this.add(delete_deadline);
    }

    public void addActionListeners() {
        //Todos
        add_todo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TodoPanel.this.AddTodoPressed();
            }
        });

        edit_todo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TodoPanel.this.EditTodoPressed();
            }
        });

        delete_todo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TodoPanel.this.DeleteTodoPressed();
            }
        });

        //Deadlines
        add_deadline.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TodoPanel.this.AddDeadlinePressed();
            }
        });

        edit_deadline.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TodoPanel.this.EditDeadlinePressed();
            }
        });

        delete_deadline.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TodoPanel.this.DeleteDeadlinePressed();
            }
        });
    }


    public void AddTodoPressed() {
        Todo newTodo = this.makeTodo('N');

        try {
            service.create(newTodo);
        } catch(ValidationException e) {
            logger.error(e.getMessage());
        } catch(ServiceException e) {
            logger.error(e.getMessage());
        }
    }

    public void EditTodoPressed() {
        Todo editedTodo = this.makeTodo('E');

        try {
            service.update(editedTodo);
            refreshTodos();
        } catch(ValidationException e) {
            logger.error(e.getMessage());
        } catch(ServiceException e) {
            logger.error(e.getMessage());
        }
    }

    public void DeleteTodoPressed() {
        int todo_id = 0;
        try {
            todo_id = (Integer)displayTodos.getValueAt(displayTodos.getSelectedRow(), 0);
        } catch(ArrayIndexOutOfBoundsException e) {
            //TODO
        }

        try {
            service.delete(todo_id);
            logger.debug("deleting Todo with id=" + todo_id);
            refreshTodos();
        } catch (ServiceException e) {
            logger.error(e.getMessage());
        } catch (ValidationException e) {
            logger.error(e.getMessage());
        }
    }

    public void AddDeadlinePressed() {
        LvaDate newDeadline = this.makeDeadline('N');

        try {
            serviceDeadlines.create(newDeadline);
        } catch(ValidationException e) {
            logger.error(e.getMessage());
        } catch(ServiceException e) {
            logger.error(e.getMessage());
        }
    }

    public void EditDeadlinePressed() {
        LvaDate editedDeadline = this.makeDeadline('E');

        try {
            serviceDeadlines.update(editedDeadline);
            refreshDeadlines();
        } catch(ValidationException e) {
            logger.error(e.getMessage());
        } catch(ServiceException e) {
            logger.error(e.getMessage());
        }
    }

    public void DeleteDeadlinePressed() {
        int deadline_id = 0;
        try {
            deadline_id = (Integer)displayDeadlines.getValueAt(displayDeadlines.getSelectedRow(), 0);
        } catch(ArrayIndexOutOfBoundsException e) {
            //TODO
        }

        try {
            serviceDeadlines.delete(deadline_id);
            logger.debug("deleting Deadline with id=" + deadline_id);
            refreshDeadlines();
        } catch (ServiceException e) {
            logger.error(e.getMessage());
        } catch (ValidationException e) {
            logger.error(e.getMessage());
        }
    }

    //gibt erste freie Reihe in Todos(verwendung == 'T') bzw Deadlines(verwendung == 'D') zurück
    protected int getFirstFreeRow(char verwendung) {
        JTable anzeigeTab = null;
        int columns = 0;
        if(verwendung == 'T') { //Todos
            anzeigeTab = displayTodos;
            columns = COLUMNS_TODOS;
        } else if(verwendung == 'D') {   //Deadlines
            anzeigeTab = displayDeadlines;
            columns = COLUMNS_DEADLINES;
        }
        int row_index = -1;
        boolean free_row = false;
        while(!free_row && row_index < MAX_ROWS) {
            row_index++;
            free_row = true;
            for(int j = 0; j < columns; j++) {
                if(anzeigeTab.getValueAt(row_index, j) != null && !anzeigeTab.getValueAt(row_index, j).equals(""))
                    free_row = false;
            }
        }
        return row_index;
    }

    //creates(verwendung == 'N') or edits(verwendung == 'E') a todo
    public Todo makeTodo(char verwendung) {
        int safing_row = this.getFirstFreeRow('T') - 1;

        int id = Integer.valueOf((String) (displayTodos.getValueAt(safing_row,0)));
        int index = ((MyComboBoxRenderer) displayTodos.getCellRenderer(safing_row, 1)).getSelectedIndex();
        LVA lva = lvaList.get(index);
        String name = (String) displayTodos.getValueAt(safing_row, 2);
        String description = (String) displayTodos.getValueAt(safing_row,3);
        boolean done = Boolean.valueOf((String) displayTodos.getValueAt(safing_row, 4)).booleanValue();

        Todo todo = new Todo();
        if(verwendung == 'N') {  //new Todo
            todo.setId(id);
        }
        else if (verwendung == 'E') {  //edit Todo
            try {
                todo = service.readById(id);
            } catch (ServiceException e) {
                logger.error(e.getMessage());
            } catch (ValidationException e) {
                logger.error(e.getMessage());
            }
        }

        todo.setLva(lva);
        todo.setName(name);
        todo.setDescription(description);
        todo.setDone(done);

        return todo;
    }

    public LvaDate makeDeadline(char verwendung) {
        int safing_row = this.getFirstFreeRow('D');

        int id = Integer.valueOf((String) (displayDeadlines.getValueAt(safing_row,0)));
        int index = ((MyComboBoxRenderer) displayDeadlines.getCellRenderer(safing_row, 1)).getSelectedIndex();
        LVA lva = lvaList.get(index);
        String name = (String) displayDeadlines.getValueAt(safing_row, 2);
        String description = (String) displayDeadlines.getValueAt(safing_row,3);
        String timestring = ((String) displayDeadlines.getValueAt(safing_row,4)); //"1999-09-01 21:34"
        DateTime time_deadline = DateTime.parse(timestring); //TODO: korrektes Parsing?
        boolean wasAttendant = Boolean.valueOf((String) displayDeadlines.getValueAt(safing_row, 5)).booleanValue();

        LvaDate deadline = new LvaDate();
        if(verwendung == 'N') {  //new Deadline
            deadline.setId(id);
        }
        else if (verwendung == 'E') {  //edit Deadline
            try {
                deadline = serviceDeadlines.readById(id);
            } catch (ServiceException e) {
                logger.error(e.getMessage());
            } catch (ValidationException e) {
                logger.error(e.getMessage());
            }
        }

        deadline.setLva(lva.getId());
        deadline.setName(name);
        deadline.setDescription(description);
        deadline.setType(LvaDateType.DEADLINE);
        deadline.setRoom("");
        deadline.setResult(1);
        deadline.setStart(time_deadline);
        deadline.setStop(time_deadline);
        deadline.setAttendanceRequired(true);
        deadline.setWasAttendant(wasAttendant);

        return deadline;
    }

    public void refreshTodos() {
        try {
            refreshLVAListByYearAndSemester(); //lvaListe aktualisieren

            List<Todo> todoList = service.getAllTodos();
            String[][] TodosAktuell = this.convertTodos(todoList);
            logger.debug("current number of horses: "+ TodosAktuell.length);
            display(TodosAktuell, 'T');
            //suchAnsicht = false;
        } catch(ServiceException e) {
            logger.error(e.getMessage());
        }
    }

    public void refreshDeadlines() {
        try {
            refreshLVAListByYearAndSemester(); //lvaListe aktualisieren

            List<LvaDate> deadlineList = serviceDeadlines.getAllDeadlines();
            String[][] DeadlinesAktuell = this.convertDeadlines(deadlineList);
            logger.debug("current number of deadlines: "+ DeadlinesAktuell.length);
            display(DeadlinesAktuell, 'D');
            //suchAnsicht = false;
        } catch(ServiceException e) {
            logger.error(e.getMessage());
        }
    }

    public String[][] convertTodos(List<Todo> liste)  {

        if(liste == null) {
            logger.error("invalid parameter");
            return null;
        }
        String[][] arr = new String[liste.size()][COLUMNS_TODOS];
        int i = 0;
        for(Todo todo : liste) {
            arr[i][0] = todo.getId().toString();
            arr[i][1] = ""+ todo.getLva().getMetaLVA().getName();
            arr[i][2] = ""+ todo.getDescription();
            arr[i][3] = ""+ todo.getDone().toString();
            i++;
        }
        return arr;
    }

    public String[][] convertDeadlines(List<LvaDate> liste) {
        if(liste == null) {
            logger.error("invalid parameter");
            return null;
        }
        String[][] arr = new String[liste.size()][COLUMNS_DEADLINES];
        int i = 0;
        for(LvaDate deadline : liste) {
            arr[i][0] = deadline.getId().toString();
            LVA lva = null;
            try {
                lva = service.getLVAByID(deadline.getLva());
            } catch (ServiceException e) {
                logger.error(e.getMessage());
            } catch (ValidationException e) {
                logger.error(e.getMessage());
            }
            arr[i][1] = lva.getMetaLVA().getName();
            arr[i][2] = deadline.getDescription();
            arr[i][3] = deadline.getStop().toString();
            arr[i][4] = deadline.getWasAttendant().toString();
            i++;
        }
        return arr;
    }

    public void display(String[][] array, char type) {
        JTable display = null;
        int columns = 0;
        int amount = 0;
        if(type == 'T') { //Todos
            display = displayTodos;
            columns = COLUMNS_TODOS;
            try {
                amount = service.getAllTodos().size();
            } catch(ServiceException e) {
                logger.error(e.getMessage());
            }
        } else if(type == 'D') { //Deadlines
            display = displayDeadlines;
            columns = COLUMNS_DEADLINES;
            try {
                amount = serviceDeadlines.getAllDeadlines().size();
            } catch(ServiceException e) {
                logger.error(e.getMessage());
            }
        }
        //Ergebnis-Array in Tabelle darstellen
        int i = 0;
        for(i = 0; i < array.length; i++) {
            for(int j = 0; j < columns; j++) {
                display.setValueAt(array[i][j], i, j);
            }
        }

        //alle weiteren Felder auf "" setzen
        boolean end_reached = false;
        while(!end_reached && i <= amount) {
            end_reached = true;
            for(int col = 0; col < columns; col++) {
                if(display.getValueAt(i, col) != null) {
                    if(display.getCellRenderer(i, col) instanceof MyComboBoxRenderer) {
                        ((MyComboBoxRenderer) display.getCellRenderer(i, col)).setSelectedItem("");
                        ((MyComboBoxRenderer) display.getCellRenderer(i, col)).revalidate();
                        //((MyComboBoxRenderer) anzeigeTab.getCellRenderer(i, col)).setSelectedItem(null);

                    } else if (!display.getValueAt(i, col).equals("")) {
                        end_reached = false;
                        display.setValueAt("", i, col);
                    }
                }

            }
            i++;
        }
    }
}
