package action;

import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;

import entity.Admin;
import entity.StudentInfo;
import entity.Teacher;
import service.IAdminService;
import service.IStudentInfoService;
import service.ITeacherService;

@ParentPackage("json-default")
@Namespace(value="/checkLogin")
public class CheckLoginAction {

	@Autowired(required = true)
	private IAdminService adminService;

	private Admin admin; //页面上传过来的对象

	private String id;

	private String code;//输入的验证码

	private String userName;//用户名

	private Map<String,Object> msg = new HashMap<String,Object>();//后台返回信息

	private String type;//返回页面的type

	private String name;//名称

	private ActionContext actionContext = ActionContext.getContext();  

	private Map<String,Object> session = actionContext.getSession(); 

	private String image;
	
	private String appUsername;//app中的用户名
	
	private String appPassword;//app中的密码
	
	private String appToken;//密钥
	
	private Map<String, Object> result = new HashMap<>();

	@Autowired
	private IStudentInfoService studentInfoService;

	@Autowired
	private ITeacherService teacherService;



	@Action(value="checkLogin",results = {
			@Result(name = "success",type="json",params={"root","result"}),
			@Result(name = "error",type="json",params={"root","result"}),
			@Result(name = "input",type="json",params={"root","result"})}
			)
	public String checkLogin() {
		try {
			if(admin != null) {
				Admin adminResult = adminService.checkLogin(admin.getUsername());
				StudentInfo studentInfo = studentInfoService.checkLogin(admin.getUsername());
				Teacher teacher = teacherService.checkLogin(admin.getUsername());
				if(adminResult == null && studentInfo == null && teacher == null) {
					result.put("state", false);
					result.put("msg", "用户名无效!");
					return "error";
				}else if((adminResult != null&&!adminResult.getPassword().equals(admin.getPassword()))
						||(studentInfo != null && !studentInfo.getPassword().equals(admin.getPassword()))
						||teacher != null && !teacher.getPassword().equals(admin.getPassword())) {
					result.put("state", false);
					result.put("msg", "密码错误!");
					return "error";
				}else if(adminResult != null && adminResult.getPassword().equals(admin.getPassword())){
					session.clear();
					session.put("name", adminResult.getName());
					session.put("type", adminResult.getType());
					session.put("id", Integer.toString(adminResult.getId()));
					session.put("userName", adminResult.getUsername());
					session.put("image", adminResult.getImage());
					result.put("state", true);
					result.put("msg", "超级管理员登录成功!");
					return "success";
				}else if(studentInfo != null && studentInfo.getPassword().equals(admin.getPassword())){
					session.clear();
					session.put("name", studentInfo.getStu_name());
					session.put("type", studentInfo.getType());
					session.put("id", Integer.toString(studentInfo.getId()));
					session.put("userName", studentInfo.getStu_id());
					session.put("image", studentInfo.getImage());
					result.put("state", true);
					result.put("msg", "学生用户登录成功!");
					return "success";
				}else if(teacher != null && teacher.getPassword().equals(admin.getPassword())) {
					session.clear();
					session.put("name", teacher.getTch_name());
					session.put("type", teacher.getType());
					session.put("id", Integer.toString(teacher.getId()));
					session.put("userName", teacher.getTch_id());
					session.put("image", teacher.getImage());
					result.put("state", true);
					result.put("msg", "教师用户登录成功!");
					return "success";
				}else {
					return "input";
				}
				
			}else {
				return "input";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "input";
		}

	}

	
	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Map<String, Object> getMsg() {
		return msg;
	}

	public void setMsg(Map<String, Object> msg) {
		this.msg = msg;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public IAdminService getAdminService() {
		return adminService;
	}

	public void setAdminService(IAdminService adminService) {
		this.adminService = adminService;
	}

	public IStudentInfoService getStudentInfoService() {
		return studentInfoService;
	}

	public void setStudentInfoService(IStudentInfoService studentInfoService) {
		this.studentInfoService = studentInfoService;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	public ITeacherService getTeacherService() {
		return teacherService;
	}

	public void setTeacherService(ITeacherService teacherService) {
		this.teacherService = teacherService;
	}

	public String getAppUsername() {
		return appUsername;
	}

	public void setAppUsername(String appUsername) {
		this.appUsername = appUsername;
	}

	public String getAppPassword() {
		return appPassword;
	}

	public void setAppPassword(String appPassword) {
		this.appPassword = appPassword;
	}

	public Map<String, Object> getResult() {
		return result;
	}

	public void setResult(Map<String, Object> result) {
		this.result = result;
	}

	public String getAppToken() {
		return appToken;
	}

	public void setAppToken(String appToken) {
		this.appToken = appToken;
	}

	

}
