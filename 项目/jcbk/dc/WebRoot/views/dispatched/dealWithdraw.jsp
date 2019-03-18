<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html lang="zh-CN">
<head>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
	<base href="<%=basePath%>">
	<title>撤控审批</title>
	<jsp:include page="/common/Head.jsp" />
	
	<style type="text/css">
		
	</style>
</head>

<body>
	<div id="divTitle">
		<span id="spanTitle">撤控审批</span>
	</div>
	<div class="content">
    	<div class="content_wrap" style="width:900px">
    		<div id="print">
                <h1 align="center" style="font-weight: bold">深圳市公安局车辆撤控审批表</h1>
                <c:set var="position2" value="${fn:substring(user.position,0,2)}" />
    			<h3 align="center">
	    			<c:if test="${fn:length(user.position) == 2 && user.position lt '80' && user.position gt '70'}">[派出所]</c:if>  
	          		<c:if test="${fn:length(user.position) == 3 }">[其他]</c:if>  
	          		<c:if test="${fn:length(user.position) == 2 && user.position gt '80' && user.position lt '90'}">[分局]</c:if>  
	                <c:if test="${fn:length(user.position) == 2 && user.position gt '90' }">[市局]</c:if>  
	            </h3>
	            <p align="right" id="dh">单号：
	            	<c:if test="${position2 lt '80' && position2 gt '70'}">${dispatched.pcsdh }</c:if>  
<!-- 	          		<c:if test="${fn:length(user.position) == 3}">${dispatched.sjdh }</c:if>   -->
	          		<c:if test="${position2 gt '80' && position2 lt '90'}">${dispatched.fjdh }</c:if>  
	                <c:if test="${position2 gt '90' }">${dispatched.sjdh }</c:if>  
	            </p>
	            <table id="table1" style="border: 2px solid #000;">
	                <tbody>
                        <tr>
                        	<td rowspan="6" style="border:2px solid #000;width: 70px;">布控申请信息</td>
                        	<td style="border:2px solid #000;width: 70px;">布控申请单位</td>
                        	<td style="border:2px solid #000;width: 70px;text-align: left;" colspan="3">${dispatched.bkjgmc }</td>
                        </tr>
                        <tr>
                        	<td style="border:2px solid #000;width: 70px;">布控申请人</td>
                        	<td style="border:2px solid #000;width: 70px;">${dispatched.bkr }</td>
                        	<td style="border:2px solid #000;width: 70px;">警号</td>
                        	<td style="border:2px solid #000;width: 70px;">${dispatched.bkrjh }</td>
                        </tr>
                        <tr>
                        	<td style="border:2px solid #000;width: 70px;">布控大类</td>
                        	<td style="border:2px solid #000;width: 70px;" id="bkdl"></td>
                        	<td style="border:2px solid #000;width: 70px;">布控类别</td>
                        	<td style="border:2px solid #000;width: 70px;" id="bklb"></td>
                        </tr>
<!--                         <tr> -->
<!--                         	<td style="border:2px solid #000;width: 70px;">布控范围类型</td> -->
<!--                         	<td colspan="3" id="bkfwlx" style="border:2px solid #000;width: 70px;text-align: left"></td> -->
<!--                         </tr> -->
                        <tr>
							<td style="border:2px solid #000;width: 70px;">报警类型</td>
                        	<td style="border:2px solid #000;width: 70px;" id="bjlx"></td>
                        	<td style="border:2px solid #000;width: 70px;">联系电话</td>
                        	<td style="border:2px solid #000;width: 70px;">${dispatched.bkjglxdh }</td>
                        </tr>
                         <tr>
                        	<td style="border:2px solid #000;width: 70px;">布控起始时间</td>
                        	<td style="border:2px solid #000;width: 70px;"><fmt:formatDate pattern="yyyy-MM-dd" value="${dispatched.bkqssj }" /></td>
                        	<td style="border:2px solid #000;width: 70px;">布控终止时间</td>
                        	<td style="border:2px solid #000;width: 70px;"><fmt:formatDate pattern="yyyy-MM-dd" value="${dispatched.bkjzsj }" /></td>
                        </tr>
                        <tr style="height:80px">
                        	<td style="border:2px solid #000;width: 70px;">简要案情</td>
                        	<td style="border:2px solid #000;width: 70px;text-align: left" colspan="4">
                        		${dispatched.jyaq }
                        	</td>
                        </tr>
                        <tr>
                        	<td style="border:2px solid #000;width: 70px;" rowspan="2">车辆信息</td>
                        	<td style="border:2px solid #000;width: 70px;" >号牌号码</td>
                        	<td style="border:2px solid #000;width: 70px;" >${dispatched.hphm }</td>
                        	<td style="border:2px solid #000;width: 70px;" >号牌颜色</td>
                        	<td style="border:2px solid #000;width: 70px;" id="hpys"></td>
                        </tr>
                        <tr>
                        	<td style="border:2px solid #000;width: 70px;">号牌种类</td>
                        	<td style="border:2px solid #000;width: 70px;" id="hpzl"></td>
                        	<td style="border:2px solid #000;width: 70px;">车辆品牌</td>
                        	<td style="border:2px solid #000;width: 70px;">${dispatched.clpp }</td>
                        </tr>
                        <tr>
                        	<td style="border:2px solid #000;width: 70px;">撤控人联系电话</td>
                        	<td style="border:2px solid #000;width: 70px;" colspan="3">${withdraw.cxsqrdh }</td>
                        	
                        </tr>
                        <tr style="height:80px">
                        	<td style="border:2px solid #000;width: 70px;">撤控原因描述</td>
                        	<td style="border:2px solid #000;width: 70px;text-align: left" colspan="4">
                        		${withdraw.ckyyms }
                        	</td>
                        </tr>
                        <tr id="pcsld" style="height:100px;display:none">
                        	<td style="border:2px solid #000;width: 70px;">
                        		<c:if test="${user.position eq '72' }">派出所领导意见</c:if> 
                        		<c:if test="${fn:length(user.position) == 3}">一级领导意见</c:if> 
                        	</td>
                        	<td colspan="4" style="border:2px solid #000;width: 70px;position: relative;">
                        		<span style="position:absolute ;top:0;left:0"></span>
                        		<span style="position:absolute ;bottom:0;right:0"></span>
                        	</td>
                        </tr>
                        <tr id="jy" style="height:100px;display:none">
                        	<td style="border:2px solid #000;width: 70px;">警员意见</td>
                        	<td colspan="4" style="border:2px solid #000;width: 70px;position: relative;">
                        		<span style="position:absolute ;top:0;left:0"></span>
                        		<span style="position:absolute ;bottom:0;right:0"></span>
                        	</td>
                        </tr>
                        <tr id="kld" style="height:100px;display:none">
                        	<td style="border:2px solid #000;width: 70px;">
                        		<c:if test="${fn:length(user.position) == 2}">科领导意见</c:if> 
                        		<c:if test="${fn:length(user.position) == 3}">一级领导意见</c:if>
                        	</td>
                        	<td colspan="4" style="border:2px solid #000;width: 70px;position: relative;">
                        		<span style="position:absolute ;top:0;left:0"></span>
                        		<span style="position:absolute ;bottom:0;right:0"></span>
                        	</td>
                        </tr>
                        <tr id="cld" style="height:100px;display:none">
                        	<td style="border:2px solid #000;width: 70px;">
								<c:if test="${fn:length(user.position) == 2}">处领导意见</c:if> 
                        		<c:if test="${fn:length(user.position) == 3}">二级领导意见</c:if>
							</td>
                        	<td colspan="4" style="border:2px solid #000;width: 70px;position: relative;">
                        		<span style="position:absolute ;top:0;left:0"></span>
                        		<span style="position:absolute ;bottom:0;right:0"></span>
                        	</td>
                        </tr>
                        <tr id="jld" style="height:100px;display:none">
                        	<td style="border:2px solid #000;width: 70px;">
                        		<c:if test="${fn:length(user.position) == 2}">局领导意见</c:if> 
                        		<c:if test="${fn:length(user.position) == 3}">三级领导意见</c:if>
                        	</td>
                        	<td colspan="4" style="border:2px solid #000;width: 70px;position: relative;">
                        		<span style="position:absolute ;top:0;left:0"></span>
                        		<span style="position:absolute ;bottom:0;right:0"></span>
                        	</td>
                        </tr>
	                </tbody>
	            </table>
	        </div>
	        <c:if test="${!empty dispatchedList }">
		        	<jsp:include page="/views/dispatched/equalDispatched.jsp" />
		    </c:if>
	        <form name="form" action="" method="post">
	        	<c:if test="${withdraw.zjck eq '0' }">
					<div class="slider_body" style="width:430px">
			                <div class="slider_selected_left">
			                    <span>审批操作：</span>
			                </div>
			                <div class="slider_selected_right" style="width:350px" id="spcz">
			                	<input type="radio" name="spcz" value="1" checked="checked"/>     
			                	   <c:if test="${user.position eq '81' || user.position eq '91'}">                      
		                                                                                         确认撤控
		                           </c:if>
		                           <c:if test="${user.position ne '81' && user.position ne '91'}">                      
		                                                                                         同意(提交上级)
		                           </c:if>
		                             &nbsp;&nbsp;
		                        <c:if test="${isDeal eq true }">
			                        <input type="radio" name="spcz" value="0"/>
			                         	    不同意  &nbsp;&nbsp;
			                        <c:if test="${isComplete eq true }">
			                        	<input type="radio" name="spcz" value="4"/>
			                         	    同意(结束审批)
			                        </c:if>
		                        </c:if>
		                     </div>
			        </div>
			    </c:if>
			    <c:if test="${withdraw.zjck eq '1' }">
					<div class="slider_body" style="width:380px">
			                <div class="slider_selected_left">
			                    <span>操作：</span>
			                </div>
			                <div class="slider_selected_right" style="width:300px" id="spcz">
			                	<input type="radio" name="spcz" value="1" checked="checked"/>                              
		                                                                                         确认撤控&nbsp;&nbsp;
		                     </div>
			        </div>
			    </c:if>
	        </form>
	        
	        <div class="button_wrap1" style="width:450px;height:200px">
				<button class="submit_b" onclick="agree()">提交</button>
	        	<button class="submit_b" onclick="toClose()">返回</button>
	        	<button class="submit_b" onclick="toPrint()">打印</button> 
	        </div>
        </div>
    </div>
	<jsp:include page="/common/Foot.jsp" />
</body>
	<script type="text/javascript" src="<%=basePath%>common/js/activiti/workflow.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/activiti/dispatched-todo.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/jquery.PrintArea.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/layer/layer.js"></script>
<script>
	$(function(){
		setTimeout(function(){
			$("#table1").find("tr").css('background-color','#ffffff');//消除所有行背景
		},100);
		//布控范围多选框初始化
		var dic = jQuery.parseJSON('${dicListJSON}');
		for(var i=0;i<dic.length;i++){
			//下拉框赋值
			if(dic[i].typeCode == "HPZL" && '${dispatched.hpzl }' == dic[i].typeSerialNo){
				$("#hpzl").html(dic[i].typeDesc);
			}else if(dic[i].typeCode == "0002" && '${hpys }' == dic[i].typeSerialNo){
				$("#hpys").html(dic[i].typeDesc);
			}
// 			else if(dic[i].typeCode == "CSYS" && '${dispatched.csys }' == dic[i].typeSerialNo){
// 				$("#csys").val(dic[i].typeDesc);
// 			}
			else if(dic[i].typeCode == "BKDL" && '${dispatched.bkdl }' == dic[i].typeSerialNo){
				$("#bkdl").html(dic[i].typeDesc);
			}
// 			else if(dic[i].typeCode == "BKJB" && '${dispatched.bkjb }' == dic[i].typeSerialNo){
// 				$("#bkjb").html(dic[i].typeDesc);
// 			}else if(dic[i].typeCode == "BKFWLX" && '${dispatched.bkfwlx }' == dic[i].typeSerialNo){
// 				$("#bkfwlx").html(dic[i].typeDesc);
// 			}
// 			else if(dic[i].typeCode == "BKXZ" && '${dispatched.bkxz }' == dic[i].typeSerialNo){
// 				$("#bkxz").val(dic[i].typeDesc);
// 			}else if(dic[i].typeCode == "BJYA" && '${dispatched.bjya }' == dic[i].typeSerialNo){
// 				$("#bjya").val(dic[i].typeDesc);
// 			}
		}
		var dicBklb= jQuery.parseJSON('${bklbList}');
		for(var i=0;i< dicBklb.length;i++){
			if('${dispatched.bklb }' == dicBklb[i].ID){
				$("#bklb").html(dicBklb[i].NAME);
			}
		}
		//报警类型赋值
		var bkdl = "";
		if('${dispatched.bkdl }' == "1"){
			bkdl = "BKDL1";
		}else if('${dispatched.bkdl }' == "2"){
			bkdl = "BKDL2";
		}else if('${dispatched.bkdl }' == "3"){
			bkdl = "BKDL3";
		}
		for(var i=0;i<dic.length;i++){
			if(dic[i].typeCode == bkdl && dic[i].typeSerialNo == '${dispatched.bjlx }'){
				$("#bjlx").html(dic[i].typeDesc);
			}
		}
		//给意见赋值
		var zw = '${user.position }'.substring(0,1);//职位第一位
		var zw1 = '${user.position }'.substring(1,2);//职位第二位
		var zwNum = '${user.position }'.length;//职位长度
		var zw2 = '${user.position }'.substring(0,2);//职位前2位
			//审批记录
			var commentList = jQuery.parseJSON('${commentList}');
			for(var i=0;i<commentList.length;i++){
				var bbrjs = commentList[i].czrjslx.substring(1,2);
				var bbrjs1 = commentList[i].czrjslx.substring(0,1);
				var bbrjsNum = commentList[i].czrjslx.length;
				var spr = commentList[i].czr;
				var spsj = commentList[i].czsj;
				if(bbrjs == '2' && bbrjs1 == zw && bbrjsNum == zwNum){
					$("#kld").children('td').eq(1).children('span').eq(0).html(commentList[i].ms);
					$("#kld").children('td').eq(1).children('span').eq(1).html(spr+" / "+formatDate(spsj));
				}else if(bbrjs == '3' && bbrjs1 == zw && bbrjsNum == zwNum){
					$("#cld").children('td').eq(1).children('span').eq(0).html(commentList[i].ms);
					$("#cld").children('td').eq(1).children('span').eq(1).html(spr+" / "+formatDate(spsj));
				}else if(bbrjs == '4' && bbrjsNum == zwNum){
					$("#jld").children('td').eq(1).children('span').eq(0).html(commentList[i].ms);
					$("#jld").children('td').eq(1).children('span').eq(1).html(spr+" / "+formatDate(spsj));
				}
// 				else if((bbrjs == '81' || bbrjs == '91') && bbrjs1 == zw){
// 					$("#jy").children('td').eq(1).children('span').eq(0).html(commentList[i].ms);
// 					$("#jy").children('td').eq(1).children('span').eq(1).html(spr+" / "+formatDate(spsj));
// 				}else{
// 					$("#pcsld").children('td').eq(1).children('span').eq(0).html(commentList[i].ms);
// 					$("#pcsld").children('td').eq(1).children('span').eq(1).html(spr+" / "+formatDate(spsj));
// 				}
			}				
		//显示相应审批人
		if(zw2 > '81' && zw2 < '90'){
			$("#kld").show();
			$("#cld").show();
		}else if(zw2 == '81' || zw2 == '91'){
// 			$("#jy").show();
		}else if(zw2 == '72'){
			$("#pcsld").show();
		}else{
			$("#kld").show();
			$("#cld").show();
			$("#jld").show();
		} 
		//在相应位置生成advice
		var showAdvice = "<textarea name='advice' id='advice' rows='3' style='width: 99%;'>同意撤控。</textarea>";
		var jyAdvice = "<textarea name='advice' id='advice' rows='3' style='width: 99%;'>已确认撤控信息。</textarea>";
		if(zw2 == '72'){
			$("#pcsld").children('td').eq(1).append(showAdvice);
			$("#pcsld").children('td').eq(1).children('span').eq(1).html('${user.userName }'+" / "+formatDate(new Date()));	
		}else if(zw1 == '2'){
			$("#kld").children('td').eq(1).append(showAdvice);
			$("#kld").children('td').eq(1).children('span').eq(1).html('${user.userName }'+" / "+formatDate(new Date()));
		}else if(zw1 == '3'){
			$("#cld").children('td').eq(1).append(showAdvice);
			$("#cld").children('td').eq(1).children('span').eq(1).html('${user.userName }'+" / "+formatDate(new Date()));
		}else if(zw1 == '4'){
			$("#jld").children('td').eq(1).append(showAdvice);
			$("#jld").children('td').eq(1).children('span').eq(1).html('${user.userName }'+" / "+formatDate(new Date()));
		}else{
			$("#jy").children('td').eq(1).append(jyAdvice);
			$("#jy").children('td').eq(1).children('span').eq(1).html('${user.userName }'+" / "+formatDate(new Date()));
		}
// 		else if('${user.position }' == '81' || '${user.position }' == '91'){
// 			$("#jy").children('td').eq(1).append(showAdvice);
// 			$("#jy").children('td').eq(1).children('span').eq(1).html('${user.userName }'+" / "+formatDate(new Date()));
// 		}else{
			
// 		}
		//审批操作赋值
		if('${withdraw.zjck}' == '0' && '${isDeal}' == 'true'){
			$("#spcz :radio").click(function(){
				if($('input:radio[name=spcz]:checked').val() == "1"){
					$("#advice").val("同意撤控！");
				}else if($('input:radio[name=spcz]:checked').val() == "4"){
					$("#advice").val("同意撤控，并结束审批流程。");
				}else{
					$("#advice").val("不同意撤控！");
				}
			});
		}
	});
	//取消..按钮
	function toClose() {
		document.forms[0].action = "<%=basePath%>withdraw/"+'${conPath}'+".do";
		document.forms[0].submit();
	}
	//同意..按钮
	function agree(){
		if($("#advice").val()==""){
			alert("请填写审批意见！");
			return;
		}
		if($('input:radio[name=spcz]:checked').val() == "1"){
			complete('${task.id }','${task.processInstanceId }','${passKey }','1',$("#advice").val(),'${conPath }',true,'${withdraw.ckid }');
		}else if($('input:radio[name=spcz]:checked').val() == "4"){
			complete('${task.id }','${task.processInstanceId }','${passKey }','4',$("#advice").val(),'${conPath }',true,'${withdraw.ckid }');
		}else{
			complete('${task.id }','${task.processInstanceId }','${passKey }','0',$("#advice").val(),'${conPath }',true,'${withdraw.ckid }');
		}
	}
	
	//点击打印
	function toPrint(){
		$("#print").printArea();
	}
	function printHtml(html) {
		var bodyHtml = document.body.innerHTML;
		document.body.innerHTML = html;
		window.print();
		document.body.innerHTML = bodyHtml;
	}
</script>
</html>