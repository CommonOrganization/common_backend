package com.junghun.common.util;

import java.util.regex.Pattern;

public abstract class RegexUtils {
    private static final String EMAIL_REGEX = "[0-9a-z]([\\-.\\w]*[0-9a-z\\-_+])*@([0-9a-z][\\-\\w]*[0-9a-z]\\.)+[a-z]{2,9}";
    private static final String PASSWORD_REGEX = "[0-9a-zA-Z]{8,}";
    private static final String NAME_REGEX = "[ㄱ-ㅎ가-힣a-zA-Z]{2,8}";
    public static boolean isValidEmail(String email){
        return Pattern.matches(EMAIL_REGEX,email);
    }

    public static boolean isValidPassword(String password){
        return Pattern.matches(PASSWORD_REGEX,password);
    }
    public static boolean isValidName(String name){
        return Pattern.matches(NAME_REGEX,name);
    }
}
