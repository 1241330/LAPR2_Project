package pt.ipp.isep.dei.domain.Industry;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.domain._Others_.Position;
import pt.ipp.isep.dei.domain.Resource.ResourcesType;
import pt.ipp.isep.dei.domain.Resource.TransformingResource;
import pt.ipp.isep.dei.domain.Scenario.Scenario;
import pt.ipp.isep.dei.domain.Map.Map;
import pt.ipp.isep.dei.domain._Others_.Size;
import pt.ipp.isep.dei.domain.Simulation.TimeDate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MixedIndustryTest {

    private MixedIndustry mixedIndustry;
    private ResourcesType resource1;
    private TransformingResource transformingResource;
    private Scenario scenario;

    @BeforeEach
    void setUp() {
        mixedIndustry = new MixedIndustry("Mixed Industry", IndustryType.MIXED, new Position(1, 2));
        resource1 = new ResourcesType("Coal", 1, 1, 1);
        List<ResourcesType> resourcesList = new ArrayList<>();
        resourcesList.add(resource1);
        transformingResource = new TransformingResource("Steel", 2, 2, 1, resourcesList);

        Map map = new Map("TestMap", new Size(10, 10));
        TimeDate start = new TimeDate(2024, 1, 1);
        TimeDate end = new TimeDate(2024, 12, 31);
        scenario = new Scenario(map, "Scenario1", 1, start, end);
    }

    @Test
    void testAddAndGetExportedResources() {
        mixedIndustry.addExportedResource(resource1);
        List<ResourcesType> exported = mixedIndustry.getExportedResources();
        assertEquals(1, exported.size());
        assertEquals(resource1, exported.get(0));
    }

    @Test
    void testAddAndGetImportedResources() {
        mixedIndustry.addImportedResource(resource1);
        List<ResourcesType> imported = mixedIndustry.getImportedResources();
        assertEquals(1, imported.size());
        assertEquals(resource1, imported.get(0));
    }

    @Test
    void testAddAndGetTransformedResources() {
        mixedIndustry.addTransformingResource(transformingResource);
        List<ResourcesType> transformed = mixedIndustry.getTransformedResources();
        assertEquals(1, transformed.size());
        assertEquals(transformingResource, transformed.get(0));
    }

    @Test
    void testCloneMixedIndustryAndGetCloned() {
        mixedIndustry.cloneMixedIndustry(scenario);
        assertEquals(1, mixedIndustry.getClonedIndustryList().size());
        MixedIndustry cloned = mixedIndustry.getClonedMixedIndustry(scenario);
        assertNotNull(cloned);
        assertEquals("Scenario1", cloned.getScenarioName());
    }

    @Test
    void testExportImportTransformResourceOnClonedIndustry() {
        mixedIndustry.cloneMixedIndustry(scenario);
        mixedIndustry.exportResource(resource1, scenario);
        mixedIndustry.importResource(resource1, scenario);
        mixedIndustry.transformedResource(transformingResource, scenario);

        MixedIndustry cloned = mixedIndustry.getClonedMixedIndustry(scenario);
        assertEquals(1, cloned.getExportedResources().size());
        assertEquals(1, cloned.getImportedResources().size());
        assertEquals(1, cloned.getTransformedResources().size());
    }

    @Test
    void testToStringFormat() {
        mixedIndustry.addImportedResource(resource1);
        mixedIndustry.addExportedResource(resource1);
        mixedIndustry.addTransformingResource(transformingResource);
        String str = mixedIndustry.toString();
        assertTrue(str.toLowerCase().contains("imports: coal"));
        assertTrue(str.toLowerCase().contains("exports: coal"));
        assertTrue(str.toLowerCase().contains("transformation: steel"));
    }

    @Test
    void testConstructorWithScenarioName() {
        MixedIndustry mi = new MixedIndustry("Indústria X", "CenarioX");
        assertEquals("Indústria X", mi.getName());
        assertEquals("CenarioX", mi.getScenarioName());
    }

    @Test
    void testAddDuplicatedResources() {
        mixedIndustry.addExportedResource(resource1);
        mixedIndustry.addExportedResource(resource1);
        assertEquals(2, mixedIndustry.getExportedResources().size());
        mixedIndustry.addImportedResource(resource1);
        mixedIndustry.addImportedResource(resource1);
        assertEquals(2, mixedIndustry.getImportedResources().size());
        mixedIndustry.addTransformingResource(transformingResource);
        mixedIndustry.addTransformingResource(transformingResource);
        assertEquals(2, mixedIndustry.getTransformedResources().size());
    }

    @Test
    void testGetClonedMixedIndustryNotFound() {
        assertNull(mixedIndustry.getClonedMixedIndustry(scenario));
    }

    @Test
    void testExportImportTransformResourceOnNonexistentClonedIndustry() {
        assertDoesNotThrow(() -> mixedIndustry.exportResource(resource1, scenario));
        assertDoesNotThrow(() -> mixedIndustry.importResource(resource1, scenario));
        assertDoesNotThrow(() -> mixedIndustry.transformedResource(transformingResource, scenario));
    }

    @Test
    void testToStringEmptyAndMultipleResources() {
        String emptyStr = mixedIndustry.toString();
        assertTrue(emptyStr.contains("imports: none"));
        assertTrue(emptyStr.contains("exports: none"));
        assertTrue(emptyStr.contains("transformation: none"));
        mixedIndustry.addImportedResource(resource1);
        mixedIndustry.addImportedResource(new ResourcesType("Iron", 1, 1, 1));
        mixedIndustry.addExportedResource(resource1);
        mixedIndustry.addExportedResource(new ResourcesType("Iron", 1, 1, 1));
        mixedIndustry.addTransformingResource(transformingResource);
        mixedIndustry.addTransformingResource(new TransformingResource("Copper", 2, 2, 1, new ArrayList<>()));
        String multiStr = mixedIndustry.toString();
        assertTrue(multiStr.contains("coal, iron"));
        assertTrue(multiStr.contains("steel, copper"));
    }

    @Test
    void testGetClonedIndustryList() {
        assertNotNull(mixedIndustry.getClonedIndustryList());
        assertEquals(0, mixedIndustry.getClonedIndustryList().size());
        mixedIndustry.cloneMixedIndustry(scenario);
        assertEquals(1, mixedIndustry.getClonedIndustryList().size());
    }
}
