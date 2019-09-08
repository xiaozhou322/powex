<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../comm/include.inc.jsp"%>
<%

%>
<c:forEach items="${fcapitaloperations }" var="v" varStatus="vs">
   <div class="txDetail">
       <ul>
           <li class="fl">
               <em class="cgreen3">￥${v.famount+v.ffees }</em>
           </li>                
          
           <li class="fr textRight">
                 <span>${v.fstatus_s }<c:if test="${v.fstatus==1 }">
                         &nbsp;|&nbsp;
                         <a class="cancelWithdrawcny opa-link cblue" href="javascript:void(0);" data-fid="${v.fid }">取消</a>
                         </c:if></span>
           </li>
           <div class="clear"></div>
       </ul>
       <ol>
       		<li>
                    <span><spring:message code="financial.witfee" />：</span>
                    <em>￥${v.ffees }</em>
                </li>
           <li>
               <span><spring:message code="financial.actarr" />：</span>
               <em>￥${v.famount }</em>
           </li>                
           <li>
               <span><spring:message code="financial.cnyrewithdrawal.remarknum" />：</span>
               <em>${v.fid }</em>
           </li>                
           <li>
               <span><spring:message code="financial.cnyrewithdrawal.account" />：</span>
               <em>${v.fAccount }</em>
           </li>                
           <li>
               <span><spring:message code="financial.wittim" />：</span>
               <em><fmt:formatDate value="${v.fcreateTime }" pattern="yyyy-MM-dd HH:mm:ss"/></em>
           </li>
       </ol>
   </div>        
  </c:forEach>

