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
public class JPATest {
    
    public JPATest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
//1	Hoe werken persist en commit in samenhang met de database.
    @Test
    public void persistCommitDatabaseTest() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("bankPU"); 
        EntityManager em = emf.createEntityManager();

        Account account = new Account(111L);
        em.getTransaction().begin();
        em.persist(account);
        //TODO: verklaar en pas eventueel aan
        assertNull(account.getId());
        em.getTransaction().commit();
        System.out.println("AccountId: " + account.getId());
        //TODO: verklaar en pas eventueel aan
        assertTrue(account.getId() > 0L);
       
        
        //clean database and delete entities.
        DatabaseCleaner dbc = new DatabaseCleaner(em);
        try {
            dbc.clean();
        } catch (SQLException ex) {
            Logger.getLogger(JPATest.class.getName()).log(Level.SEVERE, null, ex);
        }   
/*
1.	Wat is de waarde van asserties en printstatements? Corrigeer verkeerde asserties zodat de test ‘groen’ wordt.
2.	Welke SQL statements worden gegenereerd?
3.	Wat is het eindresultaat in de database?
4.	Verklaring van bovenstaande drie observaties.
*/
    }
	
//2	Rollback 
    @Test
    public void rollbackTest() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("bankPU"); 
        EntityManager em = emf.createEntityManager();
        
        Account account = new Account(111L);
        em.getTransaction().begin();
        em.persist(account);
        assertNull(account.getId());
        em.getTransaction().rollback();
        // TODO code om te testen dat table account geen records bevat. Hint: bestudeer/gebruik AccountDAOJPAImpl
        
        //clean database and delete entities.
        DatabaseCleaner dbc = new DatabaseCleaner(em);
        try {
            dbc.clean();
        } catch (SQLException ex) {
            Logger.getLogger(JPATest.class.getName()).log(Level.SEVERE, null, ex);
        }   
/*
1.	Wat is de waarde van asserties en printstatements? Corrigeer verkeerde asserties zodat de test ‘groen’ wordt.
2.	Welke SQL statements worden gegenereerd?
3.	Wat is het eindresultaat in de database?
4.	Verklaring van bovenstaande drie observaties.
*/
    }
	
//3	Flushen maar
    @Test
    public void flushTest() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("bankPU"); 
        EntityManager em = emf.createEntityManager();
        
	Long expected = -100L;
        Account account = new Account(111L);
        account.setId(expected);
        em.getTransaction().begin();
        em.persist(account);
        //TODO: verklaar en pas eventueel aan
        //assertNotEquals(expected, account.getId();
        em.flush();
        //TODO: verklaar en pas eventueel aan
        //assertEquals(expected, account.getId();
        em.getTransaction().commit();
        //TODO: verklaar en pas eventueel aan
        
        
        //clean database and delete entities.
        DatabaseCleaner dbc = new DatabaseCleaner(em);
        try {
            dbc.clean();
        } catch (SQLException ex) {
            Logger.getLogger(JPATest.class.getName()).log(Level.SEVERE, null, ex);
        }
/*
1.	Wat is de waarde van asserties en printstatements? Corrigeer verkeerde asserties zodat de test ‘groen’ wordt.
2.	Welke SQL statements worden gegenereerd?
3.	Wat is het eindresultaat in de database?
4.	Verklaring van bovenstaande drie observaties.
*/
    }
	
//4	Veranderingen na de persist
    @Test
    public void changesAfterPersistTest() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("bankPU"); 
        EntityManager em = emf.createEntityManager();
        
        Long expectedBalance = 400L;
        Account account = new Account(114L);
        em.getTransaction().begin();
        em.persist(account);
        account.setBalance(expectedBalance);
        em.getTransaction().commit();
        assertEquals(expectedBalance, account.getBalance());
        //TODO: verklaar de waarde van account.getBalance
        Long  cid = account.getId();
        account = null;
        EntityManager em2 = emf.createEntityManager();
        em2.getTransaction().begin();
        Account found = em2.find(Account.class,  cid);
        //TODO: verklaar de waarde van found.getBalance
        assertEquals(expectedBalance, found.getBalance());
        
        //clean database and delete entities.
        DatabaseCleaner dbc = new DatabaseCleaner(em);
        try {
            dbc.clean();
        } catch (SQLException ex) {
            Logger.getLogger(JPATest.class.getName()).log(Level.SEVERE, null, ex);
        }
/*
1.	Wat is de waarde van asserties en printstatements? Corrigeer verkeerde asserties zodat de test ‘groen’ wordt.
2.	Welke SQL statements worden gegenereerd?
3.	Wat is het eindresultaat in de database?
4.	Verklaring van bovenstaande drie observaties.
*/
    }
	
//5	Refresh
//	In de vorige opdracht verwijzen de objecten account en found naar dezelfde rij in de database. Pas een van de objecten aan, persisteer naar de database. Refresh vervolgens het andere object om de veranderde state uit de database te halen. Test met asserties dat dit gelukt is.
    @Test
    public void refreshTest() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("bankPU"); 
        EntityManager em = emf.createEntityManager();
        
        //clean database and delete entities.
        DatabaseCleaner dbc = new DatabaseCleaner(em);
        try {
            dbc.clean();
        } catch (SQLException ex) {
            Logger.getLogger(JPATest.class.getName()).log(Level.SEVERE, null, ex);
        }
/*
1.	Wat is de waarde van asserties en printstatements? Corrigeer verkeerde asserties zodat de test ‘groen’ wordt.
2.	Welke SQL statements worden gegenereerd?
3.	Wat is het eindresultaat in de database?
4.	Verklaring van bovenstaande drie observaties.
*/
    }
    
    
//6	Merge
//	Merge is een van de lastigere methoden uit JPA api. Het is belangrijk dat je deze opgave daarom zorgvuldig uitvoert.
    @Test
    public void mergeTest() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("bankPU"); 
        EntityManager em = emf.createEntityManager();
        
        Account acc = new Account(1L);
        Account acc2 = new Account(2L);
        Account acc9 = new Account(9L);
        
        // scenario 1
        Long balance1 = 100L;
        em.getTransaction().begin();
        em.persist(acc);
        acc.setBalance(balance1);
        em.getTransaction().commit();
        //TODO: voeg asserties toe om je verwachte waarde van de attributen te verifieren.
        //TODO: doe dit zowel voor de bovenstaande java objecten als voor opnieuw bij de entitymanager opgevraagde objecten met overeenkomstig Id.

        // scenario 2
        Long balance2a = 211L;
        acc = new Account(2L);
        em.getTransaction().begin();
        acc9 = em.merge(acc);
        acc.setBalance(balance2a);
        acc9.setBalance(balance2a+balance2a);
        em.getTransaction().commit();
        //TODO: voeg asserties toe om je verwachte waarde van de attributen te verifiëren.
        //TODO: doe dit zowel voor de bovenstaande java objecten als voor opnieuw bij de entitymanager opgevraagde objecten met overeenkomstig Id. 
        // HINT: gebruik acccountDAO.findByAccountNr

        // scenario 3
        Long balance3b = 322L;
        Long balance3c = 333L;
        acc = new Account(3L);

        em.getTransaction().begin();
        em.persist(acc);
        em.getTransaction().commit();       
        
        em.getTransaction().begin();
        acc2 = em.merge(acc);
        assertTrue(em.contains(acc)); // verklaar
        assertTrue(em.contains(acc2)); // verklaar
        assertEquals(acc,acc2);  //verklaar
        acc2.setBalance(balance3b);
        acc.setBalance(balance3c);
        em.getTransaction().commit() ;
        //TODO: voeg asserties toe om je verwachte waarde van de attributen te verifiëren.
        //TODO: doe dit zowel voor de bovenstaande java objecten als voor opnieuw bij de entitymanager opgevraagde objecten met overeenkomstig Id.

        
        // scenario 4
        Account account = new Account(114L) ;
        account.setBalance(450L) ;
        em = emf.createEntityManager() ;
        em.getTransaction().begin() ;
        em.persist(account) ;
        em.getTransaction().commit() ;
        
        //verklaar
        Account account2 = new Account(114L) ;
        Account tweedeAccountObject = account2 ;
        tweedeAccountObject.setBalance(650l) ;
        assertEquals((Long)650L,account2.getBalance()) ;  //verklaar
        account2.setId(account.getId()) ;
        em.getTransaction().begin() ;
        account2 = em.merge(account2) ;
        assertSame(account,account2) ;  //verklaar
        assertTrue(em.contains(account2)) ;  //verklaar
        assertFalse(em.contains(tweedeAccountObject)) ;  //verklaar
        tweedeAccountObject.setBalance(850l) ;
        assertEquals((Long)650L,account.getBalance()) ;  //verklaar
        assertEquals((Long)650L,account2.getBalance()) ;  //verklaar
        em.getTransaction().commit() ;
//        em.close() ;
        
        //clean database and delete entities.
        DatabaseCleaner dbc = new DatabaseCleaner(em);
        try {
            dbc.clean();
        } catch (SQLException ex) {
            Logger.getLogger(JPATest.class.getName()).log(Level.SEVERE, null, ex);
        }
/*
1.	Wat is de waarde van asserties en printstatements? Corrigeer verkeerde asserties zodat de test ‘groen’ wordt.
2.	Welke SQL statements worden gegenereerd?
3.	Wat is het eindresultaat in de database?
4.	Verklaring van bovenstaande drie observaties.
*/
    }

	
//7	Find en clear
    @Test
    public void findClearTest() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("bankPU"); 
        EntityManager em = emf.createEntityManager();
        
	Account acc1 = new Account(77L);
        em.getTransaction().begin();
        em.persist(acc1);
        em.getTransaction().commit();
        //Database bevat nu een account.
        
        // scenario 1        
        Account accF1;
        Account accF2;
        accF1 = em.find(Account.class, acc1.getId());
        accF2 = em.find(Account.class, acc1.getId());
        assertSame(accF1, accF2);

        // scenario 2     
        em.clear();
        accF1 = em.find(Account.class, acc1.getId());
        accF2 = em.find(Account.class, acc1.getId());
        assertSame(accF1, accF2);
        //TODO verklaar verschil tussen beide scenario’s
        
        //clean database and delete entities.
        DatabaseCleaner dbc = new DatabaseCleaner(em);
        try {
            dbc.clean();
        } catch (SQLException ex) {
            Logger.getLogger(JPATest.class.getName()).log(Level.SEVERE, null, ex);
        }
/*
1.	Wat is de waarde van asserties en printstatements? Corrigeer verkeerde asserties zodat de test ‘groen’ wordt.
2.	Welke SQL statements worden gegenereerd?
3.	Wat is het eindresultaat in de database?
4.	Verklaring van bovenstaande drie observaties.
*/
    }

	
//8	Remove 
    @Test
    public void removeTest() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("bankPU"); 
        EntityManager em = emf.createEntityManager();
        
        Account acc1 = new Account(88L);
        em.getTransaction().begin();
        em.persist(acc1);
        em.getTransaction().commit();
        Long id = acc1.getId();
        //Database bevat nu een account.

        em.remove(acc1);
        assertEquals(id, acc1.getId());        
        Account accFound = em.find(Account.class, id);
        assertNull(accFound);
        //TODO: verklaar bovenstaande asserts
        
        //clean database and delete entities.
        DatabaseCleaner dbc = new DatabaseCleaner(em);
        try {
            dbc.clean();
        } catch (SQLException ex) {
            Logger.getLogger(JPATest.class.getName()).log(Level.SEVERE, null, ex);
        }
/*
1.	Wat is de waarde van asserties en printstatements? Corrigeer verkeerde asserties zodat de test ‘groen’ wordt.
2.	Welke SQL statements worden gegenereerd?
3.	Wat is het eindresultaat in de database?
4.	Verklaring van bovenstaande drie observaties.
*/
    }

//9	Generation type
//	Opgave 1 heb je uitgevoerd met @GeneratedValue(strategy = GenerationType.IDENTITY)
//Voer dezelfde opdracht nu uit met GenerationType SEQUENCE en TABLE.
//Verklaar zowel de verschillen in testresultaat als verschillen van de database structuur.
    @Test
    public void generationTypeTest() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("bankPU"); 
        EntityManager em = emf.createEntityManager();
        
        //clean database and delete entities.
        DatabaseCleaner dbc = new DatabaseCleaner(em);
        try {
            dbc.clean();
        } catch (SQLException ex) {
            Logger.getLogger(JPATest.class.getName()).log(Level.SEVERE, null, ex);
        }
/*
1.	Wat is de waarde van asserties en printstatements? Corrigeer verkeerde asserties zodat de test ‘groen’ wordt.
2.	Welke SQL statements worden gegenereerd?
3.	Wat is het eindresultaat in de database?
4.	Verklaring van bovenstaande drie observaties.
*/
    }
	
}
