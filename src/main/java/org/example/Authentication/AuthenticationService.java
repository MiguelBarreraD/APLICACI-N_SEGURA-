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

    public void addUser(String username, String password) {
        String hashedPassword = getSHA256Hash(password);
        users.put(username, hashedPassword);
    }

    public  static  boolean authenticate(String username, String password) {
        String hashedPassword = getSHA256Hash(password);
        return users.containsKey(username) && users.get(username).equals(hashedPassword);
    }


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

    public static String getSHA256Hash(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedhash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(encodedhash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public void initializeUsers() {
        addUser("user1", "password1");
        addUser("user2", "password2");
        addUser("user3", "password3");
    }

}
