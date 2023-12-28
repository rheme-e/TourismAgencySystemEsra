package com.TourismAgencySystem.Helper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneNumberValidator {

    private static final String PHONE_NUMBER_PATTERN = "^\\d{11}$";

    private static final Pattern pattern = Pattern.compile(PHONE_NUMBER_PATTERN);

    public static boolean validate(final String phoneNumber) {
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }


}
