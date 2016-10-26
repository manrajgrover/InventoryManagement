package inventorymanagement.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import inventorymanagement.entities.History;

@Repository
@Transactional
public class HistoryDaoImpl implements HistoryDaoInterface {

	@Autowired
	private SessionFactory sessionFactory;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public void save(History history) {
		getSession().save(history);
	}

	@Override
	public void update(History history) {
		getSession().update(history);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<History> getAll() {
		return getSession().createCriteria(History.class).list();
	}

	@Override
	public History getById(int id) {
		return (History) getSession().get(History.class, id);
	}
}