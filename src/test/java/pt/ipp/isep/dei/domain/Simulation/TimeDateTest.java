package pt.ipp.isep.dei.domain.Simulation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TimeDateTest {

    @Test
    void testValidConstruction() {
        TimeDate date = new TimeDate(2024, 2, 29); // Ano bissexto
        assertEquals(2024, date.getYear());
        assertEquals(2, date.getMonth());
        assertEquals(29, date.getDay());
    }

    @Test
    void testInvalidYear() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new TimeDate(0, 1, 1));
        assertEquals("Year must be greater than 0.", exception.getMessage());
    }

    @Test
    void testInvalidMonth() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new TimeDate(2024, 13, 1));
        assertEquals("Month must be between 1 and 12.", exception.getMessage());
    }

    @Test
    void testInvalidDay() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new TimeDate(2023, 2, 29));
        assertTrue(exception.getMessage().contains("Day must be between 1 and"));
    }

    @Test
    void testLeapYearFebruary() {
        TimeDate leapDate = new TimeDate(2020, 2, 29); // Leap year
        assertEquals(29, leapDate.getDay());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> new TimeDate(2021, 2, 29)); // Non-leap
        assertTrue(exception.getMessage().contains("Day must be between 1 and 28"));
    }

    @Test
    void testGettersAndSetters() {
        TimeDate date = new TimeDate(2022, 5, 15);
        date.setYear(2023);
        date.setMonth(6);
        date.setDay(20);
        assertEquals(2023, date.getYear());
        assertEquals(6, date.getMonth());
        assertEquals(20, date.getDay());
    }

    @Test
    void testGetTotalMonths() {
        TimeDate date = new TimeDate(2023, 6, 15);
        assertEquals((2023 * 12) + 6, date.getTotalMonths());
    }

    @Test
    void testGetTotalDays() {
        TimeDate date1 = new TimeDate(1, 1, 1);
        assertEquals(1, date1.getTotalDays());

        TimeDate date2 = new TimeDate(2, 1, 1); // +365 days (year 1 is not leap)
        assertEquals(366, date2.getTotalDays());
    }

    @Test
    void testGetDifference() {
        TimeDate earlier = new TimeDate(2020, 1, 1);
        TimeDate later = new TimeDate(2021, 1, 1);
        assertEquals(366, later.getDiference(earlier)); // 2020 was leap year
        assertEquals(-366, earlier.getDiference(later));
    }

    @Test
    void testBefore() {
        TimeDate earlier = new TimeDate(2022, 1, 1);
        TimeDate later = new TimeDate(2023, 1, 1);
        assertTrue(earlier.before(later));
        assertFalse(later.before(earlier));
        assertFalse(earlier.before(earlier));
    }

    @Test
    void testToString() {
        TimeDate date = new TimeDate(2025, 12, 31);
        assertEquals("Actual date: 31-12-2025", date.toString());
    }

    @Test
    void testToSimpleString() {
        TimeDate date = new TimeDate(2025, 12, 31);
        assertEquals("31/12/2025", date.toSimpleString());
    }
}
