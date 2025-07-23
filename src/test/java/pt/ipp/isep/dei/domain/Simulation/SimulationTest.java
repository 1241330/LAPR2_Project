package pt.ipp.isep.dei.domain.Simulation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.domain.City.HouseBlock;
import pt.ipp.isep.dei.domain.FinancialResult.UnloadCargoLogs;
import pt.ipp.isep.dei.domain.FinancialResult.YearFinancialResult;
import pt.ipp.isep.dei.domain.Industry.Industry;
import pt.ipp.isep.dei.domain.Industry.IndustryType;
import pt.ipp.isep.dei.domain.Map.Map;
import pt.ipp.isep.dei.domain.RailwayLine.RailwayLine;
import pt.ipp.isep.dei.domain.RailwayLine.RailwayLineType;
import pt.ipp.isep.dei.domain.Scenario.Scenario;
import pt.ipp.isep.dei.domain.Station.Station;
import pt.ipp.isep.dei.domain.Station.StationType;
import pt.ipp.isep.dei.domain.Train.Carriage;
import pt.ipp.isep.dei.domain.Train.Locomotive;
import pt.ipp.isep.dei.domain.Train.Train;
import pt.ipp.isep.dei.domain._Others_.Position;
import pt.ipp.isep.dei.domain._Others_.Size;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SimulationTest {

    private Simulation simulation;
    private Scenario scenario;

    @BeforeEach
    void setUp() {
        Map map = new Map("TestMap", new Size(10, 10));
        TimeDate startDate = new TimeDate(2024, 1, 1);
        TimeDate endDate = new TimeDate(2025, 1, 1);
        scenario = new Scenario(map, "Scenario Test", 1000, startDate, endDate);
        simulation = new Simulation("Simulation Test", scenario);
    }

    @Test
    void testSetAndGetName() {
        simulation.setName("New Name");
        assertEquals("New Name", simulation.getName());
    }

    @Test
    void testSetAndGetCurrentTime() {
        simulation.setCurrentTime(10);
        assertEquals(10, simulation.getCurrentTime());
    }

    @Test
    void testSetAndGetMaxTime() {
        simulation.setMaxTime(50);
        assertEquals(50, simulation.getMaxTime());
    }

    @Test
    void testSetAndGetConverterMonthSecond() {
        simulation.setConverterMonthSecond(3);
        assertEquals(3, simulation.getConverterMonthSecond());
    }

    @Test
    void testSetAndGetActualMoney() {
        simulation.setActualMoney(200);
        assertEquals(200, simulation.getActualMoney());
    }

    @Test
    void testAddMoney() {
        simulation.setActualMoney(100);
        simulation.addMoney(50);
        assertEquals(150, simulation.getActualMoney());
    }

    @Test
    void testSetAndGetHouseBlocks() {
        List<HouseBlock> blocks = new ArrayList<>();
        blocks.add(new HouseBlock(new Position(1,1), "CityName"));
        simulation.setHouseBlocks(blocks);
        assertEquals(blocks, simulation.getHouseBlocks());
    }

    @Test
    void testSetAndGetIndustries() {
        List<Industry> industries = new ArrayList<>();
        industries.add(new Industry("IndName", IndustryType.PRIMARY_SECTOR, new Position(2,2)));
        simulation.setIndustries(industries);
        assertEquals(industries, simulation.getIndustries());
    }

    @Test
    void testSetAndGetScenario() {
        Map map = new Map("OtherMap", new Size(10, 10));
        TimeDate startDate = new TimeDate(2024, 6, 1);
        TimeDate endDate = new TimeDate(2025, 6, 1);
        Scenario newScenario = new Scenario(map, "Other", 2000, startDate, endDate);
        simulation.setScenario(newScenario);
        assertEquals(newScenario, simulation.getScenario());
    }

    @Test
    void testGetScenarioName() {
        assertEquals("Scenario Test", simulation.getScenarioName());
    }

    @Test
    void testAddAndRemoveStation() {
        Station station = new Station(StationType.STATION, new Position(1,1), 1, "N", scenario);
        boolean added = simulation.addStation(station);
        assertTrue(added);
        assertTrue(simulation.getStations().contains(station));
        simulation.removeStation(station);
        assertFalse(simulation.getStations().contains(station));
    }

    @Test
    void testAddAndRemoveRailwayLine() {
        Station station1 = new Station(StationType.STATION, new Position(1,1), 1, "N", scenario);
        Station station2 = new Station(StationType.STATION, new Position(2,2), 1, "S", scenario);
        RailwayLine line = new RailwayLine(station1, station2, RailwayLineType.SINGLE_ELECTRIFIED);
        simulation.addRailwayLine(line);
        assertTrue(simulation.getRailwayLines().contains(line));
        simulation.removeRailwayLine(line);
        assertFalse(simulation.getRailwayLines().contains(line));
    }

    @Test
    void testAddAndRemoveRoute() {
        Route route = new Route(new ArrayList<>(), new ArrayList<>(), "routeName", false);
        simulation.addRoute(route);
        assertTrue(simulation.getRoutes().contains(route));
        simulation.removeRoute(route);
        assertFalse(simulation.getRoutes().contains(route));
    }

    @Test
    void testGetAvailableDateLocomotives() {
        assertNotNull(simulation.getAvailableDateLocomotives());
    }

    @Test
    void testAddAvailableDateLocomotive() {
        Locomotive loco = new Locomotive("L1", "", 1, 1, 1, 2000, 100, null, 1, 10);
        simulation.addAvailableDateLocomotive(loco);
        assertTrue(simulation.getAvailableDateLocomotives().contains(loco));
    }

    @Test
    void testAddAvailableDateLocomotiveNull() {
        assertThrows(IllegalArgumentException.class, () -> simulation.addAvailableDateLocomotive(null));
    }

    @Test
    void testGetAvailableDateCarriages() {
        assertNotNull(simulation.getAvailableDateCarriages());
    }

    @Test
    void testAddAvailableDateCarriage() {
        Carriage carriage = new Carriage("C1", "", 1, 1, 2000);
        simulation.addAvailableDateCarriage(carriage);
        assertTrue(simulation.getAvailableDateCarriages().contains(carriage));
    }

    @Test
    void testAddAvailableDateCarriageNull() {
        assertThrows(IllegalArgumentException.class, () -> simulation.addAvailableDateCarriage(null));
    }

    @Test
    void testAddAndRemoveBoughtLocomotive() {
        Locomotive loco = new Locomotive("L1", "", 1, 1, 1, 2000, 100, null, 1, 10);
        simulation.addBoughtLocomotive(loco);
        assertTrue(simulation.getBoughtLocomotives().contains(loco));
        simulation.removeLocomotive(loco);
        assertFalse(simulation.getBoughtLocomotives().contains(loco));
    }

    @Test
    void testAddBoughtLocomotiveNull() {
        assertThrows(IllegalArgumentException.class, () -> simulation.addBoughtLocomotive(null));
    }

    @Test
    void testRemoveLocomotiveNull() {
        assertThrows(IllegalArgumentException.class, () -> simulation.removeLocomotive(null));
    }

    @Test
    void testAddAndRemoveBoughtCarriage() {
        Carriage carriage = new Carriage("C1", "", 1, 1, 2000);
        simulation.addBoughtCarriages(carriage);
        assertTrue(simulation.getBoughtCarriages().contains(carriage));
        simulation.removeCarriage(carriage);
        assertFalse(simulation.getBoughtCarriages().contains(carriage));
    }

    @Test
    void testAddBoughtCarriageNull() {
        assertThrows(IllegalArgumentException.class, () -> simulation.addBoughtCarriages(null));
    }

    @Test
    void testRemoveCarriageNull() {
        assertThrows(IllegalArgumentException.class, () -> simulation.removeCarriage(null));
    }

    @Test
    void testGetAcquiredLocomotives() {
        assertEquals(simulation.getBoughtLocomotives(), simulation.getAcquiredLocomotives());
    }

    @Test
    void testGetAcquiredCarriages() {
        assertEquals(simulation.getBoughtCarriages(), simulation.getAcquiredCarriages());
    }

    @Test
    void testAddAndRemoveTrain() {
        Locomotive loco = new Locomotive("L1", "", 1, 1, 1, 2000, 100, null, 1, 10);
        List<Carriage> carriages = new ArrayList<>();
        carriages.add(new Carriage("C1", "", 1, 1, 2000));
        TimeDate acquisitionDate = new TimeDate(2024, 1, 1);
        Train train = new Train(loco, carriages, acquisitionDate);
        simulation.addTrain(train);
        assertTrue(simulation.getTrainList().contains(train));
        simulation.removeTrain(train);
        assertFalse(simulation.getTrainList().contains(train));
    }

    @Test
    void testAddTrainNull() {
        assertThrows(IllegalArgumentException.class, () -> simulation.addTrain(null));
    }

    @Test
    void testRemoveTrainNull() {
        assertThrows(IllegalArgumentException.class, () -> simulation.removeTrain(null));
    }

    @Test
    void testSetAndGetFinancialResults() {
        List<YearFinancialResult> results = new ArrayList<>();
        results.add(new YearFinancialResult(2024));
        simulation.setFinancialResults(results);
        assertEquals(results, simulation.getFinancialResults());
    }

    @Test
    void testGetActualFinancialResult() {
        List<YearFinancialResult> results = new ArrayList<>();
        YearFinancialResult result = new YearFinancialResult(2024);
        results.add(result);
        simulation.setFinancialResults(results);
        assertEquals(result, simulation.getActualFinancialResult());
    }

    @Test
    void testSetAndGetUnloadCargoLogsList() {
        List<UnloadCargoLogs> logs = new ArrayList<>();
        Station station = new Station(StationType.STATION, new Position(1,1), 1, "N", scenario);
        logs.add(new UnloadCargoLogs(new ArrayList<>(), station));
        simulation.setUnloadCargoLogsList(logs);
        assertEquals(logs, simulation.getUnloadCargoLogsList());
    }

    @Test
    void testEqualsAndHashCode() {
        Simulation sim1 = new Simulation("A", scenario);
        Simulation sim2 = new Simulation("A", scenario);
        Simulation sim3 = new Simulation("B", scenario);
        assertEquals(sim1, sim2);
        assertNotEquals(sim1, sim3);
        assertEquals(sim1.hashCode(), sim2.hashCode());
    }

    @Test
    void testToString() {
        assertNotNull(simulation.toString());
    }
}
