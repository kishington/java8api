package ua.com.foxminded.racing.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class DataPreparerTest {

    private static final String ABBREVIATIONS_DATA_PATH = "/abbreviationsTest.txt";
    private static final String START_DATA_PATH = "/startTest.log";
    private static final String END_DATA_PATH = "/endTest.log";

    private static final String EMPTY_FILE_PATH = "/empty.txt";
    private static final String INVALID_ABBREVIATIONS_DATA_FORMAT_PATH = "/abbreviations_invalidFormat.txt";
    private static final String INVALID_START_DATA_FORMAT_PATH = "/start_invalidFormat.log";
    private static final String INVALID_END_DATA_FORMAT_PATH = "/end_invalidFormat.log";

    private static DataPreparer dataPreparer;

    @BeforeAll
    public static void initialise() {
        dataPreparer = new DataPreparer();
    }

    @Test
    void testEmptyAbbreviationsFile_exceptoinThrown() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            dataPreparer.prepareData(EMPTY_FILE_PATH, START_DATA_PATH, END_DATA_PATH);
        });
        assertEquals("There should be valid abbreviation data for at least one racer", exception.getMessage());
    }

    @Test
    void testEmptyStartTimesFile_exceptoinThrown() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            dataPreparer.prepareData(ABBREVIATIONS_DATA_PATH, EMPTY_FILE_PATH, END_DATA_PATH);
        });
        assertEquals("Missing strart time data for at least one racer", exception.getMessage());
    }

    @Test
    void testEmptyEndTimesFile_exceptoinThrown() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            dataPreparer.prepareData(ABBREVIATIONS_DATA_PATH, START_DATA_PATH, EMPTY_FILE_PATH);
        });
        assertEquals("Missing end time data for at least one racer", exception.getMessage());
    }

    @Test
    void testInvalidAbbreviationsFormat_exceptoinThrown() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            dataPreparer.prepareData(INVALID_ABBREVIATIONS_DATA_FORMAT_PATH, START_DATA_PATH, END_DATA_PATH);
        });
        assertEquals("Invalid format in abbreviations data", exception.getMessage());
    }

    @Test
    void testInvalidStartTimesFormat_exceptoinThrown() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            dataPreparer.prepareData(ABBREVIATIONS_DATA_PATH, INVALID_START_DATA_FORMAT_PATH, END_DATA_PATH);
        });
        assertEquals("Invalid format of start times data", exception.getMessage());
    }

    @Test
    void testInvalidEndTimesFormat_exceptoinThrown() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            dataPreparer.prepareData(ABBREVIATIONS_DATA_PATH, START_DATA_PATH, INVALID_END_DATA_FORMAT_PATH);
        });
        assertEquals("Invalid format of end times data", exception.getMessage());
    }
}
