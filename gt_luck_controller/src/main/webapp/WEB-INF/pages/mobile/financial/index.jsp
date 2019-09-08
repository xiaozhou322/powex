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
    <title><spring:message code="financial.perass" /></title>
    <style type="text/css">
        @font-face {
           font-family: 'PingFangMedium';
           src: url('/static/mobile2018/fonts/PingFangMedium.ttf'); 
        } 
    </style>


</head>
<body>

 <%@include file="../comm/header.jsp" %>
<div class="trade assets">
    <header class="tradeTop">
        <span class="back"></span>
        <span><spring:message code="financial.perass" /></span>
    </header>
    <div class="single_header">
        <h1><spring:message code="newmobile.teansaction" /></h1>
        <h4><spring:message code="newmobile.asset" /><span>(USDT)</span></h4>
        <p class="assets_all"><em style="font-size: 0.3rem;font-weight: bold;color:white;">$</em><span class="total"><ex:DoubleCut value="${totalCapitalTrade }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/></span> ≈ <em class="summoney" style="color:white;">57931.29 </em> CNY</p>
    </div>
    <div class="Personal_assets">
        <table class="table">

            <tr>  
                <td class='td_01'><span>USDT</span><em><spring:message code="financial.currency" /><a href=""></a></em></td>
                <td><span><ex:DoubleCut value="${usdtfwallet.ftotal }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/></span><em><spring:message code="financial.usaass" /></em></td>
                <td class="td_03"><span><ex:DoubleCut value="${usdtfwallet.ffrozen }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/></span><em><spring:message code="financial.freass" /></em></td>
            </tr>
            <tr><td colspan="3" class="textR btnList">
            <a href="/exchange/rechargeUsdt.html"><spring:message code="financial.usdtrecharge" /></a>
            <a href="/exchange/withdrawUsdt.html" class="tb_btn"><spring:message code="financial.usdtrewithdrawal" /></a></td></tr>
            <c:forEach items="${fvirtualwallets }" var="v" varStatus="vs" begin="0">
             <tr>
                <td class='td_01'><span>${v.value.fvirtualcointype.fShortName }</span><em><spring:message code="financial.currency" /><a href=""></a></em></td>
                <td><span><ex:DoubleCut value="${v.value.ftotal }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/>
                 <c:if test="${v.value.flocked>0 }">
                                <br />(<spring:message code="newmobile.lock" /><ex:DoubleCut value="${v.value.flocked }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/>)
                </c:if>
                </span><em><spring:message code="financial.usaass" /></em></td>
                <td class="td_03"><span><ex:DoubleCut value="${v.value.ffrozen }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/>
                <c:if test="${v.value.fcanlendBtc>0 }">
                <br />(<spring:message code="newmobile.released" /><ex:DoubleCut value="${v.value.fcanlendBtc-v.value.falreadyLendBtc }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/>)
                </c:if>
                </span><em><spring:message code="financial.freass" /></em></td>
                <tr>
                    <td colspan="3" class="textR btnList">
                        <c:choose>
                            <c:when test="${v.value.fvirtualcointype.fisrecharge==true}">
                                <a href="/account/rechargeBtc.html?symbol=${v.value.fvirtualcointype.fid}&tab=2" class="" data-key="${v.value.fvirtualcointype.fid}"><spring:message code="financial.recharge" /></a>
                            </c:when>
                            <c:otherwise>
                                   <span class="noTrade"><spring:message code="financial.recharge" /></span>
                            </c:otherwise>
                        </c:choose>
                        <c:choose>
                        <c:when test="${v.value.fvirtualcointype.FIsWithDraw==true}">
                            <a href="/account/withdrawBtc.html?symbol=${v.value.fvirtualcointype.fid}&tab=2" class="tb_btn" data-key="${v.value.fvirtualcointype.fid}"><spring:message code="new.withdraw" /></a>
                        </c:when>
                        <c:otherwise>
                            <span class="noTrade"><spring:message code="new.withdraw" /></span>
                        </c:otherwise>
                        </c:choose>
                    </td>
                </tr>

            </tr>
            </c:forEach>   
        </table>
    </div>
</div>

<%@include file="../comm/tabbar.jsp"%>
<%@include file="../comm/footer.jsp" %> 

</body>
<script src="${oss_url}/static/mobile2018/js/fluckydraw/com.js"></script>
<script type="text/javascript">
  var total = parseFloat(($(".total").html() * 6.5).toFixed(4));
  $(".summoney").html(total);
</script>
</html>
