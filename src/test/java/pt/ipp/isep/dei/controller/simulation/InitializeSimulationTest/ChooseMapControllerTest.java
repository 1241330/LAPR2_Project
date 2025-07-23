package pt.ipp.isep.dei.controller.simulation.InitializeSimulationTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.controller.simulation.InitializeSimulation.ChooseMapController;
import pt.ipp.isep.dei.domain.Map.Map;
import pt.ipp.isep.dei.domain._Others_.Size;
import pt.ipp.isep.dei.repository.MapRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ChooseMapControllerTest {

    /**
     * Cleans the MapRepository before each test to ensure test isolation.
     */
    @BeforeEach
    void setUp() {
        MapRepository mapRepository = new MapRepository();
        mapRepository.getAllMaps().clear();
    }

    /**
     * Tests the initialization of ChooseMapController when the map list is empty.
     * Verifies that the list is empty and the names list has size 0.
     */
    @Test
    void testInicializacaoComListaVazia() {
        ChooseMapController controller = new ChooseMapController();
        assertTrue(controller.listOfMapsIsEmpty());
        assertEquals(0, controller.getListOfNamesMaps().size());
    }

    /**
     * Tests the initialization of ChooseMapController when the map list is not empty.
     * Verifies that the names list contains the correct map names and the list is not empty.
     */
    @Test
    void testInicializacaoComListaNaoVazia() {
        MapRepository mapRepository = new MapRepository();
        mapRepository.getAllMaps().clear();
        Map map1 = new Map("Mapa1", new Size(10, 10));
        Map map2 = new Map("Mapa2", new Size(20, 20));
        mapRepository.addMap(map1);
        mapRepository.addMap(map2);
        ChooseMapController controller = new ChooseMapController();
        List<String> nomes = controller.getListOfNamesMaps();
        assertEquals(2, nomes.size());
        assertTrue(nomes.contains("Mapa1"));
        assertTrue(nomes.contains("Mapa2"));
        assertFalse(controller.listOfMapsIsEmpty());
    }

    /**
     * Tests if setListOfNamesMaps updates the list of map names correctly.
     */
    @Test
    void testSetListOfNamesMapsAtualizaLista() {
        MapRepository mapRepository = new MapRepository();
        mapRepository.getAllMaps().clear();
        Map map1 = new Map("MapaNovo", new Size(5, 5));
        mapRepository.addMap(map1);
        ChooseMapController controller = new ChooseMapController();
        controller.setListOfNamesMaps();
        assertEquals(List.of("MapaNovo"), controller.getListOfNamesMaps());
    }

    /**
     * Tests selecting a map by index and retrieving the selected map.
     */
    @Test
    void testSetSelectedMapEGetSelectedMap() {
        MapRepository mapRepository = new MapRepository();
        mapRepository.getAllMaps().clear();
        Map map1 = new Map("Mapa1", new Size(10, 10));
        Map map2 = new Map("Mapa2", new Size(20, 20));
        mapRepository.addMap(map1);
        mapRepository.addMap(map2);
        ChooseMapController controller = new ChooseMapController();
        controller.setSelectedMap(1);
        assertEquals(map2, controller.getSelectedMap());
    }

    /**
     * Tests that selecting a map with an invalid index throws IndexOutOfBoundsException.
     */
    @Test
    void testSetSelectedMapComIndiceInvalido() {
        MapRepository mapRepository = new MapRepository();
        mapRepository.getAllMaps().clear();
        Map map1 = new Map("Mapa1", new Size(10, 10));
        mapRepository.addMap(map1);
        ChooseMapController controller = new ChooseMapController();
        assertThrows(IndexOutOfBoundsException.class, () -> controller.setSelectedMap(5));
    }

    /**
     * Tests adding and removing maps from the repository.
     */
    @Test
    void testAdicionarRemoverMapas() {
        MapRepository mapRepository = new MapRepository();
        mapRepository.getAllMaps().clear();
        Map map1 = new Map("Mapa1", new Size(10, 10));
        mapRepository.addMap(map1);
        assertEquals(1, mapRepository.getMapCount());
        mapRepository.removeMap(map1.getId());
        assertEquals(0, mapRepository.getMapCount());
    }

    /**
     * Tests that creating a map with an empty or null name throws IllegalArgumentException.
     */
    @Test
    void testMapComNomeVazioOuNull() {
        assertThrows(IllegalArgumentException.class, () -> new Map("", new Size(10, 10)));
        assertThrows(IllegalArgumentException.class, () -> new Map(null, new Size(10, 10)));
    }

    /**
     * Tests that creating a map with a null size throws IllegalArgumentException.
     */
    @Test
    void testMapComSizeNull() {
        assertThrows(IllegalArgumentException.class, () -> new Map("Mapa", null));
    }
}
