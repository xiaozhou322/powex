<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../comm/include.inc.jsp"%>
<%

%>
<c:forEach items="${logs }" var="v">
  <tr>
      <td class="td_1">${v.fcreateTime }</td>
      <td class="td_2">${v.ftype_s }</td>
  </tr>
</c:forEach>    
