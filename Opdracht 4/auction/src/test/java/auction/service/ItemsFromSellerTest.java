package auction.service;

import auction.dao.CategoryDAOJPAImpl;
import auction.domain.Bid;
import org.junit.Ignore;
import javax.persistence.*;
import util.DatabaseCleaner;
import auction.domain.Category;
import auction.domain.Item;
import auction.domain.User;
import java.util.Iterator;
import nl.fontys.util.Money;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ItemsFromSellerTest {

    final EntityManagerFactory emf = Persistence.createEntityManagerFactory("db");
    final EntityManager em = emf.createEntityManager();
    private AuctionMgr auctionMgr;
    private RegistrationMgr registrationMgr;
    private SellerMgr sellerMgr;

    public ItemsFromSellerTest() {
    }

    @Before
    public void setUp() throws Exception {
        registrationMgr = new RegistrationMgr(em);
        auctionMgr = new AuctionMgr(em);
        sellerMgr = new SellerMgr(em);
        DatabaseCleaner dc = new DatabaseCleaner(Persistence.createEntityManagerFactory("db").createEntityManager());
        dc.clean();
    }

    @Test
 //   @Ignore
    public void numberOfOfferdItems() {

        String email = "ifu1@nl";
        String omsch1 = "omsch_ifu1";
        String omsch2 = "omsch_ifu2";

        User user1 = registrationMgr.registerUser(email);
        assertEquals(0, user1.numberOfOfferdItems());

        Category cat = new Category("cat2");
        CategoryDAOJPAImpl categories = new CategoryDAOJPAImpl();
        categories.create(cat);
        Item item1 = sellerMgr.offerItem(user1, cat, omsch1);

       
        // test number of items belonging to user1
        assertEquals(1, user1.numberOfOfferdItems());
        
        /*
         *  expected: which one of te above two assertions do you expect to be true?
         *  QUESTION:
         *    Explain the result in terms of entity manager and persistance context.
         * Would expect 1 to be true, since we offered an item, although we had to implement
         * a method to make sure the user also new about the item, and not just the other way around
         * The other way around happens because a user is not magically informed he has a new item
         */
         
         
        assertEquals(1, item1.getSeller().numberOfOfferdItems());


        User user2 = registrationMgr.getUser(email);
        assertEquals(1, user2.numberOfOfferdItems());
        Item item2 = sellerMgr.offerItem(user2, cat, omsch2);
        assertEquals(2, user2.numberOfOfferdItems());
        User user3 = registrationMgr.getUser(email);
        assertEquals(2, user3.numberOfOfferdItems());

        registrationMgr = new RegistrationMgr(Persistence.createEntityManagerFactory("db").createEntityManager());
        User userWithItem = item2.getSeller();
        assertEquals(2, userWithItem.numberOfOfferdItems());
        //assertEquals(3, userWithItem.numberOfOfferdItems());
        /*
         *  expected: which one of te above two assertions do you expect to be true?
         *  QUESTION:
         *    Explain the result in terms of entity manager and persistance context.
         *  assertEquals 2, because we only add create 2 items, so i'm not really sure where the 3rd would come from
         */
        
        assertSame(user3, userWithItem);
        assertEquals(user3, userWithItem);

    }

    @Test
//    @Ignore
    public void getItemsFromSeller() {
        String email = "ifu1@nl";
        String omsch1 = "omsch_ifu1";
        String omsch2 = "omsch_ifu2";

        Category cat = new Category("cat2");
        CategoryDAOJPAImpl categories = new CategoryDAOJPAImpl();
        categories.create(cat);

        User user10 = registrationMgr.registerUser(email);
        Item item10 = sellerMgr.offerItem(user10, cat, omsch1);
        Iterator<Item> it = user10.getOfferedItems();
        // testing number of items of java object
        assertTrue(it.hasNext());
        
        // now testing number of items for same user fetched from db.
        User user11 = registrationMgr.getUser(email);
        Iterator<Item> it11 = user11.getOfferedItems();
        assertTrue(it11.hasNext());
        it11.next();
        assertFalse(it11.hasNext());

        // Explain difference in above two tests for te iterator of 'same' user

        
        
        User user20 = registrationMgr.getUser(email);
        Item item20 = sellerMgr.offerItem(user20, cat, omsch2);
        Iterator<Item> it20 = user20.getOfferedItems();
        assertTrue(it20.hasNext());
        it20.next();
        assertTrue(it20.hasNext());


        User user30 = item20.getSeller();
        Iterator<Item> it30 = user30.getOfferedItems();
        assertTrue(it30.hasNext());
        it30.next();
        assertTrue(it30.hasNext());

    }
    
    @Test
    public void biDirectionalTest() {
        User u1 = registrationMgr.registerUser("aa@aa.xx");
        User u2 = registrationMgr.registerUser("bb@aa.xx");
        CategoryDAOJPAImpl cda = new CategoryDAOJPAImpl();
        Category cat = new Category("somedesc");
        cda.create(cat);
        Item offerItem = sellerMgr.offerItem(u2, cat, "AnItem");
        Money m = new Money(100, "euro");
        Money m2 = new Money(200, "euro");
        offerItem.newBid(u2, m);
        assertEquals(1, offerItem.getBids().size());
        Bid bid1 = offerItem.getHighestBid();
        offerItem.newBid(u2, m2);
        assertEquals(2, offerItem.getBids().size());
        Bid bid2 = offerItem.getHighestBid();
        assertEquals(bid1.getItem(), offerItem);
    }
}
