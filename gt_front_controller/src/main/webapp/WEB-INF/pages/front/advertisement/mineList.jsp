<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ include file="../comm/include.inc.jsp"%>

<!doctype html>
<html>
<head> 
<%@ include file="../comm/basepath.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="${oss_url}/static/front/css/index/main.css" />
<%@include file="../comm/link.inc.jsp" %>
<link href="${oss_url}/static/front2018/css/usdtTrade.css?v=11" rel="stylesheet" type="text/css" />
<style type="text/css">

.table-item .tabs {
    width: 13.6%;
    text-align: center;
    font-size: 15px;
    color: #333;
}

.tables-title .tit {
    display: block;
    width: 13.6%;
    padding: 10px 0;
    text-align: center;
    font-size: 15px;
    color: #333;
    font-weight: normal;
}
</style>
</head>
<body class="">
<%@include file="../comm/newotc_header.jsp" %>
<section class="uTrade mg clear">
        <div class="uTrade_l">
          <%@include file="_leftmenuUser.jsp"%>
        </div>
         <div class="uTrade_r">
            <div class="tables-title clear">
                <span class="tit fl"><spring:message code="market.entrusttype" /></span>
                <span class="tit fl"><spring:message code="market.currencytype" /></span>
                 <span class="tit fl"><spring:message code="market.advertisingstatus" /></span>
                 <span class="tit fl"><spring:message code="market.amount" /></span>
                <span class="tit fl"><spring:message code="market.price.cny" /></span>
                <span class="tit fl"><spring:message code="agent.amount" /></span>
                <span class="tit fl"><spring:message code="market.entrustaction" /></span>
            </div>
            <div class="agentCon">
                  <c:forEach var="v" varStatus="vs" items="${list}">
                <div class="table-item">
                 <div class="tabs userFace rate fl">
                         <c:if test="${v.ad_type == 2 }"><span class='colorBlue'><spring:message code="agent.purchase" /></span></c:if>
                         <c:if test="${v.ad_type == 1 }"><span class="redcolor"><spring:message code="market.sell" /></span></c:if>
                        <div class="clear"></div>
                    </div>
                   
                       <div class="tabs rate fl"> ${v.fvirtualcointype.fShortName }</div>
                    	
                      <div class="tabs userFace rate fl">
                         <c:if test="${v.status == 1 }"><span class='colorBlue'><spring:message code="ad.shelves.downline" /></span></c:if>
                         <c:if test="${v.status == 0 }"><span class="redcolor"><spring:message code="ad.shelves.online" /></span></c:if>
                        <div class="clear"></div>
                    </div>
                    
                    <div class="tabs rate fl"><em>${v.repertory_count }</em>个</div>
                    <div class="tabs rate fl">
                    <em>${v.price }</em>CNY<br>                    
                    </div>
                    <div class="tabs rate fl">
                        <em data-v-ad45b12e="" class="gray">
                        	<fmt:formatNumber value="${v.order_limit_min }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="2"/>
                        	~
                        	<fmt:formatNumber value="${v.order_limit_max }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="2"/>CNY
                        </em>
                    </div>
                    <div class="tabs fl">
                    <c:if test="${v.status==0}">
                     <span class="buyBt" onclick="dismount(${v.id})"><spring:message code="ad.shelves.down" /></span>
                    </c:if>
                    
                   
                    </div>

                    <div class="clear"></div>
                 </div>                
                 </c:forEach>
            </div>

        	<div class="page">
				${pagin }
			</div>

        </div>
    </section>



<%@include file="../comm/footer.jsp" %>	
<script type="text/javascript" src="${oss_url}/static/front2018/js/finance/account.usdtrecharge.js?v=14"></script>
<script type="text/javascript" src="${oss_url}/static/front2018/js/index/main.js"></script>
<script type="text/javascript">
    $(".uTrade_l a").click(function(event) {
        $(this).addClass('active').siblings().removeClass('active');
    });

function dismount(id){
	var param = {'id':id};
	jQuery.post('/advertisement/doDismount.html', param, function (data) {

    	if (data.success ) {
    		util.layerAlert("", data.msg, 1);
    		
			window.location.href='/advertisement/advertisementMine.html';
		
		} else {
			util.layerAlert("", data.massage, 2);
		}
    }, "json");
}
</script>



</body>
</html>
