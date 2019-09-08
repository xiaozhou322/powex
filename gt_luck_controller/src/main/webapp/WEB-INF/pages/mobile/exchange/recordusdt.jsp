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
	<meta name = "viewport" content = "width = device-width, user-scalable = no,initial-scale=0.5,maximum-scale=0.5,minimum-scale=0.5">
	<%@include file="../comm/link.inc.jsp" %></head>
<body>
	<input type="hidden" id="max_double" value="${requestScope.constant['maxwithdrawcny'] }">
	<input type="hidden" id="min_double" value="${requestScope.constant['minwithdrawcny'] }">

	<style type="text/css">
	.textBox .form-control,#withdrawCnyButton{width:302px;}
	#withdrawBlank{width:430px;}
	.withdraw .addtips{right:-88px;}
	.textBox .control-label{width:140px;}
    .tabTit{padding-top:50px;}
    .tabTit a{display:inline-block; width:50%; font-size:0.3rem; float:left; text-align:center; color:#666;}
    .tabTit a.cur{color:#4797f2;}
    .p_01{padding-left:0.4rem; color:#333;}
    .p_02{border-bottom: 1px solid #ececec; color:#999; padding-bottom:10px;}
    /*#slidecontentbox_1 td:nth-child(2){width:27%; letter-spacing:-0.5px;}*/
</style>

	<%@include file="../comm/header.jsp" %>
		<header class="tradeTop tradeTop2">  
    <i class="back toback2"></i>
    <c:if test="${type==0}">
    <h2 class="tit"><spring:message code="financial.usdtrecharge.recrec" /></h2>
    </c:if>
     <c:if test="${type==1}">
    <h2 class="tit"><spring:message code="financial.exchangeusdt.witrec" /></h2>
    </c:if> 
</header> 

	<div class="tixianCare  lw-coinInstructions" style="font-size:28rem;">
	<div class="buyUstdMain recodeCon">
	 <c:if test="${type==0}">
            <div class="steps">
                <ol>
                    <li class="fl active textLeft">
                        <span></span>
                        <em><spring:message code="financial.usdtrecharge.step1" /></em>
                    </li>
                    <li class="fl active textCenter">
                        <span></span>
                        <em><spring:message code="financial.usdtrecharge.step2" /></em>
                    </li>                    
                    <li class="fr active textRight">
                        <span></span>
                        <em><spring:message code="financial.usdtrecharge.step3" /></em>
                    </li>
                    <div class="clear"></div>
                </ol>
            </div>
           </c:if> 
           <c:if test="${type==1}">
            <div class="steps">
               <ol>
                    <li class="fl active textLeft">
                        <span></span>
                        <em><spring:message code="financial.usdtrecharge.step1" /></em>
                    </li>
                    <li class="fl textCenter">
                    </li>                    
                    <li class="fr active textRight">   
                        <span></span>
                        <em><spring:message code="financial.usdtrecharge.step3" /></em>
                    </li>
                    <div class="clear"></div>
                </ol>
            </div>
          </c:if> 
            <div class="steps_3">
				<c:if test="${type==0}">
                 <div class="step3Con textCenter">
                 <c:if test="${fcapitaloperation.fstatus==2}">
                    <div class="stepsAll curSteps">
                         <img src="${oss_url}/static/mobile/images/ic01.png" alt="" />
                         <h3 class="Color333"><spring:message code="financial.usdtrecharge.status2" /></h3>
                         <p><a class="cblue refresh" href="javascript:;" onclick="window.location.reload();"><spring:message code="financial.exchangeusdt.viewstate" /></a></p>
                         <p class="Color999"><spring:message code="financial.usdtrecharge.status2.info" /></p>
                     </div> 
                  </c:if> 
                  <c:if test="${fcapitaloperation.fstatus==3}">                       
                     <div class="stepsAll stepsSuccess">
                         <img src="${oss_url}/static/mobile/images/ic02.png" alt="" />
                         <h3 class="Color333"><spring:message code="financial.usdtrecharge.status3" /></h3>
                         <p><a class="cblue" href="/financial/index.html"><spring:message code="financial.usdtrecharge.status3.info" /></a></p>
                     </div>
                   </c:if>
                   <c:if test="${fcapitaloperation.fstatus==4}">
                     <div class="stepsAll stepsFailure">
                         <img src="${oss_url}/static/mobile/images/ic03.png" alt="" />
                         <h3 class="Color333"><spring:message code="financial.usdtrecharge.status4" /></h3>
                         <p class="cblue"> </p>
                     </div>
                       </c:if> 
                    <c:if test="${fcapitaloperation.fstatus==5}">
                     <div class="stepsAll stepsFailure">
                         <img src="${oss_url}/static/mobile/images/ic03.png" alt="" />
                         <h3 class="Color333"><spring:message code="financial.usdtrecharge.status5" /></h3>
                         <p class="cblue"><spring:message code="financial.usdtrecharge.status5.info" /></p>
                     </div>
                       </c:if> 
                 </div>
                    </c:if>

				

				 <c:if test="${type==1}">
                 <div class="step3Con textCenter">
                 	<c:if test="${fcapitaloperation.fstatus==2}">
	                    <div class="stepsAll curSteps">
	                         <img src="${oss_url}/static/mobile/images/ic01.png" alt="" />
	                         <h3 class="Color333"><spring:message code="financial.usdtrecharge.status2" /></h3>
	                         <p><a class="cblue" href="javascript:;" onclick="window.location.reload();"><spring:message code="financial.exchangeusdt.viewstate" /></a></p>
	                         <p class="Color999"><spring:message code="financial.usdtrecharge.status2.info" /></p>
	                     </div>
                     </c:if>      
                     <c:if test="${fcapitaloperation.fstatus==3}">              
	                     <div class="stepsAll stepsSuccess">
	                         <img src="${oss_url}/static/mobile/images/ic02.png" alt="" />
	                         <h3 class="Color333"><spring:message code="financial.usdtrewithdrawal.status3" /></h3>
	                         <p><a class="cblue" href="/financial/index.html"><spring:message code="financial.usdtrecharge.status3.info" /></a></p>
	                     </div>
                     </c:if> 
                     <c:if test="${fcapitaloperation.fstatus==4}"> 
	                     <div class="stepsAll stepsFailure">
	                         <img src="static/mobile/images/ic03.png" alt="" />
	                         <h3 class="Color333"><spring:message code="financial.usdtrewithdrawal.status4" /></h3>
	                         <p class="cblue"> </p>
	                     </div>
	                 </c:if>     
                     <c:if test="${fcapitaloperation.fstatus==5}"> 
	                     <div class="stepsAll stepsFailure">
	                         <img src="static/mobile/images/ic03.png" alt="" />
	                         <h3 class="Color333"><spring:message code="financial.usdtrewithdrawal.status5" /></h3>
	                         <p class="cblue"><spring:message code="financial.usdtrewithdrawal.status5.info" /></p>
	                     </div>
	                 </c:if>    
                 </div>
                 </c:if>

             </div>
             
					
	<c:if test="${type==0}">
    <div class="tabTit">
        <a class="cur" href="/exchange/recordUsdt.html?type=0"><spring:message code="financial.usdtrecharge.recrec" /></a>
        <a href="/exchange/recordUsdt.html?type=1"><spring:message code="financial.exchangeusdt.witrec" /></a>
        <div class="clear"></div>
    </div>
	<div class="recodeMain">
		<div class="recodeDetail" id="recordbody0">
		<div id="slidecontentbox_0">
		<c:forEach items="${fcapitaloperations}" var="v">
			<table>
				<tr class="tr_spectial">
					<td colspan="4" class="td_01 "><spring:message code="financial.usdtrecharge.rechargetime" />：<fmt:formatDate value="${v.fcreateTime }" pattern="yyyy-MM-dd HH:mm:ss"/>   <c:if test="${(v.fstatus==1 || v.fstatus==2)}">
								<span class="fr" style="color:#999;">
                                    <a class="rechargecancel opa-link cblue" href="javascript:void(0);" data-fid="${v.fid }"><spring:message code="financial.cnyrecharge.cancel" /></a>
                                    
    								<c:if test="${v.fstatus==1}">|
    								<a class="rechargesub opa-link cblue" href="javascript:void(0);" data-fid="${v.fid }"><spring:message code="financial.cnyrecharge.submit" /></a>
								</c:if>
								</c:if>
                                </span></td>
				</tr>

                <tr class="tr_spectial2">
                    <td class="td_01"><b><spring:message code="financial.usdtrecharge.orderno" /></b></td>
                    <td><b><spring:message code="financial.usdtrecharge.rechargeamount" /></b></td>
                    <td><b><spring:message code="financial.usdtrecharge.rechargemode" /></b></td>
<!--                    <td class="td_02"><spring:message code="financial.usdtrecharge.rechargestatus" /></td>
 -->                    
                </tr>
				<tr>
					<td class="td_01 ">${v.fid }</td>
					<td class="colorRed">￥${v.famount }($<ex:DoubleCut value="${v.famount/6.5 }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/>)</td>
					<td class="">${v.fremittanceType }</td>
<!-- 					<td class="td_02"><spring:message code="financial.cnyrecharge.status${v.fstatus }" /></td>
 -->                   
				</tr>
                <tr>
                    <td class="td_01"><b><spring:message code="financial.usdtrecharge.rechargestatus" /></b></td>
                    <td><b><spring:message code="financial.usdtrecharge.remarknum" /></b></td>
                    <td></td>
                </tr>
                <tr>
                    <td class="td_01 "><spring:message code="financial.cnyrecharge.status${v.fstatus }" /></td>
                    <td class=" ">${v.fremark }</td>
                    <td></td>
                </tr>

              
			</table>
<!--             <p class="p_01">${v.fremark }</p>
            <p class="p_01 p_02"><spring:message code="financial.usdtrecharge.remarknum" /></p>
		 -->	</c:forEach>
			</div>

			 <c:if test="${fn:length(fcapitaloperations)==0 }">
<!-- 			 	<tr>
			 		<td colspan="6" class="no-data-tips" align="center">
			 			<span>
			 				<spring:message code="financial.usdtrecharge.norecharge" />			 
			 			</span>
			 		</td>
			 	</tr> -->
            <div class="noTxt textCenter mtop no-data-tips">
                <img src="${oss_url}/static/mobile/images/noMsg.png" alt="" />
                <p> <spring:message code="financial.usdtrecharge.norecharge" /></p>
             </div>
			 </c:if>
		</div>
	</div>
		</c:if>



		<c:if test="${type==1}">
             <div class="tabTit">
                    <a href="/exchange/recordUsdt.html?type=0"><spring:message code="financial.usdtrecharge.recrec" /></a>
                    <a  class="cur"  href="/exchange/recordUsdt.html?type=1"><spring:message code="financial.exchangeusdt.witrec" /></a>
                    <div class="clear"></div>
              </div>
			  <div class="recodeMain">
                     <div class="recodeDetail" id="recordbody1">
                     <div id="slidecontentbox_1">
                     <c:forEach items="${fcapitaloperations }" var="v" varStatus="vs">
                         <table>
                                <tr class="tr_spectial">
                                    <td colspan="4" class="td_01 Color333"><spring:message code="financial.usdtrewithdrawal.wittim" />：<fmt:formatDate value="${v.fcreateTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>

                                </tr>
                                 <tr class="tr_spectial">
                                    <td colspan="4" class="td_01 Color333"><spring:message code="financial.usdtrewithdrawal.remarknum" />：${v.fremark } <c:if test="${v.fstatus==1 }">
										<a class="fr cblue" href="/exchange/exchangeUsdt.html?code=${v.fremark }"><spring:message code="financial.usdtrewithdrawal.exchange" /></a></c:if></td>
                                    
                                </tr>
                                 <tr class="tr_spectial">
                                    <td colspan="4" class="td_01 Color333"><spring:message code="financial.usdtrewithdrawal.account" />：${v.fAccount }</td>
                                    
                                </tr>
                                <tr>
                                   <td class="td_01 Color333">$${v.famount+v.ffees  }</td>
                                   <td class="colorRed">$${v.famount}(￥<ex:DoubleCut value="${v.famount*6.5}" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/>)</td>
                                   <td class="Color333">$${v.ffees }</td>
                                   <td class="td_02"><c:if test="${v.fstatus!=1 }"><spring:message code="financial.cnyrewithdrawal.status${v.fstatus }" /></c:if>
										<c:if test="${v.fstatus==1 }"><a class="cancelWithdrawusdt opa-link cblue" href="javascript:void(0);" data-fid="${v.fid }"><spring:message code="financial.cancel" /></a>
										</c:if></td>
                               </tr>                             
                               <tr class="tr_spectial2">
                                   <td class="td_01"><spring:message code="financial.usdtrewithdrawal.amount" /></td>
                                   <td><spring:message code="financial.actarr" /></td>
                                   <td><spring:message code="financial.usdtrewithdrawal.fee" /></td>
                                   <td class="td_02"><spring:message code="financial.usdtrewithdrawal.witstatus" /></td>
                               </tr>                           
                        </table>                         
                   	</c:forEach>
                   	</div>
                    <c:if test="${fn:length(fcapitaloperations)==0 }">
                    <div class="noTxt textCenter mtop no-data-tips">
                        <img src="${oss_url}/static/mobile/images/noMsg.png" alt="" />
                        <p> <spring:message code="financial.usdtrecharge.norecharge" /></p>
                    </div>
                    </c:if>
                    </div>                     
                 </div>

		</c:if>


            </div>
        </div>
    </section>
	<%@include file="../comm/footer.jsp" %>
	<script src="${oss_url}/static/mobile2018/js/fluckydraw/com.js?v=20181126201750"></script>
	<script type="text/javascript" src="${oss_url}/static/mobile2018/js/finance/account.withdraw.js?v=3"></script>
	<script type="text/javascript" src="${oss_url}/static/mobile2018/js/finance/account.usdtrecharge.js?v=11"></script>
	<script type="text/javascript" src="${oss_url}/static/mobile2018/js/finance/city.min.js?v=20181126201750"></script>
	<script type="text/javascript" src="${oss_url}/static/mobile2018/js/finance/jquery.cityselect.js?v=20181126201750"></script>
	
<input type="hidden" id="pageCount" value="${totalPage}">
<input type="hidden" id="currentPage" value="${currentPage}">
<input type="hidden" id="type" value="${type}">
<div id="slide_loading_btn"  class="textCenter" style="padding-bottom:0.2rem; display:none;"> onclick="slideLoadMoreInfo()" style=" <c:if test="${totalPage==1 }">display:none</c:if>"><spring:message code="m.security.moremsg" /></div>
<script type="text/javascript">
$(".backIcon").click(function(){
    window.history.go(-1);
});


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
    $(window).unbind("scroll",slideLoad);
    if(currentPage>0&&currentPage<pageCount)
    {
		var index = layer.load(2);
        $(slideBtn).text(language["load.ing.msg"]).show();
        var url = window.location.href;
        var object = {};
        object.currentPage = currentPage+1;
        $.get(url,object,function(html){
            if($.trim(html)!='')
            {

                $('#slidecontentbox_${type}').append(html);
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


</body>

</html>