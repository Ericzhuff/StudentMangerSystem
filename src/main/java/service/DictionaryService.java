package service;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import entity.Dictionary;

@Transactional 
@Component(value = "dictionaryService")
public class DictionaryService implements IDictionaryServcie{

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public void saveOrUpdate(Dictionary dictionary) {
		getSession().saveOrUpdate(dictionary);
	}

	@Override
	public void delete(Dictionary dictionary) {
		getSession().delete(dictionary);
	}

	@Override
	public List<Dictionary> findAll() {
		String sql = "select * from system_dictionary";
		Query<Dictionary> query = getSession().createNativeQuery(sql,Dictionary.class);
		return query.getResultList();
	}

	@Override
	public Dictionary findById(int id) {
		String sql = "select * from system_dictionary where id = :id";
		Query<Dictionary> query = getSession().createNativeQuery(sql,Dictionary.class).setParameter("id", id);
		return query.getSingleResult();
	}

	
	@Override
	public Session getSession() {
		return getSessionFactory().getCurrentSession();
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public List<Dictionary> findByParentId(String parentId) {
		String sql = "select * from system_dictionary where parentId = :parentId";
		Query<Dictionary> query = getSession().createNativeQuery(sql,Dictionary.class).setParameter("parentId", parentId);
		return query.getResultList();
	}

	@Override
	public List<Dictionary> findByName(String name) {
		String sql = "select * from system_dictionary where name like :name";
		Query<Dictionary> query = getSession().createNativeQuery(sql,Dictionary.class).setParameter("name", "%"+name+"%");
		return query.getResultList();
		
	}

	@Override
	public List<Dictionary> findByKeyWords(String keywords) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Dictionary> findByParentIdList(List<String> parentIdList) {
		String sql = "select * from system_dictionary where parentId in (:parentId)";
		Query<Dictionary> query = getSession().createNativeQuery(sql,Dictionary.class).setParameterList("parentId", parentIdList);
		return query.getResultList();
	}

	@Override
	public Dictionary findByCode(String code) {
		String sql = "select * from system_dictionary where code = :code";
		Query<Dictionary> query = getSession().createNativeQuery(sql,Dictionary.class).setParameter("code", code);
		return query.getSingleResult();
	}


}
