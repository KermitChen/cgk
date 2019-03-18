<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	            <div class="pagenation">
					<c:if test="${pageResults.pageNo>1}">&nbsp;&nbsp;
						<a href="javascript:doGoPage(<c:out value='1' />)" class="prev">首页</a>
						<a href="javascript:doGoPage(<c:out value='${pageResults.pageNo-1}' />)" class="prev">上一页</a>
					</c:if>

					<c:choose>
						<c:when test="${pageResults.totalPageCount >5 }">
							<c:if test="${pageResults.pageNo <=3  }">
								<c:forEach varStatus="status" step="1" begin="1" end="5">
									<a href="javascript:doGoPage(${status.index})" 
										<c:if test="${status.index eq pageResults.pageNo}">class="page pagenation_select"</c:if> 
										<c:if test="${status.index != pageResults.pageNo}">class="page"</c:if>
										> ${status.index}</a>
								</c:forEach>
							</c:if>	
							<c:if test="${pageResults.pageNo>pageResults.totalPageCount-2 and pageResults.pageNo<=pageResults.totalPageCount}">
								<c:forEach varStatus="status" step="1" begin="${pageResults.totalPageCount -4 }" end="${pageResults.totalPageCount }">
									<a href="javascript:doGoPage(${status.index})" 
										<c:if test="${status.index eq pageResults.pageNo}">class="page pagenation_select"</c:if> 
										<c:if test="${status.index != pageResults.pageNo}">class="page"</c:if>
										> ${status.index}</a>
								</c:forEach>							
							</c:if>
							<c:if test="${pageResults.pageNo >3 and pageResults.pageNo<=pageResults.totalPageCount-2  }">
								<c:forEach varStatus="status" step="1" begin="${pageResults.pageNo -2 }" end="${pageResults.pageNo +2 }">
									<a href="javascript:doGoPage(${status.index})" 
										<c:if test="${status.index eq pageResults.pageNo}">class="page pagenation_select"</c:if> 
										<c:if test="${status.index != pageResults.pageNo}">class="page"</c:if>
										> ${status.index}</a>
								</c:forEach>
							</c:if>							
						</c:when>
						<c:otherwise>
							<c:forEach varStatus="status" step="1" begin="1" end="${pageResults.totalPageCount}">
								<a href="javascript:doGoPage(${status.index})"  
									<c:if test="${status.index eq pageResults.pageNo}">class="page pagenation_select"</c:if>  
									<c:if test="${status.index != pageResults.pageNo}">class="page"</c:if>
									> ${status.index}</a>
							</c:forEach>						
						</c:otherwise>
					</c:choose>
					
	                <c:if test="${pageResults.pageNo < pageResults.totalPageCount}">&nbsp;&nbsp;
						<a href="javascript:doGoPage(<c:out value='${pageResults.pageNo+1 }'/>)" class="next">下一页</a>
						<a href="javascript:doGoPage(<c:out value='${pageResults.totalPageCount }'/>)" class="next">尾页</a>
					</c:if>
	                <span class="grey">共<c:out value="${ pageResults.totalPageCount}" />页
	                	，到第&nbsp;<input id="pageNo" name="pageNo" type="text" style="width: 30px;"
						onkeypress="if(event.keyCode == 13){doGoPage(this.value);}" 
						 value="<c:out value ="${pageResults.pageNo }"/>" />
					&nbsp;&nbsp;页</span>
	                <a href='javascript:doGoPage($("#pageNo").val())' class="queding" >确定</a>
	            </div>
