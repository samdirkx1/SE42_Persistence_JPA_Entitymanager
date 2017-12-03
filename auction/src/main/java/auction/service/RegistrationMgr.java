package auction.service;

import java.util.*;
import auction.domain.User;
import auction.dao.UserDAO;
import auction.dao.UserDAOJPAImpl;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import nl.fontys.util.DatabaseCleaner;

public class RegistrationMgr {
    
    EntityManagerFactory emf;

    private EntityManager em;

    private UserDAO userDAO;

    public RegistrationMgr() {
        try {
            emf = Persistence.createEntityManagerFactory("auctionPU"); 
            em = emf.createEntityManager();
            userDAO = new UserDAOJPAImpl(em);
        }
        catch(Exception ex) {
            System.out.println("REGISTRATIONMGR createEntityManagerFactory(auctionPU) ERROR --> " + ex.getMessage());
        }
    }

    /**
     * Registreert een gebruiker met het als parameter gegeven e-mailadres, mits
     * zo'n gebruiker nog niet bestaat.
     * @param email
     * @return Een Userobject dat geïdentificeerd wordt door het gegeven
     * e-mailadres (nieuw aangemaakt of reeds bestaand). Als het e-mailadres
     * onjuist is ( het bevat geen '@'-teken) wordt null teruggegeven.
     */
    public User registerUser(String email) {
        if (!email.contains("@")) {
            return null;
        }
        User user = userDAO.findByEmail(email);
        if (user != null) {
            return user;
        }
        user = new User(email);
        userDAO.create(user);
        return user;
    }

    /**
     *
     * @param email een e-mailadres
     * @return Het Userobject dat geïdentificeerd wordt door het gegeven
     * e-mailadres of null als zo'n User niet bestaat.
     */
    public User getUser(String email) {
        return userDAO.findByEmail(email);
    }

    /**
     * @return Een iterator over alle geregistreerde gebruikers
     */
    public List<User> getUsers() {
        return userDAO.findAll();
    }
    
    public int count() {
        return userDAO.count();
    }

    public void create(User user) {
        userDAO.create(user);
    }

    public void edit(User user) {
        userDAO.edit(user);
    }

    public List<User> findAll() {
        return userDAO.findAll();
    }

    public User findByEmail(String email) {
        return userDAO.findByEmail(email);
    }

    public void remove(User user) {
        userDAO.remove(user);
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
