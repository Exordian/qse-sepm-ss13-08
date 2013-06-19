package at.ac.tuwien.sepm.service.impl;

import at.ac.tuwien.sepm.dao.PendingRegistrationDao;
import at.ac.tuwien.sepm.entity.TissExam;
import at.ac.tuwien.sepm.service.AutomaticExamRegisterService;
import at.ac.tuwien.sepm.service.PropertyService;
import at.ac.tuwien.sepm.service.ServiceException;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.*;

@Service
public class AutomaticExamRegisterServiceImpl implements AutomaticExamRegisterService {
    private static Logger log = Logger.getLogger(AutomaticExamRegisterServiceImpl.class);

    @Autowired
    private ZidAuthService zidAuthService;

    @Autowired
    private PropertyService propertyService;

    @Autowired
    private PendingRegistrationDao pendingRegistrationDao;

    Vector<TissExam> tissExamList = new Vector<>();

    //@Value("${timeout}")
    private static int timeout = 10000;

    //@Value("${baseUrl}")
    private static String BASE_URL = "https://tiss.tuwien.ac.at/";

    private static final String EDU_DETAIL_URL = "/course/educationDetails.xhtml?semester=%s&courseNr=%s";
    private static final String EDU_EXAM_BUTTON = "";

    private static final DateTimeFormatter tissDateFormat = DateTimeFormat.forPattern("dd.MM.yyyy, HH:mm");

    @Override
    public boolean registerForExam(String lvanr, String examname) throws ServiceException {
        login();
        if(lvanr == null || lvanr.isEmpty())
            throw new ServiceException("invalid study number");

        lvanr = flattenLvaNr(lvanr);

        try {
            Document doc = Jsoup.connect(BASE_URL+String.format(EDU_DETAIL_URL, getCurrentSemester(), lvanr)).cookies(zidAuthService.getTISSCookies()).get();
            if(doc.select("#examDateLink").size() == 0)
                throw new ServiceException("no exams for this lva");
            String examListLink = doc.select("#examDateLink a").attr("href");

            Document examListDoc = Jsoup.connect(BASE_URL+examListLink).cookies(zidAuthService.getTISSCookies()).get();

            Elements groups = examListDoc.select("div .groupWrapper");

            for(Element group : groups) {
                if(!group.select(".groupHeaderWrapper span").first().text().trim().equals(examname))
                    continue;
                Map<String, String> postData = new HashMap<>();
                postData.put("javax.faces.ViewState", examListDoc.select("input[id=javax.faces.ViewState]").first().attr("value"));
                postData.put("examDateListForm_SUBMIT", "1");
                postData.put("examDateListForm:confirmCancelBtn", "Abbrechen");
                postData.put(group.select("input").attr("name"), "Anmelden");

                String postAction = examListDoc.select("form[id=examDateListForm]").attr("action");
                if(postAction == null || postAction.isEmpty())
                    return false;
                Document confirmPage = Jsoup.connect(BASE_URL+postAction).cookies(zidAuthService.getTISSCookies()).data(postData).header("Content-Type","application/x-www-form-urlencoded").post();
                postData.clear();
                postData.put(confirmPage.select("input[value=Anmelden]").attr("name"), confirmPage.select("input[value=Anmelden]").attr("value"));
                postData.put("javax.faces.ViewState", confirmPage.select("input[id=javax.faces.ViewState]").first().attr("value"));
                postData.put("regForm_SUBMIT", "1");
                postAction = confirmPage.select("form[id=regForm]").attr("action");
                if(postAction == null || postAction.isEmpty())
                    return false;
                Jsoup.connect(BASE_URL+postAction).cookies(zidAuthService.getTISSCookies()).data(postData).header("Content-Type","application/x-www-form-urlencoded").post();
                return true;
            }
            return false;
        } catch (HttpStatusException e) {
            return false;
        } catch (IOException e) {
            throw new ServiceException("connection failed", e);
        }
    }


    @Override
    public List<TissExam> listExamsForLva(String lvanr) throws ServiceException {
        login();
        if(lvanr == null || lvanr.isEmpty())
            throw new ServiceException("invalid study number");

        lvanr = flattenLvaNr(lvanr);
        List<TissExam> tissExamList = new ArrayList<>();

        try {
            Document doc = Jsoup.connect(BASE_URL+String.format(EDU_DETAIL_URL, getCurrentSemester(), lvanr)).timeout(timeout).cookies(zidAuthService.getTISSCookies()).get();
            if(doc.select("#examDateLink").size() == 0)
                throw new ServiceException("no exams for this lva");
            String examListLink = doc.select("#examDateLink a").attr("href");

            Document examListDoc = Jsoup.connect(BASE_URL+examListLink).cookies(zidAuthService.getTISSCookies()).timeout(timeout).get();

            Elements groups = examListDoc.select("div .groupWrapper");

            for(Element group : groups) {
                if(group.select("input[value=Abmelden]").size() > 0)
                    continue;
                TissExam tissExam = new TissExam();
                tissExam.setLvanr(lvanr);
                tissExam.setName(group.select(".groupHeaderWrapper span").first().text().trim());
                tissExam.setMode(group.getElementsByAttributeValueContaining("id", "examMode").first().text());
                tissExam.setStartRegistration(DateTime.parse(group.getElementsByAttributeValueContaining("id", "appBegin").first().text(), tissDateFormat));
                tissExam.setEndRegistration(DateTime.parse(group.getElementsByAttributeValueContaining("id", "appEnd").first().text(), tissDateFormat));
                tissExamList.add(tissExam);
            }
        } catch(IllegalArgumentException e) {
            log.warn("lva exam has invalid data "+lvanr);
        } catch (IOException e) {
            throw new ServiceException("connection failed", e);
        }
        return tissExamList;
    }

    //@Scheduled(fixedDelay=(5 * 60 *1000))
    //@PostConstruct
    private void loadFromDB() {
        List<TissExam> tissExams = pendingRegistrationDao.readAllTissExams();
        for(TissExam e : tissExams) {
            if(DateTime.now().isAfter(e.getEndRegistration())) {
                pendingRegistrationDao.delete(e.getId());
                continue;
            }
            if(DateTime.now().minusMinutes(5).isAfter(e.getStartRegistration()))
                tissExamList.add(e);
        }
    }

    @Override
    public void addRegistration(TissExam tissExam) throws ServiceException {
        try {
            pendingRegistrationDao.create(tissExam);
            loadFromDB();
        } catch (IOException e) {
            throw new ServiceException("invalid tiss exam", e);
        }
    }

    @Scheduled(fixedDelay = 3000)
    private void tryRegister() {
        Iterator<TissExam> it = tissExamList.iterator();
        while(it.hasNext()) {
            TissExam e = it.next();
            try {
                boolean success = registerForExam(e.getLvanr(), e.getName());
                if(success) {
                    pendingRegistrationDao.delete(e.getId());
                    it.remove();
                }
            } catch (ServiceException e1) {
                log.error("no internet connection - register failing", e1);
            }
        }
    }

    @Override
    public List<TissExam> getPendingExamRegistrations() {
        return pendingRegistrationDao.readAllTissExams();
    }

    private String getCurrentSemester() {
        return String.valueOf(DateTime.now().getYear()) + ((DateTime.now().getMonthOfYear() < 8)? "S":"W");
    }

    private String flattenLvaNr(String lvaNr) {
        return lvaNr.replace(".", "").replace(" ", "");
    }

    private void login() throws ServiceException {
        try {
            zidAuthService.getTISSCookies();
        } catch (ServiceException e) {
            zidAuthService.authenticate(propertyService.getProperty("tiss.user"), propertyService.getProperty("tiss.password"));
            zidAuthService.loginTISS();
        }
    }

}
