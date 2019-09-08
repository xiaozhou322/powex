<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ include file="../comm/include.inc.jsp"%>

<!doctype html>
<html>
<head> 
<%@ include file="../comm/basepath.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
 <meta name = "viewport" content = "width = device-width, user-scalable = no,initial-scale=0.5,maximum-scale=0.5,minimum-scale=0.5">
<%@include file="../comm/link.inc.jsp" %>
<link href="${oss_url}/static/mobile2018/css/usdtTrade.css?v=20190301163300100" rel="stylesheet" type="text/css" />
<style type="text/css">
.txt:focus {
	outline:none;
    border: 1px solid #87b2f2;
}
</style>
</head>
<body class="Ubody" style="">
<%@include file="../comm/header.jsp" %>
<header class="tradeTop">  
    <i class="back toback2"></i>
    <h2 class="tit"><spring:message code="agent.accountsetting" /></h2>
</header>
    <section class="usdtTrade">
        <div class="uTrade_r">
        <form id="agentinfoForm" onsubmit="return false;">
        	<input name="paytype" id="paytype" type="hidden" value="0"/>
            <div class="baseForm">
             
                <!-- <h1 class="title">收款方式</h1> -->
                <div class="Payment">
                    <div class="payStyle clear">
                        <p class="fl active"><img src="${oss_url}/static/mobile2018/css/agent/images/wx.png" alt="" /><spring:message code="agent.wechat" /></p>
                        <p class="fl payLi2"><img src="${oss_url}/static/mobile2018/css/agent/images/jfb.png" alt="" /><spring:message code="agent.Alipayaccount" /></p>
                        <p class="fl"><img src="${oss_url}/static/mobile2018/css/agent/images/card.png" alt="" /><spring:message code="agent.bankname" /></p>
                    </div>
                    <div class="pay_con active upload sss">
                    	 <input name="paytypeweixin" type="hidden" value="2"/>
                         <input name="paytypeweixin_id" type="hidden" value="${queryisBindWeiXin.id }"/>
                         <label class="lab">
                           	<input class="checkbox" type="checkbox" id="openweixin" name="openweixin" value="1"   <c:if test="${queryisBindWeiXin.status == 1 }">  checked  </c:if>>
                         	<span></span>&nbsp;<spring:message code="agent.start"/>
                        </label>
                         <div class="payWeixin">
                            <p class="commH"><spring:message code="agent.weChatname" /></p>
                            <label for=""><input class="commH txt" type="text" value="${queryisBindWeiXin.realName }" id="weixinname" name="weixinname" /></label>
                         	<p class="payName" style="color:red;display:none;"></p>
                         </div>
                         <div class="payWeixin">
                            <p class="commH"><spring:message code="agent.wechat" /></p>
                            <label for=""><input class="commH txt" type="text" value="${queryisBindWeiXin.paymentAccount }" id="weixinid" name="weixinid"  /></label>
                         	<p class="payAccount" style="color:red;display:none;"></p>
                         </div>
                        <div class="upPic">
                            <div class="verified">
                              <div class="img_box">
                                <div class="center inner">
                                <span style="position: absolute;top: 180px;left: 25%;z-index: 8;"><spring:message code="security.wechat"/></span>
                                <c:if test="${queryisBindWeiXin.qrCode != '' }">
                                  <p class="img"><img src="${queryisBindWeiXin.qrCode }" id="wxurl" ></p>
                                  </c:if>
                                  <a class="radius upload_original" style="z-index:1001">
                                  <label for="pic1" class="s1" id="wxlabel" style="width: 100%;height: 100%;display: block;">
                                  <c:if test="${queryisBindWeiXin.qrCode == '' }">
                                  <em class="cx"><spring:message code="security.upload" /><span class="str"><spring:message code="agent.wechattit" /></span><spring:message code="agent.receiptcode" /></em>
                                  </c:if>
                                  </a>
                                  <input id="pic1" type="file" onchange="uploadImg1()" style="display: none;">
                                  <input type="hidden" id="pic1Url" name="pic1Url" value="${queryisBindWeiXin.qrCode }">
                                </div>
                              </div>
                            </div>
                        </div>
                        
                        <p id="savetwo" style="color: red;"></p>
                        <button class="tj" onclick="saveAgentInfo2()" type="button"><spring:message code="security.submit" /></button>
                    </div>                    
                    <div class="pay_con upload">
                       	<input name="paytypealipay" type="hidden" value="3"/>
                        <input name="paytypealipay_id" type="hidden" value="${queryisBindAlipy.id }"/>
                        <label class="lab">
                        	<input class="checkbox" type="checkbox" id="openalipay" name="openalipay" value="1" <c:if test="${queryisBindAlipy.status==1 }">  checked  </c:if> >
                         	<span></span>&nbsp;<spring:message code="agent.start"/>
                        </label>
                    	<div class="payWeixin">
                        	<p class="commH"><spring:message code="agent.Alipayname" /></p>
                            <label for=""><input class="commH txt" type="text"  <c:if test="${ismerchant != true }">readOnly="true" value="${realname }"</c:if> <c:if test="${ismerchant == true }">value="${queryisBindAlipy.realName }"</c:if> id="aliname" name="aliname" /></label>
                        	<p class="payAlipayname" style="color:red;display:none;"></p>
                        </div>
                         <div class="payWeixin">
                             <p class="commH"><spring:message code="agent.Alipayaccount" /></p>
                            <label for=""><input class="commH txt" type="text" value="${queryisBindAlipy.paymentAccount }" id="aliid" name="aliid"  /></label>
                         	<p class="payAliid" style="color:red;display:none;"></p>
                         </div>
                        <div class="upPic">
                            <div class="verified">
                              <div class="img_box">
                                <div class="center inner">
                                	<span style="position: absolute;top: 180px;left: 22%;z-index: 8;"><spring:message code="security.alipay"/></span>
                                	<c:if test="${queryisBindAlipy.qrCode!='' }">
                                  		<p class="img"><img src="${queryisBindAlipy.qrCode }" id="aliurl"></p>
                                  	</c:if>
                                  	<a class="radius upload_original" style="z-index:1001">
                                  	<label for="pic2" class="s1" id="alilabel" style="width: 100%;height: 100%;display: block;">
                                  	<c:if test="${queryisBindAlipy.qrCode =='' }">
                                  		<em class="cx"><spring:message code="security.upload" /><span class="str"><spring:message code="agent.Alipay" /></span><spring:message code="agent.receiptcode" /></em>
                                  	</c:if>
                                  </label></a>
                                  <input id="pic2" type="file" onchange="uploadImg2()" style="display:none">
                                   <input type="hidden" id="pic2Url" name="pic2Url"  value="${queryisBindAlipy.qrCode }">
                                </div>
                              </div>
                            </div>
                        </div>
                         
                        <p id="saveone" style="color: red;"></p>
                        <button class="tj" onclick="saveAgentInfo1()" type="button"><spring:message code="security.submit" /></button>
                    </div>
                    <div class="pay_con payInformation">
                    	 <input name="paytypebank" type="hidden" value="1"/>
                         <input name="paytypebank_id" type="hidden" value="${queryisBindBank.id }"/>
                         <label class="lab">
                        	<input class="checkbox" type="checkbox" id="openbank" name="openbank" value="1" <c:if test="${queryisBindBank.status==1 }">  checked  </c:if> >
                        	<span></span>&nbsp;<spring:message code="agent.start"/>
                   		</label>
                         <div class="payWeixin">
                             <p class="commH"><spring:message code="financial.cnyrecharge.accname" /></p>
                            <label for=""><input class="commH txt" type="text"  id="fbankperson" name="fbankperson" <c:if test="${ismerchant != true }">readOnly="true" value="${realname }"</c:if> <c:if test="${ismerchant == true }">value="${queryisBindBank.realName }"</c:if> /></label>
                        	<p class="payPerson" style="color:red;display:none;"></p>
                         </div>    
                         <div class="payWeixin">
                             <p class="commH"><spring:message code="agent.accountnum" /></p>
                            <label for=""><input class="commH txt" type="text" value="${queryisBindBank.paymentAccount }" id="fbanknumber" name="fbanknumber" /></label>
                         	<p class="paytnum" style="color:red;display:none;"></p>
                         </div>                     
                         <div class="payWeixin">
                            <p class="commH"><spring:message code="agent.Bankaccount" /></p>
                            <label for=""><input class="commH txt" type="text" value="${queryisBindBank.bank }" id="fbankname" name="fbankname" /></label>
                       		<p class="payBank" style="color:red;display:none;"></p>
                         </div>
                         <div class="payWeixin">
                             <p class="commH"><spring:message code="agent.Branchname" /></p>
                            <label for=""><input class="commH txt" type="text" value="${queryisBindBank.bankBranch }" id="fbankothers" name="fbankothers" /></label>
                            <input type="hidden" value="${queryisBindBank.remark }" id="fbankaddress" name="fbankaddress" />
                         	<p class="bankBranch" style="color:red;display:none;"></p>
                         </div>
                         
                    	<p id="save" style="color: red;"></p>
                    	<button class="tj" onclick="saveAgentInfo()" type="button"><spring:message code="security.submit" /></button>
                    </div>                               
                    
                    <div class="clear"></div>
                </div>
<!--                 <button class="tj" onclick="saveAgentInfo()" type="button">提交</button>
 -->            </div>
             </form>
        </div>
    </section>

 
<%@include file="../comm/otc_tabbar.jsp"%>
<script type="text/javascript" src="${oss_url}/static/mobile2018/js/comm/util.js?v=20180203164658.js?v=20181126201750"></script>
<script type="text/javascript" src="${oss_url}/static/mobile2018/js/layer/layer.js?v=20180203164658.js?v=20181126201750"></script>
<script type="text/javascript" src="${oss_url}/static/mobile2018/js/language/language_<spring:message code="language.title" />.js?v=20180203164658.js?v=20181126201750"></script>
<script type="text/javascript" src="${oss_url}/static/mobile2018/js/comm/comm.js?v=20180203164658.js?v=20181126201750"></script>
<script src="${oss_url}/static/mobile2018/js/fluckydraw/com.js?v=20181126201750"></script>
<script type="text/javascript" src="${oss_url}/static/mobile2018/js/plugin/ajaxfileupload.js?v=20181126201750"></script>
   <script type="text/javascript" src="${oss_url}/static/mobile2018/js/plugin/fileCheck.js?v=20181126201750"></script>
<script type="text/javascript" src="${oss_url}/static/mobile2018/js/plugin/jquery.qrcode.min.js?v=20181126201750"></script>

<script type="text/javascript" src="${oss_url}/static/mobile2018/js/plugin/jquery-migrate-1.4.1.min.js?v=20181126201750"></script>
<script>
	$(".toback2").click(function(){
	    window.history.go(-1);
	});
    $(".uTrade_l a").click(function(event) {
        $(this).addClass('active').siblings().removeClass('active');
    });

    $(".payStyle p").click(function(event) {
        $(this).addClass('active').siblings().removeClass('active');
        var num=$(this).index();
        $(".pay_con").eq(num).addClass('active').siblings().removeClass('active');
    });
    
    
    var isClick = false;
    var isClick1 = false;
    var isClick2 = false;
    
	function saveAgentInfo1()
	{
		//防止重复点击
		if(isClick1){
			util.layerAlert("", "请勿重复提交", 2);
			return;
		}
		isClick1 = true;
		
		$("#paytype").val("3");
		var param = $('#agentinfoForm').serialize();
        if($("#aliname").val() == ''){
         //   $("#saveone").html(language["agent.alipay"]);
            $(".payAlipayname").show().html(language["agent.alipay"]);
            //失败后允许再次点击按钮
			isClick1 = false;
            return false;
        }else{
        	$(".payAlipayname").hide()
        }
        
         if($("#aliid").val() == ''){
            /* $("#saveone").html(language["agent.alipay.account"]); */
            $(".payAliid").show().html(language["agent.alipay.account"]);
            //失败后允许再次点击按钮
			isClick1 = false;
            return false;
        }else{
        	$(".payAliid").hide()
        }
         
          if($("#pic2Url").val() == ''){
            $("#saveone").html(language["agent.alipay.qr"]);
            //失败后允许再次点击按钮
			isClick1 = false;
            return false;

        }
     
		jQuery.post('/user/savepaytype.html', param, function (data) {
			if (data.code == -1) {
				util.layerAlert("", data.msg, 2);
				//失败后允许再次点击按钮
    			isClick = false;
			} else {
				util.layerAlert("", data.msg, 1);
				setTimeout("window.location.href='/user/paytypeList.html'",1500)
			}
        	/* if (data.code == -1) {
				$("#saveone").html(data.msg);
				//失败后允许再次点击按钮
				isClick1 = false;
			} else {
				$("#saveone").html(data.msg);
                setTimeout("window.location.href='/user/paytypeList.html'",1500)
			} */
        }, "json");
	
	}
	
    function saveAgentInfo(){
    	//防止重复点击
		if(isClick){
			util.layerAlert("", "请勿重复提交", 2);
			return;
		}
		isClick = true;
		
    	$("#paytype").val("1");
        var param = $('#agentinfoForm').serialize();
        if($("#fbankperson").val() == ''){
        	
           /*  $("#save").html(language["agent.person"]); */
            $(".payPerson").show().html(language["agent.person"]);
            //失败后允许再次点击按钮
			isClick = false;
            return false;
        }else{
        	$(".payPerson").hide()
        }
         if($("#fbankname").val() == ''){
           /*  $("#save").html(language["agent.fbankname"]); */
            $(".payBank").show().html(language["agent.fbankname"]);
            //失败后允许再次点击按钮
			isClick = false;
            return false;
        }else{
        	$(".payBank").hide()
        }
        
         if($("#fbanknumber").val() == ''){
            /* $("#save").html(language["agent.fbanknumber"]); */
            $(".paytnum").show().html(language["agent.fbanknumber"]);
            //失败后允许再次点击按钮
			isClick = false;
            return false;
        }else{
        	$(".paytnum").hide()
        }
        
        
         if($("#fbankothers").val() == ''){
            /* $("#save").html(language["agent.fbankothers"]); */
            $(".bankBranch").show().html(language["agent.fbankothers"]);
            //失败后允许再次点击按钮
			isClick = false;
            return false;
        }else{
        	$(".bankBranch").hide()
        }
        

        jQuery.post('/user/savepaytype.html', param, function (data) {
        	if (data.code == -1) {
				util.layerAlert("", data.msg, 2);
				//失败后允许再次点击按钮
    			isClick = false;
			} else {
				util.layerAlert("", data.msg, 1);
				setTimeout("window.location.href='/user/paytypeList.html'",1500)
			}
            /* if (data.code == -1) {
                $("#save").html(data.msg);
                //失败后允许再次点击按钮
    			isClick = false;
            } else {

                $("#save").html(data.msg);
                setTimeout("window.location.href='/user/paytypeList.html'",1500)
            } */
        }, "json");
    
    }
    
    function saveAgentInfo2(){
    	//防止重复点击
		if(isClick2){
			util.layerAlert("", "请勿重复提交", 2);
			return;
		}
		isClick2 = true;
		
    	$("#paytype").val("2");
        var param = $('#agentinfoForm').serialize();
          if($("#weixinname").val() == ''){
           /*  $("#savetwo").html(language["agent.weixinname"]); */
            $(".payName").show().html(language["agent.weixinname"]);
            //失败后允许再次点击按钮
			isClick2 = false;
            return false;
        }else{
        	$(".payName").hide()
        }
        
         if($("#weixinid").val() == ''){
             /* $("#savetwo").html(language["agent.weixinid"]); */
             $(".payAccount").show().html(language["agent.weixinid"]);
            //失败后允许再次点击按钮
 			isClick2 = false;
            return false;
        }else{
        	$(".payAccount").hide()
        }
         if($("#pic1Url").val() == ''){
           
            /*  $("#savetwo").html(language["agent.wechat.qr"]); */
             $("#pic1Url").after('<p style="color: red;">'+language["agent.wechat.qr"]+'</p>');
             //失败后允许再次点击按钮
 			 isClick2 = false;
             return false;
        }
        jQuery.post('/user/savepaytype.html', param, function (data) {
            /* if (data.code == -1) {
                $("#savetwo").html(data.msg);
                //失败后允许再次点击按钮
     			isClick2 = false;
            } else {
                $("#savetwo").html(data.msg);
                setTimeout("window.location.href='/user/paytypeList.html'",1500)
            } */
            if (data.code == -1) {
				util.layerAlert("", data.msg, 2);
				//失败后允许再次点击按钮
    			isClick = false;
			} else {
			//	util.showMsg(data.msg);
				util.layerAlert("", data.msg, 1);
				
				setTimeout("window.location.href='/user/paytypeList.html'",1500)
			}
        }, "json");
    
    }
    //上传图片
	function uploadImg1() {
	    if (checkFileType('pic1', 'img')) {
	        fileUpload("/user/uploadPayway.html", "4", "pic1", "pic1Url", null, null, imgbakc1, "resultUrl");
	    }
	}
	
	function imgbakc1(resultUrl) {
	    $("#wxurl").attr('src',resultUrl);
	    $('label[for="pic1"]').text(language["kyc.tips.1"]);
	    $('.pic1name').text($('#pic1').val().split('\\').pop())
	        .siblings().text(language["kyc.tips.2"]);
	    $('#wxlabel').html('');
	}
	
	function uploadImg2() {
	    if (checkFileType('pic2', 'img')) {
	        fileUpload("/user/uploadPayway.html", "4", "pic2", "pic2Url", null, null, imgbakc2, "resultUrl");
	    }
	}
	
	function imgbakc2(resultUrl) {
	    $("#aliurl").attr('src',resultUrl);
	    $('label[for="pic2"]').text(language["kyc.tips.1"]);
	    $('.pic2name').text($('#pic2').val().split('\\').pop())
	       .siblings().text(language["kyc.tips.2"]);
	    $('#alilabel').html('');
	}
</script>    

</body>
</html>
