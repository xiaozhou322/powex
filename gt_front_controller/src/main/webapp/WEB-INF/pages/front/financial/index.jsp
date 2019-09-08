<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ include file="../comm/include.inc.jsp"%>

<!doctype html>
<html>
<head> 
<%@ include file="../comm/basepath.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="../comm/link.inc.jsp" %>
<style type="text/css">

.assetListTit{
    margin: 30px 0 0 16px;
    padding-right: 46px;
    font-size: 14px;
    height: 50px;
    line-height: 50px;
}
.assetCon_list{padding: 0 16px;}

.modal-body .tr_txt .tr_l{    width: 72px;float: left;line-height: 40px;margin-right: 10px;}
.modal-body .tr_txt .inpBox{    float: left;
    width: 298px;background: #F6F6F6;
    height: 44px;}
.modal-body{    padding: 22px 20px 0 20px;margin:0px;}
.modal .btn{    
	font-size: 14px;
    background: #fff;
    width: 92px;
    color: #FF5E3A;
    display: inline;
    border-left: 1px solid #FF5E3A;
    height: 24px;
    margin: 10px 2px 0 0;
    background: none;
    line-height: 26px;}
    .modal-content{top:30%;}
  .modal-footer button{
    border: 1px solid #FF6633;
    width: 100px;
    height: 40px;
    font-size: 16px;
    display: inline-block;
    background: #FF5E3A;
    color: #fff;
    border-radius: 4px;
    margin-right: 12px;
    cursor: pointer;
}
.modal-footer  .btn_close{
    background: #fff;
    color: #6666CC;
}
.modal-footer{width: 362px;
    margin: 0 auto;}
</style>
</head>
<body>

 <%@include file="../comm/header.jsp" %>
<section>
    <div class="mg">
        <div class="l_finance clear">
                   <%@include file="../comm/left_menu.jsp" %> 
     
            <div class="l_financeR fr">
                <%-- <div class="personal_title">
                    <h2 class="assetTitle"><spring:message code="new.profile" /></h2>
                    <div class="asset-status-box">
                        <svg class="icon sfont27" aria-hidden="true">
                           <use xlink:href="#icon-wodetouzi"></use>
                        </svg>
                        <spring:message code="new.valuation" /> 
                        <em>
                        	<ex:DoubleCut value="${totalCapitalTrade }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/>
                        </em>&nbsp;<span>USDT</span>
                    </div>
                </div> --%>
                <div class="financialCen">
                	<spring:message code="nav.index.totalass" /> 
                    <em>
                    	<ex:DoubleCut value="${totalCapitalTrade }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/>
                    </em>&nbsp;<span>USDT</span>
                </div>
                
                    
                <div class="assetList">
					<div class="assetListTit">
                        <%-- <h2 class="assetTitle fl"><spring:message code="new.assets" /></h2>   --%>         
                        <h2 class="assetListH"><spring:message code="new.assets" /></h2>         
                        <span class="switch-on" id="coinSwitch">
                            <em class="slide1"></em>
                        </span>
                        <spring:message code="new.only.coin" />
                        <div style=" float: right; margin-top: 10px;">
	                        <a href="/financial/accountcoin.html" class="btn_1 fr">
	                        	<spring:message code="new.management" />
	                        </a>
			                <button  onclick="return fisprojecter()" class="currencyAdd fr">
			                	<spring:message code="json.up.coin" />
			                </button>
                        </div>
                            
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
                                    
                                     <c:choose>
                                        <c:when test="${usdtfwallet.fvirtualcointype.fisrecharge==true}">
                                           	<a href="javascript:;" class="s_btn charge_btn btn_01" data-key="${usdtfwallet.fvirtualcointype.fid}"><spring:message code="financial.recharge" /></a>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="noTrade"><spring:message code="financial.recharge" /></span>
                                        </c:otherwise>
                                    </c:choose>
                                    <c:choose>
                                    <c:when test="${usdtfwallet.fvirtualcointype.FIsWithDraw==true}">
                                       <a href="javascript:;" class="s_btn exchange_btn btn_02" data-key="${usdtfwallet.fvirtualcointype.fid}"><spring:message code="new.withdraw" /></a>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="noTrade"><spring:message code="new.withdraw" /></span>
                                    </c:otherwise>
                                    </c:choose>
                                    	<%-- <span class="noTrade"><spring:message code="new.trading" /></span> --%>
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
                                     <%-- <span class="noTrade"><spring:message code="new.trading" /></span> --%>
                                     <c:choose>
                                    <c:when test="${v.value.fvirtualcointype.fcanConvert==true}">
                                        <a href="javascript:;" class="s_btn exchange_btn btn_03" data-key="${v.value.fvirtualcointype.fid}"><spring:message code="new.convert" /></a>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="noTrade"><spring:message code="new.convert" /></span>
                                    </c:otherwise>
                                    </c:choose>
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
			window.location.href="/project/";
		}
	}
    
    
    $(".btn_03").click(function(){
        <c:if test="${fuser.fhasRealValidate && !fuser.fhasImgValidate && fuser.fpostImgValidate}">
            window.location.href="/user/realCertification.html";
        </c:if>
        var sysmoblid = $(this).data("key");
        /* if($("iframe").length > 0) {

            if($("iframe").data("key") == sysmoblid && $("iframe").data("type") == 3){
                $("iframe").remove();
                return false;
            }
        } 
        $("iframe").remove(); 
        var html = "/account/convertCoin.html?symbol=" + sysmoblid;
        var iframeHtml = "<iframe id='c_btc' src='"+ html +"' width='100%' height='100%' data-key='"+ sysmoblid +"' data-type='3' style='border:0'></iframe>";
        $("#coin"+sysmoblid).append(iframeHtml);
        $('iframe').load(function(){
            $(this).height($(this.contentWindow.document).height());
        }); */
        window.location.href="/account/convertCoin.html?symbol=" + sysmoblid;

    });

</script>

</body>
</html>
