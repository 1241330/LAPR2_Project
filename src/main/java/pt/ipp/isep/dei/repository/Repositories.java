package pt.ipp.isep.dei.repository;

/**
 * Singleton class that manages all repositories in the application.
 * <p>
 * Inspired by: https://refactoring.guru/design-patterns/singleton/java/example
 * </p>
 * <p>
 * Provides a single access point to all repository instances, ensuring only one instance of each exists.
 * </p>
 */
public class Repositories {

    /**
     * The single instance of the Repositories class.
     */
    private static Repositories instance;

    /**
     * Repository for authentication entities.
     */
    private final AuthenticationRepository authenticationRepository;

    /**
     * Repository for locomotive entities.
     */
    private final LocomotiveRepository locomotiveRepository;

    /**
     * Repository for carriage entities.
     */
    private final CarriageRepository carriageRepository;

    /**
     * Repository for map entities.
     */
    private final MapRepository mapRepository;

    /**
     * Repository for primary resource entities.
     */
    private final PrimaryResourceRepository primaryResourceRepository;

    /**
     * Repository for transforming resource entities.
     */
    private final TransformingResourceRepository transformingResourceRepository;

    /**
     * Repository for house block resource entities.
     */
    private final HouseBlockResourceRepository houseBlockResourceRepository;

    /**
     * Private constructor to prevent direct instantiation.
     * Initializes all repositories.
     */
    private Repositories() {
        authenticationRepository = new AuthenticationRepository();
        mapRepository = new MapRepository();
        carriageRepository = new CarriageRepository();
        locomotiveRepository = new LocomotiveRepository();
        primaryResourceRepository = new PrimaryResourceRepository();
        transformingResourceRepository = new TransformingResourceRepository();
        houseBlockResourceRepository = new HouseBlockResourceRepository();
    }

    /**
     * Returns the singleton instance of the Repositories class.
     * If the instance does not exist, it is created in a thread-safe manner.
     *
     * @return The singleton instance of Repositories.
     */
    public static Repositories getInstance() {
        if (instance == null) {
            synchronized (Repositories.class) {
                instance = new Repositories();
            }
        }
        return instance;
    }

    /**
     * Gets the authentication repository.
     *
     * @return The AuthenticationRepository instance.
     */
    public AuthenticationRepository getAuthenticationRepository() {
        return authenticationRepository;
    }

    /**
     * Gets the map repository.
     *
     * @return The MapRepository instance.
     */
    public MapRepository getMapRepository() {
        return mapRepository;
    }

    /**
     * Gets the locomotive repository.
     *
     * @return The LocomotiveRepository instance.
     */
    public LocomotiveRepository getLocomotiveRepository() {
        return locomotiveRepository;
    }

    /**
     * Gets the carriage repository.
     *
     * @return The CarriageRepository instance.
     */
    public CarriageRepository getCarriageRepository() {
        return carriageRepository;
    }

    /**
     * Gets the primary resource repository.
     *
     * @return The PrimaryResourceRepository instance.
     */
    public PrimaryResourceRepository getPrimaryResourceRepository() {
        return primaryResourceRepository;
    }

    /**
     * Gets the transforming resource repository.
     *
     * @return The TransformingResourceRepository instance.
     */
    public TransformingResourceRepository getTransformingTypeRepository() {
        return transformingResourceRepository;
    }

    /**
     * Gets the house block resource repository.
     *
     * @return The HouseBlockResourceRepository instance.
     */
    public HouseBlockResourceRepository getHouseBlockResourceRepository() {
        return houseBlockResourceRepository;
    }
}