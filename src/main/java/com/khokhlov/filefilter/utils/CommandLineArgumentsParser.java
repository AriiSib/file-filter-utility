package com.khokhlov.filefilter.utils;

import org.apache.commons.cli.*;

import java.util.Arrays;
import java.util.List;

public class CommandLineArgumentsParser {
    private final String outputPath;
    private final String filePrefix;
    private final boolean appendMode;
    private final boolean fullStatsMode;
    private final boolean shortStatsMode;
    private final List<String> inputFiles;

    public CommandLineArgumentsParser(String[] args) throws ParseException {
        Options options = new Options();

        options.addOption("a", "append", false, "Append to existing files instead of overwriting");
        options.addOption("s", "short-stats", false, "Show short statistics");
        options.addOption("f", "full-stats", false, "Show full statistics");
        options.addOption("o", "output", true, "Output directory path");
        options.addOption("p", "prefix", true, "Prefix for output file names");

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = null;
        try {
            cmd = parser.parse(options, getRecognizedArguments(args, options));
        } catch (ParseException e) {
            throw new ParseException(e.getMessage());
        }

        this.appendMode = cmd.hasOption("a");
        this.shortStatsMode = cmd.hasOption("s");
        this.fullStatsMode = cmd.hasOption("f");
        this.outputPath = cmd.getOptionValue("o", "");
        this.filePrefix = cmd.getOptionValue("p", "");
        this.inputFiles = cmd.getArgList();

        if (inputFiles.isEmpty()) {
            throw new ParseException("No input files specified");
        }
    }

    public boolean isAppendMode() {
        return appendMode;
    }

    public boolean isShortStatsMode() {
        return shortStatsMode;
    }

    public boolean isFullStatsMode() {
        return fullStatsMode;
    }

    public String getOutputPath() {
        return outputPath;
    }

    public String getFilePrefix() {
        return filePrefix;
    }

    public List<String> getInputFiles() {
        return inputFiles;
    }

    public static void printHelp() {
        HelpFormatter formatter = new HelpFormatter();
        Options options = new Options();

        options.addOption("a", "append", false, "Append to existing files instead of overwriting");
        options.addOption("s", "short-stats", false, "Show short statistics");
        options.addOption("f", "full-stats", false, "Show full statistics");
        options.addOption("o", "output", true, "Output directory path");
        options.addOption("p", "prefix", true, "Prefix for output file names");

        formatter.printHelp("FileFilter", options);
    }

    private String[] getRecognizedArguments(String[] args, Options options) {
        List<String> unrecognizedOptions = Arrays.stream(args)
                .filter(arg -> arg.startsWith("-") && !options.hasOption(arg.replace("-", "")))
                .toList();

        String[] recognizedArgs = Arrays.stream(args)
                .filter(arg -> !unrecognizedOptions.contains(arg))
                .toArray(String[]::new);

        if (!unrecognizedOptions.isEmpty()) {
            System.err.println("Warning! Unknown flags detected: " + String.join(", ", unrecognizedOptions));
        }
        return recognizedArgs;
    }
}