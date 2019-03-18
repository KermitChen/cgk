<%@page import="org.springframework.ui.Model,java.util.Map,com.dyst.systemmanage.entities.*,com.dyst.utils.*"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	//获取用户信息
	User userObj = (User)request.getSession().getAttribute(StaticUtils.SESSION_NAME_USER_DATA);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>详细信息</title>
		<script type="text/javascript" src="<%=basePath%>common/js/jquery.js"></script>
		<script type="text/javascript" src="<%=basePath%>common/js/jquery.min.js"></script>
		<script type="text/javascript" src="<%=basePath%>common/js/layer/layer.js" ></script>
		<script type="text/javascript" src="<%=basePath%>common/js/sb/util.js"></script>
		<script type="text/javascript" src="<%=basePath%>common/js/xiala.js"></script>
		<script type="text/javascript" src="<%=basePath%>common/js/common.js"></script>
		<style type="text/css">
			.button_blue{
				padding: 6px 18px;
			    border: none;
			    color: #fff;
			    background: url("${pageContext.request.contextPath}/common/images/submit_b.jpg") center no-repeat;
			    border-radius: 6px;
			    outline: none;
			    cursor: pointer;
			    margin: 4px 10px;
			    border: 1px solid #fff;
			}
			.button_blue:hover{
				color: #555;
				background: url("${pageContext.request.contextPath}/common/images/submit_g.jpg") center no-repeat;
			}
			input{
				border-style:none;
				readonly:readonly;
			}
			span{
				font-size:11pt;
				font-family:"Microsoft YaHei";
			}
			td{
				align:left;
				font-size:11pt;
				font-family:"Microsoft YaHei";
			}
			th{
				align:center;
			}
			.button_div{
				display: block;
			    float: left;
			    height: 40px;
			    margin-top: 8px;
			    position: relative;
			    width: 100%;
			    text-align: center;
			}
		</style>
		<link rel="stylesheet" href="<%=basePath%>common/js/tab/kandytabs.css" type="text/css">
		<style type="text/css">
			#slide { padding:0; border:1px solid #DDD; overflow:hidden; }
			#slide .tabtitle { line-height:28px;background: #C2C2C2 }
			#slide .tabtitle .tabbtn { background:none; border-width:0 0 0 0px; cursor:pointer; width:67px; text-align:center; border-radius:0; margin:0 0 0 -1px }
			#slide .tabtitle .tabcur { background:#FFF; cursor:default; width:69px; }
			#slide .tabbody { border-width:1px 0 0 }
		</style>
		<link rel="stylesheet" href="<%=basePath%>common/css/style.css" type="text/css">
	</head>
	<body>
		<div class="content">
	    	<div class="content_wrap" style="width:1150px">
				<dl id="slide" class="kandyTabs">
	    			<dt><span class="tabbtn">预警信息</span></dt>
	    			<dd>
						<fieldset style="-moz-border-radius:8px;border:#D2691E 1px solid;">
							<legend style="color:#333; font-weight:bold; font-size: large; margin-left: 30px;">预警信息</legend>
							<table align="center" width="60%" border="0">
								<tr style="width: 0px;height: 0px;"></tr>
								<tr>
									<td>
										<div style="margin: 0px auto;width: 380px;" class="zoom-small-image" onclick="showTotalPic('${sbDto.tp1Url }');"><a href="${sbDto.tp1Url }" class='cloud-zoom' rel="position:'inside',showTitle:false,adjustX:-4,adjustY:-4"><img src="${sbDto.tp1Url }" height="270px" width="380px"/></a></div>
									</td>
									<td>
										<div style="margin: 0px auto;width: 380px;" class="zoom-small-image" onclick="showTotalPic('${sbDto.tp1Url }');"><a href="${sbDto.tp2Url }" class='cloud-zoom' rel="position:'inside',showTitle:false,adjustX:-4,adjustY:-4"><img src="${sbDto.tp2Url }" height="270px" width="380px"/></a></div>
									</td>
								</tr>
							</table>
							<div class="slider_body">
				                <div class="slider_selected_left">
				                	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;号牌号码：
				                </div>
				                <div class="slider_selected_right" style="">
				                    <div class="img_wrap">
				                        <div class="select_wrap select_input_null">
				                            <input id="hphm" name="hphm" type="text" class="slider_input" readonly="readonly" value="${ewRecieve.hphm }" style="background-color:#FFFFFF;"/>
				                        </div>
				                    </div>  
				                </div>
		        			</div>
		        			<div class="slider_body">
				                <div class="slider_selected_left">
				                	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;号牌种类：
				                </div>
				                <div class="slider_selected_right" style="">
				                    <div class="img_wrap">
				                        <div class="select_wrap select_input_null">
				                            <input id="cplx" name="cplx" type="text" class="slider_input" readonly="readonly" value="${ewRecieve.hpzl }"/>
				                        </div>
				                    </div>  
				                </div>
		        			</div>
		        			<div class="slider_body">
				                <div class="slider_selected_left">
				                	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;行驶车道：
				                </div>
				                <div class="slider_selected_right" style="">
				                    <div class="img_wrap">
				                        <div class="select_wrap select_input_null">
				                            <input id="cdid" name="cdid" type="text" class="slider_input" readonly="readonly" value="${ewRecieve.cdid }"/>
				                        </div>
				                    </div>  
				                </div>
		        			</div>
		        			<div class="slider_body">
				                <div class="slider_selected_left">
				                	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;通过时间：
				                </div>
				                <div class="slider_selected_right" style="">
				                    <div class="img_wrap">
				                        <div class="select_wrap select_input_null">
				                            <input id="sbsj" name="sbsj" type="text" class="slider_input" readonly="readonly" value="<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${ewRecieve.tgsj }"/>"/>
				                        </div>
				                    </div>  
				                </div>
		        			</div>
		        			<div class="slider_body">
				                <div class="slider_selected_left">
				                	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;上传时间：
				                </div>
				                <div class="slider_selected_right" style="">
				                    <div class="img_wrap">
				                        <div class="select_wrap select_input_null">
				                            <input id="scsj" name="scsj" type="text" class="slider_input" readonly="readonly" value="<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${ewRecieve.scsj }"/>"/>
				                        </div>
				                    </div>  
				                </div>
		        			</div>
		        			<div class="slider_body">
				                <div class="slider_selected_left">
				                	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;报警时间：
				                </div>
				                <div class="slider_selected_right" style="">
				                    <div class="img_wrap">
				                        <div class="select_wrap select_input_null">
				                            <input id="bjsj" name="bjsj" type="text" class="slider_input" readonly="readonly" value="<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${ewRecieve.bjsj }"/>"/>
				                        </div>
				                    </div>  
				                </div>
		        			</div>
		        			<div class="slider_body">
				                <div class="slider_selected_left">
				                	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;行驶速度：
				                </div>
				                <div class="slider_selected_right" style="">
				                    <div class="img_wrap">
				                        <div class="select_wrap select_input_null">
				                            <input id="sd" name="sd" type="text" class="slider_input" readonly="readonly" value="<c:if test="${ewRecieve.sd ne '0.0'}">${ewRecieve.sd }</c:if>"/>
				                        </div>
				                    </div>  
				                </div>
		        			</div>
		        			<div class="slider_body">
				                <div class="slider_selected_left">
				                	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;监测点：
				                </div>
				                <div class="slider_selected_right">
									<textarea id="jcdid" style="width: 580px;height: 20px;" readonly="readonly">${ewRecieve.jcdmc }</textarea>
								</div>
		        			</div>
						</fieldset>
					</dd>
		        	<dt><span class="tabbtn">布控信息</span></dt>
					<dd>
						<fieldset style="-moz-border-radius:8px;border:#D2691E 1px solid;">
							<legend style=" color:#333; font-weight:bold; font-size: large; margin-left: 30px;">车辆信息</legend>
							<div class="slider_body">
				                <div class="slider_selected_left">
				                	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;号牌号码：
				                </div>
				                <div class="slider_selected_right" style="">
				                    <div class="img_wrap">
				                        <div class="select_wrap select_input_null">
				                            <input id="hphm" name="hphm" type="text" class="slider_input" readonly="readonly" value="${ewRecieve.dispatched.hphm }"/>
				                        </div>
				                    </div>  
				                </div>
		        			</div>
					        <div class="slider_body">
					            <div class="slider_selected_left">
					                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;号牌种类：
					            </div>
					            <div class="slider_selected_right" style="">
				                    <div class="img_wrap">
				                        <div class="select_wrap select_input_null">
				                            <input id="hpzl" name="hpzl" type="text" class="slider_input" readonly="readonly" value="${ewRecieve.dispatched.hpzl }"/>
				                        </div>
				                    </div>  
				                </div>
					        </div>
					        <div class="slider_body">
					            <div class="slider_selected_left">
					            	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;发动机号：
					            </div>
					            <div class="slider_selected_right">
					                <div class="img_wrap">
					                   <div class="select_wrap select_input_null">
					                        <input id="fdjh" name="fdjh" type="text" class="slider_input" readonly="readonly" value="${ewRecieve.dispatched.fdjh }"/>
					                   </div>
					                </div>  
					            </div>
					        </div>
				        	<div class="slider_body">
				                <div class="slider_selected_left">
				                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;车架号：
				                </div>
				                <div class="slider_selected_right" style="">
				                    <div class="img_wrap">
				                        <div class="select_wrap select_input_null">
				                            <input id="clsbdh" name="clsbdh" type="text" class="slider_input" readonly="readonly" value="${ewRecieve.dispatched.clsbdh }"/>
				                        </div>
				                    </div>  
				                </div>
				        	</div>
		        			<div class="slider_body">
				                <div class="slider_selected_left">
				                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;中文品牌：
				                </div>
				                <div class="slider_selected_right">
				                    <div class="img_wrap">
				                        <div class="select_wrap select_input_null">
				                            <input id="clpp" name="clpp" type="text" class="slider_input" readonly="readonly" value="${ewRecieve.dispatched.clpp }"/>
				                        </div>
				                    </div>  
				                </div>
		        			</div>
			        		<div class="slider_body">
				                <div class="slider_selected_left">
				                	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;车辆型号：
				                </div>
				                <div class="slider_selected_right" style="">
				                    <div class="img_wrap">
				                        <div class="select_wrap select_input_null">
				                            <input id="clxh" name="clxh" type="text" class="slider_input" readonly="readonly" value="${ewRecieve.dispatched.clxh }"/>
				                        </div>
				                    </div>  
				                </div>
			        		</div>
					        <div class="slider_body">
					            <div class="slider_selected_left">
					                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;车身颜色：
					            </div>
					            <div class="slider_selected_right" style="">
				                    <div class="img_wrap">
				                        <div class="select_wrap select_input_null">
				                            <input id="csys" name="csys" type="text" class="slider_input" readonly="readonly" value="${ewRecieve.dispatched.csys }"/>
				                        </div>
				                    </div>  
				                </div>
					        </div>
				        	<div class="slider_body">
				                <div class="slider_selected_left">
				                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;车辆所有人：
				                </div>
				                <div class="slider_selected_right" style="">
				                    <div class="img_wrap">
				                        <div class="select_wrap select_input_null">
				                            <input id="clsyr" name="clsyr" type="text" class="slider_input" readonly="readonly" value="${ewRecieve.dispatched.clsyr }"/>
				                        </div>
				                    </div>  
				                </div>
				        	</div>
				        	<div class="slider_body">
				                <div class="slider_selected_left">
				                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;所有人电话：
				                </div>
				                <div class="slider_selected_right" >
				                    <div class="img_wrap">
				                        <div class="select_wrap select_input_null">
				                            <input id="syrlxdh" name="syrlxdh" type="text" class="slider_input" readonly="readonly" value="${ewRecieve.dispatched.syrlxdh }"/>
				                        </div>
				                    </div>  
				                </div>
				        	</div>
		        			<div class="slider_body_textarea" style="height:70px">
				                <div class="slider_selected_left">
				                	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;所有人地址：
				                </div>
				                <div class="slider_selected_right">
				                	<textarea id="syrxxdz" name="syrxxdz" rows="2" style="width:951px" readonly="readonly">${ewRecieve.dispatched.syrxxdz }</textarea>
				                </div>
		        			</div>
	        			</fieldset>
	        			<fieldset style="-moz-border-radius:8px;border:#D2691E 1px solid;">
							<legend style=" color:#333; font-weight:bold; font-size: large; margin-left: 30px;">布控申请信息</legend>
					        <div class="slider_body" style="position:relative;clear:both;">
					            <div class="slider_selected_left">
					            	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;布控大类：
					            </div>
					            <div class="slider_selected_right" >
				                    <div class="img_wrap">
				                        <div class="select_wrap select_input_null">
				                            <input id="bkdl" name="bkdl" type="text" class="slider_input" readonly="readonly" value="${ewRecieve.dispatched.bkdl }"/>
				                        </div>
				                    </div>  
				                </div>
					        </div>
					        <div class="slider_body">
					            <div class="slider_selected_left">
					                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;布控类别：
					            </div>
					            <div class="slider_selected_right" >
				                    <div class="img_wrap">
				                        <div class="select_wrap select_input_null">
				                            <input id="bklb" name="bklb" type="text" class="slider_input" readonly="readonly" value="${ewRecieve.dispatched.bklb }"/>
				                        </div>
				                    </div>  
				                </div>
					        </div>
					        <div class="slider_body">
					            <div class="slider_selected_left">
					                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;报警类型：
					            </div>
					            <div class="slider_selected_right" >
				                    <div class="img_wrap">
				                        <div class="select_wrap select_input_null">
				                            <input id="bjlx" name="bjlx" type="text" class="slider_input" readonly="readonly" value="${ewRecieve.dispatched.bjlx }"/>
				                        </div>
				                    </div>  
				                </div>
					        </div>
					        <div id="bkfw_div" class="slider_body" style="display:none">
								<div class="slider_selected_left">
								      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;布控范围：
								</div>
								<div class="slider_selected_right dropdown dropdown_all" id="dropdown_quanxuan">
								      <div class="input_select xiala">
								            <div class='multi_select'>
								            	<input type="hidden" name="bkfw" value="${ewRecieve.dispatched.bkfw}" />
												<a class="xiala_duoxuan_a"></a>
								            </div>
								      </div>
								</div>
							</div>
				        	<div class="slider_body_textarea" id="bknr" style="height:80px;">
				                <div class="slider_selected_left">
				                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;预案内容：
				                </div>
				                <div class="slider_selected_right">
				                	<textarea name="by2" id="by2" rows="3" style="width:951px" readonly="readonly">${ewRecieve.dispatched.by2 }</textarea>
				                </div>
				        	</div>
					        <div class="slider_body" >
								<div class="slider_selected_left">
								     &nbsp;布控起始时间：
								</div>
								<div class="slider_selected_right" >
				                    <div class="img_wrap">
				                        <div class="select_wrap select_input_null">
				                            <input id="bkqssj" name="bkqssj" type="text" class="slider_input" readonly="readonly" value="${ewRecieve.dispatched.bkqssj }"/>
				                        </div>
				                    </div>  
				                </div>
							</div>
							<div class="slider_body" >
								<div class="slider_selected_left">
								    &nbsp;布控终止时间：
								</div>
								<div class="slider_selected_right" >
				                    <div class="img_wrap">
				                        <div class="select_wrap select_input_null">
				                            <input id="bkjzsj" name="bkjzsj" type="text" class="slider_input" readonly="readonly" value="${ewRecieve.dispatched.bkjzsj }"/>
				                        </div>
				                    </div>  
				                </div>
							</div>
							<div class="slider_body">
				                <div class="slider_selected_left">
				                   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;联系电话：
				                </div>
				                <div class="slider_selected_right" >
				                    <div class="img_wrap">
				                        <div class="select_wrap select_input_null">
				                            <input id="bkjglxdh" name="bkjglxdh" type="text" class="slider_input" readonly="readonly" value="${ewRecieve.dispatched.bkjglxdh }"/>
				                        </div>
				                    </div>  
				                </div>
				        	</div>
					        <div class="slider_body" style="display:none">
					            <div class="slider_selected_left">
					               	 &nbsp;布控范围类型：
					            </div>
					            <div class="slider_selected_right" >
				                    <div class="img_wrap">
				                        <div class="select_wrap select_input_null">
				                            <input id="bkfwlx" name="bkfwlx" type="text" class="slider_input" readonly="readonly" value="${ewRecieve.dispatched.bkfwlx }"/>
				                        </div>
				                    </div>  
				                </div>
					        </div>
				        	<div class="slider_body">
				                <div class="slider_selected_left">
				                   	&nbsp;短信接收号码：
				                </div>
				                <div class="slider_selected_right">
				                    <div class="img_wrap">
				                        <div class="select_wrap select_input_null">
				                            <input id="dxjshm" name="dxjshm" type="text" class="slider_input" readonly="readonly" value="${ewRecieve.dispatched.dxjshm }"/>
				                        </div>
				                    </div>  
				                </div>
				        	</div>
				        	<div class="slider_body">
				                <div class="slider_selected_left">
				                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;涉枪涉爆：
				                </div>
				                <div class="slider_selected_right">
				                	<input type="radio" name="sqsb" id="sqsb" value="1" disabled/>                              
			                                                                                         是       &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			                        <input type="radio" name="sqsb" id="sqsb" value="0" disabled/>
			                         	      否
			                     </div>
				        	</div>
					        <div class="slider_body">
					            <div class="slider_selected_left">
					                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;布控性质：
					            </div>
					            <div class="slider_selected_right">
				                    <div class="img_wrap">
				                        <div class="select_wrap select_input_null">
				                            <input id="bkxz" name="bkxz" type="text" class="slider_input" readonly="readonly" value="${ewRecieve.dispatched.bkxz }"/>
				                        </div>
				                    </div>  
				                </div>
					        </div>
					        <div class="slider_body" style="display:none">
					            <div class="slider_selected_left">
					               &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;报警预案：
					            </div>
					            <div class="slider_selected_right">
				                    <div class="img_wrap">
				                        <div class="select_wrap select_input_null">
				                            <input id="bjya" name="bjya" type="text" class="slider_input" readonly="readonly" value="${ewRecieve.dispatched.bjya }"/>
				                        </div>
				                    </div>  
				                </div>
					        </div>
				        	<div class="slider_body">
				                <div class="slider_selected_left">
				                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;立案单位：
				                </div>
				                <div class="slider_selected_right" style="">
				                    <div class="img_wrap">
				                        <div class="select_wrap select_input_null">
				                            <input id="ladw" name="ladw" type="text" class="slider_input" readonly="readonly" value="${ewRecieve.dispatched.ladw }"/>
				                        </div>
				                    </div>  
				                </div>
				        	</div>
				        	<div class="slider_body">
				                <div class="slider_selected_left">
				                   	 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;立案联系人：
				                </div>
				                <div class="slider_selected_right">
				                    <div class="img_wrap">
				                        <div class="select_wrap select_input_null">
				                            <input id="lar" name="lar" type="text" class="slider_input" readonly="readonly" value="${ewRecieve.dispatched.lar }"/>
				                        </div>
				                    </div>  
				                </div>
				        	</div>
				        	<div class="slider_body">
				                <div class="slider_selected_left">
				                    	&nbsp;立案单位电话：
				                </div>
				                <div class="slider_selected_right" style="">
				                    <div class="img_wrap">
				                        <div class="select_wrap select_input_null">
				                            <input id="ladwlxdh" name="ladwlxdh" type="text" class="slider_input" readonly="readonly" value="${ewRecieve.dispatched.ladwlxdh }"/>
				                        </div>
				                    </div>  
				                </div>
				        	</div>
				        	<div class="slider_body">
				                <div class="slider_selected_left">
				                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;布控人警号：
				                </div>
				                <div class="slider_selected_right" style="">
				                    <div class="img_wrap">
				                        <div class="select_wrap select_input_null">
				                            <input id="bkrjh" name="bkrjh" type="text" class="slider_input" readonly="readonly" value="${ewRecieve.dispatched.bkrjh }"/>
				                        </div>
				                    </div>  
				                </div>
				        	</div>
				        	<div class="slider_body">
				                <div class="slider_selected_left">
				                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;布控人姓名：
				                </div>
				                <div class="slider_selected_right" style="">
				                    <div class="img_wrap">
				                        <div class="select_wrap select_input_null">
				                            <input id="bkr" name="bkr" type="text" class="slider_input" readonly="readonly" value="${ewRecieve.dispatched.bkr }"/>
				                        </div>
				                    </div>  
				                </div>
				        	</div>
				        	<div class="slider_body">
				                <div class="slider_selected_left">
				                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;布控单位：
				                </div>
				                <div class="slider_selected_right" style="">
				                    <div class="img_wrap">
				                        <div class="select_wrap select_input_null">
				                            <input id="bkjbmc" name="bkjbmc" type="text" class="slider_input" readonly="readonly" value="${ewRecieve.dispatched.bkjgmc }"/>
				                        </div>
				                    </div>  
				                </div>
				        	</div>
				        	<div class="slider_body_textarea">
				                <div class="slider_selected_left">
				                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;简要案情：
				                </div>
				                <div class="slider_selected_right">
				                	<textarea name="jyaq" id="jyaq" rows="3" style="width:951px" readonly="readonly">${ewRecieve.dispatched.jyaq }</textarea>
				                </div>
				        	</div>
	        			</fieldset>
					</dd>
					<c:if test="${!empty eWRecieveList }">
						<dt><span class="tabbtn">签收情况</span></dt>
						<dd>
							<fieldset style="-moz-border-radius:8px;border:#D2691E 1px solid;">
								<legend style="color:#333; font-weight:bold; font-size: large; margin-left: 30px;">预警签收情况</legend>
								<div class="pg_result">
									<table>
										<thead>
											<tr>
												<td width="5%" align="center">序号</td>
												<td width="15%" align="center">签收部门</td>
												<td width="10%" align="center">确认状态</td>
												<td width="10%" align="center">确认人</td>
												<td width="15%" align="center">确认单位</td>
												<td width="10%" align="center">联系电话</td>
												<td width="15%" align="center">确认时间</td>
												<td width="20%" align="center">确认结果简述</td>
											</tr>
										</thead>
										<tbody>
											<c:forEach var="ewr" items="${eWRecieveList }" varStatus="status">
												<tr <c:if test="${ewr.qrzt eq '0'}">style="color: red;"</c:if>>
													<td>${status.index+1}</td>
													<td>${ewr.bjbmmc}</td>
													<td>${ewr.qrztmc}</td>
													<td>${ewr.qrr}</td>
													<td>${ewr.qrdwdmmc }</td>
													<td>${ewr.qrdwlxdh }</td>
													<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${ewr.qrsj }"/></td>
													<td>${ewr.qrjg }</td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
								</div>
							</fieldset>
						</dd>
					</c:if>
				</dl>
				<div class="button_div">
					<input id="closeBt" type="button" class="button_blue" value="关闭">
				</div>
			</div>
	    </div>
	    <div class="mask"></div>
	</body>
	<script type="text/javascript" src="<%=basePath%>common/js/tab/kandytabs.pack.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/layer/layer.js"></script>
	<script type="text/javascript">
		$(function(){
			var dic = jQuery.parseJSON('${dicListJSON}');
			$("#slide").KandyTabs({
			    action:"slide",
			    trigger:"click"
			});
			
			if($("input[name='bklb']").val() == "00"){
				$("#bkfw_div").show();
			} else{
				$("#bkfw_div").hide();
			}
			$("input[name='sqsb'][value='${ewRecieve.dispatched.sqsb}']").attr("checked",true);
			//布控类别赋值
			var bklbList= jQuery.parseJSON('${bklbList}');
			for(var i=0;i < bklbList.length;i++){
				if(bklbList[i].ID == '${ewRecieve.dispatched.bklb }'){
					$("#bklb").val(bklbList[i].NAME);
				}
			}
			//报警类型赋值
			var bkdl = "";
			if('${ewRecieve.dispatched.bkdl }' == "1"){
				bkdl = "BKDL1";
			} else if('${ewRecieve.dispatched.bkdl }' == "2"){
				bkdl = "BKDL2";
			} else if('${ewRecieve.dispatched.bkdl }' == "3"){
				bkdl = "BKDL3";
			}
			for(var i=0;i < dic.length;i++){
				if(dic[i].typeCode == bkdl && dic[i].typeSerialNo == '${ewRecieve.dispatched.bjlx }'){
					$("#bjlx").val(dic[i].typeDesc);
				}
			}
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
				if(dic[i].typeCode == "HPZL" && '${ewRecieve.dispatched.hpzl }' == dic[i].typeSerialNo){
					$("#hpzl").val(dic[i].typeDesc);
					$("#cplx").val(dic[i].typeDesc);
				} else if(dic[i].typeCode == "CSYS" && '${ewRecieve.dispatched.csys }' == dic[i].typeSerialNo){
					$("#csys").val(dic[i].typeDesc);
				} else if(dic[i].typeCode == "BKDL" && '${ewRecieve.dispatched.bkdl }' == dic[i].typeSerialNo){
					$("#bkdl").val(dic[i].typeDesc);
				} else if(dic[i].typeCode == "BKJB" && '${ewRecieve.dispatched.bkjb }' == dic[i].typeSerialNo){
					$("#bkjb").val(dic[i].typeDesc);
				} else if(dic[i].typeCode == "BKFWLX" && '${ewRecieve.dispatched.bkfwlx }' == dic[i].typeSerialNo){
					$("#bkfwlx").val(dic[i].typeDesc);
				} else if(dic[i].typeCode == "BKXZ" && '${ewRecieve.dispatched.bkxz }' == dic[i].typeSerialNo){
					$("#bkxz").val(dic[i].typeDesc);
				} else if(dic[i].typeCode == "BJYA" && '${ewRecieve.dispatched.bjya }' == dic[i].typeSerialNo){
					$("#bjya").val(dic[i].typeDesc);
				}
			}
			
		    $('.multi_select').MSDL({
			  'value': value,
		      'data': data
		    });
		    
		    //布控范围勾选
		    var bkfwValue = '${ewRecieve.dispatched.bkfw}'.split(";"); 
		    $('.multi_select').children($('.container')).children($('.contents')).children().each(function(){
				for(var i in bkfwValue){
					if(bkfwValue[i] == $(this).children("input").val()){
						$(this).children("input").attr("checked","checked");
						$(this).children("input").trigger("change");
					}
				}
			});
			
			$("#closeBt").click(function() {
				var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
				parent.layer.close(index);
			});
		});
		
		//放大图片
		function showTotalPic(url){
		    //调用
			parent.layer.open({
				type: 2,
				title:false,
				area:['800px', '600px'],
				closeBtn:2,
				shadeClose:true,
				content: ["<%=basePath%>bdController/zoomImage.do?tpUrl=" + url, 'no']
			});
		}
	</script>
	<script type="text/javascript" src="<%=basePath%>common/js/sb/cloud-zoom.1.0.2.min.js"></script>
</html>