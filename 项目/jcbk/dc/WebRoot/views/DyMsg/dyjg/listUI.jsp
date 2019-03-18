<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html lang="en">
<head>
    <base href="<%=basePath%>">
    <title>订阅结果查询</title>
    <link rel="stylesheet" href="<%=basePath%>common/css/style.css" type="text/css">
    <script type="text/javascript" src="<%=basePath%>common/js/jquery.min.js"></script>
    <style type="text/css">
    	font{
    		color:red;
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
		<span id="spanTitle">当前位置：订阅结果查询</span>
	</div>
    <form id="form1" name="form" action="" method="post">
    <div class="content">
    	<div class="content_wrap">
    	
        	<div class="slider_body"> 
				<div class="slider_selected_left">
	                    <span>车牌号码：</span>
	            </div>
                <div class="slider_selected_right" >
                    <div class="img_wrap">
                        <div class="select_wrap input_wrap_select">
                            <input id="cphid" name="cphms" class="slider_input" type="text" value="${cphms }" />
                            <a class="empty" href="javascript:doCplrUI()"></a>
                        </div>
                    </div>  
               </div>
		    </div>
	        
	        <div class="slider_body">
		        <div class="slider_selected_left">
		            <span>车牌颜色：</span>
		        </div>
				<div class="slider_selected_right dropdown dropdowns" id="cp_1" onclick="slider(this)">
			     	<input class="input_select xiala" id="cpys1" type="text" readonly="readonly" value="==全部=="/>
			     	<input type="hidden" id="cpys11" name="cpys" value="${c.typeSerialNo }" /> 
		            <div class="ul"> 
		            	  <div class="li" data-value="" onclick="sliders(this)"><a rel="2">==全部==</a></div>
		            	  <c:forEach items= "${cpysList }" var="c">
		            	  <c:if test="${c.typeSerialNo eq cpys}">
		            	   	<script>
		            		 	$("#cpys1").val("${c.typeDesc }");
		            		 	$("#cpys11").val("${c.typeSerialNo }");
		            	   	</script>
		            	  </c:if> 
		                    <div class="li" data-value="${c.typeSerialNo }" onclick="sliders(this)"><a rel="2">${c.typeDesc }</a></div> 
						 </c:forEach>
		            </div>
	        	</div>
	        </div>
	        
	        <div class="slider_body">
	            <div class="slider_selected_left">
	                <span>监测点:<font>(默认全选)*</font></span>
	            </div>
	            <div class="slider_selected_right dropdown" id="dropdown_quanxuan">
	                <input id="jcdid1" class="input_select xiala" onclick="doChoseJcd();" type="text" value="==全部=="/> 
	                <input type="hidden" id="jcdid" name="jcdid" value="${jcdid }">
	                <a class="empty" href="javascript:doChoseJcd()"></a>
	            </div>
	        </div>	
	        
			<div class="slider_body" >
			  <div class="slider_selected_left">
				<span>识别起始日期：</span>
			  </div>
				<div class="slider_selected_right">
				  <div class="demolist" style="">
				    <input id="Check_startTime" class="inline laydate-icon" name="startTime" value="${startTime }" readonly="readonly" 
						onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})"/>
				  </div>
				</div>
			</div>

			<div class="slider_body" >
			  <div class="slider_selected_left">
				<span>至：</span>
			  </div>
				<div class="slider_selected_right">
				  <div class="demolist" style="">
				    <input id="Check_endTime" class="inline laydate-icon" name="endTime" value="${endTime }" readonly="readonly" 
						onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})"/>
				  </div>
				</div>
			</div>  
			
			<div class="slider_body" >
				<div class="slider_selected_left">
					<span>订阅类型：</span>
				</div>
				<div id="dropdown_quanxuan" class="slider_selected_right dropdown dropdown_all">
					<div class="input_select xiala">
						<div id="role_type_downlist" class='multi_select'>
							<input type="hidden" name="dylxs" id="dylx_selects" value=""/>
							<a class="xiala_duoxuan_a"></a>
						</div>
					</div>
				</div>
			</div> 
			
	        <div class="button_wrap clear_both">
		    	<input type="button" class="button_blue" value="查询" onclick="doSearch()">
		    	<input type="button" class="button_blue" value="重置" onclick="doReset()">
		    	<input type="button" class="button_blue" value="导出Excel" onclick="doExport()">
		    </div>
		    
	        <div class="pg_result">
	            <table>
	                <thead>
	                    <tr>
                            <td align="center">序号</td>
                            <td align="center">车牌号码</td>
                            <td align="center">车牌颜色</td>
                            <td align="center">订阅类型</td>
                            <td align="center">识别监测点</td>
                            <td align="center">车道</td>
                            <td align="center">识别时间</td>
                            <td align="center">操作</td>
	                    </tr>
	                </thead>
	                <tbody>
                        <c:forEach var="r" items="${pageResult.items }" varStatus="status">
                        	<tr>
                        		<td>${status.index+1} </td>
                        		<td>${r.hphm }</td>
                        		<td>
                        			<c:forEach items="${cpysList }" var="c">
                        				<c:if test="${c.typeSerialNo eq r.hpzl }">${c.typeDesc }</c:if>
                        			</c:forEach>                        		
                        		</td>
                        		<td>
                        		    <c:forEach items="${dylxList }" var="c">
                        				<c:if test="${c.typeSerialNo eq r.dylx }">${c.typeDesc }</c:if>
                        			</c:forEach>
                        		</td>
                        		<td>
                        			<%-- <c:forEach items="${jcdsList }" var="c">
                        				<c:if test="${c.typeSerialNo eq r.jcdid }">${c.typeDesc }</c:if>
                        			</c:forEach>  --%>   
                        			 ${jcdsMap[r.jcdid] }                  			
                        		</td>
                        		<td>${r.cdid }</td>
                        		<td>${r.tgsj }</td>
                        		<td>
                        			<a id="detail${id }" href="javascript:showSbDetail('${fn:substringBefore(r.tpid,',') }')">详情</a>
                        		</td>
                        	</tr>
                        </c:forEach>
	                </tbody>
	            </table>
			<jsp:include page="/common/pageNavigator.jsp"></jsp:include>
	        </div>
			</div>
        </div>
    </form>
    <jsp:include  page="/common/Foot.jsp"/>
</body>
<script  src="<%=basePath%>common/js/layer/layer.js"></script>
<script type="text/javascript">

	//文档加载的时候
	$(function(){
		//时间框...初始化时间
		//initTime();
		initMultiSelect();
		//显示现在选中的监测点的个数
		showJcdsNum();
	});
	
	//给多选下拉框赋值
	function initMultiSelect(){
	    var value = []; 
		var data = [];
	    var dicJson = $.parseJSON('${dylxListJson }');
		for(var i=0;i < dicJson.length;i++){
			value.push(dicJson[i].typeSerialNo);
			data.push(dicJson[i].typeDesc);
		}
	    $('.multi_select').MSDL({
			'value': value,
	      	  'data': data
	    });
	    //条件回显，勾选上次选定的项
	    var bkfwValue = '${dylxs}'.split(";"); 
	    $('.multi_select').children($('.container')).children($('.contents')).children().each(function(){
			for(var i in bkfwValue){
				if(bkfwValue[i] == $(this).children("input").val()){
					$(this).children("input").attr("checked","checked");
					$(this).children("input").trigger("change");
				};
			};
		});	
	}
	
	//显示现在选中的监测点的个数
	function showJcdsNum(){
		var jcdid = $("#jcdid").val();
		if(jcdid!=''&&jcdid!=null){
			var jcds = new Array();
			jcds = jcdid.split(",");
			if(jcds.length>=1){
				$("#jcdid1").val("您选中了"+jcds.length+"个监测点");
			}		
		}else{
			$("#jcdid1").val("==全部==");
		}

	}

	//选择监测点
	function doChoseJcd(){
		var url = "${pageContext.request.contextPath}/hmd/getJcd.do";
	    layer.open({
	           type: 2,
	           title: '监测点筛选窗口',
	           shadeClose: true,
	           shade: 0,
	           area: ['800px', '610px'],
	           content: url
	       }); 	
	};

	//时间框...初始化
	function initTime(){
		var startTime = moment().add('days',-7).format('YYYY-MM-DD HH:mm:ss');
		if($("#Check_startTime").val()==''){
			$("#Check_startTime").val(startTime);
		}
		var endTime = moment().format('YYYY-MM-DD HH:mm:ss');;
		if($("#Check_endTime").val()==''){
			$("#Check_endTime").val(endTime);
		}
	}

	var list_url = "${pageContext.request.contextPath}/dyjg/findDyJg.do";
	//搜索..按钮
  	function doSearch(){
  		//重置页号(两种写法DOM,JQuery)
  		//document.getElementById("pageNo").value=1;
  		$("#pageNo").val(1);
  		document.forms[0].action = "${pageContext.request.contextPath}/dyjg/findDyJg.do";
  		document.forms[0].submit();	  		
  	}

	//重置..按钮
	function doReset(){
		//订阅类型清空
		$('.multi_select').children($('.container')).children($('.contents')).children().each(function(){
			$(this).children("input").attr("checked",false);
			$(this).children("input").trigger("change");
		});
		//时间框
		initTime();
		//监测点
		$("#jcdid").val("");
		showJcdsNum();
		$("#cpys1").val("==全部==");
		$("#cpys11").val("");
	}

	//根据页号查询...
	function doGoPage(pageNo) {
		document.getElementById("pageNo").value = pageNo;
		document.forms[0].action = list_url;
		document.forms[0].submit();
	}
	
	//新增..按钮
	function doAddUI(){
		//跳转到新增页面
		var url_addUI = "${pageContext.request.contextPath}/dy/toAddUI.do";
		layer.open({
	           type: 2,
	           title: '<font style="border:16px">新增订阅信息</font>',
	           shadeClose: false,
  			   shade: [0],
	           area: ['1000px', '600px'],
	           content: url_addUI //iframe的url
	    }); 
	}
	
	//查询单条过车记录
	function showSbDetail(tpid){
		var location = (window.location+'').split('/');  
		var basePath = location[0]+'//'+location[2]+'/'+location[3];  
		var url = basePath + "/clcx/getSbByTpid.do?tpid="+tpid;
		layer.open({
		  type: 2,
		  title:false,
		  area:['826px','600px'],
		  closeBtn:2,
		  shadeClose:true,
		  content: url
		});
	}

	//导出订阅结果excel
	function doExport(){
		var url = "${pageContext.request.contextPath}/dyjg/exportExcel.do";
		document.forms[0].action = url ;
		document.forms[0].submit();
	}

</script>
</html>