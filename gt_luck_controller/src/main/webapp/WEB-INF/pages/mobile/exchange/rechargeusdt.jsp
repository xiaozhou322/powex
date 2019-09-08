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
<meta name = "viewport" content = "width = device-width, user-scalable = no,initial-scale=0.5,maximum-scale=0.5,minimum-scale=0.5">

<%@include file="../comm/link.inc.jsp" %>
</head>
<body>

 <%@include file="../comm/header.jsp" %>
	<header class="tradeTop tradeTop2">  
    <i class="back toback2"></i>
    <h2 class="tit"><spring:message code="nav.top.exchange" /> </h2>
</header>  
  <div class=" lw-content">
    <div class="lw-finance">
         
			<div class="col-xs-10 lw-financeRight" style="overflow:hidden;">
			    <!-- <h1 class="lw-financeTit"><spring:message code="financial.usdtrecharge" /></h1> -->

				<div class="lw-financeMain recharge">
				

						<div class="col-xs-12 padding-clear padding-top-40" style="margin-top:0.2rem;">
						</div>
						
						<div class="col-xs-12 padding-clear">
							<div class="col-xs-12 recharge-box clearfix buyUstdMain">
								<div class="col-xs-9 rechargeBankBox padding-top-30">
									<div class="steps">
										<ol>
											<li id="rechargeprocess1" class="active textLeft fl">
												<span></span>
	                        					<em><spring:message code="financial.usdtrecharge.step1" /></em>
											</li>
											<li id="rechargeprocess2" class="fl textCenter">
												<span></span>
	                        					<em><spring:message code="financial.usdtrecharge.step2" /></em>
											</li>
											<li id="rechargeprocess4" class="fl textCenter">
												<span></span>
	                       						<em><spring:message code="financial.usdtrecharge.step3" /></em>
											</li>
										</ol>
									</div>
									<div class="steps_1">
										<div id="rechage1" class="col-xs-12 padding-clear form-horizontal" style="font-size:14px; font-weight:normal; color:#666;" class="steps_1">
											  <h1 class="Color333"><spring:message code="financial.exchangeusdt.fillorder" /></h1>
								                <div class="stepsForm">
								             	<h2 class="commW"><spring:message code="financial.exchangeusdt.username" /></h2>
							                    <div class="commW comBorder textCenter">${fuser.frealName }</div>
							                    <h2 class="commW"><spring:message code="financial.exchangeusdt.userID" /></h2>
							                    <div class="commW comBorder textCenter">${fuser.fid }</div>   
												</div>				
								                    <h2 class="commW"><spring:message code="financial.usdtrecharge.amount" /></h2>
								                    <div class="commW comBorder">
								                    	<input id="sbank" type="hidden" value="${bankInfo.fid }">
								                        <input type="text" id="diyMoney" class="txt textCenter" placeholder="<spring:message code="financial.usdtrecharge.amount" />" />
								                        <input type="hidden" value="0.${randomMoney }" id="random">
								                        <span class="randomNum">.${randomMoney }</span>
								                    </div>
								                    <h2 class="commW special_1">≈<span class="ustdNum">0.00000000</span>USDT</h2>
								               
								                <h4 class="colorRed"><spring:message code="financial.exchangeusdt.ratenote" /></h4>
								                <button  class="btn UBtn mtop2 startBtn"><spring:message code="financial.usdtrecharge.genRem" /></button>								                <!-- <button  class="btn UBtn mtop2" id="rechargebtn" style="display:none;"><spring:message code="financial.usdtrecharge.genRem" /></button> -->
								                 </div>

										</div>
										<div id="rechage2" class="col-xs-6 padding-clear form-horizontal steps_2" style="display:none;">
											<dl>
							                    <dt><img src="${oss_url}/static/mobile/images/pic_01.png" alt="" /></dt>
							                    <dd><spring:message code="financial.usdtrecharge.note" arguments="${bankInfo.fbankName }" /></dd>
							                </dl>
							                <div class="bankInfo">
							                    <p><spring:message code="financial.usdtrecharge.payee" /><span id="fownerName"></span></p>
							                    <p><spring:message code="financial.usdtrecharge.collaccount" /><span id="fbankNumber"></span></p>  
							                    <p><spring:message code="financial.usdtrecharge.deposit" /><span id="fbankAddress"></span></p>
							                    <p><spring:message code="financial.usdtrecharge.payamount" /><span class='colorRed' id="bankMoney"></span></p>
							                    <div class="form-group" style="display: none">
														<span><spring:message code="financial.usdtrecharge.note2" /></span> <span id="bankInfo" class="text-danger font-size-16">--</span>
													</div>
													<div class="form-group margin-bottom-clear" style="display: none">
														<span class="control-label text-left text-danger rechage-tips margin-bottom-clear" style="border-top-color:#fff0e4;line-height: 18px;padding-left: 0;display: inline-block;">
														<i class="icon"></i> <spring:message code="financial.usdtrecharge.note3" /> <span id="bankInfotips">--</span>
														</span>
											</div>
							                </div>
							                <button class="btn mtop2" id="rechargenextbtn"><spring:message code="financial.usdtrecharge.completenext" /></button>
							                  <button class="btn UBtn mtop3" onclick="var id = $('#desc').val();recharge.cancelRechargeUSDT(id);"><spring:message code="financial.exchangeusdt.cancel" /></button>
										</div>
										<div id="rechage3" class="col-xs-7 padding-clear form-horizontal" style="display:none;">
											<div class="form-group ">
												<span for="fromBank" class="col-xs-2 control-label"><spring:message code="financial.usdtrecharge.remitbank" /></span>
												<div class="col-xs-6">
													<select id="fromBank" class="form-control">
														<option value="-1">
															<spring:message code="financial.usdtrecharge.choosebank" />
														</option>
														<c:forEach items="${bankTypes }" var="v">
															<option value="${v.key }">${v.value }</option>
														</c:forEach>
													</select>
												</div>
											</div>
											<div class="form-group ">
												<span for="fromAccount" class="col-xs-2 control-label"><spring:message code="financial.usdtrecharge.bankacc" /></span>
												<div class="col-xs-6">
													<input id="fromAccount" class="form-control" type="text" autocomplete="off">
												</div>
											</div>
											<div class="form-group ">
												<span for="fromPayee" class="col-xs-2 control-label"><spring:message code="financial.usdtrecharge.accname" /></span>
												<div class="col-xs-6">
													<input id="fromPayee" class="form-control" type="text" autocomplete="off">
												</div>
											</div>
											<div class="form-group ">
												<span for="fromPhone" class="col-xs-2 control-label"><spring:message code="financial.usdtrecharge.phonenumber" /></span>
												<div class="col-xs-6">
													<input id="fromPhone" class="form-control" type="text" autocomplete="off" value="">
												</div>
											</div>
											<div class="form-group">
												<span for="rechargesuccessbtn" class="col-xs-2 control-label"></span>
												<div class="col-xs-6">
													<button id="rechargesuccessbtn" class="btn btn-danger btn-block"><spring:message code="financial.usdtrecharge.submit" /></button>
												</div>
											</div>
										</div>
										<div id="rechage4" class="col-xs-7 padding-clear form-horizontal steps_3" style="display:none;">
											<div class="step3Con textCenter">
											<div class="stepsAll curSteps ">
					                         <img src="${oss_url}/static/mobile/images/ic01.png" alt="" />
					                         <h3 class="Color333"><spring:message code="financial.usdtrecharge.status2" /></h3>
					                         <p><a class="cblue refresh" href="/exchange/recordUsdt.html?type=0"><spring:message code="financial.exchangeusdt.viewstate" /></a></p>
					                         <p class="Color999"><spring:message code="financial.usdtrecharge.status2.info" /></p>
					                     </div>
					                     </div>      
										</div>
									</div>
								</div>
								
							</div>
							
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<div class="showWarp" stylel="display:none;">
        <div class="step_1Warp">       
           <spring:message code="financial.usdtrecharge.note4.m" />  
            <div class="textCenter"><a href="javascript:void();" class="btn mtop2 UBtn2" id="rechargebtn"><spring:message code="financial.usdtrecharge.note4.confirm" /> </a></div>
        </div>
    </div>



		<input id="minRecharge" value="${minRecharge }" type="hidden">
	<input id="maxRecharge" value="${maxRecharge }" type="hidden">
	<input type="hidden" value="0" name="desc" id="desc">
	<input type="hidden" value="0" name="remark" id="remark">
	<input id="sbank" type="hidden" value="${bankInfo.fid }">

<%@include file="../comm/footer.jsp" %>	
	<script src="${oss_url}/static/mobile2018/js/fluckydraw/com.js?v=20181126201750"></script>
	<script type="text/javascript" src="${oss_url}/static/mobile2018/js/finance/account.usdtrecharge.js?v=4"></script>
	<script type="text/javascript" src="${oss_url}/static/mobile2018/js/index/main.js?v=20181126201750"></script>

</body>
<script type="text/javascript">
	$(".backIcon").click(function(){
		window.history.go(-1);
	});
    $(".startBtn").click(function(event) {
        $(".showWarp").fadeIn();
        // $(this).css('display', 'none');
        //$(this).siblings("#rechargebtn").css('display', 'block');
    });
    $(".showWarp").click(function(event) {
        $(this).fadeOut();
    });

    $("#diyMoney").change(function() {
       if($('#diyMoney').val()!='')
    	{
    	  $('.ustdNum').text((parseInt($('#diyMoney').val())/6.5).toFixed(4));
    	}
    })
    
</script>
</html>
