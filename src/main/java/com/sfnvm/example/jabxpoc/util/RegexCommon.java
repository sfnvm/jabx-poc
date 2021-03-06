package com.sfnvm.example.jabxpoc.util;

import lombok.extern.slf4j.Slf4j;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class RegexCommon {
    private RegexCommon() {
    }

    private static final Pattern SENDER_CODE_PATTERN = Pattern.compile("^[VK](\\d{10}|\\d{13})$");

    private static final Pattern MESSAGE_CODE_PATTERN = Pattern.compile("^[VK](\\d{10}|\\d{13})[\\dABCDEF]{32}$");

    private static final Pattern TIN_PATTERN = Pattern.compile("\\d{10}|(\\d{10}-\\d{3})");

    private static final Pattern MTDIEP_PATTERN = Pattern.compile("<MTDiep>([\\w\\d-]+)</MTDiep>");

    private static final Pattern MLTDIEP_PATTERN = Pattern.compile("<MLTDiep>([\\w\\d-]+)</MLTDiep>");

    public static String extractMessageCode(String payload) {
        try {
            Matcher matcher = MTDIEP_PATTERN.matcher(payload);
            if (matcher.find()) {
                return matcher.group(1);
            }
        } catch (Exception ex) {
            log.error("Cannot extract mtdiep: {}", payload, ex);
        }
        return "";
    }

    public static Integer extractMessageType(String payload) {
        try {
            Matcher matcher = MLTDIEP_PATTERN.matcher(payload);
            if (matcher.find()) {
                return Integer.parseInt(matcher.group(1));
            }
        } catch (Exception ex) {
            log.error("Cannot extract mtdiep: {}", payload, ex);
        }
        return Integer.MIN_VALUE;
    }

    public static boolean validSenderCode(String tin) {
        if (tin == null) return false;
        return SENDER_CODE_PATTERN.matcher(tin).matches();
    }

    public static boolean validMessageCode(String messageCode) {
        if (messageCode == null) return false;
        return MESSAGE_CODE_PATTERN.matcher(messageCode).matches();
    }

    public static boolean validTin(String tin) {
        if (tin == null) return false;
        return TIN_PATTERN.matcher(tin).matches();
    }
}
