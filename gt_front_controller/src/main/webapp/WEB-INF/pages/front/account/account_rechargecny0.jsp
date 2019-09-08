<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ include file="../comm/include.inc.jsp"%>
<!doctype html>
<html>
<head> 
<%@ include file="../comm/basepath.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="../comm/link.inc.jsp" %>

<link rel="stylesheet" href="${oss_url}/static/front/css/finance/recharge.css?v=20181126201750" type="text/css"></link>
<link href="${oss_url}/static/front/css/index/main.css?v=20181126201750" rel="stylesheet" type="text/css" />

</head>
<body>
	




 
 <%@include file="../comm/header.jsp" %>

<!-- 	<div class="container-full padding-top-40">
		<div class="container displayFlex">
			 -->

  <div class=" lw-content">
    <div class="lw-finance">
         <div class="lw-financeLeft fl">
            <ul>
                <li ><a href="/financial/index.html"><spring:message code="financial.perass" /><i></i></a></li>
                <li class="lw-cur"><a href="/account/rechargeBtc.html"><spring:message code="financial.recharge" /> <i></i></a></li>
                <li><a href="/account/withdrawBtc.html"><spring:message code="financial.withdrawal" /> <i></i></a></li>
                <li><a href="/account/record.html"><spring:message code="financial.accrecord" /><i></i></a></li>
                <li><a href="/financial/accountcoin.html"><spring:message code="financial.capacc" /><i></i></a></li>
                <li><a href="/financial/assetsrecord.html"><spring:message code="financial.assrec" /><i></i></a></li>
				<li><a href="/trade/entrust.html?status=0"><spring:message code="entrust.title.entrust" /><i></i></a></li>
                <li><a href="/trade/entrust.html?status=1"><spring:message code="entrust.title.deal" /><i></i></a></li>
				<li><a href="/introl/mydivide.html"><spring:message code="introl.title" /><i></i></a></li>
            </ul>
          </div>






			<div class="col-xs-10 lw-financeRight" style="overflow:hidden;">
			    <h1 class="lw-financeTit"><spring:message code="financial.cnyrecharge" /></h1>

				<div class="lw-financeMain recharge">
					<div class="lw-coinTitList rightarea-con">
						<ul>
							<li class="fl lw-active">
								<a href="/account/rechargeCny.html?type=1">CNY</a>
							</li>
							<c:forEach items="${requestScope.constant['allRechargeCoins'] }" var="v">
								<li class="fl ${v.fid==symbol?'lw-active':'' }">
									<a href="/account/rechargeBtc.html?symbol=${v.fid }">${v.fShortName }</a>
								</li>
							</c:forEach>
							<div class="clear"></div>
						</ul>
					</div>


						<div class="col-xs-12 padding-clear padding-top-40" style="margin-top:30px;">
				<!-- 			<a class="recharge-type alipay" href="/account/rechargeCny.html?type=3"> </a>
							<a class="recharge-type wechat" href="/account/rechargeCny.html?type=4"></a> -->
						</div>
						
						<div class="col-xs-12 padding-clear">
							<div class="col-xs-12 recharge-box clearfix">
								<div class="col-xs-9 rechargeBankBox padding-top-30">
									<a class="recharge-type bank active" href="/account/rechargeCny.html?type=0"><spring:message code="financial.cnyrecharge.card" /></a>
									<span class="recharge-process">
										<span id="rechargeprocess1" class="col-xs-4 active">
											<span class="recharge-process-line"></span>
											<span class="recharge-process-icon"><i></i></span>
											<span class="recharge-process-text"><spring:message code="financial.cnyrecharge.step1" /></span>
										</span>
										<span id="rechargeprocess2" class="col-xs-4">
											<span class="recharge-process-line"></span>
											<span class="recharge-process-icon recharge-process-icon2"><i></i></span>
											<span class="recharge-process-text"><spring:message code="financial.cnyrecharge.step2" /></span>
										</span>
										<span id="rechargeprocess4" class="col-xs-4">
											<span class="recharge-process-line"></span>
											<span class="recharge-process-icon recharge-process-icon3"><i></i></span>
											<span class="recharge-process-text"><spring:message code="financial.cnyrecharge.step3" /></span>
										</span>
									</span>
									<div class="col-xs-12 padding-clear padding-top-30">
										<div id="rechage1" class="col-xs-12 padding-clear form-horizontal" style="font-size:14px; font-weight:normal; color:#666;">
											<div class="form-group">
												<span for="sbank" class="col-xs-2 control-label"><spring:message code="financial.cnyrecharge.bank" /></span>
												<div class="col-xs-7" style="width:365px;">
													<select id="sbank" class="form-control">
														<c:forEach items="${bankInfo }" var="v">
															<option value="${v.fid }">${v.fbankName }</option>
														</c:forEach>
													</select>
												</div>
											</div>											
											<div class="form-group ">
												<span for="diyMoney" class="col-xs-2 control-label"><spring:message code="financial.cnyrecharge.amount" /></span>
												<div class="col-xs-7" style="width:365px;">
													<input id="diyMoney" class="form-control" type="text" autocomplete="off">
													<input type="hidden" value="0.${randomMoney }" id="random">
													<span for="diyMoney" class="control-label randomtips">.${randomMoney }</span>
												</div>
											</div>
											<div class="form-group">
												<span for="rechargebtn" class="col-xs-2 control-label"></span>
												<div class="col-xs-7" style="width:365px;">
													<button id="rechargebtn" class="btn btn-danger btn-block"><spring:message code="financial.cnyrecharge.genRem" /></button>
												</div>
											</div>
										</div>
										<div id="rechage2" class="col-xs-6 padding-clear form-horizontal" style="display:none;">
											<div class="form-group">
												<span><spring:message code="financial.cnyrecharge.note" /></span>
											</div>
											<div class="form-group">
											<div class="recharge-infobox">
													<div class="form-group">
														<span><spring:message code="financial.cnyrecharge.payee" /></span> <span id="fownerName">xx</span>
													</div>
													<div class="form-group">
														<span><spring:message code="financial.cnyrecharge.collaccount" /></span> <span id="fbankNumber">--</span>
													</div>
													<div class="form-group">
														<span><spring:message code="financial.cnyrecharge.deposit" /></span> <span id="fbankAddress">--</span>
													</div>
													<div class="form-group">
														<span><spring:message code="financial.cnyrecharge.payamount" /></span> <span id="bankMoney" class="text-danger font-size-16">--</span>
													</div>
													<div class="form-group">
														<span><spring:message code="financial.cnyrecharge.note2" /></span> <span id="bankInfo" class="text-danger font-size-16">--</span>
													</div>
													<div class="form-group margin-bottom-clear">
														<span class="control-label text-left text-danger rechage-tips margin-bottom-clear" style="border-top-color:#fff0e4;line-height: 18px;padding-left: 0;display: inline-block;">
														<i class="icon"></i> <spring:message code="financial.cnyrecharge.note3" /> <span id="bankInfotips">--</span>
														</span>
													</div>
												</div>
											 </div>	
											<div class="form-group">
											<span for="diyMoney" class="col-xs-2 control-label"></span>
												<div class="col-xs-6 padding-left-clear">
													<button id="rechargenextbtn" class="btn btn-danger btn-block"><spring:message code="financial.cnyrecharge.completenext" /></button>
												</div>
											</div>
										</div>
										<div id="rechage3" class="col-xs-7 padding-clear form-horizontal" style="display:none;">
											<div class="form-group ">
												<span for="fromBank" class="col-xs-2 control-label"><spring:message code="financial.cnyrecharge.remitbank" /></span>
												<div class="col-xs-6">
													<select id="fromBank" class="form-control">
														<option value="-1">
															<spring:message code="financial.cnyrecharge.choosebank" />
														</option>
														<c:forEach items="${bankTypes }" var="v">
															<option value="${v.key }">${v.value }</option>
														</c:forEach>
													</select>
												</div>
											</div>
											<div class="form-group ">
												<span for="fromAccount" class="col-xs-2 control-label"><spring:message code="financial.cnyrecharge.bankacc" /></span>
												<div class="col-xs-6">
													<input id="fromAccount" class="form-control" type="text" autocomplete="off">
												</div>
											</div>
											<div class="form-group ">
												<span for="fromPayee" class="col-xs-2 control-label"><spring:message code="financial.cnyrecharge.accname" /></span>
												<div class="col-xs-6">
													<input id="fromPayee" class="form-control" type="text" autocomplete="off">
												</div>
											</div>
											<div class="form-group ">
												<span for="fromPhone" class="col-xs-2 control-label"><spring:message code="financial.cnyrecharge.phonenumber" /></span>
												<div class="col-xs-6">
													<input id="fromPhone" class="form-control" type="text" autocomplete="off" value="">
												</div>
											</div>
											<div class="form-group">
												<span for="rechargesuccessbtn" class="col-xs-2 control-label"></span>
												<div class="col-xs-6">
													<button id="rechargesuccessbtn" class="btn btn-danger btn-block"><spring:message code="financial.cnyrecharge.submit" /></button>
												</div>
											</div>
										</div>
										<div id="rechage4" class="col-xs-7 padding-clear form-horizontal" style="display:none;">
											<span class="rechare-success">
												<span class="success-icon"><spring:message code="financial.cnyrecharge.submitinfo" /></span>
												<a href="/account/rechargeCny.html?type=0"><spring:message code="financial.cnyrecharge.nextrecharge" /> >></a>
												<p><spring:message code="financial.cnyrecharge.waitinfo" /></p>
											</span>
										</div>
									</div>
								</div>
<!-- 								<div class="col-xs-4 padding-clear text-center jcLink">
									<a target="_blank"  href="/about/index.html?id=44" class="recharge-help"> </a>
								</div> -->
							</div>
							<div class="col-xs-12 padding-clear">
								<div class="lw-coinInstructions" style="font-size:14px;">
									<div class="panel-header">
										<h3 class="panel-title"><spring:message code="financial.recins" /></h3>
									</div>
									<div>
									    <p class="word-break:break-all;"><spring:message code="financial.cnyrecharge.thsmininum"   arguments="${requestScope.constant['minrechargecny'] },${requestScope.constant['minrechargecny'] }" /></p>
										<spring:message code="financial.cnyrecharge.note4" />
									</div>
								</div>
							</div>
							<div class="col-xs-12 padding-clear padding-top-30" style="font-size:14px;">
								<div class="">
									<div class="lw-Coinrecord" style="margin-bottom:5px;">
										<span class="">CNY <spring:message code="financial.recrec" /></span>
										<span style="display:none;" class="pull-right recordtitle" data-type="0" data-value="0"><spring:message code="financial.cnyrecharge.folded" /> -</span>
									</div>
									<div  id="recordbody0" class="">
										<table class="table" width="100%">
											<tr>
												<th><spring:message code="financial.cnyrecharge.orderno" /></th>
												<th><spring:message code="financial.cnyrewithdrawal.remarknum" /></th>
												<th><spring:message code="financial.cnyrecharge.rechargetime" /></th>
												<th><spring:message code="financial.cnyrecharge.rechargemode" /></th>
												<th><spring:message code="financial.cnyrecharge.rechargeamount" /></th>
												<th><spring:message code="financial.cnyrecharge.rechargestatus" /></th>
												<th><spring:message code="financial.cnyrecharge.rechargeoper" /></th>
											</tr>
											 <c:forEach items="${list}" var="v">
													<tr>
														<td class="gray">${v.fid }</td>
														<td>${v.fremark }</td>
														<td><fmt:formatDate value="${v.fcreateTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
														<td>${v.fremittanceType }</td>
														<td>${v.famount }</td>
														<td><spring:message code="financial.cnyrecharge.status${v.fstatus }" /></td>
														<td class="opa-link">
														<c:if test="${(v.fstatus==1 || v.fstatus==2)}">
														<a class="rechargecancel opa-link" href="javascript:void(0);" data-fid="${v.fid }"><spring:message code="financial.cnyrecharge.cancel" /></a>
														<c:if test="${v.fstatus==1}">
														&nbsp;|&nbsp;&nbsp;<a class="rechargesub opa-link" href="javascript:void(0);" data-fid="${v.fid }"><spring:message code="financial.cnyrecharge.submit" /></a>
														</c:if>
														</c:if>
														<c:if test="${(v.fstatus==3 || v.fstatus==4)}">
														--
														</c:if>
														</td>
									                 </tr>
									          </c:forEach>
											  <c:if test="${fn:length(list)==0 }">
												<tr>
													<td colspan="6" class="no-data-tips" align="center">
														<span>
															<spring:message code="financial.cnyrecharge.norecharge" />
														</span>
													</td>
												</tr>
											  </c:if>
										</table>
										
											
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
	<input type="hidden" value="${currentPage }" name="currentPage" id="currentPage">
	<input type="hidden" value="${type }" name="finType" id="finType">
	<input id="minRecharge" value="${minRecharge }" type="hidden">
	<input id="maxRecharge" value="${maxRecharge }" type="hidden">
	<input type="hidden" value="0" name="desc" id="desc">
	<input type="hidden" value="0" name="remark" id="remark">


<%@include file="../comm/footer.jsp" %>	

	<script type="text/javascript" src="${oss_url}/static/front/js/finance/account.recharge.js?v=20181126201750"></script>
	<script type="text/javascript" src="${oss_url}/static/front/js/index/main.js?v=20181126201750"></script>

</body>
</html>
