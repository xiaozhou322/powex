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
<%@include file="../comm/link.inc.jsp" %>
<meta name = "viewport" content = "width = device-width, initial-scale = 1.0, maximum-scale = 1.0, user-scalable = 0" />
<link rel="stylesheet" type="text/css" href="${oss_url}/static/mobile/css/iosSelect.css?v=20181126201750" />

    <title>CNY<spring:message code="financial.withdrawal" /></title>
    <style type="text/css">
        @font-face {
           font-family: 'PingFangMedium';
           src: url('/static/mobile/fonts/PingFangMedium.ttf'); 
        } 
    </style>
</head>
<body>
	<input type="hidden" id="max_double" value="${requestScope.constant['maxwithdrawcny'] }">
	<input type="hidden" id="min_double" value="${requestScope.constant['minwithdrawcny'] }">
	


<%@include file="../comm/header.jsp" %>
<div id="top1">
	<header class="header backHeader">  
    <i class="backIcon1"></i>
    <h2 class="tit">${fvirtualcointype.fShortName }<spring:message code="financial.withdrawal" /></h2>
</header>
<section>
    <div class="topUpCon withdrawalCon CNYwithdrawalCon" style="padding-top:1.6rem;">
            <div class="choiceBox choiceBox2 login_item_02">
            
               <span class="fl login_item_l noMargin"><spring:message code="m.security.current" /></span> 
               <span class="fl choiceMore selStyle selStyle2">CNY</span>
               <div class="availableCoin fr">
                    <!-- <span></span> -->
                    <span><spring:message code="market.usable" />&nbsp;<em class="cgray">${fvirtualcointype.fShortName }<ex:DoubleCut value="${fwallet.ftotal }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/></em></span>
                </div>
                <div class="clear"></div>
            </div>
            <div class="choiceCoin choiceCoin2">
                <div class="topUpForm withdrawalForm special_01">
                    <div class="txAddress">
                        <span class="tit fl"><spring:message code="financial.cnyrewithdrawal.card" /> </span>
                         <c:if test="${fn:length(fbankinfoWithdraws)==0 }">           
                            <span style="margin-left:0.3rem;"><spring:message code="m.security.notxaddress" /></span>
                        </c:if>
                        <c:if test="${fn:length(fbankinfoWithdraws)!=0 }">
                         <c:forEach items="${fbankinfoWithdraws }" var="v" varStatus="vs">
                         <c:if test="${0 eq  vs.index}">  
                          <span class="selStyle selStyle3" onclick="showtabs('withdrawBlank_box')" id="withdrawBlanks">${v.fname }&nbsp;&nbsp;<spring:message code="financial.cnyrewithdrawal.cardnum" />${v.fbankNumber }</span>
                          </c:if>
                          </c:forEach>  
                        </c:if>
                        <a class="fr cblue" onclick="showaddbank();"><spring:message code="financial.toadd" /></a>
                        <div class="clear"></div>
                    </div>

                   <select id="withdrawBlank" class="fl form-control select select9 sl1"  style="display: none">
                        <c:forEach items="${fbankinfoWithdraws }" var="v">
                          <option value="${v.fid }">${v.fname }&nbsp;&nbsp;<spring:message code="financial.cnyrewithdrawal.cardnum" />${v.fbankNumber }</option>
                          </c:forEach>    
                    </select>
                    <div class="withdrawalList">
                      <div class="login_item login_item_02 clear">
                          <label for="withdrawBalance" class="login_item_l"><spring:message code="financial.cnyrewithdrawal.amount" /></label>
                          <div class="login_item_r">
                              <input type="text" id="withdrawBalance" autocomplete="off" placeholder="<spring:message code="financial.cnyrewithdrawal.amount" />">
                          </div>

                      </div>      
                      <div>
                      <div class="amounttips login_item_02" style="padding:0.3rem 0 0.3rem 0.3rem; line-height:0.8rem; border-bottom:1px solid #e4e4e4;">
                          <em class="login_item_l"><spring:message code="financial.cnyrewithdrawal.fee" /></em>
                          <span id="free" class="cblue" style="margin-left:0.3rem;">0</span>
                          <span class="sFont">CNY</span><br />
                        <span>
                          <em class="login_item_l"><spring:message code="financial.cnyrewithdrawal.arr" /></em>
                          <span id="amount" class="text-danger cred2" style="margin-left:0.3rem;">0</span>
                          <span class="text-danger sFont">CNY</span>
                        </span>
                      </div>
                     </div>       
                      <div class="login_item login_item_02 clear">
                          <label for="tradePwd" class="login_item_l"><spring:message code="financial.tradingpwd" /></label>
                          <div class="login_item_r">
                              <input type="password" id="tradePwd" autocomplete="off" placeholder="<spring:message code="financial.tradingpwd" />">
                          </div>
                      </div> 

                      <c:if test="${isBindTelephone == true }"> 
                      <div class="login_item login_item_02 clear relative">
                          <label for="withdrawPhoneCode" class="login_item_l mtop5"><spring:message code="security.smscode" /></label>
                          <div class="login_item_r">
                              <input type="text" id="withdrawPhoneCode" autocomplete="off" placeholder="<spring:message code="security.smscode" />">
                          </div>
                          <button id="withdrawsendmessage" data-msgtype="4" data-tipsid="withdrawerrortips" class="sendCode btn-sendmsg"  ><spring:message code="financial.send" /></button>
                      </div>
                      </c:if> 
            
                    <c:choose>
                    <c:when test="${isBindGoogle ==true}">
                      <div class="login_item login_item_02 clear">
                          <label for="" class="login_item_l mtop5"><spring:message code="financial.goocod" /></label>
                          <div class="login_item_r">
                              <input type="text" id="withdrawTotpCode" autocomplete="off" placeholder="<spring:message code="financial.goocod" />">
                          </div>
                      </div>
                    </c:when>
                    <c:otherwise>
                      <c:if test="${isBindTelephone == false}">
                      <div class="login_item login_item_02 clear">
                     <label for="" class="login_item_l"><spring:message code="financial.goocod" /></label>
                          <div class="login_item_r">
                               <a href="/user/security.html?tab=4" style="height:36px; line-height:40px; display:block;"><spring:message code="financial.checkgoogle" /></a>
                          </div>
                         
                    </div>
                    </c:if>
                    </c:otherwise>
                  </c:choose>        
                    
                    </div>              
                </div>                
            </div>
<!--             <div class="txAddress txJiLu" onclick="showmsglist();">
                <span class="tit"><spring:message code="financial.witrec" /></span>
            </div> -->
            <button class="btn btn2 mtop2" id="withdrawCnyButton"><spring:message code="financial.immwit" /></button>
            <button class="btn mtop3" onclick="showmsglist();"><spring:message code="financial.witrec" /></button>
            <div class="care">
                <em class="cred2"><spring:message code="financial.witins" />：</em><br />
                <p><spring:message code="financial.cnyrewithdrawal.thsmininum"   arguments="${requestScope.constant['minwithdrawcny'] },${requestScope.constant['maxwithdrawcny'] }" /></p>
										<spring:message code="financial.cnyrewithdrawal.note" arguments="${fee*100 }" />
            </div>
        
    </div>

    <div class="coinWarp" id="coinWarp">
        <div class="coinLitBox" id="coinLitBox">
            <ul>
                <li><a class="active" href="/account/withdrawCny.html">CNY</a></li>
                <c:forEach items="${requestScope.constant['allWithdrawCoins'] }" var="v">
                <li><a class="${v.fid==symbol?'active':'' }" href="/account/withdrawBtc.html?symbol=${v.fid }&tab=2">${v.fShortName }</a></li>
                </c:forEach>
                <div class="clear"></div>
            </ul>
        </div>
    </div>
    </section>
</div>

<div id="top2" style="display: none">
  
  <header class="header backHeader">  
    <i class="backIcon2"></i>
    <h2 class="tit"><spring:message code="financial.cnyrewithdrawal.addcard" /></h2>
</header>
<section>
<!--     <div class="topUpMain ticketsMain">
         <ul>
             <li class="active"><a href="###">提现选择</a></li>
             <li><a href="###">账单明细</a></li>
         </ul>
     </div> -->
<div class="topUpCon withdrawalCon CNYwithdrawalCon">
            <div class="choiceCoin choiceCoin2">
                <div class="topUpForm withdrawalForm">
                    <div class="withdrawalList">
                      <div class="login_item login_item_02 clear">
                          <label for="payeeAddr" class="login_item_l"><spring:message code="financial.cnyrewithdrawal.accname" /></label>
                          <div class="login_item_r">
                              <input type="text" id="payeeAddr" value="${fuser.frealName }" readonly="readonly" autocomplete="off">
                          </div>
                      </div>      
                      <div class="login_item login_item_02 clear ">
                          <label for="withdrawAccountAddr" class="login_item_l"><spring:message code="financial.cnyrewithdrawal.account" /></label>
                          <div class="login_item_r" class="special_03">
                              <input type="text" id="withdrawAccountAddr" autocomplete="off" placeholder="<spring:message code="financial.cnyrewithdrawal.account" />">
                            </div>
                      </div>
                       <div class="login_item login_item_02 clear">
                          <label for="withdrawAccountAddr2" class="login_item_l mtop5"><spring:message code="financial.cnyrewithdrawal.confirmcard" /></label>
                          <div class="login_item_r">
                              <input type="" id="withdrawAccountAddr2" autocomplete="off" placeholder="<spring:message code="financial.cnyrewithdrawal.confirmcard" />">
                            </div>
                      </div>            
                                   
                      <div class="login_item login_item_02 clear">
                          <label for="openBankTypeAddr" class="login_item_l"><spring:message code="financial.cnyrewithdrawal.deposit" /></label>
                          <div class="login_item_r">
                                 <span class="selStyle selStyle4" onclick="showtabs('openBankTypeAddr_box')" id="openBankTypeAddrs"><spring:message code="financial.cnyrewithdrawal.choosebank" /></span>                  
                          </div>
                      </div>
                       <select id="openBankTypeAddr" class="form-control select sl1" style="display: none;">
                                  <option value="-1">
                                    <spring:message code="financial.cnyrewithdrawal.choosebank" />
                                  </option>
                                  <c:forEach items="${bankTypes }" var="v">
                                    <option value="${v.key }">${v.value }</option>
                                  </c:forEach>
                      </select>         
                      <div class="login_item login_item_02 clear" id="prov_city">
                          <label for="" class="login_item_l mtop5"><spring:message code="financial.cnyrewithdrawal.addr" /></label>
                          <div class="login_item_r login_item_r2">
                             <select id="prov" class="form-control">
                             </select>
                             <select id="city" class="form-control">
                              </select>
                                           <select id="dist" class="form-control prov">
                              </select>
                          </div>

                      </div>                      
                      <div class="login_item login_item_02 clear" >
                          <label for="" class="login_item_l mtop5"><spring:message code="m.security.bname" /></label>
                          <div class="login_item_r">
                              <input type="text" id="address" autocomplete="off" placeholder="<spring:message code="financial.cnyrewithdrawal.addrtip" />">
                          </div>
                      </div>
                      <c:if test="${isBindTelephone == true }">
                      <div class="login_item login_item_02 clear relative">
                          <label for="addressPhoneCode" class="login_item_l mtop5"><spring:message code="security.smscode" /></label>
                          <div class="login_item_r">
                              <input type="text" id="addressPhoneCode" autocomplete="off" placeholder="<spring:message code="security.smscode" />">
                          </div>
                          <button id="bindsendmessage" data-msgtype="10" data-tipsid="binderrortips"  class="sendCode btn-sendmsg" ><spring:message code="financial.send" /></button>
                      </div>
                      </c:if>
            

                  <c:if test="${isBindGoogle ==true}">
                    <div class="login_item login_item_02 clear">
                        <label for="addressTotpCode" class="login_item_l mtop5"><spring:message code="financial.goocod" /></label>
                        <div class="login_item_r">
                            <input type="text" id="addressTotpCode" autocomplete="off" placeholder="<spring:message code="financial.goocod" />">
                        </div>
                    </div>
                  </c:if> 
            
                    </div>              
                </div>                
            </div>
            <div class="care">
                <span class="cff1e48">*<spring:message code="financial.cnyrewithdrawal.nametip" /></span>
            </div>
            <button id="withdrawCnyAddrBtn" class="btn mtop2"><spring:message code="financial.cnyrewithdrawal.submit" /></button>

    </div>


</section>


</div>


<div id="top3" style="display: none">
  <header class="header backHeader">  
    <i class="backIcon3"></i>
    <h2 class="tit">CNY <spring:message code="financial.witrec" /></h2>
</header>

<section>
    <div class="billingMain topUpDetail" id="slidecontentbox">
<!--      <h2 class="allTitle">CNY <spring:message code="financial.witrec" /></h2>
 -->        <c:forEach items="${fcapitaloperations }" var="v" varStatus="vs">
        <div class="txDetail">
            <ul>
                <li class="fl">
                    <em class="cgreen3">￥${v.famount+v.ffees }</em>
                </li>                
               
                <li class="fr textRight">
                      <span>${v.fstatus_s }<c:if test="${v.fstatus==1 }">
                              &nbsp;|&nbsp;
                              <a class="cancelWithdrawcny opa-link cblue" href="javascript:void(0);" data-fid="${v.fid }"><spring:message code="financial.cnyrecharge.cancel" /></a>
                              </c:if></span>
                </li>
                <div class="clear"></div>
            </ul>
            <ol>
            	<li>
                    <span><spring:message code="financial.witfee" />：</span>
                    <em>￥${v.ffees }</em>
                </li>
                <li>
                    <span><spring:message code="financial.actarr" />：</span>
                    <em>￥${v.famount }</em>
                </li>                
                <li>
                    <span><spring:message code="financial.cnyrewithdrawal.remarknum" />：</span>
                    <em>${v.fid }</em>
                </li>                
                <li>
                    <span><spring:message code="financial.cnyrewithdrawal.account" />：</span>
                    <em>${v.fAccount }</em>
                </li>                
                <li>
                    <span><spring:message code="financial.wittim" />：</span>
                    <em><fmt:formatDate value="${v.fcreateTime }" pattern="yyyy-MM-dd HH:mm:ss"/></em>
                </li>
            </ol>
        </div>        
       </c:forEach>
       <c:if test="${fn:length(fcapitaloperations)==0 }">
        
          <p class="textCenter mtop"><spring:message code="financial.cnyrewithdrawal.nowith" /></p>

        </c:if>

    </div>
  
</section>
</div>

<div class="slet">
  
</div>
	
	
	<input id="feesRate" type="hidden" value="${fee }">
	<input id="userBalance" type="hidden" value="${requestScope.fwallet.ftotal }">
	

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
<%@include file="../comm/footer.jsp" %>	
<script type="text/javascript">
  
    $(".choiceMore").click(function(event) {
        $("#coinWarp").animate({bottom:"0"}, 200)
    });
    $("#coinLitBox ul li").click(function(event) {
        var text=$(this).html();
        $(this).addClass('active').siblings().removeClass('active')
        $(".choiceMore").html(text)
        $("#coinWarp").css('bottom', '-100%');
    });
    $("#coinWarp").click(function(event) {
        $(this).css('bottom', '-100%');
    });





    function showmsglist(){
      $("#top3").css('display','block');
      $("#top1,#top2").css('display','none');
    }
    function showaddbank(){
       $("#top2").css('display','block');
      $("#top1,#top3").css('display','none');
    }
    $(".backIcon3").click(function(event){
      $("#top1").css('display','block');
      $("#top2,#top3").css('display','none');
    });
     $(".backIcon2").click(function(event){
      $("#top1").css('display','block');
      $("#top2,#top3").css('display','none');
    });
     $(".backIcon1").click(function(event){
        window.location.href = "/account/withdrawBtc.html";
    });

// 选择银行卡

    $(".choiceBtn").click(function(event) {
        $(".coinWarp").animate({bottom:"0"}, 200)
    });
    $(".coinLitBox2 ul li").click({id: "openBankTypeAddr"},function(event) {
        id = event.data.id;
        var text=$(this).html();
        var _val = $(this).data("value");
        $(this).addClass('active').siblings().removeClass('active')
        $(".choiceBtn span").html(text);
        $(".coinWarp").css('bottom', '-100%');
        $("#" + id + " option:selected").val(_val);
        $("#" + id + " option:selected").html(text);        
    });
    $(".coinWarp").click(function(event) {
        $(this).css('bottom', '-100%');
    });



</script>
	<script type="text/javascript" src="${oss_url}/static/mobile/js/msg.js?v=20181126201750"></script>
	<script type="text/javascript" src="${oss_url}/static/mobile/js/account.withdraw.js?v=20181126201750"></script>
	<script type="text/javascript" src="${oss_url}/static/front/js/finance/city.min.js?v=20181126201750"></script>
	<script type="text/javascript" src="${oss_url}/static/front/js/finance/jquery.cityselect.js?v=20181126201750"></script>


<!-- <script type="text/javascript">
    var selectContactDom = $('#prov_city');
    var showContactDom = $('#show_contact');
    var contactProvinceCodeDom = $('#contact_province_code');
    var contactCityCodeDom = $('#contact_city_code');
    selectContactDom.bind('click', function () {
        var sccode = showContactDom.attr('data-city-code');
        var scname = showContactDom.attr('data-city-name');

        var oneLevelId = showContactDom.attr('prov');
        var twoLevelId = showContactDom.attr('city');
        var threeLevelId = showContactDom.attr('dist');
        var iosSelect = new IosSelect(3, 
            [iosProvinces, iosCitys, iosCountys],
            {
                title: '地址选择',
                itemHeight: 35,
                relation: [1, 1],
                oneLevelId: oneLevelId,
                twoLevelId: twoLevelId,
                threeLevelId: threeLevelId,
                callback: function (selectOneObj, selectTwoObj, selectThreeObj) {
                    contactProvinceCodeDom.val(selectOneObj.id); 
                    contactProvinceCodeDom.attr('data-province-name', selectOneObj.value);
                    contactCityCodeDom.val(selectTwoObj.id);
                    contactCityCodeDom.attr('data-city-name', selectTwoObj.value);

                    showContactDom.attr('data-province-code', selectOneObj.id);
                    showContactDom.attr('data-city-code', selectTwoObj.id);
                    showContactDom.attr('data-district-code', selectThreeObj.id);
                    showContactDom.html(selectOneObj.value + ' ' + selectTwoObj.value + ' ' + selectThreeObj.value);
                }
        });
    });
</script>
 -->
</body>
</html>
