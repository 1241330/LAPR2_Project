package pt.ipp.isep.dei.controller.map;

import org.junit.jupiter.api.*;
import pt.ipp.isep.dei.domain.Map.Map;
import pt.ipp.isep.dei.domain._Others_.Size;
import pt.ipp.isep.dei.repository.Repositories;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link SaveMapController} class.
 * Covers all possible scenarios for saving and discarding maps, using country names as map names.
 */
class SaveMapControllerTest {

    private static final String DIRECTORY = "data/Map/";
    private static final String EXTENSION = ".ser";
    private Map map;
    private SaveMapController controller;

    /**
     * Cleans up the map repository and deletes test files before each test.
     */
    @BeforeEach
    void setUp() {
        Repositories.getInstance().getMapRepository().getAllMaps().clear();
        deleteFile("USA");
        deleteFile("Canada");
        deleteFile("Brazil");
    }

    /**
     * Deletes a map file if it exists.
     */
    private void deleteFile(String name) {
        File file = new File(DIRECTORY + name + EXTENSION);
        if (file.exists()) {
            file.delete();
        }
    }

    /**
     * Tests that saving a map creates a file and updates lastNameUsed.
     */
    @Test
    void saveMap_ShouldCreateFileAndUpdateLastNameUsed() {
        map = new Map("USA", new Size(5, 5));
        controller = new SaveMapController(map);
        controller.saveMap();

        File file = new File(DIRECTORY + "USA" + EXTENSION);
        assertTrue(file.exists());
        assertEquals("USA", map.getLastNameUsed());
    }

    /**
     * Tests that saving a map after renaming updates the file name.
     */
    @Test
    void saveMap_RenameMap_ShouldRenameFile() {
        map = new Map("Canada", new Size(4, 4));
        controller = new SaveMapController(map);
        controller.saveMap();

        // Rename and save again
        map.setName("Brazil");
        controller.saveMap();

        File oldFile = new File(DIRECTORY + "Canada" + EXTENSION);
        File newFile = new File(DIRECTORY + "Brazil" + EXTENSION);
        assertFalse(oldFile.exists());
        assertTrue(newFile.exists());
        assertEquals("Brazil", map.getLastNameUsed());
    }

    /**
     * Tests that dontSaveMap restores the map to its last saved state if a backup exists.
     */
    @Test
    void dontSaveMap_ShouldRestoreBackupIfExists() {
        map = new Map("USA", new Size(3, 3));
        controller = new SaveMapController(map);
        controller.saveMap();

        // Change name and call dontSaveMap
        map.setName("Canada");
        controller.dontSaveMap();

        // The map should be restored to "USA"
        File file = new File(DIRECTORY + "USA" + EXTENSION);
        assertTrue(file.exists());
        assertEquals("USA", map.getName());
    }

    /**
     * Tests that dontSaveMap removes the map from the repository if no backup exists.
     */
    @Test
    void dontSaveMap_NoBackup_ShouldRemoveMapFromRepository() {
        map = new Map("Brazil", new Size(2, 2));
        Repositories.getInstance().getMapRepository().addMap(map);
        controller = new SaveMapController(map);

        // No file saved yet, so no backup
        controller.dontSaveMap();

        assertFalse(Repositories.getInstance().getMapRepository().existsMapWithID(map.getId()));
    }

    /**
     * Tests that saving and loading a map preserves its data.
     */
    @Test
    void saveMap_AndLoad_ShouldPreserveData() throws Exception {
        map = new Map("USA", new Size(7, 7));
        controller = new SaveMapController(map);
        controller.saveMap();

        // Load the map from file
        FileInputStream fileIn = new FileInputStream(DIRECTORY + "USA" + EXTENSION);
        ObjectInputStream in = new ObjectInputStream(fileIn);
        Map loadedMap = (Map) in.readObject();
        in.close();
        fileIn.close();

        assertEquals("USA", loadedMap.getName());
        assertEquals(7, loadedMap.getPixelSize().getWidth());
        assertEquals(7, loadedMap.getPixelSize().getHeight());
    }
}