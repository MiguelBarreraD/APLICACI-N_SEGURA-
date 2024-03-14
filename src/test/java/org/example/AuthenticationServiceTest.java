package org.example;

import org.example.Authentication.AuthenticationService;
import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.*;
import org.junit.Test;
import  org.junit.Before;

public class AuthenticationServiceTest {

    private AuthenticationService authenticationService;

    @Before
    public void setUp() {
        authenticationService = new AuthenticationService();
    }

    @Test
    public void testAddUser() {
        String username = "testUser";
        String password = "testPassword";
        authenticationService.addUser(username, password);
        assertTrue(authenticationService.authenticate(username, password));
    }

    @Test
    public void testAuthenticate() {
        String username = "testUser";
        String password = "testPassword";
        authenticationService.addUser(username, password);
        assertTrue(authenticationService.authenticate(username, password));
        assertFalse(authenticationService.authenticate(username, "wrongPassword"));
    }

    @Test
    public void testAuthenticateWithNonExistentUser() {
        assertFalse(authenticationService.authenticate("nonExistentUser", "password"));
    }
}
