<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../comm/include.inc.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;if (request.getServerName().equals("www.gbcax.com")){basePath="https://www.gbcax.com";}
%>
<c:choose>
       <c:when test="${recordType==1 || recordType==2 }">
           <c:forEach items="${list }" var="v">
               <li class="clear"> <em class="textL"><fmt:formatDate value="${v.fcreateTime }" pattern="yyyy/MM/dd"/></em> <em class="textC">￥<ex:DoubleCut value="${v.famount }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/></em>
                   <em>￥<ex:DoubleCut value="${v.ffees }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/></em>
                   <em class="cblue2 textR">${v.fstatus_s }</em>
               </li>
           </c:forEach>
       </c:when>
        <c:when test="${recordType==5 || recordType==6 }">
        <%--人民币充值、提现--%>
        <c:forEach items="${list }" var="v">
           <li class="clear">
          <em class="textL"><fmt:formatDate value="${v.fcreateTime }" pattern="yyyy/MM/dd"/></em>
          <em class="textC">$<ex:DoubleCut value="${v.famount }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/>(￥<ex:DoubleCut value="${v.famount*6.5 }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/>)</em>
          <em>￥<ex:DoubleCut value="${v.ffees }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/></em>
          <em class="cblue2 textR">${v.fstatus_s }</em>
      </li>           
        </c:forEach>
        </c:when>
        <c:when test="${recordType==3 || recordType==4 }">
         <%--充值、提现--%>
          <c:forEach items="${list }" var="v">
               <li class="clear">
                  <em class="textL"><fmt:formatDate value="${v.fcreateTime }" pattern="yyyy/MM/dd"/></em>
                  <em class="textC">${v.fvirtualcointype.fSymbol }<ex:DoubleCut value="${v.famount }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/></em>
                  <em>${v.fvirtualcointype.fSymbol }<ex:DoubleCut value="${v.ffees }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/></em>
                  <em class="cblue2 textR">${v.fstatus_s }</em>
              </li>         
          </c:forEach>
      </c:when>
   </c:choose>
