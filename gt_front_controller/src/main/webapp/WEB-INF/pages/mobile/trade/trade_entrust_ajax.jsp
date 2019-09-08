<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ include file="../comm/include.inc.jsp"%>
<%

%>
<c:forEach items="${fentrusts }" var="v" varStatus="vs">                 
   <li class="showmsg">
      <a>
          <i style="background: url(${v.ftrademapping.fvirtualcointypeByFvirtualcointype2.furl}) no-repeat 0 center;background-size: 85% auto;top: 0;"></i>
          	
          <div class="fl">                        
             <em><spring:message code="enum.entrust.${v.fentrustType}" />${v.fisLimit==true?'[甯備环]':'' }</em>
             <em class="time"><fmt:formatDate value="${v.fcreateTime }" pattern="yyyy-MM-dd HH:mm:ss"/></em>                        
          </div>
          <strong class="fr cgreen">${v.ftrademapping.fvirtualcointypeByFvirtualcointype1.fSymbol}<ex:DoubleCut value="${v.fprize}" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="${ftrademapping.fcount1 }"/></strong>
          <div class="clear"></div>
      </a>
    
       <div class="entrustCon" style="display: none">
          <table>
              <tr>
                  <td><spring:message code="entrust.entrust.date" /></td>
                  <td><fmt:formatDate value="${v.fcreateTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
              </tr>
              <tr>
                  <td><spring:message code="entrust.entrust.type" /></td>
                  <td><spring:message code="enum.entrust.${v.fentrustType}" />${v.fisLimit==true?'[限价单]':'' }</td>
              </tr>        
              <tr>
                  <td><spring:message code="entrust.entrust.qty" /></td>
                  <td>${ftrademapping.fvirtualcointypeByFvirtualcointype2.fSymbol}<ex:DoubleCut value="${v.fcount }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="${ftrademapping.fcount2 }"/></td>
              </tr>        
              <tr>
                  <td><spring:message code="entrust.entrust.price" /></td>
                  <td class="cgreen">${v.ftrademapping.fvirtualcointypeByFvirtualcointype1.fSymbol}<ex:DoubleCut value="${v.fprize }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="${ftrademapping.fcount1 }"/></td>
              </tr>        
              <tr>
                  <td><spring:message code="entrust.entrust.amount" /></td>
                  <td class="cff1e48">${v.ftrademapping.fvirtualcointypeByFvirtualcointype1.fSymbol}<ex:DoubleCut value="${v.famount}" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="${ftrademapping.fcount1 }"/></td>
              </tr>        
              <tr>
                  <td><spring:message code="entrust.deal.qty" /></td>
                  <td>${ftrademapping.fvirtualcointypeByFvirtualcointype2.fSymbol}<ex:DoubleCut value="${v.fcount-v.fleftCount }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="${ftrademapping.fcount2 }"/></td>
              </tr>
                 
              <tr>
                    
                  <td><spring:message code="entrust.deal.amount" /></td>
                  <td>${v.ftrademapping.fvirtualcointypeByFvirtualcointype1.fSymbol}<ex:DoubleCut value="${v.fsuccessAmount }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="${ftrademapping.fcount1 }"/></td>
              </tr>        
              <tr>
                  <td><spring:message code="entrust.deal.fee" /></td>
                  <c:choose>
                  <c:when test="${v.fentrustType==0 }"> 
                  <td class="cblue">${ftrademapping.fvirtualcointypeByFvirtualcointype2.fSymbol}<ex:DoubleCut value="${v.ffees-v.fleftfees}" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="${ftrademapping.fcount1 }"/></td>
                  </c:when>
                  <c:otherwise>
                  <td>${v.ftrademapping.fvirtualcointypeByFvirtualcointype1.fSymbol}<ex:DoubleCut value="${v.ffees-v.fleftfees}" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="${ftrademapping.fcount1 }"/></td>
                  </c:otherwise>
                  </c:choose>
              </tr>        
              <tr>
                  <td><spring:message code="entrust.deal.average" /></td>
                  <td>${v.ftrademapping.fvirtualcointypeByFvirtualcointype1.fSymbol}<ex:DoubleCut value="${((v.fcount-v.fleftCount)==0)?0:  v.fsuccessAmount/((v.fcount-v.fleftCount)) }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="${ftrademapping.fcount1 }"/></td>
              </tr>        
              <tr>
                  <td><spring:message code="market.entruststatus" />/<spring:message code="market.entrustaction" /></td>
                  <td class="cblue">
                      <c:if test="${v.fstatus==1 || v.fstatus==2}">
                      <a href="javascript:void(0);" class="tradecancel cblue" data-value="${v.fid}" refresh="1"><spring:message code="entrust.entrust.cancel" /></a>
                      </c:if>
                      <c:if test="${v.fstatus!=1}">
                      <a href="javascript:void(0);" class="tradelook" data-value="${v.fid}"><spring:message code="entrust.entrust.view" /></a>
                      </c:if>
                  </td>
              </tr>
          </table>
      </div>
    
  </li>  
</c:forEach>       

