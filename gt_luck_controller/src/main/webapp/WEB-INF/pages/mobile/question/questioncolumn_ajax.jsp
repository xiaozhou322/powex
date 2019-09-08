<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ include file="../comm/include.inc.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;if (request.getServerName().equals("www.gbcax.com")){basePath="https://www.gbcax.com";}
%>
<c:forEach items="${list }" var="v">        
<div class="ticketsList">
    
    <table class="tableTit">
        <tr>
<!--                      <th class="td1">工单类型</th>
 -->                     <th class="td1"><spring:message code="enum.question.${v.ftype }" /><span class="cgreen">&nbsp;&nbsp;(<spring:message code="enum.question.status.${v.fstatus }" />)</span><em style="display:block; font-size:12px;">${v.fcreateTime }</em></th>
           <th class="td3"><a class="cblue deteleBtn delete" href="javascript:void(0)" data-question="{&quot;questionid&quot;:${v.fid }}"><spring:message code="security.delete" /></a></th>
       </tr>
   </table>
   <table class="tableDetailTit lookWarp hideBox">
       <tr>
           <td class="td1"><spring:message code="security.quesnum" /></td>
           <td class="td2">${v.fid }</td>
       </tr>                 
       <tr>
           <td class="td1"><spring:message code="security.subtime" /></td>
           <td class="td2">${v.fcreateTime }</td>
       </tr>                 
       <tr>
           <td class="td1"><spring:message code="security.prodes" /></td>
           <td class="td2 discription" colspan="2">${v.fdesc }</td>
       </tr>                 
       <tr>
           <td class="td1"><spring:message code="security.prostate" /></td>
           <td class="td2 cgreen"><spring:message code="enum.question.status.${v.fstatus }" /></td>
           <td class=" lookMore">
           <c:if test="${v.fstatus==2 }">
           <a class="cblue" href="javascript:void(0)" data-question="${v.fid }"><spring:message code="security.view" /></a>
            </c:if>
           </td>
       </tr>
   </table>

   <span class="cblue look view" onclick="javascript:this.innerHTML=(this.innerHTML=='<spring:message code="m.question.view" />'?'<spring:message code="m.question.shou" />':'<spring:message code="m.question.view" />')"><spring:message code="m.question.view" /></span>
       
</div>   
</c:forEach>

<c:if test="${fn:length(list)==0 }">
  <p style="text-align:center; font-size:18px; display:none;"><spring:message code="financial.norec" /></p>
</c:if>

<c:forEach items="${list }" var="v">
    <c:if test="${v.fstatus==2 }">
   <div class="ticketsDetail lookMoreWarp" id="lookMoreWarp${v.fid }" data-show="${v.fid }" tabindex="-1">
       <div class="ticketsDetailCon">
           <h2 class="tcenter"><spring:message code="security.quecontent" /></h2>
           <p>${v.fdesc }</p>
           <h3 class="cblue results"><spring:message code="security.repcontent" /></h3>
           <p>${v.fanswer }</p>
           <span class="close close2"></span>
       </div>
   </div>
  </c:if>       
  </c:forEach>
  