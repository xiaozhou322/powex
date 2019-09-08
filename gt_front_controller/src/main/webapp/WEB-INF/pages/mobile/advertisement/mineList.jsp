<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ include file="../comm/include.inc.jsp"%>

<!doctype html>
<html>
<head> 
<%@ include file="../comm/basepath.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name = "viewport" content = "width = device-width, initial-scale = 1.0, maximum-scale = 1.0, user-scalable = 0" />
<%@include file="../comm/link.inc.jsp"%>
<link href="${oss_url}/static/mobile2018/css/usdtTrade.css?v=20190301163300100" rel="stylesheet" type="text/css" />

<style type="text/css">
.tradeTop{padding-left:0;}
.uTrade{
	box-shadow: 0px 2px 9px 0px rgba(70, 90, 111, 0.14);
    margin-bottom: 0.32rem;    clear: both;
    margin-top: 0.2rem;}
.buyTit{
    color: #506FC8;
    position: absolute;
    right: 0.3rem;
    font-size: 0.3rem;
}
.buyTit img{
	width: 0.29rem;
    height: 0.27rem;
    vertical-align: -0.05rem;
    }
    
    .backMyad{
        float: left;
	    color: #7C7C7C;
	    margin-left: 0.25rem;
    }
.pubLabel{    
	color: #666666;
    line-height: 0.6rem;
    padding-top: 0.2rem;
    font-family: SimHei;
    font-weight: 400;
    font-size: 0.28rem;}  
}
.amountLi{text-align: right;}
.table-item .fnickUl .amountLi .gray{color:red;}
.redcolor{color:#F06338;font-size: 0.32rem;}
.colorBlue{color:#76C80E;font-size: 0.32rem;}
.buyBt{    float: right;background: #216AD3;}
.table-item-h3{
    padding-bottom: 0.2rem;
    line-height: 0.58rem;
}
</style>
</head>
<body>
<header class="tradeTop">  
  <!--  <i class="back toback2"></i> -->
   <h2 class="tit">
	   <a class="backMyad" href="/advertisement/puborder.html">
	   	<i class="back toback2"></i><spring:message code="agent.myad" />
	   </a>
	   <c:if test="${sessionScope.login_user!=null && sessionScope.login_user.fismerchant==true }"> 
	   <span id="online" style="<c:if test="${sessionScope.online==true}"> display:none</c:if>"><img src="${oss_url}/static/mobile2018/images/of-line.png"/></span>
	   <span id="offline" style="<c:if test="${sessionScope.online==false}"> display:none</c:if>"><img src="${oss_url}/static/mobile2018/images/on-line.png"/></span>
	   </c:if>
   </h2>
</header>
<section class="uTrade mg clear">
         <div class="uTrade_r">
            <div class="agentCon">
            <ul id="slidecontentbox" class=" coin_list ">
               <c:forEach var="v" varStatus="vs" items="${list}">
                <div class="table-item">
                <h3 class="table-item-h3">
					<c:if test="${v.ad_type == 2 }"><span class='colorBlue'><spring:message code="agent.purchase" /></span></c:if>
                    <c:if test="${v.ad_type == 1 }"><span class="redcolor"><spring:message code="market.sell" /></span></c:if>
                    <c:if test="${v.status == 1 }"><span style="color:#6f6f6f;font-size:0.24rem"><spring:message code="ad.shelves.downline" /></span></c:if>
                    <c:if test="${v.status == 0 }"><span style="color:#FF1616;font-size:0.24rem">(<spring:message code="ad.shelves.online" />)</span></c:if>
                    <c:if test="${v.status==0}">
                     <span class="buyBt" onclick="dismount(${v.id})"><spring:message code="ad.shelves.down" /></span>
                    </c:if>
				</h3>
                <ul class="fnickUl">
					<li><em>${v.repertory_count }个</em>
					<spring:message code="market.amount" />
					</li>
					<li><em>${v.price }CNY</em>
					<spring:message code="market.price.cny" />
					</li>
					<li class="amountLi" style="text-align: right;">
						 <em class="gray">
	                      	<fmt:formatNumber value="${v.order_limit_min }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="2"/>
	                      	~
	                      	<fmt:formatNumber value="${v.order_limit_max }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="2"/>CNY
	                     </em>
						 <spring:message code="agent.amount" />
					</li>
				</ul>
               </div>                
               </c:forEach>
            </div>
        <%-- 	<div class="page">
				${pagin }
			</div> --%>

        </div>
    </section>

<!-- 上滑加载更多  -->
<input type="hidden" id="pageCount" value="${totalPage}">
<input type="hidden" id="currentPage" value="${currentPage}">
<div class="textC" id="slide_loading_btn" style="display:none;" onclick="slideLoadMoreInfo()" style=" <c:if test="${totalPage==1 }">display:none</c:if>"><spring:message code="m.security.moremsg" /></div>

<%@include file="../comm/footer.jsp" %>	
<script type="text/javascript" src="${oss_url}/static/front2018/js/finance/account.usdtrecharge.js?v=14"></script>
<script type="text/javascript" src="${oss_url}/static/front2018/js/index/main.js"></script>
<script type="text/javascript">
    $(".uTrade_l a").click(function(event) {
        $(this).addClass('active').siblings().removeClass('active');
    });

    var isClick = false;
    
	function dismount(id){
		var param = {'id':id};
		
		util.layerConfirm("您确定要下架该广告？", function () {
			if(isClick) {
				return ;
			}
			isClick= true;
			jQuery.post('/advertisement/doDismount.html', param, function (data) {
		    	if (data.success) {
		    		util.layerAlert("", data.massage, 1);
		    		
					window.location.href='/advertisement/advertisementMine.html';
				
				} else {
					util.layerAlert("", data.massage, 2);
					isClick= false;
				}
		    }, "json");
		});
	}

</script>

<script type="text/javascript">
var pageCount = parseInt($('#pageCount').val());
var currentPage = parseInt($('#currentPage').val());
var slideBtn = $('#slide_loading_btn');
$(document).ready(function(){
    $(window).bind("scroll",slideLoad);
    if(currentPage==pageCount&&pageCount>1){  $(slideBtn).text(language["load.no.msg"]).show(); }
});
function slideLoad(){
    var scrollbar_top = document.documentElement.scrollTop  || document.body.scrollTop
    var bottomHeight = 10;
    var screenHeight = document.documentElement.clientHeight || document.body.clientHeight
    var page_bottom_pos = scrollbar_top+screenHeight+bottomHeight
    if (page_bottom_pos >= document.body.scrollHeight){
        slideLoadMoreInfo();
    }

}
function  slideLoadMoreInfo(){
	var selectId = $('#select option:selected'); 
	
    $(window).unbind("scroll",slideLoad);
    if(currentPage>0&&currentPage<pageCount)
    {
    	var url = window.location.href;
		var index = layer.load(2);
        $(slideBtn).text(language["load.ing.msg"]).show();
    
        var object = {};
        object.currentPage = currentPage+1;
        $.get(url,object,function(html){
            if($.trim(html)!='')
            {
            
                $('#slidecontentbox').append(html);
                $('#currentPage').val(currentPage+1);
                $(slideBtn).text(language["load.more.msg"]).hide();
                $(window).bind("scroll",slideLoad);
                currentPage = parseInt($('#currentPage').val());
                if(currentPage==pageCount)
                {
                    $(slideBtn).text(language["load.no.msg"]).show();
                    $(window).unbind("scroll",slideLoad);
                }
            }
            else
            {
                $(slideBtn).text(language["load.no.msg"]).show();
                $(window).unbind("scroll",slideLoad);
            }
            layer.close(index);
        })
    }
    else
    {
        $(slideBtn).text(language["load.no.msg"]).fadeOut(3000);
        $(window).unbind("scroll",slideLoad);
    }
}


/* 在线下线 */
<c:if test="${sessionScope.login_user!=null&&sessionScope.login_user.fismerchant==true }">
	$("#online").click(function(){
		$.post("/user/online.html?random=" + Math.round(Math.random() * 100),function(data) {
			if (data.code == 0) {
				util.showMsg(language["comm.error.online.1"]);
				$("#online").hide();
				$("#offline").show();
			}else {
				util.showMsg(language["comm.error.online.2"]);
			}
		}, "json");
	
	});
	$("#offline").click(function(){
		$.post("/user/offline.html?random=" + Math.round(Math.random() * 100),function(data) {
			if (data.code == 0) {
				util.showMsg(language["comm.error.offline.1"]);
				$("#offline").hide();
				$("#online").show();
			}else {
				util.showMsg(language["comm.error.offline.2"]);
			}
		}, "json");
	});
</c:if>
</script>


</body>
</html>
