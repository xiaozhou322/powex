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
<meta name = "viewport" content = "width = device-width, initial-scale = 1.0, maximum-scale = 1.0, user-scalable = 0" />
<%@include file="../comm/link.inc.jsp" %>
<link href="${oss_url}/static/mobile2018/css/usdtTrade.css?v=1" rel="stylesheet" type="text/css" />

</head>
<body class="Ubody">
<%@include file="../comm/header.jsp" %>
<header class="tradeTop">  
    <i class="back toback2"></i>
    <h2 class="tit">信息资料</h2>
</header>
    <section class="usdtTrade">
        <div class="uTrade_r">
        <form id="agentinfoForm">
            <div class="baseForm">
             
                <h1 class="title">收款方式</h1>
                <div class="Payment">
                    <div class="payStyle clear">
                        <p class="fl active"><img src="${oss_url}/static/mobile2018/css/agent/images/wx.png" alt="" />微信支付</p>
                        <p class="fl"><img src="${oss_url}/static/mobile2018/css/agent/images/jfb.png" alt="" />支付宝支付</p>
                        <p class="fr"><img src="${oss_url}/static/mobile2018/css/agent/images/card.png" alt="" />银行卡支付</p>
                    </div>
                    <div class="pay_con active upload sss">
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
                                  <p class="img"><img src="${fpayaccount.weixin}" id="wxurl"></p>
                                  </c:if>
                                  <a class="radius upload_original">
                                  <label for="pic1" class="s1" id="wxlabel" style="width: 100%;height: 100%;display: block;">
                                  <c:if test="${fpayaccount.weixin == '' }">
                                  <em class="cx">上传<strong class="str">微信</strong>收款二维码</em></label>
                                  </c:if>
                                  </a>
                                  <input id="pic1" type="file" onchange="uploadImg1()" style="display: none;">
                                  <input type="hidden" id="pic1Url" name="pic1Url" value="${fpayaccount.weixin}">
                                </div>
                              </div>
                            </div>
                        </div>
                        <label class="lab">
                             启用&nbsp;<input class="checkbox" type="checkbox" id="openweixin" name="openweixin" value="1"   <c:if test="${fpayaccount.openweixin == 1 }">  checked  </c:if>>
                         <span></span>
                        </label>
                        <button class="tj" onclick="saveAgentInfo()" type="button">提交</button>
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
                                  <a class="radius upload_original" style="top: 0;height: 6rem;"><label for="pic2" class="s1" id="alilabel" style="width: 100%;height: 100%;display: block;">
                                  <c:if test="${fpayaccount.alipay == '' }">
                                  <em class="cx">上传<strong style="color:#00a9f2;">支付宝</strong>收款二维码</em>
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
                        <button class="tj" onclick="saveAgentInfo()" type="button">提交</button>
                    </div>
                    <div class="pay_con payInformation">
                         <div>
                             <p class="commH">账户名</p>
                            <label for=""><input class="commH txt" type="text" value="${fpayaccount.fbankperson}" id="fbankperson" name="fbankperson" /></label>
                         </div>                         
                         <div>
                             <p class="commH">开户行</p>
                            <label for=""><input class="commH txt" type="text" value="${fpayaccount.fbankname}" id="fbankname" name="fbankname" /></label>
                         </div>
                         <div>
                             <p class="commH">账号</p>
                            <label for=""><input class="commH txt" type="text" value="${fpayaccount.fbanknumber}" id="fbanknumber" name="fbanknumber" /></label>
                         </div>
                         <div>
                             <p class="commH">开户行地址</p>
                            <label for=""><input class="commH txt" type="text" value="${fpayaccount.fbankaddress}" id="fbankaddress" name="fbankaddress" /></label>
                         </div>
                         <div>
                             <p class="commH">分行名称</p>
                            <label for=""><input class="commH txt" type="text" value="${fpayaccount.fbankothers}" id="fbankothers" name="fbankothers" /></label>
                         </div>
                         <label class="lab">
                        启用&nbsp;<input class="checkbox" type="checkbox" id="openbank" name="openbank" value="1" <c:if test="${fpayaccount.openbank == 1 }">  checked  </c:if> >
                        <span></span>
                    </label>
                    <button class="tj" onclick="saveAgentInfo()" type="button">提交</button>
                    </div>                               
                    
                    <div class="clear"></div>
                </div>
<!--                 <button class="tj" onclick="saveAgentInfo()" type="button">提交</button>
 -->            </div>
             </form>
        </div>
    </section>

 

<%@include file="../comm/footer.jsp" %>	


<script type="text/javascript" src="${oss_url}/static/mobile2018/js/plugin/ajaxfileupload.js?v=20181126201750"></script>
   <script type="text/javascript" src="${oss_url}/static/mobile2018/js/plugin/fileCheck.js?v=20181126201750"></script>
<script type="text/javascript" src="${oss_url}/static/mobile2018/js/plugin/jquery.qrcode.min.js?v=20181126201750"></script>

<script type="text/javascript" src="${oss_url}/static/mobile2018/js/plugin/jquery-migrate-1.4.1.min.js?v=20181126201750"></script>
<script>
$(".backIcon").click(function(){
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
	function saveAgentInfo()
	{
		var param = $('#agentinfoForm').serialize();
		jQuery.post('/agent/savepayaccount.html', param, function (data) {
		    console.log(data);
        	if (data.code == -1) {
				util.layerAlert("", data.msg, 2);
				
			} else {
				util.layerAlert("", data.msg, 1);
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
