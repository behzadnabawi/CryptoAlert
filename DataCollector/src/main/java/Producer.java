import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;
import java.util.concurrent.ExecutionException;


public class Producer {
    private String topic;
    private KafkaProducer<String, String> kafkaProducer;

    public Producer(String topic) {
        this.topic = topic;
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        kafkaProducer = new KafkaProducer<String, String>(props);
    }

    public void send(String key, String value) throws ExecutionException, InterruptedException {
        kafkaProducer.send(new ProducerRecord<>(topic, key, value)).get();
    }

    public void close() {
        kafkaProducer.close();
    }
}