<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>   
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html lang="zh-CN">
	<head>
		<base href="<%=basePath%>">
		<title>布控申请</title>
		<link rel="stylesheet" href="<%=basePath%>common/css/style.css" type="text/css">
		
	</head>
	<body>
		<jsp:include page="/common/Head.jsp" />
		<div id="divTitle">
			<span id="spanTitle">布控申请</span>
		</div>
		<div class="content">
	    	<div class="content_wrap">
    			<form id="form" name="form" action="" method="post">
		    		<fieldset style="-moz-border-radius:8px;border:#D2691E 1px solid;">
							<legend style=" color:#333; font-weight:bold; font-size: large; margin-left: 30px;">车辆信息</legend>
				    		<div id="110bk_div" class="slider_body" style="clear:both">
					            <div class="slider_selected_left">
					                <span>&nbsp;&nbsp;&nbsp;<span style="color: red">*</span>110布控来源：</span>
					            </div>
					            <div class="slider_selected_right dropdown dropdowns" onclick="slider(this)">
					                <input class="input_select xiala" id="cad" type="text" readonly="readonly" value="否"/>
					                <input type="hidden" name="cad" value="0" /> 
					                <div class="ul">
					                   	<div class="li" data-value="1" onclick="sliders(this);change110();">是</div>
					                   	<div class="li" data-value="0" onclick="sliders(this);change110();">否</div>
									</div>
					            </div>
					        </div>
	        				<input type="button" id="btn_110" class="submit_b" onclick="open110()" style="display:none" value="选择110"/>
			    			<div class="slider_body" style="clear:both">
				                <div class="slider_selected_left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: red">*</span>号牌号码：</div>
				                <div class="slider_selected_right" style="">
				                    <div class="img_wrap">
				                        <div class="select_wrap input_wrap_select">
				                            <input id="cphid" name="hphm" type="text" class="slider_input" maxlength="8" onchange="findVeh();"
				                            onkeyup="(this.v=function(){this.value=this.value.replace(/[ ]+/,'');}).call(this)" onblur="this.v();"/>
				                            <a id="cphUI" class="empty" href="javascript:doCplrUI()"></a>
				                        </div>
				                    </div>  
				                </div>
				       		</div>
					        <div class="slider_body">
					            <div class="slider_selected_left">
					                <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: red">*</span>号牌种类：</span>
					            </div>
					            <div id="div_hpzl" class="slider_selected_right dropdown dropdowns">
					                <input class="input_select xiala" id="hpzl" type="text" readonly="readonly" value="==请选择=="/>
					                <input type="hidden" name="hpzl" value="" /> 
					                <div class="ul">
									    <c:forEach items= "${dicList}" var="dic">
					            	  		<c:if test="${dic.typeCode eq 'HPZL' }">
						                    	<div class="li" data-value="${dic.typeSerialNo}" onclick="sliders(this);findVeh();">${dic.typeDesc}</div>
					            	  		</c:if> 
									 	</c:forEach>
									</div>
					            </div>
					        </div>
					        <div class="slider_body">
					            <div class="slider_selected_left">
					                <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: red;visibility: hidden;">*</span>发动机号：</span>
					            </div>
					            <div class="slider_selected_right">
					                <div class="img_wrap">
					                   <div class="select_wrap select_input_null">
					                        <input id="fdjh" name="fdjh" type="text" class="slider_input" maxlength="20" readonly="readonly"/>
					                   </div>
					                </div>  
					            </div>
					        </div>
				        	<div class="slider_body">
				                <div class="slider_selected_left">
				                    <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: red;visibility: hidden;">*</span>车架号：</span>
				                </div>
				                <div class="slider_selected_right" style="">
				                    <div class="img_wrap">
				                        <div class="select_wrap select_input_null">
				                            <input id="clsbdh" name="clsbdh" type="text" class="slider_input" maxlength="20" readonly="readonly"/>
				                        </div>
				                    </div>  
				                </div>
				        	</div>
				        	<div class="slider_body">
				                <div class="slider_selected_left">
				                    <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: red;visibility: hidden;">*</span>中文品牌：</span>
				                </div>
				                <div class="slider_selected_right">
				                    <div class="img_wrap">
				                        <div class="select_wrap select_input_null">
				                            <input id="clpp" name="clpp" type="text" class="slider_input" readonly="readonly"/>
				                        </div>
				                    </div>  
				                </div>
				        	</div>
				        	<div class="slider_body">
				                <div class="slider_selected_left">
				                    <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: red;visibility: hidden;">*</span>车辆型号：</span>
				                </div>
				                <div class="slider_selected_right" style="">
				                    <div class="img_wrap">
				                        <div class="select_wrap select_input_null">
				                            <input id="clxh" name="clxh" type="text" class="slider_input" maxlength="80" readonly="readonly"/>
				                        </div>
				                    </div>  
				                </div>
				        	</div>
					        <div class="slider_body">
					            <div class="slider_selected_left">
					                <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: red;visibility: hidden;">*</span>车身颜色：</span>
					            </div>
					            <div class="slider_selected_right dropdown dropdowns" >
					                <input class="input_select xiala_null" id="csys" type="text" readonly="readonly" value=""/>
					                <input type="hidden" name="csys" value="" /> 
				<!-- 	                <div class="ul"> -->
				<!-- 					    <div class="li" data-value="" onclick="sliders(this)"></div> -->
				<!-- 					    <c:forEach items= "${dicList}" var="dic"> -->
				<!-- 	            	  		<c:if test="${dic.typeCode eq 'CSYS' }"> -->
				<!-- 		                    	<div class="li" data-value="${dic.typeSerialNo}" onclick="sliders(this)">${dic.typeDesc}</div> -->
				<!-- 	            	  		</c:if>  -->
				<!-- 					 	</c:forEach> -->
				<!-- 					</div> -->
					            </div>
					        </div>
				        	<div class="slider_body">
				                <div class="slider_selected_left">
				                    <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: red;visibility: hidden;">*</span>车辆所有人：</span>
				                </div>
				                <div class="slider_selected_right" style="">
				                    <div class="img_wrap">
				                        <div class="select_wrap select_input_null">
				                            <input id="clsyr" name="clsyr" type="text" class="slider_input" maxlength="80" readonly="readonly"/>
				                        </div>
				                    </div>  
				                </div>
				        	</div>
				        	<div class="slider_body">
				                <div class="slider_selected_left">
				                    <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: red;visibility: hidden;">*</span>所有人电话：</span>
				                </div>
				                <div class="slider_selected_right" >
				                    <div class="img_wrap">
				                        <div class="select_wrap select_input_null">
				                            <input id="syrlxdh" name="syrlxdh" type="text" class="slider_input" maxlength="20" readonly="readonly" 
				                            onkeyup="(this.v=function(){this.value=this.value.replace(/[^0-9-]+/,'');}).call(this)" onblur="this.v();"/>
				                        </div>
				                    </div>  
				                </div>
				        	</div>
				        	<div class="slider_body_textarea" style="height:70px">
				                <div class="slider_selected_left">
				                    <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: red;visibility: hidden;">*</span>所有人地址：</span>
				                </div>
				                <div class="slider_selected_right">
				                	<textarea id="syrxxdz" name="syrxxdz" rows="2" style="width:951px" readonly="readonly"></textarea>
				                </div>
				        	</div>
	        			</fieldset>
				        <fieldset style="-moz-border-radius:8px;border:#D2691E 1px solid;">
							<legend style=" color:#333; font-weight:bold; font-size: large; margin-left: 30px;">布控申请信息</legend>
					        <div class="slider_body" style="position:relative;clear:both;">
					            <div class="slider_selected_left">
					                <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: red">*</span>布控大类：</span>
					            </div>
					            <div class="slider_selected_right dropdown dropdowns" onclick="slider(this)">
					                <input class="input_select xiala" id="bkdl" type="text" readonly="readonly" value="==请选择==" />
					                <input type="hidden" id="bkdlValue" name="bkdl" value="" /> 
					                <div class="ul">
									    <c:forEach items= "${dicList}" var="dic">
					            	  		<c:if test="${dic.typeCode eq 'BKDL' && dic.typeSerialNo eq '1'}">
						                    	<div class="li" data-value="${dic.typeSerialNo}" onclick="sliders(this);selectBklb('${dic.typeSerialNo}');selectBjlx('${dic.typeSerialNo}');">${dic.typeDesc}</div>
					            	  		</c:if>
					            	  		<c:if test="${dic.typeCode eq 'BKDL' && dic.typeSerialNo eq '2' && fn:length(user.position) > 2 && fn:substring(user.position,2,3) eq '0'}">
						                    	<div class="li" data-value="${dic.typeSerialNo}" onclick="sliders(this);selectBklb('${dic.typeSerialNo}');selectBjlx('${dic.typeSerialNo}');">${dic.typeDesc}</div>
					            	  		</c:if>
					            	  		<c:if test="${dic.typeCode eq 'BKDL' && dic.typeSerialNo eq '3' && (fn:length(user.position) > 2 || user.position eq '71'|| user.position eq '72')}">
						                    	<div class="li" data-value="${dic.typeSerialNo}" onclick="sliders(this);selectBklb('${dic.typeSerialNo}');selectBjlx('${dic.typeSerialNo}');">${dic.typeDesc}</div>
					            	  		</c:if>  
									 	</c:forEach>
									</div>
					            </div>
					        </div>
					        <div class="slider_body">
					            <div class="slider_selected_left">
					                <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: red">*</span>布控类别：</span>
					            </div>
					            <div class="slider_selected_right dropdown dropdowns" onclick="slider(this)">
					                <input class="input_select xiala" id="bklb" type="text" readonly="readonly" value="==请选择=="/>
					                <input type="hidden" id="bklbValue" name="bklb" value="" /> 
					                <div class="ul" id="bklbUl">
										
									</div>
					            </div>
					        </div>
					        <div class="slider_body">
					            <div class="slider_selected_left">
					                <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: red">*</span>报警类型：</span>
					            </div>
					            <div class="slider_selected_right dropdown dropdowns" onclick="slider(this)">
					                <input class="input_select xiala" id="bjlx" type="text" readonly="readonly" value="==请选择=="/>
					                <input type="hidden" id="bjlxValue" name="bjlx" value="" /> 
					                <div class="ul" id="bjlxUl">
										
									</div>
					            </div>
					        </div>
					        <div class="slider_body" style="display:none">
					            <div class="slider_selected_left">
					                <span>级别<span style="color: red">*</span>：</span>
					            </div>
					            <div class="slider_selected_right dropdown dropdowns" onclick="slider(this)">
					                <input class="input_select xiala" id="bkjb" type="text" readonly="readonly" value="==请选择=="/>
					                <input type="hidden" id="bkjbValue" name="bkjb" value="" /> 
					                <div class="ul">
									    <c:forEach items= "${dicList}" var="dic">
					            	  		<c:if test="${dic.typeCode eq 'BKJB' }">
						                    	<div class="li" data-value="${dic.typeSerialNo}" onclick="sliders(this);isOther();">${dic.typeDesc}</div>
					            	  		</c:if> 
									 	</c:forEach>
									</div>
					            </div>
					        </div>
					        <div class="slider_body" id="bkfw_div" style="display:none">
								<div class="slider_selected_left">
									<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: red">*</span>布控范围：</span>
								</div>
								<div class="slider_selected_right dropdown dropdown_all" id="dropdown_quanxuan">
								      <div class="input_select xiala">
								            <div class='multi_select'>
								            	<input type="hidden" name="bkfw" value="" />
												<a class="xiala_duoxuan_a"></a>
								            </div>
								      </div>
								</div>
							</div>
				        	<div class="slider_body_textarea" id="bknr" style="height:80px;">
				                <div class="slider_selected_left">
				                    <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: red">*</span>预案内容：</span>
				                </div>
				                <div class="slider_selected_right">
				                	<textarea name="by2" id="by2" rows="3" style="width:951px" placeholder="请先选择'布控大类'和'预案级别'"></textarea>
				                </div>
				        	</div>
					        <div class="slider_body" >
								<div class="slider_selected_left">
								     <span>&nbsp;&nbsp;<span style="color: red">*</span>布控起始时间：</span>
								</div>
								<div class="slider_selected_right">
								     <div class="demolist">
								         <input class="inline laydate-icon" name="bkqssj" value="${bkqssj }" readonly="readonly" onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})"/>
								     </div>
								</div>
							</div>
							<div class="slider_body" >
								<div class="slider_selected_left">
								     <span>&nbsp;&nbsp;<span style="color: red">*</span>布控终止时间：</span>
								</div>
								<div class="slider_selected_right">
								     <div class="demolist">
								         <input class="inline laydate-icon" name="bkjzsj" value="${bkjzsj }" readonly="readonly" onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})"/>
								     </div>
								</div>
							</div>
							<div class="slider_body">
				                <div class="slider_selected_left">
				                    <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: red">*</span>联系电话：</span>
				                </div>
				                <div class="slider_selected_right" style="">
				                    <div class="img_wrap">
				                        <div class="select_wrap select_input_wrap">
				                            <input id="bkjglxdh" name="bkjglxdh" type="text" class="slider_input" maxlength="12"
				                            onkeyup="(this.v=function(){this.value=this.value.replace(/[^0-9-]+/,'');}).call(this)" onblur="this.v();"/>
				                            <a id="bkjglxdh" class="empty"></a>
				                        </div>
				                    </div>  
				                </div>
				        	</div>
					        <div class="slider_body" style="display:none">
					            <div class="slider_selected_left">
					                <span>&nbsp;&nbsp;<span style="color: red">*</span>布控范围类型：</span>
					            </div>
					            <div class="slider_selected_right dropdown dropdowns" onclick="slider(this);changeBkfwlx();">
					                <input class="input_select xiala" id="bkfwlx" type="text" readonly="readonly" value="本地布控"/>
					                <input type="hidden" name="bkfwlx" value="1" /> 
					                <div class="ul">
									    <c:forEach items= "${dicList}" var="dic">
					            	  		<c:if test="${dic.typeCode eq 'BKFWLX' }">
						                    	<div class="li" data-value="${dic.typeSerialNo}" onclick="sliders(this);changeBkfwlx();">${dic.typeDesc}</div>
					            	  		</c:if> 
									 	</c:forEach>
									</div>
					            </div>
					        </div>
<!-- 	        <div class="slider_body"> -->
<!-- 	            <div class="slider_selected_left"> -->
<!-- 	                <span>布控范围<span style="color: red">*</span>：</span> -->
<!-- 	            </div> -->
<!-- 	            <div class="slider_selected_right dropdown dropdowns" onclick="slider(this)"> -->
<!-- 	                <input class="input_select xiala" id="bkfw" type="text" readonly="readonly" value="==请选择=="/> -->
<!-- 	                <input type="hidden" name="bkfw" value="" />  -->
<!-- 	                <div class="ul"> -->
<!-- 					    <c:forEach items= "${dicList}" var="dic"> -->
<!-- 	            	  		<c:if test="${dic.typeCode eq 'BKFW' }"> -->
<!-- 		                    	<div class="li" data-value="${dic.typeSerialNo}" onclick="sliders(this)">${dic.typeDesc}</div> -->
<!-- 	            	  		</c:if>  -->
<!-- 					 	</c:forEach> -->
<!-- 					</div> -->
<!-- 	            </div> -->
<!-- 	        </div> -->
				        	<div class="slider_body">
				                <div class="slider_selected_left">
				                    <span>&nbsp;&nbsp;&nbsp;&nbsp;短信接收号码：</span>
				                </div>
				                <div class="slider_selected_right">
				                    <div class="img_wrap">
				                        <div class="select_wrap select_input_wrap">
				                            <input id="dxjshm" name="dxjshm" type="text" class="slider_input" placeholder="多个号码以逗号隔开" maxlength="256"
				                            onkeyup="(this.v=function(){this.value=this.value.replace(/[^0-9,]+/,'');}).call(this)" onblur="this.v();"/>
				                            <a id="dxjshm" class="empty"></a>
				                        </div>
				                    </div>  
				                </div>
				        	</div>
				        	<div class="slider_body">
				                <div class="slider_selected_left">
				                    <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: red;visibility: hidden;">*</span>涉枪涉爆：</span>
				                </div>
				                <div class="slider_selected_right">
				                	<input type="radio" name="sqsb" id="sqsb" value="1" />是&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			                        <input type="radio" name="sqsb" id="sqsb" value="0" checked="checked"/>否
			                    </div>
				        	</div>
					        <div class="slider_body">
					            <div class="slider_selected_left">
					                <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: red;visibility: hidden;">*</span>布控性质：</span>
					            </div>
					            <div class="slider_selected_right dropdown dropdowns" onclick="slider(this)">
					                <input class="input_select xiala" id="bkxz" type="text" readonly="readonly" value="公开"/>
					                <input type="hidden" name="bkxz" value="1" /> 
					                <div class="ul">
									    <c:forEach items= "${dicList}" var="dic">
					            	  		<c:if test="${dic.typeCode eq 'BKXZ' }">
					            	  			<c:if test="${dic.typeSerialNo eq '1'}">
					            	  				<script>$("#bkxz").val("${dic.typeDesc}");</script>
					            	  			</c:if>
						                    	<div class="li" data-value="${dic.typeSerialNo}" onclick="sliders(this)">${dic.typeDesc}</div>
					            	  		</c:if> 
									 	</c:forEach>
									</div>
					            </div>
					        </div>
					        <div class="slider_body" style="display:none">
					            <div class="slider_selected_left">
					                <span>预案级别：</span>
					            </div>
					            <div class="slider_selected_right dropdown dropdowns" onclick="slider(this)">
					                <input class="input_select xiala" id="bjya" type="text" readonly="readonly" value="==请选择=="/>
					                <input type="hidden" name="bjya" value="" /> 
					                <div class="ul">
									    <div class="li" data-value="" onclick="sliders(this)">==请选择==</div>
									    <c:forEach items= "${dicList}" var="dic">
					            	  		<c:if test="${dic.typeCode eq 'BJYA' }">
						                    	<div class="li" data-value="${dic.typeSerialNo}" onclick="sliders(this)">${dic.typeDesc}</div>
					            	  		</c:if> 
									 	</c:forEach>
									</div>
					            </div>
					        </div>
				        	<div class="slider_body">
				                <div class="slider_selected_left">
				                    <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: red;visibility: hidden;">*</span>立案单位：</span>
				                </div>
				                <div class="slider_selected_right" style="">
				                    <div class="img_wrap">
				                        <div class="select_wrap select_input_wrap">
				                            <input id="ladw" name="ladw" type="text" class="slider_input" maxlength="40"/>
				                            <a id="ladw" class="empty"></a>
				                        </div>
				                    </div>  
				                </div>
				        	</div>
				        	<div class="slider_body">
				                <div class="slider_selected_left">
				                    <span>立案单位联系人：</span>
				                </div>
				                <div class="slider_selected_right">
				                    <div class="img_wrap">
				                        <div class="select_wrap select_input_wrap">
				                            <input id="lar" name="lar" type="text" class="slider_input" maxlength="10"/>
				                            <a id="lar" class="empty"></a>
				                        </div>
				                    </div>  
				                </div>
				        	</div>
				        	<div class="slider_body">
				                <div class="slider_selected_left">
				                    <span>&nbsp;&nbsp;<span style="color: red;visibility: hidden;">*</span>立案单位电话：</span>
				                </div>
				                <div class="slider_selected_right" style="">
				                    <div class="img_wrap">
				                        <div class="select_wrap select_input_wrap">
				                            <input id="ladwlxdh" name="ladwlxdh" type="text" class="slider_input" maxlength="12" 
				                            onkeyup="(this.v=function(){this.value=this.value.replace(/[^0-9-]+/,'');}).call(this)" onblur="this.v();"/>
				                            <a id="ladwlxdh" class="empty"></a>
				                        </div>
				                    </div>  
				                </div>
				        	</div>
				        	<div class="slider_body_textarea">
				                <div class="slider_selected_left">
				                    <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: red">*</span>简要案情：</span>
				                </div>
				                <div class="slider_selected_right">
				                	<textarea name="jyaq" id="jyaq" rows="3" style="width:951px"></textarea>
				                </div>
				        	</div>
					        <div id="leader_div">
						        <div class="slider_body">
						        	<div class="slider_selected_left">
						            	<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: red;visibility: hidden;">*</span>报备领导：</span>
						        	</div>
									<div class="slider_selected_right dropdown dropdowns" onclick="slider(this)">
							            <input class="input_select xiala" id="leader" type="text" readonly="readonly" value="==请选择=="/>
							            <input type="hidden" name="leader" value="" /> 
							           	<div class="ul">
							                <div class="li" data-value="" onclick="sliders(this)">==请选择==</div>
											<c:forEach items= "${leaders}" var="leader">
							                    <div class="li" data-value="${leader.loginName}" onclick="sliders(this)">${leader.userName}</div>
											</c:forEach>
										</div>
							    	</div>
						    	</div>
							    <div class="slider_body">
							        <div class="slider_selected_left">
							            <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: red;visibility: hidden;">*</span>发送短信：</span>
							       	</div>
							        <div class="slider_selected_right">
										<input type="radio" name="isSend" id="isSend" value="1" checked="checked" />是&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						    			<input type="radio" name="isSend" id="isSend" value="0" />否
						   			</div>
								</div>
						    </div>
					  	<div id="tzdwDiv" class="slider_body" style="clear:both;display:none;">
							<div class="slider_selected_left">
								<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: red;visibility: hidden;">*</span>通知单位：</span>
							</div>
							<div class="slider_selected_right dropdown dropdown_all" id="dropdown_quanxuan">
								<div class="input_select xiala">
									<div class='multi_select2'>
										<input type="hidden" id="tzdw" name="tzdw" value="" />
										<a class="xiala_duoxuan_a"></a>
									</div>
								</div>
							</div>
						</div>
						<div id="tznrDiv" class="slider_body_textarea" style="height:150px;display:none;">
							<div class="slider_selected_left">
								<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: red;visibility: hidden;">*</span>通知内容：</span>
							</div>
							<div class="slider_selected_right">
									<textarea name="tznr" id="tznr" rows="3" style="width:951px"></textarea>
							</div>
						</div>
	        		</fieldset>
	        		<input type="hidden" id="cadBkid" name="cadBkid"/>
	        	</form>
				<div class="clear_both" style="height: 150px;">
					<button id="bk_button" class="submit_b" onclick="toSave(false,false)">布控</button>
					<button id="zjbk_button" class="submit_b" onclick="toSave(false,true)">直接布控</button>
			        <button class="submit_b" onclick="toClose()">返回</button>
				</div>
        	</div>
    	</div>
		<jsp:include page="/common/Foot.jsp" />
	</body>
<!-- <script type="text/javascript" src="<%=basePath%>common/js/activiti/jquery-1.8.3.js"></script> -->
<script type="text/javascript" src="<%=basePath%>common/js/activiti/plugins/jui/jquery-ui-1.9.2.min.js"></script>
<script type="text/javascript" src="<%=basePath%>common/js/activiti/plugins/qtip/jquery.qtip.pack.js"></script>
<script type="text/javascript" src="<%=basePath%>common/js/activiti/plugins/html/jquery.outerhtml.js"></script>
<script type="text/javascript" src="<%=basePath%>common/js/activiti/plugins/blockui/jquery.blockUI.js"></script>
<script type="text/javascript" src="<%=basePath%>common/js/layer/layer.js"></script>
<script>
	var dic = jQuery.parseJSON('${dicListJSON}');
	var zw = '${user.position}'.substring(0,2);//职位前2位
	var zwNum = parseInt('${user.position}'.length);//职位长度
	var zw2 = "";
	if(zwNum > 2){
		zw2 = '${user.position}'.substring(2,3);//职位后1位
	}
	$(function(){
		if(zw < '81'){//110布控为直接布控
			 $("#110bk_div").hide();
		}
		//布控范围多选框初始化
		var value = []; 
		var data = []; 
		for(var i=0;i<dic.length;i++){
			if(dic[i].typeCode == "BKFW"){
				value.push(dic[i].typeSerialNo);
				data.push(dic[i].typeDesc);
			}
		}
	    $('.multi_select').MSDL({
		  'value': value,
	      'data': data
	    });
	    
	    var depts = jQuery.parseJSON('${depts}');
		//通知单位多选框初始化
		var value2 = []; 
		var data2 = []; 
		for(var i=0;i < depts.length;i++){
			if(depts[i].dept_no != '440300010100'){
				value2.push(depts[i].dept_no);
				data2.push(depts[i].dept_name);
			}
		}
	    $('.multi_select2').MSDL({
		  'value': value2,
	      'data': data2
	    });
	    
	    $("#div_hpzl").click(function(){slider(this);});
	});
	//补齐两位数
    function padleft0(obj) {
        return obj.toString().replace(/^[0-9]{1}$/, "0" + obj);
    }
	//取消..按钮
	function toClose() {
		window.location = '<%=basePath %>dispatched/findDispatched.do?' + new Date().getTime();
	}
	//保存..按钮
	function toSave(addRight,isZjbk) {
		if(isZjbk && $("input[name='leader']").val() == "" && '${leaders}' != "[]"){
			//分局、市局警员申请时不用报备，否则会重复报备
			if('${user.position}' == "81" && $("input[name='bklb']").val() == "02"){
			}else if('${user.position}' == "91" && $("input[name='bklb']").val() != "02"){
			}else{
				alert("直接布控必须选择报备领导");
				return;
			}
		}
		
		//非涉案类，不需要通知单位
		if($("input[name='bkdl']").val() != "1"){
			$("input[name='tzdw']").val("");
			$("textarea[name='tznr']").val("");
		}
		
		if(validate()){
			$.blockUI({
		        message: '<h2><img src="<%=basePath%>common/images/ajax/loading.gif" align="absmiddle"/>正在提交请求,请稍候……</h2>'
		    });
		    $.post('${pageContext.request.contextPath}/dispatched/addDispatched.do?addRight='+addRight+'&isZjbk='+isZjbk,$("#form").serialize()  
		    , function(resp) {
				$.unblockUI();
		        if (resp.result == 'success') {
		        	if(isZjbk){
		            	alert('直接布控成功，并已报备相关领导');
		        	}else{
		            	alert('布控申请成功，进入审批流程');
		            }
		            $.blockUI({
			 	        message: '<h2><img src="' + 'common/images/ajax/loading.gif" align="absmiddle"/>正在提交请求,请稍候……</h2>'
			 	    });
		            location.replace("dispatched/findDispatched.do");
		        } else if(resp.result == 'repeat'){
					alert('该车正在布控中，且等级比当前布控高，您的布控无法生效，\n现有布控信息：'+ resp.info);
				} else if(resp.result == 'equal'){
					if(confirm('该车正在布控中，且等级与当前布控相同，继续布控将会同时生效多个布控，确认要再次布控吗？\n现有布控信息：'+ resp.info)){
						toSave(true,isZjbk);//确认申请布控
					}
				} else if(resp.result == 'recover'){
					if(confirm('该车正在布控中，确认要再次布控吗？继续布控将会使现有布控失效！\n现有布控信息：'+ resp.info)){
						toSave(true,isZjbk);//确认申请布控
					}
				} else if(resp.result == 'hmd'){
					alert('该车为红名单车辆，不允许布控');
				} else {
		            alert('布控申请失败，请联系管理员');
		        }
		    },'json');
		}
	}
	
	function validate() {
		if($("input[name='dxjshm']").val() != ""){
			var dxjshm = $("input[name='dxjshm']").val();
			var phone = dxjshm.split(",");
			for(var i in phone){
				if(phone[i].length != 11){
					alert("短信接收号码错误,请输入11位手机号码,并以逗号隔开");
					$("input[name='dxjshm']").focus();
					return false;
				}
			}
		}
		if($("input[name='hphm']").val().trim() == ""){
			alert("请输入号牌号码");
			$("input[name='hphm']").focus();
			return false;
		}else if($("input[name='hphm']").val().length <5 ){
			alert("号牌号码不正确，请重新输入");
			$("input[name='hphm']").focus();
			return false;
		}else if($("input[name='hpzl']").val().trim() == ""){
			alert("请选择号牌种类");
			$("input[name='hpzl']").focus();
			return false;
		}else if($("input[name='bkdl']").val().trim() == ""){
			alert("请选择布控大类");
			$("input[name='bkdl']").focus();
			return false;
		}else if($("input[name='bklb']").val().trim() == ""){
			alert("请选择布控类别");
			$("input[name='bklb']").focus();
			return false;
		}
		else if($("input[name='bjlx']").val().trim() == ""){
			alert("请选择报警类型");
			$("input[name='bjlx']").focus();
			return false;
		}
// 		else if($("input[name='bkjb']").val().trim() == ""){
// 			alert("请选择级别");
// 			$("input[name='bkjb']").focus();
// 			return false;
// 		}
		else if($("input[name='bkjzsj']").val().trim() == ""){
			alert("请选择布控终止时间");
			$("input[name='bkjzsj']").focus();
			return false;
		}else if($("input[name='bkqssj']").val().trim() == ""){
			alert("请选择布控起始时间");
			$("input[name='bkqssj']").focus();
			return false;
		}else if($("input[name='bkjglxdh']").val().trim() == ""){
			alert("请输入联系电话");
			$("input[name='bkjglxdh']").focus();
			return false;
		}
// 		else if($("input[name='bkfwlx']").val().trim() == ""){
// 			alert("请选择布控范围类型");
// 			$("input[name='bkfwlx']").focus();
// 			return false;
// 		}
		else if($("input[name='bklb']").val()=="00" && $("input[name='bkfw']").val().trim() == ""){
			alert("请选择布控范围");
			$("input[name='bkfw']").focus();
			return false;
		}else if($("textarea[name='jyaq']").val().trim() == ""){
			alert("请输入简要案情");
			$("textarea[name='jyaq']").focus();
			return false;
		}else if(!compareDate($("input[name='bkqssj']").val(),$("input[name='bkjzsj']").val())){
			alert("布控起始时间不能大于结束时间");
			return false;
		}else if($("textarea[name='by2']").val().trim() == ""){
			alert("请输入预案内容");
			$("textarea[name='by2']").focus();
			return false;
		}else if($("input[name='bkdl']").val() == "1" && $("input[name='tzdw']").val().trim() != "" && $("textarea[name='tznr']").val().trim() == ""){
			alert("请输入通知内容");
			$("textarea[name='tznr']").focus();
			return false;
		}else{
			return true;
		}
	}
	//查询布控类别
	function selectBklb(value) {
		var dicBklb= jQuery.parseJSON('${bklbList}');
		$("#bklbUl").empty();
		$("#bklb").val("==请选择==");
		$("input[name='bklb']").val("");
		for(var i=0;i< dicBklb.length;i++){
			if(dicBklb[i].SUPERID == value){
				if(value == "1" && (zw < '70' || zw > '90') && dicBklb[i].ID == "02"){//市局和支队没有分局布控
				}else if(value == "3" && zwNum > 2 ){//管控类
					if(zw2 == "1" && dicBklb[i].ID != "03"){
					}else if(zw2 == "2" && dicBklb[i].ID != "04"){
					}else if(zw2 == "3" && dicBklb[i].ID != "05"){
					}else if(zw2 == "4" && dicBklb[i].ID != "06"){
					}else if(zw2 == "5" && dicBklb[i].ID != "07"){
					}else if(zw2 == "6" && dicBklb[i].ID != "08"){
					}else if(zw2 == "7" && dicBklb[i].ID != "09"){
					}else{
						$("#bklbUl").append('<div class="li" data-value='+dicBklb[i].ID+' onclick="sliders(this);changeBkfwlx();isOther();">'+dicBklb[i].NAME+'</div>');
					}
				} else{
					$("#bklbUl").append('<div class="li" data-value='+dicBklb[i].ID+' onclick="sliders(this);changeBkfwlx();isOther();">'+dicBklb[i].NAME+'</div>');
				}
			}
		}
		
		//是否隐藏通知单位，仅涉案类不隐藏
		if(value == "1"){
			$("#tzdwDiv").show();
			$("#tznrDiv").show();
		} else {
			$("#tzdwDiv").hide();
			$("#tznrDiv").hide();
		}
	}
	//查询报警类型
	function selectBjlx(value) {
		$("#bjlxUl").empty();
		$("#bjlx").val("==请选择==");
		$("input[name='bjlx']").val("");
		var bkdl = "";
		if(value == "1"){
			bkdl = "BKDL1";
		}else if(value == "2"){
			bkdl = "BKDL2";
		}else if(value == "3"){
			bkdl = "BKDL3";
		}
		for(var i=0;i < dic.length;i++){
			if(dic[i].typeCode == bkdl){
				$("#bjlxUl").append('<div class="li" data-value='+dic[i].typeSerialNo+' onclick="sliders(this);isOther();">'+dic[i].typeDesc+'</div>');
			}
		}
	}
	//预案参数
	function isOther() {
		if($("#bklbValue").val() != "" && $("#bjlxValue").val() != ""){
		    $.ajax({
			    url: '${pageContext.request.contextPath}/dispatched/selectYACS.do',
			    dataType: "json",  
			    async: true, 
			    data: { "bklb": $("#bklbValue").val(),
			    		"bjlx": $("#bjlxValue").val()},    
			    type: "POST",   
			    success: function(data) {
			        $("textarea[name='by2']").val(data);
			    }
			});
		}
	}
	//ajax查询车辆信息
		function findVeh(){
	    	var hphm = $("input[name='hphm']").val();
	    	var cpzl = $("input[name='hpzl']").val();
			if(hphm.length != 7 || cpzl == ""){
				return;
			}
			$.ajax({
				url:'<%=basePath%>/dispatched/getVehInfo.do?' + new Date().getTime(),
				method:"post",
				data:{hphm:hphm,cpzl:cpzl},
				success:function(data){
					if(data == null){
						$("input[name='fdjh']").val("");
						$("input[name='clsbdh']").val("");
						$("input[name='clpp']").val("");
						$("input[name='clxh']").val("");
						$("input[name='csys']").val("");
						$("#csys").val("");
						$("input[name='clsyr']").val("");
						$("input[name='syrlxdh']").val("");
						$("textarea[name='syrxxdz']").val("");
						layer.msg("没有查到该机动车信息");
					}else{
						checkIsEmpty(data.fdjh,"fdjh");
						checkIsEmpty(data.clsbdh,"clsbdh");
						checkIsEmpty(data.clpp1,"clpp");
						checkIsEmpty(data.clxh,"clxh");
						checkIsEmpty(data.csys,"csys");
						checkIsEmpty(data.jdcsyr,"clsyr");
						checkIsEmpty(data.lxfs,"syrlxdh");
						checkIsEmpty(data.djzzxz,"syrxxdz");
						layer.msg("机动车信息查询成功");
					}
				},
				error: function () {//请求失败处理函数
					$("input[name='fdjh']").val("");
					$("input[name='clsbdh']").val("");
					$("input[name='clpp']").val("");
					$("input[name='clxh']").val("");
					$("input[name='csys']").val("");
					$("#csys").val("");
					$("input[name='clsyr']").val("");
					$("input[name='syrlxdh']").val("");
					$("textarea[name='syrxxdz']").val("");
					layer.msg("没有查到该机动车信息");
				}
			});
		}
		function checkIsEmpty(val,id){
			if(val == null || val == ''){
				$("input[name='"+ id +"']").val("");
			}else{
				if(id == "syrxxdz"){
					$("textarea[name='syrxxdz']").val(val);
				}else{
					$("input[name='"+ id +"']").val(val);
					if(id == "csys"){
						$("#csys").val(val);
					}
				}
			}
		}
		
		function change110(){
			if($("input[name='cad']").val() == 1){
				$("#cphid").attr("readonly",true);
				$("#cphUI").hide();
				$("#btn_110").show();
// 				$("#div_hpzl").unbind("click");
				$("#bk_button").hide();
			}else{
				$("#cphid").attr("readonly",false);
				$("#cphUI").show();
				$("#btn_110").hide();
// 				$("#div_hpzl").click(function(){slider(this);});
				$("#bk_button").show();
			}
		}
		//打开选择110布控的页面
		function open110(){
			var url = "${pageContext.request.contextPath}/dispatched/dis110.do";
	    	layer.open({
	           type: 2,
	           title: '110布控选择窗口',
	           shadeClose: true,
	           shade: 0.8,
	           area: ['1000px', '600px'],
	           content: url //iframe的url
	        });
		}
		
		function returnDis(id, hphm, hpzl){
			$("#cphid").val(hphm);
			$("input[name='hpzl']").val(hpzl);
			$("#cadBkid").val(id);
			var hpzlmc = "";
			for(var i=0;i < dic.length;i++){
				if(dic[i].typeCode == "HPZL" && dic[i].typeSerialNo == hpzl){
					hpzlmc = dic[i].typeDesc;
				}
			}
			$("#hpzl").val(hpzlmc);
			findVeh();
		}
		
		function changeBkfwlx(){
			if($("input[name='bklb']").val() == "00"){
				$("#bkfw_div").show();
			} else{
				$("#bkfw_div").hide();
			}
			
			//只有涉案类可以直接布控
			if($("input[name='bkdl']").val() == "1"){
				$("#zjbk_button").show();
			 	$("#leader_div").show();
			 	//分局、市局警员申请时不用报备，否则会重复报备
			 	if('${user.position}' == "81" && $("input[name='bklb']").val() == "02"){
				 	$("#leader_div").hide();
				 	$("input[name='leader']").val("");
			 	} else if('${user.position}' == "91" && $("input[name='bklb']").val() != "02"){
				 	$("#leader_div").hide();
				 	$("input[name='leader']").val("");
			 	}
			} else{
				$("#zjbk_button").hide();
			 	$("#leader_div").hide();
			}
		}
</script>
</html>