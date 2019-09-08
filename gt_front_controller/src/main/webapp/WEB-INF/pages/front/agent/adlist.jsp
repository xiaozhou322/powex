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

<link href="${oss_url}/static/front2018/css/usdtTrade.css?v=12" rel="stylesheet" type="text/css" /></head>
<body class="">
<%@include file="../comm/white_header.jsp" %>
<section class="uTrade mg">
        <div class="uTrade_l">
           <%@include file="_leftmenuUser.jsp"%>
        </div>
        <div class="uTrade_r">
            <div class="tables-title clear">
                <span class="tit fl"><spring:message code="market.entrusttype" /></span>
                <span class="tit fl"><spring:message code="agent.tradablequantity" /></span>
                <span class="tit fl"><spring:message code="entrust.title.deal" /></span>
                <span class="tit fl"><spring:message code="market.price" /></span>
                <span class="tit fl"><spring:message code="agent.tradequotas" /></span>
                <span class="tit fl"><spring:message code="market.entrustaction" /></span>
            </div>
            <div class="agentCon">
               <c:forEach items="${list}" var="v">
                <div class="table-item">
                    <div class="tabs userFace rate fl">
                         <c:if test="${v.ftype == 1 }"><span class='colorBlue'><spring:message code="agent.purchase" /></span></c:if>
                         <c:if test="${v.ftype == 2 }"><span class="redcolor"><spring:message code="market.sell" /></span></c:if>
                        <div class="clear"></div>
                    </div>
                    
                    <div class="tabs rate fl"><em>${v.validnum}</em>USDT</div>
                    <div class="tabs rate fl"><em>${v.donenum}</em>USDT</div>
                    <div class="tabs rate fl">
                    <em>${v.rate}</em>CNY<br>
                    </div>                    
                    <div class="tabs rate fl">
                    <p data-v-ad45b12e="" class="gray">${v.minamount}-${v.maxamount}CNY</p>
                    </div>
                    <div class="tabs fl"><span class="buyBt sellBt delorder" data-key="${v.fid}"><spring:message code="entrust.entrust.cancel" /></span></div>
                    <div class="clear"></div>
                 </div>                
                 </c:forEach>
            </div>
       		 <c:if test="${totalPage > 1 }">
        	<div class="text-center">
				${pagin }
			</div>
			</c:if>
        </div>
    </section>

  

<%@include file="../comm/footer.jsp" %>	
<script type="text/javascript" src="${oss_url}/static/front2018/js/finance/account.usdtrecharge.js?v=14"></script>
<script type="text/javascript" src="${oss_url}/static/front2018/js/index/main.js?v=20181126201750"></script>
<script type="text/javascript">
    $(".uTrade_l a").click(function(event) {
        $(this).addClass('active').siblings().removeClass('active');
    });
	function cancelAd(agentorder_id)
    {
    	var param = {'agentorder_id':agentorder_id}
    	jQuery.post('/agent/cancelad.html', param, function (data) {
        	if (data.code == -1) {
				util.layerAlert("", data.msg, 2);
			} else {
				window.location.reload();
			}
        }, "json");
    }
    
     $(".delorder").click(function(event) {
       var id = $(this).data("key");
       layer.confirm(language["agent.delorder"], {
        btn: [language["agent.yes"],language["agent.no"]] //按钮
        }, function(){
            cancelAd(id);
        });
    });
	
</script>
</body>
</html>
