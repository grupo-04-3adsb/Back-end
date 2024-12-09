package tcatelie.microservice.auth.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

public class DateFormat {

    private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    private static final String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private static final DateTimeFormatter DEFAULT_DATE_FORMATTER = DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT);
    private static final DateTimeFormatter DEFAULT_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMAT);

    public static String format(LocalDate date) {
        return date != null ? date.format(DEFAULT_DATE_FORMATTER) : null;
    }

    public static String format(LocalDate date, String format) {
        return date != null ? date.format(DateTimeFormatter.ofPattern(format)) : null;
    }

    public static String format(LocalDateTime dateTime) {
        return dateTime != null ? dateTime.format(DEFAULT_DATE_TIME_FORMATTER) : null;
    }

    public static String format(LocalDateTime dateTime, String format) {
        return dateTime != null ? dateTime.format(DateTimeFormatter.ofPattern(format)) : null;
    }

    public static LocalDate parseDate(String dateStr) {
        try {
            return dateStr != null ? LocalDate.parse(dateStr, DEFAULT_DATE_FORMATTER) : null;
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format: " + dateStr, e);
        }
    }

    public static LocalDate parseDate(String dateStr, String format) {
        try {
            return dateStr != null ? LocalDate.parse(dateStr, DateTimeFormatter.ofPattern(format)) : null;
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format: " + dateStr, e);
        }
    }

    public static LocalDateTime parseDateTime(String dateTimeStr) {
        try {
            return dateTimeStr != null ? LocalDateTime.parse(dateTimeStr, DEFAULT_DATE_TIME_FORMATTER) : null;
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid datetime format: " + dateTimeStr, e);
        }
    }

    public static LocalDateTime parseDateTime(String dateTimeStr, String format) {
        try {
            return dateTimeStr != null ? LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ofPattern(format)) : null;
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid datetime format: " + dateTimeStr, e);
        }
    }

    public static LocalDate toLocalDate(Date date) {
        return date != null ? date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate() : null;
    }

    public static LocalDateTime toLocalDateTime(Date date) {
        return date != null ? date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime() : null;
    }

    public static Date toDate(LocalDate localDate) {
        return localDate != null ? Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant()) : null;
    }

    public static Date toDate(LocalDateTime localDateTime) {
        return localDateTime != null ? Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant()) : null;
    }

    public static Date parseToDate(String dateStr) {
        LocalDate localDate = parseDate(dateStr);
        return toDate(localDate);
    }

    public static Date parseToDate(String dateStr, String format) {
        LocalDate localDate = parseDate(dateStr, format);
        return toDate(localDate);
    }

    public static String format(Date date) {
        LocalDate localDate = toLocalDate(date);
        return format(localDate);
    }

    public static String format(Date date, String format) {
        LocalDate localDate = toLocalDate(date);
        return format(localDate, format);
    }

    public static boolean isValidDate(String dateStr, String format) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
            LocalDate.parse(dateStr, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public static LocalDate getCurrentDate() {
        return LocalDate.now();
    }

    public static LocalDateTime getCurrentDateTime() {
        return LocalDateTime.now();
    }

    public static LocalDate addDays(LocalDate date, long days) {
        return date != null ? date.plusDays(days) : null;
    }

    public static LocalDate subtractDays(LocalDate date, long days) {
        return date != null ? date.minusDays(days) : null;
    }

    public static LocalDateTime addHours(LocalDateTime dateTime, long hours) {
        return dateTime != null ? dateTime.plusHours(hours) : null;
    }

    public static LocalDateTime subtractHours(LocalDateTime dateTime, long hours) {
        return dateTime != null ? dateTime.minusHours(hours) : null;
    }

    public static String formatToCustomPattern(LocalDateTime dateTime) {
        if (dateTime == null) {
            return "";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm | dd/MM/yyyy");
        return dateTime.format(formatter);
    }

    public static String formatToCustomPattern(Date date) {
        if (date == null) {
            return "";
        }
        LocalDateTime localDateTime = toLocalDateTime(date);
        return formatToCustomPattern(localDateTime);
    }

}
