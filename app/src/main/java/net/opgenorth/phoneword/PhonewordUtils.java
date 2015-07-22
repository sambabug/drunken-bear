package net.opgenorth.phoneword;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class PhonewordUtils {

    private static final String SETTINGS_SAVED_PHONEWORDS = "net.opgenorth.phoneword.saved";
    private static final String SETTINGS_PHONEWORD = "net.opgenorth.phoneword";

    private PhonewordUtils() {

    }


    public static String formatStringAsPhoneNumber(String input) {
        String output;
        switch (input.length()) {
            case 7:
                output = String.format("%s-%s", input.substring(0,3), input.substring(3,7));
                break;
            case 10:
                output = String.format("(%s) %s-%s", input.substring(0,3), input.substring(3,6), input.substring(6,10));
                break;
            case 11:
                output = String.format("%s (%s) %s-%s", input.substring(0,1) ,input.substring(1,4), input.substring(4,7), input.substring(7,11));
                break;
            case 12:
                output = String.format("+%s (%s) %s-%s", input.substring(0,2) ,input.substring(2,5), input.substring(5,8), input.substring(8,12));
                break;
            default:
                return null;
        }
        return output;
    }

    public static String toNumber(String raw) {
        if (TextUtils.isEmpty(raw)) {
            return "";
        } else {
            raw = raw.toUpperCase();
        }

        StringBuilder newNumber = new StringBuilder();

        for (int i = 0; i < raw.length(); i++) {
            String c = raw.substring(i, i + 1);

            if ("0123456789".contains(c)) {
                newNumber.append(c);
            } else {
                int result = translateToNumber(c);
                if (result > 0) {
                    newNumber.append(result);
                }
            }
        }
        return formatStringAsPhoneNumber(newNumber.toString());

    }

    static int translateToNumber(CharSequence c) {
        if ("ABC".contains(c)) {
            return 2;
        } else if ("DEF".contains(c)) {
            return 3;
        } else if ("GHI".contains(c)) {
            return 4;
        } else if ("JKL".contains(c)) {
            return 5;
        } else if ("MNO".contains(c)) {
            return 6;
        } else if ("PQRS".contains(c)) {
            return 7;
        } else if ("TUV".contains(c)) {
            return 8;
        } else if ("WXYZ".contains(c)) {
            return 9;
        } else {
            return -1;
        }
    }

    public static void savePhoneword(Context context, String number) {

        SharedPreferences settings = context.getSharedPreferences(SETTINGS_PHONEWORD, 0);
        Set<String> phonewords = settings.getStringSet(SETTINGS_SAVED_PHONEWORDS, new HashSet<String>());

        phonewords.add(number);
        settings.edit().putStringSet(SETTINGS_SAVED_PHONEWORDS, phonewords).apply();
    }

    public static List<String> getPhonewords(Context context) {
        SharedPreferences settings = context.getSharedPreferences(SETTINGS_PHONEWORD, 0);
        Set<String> phonewords = settings.getStringSet(SETTINGS_SAVED_PHONEWORDS, new HashSet<String>());
        return new ArrayList<String>(phonewords);
    }

}
