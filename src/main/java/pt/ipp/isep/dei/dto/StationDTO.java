package pt.ipp.isep.dei.dto;

import java.util.List;

/**
 * Data Transfer Object for Station details.
 */
public class StationDTO {

    /**
     * The name of the station.
     */
    private String name;

    /**
     * The type of the station.
     */
    private String type;

    /**
     * The position of the station.
     */
    private String position;

    /**
     * The direction of the station.
     */
    private String direction;

    /**
     * The list of buildings at the station.
     */
    private List<String> buildings;

    /**
     * The list of cargoes at the station.
     */
    private List<String> cargoes;

    /**
     * Constructs a StationDTO with the specified details.
     *
     * @param name      the name of the station
     * @param type      the type of the station
     * @param position  the position of the station
     * @param direction the direction of the station
     * @param buildings the list of buildings at the station
     * @param cargoes   the list of cargoes at the station
     */
    public StationDTO(String name, String type, String position, String direction,
                      List<String> buildings, List<String> cargoes) {
        this.name = name;
        this.type = type;
        this.position = position;
        this.direction = direction;
        this.buildings = buildings;
        this.cargoes = cargoes;
    }

    /**
     * Gets the name of the station.
     *
     * @return the station name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the station.
     *
     * @param name the station name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the type of the station.
     *
     * @return the station type
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the type of the station.
     *
     * @param type the station type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Gets the position of the station.
     *
     * @return the station position
     */
    public String getPosition() {
        return position;
    }

    /**
     * Sets the position of the station.
     *
     * @param position the station position
     */
    public void setPosition(String position) {
        this.position = position;
    }

    /**
     * Gets the direction of the station.
     *
     * @return the station direction
     */
    public String getDirection() {
        return direction;
    }

    /**
     * Sets the direction of the station.
     *
     * @param direction the station direction
     */
    public void setDirection(String direction) {
        this.direction = direction;
    }

    /**
     * Gets the list of buildings at the station.
     *
     * @return the list of buildings
     */
    public List<String> getBuildings() {
        return buildings;
    }

    /**
     * Sets the list of buildings at the station.
     *
     * @param buildings the list of buildings
     */
    public void setBuildings(List<String> buildings) {
        this.buildings = buildings;
    }

    /**
     * Gets the list of cargoes at the station.
     *
     * @return the list of cargoes
     */
    public List<String> getCargoes() {
        return cargoes;
    }

    /**
     * Sets the list of cargoes at the station.
     *
     * @param cargoes the list of cargoes
     */
    public void setCargoes(List<String> cargoes) {
        this.cargoes = cargoes;
    }
}