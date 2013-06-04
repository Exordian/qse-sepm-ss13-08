package at.ac.tuwien.sepm.ui;

import javax.swing.*;

import at.ac.tuwien.sepm.entity.MetaLVA;
import at.ac.tuwien.sepm.service.LVAService;
import at.ac.tuwien.sepm.service.MetaLVAService;
import at.ac.tuwien.sepm.service.Semester;
import at.ac.tuwien.sepm.service.ServiceException;
import at.ac.tuwien.sepm.service.impl.LVAServiceImpl;
import at.ac.tuwien.sepm.service.impl.MetaLVAServiceImpl;
import at.ac.tuwien.sepm.service.impl.ValidationException;
import at.ac.tuwien.sepm.ui.MetaLva.MetaLVAAdderFrame;
import at.ac.tuwien.sepm.ui.MetaLva.MetaLVADisplayPanel;
import at.ac.tuwien.sepm.ui.MetaLva.MetaLVAEditorFrame;
import at.ac.tuwien.sepm.ui.metaLVA.MetaLVADisplayPanel;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * @author Lena Lenz
 */
public class LVAPanel extends JPanel {
    private static final Logger logger = Logger.getLogger(LVAPanel.class);

    private MetaLVADisplayPanel displayMetaLVAs;

    private JButton add_metaLva;
    private JButton edit_metaLva;
    private JButton delete_metaLva;
    private List<MetaLVA> metaLVAs;

    private MetaLVAService metaLvaService;
    private LVAService lvaService;

    public LVAPanel() {
        try {
            int year = DateTime.now().getYear();
            boolean isWinterSemester = (DateTime.now().getMonthOfYear() < 6)? false : true;
            Semester semester = isWinterSemester? Semester.W : Semester.S;
            metaLVAs = metaLvaService.readUncompletedByYearSemesterStudyProgress(year, semester, true); //TODO
        } catch(ServiceException e) {
            logger.error(e.getMessage());
        } catch(ValidationException e) {
            logger.error(e.getMessage());
        }
        displayMetaLVAs = new MetaLVADisplayPanel(metaLVAs, 400, 400);
        metaLvaService = new MetaLVAServiceImpl();
        lvaService = new LVAServiceImpl();
        addButtons();
        addActionListeners();
    }

    public void addButtons() {
        add_metaLva = new JButton("LVA Hinzufügen");
        edit_metaLva = new JButton("LVA Bearbeiten");
        delete_metaLva = new JButton("LVA Löschen");

        this.add(add_metaLva);
        this.add(edit_metaLva);
        this.add(delete_metaLva);
    }

    public void addActionListeners() {
        add_metaLva.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LVAPanel.this.addMetaLvaPressed();
            }
        });

        edit_metaLva.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LVAPanel.this.editMetaLvaPressed();
            }
        });

        delete_metaLva.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LVAPanel.this.deleteMetaLvaPressed();
            }
        });
    }

    private void addMetaLvaPressed() {
        new MetaLVAAdderFrame(displayMetaLVAs, metaLvaService);
    }

    private void editMetaLvaPressed() {
        MetaLVA toEdit = displayMetaLVAs.getSelectedMetaLVA();
        new MetaLVAEditorFrame(displayMetaLVAs, metaLvaService, toEdit);
    }

    private void deleteMetaLvaPressed() {
        MetaLVA toDelete = displayMetaLVAs.getSelectedMetaLVA();
        try {
            metaLvaService.delete(toDelete.getId());
            logger.debug("deleting metaLva with id = " + toDelete.getId());
            displayMetaLVAs.removeSelectedMetaLVA();
        } catch(ServiceException e) {
            logger.error(e.getMessage());
        }
    }

    private void addLvaPressed() {
    }

    private void editLvaPressed() {
    }

    private void deleteLvaPressed() {

    }
}
