package com.khokhlov.filefilter.service;

import com.khokhlov.filefilter.model.FloatStatistics;
import com.khokhlov.filefilter.model.IntegerStatistics;
import com.khokhlov.filefilter.model.StringStatistics;

import java.util.List;

public class StatisticsService {
    private final IntegerStatistics integerStats;
    private final FloatStatistics floatStats;
    private final StringStatistics stringStats;
    private final boolean fullStats;
    private final boolean shortStats;

    public StatisticsService(boolean fullStats, boolean shortStats) {
        this.fullStats = fullStats;
        this.shortStats = shortStats;
        this.integerStats = new IntegerStatistics();
        this.floatStats = new FloatStatistics();
        this.stringStats = new StringStatistics();
    }

    public void updateStatistics(List<Long> integers, List<Double> floats, List<String> strings) {
        if (!integers.isEmpty()) {
            integerStats.update(integers, fullStats);
        }
        if (!floats.isEmpty()) {
            floatStats.update(floats, fullStats);
        }
        if (!strings.isEmpty()) {
            stringStats.update(strings, fullStats);
        }
    }

    public void printStatistics() {
        if (shortStats || fullStats) {
            if (integerStats.getCount() > 0) {
                printIntegerStats();
            }
            if (floatStats.getCount() > 0) {
                printFloatStats();
            }
            if (stringStats.getCount() > 0) {
                printStringStats();
            }
        }
    }

    public boolean isShortStats() {
        return shortStats;
    }

    public boolean isFullStats() {
        return fullStats;
    }

    private void printIntegerStats() {
        System.out.println("\nStatistics for integers:");
        System.out.println("Amount: " + integerStats.getCount());
        if (fullStats) {
            System.out.println("Minimum: " + integerStats.getMin());
            System.out.println("Maximum: " + integerStats.getMax());
            System.out.println("Sum: " + integerStats.getSum());
            System.out.println("Average: " + integerStats.getAverage());
        }
    }

    private void printFloatStats() {
        System.out.println("\nStatistics for floats:");
        System.out.println("Amount: " + floatStats.getCount());
        if (fullStats) {
            System.out.println("Minimum: " + floatStats.getMin());
            System.out.println("Maximum: " + floatStats.getMax());
            System.out.println("Sum: " + floatStats.getSum());
            System.out.println("Average: " + floatStats.getAverage());
        }
    }

    private void printStringStats() {
        System.out.println("\nStatistics for strings:");
        System.out.println("Amount: " + stringStats.getCount());
        if (fullStats) {
            System.out.println("Length of shortest string: " + stringStats.getShortestLength());
            System.out.println("Length of longest string: " + stringStats.getLongestLength());
        }
    }
}