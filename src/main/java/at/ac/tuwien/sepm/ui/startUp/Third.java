package at.ac.tuwien.sepm.ui.startUp;

import at.ac.tuwien.sepm.ui.template.WideComboBox;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Author: Georg Plaz
 */
public class Third extends SimpleDisplayPanel {
    private static Logger logger = LogManager.getLogger(Third.class);

    private JTextField tissUsername = new JTextField();
    private JPasswordField tissPassword = new JPasswordField();
    private WideComboBox studyDrop;
    private JButton progressFurther;
    private JButton goBack= new JButton("Zurück");
    private JProgressBar progressBar = new JProgressBar();
    private ViewStartUp startUp;

    public Third(double width, double height, ViewStartUp parent) {
        super(width, height,parent);
        this.startUp=parent;
        subInit();
    }
    public void subInit(){
        progressFurther = new JButton("Abschließen");
        progressFurther.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                startUp.next();
            }
        });
        goBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startUp.back();
            }
        });
        addText("Zum Abschluss ein paar Infos..\n\nWenn du LVAs,Termine usw bearbeiten willst, " +
                "kannst du nach einem Rechtsklick aus einem Popup \"Bearbeiten\" wählen.",true);
        addText("Damit du mit dieser App arbeiten kannst, brauchst du vorher LVAs " +
                "die du deinem Studenverlauf hinzufügst. Dazu kannst du dir entweder ein Semester " +
                "planen lassen Studienverlauf -> Planen, oder du fügst die LVAs manuell hinzu. " +
                "Dazu bearbeitest du das jeweilige Semester der LVA und aktivierst die Checkbox " +
                "\"Im Studienverlauf\"", true);
        addText("Der Planer errechnet dir mögliche Semester ohne Überschneidungen. Dabei wird unter " +
                "anderem die vom User gesetzte Priorität der LVA berücksichtigt.", true);
        addText("\n\nIm Kalender fügst du neue Termine auch über das Popup nach dem Rechtsklick ein.\n" +
                "Du kannst dir private Termine, oder LVA-Termine erstellen. Es ist auch möglich Tage " +
                "als frei zu markieren.", false);
        addText("Außerdem kannst du dich unter Studienverlauf -> Anmeldungen automatisch für Prüfungen " +
                "anmelden, falls du die Daten im Tiss gesetzt hast. Wenn du auf aktualisieren klickst, " +
                "werden alle Prüfungen zu allen LVAs angezeigt, die du deinem Studienverlauf hinzugefügt hast.", false);
        addText("Wenn du auf anmelden klickst, wird die Prüfung in die Liste der ausstehenden Anmeldungen aufgenommen.",false);

                addRow(goBack,null,true,false);



        addRow(null,progressFurther,false,false);



    }
    private void setWaiting(boolean b){
        progressFurther.setEnabled(!b);
        progressBar.setVisible(b);
        tissPassword.setEnabled(!b);
        tissUsername.setEnabled(!b);
        studyDrop.setEnabled(!b);
    }
}
