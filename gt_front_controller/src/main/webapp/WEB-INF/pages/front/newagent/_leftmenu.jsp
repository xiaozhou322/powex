<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div>
      <c:forEach items="${fcoins}" var="t">
		<a <c:if test="${t.fid == symbol }"> class="active" </c:if> 
		<c:if test="${menuflag == 'agentlist' }"> href="/advertisement/buyList.html?symbol=${t.fid }">
		<svg class="icon sfont24" aria-hidden="true">
              <use xlink:href="#icon-goumai"></use>
        </svg>&nbsp;<spring:message code="agent.purchase" />&nbsp;${t.fShortName}</a>
        </c:if>
        <c:if test="${menuflag == 'buyerlist' }"> href="/advertisement/sellList.html?symbol=${t.fid }">
        <svg class="icon sfont24" aria-hidden="true">
             <use xlink:href="#icon-goumai"></use>
         </svg>&nbsp;<spring:message code="agent.navsell" />&nbsp;${t.fShortName}</a>
        </c:if>
		</c:forEach>
  </div>