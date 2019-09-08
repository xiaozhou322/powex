<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ include file="../comm/include.inc.jsp"%>
<%

%>


<!doctype html>
<html>
<head>
<jsp:include page="../comm/link.inc.jsp"></jsp:include>
<link href="${oss_url}/static/front/css/trade/trade.css?v=20181126201750" rel="stylesheet" type="text/css" media="screen, projection" />
<link rel="stylesheet" type="text/css" href="${oss_url}/static/front/css/index/main.css?v=20181126201750" />
</head>

<style type="text/css">
	.displayFlex{padding:0;}
	.rightarea{background:none;}
	.rightarea .rightarea-con{padding-top:10px!important;}
	.trade{padding-left:0!important; padding-right:0!important;}
	.lw-tradeContent{width:696px; padding:0; overflow:hidden;}
	.trade .trade-inputtips{padding-left:8px;}
	.trade .trade-tips{border:none; height:36px;}
	.trade .trade-tips.trade-tips_1{height:36px!important; margin-bottom:0!important;}
	.slider-points .proportioncircle{display:none;}
	.buysellbar{width:100%;}
	.trade .trade-buysell{background:#fff; width:333px; float:left;}
	.trade .trade-buysell:nth-child(1){margin-right:15px;}
	.trade .trade-buysell .col-xs-4{padding:0 0 0 25px!important; font-size:14px;}

	.trade .trade-amount{border-bottom:1px solid #e4e4e4;}
	.trade .trade-amount>span:nth-child(2){padding:0 20px 0 0!important;}
	.buysellbar .circle{width:12px; height:12px; background:#fff; border-radius:50%; border: 2px solid #4797f2; top:-5px; font-size:14px;}
	.slider{border:none; background:#e4e4e4; height:4px;}
	.slider .complete{background:#4797f2; height:4px;}
	.trade .form-control{border-radius:3px;}
	#sellBar{padding-top:10px;}
	#buypricediv,#sellpricediv .col-xs-12{padding:0 25px!important;}
	.btn{font-size:14px;}
	.btn:hover{opacity:0.8;}
	.trade .trade-buysell a{padding:0; background:#99cc33; border:none; color:#fff!important;}
	#coinBoxbuybtc{width:246px; padding:0; background:#fff; color:#666;}
	.btn-success{background:#da2e22; border:none;}
	.btn-success:hover{background:#ff604b;}
	#entrustInfo .col-xs-12{background:#fff; padding:20px;}
	#entrustInfo .col-xs-12 td{padding-bottom:10px!important; height:36px;}
	.trade .trade-depth{text-align:left!important; padding-top:10px;}
	#coinBoxbuybtc span{font-size:12px; text-align:center;}
	.leftmenu .leftmenu-title{font-size:14px;}
	#sellbtn{background:#009900; border:none;}
</style>
<body>

<jsp:include page="../comm/header.jsp"></jsp:include>


	<div class="container-full" style="padding-top:120px;">
		<div class="container displayFlex">
			
			<div class="col-xs-2 leftmenu">
				<ul class="nav nav-pills nav-stacked ">
				
					<c:forEach var="v" varStatus="vs" items="${ftrademappings }">
						<span class="leftmenu-title leftmenu-folding top"
								data-folding="trademenu${v.fid }"><i class="lefticon"
								style="background: url('${v.fvirtualcointypeByFvirtualcointype2.furl }') no-repeat 0 0;background-size:100% 100%;"></i>
								<a
								href="/trade/coin.html?coinType=${v.fid }&tradeType=0">${v.fvirtualcointypeByFvirtualcointype2.fShortName }/${v.fvirtualcointypeByFvirtualcointype1.fShortName }<spring:message code="entrust.coin.traname" /></a> 
					</span>
					</c:forEach>
				</ul>
			</div>
			
			<div class="col-xs-10 padding-right-clear">
				<div class="col-xs-12 padding-right-clear padding-left-clear rightarea trade">
					
					<c:if test="${isTradePassword == false}">
						<div class="col-xs-12 rightarea-top">
							<span class="col-xs-5"> <span><spring:message code="entrust.coin.notradpwd" /></span> <a
								href="/user/security.html" class="text-danger"><spring:message code="entrust.coin.secset" /></a> </span> <span
								class="pull-right"> <span class="trade-process active">
									<i class="icon">1</i> <span><spring:message code="entrust.coin.secset" /></span> <i class="arrow"></i> </span>
								<span class="trade-process "> <i class="icon">2</i> <span><spring:message code="entrust.coin.recharge" /></span>
									<i class="arrow"></i> </span> <span class="trade-process"> <i
									class="icon">3</i> <span><spring:message code="entrust.coin.trade" /></span> <i class="arrow"></i> </span> </span>
						</div>
					</c:if>
				<div class="col-xs-12 padding-right-clear padding-left-clear">
					<div class="col-xs-8 lw-tradeContent">
						<div class="max-width trade-buysell">
							<div class="max-width trade-amount clearfix">
								<span class="col-xs-4" style="width: 50%; font-size:14px;">
									<span><spring:message code="market.usable" /></span>
									<span id="toptradecnymoney" class="redtips">
										0
									</span>
									<span class="redtips">${coin1.fShortName }</span>
								</span>
								
								
								<span class="col-xs-4 text-right" style="width:50%;">
									<span><spring:message code="entrust.coin.frozen" /></span>
									<span id="toptradelevercny">0.00</span>
									<span>${coin1.fShortName }</span>
									<i></i>
								</span>
							</div>
							<div class="max-width clearfix" id="buypricediv">
								<div class="col-xs-12">
									<div class="form-group"style="position:relative;">
										<label for="tradebuyprice" class="trade-inputtips">${coin1.fShortName }/${coin2.fShortName } <spring:message code="market.buyprice" /></label>
										<input id="tradebuyprice" class="form-control" type="text" autocomplete="off" value="${recommendPrizebuy }">
									</div>

									<div class="form-group" style="position:relative;">
										<label for="tradebuyamount" class="trade-inputtips">${coin2.fShortName } <spring:message code="market.buyvolume" /></label>
										<input id="tradebuyamount" class="form-control" type="text" autocomplete="off" value="">
									</div>
								</div>
								<div class="col-xs-12">
									<div class="form-group">
											<span class="form-control trade-tips padding-right-clear"> <span id="buyBar" class="buysellbar">
													<div class="buysellbar-box">
														<div id="buyslider" class="slider" data-role="slider" data-param-marker="marker" data-param-complete="complete" data-param-type="0"></div>
														<div class="slider-points">
															<div class="proportioncircle proportion0" data-points="0"></div>
															<div class="proportioncircle proportion1" data-points="25"></div>
															<div class="proportioncircle proportion2" data-points="50"></div>
															<div class="proportioncircle proportion3" data-points="75"></div>
															<div class="proportioncircle proportion4" data-points="100"></div>
														</div>
													</div>
											</span>
											</span>
									</div>
									<div class="" style="margin-top:30px;">
										<span class="form-control trade-tips trade-tips_1">
											<span class="col-xs-5 text-left padding-right-clear"><spring:message code="entrust.coin.total" /></span>
											<span class="col-xs-7 padding-right-clear ">
												<span id="tradebuyTurnover">0</span>
												${coin1.fShortName }
											</span>
										</span>
									</div>
								</div>
								<div class="col-xs-12">
									<div class="form-group">
										<span id="buy-errortips" class="text-danger trade-error"></span>
									</div>
								</div>
								<div class="">
									<div class="form-group">
										<button id="buybtn" class="btn btn-danger btn-success btn-block">${coin2.fShortName } <spring:message code="market.buy" /></button>
									</div>
								</div>
								<div class="">
									<div class="form-group" style="margin-bottom:45px;">
										<c:choose>
											<c:when test="${coin1.ftype==0 }">
												<a href="/account/rechargeCny.html" class="btn btn-danger btn-block"><spring:message code="entrust.coin.rechargenow" /></a>
											</c:when>
											<c:otherwise>
												<a href="/account/rechargeBtc.html?symbol=${coin1.fid }" class="btn btn-danger btn-block"><spring:message code="entrust.coin.rechargenow" /></a>
											</c:otherwise>
										</c:choose>
									</div>
								</div>
							</div>
							<div class="max-width clearfix" id="buymarketdiv" style="display: none;">
								<div class="col-xs-6">
									<div class="form-group">
											<span class="form-control trade-tips padding-right-clear"> <span id="buyBar" class="col-xs-12 buysellbar">
													<div class="buysellbar-box">
														<div id="buyslider" class="slider" data-role="slider" data-param-marker="marker" data-param-complete="complete" data-param-type="0"></div>
														<div class="slider-points">
															<div class="proportioncircle proportion0" data-points="0"></div>
															<div class="proportioncircle proportion1" data-points="25"></div>
															<div class="proportioncircle proportion2" data-points="50"></div>
															<div class="proportioncircle proportion3" data-points="75"></div>
															<div class="proportioncircle proportion4" data-points="100"></div>
														</div>
													</div>
											</span>
											</span>
										</div>
								</div>
								<div class="col-xs-6">
									<div class="form-group" >
										<span class="form-control trade-tips">
											<span class="col-xs-12 padding-right-clear ">
												<label for="tradebuyprice2" class="trade-inputtips"><spring:message code="entrust.coin.total" />/${coin1.fShortName }</label>
												<input id="tradebuyprice2" class="form-control" value="0" type="text" autocomplete="off">
											</span>
										</span>
									</div>
								</div>
								<div class="col-xs-12">
									<div class="form-group">
										<span id="buy-errortips2" class="text-danger trade-error"></span>
									</div>
								</div>
								<div class="col-xs-6">
									<div class="form-group">
										<button id="buybtn2" class="btn btn-danger btn-block">${coin2.fShortName } <spring:message code="market.buy" /></button>
									</div>
								</div>
								<div class="col-xs-6">
									<div class="form-group">
										<c:choose>
											<c:when test="${coin1.ftype==0 }">
												<a href="/account/rechargeCny.html" class="text-danger"><spring:message code="entrust.coin.rechargenow" /><i class="arrow-icon-small red"></i></a>
											</c:when>
											<c:otherwise>
												<a href="/account/rechargeBtc.html?symbol=${coin1.fid }" class="text-danger"><spring:message code="entrust.coin.rechargenow" /><i class="arrow-icon-small red"></i></a>
											</c:otherwise>
										</c:choose>
									</div>
								</div>
							</div>
						</div>
						<div class="max-width trade-buysell">
							<div class="max-width trade-amount clearfix">
								<span class="col-xs-4" style="width: 50%; font-size:14px;">
									<span><spring:message code="market.usable" /></span>
									<span id="toptrademtccoin" class="greentips">
										0
									</span>
									<span class="greentips">${coin2.fShortName }</span>
								</span>
								
								<span class="col-xs-4 text-right" style="width:50%;">
									<span><spring:message code="entrust.coin.frozen" /></span>
									<span id="toptradelevercoin">0.0</span>
									<span>${coin2.fShortName }</span>
									<i></i>
								</span>
							</div>
							<div class="max-width clearfix" id="sellpricediv">
								<div class="col-xs-12">
									<div class="form-group" style="position:relative;">
										<label for="tradesellprice" class="trade-inputtips">${coin1.fShortName }/${coin2.fShortName } <spring:message code="market.sellprice" /></label>
										<input id="tradesellprice" class="form-control" type="text" autocomplete="off" value="${recommendPrizesell }">
									</div>
									<div class="form-group" style="position:relative;">
										<label for="tradesellamount" class="trade-inputtips">${coin2.fShortName } <spring:message code="market.sellvolume" /></label>
										<input id="tradesellamount" class="form-control" type="text" autocomplete="off" value="">
									</div>

								</div>
								<div class="col-xs-12">
									
									<div class="form-group" style="padding-top:10px;">
											<span class="form-control trade-tips padding-right-clear"> <span id="sellBar" class="col-xs-12 buysellbar">
													<div class="buysellbar-box">
														<div id="sellslider" class="slider" data-role="slider" data-param-marker="marker sell-marker" data-param-complete="complete sell-complete" data-param-type="1"></div>
														<div class="slider-points">
															<div class="proportioncircle proportion0" data-points="0"></div>
															<div class="proportioncircle proportion1" data-points="25"></div>
															<div class="proportioncircle proportion2" data-points="50"></div>
															<div class="proportioncircle proportion3" data-points="75"></div>
															<div class="proportioncircle proportion4" data-points="100"></div>
														</div>
													</div>
											</span>
											</span>
									</div>
									<div class="" style="margin-top:19px;">
										<span class="form-control trade-tips">
											<span class="col-xs-5 text-left padding-right-clear"><spring:message code="entrust.coin.total" /></span>
											<span class="col-xs-7 padding-right-clear ">
												<span id="tradesellTurnover">0</span>
												${coin1.fShortName }
											</span>
										</span>
									</div>
								</div>
								<div class="col-xs-12">
									<div class="form-group">
										<span id="sell-errortips" class="text-danger trade-error"></span>
									</div>
								</div>
								<div class="col-xs-12">
									<div class="form-group">
										<button id="sellbtn" class="btn btn-danger btn-block">${coin2.fShortName } <spring:message code="market.sell" /></button>
									</div>
								</div>
								<div class="col-xs-12">
									<div class="form-group" style="margin-bottom:45px;">

										<a href="/account/rechargeBtc.html?symbol=${coin2.fid }" class="text-success  btn btn-danger btn-block"><spring:message code="entrust.coin.rechargenow" /><!-- <i class="arrow-icon-small green"></i> --></a>
									</div>
								</div>
							</div>
							
							<div class="max-width clearfix" id="sellmarketdiv" style="display: none;">
								<div class="col-xs-6">
									<div class="form-group">
											<span class="form-control trade-tips padding-right-clear"> <span id="sellBar" class="col-xs-12 buysellbar">
													<div class="buysellbar-box">
														<div id="sellslider" class="slider" data-role="slider" data-param-marker="marker sell-marker" data-param-complete="complete sell-complete" data-param-type="1"></div>
														<div class="slider-points">
															<div class="proportioncircle proportion0" data-points="0"></div>
															<div class="proportioncircle proportion1" data-points="25"></div>
															<div class="proportioncircle proportion2" data-points="50"></div>
															<div class="proportioncircle proportion3" data-points="75"></div>
															<div class="proportioncircle proportion4" data-points="100"></div>
														</div>
													</div>
											</span>
											</span>
										</div>
								</div>
								<div class="col-xs-6">
									<div class="form-group">
										<span class="form-control trade-tips">
											<span class="col-xs-12 padding-right-clear ">
												<label for="tradesellprice2" class="trade-inputtips"><spring:message code="entrust.coin.qty" />/${coin2.fShortName }</label>
												<input id="tradesellprice2" class="form-control" value="0" type="text" autocomplete="off">
											</span>
										</span>
									</div>
								</div>
								<div class="col-xs-12">
									<div class="form-group">
										<span id="sell-errortips2" class="text-danger trade-error"></span>
									</div>
								</div>
								<div class="col-xs-6">
									<div class="form-group">
										<button id="sellbtn2" class="btn btn-success btn-block">${coin2.fShortName } <spring:message code="market.sell" /></button>
									</div>
								</div>
								<div class="col-xs-6">
									<div class="form-group">
										<a href="/account/rechargeBtc.html?symbol=${coin2.fid }" class="text-success"><spring:message code="entrust.coin.rechargenow" /><i class="arrow-icon-small green"></i></a>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div id="coinBoxbuybtc" class="col-xs-4">
					    <span class="trade-depth" style="background: none;color: #333;">
						<spring:message code="nav.index.lastprice" />: 
						<span class=""> 
							<span id="lastprice"> </span> 
							<span id="lastpriceicon" class="arrow-icon-big red"></span>
						</span>
						</span>
                        <span class="trade-depth1" style="color:red;">
                        <c:if test="${isLimittrade}">
		  	            <spring:message code="entrust.coin.highlimit" />:<ex:DoubleCut value="${upPrice }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/> ~ <spring:message code="entrust.coin.lowlimit" />:<ex:DoubleCut value="${downPrice }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/>
		  	           </c:if>
                        </span>
						<ul id="sellbox" class="list-group first-child">
						</ul>
						<ul id="buybox" class="list-group ">
						</ul>
						<span class="trade-depth"> <spring:message code="entrust.coin.recent" /></span>
						<ul id="logbox" class="list-group ">
						</ul>
					</div>
				</div>
				<div id="entrustInfo">
				</div>
			</div>
		</div>
	</div>
	<div class="modal modal-custom fade" id="tradepass" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel">
		<div class="modal-dialog modal-trading-dialog" role="document">
			<div class="modal-mark"></div>
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close btn-modal" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<span class="modal-title" id="exampleModalLabel"><spring:message code="security.tradingpwd" /></span>
				</div>
				<div class="modal-body">
					<div class="form-group">
						<input type="password" autocomplete="off" class="form-control" id="tradePwd" placeholder="<spring:message code="security.tradingpwd" />">
					</div>
				</div>
				<div class="modal-footer">
					<button id="modalbtn" type="button" class="btn btn-primary"><spring:message code="market.confirm" /></button>
				</div>
			</div>
		</div>
	</div>
	<div class="modal modal-custom fade" id="entrustsdetail" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-mark"></div>
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close btn-modal" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<span class="modal-title" id="exampleModalLabel"></span>
				</div>
				<div class="modal-body"></div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="market.confirm" /></button>
				</div>
			</div>
		</div>
	</div>
	<input id="coinshortName" type="hidden" value="${coin2.fShortName }">
	<input id="symbol" type="hidden" value="${ftrademapping.fid }">
	<input id="isopen" type="hidden" value="${needTradePasswd }">
	<input id="tradeType" type="hidden" value="0">
	<input id="userid" type="hidden" value="${userid }">
	
	<input id="tradePassword" type="hidden" value="${isTradePassword }">
	<input id="isTelephoneBind" type="hidden" value="${isTelephoneBind }">
	
	
    <input id="coinCount1" type="hidden" value="${ftrademapping.fcount1 }">
    <input id="coinCount2" type="hidden" value="${ftrademapping.fcount2 }">
    <input id="minBuyCount" type="hidden" value="<ex:DoubleCut value="${ftrademapping.fminBuyCount }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="${ftrademapping.fcount2 }" />">
    <input id="limitedType" type="hidden" value="0">

 
	<jsp:include page="../comm/footer.jsp"></jsp:include>
	<script type="text/javascript" src="${oss_url}/static/front/js/plugin/jquery.jslider.js?v=20181126201750"></script>
	<script type="text/javascript" src="${oss_url}/static/front/js/trade/trade.js?r=<%=new java.util.Date().getTime() %>"></script>
 	<script type="text/javascript" src="${oss_url}/static/front/js/trade/trademarket.js?r=<%=new java.util.Date().getTime() %>"></script>
</body>

<script type="text/javascript">
var leftpath = window.location.pathname;
	var path = window.location.href.replace('http://'+window.location.host,'') ;
	if(leftpath.startWith("/trade/")){//交易中心特殊处理

	var count = 0 ;
	var isshow = false;
	$(".leftmenu").each(function(){
			var flag = false ;
			$(this).find("a").each(function(){
				if(path.indexOf($(this).attr("href")) != -1){
					$(this).parent().addClass("active") ;
					flag = true ;
					count++ ;
				}
			}) ;
			if(flag == false ){
				$(this).remove();
			}
	}) ;	
				
	}
</script>
</html>

