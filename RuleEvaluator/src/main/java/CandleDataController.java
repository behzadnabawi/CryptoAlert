import java.io.IOException;
import java.util.*;

public class CandleDataController {
    private static Map<String, List<List<Object>>> candleData = new HashMap<>();
    private static CandleDataController INSTANCE = null;

    private CandleDataController() throws IOException {
        List<String> markets = Arrays.asList(new PropertiesReader("rule_evaluator_config.properties").readProperty("markets").split("/"));
        for (String market : markets) {
            candleData.put(market, new ArrayList<>());
        }
    }

    public static CandleDataController getInstance() throws IOException {
        if (INSTANCE == null) INSTANCE = new CandleDataController();
        return INSTANCE;
    }

    public void updateData(List<Object> data) {
        String market = (String) data.get(0); // Get market
        data.remove(0);
        candleData.get(market).add(data);
    }

    public void clearCandleData() {
        candleData.clear();
    }

    public static Map<String, List<List<Object>>> getCandleData() {
        return candleData;
    }

    public static List<Float> getPriceData(String priceType, String market) {
        List<List<Object>> candleDataOfMarket = candleData.get(market);
        List<Float> priceData = new ArrayList<>();
        switch (priceType) {
            case "high":
                for (int i = 0; i < candleDataOfMarket.size(); i++) {
                    priceData.add(Float.parseFloat(candleDataOfMarket.get(i).get(1).toString())); // Get high value
                }
                break;
            case "low":
                for (int i = 0; i < candleDataOfMarket.size(); i++) {
                    priceData.add(Float.parseFloat(candleDataOfMarket.get(i).get(2).toString())); // Get low value
                }
                break;
            case "open":
                for (int i = 0; i < candleDataOfMarket.size(); i++) {
                    priceData.add(Float.parseFloat(candleDataOfMarket.get(i).get(3).toString())); // Get open value
                }
                break;
            case "close":
                for (int i = 0; i < candleDataOfMarket.size(); i++) {
                    priceData.add(Float.parseFloat(candleDataOfMarket.get(i).get(4).toString())); // Get close value
                }
                break;
            default:
        }
        return priceData;
    }

}
