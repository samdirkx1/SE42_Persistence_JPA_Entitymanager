package auction.domain;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import nl.fontys.util.Money;

@Entity
@NamedQueries({ //implement: verander en add de queries zodat ze passen bij item ipv user
    @NamedQuery(name = "Item.getAll", query = "select i from Item i"),
    @NamedQuery(name = "Item.count", query = "select count(i) as itemCount from Item i"),
    @NamedQuery(name = "Item.findByDescription", query = "select i from Item i where i.description = :description"),
    @NamedQuery(name = "Item.findById", query = "select i from Item i where i.Id = :Id"),
})public class Item implements Comparable, Serializable {

    @Id @GeneratedValue
    private Long Id;
    private User seller;
    private Category category;
    private String description;
    private Bid highest;
    
    //constructor for JPA manager
    public Item() {
    }

    public Item(User seller, Category category, String description) {
        this.seller = seller;
        this.category = category;
        this.description = description;
    }

    public Long getId() {
        return Id;
    }

    public User getSeller() {
        return seller;
    }

    public Category getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public Bid getHighestBid() {
        return highest;
    }

    //generated met insert code, kijk er maar hoe je het wilt sander
    public Bid getHighest() {
        return highest;
    }

    public void setId(Long Id) {
        this.Id = Id;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setHighest(Bid highest) {
        this.highest = highest;
    }
    //einde insert codes
    
    public Bid newBid(User buyer, Money amount) {
        if (highest != null && highest.getAmount().compareTo(amount) >= 0) {
            return null;
        }
        highest = new Bid(buyer, amount);
        return highest;
    }

    public int compareTo(Object arg0) {
        //TODO
        return -1;
    }

    public boolean equals(Object o) {
        //TODO
        return false;
    }

    public int hashCode() {
        //TODO
        return 0;
    }
}
