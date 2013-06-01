package at.ac.tuwien.sepm.ui;

import at.ac.tuwien.sepm.entity.DateEntity;
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
 * Time: 22:45
 * To change this template use File | Settings | File Templates.
 */
@UI
public class ViewDate extends StandardSimpleInsidePanel {
    private DateEntity dateEntity;
    private JTextArea description;
    private JButton save;

    private JLabel fromLabel;
    private JLabel toLabel;
    private JLabel intersectLabel;

    private JCheckBox intersectable;
    private JTextField from;
    private JTextField to;

    public ViewDate() {
        init();
        addImage();
        dateEntity = new DateEntity();
        addReturnButton();
        addContent();
    }

    public void setDateEntity(DateEntity dateEntity) {
        this.dateEntity=dateEntity;

        addTitle(dateEntity.getName());
        description.setText(dateEntity.getDescription());
        intersectable.setSelected(dateEntity.getIntersectable());

        this.repaint();
        this.revalidate();
    }

    private void addContent() {
        int verticalSpace = 10;

        description = new JTextArea(dateEntity.getDescription());
        description.setLineWrap(true);
        description.setWrapStyleWord(true);
        //JScrollPane scroll = new JScrollPane(description, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);  //todo scrollpane
        description.setBorder(BorderFactory.createLineBorder(Color.gray));
        description.setFont(standardTextFont);
        description.setBounds((int)simpleWhiteSpace.getX()+(int)simpleWhiteSpace.getWidth()*1/3, (int)simpleWhiteSpace.getY()+20,(int)simpleWhiteSpace.getWidth()*2/3 - 20,(int)simpleWhiteSpace.getHeight()-40);
        this.add(description);

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


        fromLabel = new JLabel("Von");
        fromLabel.setFont(standardTextFont);
        fromLabel.setBounds((int)simpleWhiteSpace.getX() + 10,(int)simpleWhiteSpace.getY() + 10,50,25);
        this.add(fromLabel);

        from = new JTextField();   //todo eventuell datechooser
        from.setFont(standardTextFont);
        from.setBounds(fromLabel.getX(), fromLabel.getY() + fromLabel.getHeight() + verticalSpace, 100,25);
        this.add(from);


        toLabel = new JLabel("Bis");
        toLabel.setFont(standardTextFont);
        toLabel.setBounds(from.getX(), from.getY() + from.getHeight() + verticalSpace*2, 50,25);
        this.add(toLabel);

        to = new JTextField();   //todo eventuell datechooser
        to.setFont(standardTextFont);
        to.setBounds(toLabel.getX(), toLabel.getY() + toLabel.getHeight() + verticalSpace, 100,25);
        this.add(to);


        intersectLabel = new JLabel("Überschneidungen zulassen?");
        intersectLabel.setFont(standardTextFont);
        intersectLabel.setBounds(to.getX(), to.getY() + to.getHeight() + verticalSpace*2, 225,25);
        this.add(intersectLabel);

        intersectable = new JCheckBox();
        intersectable.setSelected(dateEntity.getIntersectable() != null ? dateEntity.getIntersectable() : false);
        intersectable.setBackground(new Color(0,0,0,0));
        intersectable.setBounds(intersectLabel.getX() + intersectLabel.getWidth() + 5, intersectLabel.getY(), 20, 20);
        this.add(intersectable);

        this.revalidate();
        this.repaint();
    }
}
