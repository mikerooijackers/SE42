package auction.domain;

import auction.domain.Item;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.0.v20130507-rNA", date="2014-06-03T11:53:51")
@StaticMetamodel(Category.class)
public class Category_ { 

    public static volatile ListAttribute<Category, Item> items;
    public static volatile SingularAttribute<Category, String> description;

}