<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ include file="../comm/include.inc.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;if (request.getServerName().equals("www.gbcax.com")){basePath="https://www.gbcax.com";}
%>
<!doctype html>
<html>
<head> 
<base href="<%=basePath%>" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="../comm/link.inc.jsp" %>

<link href="${oss_url}/static/mobile2018/css/usdtTrade.css?v=1" rel="stylesheet" type="text/css" />

</head>
<body class="Ubody">
<%@include file="../comm/header.jsp" %>
<header class="tradeTop">  
    <i class="back toback2"></i>
    <h2 class="tit">订单详情</h2>
</header>
    <section class="usdtTrade">
        <div class="orderDetailMain">
            <div class="TitBread clear">
                订单管理<a href="javascript:;">订单详情safsdf</a>
            </div>
            <div class="orderInfo">
                <div class="title clear">
                    <span class="fl">卖出USDT</span>
                    <span class="fr cblue times times2">${fagentoperation.fstatus_s}  <c:if test="${fagentoperation.fstatus == 1 }"><em id="minute_show">00分</em><em id="second_show">00秒</em></c:if></span>
                </div>
                <p style="padding-left:0.3rem;">订单号：<span>${fagentoperation.fid}</span></p>
                <ul class="clear">
                    <li class="fl">
                        <span class="buyNum">交易金额</span>
                        <em class="buyAmount">${fagentoperation.ftotalcny} CNY</em>
                    </li>
                    <li class="fl">
                        <span class="buyNum">出售价格</span>
                        <em class="buyAmount">${fagentoperation.fprice} CNY/USDT</em>
                    </li>
                    <li class="fl textRight">
                        <span class="buyNum">购买数量</span>
                        <em class="buyAmount">${fagentoperation.famount-fagentoperation.ffees} USDT</em>
                    </li>
                </ul>
            </div>
            <c:if test="${fpayaccount.openalipay == 1 }">
             <div class="payType clear">
                <dl class="fl clear">
                    <dt class="fl"><img src="${oss_url}/static/mobile2018/css/agent/images/jfb.png" width="50" alt="" /></dt>
                    <dd class="fl">
                        <span>${fpayaccount.aliname} </span>
                        <span>${fpayaccount.aliid}</span>
                    </dd>
                </dl>
                <div class="code fr">
                    <img src="${fpayaccount.alipay}" alt="" />
                </div>
            </div>
            </c:if>  
            <c:if test="${fpayaccount.openweixin == 1 }"> 
             <div class="payType clear">
                <dl class="fl clear">
                    <dt class="fl"><img src="${oss_url}/static/mobile2018/css/agent/images/wx.png" width="50" alt="" /></dt>
                    <dd class="fl">
                        <span>${fpayaccount.weixinname} </span>
                        <span>${fpayaccount.weixinid}</span>
                    </dd>
                </dl>
                <div class="code fr">
                    <img src="${fpayaccount.weixin}" alt="" />
                </div>
            </div>
            </c:if> 
            <c:if test="${fpayaccount.openalipay == 1 }">
            <div class="payType clear">
                <dl class="fl clear">
                    <dt class="fl"><img src="${oss_url}/static/mobile2018/css/agent/images/card.png" width="50" alt="" /></dt>
                    <dd class="fl">
                        <span>${fpayaccount.fbankperson}    ${fpayaccount.fbankname} </span>
                        <span>${fpayaccount.fbankothers} ${fpayaccount.fbanknumber}</span>
                    </dd>
                </dl>
            </div>
            </c:if>
            <div class="payNote">
                <h1>备注号<p>${fagentoperation.fremark}</p></h1>
                <p class="txt"><span class="cblue">备注号：</span>请务必备注在付款信息中，便于收款方确认款项。</p>
                <c:if test="${fagentoperation.fstatus < 3 }">
                    <div class="orderBtn clear">
                    <c:if test="${is_agent == 0 }">
                     <c:if test="${fagentoperation.fstatus == 2 }">   
                    <span class="btn fl" onclick="confirmOrder()">
                        确认收款
                        <em class="ts" >确认收款后请点击“确认收款”</em>
                    </span>
                    </c:if>
                    <c:if test="${fagentoperation.fstatus == 1 }"><span class="btn btn_2 fr" onclick="cancelOrder()">取消交易</span></c:if>
                     </c:if>
                    <c:if test="${is_agent == 1 && fagentoperation.fstatus == 1 }">
                        <span class="btn fl" onclick="confirmOrder()" >
                                                                                                          我已付款
                                    <em class="ts">付款成功后请点击“我已付款”</em>
                                </span>
                                <span class="btn btn_2 fr" onclick="cancelOrder()" >取消交易</span>
                    </c:if> 
                </div>
                </c:if>
            </div>
            <div class="care reminds">
                <em class="cred2">交易提示</em><br>
                <p>您的汇款将直接进入卖方账户，交易过程中卖方出售的数字资产由平台托管保护。</p>
                <p>请在规定时间内完成付款，并务必点击“我已付款”，卖方确认收款后，系统会将数字资产划转到您的账户。</p>
            </div>
            <span class="getCon" style="display: none"></span>
        </div>
        <div class="chatMain" style="display:none;">
            <div class="chatTit">
                <div class="title clear">
                    <h2 class="fl">聊天</h2>
                    <span class="cblue fr">2018-02-09</span>
                </div>
                <div class="chatBox">
                    <div class="chatCon"></div>
                </div>
            </div>
            <div class="reminds care" style="display:none;">
                <em class="cred2"><i></i>买家注意</em><br>
                <p>请不要使用其他聊天软件与对方沟通，
                    更不要接受对方向你发送的任何文件、
                    邮箱附件等，所有沟通环节，请都在
                    本页面的聊天窗口完成.</p>
            </div>
        </div>
    </section>
<%@include file="../comm/footer.jsp" %>	
<input id="lasttime" value="${fagentoperation.fLastUpdateTime}" type="hidden">
<script type="text/javascript">
$(".back").click(function(){
    window.history.go(-1);
});

    $(".getCon").click(function(event) {
        $(this).hide();
        $(".chatMain").show(20, function() {
            var topH=$(".chatMain").offset().top
            $('body,html').animate({'scrollTop':topH-80},200)
        });;

    });
    //确认
    function confirmOrder()
    {
    	var order_id = '${fagentoperation.fid}';
    	var param = {'order_id':order_id,'ac':'confirm'};
    	jQuery.post('/agent/processout.html', param, function (data) {
		  
        	if (data.code == -1) {
				util.layerAlert("", data.msg, 2);
				
			} else {
				util.layerAlert("", data.msg, 1);
			}
        }, "json");
    }
     //确认
    function cancelOrder()
    {
    	var order_id = '${fagentoperation.fid}';
    	var param = {'order_id':order_id,'ac':'cancel'};
    	jQuery.post('/agent/processout.html', param, function (data) {
		  
        	if (data.code == -1) {
				util.layerAlert("", data.msg, 2);
				
			} else {
				util.layerAlert("", data.msg, 1);
			}
        }, "json");
    }
   function systemCancel()
   	{
   		var order_id = '${fagentoperation.fid}';
    	var param = {'order_id':order_id,'ac':'systemcancel'};
    	jQuery.post('/agent/processout.html', param, function (data) {
		  
        	if (data.code == -1) {
				util.showMsg("", data.msg, 2);
				
			} else {
				util.showMsg("", data.msg, 1);
			}
        }, "json");
   	}
   <c:if test="${fagentoperation.fstatus == 1 }">
    	var newTimess = parseInt(Date.parse(new Date())/1000); //输出实时时间
        var Dtime=$("#lasttime").val();//内容为时间2018-02-12 11:36:47.0
        var nowTime=new Date(Dtime);
        var startTime=nowTime.getTime()/1000;//将获取的时间格式转换为时间戳格式
        //console.log(newTimess)
        //console.log(Dtime)
        //console.log(nowTime)
        //console.log(startTime)
        if((newTimess-startTime) > 1800){
            
        }else{
            startTime = startTime + 1800;
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
                 systemCancel();
                 
                 setTimeout('var curUrl = window.location.href;window.location.href = curUrl;', 500);
                 
            }
            if (minute <= 9) minute = '0' + minute;
            if (second <= 9) second = '0' + second;
            $('#minute_show').html('<s></s>'+minute+'分');
            $('#second_show').html('<s></s>'+second+'秒');
            intDiff--;
            }, 1000);
        }
   </c:if>
</script>
</body>
</html>
