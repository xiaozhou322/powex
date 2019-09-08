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
<script type="text/javascript" src="${oss_url}/static/front2018/js/plugin/layer/layer.js?v=20181126"></script>
<script type="text/javascript" src="${oss_url}/static/front2018/js/comm/swiper.min.js?v=20181126"></script>
<script type="text/javascript" src="${oss_url}/static/front2018/js/main/jquery-1.11.2.min.js?v=3"></script>
<style>
body{
	background:#0E1C26;
}
.tal hr{
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
    background-color: #0c1821;
    margin-top: 20px;
    margin-bottom: 50px;
    border: 1px solid rgba(28, 255, 199, 0.1);
    min-height: 731px;
}

#ul1 li:last-child {
	border-bottom: none;
}

#ul li:last-child {
	border-bottom: none;
}

/* banner广告 */
.bannerPow{
    height: 408px;
    clear: both;
    text-align: center;
    position: relative;
}
.bannerPow .bannerCenter{
    padding-top: 76px;
    width: 1230px;
    margin: 0 auto;
    text-align: left;
    position: relative;
    }
.bannerPow .bannerCenter .bannerbg{
    text-align: center;
    border: 1px solid rgba(1, 255, 191, 0.5);
    width: 620px;
    clear: both;
    overflow: hidden;
    zoom: 1;
}
.bannerPow .bannerCenter .bannerbg h3{
    color: #C6DCDD;
    font-weight: 100;
    margin-bottom: 20px;
    font-size: 36px;
    font-family: MicrosoftYaHei;
    margin-top: 20px;
}
.bannerPow .bannerCenter .bannerbg h4{
    font-weight: 400;
    color: #DEF8F9;
    font-size: 20px;
    font-family: MicrosoftYaHei;
    line-height: 36px;
    width: 320px;
    margin: 0 auto;
    text-align: left;
}
.bannerPow .bannerCenter a{
   	float: right;
    font-size: 17px;
    font-family: MicrosoftYaHei;
    font-weight: 400;
    text-decoration: underline;
    color: #1DFFCD;
    margin: 22px 22px 19px 0;
}
/* 公告、资讯 */
.newsPowBox{
    clear: both;
    overflow: hidden;
    zoom: 1;
    background: #0B141B;
}
.newsPow{
    clear: both;
    overflow: hidden;
    zoom: 1;
    min-height: 130px;
    width: 1300px;
    margin: 0 auto;
    padding: 20px 0px;
}
.newsPow .newsBorder{
    width: 1px;
    height: 126px;
    border-left: 1px dotted #666666;
    float: left;
    margin: 0 40px;
    margin-top: 10px;
}
.newsPow .newsPowLeft h2{
    color: #C6DCDD;
    font-size: 20px;
    font-family: MicrosoftYaHei;
    font-weight: 400;
}
.newsPow .newsPowLeft h2>a{
	float: right;
    color: #1CFFC7;
    font-size: 14px;
    font-family: MicrosoftYaHei;}
#ul2{
	clear: both;
    overflow: hidden;
    margin-top: 10px;
}
#ul2 li{
	float:left;
}
#ul2 li:nth-child(2){
    border-left: 1px rgba(28, 255, 199, 0.3) dotted;
    margin-left: 60px;
    padding-left: 30px;
    border-right: 1px rgba(28, 255, 199, 0.3) dotted;
    margin-right: 54px;
    padding-right: 60px;
}
#ul2 li:nth-child(5){
	border-left: 1px #1CFFC7 dotted;
    margin-left: 60px;
    padding-left: 30px;
    border-right: 1px #1CFFC7 dotted;
    margin-right: 54px;
    padding-right: 60px;
}
#ul2 li a{
    color: #A8BEBF;
    font-size: 14px;
    width: 364px;
    display: block;
    clear: both;
    overflow: hidden;
}
#ul2 li,#ul3 li{
	height: 40px;
    line-height: 40px;}
#ul3 li a{    
	color: #666666;
    font-size: 14px;
}
#ul2 li .ul2Date,#ul3 li .ul2Date{float: right;}
 .ul2ftitle{    
    font-size: 14px;
    line-height: 22px;
    height: 46px;
    color: #A8BEBF;
    margin: 10px 5px 10px 5px;
    overflow: hidden;
    text-overflow: ellipsis;
    display: -webkit-box;
    -webkit-box-orient: vertical;
    -webkit-line-clamp: 2;
    word-break: break-all;
}
.articleSpan{
    font-size: 14px;
    line-height: 42px;
    height: 46px;
    color: #A8BEBF;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
    width: 245px;
    float: left;
}
.coin_trade .tableCon ol li .span_02,.coin_trade  .em_2{width:250px;}
/* END公告、资讯 */
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
#app .lw_tab .s_name{margin-left: 8px;color: #C5E1E2;}
.tabs li.active{
    color: #C5E1E2;
    border-bottom: 4px solid #C6DCDD;
    background: #0C1821;
}
.title_bck .coinNav{
	height: 68px;
}
.iconSearch{
    right: 15px;
    opacity: 0.7;
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
	background: #0c1821;
    font-size: 18px;
    font-family: MicrosoftYaHei;
    border: none;
}
#app{
    width: 1300px;
    margin: 0 auto;
    margin-top: 60px;
	box-shadow: 0px 2px 9px 0px rgba(70, 90, 111, 0.14);
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
    .search{
     	margin-top: 16px;
        border: 1px solid rgba(28, 255, 199, 0.3);
    }
    .indIcon{
    	color:#1FA698;
    }
    .coin_trade .tableCon .tradeUl1{
	   	border: none;
	    width: 1298px;
	    background-color: #101A21;
	    border-top: 1px solid rgba(28, 255, 199, 0.1);
	    margin-top: 10px;
	    border-bottom: 1px solid rgba(28, 255, 199, 0.1);
    }
    .coin_trade .tableCon ol li span{
    	color:#C6DCDD;
    	line-height: 46px;
    	height: 46px;
    
    }
    #ul1 li .emWidth{
        line-height: 46px;
    	height: 46px;
    }
    #ul1 li{
	    margin-top: 16px;}
     /*奇数*/
    #ul1 li:nth-child(odd){
        background-color: #16262A;
	    width: 1298px;
	    
	    border-top: 1px solid rgba(28, 255, 199, 0.1);
	    border-bottom: 1px solid rgba(28, 255, 199, 0.1);
    }
    /*偶数*/
    #ul1 li:nth-child(even){
        background-color: #101A21;
	    width: 1298px;
	    border-top: 1px solid rgba(28, 255, 199, 0.1);
	    border-bottom: 1px solid rgba(28, 255, 199, 0.1);
    }
    .coin_trade .tableCon ol, .coin_trade .tableCon ul li{
   	    border: none;
    }
    #ul1 li:hover{
    	background-color: #0e1c26;
    }
/* END主板市场 */
.coin_trade .tableCon.cur{
    background: #0C1821;
    width: 1298px;
}
.coin_trade{margin:0; width: 100%;}

/* 联系我们 */
.temContact{
    clear: both;
    text-align: center;
    padding-top: 47px;
    background: #0C151C;
    padding-bottom: 60px;
}
.temContact .linkTimeH3 span{
    font-size: 32px;
    font-family: MicrosoftYaHei;
    color: #BBD0D1;
}
.temContact .linkTimeH3 p{
    font-size: 20px;
    font-family: MicrosoftYaHei;
    color: #AABDBE;
    padding-top: 31px;
    width: 1300px;
    margin: 0 auto;
    line-height: 34px;
}


.temContact .linkTime{
	margin-top: 40px;
}
.temContact .linkTime li{
    display: inline-block;
    background: url(/static/front2018/images/powexImg/juxing3x.png) no-repeat center;
    height: 60px;
    background-size: 100% 100%;
    padding: 0 40px;
}
.temContact .linkTime li:nth-child(2){
    margin: 0 50px;
}
.temContact .linkTime li img{
    width: 37px;
    height: 37px;
    vertical-align: -8px;
    margin-right: 10px;
    opacity: 0.8;
}
.temContact .linkTime li em{
    font-size: 21px;
    font-family: MicrosoftYaHei;
    color: #FEFEFE;
    display: inline-block;
    line-height: 60px;
}
/* 资讯列表 */
.newsPowRight{
    width: 1300px;
    margin: 0 auto;
    background: #0E1C26;
    padding: 20px 30px;
    margin-bottom: 20px;
    border-top: 1px dotted rgba(168, 190, 191, 0.3);
    border-bottom: 1px rgba(168, 190, 191, 0.3) dotted;
}
.newsPowRight h2{
    margin-bottom: 40px;
}
.newsPowRight .newsArticles{
	clear: both;
    overflow: hidden;
    margin-top: 20px;
}
.newsPowRight .articleTit{
    float: left;
    width: 285px;
}
.newsPowRight .articleTit:nth-child(2){margin: 0 191px;}

.newsPowRight .articleTit .articleDate{
	color: #A8BEBF;
    border-bottom: 1px dotted rgba(168, 190, 191, 0.3);
    height: 30px;
    line-height: 30px;
    font-size: 16px;}
.newsPowRight .newsRt{
	color: #C6DCDD;
    font-size: 20px;
    font-family: MicrosoftYaHei;
    font-weight: 400;
}
.newsPowRight .getMore{
    float: right;
    color: #1CFFC7;
    font-size: 14px;
    font-family: MicrosoftYaHei;
    padding-top: 4px;
}
.institution{
    margin: 0px auto;
    width: 1300px;
        padding: 0px 30px;
}
.institution>h3{
    color: #C6DCDD;
    margin-bottom: 30px;
    font-size: 20px;
    font-family: MicrosoftYaHei;
}
.institution .institutionUl{
    width: 1200px;
    margin: 0 auto;
    text-align: center;
    clear: both;
    overflow: hidden;
    margin-bottom: 150px;
    margin-top: 81px;
}
.institution .institutionUl li{
    float: left;
    width: 300px;
    margin-bottom: 10px;
    
}
/* 战略合作机构 */
.swiper-container{
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
      background: #0E1C26;
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
.l-nav ul li a, .l-regForm a {
    color: #FFFFFF;
}
.emWidth a,.coin_trade .tableCon ol li span, .coin_trade .emWidth{
    color: #BAD1D2;
}
.coin_trade .emWidth .text-danger .referprice{color: #BAD1D2 !important;}
.l-footer,.CopyRight{background: #041019;}
/* 背景图片 */
/* .bannerli{
    position: absolute;
    right: 29px;
    top: 28px;
} */
.bannerli0{
    position: absolute;
    top: 0px;
    left: -91px;
    height: 319px;
    overflow: hidden;
    opacity: 0.6;
}
.bannerli1{
	position: absolute;
    top: 184px;
    right: 548px;
}
.bannerli2{
    position: absolute;
    top: 133px;
    right: 160px;
}
.bannerli3{
    position: absolute;
    top: 344px;
    right: 148px;
}
.bannerli4{
    position: absolute;
    top: 358px;
    left: 197px;
}
.bannerli5{
    position: absolute;
    left: 18px;
    top: 446px;
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
			<%-- <p class="centercontent1">${requestScope.project.name}</p>
			<p class="centercontent2">${requestScope.project.advantage}</p> --%>
			<div class="bannerbg">
			<div class="bannerli0"><img src="${oss_url}/static/front2018/images/powexImg/panel0.png" alt="" /></div>
				<h3>${requestScope.project.name}</h3>
				<h4>${requestScope.project.advantage}</h4>
				<a href="#">POWEX.PRO→</a>
			</div>
		</div>
		<!-- 背景图片 -->
		<%-- <div class="bannerli"><img src="${oss_url}/static/front2018/images/powexImg/12x.png" alt="" /></div> --%>
		
		<div class="bannerli1"><img src="${oss_url}/static/front2018/images/powexImg/yuansu2x.png" alt="" /></div>
		<div class="bannerli2"><img src="${oss_url}/static/front2018/images/powexImg/yuansu22x.png" alt="" /></div>
		<div class="bannerli3"><img src="${oss_url}/static/front2018/images/powexImg/yuansu32x.png" alt="" /></div>
		<div class="bannerli4"><img src="${oss_url}/static/front2018/images/powexImg/yuansu42x.png" alt="" /></div>
		<div class="bannerli5"><img src="${oss_url}/static/front2018/images/powexImg/22x.png" alt="" /></div>

	</div>
<!-- END banner广告 -->
		<!-- 公告-->
		<div class="newsPowBox">
 			<div class="newsPow">
                <div class="newsPowLeft">
                    <h2>
                     <spring:message code="new.announcement" /> 
                     <a href="/service/ourService.html?id=1" class="getMore"><spring:message code="new.more" />&gt;&gt;</a>
                    </h2>
                    <ul id="ul2">
                      <c:forEach items="${articles[0].value }" var="v" varStatus="n">
                     <c:choose>
                      <c:when test="${pageContext.response.locale eq 'zh_CN' }">            
                          <li>
	                         <a href="/service/article.html?id=${v.fid }">
	                          <span class="articleSpan">【${n.index+1}】&nbsp;${v.ftitle_cn }</span>
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
                </div>
             </div>
      </div>       
<!-- END公告新闻 -->

    <div class="index_con" id="app">
      <div class="title_bck" >
        <div class="coinNav tableConOlA">
            <div style="width:1298px;">
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
						<img src="${oss_url}/static/front2018/images/powexImg/shousuo3x.png" class="icon iconSearch" />
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
                            
                         <%--    <span><spring:message code="nav.index.pricetrend" /></span> --%>
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
                           <%--  <em class="emWidth c_upPrice" id="${v.fid }_plot"></em> --%>
                            <em class="emWidth tradeBtn">
	                            <a href="/trademarket.html?symbol=${v.fid }">
	                            	<%-- <spring:message code="new.tradebtn" /> --%>
	                            	<img src="${oss_url}/static/front2018/images/major.png" style="height: 26px;margin-top: 10px;"/>
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
    <!-- 资讯 -->
    <div class="newsPowRight">
    	<h2>
    	 <span class="newsRt"><spring:message code="new.information" /></span>
	     <a href="/service/ourService.html?id=3" class="getMore"><spring:message code="new.more" />&gt;&gt;</a>
	    </h2>
	    <div class="newsArticles">
	     <c:forEach items="${articles[1].value }" var="v" varStatus="n">
	        <c:choose>
	    	<c:when test="${pageContext.response.locale eq 'zh_CN' }">            
		        <dl class="articleTit">
		         <a href="/service/article.html?id=${v.fid }">
		         	<dt><img src="${v.furl }" height="183" width="285" alt="" /></dt>
		         	<dd class="articleDate"><fmt:formatDate value="${v.fcreateDate }" pattern="yyyy-MM-dd"/></dd>
		         	<dd class="ul2ftitle"><span>[${n.index+1}]&nbsp; ${v.ftitle_cn }</span></dd>
		          </a>
		         </dl>
	          </c:when>
		      <c:otherwise>
			       <dl class="articleTit">
				       <a href="/service/article.html?id=${v.fid }">
					       <dt><img src="${v.furl }" height="183" width="285" alt="" /></dt>
					       <dd class="articleDate"><fmt:formatDate value="${v.fcreateDate }" pattern="yyyy-MM-dd"/></dd>
			         	   <dd class="ul2ftitle"><span>[${n.index+1}]&nbsp; ${v.ftitle }</span></dd>
				       </a>
			       </dl>
		        </c:otherwise>
	    	</c:choose>
        </c:forEach> 
        </div>
    </div>
   
    <!-- 战略合作机构 -->
    <c:if test="${null != cooperateOrgModelList}">
	    <div class="institution">
	    	<h3><spring:message code="new.coinnav.institution"/></h3>
	    	<ul class="institutionUl">
	    		<c:forEach items="${cooperateOrgModelList }" var="v" varStatus="n">
			      <li>
				      <a href="${v.linkUrl}">
				      	<img alt="" src="${v.picUrl }" style="max-height:60px;max-width:302.5px">
				      </a>
			      </li>
			    </c:forEach>
	    	</ul>
	    	  <%--  <div class="swiper-container">
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
			  </div> --%>
	    </div>
    </c:if>
    <!-- 联系我们 -->
    <div class="temContact">
		<h3 class="linkTimeH3">
  			<span>${requestScope.project.name}</span>
  			<p>${requestScope.project.introduce}</p>
  		</h3>
    	<ul class="linkTime">
    		<li>
    		<c:if test="${null != systemconfigMap.Email && '' != systemconfigMap.Email}">
    			<img src="${oss_url}/static/front2018/images/powexImg/email2x.png" style="width: 37px;height: 29px;vertical-align: -4px;">
	    		<em class="titLink">${systemconfigMap.Email}</em>
	    	</c:if>
    		</li>
    		<li>
    		<c:if test="${null != systemconfigMap.WeiXin && '' != systemconfigMap.WeiXin}">
    			<img src="${oss_url}/static/front2018/images/powexImg/weichat2x.png">
	    		<em class="titLink">${systemconfigMap.WeiXin}</em>
	    	</c:if>
    		</li>
    		<li>
    		<c:if test="${null != systemconfigMap.QQ && '' != systemconfigMap.QQ}">
    			<img src="${oss_url}/static/front2018/images/powexImg/panel2x.png">
	    		<em class="titLink">${systemconfigMap.QQ}</em>
	    	</c:if>
    		</li>
    		<%-- <li>
    		<c:if test="${null != systemconfigMap.WeiXin && '' != systemconfigMap.WeiXin}">
    			<p>
	    			<span class="linkTimeImg">
	    				<img src="${oss_url}/static/front2018/images/powexImg/weichat1x.png" width="25" alt="" />
	    			</span>
	    			<span>${systemconfigMap.WeiXin}</span>
    			</p>
    		</c:if>
    		<c:if test="${null != systemconfigMap.QQ && '' != systemconfigMap.QQ}">
    			<p>
	    			<span class="linkTimeImg">
	    				<img src="${oss_url}/static/front2018/images/powexImg/QQ1x.png" width="25" alt="" />
	    			</span>
	    			<span>${systemconfigMap.QQ}</span>
    			</p>
    		</c:if>
    		<c:if test="${null != systemconfigMap.Email && '' != systemconfigMap.Email}">
    			<p>
	    			<span class="linkTimeImg">
	    				<img src="${oss_url}/static/front2018/images/powexImg/email1x.png" width="25" alt="" />
	    			</span>
	    			<span>${systemconfigMap.Email}</span>
    			</p>
    		</c:if>
    		</li> --%>
    	</ul>
    </div>
   
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
    
  </script>

</body>
</html>
