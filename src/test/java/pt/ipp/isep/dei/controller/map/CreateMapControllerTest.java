package pt.ipp.isep.dei.controller.map;

 import org.junit.jupiter.api.BeforeEach;
 import org.junit.jupiter.api.Test;
 import pt.ipp.isep.dei.domain.Map.Map;
 import pt.ipp.isep.dei.repository.Repositories;

 import static org.junit.jupiter.api.Assertions.*;

 /**
  * Unit tests for the {@link CreateMapController} class.
  * Covers all possible scenarios for creating maps, using country names as map names.
  */
 class CreateMapControllerTest {

     private CreateMapController controller;

     /**
      * Clears the map repository and initializes the controller before each test.
      */
     @BeforeEach
     void setUp() {
         Repositories.getInstance().getMapRepository().getAllMaps().clear();
         controller = new CreateMapController();
     }

     /**
      * Tests that a map is successfully created with valid parameters.
      */
     @Test
     void createMap_ValidParameters_ShouldCreateMap() {
         controller.setNameMap("Canada");
         controller.setWidthMap(10);
         controller.setHeightMap(8);
         controller.createMap();
         Map map = controller.getMapCreated();
         assertNotNull(map);
         assertEquals("Canada", map.getName());
         assertEquals(10, map.getPixelSize().getWidth());
         assertEquals(8, map.getPixelSize().getHeight());
     }

     /**
      * Tests that an exception is thrown when trying to create a map with a null name.
      */
     @Test
     void createMap_NullName_ShouldThrowException() {
         controller.setNameMap(null);
         controller.setWidthMap(5);
         controller.setHeightMap(5);
         assertThrows(IllegalArgumentException.class, controller::createMap);
     }

     /**
      * Tests that an exception is thrown when trying to create a map with an empty name.
      */
     @Test
     void createMap_EmptyName_ShouldThrowException() {
         controller.setNameMap("   ");
         controller.setWidthMap(5);
         controller.setHeightMap(5);
         assertThrows(IllegalArgumentException.class, controller::createMap);
     }

     /**
      * Tests that an exception is thrown when invalid (negative or zero) dimensions are provided.
      */
     @Test
     void createMap_InvalidDimensions_ShouldThrowException() {
         controller.setNameMap("Australia");
         controller.setWidthMap(-1);
         controller.setHeightMap(5);
         assertThrows(IllegalArgumentException.class, controller::createMap);

         controller.setWidthMap(5);
         controller.setHeightMap(0);
         assertThrows(IllegalArgumentException.class, controller::createMap);
     }

     /**
      * Tests the creation of a default-named map with valid dimensions.
      */
     @Test
     void createMapDefault_ValidDimensions_ShouldCreateMap() {
         controller.setWidthMap(12);
         controller.setHeightMap(9);
         controller.createMapDefault();
         Map map = controller.getMapCreated();
         assertNotNull(map);
         assertTrue(map.getName().startsWith("Map_"));
         assertEquals(12, map.getPixelSize().getWidth());
         assertEquals(9, map.getPixelSize().getHeight());
     }

     /**
      * Tests that an exception is thrown when default map creation is attempted with invalid dimensions.
      */
     @Test
     void createMapDefault_InvalidDimensions_ShouldThrowException() {
         controller.setWidthMap(0);
         controller.setHeightMap(5);
         assertThrows(IllegalArgumentException.class, controller::createMapDefault);

         controller.setWidthMap(5);
         controller.setHeightMap(-2);
         assertThrows(IllegalArgumentException.class, controller::createMapDefault);
     }

     /**
      * Tests that map names are unique when creating multiple default maps.
      */
     @Test
     void createMapDefault_MultipleMaps_ShouldHaveUniqueNames() {
         controller.setWidthMap(7);
         controller.setHeightMap(7);
         controller.createMapDefault();
         String firstName = controller.getMapCreated().getName();

         CreateMapController controller2 = new CreateMapController();
         controller2.setWidthMap(7);
         controller2.setHeightMap(7);
         controller2.createMapDefault();
         String secondName = controller2.getMapCreated().getName();

         assertNotEquals(firstName, secondName);
     }

     /**
      * Tests that the map repository is updated after creating a map.
      */
     @Test
     void createMap_RepositoryShouldContainMap() {
         controller.setNameMap("Japan");
         controller.setWidthMap(6);
         controller.setHeightMap(6);
         controller.createMap();
         assertTrue(controller.getMapRepository().getAllMaps().stream()
                 .anyMatch(m -> m.getName().equals("Japan")));
     }
 }