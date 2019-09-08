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
                        <spring:message code="financial.usdtrewithdrawal" />
                    </h2>                        
                </div>
                <div class="tb_address">
                    <div class="area">
                        <div class="changeCoin tx_title">
                            <span class=""><spring:message code="financial.cnyrewithdrawal.addcard" /></span>
                        </div>
                        <div class="tr_txt clear">
                             <span class="tr_l"><spring:message code="financial.cnyrewithdrawal.accname" /></span>
                             <input type="text" id="payeeAddr" class="inpBox"  autocomplete="off" value="${fuser.frealName }" readonly="readonly"/>
                             <br />
                        </div>
                        <div class="tr_txt clear" style="margin:0;">
                            <span class="tr_l clear"></span>
                             <span class="cred fr">*<spring:message code="financial.cnyrewithdrawal.nametip" /></span>
                        </div>                                        
                        <div class="tr_txt">
                             <span class="tr_l"><spring:message code="financial.cnyrewithdrawal.account" /></span>
                             <input type="" id="withdrawAccountAddr" class="inpBox"  />
                        </div>                                        
                        <div class="tr_txt">
                             <span class="tr_l"><spring:message code="financial.cnyrewithdrawal.confirmcard" /></span>
                             <input type="" class="inpBox"  id="withdrawAccountAddr2" />
                        </div>
                        <div class="tr_txt">
                             <span class="tr_l"><spring:message code="financial.cnyrewithdrawal.deposit" /></span>
                             <select id="openBankTypeAddr" class="inpBox form-control">
                                <option value="-1">
                                    <spring:message code="financial.cnyrewithdrawal.choosebank" />
                                </option>
                                <c:forEach items="${bankTypes }" var="v">
                                    <option value="${v.key }">${v.value }</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div id="prov_city" class="tr_txt form-group clear">
                            <label for="prov" class="col-xs-3 control-label tr_l"><spring:message code="financial.cnyrewithdrawal.addr" /></label>
                            <div class="kh_addr fr">
                                <div class="sec_list fl">
                                    <select id="prov" class="form-control">
                                    </select>
                                </div>
                                <div class="sec_list fl">
                                    <select id="city" class="form-control">
                                    </select>
                                </div>
                                <div class="sec_list fl">
                                    <select id="dist" class="form-control prov">
                                    </select>
                                </div>
                                <div class="clear"></div>
                                <div class="col-xs-12 padding-right-clear padding-left-clear">
                                    <input id="address" class="form-control" type="text" autocomplete="off" placeholder="<spring:message code="financial.cnyrewithdrawal.addrtip" />"/>
                                </div>
                            </div>
                        </div>
                        <c:if test="${isBindTelephone == true }">                                        
                        <div class="tr_txt">
                             <span class="tr_l"><spring:message code="security.smscode" /></span>
                             <input type="text" class="inpBox"  id="addressPhoneCode" autocomplete="off"/>
                             <button class="sendCode btn-sendmsg" id="bindsendmessage" data-msgtype="10" data-tipsid="binderrortips"><spring:message code="financial.send" /></button>
                        </div>
                        </c:if>
                       <c:if test="${isBindGoogle ==true}">                                         
                        <div class="tr_txt">
                             <span class="tr_l"><spring:message code="financial.goocod" /></span>
                             <input type="text" class="inpBox" id="addressTotpCode" autocomplete="off"/>
                        </div>
                         </c:if>

                        <div class="tr_txt">
                            <span class="tr_l tr_ts"></span>
                                        <span id="binderrortips" class="cred"></span>
                                </div>                                          
                        <div class="tr_txt">
                             <span class="tr_l"></span>
                             <button class="btn sub mgt30 btn-danger btn-block btnHover" id="withdrawCnyAddrBtn"><spring:message code="financial.cnyrewithdrawal.submit" /></button>
                        </div>
                    </div>                    
                </div>
                
                <div class="tb_addressList">
                    <table>
                        <tr>
                            <th><spring:message code="financial.cnyrewithdrawal.deposit" /></th>
                            <th><spring:message code="financial.cnyrewithdrawal.account" /></th>
                            <th><spring:message code="financial.cnyrewithdrawal.addr" /></th>
                            <th><spring:message code="market.entrustaction" /></th>
                        </tr>
                        <c:forEach items="${bankinfos }" var="v">
                        <tr>
                            <td>${v.fname }</td>
                            <td class="coinAdress">${v.fbankNumber }</td>
                            <td>${v.faddress },${v.fname },${v.fothers }</td>
                            <td><a  class="lightBlue bank-item-del" href="javascript:;" data-fid="${v.fid }" ><spring:message code="financial.delete" /></a></td>
                        </tr>
                        </c:forEach>                         
                    </table>
                </div>

            </div>
        </div>
    </div>
</section>


<%@include file="../comm/footer.jsp" %>	

	<script type="text/javascript" src="${oss_url}/static/front2018/js/comm/msg.js?v=20181126201750"></script>
	<script type="text/javascript" src="${oss_url}/static/front2018/js/finance/account.assets.js?v=20181126201750"></script>
	<script type="text/javascript" src="${oss_url}/static/front2018/js/finance/city.min.js?v=20181126201750"></script>
	<script type="text/javascript" src="${oss_url}/static/front2018/js/finance/jquery.cityselect.js?v=20181126201750"></script>
	<script type="text/javascript" src="${oss_url}/static/front2018/js/index/main.js?v=20181126201750"></script>

</body>
</html>
