<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../comm/include.inc.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;if (request.getServerName().equals("www.gbcax.com")){basePath="https://www.gbcax.com";}
%>

<!doctype html>
<html>
<head> 
<base href="<%=basePath%>" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="../comm/link.inc.jsp" %>

</head>
<body>
  <%@include file="../comm/header.jsp" %>
<section>
    <div class="mg">
        <div class="l_finance clear">
            <div class=" fl">
                <div class="firstNav">
                   <%@include file="../comm/left_menu.jsp" %>
                </div>
            </div>
            <div class="l_financeR fr">
                <div class="personal_title">
                    <h2 class="assetTitle"><spring:message code="new.recode" /></h2>
                    <div class="asset-status-box">
                        <div class="choiceTime">
                            <span>
                                <svg class="icon" aria-hidden="true">
                                   <use xlink:href="#icon-hongbaoguanli"></use>
                                 </svg><spring:message code="financial.record.starttime" />
                            </span>
                            <input class="databtn datainput" id="begindate" value="${begindate }" readonly="readonly">
                            <span class="databtn datatips">to</span>
                            <input class="databtn datainput" id="enddate" value="${enddate }" readonly="readonly">
                            <span class="databtn datatime  ${datetype==1?'active':'' }" data-type="1"><spring:message code="financial.record.today" /></span>
                            <span class="databtn datatime ${datetype==2?'active':'' }" data-type="2">7<spring:message code="financial.record.days" /></span>
                            <span class="databtn datatime ${datetype==3?'active':'' }" data-type="3">15<spring:message code="financial.record.days" /></span>
                            <span class="databtn datatime ${datetype==4?'active':'' }" data-type="4">30<spring:message code="financial.record.days" /></span>
                            <input type="hidden" id="datetype" value="${datetype }">
                        </div>
                    </div>
                </div>
                <div class="assetList">
                    <div class="assetList_title clear" style="margin:30px 0;">
                        <h2 class="assetTitle fl"><spring:message code="new.bill" /></h2>                    
                        <select class="typeselect fl" name="" class="fl" id="recordType">
                        	<c:forEach items="${filters }" var="v">
                           		<c:choose>
									<c:when test="${select==v.value}">
										<option value="${v.key }" selected="selected">${v.value }</option>
									 </c:when>
									 	<c:otherwise>
											<option value="${v.key }">${v.value }</option>
									 </c:otherwise>
								</c:choose>
							</c:forEach>
                        </select>
                    </div>

                    <div class="assetCon_list accountSettings">
                        <ol class="infoTit clear">
                            <li><spring:message code="financial.record.tradtime" /></li>
                            <li><spring:message code="financial.record.type" /></li>
                            <li>
                            	 <c:choose>
									<c:when test="${recordType==1 || recordType==2 }">
										<spring:message code="financial.record.amount" />
									</c:when>
								<c:when test="${recordType==3 || recordType==4 || recordType==5 || recordType==6 }">
										<spring:message code="financial.record.qty" />
									</c:when>
								</c:choose>
                            </li>
                            <li><spring:message code="financial.cnyrewithdrawal.fee" /></li>
                            <li><spring:message code="financial.cnyrecharge.rechargestatus" /></li>
                        </ol>
                        <ul class="infoCon">
                        	<c:choose>
                        	<c:when test="${recordType==1 || recordType==2 }">
                        	<c:forEach items="${list }" var="v">
                            <li>
                                <div class="coinInfo">
                                    <span class="s-1"><fmt:formatDate value="${v.fcreateTime }" pattern="yyyy-MM-dd HH:mm:ss"/></span>
                                    <span class="s-1 cred s_type">${select }</span>
                                    <span class="s-1">￥<ex:DoubleCut value="${v.famount }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/></span>
                                    <span class="s-1">￥<ex:DoubleCut value="${v.ffees }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/></span>
                                    <span class="s-1 cgreen">${v.fstatus_s }
                                    
                                      <c:if test="${v.fstatus==1 }">
                                           <c:if test="${recordType==1 }"> <a class="rechargecancel opa-link" href="javascript:void(0);" data-fid="${v.fid }">取消</a> </c:if>
                                           <c:if test="${recordType==2 }"> <a class="cancelWithdrawcny opa-link" href="javascript:void(0);" data-fid="${v.fid }">取消</a></c:if>                                    
                                     
                                      </c:if>                                      
                                    </span>
                                    <div class="clear"></div>
                                </div>
                            </li>
                            </c:forEach>
                            </c:when>

                            <c:when test="${recordType==3 || recordType==4 }">
                            <c:forEach items="${list }" var="v">                            
                          	  <li>
                                <div class="coinInfo">
                                    <span class="s-1"><fmt:formatDate value="${v.fcreateTime }" pattern="yyyy-MM-dd HH:mm:ss"/></span>
                                    <span class="s-1 cred s_type">${select }</span>
                                    <span class="s-1">${v.fvirtualcointype.fSymbol }<ex:DoubleCut value="${v.famount }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/></span>
                                    <span class="s-1">${v.fvirtualcointype.fSymbol }<ex:DoubleCut value="${v.ffees }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/></span>
                                    <span class="s-1 cred">${v.fstatus_s }
                                      <c:if test="${v.fstatus==1 }">
                                      <c:if test="${recordType==4 }">
                                        <a class="cancelWithdrawBtc opa-link" href="javascript:void(0);" data-fid="${v.fid }">取消</a>
                                      </c:if>
                                    
                                      
                                      </c:if>
                                    </span>
                                    <div class="clear"></div>
                                </div>
                            </li>
                            </c:forEach>
							</c:when> 

						  <c:when test="${recordType==5 }">
							 <c:forEach items="${list }" var="v">                          
                            <li>
                                <div class="coinInfo">
                                    <span class="s-1"><fmt:formatDate value="${v.fcreateTime }" pattern="yyyy-MM-dd HH:mm:ss"/></span>
                                    <span class="s-1 cred s_type">${select }</span>
                                    <span class="s-1">$<ex:DoubleCut value="${v.famount/6.5 }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/>
								(￥<ex:DoubleCut value="${v.famount }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/>)</span>
                                    <span class="s-1">$<ex:DoubleCut value="${v.ffees }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/></span>
                                    <span class="s-1 cgreen">${v.fstatus_s }
                                    
                                    <c:if test="${v.fstatus==1 }"><a class="cancelRechargeUSDT opa-link" href="javascript:void(0);" data-fid="${v.fid }">取消</a></c:if>
                                    </span>
                                    <div class="clear"></div>
                                </div>
                            </li>
								</c:forEach>
						</c:when>
							

						<c:when test="${recordType==6 }">
							<%--USDT提现--%>
							<c:forEach items="${list }" var="v">
                              <li>
                                <div class="coinInfo">
                                    <span class="s-1"><fmt:formatDate value="${v.fcreateTime }" pattern="yyyy-MM-dd HH:mm:ss"/></span>
                                    <span class="s-1 cred s_type">${select }</span>
                                    <span class="s-1">$<ex:DoubleCut value="${v.famount }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/>
								(￥<ex:DoubleCut value="${v.famount*6.5 }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/>)</span>
                                    <span class="s-1">$<ex:DoubleCut value="${v.ffees }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/></span>
                                    <span class="s-1 cred">${v.fstatus_s }
                                      <c:if test="${v.fstatus==1 }"><a class="cancelWithdrawusdt opa-link" href="javascript:void(0);" data-fid="${v.fid }"><spring:message code="financial.cancel" /></a></c:if>
                                    </span>
                                    <div class="clear"></div>
                                </div>
                            </li> 

                           </c:forEach>
						</c:when>
					    </c:choose>                           
                          
                          <c:if test="${fn:length(list)==0 }">		
									<span class='noMsg' >
										<spring:message code="financial.record.nobill" />
									</span>
							
						</c:if>
                                                      
                        </ul>
                        <c:if test="${!empty(pagin) }">
                        <div class="page">
                           ${pagin }
                        </div>
                        </c:if>	
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>

	
	
 
<%@include file="../comm/footer.jsp" %>	


	<script type="text/javascript" src="${oss_url}/static/front2018/js/plugin/My97DatePicker/WdatePicker.js?v=20181126201750"></script>
	<script type="text/javascript" src="${oss_url}/static/front2018/js/finance/account.record.js?v=20181126201750"></script>
	 <script type="text/javascript" src="${oss_url}/static/front2018/js/finance/account.withdraw.js?v=20181126201750"></script>
	   <script type="text/javascript" src="${oss_url}/static/front2018/js/finance/account.recharge.js?v=20181126201750"></script>
	   <script type="text/javascript" src="${oss_url}/static/front2018/js/finance/account.usdtrecharge.js?v=14"></script>
	<script type="text/javascript" src="${oss_url}/static/front2018/js/index/main.js?v=20181126201750"></script>

</body>
</html>
