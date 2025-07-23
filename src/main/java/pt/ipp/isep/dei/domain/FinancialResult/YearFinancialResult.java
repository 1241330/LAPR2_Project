package pt.ipp.isep.dei.domain.FinancialResult;

import java.io.Serializable;

/**
 * Represents the financial results for a specific year, including earnings and expenses.
 */
public class YearFinancialResult implements Serializable {

    /** The year for which the financial result is recorded. */
    private int year;

    /** The total earnings for the year. */
    private int earning;

    /** The total track maintenance cost for the year. */
    private int trackMaintenance;

    /** The total train maintenance cost for the year. */
    private int trainMaintenance;

    /** The total fuel cost for the year. */
    private int fuelCost;

    /**
     * Constructs a YearFinancialResult for a specific year.
     *
     * @param year the year of the financial result
     */
    public YearFinancialResult(int year) {
        this.year = year;
    }

    /**
     * Gets the year of the financial result.
     *
     * @return the year
     */
    public int getYear() {
        return year;
    }

    /**
     * Sets the year of the financial result.
     *
     * @param year the year to set
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * Gets the total earnings for the year.
     *
     * @return the earnings
     */
    public int getEarning() {
        return earning;
    }

    /**
     * Sets the total earnings for the year.
     *
     * @param earning the earnings to set
     */
    public void setEarning(int earning) {
        this.earning = earning;
    }

    /**
     * Gets the total track maintenance cost for the year.
     *
     * @return the track maintenance cost
     */
    public int getTrackMaintenance() {
        return trackMaintenance;
    }

    /**
     * Sets the total track maintenance cost for the year.
     *
     * @param trackMaintenance the track maintenance cost to set
     */
    public void setTrackMaintenance(int trackMaintenance) {
        this.trackMaintenance = trackMaintenance;
    }

    /**
     * Gets the total train maintenance cost for the year.
     *
     * @return the train maintenance cost
     */
    public int getTrainMaintenance() {
        return trainMaintenance;
    }

    /**
     * Sets the total train maintenance cost for the year.
     *
     * @param trainMaintenance the train maintenance cost to set
     */
    public void setTrainMaintenance(int trainMaintenance) {
        this.trainMaintenance = trainMaintenance;
    }

    /**
     * Gets the total fuel cost for the year.
     *
     * @return the fuel cost
     */
    public int getFuelCost() {
        return fuelCost;
    }

    /**
     * Sets the total fuel cost for the year.
     *
     * @param fuelCost the fuel cost to set
     */
    public void setFuelCost(int fuelCost) {
        this.fuelCost = fuelCost;
    }

    /**
     * Calculates the revenue for the year.
     *
     * @return the revenue (earnings + total expenses)
     */
    public int getRevenue() {
        return earning + getTotalExpenses();
    }

    /**
     * Calculates the total expenses for the year.
     *
     * @return the total expenses (track maintenance + train maintenance + fuel cost)
     */
    public int getTotalExpenses() {
        return trackMaintenance + trainMaintenance + fuelCost;
    }
}