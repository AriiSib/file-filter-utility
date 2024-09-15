package com.khokhlov.filefilter.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public class FileReaderService {

    public List<String> readFiles(String fileName) {
        try (Stream<String> lines = Files.lines(Paths.get(fileName))) {
            return lines
                    .filter(line -> !line.isBlank())
                    .map(String::trim)
                    .toList();
        } catch (IOException e) {
            System.err.println("File read error: " + fileName);
        }
        return Collections.emptyList();
    }
}