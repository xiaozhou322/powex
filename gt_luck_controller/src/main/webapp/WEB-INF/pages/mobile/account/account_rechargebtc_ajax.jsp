<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ include file="../comm/include.inc.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;if (request.getServerName().equals("www.gbcax.com")){basePath="https://www.gbcax.com";}
%>
<c:forEach items="${fvirtualcaptualoperations }" var="v" varStatus="vs">     
    <div class="txDetail">
      <ul>
          <li class="fl">
              <em><ex:DoubleCut value="${v.famount }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/></em>
              <span><spring:message code="financial.recamo" /></span>
          </li>                
          <li class="fl">
              <em>${v.fconfirmations }</em>
              <span><spring:message code="financial.contim" /></span>
          </li>                
          <li class="fl">
              <em class="cgreen3"><spring:message code="enum.capitalopera.in.${v.fstatus }" /></em>
              <span><spring:message code="financial.status" /></span>
          </li>
          <div class="clear"></div>
      </ul>
      <ol>
          <li class="fl">
              <span><spring:message code="financial.latupd" />：</span>
              <em>${v.flastUpdateTime }</em>
          </li>
          <li class="fl special">
              <span class="fl"><spring:message code="financial.waladd" />：</span>
              <em class="cblue fl">${v.recharge_virtual_address }</em>
              <div class="clear"></div>
          </li>
          <div class="clear"></div>
      </ol>
  </div>        

  </c:forEach>

