package service;

import java.util.List;

import org.hibernate.Session;

public interface IBaseService<T> {
	
	public void saveOrUpdate(T t);    //保存
	
	public void delete(T t);    //删除
	
	public List<T> findAll();      //查找全部
	
	public T findById(int id);    //根据id查找
	
	public Session getSession();  //获取Session
	
	public List<T> findByKeyWords(String keywords);//关键字查找
	
}
