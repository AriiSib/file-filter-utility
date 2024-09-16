package com.khokhlov.filefilter.service;

import com.khokhlov.filefilter.model.DataType;
import com.khokhlov.filefilter.model.FilteredData;

import java.io.IOException;
import java.util.List;

public class FileProcessService {
    private final FilePathService filePathService;
    private final FileReaderService fileReaderService;
    private final FileWriterService fileWriterService;
    private final StatisticsService statisticsService;
    private final DataFilterService dataFilterService;

    private final boolean shortStats;
    private final boolean fullStats;

    public FileProcessService(FilePathService filePathService,
                              FileReaderService fileReaderService,
                              FileWriterService fileWriterService,
                              StatisticsService statisticsService,
                              DataFilterService dataFilterService) {

        this.filePathService = filePathService;
        this.fileReaderService = fileReaderService;
        this.fileWriterService = fileWriterService;
        this.dataFilterService = dataFilterService;
        this.statisticsService = statisticsService;
        this.shortStats = statisticsService.isShortStats();
        this.fullStats = statisticsService.isFullStats();
    }

    public void processFile(String fileName) {
        if (!filePathService.checkFileExistence(fileName)) {
            return;
        }

        List<String> lines = fileReaderService.readFiles(fileName);

        if (lines.isEmpty()) {
            System.err.println(fileName + " - empty or unreadable");
            return;
        }

        FilteredData data = dataFilterService.filterData(lines);

        try {
            writeDataToFile(data);
        } catch (IOException e) {
            return;
        }

        updateStatistics(data);
    }

    private void updateStatistics(FilteredData data) {
        if (shortStats || fullStats) {
            statisticsService.updateStatistics(data.getIntegers(), data.getFloats(), data.getStrings());
        }
    }

    private void writeDataToFile(FilteredData data) throws IOException {
        if (!data.getIntegers().isEmpty()) {
            fileWriterService.writeData(DataType.INTEGER, data.getIntegers());
        }
        if (!data.getFloats().isEmpty()) {
            fileWriterService.writeData(DataType.FLOAT, data.getFloats());
        }
        if (!data.getStrings().isEmpty()) {
            fileWriterService.writeData(DataType.STRING, data.getStrings());
        }
    }
}