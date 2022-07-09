import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

//@Entity
//@Table(name = "TBL_ALERT")
public class Alert {
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
//    @Column(name = "ruleName", nullable = false)
    private String ruleName;
//    @Column(name = "market", nullable = false)
    private String market;
//    @Column(name = "price", nullable = false)
    private float price;
//    @Column(name = "date", nullable = false)
    private Date date;


    public Alert(String ruleName, String market, float price, long time) {
        this.ruleName = ruleName;
        this.market = market;
        this.price = price;
        setDate(time);
    }

    public Alert() {

    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setMarket(String market) {
        this.market = market;
    }

    public String getMarket() {
        return market;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getPrice() {
        return price;
    }

    public void setDate(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");
        this.date = new Date(time);
    }
}
