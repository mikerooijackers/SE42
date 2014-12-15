package auction.domain;

import auction.domain.Bid;
import auction.domain.Item;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.0.v20130507-rNA", date="2014-12-15T13:56:30")
@StaticMetamodel(User.class)
public class User_ { 

    public static volatile SetAttribute<User, Item> offeredItems;
    public static volatile SingularAttribute<User, String> email;
    public static volatile ListAttribute<User, Bid> bids;

}