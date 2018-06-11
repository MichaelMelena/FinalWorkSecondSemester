package utill;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Comparator;

public class BugetKey implements  Comparable<BugetKey> {
    Double value;
    LocalDate start;
    LocalDate end;
    String text;
    public BugetKey(String text)
    {
        System.out.println(text);
        this.text = text;
        String[] values = text.split("[/:]");
        start =LocalDate.parse(values[0].trim(),Date.timeFromat);
        end = LocalDate.parse(values[1].trim(),Date.timeFromat);
        value=Double.parseDouble(values[2]);
        System.out.println(Arrays.toString(values));//TODO remove
    }
    public String getText()
    {
        return text;
    }
    public Double getValue()
    {
        return value;
    }
    public LocalDate getStart()
    {
        return  start;
    }
    public LocalDate getEnd()
    {
        return end;
    }

    @Override
    public int compareTo(BugetKey o) {
        return (int)(this.value - o.getValue());
    }
    public static  class startDateComparator implements Comparator<BugetKey>
    {

        @Override
        public int compare(BugetKey a, BugetKey b) {
            return a.getStart().compareTo(b.getStart());
        }

    }
    public static  class endDateComparator implements  Comparator<BugetKey>
    {
        @Override
        public int compare(BugetKey a, BugetKey b) {
            return a.getEnd().compareTo(b.getEnd());
        }
    }
    public static class spanDateComparator implements Comparator<BugetKey>
    {
        @Override
        public int compare(BugetKey a, BugetKey b) {
            return Math.round((a.getEnd().toEpochDay()-a.getStart().toEpochDay())-(b.getEnd().toEpochDay()-b.getStart().toEpochDay()));
        }
    }
}
