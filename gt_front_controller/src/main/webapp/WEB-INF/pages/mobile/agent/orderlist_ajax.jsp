<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ include file="../comm/include.inc.jsp"%>
<%

%>

 <c:forEach items="${fcapitaloperations}" var="v">   
         <div class="recodeMains">
         <a href="/agent/orderdetail.html?order_id=${v.fid}">
             <div class="table-item table-item2 clear">
                 <div class="tabs userFace fl">
                   订单编号：${v.fid}
                    <div class="clear"></div>
                 </div>
                 <span class="tm fr">${v.fcreateTime}</span>
             </div>
             <div class="recode_item">
                 <div class="reodeDetails">
                     <p>
                        <span class="fl tt">交易对象: </span><c:if test="${is_agent == 1 }"><span class="fl tts"><%-- ${v.fuser.frealName} --%></span></c:if><c:if test="${is_agent == 0 }"><span class="fl tts">${v.fremittanceType}</span></c:if>  
                         <em class="fr tts">¥ ${v.fprice}<i><img src="${oss_url}/static/mobile2018/css/agent/images/cny.png" height="12" width="24" alt="" /></i></em>
                     </p>
                     <p>
                        <span class="fl tt">订单金额: </span><span class="fl tts">${v.ftotalcny}</span> 
                         <em class="fr">x${v.famount}<i><img src="${oss_url}/static/mobile2018/css/agent/images/usdt.png" height="12" width="24" alt="" /></i></em>
                     </p>
                     <p><span class="fl tt">交易类型:</span> <span class="fl tts">${v.ftype_s}</span> <em class="fr frees">手续费:  ${v.ffees}</em></p>
                     <p><span class="fl tt">备注号: </span><span class="fl tts">${v.fremark}</span></p>
                 </div>             
                
                
             </div>
             </a>
         </div>
     </c:forEach>   