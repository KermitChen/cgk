<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html lang="zh-CN">
<head>
    <base href="<%=basePath%>">
    <link rel="stylesheet" href="<%=basePath%>common/css/zTreeStyle/zTreeStyle.css" type="text/css">
	<link rel="stylesheet" type="text/css" href="<c:url value='/common/css/baseDataMsg/jcd/jcdChoose.css'/>">
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
			min-height:200px; height:auto!important; height:200px;
			margin: auto;
		}
		#table1 td,#table3 td{
			background: #fff;
		}
		#table1 td input{
			width: 80%;
			cursor: pointer;
		}
		table{
			width: 99%;
			margin: auto;
			table-layout:fixed;
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
		body{
			font-size: 12px;
		}
	</style>

</head>
<body>
	<div id="divMain">
		<div id="divbody">
						<form id="form0">
						<table id="table1">
							<tbody>
								<tr>
									<td width="20%">订阅数据类型：</td>
									<td width="30%">
										<input readonly="readonly" type="text" id="_dylx"/>
									</td>
									<td width="20%">监测点：</td>
									<td width="30%">
										<input type="button" id="jcds" onclick="showJcd()" value="查看监测点"/>
										<input type="hidden" id ="jcdid" name="jcds"/>
									</td>
								</tr>
								<tr>
									<td>有效起始时间：</td>
									<td>
										<div class="slider_body" >
											<div class="slider_selected_right">
											  <div class="demolist">
											    <input id="_qssj" class="inline laydate-icon" name="_qssj" value="${Check_startTime }" readonly="readonly" 
													onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})"/>
											  </div>
											</div>
										</div>
									</td>
									<td>有效截止时间：</td>
									<td>
										<div class="slider_body" >
											<div class="slider_selected_right">
											  <div class="demolist">
											    <input id="_jzsj" class="inline laydate-icon" name="_jzsj" value="${Check_startTime }" readonly="readonly" 
													onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})"/>
											  </div>
											</div>
										</div>									
									</td>
								</tr>
								<tr>
									<td>订阅人姓名：</td>
									<td><input readonly="readonly" type="text" id="_jyxm"/></td>
									<td>订阅申请时间：</td>
									<td>
										<input type="text" id="_lrsj" readonly="readonly" />
									</td>
								</tr>
								<tr>
									<td>记录状态：</td>
									<td><input readonly="readonly" type="text" id="_jlzt"/></td>
									<td>通知方式：</td>
									<td>
										<input name="tzfs" style="width: 15%;" type="checkbox" id="_ym" value="001" />WEB页面
										<input name="tzfs" style="width: 15%;" type="checkbox" id="_dx" value="002"/>短信通知
									</td>
								</tr>
								<tr>
									<td>备注：</td>
									<td colspan="2"><textarea id="_bz" name="bz" style="width: 99%;"></textarea></td>
									<td style="text-align: center;">
									</td>
								</tr>
							</tbody>
						</table>
						</form>
						
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
				<div style="text-align: center;">
					<form id="form1">
					<table id="table3">
						<tr>
							<td>审批意见：</td>
							<td>
								<input type="radio" name="spyj" value="1" id="agree" checked="checked" onclick="textareaFuZhi()" />同意
								<input type="radio" name="spyj" value="0" id="disagree" onclick="textareaFuZhi()"/>不同意
							</td>
							<td></td>
							<td></td>
						</tr>
						<tr>
							<td>审批结果描述：</td>
							<td colspan="3">
								<textarea name="spjgDesc" id="textarea" rows="1" style="width: 99%;"></textarea>
							</td>
						</tr>
					</table>
					<input type="hidden" name="dyxxId" id="dyxxId" value=""/>
					<input type="button" value="确定" class="button_blue" onclick="doSubmit()"/>
					<input type="button" value="关闭" class="button_blue" onclick="doClose()"/>
					</form>
				</div>
			</div>
		</div>

</body>
	<script type="text/javascript" src="<%=basePath%>common/js/activiti/jquery-1.8.3.js"></script>
<%-- <script  src="<%=basePath%>common/js/1.9.0-jquery.js"></script> --%>
<script  src="<%=basePath%>common/js/layer/layer.js"></script>
<script  src="<%=basePath%>common/js/time/moment.js"></script>
<script type="text/javascript" src="<%=basePath%>common/js/laydate-master/laydate.js"></script>
<script type="text/javascript" src="<%=basePath%>common/js/laydate-master/laydate.dev.js"></script>
<script type="text/javascript" src="<%=basePath%>common/js/common.js"></script>
<script type="text/javascript" src="<%=basePath%>common/js/activiti/plugins/jui/jquery-ui-1.9.2.min.js"></script>
<script type="text/javascript" src="<%=basePath%>common/js/activiti/plugins/qtip/jquery.qtip.pack.js"></script>
<script type="text/javascript" src="<%=basePath%>common/js/activiti/plugins/html/jquery.outerhtml.js"></script>
<script type="text/javascript" src="<%=basePath%>common/js/activiti/plugins/blockui/jquery.blockUI.js"></script>
<script type="text/javascript" src="<%=basePath%>common/js/activiti/workflow.js"></script>
	<script type="text/javascript">
	//文档加载时
	$(function (){
		//textarea赋值
		textareaFuZhi();
		var xqListJson = $.parseJSON('${xqListJson}');
		$("#dyxxId").val(xqListJson[0].id);
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
	
	
	//点击提交按钮，保存审批表
	function doSubmit(){
		if($("#agree").is(':checked')){
			complete('${task.id }','${task.processInstanceId }','${passKey }',true,$("#textarea").text());
		}else  if($("#disagree").is(':checked')){
			complete('${task.id }','${task.processInstanceId }','${passKey }',false,$("#textarea").text());
		}
	}
	
	//审批结果描述 textarea自动赋值
	function textareaFuZhi(){
		if($("#agree").is(':checked')){
			$("#textarea").text("同意订阅！");
		}else  if($("#disagree").is(':checked')){
			$("#textarea").text("不同意订阅！");
		}
	}
	
	//动态生成table2下面的tr
	function table2AddTr(xqListJson){
		var xqs = xqListJson[0].dyxxXqs;
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
			$("#table2_tbody").append('<tr style="text-align: center;" id="'+xqs[i].dyxqid+'">'
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
           area: ['420px'],
           content: url
       }); 
	}
	
	//关闭弹出层
	function doClose(){
		var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
		parent.layer.close(index);
	}	
	/**
	 * 完成任务
	 * @param {Object} taskId
	 */
	function complete(taskId,taskProInstId, key,value,advice) {
		$.blockUI({
	        message: '<h2><img src="' + 'common/images/ajax/loading.gif" align="absmiddle"/>正在提交请求……</h2>'
	    });
		// 发送任务完成请求
	    $.post('/dc/workflow/complete.do?taskId=' + taskId+'&key='+key+'&value='+value+'&taskProInstId='+taskProInstId+'&advice='+encodeURI(encodeURI(advice))  
	    , function(resp) {
			$.unblockUI();
	        if (resp == 'success') {
	            alert('审批完成');
	            parent.location.reload();
	            doClose();
	            //新增一条订阅审批详情
	        } else {
	            alert('操作失败!请联系管理员');
	            parent.location.reload();
	            doClose();
	        }
	    });
	}
</script>
</html>