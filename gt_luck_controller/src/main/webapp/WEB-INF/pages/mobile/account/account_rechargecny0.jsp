<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
<meta name = "viewport" content = "width = device-width, initial-scale = 1.0, maximum-scale = 1.0, user-scalable = 0" />
<%@include file="../comm/link.inc.jsp" %>

  <link href="${oss_url}/static/mobile/css/base.css?v=2" rel="stylesheet" type="text/css" />
<!--   <link href="${oss_url}/static/mobile/css/main.css?v=4" rel="stylesheet" type="text/css" />
 -->    <title><spring:message code="financial.recharge" /></title>
    <style type="text/css">
        @font-face {
           font-family: 'PingFangMedium';
           src: url('/static/mobile/fonts/PingFangMedium.ttf'); 
        } 
    </style>

</head>
<body>
<div id="topUp1">
<header class="header backHeader">  
    <i class="backIcon" onclick="previousPage();"></i>
    <h2 class="tit">CNY <spring:message code="financial.recharge" /></h2>
</header>
<section>
    <div class="topUpCon">
        <div class="choiceCoin">
        <div class="textRight" style="padding-top:0.5rem;">
                <a href="" class="backHome fl"><spring:message code="m.security.gotopage" /></a>
                <!-- <a  onclick="showmsg();" class="cblue fr"><spring:message code="financial.recrec" /></a> -->
                <div class="clear"></div>
            </div>
            <div class="topUpForm">
                <span class="topUpStep recharge-process">
                    <span id="rechargeprocess1" class="fl active">
                        <span class="recharge-process-icon recharge-process-icon1"></span>
                        <span class="recharge-process-text textLeft"><spring:message code="financial.cnyrecharge.step1" /></span>
                    </span>
                    <span id="rechargeprocess2" class="fl">
                        <span class="recharge-process-icon recharge-process-icon2"></span>
                        <span class="recharge-process-text textCenter"><spring:message code="financial.cnyrecharge.step2" /></span>
                    </span>
                    <span id="rechargeprocess4" class="fl">
                        <span class="recharge-process-icon recharge-process-icon3"></span>
                        <span class="recharge-process-text textRight"><spring:message code="financial.cnyrecharge.step3" /></span>
                    </span>
                    <div class="clear"></div>
                </span>
                <div class="step1Con" id="rechage1">
                    <p><spring:message code="financial.cnyrecharge.bank" />
                        <c:forEach items="${bankInfo }" var="v" varStatus="vs">
                            <c:if test="${0 eq  vs.index}"> 
                                <span class="selStyle fr" onclick="showtabs('sbank_box')" id="sbanks">${v.fbankName }</span>
                            </c:if>
                        </c:forEach>
                    </p>


                    <select class="select select3 sl1" id="sbank" style="display:none; border:none;">
                            <c:forEach items="${bankInfo }" var="v">
                                <option value="${v.fid }">${v.fbankName }</option>
                            </c:forEach>
                    </select>


                    <p><spring:message code="financial.cnyrecharge.amount" /><input type="text fl"  class="input" id="diyMoney" autocomplete="off"/><span for="diyMoney" class="fr randomtips">.${randomMoney }</span>
                    </p>
                    <input type="hidden" value="0.${randomMoney }" id="random"> 
                    <button class="btn btn4 mtop" id="rechargebtn"><spring:message code="financial.cnyrecharge.genRem" /></button> 
                    <button onclick="showmsg();" class="btn mtop3"><spring:message code="financial.recrec" /></button>

                </div>


                <div class="step1Con step2Con" id="rechage2" style="display: none">
                    <div class="txt">
                        <spring:message code="financial.cnyrecharge.payee" />： <span id="fownerName">吕易文</span><br />
                        <spring:message code="financial.cnyrecharge.collaccount" />： <span id="fbankNumber">--</span> <br />
                        <spring:message code="financial.cnyrecharge.deposit" />： <span id="fbankAddress">--</span><br />
                        <spring:message code="financial.cnyrecharge.payamount" />： <em class="cred2"><span id="bankMoney">--</span></em><br />
                        <spring:message code="financial.cnyrecharge.note2" />： <em class="cred2"><span id="bankInfo">--</span></em> <br />
                        <em class="cred2"><spring:message code="financial.cnyrecharge.note3" /> <span id="bankInfotips">--</span></em> <br />            
                    </div>
                    <button class="btn btn5 mtop2" id="rechargenextbtn"><spring:message code="financial.cnyrecharge.completenext" /></button> 
                </div>
				

				<div class="step1Con step2Con step3Con" id="rechage4" style="display: none">
                    <div class="step3Main">
                        <h1><spring:message code="m.security.succ" /></h1>
                        <em><spring:message code="financial.cnyrecharge.waitinfo" /></em>
                    </div>
                    <a href="/account/rechargeCny.html?type=0" class="btn btn5 mtop"><spring:message code="financial.cnyrecharge.nextrecharge" /></a> 
                </div>
            </div>
        </div>

    </div>
    <div class="care">
        <em class="cred2"><spring:message code="financial.recins" /></em><br />
        <p><spring:message code="financial.cnyrecharge.thsmininum"   arguments="${requestScope.constant['minrechargecny'] },${requestScope.constant['minrechargecny'] }" /></p>
        <spring:message code="financial.cnyrecharge.note4" />
    </div>
    <div class="coinWarp">
        <div class="coinLitBox">
            <ul>
                <a class="cny active" href="/account/rechargeCny.html?type=1">CNY</a>
                <c:forEach items="${requestScope.constant['allRechargeCoins'] }" var="v">
                <li><a href="/account/rechargeBtc.html?symbol=${v.fid }">${v.fShortName }</a></li>
                </c:forEach>
                <div class="clear"></div>
            </ul>
        </div>
    </div>
</section>

</div>  

<div id="topUp2" style="display: none;">
    <header class="header backHeader">  
    <i class="backIcon2"></i>
    <h2 class="tit">CNY <spring:message code="financial.recrec" /></h2>
</header>

<section>
    <div class="billingMain topUpDetail" id="slidecontentbox" style="padding-bottom:0;">
        <!--  <h2 style="height:1.5rem; line-height:1.5rem; padding-left:0.3rem; background:#fff; margin-bottom:0.2rem;">CNY <spring:message code="financial.recrec" /></h2> -->
        <c:forEach items="${list}" var="v">
        <div class="txDetail" >
            <ul>
                <li class="fl">
                    <em class="cgreen3">${v.famount }</em>
                </li>                
                <li class="fl">
                    <em><spring:message code="financial.cnyrecharge.status${v.fstatus }" /></em>
                </li>                
                <li class="fl textRight ">
                <c:if test="${(v.fstatus==1 || v.fstatus==2)}">
                    <a class="cblue opa-link rechargecancel" href="javascript:void(0);" id="rechargecancel_${v.fid }" data-fid="${v.fid }"><spring:message code="financial.cnyrecharge.cancel" /></a>
                    <span id="show${v.fid }" style="display: none">--</span>
                    <c:if test="${v.fstatus==1}">
                    <a class="cblue opa-link rechargesub" href="javascript:void(0);" id="rechargesub_${v.fid }" data-fid="${v.fid }"><spring:message code="financial.cnyrecharge.submit" /></a>
                    </c:if>
                </c:if>
                    <c:if test="${(v.fstatus==3 || v.fstatus==4)}">
                        <td class="opa-link">--</td> 
                    </c:if>
                </li>
                <div class="clear"></div>
            </ul>
            <ol>
                <li>
                    <span><spring:message code="financial.cnyrecharge.orderno" />：</span>
                    <em>${v.fid }</em>
                </li>                
                <li>
                    <span><spring:message code="financial.cnyrewithdrawal.remarknum" />：</span>
                    <em>${v.fremark }</em>
                </li>                
                <li>
                    <span><spring:message code="financial.cnyrecharge.rechargemode" />：</span>
                    <em>${v.fremittanceType }</em>
                </li>                
                <li>
                    <span><spring:message code="financial.cnyrecharge.rechargetime" />：</span>
                    <em><fmt:formatDate value="${v.fcreateTime }" pattern="yyyy-MM-dd HH:mm:ss"/></em>
                </li>
            </ol>
        </div>
        </c:forEach>        
         <c:if test="${fn:length(list)==0 }">
  
                <div class="textCenter mtop2">
                    <img src="${oss_url}/static/mobile/images/noMsg.png" width="86" alt="" />
                    <p><spring:message code="m.security.norecharge" /></p>
                </div>

         </c:if>

    </div>
   
</section>

</div>
<div class="slet"></div> 
 <%@include file="../comm/header.jsp" %>

<!-- 	<div class="container-full padding-top-40">
		<div class="container displayFlex">
			 -->
	


  
    <input type="hidden" value="${currentPage }" name="currentPage" id="currentPage">
    <input type="hidden" value="${type }" name="finType" id="finType">
    <input id="minRecharge" value="${minRecharge }" type="hidden">
    <input type="hidden" value="0" name="desc" id="desc">
    <input type="hidden" value="0" name="remark" id="remark">


<%@include file="../comm/footer.jsp" %>	
<!-- 上滑加载更多  -->
<input type="hidden" id="pageCount" value="${totalPage}">
<input type="hidden" id="currentPage" value="${currentPage}">
<div id="slide_loading_btn"  onclick="slideLoadMoreInfo()" style=" <c:if test="${totalPage==1 }">display:none</c:if>"><spring:message code="m.security.moremsg" /></div>
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
	<script type="text/javascript" src="${oss_url}/static/mobile/js/account.recharge.js?v=4"></script>
	<script type="text/javascript" src="${oss_url}/static/front/js/index/main.js?v=20181126201750"></script>
<script type="text/javascript">
    $(".choiceMore").click(function(event) {
        $(".coinWarp").animate({bottom:"0"}, 200)
    });

    $(".coinLitBox ul li").click(function(event) {
        var text=$(this).html();
        $(this).addClass('active').siblings().removeClass('active');
        $(".choiceMore").html(text)
        $(".choiceMore").css('color', '#2a86fe');
        $(".coinWarp").css('bottom', '-100%');
    });

    function showmsg(){
        $("#topUp2").css('display','block');
        $("#topUp1").css('display','none');
    }
    $(".backIcon2").click(function(event){
        $("#topUp1").css('display','block');
        $("#topUp2").css('display','none');
    });


 function previousPage(){
      window.history.go(-1);
    }
</script>
</body>
</html>
