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
<body>
  <%@include file="../comm/header.jsp" %>
<section>
    <div class="mg">
        <div class="l_finance clear">
            <div class=" fl">
                <div class="firstNav">
                     <%@include file="../comm/left_menu.jsp" %>
                </div>
            </div>
            <div class="l_financeR fr">
                <div class="personal_title">
                    <h2 class="assetTitle">
                        <a href="/financial/index.html"><spring:message code="newuser.financial.asset" /></a>
                        <svg class="icon sfont16" aria-hidden="true">
                            <use xlink:href="#icon-youjiantouda"></use>
                        </svg>
                        <spring:message code="new.coinaddress" />
                    </h2>                        
                </div>
                <div class="tb_address">
                    <div class="area">
                        <div class="tr_txt changeCoin">
                            <span class="tr_l"><spring:message code="m.security.selectcoin" /></span>
                            <c:forEach items="${requestScope.constant['allWithdrawCoins'] }" var="v">
                            <c:if test="${v.fid==symbol }">
                            <span class='coinCur'>${v.fShortName }</span>
                            </c:if>
                             </c:forEach>
                            <em class='titTag'>▼</em>
                            <div class="allCoin">
                              <ul>
                              <c:forEach items="${requestScope.constant['allWithdrawCoins'] }" var="v">
                                <li><a href="/financial/accountcoin.html?symbol=${v.fid }">${v.fShortName }</a></li>
                              </c:forEach>
                              </ul>
                            </div>
                        </div>
                        <div class="tr_txt">
                             <span class="tr_l"><spring:message code="financial.witadd" /></span>
                             <input type="text" class="inpBox"  id="withdrawBtcAddr" autocomplete="off"/>
                        </div>                                        
                        <div class="tr_txt">
                             <span class="tr_l"><spring:message code="financial.remarks" /></span>
                             <input type="text" class="inpBox"  id="withdrawBtcRemark" />
                        </div>                                        
                        <div class="tr_txt">
                             <span class="tr_l"><spring:message code="financial.tradingpwd" /></span>
                             <input type="password" class="inpBox"  id="withdrawBtcPass" autocomplete="off"/>
                        </div>
                        <c:if test="${isBindTelephone == true }">                                        
                        <div class="tr_txt">
                             <span class="tr_l"><spring:message code="financial.smscode" /></span>
                             <input type="text" class="inpBox"  id="withdrawBtcAddrPhoneCode" autocomplete="off"/>
                             <button class="sendCode btn-sendmsg" id="bindsendmessage" data-msgtype="8" data-tipsid="binderrortips"><spring:message code="financial.send" /></button>
                        </div>
                        </c:if>
                        <c:if test="${isBindGoogle ==true}">	                                        
                        <div class="tr_txt">
                             <span class="tr_l"><spring:message code="financial.goocod" /></span>
                             <input type="text" class="inpBox" id="withdrawBtcAddrTotpCode" autocomplete="off"/>
                        </div>
                         </c:if>
						<!-- 添加人机验证 -无痕验证 -->
						<div class="tr_txt" id="nvc_validate"></div>
                        <div class="tr_txt">
                            <span class="tr_l tr_ts"></span>
              							<span id="binderrortips" class="cred"></span>
              					</div>                                          
                        <div class="tr_txt">
                             <span class="tr_l"></span>
                             <button class="btn sub mgt30 btn-danger btn-block btnHover" id="withdrawBtcAddrBtn"><spring:message code="financial.submit" /></button>
                        </div>
                    </div>                    
                </div>
                
                <div class="tb_addressList">
                    <table>
                        <tr>
                            <th><spring:message code="new.coin.ti" /></th>
                            <th><spring:message code="financial.witadd" /></th>
                            <th><spring:message code="financial.remarks" /></th>
                            <th><spring:message code="market.entrustaction" /></th>
                        </tr>
                        <c:forEach items="${alls }" var="v">
                        <tr>
                         
                            <td>${v.fvirtualcointype.fShortName }</td>
                            <td class="coinAdress">${v.fadderess }</td>
                            <td>${v.fremark }</td>
                            <td><a  class="lightBlue coin-item-del" href="javascript:;" data-fid="${v.fid }" ><spring:message code="financial.delete" /></a></td>
                        </tr>
                        </c:forEach>                         
                    </table>
                </div>

            </div>
        </div>
    </div>
</section>

<%@include file="../comm/footer.jsp" %>	

	<input type="hidden" id="symbol" value="${symbol }">
	<input type="hidden" value="${coinName }" id="coinName" name="coinName">
	<script type="text/javascript" src="${oss_url}/static/front2018/js/comm/msg.js?v=20181126201750"></script>
	<script type="text/javascript" src="${oss_url}/static/front2018/js/plugin/jquery.qrcode.min.js?v=20181126201750"></script>
	<script type="text/javascript" src="${oss_url}/static/front2018/js/finance/account.assets.js?v=20181126201750"></script>
	
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
