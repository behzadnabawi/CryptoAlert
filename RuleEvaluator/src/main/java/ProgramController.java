import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.List;

//@Controller
public class ProgramController {
    private boolean isRunning = true;
    private long timeOut = Long.parseLong(new PropertiesReader("rule_evaluator_config.properties").readProperty("time_out"));
    private long clearDataTime = Long.parseLong(new PropertiesReader("rule_evaluator_config.properties").readProperty("clear_data_time"));
    private String topic;
    private Consumer consumer;
//    @Autowired
//    private AlertRepository repository;
    public ProgramController() throws IOException {
        this.topic = new PropertiesReader("rule_evaluator_config.properties").readProperty("topic");
        this.consumer = new Consumer(topic);
    }

    public void run() throws Exception {
        KafkaDeserializer kafkaDeserializer = KafkaDeserializer.getInstance(); //Singleton class
        CandleDataController candleDataController = CandleDataController.getInstance(); // Singleton class
        long startTime = System.currentTimeMillis();
        while (isRunning) {
            List<String> values = consumer.receive(timeOut);
            for (int i = 0; i < values.size(); i++) {
                List<Object> data = kafkaDeserializer.deserialize(values.get(i));
                candleDataController.updateData(data);
//                System.out.println("data:");
//                System.out.println(CandleDataController.getCandleData());
                RuleController ruleController = new RuleController();
                List<Rule> satisfiedRules = ruleController.getSatisfiedRules();
//                System.out.println("satisfied Rules:");
//                System.out.println(satisfiedRules);
                /*
                for (Rule rule:satisfiedRules) {
                    int length = CandleDataController.getCandleData().get(rule.getMarket()).size();
                    float price = Float.parseFloat(CandleDataController.getCandleData().get(rule.getMarket()).get(length - 1).get(5).toString()); // Get the latest price
                    long time = Long.parseLong(CandleDataController.getCandleData().get(rule.getMarket()).get(length - 1).get(6).toString()); // Get the latest openTime
                    AlertEntity alertEntity = new AlertEntity(rule.getName(), rule.getMarket(), price, time);
                    repository.save(alertEntity);
                }
                 */
            }
            // Clear saved data from kafka after specific time
            if (System.currentTimeMillis() - startTime > clearDataTime) {
                candleDataController.clearCandleData();
                startTime = System.currentTimeMillis();
            }
        }
    }

    public void stop() {
        isRunning = false;
        consumer.close();
    }
}
