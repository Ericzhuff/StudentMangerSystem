package service;

import java.util.List;

import entity.Score;

public interface IScoreService extends IBaseService<Score>{
		public Score findByFilter(String stu_id,String course_id);
		
		public List<Score> findByCourse(String course_id);
}
