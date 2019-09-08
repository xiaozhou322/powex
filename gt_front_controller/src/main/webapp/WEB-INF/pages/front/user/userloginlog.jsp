<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../comm/include.inc.jsp"%>
<%

%>

<!doctype html>
<html>
<head> 
<%@ include file="../comm/basepath.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="../comm/link.inc.jsp" %>

</head>
<body>
	

 
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
                <div class="personal_title">
                    <h2 class="financialCen"><spring:message code="security.loginlog" /></h2>
                </div>
                <div class="assetList loginRecord">
                    <div class="RecordTit">
                        <a href="javascript:void(0)" class="active"><spring:message code="security.loginlog.recentlog" /></a>
                        <a href="/user/userloginlog.html?type=2" ><spring:message code="security.loginlog.settinglog" /></a>
                    </div>
                    <div class="assetCon_list">
                        <ol class="infoTit clear">
                            <li><spring:message code="security.loginlog.logtime" /></li>
                            <li><spring:message code="security.loginlog.logip" /></li>
                        </ol>
                        <ul class="infoCon">
                        	<c:forEach items="${logs }" var="v">
                            <li>
                                <div class="coinInfo">
                                    <span class="s-1"><fmt:formatDate value="${v.fcreateTime }" pattern="yyyy-MM-dd HH:mm:ss"/></span>
                                    <span class="s-1">${v.fkey3 }</span>
                                    <div class="clear"></div>
                                </div>
                            </li>                             
                            </c:forEach>

                            <c:if test="${fn:length(logs)==0 }">							
								<span class='noMsg'> <spring:message code="security.loginlog.nolog" /> </span>
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


</body>
</html>