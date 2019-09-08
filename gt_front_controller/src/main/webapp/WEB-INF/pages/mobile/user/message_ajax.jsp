<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../comm/include.inc.jsp"%>
<%

%>
<c:forEach items="${messages }" var="v">
 <li>
     <h2 class="Cff9000">${v.ftitle }</h2>
     <p>${v.fcontent}</p>
     <div class="lookDetail">
         <span class="fl">${v.fcreateTime }</span>
         <a  class="fr Cff9000 showmsg msglook"  data-value="${v.fid}" data-msg="${v.fid}"><spring:message code="security.view" /></a>
         <div class="clear"></div>
     </div>
             
 </li>
</c:forEach>    
<c:forEach items="${messages }" var="v">
<section class="ptop2" style="display: none;" id="showmsg${v.fid}">
    <div class="msgDetail">
        <div class="msgTit">
            <h1>${v.ftitle }</h1>
            <span>${v.fcreateTime }</span>
        </div>
        <div class="msgText">
            <p>${v.fcontent}</p>
        </div>
    </div>
</section>
</c:forEach> 
