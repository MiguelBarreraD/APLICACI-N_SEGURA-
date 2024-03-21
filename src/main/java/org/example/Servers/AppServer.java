    package org.example.Servers;

    import org.example.Authentication.AuthenticationService;

    import static spark.Spark.*;
    public class AppServer {

        private static AuthenticationService authenticationService = new AuthenticationService();
        public static void main(String[] args) {
            staticFiles.location("/public");
            port(getPort());

            //API: secure(keystoreFilePath, keystorePassword, truststoreFilePath,truststorePassword);
            secure("certificados/ecikeystore.p12", "123456", null, null);

            get("/login", (req, res) -> {
                String user = req.queryParams("username");
                String password = req.queryParams("password");
                if (authenticationService.authenticate(user, password)) {
                    byte[] qrCodeImage = SecureURLReader.readExternalURL();
                    res.type("image/png");
                    res.status(200); 
                    return qrCodeImage;
                } else {
                    res.status(401); 
                    return "Authentication failed";
                }
            });
        }


        static int getPort() {
            if (System.getenv("PORT") != null) {
                return Integer.parseInt(System.getenv("PORT"));
            }
            return 5000; 
        }
    }