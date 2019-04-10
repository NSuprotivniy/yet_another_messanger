package handlers.utils;

import java.util.ArrayList;
import java.util.List;

public class Utils {
    public static boolean fieldIsBlank(String field) {
        return (field == null || field.isEmpty());
    }

    public static String blankFieldToString(String[] fields, boolean[] isBlank) {
        List<String> blankFields = new ArrayList<>(fields.length);
        for (int i = 0; i < isBlank.length; i++) {
            if (isBlank[i]) {
                blankFields.add(fields[i]);
            }
        }
        return String.join(", ", blankFields);
    }
}
