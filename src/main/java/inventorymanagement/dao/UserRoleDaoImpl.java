package inventorymanagement.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import inventorymanagement.entities.UserRole;

@Repository
public class UserRoleDaoImpl implements UserRoleDaoInterface {

  @Autowired
  private SessionFactory sessionFactory;

  private Session getSession() {
    return sessionFactory.getCurrentSession();
  }

  @Override
  public void save(UserRole user) {
    getSession().save(user);
  }

  @Override
  public void refresh(UserRole userRole) {
    getSession().refresh(userRole);
  }
}
