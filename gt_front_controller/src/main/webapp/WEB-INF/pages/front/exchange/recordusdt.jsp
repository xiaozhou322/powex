<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ include file="../comm/include.inc.jsp"%>

<!doctype html>
<html>
<head> 
<%@ include file="../comm/basepath.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="../comm/link.inc.jsp" %>
<link href="${oss_url}/static/front2018/css/exchange/common.css?v=20181126201750" rel="stylesheet" type="text/css" />
<link href="${oss_url}/static/front2018/css/exchange/main.css?v=20181126201750" rel="stylesheet" type="text/css" />
<!-- <link href="${oss_url}/static/front2018/css/exchange/common.css?v=20181126201750" rel="stylesheet" type="text/css" />
<link href="${oss_url}/static/front2018/css/exchange/main.css?v=20181126201750" rel="stylesheet" type="text/css" /> -->

</head>
<body class="lw-UstdBody lw-UstdBody2">
	 
 <%@include file="../comm/header.jsp" %>

 <section>
        <div class="buyUstdMain stepsMain">
        <c:if test="${type==0}">
            <div class="steps">
                <ol>
                    <li class="fl active textLeft">
                        <span></span>
                        <em><spring:message code="financial.usdtrecharge.step1" /></em>
                    </li>
                    <li class="fl active textCenter">
                        <span></span>
                        <em><spring:message code="financial.usdtrecharge.step2" /></em>
                    </li>                    
                    <li class="fr active textRight">
                        <span></span>
                        <em><spring:message code="financial.usdtrecharge.step3" /></em>
                    </li>
                    <div class="clear"></div>
                </ol>
            </div>
          </c:if> 
           <c:if test="${type==1}">
            <div class="steps">
               <ol>
                    <li class="fl active textLeft">
                        <span></span>
                        <em><spring:message code="financial.usdtrecharge.step1" /></em>
                    </li>
                    <li class="fl textCenter">
                    </li>                    
                    <li class="fr active textRight">   
                        <span></span>
                        <em><spring:message code="financial.usdtrecharge.step3" /></em>
                    </li>
                    <div class="clear"></div>
                </ol>
            </div>
          </c:if> 
            <div class="steps_3">
             	 <c:if test="${type==0}">
                 <div class="step3Con textCenter">
                 	<c:if test="${fcapitaloperation.fstatus==2}">
	                    <div class="stepsAll curSteps">
	                         <img src="${oss_url}/static/front2018/images/exchange/ic01.png" alt="" />
	                         <h3 class="Color333"><spring:message code="financial.usdtrecharge.status2" /></h3>
	                         <p><a class="cblue" href="javascript:;" onclick="window.location.reload();"><spring:message code="financial.exchangeusdt.viewstate" /></a></p>
	                         <p class="Color999"><spring:message code="financial.usdtrecharge.status2.info" /></p>
	                     </div>
                     </c:if>      
                     <c:if test="${fcapitaloperation.fstatus==3}">              
	                     <div class="stepsAll stepsSuccess">
	                         <img src="${oss_url}/static/front2018/images/exchange/ic02.png" alt="" />
	                         <h3 class="Color333"><spring:message code="financial.usdtrecharge.status3" /></h3>
	                         <p><a class="cblue" href="/financial/index.html"><spring:message code="financial.usdtrecharge.status3.info" /></a></p>
	                     </div>
                     </c:if>     
                     <c:if test="${fcapitaloperation.fstatus==4}"> 
	                     <div class="stepsAll stepsFailure">
	                         <img src="${oss_url}/static/front2018/images/exchange/ic03.png" alt="" />
	                         <h3 class="Color333"><spring:message code="financial.usdtrecharge.status4" /></h3>
	                         <p class="cblue"> </p>
	                     </div>
	                 </c:if>
	                 <c:if test="${fcapitaloperation.fstatus==5}"> 
	                     <div class="stepsAll stepsFailure">
	                         <img src="${oss_url}/static/front2018/images/exchange/ic03.png" alt="" />
	                         <h3 class="Color333"><spring:message code="financial.usdtrecharge.status5" /></h3>
	                         <p class="cblue"><spring:message code="financial.usdtrecharge.status5.info" /></p>
	                     </div>
	                 </c:if>  
                 </div>
                 </c:if>
                  <c:if test="${type==1}">
                 <div class="step3Con textCenter">
                 	<c:if test="${fcapitaloperation.fstatus==2}">
	                    <div class="stepsAll curSteps">
	                         <img src="${oss_url}/static/front2018/images/exchange/ic01.png" alt="" />
	                         <h3 class="Color333"><spring:message code="financial.usdtrecharge.status2" /></h3>
	                         <p><a class="cblue" href="javascript:;" onclick="window.location.reload();"><spring:message code="financial.exchangeusdt.viewstate" /></a></p>
	                         <p class="Color999"><spring:message code="financial.usdtrecharge.status2.info" /></p>
	                     </div>
                     </c:if>      
                     <c:if test="${fcapitaloperation.fstatus==3}">              
	                     <div class="stepsAll stepsSuccess">
	                         <img src="${oss_url}/static/front2018/images/exchange/ic02.png" alt="" />
	                         <h3 class="Color333"><spring:message code="financial.usdtrewithdrawal.status3" /></h3>
	                         <p><a class="cblue" href="/financial/index.html"><spring:message code="financial.usdtrecharge.status3.info" /></a></p>
	                     </div>
                     </c:if>     
                     <c:if test="${fcapitaloperation.fstatus==4}"> 
	                     <div class="stepsAll stepsFailure">
	                         <img src="${oss_url}/static/front2018/images/exchange/ic03.png" alt="" />
	                         <h3 class="Color333"><spring:message code="financial.usdtrewithdrawal.status4" /></h3>
	                         <p class="cblue"></p>
	                     </div>
	                 </c:if>    
	                 <c:if test="${fcapitaloperation.fstatus==5}"> 
	                     <div class="stepsAll stepsFailure">
	                         <img src="${oss_url}/static/front2018/images/exchange/ic03.png" alt="" />
	                         <h3 class="Color333"><spring:message code="financial.usdtrewithdrawal.status5" /></h3>
	                         <p class="cblue"><spring:message code="financial.usdtrewithdrawal.status5.info" /></p>
	                     </div>
	                 </c:if>  
                 </div>
                 </c:if>
                 <div class="recodeMain">
                     <div class="usdtTit">
                         <ul>
                             <a href="/exchange/recordUsdt.html?type=0"><li class="fl <c:if test="${type==0}"> cur </c:if>"><spring:message code="financial.usdtrecharge.recrec" /></li></a>
                              <a href="/exchange/recordUsdt.html?type=1"><li class="fl <c:if test="${type==1}"> cur </c:if>"><spring:message code="financial.exchangeusdt.witrec" /></li></a>
                             <div class="clear"></div>
                         </ul>
                     </div>
                     <div class="recodeDetail <c:if test="${type==0}"> cur </c:if>" id="recordbody0">
                         <table>
                             <tr class="tr_1">
                                 <th><spring:message code="financial.usdtrecharge.orderno" /></th>
								<th><spring:message code="financial.usdtrecharge.remarknum" /></th>
								<th><spring:message code="financial.usdtrecharge.rechargetime" /></th>
								<th><spring:message code="financial.usdtrecharge.rechargemode" /></th>
								<th><spring:message code="financial.usdtrecharge.rechargeamount" /></th>
								<th><spring:message code="financial.usdtrecharge.rechargestatus" /></th>
                             </tr>
                             <c:forEach items="${fcapitaloperations}" var="v">
							<tr class="tr_1">
								<td class="gray">${v.fid }</td>
								<td>${v.fremark }</td>
								<td><fmt:formatDate value="${v.fcreateTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
								<td>${v.fremittanceType }</td>
								<td>￥${v.famount }($<ex:DoubleCut value="${v.famount/6.5 }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/>)</td>
								<td><c:if test="${v.fstatus!=1 }"><spring:message code="financial.cnyrecharge.status${v.fstatus }" /></c:if>
								<c:if test="${(v.fstatus==1 || v.fstatus==2)}">
								<a class="rechargecancel opa-link" href="javascript:void(0);" data-fid="${v.fid }"><spring:message code="financial.cnyrecharge.cancel" /></a>
								<c:if test="${v.fstatus==1}">
								&nbsp;|&nbsp;&nbsp;<a class="rechargesub opa-link" href="javascript:void(0);" data-fid="${v.fid }"><spring:message code="financial.cnyrecharge.submit" /></a>
								</c:if>
								</c:if>
								</td>
								
			                 </tr>
				          </c:forEach>
						  <c:if test="${fn:length(fcapitaloperations)==0 }">
							<tr>
								<td colspan="6" class="no-data-tips" align="center">
									<span>
										<spring:message code="financial.usdtrecharge.norecharge" />
									</span>
								</td>
							</tr>
						  </c:if>
                         </table>
                          <input type="hidden" value="${cur_page }" name="currentPage" id="currentPage"></input>
							<div class="text-center">
								${pagin }
							</div>
                     </div>                     
                     <div class="recodeDetail recodeDetail2 <c:if test="${type==1}"> cur </c:if>" id="recordbody1">
                         <table>
                             <tr class="tr_2">
                                <th><spring:message code="financial.usdtrewithdrawal.wittim" /></th>
								<th><spring:message code="financial.usdtrewithdrawal.account" /></th>
								<th><spring:message code="financial.usdtrewithdrawal.amount" /></th>
								<th><spring:message code="financial.usdtrewithdrawal.fee" /></th>
								<th><spring:message code="financial.actarr" /></th>
								<th><spring:message code="financial.usdtrewithdrawal.remarknum" /></th>
								<th><spring:message code="financial.usdtrewithdrawal.witstatus" /></th>
                             </tr>
                            
                            <c:forEach items="${fcapitaloperations }" var="v" varStatus="vs">
									<tr class="tr_2">
										<td><fmt:formatDate value="${v.fcreateTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
										<td>${v.fAccount }</td>
										<td>$${v.famount+v.ffees  }</td>
										<td>$${v.ffees }</td>
										<td>$${v.famount}(￥<ex:DoubleCut value="${v.famount*6.5}" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/>)</td>
										<td><font color="red">${v.fremark }</font></td>
										<td  class="opa-link"><span>
										<c:if test="${v.fstatus!=1 }"><spring:message code="financial.cnyrewithdrawal.status${v.fstatus }" /></c:if>
										<c:if test="${v.fstatus==1 }">
										<a href="/exchange/exchangeUsdt.html?code=${v.fremark }"><spring:message code="financial.usdtrewithdrawal.exchange" /></a>
										&nbsp;|&nbsp;
										<a class="cancelWithdrawusdt opa-link" href="javascript:void(0);" data-fid="${v.fid }"><spring:message code="financial.cancel" /></a>
										</c:if>
										</span></td>
									</tr>
						</c:forEach>
						<c:if test="${fn:length(fcapitaloperations)==0 }">
								<tr>
									<td colspan="7" class="no-data-tips">
										<span>
											<spring:message code="financial.cnyrewithdrawal.nowith" />
										</span>
									</td>
								</tr>
						</c:if>
                         </table>
                         <input type="hidden" value="${cur_page}" name="currentPage" id="currentPage"></input>
                         <input type="hidden" id="type" value="${type}">
							<div class="text-center">
								${pagin }
							</div>
                     </div>
                 </div>
            </div>
        </div>
    </section>


<%@include file="../comm/usdfooter.jsp" %>	

<script type="text/javascript" src="${oss_url}/static/front2018/js/comm/msg.js?v=20181126201750"></script>
<script type="text/javascript" src="${oss_url}/static/front2018/js/finance/account.withdraw.js?v=20181126201750"></script>
<script type="text/javascript" src="${oss_url}/static/front2018/js/finance/account.usdtrecharge.js?v=11"></script>
<script type="text/javascript" src="${oss_url}/static/front2018/js/finance/city.min.js?v=20181126201750"></script>
<script type="text/javascript" src="${oss_url}/static/front2018/js/finance/jquery.cityselect.js?v=20181126201750"></script>
<script type="text/javascript" src="${oss_url}/static/front2018/js/index/main.js?v=20181126201750"></script>

</body>
</html>
