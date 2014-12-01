package auction.domain;

import nl.fontys.util.Money;
import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.CascadeType;
import javax.persistence.GeneratedValue;
import javax.persistence.OneToOne;

/**
 * Contains an item entity
 * @author Subhi
 */
@Entity
public class Item implements Comparable {
    /**
     * The id of this item
     */
    @Id
    @GeneratedValue
    private Long id;
    
    /**
     * The seller of this item
     */
    @ManyToOne(cascade = CascadeType.MERGE)
    private User seller;
    
    /**
     * The category this item falls in
     */
    @ManyToOne(cascade = CascadeType.MERGE)
    private Category category;
    
    /**
     * The description of this item
     */
    @Column
    private String description;
    
    /**
     * The highest bid that was placed on this item
     */
    @OneToOne
    private Bid highest;

    public Item() {
    }

    /**
     * Item constructor
     * @param seller The seller of the item
     * @param category The category this item falls in
     * @param description The description this item has
     */
    public Item(User seller, Category category, String description) {
        this.seller = seller;
        this.category = category;
        this.description = description;
    }

    /**
     * Returns this item's id
     * @return The id
     */
    public Long getId() {
        return id;
    }

    /**
     * Returns the user that is selling this item
     * @return The user
     */
    public User getSeller() {
        return seller;
    }

    /**
     * Returns the category this item belongs to
     * @return The category
     */
    public Category getCategory() {
        return category;
    }

    /**
     * Returns the description of this item
     * @return The description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the highest bid that was placed on this item
     * @return The highest bid
     */
    public Bid getHighestBid() {
        return highest;
    }

    /**
     * Sets a new bid for this item
     * @param buyer The buyer
     * @param amount The amount of the bid
     * @return The created bid
     */
    public Bid newBid(User buyer, Money amount) {
        if (highest != null && highest.getAmount().compareTo(amount) >= 0) {
            return null;
        }
        highest = new Bid(buyer, amount);
        return highest;
    }

    /**
     * Compares to  an object
     * @param arg0
     * @return 
     */
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
