import java.util.List;

public class SMA extends Indicator {
    private long timeSections;
    private String priceType;
    private String market;

    public SMA(long timeSections, String priceType, String market) {
        super();
        this.timeSections = timeSections;
        this.priceType = priceType;
        this.market = market;
    }

    @Override
    public void initializeValue() {
        List<Float> prices = CandleDataController.getPriceData(priceType, market);
        value = 0;
        if (prices.size() > timeSections) {
            for (int i = 0; i < timeSections; i++) {
                value += (prices.get(prices.size() - (i + 1))) / timeSections;
            }
        } else {
            for (int i = 0; i < prices.size(); i++) {
                value += (prices.get(prices.size() - (i + 1))) / prices.size();
            }
        }
    }

    @Override
    public void updateValue() {
        List<Float> prices = CandleDataController.getPriceData(priceType, market);
        if (prices.size() > timeSections) {
            value += (prices.get(prices.size() - 1) - prices.get((int) (prices.size() - (timeSections + 1)))) / timeSections;
        } else {
            value = (value * (prices.size() - 1) + prices.get(prices.size() - 1)) / prices.size();
        }
    }
}
