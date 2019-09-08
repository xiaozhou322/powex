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
<%@include file="../comm/link.inc.jsp" %>
<link href="${oss_url}/static/mobile2018/css/usdtTrade.css?v=20181126201750" rel="stylesheet" type="text/css" />

</head>
<body class="Ubody">
<%@include file="../comm/header.jsp" %>
<header class="tradeTop">  
    <i class="back toback2"></i>
    <h2 class="tit">代理商申请状态</h2>
</header>
    <section class="agentMain mg clear">
        <div class="agentApply">
            <h1>代理商申请</h1>
            <div class="applyFor">
                <div class="steps">
                    <ol>
                        <li id="rechargeprocess1" class="active textLeft fl">
                            <span></span>
                            <em>资料准备</em>
                        </li>
                        <li id="rechargeprocess2" class="fl active textLeft">
                            <span></span>
                            <em>发送邮件</em>
                        </li>
                        <li id="rechargeprocess3" class="fl active textCenter">
                            <span></span>
                            <em>提交审核</em>
                        </li>                        
                        <li id="rechargeprocess4" class="fl active textRight">
                            <span></span>
                            <em>审核结果</em>
                        </li>
                    </ol>
                </div>
                <div class="applyStatus">
                <c:if test="${fagent.process == 0 }">
                    <div class="status1 active">
                        <img src="${oss_url}/static/mobile/css/agent/images/apply.png" alt="" />
                        <p>您的申请正在审核中</p>
                    </div>
                    </c:if>
                    <c:if test="${fagent.process == 2 }">                      
                    <div class="status1 status2 active">
                        <img src="${oss_url}/static/mobile/css/agent/images/fil.png" alt="" />
                        <p>您的申请失败了</p>
                    </div>
                    </c:if>
                    <c:if test="${fagent.process == 1 }">                     
                    <div class="status1 status3 active">
                        <img src="${oss_url}/static/mobile/css/agent/images/succ.png" alt="" />
                        <p>您的申请已通过</p>
                    </div>
                    </c:if>
                </div>
            </div>
        </div>
    </section>
 <%@include file="../comm/footer.jsp" %>	
<script type="text/javascript" src="${oss_url}/static/front/js/index/main.js?v=20181126201750"></script>

</body>
</html>
