package ru.importsupport;

import ru.importsupport.config.ConfigLoader;
import ru.importsupport.config.HibernateConfig;
import ru.importsupport.provider.MyTableProvider;
import ru.importsupport.service.ImportService;

public class ImportSupport {
    public static void main(String[] args) {
        ConfigLoader configLoader = new ConfigLoader();
        HibernateConfig.init(configLoader);
        ImportService importService = new ImportService(configLoader, new MyTableProvider(configLoader));
        importService.addToFile();
    }
}
