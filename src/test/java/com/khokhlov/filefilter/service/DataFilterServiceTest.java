package com.khokhlov.filefilter.service;

import com.khokhlov.filefilter.model.FilteredData;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DataFilterServiceTest {
    private final DataFilterService dataFilterService = new DataFilterService();

    @Test
    void Should_FilterData_WhenLinesContainMixedData() {
        List<String> lines = List.of(" 123 ", " 1234567890123456789 ", "!@#$%^&*<>", " 0 ", " -99 ", " 3.14 ", "string");

        FilteredData filteredData = dataFilterService.filterData(lines);

        List<Long> expectedIntegers = List.of(123L, 1234567890123456789L, 0L, -99L);
        List<Double> expectedFloats = List.of(3.14d);
        List<String> expectedStrings = List.of("!@#$%^&*<>", "string");

        assertEquals(expectedIntegers, filteredData.getIntegers());
        assertEquals(expectedFloats, filteredData.getFloats());
        assertEquals(expectedStrings, filteredData.getStrings());
    }

    @Test
    void Should_FilterEmptyLines_When_LinesContainEmptyStrings() {
        List<String> lines = Arrays.asList("", "   ", "123", "string", "  ");

        FilteredData result = dataFilterService.filterData(lines);

        assertEquals(List.of(123L), result.getIntegers());
        assertEquals(List.of(), result.getFloats());
        assertEquals(List.of("string"), result.getStrings());
    }
}