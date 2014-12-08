package auction.dao;

import auction.domain.Item;
import auction.domain.User;
import java.util.List;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

public class ItemDAOJPAImpl implements ItemDAO {
    private EntityManager items;
    public ItemDAOJPAImpl(EntityManager em) {
        this.items = em;
    }

    @Override
    public int count() {
        return (Integer) items.createNativeQuery("SELECT count(*) FROM Item i")
                .getSingleResult();  
    }

    @Override
    public void create(Item item) {
        // if (find(item.getId()) != null) {
        //     throw new EntityExistsException();
        // }
        items.persist(item);
    }

    @Override
    public void edit(Item item) {
        // if (find(item.getId()) == null) {
        //     throw new IllegalArgumentException();
        // }
        items.merge(item);
    }


    @Override
    public List<Item> findAll() {
        return items.createQuery("SELECT i FROM Item i").getResultList();
    }

    @Override
    public void remove(Item item) {
        items.remove(this.find(item.getId()));
    }

    @Override
    public Item find(Long id) {
        return items.find(Item.class, id);
    }

    @Override
    public List<Item> findByDescription(String description) {
        Query q = items.createQuery("SELECT i FROM Item i WHERE i.description = :description", Item.class);
        return q.setParameter("description", description).getResultList();
    }
}
