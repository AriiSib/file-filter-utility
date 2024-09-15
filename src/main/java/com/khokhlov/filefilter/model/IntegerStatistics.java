package com.khokhlov.filefilter.model;

import java.util.List;

public class IntegerStatistics {
    private int count = 0;
    private long sum = 0;
    private long min = Long.MAX_VALUE;
    private long max = Long.MIN_VALUE;

    public void update(List<Long> integers, boolean fullStats) {
        count += integers.size();
        if (fullStats) {
            for (long value : integers) {
                sum += value;
                min = Math.min(min, value);
                max = Math.max(max, value);
            }
        }
    }

    public int getCount() {
        return count;
    }

    public long getSum() {
        return sum;
    }

    public long getMin() {
        return min;
    }

    public long getMax() {
        return max;
    }

    public double getAverage() {
        return count == 0 ? 0 : (double) sum / count;
    }
}