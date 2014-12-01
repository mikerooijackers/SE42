/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank.domain;

import bank.dao.AccountDAOJPAImpl;
import java.sql.SQLException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import util.DatabaseCleaner;

/**
 *
 * @author Mike
 */
public class AccountTest {

    EntityManagerFactory emf;
    EntityManager em;

    DatabaseCleaner dc;

    public AccountTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() throws SQLException {
        emf = Persistence.createEntityManagerFactory("bankPU");

        em = emf.createEntityManager();
        dc = new DatabaseCleaner(em);
        dc.clean();
    }

    @After
    public void tearDown() {

    }

    /**
     * maakt account object aan transactie begint schrijft account object weg
     * account is null controle schrijft de data weg naar database print account
     * id controleert accountid > 0
     */
    @Test
    public void testAccount() {
        Account account = new Account(111L);
        em.getTransaction().begin();
        em.persist(account);
        //TODO: verklaar en pas eventueel aan
        //INSERT INTO ACCOUNT (ACCOUNTNR, BALANCE, THRESHOLD) VALUES (?, ?, ?)
        //bind => [111, 0, 0]
        assertNull(account.getId());
        em.getTransaction().commit();
        System.out.println("AccountId: " + account.getId());
        //TODO: verklaar en pas eventueel aan
        //SELECT LAST_INSERT_ID()
        //AccountId: 1

        assertTrue(account.getId() > 0L);

    }

    /**
     * maakt account object transactie begint schrijft account controle account
     * null draai alles terug maakt accountDAOJPAImpl object aan controle of
     * alles terug gedraait is dan komt er 1 uit omdat er een in stond
     */
    @Test
    public void testRollback() {

        Account account = new Account(111L);
        em.getTransaction().begin();
        em.persist(account);
        assertNull(account.getId());
        em.getTransaction().rollback();
        // TODO code om te testen dat table account geen records bevat. Hint: bestudeer/gebruik AccountDAOJPAImpl
        AccountDAOJPAImpl accountDAOJPAImpl = new AccountDAOJPAImpl(em);

        assertEquals(accountDAOJPAImpl.count(), 0);

        //INSERT INTO ACCOUNT (ACCOUNTNR, BALANCE, THRESHOLD) VALUES (?, ?, ?)
        //bind => [111, 0, 0]
    }

    /**
     * expected = -100 maak account object aan set account id -100 begin
     * transactie schrijfweg account object controle of expected niet gelijk is
     * aan accountid em leeggooien controleren of expected gelijk is aan
     * accountid transactie wegschrijven
     */
    @Test
    public void testFlush() {

        Long expected = -100L;
        Account account = new Account(111L);
        account.setId(expected);
        em.getTransaction().begin();
        em.persist(account);
        //TODO: verklaar en pas eventueel aan
        assertEquals(expected, account.getId());
        em.flush();
        //TODO: verklaar en pas eventueel aan
        assertNotEquals(expected, account.getId());
        em.getTransaction().commit();
        //TODO: verklaar en pas eventueel aan
    }

    /*
    
     De waarde van account.getBalance is 400. 
     eerst is de balance 114 daarna wordt hij geset naar 400
     De found.getBalance is 400L.
     */
    @Test
    public void testPersistance() {

        Long expectedBalance = 400L;
        Account account = new Account(114L);
        em.getTransaction().begin();
        em.persist(account);
        account.setBalance(expectedBalance);
        em.getTransaction().commit();
        assertEquals(expectedBalance, account.getBalance());

//TODO: verklaar de waarde van account.getBalance
        Long acId = account.getId();
        account = null;
        EntityManager em2 = emf.createEntityManager();
        em2.getTransaction().begin();
        Account found = em2.find(Account.class, acId);
//TODO: verklaar de waarde van found.getBalance
        assertEquals(expectedBalance, found.getBalance());

    }

    @Test
    public void testRefresh() {
        Long expectedBalance = 400L;
        Account account = new Account(114L);
        em.getTransaction().begin();
        em.persist(account);
        account.setBalance(expectedBalance);
    
        assertEquals(expectedBalance, account.getBalance());
        Account testAccount = new Account(115L);
//TODO: verklaar de waarde van account.getBalance
        
      
        
      
        testAccount.setBalance(expectedBalance);
        em.persist(testAccount);
        em.getTransaction().commit();
        Long acId = testAccount.getId();
        EntityManager em2 = emf.createEntityManager();
        em2.getTransaction().begin();
        Account found = em2.find(Account.class, acId);  
    
//TODO: verklaar de waarde van found.getBalance
        assertEquals(expectedBalance, found.getBalance());
    }

    @Test
    public void testFindAndClear() {
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
        accF1 = em.find(Account.class, acc1.getId());
        em.clear();
        accF2 = em.find(Account.class, acc1.getId());
        assertNotSame(accF1, accF2);
//TODO verklaar verschil tussen beide scenario's
        // Bij het tweede scenario cleart hij heel de datbase waardoor account1 niet meer bestaat en assertsame zal falen.
    }

    @Test
    public void testMerge() {

        Account acc = new Account(1L);
        Account acc2 = new Account(2L);
        Account acc9 = new Account(9L);

        /*
         begin transatie
         vergelijk account met 1L
         schrijf weg
         set balance naar 100
         vergelijk account met 100L
         commit waarde
         */
        // scenario 1
        Long balance1 = 100L;
        em.getTransaction().begin();
        

        em.persist(acc);
        assertEquals((Long) 1L, acc.getBalance());
        acc.setBalance(balance1);
        assertEquals((Long)100L, acc.getBalance());
        em.getTransaction().commit();
        //TODO: voeg asserties toe om je verwachte waarde van de attributen te verifieren.
        //TODO: doe dit zowel voor de bovenstaande java objecten als voor opnieuw bij de entitymanager opgevraagde objecten met overeenkomstig Id.

        // scenario 2
        Long balance2a = 211L;
        acc = new Account(2L);
        em.getTransaction().begin();
        assertEquals((Long)2L, acc.getBalance());
        acc9 = em.merge(acc);
        acc.setBalance(balance2a);
        assertEquals((Long)211L, acc9.getBalance());
        acc9.setBalance(balance2a + balance2a);
        assertEquals((Long)422L, acc9.getBalance());
        em.getTransaction().commit();
        //TODO: voeg asserties toe om je verwachte waarde van de attributen te verifiëren.
        //TODO: doe dit zowel voor de bovenstaande java objecten als voor opnieuw bij de entitymanager opgevraagde objecten met overeenkomstig Id. 
        // HINT: gebruik acccountDAO.findByAccountNr

        // scenario 3
        Long balance3b = 322L;
        Long balance3c = 333L;
        acc = new Account(3L);
        em.getTransaction().begin();
        acc2 = em.merge(acc);
        assertTrue(em.contains(acc)); // verklaar kijken of het bestaat
        assertTrue(em.contains(acc2)); // verklaar kijken of het bestaat
        assertEquals(acc, acc2);  //verklaar kijken of het gelijk is aan elkaar
        acc2.setBalance(balance3b);
        acc.setBalance(balance3c);
        em.getTransaction().commit();
        //TODO: voeg asserties toe om je verwachte waarde van de attributen te verifiëren.
        //TODO: doe dit zowel voor de bovenstaande java objecten als voor opnieuw bij de entitymanager opgevraagde objecten met overeenkomstig Id.

        // scenario 4
        Account account = new Account(114L);
        account.setBalance(450L);
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(account);
        em.getTransaction().commit();

        Account account2 = new Account(114L);
        Account tweedeAccountObject = account2;
        tweedeAccountObject.setBalance(650l);
        assertEquals((Long) 650L, account2.getBalance());  //verklaar kijken of het gelijk is aan 650
        account2.setId(account.getId());
        em.getTransaction().begin();
        account2 = em.merge(account2);
        assertSame(account, account2);  //verklaar kijken of ze het zelfde zijn
        assertTrue(em.contains(account2));  //verklaar kijken of het bestaat
        assertFalse(em.contains(tweedeAccountObject));  //verklaar kijken of het niet bestaat
        tweedeAccountObject.setBalance(850l);
        assertEquals((Long) 650L, account.getBalance());  //verklaar kijken of het gelijk is aan 650
        assertEquals((Long) 650L, account2.getBalance());  //verklaar kijken of het gelijk is aan 650
        em.getTransaction().commit();
        em.close();

    }

    @Test
    public void testRemove() {
        Account acc1 = new Account(88L);
        em.getTransaction().begin();
        em.persist(acc1);
        em.getTransaction().commit();
        Long id = acc1.getId();
        //Database bevat nu een account.

        em.remove(acc1);
        assertEquals(id, acc1.getId()); // kijken of ze gelijk zijn aan elkaar
        Account accFound = em.find(Account.class, id);
        assertNull(accFound); // kijken of het null is.
        //TODO: verklaar bovenstaande asserts

    }
    
}
