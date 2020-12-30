package vn.elca.training.util;

import vn.elca.training.model.exception.IllegalUserFormException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class FormUtils {

    public static boolean isShort(String strShort) {
        try {
            short s = Short.parseShort(strShort);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public static LocalDate formatLocalDate(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            return LocalDate.parse(dateString, formatter);
        } catch (Exception e){
            throw new IllegalUserFormException("Date Is Not Parsable");
        }
    }

}
