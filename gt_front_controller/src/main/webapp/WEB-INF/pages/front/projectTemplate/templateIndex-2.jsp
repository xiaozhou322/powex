<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ include file="../comm/include.inc.jsp"%>

<!doctype html>
<html>
<head>
<%@ include file="../comm/basepath.jsp"%>
<meta http-equiv = "X-UA-Compatible" content = "IE=edge,chrome=1" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="../comm/link.inc.jsp"%>
<link href="${oss_url}/static/front2018/css/exchange/swiper.min.css?v=20181126" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${oss_url}/static/front2018/js/echarts/echarts.js"></script>
<script type="text/javascript" src="${oss_url}/static/front2018/js/plugin/layer/layer.js?v=20181126"></script>
<script type="text/javascript" src="${oss_url}/static/front2018/js/comm/swiper.min.js?v=20181126"></script>
<script type="text/javascript" src="${oss_url}/static/front2018/js/main/jquery-1.11.2.min.js?v=3"></script>
<style>
.headerTit{
position: absolute;
}
.tal hr {
	width: 4px;
	height: 18px;
	float: left;
	background-color: #1fa698;
	margin-top: 4px;
	border-color: #1fa698;
	border-style: solid;
	border-width: 1px;
}

.tal span {
	color: #1fa698;
	font-size: 18px;
	font-weight: 400;
	margin-left: 5px;
}

.title_bck {
	background-color: #ffffff;
	margin-top: 20px;
	margin-bottom: 50px;
}

#ul1 li:last-child {
	border-bottom: none;
}

#ul li:last-child {
	border-bottom: none;
}

/* banner广告 */
.bannerPow{
    height: 600px;
    clear: both;
    background: url(/static/front2018/images/powexImg/indexbg2.png) no-repeat center;
    background-size: 100% 100%;
    text-align: center;
    position: relative;
}
.bannerCenter{
	width:1300px;
	margin:0 auto;
	clear: both;
	overflow: hidden;
}
.bannerTit{    float: left;margin-top: 130px;}
.bannerTit h3{
    color: #fff;
    font-size: 48px;
    font-family: MicrosoftYaHei;
}
.bannerTit h4{
        font-size: 24px;
    font-family: MicrosoftYaHei;
    color: #fff;
    margin-top: 45px;
    margin-bottom: 45px;
    max-width: 410px;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
}
.bannerTit>a{
    font-size: 24px;
    font-family: MicrosoftYaHei;
    color: #fefefe;
    background: url(/static/front2018/images/powexImg/powex_go2x.png) no-repeat center;
    background-size: 100% 100%;
    display: block;
    height: 100px;
    line-height: 100px;
    width: 254px;
    margin: 0 auto;
}
.newsPow{
	float:right;
    margin-top: 170px;
}
#ul2,#ul3{
	margin-top: 16px;
    margin-bottom: 16px;
}
.newsPowLeft{
    float: left;
    background: url(/static/front2018/images/powexImg/gonggao2x.png) no-repeat center;
    background-size: 100% 100%;
    width: 380px;
    height: 260px;
    padding: 24px;
}
.newsPowLeft .getMore,.newsPowRight .getMore{
	float: right;
    color: #FFDCDC;
}
.newsPowLeft h2,.newsPowRight h2{
	color: #FFDCDC;
    font-size: 24px;
    font-family: MicrosoftYaHei;
    font-weight: 400;
    text-align: left;
}
.newsPowRight h2{
	color:#A9BCFF;
}
.newsPowRight{
    float: right;
    background: url(/static/front2018/images/powexImg/zixun2x.png) no-repeat center;
    background-size: 100% 100%;
    width: 380px;
    height: 260px;
    padding: 24px;
    margin-left: 56px;
}
/* 公告、资讯 */
#ul2 li,#ul3 li{
	height: 40px;
    line-height: 40px;
    text-align: left;}
#ul2 li a,#ul3 li a{  
    font-size: 14px;
}
#ul2 li .ul2Date,#ul3 li .ul2Date{float: right;color: #FFDCDC;}
.ul2ftitle{    
    color: #FFDCDC;
    font-size: 14px;
    font-family: MicrosoftYaHei;
    display: inline-block;
    width: 245px;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
}
#ul3 .ul2ftitle,#ul3 li .ul2Date,.newsPowRight .getMore{color:#A9BCFF;}
.articleSpan{

    font-size: 14px;
    line-height: 42px;
    height: 46px;
    color: #787b80;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
    width: 854px;
    float: left;
}
.coin_trade .tableCon ol li .span_02,.coin_trade  .em_2{width:250px;}
/* END公告、资讯 */
/* 图表echarts */
.echartsBox{
	margin: 0 auto;
    background: rgba(255,255,255,1);
    height: 208px;
    box-shadow: 0px 2px 9px 0px rgba(70, 90, 111, 0.14);
}
.echartsBox .echartsCenter{
	width: 1300px;
    margin: 0 auto;
    clear: both;
    overflow: hidden;
    padding-top: 16px;
}
.echartsBox .echartsCenter .echartsP{}
.echartsBox .echartsCenter .echartsP>em{    
	color: #654545;
    font-size: 24px;
    font-family: MicrosoftYaHei;
    float: left;}
.echartsBox .echartsCenter .echartsP>span{
	float: right;
}
.echartsBox .echartsCenter .echartsP2{
    color: #9C6C6C;
    font-size: 20px;
    font-family: MicrosoftYaHei;
    clear: both;
    overflow: hidden;
    }
    .echartsBox .echartsCenter .echartsP2 span{    font-size: 16px;}
.cred{color:#e56549;}
.cgreen{color:#76c80e;}
.echartsA,.echartsB,.echartsC{
    float: left;
    text-align: center;
    line-height: 46px;
}
.echartsB{margin: 0 28%;}
.echartsA{text-align: left;}
.echartsC{text-align: right;}
#echartsA,#echartsB,#echartsC{
	width:154px;
	height: 66px;
}
#echartsB{
    margin: 0 auto;
}
#echartsC{
	float: right;
}
/* END图表echarts */
.tabs li{
    height: 68px;
    padding: 0;
    width: 118px;
    line-height: 68px;
}
#app .lw_tab .s_icon img{
    vertical-align: -6px;
    width: 22px;
}
.tabs .s_icon, .s_name {
    display: inline-block;
}
#app .lw_tab .s_name{margin-left: 8px;color: #654545;}
.tabs li.active{
    color: #654545;
    border-bottom: 4px solid #685C88;
}
.title_bck .coinNav{
	height: 68px;
}
.iconSearch{
	right: 15px;
}
.search{
    margin-top: 16px;
    border: 1px solid #6E71A5;
}
.search .submit{
    width: 57px;
    right: 0px;
    z-index: 99;
    opacity: 0;
}

/* 主板市场 */
.mainBoard{
	text-align: center;
    font-size: 24px;
    font-family: MicrosoftYaHei;
    color: #1FA698;
}
.mainBoard hr{
   	width: 15px;
    height: 6px;
    display: inline-block;
    background-color: #1fa698;
    border-color: #1fa698;
    border-style: solid;
    border-width: 1px;
    vertical-align: 6px;
}
.tableConOlA{
	background: #fff;
    font-size: 18px;
    font-family: MicrosoftYaHei;
    border: none;
}
#app{
    width: 1300px;
    margin: 0 auto;
	box-shadow: 0px 2px 9px 0px rgba(70, 90, 111, 0.14);
   	margin-top: 58px;
}
.search .text{width: 293px;}
 /*奇数*/
    #ul li:nth-child(odd){
        background-color: #F9FBFF;
    }
    /*偶数*/
    #ul li:nth-child(even){
        background-color: #FFF;
    }
    #ul li:hover{
    background-color: #F0F5FF;
    }
    .indIcon{
    	color:#1FA698;
    }
    .coin_trade .tableCon .tradeUl1{
    background-color: rgba(109, 110, 162, 0.2);
    border: none;
    color: #9C6C6C;}
    .coin_trade .tableCon ol li span{color: #9C6C6C;font-size: 16px;}
    
     /*奇数*/
    #ul1 li:nth-child(odd){
        background-color: #FFF;
    }
    /*偶数*/
    #ul1 li:nth-child(even){
            background-color: rgba(109, 110, 162, 0.1);
    }
    .coin_trade .tableCon ol, .coin_trade .tableCon ul li{
   	    border: none;
    }
    #ul1 li:hover{
    background-color: #e2e2ec;
    }
/* END主板市场 */
.coin_trade .tableCon.cur{
    width: 100%;
}
.coin_trade{margin:0; width: 100%;}

/* 联系我们 */
.temContact{
    height: 474px;
    clear: both;
    background: url(/static/front2018/images/powexImg/link2x.png) no-repeat center;
    background-size: 100% 100%;
    width: 1300px;
    margin: 0 auto;
    box-shadow: 0px 2px 9px 0px rgba(70, 90, 111, 0.14);
    margin-top: 58px;
}
.temContact .temContactUl{
    float: right;
    width: 575px;
    margin-top: 40px;
    margin-right: 29px;
}
.temContactUl .temContactbor{
    border-bottom: 1px dotted #685C88;
    width: 257px;
    margin: 0 auto;
    margin-top: 17px;
    margin-bottom: 40px;
}
.temContact .temContactUl li:nth-child(1){
    width: 370px;
    margin: 0 auto;
    text-align: center;
    font-size: 36px;
    font-family: MicrosoftYaHei;
    color: #654545;
    border-bottom: 12px solid #685C88;
    padding-bottom: 14px;
}
.temContact .temContactUl li:nth-child(2){
    font-size: 22px;
    font-family: MicrosoftYaHei;
    line-height: 48px;
    color: #654545;
}
/* 资讯列表 */
.institution{
	padding: 30px 0px;
	margin: 58px auto;
    width: 1300px;
    box-shadow: 0px 2px 9px 0px rgba(70, 90, 111, 0.14);
}
.institution>h3{
    color: #654545;
    font-size: 24px;
    margin-bottom: 30px;
    text-align: left;
    margin-left: 30px;
    padding-left: 10px;
    border-left: 8px solid #4B265A;
    height: 25px;
    line-height: 24px;
}
.institution>h3>span{
	float: right;
    width: 1060px;
    border-bottom: 1px dotted #685C88;
    height: 14px;
    margin-right: 30px;
}
/* 战略合作机构 */
.swiper-container {
      width: 100%;
      height: auto;
      margin-left: auto;
      margin-right: auto;
    }
  .swiper-container  .swiper-wrapper{
  		margin-bottom: 2em;
  }
  .swiper-pagination-bullet-active{
    background: #345655;
  }
  .swiper-button-prev{ background: url(/static/front2018/images/powexImg/more2x.png) no-repeat center;}
  .swiper-button-next{ background: url(/static/front2018/images/powexImg/more1x.png) no-repeat center;}
.swiper-button-next, .swiper-button-prev{    top: 40%;background-size: 44%;}
    .swiper-slide {
      text-align: center;
      font-size: 18px;
      background: #fff;
      height: 60px;
      display: -webkit-box;
      display: -ms-flexbox;
      display: -webkit-flex;
      display: flex;
      -webkit-box-pack: center;
      -ms-flex-pack: center;
      -webkit-justify-content: center;
      justify-content: center;
      -webkit-box-align: center;
      -ms-flex-align: center;
      -webkit-align-items: center;
      align-items: center;
    }
    .swiper-slide h3{
	    text-align: center;
	    color: #31564C;
	    font-size: 16px;
    }
/* 媒体查询 */
@media screen and (min-width: 1300px) {

}
</style>
</head>
<body>

<input id="locale" value="${pageContext.response.locale}" type="hidden"/>
<div>
  <%@include file="../comm/header.jsp"%>
</div>
 
<section class="l-content">
<!-- banner广告 -->
	<div class="bannerPow">
		<div class="bannerCenter">
				<!-- 项目方介绍 -->
			<div class="bannerTit">
				<h3>${requestScope.project.name}</h3>
				<h4>${requestScope.project.advantage}</h4>
				<a href="/lucky/luckyIndex.html">POWEX.PRO→</a>
			</div>
				<!-- 公告资讯 -->
			<div class="newsPow">
                <div class="newsPowLeft">
                    <h2>
                     <spring:message code="new.announcement" /> 
                    
                    </h2>
                    <ul id="ul2">
                      <c:forEach items="${articles[0].value }" var="v" varStatus="n">
                     <c:choose>
                      <c:when test="${pageContext.response.locale eq 'zh_CN' }">            
                          <li>
                         <a href="/service/article.html?id=${v.fid }">
                          <span class="ul2ftitle">[${n.index+1}]&nbsp;${v.ftitle_cn }</span>
                          <span class="ul2Date">
                          		<fmt:formatDate value="${v.fcreateDate }" pattern="yyyy-MM-dd"/>
                           </span>
                        </a>
                          </li>
                       </c:when>
                      <c:otherwise>
                       <li>
                      	  <a href="/service/article.html?id=${v.fid }">[<fmt:formatDate value="${v.fcreateDate }" pattern="yyyy-MM-dd"/>] ${v.ftitle }</a>
                       </li>
                      </c:otherwise>
                    </c:choose>
                      </c:forEach> 
                    </ul>
                     <a href="/service/ourService.html?id=1" class="getMore"><spring:message code="new.more" />&gt;&gt;</a>
                </div>
                <!-- 行业资讯 -->               
                <div class="newsPowRight">
                    <h2>
                     <spring:message code="new.information" />
                     
                    </h2>
                    <ul id="ul3">
                       <c:forEach items="${articles[1].value }" var="v" varStatus="n">
                        <c:choose>
                    	<c:when test="${pageContext.response.locale eq 'zh_CN' }">            
                        <li>
                         <a href="/service/article.html?id=${v.fid }">
                         	<span class="ul2ftitle">[${n.index+1}]&nbsp; ${v.ftitle_cn }</span>
                         	<span class="ul2Date">
                          	<fmt:formatDate value="${v.fcreateDate }" pattern="yyyy-MM-dd"/>
                        	</span>  
                          </a>
                         </li>
                          </c:when>
                      <c:otherwise>
                       <li><a href="/service/article.html?id=${v.fid }">[<fmt:formatDate value="${v.fcreateDate }" pattern="yyyy-MM-dd"/>] ${v.ftitle }</a></li>
                        </c:otherwise>
                    </c:choose>
                        </c:forEach> 
                    </ul>
                    <a href="/service/ourService.html?id=3" class="getMore"><spring:message code="new.more" />&gt;&gt;</a>
                </div> 
           </div>
        </div>
<!-- END资讯新闻 -->
	</div>
	<!-- 图表数据↑↓ -->
	<div class="echartsBox">
		<div class="echartsCenter">
			<div class="echartsA">
				<p class="echartsP">
					<em>USDT</em>
					<span class="cred">↑1.20%</span>
				</p>
				<p class="echartsP2">￥123456/<span>$12345</span></p>
				<div id="echartsA"></div>
			</div>
			<div class="echartsB">
				<p class="echartsP">
					<em>BTC</em>
					<span class="cgreen">↓1.20%</span>
				</p>
				<p class="echartsP2">￥123456/<span>$12345</span></p>
				<div id="echartsB"></div>
			</div>
			<div class="echartsC">
				<p class="echartsP">
					<em>ETH</em>
					<span class="cgreen">↓1.20%</span>
				</p>
				<p class="echartsP2">￥123456/<span>$12345</span></p>
				<div id="echartsC"></div>
			</div>
		</div>
	</div>
<!-- END banner广告 -->
    <div class="index_con" id="app">
      <div class="title_bck" >
        <div class="coinNav tableConOlA">
            <div style="width:1300px;">
                <div class="coin_tab clear">
                    <ul class="clear tabs fl">
                        <li class="fl active lw_market lw_tab" id="0_market" data-key="0" data-max="3">
                        	<span class="s_name"><spring:message code="nav.index.allmarket" /></span>
                       	</li>                        
                         <li class="fl lw_tab" id="4_market" data-key="4" data-max="3">
	                         <i class="s_icon"><img src="${oss_url}/static/front2018/images/powexImg/usdt1x.png" width="25" alt="" /></i>
	                         <span class="s_name">USDT</span>
                         </li>
                         <li class="fl lw_tab" id="1_market" data-key="1" data-max="3">
	                         <i class="s_icon"><img src="${oss_url}/static/front2018/images/powexImg/btc1x.png" width="25" alt="" /></i>
	                         <span class="s_name">BTC</span>
                         </li>
                        <li class="fl lw_tab" id="3_market" data-key="3" data-max="3">
	                        <i class="s_icon"><img src="${oss_url}/static/front2018/images/powexImg/eth1x.png" width="25" alt="" /></i>
	                        <span class="s_name">ETH</span>
                        </li>

                        <!-- <c:forEach var="vv" items="${fMap }" varStatus="vn">
                         <li class="fl lw_tab " id="${vv.key.fid }_market" data-key="${vv.key.fid }" data-max="${fn:length(fMap) }"><i class="s_icon"><img src="${oss_url}/static/front2018/images/ETH.png" width="25" alt="" /></i><span class="s_name">${vv.key.fShortName }市场</span></li>
                        </c:forEach> -->
                    </ul>
                    <div class="search fr">
                        <!-- <span>Search</span> -->
                        <input class="text searchInput" type="text" value='' <%-- placeholder="<spring:message code="new.serachval" />" --%> id="search">
                        
                        <!-- <svg class="icon iconSearch" aria-hidden="true">
                            <use xlink:href="#icon-sousuo"></use>
                        </svg> -->
                        <input class="submit" type="submit" value="搜索">
						<img src="${oss_url}/static/front2018/images/powexImg/shousuo2x.png" class="icon iconSearch" />
                    </div>
                </div>
            </div>
        </div>
        <div class="coin_trade">
            <div style="width:1300px;">
                <div class="tableCon cur">
                    <ol class="tradeUl1">
                        <li class="clear">
                            <span><spring:message code="nav.index.coin" /></span>
                            <span class="span_02"><spring:message code="nav.index.latpri" /></span>
                            <span><spring:message code="nav.index.24volume" /></span>
                            <span><spring:message code="market.24change" /></span>
                            
                            <span><spring:message code="nav.index.24high" /></span>
                            <span><spring:message code="nav.index.24low" /></span>
                            
                            <%-- <span><spring:message code="nav.index.pricetrend" /></span> --%>
                            <span>
                                <spring:message code="new.coinnav.Turnover" />
                            </span>
                            <span>
                                <svg class="icon indIcon" aria-hidden="true">
                                    <use xlink:href="#icon-heguiqushi"></use>
                                </svg>
                                <spring:message code="new.coinnav.tit" />
                            </span>
                        </li>
                    </ol>
                    <ul id="ul1">
                     <c:forEach var="vv" items="${fMap }" varStatus="vn">
                        <c:forEach items="${vv.value }" var="v" varStatus="vs">
                        <li class="clear ${vv.key.fid }_market_list market-con market_show markent_s" data-mark="${vv.key.fid }" data-key="${v.fvirtualcointypeByFvirtualcointype2.fShortName }">
                            <em class="emWidth"><a href="/trademarket.html?symbol=${v.fid }"><i></i>${v.fvirtualcointypeByFvirtualcointype2.fShortName }/${vv.key.fShortName }</a></em>
                            <em class="emWidth em_2"><span id="${v.fid }_price_1">--</span></em>
                            <em class="emWidth"><span id="${v.fid }_total_1">--</span></em>
                            <em class="emWidth" id="${v.fid }_rose_1">--</em>
                            <em class="emWidth" id="${v.fid }_high_1">--</em>
                            <em class="emWidth" id="${v.fid }_low_1">--</em>
                            <em class="emWidth" id="${v.fid }_total_24rmb">--</em>
                            <%-- <em class="emWidth c_upPrice" id="${v.fid }_plot"></em> --%>
                            <em class="emWidth tradeBtn">
	                            <a href="/trademarket.html?symbol=${v.fid }">
	                            	<%-- <spring:message code="new.tradebtn" /> --%>
	                            	<img src="${oss_url}/static/front2018/images/major.png" style="height: 26px;margin-top: 19px;"/>
	                            </a>
                            </em>
                        </li> 
                          </c:forEach>
                      </c:forEach>
                    </ul>
                </div>
            </div>
        </div>
      </div>
    </div>
     <!-- 联系我们 -->
    <div class="temContact">
    	<ul class="temContactUl">
    		<li>
    			<h3>项目特点</h3>
    		</li>
    		<li>
    		<p class="temContactbor"></p>
    		${requestScope.project.introduce}
    		</li>
    	</ul>
    </div>
    <!-- 战略合作机构 -->
    <c:if test="${null != cooperateOrgModelList}">
	    <div class="institution">
	    	<h3><spring:message code="new.coinnav.institution"/>
	    		<span></span>
	    	</h3>
	    	   <div class="swiper-container">
				    <div class="swiper-wrapper">
				    <c:forEach items="${cooperateOrgModelList }" var="v" varStatus="n">
				      <div class="swiper-slide">
				      <a href="${v.linkUrl}">
				      	<p><img alt="" src="${v.picUrl }" style="max-height:60px;max-width:302.5px"></p>
				      </a>
				      </div>
				    </c:forEach>
				    </div>
				    <div class="swiper-button-next"></div>
				    <div class="swiper-button-prev"></div>
				    <!-- Add Pagination -->
				    <div class="swiper-pagination"></div>
			  </div>
	    </div>
    </c:if>
    <!-- END资讯、联系我们 -->
</section>
<%@include file="../comm/footer.jsp" %>
<input type="hidden" id="errormsg" value= />
<script type="text/javascript" src="${oss_url}/static/front2018/js/index/index.js?v=20001234362"></script>
<script type="text/javascript" src="${oss_url}/static/front2018/js/index/indexSearch.js?v=20001234233"></script>


 <script>
    var swiper = new Swiper('.swiper-container', {
      slidesPerView: 4,
      slidesPerColumn: 2,
      spaceBetween: 30,
      pagination: {
        el: '.swiper-pagination',
        clickable: true,
      }, 
      navigation: {
        nextEl: '.swiper-button-next',
        prevEl: '.swiper-button-prev',
      },
    });
    

    	var domA = document.getElementById("echartsA");
    	var domB = document.getElementById("echartsB");
    	var domC = document.getElementById("echartsC");
    	var myChartC = echarts.init(domC);
    	var myChart = echarts.init(domB);
    	var myChartA = echarts.init(domA);
    	option = {
    	    xAxis: {
    	        splitLine:{show: false},
    	        show:false,
    	        type: 'category',
    	        boundaryGap: false
    	    },
    	    yAxis: {
    	        show:false,
    	        splitLine:{show: false},
    	        type: 'value'
    	    },
    	    series: [{
    	        symbol: "none",
    	         itemStyle : {
    	            normal: {   //颜色渐变函数 前四个参数分别表示四个位置依次为左、下、右、上
    	                color: new echarts.graphic.LinearGradient(0, 0, 0, 1,[{
    	                        offset: 0, color: '#6A6699' // 0% 处的颜色
    	                    }, {
    	                        offset: 0.5, color: '#685A84' // 100% 处的颜色
    	                    }, {
    	                        offset: 1, color: '#fff' // 100% 处的颜色
    	                    }]
    	                ),  //背景渐变色 
    	                lineStyle: {        // 系列级个性化折线样式  
    	                    width: 1,  
    	                    type: 'solid',  
    	                    color: "#6C6797"
    	                }
    	            },
    	        },  
    	        data: [820, 932, 901, 934, 1290, 1330, 1320],
    	        type: 'line',
    	        areaStyle: {}
    	    }]
    	};
    	optionA = {
    	    xAxis: {
    	        splitLine:{show: false},
    	        show:false,
    	        type: 'category',
    	        boundaryGap: false
    	    },
    	    yAxis: {
    	        show:false,
    	        splitLine:{show: false},
    	        type: 'value'
    	    },
    	    series: [{
    	        symbol: "none",
    	         itemStyle : {
    	            normal: {   //颜色渐变函数 前四个参数分别表示四个位置依次为左、下、右、上
    	                color: new echarts.graphic.LinearGradient(0, 0, 0, 1,[{
    	                        offset: 0, color: '#6A6699' // 0% 处的颜色
    	                    }, {
    	                        offset: 0.5, color: '#685A84' // 100% 处的颜色
    	                    }, {
    	                        offset: 1, color: '#fff' // 100% 处的颜色
    	                    }]
    	                ),  //背景渐变色 
    	                lineStyle: {        // 系列级个性化折线样式  
    	                    width: 1,  
    	                    type: 'solid',  
    	                    color: "#6C6797"
    	                }
    	            },
    	        },  
    	        data: [820, 932, 901, 934, 1290, 1330, 1320],
    	        type: 'line',
    	        areaStyle: {}
    	    }]
    	};
    	optionC = {
    	    xAxis: {
    	        splitLine:{show: false},
    	        show:false,
    	        type: 'category',
    	        boundaryGap: false
    	    },
    	    yAxis: {
    	        show:false,
    	        splitLine:{show: false},
    	        type: 'value'
    	    },
    	    series: [{
    	        symbol: "none",
    	         itemStyle : {
    	            normal: {   //颜色渐变函数 前四个参数分别表示四个位置依次为左、下、右、上
    	                color: new echarts.graphic.LinearGradient(0, 0, 0, 1,[{
    	                        offset: 0, color: '#6A6699' // 0% 处的颜色
    	                    }, {
    	                        offset: 0.5, color: '#685A84' // 100% 处的颜色
    	                    }, {
    	                        offset: 1, color: '#fff' // 100% 处的颜色
    	                    }]
    	                ),  //背景渐变色 
    	                lineStyle: {        // 系列级个性化折线样式  
    	                    width: 1,  
    	                    type: 'solid',  
    	                    color: "#6C6797"
    	                }
    	            },
    	        },  
    	        data: [820, 932, 901, 934, 1290, 1330, 1320],
    	        type: 'line',
    	        areaStyle: {}
    	    }]
    	};

    	myChartC.setOption(optionC);
    	myChart.setOption(option);
    	myChartA.setOption(optionA);
    	//根据窗口的大小变动图表 --- 重点
    	window.onresize = function(){
    	    myChart.resize();
    	    myChartA.resize();
    	    myChartC.resize();    //若有多个图表变动，可多写

    	}

  </script>

</body>
</html>
