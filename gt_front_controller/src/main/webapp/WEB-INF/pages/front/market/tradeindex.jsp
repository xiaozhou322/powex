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
<link rel="stylesheet" href="${oss_url}/static/front/css/market/trade.css?v=2018222222.css?v=20181126201750" type="text/css"></link>
<link rel="stylesheet" href="${oss_url}/static/front/css/market/loader.css?v=20171026105823.css?v=20181126201750" type="text/css"></link>
<link href="https://fonts.googleapis.com/css?family=Open+Sans" rel="stylesheet">
<script type="text/javascript" src="${oss_url}/static/front/js/index/jquery.cookie.js?v=1"></script>
<script type="text/javascript" src="${oss_url}/static/front/js/echarts/echarts_<spring:message code="language.title" />.min.js?v=4"></script>
<script type="text/javascript" src="${oss_url}/static/front/js/echarts/dark.js?v=5"></script>

<style type="text/css">
    body{font-family:'Open Sans', sans-serif;}
    /*2017-12-28开关样式*/ 
    .lw-lang .lanague{background:#24273b;} 
    .switchBox{position:relative;}  
    .switchBox:before{content:""; width:1px; height:30px; background:rgba(21,35,44,.2); position: absolute; left:15px; top:13px;}
    [class|=switch] {position: relative;display:block;width:62px;height:22px;border-radius: 16px;line-height:20px;-webkit-tap-highlight-color:rgba(255,255,255,0);}
    .switch-on {border: 1px solid white;box-shadow: white 0px 0px 0px 16px inset;transition: border 0.4s, box-shadow 0.2s, background-color 1.2s;background-color: white;cursor: pointer; text-align:right; padding:0 5px 0 0;}
    .switch-on em{color:#fff;}
    #coinSwitch{margin:8px 0 0 30px;}
    #coinSwitch em{font-size:13px; display:block; line-height:22px;}
    .slider1{position: absolute;display: inline-block;width:18px;height:18px;background: white;box-shadow: 0 1px 3px rgba(0, 0, 0, 0.4);border-radius: 50%;left:0;top:1px;}
    .switch-on .slider1 {left:1px;transition: background-color 0.4s, left 0.2s;}
    .switch-off {border: 1px solid #dfdfdf;transition: border 0.4s, box-shadow 0.4s;background-color: rgb(255, 255, 255);box-shadow: rgb(223, 223, 223) 0px 0px 0px 0px inset;background-color: rgb(255, 255, 255);cursor: pointer; text-align:left;padding:0 0 0 5px;}
    .switch-off .slider1 {left:40px;transition: background-color 0.4s, left 0.2s;}
    .switch-on.switch-disabled{opacity:.5;cursor:auto;}
    .switch-off.switch-disabled{background-color:#F0F0F0 !important;cursor:auto;}


    .header2 .lw-header{background:#fff; height:55px;}
    .userinfo-top{padding:10px 0 0 20px; height:70px;}
    .userinfo-top p{padding:0;}
    .userinfo-t{padding-bottom:10px;}
    .header2 .lw-userinfo .userinfo-title{background:#1b1e2e;}
    .header2 .lw-nav ul{padding-top:14px; margin:0;}
    .header2 .lw-nav ul li a{color:#666;}
    .header2 .lw-nav ul li:hover a{color:#fff;}
    .header2 .lw-header .lw-logo{padding-top:14px;}
    .header2 .lw-userinfo{ top:55px;}
    .header2 .userinfo-t,.lw-userinfo ul li{background:#2c2f48; font-size:14px; } 
    .header2 .lw-userinfo ul{background:#1b1e2e; margin-bottom:0;}
.lw-userinfo ul li{border-color:#2c2f48;font-size:14px;  height:32px; line-height:32px;}
    .header2 .lw-userinfo ul .listscoll a:hover li{background:#1b1e2e;}
    .userinfo-top p{margin-bottom:0;}
    .lw-userinfo ul .listscoll li>div.userinfo-available{font-size:14px;}
    .lw-userinfo ul .listscoll li>div.userinfo-frozen{font-size:14px;}
    .lw-userinfo ul .listscoll li>div.userinfo-frozen
    .lw-userinfo ul::-webkit-scrollbar{width:6px; -webkit-box-shadow: inset 0 0 6px rgba(0,0,0,0.3); border-radius: 10px;}
    .lw-userinfo ul::-webkit-scrollbar-track{background-color:#1b1e2e;}
    .lw-userinfo ul::-webkit-scrollbar-thumb{background-color:#3f4365;border-radius:5px;}
    .header2 .lw-loginBtn{margin-top:14px;}
    .header2 .lw-lang{margin-bottom:0;}
    .header2 .lw-lang .lanague{top:42px;}
    .chartMaxBox{
        position:fixed!important;
        top:0;
        left:0;
        z-index:9999;
        width:100%!important;
        height:100%!important;
        border-radius:0;
    }
    #chart_container.dark #chart_tabbar{background:#24273b;}
    .isMax{margin-top:15px;}
    .back_btn{border: 1px solid #8b91b7; height:21px; text-align:center; padding:0 15px; line-height:19px; cursor:pointer; transition:all 0.3s; font-size:12px;margin-top:4px; display:inline-block; display:none;}
    .back_btn:hover{background:#8b91b7; color:#fff;}
/*    .buy_sell{padding:5px 20px;}*/
    .chartMain{position:relative;}
    .toMax{position: absolute; right:20px; top:0; cursor:pointer;}
    .toMax img{display:block; margin-top:6px;}
</style>
</head>
<body>
<div class="header2">
    <%@include file="../comm/header.jsp" %>
</div>
<div class="tradeMain">
    <div class="tradeHeader">

        <div class="coinDataDetail fl">
            <div class="coinChoice fl">
                <div class="coinType fl">
                    <div>
                        <p>${ftrademapping.fvirtualcointypeByFvirtualcointype2.fShortName }/<span>${ftrademapping.fvirtualcointypeByFvirtualcointype1.fShortName }</span></p>
                        <p><spring:message code="market.selectpro" /></p>
                    </div>
                    <div class="coinTypeChoice">
                        <div class="tit">
                            <h2><span>${ftrademapping.fvirtualcointypeByFvirtualcointype1.fShortName }</span></h2>
                            <ol>
                                <li class="textL"><spring:message code="nav.index.coin" /></li>
                                <li><spring:message code="nav.index.latpri" /></li>
                                <li class="textR"><spring:message code="market.24change" /></li>
                            </ol>
                        </div>
                        <div class="coinTpyeData">
                            <table>
                                <c:forEach items="${requestScope.constant['fbs']}" var="v" varStatus="n">
                                    <c:forEach items="${ftrademappings }" var="vv">
                                        <c:if test="${v.fShortName==vv.fvirtualcointypeByFvirtualcointype1.fShortName }">
                                        <tr onclick="window.location.href='/tradeindex.html?symbol=${vv.fid }'">
                                            <td class="textL lw-coinName"  data-setter="${vv.fid }">${vv.fvirtualcointypeByFvirtualcointype2.fShortName}/<span class="coin_title">${vv.fvirtualcointypeByFvirtualcointype1.fShortName}</span></td>
                                            <td class="lw-coinPrice lw-zuixin coin_price">${vv.fprice}</td>
                                            <td class="textR "><span class="lw-proportion lw-fwf"><em></em></span></td>
                                        </tr>   
                                        </a> 
                                    </c:if>
                                    </c:forEach>   
                                 </c:forEach>                 
                            </table>
                        </div>
                    </div>
                </div>
                <div class="clear"></div>
            </div>
            <ul class="fl">
                <li class="fl">
                    <span><spring:message code="market.lastradprice" /></span>
                    <span class="downColor coin_price" id="kaipan"></span>
                </li>
                <li class="fl">
                    <span><spring:message code="market.24change" /></span>
                    <span id="rosed"></span>
                </li>                    
                <li class="fl">
                    <span><spring:message code="nav.index.24low" /></span>
                    <span class="upColor" id="hoursdi"></span>
                </li>                    
                <li class="fl">
                    <span><spring:message code="nav.index.24hign" /></span>
                    <span class="downColor" id="hoursgao"></span>
                </li>                    
                <li class="fl">
                    <span><spring:message code="market.24volume" /></span>
                    <span id="vold"></span>
                </li>                    
                
                <div class="clear"></div>
            </ul>
            <div class="fl switchBox" ><span class="switch-on" id="coinSwitch" onclick="switchPrice()"><em>USDT</em></span></div>
            <div class="clear"></div>
        </div>
        <div class="clear"></div>
    </div>        
    <div class="tradeContent">
        <div class="tradeLeft fl">
            <div class="chartCon">
                <!-- <div class="chartHeader"> -->
                   <!--  <ul class="fl " >
                        <li class="fl timechoose timechooses" data-val="75" data-url='/kline/fullperiod.html?symbol=${ftrademapping.fid}&step=60&t=<%=new java.util.Date().getTime() %>'><spring:message code="trder.market.line" /></li>
                        <li class="fl timechoose colorBlue" >
                            <span id="min" class="timechooses " data-val="75"><spring:message code="trder.market.minFive" /></span>
                            <ol class="subTimes">
                                <li class="sub_timechoose" data-val="75" data-url='/kline/fullperiod.html?symbol=${ftrademapping.fid}&step=60&t=<%=new java.util.Date().getTime() %>'><spring:message code="trder.market.minOne" /></li>
                                <li class="sub_timechoose" data-val="88" data-url='/kline/fullperiod.html?symbol=${ftrademapping.fid}&step=300&t=<%=new java.util.Date().getTime() %>'><spring:message code="trder.market.minTen" /></li>
                                <li  class="sub_timechoose" data-val="75" data-url='/kline/fullperiod.html?symbol=${ftrademapping.fid}&step=900&t=<%=new java.util.Date().getTime() %>'><spring:message code="trder.market.minFive" /></li>
                                <li class="sub_timechoose"  data-val="70" data-url='/kline/fullperiod.html?symbol=${ftrademapping.fid}&step=1800&t=<%=new java.util.Date().getTime() %>'><spring:message code="trder.market.minThere" /></li>
                            </ol>
                            <i class="arr"></i>
                        </li>
                        <li class="fl timechoose timechooses" data-val="70" data-url='/kline/fullperiod.html?symbol=${ftrademapping.fid}&step=3600&t=<%=new java.util.Date().getTime() %>'><spring:message code="trder.market.Hour" /></li>
                        <li class="fl timechoose timechooses " data-val="20" data-url='/kline/fullperiod.html?symbol=${ftrademapping.fid}&step=86400&t=<%=new java.util.Date().getTime() %>'><spring:message code="trder.market.daily" /></li>
                        <li class="fl timechoose timechooses" data-val="1" data-url='/kline/fullperiod.html?symbol=${ftrademapping.fid}&step=604800&t=<%=new java.util.Date().getTime() %>'><spring:message code="trder.market.weekly" /></li>
                        
                        <div class="clear"></div>
                    </ul> -->
                 <!--   <em class="chartMax fr">
                        <img src="${oss_url}/static/front/images/market/max.png" height="18" width="18" alt="" />
                        <span class="back_btn">退出全屏</span>
                   </em> -->
                    
                <!-- 
                </div> -->
                <!-- <div class="chartMain" id="kline"  ></div> -->
                <div class="chartMain">
                    <div id="marketStart" class="market-start" style="height: 100%">
                    <iframe frameborder="0" border="0" width="100%" height="100%" id="klineFullScreen" src="/kline/fullstart2018.html?symbol=${symbol }&themename=dark"></iframe>
                    </div>
                    <span class="toMax">
                        <img src="${oss_url}/static/front/images/market/max.png" height="18" width="18" alt="" />
                        <div class="back_btn">退出全屏</div>
                    </span>
                </div>
            </div>
            <div class="marketEntruts">
                <div class="EntrutsTit">
                    <ul>
                        <li class="fl active"><spring:message code="market.openorders" /></li>
                        <li class="fl"><spring:message code="market.tradehis" /></li>
                        <div class="clear"></div>
                    </ul>
                    <div class="EntrutsCon EntrutsCon1 EntrutsDate">
                        <table>
                            <tr>
                                <th class="textL"><spring:message code="market.entrustdate" /></th>
                                <th><spring:message code="market.entrusttype" /></th>
                                <th><spring:message code="market.entrustprice" /></th>
                                <th><spring:message code="market.entrustamount" /></th>
                                <th><spring:message code="market.entrusttotal" /></th>
                                <th><spring:message code="market.entruststatus" /></th>
                                <th><spring:message code="market.entrustaction" /></th>
                            </tr>
                        </table>
                        <div class="" id="entrutsCurData">
                        </div>                    
                    </div>

                     <div class="EntrutsCon EntrutsCon2 EntrutsDate" style="display: none">
                        <table>
                            <tr>
                                <th class="textL"><spring:message code="market.entrustdate" /></th>
                                <th><spring:message code="market.entrusttype" /></th>
                                <th><spring:message code="market.price" /></th>
                                <th><spring:message code="market.entrustamount" /></th>
                                <th><spring:message code="market.entrusttotal" /></th>
                                <th><spring:message code="market.entruststatus" /></th>
                            </tr>
                        </table>
                        <div class="" id="entrutsHisData">
                           
                        </div>                    
                    </div>                           
                </div>
            </div>
        </div>


        <div class="tradeRight fr">
            <div class="right_up">
                <div class="buy_sell fl">
                    <!--
                    <div class="tradingTit">
                         <ul class="fl">
                             <li class="active fl">深</li>
                             <li class="fl">买</li>
                             <li class="fl">卖</li>
                         </ul>
                         <div class="tradePoint fr">
                             <span>合并深度：</span>
                             <select class="choicePoint" name="" id="">
                                 <option value="">8位小数</option>
                                 <option value="">7位小数</option>
                                 <option value="">6位小数</option>
                                 <option value="">5位小数</option>
                                 <option value="">4位小数</option>
                             </select>
                             <em class="icon"></em>
                         </div>
                         <div class="clear"></div>
                    </div>
                    -->
                    <div class="tradingMain">
                        <ol>
                            <li class="fl textL"><span><spring:message code="market.entrusttype" /></span></li>
                            <li class="fl"><span><spring:message code="market.price" /></span></li>
                            <li class="fl textR"><span><spring:message code="market.amount" /></span></li>
                            <div class="clear"></div>
                        </ol>
                        <div class="tradingDate active">

                            <!--<p class="coinswitch" style="display:block">
                                <span id="marketPrice">0.0000</span> ≈ <span>
                                <em id="marketPricecny">0.000</em> CNY</span>
                            </p> -->
                            <div class="coinswitch"style="color:#8b91b7;">
                                <div id="marketDepthSell">
                                </div>
                                <span class="depth-price text-left fl" >
                                <spring:message code="market.lastprice" />&nbsp;<span id="marketPrice" class="market-font-sell">0.0000</span>
                                </span>
                                <span class="depth-price right fr">
                                    <spring:message code="market.change" />&nbsp;<span class="market-font-sell" id="marketRose">0%</span>
                                </span>
                                <div class="clear"></div>
                                <div id="marketDepthBuy">
                                </div>
                            </div>


                        </div>                
                        <div class="tradingDate" id="marketDepthBuy1">
                          
                        </div>    
                        <div class="tradingDate" id="marketDepthSell1">
                          
                        </div>                    
                    </div>
                </div>
                <div class="marketSuc fr">
<!--                     <div class="tit"><spring:message code="trder.market.latest" /></div>
 -->                    <div class="marketSucDateTit">
                        <span class="textL fl"><spring:message code="market.price" />(<em class="qh-usdt">${ftrademapping.fvirtualcointypeByFvirtualcointype1.fSymbol }</em>)</span>
                        <span class="fl"><spring:message code="market.volume" /></span>
                        <span class="textR fl"><spring:message code="market.date" /></span>
                        <div class="clear"></div>
                    </div>
                    <ul class="marketSucDate" id="marketSuccessData">       
                    </ul>
                </div>
                <div class="clear"></div>
            </div>
            <div class="marketTrade">
                <div class="buyCon fl">
                    <div class="buyTit">
                        <h3 class="fl buyColor"><spring:message code="market.buy" /></h3>
                        <div class="fr">
                            <span>${ftrademapping.fvirtualcointypeByFvirtualcointype1.fShortName } <spring:message code="trder.market.balance" /></span>
                            <span id="totalCny">0.0000000</span>
                        </div>
                        <div class="clear"></div>
                    </div>
                    <div class="trade_tr trade_txt clear">
                        <span class="tr_l"><spring:message code="market.buyprice" /></span>
                        <input type="text" class="trade_txt fl" id="buy-price" value="" />
                        <span class="tr_r">${ftrademapping.fvirtualcointypeByFvirtualcointype1.fShortName }</span>
                    </div>
                    ≈<span><em class="buyCNY">4.39</em>CNY</span></p>
                    <div class="trade_tr trade_txt clear">
                        <span class="tr_l"><spring:message code="market.buyvolume" /></span>
                        <input type="text" class="trade_txt" value="" id="buy-amount" placeholder="" />
                        <span class="tr_r">${ftrademapping.fvirtualcointypeByFvirtualcointype2.fShortName }</span>
                    </div>
                    <div class="numBar" id="buyBar">
                        <span class="proportioncircle proportion0" data-points="25" onclick="$('#buyBar span').removeClass('active');$(this).addClass('active')">25%</span>
                        <span data-points="50" class="proportioncircle proportion1" onclick="$('#buyBar span').removeClass('active');$(this).addClass('active')">50%</span>
                        <span data-points="75" class="proportioncircle proportion2" onclick="$('#buyBar span').removeClass('active');$(this).addClass('active')">75%</span>
                        <span data-points="100" class="noMargin proportioncircle proportion2" onclick="$('#buyBar span').removeClass('active');$(this).addClass('active')">100%</span>
                    </div>
                    <p><spring:message code="market.total" /><span id="buy-limit">0.0000&nbsp;</span><span>${ftrademapping.fvirtualcointypeByFvirtualcointype1.fShortName }</span></p>
                    <button class="buy_btn" id="buy_sub"><spring:message code="market.buy" /></button>
                </div>                
                <div class="buyCon sellCon fr">
                    <div class="buyTit">
                        <h3 class="fl buyColor"><spring:message code="market.sell" /></h3>
                        <div class="fr">
                            <span>${ftrademapping.fvirtualcointypeByFvirtualcointype2.fShortName } <spring:message code="trder.market.balance" /></span>
                            <span id="totalCoin">0</span>
                        </div>
                        <div class="clear"></div>
                    </div>
                    <div class="trade_tr trade_txt">
                        <span class="tr_l"><spring:message code="market.sellprice" /></span>
                        <input type="text" class="trade_txt" id="sell-price" value="" />
                        <span class="tr_r">${ftrademapping.fvirtualcointypeByFvirtualcointype1.fShortName }</span>
                    </div>
                    <p>≈<span><em class="sellCNY">4.39</em>CNY</span></p>
                    <div class="trade_tr trade_txt">
                        <span class="tr_l"><spring:message code="market.sellvolume" /></span>
                        <input type="text" class="trade_txt" value="" id="sell-amount" placeholder="请输入数量" />
                        <span class="tr_r">ETH</span>
                    </div>
                    <div class="numBar" id="sellBar">
                        <span class="proportioncircle proportion0" data-points="25" onclick="$('#sellBar span').removeClass('active');$(this).addClass('active')">25%</span>
                        <span class="proportioncircle proportion1" data-points="50" onclick="$('#sellBar span').removeClass('active');$(this).addClass('active')">50%</span>
                        <span class="proportioncircle proportion2" data-points="75" onclick="$('#sellBar span').removeClass('active');$(this).addClass('active')">75%</span>
                        <span class="noMargin proportioncircle proportion3" data-points="100" onclick="$('#sellBar span').removeClass('active');$(this).addClass('active')">100%</span>
                    </div>
                    <p><spring:message code="market.total" /><span id="sell-limit">0.0000&nbsp;</span><span>${ftrademapping.fvirtualcointypeByFvirtualcointype1.fShortName }</span></p>
                    <button class="buy_btn sell_btn" id="sell_sub"><spring:message code="market.sell" /></button>
                </div>
                <div class="clear"></div>
                <div class="mardeTradeWarp">
                    <p><spring:message code="market.nologin" /></p>
                </div>
            </div>
        </div>
        <div class="clear"></div>
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
    <input id="isGoogleBind" type="hidden" value="${isGoogleBind }">
    <input id="rate" type="hidden" value="6.5">
    <input id="indexurl" type="hidden" data-val="75" value="/kline/fullperiod.html?symbol=${ftrademapping.fid}&step=900&t=<%=new java.util.Date().getTime() %>">
    <input id="indexurl1" type="hidden" data-val="75" value="/kline/fullperiod.html?symbol=${ftrademapping.fid}&step=900&t=<%=new java.util.Date().getTime() %>">

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
                                <button id="modalbtn" type="button" class="btn btn-danger btn-block"><spring:message code="entrust.entrust.close" /></button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

<input type="hidden" id="coinId" value="${ftrademapping.fid}"/>
<input type="hidden" id="coinName" value="${ftrademapping.fvirtualcointypeByFvirtualcointype2.fShortName}"/>
<script type="text/javascript" src="${oss_url}/static/front/js/plugin/bootstrap.js?v=20171026105823.js?v=20181126201750"></script>
<script type="text/javascript" src="${oss_url}/static/front/js/plugin/layer/layer.js?v=20171026105823.js?v=20181126201750"></script>
<script type="text/javascript" src="${oss_url}/static/front/js/comm/util.js?v=20171026105823.js?v=20181126201750"></script>
<script type="text/javascript" src="${oss_url}/static/front/js/comm/comm.js?v=20171026105823.js?v=20181126201750"></script>
<script type="text/javascript" src="${oss_url}/static/front/js/plugin/jquery.jslider.js?v=20171026105823.js?v=20181126201750"></script>

<script type="text/javascript" src="${oss_url}/static/front/js/language/language_<spring:message code="language.title" />.js?v=20171026105823.js?v=20181126201750"></script>
<script type="text/javascript" src="${oss_url}/static/front/js/market/tradeindex.js?t=<%=new java.util.Date().getTime() %>"></script>
<script type="text/javascript">
    $(document).ready(function($) {
        var img=$(".lw-logo img");
        $(img).attr('src', '/static/front/images/market/market.png');
    });

</script>
<script type="text/javascript">
    //币种选择
    $(".coinType").hover(function(){
        var _this=$(this).children('.coinTypeChoice')
        $(_this).stop().slideToggle(200)
    });

</script> 
<script type="text/javascript">
    //全屏
    $(".toMax img").click(function(event) {
        $(".chartMain").addClass('chartMaxBox')
        $(this).hide(100, function() {
            $(".back_btn").show();
        });
    });
    //退出全屏
    $(".back_btn").click(function(event) {
        $(".chartMain").removeClass('chartMaxBox');
        $(this).hide();
        $(this).siblings('img').show();
    });
</script>   
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
    
    //定时调取当前虚拟币的信息
    var seconds = 2;
    //定时调用函数
    var timer = setInterval(timeRun, 60000);
    //倒计时函数
    function timeRun(){
        if (seconds <= 0) {
            clearInterval(timer);
            var dscurrent = document.getElementById('symbol');
            var dsurl = "real/market2.html?symbol=${ftrademapping.fid }&_t=" + parseInt(Math.random()*90+10);
            test(dsurl);
            var indexurl = $('#indexurl').val();
            showKline(indexurl);
            seconds = 2;
            timer = setInterval(timeRun, 60000);
            return;
        }
        seconds --;
    }


    function test(url) {
        var result = initArg();
        var rate = result.rate;
        var coinclass = result.coinclass;
        $.get(url, function(data) {
            document.getElementById('vold').innerHTML = data.vol;
            var kaipan = util.numFormat(Number(data.p_new) * rate, 4);
            var hoursdi = util.numFormat(Number(data.low) * rate, 4);
            var hoursgao = util.numFormat(Number(data.high) * rate, 4);
            document.getElementById('kaipan').innerHTML = kaipan;
            document.getElementById('hoursdi').innerHTML = hoursdi;
            document.getElementById('hoursgao').innerHTML = hoursgao;
            $('#kaipan').addClass("coin_price").addClass(coinclass);
            $('#hoursdi').addClass("coin_price").addClass(coinclass);
            $('#hoursgao').addClass("coin_price").addClass(coinclass);
            if (data.rose == 0) {
                var rose = "+" + data.rose + ".00%";
                document.getElementById('rosed').innerHTML = rose;
                document.getElementById('rosed').className = "lw-coin upColor";
                return;
            }
            var rose = data.rose.toString();
            if (rose.indexOf('-') == -1) {
                document.getElementById('rosed').innerHTML = "+" + rose + "%";
                document.getElementById('rosed').className = "lw-coin upColor";
            } else {
                document.getElementById('rosed').innerHTML = rose + "%";
                document.getElementById('rosed').className = "lw-coin downColor";
            }
        });
    
    }
    function testList(url,id){
         var result = initArg();
         var rate = result.rate;
         var coinclass =  result.coinclass;
         $.get(url,function(data){
                var iconList = document.getElementsByClassName('lw-fwf');
                var zuixin = document.getElementsByClassName('lw-zuixin');
                var pnew = util.numFormat(Number(data.p_new)*rate, 4);
                zuixin[id].innerHTML = pnew;
                $(zuixin[id]).addClass("coin_price").addClass(coinclass);
                if(data.rose == 0){
                    var rose = "+" + data.rose + ".00%";
                    iconList[id].innerHTML = rose+"<em>↑</em>";;
                    iconList[id].className = "lw-fwf upColor";
                    return;
                }
                var rose = data.rose.toString();
                if(rose.indexOf('-') == -1){
                    iconList[id].innerHTML = "+" + rose + "% <em>↑</em>";
                    iconList[id].className = "lw-fwf upColor";
                }else{
                    iconList[id].innerHTML = rose+ "% <em>↓</em>";
                    iconList[id].className = "lw-fwf downColor";
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
        changeSwitchClass(fromtype);
        $.cookie('cointype', fromtype);
        //初始化 
         var rateval = $("#rate").val();
        var totype = $.cookie('cointype');
        var rate = totype=='CNY'?rateval:1;
        var fromtype = totype=='CNY'?'USDT':'CNY';
        var cnytype = totype=='CNY'?'￥':'$';
        $('.qh-usdt').text(cnytype);
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
        
        changeSwitchClass(totype);
        $.cookie('cointype', totype);
        showData();
        var indexurl = $('#indexurl').val();

        // showKline(indexurl);
        $('#klineFullScreen').attr('src',$('#klineFullScreen').attr('src')+Math.random())
    }

    function changeSwitchClass(totype){
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
    }

    function showData(){
        var totype = $.cookie('cointype');
        var rateval = $("#rate").val();
        if(typeof totype!='undefined' && totype!='')
        {
            var rate = totype=='CNY'?rateval:(1/rateval);
            var fromtype = totype=='CNY'?'USDT':'CNY';
            var cnytype = totype=='CNY'?'￥':'$';
            $('.qh-usdt').text(cnytype);
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
</script>
<script type="text/javascript">
    //挂单
    $(".tradingTit ul li").click(function(event) {
        $(this).addClass('active').siblings().removeClass("active");
        var num=$(this).index();
        $(".tradingDate").eq(num).addClass('active').siblings().removeClass("active");
    });
    //委托记录
    $(".EntrutsTit ul li").click(function(event) {
        $(this).addClass('active').siblings().removeClass("active");
        var num=$(this).index();
        if(num == 0){
            $(".EntrutsCon1").css('display','block');
             $(".EntrutsCon2").css('display','none');
        }else{
            $(".EntrutsCon2").css('display','block');
             $(".EntrutsCon1").css('display','none');
        }
    });

    function initArg()
    {
        var cointype = $.cookie('cointype');
        var rateval = $("#rate").val();
        var rate = 1;
        var coinclass = '';
        if(typeof cointype!='undefined'&&cointype!='')
        {
            
             rate = cointype=='CNY'?rateval:1;
             coinclass = 'coin_price_'+cointype;
        }   
        return {rate:rate,coinclass:coinclass}
    }
    $(function() {
        var starturl = "real/market2.html?symbol=${ftrademapping.fid }&_t=" + parseInt(Math.random()*90+10);
        test(starturl);
        //获取其他币种的涨跌幅
        var iconList = document.getElementsByClassName('lw-coinName');
        for(var i = 0; i < iconList.length;i++){
            var iconUrl = "real/market2.html?symbol=" + iconList[i].getAttribute('data-setter') + "&_t=" + parseInt(Math.random()*90+10);
            testList(iconUrl,i);
        }
        honeySwitch.init();
        initPrice();
        
        var indexurl = $('#indexurl').val();

       
    });
</script>
<!-- k线图 -->
<script>



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

</body>
</html>
