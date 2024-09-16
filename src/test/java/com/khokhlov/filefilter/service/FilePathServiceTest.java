package com.khokhlov.filefilter.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FilePathServiceTest {

    @Test
    void Should_ConvertRelativePathToAbsolute_When_CreatePathCalled() throws IOException {
        FilePathService relativePathService = new FilePathService("relativePath", "prefix");

        Path absolutePath = relativePathService.createPath();

        assertTrue(absolutePath.isAbsolute());
        Files.deleteIfExists(absolutePath);
    }

    @Test
    void Should_ThrowException_When_InvalidFilePath() {
        FilePathService filePathServiceMock = Mockito.mock(FilePathService.class);

        String invalidPath = "invalidPath";

        when(filePathServiceMock.validateFileOutputPath(invalidPath))
                .thenThrow(new InvalidPathException(invalidPath, "Invalid path for result files"));

        Exception exception = assertThrows(InvalidPathException.class, () -> {
            filePathServiceMock.validateFileOutputPath(invalidPath);
        });

        assertTrue(exception.getMessage().contains("Invalid path for result files"));
    }

    @Test
    void Should_ResolvePathCorrectly_When_ValidFileNameGiven() {
        FilePathService filePathService = new FilePathService(".", "prefix");
        Path tempPath = Paths.get("temp");
        String fileName = "test.txt";

        String resolvedPath = filePathService.resolvePathFileName(tempPath, fileName);

        assertEquals(tempPath.resolve(fileName).toString(), resolvedPath);
    }

    @Test
    void Should_ThrowInvalidPathException_When_InvalidFileNameGiven() {
        FilePathService filePathServiceMock = Mockito.mock(FilePathService.class);
        Path tempPath = Paths.get("temp");
        String invalidFileName = "invalidFile.txt";

        when(filePathServiceMock.resolvePathFileName(tempPath, invalidFileName))
                .thenThrow(new InvalidPathException(invalidFileName, "Failed to create path for output file"));

        Exception exception = assertThrows(InvalidPathException.class, () -> {
            filePathServiceMock.resolvePathFileName(tempPath, invalidFileName);
        });

        assertTrue(exception.getMessage().contains("Failed to create path for output file"));
    }

    @Test
    void Should_ReturnTrue_When_FileExistsAndIsReadable() throws IOException {
        Path tempFile = Files.createTempFile("testFile", ".txt");
        FilePathService filePathService = new FilePathService(tempFile.getParent().toString(), "prefix");

        boolean result = filePathService.checkFileExistence(tempFile.toString());

        assertTrue(result);

        Files.deleteIfExists(tempFile);
    }

    @Test
    void Should_ReturnFalse_When_FileDoesNotExist() {
        FilePathService filePathService = new FilePathService(".", "prefix");
        boolean result = filePathService.checkFileExistence("nonexistentfile.txt");

        assertFalse(result);
    }

    @Test
    void Should_ReturnDefaultPrefix_When_InvalidCharactersInPrefix() {
        FilePathService filePathService = new FilePathService(".", "invalid|?/prefix");
        String prefix = filePathService.getPrefix();

        assertEquals("", prefix);
    }
}