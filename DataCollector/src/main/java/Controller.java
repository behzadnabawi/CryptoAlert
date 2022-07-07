import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


public class Controller {
    private boolean isRunning = true;
    private long intervalTime = Long.parseLong(new PropertiesReader("config.properties").readProperty("interval_time"));
    private String topic;
    private Producer producer;
    public Controller() throws IOException {
        this.topic = new PropertiesReader("config.properties").readProperty("topic");
        this.producer = new Producer(topic);;
    }
    public void createCandleDataFromBinanceData() throws IOException {
        BinanceDataCollector binanceDataCollector = BinanceDataCollector.getInstance(); //Singleton class
        List<String> markets = Arrays.asList(new PropertiesReader("config.properties").readProperty("markets").split("/"));
        for (String market:markets) {
            Map<String, String> binanceData = binanceDataCollector.getBinanceData(market);
            new CandleData(Float.parseFloat(binanceData.get("high")), Float.parseFloat(binanceData.get("low")), Float.parseFloat(binanceData.get("open")),
                    Float.parseFloat(binanceData.get("close")), Float.parseFloat(binanceData.get("price")), Long.parseLong(binanceData.get("openTime")), market);
        }
    }
    public void run() throws Exception {
        while(isRunning){
            createCandleDataFromBinanceData();
            for (CandleData candleData : CandleData.getCandleDataList()) {
                System.out.println(candleData.serialize());
                producer.send(candleData.getMarket(), candleData.serialize());
            }
            System.out.println("DONE.");
            CandleData.clearCandleDataList(); // Clear data from the last minute
            Thread.sleep(intervalTime);
        }
    }
    public void stop() {
        isRunning = false;
        producer.close();
    }
}
