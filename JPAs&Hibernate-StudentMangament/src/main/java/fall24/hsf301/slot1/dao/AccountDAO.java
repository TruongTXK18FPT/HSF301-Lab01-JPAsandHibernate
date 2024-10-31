package fall24.hsf301.slot1.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import fall24.hsf301.slot1.pojo.Accounts;

public class AccountDAO {
    private static EntityManager em;
    private static EntityManagerFactory emf;

    public AccountDAO(String persistenceUnitName) {
        emf = Persistence.createEntityManagerFactory(persistenceUnitName);
    }

    public Accounts findByUserNameAndPassword(String userName, String password) {
        Accounts account = null;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();
            
            // Use TypedQuery for secure parameter binding and query execution
            TypedQuery<Accounts> query = em.createQuery(
                    "SELECT a FROM Accounts a WHERE a.userName = :username AND a.password = :password", Accounts.class);
            query.setParameter("username", userName);
            query.setParameter("password", password);
            
            // Get the single result, if available
            account = query.getSingleResult();
            em.getTransaction().commit();
        } catch (NoResultException ex) {
            System.out.println("No account found for the given username and password.");
        } catch (Exception ex) {
            System.out.println("Error " + ex.getMessage());
        } finally {
            em.close();
        }
        return account;
    }
}
