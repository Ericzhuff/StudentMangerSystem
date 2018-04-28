package service;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import entity.Dictionary;
import entity.Teacher;

@Transactional 
@Component(value = "teacherService")
public class TeacherService implements ITeacherService{

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public void saveOrUpdate(Teacher teacher) {
		getSession().saveOrUpdate(teacher);
	}

	@Override
	public void delete(Teacher teacher) {
		getSession().delete(teacher);
	}


	@Override
	public List<Teacher> findAll() {
		String sql = "select * from t_teacher";
		Query<Teacher> query = getSession().createNativeQuery(sql,Teacher.class);
		return query.list();
	}

	@Override
	public Teacher findById(int id) {
		String sql = "select * from t_teacher where id = :id";
		Query<Teacher> query = getSession().createNativeQuery(sql,Teacher.class).setParameter("id", id);
		return (Teacher) query.uniqueResult();
	}

	@Override
	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	
	@Override
	public List<Teacher> findByKeyWords(String keywords) {
		String sql = "select * from t_teacher where tch_name like :tch_name or tch_id like :tch_id";
		Query<Teacher> query = getSession().createNativeQuery(sql,Teacher.class).setParameter("tch_id", "%"+keywords+"%").setParameter("tch_name", "%"+keywords+"%");
		return query.getResultList();
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public List<Dictionary> findByPId(int parentId) {
		String sql = "select * from system_dictionary where parentId = :parentId";
		Query<Dictionary> query = getSession().createNativeQuery(sql,Dictionary.class).setParameter("parentId", parentId);
		return query.list();
	}

	@Override
	public Teacher checkLogin(String tch_id) {
		String sql = "select * from t_teacher where tch_id = :tch_id";
		Query<Teacher> query = getSession().createNativeQuery(sql,Teacher.class).setParameter("tch_id", tch_id);
		return (Teacher) query.uniqueResult();
	}

}
