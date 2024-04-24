package pe.edu.utp.csvsorterandsearcher.CSV;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.stream.Stream;

public class DataTypeDetector {

    private static boolean isDouble(String value) {
        try {
            Double.parseDouble(value);
            return true;
        } catch (NumberFormatException e){
            return false;
        }
    }

    private static boolean isDate(String value) {
        try {
            LocalDate.parse(value);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    private static boolean isBoolean(String value) {
        return value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false");
    }

    public static FieldType dataTypeDetector(String value) {
        if (DataTypeDetector.isDouble(value)) {
            return FieldType.Double;

        } else if (DataTypeDetector.isDate(value)) {
            return FieldType.Date;

        } else if (DataTypeDetector.isBoolean(value)) {
            return FieldType.Boolean;
        } else {
            return FieldType.String;
        }
    }

    public static Object[] dataTypeConversion(String[] items, FieldType dataType){
        return switch (dataType){
            case String -> items;
            case Boolean -> Stream.of(items)
                    .map(i -> (i.equalsIgnoreCase("true") ? 1 : 0 ))
                    .toArray();
            case Double -> Stream.of(items).map(Double::parseDouble).toArray();
            case Date -> Stream.of(items).map(LocalDate::parse).toArray();
        };
    }


}
