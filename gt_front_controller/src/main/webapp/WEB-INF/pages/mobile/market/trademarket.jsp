<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ include file="../comm/include.inc.jsp"%>
<%

%>

<!doctype html>
<html>
<head>

 <meta name = "viewport" content = "width = device-width, user-scalable = no,initial-scale=0.5,maximum-scale=0.5,minimum-scale=0.5">
<%@ include file="../comm/basepath.jsp"%>
<%@include file="../comm/link.inc.jsp" %>
<link rel="stylesheet" type="text/css" href="${oss_url}/static/mobile2018/icons/iconfont.css?v=20181126201750">
<script type="text/javascript" src="${oss_url}/static/front/js/index/jquery.cookie.js?v=5"></script>
 <style type="text/css">
   @font-face {
      font-family: 'PingFangMedium';
      src: url('/static/mobile2018/fonts/PingFangMedium.ttf'); 
   }
   .tradeCenter{
   	padding-bottom:0;
   }
   .footer{
    background: #131E30;
    box-shadow: 0px 0px 10px 0px rgba(8,24,37,1);
   }
   .footer a span {
    color: #D8D8D8;
	}
	.tradeTop{background: none;}
   em{color: #9AADCD;}
   .tradeList_tit{border-bottom: none;}
</style>
</head>
<body style="background: #31354c;">

<%@include file="../comm/header.jsp" %>
<section id="tabOne">
<%-- <header class="tradeTop clear">
            <a href="/" class="backHhome"></a>
            <!-- <span class='coin_names'><em>${ftrademapping.fvirtualcointypeByFvirtualcointype2.fShortName }</em>/${ftrademapping.fvirtualcointypeByFvirtualcointype1.fShortName }</span> -->
             <span class="moreCoin_btn fr"><i class="iconfont fl icon-tubiao_daohangcaidan"></i><span class='coin_names fl'><em>${ftrademapping.fvirtualcointypeByFvirtualcointype2.fShortName }</em>/${ftrademapping.fvirtualcointypeByFvirtualcointype1.fShortName }</span></span>
            <!-- <a href="/" class="fr return_home"><i class="iconfont sfont50 icon-shouye1" style="font-size:0.6rem;"></i></a> -->
  </header> --%>
   <!--  <span class="moreCoin_btn"><i class="iconfont icon-weibiaoti26"></i></span> -->
     <header class="tradeTop">
        <a href="/" class="backHhome"></a>
<%--         <span style="color:#8D939B"><em>${ftrademapping.fvirtualcointypeByFvirtualcointype2.fShortName }</em>/${ftrademapping.fvirtualcointypeByFvirtualcointype1.fShortName }</span> --%>
        <span class="moreCoin_btn fr" style="color:#8D939B"><i class="iconfont fl icon-tubiao_daohangcaidan"></i><span class='coin_names fl'><em>${ftrademapping.fvirtualcointypeByFvirtualcointype2.fShortName }</em>/${ftrademapping.fvirtualcointypeByFvirtualcointype1.fShortName }</span></span>
    </header>
    <div class="trade" style="position:relative;">
        <div class="tradeHeader clear">
            <div class="tradeHL fl">
                <h1 class="downPrice new_price" id="kaipan"></h1>
                <p class="priceDetail"><span id="kaipancny"></span>&nbsp; <em class="" id="rosed"></em></p>
            </div>
            <div class="tradeHR fr">
                <p><span class="s_L"><spring:message code="newmobile.high" /></span><em class="s_num" id="hoursgao"></em></p>
                <p><span class="s_L"><spring:message code="newmobile.low" /></span><em class="s_num" id="hoursdi"></em></p>
                <p><span class="s_L"><spring:message code="market.volume2" /></span><em class="s_num" id="vold"></em></p>
            </div>
        </div>
        <div class="tradeKline klineMain">
           <iframe frameborder="0" border="0" style="width:50vw;height:52.5%;transform-origin:0 0;transform: scale(2);" id="klineFullScreen" src="/kline/fullstart.html?symbol=${symbol}"></iframe>
        </div>
        <div class="tradeCon">
            <ol class="tradeCon_tit clear">
                <li class="fl"><spring:message code="entrust.title.deal2" /></li>
            </ol>
            <div class="tradeList" style="display: block;">
                <div class="tradeList_tit">
                    <span><spring:message code="market.date" /></span>
                    <span><spring:message code="market.entrusttype" /></span>
                    <span><spring:message code="market.price" /><em>(${ftrademapping.fvirtualcointypeByFvirtualcointype1.fSymbol })</em></span>
                    <span><spring:message code="market.volume" /></span>
                </div>
                <ul class="ul_con" id="marketSuccessData">
                </ul>
            </div>
        </div>
    </div>
    <div class="trade_btns">
        <a href="javascript:;" data-key="1"><span><spring:message code="market.buy" /></span></a>
        <a href="javascript:;" data-key="2"><span class="sell_btn"><spring:message code="market.sell" /></span></a>
    </div>
</section>


<section id="tabTwo" style="display: none">
  <%--  <header class="tradeTop">
        <span class="toback toback2"></span>
        <span><em>${ftrademapping.fvirtualcointypeByFvirtualcointype2.fShortName }</em>/${ftrademapping.fvirtualcointypeByFvirtualcointype1.fShortName }</span>
    </header> --%>
   <header class="tradeTop clear">
            <span class="toback"></span>
            <!-- <span class='coin_names'><em>${ftrademapping.fvirtualcointypeByFvirtualcointype2.fShortName }</em>/${ftrademapping.fvirtualcointypeByFvirtualcointype1.fShortName }</span> -->
             <span class="moreCoin_btn fr" style="color:#8D939B"><i class="iconfont fl icon-tubiao_daohangcaidan"></i><span class='coin_names fl'><em>${ftrademapping.fvirtualcointypeByFvirtualcointype2.fShortName }</em>/${ftrademapping.fvirtualcointypeByFvirtualcointype1.fShortName }</span></span>
            <!-- <a href="/" class="fr return_home"><i class="iconfont sfont50 icon-shouye1" style="font-size:0.6rem;"></i></a> -->
  </header>
  <div class="tradeCenter">
    <div class="tradeUp clear">
        <div class="tradeLeft fl">
            <div class="trade_btn">
                <span class="tit  buy_tit active"><spring:message code="market.buy" /></span>
                <span class="tit sell_tit"><spring:message code="market.sell" /></span>
            </div>
            <div class="buyCon active buyCon1">
                <div class="sell_tr">
                    <input type="text" class="inpTxt"  id="buy-price"/>
                    <span class="tr_txt">${ftrademapping.fvirtualcointypeByFvirtualcointype1.fShortName }</span>
                </div>
                <p class="txt_p">≈<em class="buyCNY">0.000000</em></p>                
                <div class="sell_tr">
                    <input type="text" class="inpTxt" value="" id="buy-amount"/>
                    <span class="tr_txt">${ftrademapping.fvirtualcointypeByFvirtualcointype2.fShortName }</span>
                </div>
                <p class="txt_p"><spring:message code="market.usable" /> <em id="totalCny">0.00000</em>&nbsp;${ftrademapping.fvirtualcointypeByFvirtualcointype1.fShortName }</p>
                <div class="numBar" id="buyBar" >
                    <span class="fl proportioncircle proportion0" data-points="25" onclick="$('#buyBar  span').removeClass('active');$(this).addClass('active')"><em>25%</em></span>
                    <span class='fl proportioncircle proportion1' data-points="50" onclick="$('#buyBar  span').removeClass('active');$(this).addClass('active')"><em>50%</em></span>
                    <span class='fl proportioncircle proportion2' data-points="75" onclick="$('#buyBar  span').removeClass('active');$(this).addClass('active')"><em>75%</em></span>
                    <span class='fl proportioncircle proportion3' data-points="100" onclick="$('#buyBar  span').removeClass('active');$(this).addClass('active')"><em>100%</em></span>
                </div>
               
                <button class="buyBtn" id="buy_sub"><spring:message code="market.buy" /><span>${ftrademapping.fvirtualcointypeByFvirtualcointype2.fShortName }</span></button>
            </div>            
            <div class="buyCon sellCon">
                <div class="sell_tr">
                    <input type="text" class="inpTxt"  id="sell-price"/>
                    <span class="tr_txt">${ftrademapping.fvirtualcointypeByFvirtualcointype1.fShortName }</span>
                </div>
                <p class="txt_p" >≈<em class="sellCNY"></em></p>                
                <div class="sell_tr">
                    <input type="text" class="inpTxt"  id="sell-amount" />
                    <span class="tr_txt">${ftrademapping.fvirtualcointypeByFvirtualcointype2.fShortName }</span>
                </div>
                <p class="txt_p"><spring:message code="market.usable" /> <em id="totalCoin">0.00000</em>&nbsp;${ftrademapping.fvirtualcointypeByFvirtualcointype2.fShortName }</p>
                <div class="numBar" id="sellBar">
                    <span class="fl proportioncircle proportion0" data-points="25" onclick="$('#sellBar span').removeClass('active');$(this).addClass('active')"><em>25%</em></span>
                    <span class='fl proportioncircle proportion1' data-points="50" onclick="$('#sellBar span').removeClass('active');$(this).addClass('active')"><em>50%</em></span>
                    <span class='fl proportioncircle proportion2' data-points="75" onclick="$('#sellBar span').removeClass('active');$(this).addClass('active')"><em>75%</em></span>
                    <span class='fl proportioncircle proportion3' data-points="100" onclick="$('#sellBar span').removeClass('active');$(this).addClass('active')"><em>100%</em></span>
                </div>
               
                <button class="buyBtn sellBtn" id="sell_sub"><spring:message code="market.sell" /><span>${ftrademapping.fvirtualcointypeByFvirtualcointype2.fShortName }</span></button>
            </div>            

        </div>
        <div class="tradeRight fr">
            <ul class="tradetitle">
            	<li class="t_title"><spring:message code="financial.type" /></li>
                <li class="t_title"><spring:message code="market.price" /></li>
                <li class="t_title"><spring:message code="market.amount" /></li>
            </ul>
            <div class="trade_detail_box">
                <div class="last_price">
                    <ul class="cgreen buyData buyData1" id="marketDepthSell">
                    </ul>
                    <span class="cred" id="marketPrice" style="color: #9AADCD;"> </span><br />≈<em class="newestprice" style="font-size: 0.18rem"></em>
                    <ul class="cred buyData sellDate" id="marketDepthBuy">
                    </ul>                 
                </div>
            </div>
        </div>
    </div>
    <div class="tradeDown">
        <div class="curTit clear">
            <h2 class="fl"><spring:message code="market.openorders" /></h2>
            <%-- <a class="fr" href="/trade/entrust.html?symbol=${ftrademapping.fid }&status=0"><spring:message code="agent.all" /></a> --%>
        </div>
        <div class="entrust">
            <ol class="clear">
                <li class="fl"><spring:message code="market.price" /></li>
                 <li class="fl"><spring:message code="financial.type" /></li>
                <li class="fl"><spring:message code="newmobile.number" /></li>
                <li class="fl"><spring:message code="newmobile.amount" /></li>
                <li class="fl"><spring:message code="market.entrustaction" /></li>
            </ol>
            <ul id="entrutsCurData">
                 <p class="textCenter" style="padding-top:1rem; font-size:0.3rem;">
                 		<spring:message code="market.nodelegate" />
                 		 <a class="cblue2" href="/user/login.html">
                 		<spring:message code="market.login"/></a>
                 		<spring:message code="market.or" />
                 		<a class="cblue2" href="/user/register.html">
                 		<spring:message code="market.register" /></a>
                 		<spring:message code="market.see" /></p>
            </ul>
        </div>
                                                   
    </div>
</div>
 
<%@include file="../comm/tabbar.jsp"%>
</section>
<!-- 币种选择 -->
    <div class="moreWarp">
        
        <div class="moreCoin">
            <ol class="clear">
            <c:forEach items="${requestScope.constant['fbs']}" var="v" varStatus="fbn">
                <li class="<c:if test="${v.fShortName==ftrademapping.fvirtualcointypeByFvirtualcointype1.fShortName }"> active</c:if>"><span>${v.fShortName }</span></li>
               
            </c:forEach>
                <span class="show_btn"><i class="iconfont icon-tubiao_daohangcaidan"></i></span>
            </ol>
            <c:forEach items="${requestScope.constant['fbs']}" var="v" varStatus="n">
            <div class="coinList_item <c:if test="${v.fShortName==ftrademapping.fvirtualcointypeByFvirtualcointype1.fShortName }"> active clear</c:if>">
                <ul>
                <c:forEach items="${ftrademappings }" var="vv">
                    <c:if test="${v.fShortName==vv.fvirtualcointypeByFvirtualcointype1.fShortName }">
                    <li class="coin_list_item">
                        <a onclick="document.location.href='/trademarket.html?symbol=${vv.fid }'" class="clear" target="_self">
                            <span class="currency textL lw-coinName" data-setter="${vv.fid }">${vv.fvirtualcointypeByFvirtualcointype2.fShortName}</span>
                            <span class="currency_price coin_price lw-zuixin">${vv.fprice}</span>
                            <span class="currency_gains textR coin_zf lw-fwf"></span>
                        </a>
                    </li>
                    </c:if>
                </c:forEach>                
                </ul>
            </div>            
            </c:forEach>
        
        </div>
    </div>
<input type="hidden"  value="${ftrademapping.fvirtualcointypeByFvirtualcointype1.fSymbol }" id="ftrademappingtype">
<input type="hidden"  value="${ftrademapping.fid }" id="isftrademapping">
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

<c:choose>
<c:when test="${pageContext.response.locale eq 'zh_CN' }">
	<input type="hidden" name="curlang" id="curlang" value="cn">
	<input type="hidden" name="usdtrate" id="usdtrate" value="6.5">
</c:when>
<c:otherwise>
	<input type="hidden" name="curlang" id="curlang" value="en">
	<input type="hidden" name="usdtrate" id="usdtrate" value="1">
</c:otherwise>
</c:choose>
<input type="hidden" name="fbprice" id="fbprice" value="1">

<!-- 2018-3-2tab切换 -->



<%@include file="../comm/footer.jsp" %> 
<script src="${oss_url}/static/mobile2018/js/fluckydraw/com.js?v=20181126201750"></script>
<script type="text/javascript" src="${oss_url}/static/mobile2018/js/plugin/bootstrap.js?v=20171026105823.js"></script>
<script type="text/javascript" src="${oss_url}/static/mobile2018/js/plugin/jquery.jslider.js?v=20171026105823.js?v=20181126201750"></script>
<script type="text/javascript" src="${oss_url}/static/mobile2018/js/market/trademarket.js?t=<%=new java.util.Date().getTime() %>"></script>
<script type="text/javascript" src="${oss_url}/static/mobile2018/js/TouchSlide.1.1.source.js?v=20171026105823.js"></script>
<script type="text/javascript" src="${oss_url}/static/mobile2018/js/market/tradegetdata.js?v=20171026105823.js"></script>


</body>
</html>
