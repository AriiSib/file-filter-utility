package com.khokhlov.filefilter.service;

import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileReaderServiceTest {
    private final FileReaderService fileReaderService = new FileReaderService();

    @Test
    void Should_ReturnListOfData_When_FileNonEmpty() throws Exception {
        Path tempFile = Files.createTempFile("test", ".txt");
        Files.write(tempFile, List.of("  line1  ", "line2", "", "123", "   ", "123.456", "!@#$%^&*<>,."));

        List<String> result = fileReaderService.readFiles(tempFile.toString());

        assertEquals(5, result.size());
        assertEquals("line1", result.get(0));
        assertEquals("line2", result.get(1));
        assertEquals("123", result.get(2));
        assertEquals("123.456", result.get(3));
        assertEquals("!@#$%^&*<>,.", result.get(4));

        Files.deleteIfExists(tempFile);
    }

    @Test
    void Should_ReturnEmptyList_When_FileIsEmpty() throws Exception {
        Path tempFile = Files.createTempFile("empty", ".txt");

        List<String> result = fileReaderService.readFiles(tempFile.toString());

        assertTrue(result.isEmpty());

        Files.deleteIfExists(tempFile);
    }

    @Test
    void Should_ReturnEmptyList_When_FileDoesNotExist() {
        String nonExistentFile = "non_existent_file.txt";

        List<String> result = fileReaderService.readFiles(nonExistentFile);

        assertTrue(result.isEmpty());
    }
}