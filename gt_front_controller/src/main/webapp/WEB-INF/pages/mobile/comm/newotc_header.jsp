<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@include file="include.inc.jsp"%>
<style>
.header_in{
    background: #fff;
}
.otcListNav{
    border-bottom: 2px solid #f5f7ff;
    
}
.otcListNav li{    
    padding: 0.2rem 0rem;
    height: 0.96rem;
    line-height: 0.6rem;
    text-align: center;
    text-decoration: none;
    color: #8b8b8b;
    display: inline-block;
    width: 31%;
    }
.otcListNav li>a{
        color: #999999;
	    font-size: 0.3rem;
    }
.otcListNav .otcListNav{    
	color: #488bef;
    border-bottom: 4px solid #488bef;}
   .otcListNav .otcListNav a{
           color: #506FC8;
    	font-size: 0.28rem;
    }
</style>
<header class="tradeTop">  
	  <!--  <i class="back toback2"></i> -->
	   <h2 class="tit">
	   <c:choose>
	   <c:when test="${sessionScope.login_user!=null && sessionScope.login_user.fismerchant==true }"> 
	   	   <a class="editLi" href="/advertisement/puborder.html"><img src="${oss_url}/static/mobile2018/images/edit.png"/>发布广告</a>
		   <span id="online" style="<c:if test="${sessionScope.online==true}"> display:none</c:if>"><img src="${oss_url}/static/mobile2018/images/of-line.png"/></span>
		   <span id="offline" style="<c:if test="${sessionScope.online==false}"> display:none</c:if>"><img src="${oss_url}/static/mobile2018/images/on-line.png"/></span>
	   </c:when>
	    <c:otherwise>
	    	<em><spring:message code="m.security.otccentre" /></em>
	    </c:otherwise>
	   </c:choose>
	   <%-- <em><spring:message code="m.security.otccentre" /></em> --%>
	   	<a href="/user/paytypeList.html" class="fr buyTit">
		   	<%-- <spring:message code="agent.accountsetting" /> --%>
		   	<img src="${oss_url}/static/mobile2018/images/icon_set.png"/>
	   	</a>
	   </h2>
</header>
<header class="l-header header_in">
    <div class="clear">
         <nav class="l-nav">
             <ul class="otcListNav" style="margin: 0 0.32rem;">
          		<li class="otcListNav"><a href="/advertisement/buyList.html"><spring:message code="agent.purchase" /></a></li>
                <li><a href="/advertisement/sellList.html"><spring:message code="agent.navsell" /></a></li>
                <c:if test="${sessionScope.login_user!=null}">
                 <li><a href="/order/orderList.html"><spring:message code="agent.otcorder" /></a></li>
                <!--  <li class="fl"></li> -->
                </c:if>
             </ul>
         </nav>
    </div>
    <audio id="sound" autoplay="autoplay"></audio>
</header>

<script type="text/javascript">
var headerpath = window.location.pathname;
var lis = $(".l-nav .otcListNav li");
if(headerpath == '/advertisement/buyList.html'){
  lis.eq(0).addClass("otcListNav").siblings().removeClass('otcListNav');
}else if(headerpath == "/advertisement/sellList.html"){
	lis.eq(1).addClass("otcListNav").siblings().removeClass('otcListNav');
}else if(headerpath == "/order/orderList.html"){
	lis.eq(2).addClass("otcListNav").siblings().removeClass('otcListNav');
}else if(headerpath == "/order/orderPageList.html"){
	lis.eq(2).addClass("otcListNav").siblings().removeClass('otcListNav');
}
</script>

<script type="text/javascript">

	var notification;
	$(function() {
		getOrderCount();
	});

	function notifyMe(count) {
		document.getElementById("sound").src = "${oss_url}/static/front2018/images/4123.wav";
		// Let's check if the browser supports notifications
		if (!("Notification" in window)) {
	//		alert("This browser does not support desktop notification");
		}

		// Let's check whether notification permissions have already been granted
		else if (Notification.permission === "granted") {
			// If it's okay let's create a notification
	//		document.getElementById("sound").src = "${oss_url}/static/front2018/images/4123.wav";
			notification = new Notification('状态更新提醒', {
				body : '有' + count + '个未处理订单请查收!',
				data : {
					url : '/order/orderList.html'
				},
				tag : 'linxin',
				icon : '${oss_url}/static/front2018/images/timg.jpg',
			});
		}

		// Otherwise, we need to ask the user for permission
		else if (Notification.permission !== "denied") {
			Notification.requestPermission(function(permission) {
				// If the user accepts, let's create a notification
				if (permission === "granted") {
		//			document.getElementById("sound").src = "${oss_url}/static/front2018/images/4123.wav";
					notification = new Notification('状态更新提醒',
					{
							body : '有' + count + '个未处理订单请查收!',
							data : {
								url : '/order/orderList.html'
							},
							tag : 'linxin',
							icon : '${oss_url}/static/front2018/images/timg.jpg',
					});

				}
			});
		} else {
	//		document.getElementById("sound").src = "${oss_url}/static/front2018/images/4123.wav";
			notification = new Notification('状态更新提醒', {
				body : '有' + count + '个未处理订单请查收!',
				data : {
					url : '/order/orderList.html'
				},
				tag : 'linxin',
				icon : '${oss_url}/static/front2018/images/timg.jpg',
			});

		}

		notification.onclick = function() {
			// window.open(notification.data.url, '_blank');      // 打开网址
			location.href = notification.data.url;// 打开网址
			notification.close();
		}
		setTimeout(function() {
			notification.close();
		}, 15000);
		// At last, if the user has denied notifications, and you 
		// want to be respectful there is no need to bother them any more.
	}

	function getOrderCount() {
		var url = "/order/allList.html";
		$.ajax({
			type : 'post',
			url : url,
			async : false,
			data : {},
			dataType : "json",
			success : function(result) {
				$("#newtitle").empty();

				var html = "";
				if (result.success == true && result.count != null
						&& result.count > 0) {
					html += '<a href="/trade/orderList.html"> <div class="newmassage" style="color:black;">';
					html += '<span class="title">提示</span>';
					html += '<span>你有' + result.count + '个未处理订单</span>';
					html += ' </div></a>';
					$("#newtitle").html(html);
					$("#newtitle").fadeIn(5000);
					notifyMe(result.count);
				} else {
					$("#newtitle").hide();

				}
				$("#newtitle").html(html);
			},
		});

		setTimeout("getOrderCount()", "25000");
	}
	/* 在线下线 */
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
</script>

