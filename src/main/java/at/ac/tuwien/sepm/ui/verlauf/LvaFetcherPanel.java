package at.ac.tuwien.sepm.ui.verlauf;

import at.ac.tuwien.sepm.entity.Curriculum;
import at.ac.tuwien.sepm.entity.MetaLVA;
import at.ac.tuwien.sepm.entity.Module;
import at.ac.tuwien.sepm.service.LvaFetcherService;
import at.ac.tuwien.sepm.service.ServiceException;
import at.ac.tuwien.sepm.ui.UI;
import net.miginfocom.swing.MigLayout;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

@UI
public class LvaFetcherPanel extends JPanel{
    private static Logger log = LogManager.getLogger(LvaFetcherPanel.class);

    @Autowired
    private LvaFetcherService lvaFetcherService;

    private JTree tissTree;
    private JScrollPane treeView;
    private JComboBox<CurriculumSelectItem> academicPrograms;
    private JButton fetchProgram;
    private JButton importb;

    public LvaFetcherPanel() {
        super(new MigLayout("debug"/*, "[70%][30%]", "[10%][90%]"*/));
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

        // TODO implement this

    }

    public void refreshTree() {
        try {
            setCursor(new Cursor (Cursor.WAIT_CURSOR));
            Curriculum curriculum = ((CurriculumSelectItem) academicPrograms.getSelectedItem()).get();
            DefaultMutableTreeNode top = new DefaultMutableTreeNode(curriculum.getName());

            List<Module> modules = lvaFetcherService.getModules(curriculum.getStudyNumber(), true);

            for(Module m : modules) {
                DefaultMutableTreeNode moduleNode = new DefaultMutableTreeNode(m.getName());
                if(m.getMetaLvas() != null)
                    for(MetaLVA ml: m.getMetaLvas()) {
                        DefaultMutableTreeNode mln = new DefaultMutableTreeNode(ml.getName());
                        moduleNode.add(mln);
                    }
                top.add(moduleNode);
            }

            treeView.setViewportView(new JTree(top));
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
