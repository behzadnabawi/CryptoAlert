import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CandleData {
    private float high;
    private float low;
    private float open;
    private float close;
    private float price;
    private long openTime;
    private String market;
    private static List<CandleData> candleDataList = new ArrayList<>();
    public CandleData(float high, float low, float open, float close, float price, long openTime, String market){
        this.high = high;
        this.low = low;
        this.open = open;
        this.close = close;
        this.price = price;
        this.openTime = openTime;
        this.market = market;
        candleDataList.add(this);
    }

    public float getHigh() {
        return high;
    }

    public float getLow() {
        return low;
    }

    public float getOpen() {
        return open;
    }

    public float getClose() {
        return close;
    }

    public float getPrice() {
        return price;
    }

    public long getOpenTime() {
        return openTime;
    }

    public String getMarket() {
        return market;
    }
    public String serialize() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(this);
    }
    public static CandleData deserialize(String json) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, CandleData.class);
    }
    public static List<CandleData> getCandleDataList(){
        return candleDataList;
    }
    public static void clearCandleDataList(){
        candleDataList.clear();
    }
}
