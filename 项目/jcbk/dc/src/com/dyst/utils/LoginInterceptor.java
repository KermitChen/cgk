package com.dyst.utils;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.dyst.systemmanage.entities.User;

/**
 * 未登录拦截器 
 * @author cgk
 *
 */
public class LoginInterceptor extends HandlerInterceptorAdapter{
	//排除的请求列表
	private List<String> excludedUrls;
	public List<String> getExcludedUrls() {
		return excludedUrls;
	}
	public void setExcludedUrls(List<String> excludedUrls) {
		this.excludedUrls = excludedUrls;
	}

	/**  
	 * 在业务处理器处理请求之前被调用  
	 * 如果返回false  
	 *     从当前的拦截器往回执行所有拦截器的afterCompletion(),再退出拦截器链 
	 * 如果返回true  
	 *    执行下一个拦截器,直到所有的拦截器都执行完毕  
	 *    再执行被拦截的Controller  
	 *    然后进入拦截器链,  
	 *    从最后一个拦截器往回执行所有的postHandle()  
	 *    接着再从最后一个拦截器往回执行所有的afterCompletion()  
	 */    
	@Override    
	public boolean preHandle(HttpServletRequest request,    
			HttpServletResponse response, Object handler) throws Exception {
		//排除一些请求，不需要拦截
		String requestUri = request.getRequestURI();
        for (String url : excludedUrls) {
            if (requestUri.endsWith(url)) {
                return true;
            }
        }
        
        //免登录标记
        String login = request.getParameter("login");
        if(login != null && "1".equals(login.trim())){
        	return true;
        }

		//获取用户信息
		User user = (User)request.getSession().getAttribute(StaticUtils.SESSION_NAME_USER_DATA);
		if(user == null){
			//跳转页面
			request.getRequestDispatcher("/login.jsp").forward(request, response);
			return false;
		}
		return true;
	}

	/** 
	 * 在业务处理器处理请求执行完成后,生成视图之前执行的动作    
	 * 可在modelAndView中加入数据，比如当前时间 
	 */  
	@Override    
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,    
		ModelAndView modelAndView) throws Exception {
		
	}

	/**  
	 * 在DispatcherServlet完全处理完请求后被调用,可用于清理资源等   
	 * 当有拦截器抛出异常时,会从当前拦截器往回执行所有的拦截器的afterCompletion()  
	 */    
	@Override    
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, 
			Object handler, Exception ex) throws Exception {
		
	}
}
