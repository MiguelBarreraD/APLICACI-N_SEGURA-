package org.example.Authentication;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class AuthenticationService {

    public AuthenticationService() {
        initializeUsers();
    }

    private static Map<String, String> users = new HashMap<>();

    /**
     * Agrega un nuevo usuario al mapa de usuarios.
     *
     * @param username El nombre de usuario a agregar.
     * @param password La contraseña del usuario a agregar.
     */
    public void addUser(String username, String password) {
        String hashedPassword = getSHA256Hash(password);
        users.put(username, hashedPassword);
    }

    /**
     * Autentica a un usuario verificando su nombre de usuario y contraseña.
     *
     * @param username El nombre de usuario a autenticar.
     * @param password La contraseña del usuario a autenticar.
     * @return true si el usuario y la contraseña son válidos, false en caso contrario.
     */
    public static boolean authenticate(String username, String password) {
        String hashedPassword = getSHA256Hash(password);
        return users.containsKey(username) && users.get(username).equals(hashedPassword);
    }

    /**
     * Convierte un arreglo de bytes a una cadena de texto hexadecimal.
     *
     * @param hash El arreglo de bytes a convertir.
     * @return Una cadena de texto hexadecimal que representa el arreglo de bytes.
     */
    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    /**
     * Calcula el hash SHA-256 de la cadena de entrada.
     *
     * @param input La cadena de entrada para calcular el hash.
     * @return El hash SHA-256 de la cadena de entrada como una cadena de texto hexadecimal.
     */
    public static String getSHA256Hash(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedhash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(encodedhash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Inicializa el mapa de usuarios con algunos usuarios predefinidos.
     */
    public void initializeUsers() {
        addUser("user1", "password1");
        addUser("user2", "password2");
        addUser("user3", "password3");
    }
}