package ru.importsupport.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.importsupport.config.ConfigLoader;
import ru.importsupport.dto.MyTableDto;
import ru.importsupport.provider.Provider;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class ImportService {
    private final Logger log = LogManager.getLogger(ImportService.class);
    private final ConfigLoader configLoader;
    private final Provider provider;
    private Integer count = 0;

    public ImportService(ConfigLoader configLoader, Provider provider) {
        this.configLoader = configLoader;
        this.provider = provider;
    }

    public void addToFile() {
        try (BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(configLoader.getFilePathEnv() + configLoader.getFileName()))) {
            List<MyTableDto> entityList = provider.getAll();
            int INSERT_SIZE = configLoader.getInsertSize();
            String newDatabase = configLoader.getImportDbNameEnv();
            String newTable = configLoader.getImportDbTableNameEnv();
            for (MyTableDto rcsSetupDto : entityList) {
                if (count == INSERT_SIZE) {
                    bufferedOutputStream.write(";\n".getBytes(StandardCharsets.UTF_8));
                    log.info("close insert");
                    count = 0;
                }
                if (count == 0) {
                    String insert = ("INSERT INTO " + newDatabase + "." + newTable +
                            //TODO add columns name
                            " (id, user_id, channel_id, bot_id, data, date_created, date_updated, status) VALUES " + parseEntityToString(rcsSetupDto));
                    bufferedOutputStream.write(insert.getBytes(StandardCharsets.UTF_8));
                    log.info("add new insert {}", insert);
                } else {
                    String sql = parseEntityToString(rcsSetupDto);
                    bufferedOutputStream.write((", " + sql).getBytes(StandardCharsets.UTF_8));
                    log.info("add entity {}", sql);
                }
                count++;
            }
            bufferedOutputStream.write(";".getBytes(StandardCharsets.UTF_8));
            log.info("close insert");
        } catch (IOException ex) {
            log.error(ex);
            throw new RuntimeException(ex);
        }
    }

    private String escapeSql(String string) {
        string = string.replace("'", "\\'").replace("\"", "\\\"");
        return string;
    }

    /**
     * parse {@code entity} to string for insert
     * every string or time variables must be in ''
     * if variables is null, '' no need
     *
     * @param entity recorded entity
     * @return string for insert
     */
    private String parseEntityToString(MyTableDto entity) {
        Integer id = entity.getId();
        Integer userId = entity.getUserId();
        Integer channelId = entity.getChannelId();
        Integer botId = entity.getBotId();
        String data = entity.getData() == null ? null : String.format("'%s'", escapeSql(entity.getData()));
        String dateCreated = entity.getDateCreated() == null ? null : String.format("'%s'", escapeSql(entity.getDateCreated().toString()));
        String dateUpdated = entity.getDateUpdated() == null ? null : String.format("'%s'", escapeSql(entity.getDateUpdated().toString()));
        Integer status = entity.getStatus();
        String sql = String.format("(%d, %d, %d, %d, %s, %s, %s, %d)", id, userId, channelId, botId, data, dateCreated, dateUpdated, status);
        log.info("parse entity {} to sql {}", entity, sql);
        return sql;
    }

}
