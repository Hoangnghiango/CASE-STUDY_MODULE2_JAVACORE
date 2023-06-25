package untils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Inputs {

    private static final String DEFAULT_DATE_FORMAT = "MM/dd/yyyy";

    private static final Scanner scanner = new Scanner(System.in);

    public static String prompt(String request){
        System.out.println(request);
        return scanner.nextLine();
    }
    public static String prompt(String request, String regexPattern) {
        String text;
        do {
            text = prompt(request);
            if (Validation.validate(text, regexPattern)) {
                System.err.println("Invalid input! Incorrect format!");
            }
        } while (Validation.validate(text, regexPattern));
        return text;
    }
//    public static Date promptDate1(String request){
//        String text;
//        do {
//            System.out.println("Enter Check-In Date mm/dd/yyyy example 02/01/2020");
//            text = prompt(request);
//            return Validation.getInputDate(text);
//        } while (Validation.getInputDate(text) == null);
//
//    }
    public static Date promptDate(String request){
        String text;
        do {
            try {
                System.out.println("Enter Check-In Date mm/dd/yyyy example 02/01/2020");
                text = prompt(request);
                return new SimpleDateFormat(DEFAULT_DATE_FORMAT).parse(text);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } while ( true );

    }
    public static int IntegerPrompt(String request){
        int output;
        do {
            try{
                System.out.println(request);
                output = Integer.parseInt(scanner.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input !!! Incorrect format : " + e.getMessage());
            }
        } while (true);
        return output;
    }
    public static double DoublePrompt(String request){
        double output;
        do {
            try{
                System.out.println(request);
                output = Double.parseDouble(scanner.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input !!! Incorrect format : " + e.getMessage());
            }
        } while (true);
        return output;
    }

}
