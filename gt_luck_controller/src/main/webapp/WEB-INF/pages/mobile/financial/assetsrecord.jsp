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
    <script type="text/javascript" src="${oss_url}/static/front/js/index/main.js?v=20181126201750"></script>
<title><spring:message code="financial.accrecord" /></title>
      <style type="text/css">
        @font-face {
           font-family: 'PingFangMedium';
           src: url('/static/mobile/fonts/PingFangMedium.ttf'); 
        } 
    </style>

</head>
<body>

 <%@include file="../comm/header.jsp" %>
<header class="header backHeader">  
    <i class="backIcon"></i>
    <h2 class="tit"><spring:message code="financial.accrecord" /></h2>
</header>
<section class="assets">
    <div class="passetsMain assetsMain">
        <div class="passetsCon tabList">
        <c:forEach items="${list }" var="vf" varStatus="vsf">
        <c:if test="${0 eq  vsf.index}">
        <c:forEach var="vvf" varStatus="vvsf" items="${vf.list }">
        	<c:if test="${vvf.value3!='CNY' }">
            <table>  
                <tr>  
                    <td width="25%" class="textLeft td_01" rowspan="2">${vvf.value3 }</td> 
                    
                        <!--    <td class="time2"><fmt:formatDate value="${vf.flastupdatetime }" pattern="yyyy-MM-dd"/></font></td> -->
                            
                                <td width="36%" class="textLeft"><ex:DoubleCut value="${vvf.value1 }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/></td>  
                                <td width="36%" class="textLeft"><ex:DoubleCut value="${vvf.value2 }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/></td>  
    
                </tr>  
                <tr class="tr_02">  
                    <td width="36%" class="textLeft"><spring:message code="financial.usaass" /></td>  
                    <td width="36%" class="textLeft"><spring:message code="financial.freass" /></td>  
                </tr>  
             </c:if>
            </table>
			</c:forEach>
       </c:if>
       </c:forEach>














        </div>
    </div>
</section>


<%@include file="../comm/footer.jsp" %>	
<script src="${oss_url}/static/mobile2018/js/fluckydraw/com.js"></script>
<script type="text/javascript">
    $(".backIcon").click(function(event) {
        window.history.go(-1);
    });
</script>
</body>
</html>
