package at.ac.tuwien.sepm.ui.entityViews;

import at.ac.tuwien.sepm.entity.LVA;
import at.ac.tuwien.sepm.entity.LvaDate;
import at.ac.tuwien.sepm.entity.LvaDateType;
import at.ac.tuwien.sepm.service.*;
import at.ac.tuwien.sepm.service.impl.ValidationException;
import at.ac.tuwien.sepm.ui.template.PanelTube;
import at.ac.tuwien.sepm.ui.template.SelectItem;
import at.ac.tuwien.sepm.ui.StandardSimpleInsidePanel;
import at.ac.tuwien.sepm.ui.UI;
import at.ac.tuwien.sepm.ui.template.WideComboBox;
import com.toedter.calendar.JDateChooser;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Flo
 * Date: 03.06.13
 * Time: 01:41
 * To change this template use File | Settings | File Templates.
 */
@UI
public class ViewDeadline extends StandardSimpleInsidePanel {
    private LvaDateService lvaDateService;
    private LvaDate deadline;
    private JTextArea description;
    private JCheckBox done;
    private JLabel doneLabel;
    private JButton save;
    private JButton delete;
    private JLabel lvaLabel;
    private WideComboBox lva;
    private List<LVA> lvas;
    private LVAService lvaService;
    private JLabel timeLabel;

    private JDateChooser calTime;
    private JSpinner time;

    private Logger log = LogManager.getLogger(this.getClass().getSimpleName());

    @Autowired
    public ViewDeadline(LvaDateService lvaDateService, LVAService lvaService) {
        this.lvaService=lvaService;
        this.lvaDateService = lvaDateService;
        init();
        addImage();
        deadline = new LvaDate();
        addEditableTitle(deadline.getName());
        addReturnButton();
        addContent();
        addButtons();
        this.repaint();
        this.revalidate();
    }

    public void setDeadline(LvaDate deadline) {
        if(deadline == null){
            this.deadline = new LvaDate();
            changeTitle("Neue Deadline");
            description.setText("Bitte Beschreibung einfügen");
            calTime.setDate(new Date());
            time.setValue(new Date());
            done.setSelected(false);
            setDeleteButton(false);
        } else {
            this.deadline=deadline;
            changeTitle(deadline.getName());
            description.setText(deadline.getDescription());
            done.setSelected(deadline.getWasAttendant());
            calTime.setDate(deadline.getStart().toDate());
            time.setValue(deadline.getStart().toDate());
           /* try {
                lva.setSelectedItem(lvaService.readById(deadline.getLva()));     //todo
            } catch (ServiceException e) {
                log.error("Problem beim Einlesen der zugehörigen MetaLva.");
                e.printStackTrace();
            } catch (ValidationException e) {
                log.error("Problem beim Einlesen der zugehörigen MetaLva.");
                e.printStackTrace();
            }  */
            setDeleteButton(true);
        }
    }

    private void setDeleteButton(boolean showDeleteButton) {
        if (showDeleteButton) {
            delete.setVisible(true);
            done.setVisible(true);
            doneLabel.setVisible(true);
        } else {
            delete.setVisible(false);
            done.setVisible(false);
            doneLabel.setVisible(false);
        }
        refresh();
    }

    private void addButtons() {
        delete = new JButton("Löschen");
        delete.setFont(standardTextFont);
        delete.setBounds((int)simpleWhiteSpace.getX()+(int)simpleWhiteSpace.getWidth()*1/3-145, (int)simpleWhiteSpace.getY() + (int)simpleWhiteSpace.getHeight()-60, 130,40);
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    if (lvaDateService.readById(deadline.getId()) != null) {
                        int i = JOptionPane.showConfirmDialog(ViewDeadline.this, "Wollen sie diese Deadline wirklich löschen?", "", JOptionPane.YES_NO_OPTION);
                        if (i == 0) {
                            lvaDateService.delete(deadline.getId());
                        }
                        setVisible(false);
                        PanelTube.backgroundPanel.showLastComponent();
                    } else {
                        JOptionPane.showMessageDialog(ViewDeadline.this, "Diese Deadline existiert nicht in der Datenbank.", "Fehler", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (ServiceException e) {
                    log.error("Deadline is invalid.");
                    JOptionPane.showMessageDialog(ViewDeadline.this, "Die Angaben sind ungültig.", "Fehler", JOptionPane.ERROR_MESSAGE);
                } catch (ValidationException e) {
                    log.error("Deadline is invalid.");
                    JOptionPane.showMessageDialog(ViewDeadline.this, "Die Angaben sind ungültig.", "Fehler", JOptionPane.ERROR_MESSAGE);
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
                    deadline.setName(title.getText());
                    deadline.setDescription(description.getText());
                    deadline.setLva(((LvaSelectItem) lva.getSelectedItem()).get().getId());
                    deadline.setWasAttendant(done.isSelected());
                    deadline.setType(LvaDateType.DEADLINE);
                    deadline.setTime(new TimeFrame(convertDateAndTime(time, calTime), convertDateAndTime(time, calTime)));

                    if (deadline.getId() != null) {
                        if (lvaDateService.readById(deadline.getId()) != null) {
                            lvaDateService.update(deadline);
                        }
                    } else {
                        lvaDateService.create(deadline);
                    }
                    setVisible(false);
                    PanelTube.backgroundPanel.showLastComponent();
                } catch (ServiceException e) {
                    log.error("Deadline is invalid.");
                    JOptionPane.showMessageDialog(ViewDeadline.this, "Die Angaben sind ungültig.", "Fehler", JOptionPane.ERROR_MESSAGE);
                } catch (ValidationException e) {
                    log.error("Deadline is invalid.");
                    JOptionPane.showMessageDialog(ViewDeadline.this, "Die Angaben sind ungültig.", "Fehler", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        this.add(save);
    }

    private void addContent() {
        int verticalSpace = 10;

        description = new JTextArea(deadline.getDescription());
        description.setLineWrap(true);
        description.setWrapStyleWord(true);
        description.setFont(standardTextFont);
        description.setBounds((int)simpleWhiteSpace.getX()+(int)simpleWhiteSpace.getWidth()*1/3, (int)simpleWhiteSpace.getY()+20,(int)simpleWhiteSpace.getWidth()*2/3 - 20,(int)simpleWhiteSpace.getHeight()-40);
        JScrollPane scroll = new JScrollPane(description);
        scroll.setBounds((int)simpleWhiteSpace.getX()+(int)simpleWhiteSpace.getWidth()*1/3, (int)simpleWhiteSpace.getY()+20,(int)simpleWhiteSpace.getWidth()*2/3 - 20,(int)simpleWhiteSpace.getHeight()-40);
        this.add(scroll);


        timeLabel = new JLabel("Datum");
        timeLabel.setFont(standardTextFont);
        timeLabel.setBounds((int)simpleWhiteSpace.getX() + 10,(int)simpleWhiteSpace.getY() + 10,80,25);
        this.add(timeLabel);

        calTime = new JDateChooser();
        calTime.setFont(standardButtonFont);
        calTime.setBounds(timeLabel.getX(), timeLabel.getY() + timeLabel.getHeight() + verticalSpace, 100,25);
        this.add(calTime);

        time = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(time, "HH:mm");
        time.setEditor(timeEditor);
        time.setFont(standardButtonFont);
        time.setBounds(calTime.getX() + calTime.getWidth() + 5, calTime.getY(), 65,25);
        this.add(time);


        lvaLabel = new JLabel("Lva");
        lvaLabel.setFont(standardTextFont);
        lvaLabel.setBounds(calTime.getX(), calTime.getY() + calTime.getHeight() + verticalSpace*2, 60,25);
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
            lva.addItem(new LvaSelectItem(t));
        }
        lva.setFont(standardTextFont);
        lva.setBounds(lvaLabel.getX() + lvaLabel.getWidth() + 10, lvaLabel.getY(), 200,25);
        this.add(lva);


        doneLabel = new JLabel("Erledigt");
        doneLabel.setFont(standardTextFont);
        doneLabel.setBounds(lvaLabel.getX(), lvaLabel.getY() + lvaLabel.getHeight() + verticalSpace*2, 60,25);
        this.add(doneLabel);

        done = new JCheckBox();
        done.setSelected(deadline.getWasAttendant() != null ? deadline.getWasAttendant() : false);
        done.setBackground(new Color(0, 0, 0, 0));
        done.setBounds(doneLabel.getX() + doneLabel.getWidth() + 10, doneLabel.getY(), 20, 20);
        this.add(done);
    }

    private static class LvaSelectItem extends SelectItem<LVA> {
        LvaSelectItem(LVA item) {
            super(item);
        }

        @Override
        public String toString() {
            return item.getMetaLVA().getName();
        }
    }

    @Override
    public void refresh() {
        try {
            int year = DateTime.now().getYear();
            boolean isWinterSemester = (DateTime.now().getMonthOfYear() > 7); //todo was is mit januar?
            lvas = lvaService.readByYearAndSemester(year, isWinterSemester);
        } catch(ServiceException e) {
            log.error(e.getMessage());
        } catch(ValidationException e) {
            log.error(e.getMessage());
        }
        lva.removeAllItems();
        for (LVA t : lvas) {
            lva.addItem(new LvaSelectItem(t));
        }
    }
}

