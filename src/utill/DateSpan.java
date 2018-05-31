package utill;

import java.util.Comparator;

/**
 * Time-span  of one month with only accountable days
 * @author Michael Melena
 * */
public class DateSpan implements Comparable<DateSpan> {


    /**
     * Compares based on number of accountable day in time-span
     * */
    @SuppressWarnings("unused")
    private static class NumberOfDaysComparator implements Comparator<DateSpan>
    {
        @Override
        public int compare(DateSpan a, DateSpan b) {
            return a.getAmountOfDays() - b.getAmountOfDays();
        }

    }

    @SuppressWarnings("unused")
    private static final long serialVersionUID = 1L;
    private int amountOfDays;
    private int month;
    private int year;


    private double value;
    public DateSpan(Date date)
    {
        this.amountOfDays = date.getDay();
        this.month = date.getMonth();
        this.year = date.getYear();

    }
    /**
     * Compares based on value
     * */
    @Override
    public int compareTo(DateSpan t) {
        return (int)Math.signum(value -t.getValue());
    }
    public int getAmountOfDays()
    {
        return amountOfDays;
    }

    public int getMonth()
    {
        return month;
    }

    /**
     * @return The value for this timespan
     * */
    public double getValue()
    {
        return value;
    }

    public int getYear()
    {
        return year;
    }

    /**
     * @param value The value for to for this timespan
     * */
    public void setValue(double value)
    {
        this.value=value;
    }
}

