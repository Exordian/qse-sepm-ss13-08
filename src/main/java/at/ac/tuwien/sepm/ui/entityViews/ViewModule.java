package at.ac.tuwien.sepm.ui.entityViews;

import at.ac.tuwien.sepm.entity.MetaLVA;
import at.ac.tuwien.sepm.entity.Module;
import at.ac.tuwien.sepm.service.EscapeException;
import at.ac.tuwien.sepm.service.MetaLVAService;
import at.ac.tuwien.sepm.service.ModuleService;
import at.ac.tuwien.sepm.service.ServiceException;
import at.ac.tuwien.sepm.service.impl.ValidationException;
import at.ac.tuwien.sepm.ui.StandardSimpleInsidePanel;
import at.ac.tuwien.sepm.ui.UI;
import at.ac.tuwien.sepm.ui.metaLva.MetaLVADisplayPanel;
import at.ac.tuwien.sepm.ui.template.HintTextField;
import at.ac.tuwien.sepm.ui.template.PanelTube;
import at.ac.tuwien.sepm.ui.template.SelectItem;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Georg
 * Date: 04.06.13
 * Time: 13:57
 * To change this template use File | Settings | File Templates.
 */
@UI
public class ViewModule extends StandardSimpleInsidePanel {
    //private JLabel nameLabel;
    //private JTextField name;

    private JLabel descriptionLabel;
    private JTextArea description;

    private JTextArea priorityLabel;
    private HintTextField priority;

    private JLabel metaLVALabel;
    private MetaLVADisplayPanel metaLVADisplayPanel;

    private Rectangle paneModule = new Rectangle(520,95,490,413);

    private JButton save;

    private ModuleService moduleService;
    private MetaLVAService metaLVAService;
    private Module module;

    private Logger log = LogManager.getLogger(this.getClass().getSimpleName());

    @Autowired
    public ViewModule(ModuleService moduleService,MetaLVAService metaLVAService) {
        this.moduleService=moduleService;
        this.metaLVAService=metaLVAService;
        init();
        addImage();
        this.module = new Module();
        module.setMetaLvas(new ArrayList<MetaLVA>(0));
        addEditableTitle("Neues Modul");
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

                    if(!priority.getText().trim().equals("")){
                        float newPriority = Float.parseFloat(priority.getText());

                        for(MetaLVA mLVA : module.getMetaLvas()){
                            if(mLVA.getPriority()>(newPriority+0.001) || mLVA.getPriority()<(newPriority-0.001)){
                                int optionChosen = JOptionPane.showConfirmDialog(ViewModule.this, "Wollen sie wirklich die Priorität aller LVAs aus dem Modul: "+module.getName()+" auf " + newPriority + " setzen?", "Fehler", JOptionPane.OK_CANCEL_OPTION);
                                if(optionChosen==JOptionPane.OK_OPTION){
                                    for(MetaLVA metaLVA:module.getMetaLvas()){
                                        metaLVA.setPriority(newPriority);
                                        metaLVAService.update(metaLVA);
                                    }
                                }else{
                                    throw new EscapeException();
                                }
                                break;
                            }
                        }
                    }
                    module.setName(title.getText());
                    module.setDescription(description.getText());
                    if (module.getId() != null) {
                        moduleService.update(module);
                    } else {
                        moduleService.create(module);
                    }
                    setVisible(false);
                    PanelTube.backgroundPanel.showLastComponent();
                } catch (ServiceException e) {
                    log.error("Module is invalid.");
                    JOptionPane.showMessageDialog(ViewModule.this, "Die Angaben sind ungültig.", "Fehler", JOptionPane.ERROR_MESSAGE);
                } catch (ValidationException e) {
                    log.error("Module is invalid.");
                    JOptionPane.showMessageDialog(ViewModule.this, "Die Angaben sind ungültig.", "Fehler", JOptionPane.ERROR_MESSAGE);
                } catch (NumberFormatException e){
                    log.error("Input priority is invalid.");
                    JOptionPane.showMessageDialog(ViewModule.this, "Die angegebene Priorität ist ungültig!", "Fehler", JOptionPane.ERROR_MESSAGE);
                } catch (EscapeException ignore){
                    log.info("user cancelled saving");
                }
            }
        });
        this.add(save);
    }

    private void addContent() {
        int verticalSpace = 10;

        descriptionLabel = new JLabel("Beschreibung:");
        descriptionLabel.setFont(standardTextFont);
        descriptionLabel.setBounds((int) simpleWhiteSpace.getX() + 20, (int) paneModule.getY(), 110, 25);
        this.add(descriptionLabel);

        description = new JTextArea();
        description.setFont(standardTextFont);
        description.setBounds(descriptionLabel.getX() + descriptionLabel.getWidth() + 20, descriptionLabel.getY(), 250, 25 * 10);
        description.setBorder(new JTextField().getBorder());
        this.add(description);

        priorityLabel = new JTextArea("Priorität\nder LVAs:");
        priorityLabel.setEditable(false);
        priorityLabel.setCursor(null);
        priorityLabel.setOpaque(false);
        priorityLabel.setFocusable(false);
        priorityLabel.setFont(standardTextFont);
        priorityLabel.setBounds(descriptionLabel.getX(), descriptionLabel.getY() + description.getHeight() + verticalSpace, 110, 25*2);
        this.add(priorityLabel);

        priority = new HintTextField("");
        //priority.setText("5");
        priority.setFont(standardTextFont);
        priority.setBounds(priorityLabel.getX() + priorityLabel.getWidth() + 20, priorityLabel.getY()+(25), 250, 25 );
        this.add(priority);

        metaLVALabel = new JLabel("LVAs in diesem Modul:");
        metaLVALabel.setFont(standardTextFont);
        metaLVALabel.setBounds((int)paneModule.getX(),(int)paneModule.getY(),200,25);
        this.add(metaLVALabel);

        metaLVADisplayPanel = new MetaLVADisplayPanel(module.getMetaLvas(),(int)paneModule.getWidth(),(int)paneModule.getHeight());
        metaLVADisplayPanel.setBounds(metaLVALabel.getX(),metaLVALabel.getY()+metaLVALabel.getHeight(),(int)paneModule.getWidth(),(int)paneModule.getHeight()-(metaLVALabel.getY()+metaLVALabel.getHeight()));
        this.add(metaLVADisplayPanel);

    }

    public void setModule(Module module) {
        priority.setText("");
        if (module == null) {

            this.module = new Module();
            this.module.setMetaLvas(new ArrayList<MetaLVA>(0));
            changeTitle("Neues Modul");
            description.setText("");
            priority.setHint("Keine LVA");
        } else {
            this.module = module;
            changeTitle(module.getName());
            if(module.getMetaLvas().isEmpty()){
                priority.setHint("Keine LVAs in diesem Modul");
            }else{
                boolean prioritiesMatch = true;
                float foundPriority = module.getMetaLvas().get(0).getPriority();
                for(MetaLVA mLVA : module.getMetaLvas()){
                    if(mLVA.getPriority()!=foundPriority){
                        prioritiesMatch=false;
                        break;
                    }
                }
                if(prioritiesMatch){
                    priority.setText("" + foundPriority);
                }else{
                    priority.setHint("verschiedene Werte");
                }
            }
            description.setText(module.getDescription());
        }
        metaLVADisplayPanel.refresh(this.module.getMetaLvas());
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
