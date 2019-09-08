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
<link href="${oss_url}/static/front2018/css/usdtTrade.css?v=12" rel="stylesheet" type="text/css" />
</head>
<body class="">
<%@include file="../comm/white_header.jsp" %>
<section class="uTrade mg clear">
        <div class="uTrade_l">
            <%@include file="_leftmenu.jsp"%>
        </div>
        <div class="uTrade_r">
            <div class="tables-title clear">
                <span class="tit fl"><spring:message code="agent.agency.name" /></span>
                <span class="tit fl"><spring:message code="agent.payment" /></span>
                 <span class="tit fl"><spring:message code="market.amount" /></span>
                <span class="tit fl"><spring:message code="market.price" /></span>
                <span class="tit fl"><spring:message code="agent.amount" /></span>
                <span class="tit fl"><spring:message code="market.entrustaction" /></span>
            </div>
            <div class="agentCon">
               <c:forEach items="${list}" var="v">
                <div class="table-item">
                    <div class="tabs userFace fl">
                        <span class="fl iconTag">${v.fagentinfo['fname']}</span>
                        <div class="uName fl">
                            <a href="javascript:;">${v.fagentinfo['fname']}</a>
                            <c:if test="${v.fagentinfo['isaudit']==1}">
                           		<img src="${oss_url}/static/front2018/images/v.png" alt="" />
                            </c:if>
                        </div>
                        <div class="clear"></div>
                    </div>
                    <div class="tabs group-payway fl">
                       
                    	 <c:if test="${v.fagentpayaccount['openalipay'] == 1 }">
                        <i><img src="${oss_url}/static/front2018/images/jfb.png" alt="" /></i>
                        </c:if>
                        <c:if test="${v.fagentpayaccount['openweixin'] == 1 }">
                        <i><img src="${oss_url}/static/front2018/images/wx.png" alt="" /></i>
                        </c:if>
                        <c:if test="${v.fagentpayaccount['openbank'] == 1 }">
                        <i><img src="${oss_url}/static/front2018/images/card.png" alt="" /></i>
                        </c:if>
                     
                    </div>
                    <div class="tabs rate fl"><em>${v.validnum}</em>USDT</div>
                    <div class="tabs rate fl">
                    <em>${v.rate}</em>CNY<br>                    
                    </div>
                    <div class="tabs rate fl">
                        <em data-v-ad45b12e="" class="gray">${v.minamount}-${v.maxamount}CNY</em>
                    </div>
                    <div class="tabs fl"><span class="buyBt" onclick="buyIn(${v.rate},${v.fid})"><spring:message code="market.buy" /></span></div>

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

<div class="warpForm">
        <div class="showBox">
            <h2 class="title">
                <spring:message code="market.buy" />USDT
                <span class='colorRed'><spring:message code="agent.showmsg" /></span>
            </h2>
            <input type="hidden" id='agentorder_id' >
            <div class="showCon">
                <p class="rateBox"><spring:message code="agent.toprice" />（CNY/USDT）<span id="price">--</span></p>
                <p class="tr"><spring:message code="market.amount" />USDT</p>
                <div class="tr inputBox">
                    <input type="text" class="inp" id="usdt_num" placeholder="<spring:message code="agent.showamount" />" />
               </div>
                <p class="tr"><spring:message code="agent.cnyamount" />CNY</p>
                <div class="tr inputBox">
                    <input type="text" class="inp" id="cny_num" placeholder="0.0000" />
               </div>
                <span class="remind colorRed" style="display:none"><spring:message code="agent.cnyzero" /></span>
                <div class="btns">
                    <input type="button" class="button active" value="<spring:message code="agent.confirm" />" onclick="doBuy()"/>
                    <span class="button cancle">
                        <em class="countdown colorRed" id="countdown">(45S)</em>
                        <span class="em"><spring:message code="financial.cancel" /></span>
                    </span>
                </div>
             </div>
             <em class="close">×</em>
        </div> 
    </div>

<%@include file="../comm/footer.jsp" %>	
<script type="text/javascript" src="${oss_url}/static/front2018/js/finance/account.usdtrecharge.js?v=14"></script>
<script type="text/javascript" src="${oss_url}/static/front2018/js/index/main.js?v=20181126201750"></script>
<script type="text/javascript">
    $(".uTrade_l a").click(function(event) {
        $(this).addClass('active').siblings().removeClass('active');
    });

    var time;   
    var second=45;
    function showTime(){
        if(second <= 0)
        {
        $(".warpForm").fadeOut(200);
        clearInterval(time);
        setTimeout(setSecond,500);
        }else{
            second--;
            $(".countdown").html('('+second+'S)');
        }
    }

    function buyIn(price,fid)
    {
    	$('.remind').text('').hide();
        $('#price').text(price);
        $('#agentorder_id').val(fid);
     	time=setInterval (showTime, 1000);
        $(".warpForm").fadeIn(200, function() {
            $(".warpForm .close").click(function(event) { 
               $(this).parent().parent().fadeOut(100);
               setTimeout(setSecond,500);
               clearInterval(time);
            });
        });
    }
    
    function doBuy()
    {
    	var usdt_num = $('#usdt_num').val();
    	var cny_num = $('#cny_num').val();
    	var price = $('#price').text();
    	var agentorder_id =  $('#agentorder_id').val();
    	if(usdt_num=='')
    	{
    		$('.remind').text(language["agent.sum"]).show();
    		return;
    	}
    	if(cny_num=='')
    	{
    		$('.remind').text(language["agent.sumamount"]).show();
    		return;
    	}
    	
    	var param = {'usdt_num':usdt_num,'cny_num':cny_num,'price':price,'agentorder_id':agentorder_id}
    	
    	jQuery.post('/agent/dobuy.html', param, function (data) {
		    $(".warpForm").fadeOut(100);
	        setTimeout(setSecond,500);
	        clearInterval(time);
        	if (data.code == -1) {
				util.layerAlert("", data.msg, 2);
				
			} else {
				//util.layerAlert("", data.msg, 1);
				window.location.href='/agent/orderdetail.html?order_id='+data.operid;
			}
        }, "json");
    }

    $("#cny_num").on("input",function(){
       if($('#cny_num').val()!='')
        {
          $('#usdt_num').val((parseFloat($('#cny_num').val())/$('#price').text()).toFixed(4));
        }else{
            $('#usdt_num').val(parseFloat(0.0000).toFixed(4));
        }
    });


    $("#usdt_num").on("input",function(){
       if($('#usdt_num').val()!='')
        {
          $('#cny_num').val(($('#usdt_num').val()*$('#price').text()).toFixed(4));
        }else{
            $('#cny_num').val(parseFloat(0.0000).toFixed(4));
        }
    });

	
    $(".warpForm .cancle").click(function(event) {
       $(".warpForm").fadeOut(100);
        setTimeout(setSecond,500);
        clearInterval(time);
    });

    function setSecond(){
        second = 45;
       $(".countdown").html('('+second+'S)');
    }
  
    $("#usdt_num,#cny_num").on("keypress", function(event) {
        return util.VerifyKeypress(this, event, 4);
    })


</script>

</body>
</html>
