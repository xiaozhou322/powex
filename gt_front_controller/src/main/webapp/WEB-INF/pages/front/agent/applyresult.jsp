<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ include file="../comm/include.inc.jsp"%>
<%

%>
<!doctype html>
<html>
<head> 
<%@ include file="../comm/basepath.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="../comm/link.inc.jsp" %>

<link href="${oss_url}/static/front2018/css/usdtTrade.css?v=11" rel="stylesheet" type="text/css" />
</head>
<body class="">
<%@include file="../comm/white_header.jsp" %>
<section class="uTrade mg clear">
        <div class="agentApply">
            <!-- <h1>代理商申请</h1> -->
            <div class="applyFor">
                <ol class="clear">                                                   
                    <li class="fl active">
                        <em class="fl"><spring:message code="agent.Datapreparation" /></em>
                    </li>                    
                    <li class="fl active">
                        <i class="fl"></i>
                        <span class="s_line fl"></span>
                        <em class="fl"><spring:message code="agent.sendemail" /></em>
                    </li>                    
                    <li class="fl active">
                        <i class="fl"></i>
                        <span class="s_line fl"></span>
                        <em class="fl"><spring:message code="agent.Submitreview" /></em>
                    </li>                    
                    <li class="fl active">
                        <i class="fl"></i>
                        <span class="s_line fl"></span>                        
                        <em class="fl"><spring:message code="agent.Auditresults" /></em>
                    </li>
                </ol>
                <div class="applyStatus">
                	<c:if test="${fagent.process == 0 }">
                    <div class="status1 active">
                        <img src="${oss_url}/static/front/css/agent/images/apply.png" alt="">
                        <p><spring:message code="agent.waitresult1" /></p>
                    </div> 
                    </c:if>      
                    <c:if test="${fagent.process == 2 }">             
                    <div class="status1 status2 active">
                        <img src="${oss_url}/static/front/css/agent/images/fil.png" alt="">
                        <p><spring:message code="agent.waitresult2" /></p>
                    </div>   
                     </c:if>           
                    <c:if test="${fagent.process == 1 }">                 
                    <div class="status1 status3 active">
                        <img src="${oss_url}/static/front/css/agent/images/succ.png" alt="">
                        <p><spring:message code="agent.waitresult3" /></p>
                    </div>
                    </c:if>
                </div>
            </div>
        </div>
    </section>
 <%@include file="../comm/footer.jsp" %>	
<script type="text/javascript" src="${oss_url}/static/front2018/js/index/main.js?v=20181126201750"></script>

</body>
</html>
