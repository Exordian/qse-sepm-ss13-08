package at.ac.tuwien.sepm.service.impl;

import at.ac.tuwien.sepm.service.AuthService;
import at.ac.tuwien.sepm.service.ServiceException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext-test.xml"})
public class ZidAuthServiceTest {
    @Autowired
    AuthService authService;

    @Test(expected = ServiceException.class)
    public void testGetLoginCookiesBeforeTest() throws Exception {
        authService.getLoginCookies();
    }

    @Test(expected = ServiceException.class)
    public void testAuthenticateFail() throws Exception {
        authService.authenticate("blub", "bla");
    }

    @Test(expected = ServiceException.class)
    public void testLoginCookiesAfterAuthenticationFail() throws Exception {
        try {
            authService.authenticate("blub", "bla");
        } catch (Exception e) {}
        authService.getLoginCookies();
    }

    @Test(expected = ServiceException.class)
    public void testLoginTissWithoutAuth() throws Exception {
        authService.loginTISS();
    }

    @Test(expected = ServiceException.class)
    public void testgetTissCookiesWithoutAuth() throws Exception {
        authService.getTISSCookies();
    }
}
