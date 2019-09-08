<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
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
    <link href="${oss_url}/static/front/css/index/main.css?v=20181126201750" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="${oss_url}/static/front/js/index/main.js?v=20181126201750"></script>
<link rel="stylesheet" href="${oss_url}/static/front/css/finance/withdraw.css?v=20181126201750" type="text/css"></link>
</head>
<body>
	<input type="hidden" id="max_double" value="${fvirtualcointype.fmaxqty }">
	<input type="hidden" id="min_double" value="${fvirtualcointype.fminqty }">


 <%@include file="../comm/header.jsp" %>
<section class="lw-content">
    <div class="lw-finance">
        <div class="lw-financeLeft fl">
            <ul>

                <li ><a href="/financial/index.html"><spring:message code="financial.perass" /><i></i></a></li>
                <li ><a href="/account/rechargeBtc.html"><spring:message code="financial.recharge" /> <i></i></a></li>
                <li><a href="/account/withdrawBtc.html"><spring:message code="financial.withdrawal" /> <i></i></a></li>
                <li class="lw-cur"><a href="/account/transfer.html"><spring:message code="financial.transfer" /> <i></i></a></li>
                <li><a href="/account/record.html"><spring:message code="financial.accrecord" /><i></i></a></li>
                <li><a href="/financial/accountcoin.html"><spring:message code="financial.capacc" /><i></i></a></li>
                <li><a href="/financial/assetsrecord.html"><spring:message code="financial.assrec" /><i></i></a></li>
                <li><a href="/trade/entrust.html?status=0"><spring:message code="entrust.title.entrust" /><i></i></a></li>
                <li><a href="/trade/entrust.html?status=1"><spring:message code="entrust.title.deal" /><i></i></a></li>
                <li><a href="/introl/mydivide.html"><spring:message code="introl.title" /><i></i></a></li>
            </ul>
        </div>
        <div class="lw-financeRight fr">
            <h1 class="lw-financeTit">${fvirtualcointype.fShortName } <spring:message code="financial.transfer" /> </h1>
            <div class="lw-financeMain lw-depositMain lw-withdrawatMain">
                <div class="lw-coinTitList">
                    <ul>
                    <c:forEach items="${requestScope.constant['allTransferCoins'] }" var="v">
                      <li class="fl ${v.fid==symbol?'lw-active':'' }"><a href="/account/transfer.html?symbol=${v.fid }">${v.fShortName }</a></li>
                      </c:forEach>
                      <div class="clear"></div>
                    </ul>
                </div>
                <div class="lw-tixianArea">
                    <div action="">
                        <label for="">
                          <span class="fl s_2"><spring:message code="financial.transfer.accbal" /> </span>
                          <span class="fl lw-coinNums"><ex:DoubleCut value="${fvirtualwallet.ftotal }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/></span>
                        </label>
                        <label for="skruid" class="lw-txAddress">
                            <span class="fl s_2"><spring:message code="financial.transfer.uid" /> </span>
                            <input type="text" id="skruid" value="" class="fl txtArea" autocomplete="off"/>
                        </label>
                        <label for="skruname" class="lw-txAddress">
                            <span class="fl s_2"><spring:message code="financial.transfer.payee" /></span>
                            <input type="text" id="skruname" value="" class="fl txtArea" autocomplete="off"/>
                        </label>                        
                        <label for="withdrawAmount">
                            <span class="fl s_2"><spring:message code="financial.transfer.witamo" /></span>
                            <input type="text" id="withdrawAmount" value="" class="fl txtArea" autocomplete="off"/>
                            <span class="amounttips">
								<span>
									<spring:message code="financial.cnyrewithdrawal.fee" />
									<span id="free">0</span>
									<span>${fvirtualcointype.fShortName }</span>
								</span>
								<span>
									<spring:message code="financial.cnyrewithdrawal.arr" />
									<span style="font-size:15px!" id="amount" class="text-danger">0</span>
									<span style="font-size:15px!" class="text-danger">${fvirtualcointype.fShortName }</span>
								</span>
							</span>
                        </label>                        
                        
                        <label for="password">
                            <span class="fl s_2"><spring:message code="financial.tradingpwd" /></span>
                            <input id="tradePwd" type="password" value="" class="fl txtArea" autocomplete="off"/>
                        </label>
                        
                        <c:if test="${isBindTelephone == true }">		
            							<label for="withdrawPhoneCode" style="position:relative; width:500px;">
            								<span class="fl s_2"><spring:message code="security.smscode" /></span>
            								<input id="withdrawPhoneCode" class="fl txtArea" type="text" autocomplete="off">
            								<button id="withdrawsendmessage" data-msgtype="5" data-tipsid="withdrawerrortips" class="btn btn-sendmsg" style="right:27px; height:40px;"><spring:message code="financial.send" /></button>
            							</label>
            						</c:if>	


                        
						<c:if test="${isBindGoogle ==true}">                  
	                        <label for="withdrawTotpCode">
	                            <span class="fl s_2"><spring:message code="financial.goocod" /></span>
	                            <input id="withdrawTotpCode" type="text" value="" class="fl txtArea"  autocomplete="off"/>                      
	                        </label>
                        </c:if>
                        
                         <label for="diyMoney" style="margin:0 0 0 0;">
                           <span id="withdrawerrortips" class="text-danger"  style="margin:0 0 0 170px; display:block;">
											
										</span>                  
                        </label>                             
                        <label for="diyMoney">
                            <span class="fl s_2"></span>
                            <input type="button" id="withdrawBtcButton" value="<spring:message code="financial.transfer.immwit" />" class="fl txtArea lw-send" />
                        </label>
                    </div>
                    <div class="tixianCare lw-coinInstructions">
                        <h3><spring:message code="financial.transfer.witins" /></h3>
                        <p><spring:message code="financial.transfer.thsmininum"   arguments="${fvirtualcointype.fminqty },${fvirtualcointype.fmaxqty }" /></p>
                        <spring:message code="financial.witins.note" />
                    </div>
                </div>



                <div class="lw-Coinrecord">
                   <h2>${fvirtualcointype.fname } <spring:message code="financial.transfer.witrec" /></h2>
                   <table>
                      <tr>
                        <th width=300><spring:message code="financial.transfer.wittim" /></th>
                        <th width=300><spring:message code="financial.transfer.type" /></th>
                        <th width=300><spring:message code="financial.transfer.tranuid" /></th>
                        <th width=300><spring:message code="financial.transfer.payee" /></th>
                        <th width=100><spring:message code="financial.transfer.uid" /></th>
                        <th width=300><spring:message code="financial.transfer.witamo" /></th>
                        <th width=300><spring:message code="financial.transfer.witfee" /></th>
                        <th width=300><spring:message code="financial.actarr" /></th>
                        <th width=300><spring:message code="financial.transfer.witstatus" /></th>
                      </tr>
                      <c:forEach items="${fvirtualcaptualoperations }" varStatus="vs" var="v">
                   <tr>
                   <c:if test="${fuser.fid == v.fuser.fid }">
                     <td><fmt:formatDate value="${v.fcreateTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                     <td><spring:message code="financial.transfer.out" /></td>
                     <td>${v.fuser.fid }</td>
                     <td>${v.fischarge }</td>
                     <td>${v.withdraw_virtual_address }</td>
                     <td><ex:DoubleCut value="${v.famount+v.ffees }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/></td>
                     <td><ex:DoubleCut value="${v.ffees }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="6"/></td>
                     <td><ex:DoubleCut value="${v.famount }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="6"/></td>
                     <td><spring:message code="enum.capitalopera.transfer.${v.fstatus }" />
                    <c:if test="${v.fstatus==1 }">
                      &nbsp;|&nbsp;
                      <a class="cancelWithdrawBtc" href="javascript:void(0);" data-fid="${v.fid }"><spring:message code="financial.cancel" /></a>
                     </c:if></td>
                   </c:if>
                   <c:if test="${fuser.fid != v.fuser.fid }">
                     <td><fmt:formatDate value="${v.fcreateTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                     <td><spring:message code="financial.transfer.in" /></td>
                     <td>${v.fuser.fid }</td>
                     <td>${v.fischarge }</td>
                     <td>${v.withdraw_virtual_address }</td>
                     <td><ex:DoubleCut value="${v.famount+v.ffees }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/></td>
                     <td><ex:DoubleCut value="${v.ffees }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="6"/></td>
                     <td><ex:DoubleCut value="${v.famount }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="6"/></td>
                     <td><spring:message code="enum.capitalopera.transfer.${v.fstatus }" /></td>
                   </c:if>
                   </tr>
                   </c:forEach>
                 
                  <c:if test="${fn:length(fvirtualcaptualoperations)==0 }">
                        <tr>
                          <td colspan="7" class="no-data-tips">
                            <span>
                              <spring:message code="financial.cnyrewithdrawal.nowith" />
                            </span>
                          </td>
                        </tr>
                    </c:if>



                 
                   </table>
						<input type="hidden" value="${cur_page }" name="currentPage" id="currentPage"></input>
											<div class="text-right">
												${pagin }
											</div>
                </div>
            </div>
        </div>
    </div>
</section>
 
	<input type="hidden" id="isEmptyAuth" value="${isEmptyAuth }">
	<input type="hidden" id="symbol" value="${fvirtualcointype.fid }">
	<input type="hidden" value="<ex:DoubleCut value="${fvirtualwallet.ftotal }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/>" id="btcbalance" name="btcbalance">
	<input type="hidden" value="${fvirtualcointype.fShortName }" id="coinName" name="coinName">
	<input id="feesRate" type="hidden" value="${fee }">
	<input id="btcfee" type="hidden" value="${level }">
<%@include file="../comm/footer.jsp" %>	
	<script type="text/javascript" src="${oss_url}/static/front/js/comm/msg.js?v=20180110110659.js?v=20181126201750"></script>
  <script type="text/javascript" src="${oss_url}/static/front/js/finance/account.transfer.js?v=20180110110659.js?v=20181126201750"></script>
  <script type="text/javascript" src="${oss_url}/static/front/js/index/main.js?v=20180110110659.js?v=20181126201750"></script>
  <script type="text/javascript">
          $(".btnHover").hover(function() {
              $(this).css('color', '#fff');
          }, function() {
              $(this).css('color', '#fff');
          });

  </script>

</body>
</html>
