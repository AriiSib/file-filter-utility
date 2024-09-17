package com.khokhlov.filefilter;

import com.khokhlov.filefilter.initializer.AppContextInitializer;
import com.khokhlov.filefilter.service.*;
import com.khokhlov.filefilter.utils.CommandLineArgumentsParser;

public class FileFilterApplication {

    public static void main(String[] args) {
        try {
            AppContextInitializer applicationContext = new AppContextInitializer();
            FileProcessService fileProcessService = applicationContext.initialize(args);

            for (String fileName : applicationContext.getInputFiles()) {
                fileProcessService.processFile(fileName);
            }

            applicationContext.getStatisticsPrinter().printStatistics();
        } catch (Exception e) {
            System.err.println("Critical error! " + e.getMessage());
            CommandLineArgumentsParser.printHelp();
        }
    }
}