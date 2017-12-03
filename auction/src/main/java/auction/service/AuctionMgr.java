package auction.service;

import auction.dao.ItemDAO;
import auction.dao.ItemDAOJPAImpl;
import nl.fontys.util.Money;
import auction.domain.Bid;
import auction.domain.Item;
import auction.domain.User;
import java.sql.SQLException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import nl.fontys.util.DatabaseCleaner;

public class AuctionMgr  {

    private EntityManagerFactory emf;
    private EntityManager em;
    private ItemDAO dao;
    
    public AuctionMgr()
    {
        try {
            emf = Persistence.createEntityManagerFactory("auctionPU"); 
            em = emf.createEntityManager();
            dao = new ItemDAOJPAImpl(em);
        }
        catch(Exception ex) {
            System.out.println("AUCTIOMGR createEntityManagerFactory(auctionPU) ERROR --> " + ex.getMessage());
        }
    }
    
   /**
     * @param id
     * @return het item met deze id; als dit item niet bekend is wordt er null
     *         geretourneerd
     */
    public Item getItem(Long id) {
        return dao.find(id);
    }

  
   /**
     * @param description
     * @return een lijst met items met @desciption. Eventueel lege lijst.
     */
    public List<Item> findItemByDescription(String description) {
        return dao.findByDescription(description);
    }

    /**
     * @param item
     * @param buyer
     * @param amount
     * @return het nieuwe bod ter hoogte van amount op item door buyer, tenzij
     *         amount niet hoger was dan het laatste bod, dan null
     */
    public Bid newBid(Item item, User buyer, Money amount) {
        if(item.getHighest() == null || amount.compareTo(item.getHighest().getAmount()) > 0) {        
            return item.newBid(buyer, amount); 
        }
        return null;
    }
    
    public void cleanDatabase() {
        //clean database and delete entities.
        DatabaseCleaner dbc = new DatabaseCleaner(em);
        try {
            dbc.clean();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }         
    }
}
