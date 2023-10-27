package UnitTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import server.DAO.AuthTokenDAO;
import server.DAO.UserDAO;
import server.MyServerException;
import server.models.AuthToken;
import server.requests.AuthTokenRequest;
import server.requests.LoginRequest;
import server.results.LoginResult;
import server.services.ClearService;
import server.services.LoginService;
import server.services.LogoutService;

import static org.junit.jupiter.api.Assertions.*;

class LogoutServiceTest {
    @BeforeEach
    public void testSetup() {
        ClearService clear = new ClearService();
        clear.clear();
    }

    @Test
    @DisplayName("Junit logout success")
    public void successLogout() {
        UserDAO.getInstance().insert("Doug", "Fillmore", "d@f.com");
        AuthToken token = AuthTokenDAO.getInstance().create("Doug");

        // Log a user in
        LoginRequest request = new LoginRequest();
        LoginService loginService = new LoginService();
        request.setUsername("Doug");
        request.setPassword("Fillmore");
        LoginResult result = loginService.login(request);

        assertEquals("Doug", result.getUsername());

        // log a user out
        AuthTokenRequest authReq = new AuthTokenRequest();
        authReq.setAuthToken(token.getAuthToken());
        LogoutService logout = new LogoutService();

        assertThrows(MyServerException.class, () -> logout.logout(authReq));
    }

    @Test
    @DisplayName("Junit login fail")
    public void failLogin() {
        UserDAO.getInstance().insert("Doug", "Fillmore", "d@f.com");
        AuthToken token = AuthTokenDAO.getInstance().create("Doug");
        AuthTokenRequest request = new AuthTokenRequest();
        LogoutService logoutService = new LogoutService();

        // set a bad logout
        request.setAuthToken("");

        assertThrows(MyServerException.class, () -> logoutService.logout(request));
    }

}