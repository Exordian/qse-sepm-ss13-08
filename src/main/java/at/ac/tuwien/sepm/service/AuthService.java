package at.ac.tuwien.sepm.service;

import java.util.Map;

public interface AuthService  {

    /**
     * ZID Single Sign on - Authentication Service
     * @param username student number
     * @param password zid password
     * @throws ServiceException thrown if zid service if not available or invalid authentication data
     */
    void authenticate(String username, String password) throws ServiceException;

    /**
     * cookies required for zid recognition
     * @return auth cookies
     * @throws ServiceException if not autenticated before
     */
    Map<String, String> getLoginCookies() throws ServiceException;

    /**
     * login to tiss from zid
     * @throws ServiceException if not autenticated before or service not available
     */
    void loginTISS() throws ServiceException;

    /**
     * cookies required for tiss recognition
     * @return auth cookies
     * @throws ServiceException if not autenticated before
     */
    Map<String, String> getTISSCookies() throws ServiceException;
}
