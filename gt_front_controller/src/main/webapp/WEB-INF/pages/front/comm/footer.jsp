<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@include file="include.inc.jsp"%>

<c:if test="${requestScope.isprojectUrl}">
<%-- <c:choose>
     <c:when test="${requestScope.templateCfg eq 'template1'}"> 
        <%@include file="pro_footer1.jsp" %>       
     </c:when>
     <c:when test="${requestScope.templateCfg eq 'template2'}"> 
        <%@include file="pro_footer2.jsp" %>       
     </c:when>
     <c:when test="${requestScope.templateCfg eq 'template3'}"> 
        <%@include file="pro_footer3.jsp" %>       
     </c:when>
     <c:otherwise>
      	<%@include file="pro_footer1.jsp" %>
     </c:otherwise>
</c:choose> --%>
<%@include file="pro_footer1.jsp" %>
</c:if>
<c:if test="${!requestScope.isprojectUrl}">
<%@include file="main_footer.jsp" %>
</c:if>