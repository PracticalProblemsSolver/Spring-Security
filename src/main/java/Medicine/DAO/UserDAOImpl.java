package Medicine.DAO;

import Medicine.Entities.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class UserDAOImpl implements UserDAO{
    private SessionFactory session;

    @Autowired
    public void setSession(SessionFactory session) {
        this.session = session;
    }

    public void addUser(User user) {
        Transaction transaction = null;
        try (Session newSession = this.session.openSession()) {
            transaction = newSession.beginTransaction();
            newSession.save(user);
            transaction.commit();
            newSession.close();
        } catch (HibernateException hibernateException) {
            hibernateException.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }


    public List<User> getUserList() {
        try (Session newSession = this.session.openSession()){
            CriteriaBuilder criteriaBuilder = newSession.getCriteriaBuilder();
            CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
            Root<User> userRoot = criteriaQuery.from(User.class);
            criteriaQuery.select(userRoot).where();
            Query selectQuery = newSession.createQuery(criteriaQuery);
            return (List<User>) selectQuery.getResultList();
        }
        catch (HibernateException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public boolean userExist(String username) {
        List<User> userList = this.getUserList();
        for (User user: userList) {
            if(user.getUsername().equals(username)){
                return true;
            }
        }
        return false;
    }

    public User findByLogin(String login) {
        List<User> userList = this.getUserList();
        for (User user: userList) {
            if(user.getUsername().equals(login)) {
                return user;
            }
        }
        return null;
    }

}
