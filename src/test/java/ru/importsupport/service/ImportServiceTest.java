package ru.importsupport.service;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import ru.importsupport.config.ConfigLoader;
import ru.importsupport.dto.MyTableDto;
import ru.importsupport.provider.Provider;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class ImportServiceTest {
    @Mock
    private Provider provider;
    @Mock
    private ConfigLoader configLoader;
    @InjectMocks
    private ImportService importService;

    private final MyTableDto conn = new MyTableDto(1, 1, 1, 1, "data1", Timestamp.valueOf(LocalDateTime.MAX), Timestamp.valueOf(LocalDateTime.MAX), 1);
    private final MyTableDto conn1 = new MyTableDto(2, 2, 2, 2, "data2", Timestamp.valueOf(LocalDateTime.MIN), Timestamp.valueOf(LocalDateTime.MIN), 2);
    private final MyTableDto conn2 = new MyTableDto(3, 3, 3, 3, "data3", null, null, 3);
    private final String expectedResult = "INSERT INTO dbname.dbtable (id, user_id, channel_id, bot_id, data, date_created, date_updated, status) VALUES (1, 1, 1, 1, 'data1', '169104628-12-10 19:08:15.999999999', '169104628-12-10 19:08:15.999999999', 1), " +
            "(2, 2, 2, 2, 'data2', '169087565-03-15 04:51:43.0', '169087565-03-15 04:51:43.0', 2), (3, 3, 3, 3, 'data3', null, null, 3);";

    @Test
    public void addToFileWhenAllValid() {
        provider = Mockito.mock(Provider.class);
        configLoader = Mockito.mock(ConfigLoader.class);
        importService = new ImportService(configLoader, provider);
        when(provider.getAll()).thenReturn(Arrays.asList(conn, conn1, conn2));
        when(configLoader.getInsertSize()).thenReturn(3);
        when(configLoader.getImportDbNameEnv()).thenReturn("dbname");
        when(configLoader.getImportDbTableNameEnv()).thenReturn("dbtable");
        when(configLoader.getFileName()).thenReturn("TestFileName.sql");
        when(configLoader.getFilePathEnv()).thenReturn("");
        importService.addToFile();
        try (BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream("TestFileName.sql"))) {
            String result = new String(bufferedInputStream.readAllBytes());
            assertEquals(expectedResult, result);
        } catch (IOException e) {
            assertEquals("", e.getMessage());
        }
    }
}