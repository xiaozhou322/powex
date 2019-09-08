<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ include file="../comm/include.inc.jsp"%>
<%

%>
<!doctype html>
<html>
<head> 
<%@ include file="../comm/basepath.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="../comm/link.inc.jsp" %>
<link href="${oss_url}/static/front2018/css/usdtTrade.css?v=20181126201750" rel="stylesheet" type="text/css" />
</head>
<body class="">
<%@include file="../comm/white_header.jsp" %>
<section class="uTrade mg">
		<input id="lasttime" value="${fagentoperation.fLastUpdateTime}" type="hidden">
        <div class="TitBread">
            <a href="/agent/orderlist.html"><spring:message code="agent.orders" /></a> <i></i> <a href="javascript:;"><spring:message code="agent.orderdetails" /></a>
        </div>
        <div class="orderMain">
            <div class="tit clear">
                <p class="fl"><spring:message code="market.sell" />USDT&nbsp;&nbsp;<spring:message code="agent.orderid" />：<span>${fagentoperation.fid}</span></p>
                <p class="fr time">${fagentoperation.fstatus_s}<c:if test="${fagentoperation.fstatus == 1 }">&nbsp;<em id="minute_show">：30<spring:message code="agent.m" /></em><em id="second_show">36<spring:message code="agent.s" /></em></c:if></p>
            </div>
            <div class="orderCon clear">
                <div class="orderDl fl">
                    <div class="orderPrice">
                        <spring:message code="agent.totalprice" /><br />
                        <span class='colorBlue'>${fagentoperation.ftotalcny}</span><em class="colorBlue">CNY</em>
                    </div>
                    <div class="payHover">    
                        <c:if test="${fpayaccount.openweixin == 1 }">                 
                        <div class="orderAmount">
                            <div class="fl buyNum">
                                 <spring:message code="agent.quantitysold" /><br />
                                <span>${fagentoperation.famount}USDT</span>
                            </div>
                            <div class="fr payType">
                                <dl class="fl clear">
                                    <dt class='fl'><img src="${oss_url}/static/front/css/agent/images/wx.png" alt="" /></dt>
                                    <dd class='fl'>
                                        <span>${fpayaccount.weixinname}</span>
                                        <span>${fpayaccount.weixinid}</span>
                                    </dd>
                                </dl>
                                <div class="clear"></div>
                                <span class="code"><img src="${oss_url}/static/front/css/agent/images/hCode.png" alt="" /></span>
                                <i class="arr"></i>
                                <div class="hoverPic">
                                    <img src="${fpayaccount.weixin}" alt="" />
                                </div>
                            </div>
                            <div class="clear"></div>
                        </div>    
                        </c:if>    
                        <c:if test="${fpayaccount.openalipay == 1 }">                  
                        <div class="orderAmount">
                             <div class="fl buyNum">
                                <spring:message code="agent.quantitysold" /><br />
                                <span>${fagentoperation.famount}USDT</span>
                            </div>
                            <div class="fr payType">
                                <dl class="fl clear">
                                    <dt class='fl'><img src="${oss_url}/static/front/css/agent/images/jfb.png" alt="" /></dt>
                                    <dd class='fl'>
                                        <span>${fpayaccount.aliname}</span>
                                        <span>${fpayaccount.aliid}</span>
                                    </dd>
                                </dl>
                                <div class="clear"></div>
                              <span class="code"><img src="${oss_url}/static/front/css/agent/images/hCode.png" alt="" /></span>
                                <i class="arr"></i>
                                <div class="hoverPic">
                                    <img src="${fpayaccount.alipay}" alt="" />
                                </div>
                            </div>
                            <div class="clear"></div>
                        </div>   
                         </c:if>                     
                        <c:if test="${fpayaccount.openalipay == 1 }">
                        <div class="orderAmount noBoder">
                            <div class="fl buyNum" style="padding-top:10px;">
                                <spring:message code="agent.quantitysold" /><br />
                                <span>${fagentoperation.famount}USDT</span>
                            </div>
                            <div class="fr payType">
                                <dl class="fl clear">
                                    <dt class='fl'><img src="${oss_url}/static/front/css/agent/images/card.png" alt="" /></dt>
                                    <dd class='fl'>
                                        <span>${fpayaccount.fbankperson}    ${fpayaccount.fbankname}</span>
                                        <span>${fpayaccount.fbankothers}</span>
                                        <span>${fpayaccount.fbanknumber}</span>
                                    </dd>
                                </dl>
                                <div class="clear"></div>
                            </div>
                            <div class="clear"></div>
                        </div>   
                        </c:if>
                    </div>
                </div>
                    
                <div class="orderDr fr">
                    <h1><spring:message code="agent.remarknumber" /><p>${fagentoperation.fremark}</p></h1>
                    <p class="txt"><span class="colorBlue"><spring:message code="agent.remarknumber" />：</span><spring:message code="agent.remarkmsg" /></p>
                    <c:if test="${fagentoperation.fstatus < 3 }">
	                    <div class="orderBtn clear">
	                         <c:if test="${is_agent == 0 }">      
		                        <c:if test="${fagentoperation.fstatus == 2 }"><span class="btn fl" onclick="confirmOrder()" onmouseover="$('.ts').fadeIn()">
		                             <spring:message code="agent.confirmpayment" />
		                            <em class="ts" style="display:none"><spring:message code="agent.afterpay" /></em>
		                        </span></c:if>
		                         <c:if test="${fagentoperation.fstatus == 1 }"><span class="btn btn_2 fl" onclick="cancelOrder()" ><spring:message code="agent.canceldeal" /></span></c:if>
	                        </c:if>
	                         <c:if test="${is_agent == 1 && fagentoperation.fstatus == 1 }">
	                         	<span class="btn fl" onclick="confirmOrder()" onmouseover="$('.ts').fadeIn()" style="margin-right: 20px;">
		                            <spring:message code="agent.ipaid" />
		                            <em class="ts" style="display:none"><spring:message code="agent.aftersucc" /></em>
		                        </span>
		                        <span class="btn btn_2 fl" onclick="cancelOrder()" ><spring:message code="agent.canceldeal" /></span>
	                         </c:if> 
	                    </div>
                   </c:if>
                </div>
                <div class="clear"></div>
                <div class="remind">
                    <div class="remindCon">
                        <h3><i></i><spring:message code="agent.tradingtips" /></h3>
                        <p>1、<spring:message code="agent.tipone" /></p>
                        <p>2、<spring:message code="agent.tiptwo" /></p>
                        <p>3、<spring:message code="agent.tipthree" /><a href="javascript:;" class="colorBlue"><spring:message code="agent.seerule" /></a></p>
                    </div>
                </div>
            </div>
        </div>
       <%@include file="../agent/chatbox.jsp" %>	
    </section>
<%@include file="../comm/footer.jsp" %>	
<script type="text/javascript" src="${oss_url}/static/front2018/js/index/main.js?v=20181126201750"></script>
<script type="text/javascript">
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
				
                window.location.reload();
				
			} else {
				window.location.reload();
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
             systemCancel();
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
            $('#minute_show').html('<s></s>'+minute+ language["agent.m"]);
            $('#second_show').html('<s></s>'+second+ language["agent.s"]);
            intDiff--;
            }, 1000);
        }
   </c:if>
</script>
</body>
</html>
