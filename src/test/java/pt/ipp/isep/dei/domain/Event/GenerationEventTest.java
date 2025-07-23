package pt.ipp.isep.dei.domain.Event;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.domain.City.HouseBlock;
import pt.ipp.isep.dei.domain.Industry.Industry;
import pt.ipp.isep.dei.domain.Resource.Resource;
import pt.ipp.isep.dei.domain.Resource.ResourcesType;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GenerationEventTest {


    static class TestResourceType extends ResourcesType {
        public TestResourceType(String name) { super(name, 0, 0, 0); }
    }


    static class TestResource extends Resource {
        private final ResourcesType type;
        public TestResource(String name) {
            super(new TestResourceType(name), 1);
            this.type = new TestResourceType(name);
        }
        @Override
        public ResourcesType getResourceType() { return type; }
    }

    // Subclasse para Industry
    static class TestIndustry extends Industry {
        private int addCount = 0;
        private boolean updated = false;
        public TestIndustry() { super("Indústria", null, null); }
        @Override
        public int addResourceToInventory(Resource r) { addCount++; return 7; }
        @Override
        public String getName() { return "Indústria"; }
        @Override
        public void setUpdatedInventory(boolean updated) { this.updated = updated; }
        public boolean isUpdated() { return updated; }
        public int getAddCount() { return addCount; }
    }


    static class TestHouseBlock extends HouseBlock {
        private int addCount = 0;
        private boolean updated = false;
        public TestHouseBlock() { super(null, null); }
        @Override
        public int addResourceToInventory(Resource r) { addCount++; return 4; }
        @Override
        public String getCityName() { return "Cidade"; }
        @Override
        public void setUpdatedInventory(boolean updated) { this.updated = updated; }
        public boolean isUpdated() { return updated; }
        public int getAddCount() { return addCount; }
    }

    private TestResource resource;
    private TestIndustry industry;
    private TestHouseBlock houseBlock;
    private GenerationEvent eventIndustry;
    private GenerationEvent eventHouseBlock;

    @BeforeEach
    void setUp() {
        resource = new TestResource("Água");
        industry = new TestIndustry();
        houseBlock = new TestHouseBlock();
        GenerationEvent.setPrintFirstMenu(true);
        eventIndustry = new GenerationEvent("Geração Indústria", 5, 10, resource, industry);
        eventHouseBlock = new GenerationEvent("Geração Casa", 3, 20, resource, houseBlock);
    }

    @Test
    void testConstructorAndGetters_Industry() {
        assertEquals("Geração Indústria", eventIndustry.getName());
        assertEquals(5, eventIndustry.getInterval());
        assertEquals(15, eventIndustry.getNextGenerationDate());
        assertEquals(resource, eventIndustry.getResource());
        assertEquals(industry, eventIndustry.getIndustry());
        assertNull(eventIndustry.getHouseBlock());
        assertTrue(GenerationEvent.isPrintFirstMenu());
    }

    @Test
    void testConstructorAndGetters_HouseBlock() {
        assertEquals("Geração Casa", eventHouseBlock.getName());
        assertEquals(3, eventHouseBlock.getInterval());
        assertEquals(23, eventHouseBlock.getNextGenerationDate());
        assertEquals(resource, eventHouseBlock.getResource());
        assertEquals(houseBlock, eventHouseBlock.getHouseBlock());
        assertNull(eventHouseBlock.getIndustry());
    }

    @Test
    void testSetResource() {
        TestResource newResource = new TestResource("Sal");
        eventIndustry.setResource(newResource);
        assertEquals(newResource, eventIndustry.getResource());
    }

    @Test
    void testTrigger_Industry() {
        GenerationEvent.setPrintFirstMenu(true);
        int nextDateBefore = eventIndustry.getNextGenerationDate();
        List<String> logs = eventIndustry.trigger();
        assertFalse(logs.isEmpty());
        assertTrue(logs.get(0).contains("Production"));
        assertTrue(logs.get(1).contains("Indústria"));
        assertEquals(nextDateBefore + 5, eventIndustry.getNextGenerationDate());
        assertTrue(industry.isUpdated());
        assertEquals(1, industry.getAddCount());
        assertFalse(GenerationEvent.isPrintFirstMenu());
    }

    @Test
    void testTrigger_HouseBlock() {
        GenerationEvent.setPrintFirstMenu(true);
        int nextDateBefore = eventHouseBlock.getNextGenerationDate();
        List<String> logs = eventHouseBlock.trigger();
        assertFalse(logs.isEmpty());
        assertTrue(logs.get(0).contains("Production"));
        assertTrue(logs.get(1).contains("House Block"));
        assertEquals(nextDateBefore + 3, eventHouseBlock.getNextGenerationDate());
        assertTrue(houseBlock.isUpdated());
        assertEquals(1, houseBlock.getAddCount());
        assertFalse(GenerationEvent.isPrintFirstMenu());
    }

    @Test
    void testPrintFirstMenuStaticSetterAndGetter() {
        GenerationEvent.setPrintFirstMenu(false);
        assertFalse(GenerationEvent.isPrintFirstMenu());
        GenerationEvent.setPrintFirstMenu(true);
        assertTrue(GenerationEvent.isPrintFirstMenu());
    }
}