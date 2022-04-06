package ru.importsupport.provider;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import ru.importsupport.config.ConfigLoader;
import ru.importsupport.config.HibernateConfig;
import ru.importsupport.dto.MyTableDto;

import java.sql.SQLException;
import java.util.List;

public class MyTableProvider implements Provider{
    private final Logger log = LogManager.getLogger(MyTableProvider.class);
    private final ConfigLoader configLoader;

    public MyTableProvider(ConfigLoader configLoader) {
        this.configLoader = configLoader;
    }

    //TODO each entity needs its own method
    public List<MyTableDto> getAll() {
        List<MyTableDto> myTableDtos;
        try (Session session = HibernateConfig.getSession()) {
            myTableDtos = session.createSQLQuery("select * FROM " + configLoader.getDbTableName())
                    //TODO add entity to .addEntity
                    .addEntity(MyTableDto.class).list();
            log.info("get entity list");
            return myTableDtos;
        } catch (SQLException ex) {
            log.error(ex);
            throw new RuntimeException(ex);
        }
    }
}
