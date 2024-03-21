package org.example.GenQR;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

public class GenQR {

    private static String URL = "https://github.com/MiguelBarreraD";
    private static int WIDTH = 300;
    private static int HEIGHT = 300;

    /**
     * Genera una imagen de código QR en formato PNG como un arreglo de bytes.
     *
     * @return Un arreglo de bytes que representa la imagen de código QR en formato PNG, o null si ocurre un error.
     */
    public static byte[] generateQRCodeImage() {
        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(URL, BarcodeFormat.QR_CODE, WIDTH, HEIGHT);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            MatrixToImageConfig config = new MatrixToImageConfig(0xFF000001, 0xFFFFFFFF);
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", byteArrayOutputStream, config);
            return byteArrayOutputStream.toByteArray();
        } catch (WriterException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}