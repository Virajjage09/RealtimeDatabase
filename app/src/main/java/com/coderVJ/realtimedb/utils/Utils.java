package com.coderVJ.realtimedb.utils;

import com.coderVJ.realtimedb.activities.MainActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Viraj Jage on 12/09/2017.
 */

public class Utils {

    private static String dateFormatter = "dd/MM/yyyy HH:mm:ss";
    public static boolean checkEmpty(String text) {
        boolean flag = false;
        if (text.equals("") || text == null || text.length() == 0) {
            flag = true;
        } else {
            flag = false;
        }

        return flag;
    }

    public static boolean validAmount(String amount) {

        boolean flag = false;
        if (amount.equals("0")) {
            flag = true;
        } else {
            flag = false;
        }

        return flag;
    }

    public static String DateToString(Date date) {

        DateFormat df = new SimpleDateFormat(dateFormatter);

        String strDate = df.format(date);

        return strDate;
    }

    public static boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
