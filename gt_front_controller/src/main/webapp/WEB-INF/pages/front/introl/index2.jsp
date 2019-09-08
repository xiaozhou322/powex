<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../comm/include.inc.jsp"%>

<!doctype html>
<html>
<head> 
<%@ include file="../comm/basepath.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="../comm/link.inc.jsp" %>
<style type="text/css">
.loginRecord .assetCon_list .infoTit li , .mg .loginRecord .infoCon .s-1{
   width:16.6%;
}
</style>
</head>
<body>
<%@include file="../comm/header.jsp" %>
<input type="hidden" id="type" value="2"/>
<section>
    <div class="mg">
        <div class="l_finance clear">
            <div class=" fl">
                <div class="firstNav">
                   <%@include file="../comm/left_menu.jsp" %>
                </div>
            </div>
            <div class="l_financeR fr">
                <h2 class="financialCen"><a href="javascript:void(0)"><spring:message code="introl.income.detail" /></a></h2>
                <div class="assetList loginRecord">
                    <div class="assetCon_list">
                        <ol class="infoTit clear">
                             <li><spring:message code="introl.number" /></li>
                             <li><spring:message code="introl.currency.name" /></li>
                             <li><spring:message code="introl.number.benefits" /></li>
                             <li><spring:message code="introl.type.reward" /></li>
                             <li><spring:message code="introl.income.item" /></li>
                             <li><spring:message code="introl.income.date" /></li>
                        </ol>
                        <ul class="infoCon">
                        	<c:forEach items="${fintrolinfos }" var="v" varStatus="st">
                            <li>
                                <div class="coinInfo">
                                <span class="s-1">${st.count}</span>
                                <span class="s-1">${v.fname }</span>
                                <span class="s-1">${v.fqty }</span>
                                 <span class="s-1">${IntrolInfoTypeEnum[v.ftype] }</span>
                                 
                                    <span class="s-1">${v.ftitle }</span>
                                    <span class="s-1"><fmt:formatDate
									value="${v.fcreatetime }" pattern="yyyy-MM-dd HH:mm:ss" /></span>
                                    <div class="clear"></div>
                                </div>
                            </li>
                            </c:forEach>                            
                           <c:if test="${fn:length(fintrolinfos)==0 }">
								<span class='noMsg'> <spring:message code="introl.affi.norec" /> </span>
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