package at.ac.tuwien.sepm.ui.entityViews;

import at.ac.tuwien.sepm.entity.MetaLVA;
import at.ac.tuwien.sepm.entity.Module;
import at.ac.tuwien.sepm.service.*;
import at.ac.tuwien.sepm.service.impl.ValidationException;
import at.ac.tuwien.sepm.ui.StandardSimpleInsidePanel;
import at.ac.tuwien.sepm.ui.UI;
import at.ac.tuwien.sepm.ui.template.PanelTube;
import at.ac.tuwien.sepm.ui.template.SelectItem;
import at.ac.tuwien.sepm.ui.template.WideComboBox;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created with IntelliJ IDEA.
 * User: Flo
 * Date: 04.06.13
 * Time: 13:57
 * To change this template use File | Settings | File Templates.
 */
@UI
public class ViewMetaLva extends StandardSimpleInsidePanel {
    private JLabel nrLabel;
    private JTextField nr;

    private JLabel ectsLabel;
    private JSpinner ects;

    private JLabel typeLabel;
    private JComboBox type;

    private JLabel priorityLabel;
    private JSpinner priority;

    private JLabel semestersOfferedLabel;
    private JComboBox semestersOffered;

    private JLabel moduleLabel;
    private JComboBox moduleDropdown;

    private JLabel completedLabel;
    private JCheckBox completed;

    private JButton save;

    private MetaLVAService metaLVAService;
    private ModuleService moduleService;
    private MetaLVA metaLVA;

    private Logger log = LogManager.getLogger(this.getClass().getSimpleName());

    @Autowired
    public ViewMetaLva(MetaLVAService metaLVAService, ModuleService moduleService) {
        this.metaLVAService=metaLVAService;
        this.moduleService=moduleService;
        init();
        addImage();
        this.metaLVA = new MetaLVA();
        addEditableTitle("Neue Lva");
        addReturnButton();
        addContent();
        addButtons();
        this.repaint();
        this.revalidate();
    }

    private void addButtons() {
        save = new JButton("Speichern");
        save.setFont(standardTextFont);
        save.setBounds((int)simpleWhiteSpace.getX()+(int)simpleWhiteSpace.getWidth()*1/3-170-120, (int)simpleWhiteSpace.getY() + (int)simpleWhiteSpace.getHeight()-60, 130,40);
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    metaLVA.setName(title.getText());
                    metaLVA.setCompleted(completed.isSelected());
                    metaLVA.setECTS(((Number)ects.getValue()).floatValue());
                    metaLVA.setModule(((ModuleSelectItem) moduleDropdown.getSelectedItem()).get().getId());
                    metaLVA.setNr(nr.getText());
                    metaLVA.setPriority(((Number)priority.getValue()).floatValue());
                    metaLVA.setSemestersOffered((Semester)semestersOffered.getSelectedItem());
                    metaLVA.setType((LvaType)type.getSelectedItem());

                    if (metaLVA.getId() != 0) {
                        if (metaLVAService.readById(metaLVA.getId()) != null)
                            metaLVAService.update(metaLVA);
                    } else {
                        metaLVAService.create(metaLVA);
                    }
                    setVisible(false);
                    PanelTube.backgroundPanel.showLastComponent();
                } catch (ServiceException e) {
                    log.error("MetaLvaEntity is invalid.");
                    JOptionPane.showMessageDialog(ViewMetaLva.this, "Die Angaben sind ungültig.", "Fehler", JOptionPane.ERROR_MESSAGE);
                } catch (ValidationException e) {
                    log.error("MetaLvaEntity is invalid.");
                    JOptionPane.showMessageDialog(ViewMetaLva.this, "Die Angaben sind ungültig.", "Fehler", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        this.add(save);
    }

    private void addContent() {
        int verticalSpace = 10;

        nrLabel = new JLabel("Lva Nummer:");
        nrLabel.setFont(standardTextFont);
        nrLabel.setBounds((int)simpleWhiteSpace.getX() + 20,(int)simpleWhiteSpace.getY() + 10,140,25);
        this.add(nrLabel);

        nr = new JTextField();
        nr.setFont(standardTextFont);
        nr.setBounds(nrLabel.getX() + nrLabel.getWidth() + 20, nrLabel.getY(), 140,25);
        this.add(nr);

        ectsLabel = new JLabel("ECTs:");
        ectsLabel.setFont(standardTextFont);
        ectsLabel.setBounds(nrLabel.getX(), nrLabel.getY() + nrLabel.getHeight() + verticalSpace, 140,25);
        this.add(ectsLabel);

        ects = new JSpinner();
        ects.setModel(new SpinnerNumberModel(0.,0.,30.,0.1));
        ects.setEditor(new JSpinner.NumberEditor(ects, "0.#"));
        ects.setFont(standardTextFont);
        ects.setBounds(ectsLabel.getX() + ectsLabel.getWidth() + 20, ectsLabel.getY(), 140,25);
        this.add(ects);

        typeLabel = new JLabel("Typ:");
        typeLabel.setFont(standardTextFont);
        typeLabel.setBounds(ectsLabel.getX(), ectsLabel.getY() + ectsLabel.getHeight() + verticalSpace, 140,25);
        this.add(typeLabel);

        type = new WideComboBox();
        for (LvaType t : LvaType.values()) {
            type.addItem(t);
        }
        type.setFont(standardTextFont);
        type.setBounds(typeLabel.getX() + typeLabel.getWidth() + 20, typeLabel.getY(), 140,25);
        this.add(type);

        semestersOfferedLabel = new JLabel("angebotene Semester:");
        semestersOfferedLabel.setFont(standardTextFont);
        semestersOfferedLabel.setBounds(typeLabel.getX(), typeLabel.getY() + typeLabel.getHeight() + verticalSpace, 140,25);
        this.add(semestersOfferedLabel);

        semestersOffered = new WideComboBox();
        for(Semester s : Semester.values())
            semestersOffered.addItem(s);
        semestersOffered.setFont(standardTextFont);
        semestersOffered.setBounds(semestersOfferedLabel.getX() + semestersOfferedLabel.getWidth() + 20, semestersOfferedLabel.getY(), 140,25);
        this.add(semestersOffered);

        moduleLabel = new JLabel("Gehört zu Modul:");
        moduleLabel.setFont(standardTextFont);
        moduleLabel.setBounds(semestersOfferedLabel.getX(), semestersOfferedLabel.getY() + semestersOfferedLabel.getHeight() + verticalSpace, 140,25);
        this.add(moduleLabel);

        moduleDropdown = new WideComboBox();
        try {
            for (Module m :  moduleService.readAll())
                moduleDropdown.addItem(new ModuleSelectItem(m));
        } catch (ServiceException e) {
            log.error("Exception: " +e.getMessage());
        }
        moduleDropdown.setFont(standardTextFont);
        moduleDropdown.setBounds(moduleLabel.getX() + moduleLabel.getWidth() + 20, moduleLabel.getY(), 140, 25);
        this.add(moduleDropdown);

        priorityLabel = new JLabel("Priorität:");
        priorityLabel.setFont(standardTextFont);
        priorityLabel.setBounds(moduleLabel.getX(), moduleLabel.getY() + moduleLabel.getHeight() + verticalSpace, 140,25);
        this.add(priorityLabel);

        priority = new JSpinner();
        priority.setModel(new SpinnerNumberModel(5.,0.,10.,0.5));
        priority.setEditor(new JSpinner.NumberEditor(priority, "0.#"));
        priority.setFont(standardTextFont);
        priority.setBounds(priorityLabel.getX() + priorityLabel.getWidth() + 20, priorityLabel.getY(), 140,25);
        this.add(priority);

        completedLabel = new JLabel("Abgeschlossen:");
        completedLabel.setFont(standardTextFont);
        completedLabel.setBounds(priorityLabel.getX(), priorityLabel.getY() + priorityLabel.getHeight() + verticalSpace, 140,25);
        this.add(completedLabel);

        completed = new JCheckBox();
        completed.setBackground(new Color(0,0,0,0));
        completed.setBounds(completedLabel.getX() + completedLabel.getWidth() + 20, completedLabel.getY(), 20, 20);
        this.add(completed);
    }

    public void setMetaLva(MetaLVA metaLVA) {
        if (metaLVA == null) {
            this.metaLVA = new MetaLVA();
            changeTitle("Neue Lva");
            nr.setText("");
            ects.setValue(0);
            type.setSelectedIndex(0);
            priority.setValue(1);
            semestersOffered.setSelectedIndex(0);
            moduleDropdown.setSelectedIndex(0);
            completed.setSelected(false);
        } else {
            this.metaLVA=metaLVA;
            changeTitle(metaLVA.getName());
            nr.setText(metaLVA.getNr());
            ects.setValue(metaLVA.getECTS());
            type.setSelectedItem(metaLVA.getType());
            priority.setValue(metaLVA.getPriority());
            semestersOffered.setSelectedItem(metaLVA.getSemestersOffered());
            for(int i = 0; i < moduleDropdown.getModel().getSize(); i++)
                if (((ModuleSelectItem) moduleDropdown.getItemAt(i)).get().getId() == metaLVA.getModule()) {
                    moduleDropdown.setSelectedIndex(i);
                    break;
                }
            completed.setSelected(metaLVA.isCompleted());
        }
    }

    private static class ModuleSelectItem extends SelectItem<Module> {
        ModuleSelectItem(Module item) {
            super(item);
        }

        @Override
        public String toString() {
            return item.getName();
        }


    }
}
