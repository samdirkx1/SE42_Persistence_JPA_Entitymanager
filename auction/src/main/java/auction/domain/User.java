package auction.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class User {

    @Id @GeneratedValue
    private Long id; //ORM
    private String email;

    public User(String email) {
        this.email = email;

    }
    
    //dummy constructor for JPA manager
    public User() {
    }

    public String getEmail() {
        return email;
    }
}
