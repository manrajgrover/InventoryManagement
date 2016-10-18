package in.manrajsingh.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import in.manrajsingh.entities.User;

@Repository
@Transactional
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
	
	@Override
	public void delete(User user) {
		getSession().delete(user);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getAll() {
		return getSession().createQuery("from User").list();
	}
	
	@Override
	public User getByEmail(String email) {
		return (User) getSession()
				.createQuery("from User where email=:email")
				.setParameter("email", email)
				.uniqueResult();
	}
	
	@Override
	public User getId(int id) {
		return (User) getSession().load(User.class, id);
	}
	
	@Override
	public void update(User user) {
		getSession().update(user);
	}
}