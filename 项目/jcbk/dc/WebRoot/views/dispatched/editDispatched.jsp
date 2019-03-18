<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html lang="zh-CN">
	<head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
		<base href="<%=basePath%>">
		<title>布控申请调整</title>
		<link rel="stylesheet" href="<%=basePath%>common/css/style.css" type="text/css">
	</head>
	<body>
		<jsp:include page="/common/Head.jsp" />
		<div id="divTitle">
			<span id="spanTitle">布控申请调整</span>
		</div>
		<div class="content">
	    	<div class="content_wrap">
	    		<form name="form" action="" method="post">
		    		<input type="hidden" name="bkid" value="${dispatched.bkid }" /> 
		    		<input type="hidden" name="bksj" value="" /> 
		    		<input type="hidden" name="bkr" value="${dispatched.bkr }" /> 
		    		<input type="hidden" name="bkrjh" value="${dispatched.bkrjh }" /> 
		    		<input type="hidden" name="bkjg" value="${dispatched.bkjg }" /> 
		    		<input type="hidden" name="bkjgmc" value="${dispatched.bkjgmc }" /> 
		    		<input type="hidden" name="ywzt" value="${dispatched.ywzt }" /> 
		    		<input type="hidden" name="by3" value="${dispatched.by3 }" /> 
		    		<input type="hidden" name="by4" value="${dispatched.by4 }" /> 
		    		<input type="hidden" name="by5" value="${dispatched.by5 }" /> 
		    		<input type="hidden" name="tznr" value="${dispatched.tznr }" /> 
		    		<input type="hidden" name="tzdw" value="${dispatched.tzdw }" /> 
		    		<input type="hidden" name="pcsdh" value="${dispatched.pcsdh }" /> 
		    		<input type="hidden" name="fjdh" value="${dispatched.fjdh }" /> 
		    		<input type="hidden" name="sjdh" value="${dispatched.sjdh }" /> 
		    		<input type="hidden" name="bklbjb" value="${dispatched.bklbjb }" />
		    		<input type="hidden" name="xxly" value="${dispatched.xxly }" />
		    		<input type="hidden" name="zjbk" value="${dispatched.zjbk }" />
		    		<input type="hidden" name="fjbh" value="${dispatched.fjbh }" />
		    		<input type="hidden" name="fjsn" value="${dispatched.fjsn }" />
		    		<input type="hidden" name="lskhbm" value="${dispatched.lskhbm }" />
		    		<input type="hidden" name="lskhbmmc" value="${dispatched.lskhbmmc }" />
	    			<fieldset style="-moz-border-radius:8px;border:#D2691E 1px solid;">
						<legend style=" color:#333; font-weight:bold; font-size: large; margin-left: 30px;">车辆信息</legend>
		    			<div class="slider_body">
			                <div class="slider_selected_left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: red">*</span>号牌号码：
			                </div>
			                <div class="slider_selected_right" style="">
			                    <div class="img_wrap">
			                        <div class="select_wrap input_wrap_select">
			                            <input id="cphid" name="hphm" type="text" class="slider_input" maxlength="8" readonly="readonly" value="${dispatched.hphm }"/>
			                            <a class="empty" href="javascript:doCplrUI()"></a>
			                        </div>
			                    </div>
			                </div>
			        	</div>
				        <div class="slider_body">
				            <div class="slider_selected_left">
				                <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: red">*</span>号牌种类：</span>
				            </div>
				            <div class="slider_selected_right dropdown dropdowns" onclick="slider(this)">
				                <input class="input_select xiala" id="hpzl" type="text" readonly="readonly" value="==请选择=="/>
				                <input type="hidden" name="hpzl" value="${dispatched.hpzl }" /> 
				                <div class="ul">
								    <c:forEach items= "${dicList}" var="dic">
				            	  		<c:if test="${dic.typeCode eq 'HPZL' }">
					                    	<div class="li" data-value="${dic.typeSerialNo}" onclick="sliders(this)">${dic.typeDesc}</div>
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
				                   <div class="select_wrap select_input_wrap">
				                        <input id="fdjh" name="fdjh" type="text" class="slider_input" maxlength="20" readonly="readonly" value="${dispatched.fdjh }">
				                        <a id="fdjh" class="empty"></a>
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
			                        <div class="select_wrap select_input_wrap">
			                            <input id="clsbdh" name="clsbdh" type="text" class="slider_input" maxlength="20" readonly="readonly" value="${dispatched.clsbdh }"/>
			                            <a id="clsbdh" class="empty"></a>
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
			                        <div class="select_wrap select_input_wrap">
			                            <input id="clpp" name="clpp" type="text" class="slider_input" maxlength="" readonly="readonly" value="${dispatched.clpp }"/>
			                            <a id="clpp" class="empty"></a>
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
			                        <div class="select_wrap select_input_wrap">
			                            <input id="clxh" name="clxh" type="text" class="slider_input" maxlength="80" readonly="readonly" value="${dispatched.clxh }"/>
			                            <a id="clxh" class="empty"></a>
			                        </div>
			                    </div>  
			                </div>
			        	</div>
				        <div class="slider_body">
				            <div class="slider_selected_left">
				                <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: red;visibility: hidden;">*</span>车身颜色：</span>
				            </div>
				            <div class="slider_selected_right dropdown dropdowns" onclick="slider(this)">
				                <input class="input_select xiala" id="csys" type="text" readonly="readonly" value=""/>
				                <input type="hidden" name="csys" value="${dispatched.csys }" /> 
				                <div class="ul">
								    <div class="li" data-value="" onclick="sliders(this)">==请选择==</div>
								    <c:forEach items= "${dicList}" var="dic">
				            	  		<c:if test="${dic.typeCode eq 'CSYS' }">
					                    	<div class="li" data-value="${dic.typeSerialNo}" onclick="sliders(this)">${dic.typeDesc}</div>
				            	  		</c:if> 
								 	</c:forEach>
								</div>
				            </div>
				        </div>
			        	<div class="slider_body">
			                <div class="slider_selected_left">
			                    <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: red;visibility: hidden;">*</span>车辆所有人：</span>
			                </div>
			                <div class="slider_selected_right" style="">
			                    <div class="img_wrap">
			                        <div class="select_wrap select_input_wrap">
			                            <input id="clsyr" name="clsyr" type="text" class="slider_input" maxlength="80" readonly="readonly" value="${dispatched.clsyr }"/>
			                            <a id="clsyr" class="empty"></a>
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
			                        <div class="select_wrap select_input_wrap">
			                            <input id="syrlxdh" name="syrlxdh" type="text" class="slider_input" maxlength="20" readonly="readonly" value="${dispatched.syrlxdh }"
			                            onkeyup="(this.v=function(){this.value=this.value.replace(/[^0-9-]+/,'');}).call(this)" onblur="this.v();"/>
			                            <a id="syrlxdh" class="empty"></a>
			                        </div>
			                    </div>  
			                </div>
			        	</div>
			        	<div class="slider_body_textarea" style="height:70px">
			                <div class="slider_selected_left">
			                    <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: red;visibility: hidden;">*</span>所有人地址：</span>
			                </div>
			                <div class="slider_selected_right">
			                	<textarea id="syrxxdz" name="syrxxdz" rows="2" style="width:951px" readonly="readonly">${dispatched.syrxxdz }</textarea>
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
				                <input class="input_select xiala" id="bkdl" type="text" readonly="readonly" value="==请选择=="/>
				                <input type="hidden" name="bkdl" value="${dispatched.bkdl }" /> 
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
				                <input type="hidden" name="bklb" value="${dispatched.bklb }" /> 
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
				                <input type="hidden" id="bjlxValue" name="bjlx" value="${dispatched.bjlx }"/> 
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
				                <input type="hidden" id="bkjbValue" name="bkjb" value="${dispatched.bkjb }" /> 
				                <div class="ul">
								    <c:forEach items= "${dicList}" var="dic">
				            	  		<c:if test="${dic.typeCode eq 'BKJB' }">
					                    	<div class="li" data-value="${dic.typeSerialNo}" onclick="sliders(this);isOther()">${dic.typeDesc}</div>
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
			                	<textarea name="by2" id="by2" rows="3" style="width:951px" placeholder="请先选择'布控大类'和'预案级别'" >${dispatched.by2 }</textarea>
			                </div>
			        	</div>
				        <div class="slider_body" >
							<div class="slider_selected_left">
							     <span>&nbsp;&nbsp;<span style="color: red">*</span>布控起始时间：</span>
							</div>
							<div class="slider_selected_right">
							     <div class="demolist">
							     	 <input class="inline laydate-icon" name="bkqssj" readonly="readonly" onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})"/>
							     </div>
							</div>
						</div>
						<div class="slider_body" >
							<div class="slider_selected_left">
							     <span>&nbsp;&nbsp;<span style="color: red">*</span>布控终止时间：</span>
							</div>
							<div class="slider_selected_right">
							     <div class="demolist">
							         <input class="inline laydate-icon" name="bkjzsj" readonly="readonly" onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})"/>
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
			                            <input id="bkjglxdh" name="bkjglxdh" type="text" class="slider_input" maxlength="12" value="${dispatched.bkjglxdh }"
			                            onkeyup="(this.v=function(){this.value=this.value.replace(/[^0-9-]+/,'');}).call(this)" onblur="this.v();"/>
			                            <a id="bkjglxdh" class="empty"></a>
			                        </div>
			                    </div>  
			                </div>
			        	</div>
			        	<div class="slider_body" style="display:none">
				            <div class="slider_selected_left">
				                <span><span style="color: red">*</span>布控范围类型：</span>
				            </div>
				            <div class="slider_selected_right dropdown dropdowns" onclick="slider(this)">
				                <input class="input_select xiala" id="bkfwlx" type="text" readonly="readonly" value="==请选择=="/>
				                <input type="hidden" name="bkfwlx" value="${dispatched.bkfwlx }" /> 
				                <div class="ul">
								    <c:forEach items= "${dicList}" var="dic">
				            	  		<c:if test="${dic.typeCode eq 'BKFWLX' }">
					                    	<div class="li" data-value="${dic.typeSerialNo}" onclick="sliders(this)">${dic.typeDesc}</div>
				            	  		</c:if> 
								 	</c:forEach>
								</div>
				            </div>
				        </div>
			        	<div class="slider_body">
			                <div class="slider_selected_left">
			                    <span>&nbsp;&nbsp;&nbsp;<span style="color: red;visibility: hidden;">*</span>短信接收号码：</span>
			                </div>
			                <div class="slider_selected_right">
			                    <div class="img_wrap">
			                        <div class="select_wrap select_input_wrap">
			                            <input id="dxjshm" name="dxjshm" type="text" class="slider_input" placeholder="多个号码以逗号隔开" value="${dispatched.dxjshm }" maxlength="256" onkeyup="(this.v=function(){this.value=this.value.replace(/[^0-9,]+/,'');}).call(this)" onblur="this.v();"/>
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
			                	<input type="radio" name="sqsb" id="sqsb" value="1"/>是&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		                        <input type="radio" name="sqsb" id="sqsb" value="0"/>否
		                     </div>
			        	</div>
				        <div class="slider_body">
				            <div class="slider_selected_left">
				                <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: red;visibility: hidden;">*</span>布控性质：</span>
				            </div>
				            <div class="slider_selected_right dropdown dropdowns" onclick="slider(this)">
				                <input class="input_select xiala" id="bkxz" type="text" readonly="readonly" value="公开"/>
				                <input type="hidden" name="bkxz" value="${dispatched.bkxz }" /> 
				                <div class="ul">
								    <c:forEach items= "${dicList}" var="dic">
				            	  		<c:if test="${dic.typeCode eq 'BKXZ' }">
					                    	<div class="li" data-value="${dic.typeSerialNo}" onclick="sliders(this)">${dic.typeDesc}</div>
				            	  		</c:if> 
								 	</c:forEach>
								</div>
				            </div>
				        </div>
				        <div class="slider_body" style="display:none">
				            <div class="slider_selected_left">
				                <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: red;visibility: hidden;">*</span>报警预案：</span>
				            </div>
				            <div class="slider_selected_right dropdown dropdowns" onclick="slider(this)">
				                <input class="input_select xiala" id="bjya" type="text" readonly="readonly" value="==请选择=="/>
				                <input type="hidden" name="bjya" value="${dispatched.bjya }" /> 
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
			                            <input id="ladw" name="ladw" type="text" class="slider_input" maxlength="40" value="${dispatched.ladw }"/>
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
			                            <input id="lar" name="lar" type="text" class="slider_input" maxlength="10" value="${dispatched.lar }"/>
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
			                            <input id="ladwlxdh" name="ladwlxdh" type="text" class="slider_input" maxlength="12" value="${dispatched.ladwlxdh }"
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
			                	<textarea name="jyaq" id="jyaq" rows="3" style="width:951px">${dispatched.jyaq }</textarea>
			                </div>
			        	</div>
	        		</fieldset>
	        	</form>
		        <c:if test="${!empty commentList }">
		        	<jsp:include page="/views/workflow/HisComment.jsp" />
		        </c:if>
		        <div class="clear_both">
					<button class="submit_b" onclick="agree(true)">重新提交</button>
					<button class="submit_b" onclick="agree(false)">取消申请</button>
		        	<button class="submit_b" onclick="toClose()">返回</button>
		        </div>
        	</div>
    	</div>
		<jsp:include page="/common/Foot.jsp" />
	</body>
<!-- 	<script type="text/javascript" src="<%=basePath%>common/js/activiti/jquery-1.8.3.js"></script> -->
	<script type="text/javascript" src="<%=basePath%>common/js/activiti/plugins/jui/jquery-ui-1.9.2.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/activiti/plugins/qtip/jquery.qtip.pack.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/activiti/plugins/html/jquery.outerhtml.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/activiti/plugins/blockui/jquery.blockUI.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/activiti/workflow.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/activiti/dispatched-todo.js"></script>
<script>
	var dic = jQuery.parseJSON('${dicListJSON}');
	var zw = '${user.position}'.substring(0,2);//职位前2位
	var zwNum = parseInt('${user.position}'.length);//职位长度
	var zw2 = "";
	if(zwNum > 2){
		zw2 = '${user.position}'.substring(2,3);//职位后1位
	}
		
	$(function(){
		changeBkfwlx();
		//布控范围多选框初始化
		var value = []; 
		var data = []; 
		for(var i=0;i < dic.length;i++){
			//布控范围初始化
			if(dic[i].typeCode == "BKFW"){
				value.push(dic[i].typeSerialNo);
				data.push(dic[i].typeDesc);
			}
			//下拉框赋值
			if(dic[i].typeCode == "HPZL" && '${dispatched.hpzl }' == dic[i].typeSerialNo){
				$("#hpzl").val(dic[i].typeDesc);
			} else if(dic[i].typeCode == "CSYS" && '${dispatched.csys }' == dic[i].typeSerialNo){
				$("#csys").val(dic[i].typeDesc);
			} else if(dic[i].typeCode == "BKDL" && '${dispatched.bkdl }' == dic[i].typeSerialNo){
				$("#bkdl").val(dic[i].typeDesc);
				selectBklb('${dispatched.bkdl }');
				selectBjlx('${dispatched.bkdl }');
			} else if(dic[i].typeCode == "BKJB" && '${dispatched.bkjb }' == dic[i].typeSerialNo){
				$("#bkjb").val(dic[i].typeDesc);
			} else if(dic[i].typeCode == "BKFWLX" && '${dispatched.bkfwlx }' == dic[i].typeSerialNo){
				$("#bkfwlx").val(dic[i].typeDesc);
			} else if(dic[i].typeCode == "BKXZ" && '${dispatched.bkxz }' == dic[i].typeSerialNo){
				$("#bkxz").val(dic[i].typeDesc);
			} else if(dic[i].typeCode == "BJYA" && '${dispatched.bjya }' == dic[i].typeSerialNo){
				$("#bjya").val(dic[i].typeDesc);
			}
		}
	    $('.multi_select').MSDL({
		  'value': value,
	      'data': data
	    });
	    //布控范围勾选
	    var bkfwValue = '${dispatched.bkfw}'.split(";"); 
	    $('.multi_select').children($('.container')).children($('.contents')).children().each(function(){
			for(var i in bkfwValue){
				if(bkfwValue[i] == $(this).children("input").val()){
					$(this).children("input").attr("checked","checked");
					$(this).children("input").trigger("change");
				}
			}
		});
	    
		//Radio选择
		$("input[name='sqsb'][value='${dispatched.sqsb}']").attr("checked",true);
		var bklbList= jQuery.parseJSON('${bklbList}');
		for(var i=0;i < bklbList.length;i++){
			if(bklbList[i].ID == '${dispatched.bklb }') {
				$("input[name='bklb']").val('${dispatched.bklb }');
				$("#bklb").val(bklbList[i].NAME);
			}
		}
		
		var bkdl = "";
		if('${dispatched.bkdl }' == "1"){
			bkdl = "BKDL1";
		} else if('${dispatched.bkdl }' == "2"){
			bkdl = "BKDL2";
		} else if('${dispatched.bkdl }' == "3"){
			bkdl = "BKDL3";
		}
		for(var i=0;i < dic.length;i++){
			if(dic[i].typeCode == bkdl && dic[i].typeSerialNo == '${dispatched.bjlx }'){
				$("#bjlx").val(dic[i].typeDesc);
			}
		}
		//设置日期
		$("input[name='bksj']").val('${dispatched.bksj}'.substr(0,19));
		$("input[name='bkqssj']").val('${dispatched.bkqssj}'.substr(0,19));
		$("input[name='bkjzsj']").val('${dispatched.bkjzsj}'.substr(0,19));
	});
	//取消..按钮
	function toClose() {
		location.replace("<%=basePath%>dispatched/"+'${conPath}'+".do");
	}
	//同意+不同意.按钮
	function agree(value) {
		if(!value){
			if(!confirm("确认要取消申请吗？")){
				return;
			}
		}
		if(validate()){
			$.blockUI({
		        message: '<h2><img src="<%=basePath%>common/images/ajax/loading.gif" align="absmiddle"/>正在提交请求……</h2>'
		    });
// 		    alert("任务完成");
			document.forms[0].action = "dispatched/editComplete.do?taskId="+'${task.id }'+"&taskProInstId="+'${task.processInstanceId }'+"&key="+'${passKey }'+"&value="+value+"&conPath="+"dispatched/"+'${conPath}'+".do";
// 			document.forms[0].action = "dispatched/editComplete.do?";
			document.forms[0].submit();
		}
// 		complete('${task.id }','${task.processInstanceId }','${passKey }',true,"",'${conPath }');
	}

	//查询布控类别
	function selectBklb(value) {
		var dicBklb= jQuery.parseJSON('${bklbList}');
		$("#bklbUl").empty();
		$("#bklb").val("==请选择==");
		$("input[name='bklb']").val("");
		for(var i=0;i < dicBklb.length;i++){
			if(dicBklb[i].SUPERID == value){
				if(value == "1" && (zw < '70' || zw > '90') && dicBklb[i].ID == "02"){//市局和支队没有分局布控
				} else if(value == "3" && zwNum > 2 ){//管控类
					if(zw2 == "1" && dicBklb[i].ID != "03"){
					} else if(zw2 == "2" && dicBklb[i].ID != "04"){
					} else if(zw2 == "3" && dicBklb[i].ID != "05"){
					} else if(zw2 == "4" && dicBklb[i].ID != "06"){
					} else if(zw2 == "5" && dicBklb[i].ID != "07"){
					} else if(zw2 == "6" && dicBklb[i].ID != "08"){
					} else if(zw2 == "7" && dicBklb[i].ID != "09"){
					} else{
						$("#bklbUl").append('<div class="li" data-value='+dicBklb[i].ID+' onclick="sliders(this);changeBkfwlx();isOther();">'+dicBklb[i].NAME+'</div>');
					}
				} else{
					$("#bklbUl").append('<div class="li" data-value='+dicBklb[i].ID+' onclick="sliders(this);changeBkfwlx();isOther();">'+dicBklb[i].NAME+'</div>');
				}
			}
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
				$("#bjlxUl").append('<div class="li" data-value=' + dic[i].typeSerialNo + ' onclick="sliders(this);isOther();">' + dic[i].typeDesc + '</div>');
			}
		}
	}
	
	function validate() {
		if($("input[name='dxjshm']").val() != ""){
			var dxjshm = $("input[name='dxjshm']").val();
			var phone = dxjshm.split(",");
			for(var single in phone){
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
			return false;
		}else if($("input[name='bkjzsj']").val().trim() == ""){
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
		}else if($("input[name='bkfwlx']").val().trim() == ""){
			alert("请选择布控范围类型");
			$("input[name='bkfwlx']").focus();
			return false;
		}else if($("input[name='bkqssj']").val().trim() == ""){
			alert("请选择布控范围");
			$("input[name='bkqssj']").focus();
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
		}else{
			return true;
		}
	}
	//其他..按钮
	function isOther() {
		if($("#bkjbValue").val() == "99"){
			$("textarea[name='by2']").val("");
			return;
		}
		if($("#bkjbValue").val() == "" || $("#bkdlValue").val() == ""){
			return;
		}
	    $.ajax({
		    url: '${pageContext.request.contextPath}/dispatched/selectYACS.do',
		    dataType: "json",  
		    async: true, //请求是否异步，默认为异步，这也是ajax重要特性
		    data: { "bkdl": $("#bkdlValue").val(),
		    		"yajb": $("#bkjbValue").val()},    //参数值
		    type: "POST",   //请求方式
		    success: function(data) {
		        $("textarea[name='by2']").val(data);
		    }
		});
	}
	function changeBkfwlx(){
		if($("input[name='bklb']").val() == "00"){
			$("#bkfw_div").show();
		} else{
			$("#bkfw_div").hide();
		}
	}
</script>
</html>