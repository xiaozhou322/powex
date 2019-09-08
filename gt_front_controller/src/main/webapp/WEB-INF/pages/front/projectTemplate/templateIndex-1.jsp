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
    height: 646px;
    clear: both;
    background: url(/static/front2018/images/powexImg/indexbg1.png) no-repeat center;
    background-size: 100% 100%;
    text-align: center;
    position: relative;
}
.bannerPow .bannerCenter{padding-top: 218px;}
.bannerPow .bannerCenter h3{
  	font-size: 72px;
    color: #fff;
    font-family: MicrosoftYaHei;
    font-weight: 400;
}
.bannerPow .bannerCenter h4{
   font-size:32px;
	font-family:MicrosoftYaHei;
	font-weight:400;
	color:#FFFFFF;
	margin: 50px 0 40px 0;
}
.bannerPow .bannerCenter a{
 	clear: both;
    overflow: hidden;
    background: url(/static/front2018/images/powexImg/tiaozhuan1.png) no-repeat center;
    background-size: 100% 100%;
    max-width: 500px;
    height: 57px;
    display: block;
    margin: 0 auto;
    color: #fd7e36;
    font-size: 28px;
    line-height: 57px;
}
/* 公告、资讯 */
.newsPow{
  	width: 1051px;
    margin: 0 auto;
    background: #fff;
    clear: both;
    overflow: hidden;
    zoom: 1;
    padding: 20px 30px;
    height: 184px;
    box-shadow: 0px 2px 9px 0px rgba(70, 90, 111, 0.14);
    text-align: left;
    z-index: 9;
    position: absolute;
    bottom: -136px;
    left: 50%;
    margin-left: -510px;
}
.newsPow .newsBorder{
    width: 1px;
    height: 126px;
    border-left: 1px dashed #666666;
    float: left;
    margin: 0 40px;
    margin-top: 10px;
}
.newsPow .newsPowLeft h2{
color: #31564C;
    font-size: 18px;
    font-family: MicrosoftYaHei;
    font-weight: 400;
}
.newsPow .newsPowLeft h2>a{
	float: right;
    color: #F86512;
    font-size: 14px;}

#ul2 li,#ul3 li{
	height: 40px;
    line-height: 40px;}
#ul2 li a,#ul3 li a{    
	color: #666666;
    font-size: 14px;
}
#ul2 li .ul2Date,#ul3 li .ul2Date{float: right;}
 .ul2ftitle{    
   font-size: 14px;
    line-height: 22px;
    height: 46px;
    color: #787b80;
    margin: 10px 5px 10px 5px;
    overflow: hidden;
    text-overflow: ellipsis;
    display: -webkit-box;
    -webkit-box-orient: vertical;
    -webkit-line-clamp: 2;
}
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
#app .lw_tab .s_name{    margin-left: 8px;}
.tabs li.active{
        color: #31564C;
    border-bottom: 4px solid #31564C;
}
.title_bck .coinNav{
	height: 68px;
}
.iconSearch{
	right: 15px;
}
.search{
    margin-top: 16px;
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
    margin-top: 160px;
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
        border: 1px solid #F86512;
    }
    .indIcon{
    	color:#1FA698;
    }
    .coin_trade .tableCon .tradeUl1{background-color: #FBF6F3;border: none;}
    .coin_trade .tableCon ol li span{color:#497674;}
    
     /*奇数*/
    #ul1 li:nth-child(odd){
        background-color: #FFF;
    }
    /*偶数*/
    #ul1 li:nth-child(even){
        background-color: rgba(251, 246, 243, 0.45);
    }
    .coin_trade .tableCon ol, .coin_trade .tableCon ul li{
   	    border: none;
    }
    #ul1 li:hover{
    background-color: #FBF6F3;
    }
/* END主板市场 */
.coin_trade .tableCon.cur{
    width: 100%;
}
.coin_trade{margin:0; width: 100%;}

/* 联系我们 */
.temContact{
	height: 400px;
    clear: both;
    background: url(/static/front2018/images/powexImg/link1x.png) no-repeat center;
    background-size: 100% 100%;
    text-align: center;
    position: relative;
}
.temContact .linkTime{
    position: absolute;
    left: 59%;
    margin-left: -39px;
    top: 40px;
    background: url(/static/front2018/images/powexImg/juxing1x.png) no-repeat center;
    background-size: 83% 86%;
    background-position: 55px 31px;
    height: 300px;
    width: 365px;
}
.temContact .linkTime li{
    clear: both;
    overflow: hidden;
    text-align: left;
}
.temContact .linkTime li:nth-child(2){margin-left:40px;}
.temContact .linkTime li:nth-child(2) p{
    line-height: 43px;
    color: #2B4D44;
    font-size: 18px;
}
.temContact .linkTime li .linkTimeImg img{vertical-align: -4px;}
.temContact .linkTime li .linkTimeH3 p:nth-child(1){font-size: 40px; color: #2B4D44;}
.temContact .linkTime li .linkTimeH3 p:nth-child(2){    
	font-size: 20px;
    font-family: MicrosoftYaHei;
    color: #2B4D44;}
.temContact .linkTime .titLink{
    background: #345655;
    width: 13px;
    height: 39px;
    float: left;
    margin-top: 11px;
}
.temContact .linkTime h3{
    float: left;
    text-align: left;
    margin-left: 10px;
    margin-bottom: 18px;
}
/* 资讯列表 */
.newsPowRight{
    box-shadow: 0px 2px 9px 0px rgba(70, 90, 111, 0.14);
    width: 1300px;
    margin: 0 auto;
    background: #fff;
    padding: 20px 30px;
    margin-bottom: 50px;
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
.newsPowRight .articleTit a{}
.newsPowRight .articleTit dt{}
.newsPowRight .articleTit .articleDate{
	color: #384048;
    border-bottom: 1px dashed #e9e9e9;
    height: 30px;
    line-height: 30px;}
.newsPowRight .newsRt{
	color: #31564C;
    font-size: 18px;
    font-family: MicrosoftYaHei;
    font-weight: 400;
}
.newsPowRight .getMore{
	float: right;
    color: #F86512;
    padding-top: 4px;
}
.institution{
	padding: 30px 0px;
	margin: 50px auto;
    width: 1300px;
    box-shadow: 0px 2px 9px 0px rgba(70, 90, 111, 0.14);
    margin-bottom: 30px;
}
.institution>h3{
    text-align: center;
    color: #31564C;
    font-size: 24px;
    margin-bottom: 30px;
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
			<%-- <p class="centercontent1">${requestScope.project.name}</p>
			<p class="centercontent2">${requestScope.project.advantage}</p> --%>
			<h3>${requestScope.project.name}</h3>
			<h4>${requestScope.project.advantage}</h4>
			<a href="/lucky/luckyIndex.html">10000ETH抽奖活动火爆进行中→</a>
		</div>
		
		<!-- 公告-->
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
<!-- END资讯新闻 -->
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
						<img src="${oss_url}/static/front2018/images/powexImg/shousuo1x.png" class="icon iconSearch" />
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
    <!-- 联系我们 -->
    <div class="temContact">
    	<ul class="linkTime">
    		<li>
	    		<em class="titLink"></em>
	    		<h3 class="linkTimeH3">
	    			<p>联系我们</p>
	    			<p>${requestScope.project.website}</p>
	    		</h3>
    		</li>
    		<li>
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
    		</li>
    	</ul>
    </div>
    <!-- 战略合作机构 -->
    <c:if test="${null != cooperateOrgModelList}">
	    <div class="institution">
	    	<h3><spring:message code="new.coinnav.institution"/></h3>
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
    
  </script>

</body>
</html>
