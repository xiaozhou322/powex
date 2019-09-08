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
<%@include file="../comm/link.inc.jsp" %>

</head>
<body>
<input type="hidden" id="type" value="1"/>
<%@include file="../comm/header.jsp" %>
<section>
    <div class="mg">
        <div class="l_finance clear">
            <div class=" fl">
                <div class="firstNav">
				<%@include file="../comm/left_menu.jsp" %>
                </div>
            </div>
            <div class="l_financeR fr">
                <div class="assetList loginRecord promote">
                        <h3 class="assetTitle"><spring:message code="introl.invite" /><span style="color:red;font-size:12px;margin-left:10px;letter-spacing: 1px;">*每邀请一个用户注册成功并完成KYC2认证即赠送1POW</span></h3>
                       		
                        <!-- <h3 class="assetTitle"><a href="javascript:void(0)" class="active"><spring:message code="introl.affi.detail" /> </a></h3>  -->
                       <%--  <a href="/introl/mydivide.html?type=2"><spring:message code="introl.income.detail" /></a> --%>
                    <div class="tgForm">
                        <div class="tgForm_bg">
                        	<ul>
                        		<li><img alt="" src="${oss_url}/static/front2018/images/groupt.png"></li>
                        		<li>
                        			<p class="tgformP">
                        				<span><spring:message code="introl.one.people" /></span><b>${fristtotal }</b><br>
                        				<span><spring:message code="introl.winrat" /></span><b>
                                   <fmt:formatNumber type="number" value="${firsttranslation}" pattern="0.00" maxFractionDigits="2"/>%</b>
                        			</p>
                        			<p class="tgformPl">
                        			<span><spring:message code="introl.two.people" /></span><b>${introTotal }</b><br>
                        			<span><spring:message code="introl.winrat" /></span><b>
                        			<fmt:formatNumber type="number" value="${twotranslation}" pattern="0.00" maxFractionDigits="2"/>%</b>
                        			
                        			
                        			</b>
                        			</p>
                        			
                        		</li>
                        		<li>
                        			<img alt="" src="${oss_url}/static/front2018/images/13.png">
                        		</li>
                        		<li id="hidden_enent">
                        			<span class="">邀请好友</span><br>
                        			<span class="">享受更多福利</span>
                        			
                        		</li>
                        	</ul>
                        </div>
                    </div>
                    <div class="a_link">
                    		<spring:message code="introl.affi.link" />
                            <input id="link" readonly="" value="${spreadLink}" type="text">
                                 &nbsp;&nbsp;
                          <img class="link_cp" alt="" src="${oss_url}/static/front2018/images/button@2x.png">
                          <span class="a_linkcopy link_cp userInvite-linkCopy" data-clipboard-target="#link"><spring:message code="introl.affi.copylink" /></span>
                          <span class="userInvite lightBlue userInvite-linktips" style="margin-left: -42px;"><spring:message code="introl.affi.copysuc" />√</span>
                    </div>
                    
                     <div class="a_link" style="font-size:15.5px;margin:-33px 0 29px 17px;color:#cc6757;">
                    		<spring:message code="introl.affi.invitation" />
                            <input id="link" readonly="" value="${FuserNo}" type="text" style="color:#cc6757;border: 0px;width:95px;margin: 7px 0 0 24px;">
                                 &nbsp;&nbsp;
                    </div>
                    
                    <div class="pro_picture">
                    	<div class="pro_hint">推广专属二维码</div><br>
                    	<div id="qrcode"></div>
                    </div>
                    <div class="cli_invite" hidden="hidden">
                    	<div class="invite_content">
	                    	<span>*邀请好友获得推荐人奖励</span>
	                    	<span class="close_times">&times;</span><br>
	                    	<div class="invite_cont1">参与平台抽奖可有机会获得平台超级大奖10000ETH超级大奖</div>
	                    	<ul class="invite_cont2">
	                    		<li>10000ETH</li>
	                    		<li><img alt="" src="${oss_url}/static/front2018/images/a@2x.png"></li>
	                    		<li>A</li>
	                    		<li>超级大奖</li>
	                    		<li>（最高奖金获得者）</li>
	                    	</ul>
	                    	<ul class="invite_arrows">
	                    		<li><img alt="" src="${oss_url}/static/front2018/images/jiangtou1@2x.png" width="50px" height="20px"></li>
	                    	</ul>
                    	
                    	<ul class="invite_cont2">
	                    		<li>1000ETH</li>
	                    		<li><img alt="" src="${oss_url}/static/front2018/images/b@2x.png"></li>
	                    		<li>B</li>
	                    		<li>引荐奖</li>
	                    		<li>（A的推荐人B）</li>
	                    	</ul>
	                    	<ul class="invite_arrows">
	                    		<li><img alt="" src="${oss_url}/static/front2018/images/jiangtou1@2x.png" width="50px" height="20px"></li>
	                    	</ul>
	                    	
	                    	<ul class="invite_cont2">
	                    		<li>100ETH</li>
	                    		<li><img alt="" src="${oss_url}/static/front2018/images/c@2x.png"  width="89px" height="63px"></li>
	                    		<li>C</li>
	                    		<li>溯源奖</li>
	                    		<li>（B的推荐人C）</li>
	                    	</ul>
	                    	</div>
                    </div>
                    <div class="assetCon_list">
                    	<h1 class="assetTitle detail_list" ><spring:message code="introl.affi.details" /></h1>
                        <ol class="infoTit clear">
                            <li><spring:message code="introl.affi.member" />UID </li>
                            <li><spring:message code="introl.affi.time" /></li>
                            <li><spring:message code="introl.affi.isauth" /></li>
                        </ol>
                        <ul class="infoCon">
                        <c:forEach items="${fusers }" var="v" >
                            <li>
                                <div class="coinInfo">
                                    <span class="s-1">${v.fid }</span>
                                    <span class="s-1"><fmt:formatDate value="${v.fregisterTime }" pattern="yyyy-MM-dd HH:mm:ss" /></span>
                                    <span class="s-1">
	                                    <em class="noProm">
	                                    <c:if test="${v.fisValid }">
										<img style="right:1px;top:1px; height:18px;" src="${oss_url}/static/front/images/user/suc.png"><span>KYC1 &nbsp; </span>
										<c:choose>
							               <c:when test="${v.fhasImgValidate && v.fpostImgValidate}">
							               		<img style="right:1px;top:1px; height:18px;" src="${oss_url}/static/front/images/user/suc.png"><span>KYC2</span>
							               </c:when>
							               <c:otherwise>
							               		<img style="right:1px;top:1px; height:18px;" src="${oss_url}/static/front/images/user/nosuc.png"><span>KYC2</span>
							               </c:otherwise>
							             </c:choose>
										</c:if>
										<c:if test="${!v.fisValid }"><img style="right:1px;top:1px; height:18px;" src="${oss_url}/static/front/images/user/nosuc.png"><span>KYC1 &nbsp; </span><img style="right:1px;top:1px; height:18px;" src="${oss_url}/static/front/images/user/nosuc.png"><span>KYC2</span></c:if>
										</em>
									</span>
                                    <div class="clear"></div>
                                </div>
                            </li>
                         </c:forEach>                            
                        	<c:if test="${fn:length(fusers)==0 }">
            								<span class="noMsg"> <spring:message code="introl.affi.norec" /> </span>
            							</c:if>	
                          
                        </ul>
                        <div class="page">
                         	${pagin }
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>

<%@include file="../comm/footer.jsp" %>
<script type="text/javascript" src="${oss_url}/static/front2018/js/plugin/clipboard.min.js?v=20181126201750"></script>
<script type="text/javascript" src="${oss_url}/static/front2018/js/plugin/jquery.qrcode.min.js?v=20181126201750"></script>
<script type="text/javascript">
// 将【复制】按钮充当复制数据的元素载体
var clip = new Clipboard('.userInvite-linkCopy');
clip.on('success', function(e) {
		   /* 	$('.userInvite-linktips').show(); */
		   util.showMsg("复制成功√");
		    e.clearSelection();
		});

$(".close_times").click(function(){
	$(".cli_invite").css("display","none");
})

$(function(){
  $('#hidden_enent').click(function(){
    if($('.cli_invite').is(':hidden')){
      $('.cli_invite').show();
    }
    else{
      $('.cli_invite').hide();
    }
  })
})



$(function () {
	var spreadLink = $("#link").val();
    //生成二维码
	createQrCode(spreadLink);

})

function createQrCode(spreadLink) {
    if (navigator.userAgent.indexOf("MSIE") > 0) {
       jQuery('#qrcode').qrcode({
         text : spreadLink,
         width : "224",
         height : "224",
         render : "canvas"
       });
     } else {
       jQuery('#qrcode').qrcode({
         text : spreadLink,
         width : "224",
         height : "224",
         render : "canvas"
       });
     }
 }
	
</script>
</body>
</html>