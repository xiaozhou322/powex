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
<script type="text/javascript" src="${oss_url}/static/front2018/js/plugin/layer/layer.js?v=20181126"></script>
<script type="text/javascript" src="${oss_url}/static/front2018/js/main/jquery-1.11.2.min.js?v=3"></script>
<script type="text/javascript" src="${oss_url}/static/front2018/js/main/jquery.SuperSlide.2.1.js?v=3"></script>
<script type="text/javascript" src="${oss_url}/static/front2018/js/fluckydraw/index.js?v=20181126"></script>
<style>
.tal hr {
	width: 4px;
	height: 18px;
	float: left;
	background-color: #1fa698;
	margin-top: 4px;
	border-color: #1fa698;
	/* border: solid 10px; */
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

.bd .contentFont_0,.bd .contentFont_2{
    font-size: 30px;
}
.bd .contentFont_1 {
	font-size: 48px;
}

.bd .fbannerscontent_0_0 {
	color: #ff6d56;
	 font-size: 30px;
}
.bd .fbannerscontent_0_1 {
	 color: #ffffff;
	 font-size: 48px;
}
.bd .fbannerscontent_0_2 {
	color: #ffffff;
	 font-size: 30px;
}

.bd .fbannerscontent_1_0 {
	color: #ffe623;
	 font-size: 30px;
}
.bd .fbannerscontent_1_1 {
	    color: #ffffff;
	    font-size: 48px;
}
.bd .fbannerscontent_2_0 {
	color: #5af8fe;
	 font-size: 30px;
}
.bd .fbannerscontent_2_1 {
    color: #ffffff;
    font-size: 48px;
}
.bd .fbannerscontent_3_0{
	color: #34253b;
	 font-size: 30px;
}
.bd .fbannerscontent_3_1{
     color: #46384d;
     font-size: 48px;
}

.centercontent {
	margin-top: 20px;
	letter-spacing: 2px;
	width: 100vw;
}
.centercontent1 {
	     font-size: 48px;
    color: #ffffff;
    letter-spacing: 2px;
    width:100vw;
}
.centercontent2{
    font-size: 32px;
    color: #ffffff;
    letter-spacing: 2px;
    margin-top: 1em;
	width:100vw;
}


.newsTxt ul {
    width: 1100px;
}
.textcontent_0{
    margin-top: 100px;
}
.textcontent_1{
margin-top:130px;
}
.textcontent_2{
margin-top:130px;
}
.textcontent_3{
margin-top:130px;
}
.newsTxt ul li{float:right;}
.newsTxt ul>.newsTxtA{float:right}
.newsTxt ul li a{    
width: 300px;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
    text-indent: 6px;}
</style>
</head>
<body>

	<input id="locale" value="${pageContext.response.locale}" type="hidden"/>
<div>
        <%@include file="comm/header.jsp"%>
</div>
 
<section class="l-content" style="background-color: #f9fbff;">
		<c:choose>
			<c:when test="${requestScope.isprojectUrl}">
				<div class="index_banner" style="height:275px;">
					<div class="hd">
						<ul></ul>
					</div>
					<div class="bd">
						<ul style="height:275px;">
							<li style="height:275px;"><a href=""
								style="background:url(${oss_url}/static/front2018/images/projectbanner.png) no-repeat center;height:275px;">
									<div style="margin-top:50px;">
										<%-- <img  style="width: 110px; float: left; margin-left: 20%;" src="${requestScope.project.logoUrl}" /> --%>
										<c:choose>
											<c:when test="${pageContext.response.locale eq 'zh_CN' }">
												<p class="centercontent1">${requestScope.project.name}</p>
												<p class="centercontent2">
													${requestScope.project.advantage}</p>
											</c:when>
											<c:otherwise>
												<p class="centercontent1">${requestScope.project.name}</p>
												<p class="centercontent2">
													${requestScope.project.advantage}</p>

											</c:otherwise>
										</c:choose>
									</div>
							</a></li>
						</ul>
					</div>
				</div>
			</c:when>
			<c:otherwise>
				<div class="index_banner">
					<div class="hd">
						<ul></ul>
					</div>
					<div class="bd">
						<ul>
							<c:forEach items="${requestScope.constant['fbanners']}" var="v" varStatus="vs">

								<li><a href="${v.furl }"
									style="background:url(${v.fimgUrl }) no-repeat center;"> <c:choose>
											<c:when test="${pageContext.response.locale eq 'zh_CN' }">
												<c:set value="${ fn:split(v.fcontent, '#') }"
													var="fcontents" />
											</c:when>
											<c:otherwise>
												<c:set value="${ fn:split(v.fcontent_en, '#') }"
													var="fcontents" />

											</c:otherwise>
											
										</c:choose>
										<div class="textcontent_${vs.index}">
											<c:forEach items="${ fcontents }" var="fcontent" varStatus="fc">
												<p class="centercontent fbannerscontent_${vs.index}_${fc.index}">${fcontent}</p>
											</c:forEach>
										</div>
								</a></li>
							</c:forEach>
						</ul>
					</div>

					<script>
						$(".index_banner").slide({
							mainCell : ".bd ul",
							effect : "leftLoop",
							autoPlay : true,
							autoPage : "<li></li>",
							titCell : ".hd ul",
							prevCell : ".prev",
							nextCell : ".next",
							interTime : 300000,
							trigger : "click",
							delayTime : 700
						});
					</script>
				</div>
			</c:otherwise>

		</c:choose>
		<div class="newsTxt">     
     <ul>
     <a href="/service/ourService.html?id=1" class="newsTxtA">
     <spring:message code="new.more" />&nbsp;></a>
	 	<%-- <c:forEach items="${requestScope.constant['news']}" var="v"> --%>
	 	<c:forEach items="${articles[0].value}" var="v">
              <c:choose>
              <c:when test="${pageContext.response.locale eq 'zh_CN' }">                
              <li><a href="${v.url }" target="_blank">${v.ftitle_cn }</a><span style="color: #11B2B8;">/</span></li>
               </c:when>
              <c:otherwise>
              <li><a href="${v.url }" target="_blank">${v.ftitle }</a><span style="color: #11B2B8;">/</span></li>
              </c:otherwise>
            </c:choose>
          </c:forEach>
          
     </ul>
 </div>

 <c:if test="${!requestScope.isprojectUrl}">
 <div class="index_drawImg">
 	<a href="/lucky/luckyIndex.html"><spring:message code="nav.index.activity" /></a>
 </div>
 </c:if>
 
 
 
 
    <div class="index_con" id="app" style="width: 1200px;margin: 0 auto;margin-top: 2em;">

       <div class="tal"><hr/><span><spring:message code="nav.index.main.moard" /></span></div>
           <div class="title_bck">
        <div class="coin_trade">
            <div style="width:1200px;">
                <div class="tableCon cur">
                    <ol>
                        <li class="clear">
                            <span><spring:message code="nav.index.coin" /></span>
                            <span class="span_02"><spring:message code="nav.index.latpri" /></span>
                            <span><spring:message code="nav.index.24volume" /></span>
                            <span><spring:message code="market.24change" /></span>
                            
                            <span><spring:message code="nav.index.24high" /></span>
                            <span><spring:message code="nav.index.24low" /></span>
                            
                         <%--    <span><spring:message code="nav.index.pricetrend" /></span> --%>
                            <span>
                                <svg class="icon" aria-hidden="true">
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
   
       <div class="tal"><hr></hr><span><spring:message code="nav.index.innovation.board" /></span></div>
           <div class="title_bck" >
        <div class="coinNav">
            <div style="width:1200px;">
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
                        <span>Search</span>
                        <input class="text searchInput" type="text" value='' placeholder="<spring:message code="new.serachval" />" id="search">
                        <!-- <input class="submit" type="submit" value="搜索"> -->
                        <svg class="icon iconSearch" aria-hidden="true">
                            <use xlink:href="#icon-sousuo"></use>
                        </svg>
                    </div>
                </div>
            </div>
        </div>
        <div class="coin_trade">
            <div style="width:1200px;">
                <div class="tableCon cur">
                    <ol>
                        <li class="clear">
                            <span><spring:message code="nav.index.coin" /></span>
                            <span class="span_02"><spring:message code="nav.index.latpri" /></span>
                            <span><spring:message code="nav.index.24volume" /></span>
                            <span><spring:message code="market.24change" /></span>
                            
                            <span><spring:message code="nav.index.24high" /></span>
                            <span><spring:message code="nav.index.24low" /></span>
                            
                         <%--    <span><spring:message code="nav.index.pricetrend" /></span> --%>
                            <span>
                                <svg class="icon" aria-hidden="true">
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
                    <%--   <template v-for="vv in result.fMap">
                          <template v-for="v in vv.value">
                        <li v-bind:class="'clear ' +vv.key.fid +'_market_list market-con market_show markent_s'" v-bind:data-mark='vv.key.fid' v-bind:data-key='v.fvirtualcointypeByFvirtualcointype2.fShortName'>
                            <em class="emWidth"><a v-bind:href="'/trademarket.html?symbol='+v.fid "><i></i>{{v.fvirtualcointypeByFvirtualcointype2.fShortName }}/{{vv.key.fShortName }}</a></em>
                            <em class="emWidth em_2"><span v-bind:id="v.fid+'_price_1'">--</span></em>
                            <em class="emWidth"><span v-bind:id="v.fid +'_total_1'">--</span></em>
                            <em class="emWidth" v-bind:id="v.fid+'_rose_1'">--</em>
                            <em class="emWidth c_upPrice" v-bind:id="v.fid+'_plot'"></em>
                            <em class="emWidth tradeBtn"><a v-bind:href="'/trademarket.html?symbol='+v.fid "><spring:message code="new.tradebtn" /></a></em>
                        </li>  
                        </template>
                        </template>     --%>                 

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
            <div class="news">
                <div class="newsCon mg clear">
                    <div class="newsLeft fl">
                        

                        <h2><spring:message code="new.announcement" /></h2>
                        <ul id="ul2">
                            <c:forEach items="${articles[0].value }" var="v" varStatus="n">
                             <c:choose>
                        <c:when test="${pageContext.response.locale eq 'zh_CN' }">            
                            <li><a href="/service/article.html?id=${v.fid }">[<fmt:formatDate value="${v.fcreateDate }" pattern="yyyy-MM-dd"/>] ${v.ftitle_cn }</a></li>
                         </c:when>
                        <c:otherwise>
                        <li><a href="/service/article.html?id=${v.fid }">[<fmt:formatDate value="${v.fcreateDate }" pattern="yyyy-MM-dd"/>] ${v.ftitle }</a></li>
                            </c:otherwise>
                      </c:choose>
                            </c:forEach> 
                        </ul>
                        <a href="/service/ourService.html?id=1" class="getMore"><spring:message code="new.more" />&gt;&gt;</a>
                    </div>
                    <div class="news_timeBar">
                        <span>2018</span>
                    </div>                    
                    <div class="newsLeft newsRight fr">
                        <h2><spring:message code="new.information" /></h2>
                        <ul id="ul3">
                   
                        
                            <c:forEach items="${articles[1].value }" var="v" varStatus="n">
                            <c:choose>
                        <c:when test="${pageContext.response.locale eq 'zh_CN' }">            
                            <li><a href="/service/article.html?id=${v.fid }">[<fmt:formatDate value="${v.fcreateDate }" pattern="yyyy-MM-dd"/>] ${v.ftitle_cn }</a></li>
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
<script type="text/javascript" src="${oss_url}/static/front2018/js/index/index.js?v=20001234332"></script>
<script type="text/javascript" src="${oss_url}/static/front/js/plugin/jquery.flot.js?v=20171026105813.js?v=20181126201750"></script>
<script type="text/javascript" src="${oss_url}/static/front2018/js/index/indexSearch.js?v=20001234233"></script>
<script type="text/javascript" src="${oss_url}/static/front2018/js/jquery.rotate.min.js?v=20181126"></script>
<script type="text/javascript" src="${oss_url}/static/front2018/js/main/scroll.js?v=20181126"></script>
<script type="text/javascript">
    $(document).ready(function(){
        var cookie = $.cookie('close');
        if(typeof(cookie) == "undefined"){
            $('.top').css('display','block');
        }else{
           $('.top').css('display','none')
        }
    });
    $(".luckyClose").click(function(){
  	  $(".lucky").hide();
  	 });
    $('.close').click(function(event) {
       $('.top').remove();
       $.cookie('close', 'yes');
    });
</script>

</body>
</html>
