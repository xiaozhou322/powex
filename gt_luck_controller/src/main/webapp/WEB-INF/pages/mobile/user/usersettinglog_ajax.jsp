<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../comm/include.inc.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;if (request.getServerName().equals("www.gbcax.com")){basePath="https://www.gbcax.com";}
%>
<c:forEach items="${logs }" var="v">
  <tr>
      <td class="td_1">${v.fcreateTime }</td>
      <td class="td_2">${v.ftype_s }</td>
  </tr>
</c:forEach>    
