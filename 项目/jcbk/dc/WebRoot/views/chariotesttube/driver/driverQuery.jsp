<%@ page language="java" import="java.util.*,com.dyst.utils.*,com.dyst.systemmanage.entities.*" pageEncoding="utf-8"%>
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
		<title>驾驶员信息查询</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">    
		<meta http-equiv="keywords" content="缉查,布控,缉查布控,缉查布控系统">
		<meta http-equiv="description" content="深圳市公安局缉查布控系统-驾驶员信息查询">
		
		<style type="text/css">
			#menu{overflow:hidden; margin:10px 5px;}
			#menu #nav {display:block;width:100%;padding:0;margin:0;list-style:none;}
			#menu #nav li {float:left;width:120px;border:1px solid #BF9660;}
			#menu #nav li a {display:block;line-height:27px;text-decoration:none;padding:0 0 0 5px; text-align:center; color:#333;}
			#menu_con{clear:left;border-top:none;border:1px solid #BF9660;}
			.tag{overflow:hidden;padding:0;margin:0;}
			.selected{background:#C5A069; color:#fff;}
		</style>
		
		<script type="text/javascript" src="<%=basePath%>common/js/jquery.min.js"></script>
		<script type="text/javascript" src="<%=basePath%>common/js/layer/layer.js"></script>
		<script type="text/javascript">
			window.moveTo(0, 0);//最大化打开窗体
    		window.resizeTo(screen.width, screen.height);
    		
			$(document).ready(function(){
		  		//根据条件获取符合条件的数据
				$("#searchBt").click(function() {
					//获取条件
					//var zjlx = $.trim($("#zjlx_select").val());//证件类型
					var zjhm = $.trim($("#zjhm").val());//证件号码
					
					//判断条件是否为空
					//if(zjlx == null || zjlx == ""){
					//	alert("请先选择证件类型！");
					//	return;
					//}
					if(zjhm == null || zjhm == ""){
						alert("身份证号不能为空！");
						return;
					}
					//校验是否为正确的身份证号码
					//if(zjhm && !isIdCard(zjhm)){
					//	alert("请输入正确的18位身份证号码！");
					//	return;
					//}
					
					//身份证号不能为空
					var identityCard = <%=userObj.getIdentityCard() != null && !"".equals(userObj.getIdentityCard().trim())?"1":"0" %>;
					if(identityCard == "0"){
						alert("您的身份信息不完善，驾驶员查询需要提供个人身份证号，\n请先提供身份证号给管理员，以便完善身份信息！");
						return;
					}
					
					//显示进度条
					var index = layer.load();
					
					//提交
					$.ajax({
						url:'<%=basePath%>/cjg/queryDriverInfo.do?' + new Date().getTime(),
						method:"post",
						data:{jszh:zjhm},
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
		  			//$('#zjlx_select').trigger("click");//证件类型
					$("#zjhm").val("");//证件号码
				});
			});
			
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
  			
			var dicArr = jQuery.parseJSON('${dicJson}');
			//性别翻译
			function xbFunction(xbDm){
				for(var i=0;i < dicArr.length;i++){
					var obj = dicArr[i];
					if(obj.typeCode == 'XB' && obj.typeSerialNo == xbDm){
						return obj.typeDesc;
					}
				}
				return '其他';
			}
			//驾驶证状态翻译
			function jszztFunction(jszztDm){
				for(var i=0;i < dicArr.length;i++){
					var obj = dicArr[i];
					if(obj.typeCode == 'JSZZT' && obj.typeSerialNo == jszztDm){
						return obj.typeDesc;
					}
				}
				return jszztDm;
			}
		</script>
	</head>
	<body>
		<jsp:include page="/common/Head.jsp"/>
		<div id="divTitle">
			<span id="spanTitle">当前位置：车辆综合查询&gt;&gt;驾驶员信息查询</span>
		</div>
	    <div class="content">
	    	<div class="content_wrap">
	    		<fieldset style="margin: 0px 10px 0px 0px;">
					<legend  style="color:#FF3333">查询条件</legend>
			        <!-- <div class="slider_body">
		                <div class="slider_selected_left">
		                    <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: red;">*</span>证件类型：</span>
		                </div>
		                <div class="slider_selected_right dropdown dropdowns" onclick="slider(this)">
						    <input class="input_select xiala" id="zjlx_downlist" readonly="readonly" type="text" value="==请选择=="/>
						    <input type="hidden" id="zjlx_select" name="property" value=""/>
						    <div class="ul">
						    	<div id="zjlx_div" class="li" data-value="" onclick="sliders(this)">==请选择==</div>
						       	<c:forEach items="${dicList}" var="dic">
						       		<c:if test="${dic.typeCode eq '1003'}">
				                    	<div class="li" data-value="${dic.typeSerialNo}" onclick="sliders(this)">${dic.typeDesc}</div>
			                    	</c:if>
						 		</c:forEach>
						    </div>
						</div>
		        	</div> -->
		        	<div class="slider_body">
						<div class="slider_selected_left">
		                    <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: red;">*</span>身份证号：</span>
		                </div>
		                <div class="slider_selected_right" style="">
		                    <div class="img_wrap">
		                        <div class="select_wrap select_input_wrap">
		                            <input id="zjhm" type="text" class="slider_input" maxlength="18">
		                            <a id="zjhm" class="empty"></a>
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
			      		
					</div>
			   </fieldset>
			</div>
   		</div>
    	<jsp:include page="/common/Foot.jsp"/>
	</body>
	<script type="text/javascript">
		function openHphmWin(){
			var awidth = screen.availWidth/6*5;
			var aheight = screen.availHeight/5*4;
			var atop = (screen.availHeight-380)/2;
			var aleft = (screen.availWidth-440)/2;
			window.open("/dc/common/cph.jsp?winid=cphid","_blank","height=380, width=405, top="+atop+",left="+aleft+", menubar=no, scrollbars=no, resizable=no")
		}
	</script>
</html>