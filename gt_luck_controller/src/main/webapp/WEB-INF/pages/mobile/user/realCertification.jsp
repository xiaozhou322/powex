<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
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
 <title><spring:message code="security.ideaut" /></title>
   
</style>
</head>
<body>
<%@include file="../comm/header.jsp" %>
  <div class="real">
    <header class="tradeTop">
        <span class="back toback2"></span>
        <span><spring:message code="security.ideaut" /></span>
    </header>
    <div class="realCon">
         <div class="careText cred"> (<spring:message code="security.besureto" />)</div>
         <div class="real_1">
           <h1 class="cblue2">KYC1 
             <c:if test="${fuser.fpostRealValidate && fuser.fhasRealValidate}">
               <span class="s2">(<spring:message code="security.authenticated" />)</span>
               </c:if>
               <c:if test="${!fuser.fpostRealValidate || !fuser.fhasRealValidate}">
               <span class="s2">(<spring:message code="security.notcertified" />)</span>
             </c:if>
           </h1>
           <c:if test="${fuser.fpostRealValidate }">
                 <p> ${fuser.frealName }:${fuser.fidentityNo_s }</p>
            </c:if>
          </div> 
        <c:if test="${fuser.fpostRealValidate && !fuser.fhasRealValidate}">
            <c:if test="${fuser.fareaCode=='86'}">
               <span class="step1-btn disabled"><spring:message code="security.inaudit" /></span>
            </c:if>
              <c:if test="${fuser.fareaCode!='86'}">
                <span class="step1-btn disabled"><spring:message code="security.waitupload" /></span>
             </c:if>
           </c:if>
           <c:if test="${fuser.fpostRealValidate && fuser.fhasRealValidate}">
             <span class="step1-btn disabled"><spring:message code="security.authenticated" /></span>
            </c:if>
        
        <c:if test="${!fuser.fpostRealValidate && !fuser.fhasRealValidate}">
        
         <div class="txt_tr">
             <span class="tr_name"><spring:message code="m.security.realname" /></span>
             <input type="text" class="txt_inp" id="bindrealinfo-realname" placeholder="<spring:message code="security.pleasefillin" />"/>
         </div>
        <div class="txt_tr">
             <span class="tr_name"><spring:message code="security.areacountry" /></span>
             <span onclick="showtabs('bindrealinfo-address_box')" id="bindrealinfo-addresss"><spring:message code="security.area.chinamainland" /></span>
         </div>
          <select class="form-control select select3 sl1" id="bindrealinfo-address" style="display:none;">
                    <option value="86" selected><spring:message code="security.area.chinamainland" /></option>
                    <option value="853"><spring:message code="security.area.mc" /></option>
                    <option value="886"><spring:message code="security.area.tw" /></option>
                    <option value="852"><spring:message code="security.area.hk" /></option>
                    <option value="61"><spring:message code="security.area.au" /></option>
                    <option value="49"><spring:message code="security.area.ge" /></option>
                    <option value="7"><spring:message code="security.area.ru" /></option>
                    <option value="33"><spring:message code="security.area.fr" /></option>
                    <option value="63"><spring:message code="security.area.ph" /></option>
                    <option value="82"><spring:message code="security.area.kr" /></option>
                    <option value="1"><spring:message code="security.area.ca" /></option>
                    <option value="52"><spring:message code="security.area.mx" /></option>
                    <option value="81"><spring:message code="security.area.jp" /></option>
                    <option value="66"><spring:message code="security.area.tl" /></option>
                    <option value="65"><spring:message code="security.area.sg" /></option>
                    <option value="91"><spring:message code="security.area.in" /></option>
                    <option value="44"><spring:message code="security.area.uk" /></option>
                </select>
         <div class="txt_tr">
             <span class="tr_name"><spring:message code="security.idtype" /></span>
            <span onclick="showtabs('bindrealinfo-identitytype_box')" id="bindrealinfo-identitytypes"><spring:message code="security.idcard" /></span>
         </div>
          <select class="form-control select select3 sl1" id="bindrealinfo-identitytype" style="display:none;">
                    <option value="0"><spring:message code="security.idcard" /></option>
                    <option value="2"><spring:message code="security.passport" /></option>
                </select>
         
         <div class="txt_tr">
             <span class="tr_name"><spring:message code="security.idnumber" /></span>
             <input type="text" class="txt_inp" id="bindrealinfo-identityno" placeholder="<spring:message code="security.idnumber" />" autocomplete="off"/>
         </div>
           <div class="txt_tr">
             
             <input type="checkbox" checked id="bindrealinfo-ckinfo"/>
             <spring:message code="security.iguarantee" />
         </div>

         <div class="real_btn"><button class="btn" id="bindrealinfo-Btn"><spring:message code="security.submit" /></button>
            <p class="careText">
               <span class="cred clear">
               <i class="iconfont fl sfont45 icon-weibiaoti2"></i>&nbsp;<spring:message code="security.acctotherele" />
                </span>
            </p>
         </div>
          </c:if>
    </div>
    <div class="kyc_02">
        <h1 class="cblue2">KYC2<spring:message code="security.authentication" /></h1>
        <input id="userId" type="hidden" value="${fuser.fid}">
        <div class="real_btn to_up">
        	 <c:choose>
                <c:when test="${userFaceID != null && userFaceID.status == 101}">
                <span class="s2 btn sub"><spring:message code="security.inaudit" /></span>
                </c:when>
                <c:when test="${userFaceID != null && userFaceID.status == 102}">
                <span class="s2 btn sub"><spring:message code="security.authenticated" /></span>
                </c:when>
                <c:otherwise>
                <c:if test="${userFaceID != null && userFaceID.status == 103}">
               		<span id="authfailed" ><spring:message code="security.authenticatefailed" /></span>
               	</c:if>
                <span class="btn btn4 sub s1" onclick="applyFaceIDValidate()" id="goApply"><spring:message code="security.applyauthenticate" /></span>
                <a id="goValidate" href=""  class="s2 btn sub"><spring:message code="security.goauthenticate" /></a>
                </c:otherwise>
             </c:choose>   
        </div>
    </div>
</div>
    
<div class="slet"></div>
<%@ include file="../comm/tabbar.jsp"%>
<%@ include file="../comm/footer.jsp"%> 

	<script src="${oss_url}/static/mobile2018/js/fluckydraw/com.js?v=20181126201750"></script>
    <script type="text/javascript" src="${oss_url}/static/mobile2018/js/plugin/ajaxfileupload.js?v=20181126201750"></script>
    <script type="text/javascript" src="${oss_url}/static/mobile2018/js/plugin/fileCheck.js?v=20181126201750"></script>
    <script type="text/javascript" src="${oss_url}/static/mobile2018/js/comm/msg.js?v=20181126201750"></script>
	<script type="text/javascript" src="${oss_url}/static/mobile2018/js/plugin/jquery.qrcode.min.js?v=20181126201750"></script>
	<!-- <script type="text/javascript" src="${oss_url}/static/front/js/plugin/jquery.autocomplete.min.js?v=20181126201750"></script> -->
	<script type="text/javascript" src="${oss_url}/static/mobile2018/js/kyc.js?v=20180203164658.js.js?v=20181126201750"></script>
	<script type="text/javascript" src="${oss_url}/static/mobile2018/js/plugin/jquery-migrate-1.4.1.min.js?v=20181126201750"></script>
    <script type="text/javascript">
    $("#bindrealinfo-Btn").hover(function() {
        $(this).css('color', '#fff');
         
    });
    
    
    $(function () {

    //	$("#id").css('visibility', 'visible');
    	$("#goValidate").css('visibility', 'hidden');

    })
    
    function applyFaceIDValidate() {
    	var code = null;
    	var userId = document.getElementById("userId").value;
    	var param={	
    		userId:userId
    	}
    	$.post("/user/faceValidateReq.html?random=" + Math.round(Math.random() * 100), param, function(data) {
    		if (data.code == 0) {
    			//生成请求url地址
    			document.getElementById("goValidate").href=data.faceValidateUrl;
    			$("#goValidate").css('visibility', 'visible');
    			document.getElementById("goApply").style.display = "none";
    			document.getElementById("authfailed").style.display = "none";
    		} else {
    			util.layerAlert("", data.msg, 2);
    		}
    	}, "json");
    }
    
    </script>
   
</body>
</html>
