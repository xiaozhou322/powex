<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ include file="../comm/include.inc.jsp"%>
<%

%>
<c:forEach items="${fvirtualcaptualoperations }" varStatus="vs" var="v">
   <div class="txDetail">
       <ul>
           <li class="fl">
               <em><ex:DoubleCut value="${v.famount+v.ffees }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/></em>
               <span><spring:message code="financial.witamo" /></span>
           </li>                
           <li class="fl">
               <em><ex:DoubleCut value="${v.famount }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="6"/></em>
               <span><spring:message code="financial.actarr" /></span>
           </li>                
           <li class="fl">
               <em class="cgreen3"><spring:message code="enum.capitalopera.out.${v.fstatus }" />
               <c:if test="${v.fstatus==1 }">
                 &nbsp;|&nbsp;
                 <a class="" href="javascript:void(0);" data-fid="${v.fid }"><spring:message code="financial.cancel" /></a>
                </c:if>
                </em>
               <span><spring:message code="financial.witstatus" /></span>
           </li>
           <div class="clear"></div>
       </ul>
       <ol>
           <li class="fl">
               <span><spring:message code="financial.wittim" />：</span>
               <em><fmt:formatDate value="${v.fcreateTime }" pattern="yyyy-MM-dd HH:mm:ss"/></em>
           </li>
           <li class="fl special">
               <span class="fl"><spring:message code="financial.witadd" />：</span>
               <em class="cblue fl">${v.withdraw_virtual_address }</em>
               <div class="clear"></div>
           </li>
           <div class="clear"></div>
       </ol>
   </div>        
  </c:forEach>

