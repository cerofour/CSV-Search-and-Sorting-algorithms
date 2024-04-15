package pe.edu.utp.csvsorterandsearcher.Utilities;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class DataTypeDetector {

    private static boolean isDouble(String value){
        try {
            Double.parseDouble(value);
            return true;
        } catch (NumberFormatException e){
            return false;
        }
    }

    private static boolean isDate(String value){
        try{
            LocalDate.parse(value);
            return true;
        }catch (DateTimeParseException e){
            return false;
        }
    }

    private static boolean isBoolean(String value){
        return value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false");
    }

    public static FieldType dataTypeDetector(String value){
        if(DataTypeDetector.isDouble(value)){
            return FieldType.Numeric;
        } else if (DataTypeDetector.isDate(value)) {
            return FieldType.Date;

        } else if (DataTypeDetector.isBoolean(value)){
            return FieldType.Boolean;
        }
        else{
            return FieldType.String;
        }
    }

}
