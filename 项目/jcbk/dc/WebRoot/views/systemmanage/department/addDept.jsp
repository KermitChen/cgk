<%@ page language="java" import="java.util.*,com.dyst.base.utils.*,com.dyst.systemmanage.entities.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<base href="<%=basePath %>">
		<title>添加部门信息页面</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="缉查,布控,缉查布控,缉查布控系统">
		<meta http-equiv="description" content="深圳市公安局缉查布控系统-添加部门信息页面">
		
		<link rel="stylesheet" href="<%=basePath%>common/css/style.css" type="text/css">
		<link rel="stylesheet" href="<%=basePath%>common/css/zTreeStyle/zTreeStyle.css" type="text/css">
		<style type="text/css">
			.layer_span:hover {
				color:#FF0000 !important;
			}
		</style>
	</head>
	<body>
		<div id="content" style="width: 800px;height: 400px;margin:0 auto;">
			<div id="content_wrap" style="width: 750px;height: 400px;">
				<div class="slider_body">
		        	<div class="slider_selected_left">
		            	<span id="DeptNo_span" class="layer_span" style="color:#900b09">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: red;visibility: hidden;">*</span>部门编号：</span>
		        	</div>
		        	<div class="slider_selected_right" style="">
		        		<div class="img_wrap">
		        			<div class="select_wrap select_input_wrap">
		                    	<input id="DeptNo" name="deptNo" type="text" class="slider_input" maxlength="20">
		                    	<a id="DeptNo" class="empty"></a>
		                    </div>
		                </div>  
		            </div>
		        </div>
				<div class="slider_body">
		        	<div class="slider_selected_left">
		            	<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: red;">*</span>部门名称：</span>
		        	</div>
		        	<div class="slider_selected_right" style="">
		        		<div class="img_wrap">
		        			<div class="select_wrap select_input_wrap">
		                    	<input id="DeptName" name="deptName" type="text" class="slider_input" maxlength="40">
		                    	<a id="DeptName" class="empty"></a>
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
		                    	<input id="Telphone" name="telphone" type="text" class="slider_input" maxlength="11">
		                    	<a id="Telphone" class="empty"></a>
		                    </div>
		                </div>  
		            </div>
		        </div>	
				<div class="slider_body">
		        	<div class="slider_selected_left">
		            	<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: red;">*</span>上级部门：</span>
		        	</div>
		        	<div id="deptDiv" class="slider_selected_right">
						<input class="input_select xiala" id="dept_downlist" readonly="readonly" type="text" value="==请选择==" onclick="javascript:showTree();"/> 
						<input type="hidden" id="dept_select" name="property" value=""/>
						<div id="treeUl" class="ul"> 
							<ul id="deptTree" class="ztree" style="height: 200px;"></ul>
						</div> 
					</div>
		        </div>
		        <div class="slider_body  hidden_div">
		            <div class="slider_selected_left">
		                <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: red;">*</span>绩效考核：</span>
		            </div>
		            <div class="slider_selected_right dropdown" id="dropdown_quanxuan">
		            	<input id="jxkh_id1" type="radio" name="jxkh" value="0" checked="checked"/> 否&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input id="jxkh_id2" type="radio" name="jxkh" value="1"/> 是
		            </div>
		        </div>
		        <div class="slider_body_textarea">
		        	<div class="slider_selected_left">
		            	<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: red;visibility: hidden;">*</span>备注：</span>
		        	</div>
		        	<div class="slider_selected_right" style="">
	                	<textarea id="remark" name="remark" style="width: 580px;height: 80px;" maxlength="500"></textarea>
		            </div>
		        </div>
				<div class="button_wrap clear_both">
					<input id="addBt" type="button" class="button_blue" value="保存">
					<input id="backBt" type="button" class="button_blue" value="关闭">
				</div>
			</div>
		</div>
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
					//window.location = '<%=basePath%>dept/initDeptManage.do?' + new Date().getTime();
					var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
					parent.layer.close(index);
				});
				
				//保存按钮
				$("#addBt").click(function() {
					//获取数据
					var deptNo = $.trim($("#DeptNo").val());//部门编号
					var deptName = $.trim($("#DeptName").val());//部门名称
					var telphone = $.trim($("#Telphone").val());//联系电话
					var parentId = $.trim($("#dept_select").val());//部门ID
					var parentName = $.trim($("#dept_downlist").val());//部门名称
					var jxkh = $.trim($("input[name='jxkh']:checked").val());//绩效考核
					var remark = $.trim($("#remark").val());//备注
					
					//验证信息
					if(validateDeptNo() && validateDeptName() && validateTelphone() && validateDept()){
						//显示进度条
						var index = layer.load();
						
						//提交
						$.ajax({
							url:'<%=basePath %>/dept/addDept.do?' + new Date().getTime(),
							method:"post",
							data:{deptNo:deptNo, deptName:deptName, telphone:telphone, parentId:parentId, parentName:parentName, jxkh:jxkh, remark:remark},
							success:function(data){
								//关闭进度条
					   	 		layer.close(index);
								
								if(data.result == "1"){//添加成功！
									//重置
									//reset();
									alert('添加成功！');
									parent.window.reloadData();
									$("#backBt").trigger("click");
								} else if(data.result == "2"){//部门编号已存在
									alert('部门编号已被使用！');
								} else if(data.result == "3"){//部门名称已存在
									alert('部门名称已被使用！');
								} else if(data.result == "0"){//添加失败！    
									alert('添加失败！');
								}
							},
							error: function () {//请求失败处理函数
								//关闭进度条
					   	 		layer.close(index);
							
								alert('添加失败！');
							}
						});
					}
				});
				
				$("#DeptNo_span").mouseover(function(){
					showTip("#DeptNo_span");
				});
				$("#DeptNo_span").mouseleave(function(){
					layer.closeAll('tips');
				});
			});
			
			//显示车牌号码输入提示
			function showTip(id){
				layer.open({
			           type: 4,
			           shade: 0,
			           time:8000,
			           closeBtn: 0,
			           tips: [3, '#758a99'],
			           content: ['由6至20位数字组成！', id]
			       });
			}
			
			//重置
			function reset(){
				$("#DeptNo").val("");//部门编号
		  		$("#DeptName").val("");//部门名称
		  		$("#Telphone").val("");//联系电话
		  		$("#remark").val("");//备注
		  		
		  		//重新获取部门信息，并初始部门树
		  		$("#dept_downlist").val("==请选择==");//部门名称
				$("#dept_select").val("");//部门ID
				loadDeptData();
			}
			
			//部门编号校验方法
			function validateDeptNo(){
				//非空校验
				var value = $.trim($("#DeptNo").val());
				//验证是否有非法字符
				if(value && !value.match(/^([0-9]{12})$/)){
					alert('部门编号由12位数字组成！');
					return false;
				}
				return true;
			}
			
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
				return true;
			}
			
			//联系电话校验方法
			function validateTelphone(){
				//非空校验
				var value = $.trim($("#Telphone").val());
				//验证是否有非法字符
				if(value && !value.match(/^([0-9]{5,11})$/)){
					alert('只允许输入11位的数字！');
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
					url:'<%=basePath%>/dept/getAllDeptToJson.do?' + new Date().getTime(),
					method:"post",
					dataType:"json",
					success:function(data){
						//初始化树
						$.fn.zTree.init($("#deptTree"), setting, data);
						
						//展开第一个节点
						var zTree = $.fn.zTree.getZTreeObj("deptTree");
				    	var nodes = zTree.getNodes();//获取节点
						if (nodes.length > 0) {
				        	zTree.expandNode(nodes[0], true, false);
						}
						
						//如果指定了父级部门，则默认选中
						<%
							String parentDeptNo = (String)request.getAttribute("parentDeptNo");
							if(parentDeptNo != null && !"".equals(parentDeptNo)){
						%>
								var treeObj = $.fn.zTree.getZTreeObj("deptTree");
								var node = treeObj.getNodeByParam("deptNo", <%=parentDeptNo %>);
								treeObj.selectNode(node);
								treeObj.setting.callback.onClick(null, treeObj.setting.treeId, node);//调用
								//收起
								//隐藏树
								showTree();
						<%
							}
						%>
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
				data: {
					key: {
						name:"deptName",
						title:"deptName"
					},
					simpleData: {
						enable: true,
						idKey: "deptNo",
						pIdKey: "parentNo"
					}
				},
				callback: {
					onClick: onClickTree
				}
			};
		
			//点击树中某一项时响应
			function onClickTree(event, treeId, treeNode, clickFlag) {
				//显示部门名称，并获取部门ID
				$("#dept_downlist").val(treeNode.deptName);//部门名称
				$("#dept_select").val(treeNode.deptNo);//部门ID
				
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