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
<!-- <meta name = "viewport" content = "width = device-width, initial-scale = 1.0, maximum-scale = 1.0, user-scalable = 0" /> -->
<%@include file="../comm/link.inc.jsp" %>
<link href="${oss_url}/static/mobile2018/css/phone.css?v=31" rel="stylesheet" type="text/css" />
<link href="${oss_url}/static/mobile2018/css/usdtTrade.css?v=21" rel="stylesheet" type="text/css" />

</head>
<body class="">
<%@include file="../comm/header.jsp" %>
<header class="tradeTop">  
    <i class="back toback2"></i>
    <h2 class="tit">交易设置</h2>
</header>
<section class="uTrade mg clear usdtTrade trade_set">

        <div class="uTrade_r fr">
            <div class="tables-title clear">
                <span class="tit textL fl">类型</span>
                <span class="tit fl">可交易数量</span>
                <span class="tit textR fl">交易完成数量</span>
<!--                 <span class="tit fl">价格</span>
                <span class="tit fl">操作</span> -->
            </div>


            <div class="agentCon">
               <c:forEach items="${list}" var="v">
                    <div class="table-item">
                        <div class="tabs userFace textL fl">
                             <c:if test="${v.ftype == 1 }"><em class="cblue2">购买</em></c:if>
                             <c:if test="${v.ftype == 2 }"><em class="cred">出售</em></c:if>
                             <div class="clear"></div>
                        </div>
                        <div class="tabs rate fl"><em>${v.validnum}</em>USDT</div>
                        <div class="tabs rate textR fl trade_complete"><em>${v.donenum}</em>USDT</div>
                        <div class="spectail_tabs">
                            <div class="rate fl">
                              <p class="tit textL">价格</p>
                              <p data-v-ad45b12e="" class="gray"><em>${v.rate}</em>CNY ${v.minamount}-${v.maxamount}CNY</p>
                            </div>
                            <div class="group-btn fr">
                                <span class="buyBt sellBt" onclick="if(window.confirm('您确认要撤销吗？')){cancelAd(${v.fid})}">撤销</span>
                            </div>
                        </div>
                        <div class="clear"></div>
                     </div>                
                 </c:forEach>
            </div>
       		
        </div>
    </section>

  

<%@include file="../comm/footer.jsp" %>	
<script type="text/javascript" src="${oss_url}/static/front/js/finance/account.usdtrecharge.js?v=14"></script>

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
    
	
</script>
</body>
</html>
