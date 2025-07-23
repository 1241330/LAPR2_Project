package pt.ipp.isep.dei.domain.FinancialResult;

import pt.ipp.isep.dei.domain.Resource.ResourcesType;
import java.io.Serializable;

/**
 * Represents the demand for a specific resource type, including its grade and booster.
 * The demand grade can evolve or downgrade, affecting the booster value.
 */
public class Demand implements Serializable {

    /** The initial demand grade. */
    private final int INITIAL_DEMAND_GRADE = 5;

    /** The amount the booster is modified per grade change. */
    private final double BOOST_MODIFICATION_PER_GRADE = 0.2;

    /** The initial booster value. */
    private final int INITIAL_BOOST = 1;

    /** The type of resource this demand refers to. */
    private ResourcesType resourcesType;

    /** The current demand grade (1-9). */
    private int demandGrade;

    /** The current booster value. */
    private double booster;

    /**
     * Constructs a new Demand for a given resource type, with initial values.
     *
     * @param resourcesType the type of resource
     */
    public Demand(ResourcesType resourcesType) {
        this.resourcesType = resourcesType;
        this.demandGrade = INITIAL_DEMAND_GRADE;
        this.booster = INITIAL_BOOST;
    }

    /**
     * Gets the resource type associated with this demand.
     *
     * @return the resource type
     */
    public ResourcesType getResourcesType() {
        return resourcesType;
    }

    /**
     * Sets the resource type for this demand.
     *
     * @param resourcesType the resource type to set
     */
    public void setResourcesType(ResourcesType resourcesType) {
        this.resourcesType = resourcesType;
    }

    /**
     * Gets the current demand grade.
     *
     * @return the demand grade
     */
    public int getDemandGrade() {
        return demandGrade;
    }

    /**
     * Sets the demand grade.
     *
     * @param demandGrade the demand grade to set
     */
    public void setDemandGrade(int demandGrade) {
        this.demandGrade = demandGrade;
    }

    /**
     * Gets the current booster value.
     *
     * @return the booster value
     */
    public double getBooster() {
        return booster;
    }

    /**
     * Increases the demand grade by 1 (up to 9) and increases the booster accordingly.
     */
    public void evolveDemandGrade() {
        if (demandGrade < 9) {
            this.demandGrade += 1;
            this.booster += BOOST_MODIFICATION_PER_GRADE;
        }
    }

    /**
     * Decreases the demand grade by 1 (down to 1) and decreases the booster accordingly.
     */
    public void downGradeDemandGrade() {
        if (demandGrade >= 1) {
            this.demandGrade -= 1;
            this.booster -= BOOST_MODIFICATION_PER_GRADE;
        }
    }
}