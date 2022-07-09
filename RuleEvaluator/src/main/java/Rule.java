import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Rule {
    private String name;
    private String market;
    private String priceType;
    private String firstRecentTime;
    private String secondRecentTime;
    private boolean isHigher; // First indicator is higher than second indicator

    public Rule() {
    } // For deserializing

    public Rule(String name, String market, String priceType, String firstRecentTime, String secondRecentTime, boolean isHigher) {
        this.name = name;
        this.market = market;
        this.priceType = priceType;
        this.firstRecentTime = firstRecentTime;
        this.secondRecentTime = secondRecentTime;
        this.isHigher = isHigher;
    }

    public String getMarket() {
        return market;
    }

    public boolean getIsHigher() {
        return isHigher;
    }


    public String getFirstRecentTime() {
        return firstRecentTime;
    }

    public String getSecondRecentTime() {
        return secondRecentTime;
    }

    public String getName() {
        return name;
    }

    public String getPriceType() {
        return priceType;
    }

    public void serialize() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        FileWriter file = new FileWriter("src/main/resources/rules/" + name + ".json");
        file.write(mapper.writeValueAsString(this));
        file.flush();
        file.close();
    }

    public static Rule deserialize(String fileName) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(new String(Files.readAllBytes(Paths.get("src/main/resources/rules/" + fileName))), Rule.class);
    }
}
