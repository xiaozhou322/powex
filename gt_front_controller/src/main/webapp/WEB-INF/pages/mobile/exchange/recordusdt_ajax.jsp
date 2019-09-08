<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ include file="../comm/include.inc.jsp"%>
<%

%>

<c:if test="${type==0}">
  
  <c:forEach items="${fcapitaloperations}" var="v">
<table>
        <tr class="tr_spectial">
          <td colspan="4" class="td_01 "><spring:message code="financial.usdtrecharge.rechargetime" />：<fmt:formatDate value="${v.fcreateTime }" pattern="yyyy-MM-dd HH:mm:ss"/>   <c:if test="${(v.fstatus==1 || v.fstatus==2)}">
                <span class="fr" style="color:#999;">
                              <a class="rechargecancel opa-link cblue" href="javascript:void(0);" data-fid="${v.fid }"><spring:message code="financial.cnyrecharge.cancel" /></a>
                              
              <c:if test="${v.fstatus==1}">|
              <a class="rechargesub opa-link cblue" href="javascript:void(0);" data-fid="${v.fid }"><spring:message code="financial.cnyrecharge.submit" /></a>
          </c:if>
          </c:if></span>
          </td>
        </tr>
        <tr class="tr_spectial2">
          <td class="td_01"><b><spring:message code="financial.usdtrecharge.orderno" /></b></td>
          <td><b><spring:message code="financial.usdtrecharge.rechargeamount" /></b></td>
          <td><b><spring:message code="financial.usdtrecharge.rechargemode" /></b></td>
<!--           <td class="td_02"><spring:message code="financial.usdtrecharge.rechargestatus" /></td>
 -->                    
        </tr>
        <tr>
          <td class="td_01 ">${v.fid }</td>
          <td class="colorRed">￥${v.famount }($<ex:DoubleCut value="${v.famount/6.5 }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/>)</td>
          <td class="">${v.fremittanceType }</td>
<!--           <td class="td_02"><spring:message code="financial.cnyrecharge.status${v.fstatus }" /></td>
 -->                   
        </tr>

        <tr>
            <td class="td_01"><b><spring:message code="financial.usdtrecharge.rechargestatus" /></b></td>
            <td><b><spring:message code="financial.usdtrecharge.remarknum" /></b></td>
            <td></td>
        </tr>
        <tr>
            <td class="td_01 "><spring:message code="financial.cnyrecharge.status${v.fstatus }" /></td>
            <td class=" ">${v.fremark }</td>
            <td></td>
        </tr>


      </table>
<!--             <p class="p_01">${v.fremark }</p>
            <p class="p_01 p_02"><spring:message code="financial.usdtrecharge.remarknum" /></p> -->
      </c:forEach>

</c:if>

<c:if test="${type==1}">
  
   <c:forEach items="${fcapitaloperations }" var="v" varStatus="vs">
                         <table>
                                <tr class="tr_spectial">
                                    <td colspan="4" class="td_01 Color333"><spring:message code="financial.usdtrewithdrawal.wittim" />：<fmt:formatDate value="${v.fcreateTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>

                                </tr>
                                 <tr class="tr_spectial">
                                    <td colspan="4" class="td_01 Color333"><spring:message code="financial.usdtrewithdrawal.remarknum" />：${v.fremark }</td>
                                    
                                </tr>
                                 <tr class="tr_spectial">
                                    <td colspan="4" class="td_01 Color333"><spring:message code="financial.usdtrewithdrawal.account" />：${v.fAccount }</td>
                                    
                                </tr>
                                <tr>
                                   <td class="td_01 Color333">$${v.famount+v.ffees  }</td>
                                   <td class="colorRed">$${v.famount}(￥<ex:DoubleCut value="${v.famount*6.5}" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/>)</td>
                                   <td class="Color333">$${v.ffees }</td>
                                   <td class="td_02"><c:if test="${v.fstatus!=1 }">${v.fstatus_s }</c:if>
                    <c:if test="${v.fstatus==1 }">
                    <a href="/exchange/exchangeUsdt.html"><spring:message code="financial.usdtrewithdrawal.exchange" /></a>
                    &nbsp;|&nbsp;
                    <a class="cancelWithdrawusdt opa-link" href="javascript:void(0);" data-fid="${v.fid }"><spring:message code="financial.cancel" /></a>
                    </c:if></td>
                               </tr>                             
                               <tr class="tr_spectial2">
                                   <td class="td_01"><spring:message code="financial.usdtrewithdrawal.amount" /></td>
                                   <td><spring:message code="financial.actarr" /></td>
                                   <td><spring:message code="financial.usdtrewithdrawal.fee" /></td>
                                   <td class="td_02"><spring:message code="financial.usdtrewithdrawal.witstatus" /></td>
                               </tr>                           
                        </table>                         
                    </c:forEach>

</c:if>

  <script type="text/javascript" src="${oss_url}/static/mobile2018/js/msg.js?v=20181126201750"></script>
  <script type="text/javascript" src="${oss_url}/static/mobile2018/js/finance/account.withdraw.js?v=3"></script>
  <script type="text/javascript" src="${oss_url}/static/mobile2018/js/finance/account.usdtrecharge.js?v=11"></script>
  <script type="text/javascript" src="${oss_url}/static/mobile2018/js/finance/city.min.js?v=20181126201750"></script>
  <script type="text/javascript" src="${oss_url}/static/mobile2018/js/finance/jquery.cityselect.js?v=20181126201750"></script>
  