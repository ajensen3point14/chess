package UnitTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import server.DAO.AuthTokenDAO;
import server.DAO.UserDAO;
import server.MyServerException;
import models.AuthToken;
import models.User;
import requests.LoginRequest;
import results.LoginResult;
import server.services.ClearService;
import server.services.LoginService;

import static org.junit.jupiter.api.Assertions.*;

class LoginServiceTest {
    @BeforeEach
    public void testSetup() {
        ClearService clear = new ClearService();
        clear.clear();
    }

    @Test
    @DisplayName("Junit login success")
    public void successLogin() {
        User user = new User("Doug", "Fillmore", "d@f.com");
        UserDAO.getInstance().insert(user);
        AuthToken token = AuthTokenDAO.getInstance().create("Doug");

        // Log a user in
        LoginRequest request = new LoginRequest();
        LoginService loginService = new LoginService();
        request.setUsername("Doug");
        request.setPassword("Fillmore");
        LoginResult result = loginService.login(request);

        assertEquals("Doug", result.getUsername());
    }

    @Test
    @DisplayName("Junit login fail")
    public void failLogin() {
        User user = new User("Doug", "Fillmore", "d@f.com");
        UserDAO.getInstance().insert(user);
        AuthToken token = AuthTokenDAO.getInstance().create("Doug");
        LoginRequest request = new LoginRequest();
        LoginService loginService = new LoginService();

        // set a bad login
        request.setUsername("");
        request.setPassword("Fillmore");

        assertThrows(MyServerException.class, () -> loginService.login(request));
    }
}