package com.dyst.systemmanage.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jodd.datetime.JDateTime;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.XMLWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import sun.misc.BASE64Encoder;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import com.alibaba.fastjson.JSON;
import com.dyst.BaseDataMsg.entities.Dictionary;
import com.dyst.base.utils.*;
import com.dyst.dispatched.entities.Dispatched;
import com.dyst.dispatched.entities.Withdraw;
import com.dyst.dispatched.service.DispatchedService;
import com.dyst.dispatched.service.WithdrawService;
import com.dyst.log.annotation.Description;
import com.dyst.systemmanage.entities.*;
import com.dyst.systemmanage.service.*;
import com.dyst.utils.CommonUtils;
import com.dyst.utils.Config;
import com.dyst.utils.IntefaceUtils;
import com.dyst.utils.StaticUtils;
import com.dyst.utils.UserOnlineCountUtil;

/**
 * @author： cgk
 * @date：2016-03-02
 * @version：0.0.1
 * @doc：用户信息管理Controller类，主要实现方法：
 *       1.发送短信验证码
 *       2.登录系统
 *       3.退出系统
 *       4.浏览器报告在线情况并获取在线用户数
 *       5.初始化主页
 *       6.初始化用户信息管理页面
 *       7.删除用户信息
 *       8.批量删除用户信息
 *       9.初始化新增用户信息页面
 *       10新增用户信息
 *       11.初始化更新用户信息页面
 *       12.修改用户信息
 *       13.修改密码
 *       14.用户信息详情
 *       15.重置密码
 *       16.初始化批量授权页面
 *       17.批量授权
 */
@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	protected DispatchedService bkService;//布控service
	@Autowired
	protected WithdrawService ckService;//车控service
	
	//注入业务层
	@Resource
	private UserService userService;
	public UserService getUserService() {
		return userService;
	}
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	@Resource
	private RoleService roleService;
	public RoleService getRoleService() {
		return roleService;
	}
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}
	
	//注入service
	private DepartmentService deptService;
	public DepartmentService getDeptService() {
		return deptService;
	}
	@Autowired
	public void setDeptService(DepartmentService deptService) {
		this.deptService = deptService;
	}
	
	/**
	 * 发送短信验证码
	 * @return
	 *      0:获取短信验证码失败！
	 * 		1:发送短信验证码成功！
	 *      2:该用户信息不存在！
	 *      3:该用户未绑定手机号码！
	 */
	@RequestMapping("/sendCode")
	public void sendCode(HttpServletRequest request, HttpServletResponse response){
		String loginName = CommonUtils.keyWordFilter(request.getParameter("loginName"));//用户名
		String result = "1";//执行结果代码，默认为发送成功
    	String code = "";//验证码
		try {
			//1.根据警号，获取用户信息，验证用户是否存在（错误代码2）
			User user = userService.getUser(loginName);
			if(user == null){
				result = "2";
			}
			
			//信息存在
			if(user != null){
				//2.判断手机号码是否为空（错误代码3）
				String telphone = user.getTelPhone();
	        	if(telphone == null || "".equals(telphone)){
	        		result = "3";
	        	}
	        	
	        	//3.生成验证码
	        	if("1".equals(result)){
	        		code = CommonUtils.getCode(4);
					
					//4.发送短信验证码
					String sendResultFlag = IntefaceUtils.sendMessage(telphone, "您的登录验证码为：" + code + "，请勿回复此短信！（" + Config.getInstance().getSysTitle() + "）");
					if(sendResultFlag == null || !"01".equals(sendResultFlag)){
						result = "0";//获取短信验证码失败！
					}
	        	}
			}
		} catch (Exception e) {
			result = "0";//获取短信验证码失败！
			e.printStackTrace();
		} finally{
			//成功获取验证码，则把验证码放在session中，登录时先从session获取，如果没有，则再从数据库获取
			if("1".equals(result)){
				request.getSession().setAttribute("CODE", code);
			}
			
			//封装数据
			String jsonData = "{\"result\":\"" + result + "\"}";
			response.setContentType("application/json");
			PrintWriter out = null;
			try{
				out = response.getWriter();
				out.write(jsonData);
			} catch(Exception e){
				e.printStackTrace();
			}
		}
	}

	/**
	 * 登录系统
	 * @return
	 *      0:登录失败！
	 * 		1:登录成功！
	 *      2:用户名与密码不匹配，请重新输入！
	 *      3:验证码不匹配，请重新输入！
	 */
	@RequestMapping("/login")
	@Description(moduleName="登录系统",operateType="0",operationName="密码登录")
	public void login(HttpServletRequest request, HttpServletResponse response){
		String loginName = CommonUtils.keyWordFilter(request.getParameter("loginName"));//用户名
		String password = request.getParameter("password");//密码     转变成md5
		String code = request.getParameter("code");//短信验证码
		
		String result = "1";//登录成功
		User user = null;
		try {
			//根据用户和密码获取信息
			user = userService.getUser(loginName, CommonUtils.md5(password));
			if(user == null){
				result = "2";//用户名与密码不匹配，请重新输入！
			} else {//用户信息存在
				//判断验证码是否正确
				if("1".equals(Config.getInstance().getDlyzmFlag())){//需要短信验证
					String sessionCode = (String)request.getSession().getAttribute("CODE");
					if(sessionCode == null || "".equals(sessionCode) || !sessionCode.toLowerCase().equals(code.toLowerCase())){
						result = "3";//验证码不匹配，请重新输入！
					}
				}
			}
		} catch (Exception e) {
			result = "0";//登录失败！
			e.printStackTrace();
		} finally{
			//登录成功
			if("1".equals(result)){
				//从seesion中移除验证码
				request.getSession().removeAttribute("CODE");
				
				//保存用户信息在session中
				request.getSession().setAttribute(StaticUtils.SESSION_NAME_USER_DATA, user);
				
				//在线用户数增加
				UserOnlineCountUtil.addUserOnline(request);
			}
			
			//封装数据
			String jsonData = "{\"result\":\"" + result + "\"}";
			response.setContentType("application/json");
			PrintWriter out = null;
			try{
				out = response.getWriter();
				out.write(jsonData);
			} catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 从OA登录系统
	 * @return
	 *      0:登录失败！
	 * 		1:登录成功！
	 */
	@RequestMapping("/loginOa")
	@Description(moduleName="登录系统",operateType="0",operationName="OA登录")
	public void loginOa(HttpServletRequest request, HttpServletResponse response){
//		String sip = request.getHeader("REFERER");//获取上一次服务地址
//		String loginName = CommonUtils.keyWordFilter(request.getParameter("userName"));//用户名
		
		String result = "1";//登录成功
		User user = null;
		try {
			//必须是从OA跳转过来才能登录
//			if(sip != null && (sip.indexOf("10.42.61.46") != -1 || sip.indexOf("10.42.61.48") != -1 
//					|| sip.indexOf("10.42.61.196") != -1 || sip.indexOf("10.42.61.197") != -1 
//					|| sip.indexOf("10.42.61.181") != -1 || sip.indexOf("10.42.5.15") != -1 
//					|| sip.indexOf("10.42.61.65") != -1)){
				//根据用户和密码获取信息
//				user = userService.getUser(loginName);
				if(user == null){
//					result = "2";//
					result = "4";//
				} else {
					//登录成功
					//if("1".equals(result)){
						//保存用户信息在session中
					//	request.getSession().setAttribute(StaticUtils.SESSION_NAME_USER_DATA, user);
						
						//在线用户数增加
					//	UserOnlineCountUtil.addUserOnline(request);
					//}
					result = "4";//
				}
//			} else {
//				result = "3";//非法登录
//			}
		} catch (Exception e) {
			result = "0";//登录失败！
			e.printStackTrace();
		} finally{
			//跳转
			try {
				//如果登录失败，则跳转登录页面
				if(!"1".equals(result)){
					response.sendRedirect("../login.jsp?result=" + result);
				} else {
					request.getRequestDispatcher("/user/initIndex.do").forward(request, response);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * PKI登录系统
	 * @return
	 */
	@SuppressWarnings({ "deprecation", "unchecked" })
	@RequestMapping("/loginPKI")
	@Description(moduleName="登录系统",operateType="0",operationName="PKI登录")
	public void loginPKI(HttpServletRequest request, HttpServletResponse response){
		String result = "1";//登录成功
		User user = null;
		try {
			// 第四步：客户端认证
			// 第五步：服务端验证认证原文
			// 第六步：应用服务端认证
			// 第七步：网关返回认证响应
			// 第八步：服务端处理
			boolean isSuccess = true;
			/***********************************
			 * 获取应用标识及网关认证地址 *
			 ***********************************/
			// 可以根据需求使用不同的获取方法
			Config config = Config.getInstance();
			String appId = config.getAppId();
			String authURL = config.getAuthURL();
			String attrType = config.getAttrType();
			if (!isNotNull(appId) || !isNotNull(authURL)) {
				isSuccess = false;
				result = "2";//应用标识或网关认证地址不能为空
			}

			String original_data = null, signed_data = null, original_jsp = null, idCard = null;
			/**************************
			 * 获取认证数据信息 *
			 **************************/
			if (isSuccess) {
				if (isNotNull((String) request.getSession().getAttribute("original_data"))
						&& isNotNull((String) request.getParameter("signed_data"))
						&& isNotNull((String) request.getParameter("original_jsp"))) {
					// 获取session中的认证原文
					original_data = (String) request.getSession().getAttribute("original_data");
					// 获取request中的认证原文
					original_jsp = (String) request.getParameter("original_jsp");
					// 获取request中的身份证号
//					idCard = (String) request.getParameter("idCard");

					/**************************
					 * 第五步：服务端验证认证原文 *
					 **************************/
					if (!original_data.equalsIgnoreCase(original_jsp)) {
						isSuccess = false;
						result = "3";//客户端提供的认证原文与服务端的不一致
					} else {
						// 获取证书认证请求包
						signed_data = (String) request.getParameter("signed_data");

						/* 随机密钥 */
						original_data = new BASE64Encoder().encode(original_jsp.getBytes());
					}
				} else {
					isSuccess = false;
					result = "4";//证书认证数据不完整
				}
			}

			/**************************
			 * 第六步：应用服务端认证 *
			 **************************/
			// 认证处理
			try {
				byte[] messagexml = null;
				if (isSuccess) {
					/*** 1 组装认证请求报文数据 ** 开始 **/
					Document reqDocument = DocumentHelper.createDocument();
					Element root = reqDocument.addElement("message");
					Element requestHeadElement = root.addElement("head");
					Element requestBodyElement = root.addElement("body");
					/* 组装报文头信息 */
					requestHeadElement.addElement("version").setText("1.0");
					requestHeadElement.addElement("serviceType").setText("AuthenService");

					/* 组装报文体信息 */

					// 组装客户端信息
					Element clientInfoElement = requestBodyElement.addElement("clientInfo");
					Element clientIPElement = clientInfoElement.addElement("clientIP");
					clientIPElement.setText(request.getRemoteAddr());

					// 组装应用标识信息
					requestBodyElement.addElement("appId").setText(appId);
					Element authenElement = requestBodyElement.addElement("authen");
					Element authCredentialElement = authenElement.addElement("authCredential");

					// 组装证书认证信息
					authCredentialElement.addAttribute("authMode", "cert");
					authCredentialElement.addElement("detach").setText(signed_data);
					authCredentialElement.addElement("original").setText(original_data);

					// 支持X509证书 认证方式
					// 获取到的证书
					// javax.security.cert.X509Certificate x509Certificate = null;
					// certInfo 为base64编码证书
					// 可以使用
					// "certInfo =new BASE64Encoder().encode(x509Certificate.getEncoded());"
					// 进行编码
					// authCredentialElement.addElement(MSG_CERT_INFO).setText(certInfo);

					/** 访问控制 */
					requestBodyElement.addElement("accessControl").setText("true");

					// 组装口令认证信息
					// username = request.getParameter( "" );//获取认证页面传递过来的用户名/口令
					// password = request.getParameter( "" );
					// authCredentialElement.addAttribute(MSG_AUTH_MODE,MSG_AUTH_MODE_PASSWORD_VALUE
					// );
					// authCredentialElement.addElement( MSG_USERNAME
					// ).setText(username);
					// authCredentialElement.addElement( MSG_PASSWORD
					// ).setText(password);

					// 组装属性查询列表信息
					Element attributesElement = requestBodyElement.addElement("attributes");
					if (attrType == null || attrType.equals("")) {
						result = "5";//属性列表控制项为空，请确认配置！
					} else {
						attributesElement.addAttribute("attributeType", attrType);
						if (attrType.equals("portion")) {
							String attributes = config.getAttributes();
							if (null != attributes && !"".equals(attributes)) {
								String[] attrs = attributes.split(";");
								int num = attrs.length;
								for (int i = 0; i < num; i++) {
									String att = attrs[i];
									if (att.contains("X509") || att.contains("_saml")) {
										// 证书属性
										addAttribute(attributesElement, att, "http://www.jit.com.cn/cinas/ias/ns/saml/saml11/X.509");
									} else {
										// pms 属性 或 ums 属性
										addAttribute(attributesElement, att, "http://www.jit.com.cn/ums/ns/user");
										addAttribute(attributesElement, att, "http://www.jit.com.cn/pmi/pms/ns/role");
										addAttribute(attributesElement, att, "http://www.jit.com.cn/pmi/pms/ns/privilege");
									}
								}
							}
						}
						// 取公共信息
						// addAttribute(attributesElement, "X509Certificate.SubjectDN",
						// "http://www.jit.com.cn/cinas/ias/ns/saml/saml11/X.509");
						// addAttribute(attributesElement, "UMS.UserID",
						// "http://www.jit.com.cn/ums/ns/user");
						// addAttribute(attributesElement, "机构字典",
						// "http://www.jit.com.cn/ums/ns/user");
						/*** 1 组装认证请求报文数据 ** 完毕 **/

						StringBuffer reqMessageData = new StringBuffer();
						try {
							/*** 2 将认证请求报文写入输出流 ** 开始 **/
							ByteArrayOutputStream outStream = new ByteArrayOutputStream();
							XMLWriter writer = new XMLWriter(outStream);
							writer.write(reqDocument);
							messagexml = outStream.toByteArray();
							/*** 2 将认证请求报文写入输出流 ** 完毕 **/

							reqMessageData.append("请求内容开始！\n");
							reqMessageData.append(outStream.toString() + "\n");
							reqMessageData.append("请求内容结束！\n");
						} catch (Exception e) {
							isSuccess = false;
							result = "6";//组装请求时出现异常
						}
					}
				}

				/****************************************************************
				 * 创建与网关的HTTP连接，发送认证请求报文，并接收认证响应报文*
				 ****************************************************************/
				/*** 1 创建与网关的HTTP连接 ** 开始 **/
				int statusCode = 500;
				HttpClient httpClient = null;
				PostMethod postMethod = null;
				if (isSuccess) {
					// HTTPClient对象
					httpClient = new HttpClient();
					httpClient.setConnectionTimeout(10000);
					httpClient.setTimeout(20000);
					postMethod = new PostMethod(authURL);
					postMethod.setRequestHeader("Connection","close");

					// 设置报文传送的编码格式
					postMethod.setRequestHeader("Content-Type", "text/xml;charset=UTF-8");
					/*** 2 设置发送认证请求内容 ** 开始 **/
					postMethod.setRequestBody(new ByteArrayInputStream(messagexml));
					/*** 2 设置发送认证请求内容 ** 结束 **/
					// 执行postMethod
					try {
						/*** 3 发送通讯报文与网关通讯 ** 开始 **/
						statusCode = httpClient.executeMethod(postMethod);
						/*** 3 发送通讯报文与网关通讯 ** 结束 **/
					} catch (Exception e) {
						isSuccess = false;
						result = "7";//与网关连接出现异常
						
						//关闭连接
						postMethod.releaseConnection();
						httpClient.getHttpConnectionManager().closeIdleConnections(0);
						httpClient = null ;
					}
				}
				/****************************************************************
				 * 第七步：网关返回认证响应*
				 ****************************************************************/

				String respMessageXml = null;
				if (isSuccess) {
					// 当返回200或500状态时处理业务逻辑
					if (statusCode == HttpStatus.SC_OK || statusCode == HttpStatus.SC_INTERNAL_SERVER_ERROR) {
						// 从头中取出转向的地址
						try {
							/*** 4 接收通讯报文并处理 ** 开始 **/
							byte[] inputstr = postMethod.getResponseBody();
							ByteArrayInputStream ByteinputStream = new ByteArrayInputStream(inputstr);
							ByteArrayOutputStream outStream = new ByteArrayOutputStream();
							int ch = 0;
							try {
								while ((ch = ByteinputStream.read()) != -1) {
									int upperCh = (char) ch;
									outStream.write(upperCh);
								}
							} catch (Exception e) {
								isSuccess = false;
								result = "9";//读取认证响应报文出现异常！
							}

							if (isSuccess) {
								// 200 表示返回处理成功
								if (statusCode == HttpStatus.SC_OK) {
									respMessageXml = new String(outStream.toByteArray(), "UTF-8");
								} else {
									// 500 表示返回失败，发生异常
									isSuccess = false;
									result = "8";//网关返回认证响应失败
								}
							}
							/*** 4 接收通讯报文并处理 ** 结束 **/
						} catch (IOException e) {
							isSuccess = false;
							result = "9";//读取认证响应报文出现异常！
						} finally{
							if(httpClient != null){
								postMethod.releaseConnection();
								httpClient.getHttpConnectionManager().closeIdleConnections(0);
							}
						}
					}
				}

				/*** 1 创建与网关的HTTP连接 ** 结束 **/

				/**************************
				 *第八步：服务端处理 *
				 **************************/
				Document respDocument = null;
				Element headElement = null;
				Element bodyElement = null;
				if (isSuccess) {
					respDocument = DocumentHelper.parseText(respMessageXml);
					headElement = respDocument.getRootElement().element("head");
					bodyElement = respDocument.getRootElement().element("body");

					/*** 1 解析报文头 ** 开始 **/
					if (headElement != null) {
						boolean state = Boolean.valueOf(headElement.elementTextTrim("messageState")).booleanValue();
						if (state) {
							isSuccess = false;
							result = "10";//认证业务处理失败！
						}
					}
				}

				if (isSuccess) {
					/* 解析报文体 */
					// 解析认证结果集
					Element authResult = bodyElement.element("authResultSet").element("authResult");
					isSuccess = Boolean.valueOf(authResult.attributeValue("success")).booleanValue();
					if (!isSuccess) {
						result = "11";//身份认证失败！
					} else {
						Element attrsElement = bodyElement.element("attributes");
						if (attrsElement != null) {
							List<Element> namespacesElements = attrsElement.elements("attr");
							if (namespacesElements != null && namespacesElements.size() > 0) {
								for (int i = 0; i < namespacesElements.size(); i++) {
									Element attrNode = (Element) namespacesElements.get(i);
									String name = attrNode.attributeValue("name");
									String value = attrNode.getTextTrim();
									if(name != null && "_saml_cert_subject".equals(name)){
										idCard = value;
										break;
									}
								}
								
								//对身份信息进行解析
								if(idCard != null && !"".equals(idCard)){
									//截取身份信息
									idCard = idCard.split(",")[0].substring(3);
									
									//根据姓名和身份证号获取用户信息
									user = userService.getUserByNameAndIdCard(idCard.split("\\s+")[0], idCard.split("\\s+")[1]);
									//登录成功
									if("1".equals(result) && user != null){
										//保存用户信息在session中
										request.getSession().setAttribute(StaticUtils.SESSION_NAME_USER_DATA, user);
										
										//在线用户数增加
										UserOnlineCountUtil.addUserOnline(request);
									} else {
										isSuccess = false;
										result = "12";//用户信息不存在或不完整！
									}
								} else {
									result = "11";//身份认证失败！
								}
							} else {
								result = "11";//身份认证失败！
							}
						} else {
							result = "11";//身份认证失败！
						}
					}
				}
			} catch (Exception e) {
				isSuccess = false;
				result = "0";//登录失败！
				e.printStackTrace();
			}
		} catch (Exception e) {
			result = "0";//登录失败！
			e.printStackTrace();
		} finally{
			//封装数据
			String jsonData = "{\"result\":\"" + result + "\"}";
			response.setContentType("application/json");
			PrintWriter out = null;
			try{
				out = response.getWriter();
				out.write(jsonData);
			} catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 退出系统
	 * @return
	 *      0:退出失败！
	 * 		1:退出成功！
	 */
	@RequestMapping("/exitSystem")
	@Description(moduleName="退出系统",operateType="0",operationName="退出系统")
	public void exitSystem(HttpServletRequest request, HttpServletResponse response){
		String result = "1";
		try {
			//在线用户map列表移除
			UserOnlineCountUtil.getOnlineUserMap().remove(request.getSession().getId());
			//销毁session
			request.getSession().invalidate();
		} catch (Exception e) {
			result = "1";//session失效等原因，默认退出成功，没有退出失败
			e.printStackTrace();
		} finally{
			//封装数据
			String jsonData = "{\"result\":\"" + result + "\"}";
			response.setContentType("application/json");
			PrintWriter out = null;
			try{
				out = response.getWriter();
				out.write(jsonData);
			} catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 浏览器报告在线情况并获取在线用户数
	 * @return
	 * 		在线用户数
	 */
	@RequestMapping("/reportOnline")
	public void reportOnline(HttpServletRequest request, HttpServletResponse response){
		try {
			//标记用户在线
			UserOnlineCountUtil.setUserOnline(request);
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			//封装数据
			String jsonData = "{\"result\":\"" + UserOnlineCountUtil.getOnlineUserMap().size() + "\"}";
			response.setContentType("application/json");
			PrintWriter out = null;
			try{
				out = response.getWriter();
				out.write(jsonData);
			} catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 初始化主页
	 * @return
	 *      功能模块集合
	 */
	@SuppressWarnings("finally")
	@RequestMapping("/initIndex")
	public String initIndex(HttpServletRequest request, HttpServletResponse response) {
		//获取用户信息
		User user = (User)request.getSession().getAttribute(StaticUtils.SESSION_NAME_USER_DATA);
		List<List<FunctionTree>> allFunList = new ArrayList<List<FunctionTree>>();//返回给页面的功能，一个元素是List<FunctionTree>集合，一个功能模块集合
		List<FunctionTree> funList = null;
		StringBuffer sb = new StringBuffer();
		String page = "systemmanage/main/index";
		try {
			if(user != null && user.getRoleId() != null && !"".equals(user.getRoleId())){
				//根据角色Id获取角色数据
				Role role = roleService.getRoleById(user.getRoleId());
				if(role != null){
					//获取为1的权限位置
					char permission[] = role.getPermissionContent().toCharArray();
					for (int i = 0;i < permission.length;i++) {
						if (permission[i] == '1') {
							sb.append(i+1).append(",");//权限为从1开始
						}
					}
					
					//根据获得的功能权限位查询出该用户所具体的功能
					List<FunctionTree> allFun = roleService.getFunction("1,2", sb.substring(0, sb.length() - 1), "1");
					//一个模块，存放在一个List<FunctionTree>集合中，并且第一个元素是主模块，如果存在有主模块，最后放入List<List<FunctionTree>>集合中
					for(int i=0;i < allFun.size();i++){
						FunctionTree fun = allFun.get(i);
						if("1".equals(fun.getRank())){//一级功能，获取该一级功能的所有二级功能，并且创建一个List<FunctionTree>集合（包含一二级功能）
							//创建一个新模块集合，并加入集合中
							funList = new ArrayList<FunctionTree>();
							funList.add(fun);

							//获取二级功能
							for(int j=0;j < allFun.size();j++){
								FunctionTree secFun = allFun.get(j);
								if("2".equals(secFun.getRank())){//必须是二级功能
									//将功能加入模块集合中，权限为必须在该模块的范围内，且父级必须为fun
									if(fun.getPermissionPosition() < secFun.getPermissionPosition() 
											&& secFun.getPermissionPosition() <= (fun.getPermissionPosition() + 20)
											&& secFun.getParentPermission() == fun.getPermissionPosition()){
										funList.add(secFun);
									}
								}
							}
							
							//保存模块集合到List<List<FunctionTree>>中
							allFunList.add(funList);
						}
					}
				}
			}
		} catch (Exception e) {
			page = "redirect:/common/errorPage/error500.jsp";
			e.printStackTrace();
		} finally{
			request.setAttribute("allFunList", allFunList);
			return page;
		}
	}
	
	/**
	 * 初始化用户信息管理页面
	 * @return
	 *      用户信息管理页面
	 */
	@SuppressWarnings("finally")
	@RequestMapping("/initUserManage")
	public String initUserManage(Model model, HttpServletRequest request, HttpServletResponse response) {
		String page = "systemmanage/user/userManage";
		try {
			//获取用户信息
			User user = (User)request.getSession().getAttribute(StaticUtils.SESSION_NAME_USER_DATA);
			
			//获取角色类型，用户信息来源数据字典
			List<Dictionary> dicList = userService.getDictionarysByTypeCode("1001,1002");
			String dicJson = JSON.toJSONString(dicList);
			request.setAttribute("dicList", dicList);
			request.setAttribute("dicJson", dicJson);
			
			//获取角色
			List<Role> roleList = roleService.getAllMyRole(user.getRoleId(), user.getSystemNo(), user.getPosition());
			String roleJson = JSON.toJSONString(roleList);
			request.setAttribute("roleList", roleList);
			request.setAttribute("roleJson", roleJson);
			
			JDateTime jdt = new JDateTime();
			model.addAttribute("year", jdt.toString("YYYY"));
			model.addAttribute("month", jdt.toString("MM"));
			model.addAttribute("cxrq", jdt.toString("YYYY-MM-DD"));
			model.addAttribute("jssj", jdt.toString("YYYY-MM-DD hh:mm:ss"));
			model.addAttribute("kssj", jdt.subDay(1).toString("YYYY-MM-DD hh:mm:ss"));
			model.addAttribute("cxlb", "1");
		} catch (Exception e) {
			page = "redirect:/common/errorPage/error500.jsp";
			e.printStackTrace();
		} finally{
			return page;
		}
	}
	
	/**
	 * 分页查询用户信息
	 * @return
	 *      用户信息管理页面
	 */
	@SuppressWarnings("finally")
	@RequestMapping("/getUserForPage")
	@Description(moduleName="用户信息管理",operateType="1",operationName="查询用户信息")
	public String getUserForPage(HttpServletRequest request, HttpServletResponse response) {
		PageResult pageResult = null;
		try {
			//获取用户信息
			User user = (User)request.getSession().getAttribute(StaticUtils.SESSION_NAME_USER_DATA);
			
			//获取参数
			String loginName = request.getParameter("loginName");
			String username = request.getParameter("username");
			String deptno = request.getParameter("deptno");
			String roleType = request.getParameter("roleType");
			String roleId = request.getParameter("roleId");
			String infoSource = request.getParameter("infoSource");
			String startTime = request.getParameter("startTime");
			String endTime = request.getParameter("endTime");
			int pageNo = Integer.parseInt(request.getParameter("pageNo"));
			
			//获取分页结果
			pageResult = userService.getUserForPage(user.getPosition(), user.getSystemNo(), loginName, username, deptno, roleType, roleId, infoSource, startTime, endTime, pageNo, 10);
			request.setAttribute("pageResult", pageResult);
			
			//获取角色类型，用户信息来源数据字典
			List<Dictionary> dicList = userService.getDictionarysByTypeCode("1001,1002");
			request.setAttribute("dicList", dicList);
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			return "systemmanage/user/userInfoList";
		}
	}
	
	/**
	 * 删除用户信息
	 * @return
	 *      0:删除失败！
	 * 		1:删除成功！
	 */
	@RequestMapping("/deleteUser")
	@Description(moduleName="用户信息管理",operateType="4",operationName="删除用户信息")
	public void deleteUser(HttpServletRequest request, HttpServletResponse response){
		String result = "1";
		try {
			String id = request.getParameter("id");//信息编号
			if(id != null && !"".equals(id.trim())){
				userService.deleteUser(id.trim());
			} else {
				result = "0";//删除失败
			}
		} catch (Exception e) {
			result = "0";//删除失败
			e.printStackTrace();
		} finally{
			//封装数据
			String jsonData = "{\"result\":\"" + result + "\"}";
			response.setContentType("application/json");
			PrintWriter out = null;
			try{
				out = response.getWriter();
				out.write(jsonData);
			} catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 批量删除用户信息
	 * @return
	 *      0:删除失败！
	 * 		1:删除成功！
	 */
	@RequestMapping("/batcheDeleteUser")
	@Description(moduleName="用户信息管理",operateType="4",operationName="批量删除用户信息")
	public void batcheDeleteUser(HttpServletRequest request, HttpServletResponse response){
		String result = "1";
		try {
			String[] ids = request.getParameterValues("ids");//信息编号
			if(ids != null && ids.length > 0){
				StringBuilder sb = new StringBuilder();
				for(int i=0;i < ids.length;i++){
					sb.append(ids[i]).append(",");
				}
				userService.deleteUser(sb.substring(0, sb.length()-1));
			} else {
				result = "0";//删除失败
			}
		} catch (Exception e) {
			result = "0";//删除失败
			e.printStackTrace();
		} finally{
			//封装数据
			String jsonData = "{\"result\":\"" + result + "\"}";
			response.setContentType("application/json");
			PrintWriter out = null;
			try{
				out = response.getWriter();
				out.write(jsonData);
			} catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 初始化新增用户信息页面
	 * @return
	 *      新增用户信息页面
	 */
	@SuppressWarnings("finally")
	@RequestMapping("/initAddUser")
	public String initAddUser(HttpServletRequest request, HttpServletResponse response) {
		String page = "systemmanage/user/addUser";
		try {
			//获取用户信息
			User operUser = (User)request.getSession().getAttribute(StaticUtils.SESSION_NAME_USER_DATA);
			
			//获取角色
			List<Role> roleList = roleService.getAllMyRole(operUser.getRoleId(), operUser.getSystemNo(), operUser.getPosition());
			request.setAttribute("roleList", roleList);

			//获取考核部门
			List<Department> khbmList = deptService.getKhbm(operUser.getSystemNo());
			request.setAttribute("khbmList", khbmList);
		} catch (Exception e) {
			page = "redirect:/common/errorPage/error500.jsp";
			e.printStackTrace();
		} finally{
			return page;
		}
	}
	
	/**
	 * 新增用户信息
	 * @return
	 *      用户信息管理页面
	 */
	@RequestMapping("/addUser")
	@Description(moduleName="用户信息管理",operateType="2",operationName="新增用户信息")
	public void addUser(HttpServletRequest request, HttpServletResponse response) {
		String result = "1";
		try {
			//获取用户信息
			User operUser = (User)request.getSession().getAttribute(StaticUtils.SESSION_NAME_USER_DATA);
			
			//获取数据
			String loginName = request.getParameter("loginName");//用户名
			String userName = request.getParameter("userName");//姓名
			String telphone = request.getParameter("telphone");//手机号码
			String identityCard = request.getParameter("identityCard");//身份证号码
			String password = request.getParameter("password");//密码
			String deptId = request.getParameter("deptId");//部门ID
			String systemNo = request.getParameter("systemNo");//系统自身部门编码
			String deptName = request.getParameter("deptName");//部门名称
			String roleId = request.getParameter("roleId");//角色ID:角色名称:角色类型
			String remark = request.getParameter("remark");//备注
			String khbm = request.getParameter("khbm");//考核部门
			String khbmmc = request.getParameter("khbmmc");//考核部门名称
			if(khbm == null || khbmmc == null || "".equals(khbm.trim()) || "".equals(khbmmc.trim())){
				khbm = deptId;
				khbmmc = deptName;
			}
			
			//检查用户名是否存在
			if(identityCard != null && !"".equals(identityCard.trim()) && !CommonUtils.isIdentityCard(identityCard)){
				result = "3";
			} else if(userService.getUser(loginName) != null){
				result = "2";
			} else {
				//封装
				User user = new User();
				user.setLoginName(loginName);
				user.setUserName(userName);
				user.setTelPhone(telphone);
				user.setIdentityCard(identityCard);
				user.setPassword(CommonUtils.md5(password));
				user.setDeptId(deptId);
				user.setDeptName(deptName);
				user.setSystemNo(systemNo);
				if(roleId != null && !"".equals(roleId.trim())){
					user.setRoleId(Integer.parseInt(roleId.split(":")[0]));
					user.setRoleName(roleId.split(":")[1]);
					user.setPosition(roleId.split(":")[2]);
				} else {
					user.setRoleId(0);
					user.setRoleName("");
					user.setPosition("");
				}
				user.setBuildPno(operUser.getLoginName());
				user.setBuildName(operUser.getUserName());
				user.setBuildTime(new Date());
				user.setUpdateTime(new Date());
				user.setInfoSource("1");
				user.setEnable("1");
				user.setRemark(remark);
				user.setLskhbm(khbm);
				user.setLskhbmmc(khbmmc);
				
				//保存
				userService.addUser(user);
			}
		} catch (Exception e) {
			result = "0";//失败
			e.printStackTrace();
		} finally{
			//封装数据
			String jsonData = "{\"result\":\"" + result + "\"}";
			response.setContentType("application/json");
			PrintWriter out = null;
			try{
				out = response.getWriter();
				out.write(jsonData);
			} catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 初始化更新用户信息页面
	 * @return
	 *      更新用户信息页面
	 */
	@SuppressWarnings("finally")
	@RequestMapping("/initUpdateUser")
	public String initUpdateUser(HttpServletRequest request, HttpServletResponse response) {
		String page = "systemmanage/user/updateUser";
		try {
			//获取用户信息
			User operUser = (User)request.getSession().getAttribute(StaticUtils.SESSION_NAME_USER_DATA);
			
			//获取角色
			List<Role> roleList = roleService.getAllMyRole(operUser.getRoleId(), operUser.getSystemNo(), operUser.getPosition());
			request.setAttribute("roleList", roleList);
			
			//获取考核部门
			List<Department> khbmList = deptService.getKhbm(operUser.getSystemNo());
			request.setAttribute("khbmList", khbmList);
			
			//根据ID，获取用户信息
			int id = Integer.parseInt(request.getParameter("id"));//用户ID
			User user = userService.getUser(id);
			request.setAttribute("userObj", user);
		} catch (Exception e) {
			page = "redirect:/common/errorPage/error500.jsp";
			e.printStackTrace();
		} finally{
			return page;
		}
	}
	
	/**
	 * 修改用户信息
	 * @return
	 *      用户信息管理页面
	 */
	@RequestMapping("/updateUser")
	@Description(moduleName="用户信息管理",operateType="3",operationName="修改用户信息")
	public void updateUser(HttpServletRequest request, HttpServletResponse response) {
		String result = "1";
		try {
			//获取数据
			String userName = request.getParameter("userName");//姓名
			String telphone = request.getParameter("telphone");//手机号码
			String identityCard = request.getParameter("identityCard");//身份证号码
			String deptId = request.getParameter("deptId");//部门ID
			String deptName = request.getParameter("deptName");//部门名称
			String systemNo = request.getParameter("systemNo");//系统自身部门编码
			String roleId = request.getParameter("roleId");//角色ID:角色名称:角色类型
			String remark = request.getParameter("remark");//备注
			String khbm = request.getParameter("khbm");//考核部门
			String khbmmc = request.getParameter("khbmmc");//考核部门名称
			if(khbm == null || khbmmc == null || "".equals(khbm.trim()) || "".equals(khbmmc.trim())){
				khbm = deptId;
				khbmmc = deptName;
			}
			
			if(identityCard != null && !"".equals(identityCard.trim()) && !CommonUtils.isIdentityCard(identityCard)){
				result = "3";
			} else {
				//根据ID，获取用户信息
				int id = Integer.parseInt(request.getParameter("id"));//用户ID
				User user = userService.getUser(id);
				
				//更改信息
				user.setUserName(userName);
				user.setTelPhone(telphone);
				user.setIdentityCard(identityCard);
				user.setDeptId(deptId);
				user.setDeptName(deptName);
				user.setSystemNo(systemNo);
				if(roleId != null && !"".equals(roleId.trim())){
					user.setRoleId(Integer.parseInt(roleId.split(":")[0]));
					user.setRoleName(roleId.split(":")[1]);
					user.setPosition(roleId.split(":")[2]);
				} else {
					user.setRoleId(0);
					user.setRoleName("");
					user.setPosition("");
				}
				user.setUpdateTime(new Date());
				user.setRemark(remark);
				user.setLskhbm(khbm);
				user.setLskhbmmc(khbmmc);
				
				//保存
				userService.updateUser(user);
			}
		} catch (Exception e) {
			result = "0";//失败
			e.printStackTrace();
		} finally{
			//封装数据
			String jsonData = "{\"result\":\"" + result + "\"}";
			response.setContentType("application/json");
			PrintWriter out = null;
			try{
				out = response.getWriter();
				out.write(jsonData);
			} catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 修改密码
	 */
	@RequestMapping("/updatePassword")
	@Description(moduleName="密码修改",operateType="3",operationName="修改密码")
	public void updatePassword(HttpServletRequest request, HttpServletResponse response) {
		String result = "1";
		try {
			//获取数据
			int id = Integer.parseInt(request.getParameter("id"));//用户ID
			String loginName = request.getParameter("loginName");//用户名
			String oldPassword = request.getParameter("oldPassword");//旧密码
			String password = request.getParameter("password");//新密码
			
			//根据ID，获取用户信息
			User user = userService.getUser(id);
			
			//先判断用户名是否一致
			if(user != null && user.getLoginName().equals(loginName)){
				//再判断旧密码是否一致
				if(user.getPassword().equals(CommonUtils.md5(oldPassword))){
					//更改信息
					user.setPassword(CommonUtils.md5(password));
					//保存
					userService.updateUser(user);
				} else {
					result = "2";//旧密码不正确
				}
			} else {
				result = "0";//失败
			}
		} catch (Exception e) {
			result = "0";//失败
			e.printStackTrace();
		} finally{
			//封装数据
			String jsonData = "{\"result\":\"" + result + "\"}";
			response.setContentType("application/json");
			PrintWriter out = null;
			try{
				out = response.getWriter();
				out.write(jsonData);
			} catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 用户信息详情
	 * @return
	 *      用户信息详情页面
	 */
	@SuppressWarnings("finally")
	@RequestMapping("/userDetail")
	@Description(moduleName="用户信息管理",operateType="1",operationName="查看用户信息详情")
	public String userDetail(HttpServletRequest request, HttpServletResponse response) {
		String page = "systemmanage/user/userDetail";
		try {
			//获取角色类型，用户信息来源数据字典
			List<Dictionary> dicList = userService.getDictionarysByTypeCode("1001,1002");
			String dicJson = JSON.toJSONString(dicList);
			request.setAttribute("dicJson", dicJson);
			
			//根据ID，获取用户信息
			int id = Integer.parseInt(request.getParameter("id"));//用户ID
			User user = userService.getUser(id);
			request.setAttribute("userObj", user);
		} catch (Exception e) {
			page = "redirect:/common/errorPage/error500.jsp";
			e.printStackTrace();
		} finally{
			return page;
		}
	}
	
	/**
	 * 重置密码
	 */
	@RequestMapping("/resetPassword")
	@Description(moduleName="用户信息管理",operateType="3",operationName="重置密码")
	public void resetPassword(HttpServletRequest request, HttpServletResponse response) {
		String result = "1";
		try {
			//获取数据
			int id = Integer.parseInt(request.getParameter("id"));//用户ID
			
			//根据ID，获取用户信息
			User user = userService.getUser(id);
			if(user == null){
				result = "0";
			} else {
				//更改信息
				user.setPassword(CommonUtils.md5("000000"));
				//保存
				userService.updateUser(user);
			}
		} catch (Exception e) {
			result = "0";//失败
			e.printStackTrace();
		} finally{
			//封装数据
			String jsonData = "{\"result\":\"" + result + "\"}";
			response.setContentType("application/json");
			PrintWriter out = null;
			try{
				out = response.getWriter();
				out.write(jsonData);
			} catch(Exception e){
				e.printStackTrace();
			} finally{
				if(out != null){
					out.close();
				}
			}
		}
	}
	
	/**
	 * 初始化批量授权页面
	 * @return
	 * 		批量授权页面
	 */
	@SuppressWarnings("finally")
	@RequestMapping("/initGrantRole")
	public String initGrantRole(HttpServletRequest request, HttpServletResponse response){
		String page = "systemmanage/user/grantRole";
		try {
			//获取用户信息
			User operUser = (User)request.getSession().getAttribute(StaticUtils.SESSION_NAME_USER_DATA);
			
			//获取角色
			List<Role> roleList = roleService.getAllMyRole(operUser.getRoleId(), operUser.getSystemNo(), operUser.getPosition());
			request.setAttribute("roleList", roleList);
			
			DeptAndUserNode deptAndUserNode = null;
			String deptAndUserToJson = "[]";
			List<DeptAndUserNode> deptAndUserNodeList = new ArrayList<DeptAndUserNode>();
			
			//获取所有的部门信息
			List<Department> deptList = deptService.getMaxDept();
			//封装
			for(int i=0;i < deptList.size();i++){
				Department dept = deptList.get(i);
				deptAndUserNode = new DeptAndUserNode(dept.getId(), dept.getDeptNo(), dept.getDeptName(), dept.getParentNo(), (dept.getSystemNo() != null ?dept.getSystemNo():""), "1", "./common/images/department.png", true);
				deptAndUserNodeList.add(deptAndUserNode);
			}
			
//			//获取所有的用户信息，除超级管理员外
//			List<User> userList = userService.getAllUser();
//			//封装
//			for(int j=0;j < deptList.size();j++){
//				Department dept = deptList.get(j);
//				for(int i=0;i < userList.size();i++){
//					User user = userList.get(i);
//					if(user != null && !"200".equals(user.getPosition()) && dept.getDeptNo().trim().equals(user.getDeptId()) && user.getSystemNo().startsWith(operUser.getSystemNo())){//不包括超级管理员
//						deptAndUserNode = new DeptAndUserNode(user.getId(), user.getLoginName(), user.getUserName(), user.getDeptId(), "", "2", "./common/images/people.png");
//						deptAndUserNodeList.add(deptAndUserNode);
//					}
//				}
//			}
			
			//转换成json
			deptAndUserToJson = JSON.toJSONString(deptAndUserNodeList);
			request.setAttribute("deptAndUserToJson", deptAndUserToJson);
		} catch (Exception e) {
			page = "redirect:/common/errorPage/error500.jsp";
			e.printStackTrace();
		} finally{
			return page;
		}
	}
	
	/**
	 * 根据部门编号加载子部门及相关用户
	 * @return
	 * 		json格式的部门信息，如果失败，则返回空字符串
	 */
	@RequestMapping("/getDeptAndUserToWriter")
	public void getDeptAndUserToWriter(HttpServletRequest request, HttpServletResponse response){
		String result = "[]";
		try {
			//获取用户信息
			User operUser = (User)request.getSession().getAttribute(StaticUtils.SESSION_NAME_USER_DATA);
			
			//获取父节点编号
			String deptNo = request.getParameter("nodeNo");//信息编号
			//根据部门编号加载子部门
			List<Department> deptList = deptService.getDeptByParentNo(deptNo);
			
			//封装
			DeptAndUserNode deptAndUserNode = null;
			List<DeptAndUserNode> deptAndUserNodeList = new ArrayList<DeptAndUserNode>();
			for(int i=0;i < deptList.size();i++){
				Department dept = deptList.get(i);
				deptAndUserNode = new DeptAndUserNode(dept.getId(), dept.getDeptNo(), dept.getDeptName(), dept.getParentNo(), (dept.getSystemNo() != null ?dept.getSystemNo():""), "1", "./common/images/department.png", true);
				deptAndUserNodeList.add(deptAndUserNode);
			}
			
			//获取所有的用户信息，除超级管理员外
			List<User> userList = userService.getAllUser();
			//封装
			for(int i=0;i < userList.size();i++){
				User user = userList.get(i);
				//获取父级部门下的用户，并且不能是超级用户，且要是登录用户部门下的子孙部门
				if(user != null && !"99999".equals(user.getPosition()) && deptNo.trim().equals(user.getDeptId()) && user.getSystemNo().startsWith(operUser.getSystemNo())){
					deptAndUserNode = new DeptAndUserNode(user.getId(), user.getLoginName(), user.getUserName(), user.getDeptId(), "", "2", "./common/images/people.png");
					deptAndUserNodeList.add(deptAndUserNode);
				}
			}
			
			//转换成json格式
			result = JSON.toJSONString(deptAndUserNodeList);
		} catch (Exception e) {
			result = "[]";
			e.printStackTrace();
		} finally{
			//封装数据
			PrintWriter out = null;
			try{
				out = response.getWriter();
				out.write(result);
			} catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 批量授权
	 */
	@RequestMapping("/grantRole")
	@Description(moduleName="用户信息管理",operateType="3",operationName="批量授权")
	public void grantRole(HttpServletRequest request, HttpServletResponse response) {
		String result = "1";
		try {
			//获取数据
			String userIds = request.getParameter("userIds");//用户ID
			String roleId = request.getParameter("roleId");//角色ID:角色名称:角色类型
			if(userIds != null && !"".equals(userIds) && roleId != null && !"".equals(roleId) && roleId.split(":").length >= 3){
				userService.grantRole(userIds, roleId);
			} else {
				result = "0";//失败
			}
		} catch (Exception e) {
			result = "0";//失败
			e.printStackTrace();
		} finally{
			//封装数据
			String jsonData = "{\"result\":\"" + result + "\"}";
			response.setContentType("application/json");
			PrintWriter out = null;
			try{
				out = response.getWriter();
				out.write(jsonData);
			} catch(Exception e){
				e.printStackTrace();
			} finally{
				if(out != null){
					out.close();
				}
			}
		}
	}
	
	/**
	 * oa刷取审批信息
	 * @return
	 *      oa审批页面
	 */
	@SuppressWarnings("finally")
	@RequestMapping("/oaSp")
//	@Description(moduleName="oa审批",operationName="刷取审批信息")
	public String oaSp(HttpServletRequest request, HttpServletResponse response) {
		String page = "systemmanage/jcbksp";
		User user = null;
		List<Dispatched> bkspList = new ArrayList<Dispatched>();
		List<Withdraw> ckspList = new ArrayList<Withdraw>();
		String havaRole = "0";
		try {
			//获取用户名
			String loginName = CommonUtils.keyWordFilter(request.getParameter("userName"));//用户名
			if(loginName != null && !"".equals(loginName.trim())){
				//根据用户和密码获取信息
				user = userService.getUser(loginName);
			}
			if(user != null && user.getRoleId() != 0 && user.getPosition() != null && !"".equals(user.getPosition())){
				Role role = roleService.getRoleById(user.getRoleId());
				if(role != null){
					//获取审批信息
					if(role.getPermissionContent().charAt(46)=='1'){//47说明有布控审批权限
						bkspList = bkService.findTodoTasksForOa(user);
						request.setAttribute("bkConPath", "listTask");
						havaRole = "1";
					} else if(role.getPermissionContent().charAt(46) != '1' && role.getPermissionContent().charAt(44)=='1'){//说明有公开布控审批权限45位为1
						bkspList = bkService.findTodoOpenTasksForOa(user);
						request.setAttribute("bkConPath", "openTaskList");
						havaRole = "1";
					}
					
					if(role.getPermissionContent().charAt(52)=='1'){//53说明有秘密撤控审批的权限
						ckspList = ckService.findTodoTasksForOa(user);
						request.setAttribute("ckConPath", "withdrawTaskList");
						havaRole = "1";
					} else if(role.getPermissionContent().charAt(52)!='1' && role.getPermissionContent().charAt(53)=='1'){//说明有公开撤控的权限
						ckspList = ckService.findTodoOpenTasksForOa(user);
						request.setAttribute("ckConPath", "openWithdrawTaskList");
						havaRole = "1";
					}
					
					request.setAttribute("bkspList", bkspList);
					request.setAttribute("ckspList", ckspList);
					request.setAttribute("havaRole", havaRole);
				} else {
					request.setAttribute("havaRole", havaRole);
				}
			} else {
				request.setAttribute("havaRole", havaRole);
			}
		} catch (Exception e) {
			page = "redirect:/common/errorPage/error500.jsp";
			e.printStackTrace();
		} finally{
			return page;
		}
	}
	
	/**
	 * 补全身份证信息
	 * @return
	 */
	@RequestMapping("/addIdCardToUser")
	@Description(moduleName="用户信息管理",operateType="3",operationName="补全身份证号")
	public void addIdCardToUser(HttpServletRequest request, HttpServletResponse response) {
		String result = "1";
		try {
			//获取用户信息
			User operUser = (User)request.getSession().getAttribute(StaticUtils.SESSION_NAME_USER_DATA);
			
			//获取数据
			String idcard = request.getParameter("idcard");//身份证号
			if(idcard == null || "".equals(idcard.trim()) || operUser == null){
				result = "3";
			} else if(idcard != null && !"".equals(idcard.trim()) && !CommonUtils.isIdentityCard(idcard)){
				result = "2";
			} else { //更新
				User user = userService.getUser(operUser.getId());
				//更改信息
				user.setIdentityCard(idcard);
				//保存
				userService.updateUser(user);
				
				//更改session中的信息
				operUser.setIdentityCard(idcard);
				request.getSession().setAttribute(StaticUtils.SESSION_NAME_USER_DATA, operUser);
			}
		} catch (Exception e) {
			result = "0";//失败
			e.printStackTrace();
		} finally{
			//封装数据
			String jsonData = "{\"result\":\"" + result + "\"}";
			response.setContentType("application/json");
			PrintWriter out = null;
			try{
				out = response.getWriter();
				out.write(jsonData);
			} catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 判断是否是空串
	 */
	private boolean isNotNull(String str) {
		if (str == null || str.trim().equals(""))
			return false;
		else
			return true;
	}
	
	/**
	 * 向xml插入结点
	 */
	private void addAttribute(Element attributesElement, String name,
			String namespace) {
		Element attr = attributesElement.addElement("attr");
		attr.addAttribute("name", name);
		attr.addAttribute("namespace", namespace);
	}
}