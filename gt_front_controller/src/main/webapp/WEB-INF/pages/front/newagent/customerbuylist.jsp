<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ include file="../comm/include.inc.jsp"%>

<!doctype html>
<html>
<head> 
<%@ include file="../comm/basepath.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="../comm/link.inc.jsp" %>
<link href="${oss_url}/static/front2018/css/usdtTrade.css?v=12" rel="stylesheet" type="text/css" />
<link href="${oss_url}/static/front2018/css/otc.css?v=12" rel="stylesheet" type="text/css" />
</head>
<body>
<%@include file="../comm/newotc_header.jsp" %>
<section class="uTrade mg clear">
        <div class="buy_list">
            <%-- <%@include file="_leftmenu.jsp"%> --%>
            <c:forEach items="${fcoins}" var="t">
				<a <c:if test="${t.fid == symbol }"> class="active" </c:if> 
				<c:if test="${menuflag == 'agentlist' }"> href="/advertisement/buyList.html?symbol=${t.fid }">
				<spring:message code="agent.purchase" />&nbsp;${t.fShortName}</a>
		        </c:if>
		        <c:if test="${menuflag == 'buyerlist' }"> href="/advertisement/sellList.html?symbol=${t.fid }">
		        <spring:message code="agent.navsell" />&nbsp;${t.fShortName}</a>
		        </c:if>
			</c:forEach>
        </div>
        <%--  <div class="uTrade_r">
            <div class="tables-title clear">
                <span class="tit fl"><spring:message code="agent.merchant" /></span>
                <span class="tit fl"><spring:message code="agent.payment" /></span>
                <span class="tit fl"><spring:message code="market.amount" /></span>
                <span class="tit fl"><spring:message code="market.price.cny" /></span>
                <span class="tit fl"><spring:message code="agent.amount" /></span>
                <span class="tit fl"><spring:message code="market.entrustaction" /></span>
            </div>
            <div class="agentCon">
                  <c:forEach var="v" varStatus="vs" items="${list}">
                	<div class="table-item">
                    <div class="tabs userFace fl">
                        <span class="fl iconTag">
                        	<c:if test="${fn:length(v.user.frealName)>=1}">
                        		${fn:substring(v.user.frealName,0,1)}
                        	</c:if>
                        </span>
                        <div class="uName fl">
                            <a href="javascript:;">
                            	<c:if test="${fn:length(v.user.fnickName)>0} && ${fn:length(v.user.fnickName)<=2}">
                            		${fn:substring(v.user.fnickName,0,2)}
                            	</c:if>
                            	<c:if test="${fn:length(v.user.fnickName)>3}">
                            		${fn:substring(v.user.fnickName,0,3)}...
                            	</c:if>
                            </a>
                            <c:if test="${v.status == 0 }">
                           		<img src="${oss_url}/static/front2018/images/v.png" alt="" />
                            </c:if>
                        </div>
                    </div>
                   	
                   	<div class="tabs group-payway fl">
                       <c:forEach items="${v.paytypeList}" var="pay">
                    	 	<c:if test="${pay.payType == 1 }">
	                        <i><img src="${oss_url}/static/front2018/images/card.png" alt="银行卡" /></i>
                        	</c:if>
	                        <c:if test="${pay.payType == 2 }">
	                        <i><img src="${oss_url}/static/front2018/images/wx.png" alt="微信" /></i>
	                        </c:if>
	                        <c:if test="${pay.payType == 3 }">
                        	<i><img src="${oss_url}/static/front2018/images/jfb.png" alt="支付宝" /></i>
	                        </c:if>
                       </c:forEach>
                    </div>
                    
                    <div class="tabs rate fl"><em>${v.repertory_count }</em> ${v.fvirtualcointype.fShortName }</div>
                    <div class="tabs rate fl">
                    	<em>${v.price }</em>CNY<br>                   
                    </div>
                    
                    <div class="tabs rate fl">
                        <em data-v-ad45b12e="" class="gray">
	                        <fmt:formatNumber value="${v.order_limit_min }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="2"/>
	                        ~
	                        <fmt:formatNumber value="${v.order_limit_max }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="2"/>CNY
                        </em>
                    </div>
                    <div class="tabs fl">
                    <span class="buyBt" onclick="buyIn(${v.price },${v.id },'${v.fvirtualcointype.fShortName }')">
                    <spring:message code="market.buy" /></span>
                    </div>

                    <div class="clear"></div>
                 </div>                
                 </c:forEach>
            </div>
        	<div class="page">
				${pagin }
			</div>
        </div> --%>
        <div class="buyMarket">
        	<ul class="buyMarTh">
                <li><spring:message code="agent.merchant" /></li>
                <li><spring:message code="market.amount" /></li>
                <li><spring:message code="market.price.cny" /></li>
                <li><spring:message code="agent.amount" /></li>
                <li><spring:message code="agent.payment" /></li>
                <li><spring:message code="market.entrustaction" /></li>
            </ul>
            <c:forEach var="v" varStatus="vs" items="${list}">
            <ul class="buyMarTd">
            		<li>
            			<%-- <span class="fl iconTag">
                        	<c:if test="${fn:length(v.user.frealName)>=1}">
                        		${fn:substring(v.user.frealName,0,1)}
                        	</c:if>
                        </span> --%>
                        <a href="javascript:;">
                        	<c:if test="${fn:length(v.user.fnickName)>0} && ${fn:length(v.user.fnickName)<=2}">
                        		${fn:substring(v.user.fnickName,0,2)}
                        	</c:if>
                        	<c:if test="${fn:length(v.user.fnickName)>3}">
                        		${fn:substring(v.user.fnickName,0,3)}...
                        	</c:if>
                        </a>
                        <c:if test="${v.status == 0 }">
                       		<img src="${oss_url}/static/front2018/images/v.png" alt="" />
                        </c:if>
            		</li>
            		<li><em>${v.repertory_count }</em> ${v.fvirtualcointype.fShortName }</li>
            		<li><em>${v.price }</em>CNY<br></li>
            		<li>
            			<em data-v-ad45b12e="" class="gray">
	                        <fmt:formatNumber value="${v.order_limit_min }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="2"/>
	                        ~
	                        <fmt:formatNumber value="${v.order_limit_max }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="2"/>CNY
                        </em>
            		</li>
            		<li class="payImg">
            			<c:forEach items="${v.paytypeList}" var="pay">
                    	 	<c:if test="${pay.payType == 1 }">
	                        <img src="${oss_url}/static/front2018/images/card.png" alt="银行卡" />
                        	</c:if>
	                        <c:if test="${pay.payType == 2 }">
	                        <img src="${oss_url}/static/front2018/images/wx.png" alt="微信" />
	                        </c:if>
	                        <c:if test="${pay.payType == 3 }">
                        	<img src="${oss_url}/static/front2018/images/jfb.png" alt="支付宝" />
	                        </c:if>
                       </c:forEach>
            		</li>
            		<li>
	            		<span class="buyBt" onclick="buyIn(${v.price },${v.id },'${v.fvirtualcointype.fShortName }')">
	                    	<spring:message code="market.buy" />
	                    </span>
            		</li>
            </ul>
            </c:forEach>
            <div class="page">
				${pagin }
			</div>
        </div>
    </section>

<div class="warpForm">
        <div class="showBox"><%-- 
            <h2 class="title" style="margin-top:50px;">
                <spring:message code="market.buy" />
                <span class='colorRed'><spring:message code="agent.showmsg" /></span>
            </h2> --%>
            <h3 class="agentTit">
	            <spring:message code="agent.toprice" />(CNY/<em id="coin_type2"></em>)
	            <span id="price">--</span>
	            <p><spring:message code="agent.showmsg" /></p>
	            <%-- <p><spring:message code="market.buy" />&nbsp;&nbsp;<span id="coin_type1"></span></p> --%>
            </h3>
            <input type="hidden" id='ad_id' >
            <div class="showCon">
                <%-- <p class="rateBox">
                <spring:message code="agent.toprice" />（CNY/<em id="coin_type2"></em>）
                <span id="price">--</span>
                </p> --%>
                <p class="tr"><spring:message code="market.amount" />（<span id="coin_type3"></span>）</p>
                <div class="tr inputBox">
                    <input type="text" class="inp" id="usdt_num" placeholder="<spring:message code="agent.showamount" />" />
               </div>
                <p class="tr"><spring:message code="agent.cnyamount" />（CNY）</p>
                <div class="tr inputBox">
                    <input type="text" class="inp" id="cny_num" placeholder="0.0000" />
                </div>
                <span class="remind colorRed" style="display:none"><spring:message code="agent.cnyzero" /></span>
               	<div class="btns">
                    <input type="button" class="button active" value="<spring:message code="agent.confirm" />" onclick="doBuy()"/>
                    <span class="button cancle">
                        <em class="countdown colorRed" id="countdown">(45S)</em>
                        <span class="em"><spring:message code="financial.cancel" /></span>
                    </span>
                </div>
             </div>
             <em class="close">×</em>
        </div> 
    </div>


<%@include file="../comm/footer.jsp" %>	
<script type="text/javascript" src="${oss_url}/static/front2018/js/finance/account.usdtrecharge.js?v=14"></script>
<script type="text/javascript" src="${oss_url}/static/front2018/js/index/main.js"></script>
<script type="text/javascript">
    $(".uTrade_list a").click(function(event) {
        $(this).addClass('active').siblings().removeClass('active');
    });

    var time;   
    var second=45;
    function showTime(){
        if(second <= 0)
        {
        $(".warpForm").fadeOut(200);
        clearInterval(time);
        setTimeout(setSecond,500);
        }else{
            second--;
            $(".countdown").html('('+second+'S)');
        }
    }
    
    
    function buyIn(price,fid,cointype)
    {
    	<c:if test="${sessionScope.login_user == null }">
    		util.layerAlert("", "请先登录！", 2);
    		return;
    	</c:if>
    	
    	if(!queryRealValidate()) {
    		return;
    	}
    	
    	$('.remind').text('').hide();
        $('#price').text(price);
        $('#ad_id').val(fid);
        $('#coin_type1').text(cointype);
        $('#coin_type2').text(cointype);
        $('#coin_type3').text(cointype);
     	time=setInterval (showTime, 1000);
        $(".warpForm").fadeIn(200, function() {
            $(".warpForm .close").click(function(event) { 
               $(this).parent().parent().fadeOut(100);
               setTimeout(setSecond,500);
               clearInterval(time);
            });
        });
    }
    
    //校验是否进行实名认证
    function queryRealValidate() {
    	var result=false;
    	var url = "/order/queryRealValidate.html?random=" + Math.round(Math.random() * 100);
    	$.ajax({
            url: url,
            type: "POST",
            data: {},
            cache: false,
            async:false,
            dataType: "json",
            success: function(data) {
            	if (data.code != 0) {
    				util.layerAlert("", data.msg, 2);
    				result=false;
    			} else {
    				result=true;
    			}
            }
        });
    	return result;
    }
    

    function doBuy()
    {
    	var usdt_num = $('#usdt_num').val();
    	var cny_num = $('#cny_num').val();
    	var price = $('#price').text();
    	var ad_id =  $('#ad_id').val();
    	if(usdt_num=='')
    	{
    		$('.remind').text(language["agent.sum"]).show();
    		return;
    	}
    	if(cny_num=='')
    	{
    		$('.remind').text(language["agent.sumamount"]).show();
    		return;
    	}
    	
    	var param = {'usdt_num':usdt_num,'cny_num':cny_num,'price':price,'ad_id':ad_id,"orderType":1}
    	
    	jQuery.post('/order/placeOrder.html', param, function (data) {
		    $(".warpForm").fadeOut(100);
	        setTimeout(setSecond,500);
	        clearInterval(time);
        	if (data.code == -1) {
				util.layerAlert("", data.msg, 2);
				return;
			} else {
				//util.layerAlert("", data.msg, 1);
				window.location.href='/order/orderDetail.html?orderId='+data.orderId;
			}
        }, "json");
    }
    
    $("#cny_num").on("input",function(){
        if($('#cny_num').val()!='')
         {
           $('#usdt_num').val((parseFloat($('#cny_num').val())/$('#price').text()).toFixed(4));
         }else{
             $('#usdt_num').val(parseFloat(0.0000).toFixed(4));
         }
     });


     $("#usdt_num").on("input",function(){
        if($('#usdt_num').val()!='')
         {
           $('#cny_num').val(($('#usdt_num').val()*$('#price').text()).toFixed(4));
         }else{
             $('#cny_num').val(parseFloat(0.0000).toFixed(4));
         }
     });

 	
     $(".warpForm .cancle").click(function(event) {
        $(".warpForm").fadeOut(100);
         setTimeout(setSecond,500);
         clearInterval(time);
     });

     function setSecond(){
         second = 45;
        $(".countdown").html('('+second+'S)');
     }
   
     $("#usdt_num,#cny_num").on("keypress", function(event) {
         return util.VerifyKeypress(this, event, 4);
     })
</script>

</body>
</html>
