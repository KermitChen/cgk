<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>过车记录</title>
	<script type="text/javascript" src="<%=basePath%>common/js/jquery.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/common.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/sb/util.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/layer/layer.js" ></script>
	<script type="text/javascript" src="<%=basePath%>common/js/time/moment.js"></script>
	<link rel="stylesheet" href="<%=basePath%>common/css/style.css" type="text/css">
	<style type="text/css">
		html,body{
			margin: 0;
			padding: 0;
			height:100%; 
		}
		/*轨迹*/
		.date_div{
			width: 100%;
			display: block;
			position: relative;
			float: left;
			margin: 10px 0;
		}
		.date_div div{
			display: block;
		    float: left;
		    height: 30px;
		    margin: 15px 14px 0;
		    position: relative;
		    width: 130px;
		    border: 1px solid #b1d2ec;
		}
		.date_div input{
			border: medium none;
			float : left;
			height: 30px;
			width: 130px;
			line-height: 24px;
			text-align: center;
			cursor: pointer;
			color: #1f547e;
		}
		.date_div .day_simple input:hover{
			background-color: #deecf8 !important;
		}
		.date_div .day_void .day_input{
			cursor: text !important;
			color: #bdd8ee !important;
		}
	</style>
	<script type="text/javascript">
		$(function(){
			//控制gis日历显示
			$(".path_xiala").click(function(){
				var value = $(this).attr("data-value");
				$("#date_box .date_div").hide();
				$("#date_box ."+value).show();
			});
			var pathMonth = $("#date_box .ul div:first").attr("data-value");
			if(pathMonth == null || pathMonth == ''){
				layer.msg('无记录!');
			}
			$("#pathMonth_xiala").val(pathMonth);
			$("#date_box ."+pathMonth).show();
			
			//点击日期显示轨迹
			$("#date_box .date_div .day_simple").click(function(){
				var input = $(this).find("input");
				var kssj = input.attr("y")+"-"+input.attr("m")+"-"+input.val() + " 00:00:01";
				var jssj = moment(kssj,"YYYY-MM-DD hh:mm:ss").add(1,'days').format("YYYY-MM-DD")+" 00:00:01";
				var id = $.trim($("#jlid").val());
				console.log(kssj+"="+jssj+"="+id);
				if(isNotEmpty(id)){
					showPath(id,kssj,jssj);
				}
			});
		});
		function showPath(id,kssj,jssj){
			var location = (window.location+'').split('/');  
			var basePath = location[0]+'//'+location[2]+'/'+location[3];
			var url = basePath + "/clcx/showWheel.do?id="+id+"&kssj="+kssj+"&jssj="+jssj;
			parent.layer.open({
		           type: 2,
		           title: '车辆轨迹',
		           shadeClose: true,
		           shade: 0.1,
		           area: ['800px', '580px'],
		           content: url
		       });
		}
	</script>
</head>
<body>
	<form>
		<input type="hidden" id="jlid" value="${fal.id }">
	</form>
	<div class="show_data_div">
		<div id="date_box" class="date_box">
			<div class="date_top">
				<div class="slider_body">
		            <div class="slider_selected_left">
		                <span>月份选择：</span>
		            </div>
		            <div class="slider_selected_right dropdown dropdowns" id="cp_1" onclick="slider(this)">
			           <input class="input_select xiala pathMonth" type="text" id="pathMonth_xiala" readonly="readonly"/>
			           <input type="hidden" id="pathMonth" name="pathMonth"/>
		                <div class="ul">
		                	<c:forEach items="${dayList }" var="s" varStatus="c">
			                	<div class="li path_xiala" data-value="${s.key }" onclick="sliders(this)">${s.key }月</div> 
			                </c:forEach>
		                </div> 
		            </div>
		        </div>
			</div>
			<c:forEach items="${dayList }" var="s">
				<div style="display:none;" class="date_div ${s.key }">
					<c:forEach items="${s.list }" var="c">
						<div class="${c.className }" >
							<input class="day_input" y="${c.year }" m="${c.month }" value="${c.day }">
						</div>
					</c:forEach>
				</div>
             </c:forEach>
		</div>
     </div>
</body>
</html>