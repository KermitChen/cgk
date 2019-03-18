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
<title>假牌车辆查询</title>
</head>
<body>
	<jsp:include page="/common/Head.jsp" />
	<div id="divTitle">
		<span id="spanTitle">假牌车辆查询</span>
	</div>
    <div class="content">
    	<div class="content_wrap">
    	
    		<form>
    	
    		<div class="slider_body">
                <div class="slider_selected_left">
                    <span>车牌号码：</span>
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
	        <div class="slider_body" >
			  <div class="slider_selected_left">
				<span>查询时间：</span>
			  </div>
				<div class="slider_selected_right">
				  <div class="demolist" style="">
				    <input class="inline laydate-icon" id="kssj" name="kssj" value="${kssj }"
			onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})"/>
				  </div>
				</div>
			</div>
			<div class="slider_body" >
			  <div class="slider_selected_left">
				<span>至</span>
			  </div>
				<div class="slider_selected_right">
				  <div class="demolist" style="">
				    <input class="inline laydate-icon" id="jssj" name="jssj" value="${jssj }"
			onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})"/>
				  </div>
				</div>
			</div>
			
			</form>
	        
	        <div class="button_wrap clear_both">
		    	<input id="query_button" name="query_button" type="button" class="button_blue" value="查询">
		    	<input id="reset_button" name="reset_button" type="button" class="button_blue" value="重置" onclick="doReset();">
		    	<input id="excel_button" name="excel_button" type="button" class="button_blue" value="导出Excel" onclick="doExport();">
		    	<!-- 错误信息提示 -->
				<div>
					<span id="errSpan"  style="color:red;margin-left:auto;margin-right:auto" >${pageResult.errorMessage }</span>
		    	</div>
		    </div>
    	 	
	        <div id="data_div" class="pg_result">

	        </div>
        </div>
    </div>
    <jsp:include page="/common/Foot.jsp" />
</body>
	<script type="text/javascript" src="<%=basePath%>common/js/sb/util.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/layer/layer.js" ></script>
	<script type="text/javascript">
		//根据页号查询
		function doGoPage(pageNo) {
			//获取条件
			var hphm = $.trim($("#cphid").val());//号牌号码
			var kssj = $.trim($("#kssj").val());//起始时间
			var jssj = $.trim($("#jssj").val());//截止时间
			var index = layer.load();
			//提交
			$.ajax({
				url:'<%=basePath%>fals/getFalseForPage.do?' + new Date().getTime(),
				method:"post",
				data:{hphm:hphm,kssj:kssj,jssj:jssj,pageNo:pageNo},
				success:function(data){
					layer.close(index);
					$('#data_div').html(data);
				},
				error: function () {//请求失败处理函数
					layer.close(index);
					layer.msg('查询失败！');
				}
			});
		}
		//日历展示假牌过车记录
		function showCalendar(id){
			var jssj = moment().format('YYYY-MM-DD hh:mm:ss');
			var kssj = moment().subtract(6,'months').format('YYYY-MM-DD hh:mm:ss');
			console.log(kssj+"=="+jssj+"=="+id);
			var location = (window.location+'').split('/');
			var basePath = location[0]+'//'+location[2]+'/'+location[3];
			var url = basePath +"/clcx/showMonth.do?id="+id+"&kssj="+kssj+"&jssj="+jssj;
			layer.open({
				type:2,
				title:'过车详情',
				shadeClose:false,
				shade:0.2,
				area:['800px','450px'],
				content:url
			});
		}
		function showDetail(id){
			var location = (window.location+'').split('/');  
			var basePath = location[0]+'//'+location[2]+'/'+location[3];
			var url = basePath + "/fals/showDetail.do?id="+id;
			layer.open({
		           type: 2,
		           title: '详细信息',
		           shadeClose: true,
		           shade: 0.1,
		           area: ['930px', '570px'],
		           content: [url, 'no']
		       });
		}
		$(function(){
			//findAll();
			//根据条件获取符合条件的数据
			$("#query_button").click(function() {
				doGoPage(1);
			});
		});
		function findAll(){
			var index = layer.load();
			$.ajax({
				url:'<%=basePath%>fals/getFalseForPage.do?' + new Date().getTime(),
				method:"post",
				data:{pageNo:'1'},
				success:function(data){
					layer.close(index);
					$('#data_div').html(data);
				},
				error: function () {//请求失败处理函数
					layer.close(index);
					layer.msg('查询失败！');
				}
			});
		}
		layer.config({
		  extend: 'extend/layer.ext.js'
		});
		//处理记录
		function dealWith(id){
			layer.prompt({
			  formType: 2,
			  value: '',
			  title: '请输入处理原因并确定!'
			}, function(value, index, elem){
				  var loadIndex = layer.load();
				  $.ajax({
					  async:true,
					  url:'<%=basePath%>fals/update.do?' + new Date().getTime(),
					  method:"post",
					  data:{id:id,clyj:value},
					  success:function(data){
						  console.log(data);
						  layer.close(loadIndex);
						  layer.msg('处理成功!');
						  layer.close(index);
						  doGoPage($.trim($("#pageNo").val()));
					  },
					  error:function(){
						  layer.close(loadIndex);
						  layer.close(index);
						  layer.msg('处理失败!');
					  }
				  });
			});  
		}
		function doReset(){
			$("#errSpan").text("");
			$("#cphid").val("");
			$("#kssj").val("");
			$("#jssj").val("");
		}
		
		function doExport(){
			var url = "${pageContext.request.contextPath}/fals/excelExportForFals.do"
			document.forms[0].action=url;
			document.forms[0].submit();
		}
	</script>
</html>