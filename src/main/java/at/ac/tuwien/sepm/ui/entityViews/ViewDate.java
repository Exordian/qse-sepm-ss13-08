package at.ac.tuwien.sepm.ui.entityViews;

import at.ac.tuwien.sepm.entity.DateEntity;
import at.ac.tuwien.sepm.service.DateService;
import at.ac.tuwien.sepm.service.ServiceException;
import at.ac.tuwien.sepm.service.TimeFrame;
import at.ac.tuwien.sepm.ui.SmallInfoPanel;
import at.ac.tuwien.sepm.ui.StandardSimpleInsidePanel;
import at.ac.tuwien.sepm.ui.UI;
import at.ac.tuwien.sepm.ui.template.PanelTube;
import com.toedter.calendar.JDateChooser;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;


/**
 * Created with IntelliJ IDEA.
 * User: Flo
 * Date: 01.06.13
 * Time: 22:45
 * To change this template use File | Settings | File Templates.
 */
@UI
public class ViewDate extends StandardSimpleInsidePanel {
    private DateEntity dateEntity;
    private DateService dateService;
    private JTextArea description;
    private JButton save;
    private JButton delete;

    private JLabel fromLabel;
    private JLabel toLabel;
    private JLabel intersectLabel;

    private JCheckBox intersectable;
    private JDateChooser from;
    private JSpinner fromTime;
    private JDateChooser to;
    private JSpinner toTime;

    private Logger log = LogManager.getLogger(this.getClass().getSimpleName());

    @Autowired
    public ViewDate(DateService dateService) {
        this.dateService=dateService;
        init();
        addImage();
        dateEntity = new DateEntity();
        addEditableTitle(dateEntity.getName());
        addReturnButton();
        addContent();
        addButtons();
        this.repaint();
        this.revalidate();
    }

    public void setDateEntity(DateEntity dateEntity, DateTime dateTime) {
        if (dateEntity == null) {
            this.dateEntity=new DateEntity();
            changeTitle("Neuer Termin");
            description.setText("Bitte Beschreibung einfügen");
            intersectable.setSelected(true);
            if (dateTime != null) {
                from.setDate(dateTime.toDate());
                to.setDate(dateTime.toDate());
                fromTime.setValue(dateTime.toDate());
                toTime.setValue(dateTime.toDate());
            } else {
                from.setDate(new Date());
                to.setDate(new Date());
                fromTime.setValue(new Date());
                toTime.setValue(new Date());
            }
            setDeleteButton(false);
        } else {
            this.dateEntity=dateEntity;
            changeTitle(dateEntity.getName());
            description.setText(dateEntity.getDescription());
            intersectable.setSelected(dateEntity.getIntersectable());
            from.setDate(dateEntity.getStart().toDate());
            to.setDate(dateEntity.getStop().toDate());
            fromTime.setValue(dateEntity.getStart().toDate());
            toTime.setValue(dateEntity.getStop().toDate());
            setDeleteButton(true);
        }
    }

    private void setDeleteButton(boolean showDeleteButton) {
        if (showDeleteButton) {
            delete.setVisible(true);
        } else {
            delete.setVisible(false);
        }
    }

    private void addButtons() {
        delete = new JButton("Löschen");
        delete.setFont(standardTextFont);
        delete.setBounds((int)simpleWhiteSpace.getX()+(int)simpleWhiteSpace.getWidth()*1/3-145, (int)simpleWhiteSpace.getY() + (int)simpleWhiteSpace.getHeight()-60, 130,40);
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    if (dateService.readDateById(dateEntity.getId()) != null) {
                        int i = JOptionPane.showConfirmDialog(ViewDate.this, "Wollen sie diesen Termin wirklich löschen?", "", JOptionPane.YES_NO_OPTION);
                        if (i == 0) {
                            dateService.deleteDate(dateEntity.getId());
                            PanelTube.backgroundPanel.viewInfoText("Der Termin wurde gelöscht.", SmallInfoPanel.Info);
                        }
                    } else {
                        PanelTube.backgroundPanel.viewInfoText("Dieser Termin ist noch nicht in der Datenbank.", SmallInfoPanel.Error);
                    }
                    setVisible(false);
                    PanelTube.backgroundPanel.showLastComponent();
                } catch (ServiceException e) {
                    log.error("DateEntity is invalid.");
                    PanelTube.backgroundPanel.viewInfoText("Die Angaben sind ungültig.", SmallInfoPanel.Error);
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
                    dateEntity.setName(title.getText());
                    dateEntity.setDescription(description.getText());
                    dateEntity.setIntersectable(intersectable.isSelected());
                    if (convertDateAndTime(fromTime, from).isAfter(convertDateAndTime(toTime, to))) {
                        PanelTube.backgroundPanel.viewInfoText("Das Start-Datum muss vor dem End-Datum liegen.", SmallInfoPanel.Info);
                        return;
                    }
                    dateEntity.setTime(new TimeFrame(convertDateAndTime(fromTime, from), convertDateAndTime(toTime, to)));
                    if (dateEntity.getId() != null) {
                        if (dateService.readDateById(dateEntity.getId()) != null)
                            dateService.updateDate(dateEntity);
                    } else {
                        dateService.createDate(dateEntity);
                    }
                    PanelTube.backgroundPanel.viewInfoText("Der Termin wurde gespeichert.", SmallInfoPanel.Success);
                    setVisible(false);
                    PanelTube.backgroundPanel.showLastComponent();
                } catch (ServiceException e) {
                    log.error("DateEntity is invalid.");
                    PanelTube.backgroundPanel.viewInfoText("Die Angaben sind ungültig.", SmallInfoPanel.Error);
                }
            }
        });
        this.add(save);
    }

    private void addContent() {
        int verticalSpace = 10;

        description = new JTextArea(dateEntity.getDescription());
        description.setLineWrap(true);
        description.setWrapStyleWord(true);
        description.setFont(standardTextFont);
        description.setBounds((int)simpleWhiteSpace.getX()+(int)simpleWhiteSpace.getWidth()*1/3, (int)simpleWhiteSpace.getY()+20,(int)simpleWhiteSpace.getWidth()*2/3 - 20,(int)simpleWhiteSpace.getHeight()-40);
        JScrollPane scroll = new JScrollPane(description);
        scroll.setBounds((int)simpleWhiteSpace.getX()+(int)simpleWhiteSpace.getWidth()*1/3, (int)simpleWhiteSpace.getY()+20,(int)simpleWhiteSpace.getWidth()*2/3 - 20,(int)simpleWhiteSpace.getHeight()-40);
        this.add(scroll);



        fromLabel = new JLabel("Von");
        fromLabel.setFont(standardTextFont);
        fromLabel.setBounds((int)simpleWhiteSpace.getX() + 10,(int)simpleWhiteSpace.getY() + 10,50,25);
        this.add(fromLabel);

        from = new JDateChooser();
        from.setFont(standardButtonFont);
        from.setBounds(fromLabel.getX(), fromLabel.getY() + fromLabel.getHeight() + verticalSpace, 100,25);
        this.add(from);

        fromTime = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(fromTime, "HH:mm");
        fromTime.setEditor(timeEditor);
        fromTime.setFont(standardButtonFont);
        fromTime.setBounds(from.getX() + from.getWidth() + 5, from.getY(), 65,25);
        this.add(fromTime);



        toLabel = new JLabel("Bis");
        toLabel.setFont(standardTextFont);
        toLabel.setBounds(from.getX(), from.getY() + from.getHeight() + verticalSpace*2, 50,25);
        this.add(toLabel);

        to = new JDateChooser();
        to.setFont(standardButtonFont);
        to.setBounds(toLabel.getX(), toLabel.getY() + toLabel.getHeight() + verticalSpace, 100,25);
        this.add(to);

        toTime = new JSpinner(new SpinnerDateModel());
        timeEditor = new JSpinner.DateEditor(toTime, "HH:mm");
        toTime.setEditor(timeEditor);
        toTime.setFont(standardButtonFont);
        toTime.setBounds(to.getX() + to.getWidth() + 5, to.getY(), 65,25);
        this.add(toTime);




        intersectLabel = new JLabel("Überschneidungen zulassen?");
        intersectLabel.setFont(standardTextFont);
        intersectLabel.setBounds(to.getX(), to.getY() + to.getHeight() + verticalSpace*2, 225,25);
        this.add(intersectLabel);

        intersectable = new JCheckBox();
        intersectable.addChangeListener(dONTFUCKINGBUGSWINGListener());
        intersectable.setSelected(dateEntity.getIntersectable() != null ? dateEntity.getIntersectable() : false);
        intersectable.setBackground(new Color(0,0,0,0));
        intersectable.setBounds(intersectLabel.getX() + intersectLabel.getWidth() + 5, intersectLabel.getY(), 20, 20);
        this.add(intersectable);
    }

}
