<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ include file="../comm/include.inc.jsp"%>
<%

%>

<!doctype html>
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="../comm/link.inc.jsp" %>

</head>
<body>
<%@include file="../comm/header.jsp" %>
   
<section>
    <div class="mg">
        <div class="clear" style="padding: 35px 0 0 0;">
            <div class="fl">
                <div class="firstNav">
                 <%@include file="../comm/left_menu.jsp" %> 
                </div>
            </div>
            <div class="l_financeR fr">
                <h2 class="financialCen"><a href="/user/security.html"><spring:message code="newuser.security.settings" /></a> 
                    <svg class="icon sfont16" aria-hidden="true">
                        <use xlink:href="#icon-youjiantouda"></use>
                    </svg>
                    <spring:message code="security.ideaut" />
                </h2>                
                <div class="anquan">
                    <h3 class="ts">
                        <span>
                            <svg class="icon" aria-hidden="true">
                                <use xlink:href="#icon-2shenfenzhenghaoma"></use>
                            </svg>
                            KYC1 <spring:message code="security.authentication" />
                             <c:if test="${fuser.fpostRealValidate && fuser.fhasRealValidate}">
                             <span class="s2 cred">(<spring:message code="security.authenticated" />)</span>
                             </c:if>
                             <c:if test="${!fuser.fpostRealValidate || !fuser.fhasRealValidate}">
                             <span class="s2">(<spring:message code="security.notcertified" />)</span>
                             </c:if>
                        </span>
                        &nbsp;&nbsp;
                        <span class="cred">
                            <svg class="icon sfont18" aria-hidden="true">
                                <use xlink:href="#icon-tishi2"></use>
                            </svg>
                            <spring:message code="security.acctotherele" />
                        </span>
                    </h3>
                    

                    <div class="aqMain accountBdMain">
                            
                        <div class="accoutType">

                            <div class="area accoutForm">
                                
                           <%--  <c:if test="${fuser.fpostRealValidate }">
                             ${fuser.frealName }:${fuser.fidentityNo_s }
                             </c:if> --%>
                             <p class="p2">
                    <%--             <c:if test="${fuser.fpostRealValidate && !fuser.fhasRealValidate}">
                                    <c:if test="${fuser.fareaCode=='86'}">
                                    <span class="step1-btn disabled"><spring:message code="security.inaudit" /></span>
                                    </c:if>
                                    <c:if test="${fuser.fareaCode!='86'}">
                                     <span class="step1-btn disabled"><spring:message code="security.waitupload" /></span>
                                    </c:if>
                                </c:if>
                                <c:if test="${fuser.fpostRealValidate && fuser.fhasRealValidate}">
                                <span class="step1-btn disabled cred"><spring:message code="security.authenticated" /></span>
                                </c:if> --%>

                      <%--      	<c:if test="${!fuser.fpostRealValidate || !fuser.fhasRealValidate}"> --%>
                                <div class="tr_txt">
                                     <span class="tr_l"><spring:message code="security.realname" /></span>
                                     <c:choose>
                                   
                                     <c:when test="${!fuser.fpostRealValidate || !fuser.fhasRealValidate}">
                                      <input type="text" class="inpBox" id="bindrealinfo-realname" placeholder="<spring:message code="security.pleasefillin" />">
                                     </c:when>
                                     <c:otherwise>
                                      <input type="text" disabled="disabled" readonly="readonly" class="inpBox"  value="${fuser.frealName}" >
                                     </c:otherwise>
                                       </c:choose>
                                      
                                </div>
                                <div class="tr_txt">
                                 <span class="tr_l"><spring:message code="security.areacountry" /></span>
                                 
                                  <c:choose>
                                   
                                     <c:when test="${!fuser.fpostRealValidate || !fuser.fhasRealValidate}">
                                      <select class="inpBox" name="" id="bindrealinfo-address">
                                        <option value="86" selected><spring:message code="security.area.chinamainland" /></option>
                           
                                     </select>
                                     </c:when>
                                     <c:otherwise>
                                      <select class="inpBox" disabled="disabled" style="background-color: rgb(235, 235, 228);padding-left: 6px;"name="" id="bindrealinfo-address">
                                        <option value="86" selected><spring:message code="security.area.chinamainland" /></option>                                                
                                     </select>
                                     </c:otherwise>
                                       </c:choose>
                                 
                                </div>
                                <div class="tr_txt">
                                 <span class="tr_l"><spring:message code="security.idtype" /></span>
                                 
                                  <c:choose>
                                   
                                     <c:when test="${!fuser.fpostRealValidate || !fuser.fhasRealValidate}">
                                     <select class="inpBox" name="" id="bindrealinfo-identitytype">
                                         <option value="0"><spring:message code="security.idcard" /></option>          
                                     </select>
                                     </c:when>
                                     <c:otherwise>
                                     <select class="inpBox" disabled="disabled" style="background-color: rgb(235, 235, 228);padding-left: 6px;" name="" id="bindrealinfo-identitytype">
                                         <option value="0"><spring:message code="security.idcard" /></option>          
                                     </select>
                                     </c:otherwise>
                                       </c:choose>
                                  
                                </div>                                              
                                <div class="tr_txt">
                                     <span class="tr_l"><spring:message code="security.idnumber" /></span>
                                     
                                      <c:choose>
                                   
                                     <c:when test="${!fuser.fpostRealValidate || !fuser.fhasRealValidate}">
                                      <input type="text" class="inpBox" id="bindrealinfo-identityno" placeholder="<spring:message code="new.idcard.number" />" autocomplete="off">
                                     </c:when>
                                     <c:otherwise>
                                      <input type="text" class="inpBox" disabled="disabled" readonly="readonly" id="bindrealinfo-identityno" value="${fuser.fidentityNo_s }"  autocomplete="off">
                                     </c:otherwise>
                                       </c:choose>
                                       
                                   
                                </div>
                                  <c:if test="${!fuser.fpostRealValidate || !fuser.fhasRealValidate}">
                                <div class="clear div_pdl">
                                    <input type="checkbox" value="" checked id="bindrealinfo-ckinfo">
                                        <spring:message code="security.iguarantee" />
                                    </div>
                                <div class="clear div_pdl">
                                    <span id="certificationinfo-errortips" class="cred"></span>
                                </div>
                              
                                 <div class="tr_txt relBtn">
                                     <button class="btn sub mgt20" id="bindrealinfo-Btn"><spring:message code="security.submit" /></button>
                                </div>
                                </c:if>
                               
                              <%--   </c:if> --%>
                            </p>
                            </div>                           
                        </div>
                    </div>
                    <div class="kyc_02">
                        <h3 class="ts">
                            <span>
                                <svg class="icon" aria-hidden="true">
                                    <use xlink:href="#icon-2shenfenzhenghaoma"></use>
                                </svg>
                                KYC2 <spring:message code="security.authentication" />
                            </span>
                            &nbsp;&nbsp;
                            <input id="identityType" type="hidden" value="${fuser.fidentityType}">
                            <input id="areacode" type="hidden" value="${fuser.fareaCode}">
                            <input id="userId" type="hidden" value="${fuser.fid}">
                            <span class="cred">
                                <svg class="icon sfont18" aria-hidden="true">
                                    <use xlink:href="#icon-tishi2"></use>
                                </svg>
                                (<spring:message code="security.besureto" />)
                            </span>
                        </h3>
                        <div class="step_01">
							<div class="to_up relBtn clear">
						             <c:choose>
	                                    <c:when test="${fuser.fhasRealValidate && fuser.fhasImgValidate && fuser.fpostImgValidate}">
	                                    	<span class="btn sub mgt20 s2"><spring:message code="security.authenticated" /></span>
	                                    </c:when>
	                                    <c:when test="${fuser.fhasRealValidate && !fuser.fhasImgValidate && fuser.fpostImgValidate}">
	                                       <span class="btn sub mgt20 s2"><spring:message code="security.inaudit" /></span>
	                                    </c:when>
	                                    <c:otherwise>
	                                      	<span class="btn sub" onclick="applyFaceIDValidate()" id="goApply"><spring:message code="security.applyauthenticate" /></span>
							                <div class="area clear">
							                	<ul>
							                		<li  class="hcode qrcode" id="qrcode"></li>
							                		<li id="txt_c" hidden="hidden"><spring:message code="security.scan" /></li>
							                	</ul>
										    </div>
	                                    </c:otherwise>
                                    </c:choose>
                               </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- 弹窗认证 -->
    <div class="realfixed ngrealModule">
    	<ul>
    		<h3><img src="${oss_url}/static/front2018/images/close@2x.png" class="realclose"></h3>
    		<li>
    			<p>KYCI认证成功获得1POW奖励可</p>
    			<a href="/luckydraw/luckydrawIndex.html">参与平台抽奖</a>
    			<button class="kycBtn">点击KYC2认证</button>
    			<span>前往KYC2认证获得更多POW</span>
   			</li>
    	</ul>
    </div>
    <!--蒙层-->
    <div class="realModule ngrealModule"></div>
</section>


    <jsp:include page="../comm/footer.jsp"></jsp:include>

    <script type="text/javascript" src="${oss_url}/static/front2018/js/plugin/ajaxfileupload.js?v=20181126201750"></script>
    <script type="text/javascript" src="${oss_url}/static/front2018/js/plugin/fileCheck.js?v=20181126201750"></script>
    <script type="text/javascript" src="${oss_url}/static/front2018/js/comm/msg.js?v=20181126201750"></script>
    <script type="text/javascript" src="${oss_url}/static/front2018/js/plugin/jquery.qrcode.min.js?v=20181126201750"></script>
   
    <script type="text/javascript" src="${oss_url}/static/front2018/js/user/kyc.js?v=211808241450"></script>
    <script type="text/javascript" src="${oss_url}/static/front2018/js/plugin/jquery-migrate-1.4.1.min.js?v=20181126201750"></script>
    <script type="text/javascript">
    $("#bindrealinfo-Btn").hover(function() {
        $(this).css('color', '#fff');
    });
    $(".realclose").click(function () {
		$(".realfixed").hide();
		$(".realModule").hide();
	})
    function applyFaceIDValidate() {
    	if(!${fuser.fpostRealValidate && fuser.fhasRealValidate}) {
    		util.layerAlert("", "请先完成kyc1认证", 2);
    		return;
    	}
    	var code = null;
    	var userId = document.getElementById("userId").value;
    	var param={	
    		userId:userId
    	}
    	$.post("/user/faceValidateReq.html?random=" + Math.round(Math.random() * 100), param, function(data) {
    		if (data.code == 0) {
    			//生成请求url地址
    			createQrCode(data.faceValidateUrl);
    			document.getElementById("goApply").style.display = "none";
    			
    		} else {
    			util.layerAlert("", data.msg, 2);
    		}
    	}, "json");
    }
    
    function createQrCode(faceValidateUrl) {
    	document.getElementById("txt_c").style.display = "block";
    	document.getElementById("txt_c").style.color = "#6f6f6f";
        if (navigator.userAgent.indexOf("MSIE") > 0) {
           jQuery('#qrcode').qrcode({
             text : faceValidateUrl,
             width : "149",
             height : "143",
             render : "table"
           });
         } else {
           jQuery('#qrcode').qrcode({
             text : faceValidateUrl,
             width : "149",
             height : "143"
           });
         }
       };
    
    </script>
</body>
</html>
