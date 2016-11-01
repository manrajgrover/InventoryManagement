package inventorymanagement.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import inventorymanagement.entities.User;

@Repository
public class UserDaoImpl implements UserDaoInterface {

  @Autowired
  private SessionFactory sessionFactory;

  private Session getSession() {
    return sessionFactory.getCurrentSession();
  }

  @Override
  public void save(User user) {
    getSession().save(user);
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<User> getAll() {
    return getSession().createCriteria(User.class).list();
  }

  @Override
  public User getUserByEmail(String email) {
    DetachedCriteria criteria = DetachedCriteria.forClass(User.class);

    criteria.add(Restrictions.eq("email", email));
    Criteria executableCriteria = criteria.getExecutableCriteria(getSession());

    return (User) executableCriteria.uniqueResult();
  }

  @Override
  public User getById(int id) {
    return (User) getSession().get(User.class, id);
  }

  @Override
  public void refresh(User user) {
    getSession().refresh(user);
  }
}
