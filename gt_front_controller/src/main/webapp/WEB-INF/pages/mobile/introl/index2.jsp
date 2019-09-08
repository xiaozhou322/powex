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
<body >
	

 
<%@include file="../comm/header.jsp" %>

    <header class="tradeTop">
     <a href="/introl/mydivide.html">
     	<span class="toback2"></span>
        <span class="tit"><spring:message code="introl.income.detail" /></span>
     </a>
     <a class="lookGZ lookRules" ><spring:message code="m.security.see" /></a>
    </header>
<div class="mydivide">
    <div class="invitation">
    
             <ul>
             	<li><img src="${oss_url}/static/mobile2018/images/2@3x.png" alt="" /></li>
             	<li>
             		<p class="tgformP">
                        	<span><spring:message code="introl.one.people" /></span>：<b>${fristtotal }</b><br>
                        	<span><spring:message code="introl.winrat" /></span>：<b><fmt:formatNumber type="number" value="${firsttranslation}" pattern="0.00" maxFractionDigits="2"/>%</b>
                       </p>
             	</li>
                 <li>
                    <p class="tgformPl">
                        	<span><spring:message code="introl.two.people" /></span>：<b>${introTotal }</b><br>
                        	<span><spring:message code="introl.winrat" /></span>：<b><fmt:formatNumber type="number" value="${twotranslation}" pattern="0.00" maxFractionDigits="2"/>%</b>
                       </p>
                 </li>
             </ul>
    </div>
</div>
<dl class="clear">
        <dd class='fl promote_link'>
                <p><spring:message code="introl.affi.link" /> <span>${spreadLink }</span></p>
            </dd>
            
        </dl>
<div class="tg_detail">
    <ol class="clear">
        <li class="fl"><a href="/introl/mydivide.html?type=1&tab=2"><spring:message code="introl.affi.detail" /></a></li>
        <li class="fr active"><a href="javascript:void(0)"><spring:message code="introl.income.detail" /></a></li>
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
                    <img src="${oss_url}/static/mobile2018/images/noMsg.png" width="86" alt="" />
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
        <p><spring:message code="m.security.getgt" />
         </p>
<!--         <span class="close_show">×</span>
 -->    </div>
    <div class="yqCode">
        <h1><img src="${oss_url}/static/mobile2018/images/gt.png " alt="" /></h1>
        <div class="pic"><span id="qrcode"></span></div>
        <p><spring:message code="m.security.erweim" /></p>
    </div>
</div>




<%-- <%@include file="../comm/tabbar.jsp"%> --%>

<%@include file="../comm/footer.jsp" %>	
 <script src="${oss_url}/static/mobile2018/js/fluckydraw/com.js?v=20181126201750"></script>
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