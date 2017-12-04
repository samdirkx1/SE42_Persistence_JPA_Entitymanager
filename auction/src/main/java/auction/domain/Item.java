package auction.domain;

import java.io.Serializable;
import javax.persistence.*;
import nl.fontys.util.Money;

@Entity
@NamedQueries({ //implement: verander en add de queries zodat ze passen bij item ipv user
    @NamedQuery(name = "Item.getAll", query = "select i from Item i"),
    @NamedQuery(name = "Item.count", query = "select count(i) as itemCount from Item i"),
    @NamedQuery(name = "Item.findByDescription", query = "select i from Item i where i.description = :description"),
    @NamedQuery(name = "Item.findById", query = "select i from Item i where i.Id = :Id"),
})public class Item implements Comparable, Serializable {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long Id;
    @ManyToOne(cascade = CascadeType.MERGE)
    private User seller;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "description", column = @Column(name = "category"))
    })
    private Category category;
    private String description;
    @OneToOne(cascade = CascadeType.PERSIST)
    private Bid highest;
    
    //constructor for JPA manager
    public Item() {
    }

    public Item(User seller, Category category, String description) {
        this.seller = seller;
        this.category = category;
        this.description = description;
        seller.addItem(this);
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
        if(o == null) { return false; }
        if(o == this) { return true; }
        if(!(o instanceof Item)) { return false; }
        Item otherItem = (Item) o;

        if(otherItem.getId() != this.getId()) { return false; }
        if(otherItem.getDescription() != this.getDescription()) { return false; }
        if(otherItem.getHighestBid().getAmount().getCents() != this.getHighestBid().getAmount().getCents()) { return false; }

        return true;
    }

    public int hashCode() {
        int hashCode = 1;
        hashCode = hashCode * 12 + (this.seller.getEmail().hashCode());
        hashCode = hashCode * 13 + (this.getDescription().hashCode());
        return hashCode;
    }
}
