package org.example.Servers;

import static spark.Spark.*;

import org.example.GenQR.GenQR;

public class QRCodeAPI {

    private static final GenQR generateQRCodeImage = new GenQR();
    public static void main(String[] args) {
        port(getPort());

        secure("certificados/ecikeystore.p12", "123456", null, null);

        get("/qrcode", (req, res) -> {
            byte[] qrCodeImage = generateQRCodeImage.generateQRCodeImage();
            res.type("image/png");
            return qrCodeImage;
        });
    }

    static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 4567; 
    }

}
