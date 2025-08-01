package com.driagon.services.configserver.utils;

import lombok.RequiredArgsConstructor;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EncryptionUtil {

    private final StringEncryptor stringEncryptor;

    public String encrypt(String plainText) {
        if (plainText == null || plainText.trim().isEmpty() || isEncrypted(plainText)) {
            return plainText;
        }
        return stringEncryptor.encrypt(plainText);
    }

    public String decrypt(String encryptedText) {
        if (encryptedText == null || encryptedText.trim().isEmpty()) {
            return encryptedText;
        }
        try {
            return stringEncryptor.decrypt(encryptedText);
        } catch (Exception e) {
            return encryptedText;
        }
    }

    public boolean isEncrypted(String text) {
        if (text == null || text.trim().isEmpty()) {
            return false;
        }

        try {
            stringEncryptor.decrypt(text);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}