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
 <meta name = "viewport" content = "width = device-width, user-scalable = no,initial-scale=0.5,maximum-scale=0.5,minimum-scale=0.5">
<%@include file="../comm/link.inc.jsp" %>
	<title><spring:message code="financial.capacc" /></title>
	<style type="text/css">
		   .sendcode{width: 32%;} 
		   .address_tr{box-shadow: 0px 2px 9px 0px rgba(70, 90, 111, 0.14);}
	</style>
</head>
<body>
  <%@include file="../comm/header.jsp" %>
  <input type="hidden" id="max_double" value="${fvirtualcointype.fmaxqty }">
  <input type="hidden" id="min_double" value="${fvirtualcointype.fminqty }">
  

  <div id="top1" class="entrust_recorde">
    <header class="tradeTop">
         <span class="back toback2"></span> 
        <span class="tit"><spring:message code="new.recharge" /></span>
    </header>
    <section>
        <div class="coinAllCon">
             <div class="coinAll coin_list">
                 <ul>  
                 <!--  <li><a href="/financial/accountbank.html">USDT</a></li>  --> 
                   <c:forEach items="${requestScope.constant['allWithdrawCoins'] }" var="v">            
                     <li><a href="/financial/accountcoin.html?symbol=${v.fid }&tab=2"> ${v.fShortName }</a></li>
                     </c:forEach>                                              
                 </ul>
             </div>
        </div>
    </section>
    </div>

<!-- <i class='listIcon' style="background: url(${v.fvirtualcointypeByFvirtualcointype2.furl }) no-repeat 0 center;background-size:85% auto; "> -->

  <div id="top2" style="display: none;">
  <div class="recharge withdraw" >
    <header class="tradeTop">
        <span class="back toback2"></span>
        <c:forEach items="${requestScope.constant['allWithdrawCoins'] }" var="v">
        <c:if test="${v.fid==symbol}">
        <span>${v.fShortName } <spring:message code="m.security.toaddress" /></span>
        </c:if>
        </c:forEach>
    </header>

    <div class="address_list">
    <c:forEach items="${alls }" var="v">
         <div class="address_tr clear">
              <span style="z-index: 910;" class="coin-item-code fl">
              <span class="addresscode" data-text="${v.fadderess }" data-fid="${v.fid }" style="display:block; margin-top:15px">
              <span class="qrcode" id="qrcode${v.fid }"></span>
              </span>
              </span>
             <p class="fl"><em>${v.fadderess } </em><span class='cgreen note'>${v.fremark }</span></p>
             <em class="delete_btn coin-item-del" data-fid="${v.fid }"><spring:message code="security.delete" /></em>
         </div>
           </c:forEach>
    </div>
   
      <c:if test="${fn:length(alls)==0 }">
           <div class="textCenter mtop">
                <img src="${oss_url}/static/mobile2018/css/agent/images/noMsg.png" width="86" alt="" />
                <p><spring:message code="json.jingji.noord" /></p>
           </div>
        </c:if>    
    <div class="withdraw_footer">
        <button class="btn tb_btn showtop3"><spring:message code="financial.addanadd" /></button>
    </div>
</div>
</div>

<div class="recharge withdraw" style="display: none;" id="top3">
    <header class="tradeTop">
        <span class="soback toback2" ></span>
         <c:forEach items="${requestScope.constant['allWithdrawCoins'] }" var="v">
        <c:if test="${v.fid==symbol}">
        <span><spring:message code="m.security.addto" /> ${v.fShortName } <spring:message code="m.security.toaddress" /></span>
        </c:if>
        </c:forEach>
    </header>
    <div class="addAdressCon">

            <div class="withdrawalList">
                  <div class="login_item clear">
                      <label for="" class="login_item_l fl"><spring:message code="financial.witadd" /></label>
                      <div class="login_item_r fl">
                          <input type="text" id="withdrawBtcAddr" autocomplete="off" placeholder="<spring:message code="financial.witadd" />">
                      </div>
                  </div>      
                  <div class="login_item clear">
                      <label for="" class="login_item_l fl"><spring:message code="financial.remarks" /></label>
                      <div class="login_item_r fl">
                          <input type="text" id="withdrawBtcRemark" autocomplete="off" placeholder="<spring:message code="financial.remarks" />">
                      </div>
                  </div>      
                  <div class="login_item clear">
                      <label for="" class="login_item_l fl"><spring:message code="financial.tradingpwd" /></label>
                      <div class="login_item_r fl">
                          <input onfocus="this.type='password'" id="withdrawBtcPass" autocomplete="off" placeholder="<spring:message code="financial.tradingpwd" />">
                      </div>
                  </div>
                  <c:if test="${isBindTelephone == true }">  
                  <div class="login_item clear relative">
                      <label for="" class="login_item_l fl"><spring:message code="financial.smscode" /></label>
                      <div class="login_item_r fl">
                          <input type="text" id="withdrawBtcAddrPhoneCode" autocomplete="off" placeholder="<spring:message code="financial.smscode" />">
                          <span id="bindsendmessage" data-msgtype="8" data-tipsid="binderrortips" class="sendCode btn-sendmsg"><spring:message code="financial.send" /></span>
                      </div>
                      
                  </div>
                  </c:if>
                  <c:choose>
                <c:when test="${isBindGoogle ==true}">      
                  <div class="login_item clear">
                      <label for="" class="login_item_l fl"><spring:message code="financial.goocod" /></label>
                      <div class="login_item_r fl">
                          <input type="text" id="withdrawBtcAddrTotpCode" autocomplete="off" placeholder="<spring:message code="financial.goocod" />">
                      </div>
                  </div>
                  </c:when>
             <c:otherwise>
             <c:if test="${isBindTelephone == fales }">
                  <div class="login_item clear">
                      <label for="" class="login_item_l fl"><spring:message code="financial.goocod" /></label>
                      <div class="login_item_r fl">
                           <a href="/user/security.html?tab=4" style="height:36px; line-height:40px; display:block;"><spring:message code="financial.checkgoogle" /></a>
                      </div>
                  </div>
             </c:if>
             </c:otherwise>
             </c:choose>
             <!-- 添加人机验证 -无痕验证 -->
			 <div class="login_item clear" id="nvc_validate"></div>
            </div>
              <div class="form-group" >
            <label for="diyMoney" class="col-xs-3 control-label" style="padding-right:0; width:30%;"></label>
            <div class="col-xs-8" style="width:60%;">
              <span id="binderrortips" class="text-danger"></span>
            </div>
          </div> 
            <button class="btn add_btn" id="withdrawBtcAddrBtn"><spring:message code="financial.submit" /></button>
       
    </div>
</div>


<%@include file="../comm/footer.jsp" %>	

	<input type="hidden" id="symbol" value="${symbol }">
	<input type="hidden" value="${coinName }" id="coinName" name="coinName">
	<script src="${oss_url}/static/mobile2018/js/fluckydraw/com.js?v=20181126201750"></script>
	<script type="text/javascript" src="${oss_url}/static/mobile2018/js/msg.js?v=3"></script>
	<script type="text/javascript" src="${oss_url}/static/mobile2018/js/plugin/jquery.qrcode.min.js?v=20181126201750"></script>
	<script type="text/javascript" src="${oss_url}/static/mobile2018/js/language/language_<spring:message code="language.title" />.js?v=20171025221650.js"></script>
	<script type="text/javascript" src="${oss_url}/static/mobile2018/js/account.assets.js?v=3"></script>

   
    </body>
    <script type="text/javascript">
      $("#addaddress").click(function(){

      });

       $(document).ready(function(){
            var top = window.location.href;
            var nums = top.indexOf("tab=");
            var tab = top.substr(nums+4, 3);
            if(tab == 2){
              $("#top1").css('display','none');
              $("#top2").css('display','block');
            }
    });

      $(".soback").click(function(){
          $("#top2").css('display','block');
          $("#top3").css('display','none');
      });
      
      $(".showtop3").click(function(){
        $("#top2").css('display','none');
          $("#top3").css('display','block');
      });
    </script>
    
    <script>
	var appkey = "FFFF00000000017F11B9";
	var scene = "nvc_other_h5";
	window.NVC_Opt = {
        //无痕配置 && 滑动验证、刮刮卡通用配置
        appkey:appkey,
        scene:scene,
        isH5:true,
        popUp:false,
        renderTo:'#nvc_validate',
        nvcCallback:function(data){
            // data为getNVCVal()的值，此函数为二次验证滑动或者刮刮卡通过后的回调函数
            // data跟业务请求一起上传，由后端请求AnalyzeNvc接口，接口会返回100或者900
        },
        trans: {"key1": "code0","nvcCode":400},
        language: "cn",
        //滑动验证长度配置
        customWidth: 300,
        //刮刮卡配置项
        width: 300,
        height: 100,
        elements: [
            '//img.alicdn.com/tfs/TB17cwllsLJ8KJjy0FnXXcFDpXa-50-74.png',
            '//img.alicdn.com/tfs/TB17cwllsLJ8KJjy0FnXXcFDpXa-50-74.png'
        ], 
        bg_back_prepared: '//img.alicdn.com/tps/TB1skE5SFXXXXb3XXXXXXXXXXXX-100-80.png',
        bg_front: 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAGQAAABQCAMAAADY1yDdAAAABGdBTUEAALGPC/xhBQAAAAFzUkdCAK7OHOkAAAADUExURefk5w+ruswAAAAfSURBVFjD7cExAQAAAMKg9U9tCU+gAAAAAAAAAIC3AR+QAAFPlUGoAAAAAElFTkSuQmCC',
        obj_ok: '//img.alicdn.com/tfs/TB1rmyTltfJ8KJjy0FeXXXKEXXa-50-74.png',
        bg_back_pass: '//img.alicdn.com/tfs/TB1KDxCSVXXXXasXFXXXXXXXXXX-100-80.png',
        obj_error: '//img.alicdn.com/tfs/TB1q9yTltfJ8KJjy0FeXXXKEXXa-50-74.png',
        bg_back_fail: '//img.alicdn.com/tfs/TB1w2oOSFXXXXb4XpXXXXXXXXXX-100-80.png',
        upLang:{"cn":{
        	_ggk_guide: language["aliyun.nvc.ggk_guide"],
            _ggk_success: language["aliyun.nvc.ggk_success"],
            _ggk_loading: language["aliyun.nvc.ggk_loading"],
            _ggk_fail: language["aliyun.nvc.ggk_fail"],
            _ggk_action_timeout: language["aliyun.nvc.ggk_action_timeout"],
            _ggk_net_err: language["aliyun.nvc.ggk_net_err"],
            _ggk_too_fast: language["aliyun.nvc.ggk_too_fast"]
            }
        }
    }
    
</script>
<script src="//g.alicdn.com/sd/nvc/1.1.112/guide.js?v=20181126201750"></script>
</html>
