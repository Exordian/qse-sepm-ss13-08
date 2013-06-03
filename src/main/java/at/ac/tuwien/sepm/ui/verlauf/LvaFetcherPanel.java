package at.ac.tuwien.sepm.ui.verlauf;

import at.ac.tuwien.sepm.entity.Curriculum;
import at.ac.tuwien.sepm.entity.MetaLVA;
import at.ac.tuwien.sepm.entity.Module;
import at.ac.tuwien.sepm.service.LvaFetcherService;
import at.ac.tuwien.sepm.service.MetaLVAService;
import at.ac.tuwien.sepm.service.ServiceException;
import at.ac.tuwien.sepm.service.impl.ValidationException;
import at.ac.tuwien.sepm.ui.UI;
import net.miginfocom.swing.MigLayout;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

@UI
public class LvaFetcherPanel extends JPanel{
    private static Logger log = LogManager.getLogger(LvaFetcherPanel.class);

    @Autowired
    private LvaFetcherService lvaFetcherService;

    @Autowired
    private MetaLVAService metaLVAService;


    private JTree tissTree;
    private JScrollPane treeView;
    private JComboBox<CurriculumSelectItem> academicPrograms;
    private JButton fetchProgram;
    private JButton importb;

    public LvaFetcherPanel() {
        super(new MigLayout());
    }

    void reconfigLayout(Font textFont, Font buttonFont) {
        academicPrograms.setFont(buttonFont);
        fetchProgram.setFont(buttonFont);
        importb.setFont(buttonFont);
        treeView.setFont(textFont);

        academicPrograms.setMinimumSize(new Dimension((int)this.getBounds().getWidth()-145, 20));
        treeView.setMinimumSize(new Dimension((int) this.getBounds().getWidth() - 145, 20));

        revalidate();
        repaint();
    }

    @PostConstruct
    public void init() {
        academicPrograms = new JComboBox();
        try {
            for(Curriculum c : lvaFetcherService.getAcademicPrograms())
                academicPrograms.addItem(new CurriculumSelectItem(c));
        } catch (ServiceException e) {
            log.info("no academic prorgams");
        }
        fetchProgram = new JButton("Studium laden");

        treeView = new JScrollPane(new JTree());

        fetchProgram.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                refreshTree();
            }
        });

        importb = new JButton("Importieren");
        importb.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performImport();
            }
        });

        add(academicPrograms, "push");
        add(fetchProgram, "wrap");
        add(treeView, "grow, push, span, wrap");
        add(importb);
    }

    private void performImport() {
        TreePath path = tissTree.getSelectionPath();
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) path.getLastPathComponent();
        Object item = selectedNode.getUserObject();
        try {
            if(item instanceof ModuleSelectItem) {
                // TODO
            } else if(item instanceof CurriculumSelectItem) {
                // TODO
            } else if(item instanceof MetaLvaSelectItem) {
                metaLVAService.create(((MetaLvaSelectItem) item).get());
            }
        } catch (ServiceException e) {
            log.error("metalva create failed");
        } catch (ValidationException e) {
            log.error("tried to import invalid lva");
        }
    }

    public void refreshTree() {
        try {
            setCursor(new Cursor (Cursor.WAIT_CURSOR));
            Curriculum curriculum = ((CurriculumSelectItem) academicPrograms.getSelectedItem()).get();
            DefaultMutableTreeNode top = new DefaultMutableTreeNode(new CurriculumSelectItem(curriculum));

            List<Module> modules = lvaFetcherService.getModules(curriculum.getStudyNumber(), true);

            for(Module m : modules) {
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
            setCursor(new Cursor (Cursor.DEFAULT_CURSOR));
        } catch (ServiceException e) {
            log.info("couldn't build LvaTree", e);
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

    private static abstract class SelectItem<E> {
        protected E item;

        SelectItem(E item) {
            this.item = item;
        }

        public E get () {
            return item;
        }

        public abstract String toString();
    }
}
