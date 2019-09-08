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
.uTrade{
	box-shadow: 0px 2px 9px 0px rgba(70, 90, 111, 0.14);
    margin-bottom: 0.32rem;    clear: both;
    margin-top: 0.2rem;}
.buyTit{
    color: #506FC8;
    position: absolute;
    right: 0.3rem;
    font-size: 0.3rem;
}
.buyTit img{
	width: 0.29rem;
    height: 0.27rem;
    vertical-align: -0.05rem;
    }
    
    .backMyad{
        float: left;
	    color: #7C7C7C;
	    margin-left: 0.25rem;
    }
.pubLabel{    
	color: #666666;
    line-height: 0.6rem;
    padding-top: 0.2rem;
    font-family: SimHei;
    font-weight: 400;
    font-size: 0.28rem;}    
.pubcommH{    width: 100%;    text-indent: 0.2rem;
    height: 0.7rem;
    line-height: 0.7rem;
    background: #f1f1f1;
    color: #6f6f6f;}    
    .pubcommH option{    
    height: 0.4rem;
    line-height: 0.4rem;
    font-size: 0.14rem;}
    .spectial{    clear: both;
    overflow: hidden;
    margin-bottom: 0.2rem;
    width: 100%;
    text-align: center;
    line-height: 0.7rem;
    }
    #minamount,#maxamount{
    float: left;
    width: 40%;
    height: 0.7rem;
    line-height: 0.7rem;
    background: #f1f1f1;
    color: #6f6f6f;
    text-align: center;
    }
    #maxamount{float:right;}
</style>
</head>
<body class="">
<%-- <%@include file="../comm/newotc_header.jsp" %> --%>
<header class="tradeTop">  
  <!--  <i class="back toback2"></i> -->
   <h2 class="tit">
   <a class="backMyad" href="/advertisement/buyList.html">
   	<i class="back toback2"></i><spring:message code="agent.advertise" />
   </a>
   <c:if test="${sessionScope.login_user!=null && sessionScope.login_user.fismerchant==true }"> 
   <span id="online" style="<c:if test="${sessionScope.online==true}"> display:none</c:if>"><img src="${oss_url}/static/mobile2018/images/of-line.png"/></span>
   <span id="offline" style="<c:if test="${sessionScope.online==false}"> display:none</c:if>"><img src="${oss_url}/static/mobile2018/images/on-line.png"/></span>
   </c:if>
   	<a href="/advertisement/advertisementMine.html" class="fr buyTit">
	   	<spring:message code="agent.myad" />
	   	<img src="${oss_url}/static/mobile2018/images/Adware.png"/>
   	</a>
   </h2>
</header>
<section class="uTrade">
<div style="color:red;padding-left: 0.3rem;"><spring:message code="agent.pub.tips" /></div>               
        <form id="agentinfoForm">
            <div class="uTrade_r">
                <div class="baseForm">
                    	<!-- <h1 class="title">发布广告</h1> -->
                        <div class="baseInfo baseInfo_2">
                        	<p class="pubLabel"><spring:message code="agent.cointype" /></p>
                            <select class="pubcommH" onchange="showOption()" autocomplete="off"  id="amount_type" name="amount_type">
								<option value=""   selected=true><spring:message code="agent.cointype" /></option>
								<c:forEach items="${amountTypeMap}" var="t">										
									<option value="${t.fid}">${t.fShortName}</option>
								</c:forEach>
							</select>
                        
                            <p class="pubLabel"><spring:message code="agent.tradetype" /></p >
                            <select class="pubcommH" id="ftype" name="ad_type" autocomplete="off">
                                <option value=""><spring:message code="agent.choose" /></option>
                                <option value="2"><spring:message code="agent.purchase" /></option>
                                <option value="1"><spring:message code="market.sell" /></option>
                            </select>
                            
                             <div id="showPrice">
	                            <p class="pubLabel"><spring:message code="market.price.cny" /> <span class="colorBlue"></span></p>
	                            <label for=""><input class="pubcommH" id="rate" name="price" type="text"  autocomplete="off"/></label>
                            </div>
                            
                            <p class="pubLabel"><spring:message code="agent.tradenum" /></p>
                            <label for="">
                            	<input class="pubcommH" id="validnum" name="total_count" type="text" autocomplete="off"/>
                            </label>
                            <p class="pubLabel"><spring:message code="agent.tradequotas" /> <span class="colorBlue">CNY</span></p>

                            <div class="spectial">
                                <input id="minamount" name="order_limit_min" autocomplete="off" type="text" placeholder='<spring:message code="agent.minPrice" /> ' />
                                &nbsp;&nbsp;-&nbsp;&nbsp;
                                <input id="maxamount" name="order_limit_max" autocomplete="off" type="text" placeholder='<spring:message code="agent.maxPrice" /> ' />
                            </div>
                            
                            <div class="tj_box">
                                <button class="tj" onclick="saveOrderInfo()" type="button"><spring:message code="security.submit" /></button> 
                           		<em></em>
                            </div>
                            
                        </div>               
                </div>
                
            </div>
        </form>
    </section>

 

<%@include file="../comm/footer.jsp" %>	
<script type="text/javascript" src="${oss_url}/static/front2018/js/index/main.js"></script>
<script type="text/javascript" src="${oss_url}/static/front2018/js/plugin/ajaxfileupload.js"></script>
<script type="text/javascript" src="${oss_url}/static/front2018/js/plugin/fileCheck.js"></script>
<script type="text/javascript" src="${oss_url}/static/front2018/js/comm/msg.js"></script>
<script type="text/javascript" src="${oss_url}/static/front2018/js/plugin/jquery.qrcode.min.js"></script>
<script type="text/javascript" src="${oss_url}/static/front2018/js/plugin/jquery-migrate-1.4.1.min.js"></script>
<script>
var isClick = false;
	function saveOrderInfo(){		
		var ftype = $('#ftype').val();
		if(ftype==''){
			 util.layerAlert("", language["agent.type"], 0);
             return;
		}
		var shortName=$("#amount_type option:selected").text();
		if(!shortName=="WTO"){
			var rate = $('#rate').val();
			if(rate==''){
				 util.layerAlert("", language["agent.tpirce"], 0);
	             return;
			}	
		}
		var amount_type=$("#amount_type").val();
		if(amount_type==null||amount_type==""||amount_type.length==0){
			 util.layerAlert("", language["agent.cointype"], 0);
             return;
		}
		
		var validnum = $('#validnum').val();
		if(validnum==''){
			 util.layerAlert("", language["agent.tamount"], 0);
             return;
		}
		
		var minamount = $('#minamount').val();
		var maxamount = $('#maxamount').val();
		if(parseInt(maxamount)<parseInt(minamount)){
			 util.layerAlert("", "最小交易额不得大于最大交易额", 0);
			 $('#minamount').val("");
			 $('#maxamount').val("");
             return;
		}
		if(minamount==''||maxamount==''){
			 util.layerAlert("", language["agent.topirce"], 0);
             return;
		}
		var param = $('#agentinfoForm').serialize();
		 <c:if test="${sessionScope.online==false}">		
	  	    util.layerConfirm("您当前状态为处于离线状态，该广告不会展示给给其他用户", function () {
	  			if(isClick) {
	  				return ;
	  			}
	  			isClick= true;

	  			jQuery.post('/advertisement/saveAdvertisement.html', param, function (data) {
	  	        	if (data.success) {
	  	        		util.layerAlert("", data.massage, 1);
	  				} else {
	  					util.layerAlert("", data.massage, 2);
	  					return;
	  				}
	  	        }, "json");
	  	    });
	  		
	  </c:if>
	  <c:if test="${sessionScope.online==true}">	 
		jQuery.post('/advertisement/saveAdvertisement.html', param, function (data) {
	        	if (data.success) {
	        		util.layerAlert("", data.massage, 1);
				} else {
					util.layerAlert("", data.massage, 2);
					return;
				}
	        }, "json");
	   
	  </c:if>
	
	}
	function showOption(){
		var shortName=$("#amount_type option:selected").text();
		if(shortName=="WTO"){
			$("#showPrice").hide();
		}else{
			$("#showPrice").show();
		}
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

</body>
</html>
