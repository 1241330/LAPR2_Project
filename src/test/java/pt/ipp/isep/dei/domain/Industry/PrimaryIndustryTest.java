package pt.ipp.isep.dei.domain.Industry;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.domain.Resource.PrimaryResource;
import pt.ipp.isep.dei.domain.Scenario.Scenario;
import pt.ipp.isep.dei.domain.Map.Map;
import pt.ipp.isep.dei.domain._Others_.Size;
import pt.ipp.isep.dei.domain.Simulation.TimeDate;

import static org.junit.jupiter.api.Assertions.*;

class PrimaryIndustryTest {

    private PrimaryIndustry primaryIndustry;
    private PrimaryResource primaryResource;
    private Scenario scenario;

    @BeforeEach
    void setUp() {
        primaryResource = new PrimaryResource("Iron Ore", 1, 100, 5);
        primaryIndustry = new PrimaryIndustry("Iron Mine", "Scenario1", primaryResource);
        Map map = new Map("TestMap", new Size(10, 10));
        TimeDate start = new TimeDate(2024, 1, 1);
        TimeDate end = new TimeDate(2024, 12, 31);
        scenario = new pt.ipp.isep.dei.domain.Scenario.Scenario(map, "Scenario1", 1, start, end);
    }

    @Test
    void testGetPrimaryResource() {
        PrimaryIndustry industry = new PrimaryIndustry("Iron Mine", "Scenario1", primaryResource);
        assertSame(primaryResource, industry.getPrimaryResource());
    }

    @Test
    void testGetMaxResources() {
        PrimaryIndustry industry = new PrimaryIndustry("Iron Mine", "Scenario1", primaryResource);
        assertEquals(primaryResource.getMaxResources(), industry.getMaxResources());
    }

    @Test
    void testGetIntervalBetweenResourceGeneration() {
        PrimaryIndustry industry = new PrimaryIndustry("Iron Mine", "Scenario1", primaryResource);
        assertEquals(primaryResource.getIntervalBetweenResourceGeneration(), industry.getIntervalBetweenResourceGeneration());
    }

    @Test
    void testGetQuantityProduced() {
        PrimaryIndustry industry = new PrimaryIndustry("Iron Mine", "Scenario1", primaryResource);
        assertEquals(primaryResource.getQuantityProduced(), industry.getQuantityProduced());
    }

    @Test
    void testClonePrimaryIndustryAndGetCloned() {
        primaryIndustry.clonePrimaryIndustry(primaryResource, scenario);
        PrimaryIndustry cloned = primaryIndustry.getClonedPrimaryIndustry(scenario);
        assertNotNull(cloned);
        assertSame(primaryResource, cloned.getPrimaryResource());
    }

    @Test
    void testToStringFormat() {
        PrimaryIndustry industry = new PrimaryIndustry("Iron Mine", "Scenario1", primaryResource);
        String str = industry.toString().toLowerCase();
        assertTrue(str.contains("iron mine"));
        assertTrue(str.contains("generates"));
        assertTrue(str.contains("iron ore"));
    }

    @Test
    void testConstructorWithNullResource() {
        PrimaryIndustry industry = new PrimaryIndustry("NoResource", "Scenario2", null);
        assertNull(industry.getPrimaryResource());
        assertEquals(0, industry.getMaxResources());
        assertEquals(0, industry.getIntervalBetweenResourceGeneration());
        assertEquals(0, industry.getQuantityProduced());
    }

    @Test
    void testClonePrimaryIndustryNotFound() {
        PrimaryIndustry industry = new PrimaryIndustry("Iron Mine", "Scenario1", primaryResource);
        Map map = new Map("TestMap2", new Size(5, 5));
        TimeDate start = new TimeDate(2025, 1, 1);
        TimeDate end = new TimeDate(2025, 12, 31);
        Scenario otherScenario = new Scenario(map, "Scenario2", 2, start, end);
        assertNull(industry.getClonedPrimaryIndustry(otherScenario));
    }

    @Test
    void testClonePrimaryIndustryWithNullScenario() {
        primaryIndustry.clonePrimaryIndustry(primaryResource, null);
        assertNull(primaryIndustry.getClonedPrimaryIndustry(null));
    }

    @Test
    void testToStringWithNullResource() {
        PrimaryIndustry industry = new PrimaryIndustry("NoResource", "Scenario2", null);
        String str = industry.toString().toLowerCase();
        assertTrue(str.contains("noresource"));
        assertTrue(str.contains("generates"));
    }
}
