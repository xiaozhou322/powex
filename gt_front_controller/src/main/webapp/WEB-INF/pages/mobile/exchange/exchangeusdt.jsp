<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../comm/include.inc.jsp"%>
<%

%>

<!doctype html>
<html>
<head> 
<%@ include file="../comm/basepath.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
 <meta name = "viewport" content = "width = device-width, user-scalable = no,initial-scale=0.5,maximum-scale=0.5,minimum-scale=0.5">
<%@include file="../comm/link.inc.jsp" %>

</head>
<body class="lw-UstdBody2">
	<input type="hidden" id="max_double" value="${requestScope.constant['maxwithdrawcny'] }">
	<input type="hidden" id="min_double" value="${requestScope.constant['minwithdrawcny'] }">
	

  <%@include file="../comm/header.jsp" %>
	<header class="tradeTop tradeTop2">  
    <i class="back toback2"></i>
    <h2 class="tit"><spring:message code="nav.top.exchange" /></h2>
</header>    
<section>
        <div class="buyUstdMain">
            <div class="steps">
                <ol>
                    <li class="fl active textLeft">
                        <span></span>
                        <em><spring:message code="financial.usdtrecharge.step1" /></em>
                    </li>
                    <li class="fl textCenter"></li>                    
                    <li class="fr textRight">
                        <span></span>
                        <em><spring:message code="financial.usdtrecharge.step3" /></em>
                    </li>
                    <div class="clear"></div>
                </ol>
            </div>
            <div class="steps_1">
                <div class="stepsForm">
                    <h2 class="commW"><spring:message code="financial.exchangeusdt.username" /></h2>
                    <div class="commW comBorder textCenter">${fuser.frealName }</div>
                    <h2 class="commW"><spring:message code="financial.exchangeusdt.userID" /></h2>
                    <div class="commW">
           			<div class="commW comBorder textCenter">${fuser.fid }</div>
                    </div> 
                    <h2 class="commW"><spring:message code="financial.usdtrewithdrawal.remarknum" /></h2>
                    <div class="commW comBorder">
                        <input type="text" class="txt textCenter" id="exchangecode" placeholder="<spring:message code="financial.exchangeusdt.fillcode" />" value="${code }" />
                    </div>
                    <!-- 添加人机验证 -无痕验证 -->
					<div class="commW comBorder" id="nvc_validate"></div>
                </div>
                <h4 class="colorRed mtop2"><spring:message code="financial.exchangeusdt.ratenote" /></h4>
                <button id="exchangeUsdtButton" class="btn UBtn mtop2 startBtn"><spring:message code="financial.exchangeusdt.next" /></button>
            </div>
        </div>
    </section>
	
	
	<input id="feesRate" type="hidden" value="${fee }">
	<input id="userBalance" type="hidden" value="${requestScope.fwallet.ftotal }">
	


<%@include file="../comm/footer.jsp" %>	
	
  	<script src="${oss_url}/static/mobile2018/js/fluckydraw/com.js?v=20181126201750"></script>
	<script type="text/javascript" src="${oss_url}/static/mobile2018/js/msg.js?v=20181126201750"></script>
	<script type="text/javascript" src="${oss_url}/static/mobile2018/js/finance/account.withdraw.js?v=20181126201750"></script>
	<script type="text/javascript" src="${oss_url}/static/mobile2018/js/finance/city.min.js?v=20181126201750"></script>
	<script type="text/javascript" src="${oss_url}/static/mobile2018/js/finance/jquery.cityselect.js?v=20181126201750"></script>
	<script type="text/javascript" src="${oss_url}/static/mobile2018/js/language/language_<spring:message code="language.title" />.js?v=20171025221650.js"></script>

</body>
<script type="text/javascript">
	$(".backIcon").click(function(){
		window.history.go(-1);
	});
</script>
<script>
	var appkey = "FFFF00000000017F11B9";
	var scene = "nvc_other_h5";
	window.NVC_Opt = {
        //无痕配置 && 滑动验证、刮刮卡通用配置
        appkey:appkey,
        scene:scene,
        isH5:true,
        popUp:false,
        renderTo:'#nvc_validate',
        nvcCallback:function(data){
            // data为getNVCVal()的值，此函数为二次验证滑动或者刮刮卡通过后的回调函数
            // data跟业务请求一起上传，由后端请求AnalyzeNvc接口，接口会返回100或者900
        },
        trans: {"key1": "code0","nvcCode":400},
        language: "cn",
        //滑动验证长度配置
        customWidth: 300,
        //刮刮卡配置项
        width: 300,
        height: 100,
        elements: [
            '//img.alicdn.com/tfs/TB17cwllsLJ8KJjy0FnXXcFDpXa-50-74.png',
            '//img.alicdn.com/tfs/TB17cwllsLJ8KJjy0FnXXcFDpXa-50-74.png'
        ], 
        bg_back_prepared: '//img.alicdn.com/tps/TB1skE5SFXXXXb3XXXXXXXXXXXX-100-80.png',
        bg_front: 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAGQAAABQCAMAAADY1yDdAAAABGdBTUEAALGPC/xhBQAAAAFzUkdCAK7OHOkAAAADUExURefk5w+ruswAAAAfSURBVFjD7cExAQAAAMKg9U9tCU+gAAAAAAAAAIC3AR+QAAFPlUGoAAAAAElFTkSuQmCC',
        obj_ok: '//img.alicdn.com/tfs/TB1rmyTltfJ8KJjy0FeXXXKEXXa-50-74.png',
        bg_back_pass: '//img.alicdn.com/tfs/TB1KDxCSVXXXXasXFXXXXXXXXXX-100-80.png',
        obj_error: '//img.alicdn.com/tfs/TB1q9yTltfJ8KJjy0FeXXXKEXXa-50-74.png',
        bg_back_fail: '//img.alicdn.com/tfs/TB1w2oOSFXXXXb4XpXXXXXXXXXX-100-80.png',
        upLang:{"cn":{
        	_ggk_guide: language["aliyun.nvc.ggk_guide"],
            _ggk_success: language["aliyun.nvc.ggk_success"],
            _ggk_loading: language["aliyun.nvc.ggk_loading"],
            _ggk_fail: language["aliyun.nvc.ggk_fail"],
            _ggk_action_timeout: language["aliyun.nvc.ggk_action_timeout"],
            _ggk_net_err: language["aliyun.nvc.ggk_net_err"],
            _ggk_too_fast: language["aliyun.nvc.ggk_too_fast"]
            }
        }
    }
    
</script>
<script src="//g.alicdn.com/sd/nvc/1.1.112/guide.js?v=20181126201750"></script>
</html>
