import com.binance.api.client.BinanceApiClientFactory;
import com.binance.api.client.BinanceApiRestClient;
import com.binance.api.client.domain.market.Candlestick;
import com.binance.api.client.domain.market.CandlestickInterval;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class DataScanner {
    private HashMap<String,Candlestick> candlesticks = new HashMap<String,Candlestick>();
    public DataScanner(){

    }
    public void run(){
        /*
        String currentTime = String.valueOf(System.currentTimeMillis() - 1000 * 60 * 60);
        String sURL = "https://api.binance.com/api/v3/klines?symbol=BNBBTC&interval=1m&startTime=" + currentTime;
        System.out.println(getJsonElement(sURL));
        */
        BinanceApiClientFactory factory = BinanceApiClientFactory.newInstance();
        BinanceApiRestClient client = factory.newRestClient();
        long serverTime = client.getServerTime();
        //System.out.println(client.getExchangeInfo().getSymbols());
        List<String> markets = Arrays.asList(new String[]{"BTC", "LTC", "ETH"});
        for (String market : markets) {
            Candlestick candlestick = client.getCandlestickBars("NEOETH", CandlestickInterval.ONE_MINUTE, 1,serverTime - 1000*60, serverTime).get(0);
            candlesticks.put(market, candlestick);
        }
    }
    /*
    private static JsonElement getJsonElement(String sURL) throws IOException {
        URL url = new URL(sURL);
        URLConnection request = url.openConnection();
        request.connect();

        JsonParser jp = new JsonParser();
        JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent())); //Convert the input stream to a json element
        return root;
    }
     */
}
