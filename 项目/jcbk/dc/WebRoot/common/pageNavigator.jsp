<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
			<c:if test="${pageResult.totalCount< 1 }"><div style="text-align: center;"><font style="margin-left: auto;margin-right: auto;" size="3">暂无数据!</font></div></c:if>
	            <div class="pagenation">
					<c:if test="${pageResult.pageNo>1}">
						<a href="javascript:doGoPage(<c:out value='1' />)" class="prev">首页</a>
						<a href="javascript:doGoPage(<c:out value='${pageResult.pageNo-1}' />)" class="prev">上一页</a>
					</c:if>

					<c:choose>
						<c:when test="${pageResult.totalPageCount > 5 }">
							<c:if test="${pageResult.pageNo <=3  }">
								<c:forEach varStatus="status" step="1" begin="1" end="5">
									<a href="javascript:doGoPage(${status.index})" 
										<c:if test="${status.index eq pageResult.pageNo}">class="page pagenation_select"</c:if> 
										<c:if test="${status.index != pageResult.pageNo}">class="page"</c:if>
										> ${status.index}</a>
								</c:forEach>
							</c:if>	
							<c:if test="${pageResult.pageNo>pageResult.totalPageCount-2 and pageResult.pageNo<=pageResult.totalPageCount}">
								<c:forEach varStatus="status" step="1" begin="${pageResult.totalPageCount -4 }" end="${pageResult.totalPageCount }">
									<a href="javascript:doGoPage(${status.index})" 
										<c:if test="${status.index eq pageResult.pageNo}">class="page pagenation_select"</c:if> 
										<c:if test="${status.index != pageResult.pageNo}">class="page"</c:if>
										> ${status.index}</a>
								</c:forEach>							
							</c:if>
							<c:if test="${pageResult.pageNo >3 and pageResult.pageNo<=pageResult.totalPageCount-2  }">
								<c:forEach varStatus="status" step="1" begin="${pageResult.pageNo -2 }" end="${pageResult.pageNo +2 }">
									<a href="javascript:doGoPage(${status.index})" 
										<c:if test="${status.index eq pageResult.pageNo}">class="page pagenation_select"</c:if> 
										<c:if test="${status.index != pageResult.pageNo}">class="page"</c:if>
										> ${status.index}</a>
								</c:forEach>
							</c:if>							
						</c:when>
						<c:otherwise>
							<c:forEach varStatus="status" step="1" begin="1" end="${pageResult.totalPageCount}">
								<a href="javascript:doGoPage(${status.index})"  
									<c:if test="${status.index eq pageResult.pageNo}">class="page pagenation_select"</c:if>  
									<c:if test="${status.index != pageResult.pageNo}">class="page"</c:if>
									> ${status.index}</a>
							</c:forEach>						
						</c:otherwise>
					</c:choose>
					
	                <c:if test="${pageResult.pageNo < pageResult.totalPageCount}">
						<a href="javascript:doGoPage(<c:out value='${pageResult.pageNo+1 }'/>)" class="next">下一页</a>
						<a href="javascript:doGoPage(<c:out value='${pageResult.totalPageCount }'/>)" class="next">尾页</a>
					</c:if>
	                <span class="grey">共&nbsp;<c:out value="${ pageResult.totalPageCount}"/>&nbsp;页
	                	，共&nbsp;<c:out value="${ pageResult.totalCount}"/>&nbsp;条，到第&nbsp;<input id="pageNo" name="pageNo" type="text" style="width: 35px;"
						onkeypress="if(event.keyCode == 13){doGoPage(this.value);}" value="<c:out value ='${pageResult.pageNo }'/>" />&nbsp;页</span>
	                <a href='javascript:doGoPage($("#pageNo").val())' class="queding">确定</a>
	            </div>
