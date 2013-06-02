package at.ac.tuwien.sepm.ui.verlauf;

import at.ac.tuwien.sepm.entity.Curriculum;
import at.ac.tuwien.sepm.entity.MetaLVA;
import at.ac.tuwien.sepm.entity.Module;
import at.ac.tuwien.sepm.service.LvaType;
import at.ac.tuwien.sepm.service.Semester;
import at.ac.tuwien.sepm.service.ServiceException;
import at.ac.tuwien.sepm.service.impl.CreateCurriculumServiceImpl;
import at.ac.tuwien.sepm.ui.StandardInsidePanel;
import at.ac.tuwien.sepm.ui.UI;
import net.miginfocom.swing.MigLayout;
import org.springframework.beans.factory.annotation.Autowired;

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
    CreateCurriculumServiceImpl service;

    private JPanel panel;
    private JButton baddmlva;
    private JButton baddcurr;
    private JButton baddmod;
    private JButton bcreate;
    private JTable tmodule;
    private DefaultTableModel mmodule;
    private JScrollPane spane;
    private JComboBox ccurr;
    private DefaultComboBoxModel<CurriculumComboBoxItem> mcurr;

    public StudienplanPanel() {
        this.setLayout(null);
        this.setOpaque(false);
        loadFonts();
        setBounds((int)StudStartCoordinateOfWhiteSpace.getX(), (int)StudStartCoordinateOfWhiteSpace.getY(),(int)whiteSpaceStud.getWidth(),(int)whiteSpaceStud.getHeight());
        initPanel();
        initAddMetaLvaButton();
        initAddCurriculumButton();
        initAddModuleButton();
        initModuleTable();
        initCurriculumComboBox();
        initButtonCreate();
        placeComponents();
        revalidate();
        repaint();
    }
    // TODO
    // JList bei denen alle MetaLvas angezeigt werden
    // Dialog zum hinzufügen neuer MetaLvas
    // Erstellen des Studienplans:
    /*
    Erstellen des Studienplans
    - Erzeugen neuer Meta LVAs  --> DONE
    - Anlegen eines neuen Studiums --> DONE
    - Auswählen, welche Module man zum Studium hinzufügen will
    - Erstellen eines neuen Moduls --> DONE
     */

    private void placeComponents() {
        this.add(panel);
        panel.add(baddcurr);
        panel.add(baddmod);
        panel.add(baddmlva, "wrap");
        panel.add(ccurr, "wrap");
        panel.add(spane, "span, wrap");
        panel.add(bcreate);
    }

    private void initButtonCreate() {
        bcreate = new JButton();
        bcreate.setText("Speichern");
        bcreate.setFont(standardButtonFont);
        bcreate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO call the service
            }
        });

    }

    private void initCurriculumComboBox() {
        mcurr = new DefaultComboBoxModel<CurriculumComboBoxItem>();
        ccurr = new JComboBox(mcurr);
        ccurr.setFont(standardButtonFont);
        refreshCurriculumComboBox();
    }

    private void refreshCurriculumComboBox() {
        Curriculum c0 = new Curriculum();
        Curriculum c1 = new Curriculum();
        Curriculum c2 = new Curriculum();

        c0.setId(0);
        c1.setId(1);
        c2.setId(2);

        c0.setStudyNumber("033 533");
        c1.setStudyNumber("033 534");
        c2.setStudyNumber("033 535");

        List<Curriculum> l = new ArrayList<Curriculum>();

        l.add(c0);
        l.add(c1);
        l.add(c2);

        /*
        try {
            l = service.readAllCurriculum();
        } catch (ServiceException e) {
            // TODO do something useful
            e.printStackTrace();
        }
        */

        mcurr.removeAllElements();

        for(Curriculum c : l) {
            mcurr.addElement(new CurriculumComboBoxItem(c));
        }
    }

    private void initPanel() {
        panel = new JPanel();
        panel.setLayout(new MigLayout());
        //panel.setBounds(this.getBounds().x - 51, this.getBounds().y - 78, (int) this.getBounds().getWidth(), (int) this.getBounds().getHeight() - 1);
        panel.setBounds(whiteSpaceStud);
        panel.setBackground(Color.WHITE);
    }

    private void initAddMetaLvaButton () {
        baddmlva = new JButton();
        baddmlva.setText("LVA erstellen");
        baddmlva.setFont(standardButtonFont);
        baddmlva.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddMetaLvaFrame();
            }
        });
    }

    private void initAddCurriculumButton () {
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

    private void initAddModuleButton () {
        baddmod = new JButton();
        baddmod.setText("Modul erstellen");
        baddmod.setFont(standardButtonFont);
        baddmod.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddModuleFrame();
            }
        });
    }

    private void initModuleTable() {
        String[] head = new String[]{"ID", "Name", "Komplett", "Beschreibung"};
        mmodule = new DefaultTableModel(head, 0);
        tmodule = new JTable(mmodule);
        spane = new JScrollPane(tmodule);
        spane.setBackground(Color.WHITE);
        // TODO add checkbox to JTable for selection if the module must be completed or not
        // TODO read all selected rows for the creating of a new study plan

        fillTable();
    }

    private void fillTable() {
        Module m0 = new Module();
        Module m1 = new Module();
        Module m2 = new Module();
        Module m3 = new Module();

        m0.setId(0);
        m1.setId(1);
        m2.setId(2);
        m3.setId(3);

        m0.setName("Name 0");
        m1.setName("Name 1");
        m2.setName("Name 2");
        m3.setName("Name 3");

        m0.setCompleteall(true);
        m1.setCompleteall(false);
        m2.setCompleteall(false);
        m3.setCompleteall(true);

        m0.setDescription("asdfasdfasdf");
        m1.setDescription("asdfasdfasdf");
        m2.setDescription("asdfasdfasdf");
        m3.setDescription("asdfasdfasdf");

        List<Module> list = new ArrayList<Module>();

        list.add(m0);
        list.add(m1);
        list.add(m2);
        list.add(m3);

        /*
        // TODO implement this
        try {
            list = service.readAllModules();
        } catch (ServiceException e) {
            // TODO do something useful
            e.printStackTrace();
        }
        */

        clearTable();

        for(Module m : list) {
            mmodule.addRow(createTableRow(m));
        }
    }

    private String[] createTableRow (Module m) {
        return new String[]{""+m.getId(), m.getName(), "" + m.getCompleteall(), m.getDescription()};
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

    private class AddModuleFrame extends JFrame {
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
                    // TODO call the add module lva service
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
            lectss = new JLabel("Pflicht-ECTS: ");

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
                    // TODO call the add curriculumSS lva service
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
    }
}
