import java.util.ArrayList;
import java.util.List;

public class KafkaDeserializer {
    private static KafkaDeserializer INSTANCE = null;

    private KafkaDeserializer() {
    }

    public static KafkaDeserializer getInstance() {
        if (INSTANCE == null) INSTANCE = new KafkaDeserializer();
        return INSTANCE;
    }

    public List<Object> deserialize(String value) {
        List<Object> result = new ArrayList<>();
        String[] info = value.split(",");
        result.add(info[0].split(":")[1]); // Add market
        result.add(Float.parseFloat(info[1].split(":")[1])); // Add high
        result.add(Float.parseFloat(info[2].split(":")[1])); // Add low
        result.add(Float.parseFloat(info[3].split(":")[1])); // Add open
        result.add(Float.parseFloat(info[4].split(":")[1])); // Add close
        result.add(Float.parseFloat(info[5].split(":")[1])); // Add price
        result.add(Long.parseLong(info[6].split(":")[1])); // Add openTime
        return result;
    }
}
