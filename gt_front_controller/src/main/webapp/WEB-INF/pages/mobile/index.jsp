<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ include file="comm/include.inc.jsp"%>

<!DOCTYPE html>
<html>
<head>
	<%@ include file="comm/basepath.jsp"%>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <meta name = "viewport" content = "width = device-width, user-scalable = no,initial-scale=0.5,maximum-scale=0.5,minimum-scale=0.5">
  <script type="text/javascript" src="${oss_url}/static/mobile2018/js/html5shiv.js?v=201902230500100"></script>
   <%--  <script type="text/javascript" src="${oss_url}/static/mobile2018/js/respond.js?v=20181126201750"></script>  --%>

	<%@include file="comm/link.inc.jsp"%>
    <style type="text/css">
        @font-face {
           font-family: 'PingFangMedium';
           src: url('${oss_url}/static/mobile2018/fonts/PingFangMedium.ttf'); 
        }
        
        
  .indexMain .fbannerscontent_0_0 {
	font-size:  1em;
	color: #ff6d56;
}

.indexMain .fbannerscontent_0_2 {
	font-size:  1em;
	color: #ffffff;
}

.indexMain .fbannerscontent_1_0 {
	font-size:  1em;
	color: #ffe623;
}

.indexMain .fbannerscontent_2_0 {
	font-size: 1em;
	color: #5af8fe;
}

.indexMain .fbannerscontent_3_0{
    font-size: 1em;
	color: #34253b;
}
.indexMain .fbannerscontent_3_1{
     color: #46384d;
      font-size: 1.6em;
}
.centercontent {
	font-size: 1.6em;
	color: #ffffff;
	margin-top: 10px;
	letter-spacing: 2px;
}
.centercontent1 {
	font-size: 1.6em;
    color: #ffffff;
    margin-top: 15px; 
    letter-spacing: 2px;
    text-align: center;
    width: 100%;
}
.centercontent2 {
	font-size: 1em;
    color: #ffffff;
    margin-top: 15px; 
    letter-spacing: 2px;
    text-align: center;
    width: 100%;
}

#barrage{position: fixed;
    display: block;
    z-index: 9;
    bottom: 1rem;
    right: 0px;}  
    #barrage img{
        width: 1rem;
    	height: 1rem;
    }     
.downloadApp{
color: #fff;
    position: absolute;
    z-index: 9;
    right: 1rem;
    top: 0.3rem;
    font-size: 0.26rem;
}
.introlbg{
 	background: url(/static/mobile2018/images/exchange/yaoqinglianjie.png) no-repeat center #c9586e;
    background-size: 100% 100%;
    height: 1rem;
    display: block;
    margin: 0 0.3rem 0.2rem 0.3rem;
    box-shadow: 0px 2px 9px 0px rgba(70, 90, 111, 0.62);
  
}

    </style>    
</head>

<body>
<a href="/luckydraw/luckydrawIndex.html" id="barrage">
<img src="${oss_url}/static/mobile2018/images/exchange/lucky.png" class="turntable"/> 
</a>
<%@include file="comm/header.jsp" %>
<a href="/download/index.html" class="downloadApp"><spring:message code="nav.top.appdownload" /></a> 
<div class="topR">

  <c:if test="${pageContext.response.locale eq 'en_US' }">
  <a href="javascript:;" class="lang fl clear en" onclick="langs('lang=zh_CN');"></a>
  </c:if>
  <c:if test="${pageContext.response.locale eq 'zh_CN' }">
  <a href="javascript:;" class="lang fl clear" onclick="langs('lang=en_US');"></a>
  </c:if>
 </div>
<section class="indexMain">
    <div class="swiper-container banner">
			<div class="swiper-wrapper">
				<c:choose>

					<c:when test="${requestScope.isprojectUrl}">

						<div class="swiper-slide">
							<a href="">
							<img src="${oss_url}/static/front2018/images/projectbanner.png" alt="" /> 
							<c:choose>
									<c:when test="${pageContext.response.locale eq 'zh_CN' }">
										<c:set value="${ fn:split(v.fcontent, '#') }" var="fcontents" />
									</c:when>
									<c:otherwise>
										<c:set value="${ fn:split(v.fcontent_en, '#') }"
											var="fcontents" />

									</c:otherwise>
								</c:choose>
								<div style="margin-top: -270px;position: absolute; z-index: 10000; width: 100%; text-align: center;">

									<c:choose>
										<c:when test="${pageContext.response.locale eq 'zh_CN' }">
											<p class="centercontent1">${requestScope.project.name}</p>
											<p class="centercontent2">${requestScope.project.advantage}</p>
										</c:when>
										<c:otherwise>
											<p class="centercontent1">${requestScope.project.name}</p>
											<p class="centercontent2">${requestScope.project.advantage}</p>
										</c:otherwise>
									</c:choose>

								</div> </a>
						</div>

					</c:when>
					<c:otherwise>
						<c:forEach items="${requestScope.constant['fbanners']}" var="v"
							varStatus="vs">
							<div class="swiper-slide">
								<a href="${v.furl }"><img src="${v.fimgUrlm }" alt="" /> <c:choose>
										<c:when test="${pageContext.response.locale eq 'zh_CN' }">
											<c:set value="${ fn:split(v.fcontent, '#') }" var="fcontents" />
										</c:when>
										<c:otherwise>
											<c:set value="${ fn:split(v.fcontent_en, '#') }"
												var="fcontents" />

										</c:otherwise>
									</c:choose>
									<div style="margin-top: -270px;position: absolute; z-index: 10000; width: 100%; text-align: center;">
										<c:forEach items="${ fcontents }" var="fcontent"
											varStatus="fc">
											<p
												class="centercontent fbannerscontent_${vs.index}_${fc.index}">${fcontent}</p>
										</c:forEach>
									</div> </a>
							</div>
						</c:forEach>
					</c:otherwise>

				</c:choose>


			</div>
			<div class="swiper-pagination"></div>
    </div>
    <div class="index_news">
        <span class="notice">
           <!--<svg class="icon" aria-hidden="true">
                   <use xlink:href="#icon-tongzhigonggao"></use>
            </svg> -->
            <!-- <i class="iconfont icon-xitonggonggao1" style="font-size:0.34rem; vertical-align:-6px;"></i> -->
            <img src="${oss_url}/static/mobile2018/images/exchange/icon_news.png" style="width: 0.24rem;height: 0.24rem;"/>
        </span>
        <div class="swiper-container news_all">
            <ul class="swiper-wrapper">
             <c:forEach items="${articles[0].value }" var="v">
	             <c:choose>
	                <c:when test="${pageContext.response.locale eq 'zh_CN' }">
	                    <li class="swiper-slide news_a"><a href="${v.url }">${v.ftitle_cn }</a></li>
	                 </c:when>
	                <c:otherwise>
	                    <li  class="swiper-slide news_a"><a href="${v.url }">${v.ftitle }</a></li>
	                </c:otherwise>
	            </c:choose>
            </c:forEach>
            </ul> 
        </div> 
    </div>
   
    <div class="coinNumType">
        <ul class="clear">
            <c:forEach var="vv" items="${fMap }" varStatus="vn">
                <c:forEach items="${vv.value }" var="v" varStatus="vs">
                    <c:if test="${(v.fvirtualcointypeByFvirtualcointype2.fShortName=='BTC' && vv.key.fShortName == 'USDT') || (v.fvirtualcointypeByFvirtualcointype2.fShortName=='ETH'  && vv.key.fShortName == 'USDT') || (v.fvirtualcointypeByFvirtualcointype2.fShortName == 'BFSC' && vv.key.fShortName == 'USDT') }">
                        <li class="fl ">
                            <a href="/trademarket.html?symbol=${v.fid }">
                                <p class="c_name"><!-- <img src="${v.fvirtualcointypeByFvirtualcointype2.furl }" alt="" /> -->
                                	${v.fvirtualcointypeByFvirtualcointype2.fShortName }/${vv.key.fShortName }
                               		<b class="" id="${v.fid }_rose" style="vertical-align: 0.04rem;">--</b>
                                </p>
                                <p class=" price_1" id="${v.fid }_price">--</p>
                                <p>
                                <em style="font-weight:400;">≈</em><span id="${v.fid }_cny">--</span>
                                <span style="font-size:0.24rem; padding-left:2px;">CNY</span>
                                </p>
                            </a>
                        </li>
                    </c:if>            
                </c:forEach>
            </c:forEach> 
        </ul>
    </div>
    
    <a href="/introl/introlIndex.html" class="introlbg"></a>
    <div class="coinMarket">
        <div class="market_tit">
            <ol class="clear market_ol">
                <li class="fl active lw_tab" id="0_market" data-key="0"  data-max="3"><spring:message code="nav.index.allmarket" /></li>
                <li class="fl lw_tab" id="4_market" data-key="4"  data-max="3">USDT</li>
                <li class="fl lw_tab" id="1_market" data-key="1"  data-max="3">BTC</li>
                <li class="fl lw_tab" id="3_market" data-key="3"  data-max="3">ETH</li>
            </ol>
        </div>
        <div class="coinList">
            <ul class="coin_ul " data-setter="${v.fid }">
            <c:forEach var="vv" items="${fMap }" varStatus="vn">   
                <c:forEach items="${vv.value }" var="v" varStatus="vs"> 
                <li class="market-con ${vv.key.fid }_market_list" data-setter="${v.fid }">
                    <a  href="/trademarket.html?symbol=${v.fid }">
                        <table class="table">
                            <tr>
                                <td class="td textL td_1">
                                    <div class="listNum listDownNum" style="background: url(${v.fvirtualcointypeByFvirtualcointype2.furl }) no-repeat 0 center;background-size:85% auto; "></div>
                                    <span class="upTit">${v.fvirtualcointypeByFvirtualcointype2.fShortName }/${vv.key.fShortName } </span>
                                    <span><!-- <spring:message code="json.trade.dealvolume" /> --> <em id="${v.fid }_total_1">--</em></span>
                                </td>
                                <td class="td textL td_2">
                                    <span class="upTit" id="${v.fid }_price_1">--</span>
                                </td>
                                <td class="td textR"><span class="applies downApplies" id="${v.fid }_rose_1">--</span></td>
                            </tr>
                        </table>
                    </a>
                </li>                               
               </c:forEach>
            </c:forEach>               
            </ul>
        </div>
    </div>
<c:choose>
<c:when test="${pageContext.response.locale eq 'zh_CN' }">
 <input type="hidden" name="curlang" id="curlang" value="cn">
</c:when>
<c:otherwise>
  <input type="hidden" name="curlang" id="curlang" value="en">
</c:otherwise>
</c:choose>
</section>

<%@include file="comm/tabbar.jsp"%>
<%@include file="comm/footer.jsp"%>
 <script src="${oss_url}/static/mobile2018/js/fluckydraw/com.js?v=20181126201750"></script>
<script type="text/javascript" src="${oss_url}/static/mobile2018/js/plugin/jquery.flot.js?v=20171026105823.js"></script> 
<script type="text/javascript" src="${oss_url}/static/mobile2018/js/2018-index.js?v=201902230500100.js"></script> 
<script type="text/javascript">
    $(function(){
       var announcementSwiper = new Swiper('.news_all', {
			    direction: 'vertical',
				loop: true,
				autoplay : 2000,
				autoplayDisableOnInteraction : false,
			})
    })
</script>
</body>
</html>
