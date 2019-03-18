package com.dyst.log.aspect;

import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import jodd.datetime.JDateTime;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;
import com.dyst.kafka.service.KafkaService;
import com.dyst.log.annotation.Description;
import com.dyst.log.util.ParamName;
import com.dyst.systemmanage.entities.User;
import com.dyst.utils.CommonUtils;
import com.dyst.utils.StaticUtils;

@Component
@Aspect
public class ControllerAspect {

	private static final String BUSINESS_TOPIC = "business_log";// 业务日志topic
	private String loginName = "";// 用户名
	private String userName = "";// 姓名
	private String deptId = "";//部门ID
	private String deptName = "";//部门名称
	private String ip = "";// ip
	private String requestTime = null;// 请求时间
	private String moduleName = "";// 模块名称
	private String operationName = "";// 操作内容
	private String operateType = "";// 操作类型 0登录 1查询 2新增 3修改 4删除
	private Map<String, Object> inputParamMap = null;// 输入参数
	@Autowired
	private KafkaService kafkaService;

	@After("execution(* com.dyst.*.controller.*.*(..))")
	public void doAfter(JoinPoint joinPoint){
		try {
			// 判断是否有注解信息
			boolean flag = getAnnoMess(joinPoint);
			if(flag){
				// 1.获取request信息2.根据request获取session3.从session中取出登录用户信息
				RequestAttributes ra = RequestContextHolder.getRequestAttributes();
				ServletRequestAttributes sra = (ServletRequestAttributes) ra;
				HttpServletRequest request = sra.getRequest();
				// 从session中获取用户信息
				User user = (User) request.getSession().getAttribute(
						StaticUtils.SESSION_NAME_USER_DATA);
				if (user != null) {
					loginName = user.getLoginName();
					userName = user.getUserName();
					deptId = user.getDeptId();
					deptName = user.getDeptName();
				} else {
					loginName = "";
					userName = "用户未登录";
				}
				// 获取输入参数
				getInputParams(request);
				// 获取ip
				ip = CommonUtils.getIpAddr(request);
				// 请求时间
				requestTime = new JDateTime().toString("YYYY-MM-DD hh:mm:ss");
				// 方法路径
				// methodPath =
				// pjp.getSignature().getDeclaringTypeName()+"."+pjp.getSignature().getName();
				String log = joinStr("#", loginName, userName, ip, moduleName, operationName, JSON.toJSONString(inputParamMap), 
						requestTime, deptId, deptName, operateType);
				// 向kafka发送消息
				kafkaService.sendMessage(BUSINESS_TOPIC, log, "");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取方法注解信息
	 * 
	 * @param joinPoint
	 */
	@SuppressWarnings("unchecked")
	private boolean getAnnoMess(JoinPoint joinPoint) {
		try {
			String targetName = joinPoint.getTarget().getClass().getName();
			String methodName = joinPoint.getSignature().getName();
			Object[] args = joinPoint.getArgs();
			Class targetClass = Class.forName(targetName);
			Method[] methods = targetClass.getDeclaredMethods();
			for (Method method : methods) {
				if (method.getName().equals(methodName)) {
					Class[] clazzs = method.getParameterTypes();
					if (args.length == clazzs.length) {
						if (method.isAnnotationPresent(Description.class)) {
							moduleName = method.getAnnotation(Description.class).moduleName();
							operationName = method.getAnnotation(Description.class).operationName();
							operateType = method.getAnnotation(Description.class).operateType();
							return true;
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 获取request中的参数
	 * 
	 * @param request
	 */
	@SuppressWarnings("unchecked")
	private void getInputParams(HttpServletRequest request) {
		Properties prop = ParamName.getParamNameProps();
		Enumeration enu = request.getParameterNames();
		this.inputParamMap = new HashMap<String, Object>();
		while (enu.hasMoreElements()) {
			String key = (String) enu.nextElement();
			String value = request.getParameter(key);
			String name = prop.getProperty(key);
			if (StringUtils.isNotEmpty(value) && StringUtils.isNotEmpty(name)) {
				this.inputParamMap.put(name, value);
			} else if(!"password".equals(key)){//如果找不到配置且不是密码参数，则显示英文
				this.inputParamMap.put(key, value);
			}
		}
	}

	/**
	 * 按指定的分隔符拼接字符串
	 * 
	 * @param splitStr
	 * @param strs
	 * @return
	 */
	private static String joinStr(String splitStr, String... strs) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < strs.length; i++) {
			sb.append(strs[i]).append(splitStr);
		}
		return sb.substring(0, sb.length() - 1);
	}
}