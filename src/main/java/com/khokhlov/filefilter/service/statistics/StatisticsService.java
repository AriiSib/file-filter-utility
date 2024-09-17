package com.khokhlov.filefilter.service.statistics;

import com.khokhlov.filefilter.model.FloatStatistics;
import com.khokhlov.filefilter.model.IntegerStatistics;
import com.khokhlov.filefilter.model.StringStatistics;

import java.util.List;

public class StatisticsService {
    private final IntegerStatistics integerStatistics;
    private final FloatStatistics floatStatistics;
    private final StringStatistics stringStatistics;
    private final boolean fullStatistics;
    private final boolean shortStatistics;

    public StatisticsService(boolean fullStatistics, boolean shortStatistics) {
        this.fullStatistics = fullStatistics;
        this.shortStatistics = shortStatistics;
        this.integerStatistics = new IntegerStatistics();
        this.floatStatistics = new FloatStatistics();
        this.stringStatistics = new StringStatistics();
    }

    public void updateStatistics(List<Long> integers, List<Double> floats, List<String> strings) {
        if (!integers.isEmpty()) {
            integerStatistics.update(integers, fullStatistics);
        }
        if (!floats.isEmpty()) {
            floatStatistics.update(floats, fullStatistics);
        }
        if (!strings.isEmpty()) {
            stringStatistics.update(strings, fullStatistics);
        }
    }

    public IntegerStatistics getIntegerStatistics() {
        return integerStatistics;
    }

    public FloatStatistics getFloatStatistics() {
        return floatStatistics;
    }

    public StringStatistics getStringStatistics() {
        return stringStatistics;
    }

    public boolean isShortStatistics() {
        return shortStatistics;
    }

    public boolean isFullStatistics() {
        return fullStatistics;
    }
}