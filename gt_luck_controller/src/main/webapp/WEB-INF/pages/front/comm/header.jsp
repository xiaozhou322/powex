<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@include file="include.inc.jsp"%>
<%
pageContext.setAttribute("otcPath", "http://192.168.0.158:8580");
pageContext.setAttribute("url", "http://192.168.0.158:8480");
%>
<style>
.nav .open>a, .nav .open>a:hover, .nav .open>a:focus {
    background-color: transparent !important;
    border-color: #c83935;
}
</style>

<header class="l-header header_in">
    <div class="clear">
         <h1 class="l-logo fl"><a href="/"><img src="${oss_url}/static/front2018/images/logo.png" alt="" /></a></h1>
         <nav class="l-nav fl">
             <ul class="fl">
                 <li class="fl"><a href="/index.html"><spring:message code="首页" /> </a></li>
                 <li class="fl"><a href="/projects/projectsIndex.html"><spring:message code="项目账户" /> </a></li>
                 <li class="fl"><a href="/community/communityIndex.html"><spring:message code="社群账户" /> </a></li>
                 <li class="fl"><a href="/luckydraw/luckydrawIndex.html"><spring:message code="抽奖活动" /> </a></li>
                 <%--<li class="fl"><a href="/platform/index.html"><spring:message code="关于平台" /> </a></li> --%>
                 <div class="clear"></div>
             </ul>
             <div class="clear"></div>
         </nav>
         
           
         <c:if test="${sessionScope.login_user == null }">  
          <div class="l-loginBtn fr"> 
          <span class="fl"><a href="/platform/platformIndex.html" class="platforms" style="color: #fff;font-size: 16px;line-height: 40px;margin: 0 22px;"><spring:message code="关于平台" /> </a></span>                    
             <div class="fl l-regForm">
                 <span class="fl">
                     <a href="/user/login.html"><spring:message code="nav.top.login" /></a>
                     |
                     <a href="/user/register.html" class="current"><spring:message code="nav.top.register" /></a>
                 </span>
            </div>
        <%--     <div class="fl">
                <dl class="l-lang  fl">
                  <dt class="lang"><a href="javascript:;"><img class="fl" src="${oss_url}/static/front2018/images/<spring:message code="language.title" />.png" width="24" alt="" /><spring:message code="language.desc" /></a></dt>
                  <dd class="lanague clear">
                      <a onclick="langs('lang=en_US')" href="javascript:;"><img class="fl" src="${oss_url}/static/front2018/images/en.png" width="24" alt="" /><spring:message code="language.en" /></a>
                      <a onclick="langs('lang=zh_CN')" href="javascript:;"><img class="fl" src="${oss_url}/static/front2018/images/cn.png" width="24" alt="" /><spring:message code="language.cn" /></a>
                  </dd>
                </dl>
             </div> --%>
             <div class="clear"></div>
         </div>
         </c:if>
         <c:if test="${sessionScope.login_user != null }">      
         <div class="fr">  
         <span class="fl"><a href="/platform/platformIndex.html" class="platforms" style="color: #fff;font-size: 16px;line-height: 40px;margin: 0 22px;"><spring:message code="关于平台" /> </a></span>                   
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
                         <span style="margin: 0 0px 0 -25px;font-size: 12px;">
                                    UID
                                </span>
                            ${login_user.fid}
                             </a>
                        </li> 
                        <li>
                            <a href="/introl/mydivide.html?type=1">
                                <svg class="icon icon_s" aria-hidden="true">
                                    <use xlink:href="#icon-jiaoyijilu"></use>
                                </svg><spring:message code="会员福利" />
                            </a>
                        </li>
                         <li>
                            <a href="/financial/index.html">
                                <svg class="icon icon_s" aria-hidden="true">
                                    <use xlink:href="#icon-zichanxinxi"></use>
                                </svg><spring:message code="newuser.financial.asset" />
                            </a>
                        </li>
                        <%--  <li>
                            <a href="/trade/entrust.html">
                                <svg class="icon icon_s" aria-hidden="true">
                                    <use xlink:href="#icon-jiaoyijilu"></use>
                                </svg><spring:message code="new.mytrade" />
                            </a>
                        </li> --%>
                         <li>
                            <a href="/user/security.html">
                                <svg class="icon icon_s" aria-hidden="true">
                                    <use xlink:href="#icon-anquan"></use>
                                </svg><spring:message code="newuser.security.settings" />
                            </a>
                        </li>
                         <%-- <li>
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
                            <a href="/about/index.html?id=2">
                                <svg class="icon icon_s" aria-hidden="true">
                                    <use xlink:href="#icon-help1"></use>
                                </svg><spring:message code="nav.top.help" />
                            </a>
                        </li> --%>
                         <li>
                            <a href="/user/logout.html">
                                <svg class="icon icon_s" aria-hidden="true">
                                    <use xlink:href="#icon-logout"></use>
                                </svg><spring:message code="nav.top.logout" />
                            </a>
                        </li>
                     </ul>
                 </div>
    <%--         </div>
            <div class="fl">
                <dl class="l-lang  fl">
                  <dt class="lang"><a href="javascript:;"><img class="fl" src="${oss_url}/static/front2018/images/<spring:message code="language.title" />.png" width="24" alt="" /><spring:message code="language.desc" /></a></dt>
                  <dd class="lanague clear">
                      <a onclick="langs('lang=en_US')" href="javascript:;"><img class="fl" src="${oss_url}/static/front2018/images/en.png" width="24" alt="" /><spring:message code="language.en" /></a>
                      <a onclick="langs('lang=zh_CN')" href="javascript:;"><img class="fl" src="${oss_url}/static/front2018/images/cn.png" width="24" alt="" /><spring:message code="language.cn" /></a>
                  </dd>
                </dl>
             </div> --%>
             <div class="clear"></div>
         </div>
         </c:if>     
    </div>
</header>
<script type="text/javascript">
    
</script>

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
if(headerpath.startWith("/index.html") || selectMenu.startWith("index")){
  lis.eq(0).addClass("current") ;
  $("header").removeClass('header_in');
}
else if(headerpath.startWith("/financial/")||headerpath.startWith("/account/") 
|| headerpath.startWith("/crowd/logs")||headerpath.startWith("/push/")
|| headerpath.startWith("/balance/")|| headerpath.startWith("/introl/") ){
  // lis.eq(3).addClass("current") ;
}
// else if(headerpath.startWith("/user/") || headerpath.startWith("/question/") || headerpath.startWith("/api_doc.html")){
//   lis.eq(3).addClass("current");
// }
else if(headerpath.startWith("/lucky/luckyIndex.html")){
  lis.eq(0).addClass("current") ;
}
else if(headerpath.startWith("/projects/projectsIndex.html")){
  lis.eq(1).addClass("current") ;
}
else if(headerpath.startWith("/community/communityIndex.html")){
  lis.eq(2).addClass("current") ;
}
else if(headerpath.startWith("/luckydraw/luckydrawIndex.html")){
  lis.eq(3).addClass("current") ;
}
/* else if(headerpath.startWith("/platform/platformIndex.html")){
  lis.eq(9).addClass("current") ;
} */

</script>
