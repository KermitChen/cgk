<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html lang="en">
<head>
    <base href="<%=basePath%>">
    <title>订阅申请新增</title>
    <link rel="stylesheet" href="<%=basePath%>common/css/style.css" type="text/css">
    <style type="text/css">
    	.changeDiv{
    		display: block;
    		width: 100%;
    		float: left;
    	}
    	.divHuanHang{
    	    display: block;
    		width: 100%;
    		float: left;
    	}
    	font{
	    	vertical-align:middle;
	    	color: red;
    	}
		#fieldset_table{
			-moz-border-radius:8px;
			border:#D2691E 1px solid;
			min-height:300px; height:auto!important; height:300px;
		}
    </style>
</head>
<body>
    <div class="head">
        <div class="head_wrap">
            <img src="<%=basePath%>common/images/police.png" alt="">
            <h1>深圳市公安局缉查布控系统</h1>
        </div>
    </div>
    <div id="divTitle">
		<span id="spanTitle">当前位置：订阅申请新增</span>
	</div>
	<div class="content">
    <form name="form" action="" method="post">
    	<div class="content_wrap" >

    	<fieldset style="-moz-border-radius:8px;border:#D2691E 1px solid;height: auto;margin-bottom: 5px;">
			<legend style=" color:#333; font-weight:bold; font-size: large; margin-left: 30px;">订阅类型</legend>
			
			<div class="slider_body">
		        <div class="slider_selected_left">
		            <span>订阅类型：</span>
		        </div>
				<div class="slider_selected_right dropdown dropdowns" id="cp_1" onclick="slider(this)">
			     	<input class="input_select xiala"  id="dylx1" type="text" readonly="readonly" value=""/>
			     	<input type="hidden" id="dylx11" name="dylx" value="${c.typeSerialNo }" /> 
		            <div class="ul"> 
		            	  <c:forEach items= "${dylxList }" var="c">
		            	  <c:if test="${c.typeSerialNo eq Check_cllx }">
		            	   	<script>
		            		 	$("#dylx1").val("${c.typeDesc }");
		            		 	$("#dylx11").val("${c.typeSerialNo }");
		            	   	</script>
		            	  </c:if> 
		                    <div class="li xiala_div" data-value="${c.typeSerialNo }" onclick="sliders(this)"><a rel="2">${c.typeDesc }</a></div> 
						 </c:forEach>
		            </div>
	        	</div>
	        </div>
	        
	        <div class="slider_body">
			  <div class="slider_selected_left">
				<span>申请起始日期：</span>
			  </div>
				<div class="slider_selected_right">
				  <div class="demolist" style="">
				    <input id="Check_startTime" class="inline laydate-icon" name="qssj" value="${Check_startTime }" readonly="readonly" 
						onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})"/>
				  </div>
				</div>
			</div>

			<div class="slider_body" >
			  <div class="slider_selected_left">
				<span>申请结束日期：</span>
			  </div>
				<div class="slider_selected_right">
				  <div class="demolist" style="">
				    <input id="Check_endTime" class="inline laydate-icon" name="jzsj" value="${Check_endTime }" readonly="readonly" 
						onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})"/>
				  </div>
				</div>
			</div>       
	        
	        <div class="changeDiv">
	        <div id="changeDiv1">
		        <div class="slider_body"> 
					<div class="slider_selected_left">
		                    <span>车牌号码：</span>
		            </div>
	                <div class="slider_selected_right" >
	                    <div class="img_wrap">
	                        <div class="select_wrap input_wrap_select">
	                            <input id="cphid" class="slider_input" type="text" value="${Check_cpNo }" />
	                            <a class="empty" href="javascript:doCplrUI()"></a>
	                        </div>
	                    </div>  
	               </div>
		        </div>
		        
		       <div class="slider_body"> 
	            <div class="slider_selected_left">
	                <span>号牌类型：</span>
	            </div>
				<div class="slider_selected_right dropdown dropdowns" id="cp_1" onclick="slider(this)">
				     <input class="input_select xiala" id="cpys1" type="text" readonly="readonly" value="==全部=="/>
				     <input type="hidden" id="cpys11" value="${s.value }" /> 
		            <div class="ul"> 
		            	<div class="li" data-value="" onclick="sliders(this)"><a rel="2">==全部==</a></div>
		            	  <c:forEach items= "${cllxlist }" var="s" >
		            	  <c:if test="${s.typeSerialNo eq Check_jlzt }">
		            	   	<script>
		            		 	$("#cpys1").val("${s.typeDesc}");
		            		 	$("#cpys11").val("${s.typeSerialNo}");
		            	   	</script>
		            	  </c:if> 
		                    <div class="li" data-value="${s.typeSerialNo }" onclick="sliders(this)"><a rel="2">${s.typeDesc }</a></div> 
						 </c:forEach>
		            </div>
		         </div>
	         </div>
	         </div>
	        
	        <div id="changeDiv2">
	        	<div class="slider_body"> 
		            <div class="slider_selected_left">
		                <span>检索布控车牌：&nbsp;&nbsp;&nbsp;&nbsp;<font>*</font></span>
		            </div>
					<div class="slider_selected_right dropdown dropdowns" id="cp_1" onclick="slider(this)">
					     <input class="input_select xiala" onclick="doJsBkCp();" id="bkcps1" type="text" readonly="readonly" value="==检索=="/>
					     <input type="hidden" id="bkcps11" value="" />
			         </div>
	         	</div>
	        </div>
	        
	        </div>
	        
	        <div class="noChange">
	        	<div id="checks" class="slider_body">
	        		<div class="slider_selected_left">
		        		通知方式：&nbsp;&nbsp;&nbsp;&nbsp;<font>*</font>
		        		<c:forEach items="${tzfsList }" var="t">
		        			<input id="${t.typeSerialNo }" type="checkbox" name="tzfsList" value="${t.typeSerialNo }" checked="checked" style="margin-left: 10px;">&nbsp;&nbsp;${t.typeDesc }
		        		</c:forEach>
		        	</div>
		        </div>
	        	
		        <div class="slider_body">
		            <div class="slider_selected_left">
		                <span>监测点:<font>(默认全选)*</font></span>
		            </div>
		            <div class="slider_selected_right dropdown" id="dropdown_quanxuan">
		                <input id="jcdid1" class="input_select xiala" onclick="doChoseJcd();" type="text" value="==全部=="/> 
		                <input type="hidden" id="jcdid" value="${jcdid }">
		                <a class="empty" href="javascript:doChoseJcd()"></a>
		            </div>
		        </div>	
		         
		         <div class="divHuanHang">       	
		        <div class="slider_body">
		            <div class="slider_selected_left">
		                <span>选择短信接收用户：</span>
		            </div>
		            <div class="slider_selected_right dropdown" id="dropdown_quanxuan">
		                <input id="dxyhs1" class="input_select xiala" onclick="doChoseUser();" type="text" value="==请选择=="/> 
		                <input type="hidden" id="dxyhs">
		                <a class="empty" href="javascript:doChoseUser()"></a>
		            </div>
		        </div>	
		                	
		        <div class="slider_body" style="width: 350px;">
	                <div class="slider_selected_left" >
	                    <span>短信号码：</span><span style="color: red;">*</span>
	                </div>
	                <div class="slider_selected_right" style="left: 100px;">
	                    <div class="img_wrap">
	                        <div class="select_wrap select_input_wrap">
	                            <input id="dxhm" type="text" class="slider_input">
	                            <a id="dxhm" class="empty"></a>
	                        </div>
	                    </div>  
	                </div>
	        	</div>
	        	
		        </div>
		        
		        <div class="slider_body" style="margin-bottom: 10px;width: 99%;">
		            <div class="slider_selected_left">
		                <span>备注：</span>
		            </div>
		            <div class="slider_selected_right dropdown" id="dropdown_quanxuan">
		            	<textarea id="bz" style="width: 587px;height: 40px;"></textarea>
		            </div>
		        </div>
		        
		        </div>	
		</fieldset>
		
		<fieldset id="fieldset_table">
			<legend style=" color:#333; font-weight:bold; font-size: large; margin-left: 30px;">添加到表格</legend>
			<div class="slider_body" style="margin-bottom: 10px;width: 99%;text-align: center;">
	        	<input type="button" id="btn_tjzbg" class="button_blue" value="添至表格" onclick="tzbg()">
	        	<!-- <input type="button" id="btn_drcp" class="button_blue" value="导入车牌" onclick="drcp()"> -->
	        </div>
			<div id="div_table">
			<table id="table">
				<thead>
					<tr>
						<th>序号</th>
						<th>车牌号码</th>
						<th>号牌类型</th>
						<th>订阅数据类型</th>
						<th>接收短信号码</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody style="border:#000 1px solid">
				
				</tbody>
			</table>
			</div>
		</fieldset>
			<div style="text-align: center">
				<input type="button" class="button_blue" value="确定" onclick="doSubmit()">
				<input type="button" class="button_blue" value="关闭" onclick="history.go(-1)">
			</div>    		
    	</div>
    </form>
    </div>
    <jsp:include  page="/common/Foot.jsp"/>
</body>
<script  src="<%=basePath%>common/js/layer/layer.js"></script>
<script  src="<%=basePath%>common/js/time/moment.js"></script>
<script type="text/javascript">

	//文档...加载时
	$(function(){
		//订阅类型下拉框默认选中布控类型
		/* $(".xiala_div").eq(1).click(); */
		$(".xiala_div").each(function(){
			var temp = $(this).attr('data-value');
			if(temp=='bk'){
				$(this).click();
			}
		});
		//页面初始化时时间框赋值
		initTime();
	});
	
	//时间框赋值
	function initTime(){
		var startTime = moment().format('YYYY-MM-DD HH:mm:ss');
		if($("#Check_startTime").val()==''){
			$("#Check_startTime").val(startTime);
		}
		var endTime = moment().add('days',7).format('YYYY-MM-DD HH:mm:ss');
		if($("#Check_endTime").val()==''){
			$("#Check_endTime").val(endTime);
		}
	}
	
	//弹出检索布控车牌页面，回显已经布控的车辆
	function doJsBkCp(){
		var url = "${pageContext.request.contextPath}/dy/getJsBkCpUI.do";
		layer.open({
           type: 2,
           title: '检索布控车牌窗口',
           shadeClose: true,
           shade: 0,
           area: ['800px', '550px'],
           content: url
       }); 		
	}
	
	//添加各项的数据到表格..添至表格
	var tzbg_flag = 0;
	function tzbg(){
		if(validatePass()){//验证表单
			$("#table tr.bukong").remove();
			$("#table tr.guoche").remove();	//清空表单
			var trHtml = getNewRow();
			var lastRow = $('#table tr').eq(-1); 
			if(lastRow.size()==0){
				alert("指定的行数不存在!");
				return;
			}
			if(trHtml!=""){
				lastRow.after(trHtml);
				tableAddIndex();
				tzbg_flag = 1;
			}else{
				alert('添加至表格失败！');
			}
		}
	}
	
	//验证表单的函数
	function validatePass(){
		var flag = true;
		//接收短信的用户
		var $dxRadio = $("#checks input[id=002]");
		var dxhm = $("#dxhm").val();
		if($dxRadio.is(':checked')){
			if(dxhm==''||dxhm==null){
				layer.msg("接收短信用户不能为空!");
				flag=false;
			}
		}

		//布控div显示
		if($("#changeDiv2").is(":visible")){
			var bk_cphms = $("#bkcps11").val();
			if(bk_cphms==''||bk_cphms==null){
				layer.msg("布控车牌不能为空!");
				flag=false;
			}
		}
		//非布控div显示
		if($("#changeDiv1").is(":visible")){
			var cphids = $("#cphid").val();
			var cpys = $("#cpys11").val();
			if(cpys==''||cpys==null){
				layer.msg("车牌种类不能为空!");
				flag=false;
			}
			if(cphids==''||cphids==null){
				layer.msg("车牌号码不能为空！");
				flag=false;				
			}
		}
		return flag;
	}
		
	//动态生成的表格...添加序号
	function tableAddIndex(){
        var len = $('#table tr').length;
        for(var i = 1;i<len;i++){
            $('table tr:eq('+i+') td:first').text(i);
        }
            
	};
	
	//生成新增的一行数据
	function getNewRow(){
		var bk_cphms = $("#bkcps11").val();
		var gc_cphms = $("#cphid").val();
		var dylx = $("#dylx1").val();
		var dxhm = $("#dxhm").val();
		var jcds = $("#jcdid").val();
		var bz =$("#bz").val();
		if($("#changeDiv2").is(":visible")){
			//清空的所有tr
			$("#table tr.bukong").remove();
			$("#table tr.guoche").remove();
			//根据布控申请表的ids查询出所有的车牌信息
	   		$.ajax({
				async : false,//采用..同步
				cache:false,
				type: 'POST',
				data:{Check_cphms:bk_cphms},
				dataType : "json",
				url: "${pageContext.request.contextPath}/dy/getYxBkcp.do",//请求的action路径,已选布控车牌
				error: function () {//请求失败处理函数
					alert('检索布控车牌失败!');
				},
				success:function(data){ //请求成功后处理函数。
					if(data==''){
						alert('检索布控车牌失败');
					}
					//生成table中所有行
					var hpzl = '${cllxListJson}';
					var $table = $("#table");
					for(var j in data){
						$table.append('<tr class="bukong">'
												 +'<input type="hidden" value='+data[j].bkid+'>'
												 +'<input type="hidden" name="xqList['+j+'].hphm2" value='+data[j].hphm+'>'
												 +'<input type="hidden" name="xqList['+j+'].hpys2" value='+data[j].hpzl+'>'
												 +'<input type="hidden" name="xqList['+j+'].jcd2" value='+jcds+'>'
												 +'<input type="hidden" name="xqList['+j+'].bz2" value='+bz+'>'
												 +'<input type="hidden" name="xqList['+j+'].dxhm2" value='+dxhm+'>'
												 +'<td></td>'
												 +'<td>'+data[j].hphm+'</td>'
												 +'<td>'+data[j].typeDesc+'</td>'
												 +'<td>'+dylx+'</td>'
												 +'<td>'+dxhm+'</td>'
												 +'<td>'+'<a id="' +data[j].bkid+ '" href="javascript:doDel('+data[j].bkid+ ')">删除</a>' + '</td>'
										  +'</tr>');
					}					 
				}
			});
		}else if($("#changeDiv1").is(":visible")){
			//alert("111");
			//清空所有tr
			$("#table tr.bukong").remove();
			$("#table tr.guoche").remove();
			//动态添加tr
			var cpys = $("#cpys1").val();
			var cpys11 = $("#cpys11").val();
			var temp = gc_cphms.split(",");
			var $table = $("#table");
			for(var i in temp){
				$table.append('<tr class="guoche">'
												 +'<input type="hidden" name="xqList['+i+'].hphm2" value='+temp[i]+'>'
												 +'<input type="hidden" name="xqList['+i+'].cpys2" value='+cpys11+'>'
												 +'<input type="hidden" name="xqList['+i+'].jcd2" value='+jcds+'>'
												 +'<input type="hidden" name="xqList['+i+'].bz2" value='+bz+'>'
												 +'<input type="hidden" name="xqList['+i+'].dxhm2" value='+dxhm+'>'
												 +'<td></td>'
												 +'<td>'+temp[i]+'</td>'
												 +'<td>'+cpys+'</td>'
												 +'<td>'+dylx+'</td>'
												 +'<td>'+dxhm+'</td>'
												 +'<td>'+'<a id="' +temp[i]+ '" href="javascript:doDel('+temp[i]+ ')">删除</a>' + '</td>'
										  +'</tr>');
			}
		}
	}
	
	//订阅类型点击下拉选项时触发
	$(".xiala_div").click(function(){
		var temp = $(this).attr("data-value");
		if(temp == 'tp'||temp== 'gc'){
			$("#changeDiv1").show();
			$("#changeDiv2").hide();
		}else if(temp =='bk'){
			$("#changeDiv1").hide();
			$("#changeDiv2").show();
		}
	});
	
	//选择监测点
	function doChoseJcd(){
		var url = "${pageContext.request.contextPath}/hmd/getJcd.do";
	    layer.open({
	           type: 2,
	           title: '监测点筛选窗口',
	           shadeClose: true,
	           shade: 0,
	           area: ['810px', '610px'],
	           content: url
	       }); 	
	};	
	
	//查看详情弹出层
	function doDetail(id){
		var url = "${pageContext.request.contextPath}/dy/getDetail.do?id="+id;
	    layer.open({
	           type: 2,
	           title: '红名单详情',
	           shadeClose: true,
	           shade: 0,
	           area: ['800px', '600px'],
	           content: url //iframe的url
	       }); 
	}

	var list_url = "dy/findDy.do";
	//搜索..按钮
  	function doSearch(){
  		//重置页号(两种写法DOM,JQuery)
  		//document.getElementById("pageNo").value=1;
  		$("#pageNo").val(1);
  		document.forms[0].action = list_url;
  		document.forms[0].submit();	  		
  	}

	//重置..按钮
	function doReset(){
		//订阅类型清空
		$('.multi_select').children($('.container')).children($('.contents')).children().each(function(){
			$(this).children("input").attr("checked",false);
			$(this).children("input").trigger("change");
		});
		$("#xiala1").val("==全部==");
		$("#xiala11").val("");
		$("#xiala2").val("==全部==");
		$("#xiala22").val("");
		$("#Check_startTime").val("");
		$("#Check_endTime").val("");
	}

	//选择 短信接收的用户
	function doChoseUser(){
		var url = "${pageContext.request.contextPath}/dy/getDxJsYhs.do";
	    layer.open({
	           type: 2,
	           title: '接收短信用户筛选窗口',
	           shadeClose: true,
	           shade: 0,
	           area: ['400px', '500px'],
	           content: url
	       }); 	
	};	
	
	//table 删除一行
	function doDel(a){
		var temp = "#"+a;
		$(temp).parent().parent().remove();
		tableAddIndex();//table重新计算行数
	}
	
	//把条件封装成json
	var objJson = {};
	function allToJson(){
		//布控类型	
		var bklx = $("#dylx11").val();
		//开始、结束时间
		var startTime = $("#Check_startTime").val();
		var endTime = $("#Check_endTime").val();
/* 		//布控车牌s
		var bkcps = $("#bkcps11").val(); */
		//通知方式
		var tzfs =$("#checks input").val();
		//监测点
		var jcds = $("#jcdid").val();
		//短信接受的号码
		var dxhms = $("#dxhm").val();
		//备注说明
		var bzsm = $("#beizhu").val();
		objJson["bklx"]=bklx;
		objJson["startTime"]=startTime;
		objJson["endTime"]=endTime;
		objJson["tzfs"]=tzfs;
		objJson["jcds"]=jcds;
		objJson["dxhms"]=dxhms;
		objJson["bzsm"]=bzsm;
		alert(objJSON);  
		alert($.toJSON(objJSON));
	}
	
	//表单提交
	var submit_flag = 1;
	function doSubmit(){
		var url = "dy/addDyxx.do";
		//计算表格中，(class为bukong或者guoche)的tr数量，>=1就可以提交
		var tr_sum = $("#table>tbody tr").length;
		if(tr_sum == 0){
			layer.msg('请订阅车辆信息添至表格！');
			return;
		}else if(tr_sum >= 1){
			if(submit_flag == 1){
				document.forms[0].action= url;
				document.forms[0].submit();
				submit_flag = 0 ;
			}else if(submit_flag = 0){
				return;
			}
		}
	}
		
</script>
</html>