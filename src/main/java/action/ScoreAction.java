package action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

import entity.Score;
import service.IScoreService;

@ParentPackage("default")  
@Namespace("/score")
public class ScoreAction extends ActionSupport implements Preparable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	private Score score;
	
	@Autowired
	private IScoreService scoreService;
	
	private Map<String, Object> msg = new HashMap<>();
	
	private List<Score> scoreList;
	
	private String stu_id;
	
	private String course_id;
	

	public Score getScore() {
		return score;
	}
	
	public void setScore(Score score) {
		this.score = score;
	}

	public Map<String, Object> getMsg() {
		return msg;
	}

	public void setMsg(Map<String, Object> msg) {
		this.msg = msg;
	}

	public List<Score> getScoreList() {
		return scoreList;
	}

	public void setScoreList(List<Score> scoreList) {
		this.scoreList = scoreList;
	}

	public String getStu_id() {
		return stu_id;
	}

	public void setStu_id(String stu_id) {
		this.stu_id = stu_id;
	}

	public String getCourse_id() {
		return course_id;
	}

	public void setCourse_id(String course_id) {
		this.course_id = course_id;
	}

	@Override
	public void prepare() throws Exception {
		if(stu_id != null && stu_id != "" && course_id != null && course_id != "") {
			Score scoreNew = scoreService.findByFilter(score.getStu_id(), score.getCourse_id());
			if(scoreNew != null) {
				score = scoreNew;
			}else {
				score = new Score();
			}
		}
		
	}
	@Action(value="save",results = { @Result(name = "save", type="json",params={"root","msg"})})
	public String save() {
		try {
			scoreService.saveOrUpdate(score);
			msg.put("state", true);
			msg.put("msg", "保存成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			msg.put("state", true);
			msg.put("msg", "保存失败");
		}
		return "save";
	}
	@Action(value="list",results = { @Result(name = "list", type="json",params={"root","msg"})})
	public String list() {
		try {
			scoreList = scoreService.findByCourse(course_id);
			msg.put("state", true);
			msg.put("scoreList", scoreList);
		} catch (Exception e) {
			e.printStackTrace();
			msg.put("state", false);
		}
		return "list";
	}
	@Action(value="findByStudent",results= {@Result(name = "findByStudent",type="json",params= {"root","msg"})})
	public String findByStudent() {
		try {
			scoreList = scoreService.findByStudent(stu_id);
			msg.put("state", true);
			msg.put("scoreList", scoreList);
		} catch (Exception e) {
			e.printStackTrace();
			msg.put("state", false);
		}
		return "findByStudent";
	}
}
