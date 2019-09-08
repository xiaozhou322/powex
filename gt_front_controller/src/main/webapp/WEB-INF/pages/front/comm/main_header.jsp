<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@include file="include.inc.jsp"%>
<style>
.nav .open>a, .nav .open>a:hover, .nav .open>a:focus {
    background-color: transparent !important;
    border-color: #c83935;
}
.codeIco{
	position:relative;
}
#qrcodeder{
	display:none;
    position: absolute;
    left: -26px;
    z-index: 9;
   	background: #fff;
    padding: 6px 6px 2px 6px;
    box-shadow: 0px 2px 9px 0px rgba(70, 90, 111, 0.14);
    }
#qrcodederico{
    position: absolute;
    width: 50px;
    height: 50px;
    background: #fff url(/static/front2018/images/saologo.png) no-repeat center;
    background-size: 50%;
    left: 40%;
    margin: 0 auto;
    text-align: center;
    margin-left: -9px;
    top: 40%;
    margin-top: -12px;
}
</style>

<header class="l-header header_in">
    <div class="clear">
    
    
         <h1 class="l-logo fl"><a href="/"><img src="${oss_url}/static/front2018/images/logo.png" alt="" /></a></h1>
         <nav class="l-nav fl">
             <ul class="fl">
                <li class="fl"><a href="/"><spring:message code="nav.top.home" /></a></li>
                <li class="fl"><a href="/trademarket.html"><spring:message code="nav.top.trade" /></a></li>
				<li class="fl"><a href="/advertisement/buyList.html"><spring:message code="nav.top.otc" /></a></li>
				<li class="fl"><a href="/introl/introlIndex.html"><spring:message code="nav.top.inviteFriends" /></a></li>
				<li class="fl"><a href="/about/index.html?id=57"><spring:message code="nav.top.help" /></a></li>
				
				<%-- <c:if test="${pageContext.response.locale eq 'zh_CN' }">
                 <li class="fl"><a href="/lucky/luckyIndex.html"><spring:message code="10000ETH" /> </a></li>
                 <li class="fl"><a href="/projects/projectsIndex.html"><spring:message code="项目账户" /> </a></li>
                 <li class="fl"><a href="/community/communityIndex.html"><spring:message code="社群账户" /> </a></li>
                 <li class="fl"><a href="/luckydraw/luckydrawIndex.html"><spring:message code="幸运大转盘" /> </a></li>
                 </c:if> --%>
             </ul>
        <div class="clear"></div>
         </nav>

           
         <c:if test="${sessionScope.login_user == null }">  
          <div class="l-loginBtn fr"> 
          	   <span class="fl codeIco">
	          	   <a href="/download/index.html" class="download" style="color: #fff;font-size: 16px;line-height: 40px;margin: 0 22px;">
	          	   <spring:message code="nav.top.appdownload" /> </a>
          	       <div id="qrcodeder">
				    	<div id="qrcodederico"></div>
				 	</div>
          	   </span>
               <span class="fl"><a href="/platform/platformIndex.html" class="platform" style="color: #fff;font-size: 16px;line-height: 40px;margin: 0 22px;"><spring:message code="nav.top.platform" /> </a></span>                   
  
              <div class="fl l-regForm">
                 <span class="fl">
                     <a href="/user/login.html"><spring:message code="nav.top.login" /></a>
                     |
                     <a href="/user/register.html" class="current"><spring:message code="nav.top.register" /></a>
                 </span>
            </div>
            <div class="fl">
                <dl class="l-lang  fl">
                  <dt class="lang"><a href="javascript:;"><img class="fl" src="${oss_url}/static/front2018/images/<spring:message code="language.title" />.png" width="24" alt="" /><spring:message code="language.desc" /></a></dt>
                  <dd class="lanague clear">
                      <a onclick="langs('lang=en_US')" href="javascript:;"><img class="fl" src="${oss_url}/static/front2018/images/en.png" width="24" alt="" /><spring:message code="language.en" /></a>
                      <a onclick="langs('lang=zh_CN')" href="javascript:;"><img class="fl" src="${oss_url}/static/front2018/images/cn.png" width="24" alt="" /><spring:message code="language.cn" /></a>
                  </dd>
                </dl>
             </div>
             <div class="clear"></div>
         </div>
         </c:if>
         <c:if test="${sessionScope.login_user != null }">      
         <div class="fr">
         	  <span class="fl codeIco">
	         	  <a href="/download/index.html" class="download" style="color: #fff;font-size: 16px;line-height: 40px;margin: 0 22px;">
	         	  <spring:message code="nav.top.appdownload" /> </a>
	         	  <div id="qrcodeder">
			    	<div id="qrcodederico"></div>
			 	  </div>
         	  </span>
              <span class="fl"><a href="/platform/platformIndex.html" class="platform" style="color: #fff;font-size: 16px;line-height: 40px;margin: 0 22px;"><spring:message code="nav.top.platform" /> </a></span>                   

              <div class="fl l-regForm">
                 <span class="fl">
                     <svg class="icon" aria-hidden="true">
                       <use xlink:href="#icon-touxiang"></use>
                     </svg>
                     <em class='userName'>${login_user.fnickName}</em>
                 </span>
                 <div class="useInformation">
                     <ul>
                         <li>
                         <a href="javascript:void(0);">
                         <span style="margin: 0 10px 0 -26px;">
                                    UID
                                </span>
                            ${login_user.fid}
                             </a>
                        </li> 
                        <c:if test="${sessionScope.login_user.fisprojecter && !requestScope.isprojectUrl}">
                          <li>
                            <a href="/project/">
                                <svg class="icon icon_s" aria-hidden="true">
                                    <use xlink:href="#icon-jiaoyijilu"></use>
                                </svg><spring:message code="json.coin.issuecoin" />
                            </a>
                        </li>
                        
                        </c:if>
                        <li>
                            <a href="/introl/mydivide.html?type=1">
                                <svg class="icon icon_s" aria-hidden="true">
                                    <use xlink:href="#icon-jiaoyijilu"></use>
                                </svg><spring:message code="user.member.benefits" />
                            </a>
                        </li>
                         <li>
                            <a href="/financial/index.html">
                                <svg class="icon icon_s" aria-hidden="true">
                                    <use xlink:href="#icon-zichanxinxi"></use>
                                </svg><spring:message code="newuser.financial.asset" />
                            </a>
                        </li>
                         <li>
                            <a href="/trade/entrust.html">
                                <svg class="icon icon_s" aria-hidden="true">
                                    <use xlink:href="#icon-jiaoyijilu"></use>
                                </svg><spring:message code="new.mytrade" />
                            </a>
                        </li>
                         <li>
                            <a href="/user/security.html">
                                <svg class="icon icon_s" aria-hidden="true">
                                    <use xlink:href="#icon-anquan"></use>
                                </svg><spring:message code="newuser.security.settings" />
                            </a>
                        </li>
                         <li>
                            <a href="/user/realCertification.html">
                                <svg class="icon icon_s" aria-hidden="true">
                                    <use xlink:href="#icon-idcard"></use>
                                </svg><spring:message code="security.ideaut" />
                            </a>
                        </li>
                         <li>
                            <a href="/user/message.html">
                                <svg class="icon icon_s" aria-hidden="true">
                                    <use xlink:href="#icon-youjian"></use>
                                </svg><spring:message code="new.mymsg" />
                            </a>
                        </li>
                         <li>
                            <a href="/about/index.html?id=88">
                                <svg class="icon icon_s" aria-hidden="true">
                                    <use xlink:href="#icon-help1"></use>
                                </svg><spring:message code="nav.top.help" />
                            </a>
                        </li>
                         <li>
                            <a href="/user/logout.html">
                                <svg class="icon icon_s" aria-hidden="true">
                                    <use xlink:href="#icon-logout"></use>
                                </svg><spring:message code="nav.top.logout" />
                            </a>
                        </li>
                     </ul>
                 </div>
            </div>
            <div class="fl">
                <dl class="l-lang  fl">
                  <dt class="lang"><a href="javascript:;"><img class="fl" src="${oss_url}/static/front2018/images/<spring:message code="language.title" />.png" width="24" alt="" /><spring:message code="language.desc" /></a></dt>
                  <dd class="lanague clear">
                      <a onclick="langs('lang=en_US')" href="javascript:;"><img class="fl" src="${oss_url}/static/front2018/images/en.png" width="24" alt="" /><spring:message code="language.en" /></a>
                      <a onclick="langs('lang=zh_CN')" href="javascript:;"><img class="fl" src="${oss_url}/static/front2018/images/cn.png" width="24" alt="" /><spring:message code="language.cn" /></a>
                  </dd>
                </dl>
             </div>
             <div class="clear"></div>
         </div>
         </c:if>     
    </div>

</header>
<script type="text/javascript" src="${oss_url}/static/front2018/js/plugin/jquery.qrcode.min.js?v=20181126201750"></script>


<script type="text/javascript">
    function langs(lang)
    {
      var Url = window.location.href;
      if(Url.indexOf('?') == -1){
        Url = Url + "?"+ lang;
        window.location.href = Url;
        return ;
      }else{
        if(Url.indexOf('lang') == -1){
          Url = Url + "&" + lang;
          window.location.href = Url;
          return ;
        }else{
          if(Url.indexOf(lang) == -1){
            var nums = Url.indexOf("lang=");
            var danx = Url.substring(nums, nums+10);
            Url = Url.replace(danx,lang);
            window.location.href = Url;
            return ;
           
          }else{
            return;
          }
        }
      }

    }

$('.l-lang').hover(function(){
    $('.lanague').stop().slideToggle(100)
})          
$(".l-lang dd a").click(function() {
    var text = $(this).html();
    $(".l-lang dt a").html(text);
    // $(".lw-lang dd").hide();
});

</script>
<script type="text/javascript">
var headerpath = window.location.pathname;
var selectMenu = "${selectMenu}";
var lis = $(".l-nav ul li");
if(headerpath.startWith("/index.html") || headerpath.length <= 2){
  lis.eq(0).addClass("current") ;
  $("header").removeClass('header_in');
}
else if(headerpath.startWith("/trademarket.html")){
  lis.eq(1).addClass("current") ;
}
else if(headerpath.startWith("/advertisement/")){
  lis.eq(2).addClass("current") ;
}
<c:if test="${pageContext.response.locale eq 'zh_CN' }">
else if(headerpath.startWith("/introl/introlIndex.html")){
  lis.eq(3).addClass("current");
}
else if(headerpath.startWith("/about/")){
  lis.eq(4).addClass("current") ;
}
else if(headerpath.startWith("/community/")){
  lis.eq(5).addClass("current") ;
}
else if(headerpath.startWith("/luckydraw/")){
  lis.eq(6).addClass("current") ;
}
else if(headerpath.startWith("/platform/")){
  $(".platform").css("color","#85aae1");
}
else if(headerpath.startWith("/download/")){
  $(".download").css("color","#85aae1");
}
</c:if>
</script>

<script type="text/javascript">

$(function () {

	initheader();
	
	/* app下载 */
	$(".codeIco").hover(function () {
		$("#qrcodeder").show();
	},function(){
	    $("#qrcodeder").hide();
	})
})

function initheader() {
	createQrCodes("table",150, 150, window.location.origin+"/download/index.html");
	var margin = ($("#qrcodeder").height()-$("#qrcodederico").height())/2;
}


function createQrCodes(rendermethod, picwidth, picheight, url) {
    if (navigator.userAgent.indexOf("MSIE") > 0) {
       jQuery('#qrcodeder').qrcode({
         text : utf16to8(url),
         width : picwidth,
         height : picheight,
         render : rendermethod,
         typeNumber:-1,//计算模式
         correctLevel:2,//二维码纠错级别
         background:"#ffffff",//背景颜色
         foreground:"#000000"  //二维码颜色
       });
     } else {
       jQuery('#qrcodeder').qrcode({
         text : utf16to8(url),
         width : picwidth,
         height : picheight,
         render : "canvas",
         typeNumber:-1,//计算模式
         correctLevel:2,//二维码纠错级别
         background:"#ffffff",//背景颜色
         foreground:"#000000"  //二维码颜色
       });
     }
   };

function generateQRCode(rendermethod, picwidth, picheight, url) {
    $("#qrcodeder").qrcode({ 
        render: rendermethod, // 渲染方式有table方式（IE兼容）和canvas方式
        width: picwidth, //宽度 
        height:picheight, //高度 
        text: url, //内容 
    });
}


//中文编码格式转换
function utf16to8(str) {
    var out, i, len, c;
    out = "";
    len = str.length;
    for (i = 0; i < len; i++) {
        c = str.charCodeAt(i);
        if ((c >= 0x0001) && (c <= 0x007F)) {
            out += str.charAt(i);
        } else if (c > 0x07FF) {
            out += String.fromCharCode(0xE0 | ((c >> 12) & 0x0F));
            out += String.fromCharCode(0x80 | ((c >> 6) & 0x3F));
            out += String.fromCharCode(0x80 | ((c >> 0) & 0x3F));
        } else {
            out += String.fromCharCode(0xC0 | ((c >> 6) & 0x1F));
            out += String.fromCharCode(0x80 | ((c >> 0) & 0x3F));
        }
    }
    return out;
}
</script>
