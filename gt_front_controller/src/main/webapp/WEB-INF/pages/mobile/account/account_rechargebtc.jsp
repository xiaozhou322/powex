<%@ page language ="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ include file="../comm/include.inc.jsp"%>
<%

%>

<!doctype html>
<html>
<head> 
<%@ include file="../comm/basepath.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name = "viewport" content = "width = device-width, user-scalable = no,initial-scale=0.5,maximum-scale=0.5,minimum-scale=0.5">
<style>
.rechargeMain{
    box-shadow: 0px 2px 9px 0px rgba(70, 90, 111, 0.14);
}
</style>
<%@include file="../comm/link.inc.jsp" %>
    <title><spring:message code="financial.recharge" /></title>
</head>

<body>
 <%@include file="../comm/header.jsp" %>
 <div class="recharge">
    <header class="tradeTop">
        <span class="back toback2"></span>
		<span>${fvirtualcointype.fShortName } <spring:message code="financial.recharge" /></span>	

    </header>
    <div class="rechargeMain">
    <c:if test="${fvirtualaddress.fadderess == null}">
        <p class="get_dzBtn getCoinAddress opa-link" data-fid="${fvirtualcointype.fid }"><spring:message code="financial.getadd" />&gt;&gt;</p>
     </c:if>
     <c:if test="${fvirtualaddress.fadderess != null}">
        <div class="cbCode">
            <div class="code qrcode" id="qrcode">
                
            </div>
            <p><spring:message code="newmobile.qr" /></p>
        </div>
        <div class="rechare_address">
            <p><c:if test="${fvirtualcointype.fisBts==true }">${fvirtualcointype.fname }<spring:message code="newmobile.zhaccount" />：${fvirtualcointype.mainAddr }<br />MEMO：</c:if>${fvirtualaddress.fadderess}</p>
           <!--  <span class='cgreen'><spring:message code="m.security.copy" /></span> -->
        </div>
        </c:if>
        <div class="remind">
        <c:if test="${fvirtualcointype.fisBts==true }"><p>${fvirtualcointype.fname }<spring:message code="newmobile.please" /></p></c:if>
            <spring:message code="financial.aftyoufill"  arguments="${fvirtualcointype.fname }" />
        <br />  
        <spring:message code="financial.thsminiamo"  arguments="${fvirtualcointype.fconfirm},${fvirtualcointype.fpushMinQty},${fvirtualcointype.fShortName }" />  
        </div>
    </div>
</div>


<%@include file="../comm/footer.jsp" %>
<input type="hidden" id="address" value="${fvirtualaddress.fadderess}">
<input type="hidden" id="symbol" value="${fvirtualcointype.fid }">
<script src="${oss_url}/static/mobile2018/js/fluckydraw/com.js?v=20181126201750"></script>
<script type="text/javascript" src="${oss_url}/static/mobile2018/js/account.recharge.js?v=3"></script>
<script type="text/javascript" src="${oss_url}/static/mobile2018/js/plugin/jquery.qrcode.min.js?v=20181126201750"></script>
<script type="text/javascript" src="${oss_url}/static/mobile2018/js/clipboard.min.js?v=3"></script>

<script type="text/javascript">
	jQuery(document).ready(function() {
	 if (navigator.userAgent.indexOf("MSIE") > 0) {
			jQuery('#qrcode').qrcode({
				text : '${fvirtualaddress.fadderess}',
				width : "149",
				height : "143",
				render : "table"
			});
		} else {
			jQuery('#qrcode').qrcode({
				text : '${fvirtualaddress.fadderess}',
				width : "149",
				height : "143"
			});
		}
	});
</script>


</body>
</html>
