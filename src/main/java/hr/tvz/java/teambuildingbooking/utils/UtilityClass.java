package hr.tvz.java.teambuildingbooking.utils;

import java.util.Base64;

public class UtilityClass {

    public static String convertByteArrayToBase64String(byte[] bytes, String contentType) {
        return "data:" + contentType + ";base64," + Base64.getEncoder().encodeToString(bytes);
    }
}
