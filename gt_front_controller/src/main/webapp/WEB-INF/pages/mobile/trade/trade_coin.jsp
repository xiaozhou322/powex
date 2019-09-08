<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../comm/include.inc.jsp"%>
<%

%>


<!doctype html>
<html>
<head>
<meta name = "viewport" content = "width = device-width, user-scalable = no,initial-scale=0.5,maximum-scale=0.5,minimum-scale=0.5">
<jsp:include page="../comm/link.inc.jsp"></jsp:include>
<link href="${oss_url}/static/front/css/trade/trade.css?v=20181126201750" rel="stylesheet" type="text/css" media="screen, projection" />
</head>
<body>

<jsp:include page="../comm/header.jsp"></jsp:include>


	<div class="container-full padding-top-40">
		<div class="container displayFlex">
			
			<%@include file="../comm/left_menu.jsp" %>
			
			<div class="col-xs-10 padding-right-clear">
				<div class="col-xs-12 padding-right-clear padding-left-clear rightarea trade">
					
					<c:if test="${isTradePassword == false}">
						<div class="col-xs-12 rightarea-top">
							<span class="col-xs-5"> <span>在进行交易前，您需要完成</span> <a
								href="/user/security.html" class="text-danger">安全设置</a> </span> <span
								class="pull-right"> <span class="trade-process active">
									<i class="icon">1</i> <span>安全设置</span> <i class="arrow"></i> </span>
								<span class="trade-process "> <i class="icon">2</i> <span>充值</span>
									<i class="arrow"></i> </span> <span class="trade-process"> <i
									class="icon">3</i> <span>下单交易</span> <i class="arrow"></i> </span> </span>
						</div>
					</c:if>
				
				<div class="col-xs-12 rightarea-con padding-right-clear padding-left-clear">
					<div class="col-xs-8">
						<div class="max-width trade-buysell">
							<div class="max-width trade-amount clearfix">
								<span class="col-xs-4" style="width: 40%;">
									<span>可用</span>
									<span id="toptradecnymoney" class="redtips">
										0
									</span>
									<span class="redtips">${coin1.fShortName }</span>
								</span>
								
							<!-- 	<span class="col-xs-4 text-center">
									<span class="databtn datatime datatime-sel" data-type="1">限价</span>
									<span class="databtn datatime" data-type="2">市价</span>
								</span> -->
								
								<span class="col-xs-4 text-right">
									<span>冻结</span>
									<span id="toptradelevercny">0.00</span>
									<span>${coin1.fShortName }</span>
									<i></i>
								</span>
							</div>
							<div class="max-width clearfix" id="buypricediv">
								<div class="col-xs-6">
									<div class="form-group">
										<label for="tradebuyprice" class="trade-inputtips">买入价${coin1.fShortName }/${coin2.fShortName }</label>
										<input id="tradebuyprice" class="form-control" type="text" autocomplete="off" value="${recommendPrizebuy }">
									</div>
									<!-- <div class="form-group">
										<span class="form-control trade-tips padding-right-clear">
											<span id="buyBar" class="col-xs-12 buysellbar">
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
											<span id="buyslidertext" class="col-xs-12 text-center">0%</span>
										</span>
									</div> -->
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
									<div class="form-group">
										<label for="tradebuyamount" class="trade-inputtips">买入量${coin2.fShortName }</label>
										<input id="tradebuyamount" class="form-control" type="text" autocomplete="off" value="">
									</div>
									<div class="form-group">
										<span class="form-control trade-tips">
											<span class="col-xs-5 text-left padding-right-clear">交易额</span>
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
								<div class="col-xs-6">
									<div class="form-group">
										<button id="buybtn" class="btn btn-danger btn-block">买入${coin2.fShortName }</button>
									</div>
								</div>
								<div class="col-xs-6">
									<div class="form-group">
										<c:choose>
											<c:when test="${coin1.ftype==0 }">
												<a href="/account/rechargeCny.html" class="text-danger">立即充值<i class="arrow-icon-small red"></i></a>
											</c:when>
											<c:otherwise>
												<a href="/account/rechargeBtc.html?symbol=${coin1.fid }" class="text-danger">立即充值<i class="arrow-icon-small red"></i></a>
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
									<div class="form-group">
										<span class="form-control trade-tips">
											<span class="col-xs-12 padding-right-clear ">
												<label for="tradebuyprice2" class="trade-inputtips">交易额/${coin1.fShortName }</label>
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
										<button id="buybtn2" class="btn btn-danger btn-block">买入${coin2.fShortName }</button>
									</div>
								</div>
								<div class="col-xs-6">
									<div class="form-group">
										<c:choose>
											<c:when test="${coin1.ftype==0 }">
												<a href="/account/rechargeCny.html" class="text-danger">立即充值<i class="arrow-icon-small red"></i></a>
											</c:when>
											<c:otherwise>
												<a href="/account/rechargeBtc.html?symbol=${coin1.fid }" class="text-danger">立即充值<i class="arrow-icon-small red"></i></a>
											</c:otherwise>
										</c:choose>
									</div>
								</div>
							</div>
						</div>
						<div class="max-width trade-buysell">
							<div class="max-width trade-amount clearfix">
								<span class="col-xs-4" style="width: 40%;">
									<span>可用</span>
									<span id="toptrademtccoin" class="greentips">
										0
									</span>
									<span class="greentips">${coin2.fShortName }</span>
								</span>
								
								<!-- <span class="col-xs-4 text-center">
									<span class="databtn datatime datatime-sel" data-type="3">限价</span>
									<span class="databtn datatime" data-type="4">市价</span>
								</span> -->
								
								<span class="col-xs-4 text-right">
									<span>冻结</span>
									<span id="toptradelevercoin">0.0</span>
									<span>${coin2.fShortName }</span>
									<i></i>
								</span>
							</div>
							<div class="max-width clearfix" id="sellpricediv">
								<div class="col-xs-6">
									<div class="form-group">
										<label for="tradesellprice" class="trade-inputtips">卖出价${coin1.fShortName }/${coin2.fShortName }</label>
										<input id="tradesellprice" class="form-control" type="text" autocomplete="off" value="${recommendPrizesell }">
									</div>
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
										<label for="tradesellamount" class="trade-inputtips">卖出量${coin2.fShortName }</label>
										<input id="tradesellamount" class="form-control" type="text" autocomplete="off" value="">
									</div>
									<div class="form-group">
										<span class="form-control trade-tips">
											<span class="col-xs-5 text-left padding-right-clear">交易额</span>
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
								<div class="col-xs-6">
									<div class="form-group">
										<button id="sellbtn" class="btn btn-success btn-block">卖出${coin2.fShortName }</button>
									</div>
								</div>
								<div class="col-xs-6">
									<div class="form-group">
										<a href="/account/rechargeBtc.html?symbol=${coin2.fid }" class="text-success">立即充值<i class="arrow-icon-small green"></i></a>
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
												<label for="tradesellprice2" class="trade-inputtips">交易数量/${coin2.fShortName }</label>
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
										<button id="sellbtn2" class="btn btn-success btn-block">卖出${coin2.fShortName }</button>
									</div>
								</div>
								<div class="col-xs-6">
									<div class="form-group">
										<a href="/account/rechargeBtc.html?symbol=${coin2.fid }" class="text-success">立即充值<i class="arrow-icon-small green"></i></a>
									</div>
								</div>
							</div>
							
						</div>
					</div>
					<div id="coinBoxbuybtc" class="col-xs-4">
					    <span class="trade-depth" style="background: none;color: #fff;">
									           最新价： <span class=""> <span
												id="lastprice"> </span> <span id="lastpriceicon" class="arrow-icon-big red"></span></span>
						</span>
                        <span class="trade-depth1" style="color:red;">
                        <c:if test="${isLimittrade}">
		  	                                    涨停价:<ex:DoubleCut value="${upPrice }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/> ~ 跌停价:<ex:DoubleCut value="${downPrice }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/>
		  	           </c:if>
                        </span>
						<ul id="sellbox" class="list-group first-child">
						</ul>
						<ul id="buybox" class="list-group ">
						</ul>
						<span class="trade-depth"> 最近成交记录</span>
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
					<span class="modal-title" id="exampleModalLabel">交易密码</span>
				</div>
				<div class="modal-body">
					<div class="form-group">
						<input type="password" autocomplete="off" class="form-control" id="tradePwd" placeholder="请输入交易密码">
					</div>
				</div>
				<div class="modal-footer">
					<button id="modalbtn" type="button" class="btn btn-primary">确定</button>
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
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
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
	<script src="${oss_url}/static/mobile2018/js/fluckydraw/com.js?v=20181126201750"></script>
	<script type="text/javascript" src="${oss_url}/static/front/js/plugin/jquery.jslider.js?v=20181126201750"></script>
	<script type="text/javascript" src="${oss_url}/static/front/js/trade/trade.js?r=<%=new java.util.Date().getTime() %>"></script>
 	<script type="text/javascript" src="${oss_url}/static/front/js/trade/trademarket.js?r=<%=new java.util.Date().getTime() %>"></script>
</body>
</html>

