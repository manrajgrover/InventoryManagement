package in.manrajsingh.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import in.manrajsingh.entities.Product;

@Repository
@Transactional
public class ProductDaoImpl implements ProductDaoInterface {
	
	@Autowired
	private SessionFactory sessionFactory;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public void save(Product product) {
		getSession().save(product);
	}
	
	@Override
	public void delete(Product product) {
		getSession().delete(product);
	}

	@Override
	public void update(Product product) {
		getSession().update(product);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Product> getAll() {
		return getSession().createCriteria(Product.class).list();
	}

	@Override
	public Product getById(int id) {
		return (Product) getSession().get(Product.class, id);
	}

}