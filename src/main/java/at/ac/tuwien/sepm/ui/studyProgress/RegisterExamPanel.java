package at.ac.tuwien.sepm.ui.studyProgress;

import at.ac.tuwien.sepm.entity.MetaLVA;
import at.ac.tuwien.sepm.entity.TissExam;
import at.ac.tuwien.sepm.entity.TissExamState;
import at.ac.tuwien.sepm.service.AutomaticExamRegisterService;
import at.ac.tuwien.sepm.service.DateService;
import at.ac.tuwien.sepm.service.MetaLVAService;
import at.ac.tuwien.sepm.service.ServiceException;
import at.ac.tuwien.sepm.ui.SmallInfoPanel;
import at.ac.tuwien.sepm.ui.StandardInsidePanel;
import at.ac.tuwien.sepm.ui.UI;
import at.ac.tuwien.sepm.ui.template.PanelTube;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Scheduled;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

@UI
@Scope("singleton")
public class RegisterExamPanel extends StandardInsidePanel {
    private Logger log = LogManager.getLogger(RegisterExamPanel.class);

    private AutomaticExamRegisterService automaticExamRegisterService;
    private MetaLVAService metaLVAService;
    private DateService dateService;

    private ExamPanel examPanel;
    private ExamPanel pendingExamPanel;

    private Rectangle paneExams = new Rectangle(12,55,490,413);
    private Rectangle panePending = new Rectangle(510,55,490,413);

    private JButton refresh;
    private JButton registerButton;
    private JProgressBar progressBar;

    private List<TissExam> registeredExams;

    @Autowired
    public RegisterExamPanel(AutomaticExamRegisterService automaticExamRegisterService, MetaLVAService metaLVAService, DateService dateService) {
        this.setLayout(null);
        this.setOpaque(false);
        loadFonts();
        this.automaticExamRegisterService = automaticExamRegisterService;
        this.metaLVAService = metaLVAService;
        this.dateService = dateService;
        registeredExams = new ArrayList<>();
        setBounds((int) startCoordinateOfWhiteSpace.getX(), (int) startCoordinateOfWhiteSpace.getY(),(int) whiteSpace.getWidth(),(int) whiteSpace.getHeight());
        addContent();
        addTitles();
        addButtons();
        addLegende();
    }

    private void addTitles() {
        JLabel examList = new JLabel("Prüfungen");
        examList.setFont(standardSmallerTitleFont);
        examList.setBounds((int) whiteSpace.getWidth() / 2 - (int) paneExams.getWidth() / 2 - examList.getPreferredSize().width/2, 5, examList.getPreferredSize().width, 35);
        this.add(examList);

        JLabel pendingRegistrations = new JLabel("Anmeldungen");
        pendingRegistrations.setFont(standardSmallerTitleFont);
        pendingRegistrations.setBounds((int) whiteSpace.getWidth() / 2 + (int) panePending.getWidth() / 2 - pendingRegistrations.getPreferredSize().width/2, 5, pendingRegistrations.getPreferredSize().width, 35);
        this.add(pendingRegistrations);
    }

    private void addButtons() {
        refresh = new JButton("Aktualisieren");
        refresh.setFont(standardButtonFont);
        refresh.setBounds((int) whiteSpace.getWidth() / 4 - 75, (int) paneExams.getY() + (int) paneExams.getHeight() + 8, 150, 30);
        refresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RegisterExamPanel.this.refresh();
            }
        });
        this.add(refresh);

        registerButton = new JButton("Anmelden");
        registerButton.setFont(standardButtonFont);
        registerButton.setBounds((int) paneExams.getX(), (int) paneExams.getY() + (int) paneExams.getHeight() + 8, 150, 30);
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    automaticExamRegisterService.addRegistration(examPanel.getSelectedExam());
                    PanelTube.backgroundPanel.viewSmallInfoText("Die Anmeldung fuer " + examPanel.getSelectedExam().getName() + " wird ausgefuehrt.", SmallInfoPanel.Success);
                } catch (ServiceException e1) {
                    PanelTube.backgroundPanel.viewSmallInfoText("Anmeldung fehlgeschlagen.", SmallInfoPanel.Error);
                } catch (ArrayIndexOutOfBoundsException a) {
                    PanelTube.backgroundPanel.viewSmallInfoText("Sie müssen eine Pruefung auswaehlen.", SmallInfoPanel.Error);
                }
            }
        });
        this.add(registerButton);

        progressBar = new JProgressBar();
        progressBar.setVisible(false);
        progressBar.setBounds((int)panePending.getX() + (int)panePending.getWidth()-150, (int)panePending.getY() + (int)panePending.getHeight()+8, 150,30);
        this.add(progressBar);
    }

    private void addLegende() {
        JPanel leg1Color = new JPanel();
        leg1Color.setBounds(pendingExamPanel.getX(), pendingExamPanel.getY() + pendingExamPanel.getHeight()+8, 14, 14);
        leg1Color.setBackground(new Color(0, 99, 0));

        JLabel leg1 = new JLabel("Angemeldet");
        leg1.setFont(calendarDatesFont);
        leg1.setBounds(leg1Color.getX()+leg1Color.getWidth()+2, pendingExamPanel.getY() + pendingExamPanel.getHeight()+8, 150, 14);


        JPanel leg2Color = new JPanel();
        leg2Color.setBounds(pendingExamPanel.getX(), leg1Color.getY() + leg1Color.getHeight()+2, 14, 14);
        leg2Color.setBackground(new Color(225, 138, 7));

        JLabel leg2 = new JLabel("Ausstehend");
        leg2.setFont(calendarDatesFont);
        leg2.setBounds(leg2Color.getX()+leg2Color.getWidth()+2, pendingExamPanel.getY() + pendingExamPanel.getHeight()+leg1.getHeight()+10, 150, 14);

        this.add(leg1Color);
        this.add(leg2Color);
        this.add(leg1);
        this.add(leg2);
    }

    private void addContent() {
        examPanel = new ExamPanel(new ArrayList<TissExam>(), (int) paneExams.getWidth(), (int) paneExams.getHeight(), false, this);
        examPanel.setBounds(paneExams);
        this.add(examPanel);

        pendingExamPanel = new ExamPanel(new ArrayList<TissExam>(), (int) panePending.getWidth(), (int) panePending.getHeight(), true, this);
        pendingExamPanel.setBounds(panePending);
        this.add(pendingExamPanel);
    }

    @Scheduled(fixedDelay = 3000)
    public void refreshRegistrations() {
        List<TissExam> pendingExamRegistrations = automaticExamRegisterService.getPendingExamRegistrations();
        pendingExamRegistrations.addAll(registeredExams);
        pendingExamPanel.refresh(pendingExamRegistrations);
    }

    @Override
    public void refresh() {
        setCursor(new Cursor(Cursor.WAIT_CURSOR));
        progressBar.setIndeterminate(true);
        progressBar.setVisible(true);
        FetcherTask task = new FetcherTask();
        task.execute();
    }

    public void deletePending(TissExam tissExam) {
        automaticExamRegisterService.deleteRegistrationsForLva(tissExam);
    }

    private class FetcherTask extends SwingWorker<Void, Void> {
        @Override
        protected Void doInBackground() throws Exception {
            try {
                List<TissExam> tissExamList = new ArrayList<>();
                registeredExams.clear();
                for(MetaLVA metaLVA : metaLVAService.readUncompletedByYearSemesterStudyProgress(dateService.getCurrentYearOfSemester(),dateService.getCurrentSemester(),true)) {
                    try {
                        List<TissExam> lvaExamList = automaticExamRegisterService.listExamsForLva(metaLVA.getNr());
                        for(TissExam exam : lvaExamList)
                            if(exam.getTissExamState() == TissExamState.NOT_REGISTERED)
                                tissExamList.add(exam);
                            else
                                registeredExams.add(exam);
                    } catch (ServiceException e) {
                        log.info("no exams for "+metaLVA.getNr());
                    }
                }
                examPanel.refresh(tissExamList);
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                progressBar.setIndeterminate(false);
                progressBar.setVisible(false);
            } catch (ServiceException e) {
                log.error("Exception: " + e.getMessage());
                PanelTube.backgroundPanel.viewSmallInfoText("Es ist ein Fehler aufgetreten", SmallInfoPanel.Error);
            }
            return null;
        }
    }
}
