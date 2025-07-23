package pt.ipp.isep.dei.ui.console.menu;

import org.apache.commons.lang3.StringUtils;
import java.util.Objects;

/**
 * Represents a menu item with a description and an associated UI action.
 * Each MenuItem encapsulates a description and a Runnable UI action to be executed when selected.
 */
public class MenuItem {
    private String description;
    private Runnable ui;

    /**
     * Constructs a MenuItem with the specified description and UI action.
     *
     * @param description the description of the menu item
     * @param ui the Runnable UI action associated with this menu item
     * @throws IllegalArgumentException if the description is null/empty or the UI action is null
     */
    public MenuItem(String description, Runnable ui) {
        setDescription(description);
        setUi(ui);
    }

    /**
     * Executes the UI action associated with this menu item.
     */
    public void run() {
        this.ui.run();
    }

    /**
     * Checks if this menu item has the specified description.
     *
     * @param description the description to compare
     * @return true if the descriptions match, false otherwise
     */
    public boolean hasDescription(String description) {
        return this.description.equals(description);
    }

    /**
     * Returns the description of this menu item.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of this menu item.
     *
     * @param description the new description
     * @throws IllegalArgumentException if the description is null or empty
     */
    public void setDescription(String description) {
        if (StringUtils.isBlank(description)) {
            throw new IllegalArgumentException("MenuItem description cannot be null or empty.");
        }
        this.description = description;
    }

    /**
     * Returns the UI action associated with this menu item.
     *
     * @return the Runnable UI action
     */
    public Runnable getUi() {
        return ui;
    }

    /**
     * Sets the UI action for this menu item.
     *
     * @param ui the new Runnable UI action
     * @throws IllegalArgumentException if the UI action is null
     */
    public void setUi(Runnable ui) {
        if (Objects.isNull(ui)) {
            throw new IllegalArgumentException("MenuItem does not support a null UI.");
        }
        this.ui = ui;
    }

    /**
     * Returns the string representation of this menu item (its description).
     *
     * @return the description
     */
    @Override
    public String toString() {
        return this.description;
    }
}