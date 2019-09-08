<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div>

<!--       <a  <c:if test="${menuflag == 'orderlist' }"> class="active" </c:if> href="/agent/orderlist.html">
          <img class="img01" src="${oss_url}/static/front/css/agent/images/ico_2.png" alt="" />
          <img class="img02" src="${oss_url}/static/front/css/agent/images/ico2.png" alt="" />订单记录
      </a> -->
      <a  <c:if test="${menuflag == 'payaccount' }"> class="active" </c:if> href="/agent/payaccount.html">
          <svg class="icon sfont22" aria-hidden="true">
                <use xlink:href="#icon-zhanghuguanli"></use>
          </svg>&nbsp;<spring:message code="agent.accountsetting" />
      </a>
      <c:if test="${is_agent == 1 }">
      <a   <c:if test="${menuflag == 'agentinfo' }"> class="active" </c:if> href="/agent/agentinfo.html">
          <svg class="icon sfont24" aria-hidden="true">
                <use xlink:href="#icon-jiaoyi2"></use>
          </svg>&nbsp;<spring:message code="agent.tradeset" />
      </a>
       <a  <c:if test="${menuflag == 'puborder' }"> class="active" </c:if> href="/agent/puborder.html">
          <svg class="icon sfont22" aria-hidden="true">
                <use xlink:href="#icon-chanpinfabu"></use>
          </svg>&nbsp;<spring:message code="agent.advertise" />
      </a>
      <a  <c:if test="${menuflag == 'adlist' }"> class="active" </c:if> href="/agent/adlist.html">
          <svg class="icon sfont22" aria-hidden="true">
                <use xlink:href="#icon-wodefabu"></use>
          </svg>&nbsp;<spring:message code="agent.myad" />
      </a>
      </c:if>
   
  </div>