package auction.domain;

import auction.domain.Bid;
import auction.domain.Category;
import auction.domain.User;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.0.v20130507-rNA", date="2014-12-15T13:56:30")
@StaticMetamodel(Item.class)
public class Item_ { 

    public static volatile SingularAttribute<Item, Long> id;
    public static volatile SingularAttribute<Item, Category> category;
    public static volatile SingularAttribute<Item, String> description;
    public static volatile SingularAttribute<Item, Bid> highest;
    public static volatile ListAttribute<Item, Bid> bids;
    public static volatile SingularAttribute<Item, User> seller;

}