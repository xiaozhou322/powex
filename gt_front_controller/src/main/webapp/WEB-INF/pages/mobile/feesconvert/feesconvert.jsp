<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ include file="../comm/include.inc.jsp"%>

<!doctype html>
<html>
<head>
<%@ include file="../comm/basepath.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name = "viewport" content = "width = device-width, initial-scale = 1.0, maximum-scale = 1.0, user-scalable = 0" />
<%@include file="../comm/link.inc.jsp"%>
<link href="${oss_url}/static/mobile2018/css/usdtTrade.css?v=20190301163300100" rel="stylesheet" type="text/css" />
<style>
.tradeTop{padding-left:0;}
.tradeTop .tit{
    text-align: center;
    margin: 0 auto;
}
.uTrade{
	box-shadow: 0px 2px 9px 0px rgba(70, 90, 111, 0.14);
    margin-bottom: 0.32rem;
    margin-top: 0.2rem;}
.buyBt{float:right;}
.uTrade_l{
    display: block;
    overflow: hidden;
    clear: both;
}
.active{
    background: #fff;
    color: #488bef;
    border-bottom: 1px solid #488bef;
}
.leftmenuA{
    padding: 0.2rem 0.13rem;
    height: 0.8rem;
    display: inline-block;
    text-align: center;
}
.close{
    float: right;
    font-size: 0.52rem;
    padding-right: 0.3rem;
}
.buyTit{
    color: #506FC8;
    font-size: 0.24rem;
    position: absolute;
    right: 0.3rem;
}
    
.userFace{
	position: relative;
}
.order_limit{
    font-size: 0.26rem;
 white-space: nowrap;
 text-overflow: ellipsis;
 line-height: 0.5rem;
 color: #000;
}
.feesconTit{
overflow: hidden;
clear: both;
border-bottom: 1px solid #0333;
height: 0.64rem;
line-height: 0.46rem;
    color: #000;
}
.table-item .fnickUl{color:#4a4a4a;}
</style>
</head>
<body>
	<!-- 购买页面 -->
	<section class="uTrade" style="margin-top:0;">
		<div class="agentCon">
		<ul id="slidecontentbox">
			<c:forEach var="v" varStatus="vs" items="${list}">
				<div class="table-item">
					<h3 class="feesconTit">
						<span class="fl">
							时间：<fmt:formatDate value="${v.createTime }" pattern="yyyy-MM-dd"/>
						</span>
						<span class="fr">
							订单ID：${v.id }
						</span>
					</h3>
					<ul class="fnickUl">
						<li><em style="color: #ffb266;">${v.bfscAmount}</em>
						BFSC数量
						</li>
						<li><em style="color: #ffb266;">${v.usdtAmount}</em>
						USDT数量
						</li>
						<li><em style="color: #74c40d;">$${v.bfscPrice }</em>
						BFSC价格
						</li>
					</ul>
					<div>
						<p class="fl order_limit">
							状态&nbsp;&nbsp;
							<c:if test="${v.status == 1 }">
								<span style="color: #488bef;">未处理</span>
							</c:if>
							<c:if test="${v.status == 2 }">
								<span style=" color: #666;">已处理</span>
							</c:if>
						</p>
						<p class="fr">
							<c:if test="${v.status == 1 }">
								<span class="buyBt" style=" background: #488bef; color: #fff;" onclick="handleOrder(${v.id}, true)">
									处理订单
								</span>  
							</c:if>
							<c:if test="${v.status == 2 }">
								<span class="buyBt" style=" background: #ccc; color: #fdfdfd; ">
									订单已处理
								</span> 
							</c:if>
						</p>
					</div>
				</div>
			</c:forEach>
			</ul>
		</div>
	</section>
<%@include file="../comm/otc_tabbar.jsp"%>
<!-- 上滑加载更多  -->
<input type="hidden" id="pageCount" value="${totalPage}">
<input type="hidden" id="currentPage" value="${currentPage}">
<input type="hidden" id="tradePwd" value="">
<div class="textC" id="slide_loading_btn" style="display:none;" onclick="slideLoadMoreInfo()" style=" <c:if test="${totalPage==1 }">display:none</c:if>"><spring:message code="m.security.moremsg" /></div>

<%@include file="../comm/footer.jsp" %>	
<script type="text/javascript" src="${oss_url}/static/front2018/js/index/main.js"></script>
<script type="text/javascript">
	var isClick = false;
    
	function handleOrder(id, flag){
		if(flag){
			layer.prompt({title: '交易密码', formType: 1}, function(pass, index){
				$('#tradePwd').val(pass);
				handleOrder(id, false);
				layer.close(index);
			});
			return;
		}
		
		var tradePwd = util.trim(document.getElementById("tradePwd").value);
		if (tradePwd == "") {
			util.layerAlert("", language["comm.error.tips.58"], 2);
			return;
		}
		var param = {
			id:id,
			tradePwd : tradePwd
		};
		
		if(isClick) {
			return ;
		}
		isClick= true;
		
		var url = "/feesConvert/handleFeesConvert.html?random=" + Math.round(Math.random() * 100);
		
		$.post(url, param, function (data) {
			if (data.code == 0) {
	    		util.layerAlert("", data.msg, 1);
	    		setTimeout(function(){  //使用  setTimeout（）方法设定定时3000毫秒
	    			window.location.href='/feesConvert/queryFeesConvertList.html';
				},3000);
			} else {
				util.layerAlert("", data.msg, 2);
				isClick= false;
			}
	    }, "json");
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

</script>


</body>
</html>
