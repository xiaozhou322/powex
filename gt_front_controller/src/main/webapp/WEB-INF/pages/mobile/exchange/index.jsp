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
<style type="text/css">
    html,body,.lw-usdtBody{height:100%;}
</style>
<body class="lw-UstdBody">
	


  <%@include file="../comm/header.jsp" %>

<header class="tradeTop">  
    <i class="back"></i>
    <h2 class="tit"><spring:message code="nav.top.exchange" /></h2>
</header>
<section class="section">
    <div class="ustdBtns">  
        <ul>
            <li>
                <a href="/exchange/rechargeUsdt.html">
                    <h1><spring:message code="financial.usdtrecharge" />   </h1>
                    <small>CNY-USDT</small>
                    <div class="clear"></div>
                </a>
            </li>            
            <li>
                <a href="/exchange/withdrawUsdt.html">
                    <h1><spring:message code="financial.usdtrewithdrawal.exchange" />  </h1>
                    <small>USDT-CNY</small>
                    <div class="clear"></div>

                </a>
            </li>            
            <li>
                <a href="/exchange/recordUsdt.html">
                    <h1><spring:message code="financial.exchangeusdt.viewrec" />  </h1>
                    <div class="clear"></div>
                </a>
            </li>
        </ul>
    </div>
</section>

<%@include file="../comm/footer.jsp" %>	
	
    <script src="${oss_url}/static/mobile2018/js/fluckydraw/com.js?v=20181126201750"></script>
	<script type="text/javascript" src="${oss_url}/static/mobile2018/js/msg.js?v=20181126201750"></script>
	<script type="text/javascript" src="${oss_url}/static/mobile2018/js/finance/account.withdraw.js?v=20181126201750"></script>
	<script type="text/javascript" src="${oss_url}/static/mobile2018/js/finance/city.min.js?v=20181126201750"></script>
	<script type="text/javascript" src="${oss_url}/static/mobile2018/js/finance/jquery.cityselect.js?v=20181126201750"></script>

</body>
<script type="text/javascript">
    $(".backIcon").click(function(){
        window.location.href = "/user/security.html";
    });
</script>
</html>
