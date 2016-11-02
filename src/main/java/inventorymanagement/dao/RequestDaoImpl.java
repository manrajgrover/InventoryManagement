package inventorymanagement.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import inventorymanagement.entities.Item;
import inventorymanagement.entities.Request;

@Repository
public class RequestDaoImpl implements RequestDaoInterface {

  @Autowired
  private SessionFactory sessionFactory;

  private Session getSession() {
    return sessionFactory.getCurrentSession();
  }

  @Override
  public void save(Request request) {
    getSession().save(request);
  }

  @Override
  public void update(Request request) {
    getSession().update(request);
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<Request> getAll() {
    return getSession().createCriteria(Request.class).list();
  }

  @Override
  public Request getById(int id) {
    return (Request) getSession().get(Request.class, id);
  }

  @Override
  public void refresh(Request request) {
    getSession().refresh(request);
  }

  @Override
  public List<Request> getByUserId(int id) {
    DetachedCriteria criteria = DetachedCriteria.forClass(Request.class);
    DetachedCriteria userCriteria = criteria.createCriteria("user");
    userCriteria.add(Restrictions.eq("id", id));
    @SuppressWarnings("unchecked")
    List<Request> requests = (List<Request>) userCriteria.getExecutableCriteria(getSession()).list();
    return requests;
  }

}
