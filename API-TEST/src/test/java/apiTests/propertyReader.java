package apiTests;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class propertyReader {
    private static propertyReader instance;
    private Properties properties;

    public propertyReader(String filePath) {
        properties = new Properties();
        try (FileInputStream fileInputStream = new FileInputStream(filePath)) {
            properties.load(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static propertyReader getInstance() {
        if (instance == null) {
            instance = new propertyReader("src/test/test-data/config.properties");
        }
        return instance;
    }

    public String getValue(String key) {
        return properties.getProperty(key);
    }
    public static void main(String[] args) {
        propertyReader reader = propertyReader.getInstance();

        String base_url_authentication_controller = reader.getValue("base_url_authentication_controller");
        String authentication_controller_password = reader.getValue("authentication_controller.password");
        String authentication_controller_username = reader.getValue("authentication_controller.username");

    }

}
