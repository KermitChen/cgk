<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html lang="zh-CN">
	<head>
		<base href="<%=basePath%>">
		<title>布控详情</title>
		<link rel="stylesheet" href="<%=basePath%>common/js/tab/kandytabs.css" type="text/css">
		<link rel="stylesheet" href="<%=basePath%>common/css/style.css" type="text/css">
		<style type="text/css">
			#slide { padding:0; border:1px solid #DDD; overflow:hidden; }
			#slide .tabtitle { line-height:28px;background: #C2C2C2 }
			#slide .tabtitle .tabbtn { background:none; border-width:0 0 0 0px; cursor:pointer; width:67px; text-align:center; border-radius:0; margin:0 0 0 -1px }
			#slide .tabtitle .tabcur { background:#FFF; cursor:default; width:69px; }
			#slide .tabbody { border-width:1px 0 0 }
		</style>
	</head>
	<body>
		<div class="content">
	    	<div class="content_wrap" style="width:1150px">
	    		<dl id="slide" class="kandyTabs">
	    			<dt><span class="tabbtn">布控详情</span></dt>
	    			<dd>
	    				<fieldset style="-moz-border-radius:8px;border:#D2691E 1px solid;">
							<legend style=" color:#333; font-weight:bold; font-size: large; margin-left: 30px;">车辆信息</legend>
				    		<div class="slider_body">
					                <div class="slider_selected_left">
					                    	号牌号码<span style="color: red">*</span>：
					                </div>
					                <div class="slider_selected_right" style="">
					                    <div class="img_wrap">
					                        <div class="select_wrap input_wrap_select">
					                            <input id="hphm" name="hphm" type="text" class="slider_input" maxlength="8" readonly="readonly" value="${dispatched.hphm }"/>
					                        </div>
					                    </div>  
					                </div>
					        </div>
					        <div class="slider_body">
					            <div class="slider_selected_left">
					                <span>号牌种类<span style="color: red">*</span>：</span>
					            </div>
					            <div class="slider_selected_right dropdown dropdowns" >
					                <input class="input_select xiala" id="hpzl" type="text" readonly="readonly" value="==请选择=="/>
					                <input type="hidden" name="hpzl" value="${dispatched.hpzl }" /> 
					            </div>
					        </div>
					        
					        <div class="slider_body">
					            <div class="slider_selected_left">
					                <span>发动机号：</span>
					            </div>
					            <div class="slider_selected_right">
					                <div class="img_wrap">
					                   <div class="select_wrap select_input_wrap">
					                        <input id="fdjh" name="fdjh" type="text" class="slider_input" maxlength="20" readonly="readonly" value="${dispatched.fdjh }"/>
					                        <a id="fdjh" class="empty"></a>
					                   </div>
					                </div>  
					            </div>
					        </div>
					        <div class="slider_body">
					                <div class="slider_selected_left">
					                    <span>车架号：</span>
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
					                    <span>中文品牌：</span>
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
					                    <span>车辆型号：</span>
					                </div>
					                <div class="slider_selected_right" style="">
					                    <div class="img_wrap">
					                        <div class="select_wrap select_input_wrap">
					                            <input id="CLXH" name="clxh" type="text" class="slider_input" maxlength="80" readonly="readonly" value="${dispatched.clxh }"/>
					                            <a id="CLXH" class="empty"></a>
					                        </div>
					                    </div>  
					                </div>
					        </div>
					        <div class="slider_body">
					            <div class="slider_selected_left">
					                <span>车身颜色：</span>
					            </div>
					            <div class="slider_selected_right dropdown dropdowns" onclick="slider(this)">
					                <input class="input_select xiala" id="csys" type="text" readonly="readonly" value="${dispatched.csys }"/>
					                <input type="hidden" name="csys" value="${dispatched.csys }" /> 
					            </div>
					        </div>
					        <div class="slider_body">
					                <div class="slider_selected_left">
					                    <span>车辆所有人：</span>
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
					                    <span>所有人电话：</span>
					                </div>
					                <div class="slider_selected_right" >
					                    <div class="img_wrap">
					                        <div class="select_wrap select_input_wrap">
					                            <input id="syrlxdh" name="syrlxdh" type="text" class="slider_input" maxlength="20" value="${dispatched.syrlxdh }"
					                            readonly="readonly"/>
					                            <a id="syrlxdh" class="empty"></a>
					                        </div>
					                    </div>  
					                </div>
					        </div>
					        <div class="slider_body_textarea" style="height:70px">
					                <div class="slider_selected_left">
					                    <span>所有人地址：</span>
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
					                <span>布控大类<span style="color: red">*</span>：</span>
					            </div>
					            <div class="slider_selected_right dropdown dropdowns" onclick="slider(this)">
					                <input class="input_select xiala" id="bkdl" type="text" readonly="readonly" value="==请选择=="/>
					                <input type="hidden" name="bkdl" value="${dispatched.bkdl }" /> 
					            </div>
					        </div>
					        <div class="slider_body">
					            <div class="slider_selected_left">
					                <span>布控类别<span style="color: red">*</span>：</span>
					            </div>
					            <div class="slider_selected_right dropdown dropdowns" onclick="slider(this)">
					                <input class="input_select xiala" id="bklb" type="text" readonly="readonly" value="==请选择=="/>
					                <input type="hidden" name="bklb" value="${dispatched.bklb }" /> 
					            </div>
					        </div>
					        <div class="slider_body">
					            <div class="slider_selected_left">
					                <span>报警类型<span style="color: red">*</span>：</span>
					            </div>
					            <div class="slider_selected_right dropdown dropdowns" onclick="slider(this)">
					                <input class="input_select xiala" id="bjlx" type="text" readonly="readonly" value="==请选择=="/>
					                <input type="hidden" id="bklbValue" name="bjlx" value="${dispatched.bjlx }" /> 
					            </div>
					        </div>
					        <div class="slider_body" style="display:none">
					            <div class="slider_selected_left">
					                <span>级别<span style="color: red">*</span>：</span>
					            </div>
					            <div class="slider_selected_right dropdown dropdowns" onclick="slider(this)">
					                <input class="input_select xiala" id="bkjb" type="text" readonly="readonly" value="==请选择=="/>
					                <input type="hidden" id="bkjbValue" name="bkjb" value="${dispatched.bkjb }" /> 
					            </div>
					        </div>
					        <div id="bkfw_div" class="slider_body" style="display:none">
								<div class="slider_selected_left">
								      <span>布控范围<span style="color: red">*</span>：</span>
								</div>
								<div class="slider_selected_right dropdown dropdown_all" id="dropdown_quanxuan">
								      <div class="input_select xiala">
								            <div class='multi_select'>
								            	<input type="hidden" name="bkfw" value="${dispatched.bkfw}" />
												<a class="xiala_duoxuan_a"></a>
								            </div>
								      </div>
								</div>
							</div>
					        <div class="slider_body_textarea" id="bknr" style="height:80px;">
					                <div class="slider_selected_left">
					                    <span>预案内容<span style="color: red">*</span>：</span>
					                </div>
					                <div class="slider_selected_right">
					                	<textarea name="by2" id="by2" rows="3" style="width:951px" readonly="readonly">${dispatched.by2 }</textarea>
					                </div>
					        </div>
					        <div class="slider_body" >
								<div class="slider_selected_left">
								     <span>布控起始时间<span style="color: red">*</span>：</span>
								</div>
								<div class="slider_selected_right">
								     <div class="demolist">
								         <input class="inline laydate-icon" name="bkqssj" value="${dispatched.bkqssj }" readonly="readonly" />
								     </div>
								</div>
							</div>
							<div class="slider_body" >
								<div class="slider_selected_left">
								     <span>布控终止时间<span style="color: red">*</span>：</span>
								</div>
								<div class="slider_selected_right">
								     <div class="demolist">
								         <input class="inline laydate-icon" name="bkjzsj" value="${dispatched.bkjzsj }" readonly="readonly" />
								     </div>
								</div>
							</div>
							<div class="slider_body">
					                <div class="slider_selected_left">
					                    <span>联系电话<span style="color: red">*</span>：</span>
					                </div>
					                <div class="slider_selected_right" style="">
					                    <div class="img_wrap">
					                        <div class="select_wrap select_input_wrap">
					                            <input id="bkjglxdh" name="bkjglxdh" type="text" class="slider_input" maxlength="12"
					                            readonly="readonly" value="${dispatched.bkjglxdh }"/>
					                            <a id="bkjglxdh" class="empty"></a>
					                        </div>
					                    </div>  
					                </div>
					        </div>
					        <div class="slider_body" style="display:none">
					            <div class="slider_selected_left">
					                <span>布控范围类型<span style="color: red">*</span>：</span>
					            </div>
					            <div class="slider_selected_right dropdown dropdowns" >
					                <input class="input_select xiala" id="bkfwlx" type="text" readonly="readonly" value="==请选择=="/>
					                <input type="hidden" name="bkfwlx" value="${dispatched.bkfwlx }" /> 
					            </div>
					        </div>
					        
					        <div class="slider_body">
					                <div class="slider_selected_left">
					                    <span>短信接收号码：</span>
					                </div>
					                <div class="slider_selected_right">
					                    <div class="img_wrap">
					                        <div class="select_wrap select_input_wrap">
					                            <input id="dxjshm" name="dxjshm" type="text" class="slider_input" 
					                            	readonly="readonly" value="${dispatched.dxjshm }"/>
					                            <a id="dxjshm" class="empty"></a>
					                        </div>
					                    </div>  
					                </div>
					        </div>
					        
					        <div class="slider_body">
					                <div class="slider_selected_left">
					                    <span>涉枪涉爆：</span>
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
					                <span>布控性质：</span>
					            </div>
					            <div class="slider_selected_right dropdown dropdowns">
					                <input class="input_select xiala" id="bkxz" type="text" readonly="readonly" value="公开"/>
					                <input type="hidden" name="bkxz" value="${dispatched.bkxz }" /> 
					            </div>
					        </div>
					        <div class="slider_body" style="display:none">
					            <div class="slider_selected_left">
					                <span>报警预案：</span>
					            </div>
					            <div class="slider_selected_right dropdown dropdowns" onclick="slider(this)">
					                <input class="input_select xiala" id="bjya" type="text" readonly="readonly" value="==请选择=="/>
					                <input type="hidden" name="bjya" value="${dispatched.bjya }" /> 
					            </div>
					        </div>
					        <div class="slider_body">
					                <div class="slider_selected_left">
					                    <span>立案单位：</span>
					                </div>
					                <div class="slider_selected_right" style="">
					                    <div class="img_wrap">
					                        <div class="select_wrap select_input_wrap">
					                            <input id="ladw" name="ladw" type="text" class="slider_input" maxlength="40" readonly="readonly" value="${dispatched.ladw }"/>
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
					                            <input id="lar" name="lar" type="text" class="slider_input" maxlength="10" readonly="readonly" value="${dispatched.lar }"/>
					                            <a id="lar" class="empty"></a>
					                        </div>
					                    </div>  
					                </div>
					        </div>
					        <div class="slider_body">
					                <div class="slider_selected_left">
					                    <span>立案单位电话：</span>
					                </div>
					                <div class="slider_selected_right" style="">
					                    <div class="img_wrap">
					                        <div class="select_wrap select_input_wrap">
					                            <input id="ladwlxdh" name="ladwlxdh" type="text" class="slider_input" maxlength="12" 
					                            	readonly="readonly" value="${dispatched.ladwlxdh }"/>
					                            <a id="ladwlxdh" class="empty"></a>
					                        </div>
					                    </div>  
					                </div>
					        </div>
					        <div class="slider_body">
					                <div class="slider_selected_left">
					                    <span>布控单位：</span>
					                </div>
					                <div class="slider_selected_right" style="">
					                    <div class="img_wrap">
					                        <div class="select_wrap select_input_wrap">
					                            <input id="bkjbmc" name="bkjbmc" type="text" class="slider_input" maxlength="60" readonly="readonly" value="${dispatched.bkjgmc }"/>
					                        </div>
					                    </div>  
					                </div>
					        </div>
					        <div class="slider_body_textarea">
					                <div class="slider_selected_left">
					                    <span>简要案情<span style="color: red">*</span>：</span>
					                </div>
					                <div class="slider_selected_right">
					                	<textarea name="jyaq" id="jyaq" rows="3" style="width:951px" readonly="readonly">${dispatched.jyaq }</textarea>
					                </div>
					        </div>
		        		</fieldset>
		        	</dd>
			        <dt><span class="tabbtn">审批记录</span></dt>
				    <dd>
				        <c:if test="${dispatched.zjbk eq '0' && !empty commentList }">
				        	<jsp:include page="/views/dispatched/HisRecord.jsp" />
				        </c:if>
				        <c:if test="${dispatched.zjbk eq '1' && !empty disReportList }">
				        	<jsp:include page="/views/dispatched/disReport.jsp" />
				        </c:if>
				    </dd>
			        <dt><span class="tabbtn">签收情况</span></dt>
				    <dd>
				        <c:if test="${!empty receiveList }">
				        	<jsp:include page="/views/dispatched/disReceive.jsp" />
				        </c:if>
				    </dd>
			        <dt><span class="tabbtn">撤控信息</span></dt>
				    <dd>
				        <c:if test="${!empty withdrawList }">
				        	<jsp:include page="/views/dispatched/withdrawRecord.jsp" />
				        </c:if>
				    </dd>
		        	<dt><span class="tabbtn">预警记录</span></dt>
				    <dd>
				        <c:if test="${!empty ewarningList }">
				        	<jsp:include page="/views/dispatched/ewarningRecord.jsp" />
				        </c:if>
				    </dd>
				    <dt><span class="tabbtn">拦截反馈</span></dt>
				    <dd>
				    	<div class="pg_result">
							<table>
								<thead>
									<tr>
										<td width="5%" align="center">序号</td>
										<td width="10%" align="center">反馈部门</td>
										<td width="10%" align="center">反馈人</td>
										<td width="5%" align="center">联系电话</td>
										<td width="10%" align="center">反馈时间</td>
										<td width="5%" align="center">处置结果</td>
										<td width="5%" align="center">是否拦截到</td>
										<td width="10%" align="center">未拦截到原因</td>
										<td width="10%" align="center">带队人警号</td>
										<td width="10%" align="center">协办人警号</td>
										<td width="5%" align="center">抓获人数</td>
										<td width="5%" align="center">破获案件数</td>
										<td width="10%" align="center">处置过程描述</td>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="inSign" items="${instructionSignList }" varStatus="status">
										<tr>
											<td>${status.index+1}</td>
											<td>${inSign.fkbmmc}</td>
											<td>${inSign.fkrmc}</td>
											<td>${inSign.fkrlxdh }</td>
											<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${inSign.fksj }"/></td>
											<td>
												<c:forEach items= "${dicList}" var="dic">
									            	<c:if test="${dic.typeCode eq 'CJCZJG' }">
										            	<c:if test="${dic.typeSerialNo eq inSign.czjg }">
										                	${dic.typeDesc }
										                </c:if>
									            	</c:if> 
												</c:forEach>
											</td>
											<td>
												<c:forEach items= "${dicList}" var="dic">
									            	<c:if test="${dic.typeCode eq 'SFLJ' }">
										            	<c:if test="${dic.typeSerialNo eq inSign.sflj }">
										                	${dic.typeDesc }
										            	</c:if>
									            	</c:if> 
												</c:forEach>
											</td>
											<td>
												<c:forEach items= "${dicList}" var="dic">
									            	<c:if test="${dic.typeCode eq 'WLJDYY' }">
										        		<c:if test="${dic.typeSerialNo eq inSign.wljdyy }">
										        			${dic.typeDesc }
										        		</c:if>
									           		</c:if> 
												</c:forEach>
											</td>
											<td>${inSign.ddr }</td>
											<td>${inSign.xbr }</td>
											<td>${inSign.zhrs }</td>
											<td>${inSign.phajs }</td>
											<td>${inSign.fknr }</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
				    </dd>
		        </dl>
		        <div class="clear_both">
		        	<button class="submit_b" onclick="closeLayer();">关闭</button>
		        </div>
	        </div>
	    </div>
	    <div class="mask"></div>
	</body>
	<script type="text/javascript" src="<%=basePath%>common/js/jquery.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/activiti/jquery-1.8.3.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/jquery.ztree.core-3.5.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/jquery.ztree.excheck-3.5.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/laydate-master/laydate.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/laydate-master/laydate.dev.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/activiti/plugins/blockui/jquery.blockUI.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/xiala.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/common.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/time/moment.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/tab/kandytabs.pack.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/layer/layer.js"></script>
	<script>
		$(function(){
			var dic = jQuery.parseJSON('${dicListJSON}');
			if($("input[name='bklb']").val() == "00"){
					$("#bkfw_div").show();
				}else{
					$("#bkfw_div").hide();
				}
			$("#slide").KandyTabs({
			    action:"slide",
			    trigger:"click"
			});
			$("input[name='sqsb'][value='${dispatched.sqsb}']").attr("checked",true);
			//布控类别赋值
			var bklbList= jQuery.parseJSON('${bklbList}');
			for(var i=0;i< bklbList.length;i++){
				if(bklbList[i].ID == '${dispatched.bklb }'){
					$("#bklb").val(bklbList[i].NAME);
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
					$("#bjlx").val(dic[i].typeDesc);
				}
			}
			//布控范围多选框初始化
			var value = []; 
			var data = []; 
			for(var i=0;i<dic.length;i++){
				//布控范围初始化
				if(dic[i].typeCode == "BKFW"){
					value.push(dic[i].typeSerialNo);
					data.push(dic[i].typeDesc);
				}
				//下拉框赋值
				if(dic[i].typeCode == "HPZL" && '${dispatched.hpzl }' == dic[i].typeSerialNo){
					$("#hpzl").val(dic[i].typeDesc);
				}else if(dic[i].typeCode == "CSYS" && '${dispatched.csys }' == dic[i].typeSerialNo){
					$("#csys").val(dic[i].typeDesc);
				}else if(dic[i].typeCode == "BKDL" && '${dispatched.bkdl }' == dic[i].typeSerialNo){
					$("#bkdl").val(dic[i].typeDesc);
				}else if(dic[i].typeCode == "BKJB" && '${dispatched.bkjb }' == dic[i].typeSerialNo){
					$("#bkjb").val(dic[i].typeDesc);
				}else if(dic[i].typeCode == "BKFWLX" && '${dispatched.bkfwlx }' == dic[i].typeSerialNo){
					$("#bkfwlx").val(dic[i].typeDesc);
				}else if(dic[i].typeCode == "BKXZ" && '${dispatched.bkxz }' == dic[i].typeSerialNo){
					$("#bkxz").val(dic[i].typeDesc);
				}else if(dic[i].typeCode == "BJYA" && '${dispatched.bjya }' == dic[i].typeSerialNo){
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
		});
	
		function closeLayer() {
			//获取窗口索引关闭窗口
			var index = parent.layer.getFrameIndex(window.name);
			parent.layer.close(index);
		}
	</script>
</html>