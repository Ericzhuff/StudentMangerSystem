package service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import entity.Navs;

@Transactional 
@Component(value = "navsService")
public class NavsService implements INavsService{
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void saveOrUpdate(Navs navs) {
		getSession().saveOrUpdate(navs);
	}

	@Override
	public void delete(Navs navs) {
		getSession().delete(navs);
	}

	@Override
	public List<Navs> findAll() {
		String sql="select * from system_navs order by parentId";
		Query<Navs> query = getSession().createNativeQuery(sql,Navs.class);
		return query.getResultList();
	}

	@Override
	public Navs findById(int id) {
		String sql = "select * from system_navs where id=:id";
		Query<Navs> query = getSession().createNativeQuery(sql,Navs.class).setParameter("id", id);
		Navs navs = (Navs) query.uniqueResult();
		return navs;
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
	public List<Navs> findByType(String type) {
		String sql="select * from system_navs where type like :type order by parentId";
		Query<Navs> query = getSession().createNativeQuery(sql,Navs.class).setParameter("type","%"+type+"%");
		return query.list();
	}

	
	@Override
	public List<Navs> findByKeyWords(String keywords) {
		String sql="select * from system_navs where title like :title";
		if(StringUtils.isEmpty(keywords)||StringUtils.isBlank(keywords)) {
			keywords = "";
		}
		Query<Navs> query = getSession().createNativeQuery(sql,Navs.class).setParameter("title", "%"+keywords+"%");
		return query.list();
	}

	

}
