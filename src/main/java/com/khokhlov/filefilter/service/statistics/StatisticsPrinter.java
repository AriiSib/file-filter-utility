package com.khokhlov.filefilter.service.statistics;

import com.khokhlov.filefilter.consts.Consts;
import com.khokhlov.filefilter.model.FloatStatistics;
import com.khokhlov.filefilter.model.IntegerStatistics;
import com.khokhlov.filefilter.model.StringStatistics;

public class StatisticsPrinter {
    private final IntegerStatistics integerStatistics;
    private final FloatStatistics floatStatistics;
    private final StringStatistics stringStatistics;
    private final boolean shortStatistics;
    private final boolean fullStatistics;

    public StatisticsPrinter(StatisticsService statisticsService) {
        this.integerStatistics = statisticsService.getIntegerStatistics();
        this.floatStatistics = statisticsService.getFloatStatistics();
        this.stringStatistics = statisticsService.getStringStatistics();
        this.shortStatistics = statisticsService.isShortStatistics();
        this.fullStatistics = statisticsService.isFullStatistics();
    }

    public void printStatistics() {
        if (shortStatistics || fullStatistics) {
            if (integerStatistics.getCount() > 0) {
                printIntegerStats();
            }
            if (floatStatistics.getCount() > 0) {
                printFloatStats();
            }
            if (stringStatistics.getCount() > 0) {
                printStringStats();
            }
        }
    }

    private void printIntegerStats() {
        System.out.println(Consts.INTEGER_STATISTICS);
        System.out.println(Consts.AMOUNT + integerStatistics.getCount());
        if (fullStatistics) {
            System.out.println(Consts.MINIMUM + integerStatistics.getMin());
            System.out.println(Consts.MAXIMUM + integerStatistics.getMax());
            System.out.println(Consts.SUM + integerStatistics.getSum());
            System.out.println(Consts.AVERAGE + integerStatistics.getAverage());
        }
    }

    private void printFloatStats() {
        System.out.println(Consts.FLOAT_STATISTICS);
        System.out.println(Consts.AMOUNT + floatStatistics.getCount());
        if (fullStatistics) {
            System.out.println(Consts.MINIMUM + floatStatistics.getMin());
            System.out.println(Consts.MAXIMUM + floatStatistics.getMax());
            System.out.println(Consts.SUM + floatStatistics.getSum());
            System.out.println(Consts.AVERAGE + floatStatistics.getAverage());
        }
    }

    private void printStringStats() {
        System.out.println(Consts.STRING_STATISTICS);
        System.out.println(Consts.AMOUNT + stringStatistics.getCount());
        if (fullStatistics) {
            System.out.println(Consts.SHORTEST_STRING + stringStatistics.getShortestLength());
            System.out.println(Consts.LONGEST_STRING + stringStatistics.getLongestLength());
        }
    }
}