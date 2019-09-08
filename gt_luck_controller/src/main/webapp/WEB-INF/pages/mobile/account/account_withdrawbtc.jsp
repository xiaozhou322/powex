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
<meta name = "viewport" content = "width = device-width, user-scalable = no,initial-scale=0.5,maximum-scale=0.5,minimum-scale=0.5">

<%@include file="../comm/link.inc.jsp" %>
<style type="text/css">

</style>
</head>
<body>
	<input type="hidden" id="max_double" value="${fvirtualcointype.fmaxqty }">
	<input type="hidden" id="min_double" value="${fvirtualcointype.fminqty }">

 <%@include file="../comm/header.jsp" %>
<div class="recharge withdraw" id="topUp1">
    <header class="tradeTop">
        <span class="back toback2"></span>
        <span>${fvirtualcointype.fShortName } <spring:message code="new.withdraw" /></span>
    </header>
    <div class="withdrawMain">
        <div class="withdraw_tit">
            <h1><spring:message code="market.usable" /><span class='cgreen userCoin'><ex:DoubleCut value="${fvirtualwallet.ftotal }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/> ${fvirtualcointype.fShortName }</span></h1>
        </div>
        <div class="withdraw_tr">
            <div class="item_tr clear">
                <span class="fl tr_l"><spring:message code="financial.witadd" /></span>
                 <c:if test="${fn:length(fvirtualaddressWithdraws)==0 }">
                 <span><spring:message code="m.security.notxaddress" /></span> 
                </c:if>
                 <c:if test="${fn:length(fvirtualaddressWithdraws)!=0 }"> 
                     <c:forEach items="${fvirtualaddressWithdraws }" var="v" varStatus="vs">
                      <c:if test="${0 eq  vs.index}">
                        <span class="fl address_txt" onclick="showtabs('withdrawAddr_box')" id="withdrawAddrs">${v.fremark}${v.fadderess }</span>
                      </c:if>
                      </c:forEach> 
                 </c:if> 
                <!-- <em class='add_adr' onclick="showTabar();"><spring:message code="m.security.addto" /></em> -->
                <em class='add_adr cblue2' onclick="showTabar();">
                     <i class="iconfont fl icon-shouhuodizhiyebianji"></i>
                </em>

            </div>
             <select class="fl txtArea selectArea select select9 sl1" name="" id="withdrawAddr" style="display:none;">
                        <c:forEach items="${fvirtualaddressWithdraws }" var="v">
                        <c:if test="${fvirtualcointype.fisBts==true }">
                        <option value="${v.fid }">Account:${v.fadderess } | Memo:${v.fremark}</option>
                        </c:if>
                        <c:if test="${fvirtualcointype.fisBts!=true }">
                        <option value="${v.fid }">${v.fremark}-${v.fadderess }</option>
                        </c:if>
                        </c:forEach>
            </select>           
            <div class="item_tr clear">
                <span class='fl tr_l'><spring:message code="financial.witamo" /></span>
                <input type="text" class="fl txt" id="withdrawAmount" autocomplete="off" placeholder="<spring:message code="financial.witamo" />" />
            </div>            
            <div class="item_tr clear">
                <span class='fl'><spring:message code="m.security.fee" /></span>&nbsp;
                 <c:forEach items="${withdrawFees }" var="v" varStatus="vs">
                 <c:if test="${0 eq  vs.index}"> 
                <p class='clear address_txt'onclick="showtabs('btcfee_box')" id="btcfees"><ex:DoubleCut value="${v.ffee}" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/></p>
                </c:if>
                </c:forEach>
            </div>
             <select class="select select7 sl1" name="" id="btcfee" style="display:none;">
                <c:forEach items="${withdrawFees }" var="v">
                  <c:if test="${v.flevel ==5 }">
                    <option value="${v.flevel}" selected="selected"><ex:DoubleCut value="${v.ffee}" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/></option>
                  </c:if>
                 <c:if test="${v.flevel !=5 }">
                <option value="${v.flevel}"><ex:DoubleCut value="${v.ffee}" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/></option>
                  </c:if>
                </c:forEach>
                </select>             
            <div class="item_tr clear">
                <span class='fl tr_l'><spring:message code="financial.tradingpwd" /></span>
                <input type="password" class="fl txt" id="tradePwd" placeholder="<spring:message code="financial.tradingpwd" />" />
            </div>            
             <c:if test="${isBindTelephone == true }">
            <div class="item_tr clear">
                <span class='fl tr_l'><spring:message code="security.smscode" /></span>
                <div class="login_item_r">
                    <input type="text" class="fl txt" id="withdrawPhoneCode" autocomplete="off" placeholder="<spring:message code="security.smscode" />" />
                    <span class="sendCode btn-sendmsg" id="withdrawsendmessage" data-msgtype="5" data-tipsid="withdrawerrortips"><spring:message code="financial.send" /></span>
                </div>  
            </div>
            </c:if>

                <c:choose>
                <c:when test="${isBindGoogle ==true}">      
                  <div class="item_tr clear">
                      <label for="" class="login_item_l fl"><spring:message code="financial.goocod" /></label>
                      <div class="login_item_r fl">
                          <input type="text" id="withdrawTotpCode" autocomplete="off" placeholder="<spring:message code="financial.goocod" />">
                      </div>
                  </div>
                  </c:when>
             <c:otherwise>
             <c:if test="${isBindTelephone == false}">
                  <div class="item_tr clear">
                      <label for="" class="login_item_l fl"><spring:message code="financial.goocod" /></label>
                      <div class="login_item_r fl">
                           <a href="/user/security.html?tab=4" style="height:36px; line-height:40px; display:block;"><spring:message code="financial.checkgoogle" /></a>
                      </div>
                  </div>
             </c:if>
             </c:otherwise>
             </c:choose>
        </div>
    </div>
      <label for="diyMoney" style="margin:0 0 0 0;">
        <span id="withdrawerrortips" class="text-danger"  style="margin:0 0 0 170px; display:block;">
                      
      </span>      
    <div class="remind mgbt">
        <spring:message code="financial.witins" /><br />
        <spring:message code="financial.thsmininum"   arguments="${fvirtualcointype.fminqty },${fvirtualcointype.fmaxqty }" /><br />
        <spring:message code="financial.witins.note" /><br />
    </div>
    <div class="withdraw_footer">
        <button class="btn tb_btn" id="withdrawBtcButton"><spring:message code="new.withdraw" /></button>
    </div>
</div>


<div class="recharge withdraw" id="topUp2" style="display: none">
    <header class="tradeTop">
        <span class="toback toback2" onclick="hideTabar();"></span>

        <span><spring:message code="m.security.addto" /> ${fvirtualcointype.fShortName } <spring:message code="m.security.toaddress" /></span>

    </header>
    <div class="addAdressCon">
     
            <div class="withdrawalList">
                  <div class="login_item clear">
                      <label for="" class="login_item_l fl"><spring:message code="financial.witadd" /></label>
                      <div class="login_item_r fl">
                          <input type="text" id="withdrawBtcAddr"  autocomplete="off" placeholder="<spring:message code="financial.witadd" />">
                      </div>
                  </div>      
                  <div class="login_item clear">
                      <label for="" class="login_item_l fl">
                      <c:if test="${fvirtualcointype.fisBts!=true }"><spring:message code="financial.remarks" /></c:if>
                      <c:if test="${fvirtualcointype.fisBts==true }">Memo</c:if></label>
                      <div class="login_item_r fl">
                          <input type="text" id="withdrawBtcRemark" autocomplete="off" placeholder="<spring:message code="financial.remarks" />">
                      </div>
                  </div> 
                  <c:if test="${fvirtualcointype.fisBts==true }">
                  <div class="login_item clear">
                      <label for="withdrawBtcRemark" class="login_item_l fl"> </label>
                      <div class="login_item_r" style="width:100%">
                          <span id="binderrortips" class="text-danger">${fvirtualcointype.fShortName }<spring:message code="newmobile.memo" /></span>
                      </div>
                  </div> 
                  </c:if>         
                  <div class="login_item clear">
                      <label for="" class="login_item_l fl"><spring:message code="financial.tradingpwd" /></label>
                      <div class="login_item_r fl">
                          <input id="withdrawBtcPass" type="password" autocomplete="off" placeholder="<spring:message code="financial.tradingpwd" />">
                      </div>
                  </div>
                  <c:if test="${isBindTelephone == true }"> 
                  <div class="login_item clear relative">
                      <label for="" class="login_item_l fl"><spring:message code="security.smscode" /></label>
                      <div class="login_item_r fl">
                          <input type="text" id="withdrawBtcAddrPhoneCode" autocomplete="off" placeholder="<spring:message code="security.smscode" />">
                          <span class="sendCode btn-sendmsg" id="bindsendmessage" data-msgtype="8" data-tipsid="binderrortips"><spring:message code="financial.send" /></span>
                      </div>
                      
                  </div>
                  </c:if>
                   <c:choose> 
                  <c:when test="${isBindGoogle == true }">

                  <div class="login_item clear">
                      <label for="" class="login_item_l fl"><spring:message code="financial.goocod" /></label>
                      <div class="login_item_r fl">
                          <input type="text" id="withdrawBtcAddrTotpCode" autocomplete="off" placeholder="<spring:message code="financial.goocod" />">
                      </div>
                  </div>
                    </c:when>
                 <c:otherwise>
                 <c:if test="${isBindTelephone == false }">
                 <div class="login_item clear">
                      <label for="" class="login_item_l fl"><spring:message code="financial.goocod" /></label>
                      <div class="login_item_r fl">
                           <a href="/user/security.html?tab=4" style="height:36px; line-height:40px; display:block;"><spring:message code="financial.checkgoogle" /></a>
                      </div>
                  </div>
                      </c:if>
                </c:otherwise>
              </c:choose>
            </div> 
            <button class="btn add_btn" id="withdrawBtcAddrBtn"><spring:message code="financial.submit" /></button>
       
    </div>
</div>
	<div class="slet"></div>
	<input type="hidden" id="isEmptyAuth" value="${isEmptyAuth }">
	<input type="hidden" id="symbol" value="${fvirtualcointype.fid }">
	<input type="hidden" value="<ex:DoubleCut value="${fvirtualwallet.ftotal }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/>" id="btcbalance" name="btcbalance">
	<input type="hidden" value="${fvirtualcointype.fShortName }" id="coinName" name="coinName">



<%@include file="../comm/footer.jsp" %>	
  <script src="${oss_url}/static/mobile2018/js/fluckydraw/com.js"></script>
  <script type="text/javascript" src="${oss_url}/static/mobile2018/js/msg.js?v=3"></script>
  <script type="text/javascript" src="${oss_url}/static/mobile2018/js/account.withdraw.js?v=2"></script>

</body>
</html>
