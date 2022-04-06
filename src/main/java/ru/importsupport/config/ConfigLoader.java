package ru.importsupport.config;

import java.util.Properties;

public class ConfigLoader {
    private static final String DB_URL_ENV = "DB_URL";
    private static final String DB_USERNAME_ENV = "DB_USERNAME";
    private static final String DB_PASSWORD_ENV = "DB_PASSWORD";
    private static final String DB_TABLE_NAME_ENV = "DB_TABLE_NAME";
    private static final String IMPORT_DB_NAME_ENV = "IMPORT_DB_NAME";
    private static final String IMPORT_DB_TABLE_NAME_ENV = "IMPORT_DB_TABLE_NAME";
    private static final String FILE_NAME_ENV = "FILE_NAME";
    private static final String FILE_PATH_ENV = "FILE_PATH";
    private static final String INSERT_SIZE_ENV = "INSERT_SIZE";

    public Properties buildDbProperties() {
        Properties prop = new Properties();
        prop.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        prop.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
        prop.setProperty("hibernate.connection.url", System.getenv(DB_URL_ENV));
        prop.setProperty("hibernate.connection.username", System.getenv(DB_USERNAME_ENV));
        prop.setProperty("hibernate.connection.password", System.getenv(DB_PASSWORD_ENV));
        return prop;
    }

    public String getImportDbNameEnv() {
        return System.getenv(IMPORT_DB_NAME_ENV);
    }

    public String getImportDbTableNameEnv() {
        return System.getenv(IMPORT_DB_TABLE_NAME_ENV);
    }

    public String getDbTableName() {
        return System.getenv(DB_TABLE_NAME_ENV);
    }

    public String getFilePathEnv() {
        return System.getenv(FILE_PATH_ENV);
    }

    public String getFileName() {
        return System.getenv(FILE_NAME_ENV);
    }

    public Integer getInsertSize() {
        return Integer.parseInt(System.getenv(INSERT_SIZE_ENV));
    }
}
