package com.khokhlov.filefilter.service;

import com.khokhlov.filefilter.model.DataType;
import com.khokhlov.filefilter.model.FilteredData;
import com.khokhlov.filefilter.service.statistics.StatisticsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

class FileProcessServiceTest {
    private FilePathService filePathService;
    private FileReaderService fileReaderService;
    private FileWriterService fileWriterService;
    private StatisticsService statisticsService;
    private DataFilterService dataFilterService;
    private FileProcessService fileProcessService;

    @BeforeEach
    void setUp() {
        filePathService = mock(FilePathService.class);
        fileReaderService = mock(FileReaderService.class);
        fileWriterService = mock(FileWriterService.class);
        statisticsService = mock(StatisticsService.class);
        dataFilterService = mock(DataFilterService.class);

        fileProcessService = new FileProcessService(filePathService, fileReaderService, fileWriterService,
                statisticsService, dataFilterService);
    }

    @Test
    void Should_NotProcess_When_FileDoesNotExist() throws IOException {
        String fileName = "nonexistent.txt";

        when(filePathService.checkFileExistence(fileName)).thenReturn(false);

        fileProcessService.processFile(fileName);

        verify(fileReaderService, never()).readFiles(anyString());
        verify(fileWriterService, never()).writeData(any(DataType.class), anyList());
        verify(statisticsService, never()).updateStatistics(anyList(), anyList(), anyList());
    }

    @Test
    void Should_LogError_When_FileIsEmpty() throws IOException {
        String fileName = "empty.txt";

        when(filePathService.checkFileExistence(fileName)).thenReturn(true);
        when(fileReaderService.readFiles(fileName)).thenReturn(Collections.emptyList());

        fileProcessService.processFile(fileName);

        verify(fileWriterService, never()).writeData(any(DataType.class), anyList());
        verify(statisticsService, never()).updateStatistics(anyList(), anyList(), anyList());
    }

    @Test
    void Should_ProcessFileCorrectly_When_FileHasData() throws IOException {
        String fileName = "data.txt";
        List<String> fileData = List.of("123", "45.67", "test string");

        when(filePathService.checkFileExistence(fileName)).thenReturn(true);
        when(fileReaderService.readFiles(fileName)).thenReturn(fileData);

        FilteredData filteredData = new FilteredData(List.of(123L), List.of(45.67d), List.of("test string"));
        when(dataFilterService.filterData(fileData)).thenReturn(filteredData);

        fileProcessService.processFile(fileName);

        verify(fileWriterService).writeData(DataType.INTEGER, List.of(123L));
        verify(fileWriterService).writeData(DataType.FLOAT, List.of(45.67d));
        verify(fileWriterService).writeData(DataType.STRING, List.of("test string"));
    }

    @Test
    void Should_UpdateStatistics_When_StatisticsEnabled() {
        when(statisticsService.isFullStatistics()).thenReturn(true);
        FileProcessService fileProcessService1 = new FileProcessService(filePathService, fileReaderService,
                fileWriterService, statisticsService, dataFilterService);
        String fileName = "data.txt";
        List<String> fileData = List.of("123", "45.67", "test string");

        when(filePathService.checkFileExistence(fileName)).thenReturn(true);
        when(fileReaderService.readFiles(fileName)).thenReturn(fileData);

        FilteredData filteredData = new FilteredData(List.of(123L), List.of(45.67d), List.of("test string"));
        when(dataFilterService.filterData(fileData)).thenReturn(filteredData);

        fileProcessService1.processFile(fileName);

        verify(statisticsService).updateStatistics(List.of(123L), List.of(45.67d), List.of("test string"));
    }

    @Test
    void Should_NotUpdateStatistics_When_StatisticsDisabled() {
        String fileName = "data.txt";
        List<String> fileData = List.of("123", "45.67", "test string");

        when(filePathService.checkFileExistence(fileName)).thenReturn(true);
        when(fileReaderService.readFiles(fileName)).thenReturn(fileData);

        FilteredData filteredData = new FilteredData(List.of(123L), List.of(45.67d), List.of("test string"));
        when(dataFilterService.filterData(fileData)).thenReturn(filteredData);

        when(statisticsService.isShortStatistics()).thenReturn(false);
        when(statisticsService.isFullStatistics()).thenReturn(false);

        fileProcessService.processFile(fileName);

        verify(statisticsService, never()).updateStatistics(anyList(), anyList(), anyList());
    }
}