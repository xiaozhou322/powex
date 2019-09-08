<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ include file="comm/include.inc.jsp"%>

<!doctype html>
<html>
<head>
<%@ include file="comm/basepath.jsp"%>
<meta http-equiv = "X-UA-Compatible" content = "IE=edge,chrome=1" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="comm/link.inc.jsp"%>
<link href="${oss_url}/static/front2018/css/lucky.css?v=20181126" rel="stylesheet" type="text/css" />
<link href="${oss_url}/static/front2018/css/exchange/swiper.min.css?v=20190301162530" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${oss_url}/static/front2018/js/fluckydraw/easing.js?v=20181126201750"></script>
<script type="text/javascript" src="${oss_url}/static/front2018/js/plugin/layer/layer.js?v=20181126"></script>
<script type="text/javascript" src="${oss_url}/static/front2018/js/main/jquery-1.11.2.min.js?v=3"></script>
<script type="text/javascript" src="${oss_url}/static/front2018/js/main/jquery.SuperSlide.2.1.js?v=3"></script>
<script type="text/javascript" src="${oss_url}/static/front2018/js/fluckydraw/index.js?v=20181126"></script>
<script type="text/javascript" src="${oss_url}/static/front2018/js/comm/swiper.min.js?v=20190301162530"></script>
<style>
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
	height: 550px;
    clear: both;
    overflow: hidden;
    background: url(${oss_url}/static/front2018/images/powexImg/banner.png) no-repeat center;
    background-size: 100% 100%;
    text-align: center;
}
.bannerPow .bannerPowTit h3{
	font-size: 72px;
    font-family: MicrosoftYaHei;
    font-weight: 400;
    color: #fff;
    margin-top: 50px;
}
.bannerPow .bannerPowTit h4{
    font-size: 36px;
    font-family: MicrosoftYaHei;
    font-weight: 400;
    color: #fff;
    margin-top: 20px;
	
}
.bannerPow .bannerPowTit ul{
    clear: both;
	overflow: hidden;
	text-align: center;
	margin: 0 auto;
    margin-top: 0px;
	width: 548px;
	margin-top: 45px;
	height: 71px;
}
.bannerPow .bannerPowTit ul>li{
	float: left;
}
.bannerPow .bannerPowTit ul{
 	background: url(${oss_url}/static/front2018/images/exchange/bgPowLi.png) no-repeat center;
}
.bannerPow .bannerPowTit ul .PowLi{
    background: url(${oss_url}/static/front2018/images/exchange/num2.png) top center repeat-y;
    background-position-y: top;
	margin-right: 15px;
	width: 65px;
	height: 98px;
	margin-top: 10px;
	transform: scale(0.5);
	transform-origin: top center;
	-moz-transform: scale(0.5);
}
.bannerPow .bannerPowTit ul .PowLi:last-child{
    margin-right: 0px;
}
.bannerPow .bannerPowTit ul .titlist{
	font-size: 18px;
    font-family: MicrosoftYaHei;
    font-weight: 400;
    color: #3986AE;
    float: right;
    line-height: 90px;
}


.bannerPowList{
	clear: both;
    overflow: hidden;
    margin: 0 auto;
    width: 1300px;
    margin-top: 50px;}
.bannerPowList li{width: 320px;float: left;}
.bannerPowList li a{display:block;}
.bannerPowList li:last-child{margin-right: 0px;}
.bannerPowList li a>img{width: 300px;}
/* 公告、资讯 */
.newsPow{
  	width: 1300px;
    margin: 0 auto;
    background: #fff;
    clear: both;
    overflow: hidden;
    zoom: 1;
    padding: 20px 30px;
    height: 184px;
    margin-top: 30px;
    box-shadow: 0px 2px 9px 0px rgba(70, 90, 111, 0.14);
    
}
.newsPow .newsPowLeft{
    float: left;
    width: 579px;
}
.newsPow .newsBorder{
    width: 1px;
    height: 126px;
    border-left: 1px dashed #cccccc78;
    float: left;
    margin: 0 40px;
    margin-top: 10px;
}
.newsPow .newsPowLeft h2{
	color: #333333;
    font-size: 18px;
    font-family: MicrosoftYaHei;
    font-weight: 400;
}
.newsPow .newsPowLeft h2>a{
	float: right;
    color: #1FA698;
    font-size: 14px;}

#ul2 li,#ul3 li{
	height: 40px;
    line-height: 40px;}
#ul2 li a,#ul3 li a{    
	color: #666666;
    font-size: 14px;
}
#ul2 li .ul2Date,#ul3 li .ul2Date{float: right;}
#ul2 li .ul2ftitle,#ul3 li .ul2ftitle{    
	overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
    width: 470px;
    display: inline-block;
}
.coin_trade .tableCon ol li .span_02,.coin_trade  .em_2{width:250px;}
/* END公告、资讯 */
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
	background: #EEF4FF;
    font-size: 16px;
    font-family: MicrosoftYaHei;
}
.title_bck{
	box-shadow: 0px 2px 9px 0px rgba(70, 90, 111, 0.14);
}
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
        border: 1px solid #1FA698;
    }
    .indIcon{
    	color:#1FA698;
    }
    .tradeUl1{background-color: #F9FBFF;    font-size: 16px;}
    
     /*奇数*/
    #ul1 li:nth-child(odd){
        background-color: #fff;
    }
    /*偶数*/
    #ul1 li:nth-child(even){
        background-color: #F9FBFF;
    }
    #ul1 li:hover{
    background-color: #F0F5FF;
    }
/* END主板市场 */
.coin_trade .tableCon.cur{
    width: 100%;
}
.coin_trade{margin:0; width: 100%;}
.coinNav,.coin_trade .tableCon ol, .coin_trade .tableCon ul li{border-bottom:none;}
/* swiper切换 */
.swiperCen{    position: relative;
    margin: 0 auto;
    width: 1300px;
    margin-top: 20px;}
.swiper-container{width: 1200px; }
.swiper-slide {
      text-align: center;
      font-size: 18px;
      /* Center slide text vertically */
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
  .swiper-button-prev{ background: url(${oss_url}/static/front2018/images/powexImg/morelt.png) no-repeat center;width: 35px; height: 35px; background-size: contain;}
  .swiper-button-next{ background: url(${oss_url}/static/front2018/images/powexImg/moreRt.png) no-repeat center;width: 35px; height: 35px; background-size: contain;}
.swiper-pagination-bullet-active{background: #a3b2bb;}

.bannerPowLiImg{ margin: 15px;
    -webkit-transition: margin 0.5s ease-out;
    -moz-transition: margin 0.5s ease-out;
    -o-transition: margin 0.5s ease-out; }
.bannerPowLiImg:hover{margin-top: 2px;}
/* 结束swiper切换 */
/* 媒体查询 */
@media screen and (min-width: 1300px) {
  .bannerPowList li a>img {
    width: 300px;
	}
  .bannerPowList li {
    float: left;
	}
	.bannerPowList {
    width: 1430px;
	}
	
	.bannerPowList li {
    	width: 350px;
	}
}
</style>
</head>
<body>

<input id="locale" value="${pageContext.response.locale}" type="hidden"/>
<div>
   <%@include file="comm/main_header.jsp"%>
</div>
 
<section class="l-content" style="background-color: #f9fbff;">
<!-- banner广告 -->
	<div class="bannerPow">
		<div class="bannerPowTit">
			<h3>POWEX.PRO</h3>
			<h4>全新数字交易平台，运行安全可靠、服务专业高效</h4>
			
		</div>
		<div class="swiperCen">
		<div class="swiper-container">
		    <div class="swiper-wrapper">
		    <c:forEach items="${requestScope.constant['fbanners']}" var="v" varStatus="vs">
			 <div class="swiper-slide">
				<a href="${v.furl }">
					<img src="${v.fimgUrl }" class="bannerPowLiImg"/>
				</a>
			  </div>
		    </c:forEach>
		    </div>
		   
		    <!-- Add Pagination -->
		    <div class="swiper-pagination"></div>
	  	</div>
	  	 <div class="swiper-button-next"></div>
    	 <div class="swiper-button-prev"></div>
  	  </div>
	</div>
<!-- END banner广告 -->
<!-- 资讯、公告-->
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
                    </div>
                    <div class="newsBorder"></div>     
                    <!-- 行业资讯 -->               
                    <div class="newsPowLeft">
                        <h2>
	                        <spring:message code="new.information" />
	                        <a href="/service/ourService.html?id=3" class="getMore"><spring:message code="new.more" />&gt;&gt;</a>
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
                        
                    </div> 
           </div>
<!-- END资讯新闻 -->
 <c:if test="${!requestScope.isprojectUrl}">
	 <div class="index_drawImg">
	 	<a href="/lucky/luckyIndex.html"><spring:message code="nav.index.activity" /></a>
	 </div>
 </c:if>
    <div class="index_con" id="app" style="width: 1300px;margin: 0 auto;margin-top: 2em;">
    <!-- 主板市场 -->
       <div class="mainBoard">
	       	<hr>
		       <spring:message code="nav.index.main.moard" />
		    <hr>
       </div>
       <div class="title_bck">
        <div class="coin_trade">
            <div style="width:1300px;">
                <div class="tableCon cur">
                    <ol class="tableConOlA">
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
                    <ul id="ul">
                     <c:forEach var="vv" items="${fMap }" varStatus="vn">
                        <c:forEach items="${vv.value }" var="v" varStatus="vs">
                         <c:if test="${vv.key.fid==4 }">
                        <li class="clear   market_show markent_s" data-mark="${vv.key.fid }" data-key="${v.fvirtualcointypeByFvirtualcointype2.fShortName }">
                          
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
                        </c:if>                       
                          </c:forEach>
                      </c:forEach> 
                   </ul>
                </div>
            </div>
        </div>
        </div>
    <!-- END主板市场 -->
       <div class="mainBoard">
	       	<hr>
		       <spring:message code="nav.index.innovation.board" />
		    <hr>
       </div>
           <div class="title_bck" >
        <div class="coinNav tableConOlA">
            <div style="width:1300px;">
                <div class="coin_tab clear">
                    <ul class="clear tabs fl">
                        <li class="fl active lw_tab" id="0_market" data-key="0" data-max="3"><span class="s_name"><spring:message code="nav.index.allmarket" /></span></li>                        
                         <li class="fl lw_tab" id="1_market" data-key="1" data-max="3"><i class="s_icon"><img src="${oss_url}/static/front2018/images/BTC.png" width="25" alt="" /></i><span class="s_name">BTC</span></li>
                        <li class="fl lw_tab" id="3_market" data-key="3" data-max="3"><i class="s_icon"><img src="${oss_url}/static/front2018/images/ETH.png" width="25" alt="" /></i><span class="s_name">ETH</span></li>

                        <!-- <c:forEach var="vv" items="${fMap }" varStatus="vn">
                         <li class="fl lw_tab " id="${vv.key.fid }_market" data-key="${vv.key.fid }" data-max="${fn:length(fMap) }"><i class="s_icon"><img src="${oss_url}/static/front2018/images/ETH.png" width="25" alt="" /></i><span class="s_name">${vv.key.fShortName }市场</span></li>
                        </c:forEach> -->
                    </ul>
                    <div class="search fr">
                        <!-- <span>Search</span> -->
                        <input class="text searchInput" type="text" value='' <%-- placeholder="<spring:message code="new.serachval" />" --%> id="search">
                        <!-- <input class="submit" type="submit" value="搜索"> -->
                        <svg class="icon iconSearch" aria-hidden="true">
                            <use xlink:href="#icon-sousuo"></use>
                        </svg>
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
                        <c:if test="${vv.key.fid!=4 }">
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
                        </c:if>                       
                          </c:forEach>
                      </c:forEach>
                    </ul>
                </div>
            </div>
        </div>
        </div>
        <div class="index_main">
        <c:if test="${!requestScope.isprojectUrl}">
			<div class="gbIntroduce">
                <div class="mg clear">
                    <dl class="fl">
                        <dt><img src="${oss_url}/static/front2018/images/sec.png" width="30" alt="" /></dt>
                        <dd>
                            <h3><spring:message code="new.High-end" /></h3>
                            <p><spring:message code="new.High-endCon" /></p>
                        </dd>
                    </dl>                    
                    <dl class="fl">
                        <dt><img src="${oss_url}/static/front2018/images/fast.png" width="30" alt="" /></dt>
                        <dd>
                            <h3><spring:message code="new.FastConvenient" /></h3>
                            <p><spring:message code="new.FastCon" /></p>
                        </dd>
                    </dl>                    
                    <dl class="fl">
                        <dt><img src="${oss_url}/static/front2018/images/st.png" width="30" alt="" /></dt>
                        <dd>
                            <h3><spring:message code="new.SafeProfessional" /></h3>
                            <p><spring:message code="new.SafeCon" /></p>
                        </dd>
                    </dl>                    
                    <dl class="fl">
                        <dt><img src="${oss_url}/static/front2018/images/ser.png" width="30" alt="" /></dt>
                        <dd>
                            <h3><spring:message code="new.ServiceSincere" /></h3>
                            <p><spring:message code="new.ServiceCon" /></p>
                        </dd>
                    </dl>
                </div>                  
            </div>
         </c:if>
           
        </div>
    </div>
    <!-- 抽奖活动 -->
<div style="display: none;">
 <c:if test="${sessionScope.login_user != null }">  	  
	   <c:choose>
	   <c:when test="${!sessionScope.login_user.fhasRealValidate||!sessionScope.login_user.fhasImgValidate}">
	 <a href="/user/realCertification.html">KYC1和KYC2</a>
	   </c:when>
	    <c:when test="${!sessionScope.login_user.fgoogleBind}">
	   <a href="/user/security.html?tab=3">谷歌</a>
	   </c:when>
	   <c:otherwise>
	   
	    <a href="/financial/index.html">充值</a>
	   </c:otherwise>
	   
	   </c:choose>
	   
	   </c:if>         
</div>
<input id="activity_id" type="hidden"  value="1"/>
<div class="lucky">
	<div class="g-lottery-box" style="z-index: 9999;">
		<span class="luckyClose"><img src="${oss_url}/static/front2018/images/exchange/close0x.png"></span>
		<div class="g-lottery-img">
			<a class="playbtn" href="javascript:;" title="<spring:message code="json.luck.start" />"></a>
		</div>
		<a href="/luckydraw/luckydrawIndex.html?lang=zh_CN" class="playLook"><spring:message code="json.luck.seedetails" /></a>
		
	</div>
</div>
<!-- 认证获取更多-->
<div class="realfixed ngrealModule">
	<ul style="line-height: 30px;">
		<h3><img src="${oss_url}/static/front2018/images/close@2x.png" class="realclose"></h3>
		<li>
			<p class="powModules"></p>
			<p><spring:message code="json.luck.platform.draw" /></p>
			<a href="" class="googleBtn lotterKYCBtn powModulesBtn" style="margin-top: 39px;"></a>
		</li>
	</ul>
</div>
<!-- 弹出窗 -->
  <div class="comModule">
  	<ul>
  		<li>
  			<img src="${oss_url}/static/front2018/images/exchange/Winning.png" class="Winning">
  		</li>
  		<li>
  			<h3 class="WinningTit"><spring:message code="json.luck.not.winning" /></h3>
  		</li>
  		<li>
  			<button class="asdbtn"><spring:message code="json.luck.in.draw" /></button>
  		</li>
  	</ul>
  		<em class="close2x"><img src="${oss_url}/static/front2018/images/exchange/close2x.png" ></em>
  </div>
  <!-- 蒙层 -->
  <div class="fixedModule ngrealModule"></div>
</section>

<%@include file="comm/footer.jsp" %>
<input type="hidden" id="errormsg" value= />
<input type="hidden" id="usertotal" value='${usertotal}' />
<script type="text/javascript" src="${oss_url}/static/front2018/js/index/index.js?v=20001234372"></script>
<script type="text/javascript" src="${oss_url}/static/front/js/plugin/jquery.flot.js?v=20171026105813.js?v=20181126201750"></script>
<script type="text/javascript" src="${oss_url}/static/front2018/js/index/indexSearch.js?v=20001234233"></script>
<script type="text/javascript" src="${oss_url}/static/front2018/js/jquery.rotate.min.js?v=20181126"></script>
<script type="text/javascript" src="${oss_url}/static/front2018/js/main/scroll.js?v=20181126"></script>
<script type="text/javascript">
var u = 100;
var n = 1;

var isBegin  = false; //标识能否开始抽奖
    $(document).ready(function(){
    	
        var cookie = $.cookie('close');
        if(typeof(cookie) == "undefined"){
            $('.top').css('display','block');
        }else{
           $('.top').css('display','none')
        }
        
    	/* 抽奖活动弹出 */
	$(".lucky").hide();
	  $(window).scroll(function(){
		  scroll();
	    });
 });
    
	/* 注册人数 */
    function run(){
  		n++;
  		
  		isBegin = true ;
  	}  
	
	/* 默认前面补0 */
    function PrefixZero(num, n) {
        return (Array(n).join(0) + num).slice(-n);
    }
    
    /* 摇奖弹窗关闭 */
    $(".luckyClose").click(function(){
  	  	$(".lucky").hide();
  	  	$(window).unbind('scroll')
  	 });
    $('.close').click(function(event) {
       $('.top').remove();
       $.cookie('close', 'yes');
    });
    function scroll(){
   	 var scrollTop = document.documentElement.scrollTop || document.body.scrollTop;
   	  if(scrollTop >= 400){
   			$(".lucky").show();
   		} else{
   			$(".lucky").hide(); // 如果小于等于400 淡出
   		}
    }
    var swiper = new Swiper('.swiper-container', {
        slidesPerView: 3,
        spaceBetween : 20,
        pagination: {
          el: '.swiper-pagination',
          clickable: true,
        }, 
        navigation: {
            nextEl: '.swiper-button-next',
            prevEl: '.swiper-button-prev',
          },
          breakpoints: { 
        	    //当宽度小于等于1300
        	    1300: {
        	      slidesPerView: 3,
        	      spaceBetween: 0
        	    },
          }
      });
</script>

</body>
</html>
