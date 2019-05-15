package handlers.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Utils {
    public static boolean fieldIsBlank(String field) {
        return (field == null || field.isEmpty());
    }

    public static boolean fieldIsBlank(String[] field) {
        if (field == null || field.length == 0) return true;
        for (String s : field) {
            if (s == null || s.isEmpty()) return true;
        }
        return false;
    }

    public static boolean fieldIsBlank(Object field) {
        if (field instanceof String) { return fieldIsBlank(((String) field)); }
        if (field instanceof String[]) { return fieldIsBlank(((String[]) field)); }
        return false;
    }

    public static String blankFieldToString(List<String> fieldsNames,  List<Object> fields) {
        List<String> blankFields = new ArrayList<>(fields.size());
        for (int i = 0; i < fieldsNames.size(); i++) {
            if (fieldIsBlank(fields.get(i))) {
                blankFields.add(fieldsNames.get(i));
            }
        }
        return String.join(", ", blankFields);
    }

    public static String blankFieldToString(Map<String, Object> fields) {
        List<String> blankFields = fields.entrySet().stream().filter(e -> fieldIsBlank(e.getValue())).map(e -> e.getKey()).collect(Collectors.toList());
        return blankFields.isEmpty() ? null : String.join(", ", blankFields);
    }
}
