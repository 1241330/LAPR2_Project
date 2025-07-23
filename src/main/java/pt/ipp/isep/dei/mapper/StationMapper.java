package pt.ipp.isep.dei.mapper;

import pt.ipp.isep.dei.domain.Station.Building;
import pt.ipp.isep.dei.domain.Station.Station;
import pt.ipp.isep.dei.domain.Station.StationAssociations;
import pt.ipp.isep.dei.domain._Others_.Inventory;
import pt.ipp.isep.dei.domain._Others_.Position;
import pt.ipp.isep.dei.domain.Resource.Resource;
import pt.ipp.isep.dei.dto.StationDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for mapping Station domain objects to StationDTO objects.
 * Provides static methods for conversion.
 */
public class StationMapper {

    /**
     * Converts a Station domain object to a StationDTO.
     *
     * @param station the Station domain object to convert
     * @return the corresponding StationDTO
     */
    public static StationDTO toDTO(Station station) {
        String name = station.getName();
        String type = station.getStationType();

        Position pos = station.getPosition();
        String position = "(x = " + (pos.getX() + 1) + ", y = " + (pos.getY() + 1) + ")";

        String direction = station.getDirection();

        List<String> buildingNames = new ArrayList<>();
        for (Building b : station.getBuildings()) {
            buildingNames.add(b.getName());
        }

        Inventory inventory = new Inventory();
        for (StationAssociations assoc : station.getAllAssociations()) {
            inventory.addAll(assoc.getInventory().getAllResources());
        }

        List<String> cargoStrings = new ArrayList<>();
        for (Resource r : inventory.getAllResources()) {
            cargoStrings.add(r.toString());
        }

        return new StationDTO(name, type, position, direction, buildingNames, cargoStrings);
    }

    /**
     * Private constructor to prevent instantiation of utility class.
     */
    private StationMapper() {
        // Utility class, no instances allowed.
    }
}