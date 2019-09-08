<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@include file="include.inc.jsp"%>

<c:if test="${requestScope.isprojectUrl}">
<%-- <%@include file="project_header.jsp" %> --%>
     <%@include file="pro_header2.jsp" %>       
</c:if>
<c:if test="${!requestScope.isprojectUrl}">
<%@include file="main_header.jsp" %>
</c:if>
