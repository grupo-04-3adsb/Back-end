package tcatelie.microservice.auth.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DateFormatTest {

    @Test
    void testFormatLocalDateDefault() {
        LocalDate date = LocalDate.of(2024, 11, 23);
        String result = DateFormat.format(date);
        assertEquals("2024-11-23", result);
    }

    @Test
    void testFormatLocalDateWithCustomPattern() {
        LocalDate date = LocalDate.of(2024, 11, 23);
        String result = DateFormat.format(date, "dd/MM/yyyy");
        assertEquals("23/11/2024", result);
    }

    @Test
    void testFormatLocalDateNull() {
        String result = DateFormat.format((LocalDate) null);
        assertNull(result);
    }

    @Test
    void testFormatLocalDateTimeDefault() {
        LocalDateTime dateTime = LocalDateTime.of(2024, 11, 23, 15, 30);
        String result = DateFormat.format(dateTime);
        assertEquals("2024-11-23 15:30:00", result);
    }

    @Test
    void testFormatLocalDateTimeWithCustomPattern() {
        LocalDateTime dateTime = LocalDateTime.of(2024, 11, 23, 15, 30);
        String result = DateFormat.format(dateTime, "HH:mm | dd/MM/yyyy");
        assertEquals("15:30 | 23/11/2024", result);
    }

    @Test
    void testFormatLocalDateTimeNull() {
        String result = DateFormat.format((LocalDateTime) null);
        assertNull(result);
    }

    @Test
    void testParseDateDefault() {
        String dateStr = "2024-11-23";
        LocalDate result = DateFormat.parseDate(dateStr);
        assertEquals(LocalDate.of(2024, 11, 23), result);
    }

    @Test
    void testParseDateCustomFormat() {
        String dateStr = "23/11/2024";
        LocalDate result = DateFormat.parseDate(dateStr, "dd/MM/yyyy");
        assertEquals(LocalDate.of(2024, 11, 23), result);
    }

    @Test
    void testParseDateInvalidFormat() {
        String dateStr = "23-11-2024";
        assertThrows(IllegalArgumentException.class, () -> DateFormat.parseDate(dateStr));
    }

    @Test
    void testParseDateNull() {
        LocalDate result = DateFormat.parseDate(null);
        assertNull(result);
    }

    @Test
    void testParseDateTimeDefault() {
        String dateTimeStr = "2024-11-23 15:30:00";
        LocalDateTime result = DateFormat.parseDateTime(dateTimeStr);
        assertEquals(LocalDateTime.of(2024, 11, 23, 15, 30), result);
    }

    @Test
    void testParseDateTimeCustomFormat() {
        String dateTimeStr = "15:30 | 23/11/2024";
        LocalDateTime result = DateFormat.parseDateTime(dateTimeStr, "HH:mm | dd/MM/yyyy");
        assertEquals(LocalDateTime.of(2024, 11, 23, 15, 30), result);
    }

    @Test
    void testParseDateTimeInvalidFormat() {
        String dateTimeStr = "2024-11-23";
        assertThrows(IllegalArgumentException.class, () -> DateFormat.parseDateTime(dateTimeStr));
    }

    @Test
    void testToDateFromLocalDate() {
        LocalDate localDate = LocalDate.of(2024, 11, 23);
        Date result = DateFormat.toDate(localDate);
        assertEquals(Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant()), result);
    }

    @Test
    void testToLocalDateFromDate() {
        Date date = new Date();
        LocalDate result = DateFormat.toLocalDate(date);
        assertEquals(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), result);
    }

    @Test
    void testToLocalDateTimeFromDate() {
        Date date = new Date();
        LocalDateTime result = DateFormat.toLocalDateTime(date);
        assertEquals(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime(), result);
    }

    @Test
    void testFormatToCustomPatternFromLocalDateTime() {
        LocalDateTime dateTime = LocalDateTime.of(2024, 11, 23, 15, 30);
        String result = DateFormat.formatToCustomPattern(dateTime);
        assertEquals("15:30 | 23/11/2024", result);
    }

    @Test
    void testFormatToCustomPatternFromDate() {
        LocalDateTime dateTime = LocalDateTime.of(2024, 11, 23, 15, 30);
        Date date = DateFormat.toDate(dateTime);
        String result = DateFormat.formatToCustomPattern(date);
        assertEquals("15:30 | 23/11/2024", result);
    }

    @Test
    void testIsValidDateTrue() {
        boolean result = DateFormat.isValidDate("23/11/2024", "dd/MM/yyyy");
        assertTrue(result);
    }

    @Test
    void testIsValidDateFalse() {
        boolean result = DateFormat.isValidDate("2024/11/23", "dd/MM/yyyy");
        assertFalse(result);
    }

    @Test
    void testGetCurrentDate() {
        LocalDate result = DateFormat.getCurrentDate();
        assertEquals(LocalDate.now(), result);
    }

    @Test
    void testGetCurrentDateTime() {
        LocalDateTime result = DateFormat.getCurrentDateTime();
        assertTrue(result.isBefore(LocalDateTime.now().plusSeconds(1)));
    }
}
