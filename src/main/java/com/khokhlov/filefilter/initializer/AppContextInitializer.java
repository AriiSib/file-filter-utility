package com.khokhlov.filefilter.initializer;

import com.khokhlov.filefilter.service.*;
import com.khokhlov.filefilter.utils.CommandLineArgumentsParser;

import java.util.List;

public class AppContextInitializer {
    private StatisticsService statisticsService;
    private List<String> inputFiles;


    public FileProcessService initialize(String[] args) throws Exception {
        CommandLineArgumentsParser commandLineParser = new CommandLineArgumentsParser(args);

        String outputPath = commandLineParser.getOutputPath();
        String filePrefix = commandLineParser.getFilePrefix();
        boolean appendMode = commandLineParser.isAppendMode();
        boolean fullStatsMode = commandLineParser.isFullStatsMode();
        boolean shortStatsMode = commandLineParser.isShortStatsMode();
        inputFiles = commandLineParser.getInputFiles();

        this.statisticsService = new StatisticsService(fullStatsMode, shortStatsMode);
        FilePathService filePathService = new FilePathService(outputPath, filePrefix);
        FileReaderService fileReaderService = new FileReaderService();
        FileWriterService fileWriterService = new FileWriterService(filePathService, appendMode);
        DataFilterService dataFilterService = new DataFilterService();

        return new FileProcessService(filePathService,
                                    fileReaderService,
                                    fileWriterService,
                                    statisticsService,
                                    dataFilterService);
    }

    public StatisticsService getStatisticsService() {
        return statisticsService;
    }

    public List<String> getInputFiles() {
        return inputFiles;
    }
}