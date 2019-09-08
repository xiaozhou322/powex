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
<style type="text/css">
.infoCon li{
}
</style>
</head>
<body>

 <%@include file="../comm/header.jsp" %>
<section>
    <div class="mg">
        <div class="l_finance clear">
                   <%@include file="../comm/left_menu.jsp" %> 
     
            <div class="l_financeR fr">
                <div class="personal_title">
                    <h2 class="assetTitle"><spring:message code="new.profile" /></h2>
                    <div class="asset-status-box">
                        <svg class="icon sfont27" aria-hidden="true">
                           <use xlink:href="#icon-wodetouzi"></use>
                        </svg>
                        <spring:message code="new.valuation" /> <em><ex:DoubleCut value="${totalCapitalTrade }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/></em>&nbsp;<span>USDT</span>
                    </div>
                </div>
                <div class="assetList">
                    <div class="assetList_title clear" style="margin: 30px 0;padding-right: 59px;">
                        <h2 class="assetTitle fl"><spring:message code="new.assets" /></h2>                    
                        <span class="switch-on" id="coinSwitch">
                            <em class="slide1"></em>
                        </span><spring:message code="new.only.coin" />
                        <a href="/financial/accountcoin.html" class="btn_1 fr"><spring:message code="new.management" /></a>
                      <%--   <a href="http://192.168.0.119:8089/project/#/views/currencyAdd" class="currencyAdd fr"><spring:message code="我要上币" /></a> --%>
                <button  onclick="return fisprojecter()" class="currencyAdd fr">我要上币</button>    
                    </div>

                    <div class="assetCon_list">
                        <ol class="infoTit clear">
                            <li><spring:message code="financial.currency" /></li>
                            <li><spring:message code="financial.usaass" /></li>
                            <li><spring:message code="financial.freass" /></li>
                            <li><spring:message code="financial.totamo" /></li>
                            <li><spring:message code="market.entrustaction" /></li>
                        </ol>
                        <ul class="infoCon">
                            <li id="coin4">
                                <div class="coinInfo">
                                    <span class="s-1">USDT</span>
                                    <span class="s-1"><font class="tdRed is_num"><ex:DoubleCut value="${usdtfwallet.ftotal }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/></font></span>
                                    <span class="s-1"><ex:DoubleCut value="${usdtfwallet.ffrozen }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/></span>
                                    <span class="s-1 switchon"><ex:DoubleCut value="${usdtfwallet.ftotal+usdtfwallet.ffrozen }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/></span>
                                    <span class="s-1 s-2">
										<a href="javascript:;" class="s_btn charge_btn btn_01" data-key="4"><spring:message code="financial.recharge" /></a>
                                    	<a href="javascript:;" class="s_btn exchange_btn btn_02" data-key="4"><spring:message code="new.withdraw" /></a>
                                    	<span class="noTrade"><spring:message code="new.trading" /></span>
                                    </span>
                                    <div class="clear"></div>
                                </div>
                            </li>
                            <c:forEach items="${fvirtualwallets }" var="v" varStatus="vs" begin="0">                            
                            <li id="coin${v.value.fvirtualcointype.fid}">
                                <div class="coinInfo">
                                    <span class="s-1">${v.value.fvirtualcointype.fShortName }</span>
                                    <span class="s-1"><font class="tdRed is_num"><ex:DoubleCut value="${v.value.ftotal }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/></font>
                                        <c:if test="${v.value.flocked>0 }">
                                            <br />(<spring:message code="newmobile.lock" /><ex:DoubleCut value="${v.value.flocked }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/>)
                                        </c:if>     
                                    </span>
                                    <span class="s-1">
                                        <ex:DoubleCut value="${v.value.ffrozen }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/>
                                        <c:if test="${v.value.fcanlendBtc>0 }">
                                            <br />(<spring:message code="newmobile.released" /><ex:DoubleCut value="${v.value.fcanlendBtc-v.value.falreadyLendBtc }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/>)
                                        </c:if>
                                    </span>
                                    <span class="s-1 switchon"><ex:DoubleCut value="${v.value.ftotal+v.value.ffrozen }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/></span>
                                    <span class="s-1 s-2">
                                    <c:choose>
                                        <c:when test="${v.value.fvirtualcointype.fisrecharge==true}">
                                            <a href="javascript:;" class="s_btn charge_btn btn_01" data-key="${v.value.fvirtualcointype.fid}"><spring:message code="financial.recharge" /></a>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="noTrade"><spring:message code="financial.recharge" /></span>
                                        </c:otherwise>
                                    </c:choose>
                                    <c:choose>
                                    <c:when test="${v.value.fvirtualcointype.FIsWithDraw==true}">
                                        <a href="javascript:;" class="s_btn exchange_btn btn_02" data-key="${v.value.fvirtualcointype.fid}"><spring:message code="new.withdraw" /></a>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="noTrade"><spring:message code="new.withdraw" /></span>
                                    </c:otherwise>
                                    </c:choose>
                                      <%--   <a href="/trademarket.html?symbol=${v.value.fvirtualcointype.fid}" class="s_btn trade_btn"><spring:message code="new.trading" /></a></span>
                                      --%>   
                                        <span class="noTrade"><spring:message code="new.trading" /></span>
                                    <div class="clear"></div>
                                </div>
                            </li>
                            </c:forEach>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>
 <!-- 我要上币弹窗 -->   
     <div class="realfixed bindtrademodule" style="margin-left: -124px;top: 18em; height: 331px;">
    	<ul>
    		<h3><img src="${oss_url}/static/front2018/images/close@2x.png" class="bindtradeclose"></h3>
    		<li>
    			<p style="width: 218px; margin: 0 auto;line-height: 22px;">您好，联系我们成为项目方申请自助上币服务</p>
    			<img src="${oss_url}/static/front2018/images/exchange/saoma2x.png">
    			<span>扫一扫联系我们</span>
   			</li>
    	</ul>
    </div>
    <!--蒙层-->
    <div class="realModule bindtrademodule ngrealModule"></div>
<input type="hidden" id="fisprojecter" value="${sessionScope.login_user.fisprojecter }" />
</section>


<%@include file="../comm/footer.jsp" %> 

<script type="text/javascript">
window.onload=function(){

		/* var url="/financial/index1.html?random=" + Math.round(Math.random() * 100);
		 jQuery.post(url, {}, function (data) {
			var html=""; 
			var html1="";
			var html2="";
		        console.log(data)
		    if(data!=null&&data.code==1001) {
		    	
		    }else if(data!=null&&data.code==1002){	    	
		    	

		    }   
		        
		    },"json");	 */
}

    $(".btn_02").click(function(){
        <c:if test="${fuser.fhasRealValidate && !fuser.fhasImgValidate && fuser.fpostImgValidate}">
            window.location.href="/user/realCertification.html";
        </c:if>
        var sysmoblid = $(this).data("key");
        if($("iframe").length > 0) {

            if($("iframe").data("key") == sysmoblid && $("iframe").data("type") == 2){
                $("iframe").remove();
                return false;
            }
        } 
        $("iframe").remove();
        var html = "/account/withdrawBtc.html?symbol=" + sysmoblid;
        var iframeHtml = "<iframe id='w_btc' src='"+ html +"' width='100%' height='100%' data-key='"+ sysmoblid +"' data-type='2' style='border:0'></iframe>";
        $("#coin"+sysmoblid).append(iframeHtml);
        $('iframe').load(function(){
            $(this).height($(this.contentWindow.document).height());
        });

    });


     $(".btn_01").click(function(){
        var sysmoblid = $(this).data("key");
        if($("iframe").length > 0) {

            if($("iframe").data("key") == sysmoblid && $("iframe").data("type") == 1){
                $("iframe").remove();
                return false;
            }
        } 
        $("iframe").remove();
        var html = "/account/rechargeBtc.html?symbol=" + sysmoblid;
        var iframeHtml = "<iframe  id='r_btc' src='"+ html +"' width='100%' height='100%' data-key='"+ sysmoblid +"' data-type='1' style='border:0'></iframe>";
        $("#coin"+sysmoblid).append(iframeHtml);
        $('iframe').load(function(){
             $(this).height($(this.contentWindow.document).height());
        });
        
    });

     $("#coinSwitch").click(function(event) {
         if($(this).hasClass('switch-on')){
             $(this).addClass('switch-off').removeClass('switch-on');

             $(".switchon").each(function(){
               var value =  parseFloat($(this).html());
               if(value != 0){

               }else{
                 $(this).parent().hide();
               }
               
             });
        }else{
            $(this).addClass('switch-on').removeClass('switch-off');
            $(".switchon").parent().show();
        }
     });
    /* 我要上币 */
    $(".bindtradeclose").click(function(){
			$(".bindtrademodule").hide();
		})
    	function fisprojecter() {
			var fisprojecter = $("#fisprojecter").val();
			if(fisprojecter == "false"){
				$(".bindtrademodule").show();
				
			}else{
				window.location.href="http://192.168.0.119:8089/project/";
			}
		}

</script>

</body>
</html>
