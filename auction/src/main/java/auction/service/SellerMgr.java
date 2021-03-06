package auction.service;

import auction.dao.ItemDAO;
import auction.dao.ItemDAOJPAImpl;
import auction.domain.Category;
import auction.domain.Furniture;
import auction.domain.Item;
import auction.domain.Painting;
import auction.domain.User;
import java.sql.SQLException;
import java.util.Map;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import nl.fontys.util.DatabaseCleaner;

public class SellerMgr {

    private EntityManagerFactory emf;
    private EntityManager em;
    private ItemDAO dao;
    
    public SellerMgr()
    {
        try {
            emf = Persistence.createEntityManagerFactory("auctionPU"); 
            em = emf.createEntityManager();
            dao = new ItemDAOJPAImpl(em);
        }
        catch(Exception ex) {
            System.out.println("SELLERMGR createEntityManagerFactory(auctionPU) ERROR --> " + ex.getMessage());
        }
    }

//eruit commenten als item abstract is    
//    /**
//     * @param seller
//     * @param cat
//     * @param description
//     * @return het item aangeboden door seller, behorende tot de categorie cat
//     *         en met de beschrijving description
//     */
//    
//    public Item offerItem(User seller, Category cat, String description) {
//        // TODO 
//       Item i = new Item(seller, cat, description);
//       dao.create(i);
//        
//        return i;
//    }
    
    Item offerFurniture(User seller, Category cat, String omsch, String material)
    {
        Item i = new Furniture(material, seller, cat, omsch);        
        dao.create(i);
        return i;
    }

    Item offerPainting(User seller, Category cat, String omsch, String title, String painter)
    {
        Item i = new Painting(title, painter, seller, cat, omsch);
        dao.create(i);
        return i;
    }
    
     /**
     * @param item
     * @return true als er nog niet geboden is op het item. Het item word verwijderd.
     *         false als er al geboden was op het item.
     */
    public boolean revokeItem(Item item) {
        if(item.getHighest() == null)
        {
            dao.remove(item);
            return true;
        }

        return false;
    }
    
//     /**
//     * @param item
//     * @return true als er nog niet geboden is op het item. Het item word verwijderd.
//     *         false als er al geboden was op het item.
//     */
//    public Set<Map> getOfferedItems(Integer id) {
//        
//        if(item.getHighest() == null)
//        {
//            dao.remove(item);
//            return true;
//        }
//        
//        return false;
//    }
    
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
