package at.ac.tuwien.sepm.ui.lehrangebot;

import at.ac.tuwien.sepm.entity.LVA;
import at.ac.tuwien.sepm.entity.MetaLVA;
import at.ac.tuwien.sepm.entity.Module;
import at.ac.tuwien.sepm.service.MetaLVAService;
import at.ac.tuwien.sepm.service.ModuleService;
import at.ac.tuwien.sepm.service.ServiceException;
import at.ac.tuwien.sepm.service.impl.ValidationException;
import at.ac.tuwien.sepm.ui.StandardInsidePanel;
import at.ac.tuwien.sepm.ui.UI;
import at.ac.tuwien.sepm.ui.metaLva.MetaLVADisplayPanel;
import at.ac.tuwien.sepm.ui.metaLva.ModuleDisplayPanel;
import at.ac.tuwien.sepm.ui.template.PanelTube;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;


/**
 * Created with IntelliJ IDEA.
 * User: Georg Plaz
 * Date: 05.06.13
 * Time: 19:05
 * To change this template use File | Settings | File Templates.
 */
@UI
public class ModulePanel extends StandardInsidePanel {
    private ModuleService moduleService;
    //private LVAService lvaService;
    private MetaLVAService metaLVAService;

    private List<Module> modules;
    private List<MetaLVA> metaLVAs;
    private List<LVA> lvas;

    private ModuleDisplayPanel modulePane;
    private MetaLVADisplayPanel metaPane;
   // private LvaDisplayPanel lvaPane;

    private Rectangle paneModule = new Rectangle(12,55,490,413);
    private Rectangle paneMeta = new Rectangle(510,55,490,413);
    //private Rectangle paneLva = new Rectangle(510,55,490,413);

    private JButton refresh;
    private JButton addLva;
    private JButton addModule;

    private Logger log = LogManager.getLogger(this.getClass().getSimpleName());


    @Autowired
    public ModulePanel(ModuleService moduleService, MetaLVAService metaLVAService) {
        this.setLayout(null);
        this.setOpaque(false);
        loadFonts();
        setBounds((int) startCoordinateOfWhiteSpace.getX(), (int) startCoordinateOfWhiteSpace.getY(),(int) whiteSpace.getWidth(),(int) whiteSpace.getHeight());

        this.moduleService=moduleService;
        //this.lvaService=lvaService;
        this.metaLVAService=metaLVAService;

        addContent();
        addTitles();
        addButtons();
    }

    private void addTitles() {
        JLabel metaTitle = new JLabel("Module");
        metaTitle.setFont(standardSmallerTitleFont);
        metaTitle.setBounds((int)whiteSpace.getWidth()/2-(int)paneMeta.getWidth()/2-75, 5, 150,30);
        this.add(metaTitle);

        JLabel lvatitle = new JLabel("MetaLVAs");
        lvatitle.setFont(standardSmallerTitleFont);
        lvatitle.setBounds((int)whiteSpace.getWidth()/2+(int)paneMeta.getWidth()/2-90, 5, 180,30);
        this.add(lvatitle);
    }

    private void addButtons() {
        refresh = new JButton("Aktualisieren");
        refresh.setFont(standardButtonFont);
        refresh.setBounds((int)whiteSpace.getWidth()/2-75, (int)paneMeta.getY() + (int)paneMeta.getHeight()+8, 150,30);
        refresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ModulePanel.this.refresh();
            }
        });
        this.add(refresh);

        addModule = new JButton("Erstellen");
        addModule.setFont(standardButtonFont);
        addModule.setBounds((int) paneMeta.getX(), (int) paneMeta.getY() + (int) paneMeta.getHeight() + 8, 150, 30);
        addModule.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PanelTube.backgroundPanel.viewMetaLva(null);
            }
        });
        this.add(addModule);
        addLva = new JButton("Erstellen");
        addLva.setFont(standardButtonFont);
        addLva.setBounds((int)paneMeta.getX() + (int)paneMeta.getWidth()-150, (int)paneMeta.getY() + (int)paneMeta.getHeight()+8, 150,30);
        addLva.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {//todo
                try {
                    if (metaLVAService.readAll() == null) {
                        JOptionPane.showMessageDialog(ModulePanel.this, "Es muss eine MetaLva vorhanden sein, um eine semesterspezifische Lva erzeugen zu können.", "Fehler", JOptionPane.ERROR_MESSAGE);
                    } else {
                        PanelTube.backgroundPanel.viewLva(null);
                    }
                } catch (ServiceException e1) {
                    log.error("Exception: "+ e1.getMessage());
                } catch (ValidationException e1) {
                    log.error("Exception: "+ e1.getMessage());
                }
            }
        });
        this.add(addLva);
    }

    private void addContent() {
        try {
            metaLVAs = new ArrayList<MetaLVA>();
            //metaLVAs = metaLVAService.readAll();
            modules = moduleService.readAll();


            metaPane = new MetaLVADisplayPanel(new ArrayList<MetaLVA>(0), (int)paneMeta.getWidth(), (int)paneMeta.getHeight());
            metaPane.setBounds(paneMeta);

            modulePane = new ModuleDisplayPanel(modules,metaPane, (int) paneModule.getWidth(), (int) paneModule.getHeight());
            modulePane.setBounds(paneModule);

            this.add(modulePane);
            this.add(metaPane);


        } catch (ServiceException e) {
            log.error("Exception: "+ e.getMessage());
        }
    }


    @Override
    public void refresh() {
        try {
            modulePane.refresh(moduleService.readAll());
            //lvaPane.refresh(lvaService.readAll());
        } catch (ServiceException e) {
            log.error("Exception: " + e.getMessage());
        }
    }
}
