<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../comm/include.inc.jsp"%>
<%

%>

<!doctype html>
<html>
<head> 
<%@ include file="../comm/basepath.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
 <meta name = "viewport" content = "width = device-width, user-scalable = no,initial-scale=0.5,maximum-scale=0.5,minimum-scale=0.5">
<link rel="stylesheet" type="text/css" href="${oss_url}/static/mobile2018/css/iosSelect.css?v=20181126201750" />
<%@include file="../comm/link.inc.jsp" %>
	<title><spring:message code="m.security.bankcard" /></title>
    <style type="text/css">
        @font-face {
           font-family: 'PingFangMedium';
           src: url('/static/mobile2018/fonts/PingFangMedium.ttf'); 
        } 
        .sendCode{
        	width: 32%;
        }
    </style>
<style type="text/css">
    .selecc{
      display: block;
    }
  </style>

</head>
<body>
<%@include file="../comm/header.jsp" %>

<div id="topUp1" style="height:100%;">
	<header class="tradeTop tradeTop2">  
    <i class="back toback2"></i>
    <h2 class="tit"><spring:message code="m.security.bankcard" /></h2>
</header>
<section class="cnyAddcardMain">
<!-- 	  <div class="trade_chartBarBox conBox1 conBox2">
             <span class="fl"><spring:message code="m.security.selectcoin" /></span>
             	<span class="fl choiceMore selStyle selecc}">USDT</span>
             <div class="clear"></div>
        </div> -->
    <div class="cnyAdcard">
        <ul>
        	<c:forEach items="${bankinfos }" var="v">
            <li class="bank-item">           
                <div class="bank-item-top clear">                    
                    <i class="banklogo fl"><img src="${oss_url}/static/mobile2018/images/1.png" width="34" alt="" /></i>
                    <p>${v.fname }</p>
                    <p>${v.fbankNumber }</p>                  
                </div>
                <div class="bank-item-bot">                   
                    <p class="bank_name">${v.fname },${v.fothers }</p>
                    <p class="bank_other">${v.faddress }</p>                  
                </div>
                <b class="bank-item-del del_close" data-fid="${v.fid }">×</b>
            </li>
            </c:forEach>            
          
        </ul>

        <c:if test="${fn:length(bankinfos)==0 }">
           <div class="textCenter mtop3">
                <img src="${oss_url}/static/mobile2018/images/noMsg.png" width="86" alt="" />
                <p><spring:message code="json.jingji.noord" /></p>
           </div>
        </c:if>



    </div>
<div class="withdraw_footer addCardBtn textCenter">
    <a href="javascript:void();" class="btn" onclick="showaddbank();"><spring:message code="financial.cnyrewithdrawal.addcard" /></a>
</div>

  <div class="coinWarp">
        <div class="coinLitBox">
            <ul>
                <span><a class="cny" href="/financial/accountbank.html">USDT</a></span>
                <c:forEach items="${requestScope.constant['allWithdrawCoins'] }" var="v">
                <li class="${v.fid==symbol?'active':'' }"><a href="/financial/accountcoin.html?symbol=${v.fid }">${v.fShortName }</a></li>
                </c:forEach>
                
                <div class="clear"></div>
            </ul>
        </div>
    </div>

</section>

</div>

<div id="topUp2" style="display: none;">
	<header class="tradeTop tradeTop2">  
    <i class="back toback2"></i>
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
                      <div class="login_item clear">
                          <label for="payeeAddr" class="login_item_l"><spring:message code="financial.cnyrewithdrawal.accname" /></label>
                          <div class="login_item_r">
                              <input type="text" id="payeeAddr" value="${fuser.frealName }" readonly="readonly" autocomplete="off">
                          </div>
                      </div>      
                      <div class="login_item clear ">
                          <label for="withdrawAccountAddr" class="login_item_l"><spring:message code="financial.cnyrewithdrawal.account" /></label>
                          <div class="login_item_r">
                              <input type="text" id="withdrawAccountAddr" autocomplete="off" placeholder="<spring:message code="financial.cnyrewithdrawal.account" />">
                            </div>
                      </div>
                       <div class="login_item clear">
                          <label for="withdrawAccountAddr2" class="login_item_l"><spring:message code="financial.cnyrewithdrawal.confirmcard" /></label>
                          <div class="login_item_r">
                              <input type="" id="withdrawAccountAddr2" autocomplete="off" placeholder="<spring:message code="financial.cnyrewithdrawal.confirmcard" />">
                            </div>
                      </div>            
                                   
                      <div class="login_item clear">
                          <label for="openBankTypeAddr" class="login_item_l"><spring:message code="financial.cnyrewithdrawal.deposit" /></label>
                          <div class="login_item_r">
                              <div  class="form-control choiceBtn">
                                        <span class="selStyle selStyle4">
                                          <spring:message code="financial.cnyrewithdrawal.choosebank" />
                                        </span>
                               </div>


                          </div>
                      <select  id="openBankTypeAddr" style="display:none">
                        <option value="-1" selected><spring:message code="financial.cnyrewithdrawal.choosebank" /></option>
                      </select>
                      </div> 
                      <div class="login_item clear" id="prov_city">
                          <label for="" class="login_item_l"><spring:message code="financial.cnyrewithdrawal.addr" /></label>
                          <div class="login_item_r login_item_r2">
                             <select id="prov" class="form-control"></select>
                             <select id="city" class="form-control"></select>
                             <select id="dist" class="form-control prov"></select>
                          </div>                                    

                         <!--  <div class="pc-box login_item_r login_item_r2">                     
                              <input type="hidden" name="contact_province_code" data-id="0001" id="contact_province_code" value="" data-province-name="">
                              <input type="hidden" name="contact_city_code" id="contact_city_code" value="" data-city-name="">
                              <span data-city-code="" data-province-code="" data-district-code="" id="show_contact">请选择</span>                             
                          </div> 
 -->
                      </div>
                      <div class="login_item clear" >
                          <label for="" class="login_item_l"><spring:message code="m.security.bname" /></label>
                          <div class="login_item_r">
                              <input type="text" id="address" autocomplete="off" placeholder="<spring:message code="financial.cnyrewithdrawal.addrtip" />">
                          </div>
                      </div>
                      <c:if test="${isBindTelephone == true }">
                      <div class="login_item clear relative">
                          <label for="addressPhoneCode" class="login_item_l"><spring:message code="security.smscode" /></label>
                          <div class="login_item_r">
                              <input type="text" id="addressPhoneCode" autocomplete="off" placeholder="<spring:message code="security.smscode" />">
                              <button id="bindsendmessage" data-msgtype="10" data-tipsid="binderrortips"  class="sendCode btn-sendmsg" ><spring:message code="financial.send" /></button>
                          </div>
                          
                      </div>
                      </c:if> 
						

	              	<c:if test="${isBindGoogle ==true}">
	                  <div class="login_item clear">
	                      <label for="addressTotpCode" class="login_item_l"><spring:message code="financial.goocod" /></label>
	                      <div class="login_item_r">
	                          <input type="text" id="addressTotpCode" autocomplete="off" placeholder="<spring:message code="financial.goocod" />">
	                      </div>
	                  </div>
	               	</c:if>	
						
                    </div>              
                </div>                
            </div>
            <div class="care" style="padding:0.3rem; font-size:0.24rem;">
                <span class="cred">*<spring:message code="financial.cnyrewithdrawal.nametip" /></span>
            </div>
            <div class="addAdressCon">
            <button id="withdrawCnyAddrBtn" class="btn add_btn"><spring:message code="financial.cnyrewithdrawal.submit" /></button>
            </div>

    </div>

</section>

<div class="coinWarp1">
    <div class="coinLitBox coinLitBox2">
        
          <ul>
             <c:forEach items="${bankTypes }" var="v">            
                 <li data-value="${v.key }" id="key${v.key }" onclick="lwSelect(this.id,'openBankTypeAddr','choiceBtn')">${v.value }</li>
              </c:forEach>
          </ul>

        
    </div>
</div>



</div>


<%@include file="../comm/footer.jsp" %>	
	<script src="${oss_url}/static/mobile2018/js/fluckydraw/com.js?v=20181126201750"></script>
	<script type="text/javascript" src="${oss_url}/static/mobile2018/js/msg.js?v=1"></script>
	<script type="text/javascript" src="${oss_url}/static/mobile2018/js/account.assets.js?v=5"></script>
	<script type="text/javascript" src="${oss_url}/static/front/js/finance/city.min.js?v=20181126201750"></script>
	<script type="text/javascript" src="${oss_url}/static/front/js/finance/jquery.cityselect.js?v=20181126201750"></script>
	<script type="text/javascript" src="${oss_url}/static/front/js/index/main.js?v=20181126201750"></script>
	<script type="text/javascript">
    $(".choiceMore").click(function(event) {
        $(".coinWarp").animate({bottom:"0"}, 200)
    });
    $(".coinWarp ul li").click(function(event) {
        var text=$(this).html();
        $(this).addClass('active').siblings().removeClass('active')
        $(".choiceMore").html(text)
        $(".coinWarp").css('bottom', '-100%');
    });
    $(".coinWarp").click(function(event) {
        $(this).css('bottom', '-100%');
    });

    function showaddbank(){
    	$("#topUp1").css('display','none');
    	$("#topUp2").css('display','block');

    }
    $('.backIcon2').click(function(event){
    	$("#topUp2").css('display','none');
    	$("#topUp1").css('display','block');
    });
    $('.backIcon1').click(function(event){
    	window.location.href = "/user/security.html";
    });

// 选择银行卡
    function lwSelect(id,selectid,cho){
        var text = $("#"+id).html();
        var _val = $("#"+id).data("value");
        $(".choiceBtn span").html(text);
        $(".coinWarp").css('bottom', '-100%');
        $("#" + selectid + " option:selected").val(_val);
        $("#" + selectid + " option:selected").html(text); 

    }

    $(".choiceBtn").click(function(event) {
        $(".coinWarp1").animate({bottom:"0"}, 200)
    });
    // $(".coinLitBox2 ul li").click({id: "openBankTypeAddr"},function(event) {
        // id = event.data.id;
        // var text=$(this).html();
        // var _val = $(this).data("value");
        // $(this).addClass('active').siblings().removeClass('active')
        // $(".choiceBtn span").html(text);
        // $(".coinWarp").css('bottom', '-100%');
        // $("#" + id + " option:selected").val(_val);
        // $("#" + id + " option:selected").html(text);        
    // });
    $(".coinWarp1").click(function(event) {
        $(this).css('bottom', '-100%');
    });

</script>

<script type="text/javascript">
    // var selectContactDom = $('#prov_city');
    // var showContactDom = $('#show_contact');
    // var contactProvinceCodeDom = $('#contact_province_code');
    // var contactCityCodeDom = $('#contact_city_code');
    // selectContactDom.bind('click', function () {
    //     var sccode = showContactDom.attr('data-city-code');
    //     var scname = showContactDom.attr('data-city-name');

    //     var oneLevelId = showContactDom.attr('prov');
    //     var twoLevelId = showContactDom.attr('city');
    //     var threeLevelId = showContactDom.attr('dist');
    //     var iosSelect = new IosSelect(3, 
    //         [iosProvinces, iosCitys, iosCountys],
    //         {
    //             title: '地址选择',
    //             itemHeight: 35,
    //             relation: [1, 1],
    //             oneLevelId: oneLevelId,
    //             twoLevelId: twoLevelId,
    //             threeLevelId: threeLevelId,
    //             callback: function (selectOneObj, selectTwoObj, selectThreeObj) {
    //                 contactProvinceCodeDom.val(selectOneObj.id); 
    //                 contactProvinceCodeDom.attr('data-province-name', selectOneObj.value);
    //                 contactCityCodeDom.val(selectTwoObj.id);
    //                 contactCityCodeDom.attr('data-city-name', selectTwoObj.value);

    //                 showContactDom.attr('data-province-code', selectOneObj.id);
    //                 showContactDom.attr('data-city-code', selectTwoObj.id);
    //                 showContactDom.attr('data-district-code', selectThreeObj.id);
    //                 showContactDom.html(selectOneObj.value + ' ' + selectTwoObj.value + ' ' + selectThreeObj.value);
    //             }
    //     });
    // });
</script>



</body>
</html>
