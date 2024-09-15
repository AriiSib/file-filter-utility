package com.khokhlov.filefilter.service;

import com.khokhlov.filefilter.model.FilteredData;

import java.util.*;

public class DataFilterService {

    public FilteredData filterData(List<String> lines) {
        List<Long> integers = new ArrayList<>();
        List<Double> floats = new ArrayList<>();
        List<String> strings = new ArrayList<>();

        for (String line : lines) {
            line = line.trim();
            if (line.isBlank()) {
                continue;
            }

            try {
                long integerValue = Long.parseLong(line);
                integers.add(integerValue);
            } catch (NumberFormatException e) {
                try {
                    double floatValue = Double.parseDouble(line);
                    floats.add(floatValue);
                } catch (NumberFormatException e1) {
                    strings.add(line);
                }
            }
        }
        return new FilteredData(integers, floats, strings);
    }
}