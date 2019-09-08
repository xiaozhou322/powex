<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../comm/include.inc.jsp"%>
<%

%>
<c:forEach items="${list}" var="v">
   <div class="txDetail" >
       <ul>
           <li class="fl">
               <em class="cgreen3">${v.famount }</em>
           </li>                
           <li class="fl">
               <em><spring:message code="financial.cnyrecharge.status${v.fstatus }" /></em>
           </li>                
           <li class="fl textRight ">
          <c:if test="${(v.fstatus==1 || v.fstatus==2)}">
            <a class="cblue opa-link rechargecancel" href="javascript:void(0);" id="rechargecancel_${v.fid }" data-fid="${v.fid }"><spring:message code="financial.cnyrecharge.cancel" /></a>
              <span id="show${v.fid }" style="display: none">--</span>
              <c:if test="${v.fstatus==1}">
              <a class="cblue opa-link rechargesub" href="javascript:void(0);" id="rechargesub_${v.fid }" data-fid="${v.fid }"><spring:message code="financial.cnyrecharge.submit" /></a>
                </c:if>
            </c:if>
               <c:if test="${(v.fstatus==3 || v.fstatus==4)}">
                   <td class="opa-link">--</td> 
               </c:if>
           </li>
           <div class="clear"></div>
       </ul>
       <ol>
           <li>
               <span><spring:message code="financial.cnyrecharge.orderno" />：</span>
               <em>${v.fid }</em>
           </li>                
           <li>
               <span><spring:message code="financial.cnyrewithdrawal.remarknum" />：</span>
               <em>${v.fremark }</em>
           </li>                
           <li>
               <span><spring:message code="financial.cnyrecharge.rechargemode" />：</span>
               <em>${v.fremittanceType }</em>
           </li>                
           <li>
               <span><spring:message code="financial.cnyrecharge.rechargetime" />：</span>
               <em><fmt:formatDate value="${v.fcreateTime }" pattern="yyyy-MM-dd HH:mm:ss"/></em>
           </li>
       </ol>
   </div>
   </c:forEach>    
<script type="text/javascript" src="${oss_url}/static/mobile2018/js/account.recharge.js?v=4"></script>
