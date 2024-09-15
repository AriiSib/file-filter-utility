package com.khokhlov.filefilter.model;

import java.util.List;

public class FilteredData {
    private final List<Long> integers;
    private final List<Double> floats;
    private final List<String> strings;

    public FilteredData(List<Long> integers, List<Double> floats, List<String> strings) {
        this.integers = integers;
        this.floats = floats;
        this.strings = strings;
    }

    public List<Long> getIntegers() {
        return integers;
    }

    public List<Double> getFloats() {
        return floats;
    }

    public List<String> getStrings() {
        return strings;
    }
}