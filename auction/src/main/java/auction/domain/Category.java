package auction.domain;

import java.io.Serializable;
import javax.persistence.Embeddable;

@Embeddable
public class Category{

    private String description;

    private Category() {
        description = "undefined";
    }

    public Category(String description) {
        this.description = description;
    }

    public String getDiscription() {
        return description;
    }
}
