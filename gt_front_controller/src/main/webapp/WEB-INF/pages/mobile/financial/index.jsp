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
    <title><spring:message code="financial.perass" /></title>
    <style type="text/css">
        @font-face{
           font-family: 'PingFangMedium';
           src: url('/static/mobile2018/fonts/PingFangMedium.ttf'); 
        } 
.tradeTop{
    background: #fff;
    margin-bottom: 0.2rem;
}        
       .currencyAdd{
        	background: #76c80e;
		    margin-right: 0.1rem;
		    height: 0.55rem;
		    line-height: 0.55rem;
		    font-size: 0.24rem;
		    color: #fff;
		    padding: 0 0.15rem;
		    cursor: pointer;
        }
        .btn_1 {
	        line-height: 0.55rem;
		    margin-right: 0.3rem;
		    color: #506FC8;
		    font-size: 0.24rem;
		}

.realfixed{
    margin-left: -1.6rem;
    top: 27%;
    width: 5rem;
    padding-bottom: 0.42rem;
    clear: both;
    overflow: hidden;
    text-align: center;
    position: fixed;
    left: 40%;
    z-index: 100;
    background: white;
    box-shadow: 0px 2px 9px 0px rgba(183, 183, 183, 0.32);
    display:none;
}
.realfixed ul h3{
    text-align: right;
    padding: 0.2rem 0.3rem 0 0;
}
.realfixed ul h3>img{
    cursor: pointer;
    width: 0.25rem;
}

.realfixed ul li{
    color: #FF481F;
    text-align: center;
    margin: 0 auto;
    font-size: 16px;
}
.realfixed ul li p{
    width: 3.2rem;
    margin:0.2rem auto 0 auto;
    line-height: 0.3rem;
    font-size: 0.26rem;
}
.realfixed ul li img{
	width: 3rem;
}

.realfixed ul li span{
	display: block;
    font-size: 0.29rem;
    line-height: 0.4rem;
}
.realModule{
    position: fixed;
    top: 0;
    left: 0;
    bottom: 0;
    right: 0;
    background: black;
    opacity: 0.3;
    z-index: 99;
    display:none;
}

    </style>

</head>
<body>

 <%@include file="../comm/header.jsp" %>
    <header class="tradeTop">
        <span class="back toback2"></span>
        <span class="tit"><spring:message code="financial.perass" /></span>
        <a href="/financial/accountcoin.html" class="btn_1 fr"><spring:message code="new.management" /></a>
    </header>
	<div class="trade finassets">
        <h1><img src="${oss_url}/static/mobile2018/images/set2x.png" class="setimg"><spring:message code="newmobile.teansaction" /></h1>
        <h4><spring:message code="newmobile.asset" /><span>(USDT)</span></h4>
       <p class="assets_all">
        $<span class="total"><ex:DoubleCut value="${totalCapitalTrade }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/>
        </span>
       </p>
       <p class="assets_all">≈<span class="summoney" style="color:white;">57931.29</span>CNY</p>
  	</div> 
    <div class="Personal_assets">
     <%-- <div style="height: 0.8rem;margin-top: 0.2rem;"> 
    	<a href="/financial/accountcoin.html" class="btn_1 fr"><spring:message code="new.management" /></a>
  		<button  onclick="return fisprojecter()" class="currencyAdd fr"><spring:message code="json.up.coin" /></button> 
   	</div> --%>
   	<div class="financialBox">
   		<div class="finShadow">
      		<div  class="textR btnList">
	             <c:choose>
                     <c:when test="${usdtfwallet.fvirtualcointype.fisrecharge==true}">
                         <a href="/account/rechargeBtc.html?symbol=${usdtfwallet.fvirtualcointype.fid}&tab=2" class="" data-key="${usdtfwallet.fvirtualcointype.fid}"><spring:message code="financial.recharge" /></a>
                     </c:when>
                     <c:otherwise>
                         <span class="noTrade"><spring:message code="financial.recharge" /></span>
                     </c:otherwise>
                 </c:choose>
                 <c:choose>
                    <c:when test="${usdtfwallet.fvirtualcointype.FIsWithDraw==true}">
                        <a href="/account/withdrawBtc.html?symbol=${usdtfwallet.fvirtualcointype.fid}&tab=2" class="tb_btn" data-key="${usdtfwallet.fvirtualcointype.fid}"><spring:message code="new.withdraw" /></a>
                    </c:when>
                    <c:otherwise>
                        <span class="noTrade"><spring:message code="new.withdraw" /></span>
                    </c:otherwise>
                 </c:choose>
           	 </div>
        <ul>
   			 <li class='td_01'>
	   			 <span>USDT</span>
	   			 <em><spring:message code="financial.currency" /><a href=""></a></em>
   			 </li>
             <li>
	             <span><ex:DoubleCut value="${usdtfwallet.ftotal }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/></span>
	             <em><spring:message code="financial.usaass" /></em>
             </li>
             <li class="td_03">
	             <span><ex:DoubleCut value="${usdtfwallet.ffrozen }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/></span>
	             <em><spring:message code="financial.freass" /></em>
             </li>
   		</ul>
   		</div>
   		<c:forEach items="${fvirtualwallets }" var="v" varStatus="vs" begin="0">
  		 <div class="finShadow">
      			<div class="textR btnList">
                      <c:choose>
                         <c:when test="${v.value.fvirtualcointype.fisrecharge==true}">
                             <a href="/account/rechargeBtc.html?symbol=${v.value.fvirtualcointype.fid}&tab=2" class="" data-key="${v.value.fvirtualcointype.fid}"><spring:message code="financial.recharge" /></a>
                         </c:when>
                         <c:otherwise>
                                <span class="noTrade"><spring:message code="financial.recharge" /></span>
                         </c:otherwise>
                      </c:choose>
                      <c:choose>
                        <c:when test="${v.value.fvirtualcointype.FIsWithDraw==true}">
                           <a href="/account/withdrawBtc.html?symbol=${v.value.fvirtualcointype.fid}&tab=2" class="tb_btn" data-key="${v.value.fvirtualcointype.fid}"><spring:message code="new.withdraw" /></a>
                        </c:when>
                        <c:otherwise>
                           <span class="noTrade"><spring:message code="new.withdraw" /></span>
                        </c:otherwise>
                      </c:choose>
                      <c:choose>
                        <c:when test="${v.value.fvirtualcointype.fcanConvert==true}">
                           <a href="/account/convertCoin.html?symbol=${v.value.fvirtualcointype.fid}&tab=2" class="tb_btn" data-key="${v.value.fvirtualcointype.fid}"><spring:message code="new.convert" /></a>
                        </c:when>
                        <c:otherwise>
                           <span class="noTrade"><spring:message code="new.convert" /></span>
                        </c:otherwise>
                      </c:choose>
                </div>
              <ul>
	                <li class='td_01'>
		                <span>${v.value.fvirtualcointype.fShortName }</span>
		                <em><spring:message code="financial.currency" /><a href=""></a></em>
	                </li>
	                <li>
		                <span>
		                <ex:DoubleCut value="${v.value.ftotal }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/>
		                 <c:if test="${v.value.flocked>0 }">
		                   <br />(<spring:message code="newmobile.lock" /><ex:DoubleCut value="${v.value.flocked }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/>)
		                </c:if>
		                </span>
		                <em><spring:message code="financial.usaass" /></em>
	                </li>
	                <li class="td_03">
		                <span>
		                <ex:DoubleCut value="${v.value.ffrozen }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/>
		                <c:if test="${v.value.fcanlendBtc>0 }">
		                <br />(<spring:message code="newmobile.released" /><ex:DoubleCut value="${v.value.fcanlendBtc-v.value.falreadyLendBtc }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/>)
		                </c:if>
		                </span>
		                <em><spring:message code="financial.freass" /></em>
	                </li>
	   		  </ul>
   		  </div>
   		</c:forEach>
   	</div>
   	 <%-- <div style="border-bottom: 1px solid #eee;"></div> 
        <table class="table">
            <tr>  
                <td class='td_01'><span>USDT</span><em><spring:message code="financial.currency" /><a href=""></a></em></td>
                <td><span><ex:DoubleCut value="${usdtfwallet.ftotal }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/></span><em><spring:message code="financial.usaass" /></em></td>
                <td class="td_03"><span><ex:DoubleCut value="${usdtfwallet.ffrozen }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/></span><em><spring:message code="financial.freass" /></em></td>
            </tr>
            <tr>
            	<td colspan="3" class="textR btnList">
	            <a href="/exchange/rechargeUsdt.html"><spring:message code="financial.usdtrecharge" /></a>
	            <a href="/exchange/withdrawUsdt.html" class="tb_btn"><spring:message code="financial.usdtrewithdrawal" /></a>
	             <c:choose>
                     <c:when test="${usdtfwallet.fvirtualcointype.fisrecharge==true}">
                         <a href="/account/rechargeBtc.html?symbol=${usdtfwallet.fvirtualcointype.fid}&tab=2" class="" data-key="${usdtfwallet.fvirtualcointype.fid}"><spring:message code="financial.recharge" /></a>
                     </c:when>
                     <c:otherwise>
                            <span class="noTrade"><spring:message code="financial.recharge" /></span>
                     </c:otherwise>
                 </c:choose>
                 <c:choose>
                    <c:when test="${usdtfwallet.fvirtualcointype.FIsWithDraw==true}">
                        <a href="/account/withdrawBtc.html?symbol=${usdtfwallet.fvirtualcointype.fid}&tab=2" class="tb_btn" data-key="${usdtfwallet.fvirtualcointype.fid}"><spring:message code="new.withdraw" /></a>
                    </c:when>
                    <c:otherwise>
                        <span class="noTrade"><spring:message code="new.withdraw" /></span>
                    </c:otherwise>
                 </c:choose>
           	 </td>
            </tr>
            <c:forEach items="${fvirtualwallets }" var="v" varStatus="vs" begin="0">
             <tr class="001">
                <td class='td_01'>
	                <span>${v.value.fvirtualcointype.fShortName }</span>
	                <em><spring:message code="financial.currency" /><a href=""></a></em>
                </td>
                <td>
	                <span>
	                <ex:DoubleCut value="${v.value.ftotal }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/>
	                 <c:if test="${v.value.flocked>0 }">
	                   <br />(<spring:message code="newmobile.lock" /><ex:DoubleCut value="${v.value.flocked }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/>)
	                </c:if>
	                </span>
	                <em><spring:message code="financial.usaass" /></em>
                </td>
                <td class="td_03">
	                <span>
	                <ex:DoubleCut value="${v.value.ffrozen }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/>
	                <c:if test="${v.value.fcanlendBtc>0 }">
	                <br />(<spring:message code="newmobile.released" /><ex:DoubleCut value="${v.value.fcanlendBtc-v.value.falreadyLendBtc }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/>)
	                </c:if>
	                </span>
	                <em><spring:message code="financial.freass" /></em>
                </td>
               </tr>
               <tr>
                   <td colspan="3" class="textR btnList">
                      <c:choose>
                         <c:when test="${v.value.fvirtualcointype.fisrecharge==true}">
                             <a href="/account/rechargeBtc.html?symbol=${v.value.fvirtualcointype.fid}&tab=2" class="" data-key="${v.value.fvirtualcointype.fid}"><spring:message code="financial.recharge" /></a>
                         </c:when>
                         <c:otherwise>
                                <span class="noTrade"><spring:message code="financial.recharge" /></span>
                         </c:otherwise>
                      </c:choose>
                      <c:choose>
                        <c:when test="${v.value.fvirtualcointype.FIsWithDraw==true}">
                           <a href="/account/withdrawBtc.html?symbol=${v.value.fvirtualcointype.fid}&tab=2" class="tb_btn" data-key="${v.value.fvirtualcointype.fid}"><spring:message code="new.withdraw" /></a>
                        </c:when>
                        <c:otherwise>
                           <span class="noTrade"><spring:message code="new.withdraw" /></span>
                        </c:otherwise>
                      </c:choose>
                   </td>
                </tr>
            </c:forEach>   
        </table> --%>
    </div>

 <!-- 我要上币弹窗 -->   
     <div class="realfixed bindtrademodule" style="margin-left: -1.8rem;top: 10em; height: 5rem;">
    	<ul>
    		<h3><img src="${oss_url}/static/front2018/images/close@2x.png" class="bindtradeclose"></h3>
    		<li>
    			<p>您好，联系我们成为项目方申请自助上币服务</p>
    			<img src="${oss_url}/static/front2018/images/exchange/saoma2x.png">
    			<span>扫一扫联系我们</span>
   			</li>
    	</ul>
    </div>
    <!--蒙层-->
    <div class="realModule bindtrademodule ngrealModule"></div>
    <%@include file="../comm/footer.jsp" %> 
<%-- <%@include file="../comm/tabbar.jsp"%>
<%@include file="../comm/footer.jsp" %>  --%>

</body>
<script src="${oss_url}/static/mobile2018/js/fluckydraw/com.js?v=20181126201750"></script>
<script type="text/javascript">
  var total = parseFloat(($(".total").html() * 6.5).toFixed(4));
  $(".summoney").html(total);
  
  /* 我要上币 */
  $(".bindtradeclose").click(function(){
			$(".bindtrademodule").hide();
		})
  	function fisprojecter() {
			var fisprojecter = $("#fisprojecter").val();
			
				$(".bindtrademodule").show();
			
		}
</script>
</html>
