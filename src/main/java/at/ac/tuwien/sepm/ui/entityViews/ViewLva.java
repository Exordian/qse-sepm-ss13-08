package at.ac.tuwien.sepm.ui.entityViews;

import at.ac.tuwien.sepm.entity.LVA;
import at.ac.tuwien.sepm.entity.MetaLVA;
import at.ac.tuwien.sepm.service.LVAService;
import at.ac.tuwien.sepm.service.MetaLVAService;
import at.ac.tuwien.sepm.service.Semester;
import at.ac.tuwien.sepm.service.ServiceException;
import at.ac.tuwien.sepm.service.impl.ValidationException;
import at.ac.tuwien.sepm.ui.SmallInfoPanel;
import at.ac.tuwien.sepm.ui.StandardSimpleInsidePanel;
import at.ac.tuwien.sepm.ui.UI;
import at.ac.tuwien.sepm.ui.template.PanelTube;
import at.ac.tuwien.sepm.ui.template.SelectItem;
import at.ac.tuwien.sepm.ui.template.WideComboBox;
import com.toedter.calendar.JYearChooser;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.HashMap;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Flo
 * Date: 04.06.13
 * Time: 13:58
 * To change this template use File | Settings | File Templates.
 */
@UI
public class ViewLva extends StandardSimpleInsidePanel {
    private JButton save;

    private JComboBox metaLVA;
    private JYearChooser year;
    private JComboBox semester;
    private JCheckBox inStudyProgress;
    private JTextArea goals;
    private JTextArea content;
    private JTextArea additionalInfo1;
    private JTextArea additionalInfo2;
    private JTextField institute;
    private JTextField language;
    private JSpinner grade;

    private LVA lva;
    private LVAService lvaService;
    private MetaLVAService metaLVAService;

    private Logger log = LogManager.getLogger(this.getClass().getSimpleName());
    private JLabel yearLabel;
    private JLabel semesterLabel;
    private JLabel instituteLabel;
    private JLabel metaLVALabel;
    private JLabel inStudyProgressLabel;
    private JLabel languageLabel;
    private JLabel gradeLabel;

    private List<MetaLVA> allMetaLVAs;
    private HashMap<Integer,Integer> keyIndexMetaLVAJCombo;
    @Autowired
    public ViewLva(LVAService lvaService, MetaLVAService metaLVAService) {
        this.lvaService=lvaService;
        this.metaLVAService=metaLVAService;
        init();
        addImage();
        lva = new LVA();
        addTitle("Neue Lva");
        addReturnButton();
        addContent();
        addButtons();
        this.repaint();
        this.revalidate();
    }

    public void setLva(LVA lva) {
        if (lva == null) {
            this.lva=new LVA();
            changeTitle("Neue Lva");
            inStudyProgress.setSelected(false);
            additionalInfo1.setText("Bitte Infos einfügen");
            additionalInfo2.setText("Bitte Infos einfügen");
            content.setText("Bitte Inhalt einfügen");
            semester.setSelectedIndex(1);
            year.setYear(2013);
            goals.setText("Bitte Ziele einfügen");
        } else {
            this.lva=lva;
            changeTitle(lva.getMetaLVA().getName());
            inStudyProgress.setSelected(lva.isInStudyProgress());
            additionalInfo1.setText(lva.getAdditionalInfo1());
            additionalInfo2.setText(lva.getAdditionalInfo2());
            content.setText(lva.getContent());
            language.setText(lva.getLanguage());
            semester.setSelectedItem(lva.getSemester());
            metaLVA.setSelectedItem(lva.getMetaLVA());   //todo
            year.setYear(lva.getYear());
            if (lva.getGrade() != 0) {
                grade.setValue(lva.getGrade());
            }
            goals.setText(lva.getGoals());
            institute.setText(lva.getInstitute());
        }
    }

    private void addButtons() {
        save = new JButton("Speichern");
        save.setFont(standardTextFont);
        save.setBounds((int)simpleWhiteSpace.getX()+(int)simpleWhiteSpace.getWidth()*1/3-170-120, (int)simpleWhiteSpace.getY() + (int)simpleWhiteSpace.getHeight()-60, 130,40);
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    lva.setMetaLVA(((MetaLvaSelectItem) metaLVA.getSelectedItem()).get());
                    lva.setInStudyProgress(inStudyProgress.isSelected());
                    lva.setAdditionalInfo1(additionalInfo1.getText());
                    lva.setAdditionalInfo2(additionalInfo2.getText());
                    lva.setContent(content.getText());
                    lva.setLanguage(language.getText());
                    lva.setDescription("");
                    lva.setSemester(Semester.values()[semester.getSelectedIndex()]);
                    lva.setYear(year.getYear());
                    lva.setGoals(goals.getText());
                    lva.setInstitute(institute.getText());
                    lva.setGrade((int)grade.getValue());
                    if (lva.getId() != 0) {
                        if (lvaService.readById(lva.getId()) != null)
                            lvaService.update(lva);
                    } else {
                        lvaService.create(lva);
                    }
                    PanelTube.backgroundPanel.viewInfoText("Die Lva wurde gespeichert.", SmallInfoPanel.Info);
                    setVisible(false);
                    PanelTube.backgroundPanel.showLastComponent();
                } catch (ServiceException e) {
                    log.error("LvaEntity is invalid.");
                    PanelTube.backgroundPanel.viewInfoText("Die Angaben sind ungültig.", SmallInfoPanel.Error);
                } catch (ValidationException e) {
                    log.error("LvaEntity is invalid.");
                    PanelTube.backgroundPanel.viewInfoText("Die Angaben sind ungültig.", SmallInfoPanel.Error);
                }
            }
        });
        this.add(save);
    }


    private void addContent() {
        int verticalSpace = 10;

        goals = new JTextArea();
        goals.setLineWrap(true);
        goals.setWrapStyleWord(true);
        goals.setFont(standardTextFont);
        goals.setBounds((int)simpleWhiteSpace.getX()+(int)simpleWhiteSpace.getWidth()*1/3, (int)simpleWhiteSpace.getY()+20,(int)simpleWhiteSpace.getWidth()*2/3 - 20,(int)simpleWhiteSpace.getHeight()/4-25);
        goals.setCaretPosition(0);
        JScrollPane scroll = new JScrollPane(goals);
        scroll.setBounds((int)simpleWhiteSpace.getX()+(int)simpleWhiteSpace.getWidth()*1/3, (int)simpleWhiteSpace.getY()+20,(int)simpleWhiteSpace.getWidth()*2/3 - 20,(int)simpleWhiteSpace.getHeight()/4-25);
        this.add(scroll);

        content = new JTextArea();
        content.setLineWrap(true);
        content.setWrapStyleWord(true);
        content.setFont(standardTextFont);
        content.setBounds(goals.getX(), goals.getY()+goals.getHeight()+20,(int)simpleWhiteSpace.getWidth()*2/3 - 20,(int)simpleWhiteSpace.getHeight()/4-25);
        content.setCaretPosition(0);
        scroll = new JScrollPane(content);
        scroll.setBounds(goals.getX(), goals.getY()+goals.getHeight()+20,(int)simpleWhiteSpace.getWidth()*2/3 - 20,(int)simpleWhiteSpace.getHeight()/4-25);
        this.add(scroll);

        additionalInfo1 = new JTextArea();
        additionalInfo1.setLineWrap(true);
        additionalInfo1.setWrapStyleWord(true);
        additionalInfo1.setFont(standardTextFont);
        additionalInfo1.setBounds(goals.getX(), content.getY()+content.getHeight()+20,(int)simpleWhiteSpace.getWidth()*2/3 - 20,(int)simpleWhiteSpace.getHeight()/4-25);
        additionalInfo1.setCaretPosition(0);
        scroll = new JScrollPane(additionalInfo1);
        scroll.setBounds(goals.getX(), content.getY()+content.getHeight()+20,(int)simpleWhiteSpace.getWidth()*2/3 - 20,(int)simpleWhiteSpace.getHeight()/4-25);
        this.add(scroll);

        additionalInfo2 = new JTextArea();
        additionalInfo2.setLineWrap(true);
        additionalInfo2.setWrapStyleWord(true);
        additionalInfo2.setFont(standardTextFont);
        additionalInfo2.setBounds(goals.getX(), additionalInfo1.getY()+additionalInfo1.getHeight()+20,(int)simpleWhiteSpace.getWidth()*2/3 - 20,(int)simpleWhiteSpace.getHeight()/4-25);
        additionalInfo2.setCaretPosition(0);
        scroll = new JScrollPane(additionalInfo2);
        scroll.setBounds(goals.getX(), additionalInfo1.getY()+additionalInfo1.getHeight()+20,(int)simpleWhiteSpace.getWidth()*2/3 - 20,(int)simpleWhiteSpace.getHeight()/4-25);
        this.add(scroll);

        yearLabel = new JLabel("Jahr:");
        yearLabel.setFont(standardTextFont);
        yearLabel.setBounds((int)simpleWhiteSpace.getX() + 20,(int)simpleWhiteSpace.getY() + 10,130,25);
        this.add(yearLabel);

        year = new JYearChooser();
        year.setYear(2013);
        year.setFont(standardTextFont);
        year.setBounds(yearLabel.getX() + yearLabel.getWidth() + 20, yearLabel.getY(), 65,25);
        this.add(year);

        semesterLabel = new JLabel("Semester:");
        semesterLabel.setFont(standardTextFont);
        semesterLabel.setBounds(yearLabel.getX(), yearLabel.getY() + yearLabel.getHeight() + verticalSpace, 130,25);
        this.add(semesterLabel);

        semester = new WideComboBox();
        for (Semester t : Semester.values()) {
            semester.addItem(t);
        }
        semester.setFont(standardTextFont);
        semester.setBounds(semesterLabel.getX() + semesterLabel.getWidth() + 20, semesterLabel.getY(), 100,25);
        this.add(semester);

        instituteLabel = new JLabel("Institut:");
        instituteLabel.setFont(standardTextFont);
        instituteLabel.setBounds(semesterLabel.getX(), semesterLabel.getY() + semesterLabel.getHeight() + verticalSpace, 130,25);
        this.add(instituteLabel);

        institute = new JTextField();
        institute.setFont(standardTextFont);
        institute.setBounds(instituteLabel.getX() + instituteLabel.getWidth() + 20, instituteLabel.getY(), 100,25);
        this.add(institute);

        metaLVALabel = new JLabel("MetaLVA:");
        metaLVALabel.setFont(standardTextFont);
        metaLVALabel.setBounds(instituteLabel.getX(), instituteLabel.getY() + instituteLabel.getHeight() + verticalSpace, 130,25);
        this.add(metaLVALabel);

        metaLVA = new WideComboBox();

        try {
            allMetaLVAs =metaLVAService.readAll();
            for (MetaLVA t : allMetaLVAs) {
                metaLVA.addItem(new MetaLvaSelectItem(t));
            }
        } catch (ServiceException e) {
            log.error("Exception: "+ e.getMessage());
        } catch (ValidationException e) {
            log.error("Exception: " + e.getMessage());
        }
        metaLVA.setFont(standardTextFont);
        metaLVA.setBounds(metaLVALabel.getX() + metaLVALabel.getWidth() + 20, metaLVALabel.getY(), 100, 25);
        metaLVA.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent itemEvent) {
                if (itemEvent.getStateChange() == ItemEvent.SELECTED) {
                    changeTitle(itemEvent.getItem().toString());
                }
            }
        });
        this.add(metaLVA);

        inStudyProgressLabel = new JLabel("Im Studienprogress:");
        inStudyProgressLabel.setFont(standardTextFont);
        inStudyProgressLabel.setBounds(metaLVALabel.getX(), metaLVALabel.getY() + metaLVALabel.getHeight() + verticalSpace, 170,25);
        this.add(inStudyProgressLabel);

        inStudyProgress = new JCheckBox();
        inStudyProgress.setBackground(new Color(0,0,0,0));
        inStudyProgress.setBounds(inStudyProgressLabel.getX() + inStudyProgressLabel.getWidth() + 20 -30, inStudyProgressLabel.getY(), 20, 20);
        this.add(inStudyProgress);

        languageLabel = new JLabel("Sprache:");
        languageLabel.setFont(standardTextFont);
        languageLabel.setBounds(inStudyProgressLabel.getX(), inStudyProgressLabel.getY() + inStudyProgressLabel.getHeight() + verticalSpace, 130,25);
        this.add(languageLabel);

        language = new JTextField();
        language.setFont(standardTextFont);
        language.setBounds(languageLabel.getX() + languageLabel.getWidth() + 20, languageLabel.getY(), 100,25);
        this.add(language);


        gradeLabel = new JLabel("Note:");
        gradeLabel.setFont(standardTextFont);
        gradeLabel.setBounds(languageLabel.getX(), languageLabel.getY() + languageLabel.getHeight() + verticalSpace*2, 130,25);
        this.add(gradeLabel);

        grade = new JSpinner();
        grade.setModel(new SpinnerNumberModel(5,1,5,1));
        grade.setFont(standardTextFont);
        grade.setBounds(gradeLabel.getX() + gradeLabel.getWidth() + 20, gradeLabel.getY(), 50,25);
        this.add(grade);
    }

    private static class MetaLvaSelectItem extends SelectItem<MetaLVA> {
        MetaLvaSelectItem(MetaLVA item) {
            super(item);
        }

        @Override
        public String toString() {
            return item.getName();
        }
    }
}
