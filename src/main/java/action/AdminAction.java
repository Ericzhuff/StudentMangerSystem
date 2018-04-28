package action;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

import entity.Admin;
import entity.StudentInfo;
import entity.Teacher;
import service.IAdminService;
import service.IStudentInfoService;
import service.ITeacherService;
@ParentPackage("default")
@Namespace("/Admin")
public class AdminAction extends ActionSupport implements Preparable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired(required = true)
	private IAdminService adminService;

	private Admin admin; //页面上传过来的对象

	private String id;

	private String code;//输入的验证码

	private String userName;//用户名

	private Map<String,Object> msg = new HashMap<String,Object>();//后台返回信息
	
	private List<Admin> adminList;

	private String type;//返回页面的type

	private String name;//名称

	private ActionContext actionContext = ActionContext.getContext();  

	private Map<String,Object> session = actionContext.getSession(); 

	private String password;//密码

	private HttpServletRequest request= ServletActionContext.getRequest();

	@Autowired
	private IStudentInfoService studentInfoService;

	@Autowired
	private ITeacherService teacherService;

	private String image;

	private File file;

	private String fileFileName;
	@Override
	public void prepare() throws Exception {
		if(id == null ||id.equals("")) {
			admin = new Admin();
		}else {
			admin = adminService.findById(Integer.parseInt(id));
		}
	}
	@Action(value="list",results={@Result(name = "list", type="json",params={"root","adminList"})})
	public String list() {
		adminList = adminService.findAll();
		return "list";
	}
	//文件上传
	@Action(value="upload",results={@Result(name = "upload", type="json",params={"root","image"})})
	public String upload() throws IOException {
		String path = request.getSession().getServletContext().getRealPath("/upload");
		File uploadfile = new File(path);
		if(!file.exists())file.mkdirs();
		String suffixName = fileFileName.substring(fileFileName.lastIndexOf("."));
		String hash = Integer.toHexString(new Random().nextInt());//自定义随机数（字母+数字）作为文件名
		String fileName = hash + suffixName;
		FileUtils.copyFile(file, new File(uploadfile,fileName));
		image = fileName;
		return "upload";
	}

	@Action(value="save",results = {
			@Result(name = "success", type="json",params={"root","msg"})})
	public String save() {
		try {
			adminService.saveOrUpdate(admin);
			if(admin.getType().equals(session.get("type"))) {
				session.put("image", admin.getImage());
			}
			msg.put("state", true);
			msg.put("msg", "保存成功");
		} catch (Exception e) {
			msg.put("state", false);
			msg.put("msg", "保存失败");
			e.printStackTrace();
		}
		return "success";
	}
	@Action(value="edit",results = {
			@Result(name = "success", location = "/admin/page/user/userInfo.jsp")})
	public String edit() {
		return "success";
	}
	@Action(value="view",results = {
			@Result(name = "success", location = "/admin/page/user/userInfoView.jsp")})
	public String view() {
		return "success";
	}
	@Action(value="quit",results = { @Result(name = "quit", location = "/admin/login/login.jsp")})
	public String quit() {
		session.clear();
		return "quit";
	}
	@Action(value="lockScreen",results = { 
			@Result(name = "lockScreen", type="json",params={"root","msg"})})
	public String lockSceen() {
		try {
			if(admin != null) {
				Admin adminResult = adminService.checkLogin(admin.getUsername());
				StudentInfo studentInfo = studentInfoService.checkLogin(admin.getUsername());
				Teacher teacher = teacherService.checkLogin(admin.getUsername());
				if(adminResult != null && adminResult.getPassword().equals(admin.getPassword())){
					msg.put("state", true);
					msg.put("msg", "解锁成功!");
					return "lockScreen";
				}else if(studentInfo != null && studentInfo.getPassword().equals(admin.getPassword())){
					msg.put("state", true);
					msg.put("msg", "解锁成功!");
					return "lockScreen";
				}else if(teacher != null && teacher.getPassword().equals(admin.getPassword())) {
					msg.put("state", true);
					msg.put("msg", "解锁成功!");
					return "lockScreen";
				}else {

					return "lockScreen";
				}
			}else {
				msg.put("state", false);
				msg.put("msg", "系统异常，请联系管理员!");
				return "lockScreen";
			}
		}catch (Exception e) {
			msg.put("state", false);
			msg.put("msg", "系统异常，请联系管理员!");
			return "lockScreen";
		}
	}
	@Action(value="changePwd",results = { 
			@Result(name = "changePwd", type="json",params={"root","msg"})})
	public String changePwd() {
		try {
			if(admin != null) {
				Admin adminResult =  adminService.checkLogin(admin.getUsername());
				StudentInfo studentInfo = studentInfoService.checkLogin(admin.getUsername());
				if(adminResult == null) {
					if(studentInfo != null) {
						studentInfo.setPassword(password);
						studentInfoService.saveOrUpdate(studentInfo);
						msg.put("state", true);
						msg.put("msg", "密码修改成功");
						return "changePwd";
					}else {
						msg.put("state", false);
						msg.put("msg", "旧密码错误");
						return "changePwd";
					}
				}else {
					adminResult.setPassword(password);
					adminService.saveOrUpdate(adminResult);
					msg.put("state", true);
					msg.put("msg", "密码修改成功");
					return "changePwd";
				}
			}else {
				msg.put("state", false);
				msg.put("msg", "旧密码错误");
				return "changePwd";
			}
		} catch (Exception e) {
			msg.put("state", false);
			msg.put("msg", "密码修改失败");
			return "lockScreen";
		}
	}
	public IAdminService getAdminService() {
		return adminService;
	}

	public void setAdminService(IAdminService adminService) {
		this.adminService = adminService;
	}

	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	public List<Admin> getAdminList() {
		return adminList;
	}

	public void setAdminList(List<Admin> adminList) {
		this.adminList = adminList;
	}


}
