<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ include file="../comm/include.inc.jsp"%>

<!doctype html>
<html>
<head>
<%@ include file="../comm/basepath.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name = "viewport" content = "width = device-width, initial-scale = 1.0, maximum-scale = 1.0, user-scalable = 0" />
<%@include file="../comm/link.inc.jsp"%>
<link href="${oss_url}/static/mobile2018/css/usdtTrade.css?v=20190301163300100" rel="stylesheet" type="text/css" />
<style>
.tradeTop{padding-left:0;}
.tradeTop .tit{
    text-align: center;
    margin: 0 auto;
}
.uTrade{
	box-shadow: 0px 2px 9px 0px rgba(70, 90, 111, 0.14);
    margin-bottom: 0.32rem;
    margin-top: 0.2rem;}
.buyBt{float:right;}
.uTrade_l{
    display: block;
    overflow: hidden;
    clear: both;
}
.active{
    background: #fff;
    color: #488bef;
    border-bottom: 1px solid #488bef;
}
.leftmenuA{
    padding: 0.2rem 0.13rem;
    height: 0.8rem;
    display: inline-block;
    text-align: center;
}
.close{
    float: right;
    font-size: 0.52rem;
    padding-right: 0.3rem;
}
.buyTit{
    color: #506FC8;
    font-size: 0.24rem;
    position: absolute;
    right: 0.3rem;
}
    
    .userFace{
    	position: relative;
    }
    .order_limit{
        color: #F06338;
	    font-size: 0.24rem;
	    white-space: nowrap;
	    text-overflow: ellipsis;
	    line-height: 0.5rem;
    }
    
    
</style>
</head>
<body>
	<!-- 购买页面 -->
	<section class="uTrade mg clear" style="margin:0;">
		<%@include file="../comm/newotc_header.jsp" %>
		<div class="uTrade_l">
		    <div class="securityMain newsCon helpCon">
	         <c:forEach items="${fcoins }" var="t" >
	         	<c:if test="${symbol==t.fid}"> 
					<span class="selStyle" onclick="showtab('coinWarp');">${t.fShortName}</span>
	         	</c:if>
	         </c:forEach> 
			</div>
			<%-- <%@include file="../comm/_leftmenu.jsp"%> --%>
		</div>
	<div class="uTrade_r">
			<!-- <div class="tables-title clear">
				<span class="tit fl" style="min-width: 24%;">承兑商</span>
	            <span class="tit fl" style="min-width: 18%;">支付方式</span>
	            <span class="tit fl">数量</span>
	            <span class="tit fl">单价</span>
	            <span class="tit fr" style="width: 10%;float: right;">操作</span>
			</div> -->
			<div class="agentCon">
				<c:forEach var="v" varStatus="vs" items="${list}">
					<div class="table-item">
						<h3 class="table-item-h3">
						<!-- <span class="fl">承兑商：</span> -->
							<span class="fl iconTag"> 
								<c:if test="${fn:length(v.user.frealName)>=1}">
	                        		${fn:substring(v.user.frealName,0,1)}
	                        	</c:if>
							</span>
							<span class="fl">
								<a href="javascript:;" class="fnickName"> 
									<c:if test="${fn:length(v.user.fnickName)>0} && ${fn:length(v.user.fnickName)<=2}">
	                            		${fn:substring(v.user.fnickName,0,2)}
	                            	</c:if> 
	                            	<c:if test="${fn:length(v.user.fnickName)>3}">
	                            		${fn:substring(v.user.fnickName,0,3)}...
	                            	</c:if>
								</a>
								<c:if test="${v.status == 0 }">
									<img src="${oss_url}/static/front2018/images/v.png" alt="" class="fnickImg"/>
								</c:if>
							</span>
						</h3>
						<ul class="fnickUl">
							<li><em>${v.repertory_count}&nbsp;&nbsp;${v.fvirtualcointype.fShortName}</em>
							数量
							</li>
							<li><em>${v.price }&nbsp;&nbsp;CNY</em>
							单价
							</li>
							<li>
							<em>
							<c:forEach items="${v.paytypeList}" var="pay">
									<c:if test="${pay.payType == 1 }">
										<img src="${oss_url}/static/mobile2018/css/agent/images/card.png" alt="银行卡" />
									</c:if>
									<c:if test="${pay.payType == 2 }">
										<img src="${oss_url}/static/mobile2018/css/agent/images/wx.png"	alt="微信" />
									</c:if>
									<c:if test="${pay.payType == 3 }">
										<img src="${oss_url}/static/mobile2018/css/agent/images/jfb.png" alt="支付宝" />
									</c:if>
							</c:forEach>
							</em>
							支付方式
							</li>
						</ul>
						<div>
							<p class="fl order_limit">
								限额&nbsp;<fmt:formatNumber value="${v.order_limit_min }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="2"/>-<fmt:formatNumber value="${v.order_limit_max }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="2"/>CNY
							</p>
							<p class="fr">
								<span class="buyBt" onclick="buyIn(${v.price },${v.id },'${v.fvirtualcointype.fShortName }')">
									<spring:message code="market.buy"/>
								</span>  
							</p>
						</div>
					<!-- 承兑商 -->
					<%--<div class="tabs userFace fl" style="min-width: 24%;">
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
			                <div data-v-ad45b12e="" class="gray fl order_limit">
			                    	限额&nbsp;
			                    <fmt:formatNumber value="${v.order_limit_min }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="2"/>
			                    -
			                    <fmt:formatNumber value="${v.order_limit_max }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="2"/>CNY
							</div>
						</div>
					<!-- 支付方式 -->
						<div class="tabs group-payway fl" style="min-width: 18%;">
							<c:forEach items="${v.paytypeList}" var="pay">
								<c:if test="${pay.payType == 1 }">
									<span><img src="${oss_url}/static/mobile2018/css/agent/images/card.png" alt="银行卡" /></span>
								</c:if>
								<c:if test="${pay.payType == 2 }">
									<span><img src="${oss_url}/static/mobile2018/css/agent/images/wx.png"	alt="微信" /></span>
								</c:if>
								<c:if test="${pay.payType == 3 }">
									<span><img src="${oss_url}/static/mobile2018/css/agent/images/jfb.png" alt="支付宝" /></span>
								</c:if>
							</c:forEach>
						</div>
					<!-- 数量 -->
						<div class="tabs fl">
							<em>${v.repertory_count }</em> <br>${v.fvirtualcointype.fShortName }
						</div>
					<!-- 单价 -->
						 <div class="tabs fl">
						 	<em>${v.price }</em><br>CNY
						 </div>
				 	<!-- 操作 -->
						<div class="tabs rate fr" style="width: 10%;float: right;margin-top: 0.28rem;">
		                    <span class="buyBt" onclick="buyIn(${v.price },${v.id },'${v.fvirtualcointype.fShortName }')">
								<spring:message code="market.buy" />
							</span>      
		                </div> --%>
					</div>
				</c:forEach>
			</div>
		</div>
	</section>

	<div class="warpForm">
	
		<div class="showBox">
		<em class="close">×</em>
			<h2 class="title" style="margin-top: 50px;">
				<spring:message code="market.buy" />
				<span id="coin_type1"></span> <span class='colorRed'><spring:message
						code="agent.showmsg" /></span>
			</h2>
			<input type="hidden" id='ad_id'>
			<div class="showCon">
				<p class="rateBox">
					<spring:message code="agent.toprice" />
					（CNY/<em id="coin_type2"></em>）<span id="price">--</span>
				</p>
				<p class="tr">
					<spring:message code="market.amount" />
					<span id="coin_type3"></span>
				</p>
				<div class="tr inputBox">
					<input type="text" class="inp" id="usdt_num"
						placeholder="<spring:message code="agent.showamount" />" />
				</div>
				<p class="tr">
					<spring:message code="agent.cnyamount" />
					CNY
				</p>
				<div class="tr inputBox">
					<input type="text" class="inp" id="cny_num" placeholder="0.0000" />
				</div>
				<span class="remind colorRed" style="display: none">
				<spring:message code="agent.cnyzero" /></span>
				<div class="btns">
					<input type="button" class="button active"
						value="<spring:message code="agent.confirm" />" onclick="doBuy()" />
					<span class="button cancle"> <em class="countdown colorRed"
						id="countdown">(45S)</em> <span class="em"><spring:message
								code="financial.cancel" /></span>
					</span>
				</div>
			</div>
			
		</div>
	</div>

	<div class="coinWarp" id="coinWarp" onclick="hidetab(this.id);" style="z-index: 10000">
	    <div class="coinLitBox coinLitBox2">
          <ul>    
       		<c:forEach items="${fcoins}" var="t">
              <li> <a href="/advertisement/buyList.html?symbol=${t.fid }"><spring:message code="agent.purchase" />${t.fShortName}</a></li>
             </c:forEach>
          </ul>
	    </div>
	</div>

<%@include file="../comm/otc_tabbar.jsp"%>
<script type="text/javascript" src="${oss_url}/static/mobile2018/js/comm/util.js?v=20180203164658.js?v=20181126201750"></script>
<script type="text/javascript" src="${oss_url}/static/mobile2018/js/layer/layer.js?v=20180203164658.js?v=20181126201750"></script>
<script type="text/javascript" src="${oss_url}/static/mobile2018/js/comm/comm.js?v=20180203164658.js?v=20181126201750"></script>
<script type="text/javascript" src="${oss_url}/static/mobile2018/js/language/language_<spring:message code="language.title" />.js?v=20180203164658.js?v=20181126201750"></script>
<script type="text/javascript" src="${oss_url}/static/mobile2018/js/finance/account.usdtrecharge.js?v=14"></script>
<script type="text/javascript" src="${oss_url}/static/mobile2018/js/main.js"></script>	
<script src="${oss_url}/static/mobile2018/js/fluckydraw/com.js?v=20181126201750"></script>
<script type="text/javascript">
	$(".uTrade_l a").click(function(event) {
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
    
    
    function buyIn(price,fid,cointype){
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
    

    function doBuy(){
    	var usdt_num = $('#usdt_num').val();
    	var cny_num = $('#cny_num').val();
    	var price = $('#price').text();
    	var ad_id =  $('#ad_id').val();
    	if(usdt_num==''){
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
			//	util.layerAlert("", data.msg, 2);
				util.showMsg(data.msg);
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
