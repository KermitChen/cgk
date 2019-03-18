<%@ page language="java" import="java.util.*,com.dyst.utils.*,com.dyst.systemmanage.entities.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	//获取需要编辑的数据
	User userObj = (User)request.getAttribute("userObj");
	//获取session中的用户
	User user = (User)request.getSession().getAttribute(StaticUtils.SESSION_NAME_USER_DATA);
	request.setAttribute("user", user);
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<base href="<%=basePath %>">
		<title>修改用户信息页面</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">    
		<meta http-equiv="keywords" content="缉查,布控,缉查布控,缉查布控系统">
		<meta http-equiv="description" content="深圳市公安局缉查布控系统-修改用户信息页面">
		
		<link rel="stylesheet" href="<%=basePath%>common/css/style.css" type="text/css">
		<link rel="stylesheet" href="<%=basePath%>common/css/zTreeStyle/zTreeStyle.css" type="text/css">
		<style type="text/css">
			.layer_span:hover {
				color:#FF0000 !important;
			}
		</style>
	</head>
	<body>
		<div id="content" style="width: 800px;height: 450px;margin:0 auto;">
			<div id="content_wrap" style="width: 750px;height: 450px;">
				<div class="slider_body">
		        	<div class="slider_selected_left">
		            	<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: red;visibility: hidden;">*</span>用户名：</span>
		        	</div>
		        	<div class="slider_selected_right" style="">
		        		<div class="img_wrap">
		        			<div class="select_wrap select_input_null">
		                    	<input id="LoginName" name="loginName" type="text" class="slider_input" value="${userObj.loginName}" readonly="readonly">
<!--		                    	<a id="LoginName" class="empty"></a>-->
		                    </div>
		                </div>  
		            </div>
		        </div>
		        <div class="slider_body">
		        	<div class="slider_selected_left">
		            	<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: red;">*</span>姓名：</span>
		        	</div>
		        	<div class="slider_selected_right" style="">
		        		<div class="img_wrap">
		        			<div class="select_wrap select_input_wrap">
		                    	<input id="UserName" name="userName" type="text" class="slider_input" maxlength="15" value="${userObj.userName}">
		                    	<a id="UserName" class="empty"></a>
		                    </div>
		                </div>  
		            </div>
		        </div>
		        <div class="slider_body">
		        	<div class="slider_selected_left">
		            	<span id="Telphone_span" class="layer_span" style="color:#900b09">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;手机号码：</span>
		        	</div>
		        	<div class="slider_selected_right" style="">
		        		<div class="img_wrap">
		        			<div class="select_wrap select_input_wrap">
		                    	<input id="Telphone" name="telphone" type="text" class="slider_input" maxlength="11" value="${userObj.telPhone}" placeholder="11位数字的手机号码！">
		                    	<a id="Telphone" class="empty"></a>
		                    </div>
		                </div>  
		            </div>
		        </div>
		        <div class="slider_body">
		        	<div class="slider_selected_left">
		            	<span id="IdentityCard_span" class="layer_span" style="color:#900b09">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;身份证号码：</span>
		        	</div>
		        	<div class="slider_selected_right" style="">
		        		<div class="img_wrap">
		        			<div class="select_wrap select_input_wrap">
		                    	<input id="IdentityCard" name="identityCard" type="text" class="slider_input" maxlength="18" value="${userObj.identityCard}" placeholder="18位的身份证号码！">
		                    	<a id="IdentityCard" class="empty"></a>
		                    </div>
		                </div>  
		            </div>
		        </div>
		        <div class="slider_body">
		        	<div class="slider_selected_left">
		            	<span><span style="color: red;<c:if test="${user.loginName eq userObj.loginName or userObj.position eq '99999'}">visibility: hidden;</c:if>">*</span>选择隶属部门：</span>
		        	</div>
		        	<div id="deptDiv" class="slider_selected_right">
						<input class="input_select xiala" id="dept_downlist" readonly="readonly" type="text" value="==请选择==" onclick="javascript:showTree();"/> 
						<input type="hidden" id="dept_select" name="property" value=""/>
						<input type="hidden" id="system_no_select" name="property" value=""/>
						<div id="treeUl" class="ul" style="<c:if test="${user.loginName eq userObj.loginName or userObj.position eq '99999'}">visibility: hidden;</c:if>"> 
							<ul id="deptTree" class="ztree" style="height: 200px;"></ul>
						</div> 
					</div>
		        </div>
		        <div class="slider_body">
		        	<div class="slider_selected_left">
		            	<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;隶属部门：</span>
		        	</div>
		        	<div class="slider_selected_right" style="">
		        		<div class="img_wrap">
		        			<div class="select_wrap select_input_null">
		                    	<input id="deptName" name="deptName" type="text" class="slider_input" value="${userObj.deptName}" readonly="readonly">
		                    </div>
		                </div>  
		            </div>
		        </div>
		        <div class="slider_body">
		        	<div class="slider_selected_left">
<!-- 		        		<c:if test="${user.loginName eq userObj.loginName or userObj.position eq '99999'}"></c:if> -->
		            	<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style='color: red;visibility: hidden;'>*</span>用户角色：</span>
		        	</div>
					<!--  修改本人及超级管理员的信息，不允许修改角色-->
					<%
						if(!user.getLoginName().equals(userObj.getLoginName()) && !"99999".equals(userObj.getPosition())){
					%>
				        	<div class="slider_selected_right dropdown dropdowns" onclick="slider(this)">
								<input class="input_select xiala" id="role_downlist" readonly="readonly" type="text" value="==请选择=="/>
								<input type="hidden" id="RoleSelect" name="property" value=""/>
								<div class="ul">
									<div id="role_div" class="li" data-value="" onclick="sliders(this)">==请选择==</div>
									<c:forEach items="${roleList}" var="role">
										<c:set var="roleId" value="${role.id}${':'}${role.roleName}${':'}${role.roleType}"/>
							        	<div id="${role.id}" class="li" data-value="${roleId}" onclick="sliders(this)">${role.roleName}</div>
									</c:forEach>
								</div>
							</div>
					<%
						} else {
					%>
							<div class="slider_selected_right" style="">
				        		<div class="img_wrap">
				        			<div class="select_wrap select_input_null">
										<input type="hidden" id="RoleSelect" name="property" value="<%=userObj.getRoleId()+":"+userObj.getRoleName()+":"+userObj.getPosition() %>"/>
				                    	<input id="roleName" name="roleName" type="text" class="slider_input" value="${userObj.roleName}" readonly="readonly">
										<!-- <a id="roleName" class="empty"></a> -->
				                    </div>
				                </div>  
				            </div>
					<%
						}
					%>
				</div>
				<div class="slider_body">
		        	<div class="slider_selected_left">
		            	<span id="khbm_span" class="layer_span" style="color:#900b09">&nbsp;隶属考核部门：</span>
		        	</div>
		        	<div class="slider_selected_right dropdown dropdowns" onclick="slider(this)">
						<input class="input_select xiala" id="khbm_downlist" readonly="readonly" type="text" value="<%=(userObj != null && !"".equals(userObj.getLskhbmmc().trim())? userObj.getLskhbmmc().trim() : "==请选择==") %>"/>
						<input type="hidden" id="khbmSelect" name="property" value="<%=(userObj != null && !"".equals(userObj.getLskhbm().trim())? userObj.getLskhbm().trim() : "") %>"/>
						<div class="ul">
							<div id="khbm_div" class="li" data-value="" onclick="sliders(this)">==请选择==</div>
							<c:forEach items="${khbmList}" var="khbm">
					        	<div class="li" data-value="${khbm.deptNo}" onclick="sliders(this)">${khbm.deptName}</div>
							</c:forEach>
						</div>
					</div>
		        </div>
		        <div class="slider_body_textarea">
		        	<div class="slider_selected_left">
		            	<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: red;visibility: hidden;">*</span>备注：</span>
		        	</div>
		        	<div class="slider_selected_right" style="">
	                	<textarea id="remark" name="remark" style="width: 580px;height: 80px;" maxlength="500">${userObj.remark}</textarea>
		            </div>
		        </div>
		        <div class="button_wrap clear_both">
					<input id="saveBt" type="button" class="button_blue" value="保存">
					<input id="backBt" type="button" class="button_blue" value="关闭">
				</div>
			</div>
		</div>
		<div class="mask"></div>
	</body>
	    <script type="text/javascript" src="<%=basePath%>common/js/jquery.min.js"></script>
		<script type="text/javascript" src="<%=basePath%>common/js/jquery.ztree.core-3.5.js"></script>
		<script type="text/javascript" src="<%=basePath%>common/js/jquery.ztree.excheck-3.5.js"></script>
		<script type="text/javascript" src="<%=basePath%>common/js/xiala.js"></script>
		<script type="text/javascript" src="<%=basePath%>common/js/common.js"></script>
		<script type="text/javascript" src="<%=basePath%>common/js/layer/layer.js" ></script>
	    <script type="text/javascript">
			//文件加载完毕之后执行
			$(function(){
				//返回按钮
				$("#backBt").click(function() {
					//window.location = '<%=basePath%>user/initUserManage.do?' + new Date().getTime();
					var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
					parent.layer.close(index);
				});
				
				//保存按钮
				$("#saveBt").click(function() {
					//获取数据
					var loginName = $.trim($("#LoginName").val());//用户名
					var userName = $.trim($("#UserName").val());//姓名
					var telphone = $.trim($("#Telphone").val());//手机号码
					var identityCard = $.trim($("#IdentityCard").val());//身份证号码
					
					var deptId = $.trim($("#dept_select").val());//部门ID
					var deptName = $.trim($("#dept_downlist").val());//部门名称
					var systemNo = $.trim($("#system_no_select").val());//系统自身部门编码
					
					var roleId = $.trim($("#RoleSelect").val());//角色ID:角色名称:角色类型
					var remark = $.trim($("#remark").val());//备注
					
					var khbm = $.trim($("#khbmSelect").val());//考核部门
					var khbmmc = $.trim($("#khbm_downlist").val());//考核部门名称
					
					//验证信息
					if(validateLoginName() && validateUserName() && validateTelphone() && validateIdentityCard() && validateDept()){
						//显示进度条
						var index = layer.load();
						
						//提交
						$.ajax({
							url:'<%=basePath %>/user/updateUser.do?' + new Date().getTime(),
							method:"post",
							data:{id:<%=userObj.getId() %>,loginName:loginName,userName:userName,telphone:telphone,identityCard:identityCard,deptId:deptId,systemNo:systemNo,deptName:deptName,roleId:roleId,remark:remark,khbm:khbm,khbmmc:khbmmc},
							success:function(data){
								//关闭进度条
					    		layer.close(index);
								if(data.result == "1"){//修改成功！
									alert('修改成功！');
									parent.window.reloadData();
									$("#backBt").trigger("click");
								} else if(data.result == "3"){//身份证号不正确
									alert('身份证号不正确！');
								} else if(data.result == "0"){//修改失败！    
									alert('修改失败！');
								}
							},
							error: function () {//请求失败处理函数
								//关闭进度条
					    		layer.close(index);
								alert('修改失败！');
							}
						});
					}
				});
				
				//触发角色单选，赋默认值
				$("<%="#" + userObj.getRoleId() %>").trigger("click");//
				
				$("#Telphone_span").mouseover(function(){
					showTip("#Telphone_span", "有效的11位数字手机号码！");
				});
				$("#Telphone_span").mouseleave(function(){
					layer.closeAll('tips');
				});
				$("#IdentityCard_span").mouseover(function(){
					showTip("#IdentityCard_span", "有效的18位身份证号码！");
				});
				$("#IdentityCard_span").mouseleave(function(){
					layer.closeAll('tips');
				});
				$("#khbm_span").mouseover(function(){
					showTip("#khbm_span", "仅供参与涉案类考核的用户使用，其他用户不需要使用该选项（其考核部门由系统默认为本部门）！");
				});
				$("#khbm_span").mouseleave(function(){
					layer.closeAll('tips');
				});
			});
			
			//显示车牌号码输入提示
			function showTip(id, message){
				layer.open({
			           type: 4,
			           shade: 0,
			           time:16000,
			           closeBtn: 0,
			           tips: [3, '#758a99'],
			           content: [message, id]
			    });
			}
			
			// 身份证号验证 
			function isIdCard(cardid) {
      			//身份证正则表达式(18位) 
      			var isIdCard2 = /^[1-9]\d{5}(19\d{2}|[2-9]\d{3})((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])(\d{4}|\d{3}X)$/i;
      			var stard = "10X98765432"; //最后一位身份证的号码
     			var first = [7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2]; //1-17系数
    		 	var sum = 0;
    		 	
    		 	//是否二代身份证
      			if (!isIdCard2.test(cardid)) {
          			return false;
     	 		}
     	 		
     	 		//验证生日
      			var year = cardid.substr(6, 4);
      			var month = cardid.substr(10, 2);
      			var day = cardid.substr(12, 2);
      			var birthday = cardid.substr(6, 8);
      			if (birthday != dateToString(new Date(year + '/' + month + '/' + day))) { //校验日期是否合法
         			return false;
      			}
      			
      			//最后一位校验
      			for (var i = 0; i < cardid.length - 1; i++) {
          			sum += cardid[i] * first[i];
      			}
      			var result = sum % 11;
      			var last = stard[result]; //计算出来的最后一位身份证号码
     			if (cardid[cardid.length - 1].toUpperCase() != last) {
          			return false;
      			}
      			
				return true;
  			}
  			
			//日期转字符串 返回日期格式20080808
  			function dateToString(date) {
      			if (date instanceof Date) {
          			var year = date.getFullYear();
          			var month = date.getMonth() + 1;
          			month = month < 10 ? '0' + month: month;
          			var day = date.getDate();
          			day = day < 10 ? '0' + day: day;
          			return year + month + day;
      			}
      			return '';
  			}
			
			//用户名校验方法
			function validateLoginName(){
				//非空校验
				var value = $.trim($("#LoginName").val());
				if(!value){
					alert('用户名不能为空！');
					return false;
				}
				return true;
			}
			
			//姓名校验方法
			function validateUserName(){
				//非空校验
				var value = $.trim($("#UserName").val());
				if(!value){
					alert('姓名不能为空！');
					return false;
				}
				return true;
			}
			
			//手机号码校验方法
			function validateTelphone(){
				//非空校验
				var value = $.trim($("#Telphone").val());
				//if(!value){
				//	alert('手机号码不能为空！');
				//	return false;
				//}
				 
				//校验是否为正确的手机号码
				if(value != "" && !(/^1[3|4|5|8][0-9]\d{8}$/.test(value))){
					alert('请输入正确的11位手机号码！');
					return false;
				}
				return true;
			}
			
			//身份证号码校验方法
			function validateIdentityCard(){
				//非空校验
				var value = $.trim($("#IdentityCard").val());
				//if(!value){
				//	//获取对应的label,显示错误信息
				//	$("#IdentityCardError").text("身份证号码不能为空！");
				//	showError($("#IdentityCardError"));
				//	return false;
				//}
				 
				//校验是否为正确的身份证号码
				//if(value && !isIdCard(value)){
				//	alert('请输入正确的18位身份证号码！');
				//	return false;
				//}
				return true;
			}
			
			//角色校验方法
			function validateRole(){
				//非空校验
				var value = $.trim($("#RoleSelect").val());
				if(!value){
					alert('请选择用户角色！');
					return false;
				}
				return true;
			}
			
			//部门校验方法
			function validateDept(){
				//非空校验
				var value = $.trim($("#dept_select").val());
				if(!value){
					alert('请选择隶属部门！');
					return false;
				}
				return true;
			}
	    </script>
	    <!-- 部门树-->
		<script type="text/javascript">
		<!--
			//加载部门信息
			function loadDeptData() {
				$.ajax({
					url:'<%=basePath%>/dept/getMaxDeptToJsonWriter.do?' + new Date().getTime(),
					method:"post",
					dataType:"json",
					success:function(data){
						//初始化树
						$.fn.zTree.init($("#deptTree"), setting, data);
						//默认选择部门
						$("#dept_downlist").val('<%=userObj.getDeptName() %>');//部门名称
						$("#dept_select").val('<%=userObj.getDeptId() %>');//部门ID
						$("#system_no_select").val('<%=userObj.getSystemNo() %>');//
					},
					error: function () {//请求失败处理函数
						alert('加载部门信息失败！');
					}
				});
			}
			
			//树的基本设置
			var setting = {
				view: {
					dblClickExpand: true
				},
				async: {  
					enable: true,  
					url:"<%=basePath%>/dept/getDeptByParentNoToJson.do",  
					autoParam:["nodeNo"],
					dataFilter: filter
				},				
				check: {
					enable: false
				},
				data: {
					key: {
						name:"nodeName",
						title:"nodeName"
					},
					simpleData: {
						enable: true,
						idKey: "nodeNo",
						pIdKey: "nodeParent"
					}
				},
				callback: {
					onClick: onClickTree
				}
			};
			
			function filter(treeId, parentNode, childNodes) {
				if (!childNodes) return null;
				//for (var i=0, l=childNodes.length; i<l; i++) {
					//childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
				//}
				return childNodes;
			}
		
			//点击树中某一项时响应
			function onClickTree(event, treeId, treeNode, clickFlag) {
				var fdStart = treeNode.systemNo.indexOf(<%=user.getSystemNo() %>);
				if(fdStart == 0){
					//显示部门名称，并获取部门ID
					$("#dept_downlist").val(treeNode.nodeName);//部门名称
					$("#deptName").val(treeNode.nodeName);//部门名称
					$("#dept_select").val(treeNode.nodeNo);//部门ID
					$("#system_no_select").val(treeNode.systemNo);//系统自身部门编码
				} else {
					alert("只能选择本部门或本部门以下的子部门！");
				}
				
				//隐藏树
				showTree();
			}
		
			//控制树的显示
			function showTree(){
		        if($("#treeUl").css("display") == "none"){ 
		            $("#treeUl").slideDown("fast"); 
		        } else{ 
		            $("#treeUl").slideUp("fast"); 
		        } 
			}
	 
	 		//初始化树
			$(document).ready(function(){
				//加载部门json数据
				loadDeptData();
				
				//单选点击空白关闭
				$("body").click(function(event){
					//不是点击dept_downlist，则隐藏树
					if(event.target.id != "dept_downlist" && event.target.nodeName != "SPAN"){
						$("#treeUl").slideUp("fast");
					}
				});
			});
		//-->
		</script>
</html>