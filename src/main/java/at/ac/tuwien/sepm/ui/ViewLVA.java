package at.ac.tuwien.sepm.ui;

import at.ac.tuwien.sepm.entity.LvaDate;
import at.ac.tuwien.sepm.entity.LvaDateType;
import at.ac.tuwien.sepm.service.TimeFrame;
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
public class ViewLVA extends StandardSimpleInsidePanel {
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

    public ViewLVA() {
        init();
        addImage();
        lvaDate = new LvaDate();
        addReturnButton();
        addContent();
        addSaveButton();
    }

    public void setLVADateEntity(LvaDate lvaDate) {
        this.lvaDate=lvaDate;
        addTitle(lvaDate.getName());

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

        fromLabel = new JLabel();


        toLabel = new JLabel();


        typeLabel = new JLabel();
        type = new JComboBox();

        attendanceRequiredLabel = new JLabel();
        attendanceRequired = new JCheckBox();

        attendedLabel = new JLabel();
        attended  = new JCheckBox();

        roomLabel = new JLabel();


    }
}
