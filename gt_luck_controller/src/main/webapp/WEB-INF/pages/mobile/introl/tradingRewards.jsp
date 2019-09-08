<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
<body >
	

 
<%@include file="../comm/header.jsp" %>


    <header class="tradeTop">
        <span class="back toback2"></span>
        <span><%-- <spring:message code="introl.income.detail" /> --%>交易奖励</span>
    </header>
<div class="tg_detail">
    <ol class="clear">
        <li class="fl"><%-- <spring:message code="introl.affi.detail" /> --%>交易金额</li>
        |
        <li class="fr"><%-- <spring:message code="introl.income.detail" /> --%>收益日期</li>
    </ol>
    <div class="tg_con">
        <div class="tgList">
            <ul>
            <c:forEach items="${fintrolinfos }" var="v" >     
                <li class="clear">
                    <div class="fl li_con"> 
                        <p class="cred">${v.ftitle }</p>
                    </div>
                    <div class="fr li_con">
                        <p class="sy_time"><fmt:formatDate
                                    value="${v.fcreatetime }" pattern="yyyy/MM/dd HH:mm:ss" /> </p>
                    </div>
                </li>
            </c:forEach>                
                <c:if test="${fn:length(fintrolinfos)==0 }">
               <div class="noTxt textCenter mtop">
                    <img src="${oss_url}/static/mobile/images/noMsg.png" width="86" alt="" />
                    <p><spring:message code="json.jingji.noord" /></p>
               </div>
            </c:if>       
            </ul>
        </div>
    </div>
</div>

<div class="warpBg">
    <div class="lookShow">
        <h2><spring:message code="m.security.acrules" /></h2>
        <p><spring:message code="m.security.getgt" /> </p>
<!--         <span class="close_show">×</span>
 -->    </div>
    <div class="yqCode">
        <h1><img src="${oss_url}/static/mobile2018/images/gt.png " alt="" /></h1>
        <div class="pic"><span id="qrcode"></span></div>
        <p><spring:message code="m.security.erweim" /></p>
    </div>
</div>




<%@include file="../comm/tabbar.jsp"%>

<%@include file="../comm/footer.jsp" %>	
<script src="${oss_url}/static/mobile2018/js/fluckydraw/com.js"></script>
<script type="text/javascript" src="${oss_url}/static/mobile2018/js/plugin/clipboard.min.js?v=20181126201750"></script>
<script type="text/javascript" src="${oss_url}/static/mobile2018/js/plugin/jquery.qrcode.min.js?v=20181126201750"></script>

</body>
<script type="text/javascript">
//将【复制】按钮充当复制数据的元素载体
var clip = new Clipboard('.userInvite-linkCopy');
clip.on('success', function(e) {
		   	$('.userInvite-linktips').show();
		    e.clearSelection();
		});
		
jQuery(document).ready(function() {
	 if (navigator.userAgent.indexOf("MSIE") > 0) {
			jQuery('#qrcode').qrcode({
				text : '${spreadLink }',
				width : "149",
				height : "143",
				render : "table"
			});
		} else {
			jQuery('#qrcode').qrcode({
				text : '${spreadLink }',
				width : "149",
				height : "143"
			});
		}
	});

</script>
</html>