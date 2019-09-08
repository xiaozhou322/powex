<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../comm/include.inc.jsp"%>

<!doctype html>
<html>
<head> 
<%@ include file="../comm/basepath.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="../comm/link.inc.jsp" %>

<!-- <link rel="stylesheet" href="${oss_url}/static/front/css/finance/withdraw.css?v=20181126201750" type="text/css"></link>
<link href="${oss_url}/static/front/css/index/main.css?v=20181126201750" rel="stylesheet" type="text/css" /> -->
<link href="${oss_url}/static/front2018/css/exchange/common.css?v=20181126201750" rel="stylesheet" type="text/css" />
<link href="${oss_url}/static/front2018/css/exchange/main.css?v=1" rel="stylesheet" type="text/css" />
</head>
<body class="lw-UstdBody">
	<input type="hidden" id="max_double" value="${requestScope.constant['maxwithdrawusdt'] }">
	<input type="hidden" id="min_double" value="${requestScope.constant['minwithdrawusdt'] }">

  <%@include file="../comm/header.jsp" %>
	<section  class="lw-content">
		<div class="container-full padding-top-40">
			<div class="lw-finance">
<!-- 				<div class="lw-financeLeft fl">
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
		         -->
				<div class="buyUstdMain usdtTd">
					<h1 class="lw-financeTit"><spring:message code="financial.usdtrewithdrawal" /></h1>
					<div class="usdt_withdraw" style="overflow:hidden;">
						<div>
							<div id="subformdiv" class="form-horizontal" style="display:block;">
							    <div class="form-group clear">
									<span for="withdrawAmount" class="control-label fl tl_s"><spring:message code="financial.usdtrewithdrawal.balance" /></span>
									
										<span class="border-fff cred  fl tl_s">$<ex:DoubleCut value="${fwallet.ftotal }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/></span>
								
								</div>
								<div class="form-group clear">
									<span for="diyMoney" class="control-label tl_s"><spring:message code="financial.usdtrewithdrawal.card" /></span>
									
									<select id="withdrawBlank" class="fl">
										 <c:forEach items="${fbankinfoWithdraws }" var="v">
												<option value="${v.fid }">${v.fname }&nbsp;&nbsp;<spring:message code="financial.usdtrewithdrawal.cardnum" />${v.fbankNumber }</option>
										</c:forEach>		
									</select>		
									
									<a href="/financial/accountbank.html" class="text-primary addtips"><spring:message code="financial.toadd" /> >></a>
								</div>
								<div class="form-group clear">
									<span for="withdrawBalanceUsdt" class="control-label tl_s"><spring:message code="financial.usdtrewithdrawal.amount" /></span>
									<div class="textBox">
										<input id="withdrawBalanceUsdt" class="form-control" type="text" autocomplete="off">
										<span class="amounttips clear">
											<span class='fl'>
												<spring:message code="financial.usdtrewithdrawal.fee" />
												<span id="free">0</span>
												<span>USDT</span>
											</span>
											<span class='fr'>
												<spring:message code="financial.usdtrewithdrawal.arr" />
												<span id="amount" class="text-danger">0</span>
												<span class="text-danger">USDT</span>
											</span>
										</span>
									</div>
								</div>
								<div class="form-group clear">
									<span for="tradePwd" class="control-label tl_s"><spring:message code="financial.tradingpwd" /></span>
									<div class="textBox">
										<input id="tradePwd" class="form-control" type="password" autocomplete="off">
									</div>
								</div>
							
							<c:if test="${isBindTelephone == true }">		
									<div class="form-group clear>" style="overflow:hidden;">
										<span for="withdrawPhoneCode" class="control-label tl_s"><spring:message code="security.smscode" /></span>
										<div class="textBox">
											<input id="withdrawPhoneCode" class="form-control" type="text" autocomplete="off">
											<button id="withdrawsendmessage" data-msgtype="17" data-tipsid="withdrawerrortips" class="btn btn-sendmsg"><spring:message code="financial.send" /></button>
										</div>
									</div>
							</c:if>		
								
							<c:if test="${isBindGoogle ==true}">	
									<div class="form-group clear">
										<span for="withdrawTotpCode" class="control-label tl_s"><spring:message code="financial.goocod" /></span>
										<div class="textBox">
											<input id="withdrawTotpCode" class="form-control" type="text" autocomplete="off">
										</div>
									</div>
							</c:if>		
								<!-- 添加人机验证 -无痕验证 -->
	  							<div class="tr_txt" id="nvc_validate"></div>
								<div class="form-group clear">
									<span for="withdrawerrortips" class="control-label tl_s"></span>
									<div class="textBox">
										<label id="withdrawerrortips" class="text-danger">
											
										</label>
									</div>
								</div>
								<div class="form-group clear">
									<span for="withdrawCnyButton" class="control-label tl_s"></span>
									<div class="textBox">
										<button id="withdrawUsdtButton" class="btn btn-danger btn-block" style="background:#ff5818; width:100%; border:none;"><spring:message code="financial.usdtrewithdrawal.immwith" /></button>
									</div>
								</div>
							</div>
							
							<div id="showcoincode" class="textBox form-horizontal" style="display:none;font-size:15px;">							
								<div class="form-group clear">
									<span for="diyMoney" class="control-label tl_s" style="width:50%;">USDT<spring:message code="financial.usdtrewithdrawal.remarknum" /></span>
									<div class="fl uh_tr" style="margin: 10px 0 10px 0;">
										<input id="coincode" readonly="" type="text" style="border:1px solid #eee;padding-left:5px;">
										<button class="userInvite-linkCopy btn btn-danger" data-clipboard-target="#coincode" style="font-size:13px;"><spring:message code="introl.affi.copylink" /></button>
										<span class="userInvite-linktips" style="display:none; color:#5c9cfc;font-size:14px;margin:0 5px;"><spring:message code="introl.affi.copysuc" />√</span>
										
										<a href="/exchange/exchangeUsdt.html" class="text-primary addtips dy_usdt" data-toggle="modal" ><spring:message code="financial.usdtrewithdrawal.exchange" />>></a>
									</div>
								</div>
							
							</div>
						</div>

						<div class="promptTxt  lw-coinInstructions">

					
						
						<div class="" style="margin-top:70px;font-size:13px;">

							<div class="panel-header">
							<br>
								<h3 class="panel-title cred"><spring:message code="financial.usdtrewithdrawal.witins" /></h3>
							</div>
							<p><spring:message code="financial.usdtrewithdrawal.thsmininum"   arguments="${requestScope.constant['minwithdrawcny'] },${requestScope.constant['maxwithdrawcny'] }" /></p>
							<spring:message code="financial.usdtrewithdrawal.note" arguments="${fee*100 }" />
						</div>
					</div>
					</div>
					

				
				</div>
				</div>
			</div>
		</div>
	</section>
<!-- 	<div class="modal modal-custom fade" id="withdrawCnyAddress" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel">
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
				<div class="form-group clear ">
					<span for="payeeAddr" class="control-label" ><spring:message code="financial.cnyrewithdrawal.accname" /></span>
					<div class=">
						<input id="payeeAddr" class="form-control" type="text" autocomplete="off" value="${fuser.frealName }" readonly="readonly"/>
						<span class="help-block text-danger">*<spring:message code="financial.cnyrewithdrawal.nametip" /></span>
					</div>
				</div>
				<div class="form-group ">
					<span for="withdrawAccountAddr" class="control-label"><spring:message code="financial.cnyrewithdrawal.account" /></span>
					<div class=">
						<input id="withdrawAccountAddr" class="form-control" type="" text>
					</div>
				</div>
				<div class="form-group ">
					<span for="withdrawAccountAddr2" class="control-label"><spring:message code="financial.cnyrewithdrawal.confirmcard" /></span>
					<div class=">
						<input id="withdrawAccountAddr2" class="form-control" type="" text>
					</div>
				</div>
				<div class="form-group ">
					<span for="openBankTypeAddr" class="control-label"><spring:message code="financial.cnyrewithdrawal.deposit" /></span>
					<div class=">
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
					<span for="prov" class="control-label"><spring:message code="financial.cnyrewithdrawal.addr" /></span>
					<div class="">
						<div class="padding-right-clear padding-left-clear margin-bottom-15">
							<select id="prov" class="form-control">
							</select>
						</div>
						<div class="padding-right-clear margin-bottom-15">
							<select id="city" class="form-control">
							</select>
						</div>
						<div class="padding-right-clear margin-bottom-15">
							<select id="dist" class="form-control prov">
							</select>
						</div>
						<div class=" padding-right-clear padding-left-clear">
							<input id="address" class="form-control" type="text" autocomplete="off" placeholder="<spring:message code="financial.cnyrewithdrawal.addrtip" />"/>
						</div>
					</div>
				</div>
			<c:if test="${isBindTelephone == true }">	
					<div class="form-group">
						<span for="addressPhoneCode" class="control-label"><spring:message code="security.smscode" /></span>
						<div class=">
							<input id="addressPhoneCode" class="form-control" type="text" autocomplete="off">
							<button id="bindsendmessage" data-msgtype="10" data-tipsid="binderrortips" class="btn btn-sendmsg"><spring:message code="financial.send" /></button>
						</div>
					</div>
			</c:if>		
				
			<c:if test="${isBindGoogle ==true}">		
					<div class="form-group">
						<span for="addressTotpCode" class="control-label"><spring:message code="financial.goocod" /></span>
						<div class=">
							<input id="addressTotpCode" class="form-control" type="text" autocomplete="off">
						</div>
					</div>
			</c:if>		
				
				<div class="form-group">
					<span for="binderrortips" class="control-label"></span>
					<div class=">
						<span id="binderrortips" class="text-danger"></span>
					</div>
				</div>
				<div class="form-group">
					<span for="withdrawCnyAddrBtn" class="control-label"></span>
					<div class=">
						<button id="withdrawUsdtAddrBtn" class="btn btn-danger btn-block"><spring:message code="financial.cnyrewithdrawal.submit" /></button>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
 -->
	
	<input id="feesRate" type="hidden" value="${fee }">
	<input id="userBalance" type="hidden" value="${requestScope.fwallet.ftotal }">
	


<%@include file="../comm/footer.jsp" %>	
	<script type="text/javascript" src="${oss_url}/static/front2018/js/comm/msg.js?v=20181126201750"></script>
	<script type="text/javascript" src="${oss_url}/static/front2018/js/finance/account.withdraw.js?v=11"></script>
	<script type="text/javascript" src="${oss_url}/static/front2018/js/finance/city.min.js?v=20181126201750"></script>
	<script type="text/javascript" src="${oss_url}/static/front2018/js/finance/jquery.cityselect.js?v=20181126201750"></script>
	<script type="text/javascript" src="${oss_url}/static/front2018/js/index/main.js?v=20181126201750"></script>
<script type="text/javascript" src="${oss_url}/static/front2018/js/plugin/clipboard.min.js?v=20181126201750"></script>
<script type="text/javascript">
// 将【复制】按钮充当复制数据的元素载体
var clip = new Clipboard('.userInvite-linkCopy');
clip.on('success', function(e) {
		   	$('.userInvite-linktips').show();
		    e.clearSelection();
		});
</script>
<script>
	var appkey = "FFFF00000000017F11B9";
	var scene = "nvc_other";
    window.NVC_Opt = {
        //无痕配置 && 滑动验证、刮刮卡通用配置
        appkey:appkey,
        scene:scene,
        isH5:false,
        popUp:false,
        renderTo:'#nvc_validate',
        trans: {"key1": "code0","nvcCode":400},
        language: "cn",
        //滑动验证长度配置
        customWidth: 300,
        //刮刮卡配置项
        width: 300,
        height: 100,
        elements: [
            '//img.alicdn.com/tfs/TB17cwllsLJ8KJjy0FnXXcFDpXa-50-74.png',
            '//img.alicdn.com/tfs/TB17cwllsLJ8KJjy0FnXXcFDpXa-50-74.png'
        ], 
        bg_back_prepared: '//img.alicdn.com/tps/TB1skE5SFXXXXb3XXXXXXXXXXXX-100-80.png',
        bg_front: 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAGQAAABQCAMAAADY1yDdAAAABGdBTUEAALGPC/xhBQAAAAFzUkdCAK7OHOkAAAADUExURefk5w+ruswAAAAfSURBVFjD7cExAQAAAMKg9U9tCU+gAAAAAAAAAIC3AR+QAAFPlUGoAAAAAElFTkSuQmCC',
        obj_ok: '//img.alicdn.com/tfs/TB1rmyTltfJ8KJjy0FeXXXKEXXa-50-74.png',
        bg_back_pass: '//img.alicdn.com/tfs/TB1KDxCSVXXXXasXFXXXXXXXXXX-100-80.png',
        obj_error: '//img.alicdn.com/tfs/TB1q9yTltfJ8KJjy0FeXXXKEXXa-50-74.png',
        bg_back_fail: '//img.alicdn.com/tfs/TB1w2oOSFXXXXb4XpXXXXXXXXXX-100-80.png',
        upLang:{"cn":{
        	_ggk_guide: language["aliyun.nvc.ggk_guide"],
            _ggk_success: language["aliyun.nvc.ggk_success"],
            _ggk_loading: language["aliyun.nvc.ggk_loading"],
            _ggk_fail: language["aliyun.nvc.ggk_fail"],
            _ggk_action_timeout: language["aliyun.nvc.ggk_action_timeout"],
            _ggk_net_err: language["aliyun.nvc.ggk_net_err"],
            _ggk_too_fast: language["aliyun.nvc.ggk_too_fast"]
            }
        }
    }
    
</script>
<script src="//g.alicdn.com/sd/nvc/1.1.112/guide.js?v=20181126201750"></script>
</body>
</html>
