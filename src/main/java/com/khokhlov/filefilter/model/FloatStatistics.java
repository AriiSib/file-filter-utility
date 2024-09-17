package com.khokhlov.filefilter.model;

import java.util.List;

public class FloatStatistics {
    private int count = 0;
    private double sum = 0;
    private double min = Double.MAX_VALUE;
    private double max = Double.MIN_VALUE;

    public void update(List<Double> floats, boolean fullStatistics) {
        count += floats.size();
        if (fullStatistics) {
            for (double value : floats) {
                sum += value;
                min = Math.min(min, value);
                max = Math.max(max, value);
            }
        }
    }

    public int getCount() {
        return count;
    }

    public double getSum() {
        return sum;
    }

    public double getMin() {
        return min;
    }

    public double getMax() {
        return max;
    }

    public double getAverage() {
        return count == 0 ? 0 : sum / count;
    }
}