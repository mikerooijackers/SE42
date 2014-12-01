package auction.domain;

import auction.domain.Bid;
import auction.domain.Item;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.0.v20130507-rNA", date="2014-12-01T10:33:56")
@StaticMetamodel(User.class)
public class User_ { 

    public static volatile ListAttribute<User, Bid> bids;
    public static volatile ListAttribute<User, Item> items;
    public static volatile SingularAttribute<User, String> email;

}