<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ include file="../comm/include.inc.jsp"%>

<!doctype html>
<html>
<head> 
<%@ include file="../comm/basepath.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="../comm/link.inc.jsp" %>

</head>
<style type="text/css">
  html,body{width:100%; min-width:100%; height:auto;}
  .area{background: #FAFAFA;padding: 0 32px 30px 32px;}
  .care{background:#fff; padding:20px;}
  canvas{width:100px; height:100px;}
.areah2{clear: both;overflow: hidden;height: 60px;line-height: 60px;}
.qrcodedl{display: block; text-align: center;}
.unie62b{color:#333333;}
.unie62b img{width: 14px;margin-right: 8px;vertical-align: -1px;}
.add {
    color: #6666CC;
    text-align: right;
    cursor: pointer;
}
.fee_txt{
	width: 81%;
    text-align: center;
    color: #FF6633;
    font-size: 14px;
    line-height: 30px;
}
.tr_txt{width:646px;}
#withdrawAmount,#withdrawAddr{    height: 48px;}
.notarizeCen{ 
    text-align: center;
    width: 646px;
    line-height: 60px;
    height: 60px;
    margin: 0 auto;
    margin-top: 40px;
}
.notarizeCen button{
    border: 1px solid #FF6633;
    width: 100px;
    height: 40px;
    font-size: 16px;
    display: inline-block;
    background: #FF5E3A;
    color: #fff;
    border-radius: 4px;
    margin-right: 40px;
    cursor: pointer;
}
.notarizeCen .btn_close{
    background: #fff;
    color: #6666CC;
}

</style>
<body>
	<input type="hidden" id="max_double" value="${fvirtualcointype.fmaxqty }">
	<input type="hidden" id="min_double" value="${fvirtualcointype.fminqty }">

  <div class="charge_address_02" style="display: block;">
    <%-- <h2 class="assetTitle clear">
        <spring:message code="new.recharge" />
        <a class="fr cred exchange_btns" href="javascript:;" class="s_btn exchange_btn ">
          <spring:message code="new.coin.records" />  
          <svg class="icon sfont16" aria-hidden="true">
            <use xlink:href="#icon-youjiantouda"></use>
          </svg>
        </a> 
    </h2> --%>
    <div class="area">
    	<h2 class="areah2">
    		<span style=" font-size: 18px; color: #333333;float:left; padding-right: 10px;"><spring:message code="new.recharge" /></span>
    		<a href="javascript:;" class="fl cred exchange_btns">
	          <spring:message code="new.coin.records" />  
	          <svg class="icon sfont16" aria-hidden="true">
	            <use xlink:href="#icon-youjiantouda"></use>
	          </svg>
	      	</a>
    	</h2>
      <div class="tr_txt">
        <span class="tr_l">
          <spring:message code="new.recharge" />  
        </span>
        <select class="inpBox" name="" id="withdrawAddr" >
        <c:forEach items="${fvirtualaddressWithdraws }" var="v">
            <c:if test="${fvirtualcointype.fisBts==true }">
              <option value="${v.fid }">Account:${v.fadderess } | Memo:${v.fremark}</option>
            </c:if>
            <c:if test="${fvirtualcointype.fisBts!=true }">
              <option value="${v.fid }">${v.fremark}-${v.fadderess }</option>
            </c:if>
        </c:forEach>
        </select>
       <!-- 新增提币地址 -->
        <p class="add"><spring:message code="新增提币地址>" /></p>
      </div>
      <div class="tr_txt" style=" margin-bottom: 0;">
        <span class="tr_l"><spring:message code="financial.witamo" /></span>
        <input type="text" class="inpBox" autocomplete="off" value=""  id="withdrawAmount"/>  
      </div>
      <%-- <div class="tr_txt">
        <span class="tr_l"><spring:message code="financial.usdtrewithdrawal.fee" /></span>
        <span id="free">0</span> <span style="width:300px;display: inline-block;">${fvirtualcointype.fShortName }</span>
        <select class="inpBox" name="" id="btcfee"  style="display:none;">
        <c:forEach items="${withdrawFees }" var="v">
          <c:if test="${v.flevel ==5 }">
            <option value="${v.flevel}" selected="selected"><ex:DoubleCut value="${v.ffee}" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/></option>
          </c:if>
          <c:if test="${v.flevel !=5 }">
            <option value="${v.flevel}"><ex:DoubleCut value="${v.ffee}" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/></option>
          </c:if>
        </c:forEach>
        </select>  
      </div> --%>
      <div class="fee_txt">
	      <spring:message code="financial.usdtrewithdrawal.fee" />
	      <span id="free">0</span> 
	      <span>${fvirtualcointype.fShortName }</span>
	       <select class="inpBox" name="" id="btcfee"  style="display:none;">
        <c:forEach items="${withdrawFees }" var="v">
          <c:if test="${v.flevel ==5 }">
            <option value="${v.flevel}" selected="selected"><ex:DoubleCut value="${v.ffee}" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/></option>
          </c:if>
          <c:if test="${v.flevel !=5 }">
            <option value="${v.flevel}"><ex:DoubleCut value="${v.ffee}" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/></option>
          </c:if>
        </c:forEach>
        </select> 
	      &nbsp;&nbsp;&nbsp;&nbsp;
	      <spring:message code="financial.usdtrewithdrawal.arr" />
	      <span id="amount">0</span><span>${fvirtualcointype.fShortName }</span>
      </div>
      <%-- <div class="tr_txt">
        <span class="tr_l"><spring:message code="financial.usdtrewithdrawal.arr" /></span>
        <span id="amount" class="text-danger">0</span> <span class="text-danger" style="width:300px;display: inline-block;">${fvirtualcointype.fShortName }</span>
      </div> --%>
      <div class="tr_txt">
        <span class="tr_l"><spring:message code="financial.tradingpwd" /></span>
        <input type="password" class="inpBox"  id="tradePwd" autocomplete="off" value=""/>  
      </div>
      <c:if test="${isBindTelephone == true }">   
      <div class="tr_txt">
        <span class="tr_l"><spring:message code="security.smscode" /></span>
        <input type="text" class="inpBox"  id="withdrawPhoneCode" autocomplete="off"/> 
        <button id="withdrawsendmessage" data-msgtype="5" data-tipsid="withdrawerrortips" class="btn sendCode"style="font-size:14px; background:#ddd;"><spring:message code="financial.send" /></button> 
      </div>
      </c:if>
      <c:if test="${isBindGoogle ==true}">      
      <div class="tr_txt">
        <span class="tr_l"><spring:message code="financial.goocod" /></span>
        <input type="text" class="inpBox" id="withdrawTotpCode"  value="" autocomplete="off"/>  
      </div>
      </c:if>
      <!-- 添加人机验证 -无痕验证 -->
	  <div class="tr_txt" id="nvc_validate"></div>
       <div class="tr_txt">
            <span class="tr_l tr_ts"></span>
            <span id="withdrawerrortips" class="cred"></span>        
      </div>
      <div class="tr_txt">
        <span class="tr_l"></span>
        <input class="btn sub" type="button" id="withdrawBtcButton" value="<spring:message code="financial.immwit" />"/>
      </div>
<!--       <p class="mgt50">
        
      </p> -->
      <div class="care" style="line-height: 30px;color: #99999A;">
	    <h3 class="unie62b"><img src="${oss_url}/static/front2018/images/unie62b.png"><spring:message code="new.tips" /></h3>
	      <c:if test="${fvirtualcointype.fisBts==true }">
	     	<p>${fvirtualcointype.fname }<spring:message code="newmobile.please" /></p>
	      </c:if>
	      <p class="txt">
	        <spring:message code="financial.aftyoufill"  arguments="${fvirtualcointype.fname }" />
	        <br/>  
	        <spring:message code="financial.thsminiamo"  arguments="${fvirtualcointype.fconfirm},${fvirtualcointype.fpushMinQty},${fvirtualcointype.fShortName }" />  
	      </p>
     </div>
    </div>
    <span class="s_close" id='s_close' onclick='del2()'>×</span>
  </div>
	<input type="hidden" id="isEmptyAuth" value="${isEmptyAuth }">
	<input type="hidden" id="symbol" value="${fvirtualcointype.fid }">
	<input type="hidden" value="<ex:DoubleCut value="${fvirtualwallet.ftotal }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/>" id="btcbalance" name="btcbalance">
	<input type="hidden" value="${fvirtualcointype.fShortName }" id="coinName" name="coinName">
	<input id="feesRate" type="hidden" value="0.001">
	<input id="withdrawFeeMini" type="hidden" value="${withdrawFeeMini }">
 <script type="text/javascript" src="${oss_url}/static/front2018/js/plugin/layer/layer.js?v=20171025221650.js"></script>
 <script type="text/javascript" src="${oss_url}/static/front2018/js/comm/util.js?v=20171025221650.js"></script>
<script type="text/javascript" src="${oss_url}/static/front2018/js/comm/comm.js?v=20171025221650.js"></script>

  <script type="text/javascript" src="${oss_url}/static/front2018/js/language/language_<spring:message code="language.title" />.js?v=20171025221650.js"></script>
	<script type="text/javascript" src="${oss_url}/static/front2018/js/comm/msg.js?v=20181126201750"></script>
  <script type="text/javascript" src="${oss_url}/static/front2018/js/finance/account.withdraw.js?v=20181126201751"></script>
  <script type="text/javascript">
          $(".btnHover").hover(function() {
              $(this).css('color', '#fff');
          }, function() {
              $(this).css('color', '#fff');
          });
          $(".add").click(function(){
               var symbol = $("#symbol").val();
               parent.location.href="/financial/accountcoin.html?symbol="+ symbol;
          });
          $(".exchange_btns").click(function(){
               var symbol = $("#symbol").val();
               parent.location.href="/account/record.html?recordType=4&symbol="+ symbol +"&datetype=2";
          });



  </script>
<script type="text/javascript"> 
    function del2(){
        window.parent.document.getElementById("w_btc").remove();
    }
</script>
<script>
	var appkey = "FFFF00000000017F11B9";
	var scene = "nvc_other";
    window.NVC_Opt = {
        //无痕配置 && 滑动验证、刮刮卡通用配置
        appkey:appkey,
        scene:scene,
        isH5:false,
        popUp:false,
        renderTo:'#nvc_validate',
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
</body>
</html>
