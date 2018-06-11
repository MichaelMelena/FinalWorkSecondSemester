package logic;


import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import utill.Date;
import utill.DateSpan;
import utill.UserDateInput;


/**
 * Takes two dates and value and divides it by the number of days in times-pan or the per month.
 * */
public class Buget {

        private static final long serialVersionUID = 6382850961775172164L;

        /**
         * @param month Takes integer for a Month in the range (1-12)
         * @param year Takes integer fro a Year in the range  (0-3000)
         * @return Returns Number of days in month for inputed parameters
         * */
        private static int numberOfDaysInMonth(int month,int year)
        {
            int temp = Date.normalYear[month-1];
            if(month==2&&Date.isLeapYear(year))
            {
                temp+=1;
            }
            return temp;
        }
        private UserDateInput userDateInput;
        private double valuePerDay;

        private int numberOfDays;
        private double valuePerDayError;
        private double valuePerMonthError;


        private List<DateSpan> fragments;


        public Buget(UserDateInput userDateInput)
        {
            this.userDateInput =userDateInput;

            fragments=calculateFragments();
            numberOfDays=calculateNumberOfDays();
            calculateValuePerDay();
            calculateValuePerDayError();
            calculateValuePerMonth();
            calculateValuePerMonthError();
        }
        public Buget()
        {

        }

        /**
         * @return Returns List of DateSpans containing informations about each month from time segment
         * */

        private ArrayList<DateSpan> calculateFragments()
        {
            int mesic= userDateInput.getStart().getMonth();
            int rok =userDateInput.getStart().getYear();
            ArrayList<DateSpan> monthly  = new ArrayList<>();
            while(mesic <= userDateInput.getEnd().getMonth()&&rok<=userDateInput.getEnd().getYear()|| rok < userDateInput.getEnd().getYear())
            {
                if(mesic == userDateInput.getStart().getMonth()&& rok == userDateInput.getStart().getYear())//first month
                {
                    monthly.add(new DateSpan(new Date(numberOfDaysInMonth(mesic, rok)-userDateInput.getStart().getDay()+1,mesic, rok)));
                }
                else if(mesic== userDateInput.getEnd().getMonth()&& rok==userDateInput.getEnd().getYear())//lastMonth
                {
                    monthly.add(new DateSpan(new Date(userDateInput.getEnd().getDay(), mesic, rok)));
                }
                else
                {
                    monthly.add(new DateSpan(new Date(numberOfDaysInMonth(mesic, rok), mesic, rok)));
                }
                mesic++;
                if(mesic>12)
                {
                    mesic = 1;
                    rok++;
                }
            }
            return monthly;
        }

        /**
         *
         * @return Returns number of days in time segment
         * */
        private int calculateNumberOfDays()
        {
            int sum =0;
            for(DateSpan fragment: fragments)
            {
                sum+=fragment.getAmountOfDays();
            }
            return sum;
        }

        /**
         * sets the valuePerDay double to value per day in time segment
         * */
        private void calculateValuePerDay()
        {
            valuePerDay= userDateInput.getValue() /numberOfDays;
        }

        /**
         * calculates error for valuePerDay function
         * */
        private void calculateValuePerDayError()
        {
            valuePerDayError = Math.abs((numberOfDays*valuePerDay) - userDateInput.getValue());
        }

        /**
         * sets the fragment.value double to value per this month in time segment
         * */
        private void calculateValuePerMonth()
        {
            for(DateSpan fragment: fragments)
            {
                fragment.setValue(valuePerDay*fragment.getAmountOfDays());
            }
        }

        /**
         * calculates error for valuePerMonth function
         * */
        private void calculateValuePerMonthError()
        {
            double err=0;
            for(DateSpan fragment: fragments)
            {
                err += valuePerDay*fragment.getAmountOfDays();
            }
            valuePerMonthError=Math.abs(userDateInput.getValue()-err);
        }

        /**
         * @return Formatted text in plain format for calculateValuePerDay method
         * */
        public String getValuePerDay()
        {
            return String.format("--------PER DAY---------%nMoney: %.2f%nNumber of days: %s%nOd data: %s%nDo data: %s%nValue per day: %.2f%nChyba: %.2f%n--------END--------%n%n",userDateInput.getValue(),numberOfDays,userDateInput.getStart(),userDateInput.getEnd(),(double)(Math.round(valuePerDay*100))/100,valuePerDayError );
        }

        /**
         * @return Formated text in plain format for calculateValuePerMonth method.
         * */
        public String getValuePerMonth()
        {
            StringBuilder sb = new StringBuilder(
                    String.format("--------PER MONTH---------%nMoney: %.2f%nOd data: %s%nDo data: %s%nPocet dni: %s%nVMalue per day: %.2f%nChyba: %.2f%n******Vycet*******%n",userDateInput.getValue(),userDateInput.getStart(),userDateInput.getEnd(),numberOfDays,(double)(Math.round(valuePerDay*100))/100,valuePerMonthError));


            for(DateSpan fragment : fragments)
            {
                sb.append(String.format("Hodnota pro mesic(%s-%s): %.2f%n", fragment.getMonth(),fragment.getYear(),(double)(Math.round(fragment.getValue()*100))/100));
            }
            sb.append(String.format("------END------%n%n"));

            return sb.toString();
        }

        /**
         * @return Formated text in CSV format for calculateValuePerDay method.
         **/
        public String printCsvValuePerDay()
        {
            return String.format(Locale.US,"Castka,Pocet dni,Od data,Do data,Castka na den, chyba%n%.2f,%d,%s,%s,%.2f,%.2f", userDateInput.getValue(),numberOfDays,userDateInput.getStart(),userDateInput.getEnd(),(double)(Math.round(valuePerDay*100))/100,valuePerMonthError);
        }

        /**
         * @return Formated text in CSV format for calculateValuePerMonth method.
         * */
        public String printCsvValuePerMonth()
        {
            StringBuilder sb  = new StringBuilder(String.format(Locale.US,"Castka,%.2f%nPocet dni,%s%nOd data,%s%nDo data,%s%nCastka na den,%.2f%nChyba,%.2f%n", userDateInput.getValue(),numberOfDays,userDateInput.getStart(),userDateInput.getEnd(),(double)(Math.round(valuePerDay*100))/100,valuePerMonthError));
            for(DateSpan fragment : fragments)
            {
                sb.append(String.format(Locale.US,"Hodnota pro mesic(%s-%s):, %.2f%n", fragment.getMonth(),fragment.getYear(),(double)(Math.round(fragment.getValue()*100))/100));
            }
            return sb.toString();
        }

        /**
         * @return Value divided by the sum of day from each timespan
         * */
        public String printTextValuePerDay()
        {
            return getValuePerDay();
        }

        /**
         * @return Value distributed between all timespan based on number of day in each timespan.
         * */
        public String printTextValuePerMonth()
        {
            return getValuePerMonth();
        }

        public UserDateInput getUserDateInput()
        {
            return userDateInput;
        }
    }


