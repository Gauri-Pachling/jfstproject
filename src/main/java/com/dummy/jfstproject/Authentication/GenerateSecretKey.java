package com.dummy.jfstproject.Authentication;

import java.security.SecureRandom;
import java.util.Base64;

public class GenerateSecretKey {
    public static void main(String[] args) {
        SecureRandom secureRandom = new SecureRandom();
        byte[] secretKey = new byte[32]; // 32 bytes = 256 bits
        secureRandom.nextBytes(secretKey);
        System.out.println(Base64.getEncoder().encodeToString(secretKey));
    }
}
