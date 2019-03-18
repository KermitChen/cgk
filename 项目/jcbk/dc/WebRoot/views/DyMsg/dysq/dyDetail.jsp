<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html lang="zh-CN">
<head>
    <base href="<%=basePath%>">
    <link rel="stylesheet" href="<%=basePath%>common/css/zTreeStyle/zTreeStyle.css" type="text/css">
	<link rel="stylesheet" type="text/css" href="<c:url value='/common/css/baseDataMsg/jcd/jcdChoose.css'/>">
	<script  src="<%=basePath%>common/js/1.9.0-jquery.js"></script>
	<style>
		 ul,li{
			list-style:none;
			margin:0;
			padding:0;
        	cursor: pointer;
		}
		#divleft ul li{
			border-bottom: 1px solid #d0d0d0;
		}
		#divright ul li{
			border-bottom: 1px solid #d0d0d0;
		}
		#divleft,#divright{
			overflow-y:auto;
		}
		.tab_content {
		    font-size: 12px;
		    padding: 20px;
		}
		#fieldset_table{
			-moz-border-radius:8px;
			border:#D2691E 1px solid;
			min-height:250px; height:auto!important; height:250px;
		}
		#table1,#table2,#table3{
			width:99%;
			text-align: center;
		}
		#table1 td input{
			width: 99%;
		}
		#table2 tbody tr{
			margin:0;
			padding:0;
        	cursor: pointer;
		}
		#table2_tr td{
			margin:0;
			padding:0;
			width: auto;
			text-align: center;
		}
		#table2 tbody tr:nth-child(odd) {
		    background: #f4f4f4;
		}
		.button_blue{
			padding: 4px 16px;
			border: none;
			color: #fff;
			background: #08f;
			/*behavior: url(ie-css3.htc);*/
			border-radius: 6px;
			outline: none;
			cursor: pointer;
			margin: 4px 10px;
			border: 1px solid #fff;
		}
		.button_blue:hover{
			background: #fff;
			border: 1px solid #ccc;
			color: #555;
		}
		.button_white{ 
			background: #fff;
			border: 1px solid #ccc;
			color: #555;
		}
		.button_white:hover{
			color: #fff;
			background: #08f;
			border: none;
			border: 1px solid transparent;
		}
	</style>
</head>
<body>
	<div id="divMain">
		<div id="divbody">
			<div class="container">
				<ul class="tabs">
					<li class="active">
						<a href="#tab1" onclick="">订阅详情信息</a>
					</li>
					<li>
						<a href="#tab4" onclick="javascript:getSpRecord()">审批流程记录</a>
					</li>
				</ul>
				<div class="tab_container">
					<div id="tab1" class="tab_content" style="display: block; ">
						<table id="table1">
							<tbody>
								<tr>
									<td>订阅数据类型：</td>
									<td>
										<input type="text" readonly="readonly" id="_dylx"/>
									</td>
									<td>监测点：</td>
									<td>
										<input type="button" readonly="readonly" id="jcds" onclick="showJcd()" value="点击查看"/>
										<input type="hidden" id ="jcdid"/>
									</td>
								</tr>
								<tr>
									<td>有效起始时间：</td>
									<td><input type="text" readonly="readonly" id="_qssj"/></td>
									<td>有效截止时间：</td>
									<td><input type="text" readonly="readonly" id="_jzsj"/></td>
								</tr>
								<tr>
									<td>订阅人姓名：</td>
									<td><input type="text" readonly="readonly" id="_jyxm"/></td>
									<td>订阅申请时间：</td>
									<td><input type="text" readonly="readonly" id="_lrsj"/></td>
								</tr>
								<tr>
									<td>记录状态：</td>
									<td><input type="text" readonly="readonly" id="_jlzt"/></td>
									<td>通知方式：</td>
									<td>
										<input style="width: 15%;" type="checkbox" id="_ym"/>WEB页面
										<input style="width: 15%;" type="checkbox" id="_dx"/>短信通知
									</td>
								</tr>
								<tr>
									<td>备注：</td>
									<td colspan="3"><textarea id="_bz" readonly="readonly" style="width: 99%;"></textarea></td>
								</tr>
							</tbody>
						</table>
						
						<fieldset id="fieldset_table">
							<legend style=" color:#333; font-weight:bold; font-size: large; margin-left: 30px;">添加到表格</legend>
							<table border="1px" id="table2" cellpadding="0" cellspacing="0">
								<thead>
									<tr id="table2_tr">
										<td>序号</td>
										<td>车牌号码</td>
										<td>号牌种类</td>
										<td>订阅类型</td>
										<td>接收短信号码</td>
									</tr>
								</thead>
								<tbody id="table2_tbody">
									
								</tbody>
							</table>
						</fieldset>
					</div>
					<div id="tab4" class="tab_content" style="display: none; ">
						<c:if test="${!empty commentList }">
				        	<table border="1" cellpadding="0" cellspacing="0" width="100%">
								<thead>
									<tr>
										<td width="30%" align="center">时间</td>
										<td width="30%" align="center">审批人</td>
										<td width="40%" align="center">批注信息</td>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="c" items="${commentList }" varStatus="status">
										<tr>
											<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${c.time}" /></td>
											<td>${c.userId}</td>
											<td>${c.message }</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
				        </c:if>
					</div>
				</div>
				<div style="text-align: center;">
					<input type="button" value="关闭" class="button_blue" onclick="doClose()"/>
				</div>
			</div>
		</div>

	</div>
</body>
<script  src="<%=basePath%>common/js/1.9.0-jquery.js"></script>
<script  src="<%=basePath%>common/js/layer/layer.js"></script>
<script  src="<%=basePath%>common/js/time/moment.js"></script>
<script type="text/javascript">

	//文档加载时
	$(function (){
		//选项卡切换
		tabChange();
		var xqListJson = $.parseJSON('${xqListJson}');
		table2AddTr(xqListJson);
		//给所有table2下面的tr绑定事件
		var dylxList = $.parseJSON('${dylxListJson}');
		var jlztList = $.parseJSON('${jlztListJson}');
		//input 框中绑定值
			$("#_dylx").val(transforJson(dylxList,xqListJson[0].dylx));
			$("#_qssj").val(changeTime(xqListJson[0].qssj));
			$("#_jzsj").val(changeTime(xqListJson[0].jzsj));
			$("#_jyxm").val(xqListJson[0].jyxm);
			$("#_lrsj").val(changeTime(xqListJson[0].lrsj));
			$("#_jlzt").val(transforJson(jlztList,xqListJson[0].jlzt));
			$("#_bz").val(xqListJson[0].dyxxXqs[0].bz2);
			$("#jcdid").val(xqListJson[0].dyxxXqs[0].jcd2);
			var tzfs = [];
			if(xqListJson[0].tzfs!=''&&xqListJson[0].tzfs!=null){
				tzfs = xqListJson[0].tzfs;
			}
			var ym = $("#_ym");
			if(tzfs.indexOf(1)>=0){
				ym.attr('checked',true);
			}
			var dx = $("#_dx");
			if(tzfs.indexOf(2)>0){
				dx.attr('checked',true);
			}
		$("#table2 tbody").on('click','tr',function(){
			var index = $(this).index();
			$(this).siblings().css('background','');
			$(this).siblings().removeClass("checked");
			$(this).css('background','gray');
			$(this).addClass("checked");
			layer.load(0,{time: 500});		
		});
	});
	
	//动态生成table2下面的tr
	function table2AddTr(xqListJson){
		var xqs = xqListJson[0].dyxxXqs;
		var $table2_tbody = $("#table2_tbody");
		var cpysList = $.parseJSON('${cpysList}');
		var dylxList = $.parseJSON('${dylxListJson}');
		//console.log(cpArray);
		var cpys;
		var dylx;
		dylx = transforJson(dylxList,xqListJson[0].dylx);
		for(var i in xqs){
			var temp = xqs[i].hpys2;
			cpys = transforJson(cpysList,temp);
			var index = parseInt(i)+1;
			$table2_tbody.append('<tr style="text-align: center;" id="'+xqs[i].dyxqid+'">'
										+'<td>'+index+'</td>'
										+'<td>'+xqs[i].hphm2+'</td>'
										+'<td>'+cpys+'</td>'
										+'<td>'+dylx+'</td>'
										+'<td>'+xqs[i].dxhm2+'</td>'
								+'</tr>');
		}
	}
	
	//把各种json进行...按代码->转出相应的描述的function
	function transforJson(json,temp){
		var desc="";
		for(var j in json){
			var serialNo = json[j].typeSerialNo;
			if(temp==serialNo){
				desc = json[j].typeDesc;
			}
		}
		return desc;
	}
	
	//选项卡切换
	function tabChange(){
		//load Tab
		//Default Action  
		$(".tab_content").hide(); //Hide all content  
		$("ul.tabs li:first").addClass("active").show(); //Activate first tab  
		$(".tab_content:first").show(); //Show first tab content  

		//On Click Event  
		$("ul.tabs li").click(function() {
			$("ul.tabs li").removeClass("active"); //Remove any "active" class  
			$(this).addClass("active"); //Add "active" class to selected tab  
			$(".tab_content").hide(); //Hide all tab content  
			var activeTab = $(this).find("a").attr("href"); //Find the rel attribute value to identify the active tab + content  
			$(activeTab).fadeIn(); //Fade in the active content  
			return false;
			});
	}
	
	//利用moment.js ...转换时间
	function changeTime(time){
		return moment(time).format('YYYY-MM-DD HH:mm:ss');
	}
	
	//显示已选的监测点
	function showJcd(){
		//向父页面中写入jcds
		var jcds = $("#jcdid").val();
		parent.$("#jcdid").val(jcds);
		var url = "${pageContext.request.contextPath}/dy/showJcds.do";
		parent.layer.open({
           type: 2,
           title: '查看已选择的监测点窗口',
           shadeClose: false,
           shade: 0,
           area: ['420px', '300px'],
           content: url
       }); 
	}
	
	//关闭弹出层
	var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
	function doClose(){
		parent.layer.close(index);
	}	
	
	//回显审批流程记录
	function getSpRecord(){
	
	}
	
</script>
</html>