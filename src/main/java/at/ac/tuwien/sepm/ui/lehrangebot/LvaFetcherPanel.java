package at.ac.tuwien.sepm.ui.lehrangebot;

import at.ac.tuwien.sepm.entity.Curriculum;
import at.ac.tuwien.sepm.entity.MetaLVA;
import at.ac.tuwien.sepm.entity.Module;
import at.ac.tuwien.sepm.service.LvaFetcherService;
import at.ac.tuwien.sepm.service.MetaLVAService;
import at.ac.tuwien.sepm.service.ModuleService;
import at.ac.tuwien.sepm.service.ServiceException;
import at.ac.tuwien.sepm.service.impl.LVAUtil;
import at.ac.tuwien.sepm.service.impl.ValidationException;
import at.ac.tuwien.sepm.ui.SmallInfoPanel;
import at.ac.tuwien.sepm.ui.StandardInsidePanel;
import at.ac.tuwien.sepm.ui.template.PanelTube;
import at.ac.tuwien.sepm.ui.template.SelectItem;
import at.ac.tuwien.sepm.ui.UI;
import net.miginfocom.swing.MigLayout;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

@UI
public class LvaFetcherPanel extends StandardInsidePanel {
    private static Logger logger = LogManager.getLogger(LvaFetcherPanel.class);

    private static final String MERGE_FRAME_TITLE = "Konflikte beim Speichern der Daten";
    private static final String MERGE_FRAME_MESSAGE = "Einige Daten sind bereits abgespeichert, ein erneutes speichern " +
            "würde diese\nDaten überschreiben. Wie wollen Sie vorgehen?";
    private static final Object[] MERGE_FRAME_BUTTON_TEXT = new Object[] {
            "Alte Daten beibehalten",
            "Alte Daten überschreiben",
            "Daten zusammenführen"};

    private LvaFetcherService lvaFetcherService;
    private MetaLVAService metaLVAService;
    private ModuleService moduleService;

    private JTree tissTree;
    private JScrollPane treeView;
    private JComboBox<CurriculumSelectItem> academicPrograms;
    private JButton fetchProgram;
    private JButton importb;
    private JProgressBar progressBar;

    private List<Module> currentModules;

    @Autowired
    public LvaFetcherPanel(LvaFetcherService lvaFetcherService, MetaLVAService metaLVAService, ModuleService moduleService) {
        this.lvaFetcherService=lvaFetcherService;
        this.metaLVAService=metaLVAService;
        this.moduleService=moduleService;
        setLayout(new MigLayout());
        loadFonts();
        setBounds((int) startCoordinateOfWhiteSpace.getX(), (int) startCoordinateOfWhiteSpace.getY(),(int) whiteSpace.getWidth(),(int) whiteSpace.getHeight());
        setBackground(Color.WHITE);
        initP();
        revalidate();
        repaint();
    }

    public void initP() {
        academicPrograms = new JComboBox();
        academicPrograms.setFont(standardButtonFont);
        try {
            for(Curriculum c : lvaFetcherService.getAcademicPrograms())
                if (((c.getName().startsWith("Bachelor")) || (c.getName().startsWith("Master"))) && ((c.getName().contains("nformatik") || c.getName().contains("Software")) && !c.getName().contains("Geod")))
                    academicPrograms.addItem(new CurriculumSelectItem(c));
        } catch (ServiceException e) {
            logger.info("no academic prorgams");
        }
        academicPrograms.setMinimumSize(new Dimension((int)this.getBounds().getWidth()-145, 20));

        fetchProgram = new JButton("Studium laden");
        fetchProgram.setFont(standardButtonFont);

        tissTree = new JTree(new DefaultMutableTreeNode("Wähle ein Studium aus"));
        treeView = new JScrollPane(tissTree);
        treeView.setFont(standardTextFont);
        treeView.setMinimumSize(new Dimension((int) this.getBounds().getWidth() - 145, 20));

        fetchProgram.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //performImport();
                refreshTree();
            }
        });

        importb = new JButton("Importieren");
        importb.setFont(standardButtonFont);
        importb.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performImport();
                PanelTube.studienplanPanel.fillTable();
            }
        });
        importb.setEnabled(false);

        progressBar = new JProgressBar();
        progressBar.setVisible(false);

        add(academicPrograms, "push");
        add(fetchProgram, "wrap");
        add(treeView, "grow, push, span, wrap");
        add(importb);
        add(progressBar);
    }

    private int startMergeDialog() {
        return JOptionPane.showOptionDialog(new JFrame(),
                MERGE_FRAME_MESSAGE,
                MERGE_FRAME_TITLE,
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                MERGE_FRAME_BUTTON_TEXT,
                MERGE_FRAME_BUTTON_TEXT[0]);
    }

    private void performImport() {

        TreePath path = tissTree.getSelectionPath();
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) path.getLastPathComponent();
        Object item = selectedNode.getUserObject();
        ArrayList<String> failedModules = new ArrayList<String>();
        try {
            if(item instanceof ModuleSelectItem) {
                moduleService.startMergeSession();
                moduleService.create(((ModuleSelectItem)item).get());
                moduleService.stopMergeSession();
                if(moduleService.mergingNecessary()) {
                    int option = startMergeDialog();
                    logger.debug("user pressed option "+option);
                    if(option == 0) {
                        PanelTube.backgroundPanel.viewSmallInfoText("Überschneidende neue Daten wurden verworfen.", SmallInfoPanel.Info);
                    } else if(option == 1) {
                        for(Module m : moduleService.getNewModulesWithMergeConflicts()) {
                            moduleService.update(m);
                        }
                        for(MetaLVA m : moduleService.getNewMetaLvasWithMergeConflicts()) {
                            metaLVAService.update(m);
                        }
                        PanelTube.backgroundPanel.viewSmallInfoText("Daten wurden erfolgreich gespeichert.", SmallInfoPanel.Success);
                    } else if(option == 2) {
                        logger.info("merging old: \n"+LVAUtil.formatMetaLVA(metaLVAService.getOldMetaLvasWithMergeConflicts(),1));
                        PanelTube.backgroundPanel.viewMerge(metaLVAService.getOldMetaLvasWithMergeConflicts(), metaLVAService.getNewMetaLvasWithMergeConflicts());
                    }
                }else{
                    PanelTube.backgroundPanel.viewSmallInfoText("Daten wurden erfolgreich gespeichert.", SmallInfoPanel.Success);
                }
            } else if(item instanceof CurriculumSelectItem) {
                moduleService.startMergeSession();
                for(Module m : currentModules) {
                    moduleService.create(m);
                }
                moduleService.stopMergeSession();
                if(moduleService.mergingNecessary()) {
                    int option = startMergeDialog();
                    logger.debug("user pressed option "+option);
                    if(option == 0) {
                        PanelTube.backgroundPanel.viewSmallInfoText("Überschneidende neue Daten wurden verworfen.", SmallInfoPanel.Info);
                    } else if(option == 1) {
                        for(Module m : moduleService.getNewModulesWithMergeConflicts()) {
                            moduleService.update(m);
                        }
                        for(MetaLVA m : moduleService.getNewMetaLvasWithMergeConflicts()) {
                            metaLVAService.update(m);
                        }
                        PanelTube.backgroundPanel.viewSmallInfoText("Daten wurden erfolgreich gespeichert.", SmallInfoPanel.Success);

                    } else if(option == 2) {
                        logger.info("merging old: \n"+LVAUtil.formatMetaLVA(metaLVAService.getOldMetaLvasWithMergeConflicts(),1));
                        PanelTube.backgroundPanel.viewMerge(metaLVAService.getOldMetaLvasWithMergeConflicts(), metaLVAService.getNewMetaLvasWithMergeConflicts());

                    }
                }else{
                    PanelTube.backgroundPanel.viewSmallInfoText("Daten wurden erfolgreich gespeichert.", SmallInfoPanel.Success);
                }
            } else if(item instanceof MetaLvaSelectItem) {
                metaLVAService.startMergeSession();
                metaLVAService.create(((MetaLvaSelectItem) item).get());
                metaLVAService.stopMergeSession();
                if(metaLVAService.mergingNecessary()) {
                    int option = startMergeDialog();
                    logger.debug("user pressed option "+option);
                    if(option == 0) {
                        PanelTube.backgroundPanel.viewSmallInfoText("Überschneidende neue Daten wurden verworfen.", SmallInfoPanel.Info);
                    } else if(option == 1) {
                        for(MetaLVA m : moduleService.getNewMetaLvasWithMergeConflicts()) {
                            metaLVAService.update(m);
                        }
                        PanelTube.backgroundPanel.viewSmallInfoText("Daten wurden erfolgreich gespeichert.", SmallInfoPanel.Success);

                    } else if(option == 2) {
                        logger.info("merging old: \n"+LVAUtil.formatMetaLVA(metaLVAService.getOldMetaLvasWithMergeConflicts(),1));
                        PanelTube.backgroundPanel.viewMerge(metaLVAService.getOldMetaLvasWithMergeConflicts(), metaLVAService.getNewMetaLvasWithMergeConflicts());

                    }
                }else{
                    PanelTube.backgroundPanel.viewSmallInfoText("Daten wurden erfolgreich gespeichert.", SmallInfoPanel.Success);
                }
            }
        } catch (ServiceException e) {
            logger.error("metalva create failed");
            PanelTube.backgroundPanel.viewSmallInfoText("Es gab Probleme beim speichern.", SmallInfoPanel.Error);
        } catch (ValidationException e) {
            logger.error("tried to import invalid lva");
            PanelTube.backgroundPanel.viewSmallInfoText("Es gab Probleme beim speichern.", SmallInfoPanel.Error);

        }
    }

    public void refreshTree() {
        setCursor(new Cursor(Cursor.WAIT_CURSOR));
        progressBar.setIndeterminate(true);
        progressBar.setVisible(true);
        FetcherTask task = new FetcherTask();
        task.execute();
    }
    public void freeUI(){
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        progressBar.setIndeterminate(false);
        progressBar.setVisible(false);
        importb.setEnabled(true);
    }

    private class FetcherTask extends SwingWorker<Void, Void> {
        @Override
        protected Void doInBackground() throws Exception {
            try {
                Curriculum curriculum = ((CurriculumSelectItem) academicPrograms.getSelectedItem()).get();
                DefaultMutableTreeNode top = new DefaultMutableTreeNode(new CurriculumSelectItem(curriculum));

                currentModules = lvaFetcherService.getModules(curriculum.getStudyNumber(), true);

                for(Module m : currentModules) {
                    DefaultMutableTreeNode moduleNode = new DefaultMutableTreeNode(new ModuleSelectItem(m));
                    if(m.getMetaLvas() != null)
                        for(MetaLVA ml: m.getMetaLvas()) {
                            DefaultMutableTreeNode mln = new DefaultMutableTreeNode(new MetaLvaSelectItem(ml));
                            moduleNode.add(mln);
                        }
                    top.add(moduleNode);
                }

                tissTree = new JTree(top);
                tissTree.getSelectionModel().setSelectionMode
                        (TreeSelectionModel.SINGLE_TREE_SELECTION);
                treeView.setViewportView(tissTree);

                tissTree.setSelectionPath(new TreePath(tissTree.getModel().getRoot()));
                PanelTube.backgroundPanel.viewSmallInfoText("Fertig geladen. Klicken sie auf \"Importieren\" um fortzufahren.", SmallInfoPanel.Info);
            } catch (ServiceException e) {
                logger.info("couldn't build LvaTree", e);
                PanelTube.backgroundPanel.viewSmallInfoText("Die LVAs konnten nicht geladen werden.", SmallInfoPanel.Error);
            }
            freeUI();
            return null;
        }
    }

    private static class CurriculumSelectItem extends SelectItem<Curriculum> {
        CurriculumSelectItem(Curriculum item) {
            super(item);
        }

        @Override
        public String toString() {
            return item.getName();
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

    private static class MetaLvaSelectItem extends SelectItem<MetaLVA> {
        MetaLvaSelectItem(MetaLVA item) {
            super(item);
        }

        @Override
        public String toString() {
            return item.getName();
        }
    }

    private class MergingNecessaryWindow extends JDialog {
        private JPanel panel;

    }
}