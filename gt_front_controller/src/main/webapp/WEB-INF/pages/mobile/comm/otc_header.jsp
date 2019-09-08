<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@include file="include.inc.jsp"%>
<style>
.nav .open>a, .nav .open>a:hover, .nav .open>a:focus {
    background-color: transparent !important;
    border-color: #c83935;
}
.usdt_header{background:#fff; border: 1px solid #e9e9e9;}
.usdt_header .l-nav ul li{margin:0;}
.usdt_header .l-nav ul li a{color:#000; display:inline-block; height:59px; padding:0 20px;}
.l-nav ul li.current a{color:#488bef; border-bottom:2px solid #488bef;}
.l-nav ul li:hover a{color:#488bef;}
.l-lang a,.userName{color:#000;}
.userName:after{content:''; border-color:#000;}
.lanague{background:#fff; color:#666; box-shadow:0 0 2px #999;}
.release{background:#488bef; color:#fff; height:28px; line-height:28px; padding:0 25px; border-radius:15px; display:block; margin-top:15px;}
.release:hover{opacity:0.8; text-decoration:none;}
</style>

<header class="l-header usdt_header">
    <div class="clear">
         <h1 class="l-logo fl"><a href="/"><img src="/static/gbcax2018/images/logo2.png" alt="" />&nbsp;&nbsp;<span style="color:#050505; font-size:18px;">OTC</span></a></h1>
         <nav class="l-nav fl">
             <ul class="fl">
<!--                  <li class="fl"><a href="/"><spring:message code="nav.top.home" /></a></a></li>
 -->              <%--    <li class="fl"><a href="/"><spring:message code="nav.top.home" /></a></li> --%>
                <!--  <li class="fl"><a href="/advertisement/buyList.html">购买列表</a></li>
                 <li class="fl"><a href="/advertisement/sellList.html">出售列表</a></li>
                 <li class="fl"><a href="/advertisement/puborder.html">发布广告</a></li> -->
<!--                  <li class="fl"><a href="/trademarket.html"><spring:message code="agent.exchange" /> </a></li> -->
                 <div class="clear"></div>
             </ul>
             <div class="clear"></div>
         </nav>
         <c:if test="${sessionScope.login_user == null }">  
          <%-- <div class="l-loginBtn fr">
             <a href="" class="fl release"><spring:message code="agent.advertise" /></a>
             <div class="fl l-regForm">
                 <span class="fl">
                     <a href="/user/login.html">Login</a>
                     |
                     <a href="/user/register.html?phone=cn" class="current">Register</a>
                 </span>
            </div> --%>
            <div class="fl" style="align-items: right">
                <dl class="l-lang  fl">
                  <dt class="lang"><a href="javascript:;"><img class="fl" src="/static/gbcax2018/images/<spring:message code="language.title" />.png" width="24" alt="" /><spring:message code="language.desc" /></a></dt>
                  <dd class="lanague clear">
                      <a onclick="langs('lang=en_US')" href="javascript:;"><img class="fl" src="/static/gbcax2018/images/en.png" width="24" alt="" /><spring:message code="language.en" /></a>
                      <a onclick="langs('lang=zh_CN')" href="javascript:;"><img class="fl" src="/static/gbcax2018/images/cn.png" width="24" alt="" /><spring:message code="language.cn" /></a>
                  </dd>
                </dl>
             </div>
             <div class="clear"></div>
         </div>
         </c:if>
         <c:if test="${sessionScope.login_user != null }">      
         <div class="fr">
            <%-- <c:if test="${is_agent != 1 }">
                <a href="javascript:;" class="fl release toshen"><spring:message code="agent.advertise" /></a>
             </c:if>   --%> 
            <%--  <c:if test="${is_agent == 1 }"> --%>
              <%--   <a href="/advertisement/puborder.html" class="fl release"><spring:message code="agent.advertise" /></a> --%>
             <%-- </c:if>   --%>               
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
                            <a href="/financial/index.html">
                                <svg class="icon icon_s" aria-hidden="true">
                                    <use xlink:href="#icon-zichanxinxi"></use>
                                </svg><spring:message code="newuser.financial.asset" />
                            </a>
                        </li>
                         <li>
                            <a href="/trade/orderList.html?status=0">
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
                            <a href="/about/index.html?id=2">
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
                  <dt class="lang"><a href="javascript:;"><img class="fl" src="/static/gbcax2018/images/<spring:message code="language.title" />.png" width="24" alt="" /><spring:message code="language.desc" /></a></dt>
                  <dd class="lanague clear">
                      <a onclick="langs('lang=en_US')" href="javascript:;"><img class="fl" src="/static/gbcax2018/images/en.png" width="24" alt="" /><spring:message code="language.en" /></a>
                      <a onclick="langs('lang=zh_CN')" href="javascript:;"><img class="fl" src="/static/gbcax2018/images/cn.png" width="24" alt="" /><spring:message code="language.cn" /></a>
                  </dd>
                </dl>
             </div>
             <div class="clear"></div>
         </div>
         </c:if>     
    </div>
</header>

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

    $(".toshen").click(function() {
        layer.confirm(language["agent.toshen"], {
        btn: [language["agent.goshen"],language["agent.no"]] //按钮
        }, function(){
            window.location.href="/agent/agentapply.html";
        });
    });

</script>

<script type="text/javascript">
var headerpath = window.location.pathname;

var selectMenu = "${selectMenu}";

var lis = $(".l-nav ul li");
if(headerpath.startWith("/agent/agentlist.html") || headerpath.startWith("/agent/buyerlist.html")){
  lis.eq(0).addClass("current") ;
}
// if(headerpath.startWith("/agent/agentlist.html") || headerpath.startWith("/agent/buyerlist.html")){
//   lis.eq(1).addClass("current") ;
// }
else if(headerpath.startWith("/agent/orderlist.html")){
  lis.eq(1).addClass("current") ;

}

else if(headerpath.startWith("/agent/agentapply.html")){
  lis.eq(3).addClass("current") ;
}
else if(headerpath.startWith("/agent/agentapply.html")|| headerpath.startWith("/agent/payaccount.html") || headerpath.startWith("/agent/agentinfo.html") || headerpath.startWith("/agent/puborder.html") || headerpath.startWith("/agent/adlist.html")){
  lis.eq(2).addClass("current") ;
}

</script>
