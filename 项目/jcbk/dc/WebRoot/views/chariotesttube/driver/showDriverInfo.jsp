<%@ page language="java" import="java.util.*,com.dyst.utils.*,com.dyst.chariotesttube.entities.*"
	contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
	
	String result = (String)request.getAttribute("result");
	List<Driver> driverList = (List<Driver>)request.getAttribute("driverList");
	if("0".equals(result)){
%>
		<SCRIPT type="text/javascript">
			window.location = '<%=basePath%>/common/errorPage/error500.jsp';
		</SCRIPT>
<%
	} else if("1".equals(result) && driverList == null){
%>
		<SCRIPT type="text/javascript">
			alert("没有找到符合条件的数据！");
		</SCRIPT>
<%
	} else if("2".equals(result)){
%>
		<SCRIPT type="text/javascript">
			alert("您的身份信息不完善，驾驶员查询需要提供个人身份证号，\n请先提供身份证号给管理员，以便完善身份信息！");
		</SCRIPT>
<%
	} else if("3".equals(result)){
%>
		<SCRIPT type="text/javascript">
			alert("身份证号不正确，请核对驾驶员的身份证号！");
		</SCRIPT>
<%
	} else {
		int len = 0;
		if(driverList != null && driverList.size() > 0){
			len = driverList.size();
		}
%>
		<SCRIPT type="text/javascript">
			$(document).ready(function(){
				for(var i=0;i < <%=len %>;i++){
					var xb = xbFunction($("#xb" + (i+1)).val());
					var jszzt = jszztFunction($("#jszzt" + (i+1)).val());
					$("#xb" + (i+1)).val(xb);//性别
					$("#jszzt" + (i+1)).val(jszzt);//驾驶证状态
				}
			});
		</SCRIPT>
<%
	}
%>

<div id="menu">
	<ul id="nav">
		<% 
			if(driverList != null && driverList.size() > 0){
				for(int i=0;i < driverList.size();i++){
					Driver driver = driverList.get(i);
		%>
					<li><a href="javascript:;" class="<%=(driver.getId()==1?"selected":"") %>">档案<%=(i+1) %></a></li>
		<%
				}
			}
		%>
	</ul>
	<div id="menu_con">
		<% 
			if(driverList != null && driverList.size() > 0){
				for(int i=0;i < driverList.size();i++){
					Driver driver = driverList.get(i);
		%>
		<div class="tag" style="<%=(driver.getId()==1?"display:block;":"display:none;") %>">
			<div class="slider_body" style="width: 320px;">
				<div class="slider_selected_left">
					<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;姓名：</span>
				</div>
				<div class="slider_selected_right">
					<div class="img_wrap">
						<div class="select_wrap select_input_wrap">
							<input type="text" class="slider_input" readonly="readonly" value="<%=driver.getXm() %>">
						</div>
					</div>
				</div>
			</div>
			<div class="slider_body" style="width: 340px;">
				<div class="slider_selected_left">
					<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;性别：</span>
				</div>
				<div class="slider_selected_right">
					<div class="img_wrap">
						<div class="select_wrap select_input_wrap">
							<input id="<%="xb"+(i+1) %>" type="text" class="slider_input" readonly="readonly" value="<%=driver.getXb() %>">
						</div>
					</div>
				</div>
			</div>
			<div class="slider_body" style="width: 340px;">
				<div class="slider_selected_left">
					<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;出生日期：</span>
				</div>
				<div class="slider_selected_right">
					<div class="img_wrap">
						<div class="select_wrap select_input_wrap">
							<input type="text" class="slider_input" readonly="readonly" value="<%=driver.getCsrq() %>">
						</div>
					</div>
				</div>
			</div>
			<div class="slider_body" style="width: 320px;">
				<div class="slider_selected_left">
					<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;驾驶证号：</span>
				</div>
				<div class="slider_selected_right">
					<div class="img_wrap">
						<div class="select_wrap select_input_wrap">
							<input type="text" class="slider_input" readonly="readonly" value="<%=driver.getJszh() %>">
						</div>
					</div>
				</div>
			</div>
			<div class="slider_body" style="width: 340px;">
				<div class="slider_selected_left">
					<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;档案编号：</span>
				</div>
				<div class="slider_selected_right">
					<div class="img_wrap">
						<div class="select_wrap select_input_wrap">
							<input type="text" class="slider_input" readonly="readonly" value="<%=driver.getDabh() %>">
						</div>
					</div>
				</div>
			</div>
			<div class="slider_body" style="width: 340px;">
				<div class="slider_selected_left">
					<span>初次领证日期：</span>
				</div>
				<div class="slider_selected_right">
					<div class="img_wrap">
						<div class="select_wrap select_input_wrap">
							<input type="text" class="slider_input" readonly="readonly" value="<%=driver.getCclzrq() %>">
						</div>
					</div>
				</div>
			</div>
			<div class="slider_body" style="width: 320px;">
				<div class="slider_selected_left">
					<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;准驾车型：</span>
				</div>
				<div class="slider_selected_right">
					<div class="img_wrap">
						<div class="select_wrap select_input_wrap">
							<input type="text" class="slider_input" readonly="readonly" value="<%=driver.getZjcx() %>">
						</div>
					</div>
				</div>
			</div>
			<div class="slider_body" style="width: 340px;">
				<div class="slider_selected_left">
					<span>有效起始日期：</span>
				</div>
				<div class="slider_selected_right">
					<div class="img_wrap">
						<div class="select_wrap select_input_wrap">
							<input type="text" class="slider_input" readonly="readonly" value="<%=driver.getJszyxqsrq() %>">
						</div>
					</div>
				</div>
			</div>
			<div class="slider_body" style="width: 340px;">
				<div class="slider_selected_left">
					<span>有效终止日期：</span>
				</div>
				<div class="slider_selected_right">
					<div class="img_wrap">
						<div class="select_wrap select_input_wrap">
							<input type="text" class="slider_input" readonly="readonly" value="<%=driver.getJszyxzzrq() %>">
						</div>
					</div>
				</div>
			</div>
			<div class="slider_body" style="width: 320px;">
				<div class="slider_selected_left">
					<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;发证机关：</span>
				</div>
				<div class="slider_selected_right">
					<div class="img_wrap">
						<div class="select_wrap select_input_wrap">
							<input type="text" class="slider_input" readonly="readonly" value="<%=driver.getFzjg() %>">
						</div>
					</div>
				</div>
			</div>
			<div class="slider_body" style="width: 340px;">
				<div class="slider_selected_left">
					<span>&nbsp;&nbsp;&nbsp;&nbsp;驾驶证状态：</span>
				</div>
				<div class="slider_selected_right">
					<div class="img_wrap">
						<div class="select_wrap select_input_wrap">
							<input id="<%="jszzt"+(i+1) %>" type="text" class="slider_input" readonly="readonly" value="<%=driver.getJszzt() %>">
						</div>
					</div>
				</div>
			</div>
			<div class="slider_body" style="position:relative;z-index:0;clear:both;">
				<div class="slider_selected_left">
					<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;登记住址：</span>
				</div>
				<div class="slider_selected_right">
					<textarea style="width: 550px;height: 20px;" readonly="readonly"><%=driver.getDjzzxz() %></textarea>
				</div>
			</div>
			<div class="slider_body" style="position:relative;z-index:0;clear:both;">
				<div class="slider_selected_left">
					<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;现住地址：</span>
				</div>
				<div class="slider_selected_right">
					<textarea style="width: 550px;height: 20px;" readonly="readonly"><%=driver.getXzzxz() %></textarea>
				</div>
			</div>
		</div>
		<%	
				}
			}
		%>
	</div>
</div>
	<script>
		var tabs = function(){
		    function tag(name, elem, className){
		    	var results = [];
		    	var elems = (elem||document).getElementsByTagName(name);
		    	if(className == ""){
		    		results = elems;
		    	} else{
			    	for(var i=0;i < elems.length;i++){
			    		if(elems[i].className.indexOf(className) != -1){
			    			results[results.length] = elems[i];
			    		}
			    	}
		    	}
		        return results;
		    }
		    
		    //获得相应ID的元素
		    function id(name){
		        return document.getElementById(name);
		    }
		    
		    function first(elem){
		        elem = elem.firstChild;
		        return elem&&elem.nodeType==1? elem:next(elem);
		    }
		    
		    function next(elem){
		        do{
		            elem = elem.nextSibling;  
		        } while(
		            elem && elem.nodeType != 1  
		        )
		        return elem;
		    }
		    
		    return {
		        set:function(elemId, tabId){
		            var elem = tag("li", id(elemId), "");
		            var tabs = tag("div", id(tabId), "tag");
		            var listNum = elem.length;
		            var tabNum = tabs.length;
		            
		            for(var i=0;i < listNum;i++){
		            	elem[i].onclick=(function(i){
		            		return function(){
		                            for(var j=0;j<tabNum;j++){
		                                if(i==j){
		                                    tabs[j].style.display="block";
		                                    elem[j].firstChild.className="selected";
		                                } else {
		                                    tabs[j].style.display="none";
		                                    elem[j].firstChild.className="";
		                                }
		                            }
		                	}
		            	})(i)
		            }
		        }
		    }
		}();
		tabs.set("nav", "menu_con");//执行
	</script>
<!--代码部分end-->