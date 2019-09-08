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

<div class="entrust_recorde vipCon" id="top1">
    <header class="tradeTop">
        <span class="back toback2"></span>
        <span><spring:message code="newuser.financial.benefits" /></span>
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

<div id="tabup1" style="display: none">
<div class="mydivide" >
    <header class="tradeTop">
        <span class="back"></span>
        <span><spring:message code="introl.title" /></span>
    </header>
    <div class="invitation">
             <ul>
             	<li><img src="${oss_url}/static/mobile2018/images/2@3x.png" alt="" /></li>
             	<li><a class="lookGZ lookRules" ><spring:message code="m.security.see" /></a></li>
                 <li>
                     <p class="tgformP">
                        	<span><spring:message code="introl.one.people" /></span>&nbsp;&nbsp;<b>123456</b><br>
                        	<span><spring:message code="introl.winrat" /></span>&nbsp;&nbsp;<b>123456</b>
                       </p>
                      <p class="tgformPl">
                        	<span><spring:message code="introl.two.people" /></span>&nbsp;&nbsp;<b>123456</b><br>
                        	<span><spring:message code="introl.winrat" /></span>&nbsp;&nbsp;<b>123456</b>
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
        <li class="fl active"><a href="javascript:void(0)"><spring:message code="introl.affi.detail" /></a></li>
        <li class="fr"><a href="/introl/mydivide.html?type=2"><spring:message code="introl.income.detail" /></a></li>
    </ol>
    <div class="tg_con">
        <div class="tgList">
            <ul>
            <c:forEach items="${fusers }" var="v" >
                <li class="clear">
                    <div class="fl li_con"> 
                        <b>ID:${v.fid }</b>
                         <c:if test="${v.fisValid }">
                        <p><spring:message code="introl.affi.isauth" />: <img style="right:1px;top:1px; height:24px;" src="${oss_url}/static/mobile2018/images/user/suc.png">KYC1 </p>
                        <c:choose>
                               <c:when test="${v.fhasImgValidate && v.fpostImgValidate}">
                                    <img style="right:1px;top:1px; height:24px;" src="${oss_url}/static/mobile2018/images/user/suc.png">KYC2  
                               </c:when>
                               <c:when test="${!v.fhasImgValidate && v.fpostImgValidate}">
                                    <img style="right:1px;top:1px; height:24px;" src="${oss_url}/static/mobile2018/images/user/nosuc.png">KYC2 
                               </c:when>
                               <c:otherwise>
                                    <img style="right:1px;top:1px; height:24px;" src="${oss_url}/static/mobile2018/images/user/nosuc.png">KYC2  
                               </c:otherwise>
                             </c:choose>
                        </c:if>
                        <c:if test="${!v.fisValid }">
                            <p><spring:message code="introl.affi.isauth" />: <spring:message code="introl.affi.noauth" /></p>
                        </c:if>
                    </div>
                    <div class="fr li_con">
                        <p class="sy_time"><fmt:formatDate
                  value="${v.fregisterTime }" pattern="yyyy/MM/dd" /></p>
                    </div>
                </li>
            </c:forEach>                
                  <c:if test="${fn:length(fusers)==0 }">
           <div class="noTxt textCenter mtop">
                <img src="${oss_url}/static/mobile2018/images/noMsg.png" width="86" alt="" />
                <p><spring:message code="json.jingji.noord" /></p>
           </div>
        </c:if>
            </ul>
        </div>
    </div>
</div>

<div class="click_invitation yq_btn btn6">
	<img alt="" src="${oss_url}/static/mobile2018/images/button@3x.png">
	<span class=""><spring:message code="json.invitation" /></span>
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
	                    	<div style="font-size: 0.28rem;line-height: 0.5rem;">*邀请好友获得推荐人奖励<br>参与平台抽奖可有机会获得平台超级大奖10000ETH超级大奖</div>
	                    	
	                    	<!-- <ul class="invite_cont2">
	                    		<li>10000ETH</li>
	                    		<li><img alt="" src="${oss_url}/static/mobile2018/images/A@3x.png"></li>
	                    		<li>A</li>
	                    		<li>超级大奖</li>
	                    		<li>（最高奖金获得者）</li>
	                    	</ul>
	                    	
                    	<ul class="invite_cont2">
	                    		<li>1000ETH</li>
	                    		<li><img alt="" src="${oss_url}/static/mobile2018/images/B@3x.png"></li>
	                    		<li>B</li>
	                    		<li>引荐奖</li>
	                    		<li>（A的推荐人B）</li>
	                    	</ul>
	                    	
	                    	
	                    	<ul class="invite_cont2">
	                    		<li>10000ETH</li>
	                    		<li><img alt="" src="${oss_url}/static/mobile2018/images/C@3x.png"  width="89px" height="63px"></li>
	                    		<li>C</li>
	                    		<li>溯源奖</li>
	                    		<li>（B的推荐人C）</li>
	                    	</ul> -->
	                    	</div>
                    </div>
        </div>
    </div>
</div>
</div>
<%@include file="../comm/tabbar.jsp"%>
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