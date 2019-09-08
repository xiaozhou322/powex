<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ include file="../comm/include.inc.jsp"%>
<%

%>

<!doctype html>
<html>
<head> 
<%@ include file="../comm/basepath.jsp"%>
<%@include file="../comm/link.inc.jsp" %>
 <meta name = "viewport" content = "width = device-width, user-scalable = no,initial-scale=0.5,maximum-scale=0.5,minimum-scale=0.5">
  <script src="${oss_url}/static/mobile2018/js/fluckydraw/com.js?v=20181126201750"></script>
<script type="text/javascript" src="${oss_url}/static/front/js/index/jquery.cookie.js?v=5"></script>
 <style type="text/css">
   @font-face {
      font-family: 'PingFangMedium';
      src: url('/static/mobile2018/fonts/PingFangMedium.ttf'); 
   } 
   .tradeCenter{padding-bottom:55px!important;}
   .tradeKline{height:350px;}
   .lw-description{font-size:12px; color:#999;}
   .trade_business{overflow:hidden; padding:5px 0;}
   .trade_business>a{width:49.4%; border-radius:0;}
   .lw-tradeSelect{margin:0 0 5px 0;}
   .buyBar li span.buyActive{
    color: #da2e22;
    border-bottom: 2px solid #da2e22;
    text-align:center;
    font-weight:bold;
	}
	.buyBar li span.sellActive{
    color: #009900;
    border-bottom: 2px solid #009900;
    text-align:center;
	}
	.circle{display:none;}
	.trade_r2{position: relative; padding-top:20px;}
	.trade_r2 .emW{position: absolute; left:10px; margin-top:6px; padding:0;}
	.buyCon .trade_r .nums{padding:1px 0 0 150px;}
	.weiTuo{background:none;}
	.weiTuo ol{background:#fff;}
	.weiTuo ol li{font-size:0.55rem; color:#666;}
	.p_tit{overflow:hidden; background:#fff; border-bottom: 1px solid #e7e7e7; font-size:14px;}
	.p_tit em{float:left; display:block; width:33%; text-align:center; color:#999;}
	.weiTuo table td .s_01{font-weight:normal; font-size: 13px;}
	.buyTit{font-weight:normal; font-size:13px;}
	.trade_success{width:100%; background:#fff; color:#333;}
	.trade_success h2{font-size:16px; padding:6px 10px;}
	.trade_tab{width:100%; border-bottom: 1px solid #e4e4e4;}
	.trade_tab p,.trade_successMain{padding:0 10px; overflow:hidden;}
	.trade_successMain{height:200px; overflow-y:scroll;}
	.trade_tab span,.trade_successMain p em{width:33.3%; display:block;}
	.trade_successMain p em{padding:6px 0;}
	.market-font-sell{color:#009900!important;}
	.market-font-buy{color:#da2e22!important;}
	.sellDateBox,.buyDateBox,.weituoDateBox{height:220px; overflow-y:scroll; background:#fff; margin-bottom:8px;}
    .swit{position: absolute; right:10px; top:7px;}
</style>
<style type="text/css">
[class|=switch] {position: relative;display:block;width:58px;height:20px;border-radius: 16px;line-height:20px;-webkit-tap-highlight-color:rgba(255,255,255,0);}
.switch-on {border: 1px solid white;box-shadow: white 0px 0px 0px 16px inset;transition: border 0.4s, box-shadow 0.2s, background-color 1.2s;background-color: white;cursor: pointer; text-align:right; padding:0 5px 0 0;}
.switch-on em{color:#fff;}
#coinSwitch{margin:4px 0 0 50px;}
#coinSwitch em{font-size:12px; display:block; line-height:20px;}
.slider1{position: absolute;display: inline-block;width:18px;height:18px;background: white;box-shadow: 0 1px 3px rgba(0, 0, 0, 0.4);border-radius: 50%;left:0;top: 0;}
.switch-on .slider1 {left:0px;transition: background-color 0.4s, left 0.2s;}
.switch-off {border: 1px solid #dfdfdf;transition: border 0.4s, box-shadow 0.4s;background-color: rgb(255, 255, 255);box-shadow: rgb(223, 223, 223) 0px 0px 0px 0px inset;background-color: rgb(255, 255, 255);cursor: pointer; text-align:left;padding:0 0 0 5px;}
.switch-off .slider1 {left:38px;transition: background-color 0.4s, left 0.2s;}
.switch-on.switch-disabled{opacity:.5;cursor:auto;}
.switch-off.switch-disabled{background-color:#F0F0F0 !important;cursor:auto;}
</style>
</head>
<body>

<%@include file="../comm/header.jsp" %>
<div id="mainbox" class="tabbox">
	<header class="header backHeader">  
	    <h2 class="tit"><spring:message code="nav.top.trade" /></h2>
        <span class="swit"><span class="switch-on" id="coinSwitch" onclick="switchPrice();"><em>USDT</em></span></span>
	</header>
	<section>
	    <div class="tradeMain tradeCenter">
	        <div class="head-nav">
	            <div class="lw-tradeSelect">
	                <ul>
	                    <li class="fl">
	                        <div class="lw-coinCon">
	                            <span class="lw-coin lw-coinPrice cblue fBold coin_price"  id="kaipan">${ftrademapping.fprice}</span>
	                            <span class="lw-description"><spring:message code="m.security.newmoney" /></span>
	                        </div>
	                    </li>
	                    <li class="fl">
	                        <div class="lw-coinCon">
	                            <span class="lw-coin lw-upColor" id="rosed">+0.00%</span>
	                            <span class="lw-description"><spring:message code="m.security.changea" /></span>
	                        </div>
	                    </li>
	                    <li class="fl">
	                        <div class="lw-coinCon">
	                            <span class="lw-coin Color333 fBold" id="vold">0</span>
	                            <span class="lw-description"><spring:message code="m.security.timevol" /></span>
	                        </div>
	                    </li>
	                    <li class="fr lw-cur">
	                        <div class="lw-coinCon lw-coinCon1">
	                            <span class="lw-coin fBold">${ftrademapping.fvirtualcointypeByFvirtualcointype2.fShortName }/<span class="coin_title">${ftrademapping.fvirtualcointypeByFvirtualcointype1.fShortName }</span></span>
	                            <span class="lw-description"><spring:message code="market.selectpro" /></span>
	                        </div>
	                        <i></i>
	                    </li>
	                    <div class="clear"></div>
	                    <div class="coinListWarp">
	                        <div class="lw-coinList">
	                                <ol class="clear">
	                                    <li>
	                                       	<spring:message code="market.selectpro" /> 
	                                    </li>
	                                    <c:forEach items="${requestScope.constant['fbs']}" var="v" varStatus="n">   
	                                    <c:forEach items="${ftrademappings }" var="vv">
	                                	<c:if test="${v.fShortName==vv.fvirtualcointypeByFvirtualcointype1.fShortName }">                                 
	                                    <li <c:if test="${ftrademapping.fvirtualcointypeByFvirtualcointype2.fShortName ==vv.fvirtualcointypeByFvirtualcointype2.fShortName }"> class="active" </c:if>>
	                                        <a href="/trademarket.html?symbol=${vv.fid }">
	                                            <span class="lw-coinName" data-setter="${vv.fid }">${vv.fvirtualcointypeByFvirtualcointype2.fShortName}/<em class="coin_title">${vv.fvirtualcointypeByFvirtualcointype1.fShortName}</em></span>
	                                        </a>
	                                    </li>     
	                                     </c:if>
	                                	 </c:forEach>
	                                    </c:forEach>                               
	                                                                        
	                                    
	                                </ol>
	                        </div>
	                    </div>
	                </ul>
	            </div>
	        </div>	        
	        <div class="tradeKline"><iframe frameborder="0" border="0" width="100%" height="100%" id="klineFullScreen" src="/kline/h5.html?symbol=${symbol}"></iframe></div>
	        <div class="trade_business">
	            <a href="javascript:;" class="fl" onclick="showBox('buybox')">
	                <dl>
	                    <dt class="fl"><img src="${oss_url}/static/mobile2018/images/buy.png" alt="" /></dt>
	                    <dd class="fl">
	                        <h2><spring:message code="market.buy" /></h2>
	                        <p><spring:message code="market.usable" />：<span class="cred2" id="totalCny">0.000000 </span>${ftrademapping.fvirtualcointypeByFvirtualcointype1.fShortName }</p>
	                    </dd>
	                    <div class="clear"></div>
	                </dl>
	            </a>            
	            <a href="javascript:;" class="fr" onclick="showBox('sellbox')">
	                <dl>
	                    <dt class="fl"><img src="${oss_url}/static/mobile2018/images/sell.png" alt="" /></dt>
	                    <dd class="fl">
	                        <h2><spring:message code="market.sell" /></h2>
	                        <p><spring:message code="market.usable" />：<span class="cgreen2" id="totalCoin">0.000000 </span>${ftrademapping.fvirtualcointypeByFvirtualcointype2.fShortName }</p>
	                    </dd>
	                    <div class="clear"></div>
	                </dl>
	            </a>
	            <c:if test="${sessionScope.login_user == null }">
	            <div class="clear"></div>
	            <div class="nologinWarp">
	                <a href="/user/login.html" class="btn"><spring:message code="nav.top.login" /></a>
	            </div>
	            </c:if>
	        </div>

			<div class="trade_success">
				<h2><spring:message code="entrust.title.deal" /></h2>
				<div class="lw-tradeCon">
					<div class="trade_tab">
						<p>
							<span class="fl textLeft"><spring:message code="market.date" /></span>
							<span class="fl textCenter"><spring:message code="market.price" /></span>
							<span class="fl textRight"><spring:message code="market.volume" /></span>
							<div class="clear"></div>
						</p>
					</div>
					<div class="trade_successMain" id="marketSuccessData"></div>
				</div>
			</div>

	    </div>
	</section>
</div>


<div id="buybox" class="tabbox" style="display:none">
	<header class="header backHeader">  
	    <i class="backIcon" onclick="showBox('mainbox')"></i>
	    <h2 class="tit"><spring:message code="market.buy" /></h2>
	</header>
	<section>
	    <div class="noticeDetail buyMain">   
	        <div class="buyCon">
	            <div class="trade_r trade_r2">
		            <em class="emW">${ftrademapping.fvirtualcointypeByFvirtualcointype1.fShortName }&nbsp;<spring:message code="market.buyprice" /></em>
		            <div class="buyPrice">
		            	<input class="nums" id="buy-price" />
		            	<span class="coinName">${ftrademapping.fvirtualcointypeByFvirtualcointype1.fShortName }</span>
		            </div>
		            <span>≈<em class="buyCNY">0.000000</em>CNY</span>
	            </div>
	            <div class="trade_r trade_r2">
		            <em class="emW">${ftrademapping.fvirtualcointypeByFvirtualcointype2.fShortName }<spring:message code="market.buyvolume" /></em>
		            <div class="buyPrice sell_price">
		            	<input class="nums" id="buy-amount" />
		            	<span class="coinName">${ftrademapping.fvirtualcointypeByFvirtualcointype2.fShortName }</span>
		            </div>
	            </div>
	           	<div class="trade">
	                <spring:message code="market.usable" />：<span class="coinAll" id="totalCny_1">0.000000</span><span>${ftrademapping.fvirtualcointypeByFvirtualcointype1.fShortName }</span>
	            </div>
				<div id="buyBar" class="buysellbar-box buyBar">
					<div id="buyslider" class="slider" data-role="slider" data-param-marker="marker" data-param-complete="complete"
						data-param-type="0" data-param-markertop="marker-top"></div>
					<ol >
						<li class="fl proportioncircle proportion0 " data-points="0" ><span class="buyActive" onclick="$('#buyBar li span').removeClass('buyActive');$(this).addClass('buyActive')">0%</span></li>
						<li class="fl proportioncircle proportion1" data-points="20" ><span class="" onclick="$('#buyBar li span').removeClass('buyActive');$(this).addClass('buyActive')">20%</span></li>
						<li class="fl proportioncircle proportion2" data-points="40"><span class="" onclick="$('#buyBar li span').removeClass('buyActive');$(this).addClass('buyActive')">40%</span></li>
						<li class="fl proportioncircle proportion3" data-points="60" ><span class="" onclick="$('#buyBar li span').removeClass('buyActive');$(this).addClass('buyActive')">60%</span></li>
						<li class="fl proportioncircle proportion4" data-points="80"><span class="" onclick="$('#buyBar li span').removeClass('buyActive');$(this).addClass('buyActive')">80%</span></li>
						<li class="fl proportioncircle proportion5 " data-points="100" ><span class="" onclick="$('#buyBar li span').removeClass('buyActive');$(this).addClass('buyActive')">100%</span></li>
						<div class="clear"></div>
					</ol>
				</div>
				
	            <p class="trade_r">
		            <em class="emW2"><spring:message code="market.total" /></em>
		            <span class="fr fBold"  id="buy-limit">0.000000 ${ftrademapping.fvirtualcointypeByFvirtualcointype1.fShortName }</span>
					<div class="clear"></div>
		        </p>
	            <p class="trade_r">
		            <em class="emW2"><spring:message code="market.fee" /></em>
		            <span class="fr fBold"><fmt:formatNumber type="percent" value="${fbuyfee }" maxFractionDigits="3" /></span>
		            <div class="clear"></div>
	            </p>
	            <button class="buyBtn" id="buy_sub"><spring:message code="market.buy" /></button>

	        </div>
	    </div>
	
	</section>
</div>

<div id="sellbox" class="tabbox" style="display:none">
	<header class="header backHeader">  
	    <i class="backIcon" onclick="showBox('mainbox')"></i>
	    <h2 class="tit"><spring:message code="market.sell" /></h2>
	</header>
	<section>
	    <div class="noticeDetail buyMain sellMain">   
	        <div class="buyCon">

	            <div class="trade_r trade_r2">
	            	<em class="emW">${ftrademapping.fvirtualcointypeByFvirtualcointype1.fShortName }&nbsp;<spring:message code="market.sellprice" /></em>
	            	 <div class="buyPrice">
	            	 	<input id="sell-price" class="nums"/>
	            	 	<span class="coinName">${ftrademapping.fvirtualcointypeByFvirtualcointype1.fShortName }</span>
	            	 </div>
	            	   <span>≈<em class="sellCNY">0.000000</em>CNY</span>
	            </div>
	            <div class="trade_r trade_r2">
	            	<em class="emW">${ftrademapping.fvirtualcointypeByFvirtualcointype2.fShortName }<spring:message code="market.sellvolume" /></em>
	            	<div class="buyPrice sell_price">
	            		<input id="sell-amount" class="nums"/>
	            		<span class="coinName">${ftrademapping.fvirtualcointypeByFvirtualcointype2.fShortName }</span>
		            </div>

	            </div>

	            <div class="trade">
	                <spring:message code="market.usable" />：<span class="coinAll" id="totalCoin_1">0.000000</span><span>${ftrademapping.fvirtualcointypeByFvirtualcointype2.fShortName }</span>
	            </div>
	            <div id="sellBar" class="buysellbar-box buyBar">
					<div id="sellslider" class="slider" data-role="slider" data-param-marker="marker" data-param-complete="complete"
						data-param-type="0" data-param-markertop="marker-top"></div>
					<ol >
						<li class="fl proportioncircle proportion0 " data-points="0" ><span class="sellActive" onclick="$('#sellBar li span').removeClass('sellActive');$(this).addClass('sellActive')">0%</span></li>
						<li class="fl proportioncircle proportion1" data-points="20" ><span class="" onclick="$('#sellBar li span').removeClass('sellActive');$(this).addClass('sellActive')">20%</span></li>
						<li class="fl proportioncircle proportion2" data-points="40"><span class="" onclick="$('#sellBar li span').removeClass('sellActive');$(this).addClass('sellActive')">40%</span></li>
						<li class="fl proportioncircle proportion3" data-points="60" ><span class="" onclick="$('#sellBar li span').removeClass('sellActive');$(this).addClass('sellActive')">60%</span></li>
						<li class="fl proportioncircle proportion4" data-points="80"><span class="" onclick="$('#sellBar li span').removeClass('sellActive');$(this).addClass('sellActive')">80%</span></li>
						<li class="fl proportioncircle proportion5 " data-points="100" ><span class="" onclick="$('#sellBar li span').removeClass('sellActive');$(this).addClass('sellActive')">100%</span></li>
					</ol>
				</div>
	            <p class="trade_r">
		            <em class="emW2"><spring:message code="market.total" /></em>
		            <span class="fr fBold"  id="sell-limit">0.000000 ${ftrademapping.fvirtualcointypeByFvirtualcointype1.fShortName }</span>
					<div class="clear"></div>
		        </p>
	            <p class="trade_r">
		            <em class="emW2"><spring:message code="market.fee" /></em>
		            <span class="fr fBold"><fmt:formatNumber type="percent" value="${ffee }" maxFractionDigits="3" /></span>
		            <div class="clear"></div>
	            </p>
	        	<button class="buyBtn selBtn" id="sell_sub"><spring:message code="market.sell" /></button>
	        </div>
	    </div>
	  <!--   <div class="weiTuo" >
	    	<h3 class="fBold"><spring:message code="market.openorders" /></h3>
	    	<div class="weiTuoCon" id="entrutsCurData1">	    		
	    		
	    	</div>
	    </div> -->


	</section>
	
</div>


<div class="weiTuo tabbox" id="weiTuo" style="display: none;" >
    <ol class="hd">
        <li class="on"><spring:message code="market.sell" /></li>
        <li><spring:message code="market.buy" /></li>
        <li><spring:message code="market.openorders" /></li>
    </ol>
    <style>
		.weiTuo ul li{display: none;}
		.weiTuo ul li.active{display: block;}
    </style>
    <ul class="bd">
        <li class="active">
           <p class="p_tit">
				<em><spring:message code="market.price" /></em>
				<em><spring:message code="market.amount" /></em>
				<em><spring:message code="market.entrusttype" /></em>
           </p>
           <div  id="marketDepthSell" class="sellDateBox"></div>
        </li>
        <li>
           <p class="p_tit">
				<em><spring:message code="market.price" /></em>
				<em><spring:message code="market.amount" /></em>
				<em><spring:message code="market.entrusttype" /></em>
           </p>
           <div  id="marketDepthBuy" class="buyDateBox" ></div>
        </li>    
        <li id="entrutsCurData">
        </li>
    </ul>
</div>
<input type="hidden"  value="${sessionScope.login_user}" id="usename_1">
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
<input type="hidden"  id="tradePwd" >

<script type="text/javascript">
$(document).ready(function($) {

    if($('#usename_1').val() == ''){
	    var wh=$(window).height()
	    var klineH=wh-239
	    $(".tradeKline").height(klineH);
	    $(".trade_success").css('display', 'none');
    }else{
    	$(".tradeKline").height(350);
    	$(".trade_success").css('display', 'block');

    }
});

$(".lw-tradeSelect ul li.lw-cur").click(function(event) {
   $(".coinListWarp").fadeIn(100, function() {       
      $(".coinListWarp").click(function(event) {
         $(this).fadeOut(100);
      });
   });
});
function showBox(id){
	$('.tabbox').hide();
	$('#totalCny_1').text($('#totalCny').text())
	$('#totalCoin_1').text($('#totalCoin').text())
	$('#'+id).show();
	if(id == 'mainbox'){
	$('#weiTuo').css('display','none');
	}else if(id == 'buybox'){
		$('#weiTuo').css('display','block');
		$("#weiTuo ol li").removeClass('on');
		$("#weiTuo ol li:eq(0)").addClass('on');		
		$("#weiTuo ul li").removeClass('active');
		$("#weiTuo ul li:eq(0)").addClass('active');
	}else{
		$('#weiTuo').css('display','block');
		$("#weiTuo ol li").removeClass('on');
		$("#weiTuo ol li:eq(1)").addClass('on');		
		$("#weiTuo ul li").removeClass('active');
		$("#weiTuo ul li:eq(1)").addClass('active');
	}
}
function test(url){
		var result = initArg();
		var rate = result.rate;
		var coinclass =  result.coinclass;
      $.get(url,function(data){     	
      	document.getElementById('vold').innerHTML = data.vol;
      	
      	var kaipan  = util.numFormat(Number(data.p_new)*rate, 4);
      	document.getElementById('kaipan').innerHTML = kaipan;
      	if(data.rose == 0){
      		var rose = "+" + data.rose + ".00%";
      		document.getElementById('rosed').innerHTML = rose;
      		document.getElementById('rosed').className = "lw-coin lw-proportion";
      		return;
      	}
      	var rose = data.rose.toString();

      	if(rose.indexOf('-') == -1){
      		document.getElementById('rosed').innerHTML = "+" + rose + "%";
      		document.getElementById('rosed').className = "lw-coin lw-proportion";
      	}else{
      		document.getElementById('rosed').innerHTML = rose + "%";
      		document.getElementById('rosed').className = "lw-coin lw-downColor";
      	}

});

}

function initPrice()
	{
		var fromtype = $.cookie('cointype');
		
		if(typeof fromtype=='undefined'||fromtype=='')
		{
			
			fromtype = 'USDT';
		}
		if(fromtype=='CNY')
		 {
		 	$('#coinSwitch').removeClass('switch-on').addClass('switch-off');
		 	$('#coinSwitch em').text(fromtype);
		 	 $(".switch-off").css({
                'border-color' : '#dfdfdf',
                'box-shadow' : 'rgb(223, 223, 223) 0px 0px 0px 0px inset',
                'background-color' : 'rgb(255, 255, 255)'
            });
		 }
		 else
		 {
		 	$('#coinSwitch').removeClass('switch-off').addClass('switch-on');
		 	$(".switch-on").css({
					'border-color' : 'rgb(72, 139, 239)',
					'box-shadow' : 'rgb(72, 139, 239) 0px 0px 0px 16px inset',
					'background-color' : 'rgb(72, 139, 239)'
				});
		 	$('#coinSwitch em').text(fromtype);
		 }
		$.cookie('cointype', fromtype);
		
		//初始化 
		var totype = $.cookie('cointype');
		var rate = totype=='CNY'?6.5:1;
		var fromtype = totype=='CNY'?'USDT':'CNY';
		$('.coin_title').text(totype);
		$('.coin_price').each(function(){
			if(!$(this).hasClass('coin_price_'+totype)&&parseFloat($(this).text())>0)
			{
				$(this).text(util.numFormat(Number($(this).text())*rate, 4)).removeClass('coin_price_'+fromtype).addClass('coin_price_'+totype);
			}
		});
		$('.coin_price1').each(function(){
			if(!$(this).hasClass('coin_price1_'+totype)&&parseFloat($(this).text())>0)
			{
				$(this).text(util.numFormat(Number($(this).text())*rate, 4)).removeClass('coin_price1_'+fromtype).addClass('coin_price1_'+totype);
			}
		});  
		
	}
	function switchPrice()
	{
		var fromtype = $.cookie('cointype');
		var totype = '';
		if(typeof fromtype=='undefined'||fromtype==''||fromtype=='USDT')
		{
			
			totype = 'CNY';
		}
		if(fromtype=='CNY')
		{
			totype = 'USDT';
		}
		
		
		if(totype=='CNY')
		 {
		 	$('#coinSwitch').removeClass('switch-on').addClass('switch-off');
		 	$('#coinSwitch em').text(totype);
		 	 $(".switch-off").css({
                'border-color' : '#dfdfdf',
                'box-shadow' : 'rgb(223, 223, 223) 0px 0px 0px 0px inset',
                'background-color' : 'rgb(255, 255, 255)'
            });
		 }
		 else
		 {
		 	$('#coinSwitch').removeClass('switch-off').addClass('switch-on');
	 		$(".switch-on").css({
					'border-color' : 'rgb(72, 139, 239)',
					'box-shadow' : 'rgb(72, 139, 239) 0px 0px 0px 16px inset',
					'background-color' : 'rgb(72, 139, 239)'
				});
		 	$('#coinSwitch em').text(totype);
		 }
		$.cookie('cointype', totype);
		showData();
		$('#klineFullScreen').attr('src',$('#klineFullScreen').attr('src')+"&"+Math.random())
	}

	function showData(){
		var totype = $.cookie('cointype');
		if(typeof totype!='undefined' && totype!='')
		{
			var rate = totype=='CNY'?6.5:(1/6.5);
			var fromtype = totype=='CNY'?'USDT':'CNY';
			$('.coin_title').text(totype);
			$('.coin_price').each(function(){
				if(!$(this).hasClass('coin_price_'+totype)&&parseFloat($(this).text())>0)
				{
				
					$(this).text(util.numFormat(Number($(this).text())*rate, 4)).removeClass('coin_price_'+fromtype).addClass('coin_price_'+totype);
				}
			});
			$('.coin_price1').each(function(){
				if(!$(this).hasClass('coin_price1_'+totype)&&parseFloat($(this).text())>0)
				{
					$(this).text(util.numFormat(Number($(this).text())*rate, 4)).removeClass('coin_price1_'+fromtype).addClass('coin_price1_'+totype);
				}
			});  
		}
		
	}

function initArg()
	{
		var cointype = $.cookie('cointype');
		var rate = 1;
		var coinclass = '';
		if(typeof cointype!='undefined'&&cointype!='')
		{
			
			 rate = cointype=='CNY'?6.5:1;
			 coinclass = 'coin_price_'+cointype;
		} 	
		return {rate:rate,coinclass:coinclass}
	}
var starturl = "real/market2.html?symbol=${ftrademapping.fid }&_t=" + parseInt(Math.random()*90+10);
test(starturl);
 </script>

	<!-- 开关切换 -->
	<script type="text/javascript">
		var honeySwitch = {};
		honeySwitch.themeColor = "rgb(72, 139, 239)";
		honeySwitch.init = function() {
		var s = "<span class='slider1'></span>";
		$("[class^=switch]").append(s);
		
		window.switchEvent = function(ele, on, off) {
			$(ele).click(function() {
				if ($(this).hasClass("switch-disabled")) {
					return;
				}
				if ($(this).hasClass('switch-on')) {
					if ( typeof on == 'function') {
						on();
					}
				} else {
					if ( typeof off == 'function') {
						off();
					}
				}
			});
		}
		if (this.themeColor) {
			var c = this.themeColor;
			$(".switch-on").css({
				'border-color' : c,
				'box-shadow' : c + ' 0px 0px 0px 16px inset',
				'background-color' : c
			});
			$(".switch-off").css({
				'border-color' : '#dfdfdf',
				'box-shadow' : 'rgb(223, 223, 223) 0px 0px 0px 0px inset',
				'background-color' : 'rgb(255, 255, 255)'
			});
		}
		if ($('[themeColor]').length > 0) {
			$('[themeColor]').each(function() {
				var c = $(this).attr('themeColor') || honeySwitch.themeColor;
				if ($(this).hasClass("switch-on")) {
					$(this).css({
						'border-color' : c,
						'box-shadow' : c + ' 0px 0px 0px 16px inset',
						'background-color' : c
					});
				} else {
					$(".switch-off").css({
						'border-color' : '#dfdfdf',
						'box-shadow' : 'rgb(223, 223, 223) 0px 0px 0px 0px inset',
						'background-color' : 'rgb(255, 255, 255)'
					});
				}
			});
		}
	};
	$(function() {
		honeySwitch.init();
		initPrice();
	}); 
</script>


<script type="text/javascript">
	$(".weiTuo ol li").click(function(){
		$(this).addClass("on").siblings().removeClass();
		var _this=$(this).index();
		$(".weiTuo ul li").eq(_this).addClass("active").siblings().removeClass();
	})
</script>
<script type="text/javascript">

	  $("#sell-price").change(function(){
	  	 var money = $("#sell-price").val();
	     $('.sellCNY').text((money*6.5).toFixed(4));
	  });

	  $("#buy-price").change(function(){
	  	 var money1 = $("#buy-price").val();
	     $('.buyCNY').text((money1*6.5).toFixed(4));
	  });
</script>
	


<%@include file="../comm/tabbar.jsp" %> 
<%@include file="../comm/footer.jsp" %> 

<script type="text/javascript" src="${oss_url}/static/front/js/plugin/bootstrap.js?v=20171026105823.js?v=20181126201750"></script>
<script type="text/javascript" src="${oss_url}/static/front/js/plugin/jquery.jslider.js?v=20171026105823.js?v=20181126201750"></script>
<script type="text/javascript" src="${oss_url}/static/mobile2018/js/market/trademarket.js?t=<%=new java.util.Date().getTime() %>"></script>
<script type="text/javascript" src="${oss_url}/static/mobile2018/js/TouchSlide.1.1.source.js?v=3"></script>
</body>
</html>
