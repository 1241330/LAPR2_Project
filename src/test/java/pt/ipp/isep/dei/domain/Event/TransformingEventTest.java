package pt.ipp.isep.dei.domain.Event;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.domain.Resource.Resource;
import pt.ipp.isep.dei.domain.Resource.ResourcesType;
import pt.ipp.isep.dei.domain.Resource.TransformingResource;
import pt.ipp.isep.dei.domain.Industry.TransformingIndustry;
import pt.ipp.isep.dei.domain.Industry.Industry;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the TranformingEvent class.
 */
class TransformingEventTest {
    private TranformingEvent event;
    private TransformingIndustry industry;
    private TransformingResource transformingResource;
    private ResourcesType neededType1;
    private ResourcesType neededType2;

    /**
     * Sets up the test environment before each test.
     */
    @BeforeEach
    void setUp() {
        neededType1 = new ResourcesType("Água", 100, 5, 10);
        neededType2 = new ResourcesType("Sal", 100, 5, 10);
        ArrayList<ResourcesType> neededResources = new ArrayList<>();
        neededResources.add(neededType1);
        neededResources.add(neededType2);
        transformingResource = new TransformingResource("Produto", 100, 5, 10, neededResources);
        industry = new TransformingIndustry("Indústria Central", "cenario", transformingResource);
        event = new TranformingEvent("Transformação", 5, 10, transformingResource, industry);
        TranformingEvent.setPrintFirstMenu(true);
    }

    /**
     * Tests the constructor and getter methods.
     */
    @Test
    void testConstructorAndGetters() {
        assertEquals("Transformação", event.getName());
        assertEquals(5, event.getInterval());
        assertEquals(15, event.getNextGenerationDate());
        assertEquals(transformingResource, event.getTransformingResource());
        assertEquals(industry, event.getIndustry());
        assertTrue(TranformingEvent.isPrintFirstMenu());
    }

    /**
     * Tests the setter methods for industry and transforming resource.
     */
    @Test
    void testSetters() {
        TransformingResource newResource = new TransformingResource("Novo", 100, 5, 10, new ArrayList<>());
        TransformingIndustry newIndustry = new TransformingIndustry("Indústria Nova", "cenario", newResource);
        event.setIndustry(newIndustry);
        event.setTransformingResource(newResource);
        assertEquals(newIndustry, event.getIndustry());
        assertEquals(newResource, event.getTransformingResource());
    }

    /**
     * Tests the trigger method for a valid operation (resources exist in inventory).
     */
    @Test
    void testTrigger_ValidOperation() {
        // Adiciona recursos necessários ao inventário da indústria
        industry.getInventory().addResource(new Resource(neededType1, 10));
        industry.getInventory().addResource(new Resource(neededType2, 10));
        int nextDateBefore = event.getNextGenerationDate();
        event.trigger();
        // O inventário deve ter recebido o produto transformado
        assertTrue(industry.getInventory().existsResourceInInventory(transformingResource));
        // Os recursos necessários devem ter sido removidos
        assertFalse(industry.getInventory().existsResourceInInventory(neededType1));
        assertFalse(industry.getInventory().existsResourceInInventory(neededType2));
        assertFalse(TranformingEvent.isPrintFirstMenu());
        assertEquals(nextDateBefore + 5, event.getNextGenerationDate());
    }

    /**
     * Tests the trigger method for an invalid operation (resources do not exist in inventory).
     */
    @Test
    void testTrigger_InvalidOperation() {
        // Não adiciona recursos necessários ao inventário
        int nextDateBefore = event.getNextGenerationDate();
        event.trigger();
        // O inventário não deve ter o produto transformado
        assertFalse(industry.getInventory().existsResourceInInventory(transformingResource));
        assertTrue(TranformingEvent.isPrintFirstMenu());
        assertEquals(nextDateBefore + 5, event.getNextGenerationDate());
    }

    /**
     * Tests the static setter and getter for printFirstMenu.
     */
    @Test
    void testPrintFirstMenuStaticSetterAndGetter() {
        TranformingEvent.setPrintFirstMenu(false);
        assertFalse(TranformingEvent.isPrintFirstMenu());
        TranformingEvent.setPrintFirstMenu(true);
        assertTrue(TranformingEvent.isPrintFirstMenu());
    }
}
