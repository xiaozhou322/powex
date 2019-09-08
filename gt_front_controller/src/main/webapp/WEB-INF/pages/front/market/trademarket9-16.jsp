<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../comm/include.inc.jsp"%>
<%

%>

<!doctype html>
<html>
<head> 
<%@ include file="../comm/basepath.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="../comm/link.inc.jsp" %>

<link rel="stylesheet" href="${oss_url}/static/front/css/market/trademarket.css?v=20181126201750" type="text/css"></link>
<link rel="stylesheet" href="${oss_url}/static/front/css/market/loader.css?v=20181126201750" type="text/css"></link>

</head>
<body style="background: #1e2b34;">
	<div id="loader-wrapper">
		<div class="loader-inner ball-scale-ripple-multiple">
			<div></div>
			<div></div>
			<div></div>
		</div>
		<div class="loader-section section-left"></div>
		<div class="loader-section section-right"></div>
	</div>
	<div class="container-full market-head">
		<h1 class="head-logo">
			<a class="marketplist login" href="/"></a>
		</h1>
		<div class="head-nav">
		 <c:forEach items="${requestScope.constant['fbs']}" var="v" varStatus="n">
				<div name="tradeType${fn:length(requestScope.constant['fbs'])==(n.index+1)?2:1 }" id="tradeType${fn:length(requestScope.constant['fbs'])==(n.index+1)?2:1 }" class="tradeType">
					<div>${v.fShortName }交易区</div>
					<ul class="list-unstyled">
					<c:forEach items="${ftrademappings }" var="vv">
					<c:if test="${vv.fvirtualcointypeByFvirtualcointype1.fid==v.fid }">
							<li class="tradeTypeItem">
								<a href="/trademarket.html?symbol=${vv.fid }">${vv.fvirtualcointypeByFvirtualcointype2.fShortName }</a>
							</li>
					</c:if>		
					</c:forEach>
					</ul>
				</div>
		</c:forEach>

		<span class="tradeTypeStatus">
			当前：${ftrademapping.fvirtualcointypeByFvirtualcointype2.fShortName }/${ftrademapping.fvirtualcointypeByFvirtualcointype1.fShortName }
		</span>
		</div>

		<c:if test="${sessionScope.login_user == null }">
			<div class="head-login">
				<input id="login_acc" placeholder="请输入邮件或手机">
				<input id="login_pwd" placeholder="请输入密码" type="password">
				<button id="login_sub">登录</button>
		    </div>
		</c:if>
		
		<c:if test="${sessionScope.login_user != null }">
		<div class="head-login">
				<span>您好，${login_user.fnickName}</span>
				<span>|</span>
				<a id="login_logout" href="/user/logout.html">退出登录</a>
		</div>
		</c:if>
	</div>
		<div class="container-full clearfix">
		<div id="marketLeft" class="market-left">
			<div id="marketStart" class="market-start">
				<iframe frameborder="0" border="0" width="100%" height="100%" id="klineFullScreen" src="/kline/fullstart.html?symbol=${symbol }&themename=dark"></iframe>
			</div>
			
				<div id="marketEntruts" class="market-entruts">
					<div id="entrutsHead" class="entruts-head">
						<span class="entruts-head-nav-full" data-show="entrutsCur" data-hide="entrutsHis">当前委托</span><span class="entruts-head-nav-full" data-show="entrutsHis" data-hide="entrutsCur">历史委托</span>
					</div>
					<div class="entruts-data" id="entrutsCur">
						<div class="entruts-data-head">
							<span class="col-1">委托时间</span> 
							<span class="col-2">类型</span> 
							<span class="col-3">委托价格</span> 
							<span class="col-3">委托数</span> 
							<span class="col-3">已成交</span> 
							<span class="col-3">状态</span> 
							<span class="col-2">操作</span>
						</div>
						<div class="entruts-data-data" id="entrutsCurData"></div>
					</div>
					<div class="entruts-data" id="entrutsHis">
						<div class="entruts-data-head">
							<span class="col-1">委托时间</span> 
							<span class="col-2">类型</span> 
							<span class="col-3">委托价格</span> 
							<span class="col-3">委托数</span> 
							<span class="col-3">已成交</span> 
							<span class="col-3">状态</span> 
						</div>
						<div class="entruts-data-data" id="entrutsHisData"></div>
					</div>
				</div>
			
		</div>
		<div class="market-right">
			<div id="marketData" class="market-data">
				<div class="market-depth">
					<div class="market-depth-head">
						<span class="depth-des">&nbsp;</span>
						<span class="depth-price">价格(${ftrademapping.fvirtualcointypeByFvirtualcointype1.fSymbol })</span>
						<span class="depth-amount">数量</span>
					</div>
					<div class="market-depth-data" id="marketDepthData">
						<div class="market-depth-price">
							<div class="market-depth-sell" id="marketDepthSell"></div>
							<div class="depth-price text-left">
								最新价&nbsp;<span id="marketPrice" class="market-font-sell">0.0000</span>
							</div>
							<div class="depth-price right">
								涨跌比&nbsp;<span class="market-font-sell" id="marketRose">0%</span>
							</div>
							<div class="market-depth-buy" id="marketDepthBuy"></div>
						</div>
					</div>

				</div>
				<div class="market-success">
					<div class="market-success-head">
						<span class="success-time">时间</span>
						<span class="success-price">价格(${ftrademapping.fvirtualcointypeByFvirtualcointype1.fSymbol })</span>
						<span class="success-amount">成交量</span>
					</div>
					<div class="market-success-data" id="marketSuccessData"></div>
				</div>
			</div>
			
				<div id="marketTrade" class="market-trade">
					<div class="trade-table left">
						<div class="trade-tr">
							<span>可用：<span class="market-font-buy" id="totalCny">0</span>&nbsp${ftrademapping.fvirtualcointypeByFvirtualcointype1.fShortName }
							</span>
						</div>
						<div class="trade-tr">
							<label for="buy-price" class="tr-tips">买入价${ftrademapping.fvirtualcointypeByFvirtualcointype1.fShortName }</label>
							<input id="buy-price" />
						</div>
						<div class="trade-tr">
							<label for="buy-amount" class="tr-tips">买入量${ftrademapping.fvirtualcointypeByFvirtualcointype2.fShortName }</label>
							<input id="buy-amount" />
						</div>
						<div class="trade-tr tr-boder tr-slider">
							<span id="buyBar" class="col-xs-12 buysellbar">
								<div class="buysellbar-box">
									<div id="buyslider" class="slider" data-role="slider" data-param-marker="marker" data-param-complete="complete"
										data-param-type="0" data-param-markertop="marker-top"></div>
									<div class="slider-points">
										<div class="proportioncircle proportion0" data-points="0">0%</div>
										<div class="proportioncircle proportion1" data-points="20">20%</div>
										<div class="proportioncircle proportion2" data-points="40">40%</div>
										<div class="proportioncircle proportion3" data-points="60">60%</div>
										<div class="proportioncircle proportion4" data-points="80">80%</div>
										<div class="proportioncircle proportion5" data-points="100">100%</div>
									</div>
								</div>
							</span>
						</div>
						<div class="trade-tr tr-boder">
							<span class="tr-tips">交易额：</span><span class="tr-right"><span class="market-font-buy" id="buy-limit">0.0000</span>&nbsp;${ftrademapping.fvirtualcointypeByFvirtualcointype1.fShortName }</span>
						</div>
						<div class="trade-tr tr-btn">
							<button id="buy_sub" class="buy">买入</button>
						</div>
					</div>
					<div class="trade-table right">
						<div class="trade-tr">
							<span>可用：<span class="market-font-sell" id="totalCoin">0</span>&nbsp;${ftrademapping.fvirtualcointypeByFvirtualcointype2.fShortName }
							</span>
						</div>
						<div class="trade-tr">
							<label for="sell-price" class="tr-tips">卖出价${ftrademapping.fvirtualcointypeByFvirtualcointype1.fShortName }</label>
							<input id="sell-price" />
						</div>
						<div class="trade-tr">
							<label for="sell-amount" class="tr-tips">卖出量${ftrademapping.fvirtualcointypeByFvirtualcointype2.fShortName }</label>
							<input id="sell-amount" />
						</div>
						<div class="trade-tr tr-boder tr-slider">
							<span id="sellBar" class="col-xs-12 buysellbar">
								<div class="buysellbar-box">
									<div id="sellslider" class="slider" data-role="slider" data-param-marker="marker sell-marker"
										data-param-complete="complete" data-param-type="1" data-param-markertop="marker-top"></div>
									<div class="slider-points">
										<div class="proportioncircle proportion0" data-points="0">0%</div>
										<div class="proportioncircle proportion1" data-points="20">20%</div>
										<div class="proportioncircle proportion2" data-points="40">40%</div>
										<div class="proportioncircle proportion3" data-points="60">60%</div>
										<div class="proportioncircle proportion4" data-points="80">80%</div>
										<div class="proportioncircle proportion5" data-points="100">100%</div>
									</div>
								</div>
							</span>
						</div>
						<div class="trade-tr tr-boder">
							<span class="tr-tips">交易额：</span><span class="tr-right"><span class="market-font-sell" id="sell-limit">0.0000</span>&nbsp;${ftrademapping.fvirtualcointypeByFvirtualcointype1.fShortName }</span>
						</div>
						<div class="trade-tr tr-btn">
							<button id="sell_sub" class="sell">卖出</button>
						</div>
					</div>
				</div>
			
		</div>
	</div>
	
	
			<div class="modal modal-custom fade" id="tradepass" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel">
			<div class="modal-dialog" role="document">
				<div class="modal-mark"></div>
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close btn-modal" data-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<span class="modal-title" id="exampleModalLabel">交易密码</span>
					</div>
					<div class="modal-body form-horizontal">
						<div class="form-group">
							<div class="col-xs-3 control-label">
								<span>交易密码：</span>
							</div>
							<div class="col-xs-6 padding-clear">
								<input type="password" class="form-control" id="tradePwd" placeholder="交易密码">
							</div>
						</div>
						<div class="form-group">
							<div class="col-xs-6 padding-clear col-xs-offset-3">
								<span id="errortips" class="error-msg text-danger"></span>
							</div>
						</div>
						<div class="form-group margin-bottom-clear">
							<div class="col-xs-6 padding-clear col-xs-offset-3">
								<button id="modalbtn" type="button" class="btn btn-danger btn-block">确定</button>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	
		<input type="hidden" id="sellShortName" value="${ftrademapping.fvirtualcointypeByFvirtualcointype2.fShortName }">
	<input type="hidden" id="coinshortName" value="${ftrademapping.fvirtualcointypeByFvirtualcointype1.fShortName }">
	
	<input id="userid" type="hidden" value="${userid }">	
	<input type="hidden" id="cnyDigit" value="${ftrademapping.fcount1 }">
	<input type="hidden" id="coinDigit" value="${ftrademapping.fcount2 }">
	<input type="hidden" id="symbol" value="${ftrademapping.fid }">
	<input type="hidden" value="${ftrademapping.fvirtualcointypeByFvirtualcointype2.fShortName }" id="coinshortName">
	<input id="minBuyCount" type="hidden" value="<ex:DoubleCut value="${ftrademapping.fminBuyCount }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/>">
    <input id="limitedType" type="hidden" value="0">
    <input id="lastprice" type="hidden" value="0">
	<input id="isopen" type="hidden" value="${needTradePasswd }">
	<input id="tradeType" type="hidden" value="${tradeType }">
	<input id="login" type="hidden" value="${login }">
	<input id="tradePassword" type="hidden" value="${tradePassword }">
	<input id="isTelephoneBind" type="hidden" value="${isTelephoneBind }">



<script type="text/javascript" src="${oss_url}/static/front/js/plugin/bootstrap.js?v=20181126201750"></script>
<script type="text/javascript" src="${oss_url}/static/front/js/plugin/layer/layer.js?v=20181126201750"></script>
<script type="text/javascript" src="${oss_url}/static/front/js/comm/util.js?v=20181126201750"></script>
<script type="text/javascript" src="${oss_url}/static/front/js/comm/comm.js?v=20181126201750"></script>
<script type="text/javascript" src="${oss_url}/static/front/js/language/language_cn.js?v=20181126201750"></script>
<script type="text/javascript" src="${oss_url}/static/front/js/plugin/jquery.jslider.js?v=20181126201750"></script>

	<script type="text/javascript" src="${oss_url}/static/front/js/market/trademarket.js?t=<%=new java.util.Date().getTime() %>"></script>
</body>
</html>