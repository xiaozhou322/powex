<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ include file="../comm/include.inc.jsp"%>

<!doctype html>
<html>
<head>
<%@ include file="../comm/basepath.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="../comm/link.inc.jsp"%>
<link href="${oss_url}/static/mobile2018/css/usdtTrade.css?v=20190301163300100" rel="stylesheet" type="text/css" />
<link href="${oss_url}/static/mobile2018/css/webchat/chat.css?v=12" rel="stylesheet" type="text/css" />

<style>
#alipay_pay,#weixin_pay,#card_pay{
    width: 0.3rem;
    height: 0.3rem;
    float: left;
    position: absolute;
    left: 0.3rem;
    top: 0.48rem;
}
.payType{
position: relative;
}
.reminds h1{  padding: 0 0 0.2rem 0;
    font-size: 0.28rem;
    color: #666666;
    border-bottom: 2px solid #f5f7ff;
    margin-bottom: 0.23rem;}
.reminds h1>img{vertical-align: -0.03rem;}
.order-flex{
    display: -webkit-box;
    display: -webkit-flex;
    display: -ms-flexbox;
    display: flex;
}
</style>
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
					}else if(to_user_id == from_id){
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
				}else if(to_user_id == from_id){
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
	

</head>
<body class="Ubody">
	<%@include file="../comm/header.jsp"%>
	<header class="tradeTop">  
     <i class="back toback2"></i>
    <span class="tit">${forderuser.frealName } 买入${ad.fvirtualcointype.fShortName}</span>
</header>
	<section class="usdtTrade">
		<input id="lasttime" value="${order.updateTime}" type="hidden">
		<input id="orderId" value="${order.id}" type="hidden">
		<input id="ismerchant" value="${fuser.fismerchant}" type="hidden">
		<input id="orderStatus" value="${order.orderStatus}" type="hidden">
		<input id="countdown" value="${countdown}" type="hidden">
		<input id="overtime" value="${order.overtime}" type="hidden">
		<input id="is_third" value="${order.is_third}" type="hidden">
		<div class="orderDetailMain">
			<div class="orderInfo">
				<%-- <div class="title clear">
					<span class="fl">买入${ad.fvirtualcointype.fShortName}</span> 
					<span class="fr cblue">&nbsp;&nbsp;>
						<c:if test="${countdown >0 }">
							<em id="minute_show">操作倒计时：&nbsp;--分</em><em id="second_show">&nbsp;--秒</em>
						</c:if>
					</span>
				</div> --%>
				<p class="order_num">
					<img src="${oss_url}/static/mobile2018/images/exchange/dindan2x.png" width="32" style=" vertical-align: -0.06rem; " />
					订单号：<span>${order.id}</span>
					<c:if test="${countdown >0 }">
						<span class="fr minute_span">
						<em id="minute_show">操作倒计时：&nbsp;--分</em><em id="second_show">&nbsp;--秒</em>
						</span>
					</c:if>
				</p>
				<ul class="clear list_tr">
					<li class="fl">
					<em class="buyAmount"><span>${order.totalPrice}</span> CNY</em>
					<span class="buyNum">交易金额</span> 
					</li>
					<li class="fl">
					<em class="buyAmount">${order.unitPrice}CNY/${ad.fvirtualcointype.fShortName}</em>
					<span class="buyNum">购买价格</span> 
					</li>
					<li class="fl textRight">
						<em class="buyAmount">
							${order.amount}
							${ad.fvirtualcointype.fShortName}
						</em>
						<span class="buyNum">购买数量</span> 
					</li>
				</ul>
			</div>
			<div class="orderZf">
				
			<c:if test="${order.orderStatus > 101 && order.orderStatus != 106}">
			<h1 class="order_num">
					<img src="${oss_url}/static/mobile2018/images/zhifu2x.png" width="24" alt="" />
					支付方式
				</h1>
				<c:if test="${empty order.payType}">
					<c:forEach items="${paytypeList}" var="pay">
						<c:if test="${pay.payType == 1 }">
	
							<div class="payType clear">
								<input type="radio" id="card_pay" name="paytype"
									value="${pay.id}"/>
								<dl class="clears fl" style="width: 100%;">
									<dt class="fl" >
										<img src="${oss_url}/static/mobile2018/css/agent/images/card.png"
											width="50" alt="" />
									</dt>
									<dd class="fl">
										<span>帐户名:&nbsp;&nbsp;${pay.realName}</span> <span>所属银行：${pay.bank}</span>
										<span>银行支行：${pay.bankBranch}</span> <span>银行账号：${pay.paymentAccount}</span>
									</dd>
								</dl>
							</div>
						</c:if>
						<c:if test="${pay.payType == 2 }">
							<div class="payType clear order-flex">
								<input type="radio" id="weixin_pay" name="paytype"
									value="${pay.id}" />
								<dl class="clears fl">
									<dt class="fl">
										<img src="${oss_url}/static/mobile2018/css/agent/images/wx.png"
											width="50" alt="" />
									</dt>
									<dd class="fl">
										<span>昵称：${pay.realName}</span> <span>账号：${pay.paymentAccount}</span>
									</dd>
								</dl>
								<div class="code">
									<img src="${pay.qrCode}" alt="" />
								</div>
							</div>
						</c:if>
						<c:if test="${pay.payType == 3 }">
	
							<div class="payType clear order-flex">
	
								<input type="radio" id="alipay_pay" name="paytype"
									value="${pay.id}" />
	
								<dl class="clears fl">
									<dt class="fl">
										<img src="${oss_url}/static/mobile2018/css/agent/images/jfb.png"
											width="50" alt="" />
									</dt>
									<dd class="fl">
										<span>昵称：${pay.realName}</span> <span>账号：${pay.paymentAccount}</span>
									</dd>
								</dl>
								<div class="code">
									<img src="${pay.qrCode}" alt="" />
								</div>
							</div>
						</c:if>
					</c:forEach>
				</c:if>
			</c:if>
			<c:if test="${not empty order.payType}">
				<c:if test="${order.payType == 1 }">
					<div class="payType clear">
						<dl class="fl clear" style="width: 100%;">
							<dt class="fl">
								<img src="${oss_url}/static/mobile2018/css/agent/images/card.png"
									width="50" alt="" />
							</dt>
							<dd class="fl">
								<span>帐户名:&nbsp;&nbsp;${order.realName}</span> <span>所属银行：${order.bank}</span>
								<span>银行支行：${order.bankBranch}</span> <span>银行账号：${order.paymentAccount}</span>
							</dd>
						</dl>
					</div>
				</c:if>
				<c:if test="${order.payType == 2 }">
					<div class="payType clear">
						<dl class="fl clear">
							<dt class="fl">
								<img src="${oss_url}/static/mobile2018/css/agent/images/wx.png"
									width="50" alt="" />
							</dt>
							<dd class="fl">
								<span>昵称：${order.realName}</span> <span>账号：${order.paymentAccount}</span>
							</dd>
						</dl>
						<div class="code">
							<img src="${order.qrCode}" alt="" />
						</div>
					</div>
				</c:if>
				<c:if test="${order.payType == 3 }">
					<div class="payType clear">
						<dl class="fl clear">
							<dt class="fl">
								<img src="${oss_url}/static/mobile2018/css/agent/images/jfb.png"
									width="50" alt="" />
							</dt>
							<dd class="fl">
								<span>昵称：${order.realName}</span> <span>账号：${order.paymentAccount}</span>
							</dd>
						</dl>
						<div class="code">
							<img src="${order.qrCode}" alt="" />
						</div>
					</div>
				</c:if>
			</c:if>
			
			</div>
			<div class="payNote order_payNote">
				<h1>
					<img src="${oss_url}/static/mobile2018/images/beizhu2x.png" width="24" alt="" />
					备注号:<span>${order.remark}</span>
				</h1>
                <c:if test="${fuser.fismerchant == true}">
                    <c:if test="${order.phone != null && order.phone !='' }"> 
                    	<p class="txt"><span id="telephone">客户联系方式：${order.phone}</span></p>
                    </c:if>
                </c:if>
				<div class="orderBtn"  id="buttonOper" style="background:#FFF;"></div>
				<p class="txt">
					<span style="color:#FF3333;font-size: 0.24rem;">
					<img src="${oss_url}/static/mobile2018/images/exchange/tishi2x.png" width="24" alt="" />
					重要提示：务必在付款信息中填写备注号，否则数字货币无法入账，且商户可能不会退款， 因此造成的损失由您自行承担，与平台无关。</span>
				</p>
			</div>
			<!-- 即时通讯 -->
			<div class="panel panel-default chatPanel" >
					<div class="panel-heading chat-title">
						<span class="ioncOrder">
							<c:if test="${fn:length(order.fotcAdvertisement.user.frealName)>=1}">
			                     ${fn:substring(order.fotcAdvertisement.user.frealName,0,1)}
			                </c:if>
			            </span>
			            <c:if test="${fuser.fismerchant == false}">
		                    <c:if test="${ad.user.ftelephone != null && ad.user.ftelephone !='' }"> 
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
									<span style="background-color: #F6F6F6;padding: 0.04rem 0.2rem;display: inline-block;margin-top: 10px;font-size: 0.16rem;border-radius: 0.1rem;">
										<c:if test="${fuser.fid==forderuser.fid}">您已成功下单，请及时支付。</c:if>
										<c:if test="${fuser.fid==order.fotcAdvertisement.user.fid}">您已成功接单，请及时支付。</c:if>
									</span>
								</div>
							</div>
						</div>
						<div class="typeBody">
							<input type="text" class="form-control" id="sendContent" placeholder="请输入消息...">
							<button type="button" class="send-msg">发送</button>
						</div>
					</div>
				</div>
			<!-- END即时通讯 -->
			<div class="care reminds">
				<h1>
					<img src="${oss_url}/static/mobile2018/images/jiaoyi2x.png" width="24" alt="" />
					交易提示
				</h1>
				<p>1、您的汇款将直接进入卖方账户，交易过程中卖方出售的数字资产将由平台暂时托管。</p>
				<p>2、请在规定时间内完成付款，否则会导致订单取消，并务必点击“我已付款”，否则会影响数字资产到账，卖方确认收款后，系统会将数字资产划转到您的账户。</p>
				<p>3、买方当日取消达5笔，会限制当天的买入功能。</p>
			</div>
			<span class="getCon" style="display: none"></span>
		</div>
	</section>
	
	<form id="pageJumpForm" method="post" action="/otc/pageJump.html">
		<input type="hidden" id="orderId" name="orderId" value="${order.id}"/>
	</form>
	
	<%@include file="../comm/footer.jsp"%>
	<script type="text/javascript"	src="${oss_url}/static/mobile2018/js/fluckydraw/com.js?v=20181126201750"></script>
	<script type="text/javascript" src="${oss_url}/static/mobile2018/js/newagent/buyUserOrderDetail.js?v=201808211850"></script>
	<script type="text/javascript">
$(function(){
	/*鼠标点击内容的时候，选中radio按钮*/
	$(".payType").click(function(){
		$(".payType").css("background","white");//先把所有的变成白色
		$(this).css("background","#f0f0f0");//选中了就把点击的变成灰色
		var $radio=$(this).find("input[type=radio]"),
		$flag=$radio.is(":checked");
		if(!$flag){//判断是否选中
			$radio.prop("checked",true);
		}
		})
	})
	

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
      //   systemCancel();
         
      //   setTimeout('var curUrl = window.location.href;window.location.href = curUrl;', 500);
         
    }
    if (minute <= 9) minute = '0' + minute;
    if (second <= 9) second = '0' + second;
    $('#minute_show').html('<s></s>操作倒计时：&nbsp;<span style="color:red;">'+minute+ language["agent.m"]+'</span>');
    $('#second_show').html('<s></s>&nbsp;<span style="color:red;">'+second+ language["agent.s"]+'</span>');
    intDiff--;
    }, 1000);
}
</c:if>

var queryTimer;
window.onload=function(){
    queryTimer = setInterval('queryOtcOrder()',2000); //每2秒刷新一次页面下边显示的数据
    
    getOrder();
    console.log(getServerTime());
}

var isClick = false;
//确认订单
function confirmOrder()
{
	<c:if test="${empty order.payType}">
	var paytype = $("input[name='paytype']:checked");
    if(null == paytype.val() || paytype.val() == '') {
    	util.layerAlert("", "请选择支付方式", 2);
    	return;
    }
    util.layerConfirm("您确定选择该支付方式？", function () {
		if(isClick) {
			return ;
		}
		isClick= true;
		var orderId = '${order.id}';
		var param = {'orderId':orderId, 'payType':paytype.val(), 'orderStatus':103};
		jQuery.post('/order/receiveOrPayOrder.html', param, function (data) {
		  
	    	if (data.code == -1) {
				util.layerAlert("", data.msg, 2);
			} else {
				util.layerAlert("", data.msg, 1);
			}
	    }, "json");
    });
    </c:if>
    <c:if test="${not empty order.payType}">
    util.layerConfirm("您确定已付款？", function () {
		if(isClick) {
			return ;
		}
		isClick= true;
		var orderId = '${order.id}';
		var param = {'orderId':orderId, 'orderStatus':103};
		jQuery.post('/order/receiveOrPayOrder.html', param, function (data) {
		  
	    	if (data.code == -1) {
				util.layerAlert("", data.msg, 2);
			} else {
				util.layerAlert("", data.msg, 1);
			}
	    }, "json");
    });
    </c:if>
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
		var param = {'orderId':orderId};
		jQuery.post('/order/failedOrder.html', param, function (data) {
		  
	    	if (data.code == -1) {
				util.layerAlert("", data.msg, 2);
			} else {
				//clearInterval(queryTimer);
				window.location.reload();
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
	jQuery.post('/otc/processorder.html', param, function (data) {
	  
    	if (data.code == -1) {
    		util.layerAlert("", data.msg, 2);
		} else {
			// clearInterval(queryTimer);
			 window.location.reload();
		}
    }, "json");
}

var errortime = 0;

function queryOtcOrder() {
	var orderId = $("#orderId").val();
	var orderStatus = $("#orderStatus").val();
	var overtime = $("#overtime").val();
	var is_third = $("#is_third").val();
	var ismerchant = $("#ismerchant").val();
	console.log(ismerchant);
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
        		
        		if(data.orderStatus == 106 || data.orderStatus == 107) {    //106:失败     107：成功
        			clearInterval(queryTimer);
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
        		util.layerAlert("", "网络异常请刷新页面", 2);
        	//	window.location.reload();
        	}
        }
    });
}

</script>
</body>
</html>
