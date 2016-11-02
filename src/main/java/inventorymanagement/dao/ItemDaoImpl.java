package inventorymanagement.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import inventorymanagement.entities.Item;

@Repository
public class ItemDaoImpl implements ItemDaoInterface {

  @Autowired
  private SessionFactory sessionFactory;

  private Session getSession() {
    return sessionFactory.getCurrentSession();
  }

  @Override
  public void save(Item item) {
    getSession().save(item);
  }

  @Override
  public void delete(Item item) {
    getSession().delete(item);
  }

  @Override
  public void update(Item item) {
    getSession().update(item);
  }

  @Override
  public void refresh(Item item) {
    getSession().refresh(item);
  }

  @Override
  public void flush() {
    getSession().flush();
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<Item> getAll() {
    return getSession().createCriteria(Item.class).list();
  }

  @Override
  public Item getByItemTag(String tag) {
    DetachedCriteria criteria = DetachedCriteria.forClass(Item.class);
    criteria.add(Restrictions.eq("tag", tag));
    Criteria executableCriteria = criteria.getExecutableCriteria(getSession());
    return (Item) executableCriteria.uniqueResult();
  }

  @Override
  public int getCountByProductId(int id) {
    DetachedCriteria criteria = DetachedCriteria.forClass(Item.class);
    criteria = criteria.add(Restrictions.eq("available", "Yes"));
    DetachedCriteria productCriteria = criteria.createCriteria("product");
    productCriteria.add(Restrictions.eq("id", id));
    @SuppressWarnings("unchecked")
    List<Item> items = (List<Item>) productCriteria.getExecutableCriteria(getSession()).list();
    int count = items.size();
    return count;
  }

  @Override
  public List<Item> getItemsByProductId(int id) {
    DetachedCriteria criteria = DetachedCriteria.forClass(Item.class);
    DetachedCriteria productCriteria = criteria.createCriteria("product");
    productCriteria.add(Restrictions.eq("id", id));
    @SuppressWarnings("unchecked")
    List<Item> items = (List<Item>) productCriteria.getExecutableCriteria(getSession()).list();
    return items;
  }

  @Override
  public Item getById(int id) {
    return getSession().get(Item.class, id);
  }

  @Override
  public void clear() {
    getSession().clear();
  }
}
