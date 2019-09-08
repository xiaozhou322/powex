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
<%@include file="../comm/link.inc.jsp" %>

</head>
<style type="text/css">
    html,body{width:100%; min-width:100%;height:auto;}
    .area{background:#eee;}
    .care{background:#fff; padding:20px;}
</style>
<body>
	<input type="hidden" id="max_double" value="${fvirtualcointype.fmaxqty }">
	<input type="hidden" id="min_double" value="${fvirtualcointype.fminqty }">

  <div class="charge_address charge_address_02" style="display: block;">
    <h2 class="assetTitle clear">
        <spring:message code="new.recharge" />
        <a class="fr cred exchange_btns" href="javascript:;" class="s_btn exchange_btn ">
          <spring:message code="new.coin.records" />  
          <svg class="icon sfont16" aria-hidden="true">
            <use xlink:href="#icon-youjiantouda"></use>
          </svg>
        </a> 
    </h2>
    <div class="area">
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
        <a href="javascript:;" class="add">+<spring:message code="financial.toadd" /></a>
      </div>
      <div class="tr_txt">
        <span class="tr_l"><spring:message code="financial.witamo" /></span>
        <input type="text" class="inpBox" autocomplete="off" value=""  id="withdrawAmount"/>  
      </div>
      <div class="tr_txt">
        <span class="tr_l">${fvirtualcointype.fShortName } <spring:message code="financial.netfee" /></span>
        <select class="inpBox" name="" id="btcfee" >
        <c:forEach items="${withdrawFees }" var="v">
          <c:if test="${v.flevel ==5 }">
            <option value="${v.flevel}" selected="selected"><ex:DoubleCut value="${v.ffee}" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/></option>
          </c:if>
          <c:if test="${v.flevel !=5 }">
            <option value="${v.flevel}"><ex:DoubleCut value="${v.ffee}" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/></option>
          </c:if>
        </c:forEach>
        </select>  
      </div>
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
      <div class="care">

        <p class="txt">
          <spring:message code="financial.thsmininum"   arguments="${fvirtualcointype.fminqty },${fvirtualcointype.fmaxqty }" />  
          <br />  
          <spring:message code="financial.witins.note" />  
        </p>
      </div>
    </div>
    <span class="s_close" id='s_close' onclick='del2()'>×</span>
  </div>
	<input type="hidden" id="isEmptyAuth" value="${isEmptyAuth }">
	<input type="hidden" id="symbol" value="${fvirtualcointype.fid }">
	<input type="hidden" value="<ex:DoubleCut value="${fvirtualwallet.ftotal }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/>" id="btcbalance" name="btcbalance">
	<input type="hidden" value="${fvirtualcointype.fShortName }" id="coinName" name="coinName">

 <script type="text/javascript" src="${oss_url}/static/front2018/js/plugin/layer/layer.js?v=20171025221650.js?v=20181126201750"></script>
 <script type="text/javascript" src="${oss_url}/static/front2018/js/comm/util.js?v=20171025221650.js?v=20181126201750"></script>
<script type="text/javascript" src="${oss_url}/static/front2018/js/comm/comm.js?v=20171025221650.js?v=20181126201750"></script>

  <script type="text/javascript" src="${oss_url}/static/front2018/js/language/language_<spring:message code="language.title" />.js?v=20171025221650.js?v=20181126201750"></script>
	<script type="text/javascript" src="${oss_url}/static/front2018/js/comm/msg.js?v=20181126201750"></script>
  <script type="text/javascript" src="${oss_url}/static/front2018/js/finance/account.withdraw.js?v=20181126201750"></script>
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
</body>
</html>
