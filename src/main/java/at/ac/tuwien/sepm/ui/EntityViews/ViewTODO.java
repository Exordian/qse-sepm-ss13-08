package at.ac.tuwien.sepm.ui.EntityViews;

import at.ac.tuwien.sepm.entity.LVA;
import at.ac.tuwien.sepm.entity.LvaDateType;
import at.ac.tuwien.sepm.entity.Todo;
import at.ac.tuwien.sepm.service.LVAService;
import at.ac.tuwien.sepm.service.ServiceException;
import at.ac.tuwien.sepm.service.TodoService;
import at.ac.tuwien.sepm.service.impl.ValidationException;
import at.ac.tuwien.sepm.ui.StandardSimpleInsidePanel;
import at.ac.tuwien.sepm.ui.UI;
import at.ac.tuwien.sepm.ui.WideComboBox;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Flo
 * Date: 03.06.13
 * Time: 01:41
 * To change this template use File | Settings | File Templates.
 */
@UI
public class ViewTODO extends StandardSimpleInsidePanel {
    private TodoService todoService;
    private Todo todo;
    private JTextArea description;
    private JCheckBox done;
    private JLabel doneLabel;
    private JButton save;
    private JButton delete;
    private JLabel lvaLabel;
    private WideComboBox lva;
    private List<LVA> lvas;
    private LVAService lvaService;

    private Logger log = LogManager.getLogger(this.getClass().getSimpleName());

    @Autowired
    public ViewTODO(TodoService todoService, LVAService lvaService) {
        this.lvaService=lvaService;
        this.todoService = todoService;
        init();
        addImage();
        todo = new Todo();
        addEditableTitle(todo.getName());
        addReturnButton();
        addContent();
        addButtons();
        this.repaint();
        this.revalidate();
    }

    public void setTodo(Todo todo) {
        this.todo=todo;
        changeTitle(todo.getName());
        description.setText(todo.getDescription());
        done.setSelected(todo.getDone());
        //lva.setSelectedItem(todo.getLva().getMetaLVA().getName()); //todo wie setze ich das in einer comobox?
    }

    private void addButtons() {
        delete = new JButton("Löschen");
        delete.setFont(standardTextFont);
        delete.setBounds((int)simpleWhiteSpace.getX()+(int)simpleWhiteSpace.getWidth()*1/3-145, (int)simpleWhiteSpace.getY() + (int)simpleWhiteSpace.getHeight()-60, 130,40);
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    if (todoService.readById(todo.getId()) != null) {
                        int i = JOptionPane.showConfirmDialog(ViewTODO.this, "Wollen sie dieses TODO wirklich löschen?", "", JOptionPane.YES_NO_OPTION);
                        if (i == 0) {
                            todoService.delete(todo.getId());
                        }
                    } else {
                        JOptionPane.showMessageDialog(ViewTODO.this, "Dieses TODO ist noch nicht in der Datenbank.", "Fehler", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (ServiceException e) {
                    log.error("TODO is invalid.");
                    JOptionPane.showMessageDialog(ViewTODO.this, "Die Angaben sind ungültig.", "Fehler", JOptionPane.ERROR_MESSAGE);
                } catch (ValidationException e) {
                    log.error("TODO is invalid.");
                    JOptionPane.showMessageDialog(ViewTODO.this, "Die Angaben sind ungültig.", "Fehler", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        this.add(delete);

        save = new JButton("Speichern");
        save.setFont(standardTextFont);
        save.setBounds((int)simpleWhiteSpace.getX()+(int)simpleWhiteSpace.getWidth()*1/3-170-120, (int)simpleWhiteSpace.getY() + (int)simpleWhiteSpace.getHeight()-60, 130,40);
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    todo.setName(title.getText());
                    todo.setDescription(description.getText());
                    todo.setLva((LVA)lva.getSelectedItem()); //todo gleiches problem wie oben
                    todo.setDone(done.isSelected());
                    if (todoService.readById(todo.getId()) != null) {
                        todoService.update(todo);
                    } else {
                        todoService.create(todo);
                    }
                } catch (ServiceException e) {
                    log.error("TODO is invalid.");
                    JOptionPane.showMessageDialog(ViewTODO.this, "Die Angaben sind ungültig.", "Fehler", JOptionPane.ERROR_MESSAGE);
                } catch (ValidationException e) {
                    log.error("TODO is invalid.");
                    JOptionPane.showMessageDialog(ViewTODO.this, "Die Angaben sind ungültig.", "Fehler", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        this.add(save);
    }

    private void addContent() {
        int verticalSpace = 10;

        description = new JTextArea(todo.getDescription());
        description.setLineWrap(true);
        description.setWrapStyleWord(true);
        description.setFont(standardTextFont);
        description.setBounds((int)simpleWhiteSpace.getX()+(int)simpleWhiteSpace.getWidth()*1/3, (int)simpleWhiteSpace.getY()+20,(int)simpleWhiteSpace.getWidth()*2/3 - 20,(int)simpleWhiteSpace.getHeight()-40);
        JScrollPane scroll = new JScrollPane(description);
        scroll.setBounds((int)simpleWhiteSpace.getX()+(int)simpleWhiteSpace.getWidth()*1/3, (int)simpleWhiteSpace.getY()+20,(int)simpleWhiteSpace.getWidth()*2/3 - 20,(int)simpleWhiteSpace.getHeight()-40);
        this.add(scroll);


        lvaLabel = new JLabel("Lva");
        lvaLabel.setFont(standardTextFont);
        lvaLabel.setBounds((int)simpleWhiteSpace.getX() + 20,(int)simpleWhiteSpace.getY() + 10, 60,25);
        this.add(lvaLabel);

        lva = new WideComboBox();

        try {
            int year = DateTime.now().getYear();
            boolean isWinterSemester = (DateTime.now().getMonthOfYear() > 7);
            lvas = lvaService.readByYearAndSemester(year, isWinterSemester);
        } catch(ServiceException e) {
            log.error(e.getMessage());
        } catch(ValidationException e) {
            log.error(e.getMessage());
        }

        for (LVA t : lvas) {
            lva.addItem(t.getMetaLVA().getName());
        }
        lva.setFont(standardTextFont);
        lva.setBounds(lvaLabel.getX() + lvaLabel.getWidth() + 10, lvaLabel.getY(), 200,25);
        this.add(lva);


        doneLabel = new JLabel("Erledigt");
        doneLabel.setFont(standardTextFont);
        doneLabel.setBounds(lvaLabel.getX(), lvaLabel.getY() + lvaLabel.getHeight() + verticalSpace*2, 60,25);
        this.add(doneLabel);

        done = new JCheckBox();
        done.setSelected(todo.getDone() != null ? todo.getDone() : false);
        done.setBackground(new Color(0,0,0,0));
        done.setBounds(doneLabel.getX() + doneLabel.getWidth() + 5, doneLabel.getY(), 20, 20);
        this.add(done);
    }
}
