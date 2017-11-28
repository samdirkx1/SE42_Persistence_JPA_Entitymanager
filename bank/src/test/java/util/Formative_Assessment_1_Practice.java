/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import bank.dao.*;
import bank.domain.*;
import bank.service.*;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Gebruiker
 */
public class Formative_Assessment_1_Practice {
    final EntityManagerFactory emf = TestUtil.getEMF();
    EntityManager em, em1, em2;
    private static final Logger LOG = Logger.getLogger(Formative_Assessment_1_Practice.class.getName());
    
    public Formative_Assessment_1_Practice() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        //setup entitymanagerfactory and entititymanager
        em = emf.createEntityManager();
        em1 = emf.createEntityManager();
        em2 = emf.createEntityManager();
        try {
            new DatabaseCleaner(emf.createEntityManager()).clean();
        } catch (SQLException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
    }
    
    @After
    public void tearDown() {       
    }
    
    @Test
    public void Test1() {
        Account account = new Account(111L);
	em.getTransaction().begin();

	em.persist(account);
	assertNull(account.getId());
	em.getTransaction().commit();

	assertTrue(account.getId() > 0L);     
    }    
    
    @Test
    public void Test2() {
        Long expected = 200L;
	Account account = new Account(112L);
	Account merged;
	account.setId(332L);
	em.getTransaction().begin();

	merged = em.merge(account);
	em.getTransaction().commit();
	merged.setBalance(expected);

	assertEquals(expected, merged.getBalance());
//	assertEquals(account, merged); //fails
    }
    
    @Test
    public void Test3() {
	Long expected = 100L;
	Account account = new Account(111L);
	account.setId(331L);
	Account merged;
	em.getTransaction().begin();

	merged = em.merge(account);
	assertEquals(merged.getId(), account.getId());
	em.getTransaction().commit();
	assertTrue(merged.getId() != account.getId());
//	assertFalse(em.contains(merged)); //fails
//	assertFalse(em.contains(account)); //fails

//	assertSame(merged, account); //fails
//	assertEquals(merged, account); //fails
    }
    
    @Test
    public void test4() {
        Account account1 = new Account();
        Account account2;
        em.persist(account1);
        em.getTransaction().begin();
        account1.setAccountNr(234L);
        em.getTransaction().commit();
        account2 = em.find(Account.class, account1.getId());
        em.persist(account2);
        account1.setAccountNr(345L);
        assertTrue(em.contains(account2));
        assertNotNull(account1.getId());
        assertSame(account1, account2);
        assertNotNull(account1.getId());
    }

    @Test
    public void test5() {
            em.getTransaction().begin();
            Long accountNr = 111L;
            Account account, account2;
            account = new Account(accountNr);
            em.persist(account);
            account.setBalance(1000L);
            em.getTransaction().commit();
            account2 = new Account(accountNr);
            account2.setBalance(2000L);
            em1.getTransaction().begin();
            em1.merge(account2);
            em1.getTransaction().commit();
            int nrRecordsInDatabase = TestUtil.getNrOfAccountRecordsInDB();
            assertEquals(1, nrRecordsInDatabase);
            assertNotSame(account, account2);
            assertFalse(account.equals(account2));
    }

}
