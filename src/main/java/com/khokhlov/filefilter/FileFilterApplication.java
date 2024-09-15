package com.khokhlov.filefilter;

import com.khokhlov.filefilter.initializer.AppContextInitializer;
import com.khokhlov.filefilter.service.*;
import com.khokhlov.filefilter.utils.CommandLineArgumentsParser;

public class FileFilterApplication {

    public static void main(String[] args) {
        try {
            AppContextInitializer appContext = new AppContextInitializer();
            FileProcessService fileProcessService = appContext.initialize(args);

            for (String fileName : appContext.getInputFiles()) {
                fileProcessService.processFile(fileName);
            }

            appContext.getStatisticsService().printStatistics();
        } catch (Exception e) {
            System.err.println("Critical error! " + e.getMessage());
            CommandLineArgumentsParser.printHelp();
        }
    }
}