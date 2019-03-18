<%@ page language="java" import="java.util.*,com.dyst.base.utils.*,com.dyst.utils.*,com.dyst.systemmanage.entities.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort() + path;
	String filePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort() + "/" + Config.getInstance().getSysAnnPath() + "/";
	//String filePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort() + "/" + "D:/upload" + "/";
	
	PageResult pageResult = (PageResult)request.getAttribute("pageResult");
	if(pageResult == null){
%>
		<SCRIPT type="text/javascript">
			window.location = '<%=basePath%>/common/errorPage/error500.jsp';
		</SCRIPT>
<%
	} 
	//else if(pageResult != null && pageResult.getItems().size() <= 0){
	//	<SCRIPT type="text/javascript">
	//		alert("没有找到符合条件的数据！");
	//	</SCRIPT>
	//}
%>
<style>
	#table1 tbody td{
		width: auto;
	}
</style>
	<%
		User user = (User)request.getSession().getAttribute(StaticUtils.SESSION_NAME_USER_DATA);
	 %>
	<script type="text/javascript" src="<%=basePath%>/common/js/jquery.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>/common/js/layer/layer.js"></script>
	<script type="text/javascript">
		$(function(){
			var hasReadIds = $('a[class="hasReadFlag"]');
			//alert(hasReadIds.length);
			hasReadIds.each(function(){
				//alert($(this).attr('id'));
				if(!CalItemIsNotHasRead($(this).attr('id'))){
					$(this).text("标为已读");
				} else{
				
				}
			});
		});
		
		//请求该用户已读的文件ids
		var hasReadIds = new Array();
		var tmp = "<%=(user!=null?user.getAnnids():"") %>";
		//判断条目是否已读，未读
		function CalItemIsNotHasRead(id){
			hasReadIds = tmp.split(',');
			//alert(hasReadIds);
			if(hasReadIds.length>=1){
				var flag = false;
				for(var i = 0;i < hasReadIds.length;i++){
					if(hasReadIds[i] == id){
						flag = true;
					}
				}
				return flag;
			}else{
				return false;
			}
		}
		
		//把该文件标为已读
		function markFileHasRead(id){
			$.ajax({
				url:"${pageContext.request.contextPath}/sysAnn/markFileHasRead.do",
				data:{id:id},
				type:'POST',
				dataType:'text',
				async:false,
				success:function(data){
					if(data == '1'){
						parent.layer.msg('标识成功！');
						$("#searchBt").click();
					} else if(data=='0'){
						parent.layer.msg('标识失败！');
					}
				}
			});
		}
	</script>
	<table id="table1">
		<thead>
			<tr>
				<td>序号</td>
				<td>文档名称</td>
				<td>公告类型</td>
				<td>公告概要</td>
				<td>发布人用户名</td>
				<td>发布人姓名</td>
				<td>发布时间</td>
				<td>操作</td>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="data" items="${pageResult.items }" varStatus="status">
			<tr ondblclick="javascript:doDetail(${data.id})">
				<td>${status.index+1}</td>
				<td>${data.fileName }</td>
				<td>
					<c:forEach items="${annTypeList }" var="a">
						<c:if test="${a.typeSerialNo eq data.annType }">
							${a.typeDesc }
						</c:if>
					</c:forEach>
				</td>
				<td>${data.outline }</td>
				<td>${data.buildPno }</td>
				<td>${data.buildName }</td>
				<td><fmt:formatDate value="${data.buildTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td>
					<a href="javascript:doDetail(${data.id})" >详情</a>
					|&nbsp;<a href="<%=filePath %>${data.fileUrl}">下载</a>
					|&nbsp;<a id="${data.id }" class="hasReadFlag" href="javascript:markFileHasRead(${data.id })"></a>
				</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>            
	<jsp:include page="/common/pageNavigator.jsp"></jsp:include>
