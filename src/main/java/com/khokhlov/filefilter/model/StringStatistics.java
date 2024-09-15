package com.khokhlov.filefilter.model;

import java.util.List;

public class StringStatistics {
    private int count = 0;
    private int shortestLength = Integer.MAX_VALUE;
    private int longestLength = 0;

    public void update(List<String> strings, boolean fullStats) {
        count += strings.size();
        if (fullStats) {
            for (String str : strings) {
                int length = str.length();
                shortestLength = Math.min(shortestLength, length);
                longestLength = Math.max(longestLength, length);
            }
        }
    }

    public int getCount() {
        return count;
    }

    public int getShortestLength() {
        return shortestLength;
    }

    public int getLongestLength() {
        return longestLength;
    }
}