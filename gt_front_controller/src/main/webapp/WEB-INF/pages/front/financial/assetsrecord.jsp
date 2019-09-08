<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ include file="../comm/include.inc.jsp"%>

<!doctype html>
<html>
<head> 
<%@ include file="../comm/basepath.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="../comm/link.inc.jsp" %>
    <link href="${oss_url}/static/front/css/index/main.css?v=20181126201750" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="${oss_url}/static/front/js/index/main.js?v=20181126201750"></script>
<link rel="stylesheet" href="${oss_url}/static/front/css/finance/assetsrecord.css?v=20181126201750" type="text/css"></link>

</head>
<body>

 <%@include file="../comm/header.jsp" %>

<section class="lw-content">
    <div class="lw-finance">
        <div class="lw-financeLeft fl">
            <ul>
                <li ><a href="/financial/index.html"><spring:message code="financial.perass" /><i></i></a></li>
                <li ><a href="/account/rechargeBtc.html"><spring:message code="financial.recharge" /> <i></i></a></li>
                <li ><a href="/account/withdrawBtc.html"><spring:message code="financial.withdrawal" /> <i></i></a></li>
                <li><a href="/account/record.html"><spring:message code="financial.accrecord" /><i></i></a></li>
                <li  ><a href="/financial/accountcoin.html"><spring:message code="financial.capacc" /><i></i></a></li>
                <li class="lw-cur"><a href="/financial/assetsrecord.html"><spring:message code="financial.assrec" /><i></i></a></li>
                <li><a href="/trade/entrust.html?status=0"><spring:message code="entrust.title.entrust" /><i></i></a></li>
                <li><a href="/trade/entrust.html?status=1"><spring:message code="entrust.title.deal" /><i></i></a></li>
                <li><a href="/introl/mydivide.html"><spring:message code="introl.title" /><i></i></a></li>
            </ul>
        </div>
        <div class="lw-financeRight fr">
            <h1 class="lw-financeTit"><spring:message code="financial.assrec" /></h1>
            <div class="lw-financeMain">
                <table width="100%" border="0" cellpadding="0" cellspacing="0">
                    <tbody>
                        <tr class="tableNavBg">
                            <th scope="col" width="25%"><spring:message code="financial.currency" /></th>
                            <th scope="col" width="25%"><spring:message code="financial.date" /></th>
                            <th scope="col" width="25%"><spring:message code="financial.usaass" /></th>
                            <th scope="col" width="25%"><spring:message code="financial.freass" /></th>
                        </tr>
                    </tbody>
                    <tbody class="balance">
                      <c:forEach items="${list }" var="vf" varStatus="vsf">
                      <c:if test="${0 eq  vsf.index}">
                        <tr>
                        <c:forEach var="vvf" varStatus="vvsf" items="${vf.list }">
                        <c:if test="${vvf.value3!='CNY' }">
	                        <td class="td_01"><i class="coin coin_bcc_25"></i><span>${vvf.value3 }</span></td>
	                        <td><font class="is_num"><fmt:formatDate value="${vf.flastupdatetime }" pattern="yyyy-MM-dd"/></font></td>
	                        <td class="is_num"><ex:DoubleCut value="${vvf.value1 }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/></td>
	                        <td class="is_num"><ex:DoubleCut value="${vvf.value2 }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/></td>
                        </c:if>
                        </tr>
                        </c:forEach>
                      </c:if>
                      </c:forEach>
                    </tbody>    
                </table>
            </div>
        </div>
    </div>
</section>

<%@include file="../comm/footer.jsp" %>	

</body>
</html>
