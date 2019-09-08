<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@include file="include.inc.jsp"%>
<style>
.nav .open>a, .nav .open>a:hover, .nav .open>a:focus {
    background-color: transparent !important;
    border-color: #c83935;
}

<c:if test="${sessionScope.login_user!=null&&sessionScope.login_user.fismerchant==true }">
.button_button::-moz-focus-inner{
  border: 0;
  padding: 0;
}

.button_button{
  display: inline-block;
  *display: inline;
  zoom: 1;
  padding: 6px 20px;
  margin: 0;
  cursor: pointer;
  border: 1px solid #bbb;
  overflow: visible;
  font: bold 13px arial, helvetica, sans-serif;
  text-decoration: none;
  white-space: nowrap;
  color: #555;
  
  background-color: #ddd;
  background-image: -webkit-gradient(linear, left top, left bottom, from(rgba(255,255,255,1)), to(rgba(255,255,255,0)));
  background-image: -webkit-linear-gradient(top, rgba(255,255,255,1), rgba(255,255,255,0));
  background-image: -moz-linear-gradient(top, rgba(255,255,255,1), rgba(255,255,255,0));
  background-image: -ms-linear-gradient(top, rgba(255,255,255,1), rgba(255,255,255,0));
  background-image: -o-linear-gradient(top, rgba(255,255,255,1), rgba(255,255,255,0));
  background-image: linear-gradient(top, rgba(255,255,255,1), rgba(255,255,255,0));
  
  -webkit-transition: background-color .2s ease-out;
  -moz-transition: background-color .2s ease-out;
  -ms-transition: background-color .2s ease-out;
  -o-transition: background-color .2s ease-out;
  transition: background-color .2s ease-out;
  background-clip: padding-box; /* Fix bleeding */
  -moz-border-radius: 3px;
  -webkit-border-radius: 3px;
  border-radius: 3px;
  -moz-box-shadow: 0 1px 0 rgba(0, 0, 0, .3), 0 2px 2px -1px rgba(0, 0, 0, .5), 0 1px 0 rgba(255, 255, 255, .3) inset;
  -webkit-box-shadow: 0 1px 0 rgba(0, 0, 0, .3), 0 2px 2px -1px rgba(0, 0, 0, .5), 0 1px 0 rgba(255, 255, 255, .3) inset;
  box-shadow: 0 1px 0 rgba(0, 0, 0, .3), 0 2px 2px -1px rgba(0, 0, 0, .5), 0 1px 0 rgba(255, 255, 255, .3) inset;
  text-shadow: 0 1px 0 rgba(255,255,255, .9);
  
  -webkit-touch-callout: none;
  -webkit-user-select: none;
  -khtml-user-select: none;
  -moz-user-select: none;
  -ms-user-select: none;
  user-select: none;
}

.button_button:hover{
  background-color: #eee;
  color: #555;
}

.button_button:active{
  background: #e9e9e9;
  position: relative;
  top: 1px;
  text-shadow: none;
  -moz-box-shadow: 0 1px 1px rgba(0, 0, 0, .3) inset;
  -webkit-box-shadow: 0 1px 1px rgba(0, 0, 0, .3) inset;
  box-shadow: 0 1px 1px rgba(0, 0, 0, .3) inset;
}

.button_button[disabled], .button_button[disabled]:hover, .button_button[disabled]:active{
  border-color: #eaeaea;
  background: #fafafa;
  cursor: default;
  position: static;
  color: #999;
  /* Usually, !important should be avoided but here it's really needed :) */
  -moz-box-shadow: none !important;
  -webkit-box-shadow: none !important;
  box-shadow: none !important;
  text-shadow: none !important;
}

/* Smaller buttons styles */

.button_button.small{
  padding: 4px 12px;
}

/* Larger buttons styles */

.button_button.large{
  padding: 12px 30px;
  text-transform: uppercase;
}

.button_button.large:active{
  top: 2px;
}

/* Colored buttons styles */

.button_button.green, .button_button.red, .button_button.blue {
  color: #fff;
  text-shadow: 0 1px 0 rgba(0,0,0,.2);
  
  background-image: -webkit-gradient(linear, left top, left bottom, from(rgba(255,255,255,.3)), to(rgba(255,255,255,0)));
  background-image: -webkit-linear-gradient(top, rgba(255,255,255,.3), rgba(255,255,255,0));
  background-image: -moz-linear-gradient(top, rgba(255,255,255,.3), rgba(255,255,255,0));
  background-image: -ms-linear-gradient(top, rgba(255,255,255,.3), rgba(255,255,255,0));
  background-image: -o-linear-gradient(top, rgba(255,255,255,.3), rgba(255,255,255,0));
  background-image: linear-gradient(top, rgba(255,255,255,.3), rgba(255,255,255,0));
}

/* */

.button_button.green{
  background-color: #57a957;
  border-color: #57a957;
}

.button_button.green:hover{
  background-color: #62c462;
}

.button_button.green:active{
  background: #57a957;
}

/* */

.button_button.red{
  background-color: #ca3535;
  border-color: #c43c35;
}

.button_button.red:hover{
  background-color: #ee5f5b;
}

.button_button.red:active{
  background: #c43c35;
}

/* */

.button_button.blue{
  background-color: #269CE9;
  border-color: #269CE9;
}

.button_button.blue:hover{
  background-color: #70B9E8;
}

.button_button.blue:active{
  background: #269CE9;
}

/* */

.green[disabled], .green[disabled]:hover, .green[disabled]:active{
  border-color: #57A957;
  background: #57A957;
  color: #D2FFD2;
}

.red[disabled], .red[disabled]:hover, .red[disabled]:active{
  border-color: #C43C35;
  background: #C43C35;
  color: #FFD3D3;
}

.blue[disabled], .blue[disabled]:hover, .blue[disabled]:active{
  border-color: #269CE9;
  background: #269CE9;
  color: #93D5FF;
}

/* Group buttons */

.button_button-group,
.button_button-group li{
  display: inline-block;
  *display: inline;
  zoom: 1;
}

.button_button-group{
  font-size: 0; /* Inline block elements gap - fix */
  margin: 0;
  padding: 0;
  background: rgba(0, 0, 0, .1);
  border-bottom: 1px solid rgba(0, 0, 0, .1);
  padding: 7px;
  -moz-border-radius: 7px;
  -webkit-border-radius: 7px;
  border-radius: 7px;
}

.button_button-group li{
  margin-right: -1px; /* Overlap each right button border */
}

.button_button-group .button_button{
  font-size: 13px; /* Set the font size, different from inherited 0 */
  -moz-border-radius: 0;
  -webkit-border-radius: 0;
  border-radius: 0;
}

.button_button-group .button_button:active{
  -moz-box-shadow: 0 0 1px rgba(0, 0, 0, .2) inset, 5px 0 5px -3px rgba(0, 0, 0, .2) inset, -5px 0 5px -3px rgba(0, 0, 0, .2) inset;
  -webkit-box-shadow: 0 0 1px rgba(0, 0, 0, .2) inset, 5px 0 5px -3px rgba(0, 0, 0, .2) inset, -5px 0 5px -3px rgba(0, 0, 0, .2) inset;
  box-shadow: 0 0 1px rgba(0, 0, 0, .2) inset, 5px 0 5px -3px rgba(0, 0, 0, .2) inset, -5px 0 5px -3px rgba(0, 0, 0, .2) inset;
}

.button_button-group li:first-child .button_button{
  -moz-border-radius: 3px 0 0 3px;
  -webkit-border-radius: 3px 0 0 3px;
  border-radius: 3px 0 0 3px;
}

.button_button-group li:first-child .button_button:active{
  -moz-box-shadow: 0 0 1px rgba(0, 0, 0, .2) inset, -5px 0 5px -3px rgba(0, 0, 0, .2) inset;
  -webkit-box-shadow: 0 0 1px rgba(0, 0, 0, .2) inset, -5px 0 5px -3px rgba(0, 0, 0, .2) inset;
  box-shadow: 0 0 1px rgba(0, 0, 0, .2) inset, -5px 0 5px -3px rgba(0, 0, 0, .2) inset;
}

.button_button-group li:last-child .button_button{
  -moz-border-radius: 0 3px 3px 0;
  -webkit-border-radius: 0 3px 3px 0;
  border-radius: 0 3px 3px 0;
}

.button_button-group li:last-child .button_button:active{
  -moz-box-shadow: 0 0 1px rgba(0, 0, 0, .2) inset, 5px 0 5px -3px rgba(0, 0, 0, .2) inset;
  -webkit-box-shadow: 0 0 1px rgba(0, 0, 0, .2) inset, 5px 0 5px -3px rgba(0, 0, 0, .2) inset;
  box-shadow: 0 0 1px rgba(0, 0, 0, .2) inset, 5px 0 5px -3px rgba(0, 0, 0, .2) inset;
}
</c:if>
</style>

<header class="l-header header_in">
    <div class="clear">
         <h1 class="l-logo fl"><a href="/">
         <c:if test="${requestScope.isprojectUrl}">
         <img src="${requestScope.project.logoUrl}" alt="" />
         </c:if>
         <c:if test="${!requestScope.isprojectUrl}">
         <img src="${oss_url}/static/front2018/images/logo.png" alt="" />
         </c:if>
         </a></h1>
         <nav class="l-nav fl">
             <ul class="fl">
                 <li class="fl"><a href="/advertisement/buyList.html"><spring:message code="agent.purchase" /></a></li>
                 <li class="fl"><a href="/advertisement/sellList.html"><spring:message code="agent.navsell" /></a></li>
                 <c:if test="${sessionScope.login_user!=null}">
                  <li class="fl"><a href="/order/orderList.html"><spring:message code="agent.otcorder" /></a></li>
                  <li class="fl"><a href="/user/paytypeList.html"><spring:message code="agent.accountsetting" /></a></li>
                  </c:if>
                  <c:if test="${sessionScope.login_user!=null&&sessionScope.login_user.fismerchant==true }">
                   <li class="fl"><a href="/advertisement/puborder.html"><spring:message code="agent.advertise" /></a></li>
                  </c:if>
                 <div class="clear"></div>
             </ul>
        <div class="clear"></div>
         </nav>
         
         <c:if test="${sessionScope.login_user == null }">  
          <div class="l-loginBtn fr"> 
          
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
         <c:if test="${sessionScope.login_user!=null&&sessionScope.login_user.fismerchant==true }">
         		<span class="fl">
         			<a id="online" href="javascript:;" class="small red button_button" style="margin: 6px 22px;<c:if test="${sessionScope.online==true}"> display:none</c:if>">商家离线</a>
         			<a id="offline" href="javascript:;" class="small green button_button" style="margin: 6px 22px;<c:if test="${sessionScope.online==false}"> display:none</c:if>">商家在线</a>
         		</span>                   
  		</c:if>
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
                        
                         <li>
                            <a href="/financial/index.html">
                                <svg class="icon icon_s" aria-hidden="true">
                                    <use xlink:href="#icon-zichanxinxi"></use>
                                </svg><spring:message code="newuser.financial.asset" />
                            </a>
                        </li>
                         <li>
                            <a href="/order/orderList.html">
                                <svg class="icon icon_s" aria-hidden="true">
                                    <use xlink:href="#icon-jiaoyijilu"></use>
                                </svg><spring:message code="agent.otcorder" />
                            </a>
                        </li>
                        <c:if test="${sessionScope.login_user!=null&&sessionScope.login_user.fismerchant==true }">
                        <li>
                            <a href="/advertisement/puborder.html">
                                <svg class="icon icon_s" aria-hidden="true">
                                    <use xlink:href="#icon-wodefabu"></use>
                                </svg><spring:message code="agent.advertise" />
                            </a>
                        </li>
                        </c:if>
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
    <audio id="sound" autoplay="autoplay"></audio>
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

<c:if test="${sessionScope.login_user!=null&&sessionScope.login_user.fismerchant==true }">
$("#online").click(function(){
	$.post("/user/online.html?random=" + Math.round(Math.random() * 100),function(data) {
		if (data.code == 0) {
			util.showMsg(language["comm.error.online.1"]);
			$("#online").hide();
			$("#offline").show();
		}else {
			util.showMsg(language["comm.error.online.2"]);
		}
	}, "json");

});
$("#offline").click(function(){
	$.post("/user/offline.html?random=" + Math.round(Math.random() * 100),function(data) {
		if (data.code == 0) {
			util.showMsg(language["comm.error.offline.1"]);
			$("#offline").hide();
			$("#online").show();
		}else {
			util.showMsg(language["comm.error.offline.2"]);
		}
	}, "json");
});
</c:if>
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
else if(headerpath.startWith("/advertisement/buyList.html")){
  lis.eq(0).addClass("current") ;
}
else if(headerpath.startWith("/advertisement/sellList.html")){
  lis.eq(1).addClass("current") ;
}
else if(headerpath.startWith("/order/orderList.html")){
  lis.eq(2).addClass("current");
}
else if(headerpath.startWith("/advertisement/puborder.html")){
  lis.eq(3).addClass("current") ;
}else if(headerpath.startWith("/advertisement/advertisementMine.html")){
  lis.eq(3).addClass("current") ;
}
</script>

<script type="text/javascript">
var notification ;
$(function(){
	getOrderCount();
});

function notifyMe(count) {
	  // Let's check if the browser supports notifications
	  if (!("Notification" in window)) {
	    alert("This browser does not support desktop notification");
	    
	  }
	
	  // Let's check whether notification permissions have already been granted
	  else if (Notification.permission === "granted") {
	    // If it's okay let's create a notification
	    document.getElementById("sound").src="${oss_url}/static/front2018/images/4123.wav";
	    notification= new Notification('状态更新提醒',{
	        body: '有'+count+'个未处理订单请查收!',
	        data: {
	        	 url: '/order/orderList.html'
	        },
	        tag: 'linxin',
	        icon: '${oss_url}/static/front2018/images/timg.jpg',
	        });
	    

	  }

	  // Otherwise, we need to ask the user for permission
	  else if (Notification.permission !== "denied") {
	    Notification.requestPermission(function (permission) {
	      // If the user accepts, let's create a notification
	      if (permission === "granted") {
			    document.getElementById("sound").src="${oss_url}/static/front2018/images/4123.wav";
			    notification= new Notification('状态更新提醒',{
			        body: '有'+count+'个未处理订单请查收!',
			        data: {
			        	 url: '/order/orderList.html'
			        },
			        tag: 'linxin',
			        icon: '${oss_url}/static/front2018/images/timg.jpg',
			        });


	      }
	    });
	  }else{
		    document.getElementById("sound").src="${oss_url}/static/front2018/images/4123.wav";
		    notification= new Notification('状态更新提醒',{
		        body: '有'+count+'个未处理订单请查收!',
		        data: {
		            url: '/order/orderList.html'
		        },
		        tag: 'linxin',
		        icon: '${oss_url}/static/front2018/images/timg.jpg',
		      });

	  }
	  
	  notification.onclick = function(){
		  // window.open(notification.data.url, '_blank');      // 打开网址
		    location.href=notification.data.url;// 打开网址
		    notification.close();   
	  }
	   setTimeout(function() {
		  notification.close();}, 15000); 
	  // At last, if the user has denied notifications, and you 
	  // want to be respectful there is no need to bother them any more.
	}
	
function getOrderCount(){
		var url="/order/allList.html";
	$.ajax({
       type: 'post',
       url: url,
        async: false,
        data:{
         },
        dataType: "json",
        success: function(result) {
        	$("#newtitle").empty();
        	
        	var html ="";
            if(result.success==true&&result.count!=null &&result.count>0){ 
        	  html+='<a href="/trade/orderList.html"> <div class="newmassage" style="color:black;">';
        	  html+='<span class="title">提示</span>';
        	  html+='<span>你有'+result.count+'个未处理订单</span>';
        		  html+=' </div></a>';	
        		  $("#newtitle").html(html);
	              $("#newtitle").fadeIn(5000);
	            notifyMe(result.count);
          }else{
        	   $("#newtitle").hide();

          }
 		$("#newtitle").html(html);
		},

		});

		setTimeout("getOrderCount()", "25000");
	}
</script>
