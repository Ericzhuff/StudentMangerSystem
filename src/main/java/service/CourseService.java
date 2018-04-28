package service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import entity.Course;

@Transactional 
@Component(value = "courseService")
public class CourseService implements ICourseService{
	
	@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void saveOrUpdate(Course course) {
		String sql = "SET FOREIGN_KEY_CHECKS=0";
		getSession().createNativeQuery(sql).executeUpdate();
		getSession().saveOrUpdate(course);
		sql = "SET FOREIGN_KEY_CHECKS=1";
		getSession().createNativeQuery(sql).executeUpdate();
	}

	@Override
	public void delete(Course course) {
		getSession().delete(course);
	}
	
	@Override
	public List<Course> findAll() {
		String sql = "select * from t_course";
		Query<Course> query = getSession().createNativeQuery(sql,Course.class);
		return query.getResultList();
	}

	@Override
	public Course findById(int id) {
		String sql = "select * from t_course where id = :id";
		Query<Course> query = getSession().createNativeQuery(sql,Course.class).setParameter("id", id);
		return query.getSingleResult();
	}

	@Override
	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public List<Course> findByKeyWords(String keywords) {
		String sql="select * from t_course where course_name like :course_name or course_id like :course_id";
		if(StringUtils.isEmpty(keywords)||StringUtils.isBlank(keywords)) {
			keywords = "";
		}
		
		Query<Course> query = getSession().createNativeQuery(sql,Course.class).setParameter("course_name", "%"+keywords+"%").setParameter("course_id", "%"+keywords+"%");
		return query.getResultList();
	}

	@Override
	public List<Course> findCourseByTeacher(int tch_id) {
		String sql = "select * from t_course where teacher_id = :teacher_id";
		Query<Course> query = getSession().createNativeQuery(sql,Course.class).setParameter("teacher_id", tch_id);
		return query.getResultList();
	}

	@Override
	public List<Course> findCourseTopTen() {
		String sql = "select * from t_course order by id desc limit 0,10";
		Query<Course> query = getSession().createNativeQuery(sql,Course.class);
		return query.getResultList();
	}


	
	
}
