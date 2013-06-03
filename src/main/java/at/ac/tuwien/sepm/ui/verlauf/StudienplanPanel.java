package at.ac.tuwien.sepm.ui.verlauf;

import at.ac.tuwien.sepm.dao.hsqldb.DBCurriculumDao;
import at.ac.tuwien.sepm.entity.Curriculum;
import at.ac.tuwien.sepm.entity.Module;
import at.ac.tuwien.sepm.service.*;
import at.ac.tuwien.sepm.ui.StandardInsidePanel;
import at.ac.tuwien.sepm.ui.UI;
import net.miginfocom.swing.MigLayout;
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

    @Autowired
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
    private JLabel ldesc;
    /*
    private JPanel infop;
    private JLabel numberl;
    private JLabel namel;
    private JLabel descriptionl;
    private JLabel titlel;
    private JLabel ectscl;
    private JLabel ectsfl;
    private JLabel ectssl;

    private JLabel numberInfol;
    private JLabel nameInfol;
    private JLabel descriptionInfol;
    private JLabel titleInfol;
    private JLabel ectscInfol;
    private JLabel ectsfInfol;
    private JLabel ectssInfol;
    */
    public StudienplanPanel() {
        this.setLayout(new MigLayout());
        this.setOpaque(false);
        loadFonts();
        setBounds((int)StudStartCoordinateOfWhiteSpace.getX(), (int)StudStartCoordinateOfWhiteSpace.getY(),(int)whiteSpaceStud.getWidth(),(int)whiteSpaceStud.getHeight());
        revalidate();
        repaint();
    }

    public void placeComponents() {
        this.add(panel);
        panel.add(baddcurr);
        panel.add(ccurr, "wrap");
        panel.add(spane, "span, wrap");
        panel.add(bcreate);
        /*
        infop.add(numberl);
        infop.add(numberInfol, "wrap");
        infop.add(namel);
        infop.add(nameInfol, "wrap");
        infop.add(descriptionl);
        infop.add(descriptionInfol, "wrap");
        infop.add(titlel);
        infop.add(titleInfol, "wrap");
        infop.add(ectscl);
        infop.add(ectscInfol, "wrap");
        infop.add(ectsfl);
        infop.add(ectsfInfol, "wrap");
        infop.add(ectssl);
        infop.add(ectssInfol, "wrap");
        this.add(infop);*/
    }
    /*
    public void initInfoPanel() {
        infop = new JPanel(new MigLayout());
        infop.setLayout(new MigLayout());
        infop.setBounds(whiteSpaceStud);
        infop.setBackground(Color.WHITE);
        numberl = new JLabel("Studienkennzahl: ");
        namel = new JLabel("Name: ");
        descriptionl = new JLabel("Beschreibung: ");
        titlel = new JLabel("Akademischer Titel: ");
        ectscl = new JLabel("Wahl-ECTS: ");
        ectsfl = new JLabel("Frei-ECTS: ");
        ectssl = new JLabel("SoftSkill-ECTS: ");
        numberInfol = new JLabel();
        nameInfol = new JLabel();
        descriptionInfol = new JLabel();
        titleInfol = new JLabel();
        ectscInfol = new JLabel();
        ectsfInfol = new JLabel();
        ectssInfol = new JLabel();

        numberl.setFont(standardTextFont);
        namel.setFont(standardTextFont);
        descriptionl.setFont(standardTextFont);
        titlel.setFont(standardTextFont);
        ectscl.setFont(standardTextFont);
        ectsfl.setFont(standardTextFont);
        ectssl.setFont(standardTextFont);

        numberInfol.setFont(standardTextFont);
        nameInfol.setFont(standardTextFont);
        descriptionInfol.setFont(standardTextFont);
        titleInfol.setFont(standardTextFont);
        ectscInfol.setFont(standardTextFont);
        ectsfInfol.setFont(standardTextFont);
        ectssInfol.setFont(standardTextFont);

        setInfoLabels();
    }
    */
    /*
    private void setInfoLabels() {
        if(ccurr.getSelectedItem()==null) {
            return;
        }
        Curriculum c = ((CurriculumComboBoxItem) ccurr.getSelectedItem()).get();
        numberInfol.setText(prepareString(c.getStudyNumber()));
        nameInfol.setText(prepareString(c.getName()));
        descriptionInfol.setText(prepareString(c.getDescription()));
        titleInfol.setText(prepareString(c.getAcademicTitle()));
        ectscInfol.setText(prepareString("" + c.getEctsChoice()));
        ectsfInfol.setText(prepareString("" + c.getEctsFree()));
        ectssInfol.setText(prepareString("" + c.getEctsSoftskill()));
    }
    */
    private String prepareString(String s) {
        if(s.length() > MAX_INFO_LENGTH) {
            s=s.substring(0,17) + "...";
        }

        return s;
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
                    e1.printStackTrace();
                    // JOptionPane.showMessageDialog(new JFrame(), e1.getMessage());
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
            // JOptionPane.showMessageDialog(new JFrame(), e.getMessage());
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
        panel.setBounds(whiteSpaceStud);
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
        spane.setMinimumSize(new Dimension(650, 400));

        fillTable();
    }

    public void fillTable() {

        List<Module> list = new ArrayList<Module>();
        try {
            list = service.readAllModules();
        } catch (ServiceException e) {
            //JOptionPane.showMessageDialog(new JFrame(), e.getMessage());
            return;
        }

        if(ccurr.getSelectedItem()!=null){
            int cid = ((CurriculumComboBoxItem)ccurr.getSelectedItem()).get().getId();
            HashMap<Module, Boolean> mMap = new HashMap<Module, Boolean>();

            try {
                mMap = service.readModuleByCurriculum(cid);
            } catch (ServiceException e) {
                //JOptionPane.showMessageDialog(new JFrame(), e.getMessage());
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
                        JOptionPane.showMessageDialog(new JFrame(), e1.getMessage());
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

    /*private class AddModuleFrame extends JFrame {
        private JPanel panel;
        private JLabel lname;
        private JLabel ldescription;
        private JLabel lcompletea;
        private JTextField tname;
        private JTextField tdescription;
        private JComboBox<Boolean> ccompletea;
        private JRadioButton rtrue;
        private JRadioButton rfalse;
        private ButtonGroup group;
        private JButton bok;

        public AddModuleFrame () {
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
            lname = new JLabel("Name: ");
            ldescription = new JLabel("Beschreibung: ");
            lcompletea = new JLabel("Muss das Modul komplett absolviert werden?");

            lname.setFont(standardTextFont);
            ldescription.setFont(standardTextFont);
            lcompletea.setFont(standardTextFont);

            tname = new JTextField();
            tdescription = new JTextField();

            tname.setFont(standardTextFont);
            tdescription.setFont(standardTextFont);

            tname.setMinimumSize(new Dimension(225, (int)tname.getSize().getHeight()));
            tdescription.setMinimumSize(new Dimension(225, (int)tdescription.getSize().getHeight()));

            rtrue = new JRadioButton();
            rfalse = new JRadioButton();

            rtrue.setText("Ja");
            rfalse.setText("Nein");

            rtrue.setFont(standardTextFont);
            rfalse.setFont(standardTextFont);

            group = new ButtonGroup();
            group.add(rtrue);
            group.add(rfalse);

            bok = new JButton("OK");
            bok.setFont(standardButtonFont);
            bok.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // TO DO call the add module lva service
                }
            });

        }

        private void placeComponents() {
            panel.add(lname);
            panel.add(tname, "wrap");
            panel.add(ldescription);
            panel.add(tdescription, "wrap");
            panel.add(lcompletea, "span 2, wrap");
            panel.add(rtrue, "wrap");
            panel.add(rfalse, "wrap");
            panel.add(bok);
        }
    }
    */
    /*
    private class AddMetaLvaFrame extends JFrame {
        private JPanel panel;
        private JLabel lnumber;
        private JLabel lname;
        private JLabel lsemester;
        private JLabel ltype;
        private JLabel lpriority;
        private JLabel lects;
        private JLabel lmodule;
        private JTextField tnumber;
        private JTextField tname;
        private JTextField tects;
        private JTextField tmodule;
        private JTextField tpriority;
        private JComboBox<Semester> csemester;
        private JComboBox<LvaType> ctype;
        private JButton bok;

        public AddMetaLvaFrame () {
            super("LVA erstellen");
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

        private void init() {
            lnumber = new JLabel("LVA-Nummer: ");
            lname = new JLabel("Name: ");
            lsemester = new JLabel("Semester: ");
            ltype = new JLabel("Typ: ");
            lpriority = new JLabel("Priorität: ");
            lects = new JLabel("ECTS: ");
            lmodule = new JLabel("Modul: ");

            lnumber.setFont(standardTextFont);
            lname.setFont(standardTextFont);
            lsemester.setFont(standardTextFont);
            ltype.setFont(standardTextFont);
            lpriority.setFont(standardTextFont);
            lects.setFont(standardTextFont);
            lmodule.setFont(standardTextFont);

            tnumber = new JTextField();
            tname = new JTextField();
            tects = new JTextField();
            tmodule = new JTextField();
            tpriority = new JTextField();

            tnumber.setFont(standardTextFont);
            tname.setFont(standardTextFont);
            tects.setFont(standardTextFont);
            tmodule.setFont(standardTextFont);
            tpriority.setFont(standardTextFont);

            tnumber.setMinimumSize(new Dimension(150, (int)tnumber.getSize().getHeight()));
            tname.setMinimumSize(new Dimension(150, (int)tname.getSize().getHeight()));
            tects.setMinimumSize(new Dimension(150, (int)tects.getSize().getHeight()));
            tmodule.setMinimumSize(new Dimension(150, (int)tmodule.getSize().getHeight()));
            tpriority.setMinimumSize(new Dimension(150, (int)tpriority.getSize().getHeight()));

            csemester = new JComboBox();
            ctype = new JComboBox();
            csemester.setFont(standardButtonFont);
            ctype.setFont(standardButtonFont);
            csemester.addItem(Semester.S);
            csemester.addItem(Semester.W);
            csemester.addItem(Semester.W_S);
            csemester.addItem(Semester.UNKNOWN);
            ctype.addItem(LvaType.VO);
            ctype.addItem(LvaType.VU);
            ctype.addItem(LvaType.UE);
            ctype.addItem(LvaType.PR);
            ctype.addItem(LvaType.UE);

            bok = new JButton("OK");
            bok.setFont(standardButtonFont);
            bok.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String lvanumber = tnumber.getText();
                    String name = tname.getText();
                    Object s = csemester.getSelectedItem();
                    Object t = ctype.getSelectedItem();
                    String p = tpriority.getText();
                    String c = tects.getText();
                    String m = tmodule.getText();
                    Semester semester;
                    LvaType type;
                    Float priority;
                    Float ects;
                    Module module;

                    if(lvanumber==null || lvanumber.equals("")) {
                        new ErrorWindow("Bitte geben Sie eine LVA-Nummer an.");
                        return;
                    }
                    if(name==null || name.equals("")) {
                        new ErrorWindow("Bitte geben Sie einen Namen an.");
                        return;
                    }
                    if(name==null || name.equals("")) {
                        new ErrorWindow("Bitte geben Sie einen Namen an.");
                        return;
                    }
                    if(s == null) {
                        new ErrorWindow("Bitte wählen Sie ein Semester aus");
                        return;
                    }
                    if(t == null) {
                        new ErrorWindow("Bitte wählen Sie einen LVA-Typ aus");
                        return;
                    }
                    if(p == null || p.equals("")) {
                        priority = 0f;
                    } else {
                        try {
                            Float.parseFloat(p);
                            priority = Float.parseFloat(p);
                        } catch (NumberFormatException n) {
                            new ErrorWindow("Die angegebene Priorität ist keine gültige Zahl.");
                            return;
                        }
                    }
                    if(c == null || c.equals("")) {
                        new ErrorWindow("Bitte geben Sie die ECTS an.");
                        return;
                    } else {
                        try {
                            Float.parseFloat(c);
                            ects = Float.parseFloat(c);
                        } catch (NumberFormatException n) {
                            new ErrorWindow("Die angegebenen ECTS-Punkte sind keine gültige Zahl.");
                            return;
                        }
                    }
                    if(!(s instanceof Semester)) {
                        new ErrorWindow("Internal Error.");
                        return;
                    } else {
                        semester = (Semester)s;
                    }
                    if(!(t instanceof LvaType)) {
                        new ErrorWindow("Internal Error.");
                        return;
                    } else {
                        type = (LvaType)t;
                    }
                    if(m==null || m.equals(""))  {
                        new ErrorWindow("Bitte geben Sie ein Modul an.");
                        return;
                    } else {
                        try {
                            module = service.readModuleByName(m);
                        } catch (ServiceException e1) {
                            new ErrorWindow(e1.getMessage());
                            return;
                        }
                        if(module==null) {
                            new ErrorWindow("Es wurde kein passendes Modul gefunden.");
                            return;
                        }
                    }

                    MetaLVA result = new MetaLVA();
                    result.setNr(lvanumber);
                    result.setName(name);
                    result.setSemestersOffered(semester);
                    result.setType(type);
                    result.setPriority(priority);
                    result.setECTS(ects);
                    result.setModule(module.getId());

                    try {
                        service.createMetaLva(result);
                    } catch (ServiceException e1) {
                        new ErrorWindow(e1.getMessage());
                        return;
                    }
                }
            });
        }

        private void placeComponents() {
            panel.add(lnumber);
            panel.add(tnumber, "wrap");
            panel.add(lname);
            panel.add(tname, "wrap");
            panel.add(lsemester);
            panel.add(csemester, "wrap");
            panel.add(ltype);
            panel.add(ctype, "wrap");
            panel.add(lpriority);
            panel.add(tpriority, "wrap");
            panel.add(lects);
            panel.add(tects, "wrap");
            panel.add(lmodule);
            panel.add(tmodule, "wrap");
            panel.add(bok);
        }
    }
    */
    /*
    private class ErrorWindow extends JFrame {
        private JLabel text;

        public ErrorWindow(String t) {
            this.setVisible(false);
            this.setLocation(500, 500);
            this.setTitle("Fehler");
            this.text=new JLabel(t);
            this.text.setFont(standardTextFont);
            this.setMinimumSize(this.text.getSize());
            this.add(this.text);
            this.pack();
            this.revalidate();
            this.repaint();
            this.setVisible(true);
        }
    }*/
}
