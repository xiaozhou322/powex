<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ include file="../comm/include.inc.jsp"%>

<!doctype html>
<html>
<head> 
<%@ include file="../comm/basepath.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="../comm/link.inc.jsp" %>
<link href="${oss_url}/static/front2018/css/exchange/common.css?v=20181126201750" rel="stylesheet" type="text/css" />
<link href="${oss_url}/static/front2018/css/exchange/main.css?v=1" rel="stylesheet" type="text/css" />
<!-- <link href="${oss_url}/static/front/css/exchange/common.css?v=20181126201750" rel="stylesheet" type="text/css" />
<link href="${oss_url}/static/front/css/exchange/main.css?v=20181126201750" rel="stylesheet" type="text/css" /> -->

</head>
<body class="lw-UstdBody">
	 
 <%@include file="../comm/header.jsp" %>

<section >
        <div class="buyUstdMain">
            <div class="steps">
                <ol class="recharge-process">
                    <li id="rechargeprocess1" class="fl active textLeft">
                        <span></span>
                        <em><spring:message code="financial.usdtrecharge.step1" /></em>
                    </li>
                    <li id="rechargeprocess2" class="fl textCenter">
                        <span></span>
                        <em><spring:message code="financial.usdtrecharge.step2" /></em>
                    </li>                    
                    <li id="rechargeprocess3" class="fr textRight">
                        <span></span>
                        <em><spring:message code="financial.usdtrecharge.step3" /></em>
                    </li>
                    <div class="clear"></div>
                </ol>
            </div>
            <div class="steps_1" id="recharge1">
                <h1 class="textCenter Color333"><spring:message code="financial.exchangeusdt.fillorder" /></h1>
                <h4 class="colorRed textCenter"><spring:message code="financial.exchangeusdt.ratenote" /></h4>
                <div class="stepsForm">
               			
	                    <div class="commW">
	                        <span><spring:message code="financial.exchangeusdt.username" /></span>
	                        <span class="bankName Color333">${fuser.frealName }</span>
						
	                    </div>
             			<div class="commW">
	                        <span><spring:message code="financial.exchangeusdt.userID" /></span>
	                        <span class="bankName Color333">${fuser.fid }</span>
						
	                    </div>
                       <p class="commW">
                           <input id="diyMoney" class="buyPrices Color333" type="text" style="height:30px;" value="" placeholder="<spring:message code="financial.usdtrecharge.amount" />" />
                           <input id="sbank" type="hidden" value="${bankInfo.fid }">
                           <input type="hidden" value="0.${randomMoney }" id="random">
                           <span class="fr">.${randomMoney }</span>
                           <div class="clear"></div>
                       </p>
                        <p class="priceAll">=<span class="ustdNum" >0.00000000</span>USDT</p>
                    </div>
               
                <button class="UBtn mtop50 UBtn2 startBtn"><spring:message code="financial.usdtrecharge.genRem" /></button>
<!--                 <button id="rechargebtn" class="UBtn mtop50 UBtn2"><spring:message code="financial.usdtrecharge.genRem"/></button>
                <style type="text/css">
                    #rechargebtn{display:none;}
                </style> -->
            </div>
        	<div class="steps_2" id="recharge2" style="display:none">
                <dl>
                    <dt class="fl"><img src="${oss_url}/static/front/images/exchange/pic_01.png" alt="" /></dt>
                    <dd class="fl"><spring:message code="financial.usdtrecharge.note" arguments="${bankInfo.fbankName }" /> </dd>
                    <div class="clear"></div>
                </dl>
                <div class="bankInfo">
                    <p><spring:message code="financial.usdtrecharge.payee" /> <span id="fownerName"></span></p>
                    <p><spring:message code="financial.usdtrecharge.collaccount" /><span id="fbankNumber"></span></p>  
                    <p><spring:message code="financial.usdtrecharge.deposit" /><span id="fbankAddress"></span></p>
                    <p><spring:message code="financial.usdtrecharge.payamount" /><span class='colorRed' id="bankMoney"></span></p>
                    <span id="bankInfotips" style="display:none">--</span>
                    <span id="bankInfo"  style="display:none">--</span>
                </div>
                <div class="promptTxt">
                    <spring:message code="financial.usdtrecharge.note4" />              
                </div>
                <button id="rechargenextbtn" class="UBtn mtop20"><spring:message code="financial.usdtrecharge.completenext" /></button>
                <button class="UBtn mtop20 UBtn2" onclick="var id = $('#desc').val();recharge.cancelRechargeUSDT(id);"><spring:message code="financial.exchangeusdt.cancel" /></button>
            </div>
        	<div class="steps_3" id="recharge3" style="display:none">
                 <div class="step3Con textCenter">
                    <div class="stepsAll curSteps">
                         <img src="${oss_url}/static/front/images/exchange/ic01.png" alt="" />
                         <h3 class="Color333"><spring:message code="financial.usdtrecharge.status2" /></h3>
                         <p><a class="cblue" href="javascript:;" onclick="var operid = $('#desc').val();var url='${oss_url}/exchange/recordUsdt.html?type=0&operid='+operid;window.location.href=url;"><spring:message code="financial.exchangeusdt.viewstate" /></a></p>
                         <p class="Color999"><spring:message code="financial.usdtrecharge.status2.info" /></p>
                     </div>                    
                     <div class="stepsAll stepsSuccess">
                         <img src="${oss_url}/static/front/images/exchange/ic02.png" alt="" />
                         <h3 class="Color333"><spring:message code="financial.usdtrecharge.status3" /></h3>
                         <p><a class="cblue" href="/financial/index.html"><spring:message code="financial.usdtrecharge.status3.info" /></a></p>
                     </div>
                     <div class="stepsAll stepsFailure">
                         <img src="${oss_url}/static/front/images/exchange/ic03.png" alt="" />
                         <h3 class="Color333"><spring:message code="financial.usdtrecharge.status5" /></h3>
                         <p class="cblue"><spring:message code="financial.usdtrecharge.status5.info" /></p>
                     </div>
                 </div>
                 
            </div>
        </div>
    </section>
    
    <div class="showWarp">
        <div class="step_1Warp">       
           <spring:message code="financial.usdtrecharge.note4.m" />
            <div class="textCenter"><a href="javascript:void();" class="UBtn UBtn3" id="rechargebtn"><spring:message code="financial.usdtrecharge.note4.confirm" /></a></div>
        </div>
    </div>


	<input id="minRecharge" value="${minRecharge }" type="hidden">
	<input id="maxRecharge" value="${maxRecharge }" type="hidden">
	<input type="hidden" value="0" name="desc" id="desc">
	<input type="hidden" value="0" name="remark" id="remark">
	<input id="sbank" type="hidden" value="${bankInfo.fid }">


<%@include file="../comm/usdfooter.jsp" %>	
<script type="text/javascript" src="${oss_url}/static/front2018/js/finance/account.usdtrecharge.js?v=14"></script>
<script type="text/javascript" src="${oss_url}/static/front2018/js/index/main.js?v=20181126201750"></script>
<script type="text/javascript">

    $(".startBtn").click(function(event) {
        $(".showWarp").fadeIn();
        // $(this).css('display', 'none');
        // $(this).siblings("#rechargebtn").css('display', 'block');
    });

    $(".showWarp").click(function(event) {
        $(this).fadeOut();
    }); 

    $("#diyMoney").change(function(event) {
    	if($('#diyMoney').val()!='')
    	{
    	  $('.ustdNum').text((parseInt($('#diyMoney').val())/6.5).toFixed(4));
    	}
    })
    

</script>

</body>
</html>
