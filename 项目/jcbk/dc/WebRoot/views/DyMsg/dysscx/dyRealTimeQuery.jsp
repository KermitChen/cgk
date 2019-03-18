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
    <title>订阅实时跟踪</title>
    <script type="text/javascript" src="<%=basePath%>dwr/engine.js"></script>
	<script type="text/javascript" src="<%=basePath%>dwr/util.js"></script>
	<script type="text/javascript" src="<%=basePath%>dwr/interface/pushMessageCompont.js"></script>
    <link rel="stylesheet" href="<%=basePath%>common/css/style.css" type="text/css">
    <script type="text/javascript" src="<%=basePath%>common/js/jquery.min.js"></script>
    <style type="text/css">
    	#startup{
	    	height:30px;
	    	width:30px;
	    	margin: auto;
	    	vertical-align: middle;
	    	cursor: pointer;
    	}
    	#doCancel{
    		margin-left: 0px;
    	}
    	#page_result{
    		min-height: 400px;height: auto!important;height: 400px;
    	}
    	/*新加的button样式*/
		.newButton{
		  line-height:30px;
		  height:30px;
		  width:70px;
		  color:#ffffff;
		  background-color:#0088ff;
		  font-size:16px;
		  font-weight:normal;
		  font-family:Arial;
		  border:0px solid #dcdcdc;
		  -webkit-border-top-left-radius:3px;
		  -moz-border-radius-topleft:3px;
		  border-top-left-radius:3px;
		  -webkit-border-top-right-radius:3px;
		  -moz-border-radius-topright:3px;
		  border-top-right-radius:3px;
		  -webkit-border-bottom-left-radius:3px;
		  -moz-border-radius-bottomleft:3px;
		  border-bottom-left-radius:3px;
		  -webkit-border-bottom-right-radius:3px;
		  -moz-border-radius-bottomright:3px;
		  border-bottom-right-radius:3px;
		  -moz-box-shadow: inset 0px 0px 0px 0px #ffffff;
		  -webkit-box-shadow: inset 0px 0px 0px 0px #ffffff;
		  box-shadow: inset 0px 0px 0px 0px #ffffff;
		  text-align:center;
		  display:inline-block;
		  text-decoration:none;
		  margin-right: 15px;
		}
		.newButton:hover{
		  background-color:#265cb4;
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
		<span id="spanTitle">当前位置：订阅实时跟踪</span>
	</div>
    <form name="form" id="form0" action="" method="post">
    <div class="content">
    	<div class="content_wrap">
    	
        	<div class="slider_body" >
				<div class="slider_selected_left">
					<span>订阅类型：</span>
				</div>
				<div id="dropdown_quanxuan" class="slider_selected_right dropdown dropdown_all">
					<div class="input_select xiala">
						<div id="role_type_downlist" class='multi_select'>
							<input type="hidden" name="Check_dylx" id="dylx_selects" value=""/>
							<a class="xiala_duoxuan_a"></a>
						</div>
					</div>
				</div>
			</div>
			
			<div class="slider_body">
		        <div class="slider_selected_left">
		            <span>最大显示记录数：</span>
		        </div>
				<div class="slider_selected_right dropdown dropdowns" id="cp_1" onclick="slider(this)">
			     	<input class="input_select xiala" id="maxRecordsNum" type="text" readonly="readonly" value="50"/>
			     	<input type="hidden" id="xiala22" name="Check_jlzt" value="${c.typeSerialNo }" /> 
		            <div class="ul"> 
		            	  <div class="li" data-value="10" onclick="sliders(this)"><a rel="2">10</a></div>
		            	  <div class="li" data-value="20" onclick="sliders(this)"><a rel="2">20</a></div>
		            	  <div class="li" data-value="50" onclick="sliders(this)"><a rel="2">50</a></div>
		            	  <div class="li" data-value="100" onclick="sliders(this)"><a rel="2">100</a></div>
		            </div>
	        	</div>
	        </div>
	         
	        <div class="button_wrap clear_both">
	        	<img alt="启动" onclick="doQxgz()" id="startup" src="${pageContext.request.contextPath }/images/green.gif">
		    	<input id="doCancel" type="button" class="button_blue" value="取消跟踪" onclick="doQxgz()">
		    	<input type="button" class="button_blue" value="清空数据" onclick="doClear()">
		    </div>
		    
	        <div class="pg_result" id="page_result">
	            <table id="table_result">
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
	                
	                </tbody>
	            </table>
	        </div>
			</div>
        </div>
    </form>
    <input type="hidden" id="jcdid"/>
    <jsp:include  page="/common/Foot.jsp"/>
</body>
<script  src="<%=basePath%>common/js/layer/layer.js"></script>
<script type="text/javascript">
	
	//车牌颜色
	var cpysJson;
	//订阅类型
	var dylxJson;
	//监测点
	var jcdsJson;
	
	//文档加载的时候
	$(function(){
		//获取车牌颜色json
		cpysJson = $.parseJSON('${cpysListJson}');
		dylxJson = $.parseJSON('${dylxListJson}');
		jcdsJson = $.parseJSON('${jcdsListJson}');
		//多选下拉框赋值
		initMultiSelect();
		//页面实时刷新
		onPageLoad();
		dwr.engine.setActiveReverseAjax(true);
		dwr.engine.setNotifyServerOnPageUnload(true);
		//refresh();
	});
	
	function onPageLoad() {
		pushMessageCompont.onPageLoad("${userId }");
	}
	
	function showMessage(sendMessages, clickEvent) {
		var zt = $("#doCancel").val();
		//现在是跟踪状态
		if(zt=='取消跟踪'){
			refresh(sendMessages);		
		}else if(zt=='继续跟踪'){//停止跟踪
			return;
		}
	}
	
	//获取最新订阅信息
	function refresh(sendMessages){
		var temp = $.parseJSON(sendMessages);
		var dylx = temp.dylx;
		var dylxs =[];
		var checks = $("#role_type_downlist .contents").find("input:checked").each(function(){
			dylxs.push($(this).val());
		});
		var $tbody = $("#table_result tbody");
		var len = $("#table_result tbody tr").length;
		var $tr = $("#table_result tbody tr").eq(0);
		var firstTpid = getFirstTpid(temp.tpid);
		if(dylxs.length==0){
			if(len==0){
				$tbody.append('<tr style="text-align: center;" id="'+temp+'">'
						+'<td></td>'
						+'<td>'+temp.hphm+'</td>'
						+'<td>'+transforJson(cpysJson,temp.hpzl)+'</td>'
						+'<td>'+transforJson(dylxJson,dylx)+'</td>'
						+'<td>'+transforJson(jcdsJson,temp.jcdid)+'</td>'
						+'<td>'+temp.cdid+'</td>'
						+'<td>'+changeTime(temp.tgsj)+'</td>'
						+"<td><a href=\"javascript:showSbDetail('"+firstTpid+"')\">详情</a></td>"
			 	);
		 	}else if(len>=1){
		 		$tr.before('<tr style="text-align: center;" id="'+temp+'">'
						+'<td></td>'
						+'<td>'+temp.hphm+'</td>'
						+'<td>'+transforJson(cpysJson,temp.hpzl)+'</td>'
						+'<td>'+transforJson(dylxJson,dylx)+'</td>'
						+'<td>'+transforJson(jcdsJson,temp.jcdid)+'</td>'
						+'<td>'+temp.cdid+'</td>'
						+'<td>'+changeTime(temp.tgsj)+'</td>'
						+"<td><a href=\"javascript:showSbDetail('"+firstTpid+"')\">详情</a></td>"
			 	);
		 	}
		 	layer.msg("有新的订阅信息!");				
		}else if(dylxs.length>0&&$.inArray(dylx,dylxs)>=0){
			$tr.before('<tr style="text-align: center;" id="'+temp+'">'
					+'<td></td>'
					+'<td>'+temp.hphm+'</td>'
					+'<td>'+transforJson(cpysJson,temp.hpzl)+'</td>'
					+'<td>'+transforJson(dylxJson,dylx)+'</td>'
					+'<td>'+transforJson(jcdsJson,temp.jcdid)+'</td>'
					+'<td>'+temp.cdid+'</td>'
					+'<td>'+changeTime(temp.tgsj)+'</td>'
					+"<td><a href=\"javascript:showSbDetail('"+firstTpid+"')\">详情</a></td>"
		 	);	
		 	layer.msg("有新的订阅信息!");	
		}
		//控制table最大显示行数
		maxRows();
		//动态生成的表格添加序号
		tableAddIndex();
	}
	//动态生成的表格...添加序号
	function tableAddIndex(){
        var len = $('#table_result tr').length;
        for(var i = 1;i<len;i++){
            $('table tr:eq('+i+') td:first').text(i);
        }
            
	};
	//控制表格  最大显示的行数
	function maxRows(){
		var num = parseInt($("#maxRecordsNum").val());
		var tableRow = $("#table_result tbody tr");
		var tableRowNum = parseInt($("#table_result tbody tr").length);
		if(tableRowNum<=num){
		
		}else if(tableRowNum>num){
			tableRow.eq(tableRowNum-1).remove();
		}	
	}
	//取出图片id字符串中的第一个id
	function getFirstTpid(ids){
		var tpids = ids.split(",");
		if(tpids[0]!=null){
			return tpids[0];
		}else{
			return "";
		}
	}
	
	//清空数据
	function doClear(){
		$("#table_result tbody tr").remove();
	}
	//取消跟踪
	function doQxgz(){
 		var temp = $("#doCancel");
		temp.val('继续跟踪');
		temp.attr('onclick','doOK()');
		var temp1 = $("#startup");
		temp1.attr({'src':'${pageContext.request.contextPath}/images/red.gif','onclick':'doOK()'});
		layer.msg('您已取消了跟踪！');
	}
	//继续跟踪
	function doOK() {
		var temp = $("#doCancel");
		temp.val('取消跟踪');
		temp.attr('onclick','doQxgz()');
		$("#startup").attr({'src':'${pageContext.request.contextPath}/images/green.gif','onclick':'doQxgz()'});
		layer.msg('您已恢复了跟踪！');
	}
	
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
	      	'data': data,
	    });
	    //条件回显，勾选上次选定的项
	    var bkfwValue = '${Check_dylx}'.split(";"); 
	    $('.multi_select').children($('.container')).children($('.contents')).children().each(function(){
			for(var i in bkfwValue){
				if(bkfwValue[i] == $(this).children("input").val()){
					$(this).children("input").attr("checked","checked");
					$(this).children("input").trigger("change");
				};
			};
		});	
	}
	
	//把各种json进行...按代码->转出相应的描述    的function
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
	
</script>
</html>