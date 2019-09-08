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

<link rel="stylesheet" href="${oss_url}/static/front/css/finance/withdraw.css?v=20181126201750" type="text/css"></link>
<link href="${oss_url}/static/front/css/index/main.css?v=20181126201750" rel="stylesheet" type="text/css" />
</head>
<body>
	<input type="hidden" id="max_double" value="${requestScope.constant['maxwithdrawcny'] }">
	<input type="hidden" id="min_double" value="${requestScope.constant['minwithdrawcny'] }">
	
<style type="text/css">
	.textBox .form-control,#withdrawCnyButton{width:302px;}
	#withdrawBlank{width:430px;}
	.withdraw .addtips{right:-88px;}
	.textBox .control-label{width:140px;}
</style>

  <%@include file="../comm/header.jsp" %>
	<section  class="lw-content">
		<div class="container-full padding-top-40">
			<div class="lw-finance">
				
		        <div class="lw-financeLeft fl">
		            <ul>

		                <li ><a href="/financial/index.html"><spring:message code="financial.perass" /><i></i></a></li>
		                <li ><a href="/account/rechargeBtc.html"><spring:message code="financial.recharge" /> <i></i></a></li>
		                <li class="lw-cur"><a href="/account/withdrawBtc.html"><spring:message code="financial.withdrawal" /> <i></i></a></li>
		                <li><a href="/account/record.html"><spring:message code="financial.accrecord" /><i></i></a></li>
		                <li><a href="/financial/accountcoin.html"><spring:message code="financial.capacc" /><i></i></a></li>
		                <li><a href="/financial/assetsrecord.html"><spring:message code="financial.assrec" /><i></i></a></li>
		                <li><a href="/trade/entrust.html?status=0"><spring:message code="entrust.title.entrust" /><i></i></a></li>
                		<li><a href="/trade/entrust.html?status=1"><spring:message code="entrust.title.deal" /><i></i></a></li>
		                
		            </ul>
		        </div>
				<div class="lw-financeRight fr">
					<h1 class="lw-financeTit"><spring:message code="financial.cnyrewithdrawal" /></h1>
					<div class="withdraw lw-financeMain" style="overflow:hidden;">
						<div class="rightarea-con lw-coinTitList">
							<ul>
								<li class="fl lw-active"><a href="/account/withdrawCny.html">CNY</a></li>
								<c:forEach items="${requestScope.constant['allWithdrawCoins'] }" var="v">
									<li class="fl ${v.fid==symbol?'lw-active':'' }">
										<a href="/account/withdrawBtc.html?symbol=${v.fid }">${v.fShortName }</a>
									</li>
								</c:forEach>
								<div class="clear"></div>							
							</ul>
						</div>
						<div class="col-xs-12 padding-clear" style="font-size:14px;">
							<div class="col-xs-7 textBox padding-clear form-horizontal">
							    <div class="form-group" style="margin:28px 0 5px 0; font-size:14px;">
									<span for="withdrawAmount" class="col-xs-3 control-label"><spring:message code="financial.cnyrewithdrawal.balance" /></span>
									<div class="col-xs-7 textBox">
										<span class="form-control border-fff" style="padding:0; font-weight:bold; font-size:14px;">￥<ex:DoubleCut value="${fwallet.ftotal }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/></span>
									</div>
								</div>
								<div class="form-group" style="margin:28px 0 5px 0; width:670px;">
									<span for="diyMoney" class="col-xs-3 control-label"><spring:message code="financial.cnyrewithdrawal.card" /></span>
									<div class="col-xs-7 textBox" style="width:430px;">
										<select id="withdrawBlank" class="form-control">
											 <c:forEach items="${fbankinfoWithdraws }" var="v">
													<option value="${v.fid }">${v.fname }&nbsp;&nbsp;<spring:message code="financial.cnyrewithdrawal.cardnum" />${v.fbankNumber }</option>
											</c:forEach>		
										</select>
										<a href="#" class="text-primary addtips" data-toggle="modal" data-target="#withdrawCnyAddress"><spring:message code="financial.toadd" /> >></a>
									</div>
								</div>
								<div class="form-group" style="margin:28px 0 5px 0;">
									<span for="withdrawBalance" class="col-xs-3 control-label"><spring:message code="financial.cnyrewithdrawal.amount" /></span>
									<div class="col-xs-7 textBox">
										<input id="withdrawBalance" class="form-control" type="text" autocomplete="off">
										<span class="amounttips">
											<span>
												<spring:message code="financial.cnyrewithdrawal.fee" />
												<span id="free">0</span>
												<span>CNY</span>
											</span>
											<span>
												<spring:message code="financial.cnyrewithdrawal.arr" />
												<span style="font-size:15px!" id="amount" class="text-danger">0</span>
												<span style="font-size:15px!" class="text-danger">CNY</span>
											</span>
										</span>
									</div>
								</div>
								<div class="form-group" style="margin:0 0 5px 0;">
									<span for="tradePwd" class="col-xs-3 control-label"><spring:message code="financial.tradingpwd" /></span>
									<div class="col-xs-7 textBox">
										<input id="tradePwd" class="form-control" type="password" autocomplete="off">
									</div>
								</div>
							
							<c:if test="${isBindTelephone == true }">		
									<div class="form-group>" style="margin:28px 0 5px 0; overflow:hidden;">
										<span for="withdrawPhoneCode" class="col-xs-3 control-label"><spring:message code="security.smscode" /></span>
										<div class="col-xs-7 textBox" style="position:relative; width:331px;">
											<input id="withdrawPhoneCode" class="form-control" type="text" autocomplete="off">
											<button id="withdrawsendmessage" data-msgtype="4" data-tipsid="withdrawerrortips" class="btn btn-sendmsg"><spring:message code="financial.send" /></button>
										</div>
									</div>
							</c:if>		
								
							<c:if test="${isBindGoogle ==true}">	
									<div class="form-group" style="margin:28px 0 5px 0;">
										<span for="withdrawTotpCode" class="col-xs-3 control-label"><spring:message code="financial.goocod" /></span>
										<div class="col-xs-7 textBox">
											<input id="withdrawTotpCode" class="form-control" type="text" autocomplete="off">
										</div>
									</div>
							</c:if>		
								
								<div class="form-group" style="margin:0 0 5px 0;">
									<span for="withdrawerrortips" class="col-xs-3 control-label"></span>
									<div class="col-xs-7 textBox">
										<label id="withdrawerrortips" class="text-danger">
											
										</label>
									</div>
								</div>
								<div class="form-group" style="margin:28px 0 5px 0;">
									<span for="withdrawCnyButton" class="col-xs-3 control-label"></span>
									<div class="col-xs-7 textBox">
										<button id="withdrawCnyButton" class="btn btn-danger btn-block" style="background:#ff5818; border:none;"><spring:message code="financial.immwit" /></button>
									</div>
								</div>
							</div>
							<div class="col-xs-12 padding-clear">
								<div class="tixianCare  lw-coinInstructions" style="font-size:14px;">
									<div class="panel-header">
										<h3 class="panel-title"><spring:message code="financial.witins" /></h3>
									</div>
									<div class="">
										<p><spring:message code="financial.cnyrewithdrawal.thsmininum"   arguments="${requestScope.constant['minwithdrawcny'] },${requestScope.constant['maxwithdrawcny'] }" /></p>
										<spring:message code="financial.cnyrewithdrawal.note" arguments="${fee*100 }" />
									</div>
								</div>
							</div>
							<div class="col-xs-12 padding-clear padding-top-30" style="font-size:14px;">
								<div class="">
									<div class="lw-Coinrecord" style="line-height:28px; margin-bottom:5px;">
										<h2 class="fl" style="margin:0!important; padding-top:8px; display:block;">CNY <spring:message code="financial.witrec" /></h2>
										<%-- <span class="pull-right fr recordtitle" data-type="0" data-value="0"><spring:message code="financial.cnyrecharge.folded" /> -</span> --%>
										<div class="clear"></div>
									</div>
									<div  id="recordbody0" class="">
										<table class="" width="100%">
											<tr>
												<th><spring:message code="financial.wittim" /></th>
												<th><spring:message code="financial.cnyrewithdrawal.account" /></th>
												<th><spring:message code="financial.cnyrewithdrawal.amount" /></th>
												<th><spring:message code="financial.witfee" /></th>
												<th><spring:message code="financial.actarr" /></th>
												<th><spring:message code="financial.cnyrewithdrawal.remarknum" /></th>
												<th><spring:message code="financial.witstatus" /></th>
											</tr>
											
											<c:forEach items="${fcapitaloperations }" var="v" varStatus="vs">
														<tr>
															<td><fmt:formatDate value="${v.fcreateTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
															<td>开户行: ${v.fBank }<br>姓名: ${v.fPayee }<br>帐号:${v.fAccount }</td>
															<td>￥${v.famount }</td>
															<td>￥${v.ffees }</td>
															<td>￥${v.famount-v.ffees }</td>
															<td><font color="red">${v.fid }</font></td>
															<td  class="opa-link"><span title="${v.fstatus==2?'平台已将款汇往您的账户，如暂时没有收到款项，是银行系统延时，请稍作等待。':'' }">
															${v.fstatus_s }
															<c:if test="${v.fstatus==1 }">
															&nbsp;|&nbsp;
															<a class="cancelWithdrawcny opa-link" href="javascript:void(0);" data-fid="${v.fid }">取消</a>
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
										
										<input type="hidden" value="${cur_page }" name="currentPage" id="currentPage"></input>
											<div class="text-right">
												${pagin }
											</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				</div>
			</div>
		</div>
	</section>
	<div class="modal modal-custom fade" id="withdrawCnyAddress" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-mark"></div>
			<div class="modal-content" style="border-radius: 8px; margin-top: 290px;">
				<div class="modal-header">
					<button type="button" class="close btn-modal" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<span class="modal-title" id="exampleModalLabel"><spring:message code="financial.cnyrewithdrawal.addcard" /></span>
				</div>
				<div class="modal-body form-horizontal">
					<div class="form-group ">
						<span for="payeeAddr" class="col-xs-3 control-label" ><spring:message code="financial.cnyrewithdrawal.accname" /></span>
						<div class="col-xs-8">
							<input id="payeeAddr" class="form-control" type="text" autocomplete="off" value="${fuser.frealName }" readonly="readonly"/>
							<span class="help-block text-danger">*<spring:message code="financial.cnyrewithdrawal.nametip" /></span>
						</div>
					</div>
					<div class="form-group ">
						<span for="withdrawAccountAddr" class="col-xs-3 control-label"><spring:message code="financial.cnyrewithdrawal.account" /></span>
						<div class="col-xs-8">
							<input id="withdrawAccountAddr" class="form-control" type="" text>
						</div>
					</div>
					<div class="form-group ">
						<span for="withdrawAccountAddr2" class="col-xs-3 control-label"><spring:message code="financial.cnyrewithdrawal.confirmcard" /></span>
						<div class="col-xs-8">
							<input id="withdrawAccountAddr2" class="form-control" type="" text>
						</div>
					</div>
					<div class="form-group ">
						<span for="openBankTypeAddr" class="col-xs-3 control-label"><spring:message code="financial.cnyrewithdrawal.deposit" /></span>
						<div class="col-xs-8">
							<select id="openBankTypeAddr" class="form-control">
								<option value="-1">
									<spring:message code="financial.cnyrewithdrawal.choosebank" />
								</option>
								<c:forEach items="${bankTypes }" var="v">
									<option value="${v.key }">${v.value }</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div id="prov_city" class="form-group ">
						<span for="prov" class="col-xs-3 control-label"><spring:message code="financial.cnyrewithdrawal.addr" /></span>
						<div class="col-xs-8 ">
							<div class="col-xs-4 padding-right-clear padding-left-clear margin-bottom-15">
								<select id="prov" class="form-control">
								</select>
							</div>
							<div class="col-xs-4 padding-right-clear margin-bottom-15">
								<select id="city" class="form-control">
								</select>
							</div>
							<div class="col-xs-4 padding-right-clear margin-bottom-15">
								<select id="dist" class="form-control prov">
								</select>
							</div>
							<div class="col-xs-12 padding-right-clear padding-left-clear">
								<input id="address" class="form-control" type="text" autocomplete="off" placeholder="<spring:message code="financial.cnyrewithdrawal.addrtip" />"/>
							</div>
						</div>
					</div>
				<c:if test="${isBindTelephone == true }">	
						<div class="form-group">
							<span for="addressPhoneCode" class="col-xs-3 control-label"><spring:message code="security.smscode" /></span>
							<div class="col-xs-8">
								<input id="addressPhoneCode" class="form-control" type="text" autocomplete="off">
								<button id="bindsendmessage" data-msgtype="10" data-tipsid="binderrortips" class="btn btn-sendmsg"><spring:message code="financial.send" /></button>
							</div>
						</div>
				</c:if>		
					
				<c:if test="${isBindGoogle ==true}">		
						<div class="form-group">
							<span for="addressTotpCode" class="col-xs-3 control-label"><spring:message code="financial.goocod" /></span>
							<div class="col-xs-8">
								<input id="addressTotpCode" class="form-control" type="text" autocomplete="off">
							</div>
						</div>
				</c:if>		
					
					<div class="form-group">
						<span for="binderrortips" class="col-xs-3 control-label"></span>
						<div class="col-xs-8">
							<span id="binderrortips" class="text-danger"></span>
						</div>
					</div>
					<div class="form-group">
						<span for="withdrawCnyAddrBtn" class="col-xs-3 control-label"></span>
						<div class="col-xs-8">
							<button id="withdrawCnyAddrBtn" class="btn btn-danger btn-block"><spring:message code="financial.cnyrewithdrawal.submit" /></button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	
	<input id="feesRate" type="hidden" value="${fee }">
	<input id="userBalance" type="hidden" value="${requestScope.fwallet.ftotal }">
	


<%@include file="../comm/footer.jsp" %>	
	<script type="text/javascript" src="${oss_url}/static/front/js/comm/msg.js?v=20181126201750"></script>
	<script type="text/javascript" src="${oss_url}/static/front/js/finance/account.withdraw.js?v=20181126201750"></script>
	<script type="text/javascript" src="${oss_url}/static/front/js/finance/city.min.js?v=20181126201750"></script>
	<script type="text/javascript" src="${oss_url}/static/front/js/finance/jquery.cityselect.js?v=20181126201750"></script>
	<script type="text/javascript" src="${oss_url}/static/front/js/index/main.js?v=20181126201750"></script>

</body>
</html>
