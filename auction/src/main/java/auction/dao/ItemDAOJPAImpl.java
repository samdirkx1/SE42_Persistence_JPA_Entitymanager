package auction.dao;

import auction.domain.Item;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NamedQuery;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

public class ItemDAOJPAImpl implements ItemDAO{

    private final EntityManager em;

    public ItemDAOJPAImpl(EntityManager em) {
        this.em = em;
    }
    
    /**
     *
     * @return number of item instances
     */
    @Override
    public int count() {
        Query q = em.createNamedQuery("Item.count", Item.class);
        return ((Long) q.getSingleResult()).intValue();
    }

    /**
     * The item is persisted. If a item with the same id allready exists an EntityExistsException is thrown
     * @param item
     */
    @Override
    public void create(Item item) {
        em.getTransaction().begin();
        em.persist(item);
        em.getTransaction().commit();
    }

    /**
     * Merge the state of the given item into persistant context. If the item did not exist an IllegalArgumentException is thrown
     * @param item
     */
    @Override
    public void edit(Item item) {
        em.getTransaction().begin();
        em.merge(item);
        em.getTransaction().commit();
    }

    /**
     *
     * @param id
     * @return the found entity instance or null if the entity does not exist
     */
    @Override
    public Item find(Long id) {
        Query q = em.createNamedQuery("Item.findById", Item.class);
        q.setParameter("Id", id);
        try {
            return (Item) q.getSingleResult();
        }
        catch (NoResultException ex) {
            return null;
        }
    }
    /**
     *
     * @return list of item instances
     */
    @Override
    public List<Item> findAll() {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(Item.class));
        try {
            return em.createQuery(cq).getResultList();        
        }
        catch (NoResultException ex) {
            return null;
        }

    }    
    
    /**
     *
     * @param description 
     * @return list of item instances having specified description
     */
    @Override
    public List<Item> findByDescription(String description) {
        Query q = em.createNamedQuery("Item.findByDescription", Item.class);
        q.setParameter("description", description);
        try {
            return (List<Item>) q.getResultList();
        }
        catch (NoResultException ex) {
            return null;
        }
    }

    /**
     * Remove the entity instance
     * @param item - entity instance
     */
    @Override
    public void remove(Item item) {
        em.getTransaction().begin();
        em.remove(em.merge(item));
        em.getTransaction().commit();
    }
}
