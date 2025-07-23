package pt.ipp.isep.dei.mapper;

import pt.ipp.isep.dei.domain.Train.Locomotive;
import pt.ipp.isep.dei.dto.LocomotiveDTO;

/**
 * Mapper class to convert between Locomotive (domain) and LocomotiveDTO.
 * Provides static methods for mapping domain objects to DTOs and vice versa.
 */
public class LocomotiveMapper {

    /**
     * Converts a Locomotive domain object to a LocomotiveDTO.
     *
     * @param locomotive the Locomotive domain object to convert
     * @return the corresponding LocomotiveDTO
     */
    public static LocomotiveDTO toDTO(Locomotive locomotive) {
        return new LocomotiveDTO(
                locomotive.getName(),
                locomotive.getImagePath(),
                locomotive.getPower(),
                locomotive.getAcceleration(),
                locomotive.getTopSpeed(),
                locomotive.getStartYearOperation(),
                locomotive.getAcquisitionPrice(),
                locomotive.getFuelType(),
                locomotive.getRailroadLineAssosiated(),
                locomotive.getMaxCarriages(),
                locomotive.getInUse(),
                locomotive.getMaintenanceCost()
        );
    }

    /**
     * Converts a LocomotiveDTO to a Locomotive domain object.
     *
     * @param dto the LocomotiveDTO to convert
     * @return the corresponding Locomotive domain object
     */
    public static Locomotive toDomain(LocomotiveDTO dto) {
        Locomotive locomotive = new Locomotive(
                dto.getName(),
                dto.getImagePath(),
                dto.getPower(),
                dto.getAcceleration(),
                dto.getTopSpeed(),
                dto.getStartYearOperation(),
                dto.getAcquisitionPrice(),
                dto.getFuelType(),
                dto.getMaxCarriages(),
                dto.getMaintenanceCost()
        );
        locomotive.setRailroadLineAssosiated(dto.getRailroadLineAssosiated());
        locomotive.setInUse(dto.isInUse());
        return locomotive;
    }
}