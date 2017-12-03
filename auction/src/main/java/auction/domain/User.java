package auction.domain;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@NamedQueries({
    @NamedQuery(name = "User.getAll", query = "select u from User u"),
    @NamedQuery(name = "User.count", query = "select count(u) as userCount from User u"),
    @NamedQuery(name = "User.findByEmail", query = "select u from User u where u.email = :email"),
})
public class User implements Serializable{
    
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(unique = true)
    private String email;
    
    //constructor for JPA manager
    public User() {
    }
    
    public User(String email) {
        this.email = email;
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
    
}
