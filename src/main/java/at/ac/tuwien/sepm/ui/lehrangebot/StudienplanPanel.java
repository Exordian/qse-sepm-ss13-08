package at.ac.tuwien.sepm.ui.lehrangebot;

import at.ac.tuwien.sepm.entity.Curriculum;
import at.ac.tuwien.sepm.entity.Module;
import at.ac.tuwien.sepm.service.*;
import at.ac.tuwien.sepm.ui.StandardInsidePanel;
import at.ac.tuwien.sepm.ui.UI;
import net.miginfocom.swing.MigLayout;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Flo
 * Date: 01.06.13
 * Time: 19:23
 * To change this template use File | Settings | File Templates.
 */

@UI
public class StudienplanPanel extends StandardInsidePanel {
    protected CreateCurriculumService service;

    private static final int MAX_INFO_LENGTH = 18;

    private JPanel panel;
    private JButton baddcurr;
    private JButton bcreate;
    private JTable tmodule;
    private DefaultTableModel mmodule;
    private JScrollPane spane;
    private JComboBox<CurriculumComboBoxItem> ccurr;
    private DefaultComboBoxModel<CurriculumComboBoxItem> mcurr;

    private Logger log = LogManager.getLogger(this.getClass().getSimpleName());

    @Autowired
    public StudienplanPanel(CreateCurriculumService service) {
        this.service=service;
        this.setLayout(new GridLayout());
        this.setOpaque(false);
        loadFonts();
        setBounds((int) startCoordinateOfWhiteSpace.getX(), (int) startCoordinateOfWhiteSpace.getY(),(int) whiteSpace.getWidth(),(int) whiteSpace.getHeight());
        initPanel();
        initAddCurriculumButton();
        initCurriculumComboBox();
        initModuleTable();
        initButtonCreate();
        placeComponents();
        initCurriculumComboBoxActionListener();
        revalidate();
        repaint();
    }

    public void placeComponents() {
        this.add(panel);
        panel.add(baddcurr);
        panel.add(ccurr, "wrap");
        panel.add(spane, "span, wrap");
        panel.add(bcreate);
    }

    public void initButtonCreate() {
        bcreate = new JButton();
        bcreate.setText("Speichern");
        bcreate.setFont(standardButtonFont);
        bcreate.addActionListener(new ActionListener() {
            @Override
            @Transactional
            public void actionPerformed(ActionEvent e) {
                try {
                    if (ccurr.getSelectedItem() != null) {
                        int cid = ((CurriculumComboBoxItem) ccurr.getSelectedItem()).get().getId();
                        HashMap<Module, Boolean> mMap = service.readModuleByCurriculum(cid);
                        Set<Module> mSet = mMap.keySet();
                        HashMap<Integer, Module> mids = new HashMap<Integer, Module>();

                        for (Module m : mSet) {
                            mids.put(m.getId(), m);
                        }

                        int rows = mmodule.getRowCount();
                        for (int i = 0; i < rows; i++) {
                            int mid = (Integer) mmodule.getValueAt(i, 1);
                            if ((Boolean) (mmodule.getValueAt(i, 0))) { // Das Modul wurde ausgewählt
                                if (mids.get(mid) == null) { // das modul wird neu aufgenommen
                                    service.addModuleToCurriculum(mid, cid, (Boolean) (mmodule.getValueAt(i, 3)));
                                } else { // das modul ist schon drinnen
                                    if (mMap.get(mids.get(mid)).booleanValue() != ((Boolean) mmodule.getValueAt(i, 3)).booleanValue()) { // Es wird upgedated
                                        service.deleteModuleFromCurriculum(mid, cid);
                                        service.addModuleToCurriculum(mid, cid, (Boolean) (mmodule.getValueAt(i, 3)));
                                    }
                                }
                            } else { // Das modul wurde nicht ausgewählt
                                if (mids.get(mid) != null) { // das modul war vorher im studienplan und wird gelöscht
                                    service.deleteModuleFromCurriculum(mid, cid);
                                }
                            }
                        }
                    }
                } catch (ServiceException e1) {
                    log.error("Error: " + e1.getMessage());
                    return;
                }
                fillTable();
            }
        });
    }

    public void initCurriculumComboBox() {
        mcurr = new DefaultComboBoxModel<CurriculumComboBoxItem>();
        ccurr = new JComboBox(mcurr);
        ccurr.setFont(standardButtonFont);
        fillCurriculumComboBox();
    }

    public void initCurriculumComboBoxActionListener() {
        ccurr.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fillTable();
            }
        });
    }

    public void fillCurriculumComboBox() {
        List<Curriculum> l = new ArrayList<Curriculum>();

        try {
            l = service.readAllCurriculum();
        } catch (ServiceException e) {
            log.error("Error: " + e.getMessage());
            return;
        }

        mcurr.removeAllElements();

        for(Curriculum c : l) {
            mcurr.addElement(new CurriculumComboBoxItem(c));
        }
    }

    public void initPanel() {
        panel = new JPanel();
        panel.setLayout(new MigLayout());
        panel.setBounds(whiteSpace);
        panel.setBackground(Color.WHITE);
    }

    public void initAddCurriculumButton () {
        baddcurr = new JButton();
        baddcurr.setText("Studium anlegen");
        baddcurr.setFont(standardButtonFont);
        baddcurr.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddCurriculumFrame();
            }
        });
    }

    public void initModuleTable() {
        String[] head = new String[]{"Enthalten", "ID", "Name", "Pflichtmodul", "Beschreibung"};
        mmodule = new DefaultTableModel(head, 0);
        tmodule = new JTable(mmodule) {
            @Override
            public Class getColumnClass(int column) {
                switch (column) {
                    case 0:
                        return Boolean.class;
                    case 1:
                        return Integer.class;
                    case 2:
                        return String.class;
                    case 3:
                        return Boolean.class;
                    case 4:
                        return String.class;
                    default:
                        return Boolean.class;
                }
            }
        };

        spane = new JScrollPane(tmodule);
        spane.setBackground(Color.WHITE);
        //spane.setMinimumSize(new Dimension(650, 400));

        fillTable();
    }

    public void fillTable() {

        List<Module> list = new ArrayList<Module>();
        try {
            list = service.readAllModules();
        } catch (ServiceException e) {
            log.error("Error: " + e.getMessage());
            return;
        }

        if(ccurr.getSelectedItem()!=null){
            int cid = ((CurriculumComboBoxItem)ccurr.getSelectedItem()).get().getId();
            HashMap<Module, Boolean> mMap = new HashMap<Module, Boolean>();

            try {
                mMap = service.readModuleByCurriculum(cid);
            } catch (ServiceException e) {
                log.error("Error: " + e.getMessage());
                return;
            }

            Set<Module> mSet = mMap.keySet();
            HashMap<Integer, Module> midsMap = new HashMap<Integer, Module>();
            Set<Integer> mids = new HashSet<Integer>();

            for(Module m : mSet) {
                mids.add(m.getId());
                midsMap.put(m.getId(), m);
            }

            clearTable();

            for(Module m : list) {
                if(mids.contains(m.getId())) {
                    mmodule.addRow(createTableRow(true, mMap.get(midsMap.get(m.getId())), m));
                } else {
                    mmodule.addRow(createTableRow(false, false, m));
                }
            }
        }
    }

    private Object[] createTableRow (Boolean incurriculum, Boolean obligatory, Module m) {
        return new Object[]{incurriculum, m.getId(), m.getName(), obligatory, m.getDescription()};
    }

    private void clearTable() {
        mmodule.setRowCount(0);
    }

    private class CurriculumComboBoxItem {
        private Curriculum c;

        CurriculumComboBoxItem(Curriculum c) {
            this.c = c;
        }

        public Curriculum get () {
            return c;
        }
        public String toString() {
            return c.getStudyNumber();
        }

    }

    private class AddCurriculumFrame extends JFrame {
        private JPanel panel;
        private JLabel lnumber;
        private JLabel lname;
        private JLabel ldescription;
        private JLabel ltitle;
        private JLabel lectsc;
        private JLabel lectsf;
        private JLabel lectss;
        private JTextField tnumber;
        private JTextField tname;
        private JTextField tdescription;
        private JTextField ttitle;
        private JTextField tectsc;
        private JTextField tectsf;
        private JTextField tectss;
        private JButton bok;

        public AddCurriculumFrame () {
            super("Studienplan erstellen");
            this.setFont(standardTitleFont);
            this.setVisible(false);
            panel = new JPanel(new MigLayout());
            this.add(panel);
            init();
            placeComponents();
            this.setLocation(500,500);
            this.setMinimumSize(panel.getSize());
            this.pack();
            this.revalidate();
            this.repaint();
            this.setVisible(true);
        }

        private void init () {
            lnumber = new JLabel("Studienkennzahl: ");
            lname = new JLabel("Name: ");
            ldescription = new JLabel("Beschreibung: ");
            ltitle = new JLabel("Akad. Titel: ");
            lectsc = new JLabel("Wahl-ECTS: ");
            lectsf = new JLabel("Frei-ECTS: ");
            lectss = new JLabel("SoftSkill-ECTS: ");

            lnumber.setFont(standardTextFont);
            lname.setFont(standardTextFont);
            ldescription.setFont(standardTextFont);
            ltitle.setFont(standardTextFont);
            lectsc.setFont(standardTextFont);
            lectsf.setFont(standardTextFont);
            lectss.setFont(standardTextFont);

            tnumber = new JTextField();
            tname = new JTextField();
            tdescription = new JTextField();
            ttitle = new JTextField();
            tectsc = new JTextField();
            tectsf = new JTextField();
            tectss = new JTextField();

            tnumber.setFont(standardTextFont);
            tname.setFont(standardTextFont);
            tdescription.setFont(standardTextFont);
            ttitle.setFont(standardTextFont);
            tectsc.setFont(standardTextFont);
            tectsf.setFont(standardTextFont);
            tectss.setFont(standardTextFont);

            tnumber.setMinimumSize(new Dimension(150, (int)tnumber.getSize().getHeight()));
            tname.setMinimumSize(new Dimension(150, (int)tname.getSize().getHeight()));
            tdescription.setMinimumSize(new Dimension(150, (int)tdescription.getSize().getHeight()));
            ttitle.setMinimumSize(new Dimension(150, (int)ttitle.getSize().getHeight()));
            tectsc.setMinimumSize(new Dimension(150, (int)tectsc.getSize().getHeight()));
            tectsf.setMinimumSize(new Dimension(150, (int)tectsf.getSize().getHeight()));
            tectss.setMinimumSize(new Dimension(150, (int)tectss.getSize().getHeight()));

            bok = new JButton("OK");
            bok.setFont(standardButtonFont);
            bok.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String number = tnumber.getText();
                    String name = tname.getText();
                    String description = tdescription.getText();
                    String title = ttitle.getText();
                    String ectsc = tectsc.getText();
                    String ectsf = tectsf.getText();
                    String ectss = tectss.getText();
                    Integer c;
                    Integer f;
                    Integer s;

                    if(number==null || number.equals("")) {
                        JOptionPane.showMessageDialog(new JFrame(), "Bitte geben Sie eine Studienkennzahl an.");
                        return;
                    }
                    if(name==null || name.equals("")) {
                        JOptionPane.showMessageDialog(new JFrame(), "Bitte geben Sie einen Namen an.");
                        return;
                    }
                    if(title==null || title.equals("")) {
                        JOptionPane.showMessageDialog(new JFrame(), "Bitte geben Sie den akademischen Titel an.");
                        return;
                    }
                    if(ectsc==null) {
                        JOptionPane.showMessageDialog(new JFrame(), "Bitte geben Sie die Wahl-ECTS-Punkte an.");
                        return;
                    }
                    if(ectsf==null) {
                        JOptionPane.showMessageDialog(new JFrame(), "Bitte geben Sie die Frei-ECTS-Punkte an.");
                        return;
                    }
                    if(ectss==null) {
                        JOptionPane.showMessageDialog(new JFrame(), "Bitte geben Sie die SoftSkill-ECTS-Punkte an.");
                        return;
                    }
                    try {
                        c = Integer.parseInt(ectsc);
                    } catch (NumberFormatException e1) {
                        JOptionPane.showMessageDialog(new JFrame(), "Die angegebenen Wahl-ECTS-Punkte sind keine gültige zahl.");
                        return;
                    }
                    try {
                        f = Integer.parseInt(ectsf);
                    } catch (NumberFormatException e1) {
                        JOptionPane.showMessageDialog(new JFrame(), "Die angegebenen Frei-ECTS-Punkte sind keine gültige zahl.");
                        return;
                    }
                    try {
                        s = Integer.parseInt(ectss);
                    } catch (NumberFormatException e1) {
                        JOptionPane.showMessageDialog(new JFrame(), "Die angegebenen SoftSkill-ECTS-Punkte sind keine gültige zahl.");
                        return;
                    }

                    Curriculum curriculum = new Curriculum();
                    curriculum.setStudyNumber(number);
                    curriculum.setName(name);
                    curriculum.setDescription(description);
                    curriculum.setAcademicTitle(title);
                    curriculum.setEctsChoice(c);
                    curriculum.setEctsFree(f);
                    curriculum.setEctsSoftskill(s);

                    try {
                        service.createCurriculum(curriculum);
                    } catch (ServiceException e1) {
                        log.error("Error: " + e1.getMessage());
                    }

                    fillCurriculumComboBox();
                    dispose();
                }
            });
        }

        private void placeComponents() {
            panel.add(lnumber);
            panel.add(tnumber, "wrap");
            panel.add(lname);
            panel.add(tname, "wrap");
            panel.add(ldescription);
            panel.add(tdescription, "wrap");
            panel.add(ltitle);
            panel.add(ttitle, "wrap");
            panel.add(lectsc);
            panel.add(tectsc, "wrap");
            panel.add(lectsf);
            panel.add(tectsf, "wrap");
            panel.add(lectss);
            panel.add(tectss, "wrap");
            panel.add(bok);
        }
    }
}
