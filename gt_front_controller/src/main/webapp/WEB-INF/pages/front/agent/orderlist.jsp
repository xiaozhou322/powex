<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ include file="../comm/include.inc.jsp"%>
<%

%>
<!doctype html>
<html>
<head> 
<%@ include file="../comm/basepath.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="../comm/link.inc.jsp" %>
<link href="${oss_url}/static/front2018/css/usdtTrade.css?v=14" rel="stylesheet" type="text/css" />
</head>
<body class="">
<%@include file="../comm/white_header.jsp" %>
<section class="uTrade mg clear">

       <div class="uTrade_r">
            <div class="baseForm recodeMain">
                 <div class="tab_tit clear">
                     <ol class="fl">
                         <li class="<c:if test="${status == 0 }">active</c:if> fl"><a href="/agent/orderlist.html?status=0"><spring:message code="agent.all" /></a></li>
                         <li class="<c:if test="${status == 1 }">active</c:if> fl"><a href="/agent/orderlist.html?status=1"><spring:message code="agent.unpaid" /></a></li>
                         <li class="<c:if test="${status == 2 }">active</c:if> fl"><a href="/agent/orderlist.html?status=2"><spring:message code="agent.paid" /></a></li>
                         <li class="<c:if test="${status == 3 }">active</c:if> fl"><a href="/agent/orderlist.html?status=3"><spring:message code="agent.complete" /></a></li>
                         <li class="<c:if test="${status == 4 }">active</c:if> fl"><a href="/agent/orderlist.html?status=4"><spring:message code="agent.cancel" /></a></li>
                         <li class="<c:if test="${status == 5 }">active</c:if> fl"><a href="/agent/orderlist.html?status=5"><spring:message code="agent.failed" /></a></li>
                     </ol>
                     <form action="/agent/orderlist.html" method="get" id="search_form">
                     <div class="usdt_search fr">
                         <input class="text fl" type="text" placeholder="<spring:message code="agent.serchorder" />" name="orderno" id="order_no">
                         <input class="sub fr" type="button" value="<spring:message code="agent.ordersearch" />" onclick="if($('#order_no').val()!='')$('#search_form')[0].submit();">
                     </div>
                     </form>
                 </div>
                 <div class="tabContent">
                     <table>
                         <tbody><tr class="tr_title">
                             <th style="width:10%;" width="10%"><spring:message code="agent.orderid" /></th>
                             <th style="width:16%;" width="16%"><spring:message code="agent.creationtime" /></th>
                             <th style="width:12%;" width="12%"><spring:message code="market.entrusttype" /></th>
                             <th style="width:12%;" width="12%"><spring:message code="agent.user" /></th>
                             <th style="width:8%;" width="8%"><spring:message code="agent.unitprice" /></th>
                             <th style="width:10%;" width="10%"><spring:message code="market.amount" /></th>
                             <th style="width:10%;" width="10%"><spring:message code="agent.cnyamount" /></th>
                             <th style="width:8%;" width="8%"><spring:message code="market.entruststatus" /></th>
                             <th style="width:10%;" width="10%"><spring:message code="agent.reference" /></th>
                         </tr>
                     </tbody></table>
                     <table class="tab_con">
                         <tbody>
                         
                          <c:forEach items="${fagentoperations}" var="v">   
                         <tr>
                             <td style="width:10%;" width="10%"><a class="colorBlue" href="/agent/orderdetail.html?order_id=${v.fid}">${v.fid}</a></td>
                             <td style="width:16%;" width="16%">${v.fcreateTime}</td>
                             <td style="width:12%;" width="12%">${v.ftype_s}</td>
                             <c:if test="${is_agent == 1 }">  <td style="width:12%;"  width="12%" class="colorBlue">${v.fuser['frealName']}</td></c:if>
                              <c:if test="${is_agent == 0 }">  <td style="width:12%;" width="12%"  class="colorBlue">${v.fremittanceType}</td></c:if>
                             <td style="width:8%;" width="8%">${v.fprice}</td>
                             <td style="width:10%;" width="10%">${v.famount}</td>
                             <td style="width:10%;" width="10%">${v.ftotalcny}</td>
                              <td style="width:8%;" width="8%">${v.fstatus_s}</td>
                             <td style="width:10%;" width="10%">${v.fremark}</td>
                         </tr>  
                         </c:forEach>                       
                           <c:if test="${fn:length(fagentoperations)==0 }">
                                <tr>
                                  <td colspan="7" class="no-data-tips">
                                    <span>
                                      <spring:message code="financial.record.nobill" />
                                    </span>
                                  </td>
                                </tr>
                            </c:if>
                     </tbody>
                     </table>
                     <p class="noMsg" style="display:none;"><spring:message code="agent.noorder" /></p>
                 </div>
            </div>
            <c:if test="${totalPage > 1 }">
        	<div class="text-center">
				${pagin }
			</div>
			</c:if>
        </div>
</section>
<%@include file="../comm/footer.jsp" %>	
<script type="text/javascript" src="${oss_url}/static/front2018/js/index/main.js?v=20181126201750"></script>
</body>
</html>
