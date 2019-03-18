<%@page import="org.springframework.ui.Model"%>
<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>服务器运维管理</title>
</head>
<body>
	<jsp:include page="/common/Head.jsp" />
	<style type="text/css">
		.pg_result{
			height:auto !important;
			height:200px;
			min-height:200px
		}
	</style>
	<div id="divTitle">
		<span id="spanTitle">服务器运维管理</span>
	</div>
    <div class="content">
    	<div class="content_wrap">
			 <div class="slider_body">
					<div class="slider_selected_left">
						<span>服务器ip：</span>
					</div>
					<div id="dropdown_quanxuan" class="slider_selected_right dropdown dropdown_all">
						<div class="input_select xiala">
							<div id="role_type_downlist" class='multi_select'>
								<input type="hidden" name="host" id="host" value=""/>
								<a class="xiala_duoxuan_a"></a>
							</div>
						</div>
					</div>
				</div>
	        <div class="button_div">
	        	<input id="query_button" name="query_button" type="button" class="button_blue" value="查询" onclick="doSubmit();">
	        </div>
	    	<!-- 错误信息提示 -->
			<div>
				<span id="errSpan"  style="color:red;margin-left:auto;margin-right:auto" ></span>
	    	</div>
	    	
	        <div class="pg_result">
	            <table>
	                <thead>
	                    <tr>
	                        <td>服务器地址</td>
	                        <td>连接状态</td>
	                        <td>cpu型号</td>
	                        <td>cpu核数</td>
	                        <td>cpu使用率</td>
	                        <td>内存大小(K)</td>
	                        <td>内存使用率</td>
	                        <td>硬盘容量</td>
	                        <td>硬盘使用率</td>
	                        <td>检测时间</td>
	                    </tr>
	                </thead>
	                <tbody id="dataCon">
	                	
	                </tbody>
	            </table>
	        </div>
			</form>
        </div>
    </div>
    <jsp:include page="/common/Foot.jsp" />
</body>
	<script type="text/javascript" src="<%=basePath%>common/js/sb/util.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/layer/layer.js" ></script>
	<script type="text/javascript">
		function doSubmit(){
			var hosts = $.trim($("#host").val());
			layer.load();
			$.ajax({
				url:'<%=basePath%>/server/getMess.do?' + new Date().getTime(),
				method:"post",
				data:{hosts:hosts},
				success:function(data){
					console.log(data);
					layer.closeAll('loading');
					if(isNotEmpty(data) || data.length > 0){
						appendDataToTable(data);
					}else{
						layer.msg("查询失败!");
					}
				},
				error: function () {//请求失败处理函数
					layer.closeAll('loading');
					layer.msg("请求失败!");
				}
			});
		}
		//动态添加table数据
		function appendDataToTable(data){
			$("#dataCon").html("");
            var html = "";
			for(i=0;i<data.length;i++){
				html += "<tr><td>"+data[i].host+"</td><td>"+data[i].status+"</td><td>"+data[i].cpuModel+"</td><td>"+data[i].cpu_num+"</td><td>"+data[i].cpuRate+"</td>"+
				"<td>"+data[i].memTotal+"</td><td>"+data[i].memPecent+"</td><td>"+data[i].disk_size+"</td><td>"+data[i].disk_usedPecent+"</td><td>"+moment(data[i].queryTime).format("YYYY-MM-DD HH:mm:ss")+"</td></tr>";
			}
			$("#dataCon").append(html);
		}
		$(function(){
			var value = []; 
			var data = [];
		    var dicJson = ${serverList };
			for(var i=0;i < dicJson.length;i++){
				value.push(dicJson[i].typeSerialNo);
				data.push(dicJson[i].typeDesc);
			}
		    $('.multi_select').MSDL({
				'value': value,
		      	  'data': data
		    });
		});
	</script>
</html>