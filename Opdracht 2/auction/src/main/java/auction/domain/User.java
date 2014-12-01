package auction.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 *
 * @author Mike
 */
@Entity
public class User implements Serializable {
    
    @Id
    private Long id;
    private String email;
    
    public User() {
    }
    
    public User(String email) {
        this.email = email;

    }

    public String getEmail() {
        return email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}