<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ include file="../comm/include.inc.jsp"%>

<!doctype html>
<html>
<head> 
<%@ include file="../comm/basepath.jsp"%>sePath%>" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="../comm/link.inc.jsp" %>
<link href="${oss_url}/static/front2018/css/exchange/common.css?v=20181126201750" rel="stylesheet" type="text/css" />
<link href="${oss_url}/static/front2018/css/exchange/main.css?v=1" rel="stylesheet" type="text/css" />
<!-- <link href="${oss_url}/static/front/css/exchange/common.css?v=20181126201750" rel="stylesheet" type="text/css" />
<link href="${oss_url}/static/front/css/exchange/main.css?v=20181126201750" rel="stylesheet" type="text/css" /> -->

</head>
<body class="lw-UstdBody">
	 
<%@include file="../comm/header.jsp" %>
<section>
        <div class="buyUstdMain">
            <div class="steps">
                <ol>
                    <li class="fl active textLeft">
                        <span></span>
                        <em><spring:message code="financial.usdtrecharge.step1" /></em>
                    </li>
                    <li class="fl textCenter">
                    </li>                    
                    <li class="fr textRight">   
                        <span></span>
                        <em><spring:message code="financial.usdtrecharge.step3" /></em>
                    </li>
                    <div class="clear"></div>
                </ol>
            </div>
            <div class="steps_1">
                <h1 class="textCenter Color333"><spring:message code="financial.exchangeusdt.fillexchange" /></h1>
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
                    <div class="commW">
                        <span><spring:message code="financial.usdtrewithdrawal.remarknum" /></span>
                        <input class="txt" type="text" value="${code }" placeholder="<spring:message code="financial.exchangeusdt.fillcode" />" id="exchangecode" autocomplete="off"/>
                    </div>
                    <c:if test="${code=='' }">
                    	<div class="getUsdtCard" style="margin-top:10px;"><a href="/exchange/withdrawUsdt.html" class="cblue"><spring:message code="financial.exchangeusdt.getcode" /></a></div>
                    </c:if>
                    <!-- 添加人机验证 -无痕验证 -->
					<div class="tr_txt" id="nvc_validate"></div>
                </div>
                <div class="form-group" style="margin:0 0 5px 0;">
					<span for="withdrawerrortips" class="col-xs-3 control-label"></span>
					<div class="textLeft textBox">
						<label id="withdrawerrortips" class="text-danger">
						</label>
					</div>
				</div>
                <button id="exchangeUsdtButton" class="UBtn mtop20 UBtn2"><spring:message code="financial.exchangeusdt.next" /></button>
            </div>
        </div>
    </section>

	<input id="feesRate" type="hidden" value="${fee }">
	<input id="userBalance" type="hidden" value="${requestScope.fwallet.ftotal }">
	

<%@include file="../comm/usdfooter.jsp" %>
<script type="text/javascript" src="${oss_url}/static/front2018/js/comm/msg.js?v=20181126201750"></script>
<script type="text/javascript" src="${oss_url}/static/front2018/js/finance/account.withdraw.js?v=12"></script>
<script type="text/javascript" src="${oss_url}/static/front2018/js/finance/city.min.js?v=20181126201750"></script>
<script type="text/javascript" src="${oss_url}/static/front2018/js/finance/jquery.cityselect.js?v=20181126201750"></script>
<script type="text/javascript" src="${oss_url}/static/front2018/js/index/main.js?v=20181126201750"></script>	
<script>
	var appkey = "FFFF00000000017F11B9";
	var scene = "nvc_other";
    window.NVC_Opt = {
        //无痕配置 && 滑动验证、刮刮卡通用配置
        appkey:appkey,
        scene:scene,
        isH5:false,
        popUp:false,
        renderTo:'#nvc_validate',
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
</body>
</html>
