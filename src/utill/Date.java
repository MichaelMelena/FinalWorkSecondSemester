package utill;

import java.time.LocalDate;
/**
 * Date
 * represents current time with days months and year. Acount for a leap year.
 * */
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Date {
    private LocalDate innerDate;
    public static final DateTimeFormatter timeFromat= DateTimeFormatter.ofPattern("dd MM uuuu");
    /**
     * The static int array with number of days for each month( not a leap year)
     * */
    public final static int[] normalYear= {31,28,31,30,31,30,31,31,30,31,30,31};

    /**
     * Factory method
     * created instance prom parameters
     * @param text The String representation of the date
     * @return The new Date object with parsed values from parameters
     * @throws  NumberFormatException if one of parameters could not be parsed.
     * */
    public static  Date init(String text) throws DateTimeParseException
    {
        return new Date(LocalDate.parse(text, timeFromat));
    }
    /**
     * @param year The year to be tested for a leap year.
     * @return True if the year is a leap year and False if is not.
     * */
    public static  boolean isLeapYear(int year )//funguje korektne minimalne do roku 2400
    {
        return (year%4==0 && year%100!=0 ||year%4==0 && year%100==0 && year%400==0 );
    }


    /**
     * Constructor
     * @param day The day in month.
     * @param month The month in year (1-12)
     * @param year The year (0-3000).
     * */
    public Date(int day, int month , int year) throws DateTimeParseException
    {
        innerDate =  LocalDate.of(year, month, day);
    }
    public  Date(LocalDate date)
    {
        innerDate=date;
    }
    /**
     * @return The day of the Date
     * */
    public int getDay()
    {
        return innerDate.getDayOfMonth();
    }

    /**
     * @return The month of the Date
     * */
    public int getMonth()
    {
        return innerDate.getMonthValue();
    }

    /**
     * @return The year of the Date
     * */
    public int getYear()
    {
        return innerDate.getYear();
    }

    /**
     * Overriden toString method.
     * @return The representation of Date object in format (day month year) without the brackets
     * */
    @Override
    public String toString() {
        return innerDate.format(timeFromat);
    }
}
