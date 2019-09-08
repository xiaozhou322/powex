<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div>
      <a  <c:if test="${menuflag == 'agentlist' }"> class="active" </c:if> href="/agent/agentlist.html">
         <svg class="icon sfont24" aria-hidden="true">
              <use xlink:href="#icon-goumai"></use>
          </svg>&nbsp;<spring:message code="financial.usdtrecharge" />
      </a>
      <a  <c:if test="${menuflag == 'buyerlist' }"> class="active" </c:if> href="/agent/buyerlist.html">
          <svg class="icon sfont24" aria-hidden="true">
                <use xlink:href="#icon-iconset0293"></use>
            </svg>&nbsp;<spring:message code="agent.navsell" />USDT      
      </a>
<!--       <a  <c:if test="${menuflag == 'orderlist' }"> class="active" </c:if> href="/agent/orderlist.html">
          <img class="img01" src="${oss_url}/static/front/css/agent/images/ico_2.png" alt="" />
          <img class="img02" src="${oss_url}/static/front/css/agent/images/ico2.png" alt="" />订单记录
      </a> -->
    
    
  </div>