package pt.ipp.isep.dei.domain.Simulation;

import java.io.Serializable;

/**
 * Represents a date with year, month, and day.
 */
public class TimeDate implements Serializable {

    private int year;
    private int month;
    private int day;

    /**
     * Array representing the number of days in each month.
     * Index 0 is unused. February (index 2) is set dynamically based on leap years.
     */
    private static int[] DAYS_PER_MONTH = {0, 31, -1, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    /**
     * Constructs a TimeDate object with the specified year, month, and day.
     *
     * @param year  the year (must be greater than 0)
     * @param month the month (must be between 1 and 12)
     * @param day   the day (must be valid for the given month and year)
     * @throws IllegalArgumentException if the year, month, or day is invalid
     */
    public TimeDate(int year, int month, int day) {
        if (year <= 0) {
            throw new IllegalArgumentException("Year must be greater than 0.");
        }
        this.year = year;

        if (isLeapYear(year)) {
            DAYS_PER_MONTH[2] = 29; // Leap year
        } else {
            DAYS_PER_MONTH[2] = 28; // Non-leap year
        }

        if (month < 1 || month > 12) {
            throw new IllegalArgumentException("Month must be between 1 and 12.");
        }
        this.month = month;

        if (day < 1 || day > DAYS_PER_MONTH[month]) {
            throw new IllegalArgumentException("Day must be between 1 and " + DAYS_PER_MONTH[month] + " for month " + month + ".");
        }
        this.day = day;
    }

    /**
     * Checks if a given year is a leap year.
     *
     * @param year the year to check
     * @return true if the year is a leap year, false otherwise
     */
    private boolean isLeapYear(int year) {
        return (year % 400 == 0) || ((year % 4 == 0) && (year % 100 != 0));
    }

    /**
     * Gets the year.
     *
     * @return the year
     */
    public int getYear() {
        return year;
    }

    /**
     * Sets the year.
     *
     * @param year the year to set
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * Gets the month.
     *
     * @return the month
     */
    public int getMonth() {
        return month;
    }

    /**
     * Sets the month.
     *
     * @param month the month to set
     */
    public void setMonth(int month) {
        this.month = month;
    }

    /**
     * Gets the day.
     *
     * @return the day
     */
    public int getDay() {
        return day;
    }

    /**
     * Sets the day.
     *
     * @param day the day to set
     */
    public void setDay(int day) {
        this.day = day;
    }

    /**
     * Gets the total number of months since year 0.
     *
     * @return the total number of months
     */
    public int getTotalMonths() {
        return this.month + (this.year * 12);
    }

    /**
     * Gets the difference in days between this date and another date.
     *
     * @param timeDate the other TimeDate
     * @return the difference in days
     */
    public int getDiference(TimeDate timeDate) {
        return this.getTotalDays() - timeDate.getTotalDays();
    }

    /**
     * Checks if this date is before another date.
     *
     * @param timeDate the other TimeDate
     * @return true if this date is before the other date, false otherwise
     */
    public boolean before(TimeDate timeDate) {
        return this.getTotalDays() < timeDate.getTotalDays();
    }

    /**
     * Returns a string representation of the date in the format "Actual date: day-month-year".
     *
     * @return the string representation
     */
    @Override
    public String toString() {
        return "Actual date: " + this.day + "-" + this.month + "-" + this.year;
    }

    /**
     * Returns a simple string representation of the date in the format "day - month - year".
     *
     * @return the simple string representation
     */
    public String toSimpleString() {
        return this.day + "/" + this.month + "/" + this.year;
    }

    /**
     * Gets the total number of days since year 0.
     *
     * @return the total number of days
     */
    public int getTotalDays() {
        int totalDays = 0;
        for (int i = 1; i < year; i++) {
            if (isLeapYear(i)) {
                totalDays += 366; // Leap year
            } else {
                totalDays += 365; // Non-leap year
            }
        }
        for (int i = 1; i < month; i++) {
            totalDays += DAYS_PER_MONTH[i];
        }
        totalDays += day;
        return totalDays;
    }
}