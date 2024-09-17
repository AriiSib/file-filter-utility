package com.khokhlov.filefilter.service;

import com.khokhlov.filefilter.model.FloatStatistics;
import com.khokhlov.filefilter.model.IntegerStatistics;
import com.khokhlov.filefilter.model.StringStatistics;
import com.khokhlov.filefilter.service.statistics.StatisticsService;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StatisticsServiceTest {

    @Test
    void Should_UpdateStatisticsCorrectly_When_FullStatsIsEnabled() {
        List<Long> integers = List.of(1L, 2L, 3L);
        List<Double> floats = List.of(1.1d, 2.2d, 3.3d);
        List<String> strings = List.of("one", "two", "three");

        StatisticsService statisticsService = new StatisticsService(true, false);
        statisticsService.updateStatistics(integers, floats, strings);

        IntegerStatistics integerStats = new IntegerStatistics();
        integerStats.update(integers, true);

        assertEquals(3, integerStats.getCount());
        assertEquals(6, integerStats.getSum());
        assertEquals(1, integerStats.getMin());
        assertEquals(3, integerStats.getMax());
        assertEquals(2.0, integerStats.getAverage());


        FloatStatistics floatStats = new FloatStatistics();
        floatStats.update(floats, true);

        assertEquals(3, floatStats.getCount());
        assertEquals(6.6, floatStats.getSum(), 0.01);
        assertEquals(1.1d, floatStats.getMin(), 0.01);
        assertEquals(3.3d, floatStats.getMax(), 0.01);
        assertEquals(2.2d, floatStats.getAverage(), 0.01);


        StringStatistics stringStats = new StringStatistics();
        stringStats.update(strings, true);

        assertEquals(3, stringStats.getCount());
        assertEquals(3, stringStats.getShortestLength());
        assertEquals(5, stringStats.getLongestLength());
    }
}