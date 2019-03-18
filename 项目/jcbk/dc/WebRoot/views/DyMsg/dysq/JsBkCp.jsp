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
    <link rel="stylesheet" href="<%=basePath%>common/css/style.css" type="text/css">
    <link rel="stylesheet" type="text/css" href="<c:url value='/common/css/baseDataMsg/jcd/jcdChoose.css'/>">
	<style type="text/css">
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
		.content{
			margin-bottom: 0px;
		}
		.data_li{
			width: 20%;
			margin-left: 0px;
		}
		.li_div{
			width: 40%;
			float: right;
			margin-right: 30%;
		}
	</style>
  </head>
  
  <body>
<!-- 	<div class="content"> -->
    	<div class="content_wrap" style="width: 770px;">
				<form action="" id="form1">
						<div class="slider_body">
					        <div class="slider_selected_left">
					            <span>布控类型：</span>
					        </div>
							<div class="slider_selected_right dropdown dropdowns" id="cp_1" onclick="slider(this)">
						     	<input class="input_select xiala"  id="bklx1" type="text" readonly="readonly" value=""/>
						     	<input type="hidden" id="bklx11" name="Check_dylx" value="${c.typeSerialNo }" /> 
					            <div class="ul"> 
					            	  <c:forEach items= "${bklbList }" var="c">
					            	  <c:if test="${c.ID eq Check_cllx }">
					            	   	<script>
					            		 	$("#bklx1").val("${c.NAME }");
					            		 	$("#bklx11").val("${c.ID }");
					            	   	</script>
					            	  </c:if> 
					                    <div class="li xiala_div" data-value="${c.ID }" onclick="sliders(this)"><a rel="2">${c.NAME }</a></div> 
									 </c:forEach>
					            </div>
	        				</div>
						</div>
						
						<div class="slider_body">
					        <div class="slider_selected_left">
					            <span>号牌种类：</span>
					        </div>
							<div class="slider_selected_right dropdown dropdowns" id="cp_1" onclick="slider(this)">
						     	<input class="input_select xiala"  id="hpzl1" type="text" readonly="readonly" value="==全部=="/>
						     	<input type="hidden" id="hpzl11" name="Check_dylx" value="${c.typeSerialNo }" /> 
					            <div class="ul"> 
					            	  <div class="li xiala_div" data-value="" onclick="sliders(this)"><a rel="2">==全部==</a></div>
					            	  <c:forEach items= "${hpzlList }" var="c">
					            	  <c:if test="${c.typeSerialNo eq Check_cllx }">
					            	   	<script>
					            		 	$("#hpzl1").val("${c.typeDesc }");
					            		 	$("#hpzl11").val("${c.typeSerialNo }");
					            	   	</script>
					            	  </c:if> 
					                    <div class="li xiala_div" data-value="${c.typeSerialNo }" onclick="sliders(this)"><a rel="2">${c.typeDesc }</a></div> 
									 </c:forEach>
					            </div>
	        				</div>
						</div>
						
						<div class="slider_body"> 
							<div class="slider_selected_left">
				                    <span>车牌号码：</span>
				            </div>
			                <div class="slider_selected_right" >
			                    <div class="img_wrap">
			                        <div class="select_wrap input_wrap_select">
			                            <input id="cphid" class="slider_input" type="text" value="${Check_cphms }" name="Check_cphms" />
			                            <a class="empty" href="javascript:doCplrUI()"></a>
			                        </div>
			                    </div>  
			               </div>
				        </div>
				       <div class="slider_body" style="margin-bottom: 10px;text-align: center;">
				        	<input type="button" id="btn_js" class="button_blue" value="检索" onclick="checkCp()">
		               </div>
				</form>
				
				<div id="divbody1" style="width:100%;float:left;display: block;text-align: center;">
					<div id="divleft" style="margin-left: 30px;">
						<!--遍历jcdDatas显示出查询的监测点的名字  -->
						<ul id="ul1" >
							
						</ul>
					</div>
					<div id="divcenter">
						<button class="btn" onclick="javascript:addOne()" id="addOne" >&gt;</button>
						<button class="btn" onclick="javascript:delOne()" >&lt;</button>
						<button class="btn" onclick="javascript:addMany()" >&gt;&gt;</button>
						<button class="btn" onclick="javascript:delMany()" >&lt;&lt;</button>
					</div>
					<div id="divright">
						<ul id="ul2">
						
						</ul>
					</div>
				</div>
               
		</div>
		
	   <div style="text-align: center;">
        	<input type="button" id="btn_ok" class="button_blue" value="确定" onclick="checkOk()">
        	<input type="button" id="btn_cancel" class="button_blue" value="取消" onclick="doClose()">
       </div>
       
<!-- 	 </div> 	 -->
  </body>
    <script  src="<%=basePath%>common/js/1.9.0-jquery.js"></script>
    <script type="text/javascript" src="<%=basePath%>common/js/common.js"></script>
    <script type="text/javascript" src="<%=basePath%>common/js/xiala.js"></script>
    <script  src="<%=basePath%>common/js/layer/layer.js"></script>
    <script  src="<%=basePath%>common/js/json/json2.js"></script>
    <script type="text/javascript">
    //选中的所有车牌id数组
    var ids=[];
    //文档加载时
    $(function(){
    	var hasIds= parent.$("#bkcps11").val();
    	if(hasIds!=''&&hasIds!=null){
    		ids = parent.$("#bkcps11").val().split(',');
    	}
    	//alert($.isArray(hasIds));
    	$.ajax({
			async : false,//同步
			cache:true,
			type: 'POST',
			data:{Check_cphms:hasIds},
			dataType : "json",
			url: "${pageContext.request.contextPath}/dy/getYxBkcp.do",//请求的action路径
			error: function () {//请求失败处理函数
				parent.layer.msg('检索布控车牌失败!');
			},
			success:function(data){ //请求成功后处理函数。
				//ul2列表清空
				$("#ul2 li").remove();
				for(var j in data){
					$("#ul2").append('<li>'+'<input type="checkbox" class="data_li" name="ids" value='+data[j].bkid+'>'+'<div class="li_div">'+data[j].hphm+'</div>'+'</li>');
				}					 
			}    		
    	});
    });
    
    //点击确定btn关闭此layer页面
    function checkOk(){
     	//获取窗口索引
    	var index = parent.layer.getFrameIndex(window.name);
    	if(ids.length==0){
    		parent.layer.msg("请选择布控车辆！");
    	}else{
    		parent.layer.msg("您选择订阅布控"+ids.length+"辆车!");
    	}
    	parent.layer.close(index);
    }
    
    //点击取消button关闭此layer页面
	function doClose(){
		var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
		parent.layer.close(index);
	}
    
    //页面关闭之前把ids中的监测点id传到父页面中
	$(window).bind("beforeunload",function(){
		parent.$("#bkcps11").val(ids);
		var len = ids.length;
		parent.$("#bkcps1").val("您选择订阅布控"+ids.length+"辆车!");
	});
    
    //根据检索的条件从布控申请表中加载出所有符合条件的布控车辆
   	function checkCp(){
   	   	var bklx = $("#bklx11").val();
   		var hpzl = $("#hpzl11").val();
   		var cphms= [];
   		cphms = $("#cphid").val();
   		$.ajax({
			async : false,//同步
			cache:true,
			type: 'POST',
			data:{Check_bklx:bklx,Check_hpzl:hpzl,Check_cphms:cphms},
			dataType : "json",
			url: "${pageContext.request.contextPath}/dy/getBkCp.do",//请求的action路径
			error: function () {//请求失败处理函数
				alert('检索布控车牌失败!');
			},
			success:function(data){ //请求成功后处理函数。
				//ul1列表清空
				$("#ul1 li").remove();
				for(var j in data){
					$("#ul1").append('<li>'+'<input type="checkbox" class="data_li" name="ids" value='+data[j].bkid+'>'+'<div class="li_div">'+data[j].hphm+'</div>'+'</li>');
				}					 
			}
		});
    	}
    	
    	//ul1 ul2 列表中li.....单击事件
		$('#ul1').on('click','li',changeColor);	
		$('#ul2').on('click','li',changeColor);	
		//改变颜色函数
		function changeColor(){
			if( $(this).find('input').get(0).checked==false || $(this).find('input').get(0).checked==undefined){
				$(this).css('background','#d0d0d0');
				$(this).find('input').get(0).checked = true;
			}else{
				$(this).css('background','#FFF');
				$(this).find('input').get(0).checked = false;
			}
		}
		//ul1列表中li.....双击事件
		$('#ul1').on('dblclick','li',function(){
			//alert($(this).find('input').attr('value'));
			$(this).remove();
			var id = $(this).find('input').attr('value');
			var index = $.inArray(id,ids);
			if(index>=0){
				return ;
			}else{
				$('#ul2').append($(this).css('background','#FFF'));
				$(this).find('input').get(0).checked = false;
				ids.push($(this).find('input').attr('value'));
			}
		});
		//ul2列表 双击事件
		$('#ul2').on('dblclick','li',function(){
			$(this).remove();
			//$(this).find('input').get(0).attr('checked')==false;
			$('#ul1').append($(this).css('background','#FFF'));
			var index = $.inArray($(this).find('input').attr('value'),ids);
			if(index>=0){
				ids.splice(index,1);
			}
			$(this).find('input').get(0).checked = false;
		});
		//单击转移button > ，从ul1中转移项li到ul2中
		function addOne(){
			//获取ul1中 选中的li标签
			var lis = $("#ul1 li");
			var flag = 1;
			for(var i=0; i<lis.length; i++){ 
				if(flag==1){
					var inputElement = $("#ul1 li input:checked").eq(0).parent();
					inputElement.dblclick();
					flag=0;
				}else{
					return ;
				}
			}
		};
		//单击转移button < ,从ul2中国转移项li到ul1中
		function delOne(){
			var lis = $("#ul2 li");
			var flag = 1;
			for(var i=0; i<lis.length; i++){ 
				if(flag==1){
					var inputElement = $("#ul2 li input:checked").eq(0).parent();
					inputElement.dblclick();
					flag=0;
				}else{
					return ;
				}
			}
		}
		//单击全部选中转移button >> 事件
		function addMany(){
			/* var lis = $("#ul1 li");
			for(var i = 0 ;i<lis.length;i++){
				var inputElement = $("#ul1 li input:checked").eq(0).parent();
				inputElement.dblclick();	
			} */
			$("#ul1 li").dblclick();
		}
		//单击全部选中转移button << 事件
		function delMany(){
		/* 	var lis = $("#ul2 li");
			for(var i = 0 ;i<lis.length;i++){
				var inputElement = $("#ul2 li input:checked").eq(0).parent();
				inputElement.dblclick();	
			}	 */
			$("#ul2 li").dblclick();
		}		
    </script>
</html>
