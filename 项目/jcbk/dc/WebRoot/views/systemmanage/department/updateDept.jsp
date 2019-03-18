<%@ page language="java" import="java.util.*,com.dyst.base.utils.*,com.dyst.systemmanage.entities.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	//获取需要编辑的数据
	Department deptObj = (Department)request.getAttribute("deptObj");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<base href="<%=basePath %>">
		<title>修改部门信息页面</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">    
		<meta http-equiv="keywords" content="缉查,布控,缉查布控,缉查布控系统">
		<meta http-equiv="description" content="深圳市公安局缉查布控系统-修改部门信息页面">
		
		<link rel="stylesheet" href="<%=basePath%>common/css/style.css" type="text/css">
		<link rel="stylesheet" href="<%=basePath%>common/css/zTreeStyle/zTreeStyle.css" type="text/css">
	</head>
	<body>
		<input id="deptId" type="hidden" value="<%=(deptObj != null ? deptObj.getId():"00") %>"/>
		<div id="content" style="width: 800px;height: 400px;margin:0 auto;">
			<div id="content_wrap" style="width: 750px;height: 400px;">
				<div class="slider_body">
		        	<div class="slider_selected_left">
		            	<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: red;visibility: hidden;">*</span>部门编号：</span>
		        	</div>
		        	<div class="slider_selected_right" style="">
		        		<div class="img_wrap">
		        			<div class="select_wrap select_input_null">
		                    	<input id="DeptNo" name="deptNo" type="text" class="slider_input" value="<c:if test="${not empty deptObj}">${deptObj.deptNo}</c:if>" readonly="readonly"/>
<!--		                    	<a id="DeptNo" class="empty"></a>-->
		                    </div>
		                </div>  
		            </div>
		        </div>
		        <div class="slider_body">
		        	<div class="slider_selected_left">
		            	<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style='color: red;<c:if test="${not empty deptObj and deptObj.infoSource eq '2'}">visibility: hidden;</c:if>'>*</span>部门名称：</span>
		        	</div>
		        	<div class="slider_selected_right" style="">
		        		<div class="img_wrap">
		        			<div class="select_wrap select_input_wrap">
		                    	<input id="DeptName" name="deptName" type="text" class="slider_input" maxlength="40" value="<c:if test="${not empty deptObj}">${deptObj.deptName}</c:if>" <c:if test="${not empty deptObj and deptObj.infoSource eq '2'}">readonly="readonly"</c:if>/>
								<a id="DeptName" <c:if test="${empty deptObj or deptObj.infoSource ne '2'}">class="empty"</c:if>></a>
		                    </div>
		                </div>  
		            </div>
		        </div>
		        <div class="slider_body">
		        	<div class="slider_selected_left">
		            	<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: red;visibility: hidden;">*</span>联系电话：</span>
		        	</div>
		        	<div class="slider_selected_right" style="">
		        		<div class="img_wrap">
		        			<div class="select_wrap select_input_wrap">
		                    	<input id="Telphone" name="telphone" type="text" class="slider_input" maxlength="11" value="<c:if test="${not empty deptObj}">${deptObj.deptTelephone}</c:if>" <c:if test="${not empty deptObj and deptObj.infoSource eq '2'}">readonly="readonly"</c:if>>
		                    	<a id="Telphone" <c:if test="${empty deptObj or deptObj.infoSource ne '2'}">class="empty"</c:if>></a>
		                    </div>
		                </div>  
		            </div>
		        </div>	
				<div class="slider_body">
		        	<div class="slider_selected_left">
		            	<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style='color: red;visibility: hidden;'>*</span>上级部门：</span>
		        	</div>
		        	<div class="slider_selected_right" style="">
				        <div class="img_wrap">
				        	<div class="select_wrap select_input_wrap">
				           		<input id="dept_downlist" type="text" class="slider_input" value="<c:if test="${not empty deptObj}">${deptObj.parentName}</c:if>" readonly="readonly"/>
				            	<input id="dept_select" type="hidden" name="property" value="<c:if test="${not empty deptObj}">${deptObj.parentNo}</c:if>"/>
				            </div>
				        </div>  
				    </div>
		        </div>
		        <div class="slider_body  hidden_div">
		            <div class="slider_selected_left">
		                <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: red;">*</span>绩效考核：</span>
		            </div>
		            <div class="slider_selected_right dropdown" id="dropdown_quanxuan">
		            	<input id="jxkh_id1" type="radio" name="jxkh" value="0" <c:if test="${not empty deptObj and deptObj.jxkh eq '0'}">checked="checked"</c:if>/> 否&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input id="jxkh_id2" type="radio" name="jxkh" value="1" <c:if test="${not empty deptObj and deptObj.jxkh eq '1'}">checked="checked"</c:if>/> 是
		            </div>
		        </div>
		        <div class="slider_body_textarea">
		        	<div class="slider_selected_left">
		            	<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: red;visibility: hidden;">*</span>备注：</span>
		        	</div>
		        	<div class="slider_selected_right" style="">
	                	<textarea id="remark" name="remark" style="width: 580px;height: 80px;" maxlength="500"><c:if test="${not empty deptObj}">${deptObj.remark}</c:if></textarea>
		            </div>
		        </div>
		        <div class="button_wrap clear_both">
					<input id="saveBt" type="button" class="button_blue" value="保存">
					<input id="backBt" type="button" class="button_blue" value="关闭">
				</div>
			</div>
		</div>	
	</body>
<%
	if(deptObj == null){
%>
		<SCRIPT type="text/javascript">
			alert("加载信息失败！");
		</SCRIPT>
<%
	}
%>
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
					//window.location = '<%=basePath%>dept/initDeptManage.do?' + new Date().getTime();
					var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
					parent.layer.close(index);
				});
				
				//保存按钮
				$("#saveBt").click(function() {
					//获取数据
					var deptId = $.trim($("#deptId").val());//部门Id
					var deptNo = $.trim($("#DeptNo").val());//部门编号
					var deptName = $.trim($("#DeptName").val());//部门名称
					var telphone = $.trim($("#Telphone").val());//联系电话
					var parentId = $.trim($("#dept_select").val());//部门ID
					var parentName = $.trim($("#dept_downlist").val());//部门名称
					var jxkh = $.trim($("input[name='jxkh']:checked").val());//绩效考核
					var remark = $.trim($("#remark").val());//备注
					
					//验证信息
					if(validateDeptName() && validateTelphone() && validateDept()){
						//显示进度条
						var index = layer.load();
						
						//提交
						$.ajax({
							url:'<%=basePath %>/dept/updateDept.do?' + new Date().getTime(),
							method:"post",
							data:{id:deptId, deptNo:deptNo, deptName:deptName, telphone:telphone, parentId:parentId, parentName:parentName, jxkh:jxkh, remark:remark},
							success:function(data){
								//关闭进度条
					   	 		layer.close(index);
								
								if(data.result == "1"){//修改成功！
									alert('修改成功！');
									parent.window.reloadData();
									$("#backBt").trigger("click");
									//加载部门json数据
									//loadDeptData(parentId);
								} else if(data.result == "2"){//部门名称已存在
									alert('部门名称已被使用！');
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
			});
			
			//部门名称校验方法
			function validateDeptName(){
				//非空校验
				var value = $.trim($("#DeptName").val());
				if(!value){
					alert('部门名称不能为空！');
					return false;
				}
				return true;
			}
			
			//上级部门校验方法
			function validateDept(){
				//非空校验
				var value = $.trim($("#dept_select").val());
				if(!value){
					alert('上级部门不能为空！');
					return false;
				}
				
				//顶级部门不允许修改上级部门
				if('440300' == <%=(deptObj != null ? deptObj.getDeptNo():"00") %> && <%=(deptObj != null ? deptObj.getDeptNo():"00") %> == <%=(deptObj != null ? deptObj.getParentNo():"00") %> 
					&& value != <%=(deptObj != null ? deptObj.getParentNo():"00") %>){
					alert('顶级部门不允许修改上级部门！');
					return false;
				}
				
				//本部门不能作为上级部门（顶级部门除外）
				if(value == <%=(deptObj != null ? deptObj.getDeptNo():"00") %> && '440300' != <%=(deptObj != null ? deptObj.getDeptNo():"00") %>){
					alert('本部门不能作为上级部门！');
					return false;
				}
				return true;
			}
			
			//联系电话校验方法
			function validateTelphone(){
				//非空校验
				var value = $.trim($("#Telphone").val());
				//验证是否有非法字符
				if(value && !value.match(/^([0-9]{5,11})$/)){
					alert('只允许输入5至11位的数字！');
					return false;
				}
				return true;
			}
	    </script>
</html>