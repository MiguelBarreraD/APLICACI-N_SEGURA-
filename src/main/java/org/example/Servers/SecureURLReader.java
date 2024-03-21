package org.example.Servers;

import java.io.*;
import java.net.*;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

public class SecureURLReader {

    /**
     * Lee y devuelve los bytes de una imagen QR desde una URL externa utilizando un
     * contexto SSL seguro.
     *
     * @return Un arreglo de bytes que representa la imagen QR, o null si ocurre un
     *         error.
     */
    public static byte[] readExternalURL() {
        try {
            // Create a file and a password representation
            File trustStoreFile = new File("certificados/myTrustStore.p12");
            char[] trustStorePassword = "654321".toCharArray();

            // Load the trust store, the default type is "pkcs12", the alternative is "jks"
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            trustStore.load(new FileInputStream(trustStoreFile), trustStorePassword);

            // Get the singleton instance of the TrustManagerFactory
            TrustManagerFactory tmf = TrustManagerFactory
                    .getInstance(TrustManagerFactory.getDefaultAlgorithm());

            // Itit the TrustManagerFactory using the truststore object
            tmf.init(trustStore);

            // Print the trustManagers returned by the TMF
            // only for debugging
            for (TrustManager t : tmf.getTrustManagers()) {
                System.out.println(t);
            }

            // Set the default global SSLContext so all the connections will use it
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, tmf.getTrustManagers(), null);
            SSLContext.setDefault(sslContext);

        } catch (Exception ex) {
            Logger logger = Logger.getLogger(SecureURLReader.class.getName());
            if (ex instanceof KeyStoreException ||
                    ex instanceof FileNotFoundException ||
                    ex instanceof IOException ||
                    ex instanceof NoSuchAlgorithmException ||
                    ex instanceof CertificateException ||
                    ex instanceof KeyManagementException) {
                logger.log(Level.SEVERE, null, ex);
            } else {
                logger.log(Level.SEVERE, "Error inesperado", ex);
            }
        }
        return readURL("https://ec2-107-23-178-87.compute-1.amazonaws.com:4567/qrcode");
    }

    /**
     * Lee y devuelve los bytes de una URL especificada.
     *
     * @param sitetoread La URL de la que se leerán los bytes.
     * @return Un arreglo de bytes que representa el contenido de la URL, o null si
     *         ocurre un error.
     */
    public static byte[] readURL(String sitetoread) {
        try {
            URL siteURL = new URL(sitetoread);
            URLConnection urlConnection = siteURL.openConnection();
            InputStream inputStream = urlConnection.getInputStream();
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            int bytesRead;
            byte[] data = new byte[1024];

            while ((bytesRead = inputStream.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, bytesRead);
            }

            return buffer.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
