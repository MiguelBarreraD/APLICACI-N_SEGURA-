package org.example;

import org.example.Authentication.AuthenticationService;

import static spark.Spark.*;
public class HelloWorld {

    private static AuthenticationService authenticationService = new AuthenticationService();
    public static void main(String[] args) {
        staticFiles.location("/public");
        port(getPort());

        //API: secure(keystoreFilePath, keystorePassword, truststoreFilePath,truststorePassword);
        secure("certificados/ecikeystore.p12", "123456", null, null);

        get("/hello", (req, res) -> "Hello World");

        get("/login", (req, res) -> {
            String user = req.queryParams("username");
            String password = req.queryParams("password");
            return authenticationService.authenticate(user, password);
        });
    }


    static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 5000; //returns default port if heroku-port isn't set (i.e. on localhost)
    }
}