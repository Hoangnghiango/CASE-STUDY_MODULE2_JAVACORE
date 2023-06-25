package untils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {

    private static final String DEFAULT_DATE_FORMAT = "MM/dd/yyyy";


    private static Pattern pattern;
    private static final String USER_NAME = "/^[a-z0-9_-]{3,16}$/";
    private static final String FULL_NAME_REGEX = "^[\\p{L} .'-]+$";
    private static final String ADDRESS_NUMBER_REGEX = "^\\d+$";
    private static final String PHONE_NUMBER_REGEX = "^(84|0[3|5|7|8|9])+([0-9]{8})$";
    private static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@])[A-Za-z\\d@]{8,}$";
    private static final String EMAIL_REGEX = "[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}";
    private static final String DATE_REGEX = "([0-9]{2})/([0-9]{2})/([0-9]{4})";
    //^\d{4}-\d{2}-\d{2}$
    //^[0-3]?[0-9]/[0-3]?[0-9]/(?:[0-9]{2})?[0-9]{2}$
    private static final String MONTH_AND_YEAR_REGEX = "^(0?[1-9]|1[0-2])/([0-9]{4})$";
    private static final String MONTH = "[1-12]{1,2}";
    private static final String DAYS = "(0[1-9]|[12]\\d|3[01])\n";
    private static final String YEAR = "^[12][0-9]{3}$";


    private static final String DATE_REGEX2 ="^(0[1-9]|[12][0-9]|[3][01])/(0[1-9]|1[012])/\\d{4}$";

    public static boolean validate(String regex, String regexPattern) {
            regexPattern = regexPattern.toUpperCase();
            switch (regexPattern) {
                case "USER_NAME" -> pattern = Pattern.compile(USER_NAME);
                case "FULL_NAME" -> pattern = Pattern.compile(FULL_NAME_REGEX);
                case "NUMBER" -> pattern = Pattern.compile(ADDRESS_NUMBER_REGEX);
                case "EMAIL" -> pattern = Pattern.compile(EMAIL_REGEX);
                case "PHONE_NUMBER" -> pattern = Pattern.compile(PHONE_NUMBER_REGEX);
                case "DATE" -> pattern = Pattern.compile(DATE_REGEX);
                case "PASSWORD" -> pattern = Pattern.compile(PASSWORD_REGEX);
                case "MONTH" -> pattern = Pattern.compile(MONTH);
                case "DAYS" -> pattern = Pattern.compile(DAYS);
                case "YEAR" -> pattern = Pattern.compile(YEAR);

            }
            Matcher matcher = pattern.matcher(regex);
            return !matcher.matches();
        }



    public static boolean checkRegexUserName(String username) {
        // Biểu thức chính quy để kiểm tra tên người dùng
        String regex = "^[a-zA-Z0-9_]+$";

        // Kiểm tra sự khớp của username với biểu thức chính quy
        return Pattern.matches(regex, username);
    }
}


