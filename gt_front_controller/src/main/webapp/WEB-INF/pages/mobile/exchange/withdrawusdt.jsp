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
<%@include file="../comm/link.inc.jsp" %>
<style type="text/css">
	 .sendCode{
    	width:32%;
    } 
</style>
</head>
<body>
	<input type="hidden" id="max_double" value="${requestScope.constant['maxwithdrawusdt'] }">
	<input type="hidden" id="min_double" value="${requestScope.constant['minwithdrawusdt'] }">
  <%@include file="../comm/header.jsp" %>
  <div id="top1">
  	<header class="tradeTop tradeTop2">  
      <i class="back toback2"></i>
      <h2 class="tit"><spring:message code="financial.usdtrewithdrawal" /></h2>
  </header>
  <section id="subformdiv">
      <div class="topUpCon withdrawalCon CNYwithdrawalCon">
              <div class="choiceBox choiceBox2 login_item_02">
                 <div class="availableCoin">
                      <!-- <span></span> -->
                      <span><spring:message code="market.usable" />USDT:&nbsp;<b class="cgray">$<ex:DoubleCut value="${fwallet.ftotal }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/></b></span>
                  </div>
                  <div class="clear"></div>
              </div>
              <div class="choiceCoin choiceCoin2">
                  <div class="topUpForm withdrawalForm special_01">
                      <div class="txAddress">
                          <span class="tit fl"><spring:message code="financial.cnyrewithdrawal.card" /> </span>
                          <a class="fr cblue" onclick="showaddbank();"><spring:message code="financial.toadd" /></a>
                          <c:if test="${fn:length(fbankinfoWithdraws)==0 }">           
                              <span><spring:message code="m.security.notxaddress" /></span>
                          </c:if>
                          <c:if test="${fn:length(fbankinfoWithdraws)!=0 }">
                           <c:forEach items="${fbankinfoWithdraws }" var="v" varStatus="vs">
                           <c:if test="${0 eq  vs.index}">  
                            <span class="selStyle selStyle3" onclick="showtabs('withdrawBlank_box')" id="withdrawBlanks">${v.fname }&nbsp;&nbsp;<spring:message code="financial.cnyrewithdrawal.cardnum" />${v.fbankNumber }</span>
                            </c:if>
                            </c:forEach>  
                          </c:if>
                          <div class="clear"></div>
                      </div>

                     <select id="withdrawBlank" class="fl form-control select select9 sl1"  style="display: none">
                          <c:forEach items="${fbankinfoWithdraws }" var="v">
                            <option value="${v.fid }">${v.fname }&nbsp;&nbsp;<spring:message code="financial.cnyrewithdrawal.cardnum" />${v.fbankNumber }</option>
                            </c:forEach>    
                      </select>
                      <div class="withdrawalList">
                        <div class="login_item login_item_02 clear">
                            <label for="withdrawBalance" class="login_item_l"><spring:message code="financial.usdtrewithdrawal.amount" /></label>
                            <div class="login_item_r">
                                <input type="text" id="withdrawBalanceUsdt" autocomplete="off" placeholder="<spring:message code="financial.cnyrewithdrawal.amount" />">
                            </div>

                        </div>      
                        <div class="login-item">
                        <div class="amounttips clear">
                          <span class="fl">
                              <em class=""><spring:message code="financial.usdtrewithdrawal.fee" /></em>
                              <span id="free" class="cblue" style="margin-left:5px;">0</span>
                              <span class="sFont">USDT</span>
                           </span>
                          <span class='fr'>
                            <em class=""><spring:message code="financial.cnyrewithdrawal.arr" /></em>
                            <span id="amount" class="text-danger cred2" style="margin-left:5px;">0</span>
                            <span class="text-danger sFont">USDT</span>
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
                                <button id="withdrawsendmessage" data-msgtype="17" data-tipsid="withdrawerrortips" class="sendCode btn-sendmsg"  ><spring:message code="financial.send" /></button>
                            </div>
                            
                        </div>
                        </c:if> 
              
           
                      <c:if test="${isBindGoogle ==true}">
                        <div class="login_item login_item_02 clear">
                            <label for="" class="login_item_l mtop5"><spring:message code="financial.goocod" /></label>
                            <div class="login_item_r">
                                <input type="text" id="withdrawTotpCode" autocomplete="off" placeholder="<spring:message code="financial.goocod" />">
                            </div>
                        </div>
                      </c:if>
               
                      <!-- 添加人机验证 -无痕验证 -->
			  		 <div class="login_item clear" id="nvc_validate"></div>
                      </div>              
                  </div>                
              </div>
              	<div class="form-group" style="margin:0 0 5px 0;">
  									<span for="withdrawerrortips" class="col-xs-3 control-label"></span>
  									<div class="col-xs-7 textBox">
  										<label id="withdrawerrortips" class="text-danger">
  											
  										</label>
  									</div>
  								</div>
              <button class="btn btn2 mtop2" id="withdrawUsdtButton"><spring:message code="financial.usdtrewithdrawal.immwith" /></button>

          </div>

      </section>
      <section id="showcoincode"  style="display:none;">
      <div class="topUpCon withdrawalCon CNYwithdrawalCon">
     		<div class="choiceBox choiceBox2 login_item_02">
            <div class="availableCoin">
                 <!-- <span></span> -->
                 <span><spring:message code="market.usable" />USDT:&nbsp;<em class="cgray">$<ex:DoubleCut value="${fwallet.ftotal }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/></em></span>
             </div>
             <div class="clear"></div>
         </div>
         <div class="choiceCoin choiceCoin2"style="background:#fff;">
  			<div class="login_item login_item_02 clear relative textCenter" >
           <label for="coincode" class="login_item_l">USDT<spring:message code="financial.usdtrewithdrawal.remarknum" /></label>
            <div class="login_item_r">
                <span id="coincode">asdfagpkehlkpeohk</span>
            </div>
        </div>
  		</div>
  		
  		<button class="btn btn2 mtop2" id="withdrawUsdtButton" onclick="var code = $('#coincode').html();window.location='/exchange/exchangeUsdt.html?code='+code;"><spring:message code="financial.usdtrewithdrawal.exchange" /> >></button>
  	</div>
  	</section>
	
    </div>

<div id="top2" style="display: none">
  
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
                                 <span class="selStyle selStyle4 s_select" onclick="showtabs('openBankTypeAddr_box')" id="openBankTypeAddrs"><spring:message code="financial.cnyrewithdrawal.choosebank" /></span>                  
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
                              <button id="bindsendmessage" data-msgtype="10" data-tipsid="binderrortips"  class="sendCode btn-sendmsg" ><spring:message code="financial.send" /></button>
                          </div>
                          
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

                  <div class="form-group">
						<span for="binderrortips" class="col-xs-3"></span>
						<div class="col-xs-8">
							<span id="binderrortips" class="text-danger"></span>
						</div>
					</div>
            
                    </div>              
                </div>                
            </div>
            <div class="care">
                <span class="cred">*<spring:message code="financial.cnyrewithdrawal.nametip" /></span>
            </div>
            <button id="withdrawUsdtAddrBtn" class="btn mtop2"><spring:message code="financial.cnyrewithdrawal.submit" /></button>

    </div>


</section>


</div>
	
<div class="slet">
  
</div>
	
	
	<input id="feesRate" type="hidden" value="${fee }">
	<input id="userBalance" type="hidden" value="${requestScope.fwallet.ftotal }">
	


<%@include file="../comm/footer.jsp" %>	
	<script src="${oss_url}/static/mobile2018/js/fluckydraw/com.js?v=20181126201750"></script>
	<script type="text/javascript" src="${oss_url}/static/mobile2018/js/msg.js?v=20181126201750"></script>
	<script type="text/javascript" src="${oss_url}/static/mobile2018/js/finance/account.withdraw.js?v=20181126201750"></script>
	<script type="text/javascript" src="${oss_url}/static/mobile2018/js/finance/city.min.js?v=20181126201750"></script>
	<script type="text/javascript" src="${oss_url}/static/mobile2018/js/finance/jquery.cityselect.js?v=20181126201750"></script>


</body>
<script type="text/javascript">
	function showaddbank(){
       $("#top2").css('display','block');
      $("#top1").css('display','none');
    }
    
     $(".backIcon2").click(function(event){
      $("#top1").css('display','block');
      $("#top2").css('display','none');
    });
     $(".backIcon1").click(function(event){
        window.location.href = "/account/withdrawBtc.html";
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
