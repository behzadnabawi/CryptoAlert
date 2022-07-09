import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


public class ProgramController {
    private boolean isRunning = true;
    private long intervalTime = Long.parseLong(new PropertiesReader("data_collector_config.properties").readProperty("interval_time"));
    private String topic;
    private Producer producer;

    public ProgramController() throws IOException {
        this.topic = new PropertiesReader("data_collector_config.properties").readProperty("topic");
        this.producer = new Producer(topic);
        ;
    }

    public void run() throws Exception {
        while (isRunning) {
            BinanceDataCollector binanceDataCollector = BinanceDataCollector.getInstance(); //Singleton class
            KafkaSerializer kafkaSerializer = KafkaSerializer.getInstance(); //Singleton class
            List<String> markets = Arrays.asList(new PropertiesReader("data_collector_config.properties").readProperty("markets").split("/"));
            for (String market : markets) {
                Map<String, String> binanceData = binanceDataCollector.getBinanceData(market);
                String value = kafkaSerializer.serialize(market, binanceData.get("high"), binanceData.get("low"), binanceData.get("open"),
                        binanceData.get("close"), binanceData.get("price"), binanceData.get("openTime"));
                //System.out.println(value);
                producer.send(market, value);
            }
            Thread.sleep(intervalTime);
        }
    }

    public void stop() {
        isRunning = false;
        producer.close();
    }
}
