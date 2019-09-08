<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ include file="../comm/include.inc.jsp"%>
<%

%>

<!doctype html>
<html>
<head>
<jsp:include page="../comm/link.inc.jsp"></jsp:include>
<link rel="stylesheet" type="text/css" href="${oss_url}/static/front/css/index/main.css?v=20181126201750" />
</head>
<body>	
<jsp:include page="../comm/header.jsp"></jsp:include>

<section>   
    <div class="myMsg mg clear">
        <div class="msgTit myRecord clear">
            <div class="clear">
               <div class="myRecord_tit fl">
                  <a href="/trade/entrust.html?symbol=${ftrademapping.fid }&status=0" class="fl   ${status eq 0?'active':'' }">
                      <svg class="icon sfont18" aria-hidden="true">
                      <use xlink:href="#icon-yuyuejilu"></use>
                      </svg>
                      <spring:message code="entrust.title.entrust" />                   
                  </a>               
                  <a href="/trade/entrust.html?symbol=${ftrademapping.fid }&status=1" class="fl ${status eq 1?'active':'' }">
                      <svg class="icon sfont18" aria-hidden="true">
                          <use xlink:href="#icon-jiaoyichenggong"></use>
                      </svg><spring:message code="entrust.title.deal" />
                  </a>
                </div>
                <div class="changeCoin fl">
                    <spring:message code="m.security.selectcoin" />
                    <c:forEach var="v" varStatus="vs" items="${ftrademappings }">
                    <c:if test="${v.fid==symbol }">
                    <span class='coinCur'>${v.fvirtualcointypeByFvirtualcointype2.fShortName }/${v.fvirtualcointypeByFvirtualcointype1.fShortName } </span>
                    </c:if>
                    </c:forEach>
                    <em class='titTag'>▼</em>
                    <div class="allCoin">
                      <ul>
                      <c:forEach var="v" varStatus="vs" items="${ftrademappings }">
                        <li><a href="/trade/entrust.html?symbol=${v.fid }&status=${status }">${v.fvirtualcointypeByFvirtualcointype2.fShortName }/${v.fvirtualcointypeByFvirtualcointype1.fShortName }</a></li>
                       
                        </c:forEach>
                      </ul>
                    </div>
                </div>
            </div>
        </div>
        <div class="mg">
            <div class="myRecord_list">
              <div class="record_item">
                  <div class="itemTit clear">
                      <span class="item_s"><spring:message code="entrust.entrust.date" /></span>
                      <span class="item_s small_itme"><spring:message code="entrust.entrust.type" /></span>
                      <span class="item_s item_shul"><spring:message code="entrust.entrust.qty" /></span>
                      <span class="item_s"><spring:message code="entrust.entrust.price" /></span>
                      <span class="item_s"><spring:message code="entrust.entrust.amount" /></span>
                      <span class="item_s item_shul"><spring:message code="entrust.deal.qty" /></span>
                      <span class="item_s"><spring:message code="entrust.deal.amount" /></span>
                      <span class="item_s small_itme"><spring:message code="entrust.deal.fee" /></span>
                      <span class="item_s"><spring:message code="entrust.deal.average" /></span>
                      <span class="item_s item_cz"><spring:message code="market.entruststatus" />/<spring:message code="market.entrustaction" /></span>
                  </div>
				  <c:if test="${fn:length(fentrusts)==0 }">
								<tr>
									<td class="no-data-tips" colspan="10">
										<span class='noMsg'>
											<spring:message code="json.trade.noord" />
										</span>
									</td>
								</tr>
				</c:if>
				
				<c:forEach items="${fentrusts }" var="v" varStatus="vs">
                  <div class="itemCon" id="data${v.fid}">
                      <div class="itemForm clear">
                          <span class="item_s"> <fmt:formatDate value="${v.fcreateTime }" pattern="yyyy-MM-dd HH:mm:ss"/></span>
                          <span class="item_s small_itme ${v.fentrustType==0?'cgreen':'cred' }"> ${v.fentrustType==0?'买入':'卖出' }</span>
                          <span class="item_s item_shul">${ftrademapping.fvirtualcointypeByFvirtualcointype2.fSymbol}<ex:DoubleCut value="${v.fcount }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="${ftrademapping.fcount2 }"/></span>
                          <span class="item_s">${v.ftrademapping.fvirtualcointypeByFvirtualcointype1.fSymbol}<ex:DoubleCut value="${v.fprize }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="${ftrademapping.fcount1 }"/></span>
                          <span class="item_s">${v.ftrademapping.fvirtualcointypeByFvirtualcointype1.fSymbol}<ex:DoubleCut value="${v.famount}" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="${ftrademapping.fcount1 }"/></span>
                          <span class="item_s item_shul">${ftrademapping.fvirtualcointypeByFvirtualcointype2.fSymbol}<ex:DoubleCut value="${v.fcount-v.fleftCount }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="${ftrademapping.fcount2 }"/></span>
                          <span class="item_s">${v.ftrademapping.fvirtualcointypeByFvirtualcointype1.fSymbol}<ex:DoubleCut value="${v.fsuccessAmount }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="${ftrademapping.fcount1 }"/></span>
                          <c:choose>
						  <c:when test="${v.fentrustType==0 }">
                          <span class="item_s small_itme">${ftrademapping.fvirtualcointypeByFvirtualcointype2.fSymbol}<ex:DoubleCut value="${v.ffees-v.fleftfees}" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="${ftrademapping.fcount1 }"/></span>
                          </c:when>
						  <c:otherwise>
						  <span class="item_s small_itme">${v.ftrademapping.fvirtualcointypeByFvirtualcointype1.fSymbol}<ex:DoubleCut value="${v.ffees-v.fleftfees}" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="${ftrademapping.fcount1 }"/></span>
						  </c:otherwise>
						   </c:choose>
                          <span class="item_s">${v.ftrademapping.fvirtualcointypeByFvirtualcointype1.fSymbol}<ex:DoubleCut value="${((v.fcount-v.fleftCount)==0)?0:  v.fsuccessAmount/((v.fcount-v.fleftCount)) }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="${ftrademapping.fcount1 }"/></span>
                          <span class="item_s item_cz"><spring:message code="enum.entrust.status.${v.fstatus}" />
                          <c:if test="${v.fstatus==1 || v.fstatus==2}">
                          &nbsp;|&nbsp;<a href="javascript:void(0);" class="lightBlue cancle tradecancel" data-value="${v.fid}" refresh="1"><spring:message code="entrust.entrust.cancel" /></a>
                          </c:if>
                          <c:if test="${v.fstatus!=1}">
                          &nbsp;|&nbsp;<a href="javascript:;" class="lightBlue cancle tradelook" data-value="${v.fid}" ><spring:message code="entrust.entrust.view" /></a>
                          </c:if>
                          </span>
                      </div>

                  </div>
                  	     <div class="itemDetail " id="item${v.fid}">
                           
                          
                      	  </div>
                </c:forEach>				                  
                 
              </div>
              <div class="page">
        					${pagin}
        			</div>
            </div>
            <input type="hidden" value="${currentPage}" id="currentPage">
        </div>
    </div>
</section>


<input type="hidden" id="symbol" value="${ftrademapping.fid }">
<jsp:include page="../comm/footer.jsp"></jsp:include>
<script type="text/javascript" src="${oss_url}/static/front2018/js/trade/trade.js?v=2"></script>
</body>
</html>
