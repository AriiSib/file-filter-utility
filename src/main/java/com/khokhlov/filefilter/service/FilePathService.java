package com.khokhlov.filefilter.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Pattern;

public class FilePathService {
    private static final String INVALID_CHARACTERS_REGEX = "[\\\\/:*?\"<>|]";

    private final String prefix;
    private Path outputPath;

    public FilePathService(String filePath, String filePrefix) {
        this.prefix = validateFilePrefix(filePrefix);
        this.outputPath = validateFileOutputPath(filePath);
    }

    public Path createPath() throws IOException {
        try {
            if (!outputPath.isAbsolute()) {
                String rawPath = outputPath.toString();
                if (rawPath.startsWith("/") || rawPath.startsWith("\\")) {
                    rawPath = rawPath.substring(1);
                }
                outputPath = Paths.get("").toAbsolutePath().resolve(rawPath);
            }
            createDirectoryIfNotExist(outputPath);
        } catch (InvalidPathException e) {
            throw new InvalidPathException(outputPath.toString(), "Cannot create a path");
        }

        return outputPath;
    }

    public void createDirectoryIfNotExist(Path path) throws IOException {
        try {
            if (Files.notExists(outputPath)) {
                Files.createDirectories(outputPath);
            }
        } catch (IOException e) {
            throw new IOException("Failed to create a directory at the specified path " + path);
        }
    }

    public String resolvePathFileName(Path filePath, String fileName) {
        try {
            return filePath.resolve(fileName).toString();
        } catch (InvalidPathException e) {
            throw new InvalidPathException(fileName, "Failed to create path for output file");
        }
    }

    public Path validateFileOutputPath(String filePath) {
        try {
            return Paths.get(filePath);
        } catch (InvalidPathException e) {
            throw new InvalidPathException(filePath, "Invalid path for result files");
        }
    }

    public boolean checkFileExistence(String fileName) {
        Path filePath = Paths.get(fileName);
        if (Files.exists(filePath)) {
            if (Files.isReadable(filePath)) {
                return true;
            } else {
                System.err.println("No permission to read the file: " + fileName);
            }
        } else {
            System.err.println("Warning! File not found: " + fileName);
        }
        return false;
    }

    public String validateFilePrefix(String filePrefix) {
        Pattern pattern = Pattern.compile(INVALID_CHARACTERS_REGEX);
        filePrefix = filePrefix.trim();
        if (pattern.matcher(filePrefix).find()) {
            System.err.println("Warning! Invalid prefix format: " + filePrefix);
            System.err.println("Results will be recorded with default names!");
            filePrefix = "";
        }
        return filePrefix;
    }

    public String getPrefix() {
        return prefix;
    }
}