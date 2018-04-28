package service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import entity.Dictionary;
import entity.StudentInfo;

@Transactional 
@Component(value = "studentInfoService")
public class StudentInfoService implements IStudentInfoService{

	@Autowired
	private SessionFactory sessionFactory;
	
	

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void saveOrUpdate(StudentInfo studentInfo) {
		getSession().saveOrUpdate(studentInfo);
	}

	@Override
	public void delete(StudentInfo studentInfo) {
		getSession().delete(studentInfo);
	}

	
	@Override
	public List<StudentInfo> findAll() {
		String sql="select * from t_stuinfo";
		Query<StudentInfo> query = getSession().createNativeQuery(sql,StudentInfo.class);
		return query.getResultList();
	}

	@Override
	public StudentInfo findById(int id) {
		String sql="select * from t_stuinfo where id=:id";
		Query<StudentInfo> query = getSession().createNativeQuery(sql,StudentInfo.class).setParameter("id", id);
		StudentInfo studentinfo = (StudentInfo) query.uniqueResult();
		return studentinfo;
	}

	@Override
	public Session getSession() {
		return getSessionFactory().getCurrentSession();
	}

	
	@Override
	public List<Dictionary> findByPId(int parentId) {
		String sql = "select * from system_dictionary where parentId = :parentId";
		Query<Dictionary> query = getSession().createNativeQuery(sql,Dictionary.class).setParameter("parentId", parentId);
		return query.getResultList();
	}

	@Override
	public StudentInfo checkLogin(String stu_id) {
		String sql = "select * from t_stuinfo where stu_id = :stu_id";
		Query<StudentInfo> query = getSession().createNativeQuery(sql,StudentInfo.class).setParameter("stu_id", stu_id);
		return  query.uniqueResult();
	}

	@Override
	public List<StudentInfo> findByKeyWords(String keywords) {
		String sql="select * from t_stuinfo where stu_name like :stu_name or stu_id like :stu_id";
		if(StringUtils.isEmpty(keywords)||StringUtils.isBlank(keywords)) {
			keywords = "";
		}
		Query<StudentInfo> query = getSession().createNativeQuery(sql,StudentInfo.class).setParameter("stu_name", "%"+keywords+"%").setParameter("stu_id", "%"+keywords+"%");
		return query.getResultList();
	}


}
