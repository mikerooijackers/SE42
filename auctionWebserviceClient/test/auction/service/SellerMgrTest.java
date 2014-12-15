package auction.service;

import auction.dao.CategoryDAOJPAImpl;
import static org.junit.Assert.*;

import nl.fontys.util.Money;

import org.junit.Before;
import org.junit.Test;



import auction.domain.Category;
import auction.domain.Item;
import auction.domain.User;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import util.DatabaseCleaner;

public class SellerMgrTest {

    private AuctionMgr auctionMgr;
    private RegistrationMgr registrationMgr;
    private SellerMgr sellerMgr;
    EntityManager em = Persistence.createEntityManagerFactory("db").createEntityManager();
    @Before
    public void setUp() throws Exception {
        System.out.print("before seller");
        registrationMgr = new RegistrationMgr(em);
        auctionMgr = new AuctionMgr(em);
        sellerMgr = new SellerMgr(em);
        DatabaseCleaner dc = new DatabaseCleaner(Persistence.createEntityManagerFactory("db").createEntityManager());
        dc.clean();
    }

    /**
     * Test of offerItem method, of class SellerMgr.
     */
    @Test
    public void testOfferItem() {
        String omsch = "omsch";

        User user1 = registrationMgr.registerUser("xx@nl");
        Category cat = new Category("cat1");
        CategoryDAOJPAImpl categories = new CategoryDAOJPAImpl();
        categories.create(cat);
        Item item1 = sellerMgr.offerItem(user1, cat, omsch);
        assertEquals(omsch, item1.getDescription());
        assertNotNull(item1.getId());
    }

    /**
     * Test of revokeItem method, of class SellerMgr.
     */
    @Test
    public void testRevokeItem() {
        String omsch = "omsch";
        String omsch2 = "omsch2";
        EntityManager em = Persistence.createEntityManagerFactory("db").createEntityManager();
        em.getTransaction().begin();
        User seller = registrationMgr.registerUser("sel@nl");
        User buyer = registrationMgr.registerUser("buy@nl");
        Category cat = new Category("cat1");
        CategoryDAOJPAImpl categories = new CategoryDAOJPAImpl();
        categories.create(cat);
        em.getTransaction().commit();
        
        
            // revoke before bidding
        em.getTransaction().begin();
        Item item1 = sellerMgr.offerItem(seller, cat, omsch);
        boolean res = sellerMgr.revokeItem(item1);
        em.getTransaction().commit();
        assertTrue(res);
        int count = auctionMgr.findItemByDescription(omsch).size();
        assertEquals(0, count);
        
            // revoke after bid has been made
        em.getTransaction().begin();
        Item item2 = sellerMgr.offerItem(seller, cat, omsch2);
        auctionMgr.newBid(item2, buyer, new Money(100, "Euro"));
        em.getTransaction().commit();
        em.getTransaction().begin();
        boolean res2 = sellerMgr.revokeItem(item2);
        em.getTransaction().commit();
        assertFalse(res2);
        int count2 = auctionMgr.findItemByDescription(omsch2).size();
        assertEquals(0, count2);
        
    }

}
