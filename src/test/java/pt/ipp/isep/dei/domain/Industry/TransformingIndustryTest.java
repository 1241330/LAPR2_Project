package pt.ipp.isep.dei.domain.Industry;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.domain.Resource.ResourcesType;
import pt.ipp.isep.dei.domain.Resource.TransformingResource;
import pt.ipp.isep.dei.domain.Scenario.Scenario;
import pt.ipp.isep.dei.domain.Map.Map;
import pt.ipp.isep.dei.domain._Others_.Size;
import pt.ipp.isep.dei.domain.Simulation.TimeDate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TransformingIndustryTest {

    private TransformingIndustry transformingIndustry;
    private TransformingResource transformingResource;
    private ResourcesType neededResource;
    private Scenario scenario;

    @BeforeEach
    void setUp() {
        neededResource = new ResourcesType("Copper", 1, 100, 5);
        List<ResourcesType> neededResources = new ArrayList<>();
        neededResources.add(neededResource);
        transformingResource = new TransformingResource("Wire", 2, 50, 3, neededResources);

        transformingIndustry = new TransformingIndustry("Wire Factory", "Scenario1", transformingResource);

        Map map = new Map("TestMap", new Size(10, 10));
        TimeDate start = new TimeDate(2024, 1, 1);
        TimeDate end = new TimeDate(2024, 12, 31);
        scenario = new Scenario(map, "Scenario1", 1, start, end);
    }

    @Test
    void testGetTransformingResource() {
        assertSame(transformingResource, transformingIndustry.getTransformingResource());
    }

    @Test
    void testGetPrimaryResources() {
        List<ResourcesType> primaryResources = transformingIndustry.getPrimaryResources();
        assertEquals(1, primaryResources.size());
        assertEquals(neededResource, primaryResources.get(0));
    }

    @Test
    void testGetScenarioName() {
        assertEquals("Scenario1", transformingIndustry.getScenarioName());
    }

    @Test
    void testCloneTransformingIndustryAndGetCloned() {
        transformingIndustry.cloneTransformingIndustry(transformingResource, scenario);
        assertEquals(1, transformingIndustry.getClonedIndustryList().size());
        TransformingIndustry cloned = transformingIndustry.getClonedTransformingIndustry(scenario);
        assertNotNull(cloned);
        assertEquals("Scenario1", cloned.getScenarioName());
        assertEquals("Wire Factory", cloned.getName());
        assertSame(transformingResource, cloned.getTransformingResource());
    }

    @Test
    void testToStringFormat() {
        String str = transformingIndustry.toString().toLowerCase();
        assertTrue(str.contains("wire factory"));
        assertTrue(str.contains("transforms"));
        assertTrue(str.contains("wire"));
    }

    @Test
    void testConstructorWithNullResource() {
        TransformingIndustry industry = new TransformingIndustry("NoResource", "Scenario2", null);
        assertNull(industry.getTransformingResource());
        assertTrue(industry.getPrimaryResources().isEmpty());
    }

    @Test
    void testCloneTransformingIndustryNotFound() {
        TransformingIndustry industry = new TransformingIndustry("Wire Factory", "Scenario1", transformingResource);
        Map map = new Map("TestMap2", new Size(5, 5));
        TimeDate start = new TimeDate(2025, 1, 1);
        TimeDate end = new TimeDate(2025, 12, 31);
        Scenario otherScenario = new Scenario(map, "Scenario2", 2, start, end);
        assertNull(industry.getClonedTransformingIndustry(otherScenario));
    }

    @Test
    void testCloneTransformingIndustryWithNullScenario() {
        transformingIndustry.cloneTransformingIndustry(transformingResource, null);
        assertNull(transformingIndustry.getClonedTransformingIndustry(null));
    }

    @Test
    void testToStringWithNullResource() {
        TransformingIndustry industry = new TransformingIndustry("NoResource", "Scenario2", null);
        String str = industry.toString().toLowerCase();
        assertTrue(str.contains("noresource"));
        assertTrue(str.contains("transforms"));
    }
}
