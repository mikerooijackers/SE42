package auction.domain;

import javax.persistence.Entity;
import javax.persistence.Id;


/**
 * Contains a user entity
 * @author Subhi
 */
@Entity
public class User {
    /**
     * The email address of the user
     */
    @Id
    private String email;
    
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
