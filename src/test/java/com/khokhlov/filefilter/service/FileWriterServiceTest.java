package com.khokhlov.filefilter.service;

import com.khokhlov.filefilter.model.DataType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FileWriterServiceTest {

    private FilePathService filePathService;
    private FileWriterService fileWriterService;
    private Path tempDir;

    @BeforeEach
    void setUp() throws IOException {
        tempDir = Files.createTempDirectory("testDir");
        filePathService = mock(FilePathService.class);
        when(filePathService.getPrefix()).thenReturn("test_");
        when(filePathService.createPath()).thenReturn(tempDir);
        when(filePathService.resolvePathFileName(any(), anyString())).thenAnswer(
                invocation -> tempDir.resolve((String) invocation.getArgument(1)).toString()
        );

        fileWriterService = new FileWriterService(filePathService, false);
    }

    @Test
    void Should_CreateNewFile_When_FirstWriteAndAppendModeIsFalse() throws IOException {
        List<String> data = List.of("line1", "line2");
        fileWriterService.writeData(DataType.STRING, data);

        Path expectedFile = tempDir.resolve("test_strings.txt");

        assertTrue(Files.exists(expectedFile));

        try (BufferedReader reader = new BufferedReader(new FileReader(expectedFile.toFile()))) {
            assertEquals("line1", reader.readLine());
            assertEquals("line2", reader.readLine());
        }
    }

    @Test
    void Should_AppendToFile_When_FileAlreadyExistsAndAppendModeIsTrue() throws IOException {
        fileWriterService = new FileWriterService(filePathService, true);

        Path expectedFile = tempDir.resolve("test_strings.txt");
        Files.write(expectedFile, List.of("existing line"));

        List<String> data = List.of("new line1", "new line2");
        fileWriterService.writeData(DataType.STRING, data);

        assertTrue(Files.exists(expectedFile));

        try (BufferedReader reader = new BufferedReader(new FileReader(expectedFile.toFile()))) {
            assertEquals("existing line", reader.readLine());
            assertEquals("new line1", reader.readLine());
            assertEquals("new line2", reader.readLine());
        }
    }
}