import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesReader {
    private String configFileName;
    public PropertiesReader(String configFileName){
        this.configFileName = configFileName;
    }
    public String readProperty(String property) throws IOException {
        InputStream input = new FileInputStream("src/main/resources/" + configFileName);
        Properties prop = new Properties();
        prop.load(input);
        return prop.getProperty(property);
    }
}
