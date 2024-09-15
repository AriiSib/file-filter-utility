package com.khokhlov.filefilter.service;

import com.khokhlov.filefilter.model.DataType;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class FileWriterService {
    private final FilePathService filePathService;
    private final String prefix;
    private final boolean appendMode;
    private final Map<DataType, Boolean> isFirstWriteMap = new EnumMap<>(DataType.class);
    private Path outputPath;

    public FileWriterService(FilePathService filePathService, boolean appendMode) {
        this.filePathService = filePathService;
        this.prefix = filePathService.getPrefix();
        this.appendMode = appendMode;

        for (DataType type : DataType.values()) {
            isFirstWriteMap.put(type, true);
        }
    }

    public <T> void writeData(DataType type, List<T> data) {
        try {
            outputPath = (outputPath == null ? filePathService.createPath() : outputPath);

            boolean isFirstWrite = isFirstWriteMap.get(type);
            boolean append = !isFirstWrite || appendMode;

            String absolutePath = filePathService.resolvePathFileName(outputPath, getFileName(type));

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(absolutePath, append))) {
                for (T item : data) {
                    writer.write(item.toString());
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            System.err.println("Error writing data to a file: " + getFileName(type) + " " + e.getMessage());
        }
        isFirstWriteMap.put(type, false);
    }

    private String getFileName(DataType type) {
        return prefix + type.toString().toLowerCase() + "s" + ".txt";
    }
}