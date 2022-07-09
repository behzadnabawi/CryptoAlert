import org.springframework.beans.factory.annotation.Autowired;

public class Main {
    public static void main(String[] args) throws Exception {
        /*
        Rule rule1 = new Rule("rule03", "BTCUSDT", "close", "1-MINUTES", "2-MINUTES", true);
        rule1.serialize();
        Rule rule2 = new Rule("rule04", "ETHBTC", "open", "1-MINUTES", "2-MINUTES", false);
        rule2.serialize();
        */
        //System.out.println(Rule.deserialize("rule03.json"));
        ProgramController programController = new ProgramController();
        programController.run();
        //System.out.println(new RuleController().getSatisfiedRules());
    }
}
