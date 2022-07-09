import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class RuleController {
    public RuleController() {
    }

    public List<Rule> getSatisfiedRules() throws IOException {
        List<Rule> satisfiedRules = new ArrayList<>();
        File folder = new File("src/main/resources/rules/");
        File[] listOfFiles = folder.listFiles();
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile() && listOfFiles[i].getName().endsWith(".json")) {
                Rule rule = Rule.deserialize(listOfFiles[i].getName());
                if (isRuleSatisfied(rule)) satisfiedRules.add(rule);
            }
        }
        return satisfiedRules;
    }

    public boolean isRuleSatisfied(Rule rule) throws IOException {
        boolean isSatisfied = false; // If isSatisfied is true, it means program must alert user
        Indicator sma1 = new SMA(getTimeSections(rule.getFirstRecentTime()), rule.getPriceType(), rule.getMarket());
        Indicator sma2 = new SMA(getTimeSections(rule.getSecondRecentTime()), rule.getPriceType(), rule.getMarket());
        if (sma1.getValue() > sma2.getValue() && rule.getIsHigher()) isSatisfied = true;
        if (sma1.getValue() < sma2.getValue() && !rule.getIsHigher()) isSatisfied = true;
        return isSatisfied;
    }

    private long getTimeSections(String time) throws IOException {
        long intervalTime = Long.parseLong(new PropertiesReader("rule_evaluator_config.properties").readProperty("interval_time"));
        int magnitude = Integer.parseInt(time.split("-")[0]);
        TimeUnit timeUnit = TimeUnit.valueOf(time.split("-")[1]);
        long micros = timeUnit.toMicros(magnitude);
        return micros / intervalTime;
    }
}
