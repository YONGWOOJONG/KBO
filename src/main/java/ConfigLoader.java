import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigLoader {

    private static final String PROPERTIES_FILE = "database.properties";

    public static Properties loadProperties() throws IOException {
        Properties properties = new Properties();

        try (InputStream inputStream = new FileInputStream(PROPERTIES_FILE)) {
            properties.load(inputStream);
        } catch (IOException e) {
            throw new IOException("Failed to load properties file: " + e.getMessage());
        }

        return properties;
    }
}