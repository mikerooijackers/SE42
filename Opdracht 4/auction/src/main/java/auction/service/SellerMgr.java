package auction.service;

import auction.dao.ItemDAO;
import auction.dao.ItemDAOJPAImpl;
import auction.domain.Category;
import auction.domain.Item;
import auction.domain.User;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class SellerMgr {

    private ItemDAO itemDAO;
    private EntityManager em = Persistence.createEntityManagerFactory("db").createEntityManager();
    public SellerMgr() {
        itemDAO = new ItemDAOJPAImpl();
    }

    /**
     * @param seller
     * @param cat
     * @param description
     * @return het item aangeboden door seller, behorende tot de categorie cat
     *         en met de beschrijving description
     */
    public Item offerItem(User seller, Category cat, String description) {
        em.getTransaction().begin();
        Item item = new Item(seller, cat, description);
        itemDAO.create(item);
        em.getTransaction().commit();
        return item;
    }
    
     /**
     * @param item
     * @return true als er nog niet geboden is op het item. Het item word verwijderd.
     *         false als er al geboden was op het item.
     */
    public boolean revokeItem(Item item) {
        if (item.getHighestBid() == null) {
            em.getTransaction().begin();
            itemDAO.remove(item);
            em.getTransaction().commit();
            return true;
        }
        return false;
    }
}
