public class KafkaSerializer {
    private static KafkaSerializer INSTANCE = null;

    private KafkaSerializer() {
    }

    public static KafkaSerializer getInstance() {
        if (INSTANCE == null) INSTANCE = new KafkaSerializer();
        return INSTANCE;
    }

    public String serialize(String market, String high, String low, String open, String close, String price, String openTime) {
        String result = "market:" + market + ",high:" + high + ",low:" + low + ",open:" + open +
                ",close:" + close + ",price:" + price + ",openTime:" + openTime;
        return result;
    }
}
