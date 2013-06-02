package at.ac.tuwien.sepm.ui;

import at.ac.tuwien.sepm.entity.LvaDate;
import at.ac.tuwien.sepm.entity.LvaDateType;
import at.ac.tuwien.sepm.service.TimeFrame;
import com.toedter.calendar.JDateChooser;
import org.joda.time.DateTime;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * Created with IntelliJ IDEA.
 * User: Flo
 * Date: 01.06.13
 * Time: 22:42
 * To change this template use File | Settings | File Templates.
 */
@UI
public class ViewLvaDate extends StandardSimpleInsidePanel {
    private LvaDate lvaDate;

    private JTextArea description;
    private JButton save;

    private JLabel fromLabel;
    private JLabel toLabel;
    private JLabel typeLabel;
    private JLabel attendanceRequiredLabel;
    private JLabel attendedLabel;
    private JLabel roomLabel;

    private JCheckBox attendanceRequired;
    private JCheckBox attended;
    private JComboBox type;
    private Image room;
    private JDateChooser from;
    private JDateChooser to;
    private JSpinner fromTime;
    private JSpinner toTime;

    public ViewLvaDate() {
        init();
        addImage();
        lvaDate = new LvaDate();
        addEditableTitle(lvaDate.getName());
        addReturnButton();
        addContent();
        addSaveButton();
    }

    public void setLVADateEntity(LvaDate lvaDate) {
        this.lvaDate=lvaDate;
        changeTitle(lvaDate.getName());
        description.setText(lvaDate.getDescription());
        type.setSelectedItem(lvaDate.getType());
        attendanceRequired.setSelected(lvaDate.getAttendanceRequired());
        attended.setSelected(lvaDate.getWasAttendant());
        from.setDate(lvaDate.getStart().toDate());
        to.setDate(lvaDate.getStop().toDate());
        fromTime.setValue(lvaDate.getStart().toDate());
        toTime.setValue(lvaDate.getStop().toDate());
        this.repaint();
        this.revalidate();
    }

    private void addSaveButton() {
        save = new JButton("Speichern");
        save.setFont(standardTextFont);
        save.setBounds((int)simpleWhiteSpace.getX()+(int)simpleWhiteSpace.getWidth()*1/3-170, (int)simpleWhiteSpace.getY() + (int)simpleWhiteSpace.getHeight()-60, 150,40);
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                lvaDate.setName(title.getText());
                lvaDate.setDescription(description.getText());
                lvaDate.setWasAttendant(attended.isSelected());
                lvaDate.setType((LvaDateType) type.getSelectedItem());
                lvaDate.setAttendanceRequired(attendanceRequired.isSelected());
                lvaDate.setTime(new TimeFrame(convertDateAndTime(fromTime, from), convertDateAndTime(toTime, to)));

                //todo save
            }
        });
        this.add(save);
    }

    private void addContent() {
        int verticalSpace = 10;

        description = new JTextArea(lvaDate.getDescription());
        description.setLineWrap(true);
        description.setWrapStyleWord(true);
        description.setFont(standardTextFont);
        description.setBounds((int)simpleWhiteSpace.getX()+(int)simpleWhiteSpace.getWidth()*1/3, (int)simpleWhiteSpace.getY()+20,(int)simpleWhiteSpace.getWidth()*2/3 - 20,(int)simpleWhiteSpace.getHeight()-40);
        JScrollPane scroll = new JScrollPane(description);
        scroll.setBounds((int)simpleWhiteSpace.getX()+(int)simpleWhiteSpace.getWidth()*1/3, (int)simpleWhiteSpace.getY()+20,(int)simpleWhiteSpace.getWidth()*2/3 - 20,(int)simpleWhiteSpace.getHeight()-40);
        this.add(scroll);




        fromLabel = new JLabel("Von");
        fromLabel.setFont(standardTextFont);
        fromLabel.setBounds((int)simpleWhiteSpace.getX() + 20,(int)simpleWhiteSpace.getY() + 10,50,25);
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



        typeLabel = new JLabel("Typ");
        typeLabel.setFont(standardTextFont);
        typeLabel.setBounds(to.getX(), to.getY() + to.getHeight() + verticalSpace*2, 50,25);
        this.add(typeLabel);

        type = new JComboBox();
        for (LvaDateType t : LvaDateType.values()) {
            type.addItem(t);
        }
        type.setFont(standardTextFont);
        type.setBounds(typeLabel.getX() + typeLabel.getWidth() + 20, typeLabel.getY(), 100,25);
        this.add(type);


        attendanceRequiredLabel = new JLabel("Anwesenheitspflicht");
        attendanceRequiredLabel.setFont(standardTextFont);
        attendanceRequiredLabel.setBounds(typeLabel.getX(), type.getY() + type.getHeight() + verticalSpace*2, 180,25);
        this.add(attendanceRequiredLabel);

        attendanceRequired = new JCheckBox();
        attendanceRequired.setSelected(lvaDate.getAttendanceRequired() != null ? lvaDate.getAttendanceRequired() : false);
        attendanceRequired.setBackground(new Color(0,0,0,0));
        attendanceRequired.setBounds(attendanceRequiredLabel.getX() + attendanceRequiredLabel.getWidth() + 5, attendanceRequiredLabel.getY(), 20, 20);
        this.add(attendanceRequired);


        attendedLabel = new JLabel("War anwesend");
        attendedLabel.setFont(standardTextFont);
        attendedLabel.setBounds(attendanceRequiredLabel.getX(), attendanceRequiredLabel.getY() + attendanceRequiredLabel.getHeight() + verticalSpace*2, 180,25);
        this.add(attendedLabel);

        attended  = new JCheckBox();
        attended.setSelected(lvaDate.getWasAttendant() != null ? lvaDate.getWasAttendant() : false);
        attended.setBackground(new Color(0,0,0,0));
        attended.setBounds(attendedLabel.getX() + attendedLabel.getWidth() + 5, attendedLabel.getY(), 20, 20);
        this.add(attended);

        roomLabel = new JLabel("Raum");
        roomLabel.setFont(standardTextFont);
        roomLabel.setBounds(attendedLabel.getX(), attendedLabel.getY() + attendedLabel.getHeight() + verticalSpace*2, 100,25);
        this.add(roomLabel);

        this.revalidate();
        this.repaint();
    }
}
