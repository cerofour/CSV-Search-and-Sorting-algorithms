package pe.edu.utp.csvsorterandsearcher.CSV;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.stream.Stream;

/**
 * This class provides methods for detecting and converting data types.
 */
public class DataTypeDetector {

    /**
     * Checks if the given value can be parsed as a double.
     * @param value the value to be checked
     * @return true if the value can be parsed as a double, false otherwise
     */
    private static boolean isDouble(String value) {
        try {
            Double.parseDouble(value);
            return true;
        } catch (NumberFormatException e){
            return false;
        }
    }

    /**
     * Checks if the given value can be parsed as a date.
     * @param value the value to be checked
     * @return true if the value can be parsed as a date, false otherwise
     */
    private static boolean isDate(String value) {
        try {
            LocalDate.parse(value);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * Checks if the given value represents a boolean.
     * @param value the value to be checked
     * @return true if the value represents a boolean, false otherwise
     */
    private static boolean isBoolean(String value) {
        return value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false");
    }

    /**
     * Detects the data type of the given value.
     * @param value the value whose data type needs to be detected
     * @return the detected data type
     */
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

    /**
     * Converts an array of items to the specified data type.
     * @param items    the array of items to be converted
     * @param dataType the target data type
     * @return an array of items converted to the specified data type
     */
    public static Object[] dataTypeConversion(String[] items, FieldType dataType){
        return switch (dataType){
            case String -> items.clone();
            case Boolean -> Stream.of(items)
                    .map(i -> (i.equalsIgnoreCase("true") ? 1 : 0 ))
                    .toArray(Integer[]::new);
            case Double -> Stream.of(items).map(Double::parseDouble).toArray(Double[]::new);
            case Date -> Stream.of(items).map(LocalDate::parse).toArray(LocalDate[]::new);
        };
    }

    /**
     * Converts an item to the specified data type.
     * @param item     the item to be converted
     * @param dataType the target data type
     * @return the item converted to the specified data type
     */
    public static Object dataTypeConversion(String item, FieldType dataType){
        return switch (dataType){
            case String -> item;
            case Boolean -> Integer.parseInt(item.equalsIgnoreCase("true") ? "1" : "0" );
            case Double -> Double.parseDouble(item);
            case Date -> LocalDate.parse(item);
        };
    }


}
