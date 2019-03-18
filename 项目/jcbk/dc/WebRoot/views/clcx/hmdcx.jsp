<%@page import="org.springframework.ui.Model"%>
<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>红名单过车查询</title>
</head>
<body>
	<jsp:include page="/common/Head.jsp" />
	<link rel="stylesheet" href="<%=basePath%>common/css/sb/clcx.css" type="text/css">
	<div id="divTitle">
		<span id="spanTitle">红名单过车查询</span>
	</div>
    <div class="content" style="height: 700px">
    	<div class="content_wrap">
    	 	<form id="form1" name="form1" action="<%=basePath%>clcx/hmdClcx.do" method="post">
    	 		<input type="hidden" name="isHiddenDiv" id="isHiddenDiv" value="${isHiddenDiv }"/>
    	 		<div class="slider_body">
	                <div class="slider_selected_left">
	                    <span id="hphm_span" class="layer_span" style="color:#900b09">车牌号码：</span>
	                </div>
	                <div class="slider_selected_right">
	                    <div class="img_wrap">
	                        <div class="select_wrap input_wrap_select">
	                            <input id="cphid" name="hphm" type="text" class="slider_input" readonly="readonly" onclick="chooseHmd();" value="${hphm }">
	                            <a class="empty" href="javascript:chooseHmd()"></a>
	                        </div>
	                    </div>  
	                </div>
	        	</div>
	        	
		        <div class="slider_body">
					<div class="slider_selected_left">
						<span>车牌颜色：</span>
					</div>
					<div id="dropdown_quanxuan" class="slider_selected_right dropdown dropdown_all">
						<div class="input_select xiala">
							<div id="role_type_downlist" class='multi_select'>
								<input type="hidden" name="cplx" id="cplx" value=""/>
								<a class="xiala_duoxuan_a"></a>
							</div>
						</div>
					</div>
				</div>
		        
		        
		        <div class="slider_body">
		            <div class="slider_selected_left">
		                <span>监测点：</span>
		            </div>
		            <div class="slider_selected_right dropdown" id="dropdown_quanxuan">
		                <input class="input_select xiala" onclick="doChoseJcd();" id="jcdid1" name="jcdid1" type="text" value="${jcdid1 }"/> 
		                <input type="hidden" name="jcdid" id="jcdid" value="${jcdid }">
		            </div>
		        </div>
		        
		        <div class="slider_body">
		            <div class="slider_selected_left">
		                <span>查询类型：</span>
		            </div>
		            <div class="slider_selected_right dropdown dropdowns" id="cp_1" onclick="slider(this)">
		                <c:choose>
		            		<c:when test="${empty cxlb }">
			            		<input class="input_select xiala cxlb" type="text" id="xiala" readonly="readonly" value="按天查询"/>
		            		</c:when>
		            		<c:otherwise>
			            		<c:forEach items="${cxlxMap }" var="s">
			            			<c:if test="${s.key eq cxlb }">
		            					<input class="input_select xiala cxlb" type="text" id="xiala" readonly="readonly" value="${s.value }"/>
			            			</c:if>
			                	</c:forEach>
		            		</c:otherwise>
		            	</c:choose>
		               <input type="hidden" id="cxlb" name="cxlb" value="${cxlb }" />
		                <div class="ul"> 
			                    <div class="li xiala_div" data-value="1" onclick="sliders(this)">按天查询</div> 
			                    <div class="li xiala_div" data-value="2" onclick="sliders(this)">按月查询</div> 
			                    <div class="li xiala_div" data-value="3" onclick="sliders(this)">自定义查询</div> 
		                </div> 
		            </div>
		        </div>
		        
				<div class="slider_body cxlb_style1" <c:if test="${cxlb ne '1'}">style="display:none;"</c:if>>
				  <div class="slider_selected_left">
					<span><span style="color:red;">*</span>查询日期：</span>
				  </div>
				 <div class="slider_selected_right">
				  <div class="demolist" style="">
				    <input class="inline laydate-icon" id="cxrq" name="cxrq" value="${cxrq }" 
			onclick="laydate({istime: true, format: 'YYYY-MM-DD'})"/>
				  </div>
				</div>
				</div>
				
				<div class="slider_body cxlb_style2" <c:if test="${cxlb ne '2'}">style="display:none;"</c:if>>
				  <div class="slider_selected_left">
					<span><span style="color:red;">*</span>月份选择：</span>
				  </div>
				  <div class="slider_selected_right dropdown dropdowns little_slider_selected_left" id="cp_2" onclick="slider(this)">
		                <input class="input_select little_xiala" id="xiala1" readonly="readonly" type="text" value="${year }"/> 
		                <input type="hidden" id="year" name="year" value="${year }" />
		                <div class="ul"> 
			                    <div class="li" data-value="2010" onclick="sliders(this)">2010</div>
			                    <div class="li" data-value="2011" onclick="sliders(this)">2011</div>
			                    <div class="li" data-value="2012" onclick="sliders(this)">2012</div>
			                    <div class="li" data-value="2013" onclick="sliders(this)">2013</div>
			                    <div class="li" data-value="2014" onclick="sliders(this)">2014</div>
			                    <div class="li" data-value="2015" onclick="sliders(this)">2015</div>
			                    <div class="li" data-value="2016" onclick="sliders(this)">2016</div>
			                    <div class="li" data-value="2017" onclick="sliders(this)">2017</div>
			                    <div class="li" data-value="2018" onclick="sliders(this)">2018</div>
			                    <div class="li" data-value="2019" onclick="sliders(this)">2019</div>
			                    <div class="li" data-value="2020" onclick="sliders(this)">2020</div>
		              	  </div> 
	           	 	</div><div class="font_year">年</div>
	           	 	<div class="slider_selected_right dropdown dropdowns little_slider_selected_right"  id="cp_2" onclick="slider(this)">
		                <input class="input_select little_xiala" id="xiala1" readonly="readonly" type="text" value="${month }"/> 
		                <input type="hidden" id="month" name="month" value="${month }" />
		                <div class="ul"> 
			                    <div class="li" data-value="01" onclick="sliders(this)">01</div>
			                    <div class="li" data-value="02" onclick="sliders(this)">02</div>
			                    <div class="li" data-value="03" onclick="sliders(this)">03</div>
			                    <div class="li" data-value="04" onclick="sliders(this)">04</div>
			                    <div class="li" data-value="05" onclick="sliders(this)">05</div>
			                    <div class="li" data-value="06" onclick="sliders(this)">06</div>
			                    <div class="li" data-value="07" onclick="sliders(this)">07</div>
			                    <div class="li" data-value="08" onclick="sliders(this)">08</div>
			                    <div class="li" data-value="09" onclick="sliders(this)">09</div>
			                    <div class="li" data-value="10" onclick="sliders(this)">10</div>
			                    <div class="li" data-value="11" onclick="sliders(this)">11</div>
			                    <div class="li" data-value="12" onclick="sliders(this)">12</div>
		                </div> 
	           	 	</div><div class="font_month">月</div>
				</div>
				
				<div class="slider_body cxlb_style3" <c:if test="${cxlb ne '3'}">style="display:none;"</c:if>>
				  <div class="slider_selected_left">
					<span><span style="color:red;">*</span>查询时间：</span>
				  </div>
					<div class="slider_selected_right">
					  <div class="demolist" style="">
					    <input class="inline laydate-icon" id="kssj" name="kssj" value="${kssj }" 
				onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})"/>
					  </div>
					</div>
				</div>
				<div class="slider_body cxlb_style3" <c:if test="${cxlb ne '3'}">style="display:none;"</c:if>>
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
				
	        
	        <div class="slider_body  hidden_div" <c:if test="${isHiddenDiv eq '0'}">style="display:none;"</c:if> >
	            <div class="slider_selected_left">
	                <span>车道：</span>
	            </div>
	            <div class="slider_selected_right dropdown dropdowns" id="cp_2" onclick="slider(this)">
	            <c:choose>
	            	<c:when test="${empty cd }">
		                <input class="input_select xiala" id="cd_xiala" readonly="readonly" type="text" value="==全部=="/> 
	            	</c:when>
	            	<c:otherwise>
	            		<c:forEach items="${cdMap }" var="s">
	            			<c:if test="${s.key eq cd }">
		            			<input class="input_select xiala" id="cd_xiala" readonly="readonly" type="text" value="${s.value }"/> 
	            			</c:if>
	            		</c:forEach>
	            	</c:otherwise>
	            </c:choose>
	                 <input type="hidden" id="cd" name="cd" value="${cd }" />
	                <div class="ul"> 
	                 		<div class="li" data-value="" onclick="sliders(this)">==全部==</div>
	                 		<c:forEach items="${cdMap }" var="s">
			                    <div class="li" data-value="${s.key }" onclick="sliders(this)">${s.value }</div>
	                 		</c:forEach>
	                </div> 
	            </div>
	        </div>
	        <div class="slider_body" style="display:none;">
	            <div class="slider_selected_wrap">
	                <div class="slider_selected_left">
	                    <span>车速(km/h)：</span>
	                </div>
	                 <div>&nbsp;&nbsp;&nbsp;&nbsp;
		                <input id="sd1" name="sd1" type="text" style="width:50px;" value="${sd1 }">
	                 	至
	                  	<input id="sd2" name="sd2" type="text" style="width:50px;" value="${sd2 }">
	                 </div>
	            </div>
	        </div>
	         
	        
	        <div class="slider_body hidden_div" <c:if test="${isHiddenDiv eq '0'}">style="display:none;"</c:if> >
	            <div class="slider_selected_left">
	                <span>排序字段：</span>
	            </div>
	            <div class="slider_selected_right dropdown dropdowns" id="cp_1" onclick="slider(this)">
	                <c:choose>
	            		<c:when test="${empty sort }">
		            		<input class="input_select xiala" type="text" id="sort_xiala" readonly="readonly" value="==不排序=="/>
	            		</c:when>
	            		<c:otherwise>
		            		<c:forEach items="${sortMap }" var="s">
		            			<c:if test="${s.key eq sort }">
	            					<input class="input_select xiala" type="text" id="sort_xiala" readonly="readonly" value="${s.value }"/>
		            			</c:if>
		                	</c:forEach>
	            		</c:otherwise>
	            	</c:choose>
	               <input type="hidden" id="sort" name="sort" value="${sort }" />
	                <div class="ul"> 
	                	<div class="li" data-value="" onclick="sliders(this)">==不排序==</div> 
	                	<c:forEach items="${sortMap }" var="s">
		                    <div class="li" data-value="${s.key }" onclick="sliders(this)">${s.value }</div> 
	                	</c:forEach>
	                </div> 
	            </div>
	        </div>
	        
	        <div class="slider_body  hidden_div" <c:if test="${isHiddenDiv eq '0'}">style="display:none;"</c:if> >
	            <div class="slider_selected_left">
	                <span>排序类型：</span>
	            </div>
	            <div class="slider_selected_right dropdown" id="dropdown_quanxuan">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	            	<c:choose>
	            		<c:when test="${sortType eq 'asc' }">
	            			<input type="radio" id="ck_desc" name="sortType" value="desc"/> 降序&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="radio" id="ck_asc" name="sortType" value="asc" checked="checked"/> 升序
	            		</c:when>
	            		<c:otherwise>
	            			<input type="radio" id="ck_desc" name="sortType" value="desc" checked="checked"/> 降序&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="radio" id="ck_asc" name="sortType" value="asc" /> 升序
	            		</c:otherwise>
	            	</c:choose>
	            </div>
	        </div>
	        
	        <div class="button_wrap clear_both">
	        	<div class="slider_body show_style_control">
           			<input type="radio" id="ck1" name="showStyle" value="show_style0" <c:if test="${showStyle eq 'show_style0' }">checked="checked"</c:if> /> 详情&nbsp;&nbsp;
					<input type="radio" id="ck2" name="showStyle" value="show_style1" <c:if test="${showStyle eq 'show_style1' }">checked="checked"</c:if> /> 列表&nbsp;&nbsp;
					<input type="radio" id="ck3" name="showStyle" value="show_style2" <c:if test="${showStyle eq 'show_style2' }">checked="checked"</c:if> /> 图片&nbsp;&nbsp;
					<input type="radio" id="ck4" name="showStyle" value="show_style3" <c:if test="${showStyle eq 'show_style3' }">checked="checked"</c:if> /> 轨迹
		    	</div>
		    	<div class="button_div">
			    	<input id="query_button" name="query_button" type="button" class="button_blue" value="查询" onclick="doSubmit();">
			    	<input id="reset_button" name="reset_button" type="button" class="button_blue" value="重置" onclick="doReset();">
			    	<input id="more_button" name="more_button" type="button" class="button_blue" value="更多条件" onclick="showMore();">
		    		<div>
						<span id="errSpan"  style="color:red;margin-left:auto;margin-right:auto" ></span>
			    	</div>
		    	</div>
		    </div>
				
    	 	
	        <div class="pg_result">
	        	<div class="show_style0 show_data_div"  <c:if test="${showStyle ne 'show_style0' }">style="display:none;"</c:if> >
	            	<c:forEach items="${pageResult.items }" var="s" varStatus="c">
		            	<div class="detail_div">
		            		<div class="img_div">
		            			<img alt="" src="${s.tp1Url }" onclick="showSbDetail('${s.tp1}');">
		            		</div>
		            		<div class="content_div">
		            			<div class="title_div">监测点：</div>
		            			<div class="text_div">${jcdMap[s.jcdid] }</div>
		            		</div>
		            		<div class="content_div">
		            			<div class="title_div">通过时间：</div>
		            			<div class="text_div">${s.sbsj }</div>
		            		</div>
		            		<div class="content_div">
		            			<div class="title_div">车牌号码：</div>
		            			<div class="text_div">${s.hphm }</div>
		            		</div>
		            	</div>
	            	</c:forEach>
	            </div>
	            
	            <div class="show_style1 show_data_div"  <c:if test="${showStyle ne 'show_style1' }">style="display:none;"</c:if> >
	            	 <table>
		                <thead>
		                    <tr>
		                        <td>序号</td>
		                        <td>车牌号码</td>
		                        <td>车牌颜色</td>
		                        <td>识别时间</td>
		                        <td>入库时间</td>
		                        <td>行驶车道</td>
		                        <td>行驶速度</td>
		                        <td>监测点名称</td>
		                        <td>操作</td>
		                    </tr>
		                </thead>
		                <tbody>
		                    <c:forEach items="${pageResult.items }" var="s" varStatus="c">
		                    	<tr>
		                        <td>${c.index + 1 }</td>
		                        <td>${s.hphm }</td>
		                       	<td>${cplxMap[s.cplx] }</td>
		                        <td>${s.sbsj }</td>
		                        <td>${s.scsj }</td>
		                        <td>${s.cdid }</td>
		                         <td>${s.sd }</td>
		                        <td>${jcdMap[s.jcdid] }</td>
		                        <td><a href="javascript:showSbDetail('${s.tp1 }');">详细信息</a></td>
		                    	</tr>
		                    </c:forEach>
		                </tbody>
		            </table>
	            </div>

				

	           <c:if test="${!empty pageResult.items}">
	           		 <div class="show_style2 bannerbox show_data_div"  <c:if test="${showStyle ne 'show_style2' }">style="display:none;"</c:if>>
					    <div id="big_mess_div">
					    <c:forEach items="${pageResult.items }" var="s" varStatus="c">
					    	<div class="mess_div ${c.index }" <c:if test="${c.index ne 0 }">style="display:none;"</c:if>>
					    		<fieldset style="-moz-border-radius:8px;border:#D2691E 1px solid;">
									<legend style=" color:#333; font-weight:bold; font-size: large; margin-left: 30px;">过车信息</legend>
			                		<table id="three_first_table">
			                			<tr>
			                				<td>
			                					车牌号码:&nbsp;&nbsp;<span class="hphm_str">${s.hphm }</span>
			                				</td>
			                				<td>
			                					<input type="hidden" class="cplx_str" value="${s.cplx }">
			                					车牌颜色:&nbsp;&nbsp;<span>${cplxMap[s.cplx] }</span>
			                				</td>
			                			</tr>
			                			<tr>
			                				<td>
			                					通过时间:&nbsp;&nbsp;<span>${s.sbsj }</span>
			                				</td>
			                				<td>
			                					通过速度:&nbsp;&nbsp;<span>${s.sd }</span>
			                				</td>
			                			</tr>
			                			<tr>
			                				<td>
			                					上传时间:&nbsp;&nbsp;<span>${s.scsj }</span>
			                				</td>
			                				<td>
			                					行驶车道:&nbsp;&nbsp;<span>${s.cdid }</span>
			                				</td>
			                			</tr>
			                			<tr>
			                				<td colspan="2">
			                					监测点:&nbsp;&nbsp;<span>${jcdMap[s.jcdid] }</span>
			                				</td>
			                			</tr>
			                		</table>
		                		</fieldset>
		                		<fieldset style="-moz-border-radius:8px;border:#D2691E 1px solid;">
									<legend style=" color:#333; font-weight:bold; font-size: large; margin-left: 30px;">车辆信息</legend>
									<table id="three_second_table">
										<tr>
											<td>
												中文品牌:&nbsp;&nbsp;<span id="clpp1"><c:if test="${c.index eq 0}">${vehicle.clpp1 }</c:if></span>
											</td>
											<td>
												车辆型号:&nbsp;&nbsp;<span id="clxh"><c:if test="${c.index eq 0}">${vehicle.clxh }</c:if></span>
											</td>
										</tr>
										<tr>
											<td>
												发动机号:&nbsp;&nbsp;<span id="fdjh"><c:if test="${c.index eq 0}">${vehicle.fdjh }</c:if></span>
											</td>
											<td>
												车驾号:&nbsp;&nbsp;<span id="clsbdh"><c:if test="${c.index eq 0}">${vehicle.clsbdh }</c:if></span>
											</td>
										</tr>
										<tr>
											<td>
												车身颜色:&nbsp;&nbsp;<span id="csys"><c:if test="${c.index eq 0}">${vehicle.csys }</c:if></span>
											</td>
											<td>
												车辆状态:&nbsp;&nbsp;<span id="jdczt"><c:if test="${c.index eq 0}">${vehicle.jdczt }</c:if></span>
											</td>
										</tr>
										<tr>
											<td>
												车辆所有人:&nbsp;&nbsp;<span id="jdcsyr"><c:if test="${c.index eq 0}">${vehicle.jdcsyr }</c:if></span>
											</td>
											<td>
												联系电话:&nbsp;&nbsp;<span id="lxfs"><c:if test="${c.index eq 0}">${vehicle.lxfs }</c:if></span>
											</td>
											
										</tr>
										<tr>
											<td colspan="2">
												身份证号:&nbsp;&nbsp;<span id="sfzh"><c:if test="${c.index eq 0}">${vehicle.sfzh }</c:if></span>
											</td>
										</tr>
										<tr>
											<td colspan="2">
												详细住址:&nbsp;&nbsp;<span id="djzzxz"><c:if test="${c.index eq 0}">${vehicle.djzzxz }</c:if></span>
											</td>
										</tr>
									</table>
								</fieldset>
		                	</div>
					    </c:forEach>
					    </div>
					    <div id="focus">
					        <ul>
					        	<c:forEach items="${pageResult.items }" var="s" varStatus="c">
					            	<li><a href="" target="_blank">
					                	<img class="lunbo" src="${s.tp1Url }" alt="" />
					                	</a></li>
					            </c:forEach>
					        </ul>
					    </div>
					</div>
	           </c:if>
	           
	            <div class="show_style3 show_data_div"  <c:if test="${showStyle ne 'show_style3' || fn:length(dayList) < 1}">style="display:none;"</c:if> >
	            	<fieldset style="-moz-border-radius:8px;border:#D2691E 1px solid;">
						<legend style=" color:#333; font-weight:bold; font-size: large; margin-left: 30px;">轨迹日历</legend>
							<div id="date_box" class="date_box">
								<div class="date_top">
									<div class="slider_body" <c:if test="${fn:length(dayList) < 1}">style="display:none;"</c:if>>
							            <div class="slider_selected_left">
							                <span>月份选择：</span>
							            </div>
							            <div class="slider_selected_right dropdown dropdowns" id="cp_1" onclick="slider(this)">
								           <input class="input_select xiala pathMonth" type="text" id="pathMonth_xiala" readonly="readonly"/>
								           <input type="hidden" id="pathMonth" name="pathMonth"/>
							                <div class="ul">
							                	<c:forEach items="${dayList }" var="s" varStatus="c">
								                	<div class="li path_xiala" data-value="${s.key }" onclick="sliders(this)">${s.key }月</div> 
								                </c:forEach>
							                </div> 
							            </div>
							        </div>
								</div>
								<c:forEach items="${dayList }" var="s">
									<div style="display:none;" class="date_div ${s.key }">
										<c:forEach items="${s.list }" var="c">
											<div class="${c.className }" >
												<input class="day_input" y="${c.year }" m="${c.month }" value="${c.day }">
											</div>
										</c:forEach>
									</div>
				                </c:forEach>
							</div>
					</fieldset>
	            </div>
	            
	            
	            	<jsp:include page="/common/pageNavigator.jsp"></jsp:include>
	        </div>
			</form>
        </div>
    </div>
    <jsp:include page="/common/Foot.jsp" />
</body>
	<script type="text/javascript" src="<%=basePath%>common/js/sb/simplefoucs.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/sb/util.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/layer/layer.js" ></script>
	<script type="text/javascript">
		var list_url = "<%=basePath%>clcx/hmdClcx.do";
		//根据页号查询
		function doGoPage(pageNo) {
			if (pageNo >  10000) {
				layer.msg("一万页之后不可以查看！");
				return;
			}
			if(!commonCheck(true,true)){
				return;
			}
			if(!changeTime()){
				return;
			}
			layer.load();
			document.getElementById("pageNo").value = pageNo;
			document.forms[0].action = list_url;
			document.forms[0].submit();
		}
		$(function(){
			//选择未识别时将车牌号置空
			$(".recongnized_div .li").click(function(){
				var value = $(this).attr("data-value");
				if(value == "0"){
					$("#cphid").val("");
				}
			});
			//控制展现方式
			$("input:radio[name='showStyle']").change(function(){
				var value = $("input[name='showStyle']:checked").val();
				var cphid = $.trim($("#cphid").val());
				if (value == 'show_style3') {
					layer.msg('只能查询一辆车的轨迹！');
				}
				$(".show_data_div").hide();
				$("." + value).show();
			});
			//控制选择时间方式
			$(".xiala_div").click(function(){
				var value = $(this).attr("data-value");
				if(value == "1"){
					$(".cxlb_style1").show();
					$(".cxlb_style2").hide();
					$(".cxlb_style3").hide();
				}else if(value == "2"){
					$(".cxlb_style1").hide();
					$(".cxlb_style2").show();
					$(".cxlb_style3").hide();
				}else{
					$(".cxlb_style1").hide();
					$(".cxlb_style2").hide();
					$(".cxlb_style3").show();
				}
			});
			//控制gis日历显示
			$(".path_xiala").click(function(){
				var value = $(this).attr("data-value");
				$("#date_box .date_div").hide();
				$("#date_box ."+value).show();
			});
			var pathMonth = $(".show_style3 .ul div:first").attr("data-value");
			$("#pathMonth_xiala").val(pathMonth);
			$("#date_box ."+pathMonth).show();
			
			//点击日期显示轨迹
			$("#date_box .date_div .day_simple").click(function(){
				var input = $(this).find("input");
				var kssj = input.attr("y")+"-"+input.attr("m")+"-"+input.val() + " 00:00:01";
				var jssj = moment(kssj,"YYYY-MM-DD hh:mm:ss").add(1,'days').format("YYYY-MM-DD")+" 00:00:01";
				var hphm = $.trim($("#cphid").val());
				console.log(kssj+"="+jssj+"="+hphm);
				if(isNotEmpty(hphm)){
					showWheelPath(hphm,kssj,jssj);
				}
			});
			
			//车牌颜色多选下拉
			var value = []; 
			var data = [];
		    var dicJson = ${cplxList };
			for(var i=0;i < dicJson.length;i++){
				value.push(dicJson[i].typeSerialNo);
				data.push(dicJson[i].typeDesc);
			}
		    $('.multi_select').MSDL({
				'value': value,
		      	  'data': data
		    });
		    //条件回显，勾选上次选定的项
		    var bkfwValue = '${cplx}'.split(","); 
		    $('.multi_select').children($('.container')).children($('.contents')).children().each(function(){
				for(var i in bkfwValue){
					if(bkfwValue[i] == $(this).children("input").val()){
						$(this).children("input").attr("checked","checked");
						$(this).children("input").trigger("change");
					};
				};
			});
		    //车牌号提示tip
		    $("#hphm_span").mouseover(function(){
				showTip();
			});
			$("#hphm_span").mouseleave(function(){
				layer.closeAll('tips');
			});
		});
		//提交表单
		function doSubmit(){
			if(!changeTime()){
				return;
			}
			if(!commonCheck(true,true)){
				return;
			}
			var cphid = $.trim($("#cphid").val());
			var style = $("input[name='showStyle']:checked").val();
			if(style == 'show_style3' && (cphid.length > 7 || cphid.indexOf(";") != -1) ){
				layer.msg('只能查询一辆车的轨迹！');
				return;
			}
			layer.load();
			$("#errSpan").text("");
			$("#pageNo").val("1");
			$("#form1").submit();
		}
		//根据不同选择类型填充时间
		function changeTime(){
			var cxlb = $("#cxlb").val();
			if(cxlb == "1"){//按天
				var day = $("#cxrq").val();
				var now = new Date().Format('yyyy-MM-dd');
				var flag = compareTime(now,day);
				if(flag == '1'){
					$("#kssj").val(day + " 00:00:00");
					$("#jssj").val(day + " 23:59:59");
					return true;
				}else if(flag == '2'){
					$("#kssj").val(day + " 00:00:00");
					$("#jssj").val(new Date().Format('yyyy-MM-dd HH:mm:ss'));
					return true;
				}else{
					$("#errSpan").text("错误提示：时间选择错误，不可超过当前日期！");
					return false;
				}
			}else if(cxlb == "2"){//按月
				var year = $("#year").val();
				var month = $("#month").val();
				var now = new Date().Format('yyyy-MM-dd');
				var flag = compareTime(now,year+"-"+month);
				if(flag == '1'){
					var firstDate = new Date(year+"/"+month+"/01 00:00:00");
					var endDate = new Date(firstDate);
					endDate.setMonth(firstDate.getMonth()+1);
					endDate.setDate(1);
					$("#kssj").val(new Date(firstDate).Format('yyyy-MM-dd HH:mm:ss'));
					$("#jssj").val(new Date(endDate).Format('yyyy-MM-dd HH:mm:ss'));
					return true;
				}else if(flag == '2'){
					var firstDate = new Date(year+"/"+month+"/01 00:00:00");
					$("#kssj").val(new Date(firstDate).Format('yyyy-MM-dd HH:mm:ss'));
					$("#jssj").val(new Date().Format('yyyy-MM-dd HH:mm:ss'));
					return true;
				}else{
					$("#errSpan").text("错误提示：时间选择错误，不可超过当前月份！");
					return false;
				}
			}else{
				var kssj = $("#kssj").val();
				var jssj = $("#jssj").val();
				var now = new Date().Format('yyyy-MM-dd HH:mm:ss');
				if(!checkTime(kssj,jssj)){
					$("#errSpan").text("错误提示：时间选择错误，起始时间不可大于截止时间！");
					return false;
				}
				if(!checkTime(kssj,now)){
					$("#errSpan").text("错误提示：时间选择错误，起始时间不可大于当前时间！");
					return false;
				}
				if(checkTime(now,jssj)){
					$("#jssj").val(now);
				}
				return true;
			}
		}
		//显示车牌号码输入提示
		function showTip(){
			layer.open({
		           type: 4,
		           shade: 0,
		           time:8000,
		           closeBtn: 0,
		           tips: [3, '#758a99'],
		           content: ['多个车牌号码查询以半角逗号分隔.','#hphm_span']
		       });
		}
		//显示更多条件
		function showMore(){
			$(".hidden_div").slideToggle(0);
			var flag = $("#isHiddenDiv").val();
			if(flag == "1"){
				$("#isHiddenDiv").val("0");
			}else{
				$("#isHiddenDiv").val("1");
			}
		}
		
		//ajax查询车辆信息encodeURI(encodeURI(hphm))
		function findVeh(index){
	    	var hphm = $("." + index).find(".hphm_str").text();
	    	var cpzl = $("." + index).find(".cplx_str").val();
			$.ajax({
				url:'<%=basePath%>/clcx/getVehInfo.do?' + new Date().getTime(),
				method:"post",
				data:{hphm:hphm,cpzl:cpzl},
				success:function(data){
					if(isNotEmpty(data)){
						checkIsEmpty(index,data.clpp1,"clpp1");
						checkIsEmpty(index,data.clxh,"clxh");
						checkIsEmpty(index,data.fdjh,"fdjh");
						checkIsEmpty(index,data.clsbdh,"clsbdh");
						checkIsEmpty(index,data.csys,"csys");
						checkIsEmpty(index,data.jdczt,"jdczt");
						checkIsEmpty(index,data.jdcsyr,"jdcsyr");
						checkIsEmpty(index,data.lxfs,"lxfs");
						checkIsEmpty(index,data.sfzh,"sfzh");
						checkIsEmpty(index,data.djzzxz,"djzzxz");
					}else{
						$("."+index).find("#three_second_table").find("span").text("");
					}
				},
				error: function () {//请求失败处理函数
					$("."+index).find("#three_second_table").find("span").text("");
				}
			});
		}
		function checkIsEmpty(index,val,id){
			if(isEmpty(val)){
				$("." + index).find("#"+id).text("");
			}else{
				$("." + index).find("#"+id).text(val);
			}
		}
		//重置..按钮
		function doReset(){
			$("#errSpan").text("");
			$("#cphid").val("");
			$("#cplx").val("");
			$(".container input").prop("checked", false);
			$("#jcdid").val("");
			$("#jcdid1").val("==全部==");
			$("#unrecognizedFlag").val("11");
			$("#reco_xiala").val("==全部==");
			$("#cd_xiala").val("==全部==");
			$("#cd").val("");
			$("#sd1").val("");
			$("#sd2").val("");
			$("#sort").val("");
			$("#sort_xiala").val("==不排序==");
			$("#ck_desc").attr("checked",true);
		}
		//选择红名单
		function chooseHmd(){
			var location = (window.location+'').split('/');  
			var basePath = location[0]+'//'+location[2]+'/'+location[3];  
			var url = basePath + "/clcx/hmdChoose.do";
			layer.open({
				  type: 2,
				  title:'选择红名单',
				  area:['600px','450px'],
				  closeBtn:1,
				  shadeClose:false,
				  content: url
				});
		}
	</script>
</html>