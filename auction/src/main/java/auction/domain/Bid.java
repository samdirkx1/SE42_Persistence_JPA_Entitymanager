package auction.domain;

import java.io.Serializable;
import nl.fontys.util.FontysTime;
import nl.fontys.util.Money;

import javax.persistence.*;

@Entity
public class Bid implements Serializable {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
        
    private FontysTime time;
    
    private User buyer;
    private Money amount;

    public Bid(User buyer, Money amount) {
       this.time = FontysTime.now();
       this.buyer = buyer;
       this.amount = amount;
    }
    
    public Bid() {
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long Id) {
        this.Id = Id;
    }

    public void setTime(FontysTime time) {
        this.time = time;
    }

    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }

    public void setAmount(Money amount) {
        this.amount = amount;
    }

    public FontysTime getTime() {
        return time;
    }

    public User getBuyer() {
        return buyer;
    }

    public Money getAmount() {
        return amount;
    }
}
