<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ include file="../comm/include.inc.jsp"%>

<!doctype html>
<html>
<head> 
<%@ include file="../comm/basepath.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="../comm/link.inc.jsp" %>
<link href="${oss_url}/static/front2018/css/usdtTrade.css" rel="stylesheet" type="text/css" />
<link href="${oss_url}/static/front2018/css/webchat/chat.css?v=1" rel="stylesheet" type="text/css" />
<link href="${oss_url}/static/front2018/css/otc.css" rel="stylesheet" type="text/css" />

</head>
<body class="">

	<script type="text/javascript" src="${oss_url}/static/front/js/tradingview/socket.js"></script>
	<script type="text/javascript">
	var configJSON = '${oss_url}/static/front/config/config.json?t='+new Date().getTime();
	
	var wsUrl;
    $.ajax({
 	   url: configJSON,//json文件位置
 	   type: "GET",//请求方式为get
 	   async: false, //请求是否异步，默认为异步，这也是ajax重要特性   
 	   dataType: "json", //返回数据格式为json
 	   success: function(data) {//请求成功完成后要执行的方法 
           console.log('chat_ws_url='+data.chat_ws_url);
 		   wsUrl = data.chat_ws_url;
 	   }
 	})
 	
 	$(function(){
		var user_id = "${fuser.fid}";
		var user_real_name = "${fuser.frealName}";
		var ad_user_id = "${order.fotcAdvertisement.user.fid}";
		var forderuser_id = "${forderuser.fid}";
		var to_user_id = '';
		var to_user_real_name = '';
		if(user_id == ad_user_id){
			to_user_id = forderuser_id;
			to_user_real_name = "${forderuser.frealName}";
		}else{
			to_user_id = ad_user_id;
			to_user_real_name = "${order.fotcAdvertisement.user.frealName}";
		}
		
		if(user_real_name.length>=1){
			user_real_name = user_real_name.substring(0,1);
		}
		if(to_user_real_name.length>=1){
			to_user_real_name = to_user_real_name.substring(0,1);
		}
		var webchat_socket = new socket(wsUrl);
		webchat_socket.on('message', function(result){
			console.log(result);
			if('ping' == result.cmd){
				return;
			}
			var html = '';
			if('getmsg' == result.cmd){
				result.args.forEach(function(value,index){
					var from_id = result.args[index].fromId;
					var send_time = new Date(result.args[index].sendTime).toTimeString().substring(0,8);
					if(user_id == from_id){
						html += '<div class="chat-box"><div class="chat-time"><span class="chat-sys-content">'+send_time+'</span></div>'
							+'<div class="sigleMsg"><span class="fl-right iconTag">'+user_real_name+'</span>'
							+'<div class="chat-box-right"><p>'+result.args[index].content+'</p></div></div></div>';
					}else if(to_user_id ==from_id){
						html += '<div class="chat-box2"><div class="chat-time"><span class="chat-sys-content">'+send_time+'</span></div>'
							+'<div class="sigleMsg"><span class="iconTag">'+to_user_real_name+'</span>'
							+'<div class="chat-box-left"><p>'+result.args[index].content+'</p></div></div></div>';
					}
				});
			}else if('sendmsg' == result.cmd){
				var from_id = result.args[0];
				var send_time = new Date(result.args[3]).toTimeString().substring(0,8);
				if(user_id == from_id){
					html = '<div class="chat-box"><div class="chat-time"><span class="chat-sys-content">'+send_time+'</span></div>'
						+'<div class="sigleMsg"><span class="fl-right iconTag">'+user_real_name+'</span>'
						+'<div class="chat-box-right"><p>'+result.args[result.args.length-1]+'</p></div></div></div>';
				}else if(to_user_id ==from_id){
					html = '<div class="chat-box2"><div class="chat-time"><span class="chat-sys-content">'+send_time+'</span></div>'
						+'<div class="sigleMsg"><span class="iconTag">'+to_user_real_name+'</span>'
						+'<div class="chat-box-left"><p>'+result.args[result.args.length-1]+'</p></div></div></div>';
				}
			}
			$(".msgBody").append(html);
			$(".msgBody").animate({scrollTop:$(".msgBody")[0].scrollHeight},300);
		});
		webchat_socket.on('close', function(){
			webchat_socket.doOpen();
			webchat_socket.on('open', function() {
		        console.log(' >> : 已重连')
		    });
		});
		if (!webchat_socket.checkOpen()) {
			webchat_socket.doOpen();
		}
		webchat_socket.send({
				cmd: 'getmsg',
	        	id: $("#orderId").val()
	        });
		//$(".msgBody").mCustomScrollbar();
		//$(".msgBody").animate({scrollTop:$(".msgBody")[0].scrollHeight},300);
	
	      /* 点击发送 */
		$(".send-msg").click(function(){
			var msgContent = $("#sendContent").val();
			if('' == msgContent){
				return;
			}
			if(msgContent.length>80)
			{
				 util.layerAlert("", "消息内容最多不能大于80字符", 0);
	             return;
			}
			var to_user_id = '';
			if(user_id == ad_user_id){
				to_user_id = forderuser_id;
			}else{
				to_user_id = ad_user_id;
			}
			var param = {
			        cmd: 'sendmsg',
			        args: [user_id,to_user_id,1,new Date().getTime(),msgContent],
			        id: $("#orderId").val()
			    };
			if (!webchat_socket.checkOpen()) {
				webchat_socket.doOpen();
			}
			webchat_socket.send(param);
			$("#sendContent").val('');
		});
		/* 回车发送消息 */
		$('#sendContent').bind('keyup', function(event) {
	        if (event.keyCode == "13") {
	            //回车执行查询
	            $('.send-msg').click();
	        }
	    });
	});
	</script>
	

<c:if test="${sessionScope.third_user ==null}">
<%@include file="../comm/newotc_header.jsp" %>
</c:if>
<c:if test="${sessionScope.third_user !=null}">
<%@include file="../comm/otc_header.jsp" %>
</c:if>
<section class="uTrade mg">
		<input id="lasttime" value="${order.updateTime}" type="hidden">
		<input id="orderId" value="${order.id}" type="hidden">
		<input id="ismerchant" value="${fuser.fismerchant}" type="hidden">
		<input id="orderStatus" value="${order.orderStatus}" type="hidden">
		<input id="countdown" value="${countdown}" type="hidden">
		<input id="overtime" value="${order.overtime}" type="hidden">
		<input id="is_third" value="${order.is_third}" type="hidden">
        <div class="orderMain">
        
        <c:if test="${countdown >0 }">
        <div class="fragent">
        <em id="minute_show">操作倒计时：&nbsp;--<spring:message code="agent.m" /></em><em id="second_show">&nbsp;--<spring:message code="agent.s" /></em>
        </div>
        </c:if>
            <div class="orderidTit">
                <p class="fl"><spring:message code="agent.orderid" />：<span>${order.id}</span>&nbsp;&nbsp;</p>
                <p class="fl">${forderuser.frealName} <spring:message code="market.sell" />${ad.fvirtualcointype.fShortName}</p>
            </div>
            
            
            <div class="orderCon clear">
            	<div class="fl">
                    <ul class="priceLi">
                    	<li>
                    		<label><spring:message code="agent.totalprice" />：</label>
                    	 	<span class='colorBlue'>${order.totalPrice}</span><em class="colorBlue">CNY</em>
                    	</li>
                    	<li>
                    		<label><spring:message code="agent.gprice" />：</label>
                         	<span class='colorBlue'>${order.unitPrice}</span><em class="colorBlue">CNY/${ad.fvirtualcointype.fShortName}</em>
                    	</li>
                    	<li>
                    		<label><spring:message code="agent.quantity" />：</label>
                         	<span class='colorBlue'>${order.amount}</span><em class="colorBlue">${ad.fvirtualcointype.fShortName}</em>
                    	</li>
                    </ul>
                    <h3 style="font-size: 14px;">
                         <spring:message code="agent.remarknumber" />：
                         <span style="color:red;font-size:18px;">${order.remark}</span>
                   	</h3>
                    <div class="uTradeH3"><spring:message code="agent.important.receipt" /></div>
	            	<div class="payHover">
	                    <!-- 未支付展示 -->
	                   <c:if test="${empty order.payType}">
	                    请选择支付方式：
	                    <c:forEach items="${paytypeList}" var="pay"> 
	                    	<c:if test="${pay.payType == 1 }">
	                        <div class="orderAmount noBoder">
	                            <div class="fr payType">
	                                <dl class="fl clear">
	                                    <dt class='fl'><img src="${oss_url}/static/front2018/images/card.png" alt="" /></dt>
	                                    <dd class='fl'>
	                                       <span>${pay.realName}</span>
	                                        <span>${pay.bank}</span>
	                                        <span>${pay.bankBranch}</span>
	                                        <span>${pay.paymentAccount}</span>
	                                    </dd>
	                                </dl>
	                            </div>
	                        </div>   
	                        </c:if>   
	                        <c:if test="${pay.payType == 2 }">
	                            <div class="otc_pay clear">
	                                <dl class="fl payCode">
	                                    <dt class='fl orderImg'><img src="${oss_url}/static/front2018/images/wx.png" alt="" /></dt>
	                                    <dd class='fl'>
	                                       <span><spring:message code="userinfo.nickname"/>${pay.realName}</span>
	                                        <span><spring:message code="userinfo.account"/>${pay.paymentAccount}</span>
	                                    </dd>
	                                    
	                                     <dd class="hCode fl"><img src="${oss_url}/static/front2018/images/hCode.png" alt="" /></dd>
	                                    <dd>
		                                    <div class="hoverPic" style="width: 220px;margin-top: 10px;left: 400px;z-index:99;">
			                                    <img src="${pay.qrCode}" alt="" />
			                                </div>
		                                </dd>
	                                </dl>
	                            </div> 
	                        </c:if>    
	                        <c:if test="${pay.payType == 3 }"> 
	                            <div class="otc_pay clear">
	                            	<dl class="fl payCode">
	                                    <dt class='fl orderImg'><img src="${oss_url}/static/front2018/images/jfb.png" alt="" /></dt>
	                                    <dd class='fl'>
	                                        <span><spring:message code="userinfo.nickname"/>${pay.realName}</span>
	                                        <span><spring:message code="userinfo.account"/>${pay.paymentAccount}</span>
	                                    </dd>
	                                    <dd class="hCode fl"><img src="${oss_url}/static/front2018/images/hCode.png" alt="" /></dd>
		                                <dd>
			                              <div class="hoverPic" style="width: 220px;margin-top: 10px;left: 400px;z-index:99;">
			                                <img src="${pay.qrCode}" alt="" />
			                              </div>
			                            </dd>
	                                </dl>
	                            </div>
	                         </c:if>                     
	                        </c:forEach>
	                   </c:if>
	                   <c:if test="${not empty order.payType}">
	                    <c:if test="${order.payType == 1 }">
	                          <div class="otc_pay clear">
	                              <dl class="fl">
	                                  <dt class='fl orderImg'><img src="${oss_url}/static/front2018/images/card.png" alt="" /></dt>
	                                  <dd class='fl'>
	                                      <span>${order.realName}</span>
	                                      <span>${order.bank}</span>
	                                      <span>${order.bankBranch}</span>
	                                      <span>${order.paymentAccount}</span>
	                                  </dd>
	                                  
	                              </dl>
	                          </div>
	                        </c:if>   
	                    <c:if test="${order.payType == 2 }">
	                        <div class="otc_pay clear">
	                            <dl class="fl payCode">
	                                <dt class='fl orderImg'><img src="${oss_url}/static/front2018/images/wx.png" alt="" /></dt>
	                                <dd class='fl'>
	                                    <span><spring:message code="userinfo.nickname"/>${order.realName}</span>
	                                    <span><spring:message code="userinfo.account"/>${order.paymentAccount}</span>
	                                </dd>
	                                <dd class="hCode fl"><img src="${oss_url}/static/front2018/images/hCode.png" alt="" /></dd>
	                                <dd>
	                                 <div class="hoverPic" style="width: 220px;margin-top: 10px;left: 400px;z-index:99;">
	                                  <img src="${order.qrCode}" alt="" />
	                              </div>
	                             </dd>
	                            </dl>
	                        </div>  
	                    </c:if>    
	                    <c:if test="${order.payType == 3 }">
	                        <div class="otc_pay clear">
	                            <dl class="fl payCode">
	                             <dt class='fl orderImg'><img src="${oss_url}/static/front2018/images/jfb.png" alt="" /></dt>
	                             <dd class='fl'>
	                               <span><spring:message code="userinfo.nickname"/>${order.realName}</span>
	                               <span><spring:message code="userinfo.account"/>${order.paymentAccount}</span>
	                             </dd>
	                             <dd class="hCode fl"><img src="${oss_url}/static/front2018/images/hCode.png" alt="" /></dd>
	                             <dd>
	                           <div class="hoverPic" style="width: 220px;margin-top: 10px;left: 400px;z-index:99;">
	                             <img src="${order.qrCode}" alt="" />
	                           </div>
	                          </dd>
	                            </dl>
	                        </div>  
	                     </c:if>
	                   </c:if>
	               </div>
                    <%-- <h1><spring:message code="agent.remarknumber" />：<p style="color: red; font-size: 36px">${order.remark}</p></h1>
                    <p class="txt"><span style="color: red; font-size: 16px"><spring:message code="agent.important.receipt" /></span></p> --%>
                    
	                <div class="orderBtn clear" id="buttonOper"></div>
                <!-- 底部提示 -->
                <div class="remindCon clear">
                   <h3><i></i><spring:message code="agent.tradingtips" /></h3>
                   <p>1、<spring:message code="agent.tipone" /></p>
                   <p>2、<spring:message code="agent.tiptwo" /></p>
                   <p>3、<spring:message code="agent.tipthree" /></p>
                </div>
              </div>
  			<!-- 即时通讯 -->
			  <div class="fr">
				<div class="panel panel-default chatPanel">
					<div class="panel-heading chat-title">
						<span class="ioncOrder">
							<c:if test="${fn:length(forderuser.frealName)>=1}">
			                     ${fn:substring(forderuser.frealName,0,1)}
			                </c:if>
						</span>
						<c:if test="${ad.user.ftelephone != null && ad.user.ftelephone !='' }"> 
		                    <c:if test="${fuser.fismerchant == false}">
		                    	<span id="telephone"><spring:message code="order.phone"/><br>${ad.user.ftelephone}</span>
		                    </c:if>
	                    </c:if>
	                    <c:if test="${fuser.fismerchant == true}">
		                    <c:if test="${order.phone != null && order.phone !='' }"> 
		                    	<span id="telephone">客户联系方式<br>${order.phone}</span>
		                    </c:if>
	                    </c:if>
						
					</div>
					<div class="chatBody">
						<div class="msgBody">
							<div class="sigleMsg">
								<div class="chat-sys">
									<span class="chat-sys-content">${fn:substring(order.createTime,0,19)}</span>
									<div></div>
									<span style="background-color: #F6F6F6; padding: 2px 10px; display: inline-block; margin-top: 10px;">
										<c:if test="${fuser.fid==forderuser.fid}">您已成功下单，请及时处理。</c:if>
										<c:if test="${fuser.fid==order.fotcAdvertisement.user.fid}">您已成功接单，请及时处理。</c:if>
									</span>
								</div>
							</div>
						</div>
						<div class="typeBody">
							<div class="input-group">
								<input type="text" class="form-control" id="sendContent" placeholder="请输入消息...">
								<span class="input-group-btn send-msg">
									<button class="btn btn-success" style="width:54px;height:34px;" type="button">发送</button>
								</span>
							</div>
						</div>
					</div>
				</div>
			 </div>
          <!-- end即时通讯 -->
            </div>
        </div>
       <%-- <%@include file="../agent/chatbox.jsp" %> --%>	
    </section>


    
<form id="pageJumpForm" method="post" action="/otc/pageJump.html">
	<input type="hidden" id="orderId" name="orderId" value="${order.id}"/>
</form>	
	
	<div class="goTop">
		<!-- 返回顶部 -->
	</div>

	<c:if test="${sessionScope.third_user ==null}">
	<%@include file="../comm/footer.jsp" %>
	</c:if>
	<c:if test="${sessionScope.third_user !=null}">
	<%@include file="../comm/otc_footer.jsp"%>
	</c:if>
	<script type="text/javascript"
		src="${oss_url}/static/front2018/js/comm/msg.js"></script>
		<script type="text/javascript" src="${oss_url}/static/front2018/js/newagent/sellUserOrderDetail.js?v=201808211850"></script>
	<script type="text/javascript"
		src="${oss_url}/static/front2018/js/plugin/jquery.qrcode.min.js"></script>
</body>
</html>

<script type="text/javascript">

//从response header中获取服务器当前时间,不存在有缓存时的问题
function getServerTime(){
     var severtime=null;
     $.ajax({
         async: false,
         type: "POST",//get 方式猎豹有问题
         success: function(result, status, xhr) {
        	 severtime = xhr.getResponseHeader("Date");
         }
   	 });
     
     var date = new Date(severtime);  
     severtime=date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate() + ' ' + date.getHours() + ':' + date.getMinutes() + ':' + date.getSeconds();
     return severtime;
}

<c:if test="${ countdown gt 0}">            
//输出实时时间
var newTimess = parseInt(Date.parse(getServerTime().replace(/\-/g, "/"))/1000);     
var Dtime=$("#lasttime").val();//内容为时间2018-02-12 11:36:47.0
var nowTime=new Date(Date.parse(Dtime.slice(0,Dtime.indexOf(".")).replace(/-/g,"/")));
var startTime=nowTime.getTime()/1000;//将获取的时间格式转换为时间戳格式
if((newTimess-startTime) > ${countdown}){
	queryOtcOrder();//倒计时结束，查询订单状态
}else{
    startTime = startTime + ${countdown};
    var setT = startTime - newTimess;
    timer(setT);
}
function p(s) {
    return s < 10 ? '0' + s: s;
}
function timer(intDiff){
    window.setInterval(function(){
    var day=0,
        hour=0,     
        minute=0,
        second=0;//时间默认值        
    if(intDiff > 0){
        day = Math.floor(intDiff / (60 * 60 * 24));
        hour = Math.floor(intDiff / (60 * 60)) - (day * 24);
        minute = Math.floor(intDiff / 60) - (day * 24 * 60) - (hour * 60);
        second = Math.floor(intDiff) - (day * 24 * 60 * 60) - (hour * 60 * 60) - (minute * 60);
    }else{
        // systemCancel();
         
         //setTimeout('var curUrl = window.location.href;window.location.href = curUrl;', 500);
         
    }
    if (minute <= 9) minute = '0' + minute;
    if (second <= 9) second = '0' + second;
    $('#minute_show').html('<s></s>操作倒计时：&nbsp;'+minute+ language["agent.m"]);
    $('#second_show').html('<s></s>&nbsp;'+second+ language["agent.s"]);
    intDiff--;
    }, 1000);
}
</c:if>

var queryTimer;
window.onload=function(){
    queryTimer = setInterval('queryOtcOrder()',2000); //每2秒刷新一次页面下边显示的数据
    getOrder();
    $(".payCode").hover(function(){
    	$(this).find('.hoverPic').show();
    },function(){
    	$(this).find('.hoverPic').hide();
    });
}
var isClick = false;
//确认订单
function confirmOrder()
{
	util.layerConfirm("确认收款吗？", function () {
		if(isClick) {
			return ;
		}
		isClick= true;
		var orderId = '${order.id}';
		var param = {'orderId':orderId, 'orderStatus':104};
		jQuery.post('/order/receiveOrPayOrder.html', param, function (data) {
	    	if (data.code == -1) {
				util.layerAlert("", data.msg, 2);
			} else {
				util.layerAlert("", data.msg, 1);
			}
	    }, "json");
    });
}


//二次确认订单
function confirmOrderSecond()
{
	util.layerConfirm("二次确认收款吗？", function () {
		if(isClick) {
			return ;
		}
		isClick= true;
		var orderId = '${order.id}';
		var param = {'orderId':orderId, 'orderStatus':107};
		jQuery.post('/order/sucessOrder.html', param, function (data) {
	    	if (data.code == -1) {
				util.layerAlert("", data.msg, 2);
			} else {
				util.layerAlert("", data.msg, 1);
			}
	    }, "json");
    });
}

//买家申诉
function appealOrder()
{
	util.layerConfirm("您确定申诉吗？", function () {
		if(isClick) {
			return ;
		}
		isClick= true;
		var orderId = '${order.id}';
		var param = {'orderId':orderId};
		jQuery.post('/order/appeal.html', param, function (data) {
		  
	    	if (data.code == -1) {
				util.layerAlert("", data.msg, 2);
			} else {
				util.layerAlert("", data.msg, 1);
			}
	    }, "json");
    });
}

 //手动取消
function cancelOrder()
{
	util.layerConfirm("您确定取消该订单吗？", function () {
		if(isClick) {
			return ;
		}
		isClick= true;
		
		var orderId = '${order.id}';
		var param = {'orderId':orderId,'orderDesc':'cancel'};
		jQuery.post('/order/failedOrder.html', param, function (data) {
		  
	    	if (data.code == -1) {
				util.layerAlert("", data.msg, 2);
				
			} else {
				clearInterval(queryTimer);
				window.location.reload();
			}
	    }, "json");
    });
}
 //系统取消
function systemCancel()
{
	if(isClick) {
		return ;
	}
	isClick= true;
	
	var orderId = '${order.id}';
	var param = {'orderId':orderId,'orderDesc':'systemcancel'};
	jQuery.post('/otc/processout.html', param, function (data) {
	  
    	if (data.code == -1) {
    		util.layerAlert("", data.msg, 2);
		} else {
			 //clearInterval(queryTimer);
			 window.location.reload();
		}
    }, "json");
}
 
var errortime = 0;

function queryOtcOrder() {
	var orderId = '${order.id}';
	var orderStatus = $("#orderStatus").val();
	var overtime = $("#overtime").val();
	var is_third = $("#is_third").val();
	var ismerchant = $("#ismerchant").val();
    var url = "/order/queryOtcOrder.html?random=" + Math.round(Math.random() * 100);
	$.ajax({
        url: url,
        type: "POST",
        data: {orderId:orderId},
        cache: false,
        dataType: "json",
        success: function(data) {
        	if (data.code == 0) {
        		if(data.orderStatus != orderStatus || (null != overtime && "" != overtime && data.overtime != overtime)) {
        			window.location.reload();
        			$("#orderStatus").val(data.orderStatus);
        			$("#overtime").val(data.overtime);
        		}
        		if(data.orderStatus == 106 || data.orderStatus == 107) {   //106:失败     107：成功
        			clearInterval(queryTimer);
        			var order_update_time = "${fn:substring(order.updateTime,0,19)}";
        			var end_html = '<div class="sigleMsg"><div class="chat-sys">'
        				+'<span class="chat-sys-content">'+order_update_time+'</span>'
						+'<div></div><span>订单已结束。如有疑问，请联系客服。</span></div></div>';
        			$(".msgBody").append(end_html);
    				$(".msgBody").animate({scrollTop:$(".msgBody")[0].scrollHeight},300);
        			if(data.orderStatus == 107) {
	        			if(is_third == 1 && ismerchant == "false") {
	        				//页面通知
	        				if("1"!="${isCallSucc}"){
	        			    	$("#pageJumpForm").submit();
	        				}
	        			} else {
	        				window.location.href='/order/orderList.html';
	        			}
        			}
        		}
			}
        },
        error: function(jqXHR, textStatus, errorThrown){
        	errortime++;
        	if (errortime>=20){
       			clearInterval(queryTimer);
        		util.layerAlert("", "网络异常请检查网络状况后重新刷新页面", 2);
        	//	window.location.reload();
        	}
        }
    });
}

 
</script>