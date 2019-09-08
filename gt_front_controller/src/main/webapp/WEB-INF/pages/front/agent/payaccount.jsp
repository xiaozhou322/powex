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
<link href="${oss_url}/static/front2018/css/usdtTrade.css?v=11" rel="stylesheet" type="text/css" />
</head>
<body class="">
<%@include file="../comm/white_header.jsp" %>
<section class="uTrade mg infoMain">
    <div class="uTrade_l">
         <%@include file="_leftmenuUser.jsp"%>
    </div>
    <form id="agentinfoForm" onsubmit="return false;">
        <div class="uTrade_r">
           <!--  <div class="baseForm">
               
              <h1 class="title">收款方式</h1>
                <div class="Payment">
                    <div class="payStyle clear">
                        <p class="fl active"><img src="${oss_url}/static/front/css/agent/images/wx.png" alt="" />微信支付</p>
                        <p class="fl"><img src="${oss_url}/static/front/css/agent/images/jfb.png" alt="" />支付宝支付</p>
                        <p class="fl"><img src="${oss_url}/static/front/css/agent/images/card.png" alt="" />银行卡支付</p>
                    </div>
                    <div class="pay_con active upload">
                     	<div>
                             <p class="commH">微信昵称</p>
                            <label for=""><input class="commH txt" type="text" value="${fpayaccount.weixinname}" id="weixinname" name="weixinname" /></label>
                         </div>
                         <div>
                             <p class="commH">微信号</p>
                            <label for=""><input class="commH txt" type="text" value="${fpayaccount.weixinid}" id="weixinid" name="weixinid"  /></label>
                         </div>
                        <div class="upPic">
                            <div class="verified">
                              <div class="img_box">
                                <div class="center inner">
                                   <c:if test="${fpayaccount.weixin != '' }">
                                    <p class="img"><img src="${fpayaccount.weixin}"  id="wxurl"></p>
                                   </c:if>
                                  <a class="radius upload_original"> <label for="pic1" class="s1" id="wxlabel">
                                  <c:if test="${fpayaccount.weixin == '' }">
                                  <em class="cx">上传<span class="str">微信</span>收款二维码</em>
                                   </c:if>
                                  </label></a>
                        		   <input id="pic1" type="file" onchange="uploadImg1()" style="display:none">
                        		   <input type="hidden" id="pic1Url" name="pic1Url" value="${fpayaccount.weixin}">
                                </div>
                              </div>
                            </div>
                        </div>
                        <label class="lab">
                             启用&nbsp;<input class="checkbox" type="checkbox" id="openweixin" name="openweixin" value="1"   <c:if test="${fpayaccount.openweixin == 1 }">  checked  </c:if>>
                         <span></span>
                        </label>
                    </div>                    
                    <div class="pay_con upload">
                    	<div>
                             <p class="commH">昵称</p>
                            <label for=""><input class="commH txt" type="text" value="${fpayaccount.aliname}" id="aliname" name="aliname" /></label>
                         </div>
                         <div>
                             <p class="commH">账号</p>
                            <label for=""><input class="commH txt" type="text" value="${fpayaccount.aliid}" id="aliid" name="aliid"  /></label>
                         </div>
                        <div class="upPic">
                            <div class="verified">
                              <div class="img_box">
                                <div class="center inner">
                                  <c:if test="${fpayaccount.alipay != '' }">
                                  <p class="img"><img src="${fpayaccount.alipay}" id="aliurl"></p>
                                  </c:if>
                                  <a class="radius upload_original"> 
                                  <label for="pic2" class="s1" id="alilabel">
                                   <c:if test="${fpayaccount.alipay == '' }">
                                  <em class="cx">上传<span style="color:#00a9f2;">支付宝</span>收款二维码</em>
                                  </c:if>
                                  </label></a>
                        		   <input id="pic2" type="file" onchange="uploadImg2()" style="display:none">
                        		   <input type="hidden" id="pic2Url" name="pic2Url"  value="${fpayaccount.alipay}">
                                </div>
                              </div>
                            </div>
                        </div>
                        <label class="lab">
    	                启用&nbsp;<input class="checkbox" type="checkbox" id="openalipay" name="openalipay" value="1" <c:if test="${fpayaccount.openalipay == 1 }">  checked  </c:if> >
                         <span></span>
                        </label>
                    </div>
                    <div class="pay_con payInformation">
                         <div class="fl spectial_02">
                             <p class="commH">账户名</p>
                            <label for=""><input class="commH txt" type="text" value="${fpayaccount.fbankperson}" id="fbankperson" name="fbankperson" /></label>
                         </div>                         
                         <div class="fr spectial_02">
                             <p class="commH">开户行</p>
                            <label for=""><input class="commH txt" type="text" value="${fpayaccount.fbankname}" id="fbankname" name="fbankname" /></label>
                         </div>
                         <div class="clear"></div>
                         <div>
                             <p class="commH">账号</p>
                            <label for=""><input class="commH txt" type="text" value="${fpayaccount.fbanknumber}" id="fbanknumber" name="fbanknumber" /></label>
                         </div>
                         <div>
                             <p class="commH">开户行地址</p>
                            <label for=""><input class="commH txt" type="text" value="${fpayaccount.fbankaddress}" id="fbankaddress" name="fbankaddress"  /></label>
                         </div>
                          <div>
                             <p class="commH">分行名称</p>
                            <label for=""><input class="commH txt" type="text" value="${fpayaccount.fbankothers}" id="fbankothers" name="fbankothers" /></label>
                         </div>
                          <label class="lab">
    		            启用&nbsp;<input class="checkbox" type="checkbox" id="openbank" name="openbank" value="1" <c:if test="${fpayaccount.openbank == 1 }">  checked  </c:if> >
                          <span></span>
                         </label>
                    </div>             
                   
                    <div class="clear"></div>
                </div>
                <button class="tj" onclick="saveAgentInfo()" type="button"><spring:message code="security.submit" /></button>
            </div> -->
          <div class="anquan accountBd mg usdt_aq">
                    <h3 class="ts_title cred">
                        <svg class="icon sfont18" aria-hidden="true">
                            <use xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="#icon-tishi2"></use>
                        </svg><spring:message code="agent.accountremind" />
                    </h3>
                    <div class="aqMain accountBdMain">
                        <div class="accoutType">
                            <ul>
                                <li>
                                    <div class="accoutList clear">
                                        <div class="aq_tit aq_tit02 fl">
                                            <svg class="icon i_mgr" aria-hidden="true">
                                                <use xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="#icon-yinhangqia"></use>
                                            </svg>
                                            <span><spring:message code="agent.bankname" /></span>
                                        </div>
                                        <p class="fl discription "><spring:message code="agent.bindbank" /></p>
                                        <a class="fl cblue2 toggleBtn" href="javascript:;"><spring:message code="security.tochange" /></a>
                                        <!-- <span class="switch-on switch-on2" id="coinSwitch">
                                            <em class="slide1"></em>
                                            OFF
                                        </span> -->
                                    </div>
                                    <div class="accoutShow">
                                        <div class="area accoutForm">
                                            <div class="tr_txt">
                                                 <span class="tr_l"><spring:message code="financial.cnyrecharge.accname" /></span>
                                                 <input type="text"  value="${fpayaccount.fbankperson}" id="fbankperson" name="fbankperson" class="inpBox" >
                                            </div>
                                            <div class="tr_txt">
                                                 <span class="tr_l"><spring:message code="agent.Bankaccount" /></span>
                                                <input class="inpBox" type="text" value="${fpayaccount.fbankname}" id="fbankname" name="fbankname" />
                                                
                                                 <!-- <span class="inpBox sec">
                                                     <select name="" id="">
                                                         <option value="">选择1</option>
                                                     </select>                                                 
                                                     <select name="" id="">
                                                         <option value="">选择2</option>
                                                     </select>                                                 
                                                     <select name="" id="">
                                                         <option value="">选择3</option>
                                                     </select>
                                                 </span> -->
                                            </div>                                        
                                            <div class="tr_txt">
                                                 <span class="tr_l"><spring:message code="agent.accountnum" /></span>
                                                 <input class="inpBox" type="text" value="${fpayaccount.fbanknumber}" id="fbanknumber" name="fbanknumber" />

                                            </div>                                        
                                            <div class="tr_txt">
                                                 <span class="tr_l"><spring:message code="financial.cnyrewithdrawal.addr" /></span>
                                                 <input class="inpBox" type="text" value="${fpayaccount.fbankaddress}" id="fbankaddress" name="fbankaddress"  />
                                            </div>                                        
                                            <div class="tr_txt">
                                                 <span class="tr_l"><spring:message code="agent.Branchname" /></span>
                                                 <input class="inpBox" type="text" value="${fpayaccount.fbankothers}" id="fbankothers" name="fbankothers" />
                                            </div>                                        
 <!--                                            <div class="tr_txt">
                                                 <span class="tr_l">短信验证码</span>
                                                 <input type="text" class="inpBox" placeholder="输入短信验证码">
                                                 <span class="sendCode">发送验证码</span>
                                            </div>   --> 
                                            <div class="">
                                             <label class="lab">
                                                启用&nbsp;<input class="checkbox" type="checkbox" id="openbank" name="openbank" value="1" <c:if test="${fpayaccount.openbank == 1 }">  checked  </c:if> >
                                                  <span></span>
                                                 </label>  
                                            </div>
                                            <p id="save"></p>                                     
                                            <div class="tr_txt">
                                                 <span class="tr_l"></span>
                                                 <button class="btn sub mgt20" onclick="saveAgentInfo()"><spring:message code="security.submit" /></button>
                                            </div>

                                        </div>
                                       <div class="care">
                                            <div style="background:#fff; padding:30px;">
                                                <h3 class="upPrice">
                                                    <svg class="icon sfont18" aria-hidden="true">
                                                            <use xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="#icon-tishi2"></use>
                                                    </svg>【<spring:message code="new.tips" />】
                                                </h3>
                                                <p class="txt">
                                                    <spring:message code="agent.bankremind" />
                                                </p>
                                            </div>
                                        </div>
                                    </div>
                                </li>  
                                <li>
                                    <div class="accoutList clear">
                                        <div class="aq_tit aq_tit02 fl">
                                            <svg class="icon i_mgr" aria-hidden="true">
                                                <use xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="#icon-zhifubaoa"></use>
                                            </svg>
                                            <span><spring:message code="agent.Alipayaccount" /></span>
                                        </div>
                                        <p class="fl discription "><spring:message code="agent.bindalipay" /></p>
                                        <a class="fl cred toggleBtn" href="javascript:;"><spring:message code="security.bind" /></a>
                                    </div>
                                    <div class="accoutShow">
                                        <div class="area accoutForm">
                                            <div class="tr_txt">
                                                 <span class="tr_l"><spring:message code="agent.Alipayname" /></span>
                                                 <input type="text" value="${fpayaccount.aliname}" id="aliname" name="aliname"  class="inpBox" placeholder='<spring:message code="agent.Alipayname" />'>
                                            </div>                                        
                                            <div class="tr_txt">
                                                 <span class="tr_l"><spring:message code="agent.Alipayaccount" /></span>
                                                 <input type="text"  value="${fpayaccount.aliid}" id="aliid" name="aliid"  class="inpBox" placeholder='<spring:message code="agent.Alipayaccount" />'>
                                            </div>                                        
                                            <div class="upPic">
                                                <div class="verified">
                                                  <div class="img_box">
                                                    <div class="center inner">
                                                       <c:if test="${fpayaccount.alipay != '' }">
                                                        <p class="img"><img src="${fpayaccount.alipay}"  id="aliurl"></p>
                                                       </c:if>
                                                      <a class="radius upload_original"> <label for="pic2" class="s1" id="alilabel">
                                                      <c:if test="${fpayaccount.alipay == ''  }">
                                                      <em class="cx"><spring:message code="security.upload" /><span class="str"><spring:message code="agent.Alipay" /></span><spring:message code="agent.receiptcode" /></em>
                                                       </c:if>
                                                      </label></a>
                                                   <input id="pic2" type="file" onchange="uploadImg2()" style="display:none">
                                                   <input type="hidden" id="pic2Url" name="pic2Url" value="${fpayaccount.alipay}">
                                                    </div>
                                                  </div>
                                                </div>
                                            </div>
                                            <label class="lab">
                                                启用&nbsp;<input class="checkbox" type="checkbox" id="openalipay" name="openalipay" value="1" <c:if test="${fpayaccount.openalipay == 1 }">  checked  </c:if> >
                                                 <span></span>
                                                </label>
                                            <p id="saveone"></p>
                                            <div class="tr_txt">
                                                 <span class="tr_l"></span>
                                                 <button class="btn sub mgt30"  onclick="saveAgentInfo1()" ><spring:message code="security.submit" /></button>
                                            </div>
                                        </div>

                                    </div> 
                                </li>                                 
                                <li>
                                    <div class="accoutList clear">
                                        <div class="aq_tit aq_tit02 fl">
                                            <svg class="icon i_mgr" aria-hidden="true">
                                                <use xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="#icon-weixin2"></use>
                                            </svg>
                                            <span><spring:message code="agent.wechat" /></span>
                                        </div>
                                        <p class="fl discription "><spring:message code="agent.bindwechat" /></p>
                                        <a class="fl cred toggleBtn" href="javascript:;"><spring:message code="security.bind" /></a>
                                    </div>
                                    <div class="accoutShow">
                                        <div class="area accoutForm">
                                            <div class="tr_txt">
                                                 <span class="tr_l"><spring:message code="agent.weChatname" /></span>
                                                 <input type="text" value="${fpayaccount.weixinname}" id="weixinname" name="weixinname" class="inpBox" placeholder='<spring:message code="agent.weChatname" />'>
                                            </div>                                        
                                            <div class="tr_txt">
                                                 <span class="tr_l"><spring:message code="agent.wechat" /></span>
                                                 <input type="text" class="inpBox" value="${fpayaccount.weixinid}" id="weixinid" name="weixinid"  placeholder='<spring:message code="agent.wechat" />'>
                                            </div>                                        
                                            <div class="upPic">
                                                <div class="verified">
                                                  <div class="img_box">
                                                    <div class="center inner">
                                                       <c:if test="${fpayaccount.weixin != '' }">
                                                        <p class="img"><img src="${fpayaccount.weixin}"  id="wxurl"></p>
                                                       </c:if>
                                                      <a class="radius upload_original"> <label for="pic1" class="s1" id="wxlabel">
                                                      <c:if test="${fpayaccount.weixin == '' }">
                                                      <em class="cx"><spring:message code="security.upload" /><span class="str"><spring:message code="agent.wechattit" /></span><spring:message code="agent.receiptcode" /></em>
                                                       </c:if>
                                                      </label></a>
                                                   <input id="pic1" type="file" onchange="uploadImg1()" style="display:none">
                                                   <input type="hidden" id="pic1Url" name="pic1Url" value="${fpayaccount.weixin}">
                                                    </div>
                                                  </div>
                                                </div>
                                            </div>
                                            <div>
                                                 <label class="lab">
                                                     启用&nbsp;<input class="checkbox" type="checkbox" id="openweixin" name="openweixin" value="1"   <c:if test="${fpayaccount.openweixin == 1 }">  checked  </c:if>>
                                                 <span></span>
                                                </label>
                                            </div>
                                            <p id="savetwo"></p>
                                            <div class="tr_txt">
                                                 <span class="tr_l"></span>
                                                 <button onclick="saveAgentInfo2()" class="btn sub mgt30"><spring:message code="security.submit" /></button>
                                            </div>
                                        </div>

                                    </div> 
                                </li>                     
                            </ul>
                        </div>
                    </div>
          </div>
        
        </div>
    </form>
</section>

 

<%@include file="../comm/footer.jsp" %>	
<script type="text/javascript" src="${oss_url}/static/front2018/js/index/main.js?v=20181126201750"></script>
<script type="text/javascript" src="${oss_url}/static/front2018/js/plugin/ajaxfileupload.js?v=20181126201750"></script>
   <script type="text/javascript" src="${oss_url}/static/front2018/js/plugin/fileCheck.js?v=20181126201750"></script>
   <script type="text/javascript" src="${oss_url}/static/front2018/js/comm/msg.js?v=20181126201750"></script>
<script type="text/javascript" src="${oss_url}/static/front2018/js/plugin/jquery.qrcode.min.js?v=20181126201750"></script>
<!-- <script type="text/javascript" src="${oss_url}/static/front/js/plugin/jquery.autocomplete.min.js?v=20181126201750"></script> -->
<script type="text/javascript" src="${oss_url}/static/front2018/js/plugin/jquery-migrate-1.4.1.min.js?v=20181126201750"></script>
<script>
    $(".uTrade_l a").click(function(event) {
        $(this).addClass('active').siblings().removeClass('active');
    });

    $(".payStyle p").click(function(event) {
        $(this).addClass('active').siblings().removeClass('active');
        var num=$(this).index();
        $(".pay_con").eq(num).addClass('active').siblings().removeClass('active');
    });
	function saveAgentInfo1()
	{

		var param = $('#agentinfoForm').serialize();
        if($("#aliname").val() == ''){
            $("#saveone").html(language["agent.alipay"]);
            return false;
        }
        
         if($("#aliid").val() == ''){
            $("#saveone").html(language["agent.alipay.account"]);
            return false;
        }
          if($("#pic2Url").val() == ''){
            $("#saveone").html(language["agent.alipay.qr"]);
            return false;

        }
     
		jQuery.post('/agent/savepayaccount.html', param, function (data) {
        	if (data.code == -1) {
				$("#saveone").html(data.msg);
				
			} else {
				$("#saveone").html(data.msg);
                setTimeout("window.location.href='/agent/payaccount.html'",1500)

			}
        }, "json");
	
	}
    function saveAgentInfo()
    {
        var param = $('#agentinfoForm').serialize();
        if($("#fbankperson").val() == ''){

            $("#save").html(language["agent.person"]);
            return false;
        }
         if($("#fbankname").val() == ''){
            $("#save").html(language["agent.fbankname"]);
            return false;
        }
        
         if($("#fbanknumber").val() == ''){
            $("#save").html(language["agent.fbanknumber"]);
            return false;
        }
        
         if($("#fbankaddress").val() == ''){
            $("#save").html(language["agent.fbankaddress"]);
            return false;
        }
        
         if($("#fbankothers").val() == ''){
            $("#save").html(language["agent.fbankothers"]);
            return false;
        }
        

        jQuery.post('/agent/savepayaccount.html', param, function (data) {
            if (data.code == -1) {
                $("#save").html(data.msg);
            } else {

                $("#save").html(data.msg);
                setTimeout("window.location.href='/agent/payaccount.html'",1500)
                

            }
        }, "json");
    
    }
    function saveAgentInfo2()
    {
        var param = $('#agentinfoForm').serialize();
          if($("#weixinname").val() == ''){
           $("#savetwo").html(language["agent.weixinname"]);
            return false;
        }
        
         if($("#weixinid").val() == ''){
            
             $("#savetwo").html(language["agent.weixinid"]);
            return false;
        }
          if($("#pic1Url").val() == ''){
           
             $("#savetwo").html(language["agent.wechat.qr"]);
            return false;
        }
        jQuery.post('/agent/savepayaccount.html', param, function (data) {
            if (data.code == -1) {
                $("#savetwo").html(data.msg);
   
            } else {
                $("#savetwo").html(data.msg);
                setTimeout("window.location.href='/agent/payaccount.html'",1500)
               

            }
        }, "json");
    
    }
    //上传图片
	function uploadImg1() {
	    if (checkFileType('pic1', 'img')) {
	        fileUpload("/common/upload.html", "4", "pic1", "pic1Url", null, null, imgbakc1, "resultUrl");
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
	        fileUpload("/common/upload.html", "4", "pic2", "pic2Url", null, null, imgbakc2, "resultUrl");
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
