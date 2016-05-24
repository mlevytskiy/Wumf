package io.wumf.wumf.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by max on 17.06.15.
 */
public class Util {

    public static List<String> prepareNumbers(List<String> numbers) {
        List<String> result = new ArrayList<>();
        for (String str : numbers) {
            result.add(prepareNumber(str));
        }
        return result;
    }

    public static String prepareNumber(String number) {
        String str = number.replaceAll("[\\D\\s+_-]+", "");
        if (str.startsWith("0")) {
            str = "38" + str;
        }
        return "+" + str;
    }
}
