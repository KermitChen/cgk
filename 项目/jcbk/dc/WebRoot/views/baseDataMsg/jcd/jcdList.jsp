<%--
  itcast.cn的ajax自动补全实例
--%>
<!--与传统应用的视图层不同 ，这个jsp返回的是xml的数据，因此contentType的值是text/xml-->
<%@page contentType="text/xml;charset=UTF-8" language="java" %>
<%@page import="java.util.List"%>

<!--返回xml数据的‘视图层暂时不做任何逻辑判断，先将所有单词都返回，待前后台应用可以完整的协作之后，再限制返回的内容’-->
<words>
<%
	List listauto = (List)request.getAttribute("autolist");
	if(listauto != null && listauto.size() > 0){
  		String str = "";
 		for(int i = 0;i < listauto.size() && i <= listauto.size();i++){
    		str = (String)listauto.get(i);
%>
    		<word><%=str %></word>
<%
		}
	}
%>
</words>
