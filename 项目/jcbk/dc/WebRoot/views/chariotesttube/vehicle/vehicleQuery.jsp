<%@ page language="java" import="com.dyst.utils.*,com.dyst.systemmanage.entities.User,com.dyst.BaseDataMsg.entities.Dictionary,com.dyst.chariotesttube.entities.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
	
	//获取用户信息
	User userObj = (User)request.getSession().getAttribute(StaticUtils.SESSION_NAME_USER_DATA);
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<base href="<%=basePath%>">
		<title>机动车信息查询</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">    
		<meta http-equiv="keywords" content="缉查,布控,缉查布控,缉查布控系统">
		<meta http-equiv="description" content="深圳市公安局缉查布控系统-机动车信息查询">
		
		<script type="text/javascript" src="<%=basePath%>common/js/jquery.min.js"></script>
		<script type="text/javascript" src="<%=basePath%>common/js/layer/layer.js" ></script>
		<script type="text/javascript">
			window.moveTo(0, 0);//最大化打开窗体
    		window.resizeTo(screen.width, screen.height);
    		
			$(document).ready(function(){
		  		//根据条件获取符合条件的数据
				$("#searchBt").click(function() {
					//获取条件
					//var hphm = $.trim($("#hphmWin").val());
					var hphm = $.trim($("#cphid").val());//车牌号码
					var hpzl = $.trim($("#hpzl_select").val());//号牌种类
					
					//判断条件是否为空
					if(hphm == null || hphm == ""){
						alert("车牌号码不能为空！");
						return;
					}
					if(hpzl == null || hpzl == ""){
						alert("请先选择号牌种类！");
						return;
					}
					
					//完善身份证信息，然后接着查询
					if($.trim($("#idcard").val()) == ""){
					    var msg = '您的身份信息不完善，“机动车信息查询”功能需要提供个人身份证号，\n请先完善身份信息后再查询！';
						if (confirm(msg) == true) {
							doIdCardUI();
						}
						return;
					}
					
					//显示进度条
					var index = layer.load();
						
					//提交
					$.ajax({
						url:'<%=basePath%>/cjg/queryVehicleInfo.do?' + new Date().getTime(),
						method:"post",
						data:{hphm:hphm, hpzl:hpzl},
						success:function(data){
							//关闭进度条
					    	layer.close(index);
							$('#dataDiv').html(data);
						},
						error: function () {//请求失败处理函数
							//关闭进度条
					    	layer.close(index);
							alert('查询失败！');
						}
					});
				});
				
				//重置..按钮
				$("#resetBt").click(function() {
					//$("#hphmWin").val("");//车牌号码
					$("#cphid").val("");//车牌号码
		  			$('#hpzl_div').trigger("click");//号牌种类
				});
			});
			
			var dicArr = jQuery.parseJSON('${dicJson}');
			//车身颜色翻译
			function csysFunction(csysDm){
				for(var i=0;i < dicArr.length;i++){
					var obj = dicArr[i];
					if(obj.typeCode == 'CSYS' && obj.typeSerialNo == csysDm){
						return obj.typeDesc;
					}
				}
				return csysDm;
			}
			//车辆状态翻译
			function clztFunction(clztDm){
				for(var i=0;i < dicArr.length;i++){
					var obj = dicArr[i];
					if(obj.typeCode == 'CLZT' && obj.typeSerialNo == clztDm){
						return obj.typeDesc;
					}
				}
				return clztDm;
			}
			//
			function doIdCardUI(){
				var url = '<%=basePath%>views/chariotesttube/vehicle/idcard.jsp';
				layer.open({
			       type: 2,
			       title: '身份证号录入窗口',
			       shadeClose: false,
			       shade: 0.5,
			       area: ['350px', '150px'],
			       content: url //iframe的url
			   });	
			}
		</script>
	</head>
	<body>
		<jsp:include page="/common/Head.jsp"/>
		<div id="divTitle">
			<span id="spanTitle">当前位置：车辆综合查询&gt;&gt;机动车信息查询</span>
		</div>
	    <div class="content">
	    	<div class="content_wrap">
	    		<fieldset style="margin: 0px 10px 0px 0px;">
					<legend  style="color:#FF3333">查询条件</legend>
			        <div class="slider_body">
		                <div class="slider_selected_left">
		                    <span>&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: red;">*</span>车牌号码：</span>
		                </div>
		                <div class="slider_selected_right">
		                    <div class="img_wrap">
		                        <div class="select_wrap input_wrap_select">
		                            <input id="cphid" name="hphm" type="text" class="slider_input">
		                            <a class="empty" href="javascript:doCplrUI()"></a>
		                        </div>
		                    </div>  
		                </div>
		        	</div>
		        	<div class="slider_body">
		                <div class="slider_selected_left">
		                    <span>&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: red;">*</span>号牌种类：</span>
		                </div>
		                <div class="slider_selected_right dropdown dropdowns" onclick="slider(this)">
						    <input class="input_select xiala" id="hpzl_downlist" readonly="readonly" type="text" value="==请选择=="/>
						    <input type="hidden" id="hpzl_select" name="property" value=""/>
						    <div class="ul">
						    	<div id="hpzl_div" class="li" data-value="" onclick="sliders(this)">==请选择==</div>
						       	<c:forEach items="${dicList}" var="dic">
						       		<c:if test="${dic['typeCode'] eq 'HPZL'}">
				                    	<div class="li" data-value="${dic['typeSerialNo']}" onclick="sliders(this)">${dic['typeDesc']}</div>
			                    	</c:if>
						 		</c:forEach>
						    </div>
						</div>
		        	</div>
		        	<div class="slider_body" style="visibility: hidden;">
		                <div class="slider_selected_left">
		                    <span>&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: red;">*</span>身份证号：</span>
		                </div>
		                <div class="slider_selected_right">
		                    <div class="img_wrap">
		                        <div class="select_wrap input_wrap_select">
		                            <input id="idcard" name="idcard" type="text" class="slider_input" readonly="readonly" value="<%=userObj.getIdentityCard() != null ? userObj.getIdentityCard().trim():""%>">
		                        </div>
		                    </div>  
		                </div>
		        	</div>
			        <div class="button_wrap clear_both">
				    	<input id="searchBt" type="button" class="button_blue" value="查询">
				    	<input id="resetBt" type="button" class="button_blue" value="重置">
				    </div>
				</fieldset>
			    <fieldset style="margin: 20px 10px 0px 0px;">
			      	<legend style=" color:#FF3333">查询结果</legend>
			      	<div id="dataDiv" style="width: 100%;height: 100%;">
			      		<!-- 只是模板，信息显示在showVehicleInfo.jsp中，修改模板，同时需要修改 showVehicleInfo.jsp -->
						<div class="slider_body">
							<div class="slider_selected_left">
								<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;中文品牌：</span>
							</div>
							<div class="slider_selected_right">
								<div class="img_wrap">
									<div class="select_wrap select_input_wrap">
										<input id="zwpp" type="text" class="slider_input" readonly="readonly">
									</div>
								</div>
							</div>
						</div>
						<div class="slider_body">
							<div class="slider_selected_left">
								<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;车辆型号：</span>
							</div>
							<div class="slider_selected_right">
								<div class="img_wrap">
									<div class="select_wrap select_input_wrap">
										<input id="clxh" type="text" class="slider_input" readonly="readonly">
									</div>
								</div>
							</div>
						</div>
						<div class="slider_body">
							<div class="slider_selected_left">
								<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;发动机号：</span>
							</div>
							<div class="slider_selected_right">
								<div class="img_wrap">
									<div class="select_wrap select_input_wrap">
										<input id="fdjh" type="text" class="slider_input" readonly="readonly">
									</div>
								</div>
							</div>
						</div>
						<div class="slider_body" style="position: relative; clear: both;">
							<div class="slider_selected_left">
								<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;车驾号：</span>
							</div>
							<div class="slider_selected_right">
								<div class="img_wrap">
									<div class="select_wrap select_input_wrap">
										<input id="cjh" type="text" class="slider_input" readonly="readonly">
									</div>
								</div>
							</div>
						</div>
						<div class="slider_body">
							<div class="slider_selected_left">
								<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;车身颜色：</span>
							</div>
							<div class="slider_selected_right">
								<div class="img_wrap">
									<div class="select_wrap select_input_wrap">
										<input id="csys" type="text" class="slider_input" readonly="readonly">
									</div>
								</div>
							</div>
						</div>
						<div class="slider_body">
							<div class="slider_selected_left">
								<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;车辆状态：</span>
							</div>
							<div class="slider_selected_right">
								<div class="img_wrap">
									<div class="select_wrap select_input_wrap">
										<input id="jdczt" type="text" class="slider_input" readonly="readonly">
									</div>
								</div>
							</div>
						</div>
						<div class="slider_body" style="position: relative; clear: both;">
							<div class="slider_selected_left">
								<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;车辆类型：</span>
							</div>
							<div class="slider_selected_right" style="">
								<div class="img_wrap">
									<div class="select_wrap select_input_wrap">
										<input id="cllx" type="text" class="slider_input" readonly="readonly">
									</div>
								</div>
							</div>
						</div>
						<div class="slider_body">
							<div class="slider_selected_left">
								<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;发牌机关：</span>
							</div>
							<div class="slider_selected_right">
								<div class="img_wrap">
									<div class="select_wrap select_input_wrap">
										<input id="fpjg" type="text" class="slider_input" readonly="readonly">
									</div>
								</div>
							</div>
						</div>
						<div class="slider_body">
							<div class="slider_selected_left">
								<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;发证机构：</span>
							</div>
							<div class="slider_selected_right">
								<div class="img_wrap">
									<div class="select_wrap select_input_wrap">
										<input id="fzjg" type="text" class="slider_input" readonly="readonly">
									</div>
								</div>
							</div>
						</div>
						<div class="slider_body" style="position: relative; clear: both;">
							<div class="slider_selected_left">
								<span>&nbsp;&nbsp;车辆所有人：</span>
							</div>
							<div class="slider_selected_right" style="">
								<div class="img_wrap">
									<div class="select_wrap select_input_wrap">
										<input id="jdcsyr" type="text" class="slider_input" readonly="readonly">
									</div>
								</div>
							</div>
						</div>
						<div class="slider_body">
							<div class="slider_selected_left">
								<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;联系电话：</span>
							</div>
							<div class="slider_selected_right">
								<div class="img_wrap">
									<div class="select_wrap select_input_wrap">
										<input id="syrdh" type="text" class="slider_input" readonly="readonly">
									</div>
								</div>
							</div>
						</div>
						<div class="slider_body">
							<div class="slider_selected_left">
								<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;身份证号：</span>
							</div>
							<div class="slider_selected_right">
								<div class="img_wrap">
									<div class="select_wrap select_input_wrap">
										<input id="sfzh" type="text" class="slider_input" readonly="readonly">
									</div>
								</div>
							</div>
						</div>
						<div class="slider_body" style="position: relative; clear: both;">
							<div class="slider_selected_left">
								<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;详细住址：</span>
							</div>
							<div class="slider_selected_right">
			                	<textarea id="syrxxdz" style="width: 580px;height: 20px;" readonly="readonly"></textarea>
				            </div>
						</div>
					</div>
			   </fieldset>
			</div>
   		</div>
    	<jsp:include page="/common/Foot.jsp"/>
	</body>
</html>