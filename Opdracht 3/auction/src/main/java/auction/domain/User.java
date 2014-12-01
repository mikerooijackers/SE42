package auction.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import java.util.ArrayList;
import java.util.List;

/**
 * Contains a user entity
 */
@Entity
public class User {
    /**
     * The email address of the user
     */
    @Id
    private String email;

    @OneToMany(mappedBy = "seller", cascade = CascadeType.PERSIST)
    private List<Item> items = new ArrayList<>();

    @OneToMany(mappedBy = "buyer", cascade = CascadeType.PERSIST)
    private List<Bid> bids = new ArrayList<>();
    
    /**
     * No arg persistence constructor
     */
    public User() {}

    /**
     * Constructs a user using the passed email address
     * @param email 
     */
    public User(String email) {
        this.email = email;

    }

    /**
     * Returns the email address of this user
     * @return The email address
     */
    public String getEmail() {
        return email;
    }
}
