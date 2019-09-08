
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

<link rel="stylesheet" href="${oss_url}/static/front/css/market/market.css?v=20181126201750" type="text/css"></link>
</head>
<body>
	



<%@include file="../comm/header.jsp" %>
 



	<div class="container-full">
		<div class="container padding-clear">
			<div class="col-xs-12 rightarea market">
			<div class="market-banner">

             <c:forEach items="${requestScope.constant['fbs']}" var="v" varStatus="n">
				<div name="tradeType${fn:length(requestScope.constant['fbs'])==(n.index+1)?2:1 }" id="tradeType${fn:length(requestScope.constant['fbs'])==(n.index+1)?2:1 }" class="tradeType">
					<div>${v.fShortName }交易区</div>
					<ul class="list-unstyled">
					<c:forEach items="${ftrademappings }" var="vv">
					<c:if test="${vv.fvirtualcointypeByFvirtualcointype1.fid==v.fid }">
							<li class="tradeTypeItem">
								<a href="/market.html?symbol=${vv.fid }">${vv.fvirtualcointypeByFvirtualcointype2.fShortName }</a>
							</li>
					</c:if>		
					</c:forEach>
					</ul>
				</div>
			</c:forEach>

				<span class="tradeTypeStatus">
					当前：${ftrademapping.fvirtualcointypeByFvirtualcointype2.fShortName }/${ftrademapping.fvirtualcointypeByFvirtualcointype1.fShortName }
				</span>
				<a class="pull-right" href="/trademarket.html?symbol=${symbol }">进入专业版&gt;&gt;</a>
			</select>
				
				<div class="col-xs-12 padding-clear">
					<div class="col-xs-12 market-start padding-clear market-left">
						<iframe frameborder="0" border="0" width="100%" height="100%" id="klineFullScreen" src="/kline/fullstart.html?symbol=1&themename=dark"></iframe>
						<a class="openfullscreen" id="openfullscreen" href="javascript:void(0)" title="全屏" style="display:block;"></a>
						<a class="closefullscreen" id="closefullscreen" href="javascript:void(0)" title="退出全屏" style="display:none"></a>
					</div>
					<div class="col-xs-3 padding-right-clear market-right hidden">
						<div class="form-horizontal info-box">
							<div class="form-group ">
								<span class="col-xs-5 col-xs-offset-1 infotips">人民币余额</span>
								<span class="col-xs-5 control-label" id="tradecnymoney"> 0 </span>
								<input id="toptradecnymoney" type="hidden" value="0">
							</div>
							<div class="form-group ">
								<span class="col-xs-5 col-xs-offset-1 infotips">可买ETC</span>
								<span class="col-xs-5 control-label" id="getcoin"> 0 </span>
							</div>
							<div class="form-group ">
								<span class="col-xs-5 col-xs-offset-1 infotips">ETC余额</span>
								<span class="col-xs-5 control-label" id="trademtccoin"> 0 </span>
								<input id="toptrademtccoin" type="hidden" value="0">
							</div>
							<div class="form-group ">
								<span class="col-xs-5 col-xs-offset-1 infotips">可卖人民币</span>
								<span class="col-xs-5 control-label" id="getcny"> 0 </span>
							</div>
							<div class="form-group ">
								<span class="col-xs-10 col-xs-offset-1 infotips split"></span>
							</div>
							<div class="form-group "></div>
							<div class="form-group ">
								<span class="col-xs-2 col-xs-offset-1 infotips padding-right-clear">价格</span>
								<div class="col-xs-8">
									<input id="tradebuyprice" class="form-control" type="text" autocomplete="off" value="9.57">
									<span class="infotips-right">CNY</span>
								</div>
							</div>
							<div class="form-group ">
								<span class="col-xs-2 col-xs-offset-1 infotips padding-right-clear">数量</span>
								<div class="col-xs-8">
									<input id="tradebuyamount" class="form-control" type="text" autocomplete="off">
									<span class="infotips-right">ETC</span>
								</div>
							</div>
							<div class="form-group ">
								<span class="col-xs-2 col-xs-offset-1 infotips padding-right-clear">金额</span>
								<div class="col-xs-8">
									<span id="tradebuyTurnover" class="form-control"></span>
									<span class="infotips-right">CNY</span>
								</div>
							</div>
							<div class="form-group ">
								<span class="col-xs-2 col-xs-offset-1 infotips"></span>
								<div class="col-xs-8">
									<span id="errortips" class="text-danger"></span>
								</div>
							</div>
							<div class="form-group ">
								<div class="col-xs-5 col-xs-offset-1">
									<button id="buybtn" class="btn btn-success btn-block">买入</button>
								</div>
								<div class="col-xs-5">
									<button id="sellbtn" class="btn btn-danger btn-block">卖出</button>
								</div>
							</div>
						</div>
					</div>
				
				</div>
				<div id="delegateinfo" class="col-xs-12 padding-clear padding-top-30">
					<div class="col-xs-12 padding-clear market-left">
						<div class="col-xs-12 padding-clear list-group-title">
							<div class="col-xs-8">
								<span>
									委托信息
								</span>
							</div>
							
							<div class="col-xs-4">
								<span>
									最新成交
								</span>
							</div>
						</div>
						<div class="col-xs-4 padding-left-clear">
							<ul id="buybox" class="list-group">
								<li class="list-group-item first-child clearfix">
									<span class="col-xs-2 padding-clear">
										买入
									</span>
									<span class="col-xs-4 padding-clear text-right">
										买入价格
									</span>
									<span class="col-xs-6 padding-left-clear text-right">
										委单量
									</span>
								</li>
							</ul>
						</div>
						<div class="col-xs-4 padding-left-clear">
							<ul id="sellbox" class="list-group">
								<li class="list-group-item first-child clearfix">
									<span class="col-xs-2 padding-clear">
										卖出
									</span>
									<span class="col-xs-4 padding-clear text-right">
										卖出价格
									</span>
									<span class="col-xs-6 padding-left-clear text-right">
										委单量
									</span>
								</li>
							</ul>
						</div>
						<div class="col-xs-4 padding-left-clear padding-right-clear">
							<ul class="list-group">
								<li class="list-group-item first-child clearfix">
									<span class="col-xs-4 padding-right-clear">
										成交时间
									</span>
									<span class="col-xs-3 padding-clear text-right">
										成交价
									</span>
									<span class="col-xs-5 text-right">
										成交量
									</span>
								</li>
								<div id="logbox"></div>
							</ul>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>






<%@include file="../comm/footer.jsp" %>	



	<input type="hidden" id="coinCount1" value="${ftrademapping.fcount1 }">
	<input type="hidden" id="coinCount2" value="${ftrademapping.fcount2 }">
	<input type="hidden" id="symbol" value="${ftrademapping.fid }">
	<input type="hidden" value="${ftrademapping.fvirtualcointypeByFvirtualcointype2.fShortName }" id="coinshortName">
	<input id="isopen" type="hidden" value="${isopen }">
	<input id="tradeType" type="hidden" value="${tradeType }">
	<input id="login" type="hidden" value="${login }">
	<input id="tradePassword" type="hidden" value="${tradePassword }">
	<input id="isTelephoneBind" type="hidden" value="${isTelephoneBind }">
	<script type="text/javascript" src="${oss_url}/static/front/js/market/markt.js?t=<%=new java.util.Date().getTime() %>"></script>
</body>
</html>
