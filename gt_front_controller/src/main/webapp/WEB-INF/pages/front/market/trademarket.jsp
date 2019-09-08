<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ include file="../comm/include.inc.jsp"%>
<%

%>

<!doctype html>
<html>
<head> 
<%@ include file="../comm/basepath.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<%@include file="../comm/link.inc.jsp" %>
<link rel="stylesheet" href="${oss_url}/static/front2018/css/trade.css?v=20171026105829.css" type="text/css"></link>
<link rel="stylesheet" href="${oss_url}/static/front2018/css/jquery.mCustomScrollbar.css?v=20171026105823.css" type="text/css"></link>
<link href="https://fonts.googleapis.com/css?family=Open+Sans" rel="stylesheet">

<style>
.f_list h2,.CopyRight,.f_list ul li a,.f_list ul li{color: #9c9cb1;}
.l-footer,.CopyRight{background: #262a42;}
.mCSB_inside > .mCSB_container{    margin-right: 0px;}
.mCSB_scrollTools{width:3px;}
.mCSB_scrollTools .mCSB_dragger .mCSB_dragger_bar{background-color: #868686;}
.marketDepthSell-min{    
position: absolute;
    bottom: 0px;
    width: 100%;}
.marketDepthSell-max{height: 396px;}
</style>
</head>
<body class="tradeBg">
<%@include file="../comm/header.jsp" %>
<div class="tradeMain">
    <div class="trade_up clear">
     <!--left部分 -->
        <div class="trade_upL fl">
        <!-- left部分的TOP -->
            <div class="tradeL_tit bgColor clear mgb10">
                <div class="ser fl">
                    <span class='cblue2'><spring:message code="new.markettit" /></span>
                    <input type="text" class="t_txt" id="search" />
                    <svg class="icon cblue2 ser_icon" aria-hidden="true">
                        <use xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="#icon-sousuo"></use>
                    </svg>
                </div>               
            </div>
        <!--  left部分的Center  -->
            <div class="coin_filter bgColor">
            
            <ol class="clear">
                <!-- <li class="fl txtLeft active"><span>USDT</span></li>
                <li class="fl txtCenter"><span>BTC</span></li>
                <li class="fl txtRight"><span>ETH</span></li> -->
                <%-- <li class="fl txtCenter txtCenterLI"><span><spring:message code="nav.index.optional" /></span></li> --%>
               <c:forEach items="${requestScope.constant['fbs']}" var="v" varStatus="n">
                <li class="fl txtCenter txtCenterLI <c:if test="${v.fShortName==ftrademapping.fvirtualcointypeByFvirtualcointype1.fShortName }"> active</c:if>"><span>${v.fShortName }</span></li>
                </c:forEach>
            </ol>
                <div class="coinTable bgColor">
                    <div class="coin_list">
                        <ol class="clear">
                            <li class="fl txtLeft active"><spring:message code="nav.index.coin" /></li>
                            <li class="fl txtCenter"><spring:message code="market.price" /></li>
                            <li class="fl txtRight"><spring:message code="market.change" /></li>
                        </ol>

                        <c:forEach items="${requestScope.constant['fbs']}" var="v" varStatus="n">
                        <ul class="ul_list curDate_con <c:if test="${v.fShortName==ftrademapping.fvirtualcointypeByFvirtualcointype1.fShortName }"> active clear</c:if>">
                        <c:forEach items="${ftrademappings }" var="vv">
		                <c:if test="${v.fShortName==vv.fvirtualcointypeByFvirtualcointype1.fShortName }">
                            <li class="markent_s market_show" data-key="${vv.fvirtualcointypeByFvirtualcointype2.fShortName}">
                                <a href="/trademarket.html?symbol=${vv.fid }">
	                                <span  style="padding-left: 14px;" class="coin_name txtLeft lw-coinName" data-setter="${vv.fid }">${vv.fvirtualcointypeByFvirtualcointype2.fShortName}</span>
	                                <span class="coin_price txtCenter lw-zuixin lw-coinPrice">-</span>
	                                <span class="coin_zf cred txtRight cgreen2 lw-fwf">-</span>
                                </a>
                            </li>                                                 
                          </c:if>

		              </c:forEach>
		              </ul> 
		              	</c:forEach>
                    </div>
                </div>
                
            </div>
      
       <!-- 实时成交 -->
                <div class="trade_box trade_recode">
                    <div class="tradingDate tradingDate_2 curDate_trade fl bgColor">
                        <div class="curLeft clear" style="border-top: none;">
                            <span class="fl" style="width:102px;"><spring:message code="new.timetrade" /></span>
                        </div>
                        <div class="title lightBlue" style="padding-right: 4px;border-bottom: 1px solid #394761;padding-top: 7px;">
                            <span><spring:message code="market.date" /></span>
                            <span><spring:message code="market.entrusttype" /></span>
                            <span><spring:message code="market.price" />(${ftrademapping.fvirtualcointypeByFvirtualcointype1.fSymbol })</span>
                            <span><spring:message code="market.volume" /></span>
                        </div>
                        <div class="curDate_con" style="height: 338px">
                            <ul id="marketSuccessData" class="marketSucDate"></ul>
                        </div>
                    </div>
                </div>
        <!-- END实时成交 -->
        </div>
        <!--  -->
        <div class="trade_upR">
        	<div class="trade_header">
             <div class="tradeArea bgColor">
                <div class="trade_upR_tit">
                    <dl class="ticker_wrap clear">  
                        <dt class="dt_1">
	                        <span>${ftrademapping.fvirtualcointypeByFvirtualcointype2.fShortName }/${ftrademapping.fvirtualcointypeByFvirtualcointype1.fShortName }</span>
	                         <p>
		                        <span class="ticker_close" id="kaipan">${ftrademapping.fprice}</span>
		                        <span class="" id="kaipancny"></span>
	                        </p>
                        </dt>
                        
                        <dd><span name="rate" class="cred" id="rosed"></span><spring:message code="market.24change" /> </dd>
                        <dd><span name="high" id="hoursgao"></span><spring:message code="nav.index.24hign" /> </dd> 
                        <dd><span name="low" id="hoursdi"></span><spring:message code="nav.index.24low" /> </dd> 
                        <dd><span name="amount" id="vold"></span><spring:message code="market.24volume" /></dd> 
                        <dd style="color:#11735D;float:right;width: 120px;">
                        <c:if test="${ftrademapping.fprojectId>0 && null != coinmount && coinmount > 0}">
	                        <p class="hourshi">
	                        <span><img src="${oss_url}/static/front2018/images/shi.png"></span>
	                        <label style="color:#fff;font-size: 18px;"><ex:DoubleCut value="${coinmount}" pattern="##" maxIntegerDigits="15" maxFractionDigits="0"/>${coinName }</label>
	                        </p>
                      		<label><spring:message code="market.marketdespoit"/></label>
                      		</c:if>
                    	</dd> 
                    </dl>
                </div>
             <!--    <div class="timeArea">
                    <a href="javascript:;">分时</a>
                    <a href="javascript:;">1min</a>
                    <a href="javascript:;">5min</a>
                    <a href="javascript:;">15min</a>
                    <a href="javascript:;">30min</a>
                    <a href="javascript:;">1hour</a>
                    <a href="javascript:;">4hour</a>
                    <a href="javascript:;">1day</a>
                </div> -->
                <div class="trade_kline bgColor" id="marketStart" >
                	
				<iframe frameborder="0" border="0" width="100%" height="100%" id="klineFullScreen" allowfullscreen="true" src="/kline/fullstart2018.html?symbol=${symbol }&themename=dark"></iframe>
				
                </div>
            </div>
           	<div class="trade_box fl">
            		<ul class="trade_box_ul">
            			<li class="box_ulBlue" name="PriceDeal"><spring:message code="market.limittrade" /></li>
            			<li name="present"><spring:message code="market.openorders" /></li>
                		<li name="history"><spring:message code="market.tradehis" /></li>
            		</ul>
            		<!-- 限价交易 -->
	           		<div class="entrust PriceDeal">
	                   <div class="trade_trade fl">
	                       <div class="tr_title bgColor clear" style="border-top:none;line-height: 34px;height: 34px;font-size: 14px;">
	                           <span class="fl" style="padding-left:10px;">
	                           <spring:message code="market.usable" />&nbsp;<em class="cred" id="totalCny">0</em>&nbsp;${ftrademapping.fvirtualcointypeByFvirtualcointype1.fShortName }</span>
	                          <!--  <a href="#" class="fr lightBlue">充币</a> -->
	                       </div>
	                       <div class="sellMain bgColor">
	                           <div class="buy_Price"> 
	                            <p class="lightBlue"><spring:message code="market.buyprice" /></p>
	                            <div class="write_box">
	                                <input type="text" class="input_txt 123" id="buy-price"/>
	                                <span class="unit lightBlue">${ftrademapping.fvirtualcointypeByFvirtualcointype1.fShortName }</span>
	                            </div>
	                           </div>
	                           <p class="math_price">
	                               ≈<em class="buyCNY"> --</em>
	                           </p>
	                          <div class="buy_Amount clear">
	                            <p class="lightBlue"><spring:message code="market.buyvolume" /></p>
	                            <div class="write_box">
	                                <input type="text" class="input_txt" id="buy-amount"/>
	                                <span class="unit lightBlue">${ftrademapping.fvirtualcointypeByFvirtualcointype2.fShortName }</span>
	                            </div>
	                           </div>
	                           <div class="progress clear">
	                               <ul id="buyBar">
	                                   <li class="proportioncircle proportion0" data-points="25" onclick="$('#buyBar li').removeClass('active');$(this).addClass('active')"><span class='fl'>25%</span></li>
	                                   <li class="proportioncircle proportion1" data-points="50" onclick="$('#buyBar li').removeClass('active');$(this).addClass('active')"><span class='fl'>50%</span></li>
	                                   <li class="proportioncircle proportion2" data-points="75" onclick="$('#buyBar li').removeClass('active');$(this).addClass('active')"><span class='fl'>75%</span></li>
	                                   <li class="proportioncircle proportion3" data-points="100" onclick="$('#buyBar li').removeClass('active');$(this).addClass('active')"><span class='fl'>100%</span></li>
	                               </ul>
	                           </div>
	                           <div class="tradeMoney clear">
	                               <span class="fl"><spring:message code="market.total" /> <em class="cred" id="buy-limit">0.0000</em> ${ftrademapping.fvirtualcointypeByFvirtualcointype1.fShortName }</span>
	                               <span class="fr"><spring:message code="market.fee" /><em><fmt:formatNumber type="percent" value="${fbuyfee }" maxFractionDigits="3" /></em></span>
	                           </div>
	                           <button class="buy_btn" id="buy_sub"><spring:message code="market.buy" /></button>
	                       </div>
	                   </div>                    
	                   <div class="trade_trade fl">
	                       <div class="tr_title bgColor clear" style="border-top:none;line-height: 34px;height: 34px;font-size: 14px;">
	                           <span class="fl" style="padding-left:10px;">
	                           <spring:message code="market.usable" />&nbsp;<em class="cgreen2" id="totalCoin">0.00000</em>&nbsp;${ftrademapping.fvirtualcointypeByFvirtualcointype2.fShortName }</span>
	                           <!-- <a href="#" class="fr lightBlue">充币</a> -->
	                       </div>
	                       <div class="sellMain bgColor">
	                        <div class="buy_Price">
	                            <p class="lightBlue"><spring:message code="market.sellprice" /></p>
	                            <div class="write_box">
	                                <input type="text" class="input_txt" id="sell-price"/>
	                                <span class="unit lightBlue">${ftrademapping.fvirtualcointypeByFvirtualcointype1.fShortName }</span>
	                            </div>
	                         </div>
	                           <p class="math_price">
	                               ≈<em class="sellCNY"> --</em>
	                           </p>
	                           <div class="buy_Amount clear">
	                            <p class="lightBlue"><spring:message code="market.sellvolume" /></p>
	                            <div class="write_box">
	                                <input type="text" class="input_txt" id="sell-amount"/>
	                                <span class="unit lightBlue">${ftrademapping.fvirtualcointypeByFvirtualcointype2.fShortName }</span>
	                            </div>
	                           </div>
	                           <div class="progress clear">
	                               <ul id="sellBar">
	                                   <li class="proportioncircle proportion0" data-points="25" onclick="$('#sellBar li').removeClass('active');$(this).addClass('active')"><span class='fl'>25%</span></li>
	                                   <li class="proportioncircle proportion1" data-points="50" onclick="$('#sellBar li').removeClass('active');$(this).addClass('active')"><span class='fl'>50%</span></li>
	                                   <li class="proportioncircle proportion2" data-points="75" onclick="$('#sellBar li').removeClass('active');$(this).addClass('active')"><span class='fl'>75%</span></li>
	                                   <li class="proportioncircle proportion3" data-points="100" onclick="$('#sellBar li').removeClass('active');$(this).addClass('active')"><span class='fl'>100%</span></li>
	                               </ul>
	                           </div>
	                            <div class="tradeMoney clear">
	                               <span class="fl"><spring:message code="market.total" /> <em class='cred' id="sell-limit">0.0000</em> ${ftrademapping.fvirtualcointypeByFvirtualcointype1.fShortName }</span>
	                               <span class="fr"><spring:message code="market.fee" /><em><fmt:formatNumber type="percent" value="${ffee }" maxFractionDigits="3" /></em></span>
	                           </div>
	                           <button class="buy_btn sell_btn" id="sell_sub"><spring:message code="market.sell" /></button>
	                       </div>
	                   </div>
	           		</div>
	           		<!-- 当前委托 -->
         		<div class="entrust present curEntrust" style="display:none;">
         		<div class="EntrutsCon">
	                <table class="table">
	                    <tr class="th_title">
	                        <th><spring:message code="market.entrustdate" /></th>
	                        <th><spring:message code="market.entrusttype" /></th>
	                        <th><spring:message code="market.entrustprice" /></th>
	                        <th><spring:message code="market.entrustamount" /></th>
	                        <th><spring:message code="market.entrusttotal" /></th>
	                        <th><spring:message code="market.entruststatus" /></th>
	                        <th class="textR"><spring:message code="market.entrustaction" /></th>
	                    </tr>
	                </table>
	                <div class="Entruts_list">
	                    <table class="table">       
	                          <tbody class="" id="entrutsCurData"></tbody>         
	                    </table>
	                </div>
	            </div>           
           </div>
	           		<!-- 历史委托 -->
	           		<div class="entrust history curEntrust" style="display:none;"><div class="EntrutsCon historyCon">
                <table class="table">
                    <tr class="th_title">
                        <th><spring:message code="market.entrustdate" /></th>
                        <th><spring:message code="market.entrusttype" /></th>
                        <th><spring:message code="market.price" /></th>
                        <th><spring:message code="market.entrustamount" /></th>
                        <th><spring:message code="market.entrusttotal" /></th>
                        <th><spring:message code="market.entruststatus" /></th>
           
                    </tr>
                </table>
                <div class="Entruts_list">
                    <table class="table">
                                        
                       <tbody class="" id="entrutsHisData">
                          </tbody>               
         
                    </table>
                </div>
            </div>
        </div>
             </div>
            
            </div>
            <!-- right -->
            <div class="trade_middle">
                	
             <div class="tradingDate">
                  <div class="title bgColor lightBlue">
                      <span class="s_tr1"><spring:message code="market.entrusttype" /></span>
                      <span><spring:message code="market.price" />(${ftrademapping.fvirtualcointypeByFvirtualcointype1.fSymbol })</span>
                      <span><spring:message code="market.amount" /></span>
                  </div>
                   <div class="new_trade bgColor">  
                        <div class="sellCon" >
							<ul id="marketDepthSell" class=""></ul>
						</div>
	                  <div class="tr_title bgColor clear">
	                      	<%-- <spring:message code="market.lastprice" />&nbsp;<em id="marketPrice" class="cred">0.00000</em> --%>
	                      	<label>${ftrademapping.fvirtualcointypeByFvirtualcointype2.fShortName }/${ftrademapping.fvirtualcointypeByFvirtualcointype1.fShortName }</label>
	                      	<b id="marketPriceUD"></b>
	                      <!-- 	<em id="marketPrice" class="cred">0.00000</em> -->
	                      	<em id="marketPriceLI" class="">0.00000</em>
	                      	<label id="kaipancnyLI"></label>
	                  </div>
                       <div class="buyCon" >
                          <ul id="marketDepthBuy"></ul>
                      </div>                         
                   </div>
               </div>       
            </div>
        </div>
    </div>
<%--     <div class="trade_down">
        <div class="curEntrust">
            <ol class="title clear">
                <li><a href="javascript:;"><spring:message code="market.openorders" /></a></li>
                <li><a href="javascript:;"><spring:message code="market.tradehis" /></a></li>
            </ol>
            <div class="EntrutsCon">
                <table class="table">
                    <tr class="th_title">
                        <th><spring:message code="market.entrustdate" /></th>
                        <th><spring:message code="market.entrusttype" /></th>
                        <th><spring:message code="market.entrustprice" /></th>
                        <th><spring:message code="market.entrustamount" /></th>
                        <th><spring:message code="market.entrusttotal" /></th>
                        <th><spring:message code="market.entruststatus" /></th>
                        <th class="textR"><spring:message code="market.entrustaction" /></th>
                    </tr>
                </table>
                <div class="Entruts_list">
                    <table class="table">       
                          <tbody class="" id="entrutsCurData"></tbody>         
                    </table>
                </div>
            </div>           
            <div class="EntrutsCon historyCon">
                <table class="table">
                    <tr class="th_title">
                        <th><spring:message code="market.entrustdate" /></th>
                        <th><spring:message code="market.entrusttype" /></th>
                        <th><spring:message code="market.price" /></th>
                        <th><spring:message code="market.entrustamount" /></th>
                        <th><spring:message code="market.entrusttotal" /></th>
                        <th><spring:message code="market.entruststatus" /></th>
           
                    </tr>
                </table>
                <div class="Entruts_list">
                    <table class="table">
                                        
                       <tbody class="" id="entrutsHisData">
                          </tbody>               
         
                    </table>
                </div>
            </div>
        
        </div>
    </div> --%>
</div>

<div class="modal modal-custom fade" id="tradepass" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel">
	<div class="modal-dialog" role="document">
		<div class="modal-mark"></div>
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close btn-modal" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<span class="modal-title" id="exampleModalLabel"><spring:message code="market.tradingpwd" /></span>
			</div>
			<div class="modal-body form-horizontal">
				<div class="form-group">
					<div class="col-xs-3 control-label">
						<span><spring:message code="market.tradingpwd" /></span>
					</div>
					<div class="col-xs-6 padding-clear">
						<input type="password" class="form-control" id="tradePwd" placeholder="<spring:message code="market.tradingpwd" />">
					</div>
				</div>
				<div class="form-group">
					<div class="col-xs-6 padding-clear col-xs-offset-3">
						<span id="errortips" class="error-msg text-danger"></span>
					</div>
				</div>
				<div class="form-group margin-bottom-clear">
					<div class="col-xs-6 padding-clear col-xs-offset-3">
						<button id="modalbtn" type="button" class="btn btn-danger btn-block"><spring:message code="security.submit" /></button>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<%@include file="../comm/footer.jsp" %>

<input type="hidden" name="fbprice" id="fbprice" value="1">
<input type="hidden" name="usdtrate" id="usdtrate" value="6.5">

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
<input id="isGoogleBind" type="hidden" value="${isGoogleBind }">
<input id="isftrademapping" type="hidden" value="${ftrademapping.fid }">
<c:choose>
<c:when test="${pageContext.response.locale eq 'zh_CN' }"> 
	<input type="hidden" name="curlang" id="curlang" value="cn">  
</c:when>
<c:otherwise>
   <input type="hidden" name="curlang" id="curlang" value="en">
   </c:otherwise>
</c:choose> 
<!-- 2018-3-2tab切换 -->
<script type="text/javascript">
	var configJSON = '${oss_url}/static/front/config/config.json?t='+new Date().getTime();
</script>

<script type="text/javascript" src="${oss_url}/static/front2018/js/jquery.mCustomScrollbar.concat.min.js"></script>
<script type="text/javascript" src="${oss_url}/static/front2018/js/plugin/bootstrap.js?v=20171026105823.js"></script>
<script type="text/javascript" src="${oss_url}/static/front2018/js/plugin/layer/layer.js?v=20171026105823.js"></script>
<script type="text/javascript" src="${oss_url}/static/front2018/js/comm/util.js?v=20171026105823.js"></script>
<script type="text/javascript" src="${oss_url}/static/front2018/js/comm/comm.js?v=20171026105823.js"></script>
<script type="text/javascript" src="${oss_url}/static/front2018/js/plugin/jquery.jslider.js?v=20171026105823.js"></script>

<script type="text/javascript" src="${oss_url}/static/front2018/js/language/language_<spring:message code="language.title" />.js?v=20171026105823.js"></script>

<script type="text/javascript" src="${oss_url}/static/front/js/tradingview/socket.js"></script>

<script type="text/javascript" src="${oss_url}/static/front2018/js/market/trademarket.js?t=<%=new java.util.Date().getTime() %>"></script>
<script type="text/javascript" src="${oss_url}/static/front2018/js/market/tradegetdata.js?t=<%=new java.util.Date().getTime() %>"></script>

<script type="text/javascript">

function getData(data){
 //$.get(url,function(data){ 
 	var rate =1;
		var fbprice = data.fbprice;
		var cursymbol = '$';
		var curlang = document.getElementById("curlang").value;
		document.getElementById("fbprice").value=fbprice;
		
		if(curlang=='cn'){
			cursymbol = '￥';
			rate =data.usdtrate;
		}else{
			cursymbol = '$';
			rate =1;
		}
		document.getElementById("usdtrate").value=rate;
		var fbsymbol = '${ftrademapping.fvirtualcointypeByFvirtualcointype1.fSymbol}';
 	var pbits = Number(data.pbits);
		var vbits = Number(data.vbits);
 	document.getElementById('vold').innerHTML = data.vol;
 	var kaipan  = util.numFormat(Number(data.p_new), pbits);
 	var hoursdi = util.numFormat(Number(data.low), pbits);
     var hoursgao = util.numFormat(Number(data.high), pbits);
 	document.getElementById('kaipan').innerHTML =fbsymbol+ kaipan ;
 	document.getElementById('hoursdi').innerHTML = hoursdi;
     document.getElementById('hoursgao').innerHTML = hoursgao;
 	document.getElementById('kaipancny').innerHTML ="/" + cursymbol +util.numFormat(Number(data.p_new)*rate*fbprice, pbits);
 	document.getElementById('kaipancnyLI').innerHTML ="/" + cursymbol +util.numFormat(Number(data.p_new)*rate*fbprice, pbits);
 	$('#kaipan').addClass("coin_price");
 	if(data.rose == 0){
 		var rose = "+" + data.rose + ".00%";
 		document.getElementById('rosed').innerHTML = rose;
 		document.getElementById('rosed').className = "lw-coin cred";
 		return;
 	}
 	var rose = data.rose.toString();

 	if(rose.indexOf('-') == -1){
 		document.getElementById('rosed').innerHTML = "+" + rose + "%";
 		document.getElementById('rosed').className = "lw-coin cred";
 	}else{
 		document.getElementById('rosed').innerHTML = rose + "%";
 		document.getElementById('rosed').className = "lw-coin cgreen2";
 	}
	
	//});

}
$(function(){
	$(".trade_box_ul li").click(function(){
		var name = $(this).attr("name");
		$(".trade_box_ul li").removeClass('box_ulBlue');
		$(this).addClass('box_ulBlue');
		$(".entrust").hide();
		$(".trade_box ." + name).show();
	})
	
})

</script>

</body>
</html>
