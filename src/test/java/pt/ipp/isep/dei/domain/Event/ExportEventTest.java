package pt.ipp.isep.dei.domain.Event;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.domain.Resource.Resource;
import pt.ipp.isep.dei.domain.Resource.ResourcesType;
import pt.ipp.isep.dei.domain.Station.StationAssociations;
import pt.ipp.isep.dei.domain._Others_.Inventory;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ExportEventTest {


    static class TestAssociation implements StationAssociations {
        private int quantity;
        private boolean updated = false;
        private final List<Resource> removed = new ArrayList<>();

        @Override
        public int getResourceQuantity(ResourcesType type) {
            return quantity;
        }

        @Override
        public int removeResourceFromInventory(Resource resource) {
            removed.add(resource);
            int old = quantity;
            quantity = 0;
            return old;
        }

        @Override
        public void setUpdatedInventory(boolean updated) {
            this.updated = updated;
        }

        @Override
        public int addResourceToInventory(Resource resource) {
            // Não é necessário para estes testes
            return 0;
        }

        @Override
        public boolean existsResourceInInventory(ResourcesType type) {
            return quantity > 0;
        }

        @Override
        public Inventory getInventory() {
            // Não é necessário para estes testes
            return null;
        }

        @Override
        public boolean isUpdatedInventory() {
            return updated;
        }

        public void setQuantity(int q) {
            this.quantity = q;
        }

        public boolean isUpdated() {
            return updated;
        }

        public List<Resource> getRemoved() {
            return removed;
        }
    }

    private ExportEvent event;
    private TestAssociation association;
    private ResourcesType resourceType;

    @BeforeEach
    void setUp() {
        resourceType = new ResourcesType("Carvão", 100, 1, 10);
        association = new TestAssociation();
        association.setQuantity(50);
        ExportEvent.setPrintFirstMenu(true);
        event = new ExportEvent("Exportar Carvão", 5, 10, resourceType, association);
    }

    @Test
    void testConstructorAndGetters() {
        assertEquals("Exportar Carvão", event.getName());
        assertEquals(5, event.getInterval());
        assertEquals(15, event.getNextGenerationDate());
        assertEquals(resourceType, event.getResourceType());
        assertEquals(association, event.getAssociation());
        assertTrue(ExportEvent.isPrintFirstMenu());
    }

    @Test
    void testSetters() {
        ResourcesType newType = new ResourcesType("Ferro", 200, 2, 20);
        TestAssociation newAssociation = new TestAssociation();
        event.setResourceType(newType);
        event.setAssociation(newAssociation);
        assertEquals(newType, event.getResourceType());
        assertEquals(newAssociation, event.getAssociation());
    }

    @Test
    void testTriggerWithResource() {
        association.setQuantity(30);
        ExportEvent.setPrintFirstMenu(true);
        int nextDateBefore = event.getNextGenerationDate();
        List<String> logs = event.trigger();
        assertFalse(logs.isEmpty());
        assertEquals("📦  Export:", logs.get(0));
        assertTrue(logs.get(1).contains("30"));
        assertTrue(logs.get(1).contains("Carvão"));
        assertEquals(nextDateBefore + 5, event.getNextGenerationDate());
        assertTrue(association.isUpdated());
        assertEquals(1, association.getRemoved().size());
        Resource removed = association.getRemoved().get(0);
        assertEquals(resourceType, removed.getResourceType());
        assertEquals(30, removed.getQuantity());
    }

    @Test
    void testTriggerWithoutResource() {
        association.setQuantity(0);
        List<String> logs = event.trigger();
        assertTrue(logs.isEmpty());
        assertEquals(0, association.getRemoved().size());
        assertFalse(association.isUpdated());
    }

    @Test
    void testPrintFirstMenuStaticSetterAndGetter() {
        ExportEvent.setPrintFirstMenu(false);
        assertFalse(ExportEvent.isPrintFirstMenu());
        ExportEvent.setPrintFirstMenu(true);
        assertTrue(ExportEvent.isPrintFirstMenu());
    }
}