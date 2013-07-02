package at.ac.tuwien.sepm.ui.entityViews;

import at.ac.tuwien.sepm.entity.LVA;
import at.ac.tuwien.sepm.entity.MetaLVA;
import at.ac.tuwien.sepm.service.*;
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
import org.joda.time.DateTime;
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

    private JComboBox metaLVAInput;
    private JYearChooser year;
    private JComboBox semesterInput;
    private JCheckBox inStudyProgressInput;
    private JTextArea goals;
    private JTextArea content;
    private JTextArea additionalInfo1;
    private JTextArea additionalInfo2;
    private JTextField institute;
    private JTextField languageInput;
    private JSpinner grade;
    private WideComboBox gradeInput;

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
        metaLVAInput.removeAllItems();
        try {
            allMetaLVAs = metaLVAService.readAll();
            if(allMetaLVAs != null)
                for (MetaLVA t : allMetaLVAs) {
                    metaLVAInput.addItem(new MetaLvaSelectItem(t));
                }
        } catch (ServiceException e) {
            log.error("Exception: "+ e.getMessage());
            PanelTube.backgroundPanel.viewSmallInfoText("Es ist ein Fehler beim Lesen der Datenbank aufgetreten", SmallInfoPanel.Error);
        }
        if (lva == null) {
            this.lva=new LVA();
            changeTitle("Neue Lva");
            inStudyProgressInput.setSelected(false);
            additionalInfo1.setText("Bitte Infos einfügen");
            additionalInfo2.setText("Bitte Infos einfügen");
            content.setText("Bitte Inhalt einfügen");
            gradeInput.setSelectedIndex(0);
            semesterInput.setSelectedIndex(1);
            year.setYear(new DateTime(System.currentTimeMillis()).getYear());
            goals.setText("Bitte Ziele einfügen");
        } else {
            log.info("test");
            this.lva=lva;
            changeTitle(lva.getMetaLVA().getName());
            inStudyProgressInput.setSelected(lva.isInStudyProgress());
            additionalInfo1.setText(lva.getAdditionalInfo1());
            additionalInfo2.setText(lva.getAdditionalInfo2());
            content.setText(lva.getContent());
            languageInput.setText(lva.getLanguage());
            semesterInput.setSelectedItem(lva.getSemester());
            for(int i = 0; i < metaLVAInput.getModel().getSize(); i++){
                if (((MetaLvaSelectItem) metaLVAInput.getItemAt(i)).get().getId() == lva.getMetaLVA().getId()) {
                    metaLVAInput.setSelectedIndex(i);
                    break;
                }
            }
            year.setYear(lva.getYear());
            gradeInput.setSelectedItem(lva.getGradeEnum());
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
                    lva.setMetaLVA(((MetaLvaSelectItem) metaLVAInput.getSelectedItem()).get());
                    lva.setInStudyProgress(inStudyProgressInput.isSelected());
                    lva.setAdditionalInfo1(additionalInfo1.getText());
                    lva.setAdditionalInfo2(additionalInfo2.getText());
                    lva.setContent(content.getText());
                    lva.setLanguage(languageInput.getText());
                    lva.setDescription("");
                    lva.setSemester(Semester.values()[semesterInput.getSelectedIndex()]);
                    lva.setYear(year.getYear());
                    lva.setGoals(goals.getText());
                    lva.setInstitute(institute.getText());
                    lva.setGrade((Grade) gradeInput.getSelectedItem());
                    if (lva.getId() != 0) {
                        if (lvaService.readById(lva.getId()) != null)
                            lvaService.update(lva);
                    } else {
                        lvaService.create(lva);
                    }
                    PanelTube.backgroundPanel.viewSmallInfoText("Die Lva wurde gespeichert.", SmallInfoPanel.Success);
                    setVisible(false);
                    PanelTube.backgroundPanel.showLastComponent();
                } catch (ServiceException e) {
                    log.error("LvaEntity is invalid.");
                    PanelTube.backgroundPanel.viewSmallInfoText("Die Angaben sind ungültig.", SmallInfoPanel.Error);
                } catch (ValidationException e) {
                    log.error("LvaEntity is invalid.");
                    PanelTube.backgroundPanel.viewSmallInfoText("Die Angaben sind ungültig.", SmallInfoPanel.Error);
                }
            }
        });
        this.add(save);
    }


    private void addContent() {
        int bigSpace = 20;
        int smallSpace = 10;
        int labelLeftX = (int)simpleWhiteSpace.getX() + bigSpace;
        int labelLeftWidth = 110;
        int inputLeftX =  labelLeftX+labelLeftWidth+smallSpace;
        int inputLeftWidth=150;
        int inputRightX = inputLeftX+inputLeftWidth+bigSpace;//(int)simpleWhiteSpace.getX()+(int)simpleWhiteSpace.getWidth()*1/3;
        int inputRightWidth=(int)(simpleWhiteSpace.getX()+simpleWhiteSpace.getWidth())-(inputRightX+bigSpace);
        int textHeight = 25;
        
        goals = new JTextArea();
        goals.setLineWrap(true);
        goals.setWrapStyleWord(true);
        goals.setFont(standardTextFont);
        goals.setBounds(inputRightX, (int)simpleWhiteSpace.getY()+bigSpace,inputRightWidth,(int)simpleWhiteSpace.getHeight()/4-25);
        goals.setCaretPosition(0);
        JScrollPane scroll = new JScrollPane(goals);
        scroll.setBounds(inputRightX, (int)simpleWhiteSpace.getY()+bigSpace,inputRightWidth,(int)simpleWhiteSpace.getHeight()/4-25);
        this.add(scroll);

        content = new JTextArea();
        content.setLineWrap(true);
        content.setWrapStyleWord(true);
        content.setFont(standardTextFont);
        content.setBounds(inputRightX, goals.getY()+goals.getHeight()+bigSpace,inputRightWidth,(int)simpleWhiteSpace.getHeight()/4-25);
        content.setCaretPosition(0);
        scroll = new JScrollPane(content);
        scroll.setBounds(inputRightX, goals.getY()+goals.getHeight()+bigSpace,inputRightWidth,(int)simpleWhiteSpace.getHeight()/4-25);
        this.add(scroll);

        additionalInfo1 = new JTextArea();
        additionalInfo1.setLineWrap(true);
        additionalInfo1.setWrapStyleWord(true);
        additionalInfo1.setFont(standardTextFont);
        additionalInfo1.setBounds(inputRightX, content.getY()+content.getHeight()+bigSpace,inputRightWidth,(int)simpleWhiteSpace.getHeight()/4-25);
        additionalInfo1.setCaretPosition(0);
        scroll = new JScrollPane(additionalInfo1);
        scroll.setBounds(inputRightX, content.getY()+content.getHeight()+bigSpace,inputRightWidth,(int)simpleWhiteSpace.getHeight()/4-25);
        this.add(scroll);

        additionalInfo2 = new JTextArea();
        additionalInfo2.setLineWrap(true);
        additionalInfo2.setWrapStyleWord(true);
        additionalInfo2.setFont(standardTextFont);
        additionalInfo2.setBounds(inputRightX, additionalInfo1.getY()+additionalInfo1.getHeight()+bigSpace,inputRightWidth,(int)simpleWhiteSpace.getHeight()/4-25);
        additionalInfo2.setCaretPosition(0);
        scroll = new JScrollPane(additionalInfo2);
        scroll.setBounds(inputRightX, additionalInfo1.getY()+additionalInfo1.getHeight()+bigSpace,inputRightWidth,(int)simpleWhiteSpace.getHeight()/4-25);
        this.add(scroll);

        yearLabel = new JLabel("Jahr:");
        yearLabel.setFont(standardTextFont);
        yearLabel.setBounds(labelLeftX,(int)simpleWhiteSpace.getY() + bigSpace,labelLeftWidth,textHeight);
        this.add(yearLabel);

        year = new JYearChooser();
        year.setYear(2013);
        year.setFont(standardTextFont);
        year.setBounds(inputLeftX, yearLabel.getY(), inputLeftWidth,textHeight);
        this.add(year);

        semesterLabel = new JLabel("Semester:");
        semesterLabel.setFont(standardTextFont);
        semesterLabel.setBounds(labelLeftX, yearLabel.getY() + yearLabel.getHeight() + smallSpace, labelLeftWidth,textHeight);
        this.add(semesterLabel);

        semesterInput = new WideComboBox();
        for (Semester t : Semester.values()) {
            semesterInput.addItem(t);
        }
        semesterInput.setFont(standardTextFont);
        semesterInput.setBounds(inputLeftX, semesterLabel.getY(), inputLeftWidth,textHeight);
        this.add(semesterInput);

        instituteLabel = new JLabel("Institut:");
        instituteLabel.setFont(standardTextFont);
        instituteLabel.setBounds(labelLeftX, semesterLabel.getY() + semesterLabel.getHeight() + smallSpace, labelLeftWidth,textHeight);
        this.add(instituteLabel);

        institute = new JTextField();
        institute.setFont(standardTextFont);
        institute.setBounds(inputLeftX, instituteLabel.getY(), inputLeftWidth,textHeight);
        this.add(institute);

        metaLVALabel = new JLabel("MetaLVA:");
        metaLVALabel.setFont(standardTextFont);
        metaLVALabel.setBounds(labelLeftX, instituteLabel.getY() + instituteLabel.getHeight() + smallSpace, labelLeftWidth,textHeight);
        this.add(metaLVALabel);

        metaLVAInput = new WideComboBox();

        try {
            allMetaLVAs = metaLVAService.readAll();
            if(allMetaLVAs != null)
                for (MetaLVA t : allMetaLVAs) {
                    metaLVAInput.addItem(new MetaLvaSelectItem(t));
                }
        } catch (ServiceException e) {
            log.error("Exception: "+ e.getMessage());
            PanelTube.backgroundPanel.viewSmallInfoText("Es ist ein Fehler beim Lesen der Datenbank aufgetreten", SmallInfoPanel.Error);
        }
        metaLVAInput.setFont(standardTextFont);
        metaLVAInput.setBounds(inputLeftX, metaLVALabel.getY(), inputLeftWidth,textHeight);
        metaLVAInput.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent itemEvent) {
                if (itemEvent.getStateChange() == ItemEvent.SELECTED) {
                    changeTitle(itemEvent.getItem().toString());
                }
            }
        });
        this.add(metaLVAInput);

        inStudyProgressLabel = new JLabel("Im Studienprogress:");
        inStudyProgressLabel.setFont(standardTextFont);
        inStudyProgressLabel.setBounds(labelLeftX, metaLVALabel.getY() + metaLVALabel.getHeight() + smallSpace, inStudyProgressLabel.getPreferredSize().width,textHeight);
        this.add(inStudyProgressLabel);

        inStudyProgressInput = new JCheckBox();
        inStudyProgressInput.addChangeListener(dONTFUCKINGBUGSWINGListener());
        inStudyProgressInput.setBackground(new Color(0, 0, 0, 0));
        inStudyProgressInput.setBounds(inStudyProgressLabel.getX()+ inStudyProgressLabel.getWidth()+smallSpace, inStudyProgressLabel.getY(), inStudyProgressInput.getPreferredSize().width,textHeight);
        this.add(inStudyProgressInput);

        languageLabel = new JLabel("Sprache:");
        languageLabel.setFont(standardTextFont);
        languageLabel.setBounds(labelLeftX, inStudyProgressLabel.getY() + inStudyProgressLabel.getHeight() + smallSpace, labelLeftWidth,textHeight);
        this.add(languageLabel);

        languageInput = new JTextField();
        languageInput.setFont(standardTextFont);
        languageInput.setBounds(inputLeftX, languageLabel.getY(), inputLeftWidth,textHeight);
        this.add(languageInput);

        gradeLabel = new JLabel("Note:");
        gradeLabel.setFont(standardTextFont);
        gradeLabel.setBounds(labelLeftX, languageLabel.getY() + languageLabel.getHeight() + smallSpace*2, labelLeftWidth,textHeight);
        this.add(gradeLabel);

        /*grade = new JSpinner();
        grade.setModel(new SpinnerNumberModel(5,1,5,1));
        grade.setFont(standardTextFont);
        grade.setBounds(gradeLabel.getX() + gradeLabel.getWidth() + 20, gradeLabel.getY(), 50,textHeight);
        this.add(grade);*/
        gradeInput = Grade.getComboBox();
        gradeInput.setFont(standardTextFont);
        gradeInput.setBounds(inputLeftX, gradeLabel.getY(), inputLeftWidth,textHeight);
        this.add(gradeInput);
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
