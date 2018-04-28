package interceptor;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class Interceptor extends AbstractInterceptor{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String intercept(ActionInvocation invoker) throws Exception {
		Object id = ActionContext.getContext().getSession().get("id");  
	        if(id == null){  
	        	return "login";  // 这里返回用户登录页面视图  
	        }else {
	        	return invoker.invoke(); 
	        }
	}

}
