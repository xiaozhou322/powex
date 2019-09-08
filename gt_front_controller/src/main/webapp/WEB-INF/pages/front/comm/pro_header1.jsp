<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@include file="include.inc.jsp"%>
<style>
.nav .open>a, .nav .open>a:hover, .nav .open>a:focus {
    background-color: transparent !important;
    border-color: #c83935;
}
.l-header{   
	 background: none;
	 padding:0px;
    height: 100%;}
.headerCenter{
    width: 1300px;
    margin: 0 auto;
}
.headerTit{
    width: 1300px;
    height: 70px;
    color: #fff;
    z-index: 9;
    padding: 15px 0;
}
.login_user{
    background: #376161;
    padding: 0 7px;
    border: 1px solid #8FE7E7;
}
.headerTit-logo{
    color: #fff;
    font-family: MicrosoftYaHei;
    float: left;
}
.headerTit-logo p:nth-child(1){
    font-size: 18px;
    font-family: MicrosoftYaHei;
}
.headerTit-logo p:nth-child(1) .projectName{
	vertical-align: 6px;
    padding-left: 5px;
    font-size: 18px;
}
.headerTit-logo p:nth-child(2){
	font-size:12px;
	font-family:MicrosoftYaHei;
	font-weight:400;
}
.headerTit .headerTit-logo .logoUrlImg img{
    height: 40px;
}

</style>

<header class="l-header header_in">
 <div class="headerCenter">
    <div class="headerTit">
   		<a href="/" class="headerTit-logo 6969">
    		<p class="logoUrlImg">
	    		<img src="${requestScope.project.logoUrl}" alt="" />
    		</p>
   		</a>
         <c:if test="${sessionScope.login_user == null }">  
	          <div class="l-loginBtn fr">
	              <div class="fl l-regForm login_user">
	                 <span class="fl">
	                     <a href="/user/login.html"><spring:message code="nav.top.login" /></a>
	                     <span style="padding: 0 6px;">|</span>
	                     <a href="/user/register.html" class="current"><spring:message code="nav.top.register" /></a>
	                 </span>
	            </div>
	         </div>
         </c:if>
         
         <c:if test="${sessionScope.login_user != null }">      
         <div class="fr">
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
         </div>
         </c:if>     
		<nav class="l-nav fr">
         <ul>
            <li class="fl"><a href="/"><spring:message code="nav.top.home" /></a></li>
            <li class="fl"><a href="/trademarket.html"><spring:message code="nav.top.trade" /></a></li>
			<li class="fl"><a href="/advertisement/buyList.html"><spring:message code="nav.top.otc" /></a></li>
			<li class="fl"><a href="/luckydraw/luckydrawIndex.html">幸运转盘</li>
			<li class="fl"><a href="/download/index.html"><spring:message code="nav.top.appdownload" /></a></li>
			<li class="fl"><a href="/about/index.html?id=57"><spring:message code="nav.top.helpCenter" /></a></li>
         </ul>
        </nav>
        
    </div>
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
if(headerpath.startWith("/index.html") || headerpath.length == 0){
  lis.eq(0).addClass("current") ;
  $("header").removeClass('header_in');
}
else if(headerpath.startWith("/trademarket.html")){
  lis.eq(1).addClass("current") ;
}
else if(headerpath.startWith("/advertisement/")){
  lis.eq(2).addClass("current") ;
}
else if(headerpath.startWith("/luckydraw/")){
  lis.eq(3).addClass("current") ;
}
else if(headerpath.startWith("/download/")){
  lis.eq(4).addClass("current") ;
}
else if(headerpath.startWith("/about/")){
  lis.eq(4).addClass("current") ;
}
</script>

