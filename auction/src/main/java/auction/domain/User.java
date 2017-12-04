package auction.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.persistence.*;

@Entity
@NamedQueries({
    @NamedQuery(name = "User.getAll", query = "select u from User u"),
    @NamedQuery(name = "User.count", query = "select count(u) as userCount from User u"),
    @NamedQuery(name = "User.findByEmail", query = "select u from User u where u.email = :email"),
    @NamedQuery(name = "User.getOfferedItems", query = "select u.offeredItems from User u"),
})
public class User implements Serializable{
    
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(unique = true)
    private String email;
    
    @OneToMany
    private Set<Item> offeredItems;
    
    //constructor for JPA manager
    public User() {
        this.offeredItems = new HashSet<>();
    }
    
    public User(String email) {
        this.email = email;
        this.offeredItems = new HashSet<>();
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long Id) {
        this.Id = Id;
    }
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Get Iterator for offered items.
     * @return Iterator offered items.
     */
    public Iterator<Item> getOfferedItems() { return this.offeredItems.iterator(); }

    /**
     * Get number of offered items.
     * @return Number of offered items.
     */
    public int numberOfOfferedItems() { return this.offeredItems.size(); }

    /**
     * Add item to offered items.
     * @param item Item object.
     */
    public void addItem(Item item) {
        this.offeredItems.add(item);
    }
}
