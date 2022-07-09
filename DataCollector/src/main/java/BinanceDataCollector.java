import com.binance.api.client.BinanceApiClientFactory;
import com.binance.api.client.BinanceApiRestClient;
import com.binance.api.client.domain.market.Candlestick;
import com.binance.api.client.domain.market.CandlestickInterval;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BinanceDataCollector {
    private static BinanceDataCollector INSTANCE = null;
    private BinanceApiClientFactory factory;
    private BinanceApiRestClient client;

    private BinanceDataCollector() {
        factory = BinanceApiClientFactory.newInstance();
        client = factory.newRestClient();
    }

    public static BinanceDataCollector getInstance() {
        if (INSTANCE == null) INSTANCE = new BinanceDataCollector();
        return INSTANCE;
    }

    public Map<String, String> getBinanceData(String market) throws IOException {
        Map<String, String> binanceData = new HashMap<>();
        List<Candlestick> candlestickBars = client.getCandlestickBars(market, CandlestickInterval.ONE_MINUTE);
        Candlestick candlestick = candlestickBars.get(candlestickBars.size() - 1); // Get the latest candlestick of a market
        binanceData.put("high", candlestick.getHigh());
        binanceData.put("low", candlestick.getLow());
        binanceData.put("open", candlestick.getOpen());
        binanceData.put("close", candlestick.getClose());
        binanceData.put("price", client.get24HrPriceStatistics(market).getLastPrice());
        binanceData.put("openTime", candlestick.getOpenTime().toString());
        return binanceData;
    }
}
