package at.ac.tuwien.sepm.service.impl;

import at.ac.tuwien.sepm.service.AuthService;
import at.ac.tuwien.sepm.service.ServiceException;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
public class ZidAuthService implements AuthService {
    private Map<String, String> loginCookies = null;
    private Map<String, String> tissCookies = null;


    private static String ZID_AUTH_PORTAL_URL = "https://iu.zid.tuwien.ac.at/AuthServ.portal";
    private static String TISS_AUTH_PORTAL_URL = "https://iu.zid.tuwien.ac.at/AuthServ.authenticate?app=76";

    @Override
    public void authenticate(String username, String password) throws ServiceException {
        try {
            Connection.Response res = Jsoup.connect(ZID_AUTH_PORTAL_URL).data("name", username, "pw", password).method(Connection.Method.POST).execute();
            loginCookies = res.cookies();
            if(loginCookies.size() == 0)
                throw new ServiceException("authentication failed");
        } catch (IOException e) {
            throw new ServiceException("authentication failed");
        }
    }

    @Override
    public Map<String, String> getLoginCookies() throws ServiceException {
        if(loginCookies == null || loginCookies.size() == 0)
            throw new ServiceException("not authenticated");
        return loginCookies;
    }

    @Override
    public void loginTISS() throws ServiceException {
        try {
            Connection.Response res = Jsoup.connect(TISS_AUTH_PORTAL_URL).cookies(getLoginCookies()).method(Connection.Method.GET).execute();
            tissCookies = res.cookies();
            if(tissCookies.size() == 0)
                throw new ServiceException("tiss auth failed");
        } catch (IOException e) {
            throw new ServiceException("tiss auth failed");
        }

    }

    @Override
    public Map<String, String> getTISSCookies() throws ServiceException {
        if(tissCookies == null || tissCookies.size() == 0)
            throw new ServiceException("not logged in");
        return tissCookies;
    }
}
