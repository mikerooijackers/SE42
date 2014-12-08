package auction.domain;

import java.util.ArrayList;
import java.util.List;
import nl.fontys.util.Money;
import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.CascadeType;
import javax.persistence.GeneratedValue;
import javax.persistence.OneToMany;
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
    
    @OneToMany(mappedBy="item", cascade = CascadeType.PERSIST)
    private List<Bid> bids;

    public Item() {
    }

    /**
     * Item constructor
     * @param seller The seller of the item
     * @param category The category this item falls in
     * @param description The description this item has
     */
    public Item(User seller, Category category, String description) {
        this.category = category;
        this.description = description;
        bids = new ArrayList<Bid>();
        seller.addItem(this);
    }

    /**
     * Returns this item's id
     * @return The id
     */
    public Long getId() {
        return id;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }
    /**
     * Returns the user that is selling this item
     * @return The user
     */
    public User getSeller() {
        return seller;
    }
    
    public List<Bid> getBids() {
        return this.bids;
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
        System.out.println(bids + "HighedT");
        this.bids.add(highest);
        highest.setItem(this);
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
        if (o == null) {
            return false;
        } else if (o.getClass() != this.getClass()) {
            return false;
        }
        return ((Item) o).getId() == this.id;
    }

    public int hashCode() {
        int hash = 1;
        hash = hash * 13 + (this.getDescription() == null ? 0 : this.getDescription().hashCode());
        hash = hash * 17 + (this.getSeller() == null ? 0 : this.getSeller().getEmail().hashCode());
        hash = hash * 19 + (this.getCategory() == null ? 0 : this.getCategory().getDiscription().hashCode());
        return hash;
    }
}
