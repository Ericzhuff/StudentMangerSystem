package service;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import entity.Score;

@Transactional 
@Component(value = "scoreService")
public class ScoreService implements IScoreService{

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public void saveOrUpdate(Score score ) {
		getSession().saveOrUpdate(score);
	}

	@Override
	public void delete(Score score) {
		getSession().delete(score);
	}

	@Override
	public List<Score> findAll() {
		String sql = "select * from t_score";
		Query<Score> query = getSession().createNativeQuery(sql,Score.class);
		return query.list();
	}

	@Override
	public Score findById(int id) {
		String sql = "select * from t_score where id = :id";
		Query<Score> query = getSession().createNativeQuery(sql,Score.class).setParameter("id", id);
		return (Score) query.uniqueResult();
	}

	@Override
	public Session getSession() {
		return getSessionFactory().getCurrentSession();
	}

	@Override
	public List<Score> findByKeyWords(String keywords) {
		String sql = "select * from t_score where stu_name = :stu_name or tch_name = :tch_name";
		Query<Score> query = getSession().createNativeQuery(sql,Score.class).
				setParameter("stu_name", keywords).setParameter("tch_name", keywords);
		return query.list();
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public Score findByFilter(String stu_id, String course_id) {
		String sql = "select * from t_score where stu_id = :stu_id and course_id = :course_id";
		Query<Score> query = getSession().createNativeQuery(sql,Score.class).setParameter("stu_id", stu_id).setParameter("course_id", course_id);
		return (Score) query.uniqueResult();
	}

	
	@Override
	public List<Score> findByCourse(String course_id) {
		String sql = "select * from t_score where course_id = :course_id";
		Query<Score> query = getSession().createNativeQuery(sql,Score.class).setParameter("course_id",course_id);
		return query.list();
	}

	@Override
	public List<Score> findByStudent(String stu_id) {
		String sql = "select * from t_score where stu_id = :stu_id";
		Query<Score> query = getSession().createNativeQuery(sql, Score.class).setParameter("stu_id", stu_id);
		return query.list();
	}
	

}
