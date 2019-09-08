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
<style type="text/css">

</style>
</head>
<body >
<%@include file="../comm/header.jsp" %>

<div class="entrust_recorde vipCon" id="top1">
    <header class="tradeTop">
     <a href="/user/security.html">
        <span class="toback2"></span>
        <span class="tit"><spring:message code="newuser.financial.benefits" /></span>
     </a>
    </header>
    <div class="vip_list">
        <dl onclick="window.location.href='/introl/mydivide.html?tab=2'">
            <a href="javascript:;" class="clear">
                <dt class='fl'><img src="${oss_url}/static/mobile2018/images/vip1.png" alt="" /> </dt>
                <dd class='fl'><spring:message code="introl.title" /></dd>
            </a>
        </dl>        
        <dl onclick="window.location.href='/introl/redPocket.html'">
            <a href="javascript:;" class="clear">
                <dt class='fl'><img src="${oss_url}/static/mobile2018/images/vip2.png" alt="" /> </dt>
                <dd class='fl'><spring:message code="newuser.financial.Candy" /></dd>
            </a>
        </dl>        
        <dl onclick="window.location.href='/introl/tradingRewards.html'">
            <a href="javascript:;" class="clear">
                <dt class='fl'><img src="${oss_url}/static/mobile2018/images/vip3.png" alt="" /> </dt>
                <dd class='fl'><spring:message code="newuser.financial.rewards" /></dd>
            </a>
        </dl>
    </div>

</div>

<div id="tabup1" style="display: none;height: 12.5rem;">
    <header class="tradeTop">
	    <a href="/introl/mydivide.html">
	    	<span class="toback2"></span>
	        <span class="tit"><spring:message code="introl.title" /></span>
	    </a>
	    <a class="lookGZ lookRules" ><spring:message code="m.security.see" /></a>
    </header>
<div class="mydivide" >
  <!--   <header class="tradeTop" >
        <span class="soback toback2" id="sob"></span>
        <span class="back toback2" style="display: none" id="sotab"></span>
        <span class="tit"></span>
    </header> -->
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
<div class="tg_detail" style="height: 6.5rem;overflow: hidden;">
    <ol class="clear">
        <li class="fl active"><a href="javascript:void(0)"><spring:message code="introl.affi.detail" /></a></li>
        <li class="fr"><a href="/introl/mydivide.html?type=2"><spring:message code="introl.income.detail" /></a></li>
    </ol>
    <div class="tg_con" style="height:5.8rem;overflow: auto;">
        <div class="tgList">
            <ul>
            <c:forEach items="${fusers }" var="v" >
                <li class="clear">
                	<%-- <div class="tradeList_tit">
	                    <span class="textL"><spring:message code="introl.affi.member" />UID </span>
	                    <span class="textC"><spring:message code="introl.affi.time" /></span>
	                    <span class="textR"><spring:message code="introl.affi.isauth" /></span>
                	</div> --%>
                   <%--  <div class="fl li_con"> 
                        <span class="textL">${v.fid }</span>
                    	<span class="textC"><fmt:formatDate value="${v.fregisterTime }" pattern="yyyy/MM/dd HH:mm:ss" /></span>
                    </div> 
                     <div class="fl li_con">     
                        <span class="textR">
		                    <c:if test="${v.fisValid }">
							<img style="right:1px;top:1px; height:24px;" src="${oss_url}/static/front/images/user/suc.png"><span>KYC1 &nbsp; </span>
							<c:choose>
				               <c:when test="${v.fhasImgValidate && v.fpostImgValidate}">
				               		<img style="right:1px;top:1px; height:24px;" src="${oss_url}/static/front/images/user/suc.png"><span>KYC2</span>
				               </c:when>
				               <c:otherwise>
				               		<img style="right:1px;top:1px; height:24px;" src="${oss_url}/static/front/images/user/nosuc.png"><span>KYC2</span>
				               </c:otherwise>
				             </c:choose>
							</c:if>
							<c:if test="${!v.fisValid }"><img style="right:1px;top:1px; height:24px;" src="${oss_url}/static/front/images/user/nosuc.png"><span>KYC1 &nbsp; </span><img style="right:1px;top:1px; height:18px;" src="${oss_url}/static/front/images/user/nosuc.png"><span>KYC2</span></c:if>
                        </span>
                    </div> --%>

                    <div class="fl li_con"> 
                        <span>ID:${v.fid }</span>
                       <p class="sy_time"><fmt:formatDate value="${v.fregisterTime }" pattern="yyyy/MM/dd HH:mm:ss" /></p>
                    </div>
                    <div class="fr li_con">
                     <p>是否实名认证 </p>
                     <p>
	                    <c:if test="${v.fisValid }">
							<img style="height: 0.24rem;padding-right: 0.1rem;vertical-align: -0.02rem;" src="${oss_url}/static/front/images/user/suc.png"><span>KYC1 &nbsp; </span>
						<c:choose>
			               <c:when test="${v.fhasImgValidate && v.fpostImgValidate}">
		               		<img style="height: 0.24rem;padding-right: 0.1rem;vertical-align: -0.02rem;" src="${oss_url}/static/front/images/user/suc.png"><span>KYC2</span>
			               </c:when>
			               <c:otherwise>
		               		<img style="height: 0.24rem;padding-right: 0.1rem;vertical-align: -0.02rem;" src="${oss_url}/static/front/images/user/nosuc.png"><span>KYC2</span>
			               </c:otherwise>
			             </c:choose>
						</c:if>
						<c:if test="${!v.fisValid }">
							<img style="height: 0.24rem;padding-right: 0.1rem;vertical-align: -0.02rem;" src="${oss_url}/static/front/images/user/nosuc.png">
							<span>KYC1 &nbsp; </span>
							<img style="height: 0.24rem;padding-right: 0.1rem;vertical-align: -0.02rem;" src="${oss_url}/static/front/images/user/nosuc.png">
							<span>KYC2</span>
						</c:if>
	                      
                       </p>
                        
                    </div>
                </li>
            </c:forEach>                
                  <c:if test="${fn:length(fusers)==0 }">
           <div class="noTxt textCenter mtop">
               <%--  <img src="${oss_url}/static/mobile2018/images/noMsg.png" width="86" alt="" /> --%>
                <p><spring:message code="json.jingji.noord" /></p>
           </div>
        </c:if>
            </ul>
        </div>
    </div>
</div>

<div class="click_invitation yq_btn btn6">
<%-- 	<img alt="" src="${oss_url}/static/mobile2018/images/button@3x.png"> --%>
	<span class="buttonBox"><spring:message code="json.invitation" /></span>
</div>
<div class="warpBg shareWarp">
    <div class="lookShow">
        <h2><spring:message code="m.security.acrules" /></h2>
        <p><spring:message code="m.security.getgt" /> </p>
    </div>

	<div class="yqCode shareWarp2">
       	<p><spring:message code="m.security.erweim" /></p>
        <div class="pic"><span id="qrcode"></span></div>
       <div class="pro_picture">
           	<div class="invite_content">
	       		<spring:message code="m.invite.con1" />
	       		<br><spring:message code="m.invite.con2" />
        	</div>
       </div>
    </div>
  </div>
</div>
</div>
<%-- <%@include file="../comm/tabbar.jsp"%> --%>
<%@include file="../comm/footer.jsp" %>
 <script src="${oss_url}/static/mobile2018/js/fluckydraw/com.js?v=20181126201750"></script>
<script type="text/javascript" src="${oss_url}/static/mobile2018/js/plugin/clipboard.min.js?v=20181126201750"></script>
	<script type="text/javascript" src="${oss_url}/static/mobile2018/js/plugin/jquery.qrcode.min.js?v=20181126201750"></script>
<script type="text/javascript">
// 将【复制】按钮充当复制数据的元素载体
var clip = new Clipboard('.userInvite-linkCopy');
clip.on('success', function(e) {
		   	$('.userInvite-linktips').show();
		    e.clearSelection();
		});

jQuery(document).ready(function() {
	 if (navigator.userAgent.indexOf("MSIE") > 0) {
			jQuery('#qrcode').qrcode({
				text : '${spreadLink }',
				width : "250",
				height : "250",
				render : "table"
			});
		} else {
			jQuery('#qrcode').qrcode({
				text : '${spreadLink }',
				width : "250",
				height : "250"
			});
		}
	});

    $(function(){
      var tabnums = window.location.href;
      var nums = tabnums.indexOf("tab=");
      var tab = tabnums.substr(nums+4, 3);
      if(tab == 2){
        $("#top1").css('display','none');
        $("#tabup1").css('display','block');
      }
      
  });

</script>


</body>
</html>