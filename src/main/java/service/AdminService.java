package service;

import java.io.IOException;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import entity.Admin;

@Transactional 
@Component(value = "adminService")
public class AdminService implements IAdminService{
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void saveOrUpdate(Admin admin) {
		getSession().saveOrUpdate(admin);
	}

	@Override
	public void delete(Admin admin) {
		getSession().delete(admin);
	}

	
	@Override
	public List<Admin> findAll() {
		String sql = "select * from t_admin";
		Query<Admin> query = getSession().createNativeQuery(sql,Admin.class);
		return query.list();
	}

	@Override
	public Admin findById(int id) {
		String sql = "select * from t_admin where id=:id";
		Query<Admin> query = getSession().createNativeQuery(sql, Admin.class).setParameter("id", id);
		Admin admin =  (Admin) query.uniqueResult();
		return admin;
	}
 
	@Override
	public Admin checkLogin(String username) throws IOException {
		String sql = "select * from t_admin where username = :username ";
		Query<Admin> query = getSession().createNativeQuery(sql, Admin.class).setParameter("username", username);
		Admin result =  (Admin) query.uniqueResult();
		return result;
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
	public List<Admin> findByKeyWords(String keywords) {
		// TODO Auto-generated method stub
		return null;
	}

	

}
