package auction.domain;

import nl.fontys.util.FontysTime;
import nl.fontys.util.Money;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Entity;
import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.GeneratedValue;

@Entity
public class Bid {

    /**
     * The id of this item
     */
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private FontysTime time;

    @ManyToOne(cascade = CascadeType.MERGE)
    private User buyer;

    @Embedded
    private Money amount;
    
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(nullable=false)
    private Item item;
    
    public Bid() {
    }

    public Bid(User buyer, Money amount) {
        this.buyer = buyer;
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
    public void setItem(Item i) {
        this.item = i;
    }
    
    public Item getItem() {
        return this.item;
    }
}
