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

#newtitle{
   position: fixed;
    text-align: center;
    bottom: 40%;
    right: 10px;
    background-color: #efeff4;
    background-url:url("/static/front/images/hint.png") no-repeat 0 0;
    width: 250px;
    height: 150px;
    font-size: 18px;
    line-height: 150px;
    border-radius: 5px;
    z-index: 100;
    display: none;
}

.title{
	font-size: 14px;
    position: relative;
    top: -50px;
    left:-10px;
    color:black;
}


	.useInformation2{width:250px;background:rgba(255,255,255,.9);opacity:0.8; box-shadow:0 0 2px #999; position: absolute; left:99px; top:60px;z-index: 9999;display:none;}
	
}

</style>

<header class="l-header usdt_header">
    <div class="clear">
         <h1 class="l-logo fl"><a href="#"><img src="/static/front2018/images/logo.png" alt="" />&nbsp;&nbsp;<span style="color:#050505; font-size:18px;">OTC</span></a></h1>
         <nav class="l-nav fl">
             <ul class="fl">
<!--                    <li class="fl"><a href="/"><spring:message code="nav.top.home" /></a></li> -->
                 <li class="fl"><a href="/advertisement/buyList.html"><spring:message code="agent.purchase" /></a></li>
                 <li class="fl"><a href="/advertisement/sellList.html"><spring:message code="agent.navsell" /></a></li>
                 <c:if test="${sessionScope.login_user!=null}">
                  <li class="fl"><a href="/order/orderList.html"><spring:message code="我的订单" /></a></li>
                  </c:if>
                  <c:if test="${sessionScope.login_user!=null&&sessionScope.login_user.fismerchant==true }">
                   <li class="fl"><a href="/advertisement/puborder.html"><spring:message code="agent.ad" /></a></li>
                  </c:if>
                 
                 
                
<!--                  <li class="fl"><a href="/trademarket.html"><spring:message code="agent.exchange" /> </a></li> -->
                 <div class="clear"></div>
             </ul>
             <div class="clear"></div>
         </nav>
         <c:if test="${sessionScope.login_user == null }">  
          <div class="l-loginBtn fr">
             <%-- <a href="" class="fl release"><spring:message code="agent.advertise" /></a> --%>


             <div class="fl l-regForm">
                 <span class="fl">
                     <a href="/user/login.html"><spring:message code="nav.top.login" /></a>
                     |
                     <a href="/user/register.html?phone=cn" class="current"><spring:message code="nav.top.register" /></a>
                 </span>
            </div>
            <div class="fl">
                <dl class="l-lang  fl">
                  <dt class="lang"><a href="javascript:;"><img class="fl" src="/static/front2018/images/<spring:message code="language.title" />.png" width="24" alt="" /><spring:message code="language.desc" /></a></dt>
                  <%-- <dd class="lanague clear">
                      <a onclick="langs('lang=en_US')" href="javascript:;"><img class="fl" src="/static/gbcax2018/images/en.png" width="24" alt="" /><spring:message code="language.en" /></a>
                      <a onclick="langs('lang=zh_CN')" href="javascript:;"><img class="fl" src="/static/gbcax2018/images/cn.png" width="24" alt="" /><spring:message code="language.cn" /></a>
                  </dd> --%>
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
               <%--  <a href="/advertisement/puborder.html" class="fl release"><spring:message code="agent.advertise" /></a> --%>
             <%-- </c:if>   --%>   
   <div style="display:none;">　<c:set var="iscontain" value="false" />
　　<!-- 原始集合：items，集合元素 ：var-->
　　<c:forEach items="${applicationScope.onlineUserList}" var="element">
　　　　<!-- 判断测试值是否存在于集合中，存在将iscontain置为true -->
　　　　<c:if test="${element eq sessionScope.login_user.fid}">
　　　　　　<c:set var="iscontain" value="true" />
　　　　</c:if>
　　</c:forEach></div>          
                           　
             <div class="fl" style="height: 60px;    position: relative; line-height: 60px;">
              <c:if test="${sessionScope.login_user.fismerchant==true }">
             
                    <c:choose>
             <c:when test="${iscontain}">
              <span class="fl" style="padding-left: 20px; padding-bottom: 20px;">
                   <spring:message code="用户在线" />
                 </span> 
              <span class="fl" style="padding-left: 20px; padding-bottom: 20px;color: #000;">
                 
                         <a href="/user/outline.html"  class="down" style="color: white;background-color: #488bef; width: 30px;padding-left: 10px;padding-right: 10px;padding-top: 5px;padding-bottom: 5px;border-radius: 5px;"><spring:message code="agent.downline"/></a>
                </span>
                <div class="useInformation2">
                 <div style="height: 90px;line-height: 35px;color: #000;padding:7px 10px 0 20px; ">
                 <span>若您点击“下线”，您未下架的广告将无法展示给其他用户。</span>
                 </div>
               </div>
                 
             </c:when>
             <c:otherwise>
             <span class="fl" style="padding-left: 20px; padding-bottom: 20px;">
                        <spring:message code="用户离线" />
             </span>
              <span class="fl" style="padding-left: 20px; padding-bottom: 20px;color: #000;">
                  <a href="/user/online.html" class="on" style="color: white;background-color: #488bef; width: 30px;padding-left: 10px;padding-right: 10px;padding-top: 5px;padding-bottom: 5px;border-radius: 5px;"><spring:message code="agent.online" /></a>
                 </span>
                  <div class="useInformation2">
                 <div style="height: 90px;line-height: 35px;color: #000;padding:7px 10px 0 20px; ">
                 <span>若您点击“上线”，您未下架的广告将展示给其他用户。</span>
                 </div>
               </div>
             </c:otherwise>
             </c:choose>  
             </c:if> 
             </div>         
             <div class="fl l-regForm">

           
                 <span class="fl">
                        
                     <svg class="icon" aria-hidden="true">
                       <use xlink:href="#icon-touxiang"></use>
                     </svg>
                     
                     <a href="/financial/index.html">  <em class='userName'><%-- ${login_user.fnickName} --%><spring:message code="language.Mycenter" /></em></a>
                 </span>
                 <div class="useInformation">
                 <div style="width: 100%;padding-left: 20px; height: 54px;padding-bottom: 20px;color: #000;">
                 <p> <i>ID:</i>${login_user.fid}</p>
                 </div>
                     <ul>
                 <%--        <li> 
                             <a href="/financial/index.html">
                                 <svg class="icon icon_s" aria-hidden="true">
                                  <use xlink:href="#icon-zichanxinxi"></use>
                                </svg><spring:message code="newuser.financial.asset" />
                           </a> 
                        </li> --%>
                        
                      
<!--                          <li> -->
<!--                             <a href="/about/index.html?id=2"> -->
<!--                                 <svg class="icon icon_s" aria-hidden="true"> -->
<!--                                     <use xlink:href="#icon-help1"></use> -->
<!--                                 </svg><spring:message code="nav.top.help" /> -->
<!--                             </a> -->
<!--                         </li> -->
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
                  <dt class="lang"><a href="javascript:;"><img class="fl" src="/static/front2018/images/<spring:message code="language.title" />.png" width="24" alt="" /><spring:message code="language.desc" /></a></dt>
                  <%-- <dd class="lanague clear">
                      <a onclick="langs('lang=en_US')" href="javascript:;"><img class="fl" src="/static/gbcax2018/images/en.png" width="24" alt="" /><spring:message code="language.en" /></a>
                      <a onclick="langs('lang=zh_CN')" href="javascript:;"><img class="fl" src="/static/gbcax2018/images/cn.png" width="24" alt="" /><spring:message code="language.cn" /></a>
                  </dd> --%>
                </dl>
             </div>
             <div class="clear"></div>
         </div>
         </c:if>     
    </div>
     <audio id="sound" autoplay="autoplay"></audio>
</header>
<script type="text/javascript">
var notification ;
$(function(){
	getOrderCount();
	$(".on").mouseover(function(){
		$(".useInformation2").css("display","block");
	})
	
	 $(".on").mouseleave(function(){
		$(".useInformation2").css("display","none");
	}) 
	
	$(".down").mouseover(function(){
		$(".useInformation2").css("display","block");
	})
	$(".down").mouseleave(function(){
		$(".useInformation2").css("display","none");
	})
});
function orderList(){
}

var notification;

function notifyMe(count) {
	  // Let's check if the browser supports notifications
	  if (!("Notification" in window)) {
	    alert("This browser does not support desktop notification");
	    
	  }
	
	  // Let's check whether notification permissions have already been granted
	  else if (Notification.permission === "granted") {
	    // If it's okay let's create a notification
	    document.getElementById("sound").src="/static/front2018/images/4123.wav";
	    notification= new Notification('状态更新提醒',{
	        body: '有'+count+'个未处理订单请查收!',
	        data: {
	        	 url: '/order/orderList.html'
	        },
	        tag: 'linxin',
	        icon: '/static/front/images/timg.jpg',
	        });
	    

	  }

	  // Otherwise, we need to ask the user for permission
	  else if (Notification.permission !== "denied") {
	    Notification.requestPermission(function (permission) {
	      // If the user accepts, let's create a notification
	      if (permission === "granted") {
			    document.getElementById("sound").src="/static/front2018/images/4123.wav";
			    notification= new Notification('状态更新提醒',{
			        body: '有'+count+'个未处理订单请查收!',
			        data: {
			        	 url: '/order/orderList.html'
			        },
			        tag: 'linxin',
			        icon: '/static/front/images/timg.jpg',
			        });


	      }
	    });
	  }else{
		    document.getElementById("sound").src="/static/front2018/images/4123.wav";
		    notification= new Notification('状态更新提醒',{
		        body: '有'+count+'个未处理订单请查收!',
		        data: {
		            url: '/order/orderList.html'
		        },
		        tag: 'linxin',
		        icon: '/static/front/images/timg.jpg',
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
});          
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
