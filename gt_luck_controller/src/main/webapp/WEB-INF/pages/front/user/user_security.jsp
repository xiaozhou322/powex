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
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
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


            <!-- <spring:message code="newuser.security.settings" />首页   index -->
            <div class="l_financeR fr tabBtn">
                <h2 class="assetTitle"><spring:message code="newuser.security.settings" /></h2>
                <div class="anquan">
                    <div class="aqMain">
                        <ul>
                            <li class="clear">
                                <div class="aq_tit fl">
                                    <svg class="icon sfont18 i_mgr" aria-hidden="true">
                                        <use xlink:href="#icon-Phone"></use>
                                    </svg>
                                    <span><spring:message code="security.bindphone" /></span>
                                </div>
                                <c:choose>
                                <c:when test="${isBindTelephone ==true}">
                                  <p class="fl discription "><spring:message code="security.bindphonemsg" arguments="${telNumber }" /></p>
                                  <a class="fl cblue2" href="javascript:;" onclick="userauth(1);"><spring:message code="security.tochange" /></a>
                                  <em class="suc"></em>
                                </c:when>
                                <c:otherwise>
                                  <p class="fl discription "><spring:message code="security.nobind" /></p>
                                  <a class="fl cred" href="javascript:;" onclick="userauth(1);"><spring:message code="security.bind" /></a>
                                  <em class="suc noSuc"></em>
                                </c:otherwise>
                                </c:choose>
                            </li>                            
                            <li class="clear">
                                <div class="aq_tit fl">
                                    <svg class="icon sfont18 i_mgr" aria-hidden="true">
                                        <use xlink:href="#icon-youjian"></use>
                                    </svg>
                                    <span><spring:message code="security.bindemail" /></span>
                                </div>
                                <c:choose>
                                <c:when test="${isBindEmail ==true}">
                                  <p class="fl discription"><spring:message code="security.bindemailmsg" arguments="${email }" /></p>
                                  <a class="fl cblue2" href="javascript:;" onclick="userauth(2);"><spring:message code="security.tochange" /></a>
                                  <em class="suc"></em>
                                </c:when>
                                <c:otherwise>
                                   <p class="fl discription "><spring:message code="security.nobind" /></p>
                                  <a class="fl cred" href="javascript:;" onclick="userauth(2);"><spring:message code="security.bind" /></a>
                                  <em class="suc noSuc"></em>
                                </c:otherwise>
                                </c:choose>
                            </li>                            
                            <li class="clear">
                                <div class="aq_tit fl">
                                    <svg class="icon sfont18 i_mgr" aria-hidden="true">
                                        <use xlink:href="#icon-mima"></use>
                                    </svg>
                                    <span><spring:message code="security.logpas" /></span>
                                </div>
                                <c:choose>
                                <c:when test="${isLoginPassword==true}">
                                    <p class="fl discription "><spring:message code="security.thepassis" /></p>
                                    <a class="fl cblue2" href="javascript:;" onclick="userauth(3);"><spring:message code="security.tochange" /></a>
                                    <em class="suc"></em>
                                </c:when>
                                <c:otherwise>
                                    <p class="fl discription "><spring:message code="security.nologinpwd" /></p>
                                    <a class="fl cred" href="javascript:;" onclick="userauth(3);"><spring:message code="security.setup" /></a>
                                    <em class="suc noSuc"></em>
                                </c:otherwise>
                                </c:choose>
                            </li>
                            <li class="clear">
                                <div class="aq_tit fl">
                                    <svg class="icon sfont18 i_mgr" aria-hidden="true">
                                        <use xlink:href="#icon-mima"></use>
                                    </svg>
                                    <span><spring:message code="security.tradingpwd" /></span>
                                </div>
                                <c:choose>
                                <c:when test="${isTradePassword==true}">
                                    <p class="fl discription "><spring:message code="security.whenwithdraw" /></p>
                                    <a class="fl cblue2" href="javascript:;" onclick="userauth(4);"><spring:message code="security.tochange" /></a>
                                    <em class="suc"></em>
                                </c:when>
                                <c:otherwise>
                                    <p class="fl discription "><spring:message code="security.notranpwd" /></p>
                                    <a class="fl cred" href="javascript:;" onclick="userauth(4);"><spring:message code="security.setup" /></a>
                                    <em class="suc noSuc"></em>
                                </c:otherwise>
                                </c:choose>
                            </li>                            
                            <li class="clear">
                                <div class="aq_tit fl">
                                    <svg class="icon sfont18 i_mgr" aria-hidden="true">
                                        <use xlink:href="#icon-shimingrenzheng"></use>
                                    </svg>
                                    <span><spring:message code="security.ideaut" /></span>
                                </div>
                                <c:choose>
                                <c:when test="${isRealValidate ==true}">
                                    <p class="fl discription "><span class="cred">KYC1</span><spring:message code="security.realauthmsg" />/
                                  <c:choose>
                                  <c:when test="${userFaceID != null && userFaceID.status == 101}">
				                     <span class="cred">KYC2</span><spring:message code="security.inaudit" /></p>
				                      <a class="fl cred" href="/user/realCertification.html"><font><spring:message code="security.view" /></a>
				                  </c:when>
				                  <c:when test="${userFaceID != null && userFaceID.status == 102}">
				                     <span class="cred">KYC2</span><spring:message code="security.realauthmsg" /></p>
				                      <a class="fl cred" href="/user/realCertification.html"><font><spring:message code="security.view" /></a>
				                  </c:when>
                                  <c:otherwise>
                                    <span class="cred">KYC2</span><spring:message code="security.foryouacc" /></p>
                                    <a class="fl cred" href="/user/realCertification.html"><font><spring:message code="security.toauth" /></a>
                                  </c:otherwise>
                                  </c:choose>
                                    <em class="suc"></em>
                                </c:when>
                                <c:when test="${!isRealValidate && fuser.fpostRealValidate}">

                                    <p class="fl discription "><span class="cred">KYC1</span><spring:message code="security.inaudit" />/<span class="cred">KYC2</span><spring:message code="security.foryouacc" /></p>
                                    <a class="fl cred" href="/user/realCertification.html"><spring:message code="security.toauth" /></a>
                                    <em class="suc noSuc"></em>
                                </c:when>
                                <c:otherwise>
                                    <p class="fl discription "><span class="cred">KYC1</span><spring:message code="security.foryouacc" />/<span class="cred">KYC2</span><spring:message code="security.foryouacc" /></p>
                                    <a class="fl cred" href="/user/realCertification.html"><spring:message code="security.toauth" /></a>
                                    <em class="suc noSuc"></em>
                                </c:otherwise>
                                </c:choose>
                            </li>                            
                            <li class="clear">
                                <div class="aq_tit fl">
                                    <svg class="icon sfont18 i_mgr" aria-hidden="true">
                                        <use xlink:href="#icon-web__gugeyanzheng"></use>
                                    </svg>
                                    <span><spring:message code="security.gooaut" /></span>
                                </div>
                                <c:choose>
                                <c:when test="${isBindGoogle ==true}">
                                    <p class="fl discription "><spring:message code="security.realauthmsg" /></p>
                                   <%--  <a class="fl cred" href="javascript:;" onclick="userauth(5);"><spring:message code="security.toauth" /></a> --%>
                                    <em class="suc"></em>
                                </c:when>
                                <c:otherwise>
                                    <p class="fl discription "><spring:message code="security.googleauthcan" /></p>
                                    <a class="fl cred" href="javascript:;" onclick="userauth(5);"><spring:message code="security.toauth" /></a>
                                    <em class="suc noSuc"></em>
                                </c:otherwise>
                                </c:choose>
                            </li>

                        </ul>
                    </div>
                </div>
            </div>

            <!-- 修改手机号码 -->
                <div class="l_financeR fr tabBtn" style="display: none">
                <h2 class="assetTitle"><a href="/user/security.html"><spring:message code="newuser.security.settings" /></a> 
                    <svg class="icon sfont16" aria-hidden="true">
                        <use xlink:href="#icon-youjiantouda"></use>
                    </svg>
                    <spring:message code="security.bindphone" />
                 </h2>                
                <div class="anquan accountBd">
                    <h3 class="ts cred">
                        <svg class="icon sfont18" aria-hidden="true">
                            <use xlink:href="#icon-tishi2"></use>
                        </svg>&nbsp;<spring:message code="security.phonemsg" arguments="${loginName }" />
                    </h3>
                    <c:choose>
                    <c:when test="${isBindTelephone ==true}">
                    <div class="aqMain accountBdMain">
                        <div class="accoutType">
                            <div class="area accoutForm modifyPwd">
                                <div class="tr_txt">
                                     <span class="tr_l"><spring:message code="security.oldphone" /></span>
                                     <span>${telNumber}</span>
                                </div>
                                <div class="tr_txt">
                                     <span class="tr_l"><spring:message code="security.smscode" /></span>
                                     <input type="text" id="unbindphone-msgcode" autocomplete="off" class="inpBox" placeholder="<spring:message code="security.smscode" />">
                                     <button class="sendCode btn-sendmsg" id="unbindphone-sendmessage" data-msgtype="3" data-tipsid="unbindphone-errortips"><spring:message code="financial.send" /></button>
                                </div>                                       

                                <div class="tr_txt">
                                     <span class="tr_l"><spring:message code="security.areacountry" /></span>
                                     <select class="inpBox" name="" id="unbindphone-areaCode">
                                         <option value="86"><spring:message code="security.area.chinamainland" /></option>         
                                     </select>
                                </div>  

                                <div class="tr_txt">
                                     <span id="unbindphone-newphone-areacode" class="btn-areacode"  style="left:279px; color:#999; position:absolute; top:8px; height:38px; font-size:14px;">+86</span>
                      				 <span class="tr_l"><spring:message code="security.phone" /></span>
                                     <input type="text" class="inpBox" id="unbindphone-newphone" autocomplete="off"  placeholder="<spring:message code="security.phone" />">
                                </div>                                                                               
                                <div class="tr_txt">
                                     <span class="tr_l"><spring:message code="security.smscode" /></span>
                                     <input type="text" class="inpBox" id="unbindphone-newmsgcode" autocomplete="off" placeholder="<spring:message code="security.smscode" />">
                                     <button class="sendCode btn-sendmsg" id="unbindphone-newsendmessage" data-msgtype="2" data-tipsid="unbindphone-errortips"><spring:message code="financial.send" /></button>
                                </div>
                                <c:if test="${isBindGoogle ==true}">
                                <div class="tr_txt">
                                     <span class="tr_l"><spring:message code="security.goocod" /></span> 
                                     <input type="text" class="inpBox" id="unbindphone-googlecode" autocomplete="off" placeholder="<spring:message code="security.pleentthecode" />">
                                </div> 
                                </c:if>   

                                <div class="tr_txt">
                                     <span class="tr_l"><spring:message code="security.verifycode" /></span>
                                     <input type="text" class="inpBox" id="unbindphone-imgcode" autocomplete="off" placeholder="<spring:message code="security.verifycode" />">
                                     <img class="dyCode2 btn-imgcode"  src="/servlet/ValidateImageServlet?r=<%=new java.util.Date().getTime() %>"></img>
                                </div>
								<!-- 添加人机验证 -无痕验证 -->
								<div class="tr_txt" id="nvc_validate"></div>
                                <div class="tr_txt">
                                  <label for="unbindphone-errortips" class="tr_l tr_ts"></label>
                                   <span id="unbindphone-errortips" class="cred"></span>
                                </div>                                          
                                <div class="tr_txt">
                                     <span class="tr_l"></span>
                                     <button class="btn sub mgt30 loginpwd_btn subBtnList" id="unbindphone-Btn" ><spring:message code="security.submit" /></button>
                                </div>
                            </div>                           
                        </div>
                    </div>
                  </c:when>
                  <c:otherwise>
                        <div class="aqMain accountBdMain">
                        <div class="accoutType">
                            <div class="area accoutForm modifyPwd">
                                <div class="tr_txt">
                                     <span class="tr_l"><spring:message code="security.areacountry" /></span>
                                     <select class="inpBox" name="" id="bindphone-areaCode">
                                         <option value="86"><spring:message code="security.area.chinamainland" /></option>         
                                     </select>
                                </div>  
  
                                <div class="tr_txt">
                                    <span id="bindphone-newphone-areacode" class="btn-areacode"  style="left:279px; color:#999; position:absolute; top:8px; height:38px; font-size:14px;">+86</span>
                      				
                                      <span class="tr_l"><spring:message code="security.phone" /></span>
                                     <input type="text" id="bindphone-newphone" class="inpBox" style="padding-left: 45px;" autocomplete="off" placeholder="<spring:message code="security.phone" />">
                                </div>                                                                               
                                <div class="tr_txt">
                                     <span class="tr_l"><spring:message code="security.smscode" /></span>
                                     <input type="text" class="inpBox" id="bindphone-msgcode" autocomplete="off" placeholder="<spring:message code="security.smscode" />">
                                     <button class="sendCode btn-sendmsg" id="bindphone-sendmessage" data-msgtype="2" data-tipsid="bindphone-errortips"><spring:message code="financial.send" /></button>
                                </div>
                                <c:if test="${isBindGoogle ==true}">
                                <div class="tr_txt">
                                    <span class="tr_l"><spring:message code="security.goocod" /></span>
                                    <input type="text" id="bindphone-googlecode" class="inpBox" autocomplete="off" placeholder="<spring:message code="security.pleentthecode" />">
                                </div>
                                </c:if>        

                                <div class="tr_txt">
                                     <span class="tr_l"><spring:message code="security.verifycode" /></span>
                                     <input type="text" class="inpBox" id="bindphone-imgcode" autocomplete="off" placeholder="<spring:message code="security.verifycode" />">
                                     <img class="dyCode2 btn-imgcode" src="/servlet/ValidateImageServlet?r=<%=new java.util.Date().getTime() %>"></img>
                                </div>
                                <!-- 添加人机验证 -无痕验证 -->
								<div class="tr_txt" id="nvc_validate"></div>
                                 <div class="tr_txt">
                                    <label for="bindphone-errortips" class="tr_l tr_ts"></label>
                                     <span id="bindphone-errortips" class="cred"></span>
                                </div>                                          
                                <div class="tr_txt">
                                     <span class="tr_l"></span>
                                     <button class="btn sub mgt30 loginpwd_btn subBtnList" id="bindphone-Btn"><spring:message code="security.submit" /></button>
                                </div>
                            </div>                           
                        </div>
                    </div>

                  </c:otherwise>
                  </c:choose>
                </div>
            </div>
            

            
            <!-- 修改邮箱 -->

            <div class="l_financeR fr tabBtn" style="display: none">
                <h2 class="assetTitle"><a href="/user/security.html"><spring:message code="newuser.security.settings" /></a> 
                    <svg class="icon sfont16" aria-hidden="true">
                        <use xlink:href="#icon-youjiantouda"></use>
                    </svg>
                    <spring:message code="security.bindemail" />
                </h2>                
                <div class="anquan accountBd">
                    <h3 class="ts cred">
                        <svg class="icon" aria-hidden="true">
                            <use xlink:href="#icon-tishi2"></use>
                        </svg>&nbsp;<spring:message code="security.emailmsg" arguments="${loginName }" />
                    </h3>

                    <div class="aqMain accountBdMain">
                        <div class="accoutType">
                            <div class="area accoutForm modifyPwd">
                                <c:if test="${isBindEmail ==true}">
                                <div class="tr_txt">
                                     <span class="tr_l"><spring:message code="security.oldemail" /></span>
                                     <span>${email}</span>
                                </div>
                                </c:if>
                                <div class="tr_txt">
                                     <span class="tr_l"><spring:message code="security.email" /></span>
                                     <input type="text" class="inpBox" id="bindemail-email"  autocomplete="off">
                                </div>
                                 <div class="tr_txt" >
                                    <label for="bindemail-errortips" class="tr_l tr_ts"></label>
                                     <span id="bindemail-errortips" class="cred"></span> 
                                  </div>     
  
                                <div class="tr_txt">
                                     <span class="tr_l"></span>
                                     <button class="btn sub loginpwd_btn subBtnList" id="bindemail-Btn"><spring:message code="security.submit" /></button>
                                </div>
                            </div>                           
                        </div>
                    </div>
                </div>
            </div>

            <!-- 修改登陆密码 -->


                <div class="l_financeR fr tabBtn" style="display: none">
                <h2 class="assetTitle"><a href="/user/security.html"><spring:message code="newuser.security.settings" /></a> 
                    <svg class="icon sfont16" aria-hidden="true">
                        <use xlink:href="#icon-youjiantouda"></use>
                    </svg>
                    <spring:message code="security.chalogpas" />
                 </h2>                
                <div class="anquan accountBd">
                    <h3 class="ts cred">
                        <svg class="icon sfont18" aria-hidden="true">
                            <use xlink:href="#icon-tishi2"></use>
                        </svg>  <spring:message code="security.dontforget" />
                    </h3>
                    <div class="aqMain accountBdMain">
                        <div class="accoutType">
                            <div class="area accoutForm modifyPwd">
                                <div class="tr_txt">
                                     <span class="tr_l"><spring:message code="security.curpass" /></span>
                                     <input type="password" class="inpBox" id="unbindloginpass-oldpass" placeholder="<spring:message code="security.upper" />">
                                </div>                                       
                                <div class="tr_txt">
                                     <span class="tr_l"><spring:message code="security.newlogpass" /></span>
                                     <input type="password" class="inpBox"  id="unbindloginpass-newpass" placeholder="<spring:message code="security.upper" />">
                                </div>                                        
                                <div class="tr_txt">
                                     <span class="tr_l"><spring:message code="security.confirm" /></span>
                                     <input type="password" class="inpBox" id="unbindloginpass-confirmpass" placeholder="<spring:message code="security.upper" />">
                                </div>
                                <c:if test="${isBindTelephone }">
                                <div class="tr_txt">
                                     <span class="tr_l"><spring:message code="security.smscode" /></span>
                                     <input type="text" class="inpBox" id="unbindloginpass-msgcode" autocomplete="off" placeholder="<spring:message code="security.smscode" />">
                                     <button class="sendCode btn-sendmsg" id="unbindloginpass-sendmessage" data-msgtype="6" data-tipsid="unbindloginpass-errortips"><spring:message code="financial.send" /></button>
                                </div>
                                </c:if> 
                                <c:if test="${isBindGoogle ==true}">
                                <div class="tr_txt">
                                     <span class="tr_l"><spring:message code="security.goocod" /></span>
                                     <input type="text" id="unbindloginpass-googlecode" class="inpBox" autocomplete="off" placeholder="<spring:message code="security.pleentthecode" />">
                                </div>
                                </c:if>
                                <!-- 添加人机验证 -无痕验证 -->
                                <div class="tr_txt" id="nvc_validate"></div>
                                <div class="tr_txt">
                                    <label for="unbindloginpass-errortips" class="tr_l tr_ts control-label"></label>
                                    
                                      <span id="unbindloginpass-errortips" class="cred "></span>
                                    
                                </div>

                                <div class="tr_txt">
                                     <span class="tr_l"></span>
                                     <button class="btn sub mgt30 loginpwd_btn subBtnList" id="unbindloginpass-Btn"><spring:message code="security.submit" /></button>
                                </div>
                            </div>                           
                        </div>
                    </div>
                </div>
            </div>


            <!-- 交易密码 -->
              <div class="l_financeR fr tabBtn" style="display: none">
                <c:if test="${isTradePassword == true }">
                <h2 class="assetTitle"><a href="/user/security.html"><spring:message code="newuser.security.settings" /></a> 
                    <svg class="icon sfont16" aria-hidden="true">
                        <use xlink:href="#icon-youjiantouda"></use>
                    </svg>
                   <spring:message code="security.chatradingpwd" />
                 </h2>                
                <div class="anquan accountBd">
                    <h3 class="ts cred">
                        <svg class="icon sfont18" aria-hidden="true">
                            <use xlink:href="#icon-tishi2"></use>
                        </svg>  <spring:message code="security.whenwithdraw" />
                    </h3>
                    <div class="aqMain accountBdMain">
                        <div class="accoutType">
                            <div class="area accoutForm modifyPwd">
                                <div class="tr_txt">
                                     <span class="tr_l"><spring:message code="security.curtradpass" /></span>
                                     <input type="password" class="inpBox" id="unbindtradepass-oldpass" placeholder="<spring:message code="security.mustbe" />">
                                </div>                                       
                                <div class="tr_txt">
                                     <span class="tr_l"><spring:message code="security.newtrapass" /></span>
                                     <input type="password" class="inpBox" id="unbindtradepass-newpass" placeholder="<spring:message code="security.mustbe" />">
                                </div>                                        
                                <div class="tr_txt">
                                     <span class="tr_l"><spring:message code="security.confirm" /></span>
                                     <input type="password" class="inpBox" id="unbindtradepass-confirmpass" placeholder="<spring:message code="security.mustbe" />">
                                </div>
                                <c:if test="${isBindTelephone }">                                                                               
                                <div class="tr_txt">
                                     <span class="tr_l"><spring:message code="security.smscode" /></span>
                                     <input type="text" class="inpBox" id="unbindtradepass-msgcode" autocomplete="off" placeholder="<spring:message code="security.smscode" />">
                                     <button class="sendCode btn-sendmsg" id="unbindtradepass-sendmessage" data-msgtype="7" data-tipsid="unbindtradepass-errortips"><spring:message code="financial.send" /></button>
                                </div>
                                </c:if>
                                <c:if test="${isBindGoogle ==true}">
                                     <div class="tr_txt">
                                     <span class="tr_l"><spring:message code="security.goocod" /></span>
                                     <input type="password" class="inpBox" id="unbindtradepass-googlecode" autocomplete="off" placeholder="<spring:message code="security.pleentthecode" />">
                                </div>   
                                </c:if>
                                <!-- 添加人机验证 -无痕验证 -->
								<div class="tr_txt" id="nvc_validate"></div>
                                <div class="tr_txt">
                                     <label for="bindtradepass-errortips" class="tr_l tr_ts"></label>
                                      
                                      <span id="unbindtradepass-errortips" class="cred"></span>
                                     
                                </div>                                          
                                <div class="tr_txt">
                                     <span class="tr_l"></span>
                                     <button class="btn sub mgt30 loginpwd_btn subBtnList" id="unbindtradepass-Btn"><spring:message code="security.submit" /></button>
                                </div>
                            </div>                           
                        </div>
                    </div>
                </div>
                </c:if>
                <c:if test="${isTradePassword == false }">
                     <h2 class="assetTitle"><a href="/user/security.html"><spring:message code="newuser.security.settings" /></a> 
                    <svg class="icon sfont16" aria-hidden="true">
                        <use xlink:href="#icon-youjiantouda"></use>
                    </svg>
                   <spring:message code="security.settrapwd" />
                 </h2>                
                <div class="anquan accountBd">
                    <h3 class="ts cred">
                        <svg class="icon sfont18" aria-hidden="true">
                            <use xlink:href="#icon-tishi2"></use>
                        </svg>  <spring:message code="security.whenwithdraw" />
                    </h3>
                    <div class="aqMain accountBdMain">
                        <div class="accoutType">
                            <div class="area accoutForm modifyPwd">
                                <div class="tr_txt">
                                     <span class="tr_l"><spring:message code="security.settradpass" /></span>
                                     <input type="password" class="inpBox" id="bindtradepass-newpass" placeholder="<spring:message code="security.mustbe" />">
                                </div>                                       
                                                                   
                                <div class="tr_txt">
                                     <span class="tr_l"><spring:message code="security.contradpass" /></span>
                                     <input type="password" class="inpBox" id="bindtradepass-confirmpass" placeholder="<spring:message code="security.mustbe" />">
                                </div>


                                <c:if test="${isBindTelephone }">                                                                     
                                <div class="tr_txt">
                                     <span class="tr_l"><spring:message code="security.smscode" /></span>
                                     <input type="text" class="inpBox" id="bindtradepass-msgcode" placeholder="<spring:message code="security.smscode" />" autocomplete="off">
                                     <button class="sendCode btn-sendmsg" id="bindtradepass-sendmessage" data-msgtype="7" data-tipsid="bindtradepass-errortips"><spring:message code="financial.send" /></button>
                                </div>
                                </c:if>
                                <c:if test="${isBindGoogle ==true}">
                                  <div class="tr_txt">
                                     <span class="tr_l"><spring:message code="security.goocod" /></span>
                                     <input type="password" class="inpBox" id="bindtradepass-googlecode" autocomplete="off" placeholder="<spring:message code="security.pleentthecode" />">
                                  </div>
                                </c:if>
                                 <div class="tr_txt" >
                                    <label for="bindtradepass-errortips" class="tr_l tr_ts"></label>
                                    
                                      <span id="bindtradepass-errortips" class="cred"></span>
                                   
                                </div>                                          
                                <div class="tr_txt">
                                     <span class="tr_l"></span>
                                     <button class="btn sub mgt30 loginpwd_btn subBtnList" id="bindtradepass-Btn"><spring:message code="security.submit" /></button>
                                </div>
                            </div>                           
                        </div>
                    </div>
                </div>

                 </c:if>
            </div>



            <!-- 谷歌认证 -->

            <div class="l_financeR fr tabBtn" style="display: none;">
                <h2 class="assetTitle"><a href="/user/security.html"><spring:message code="newuser.security.settings" /></a>
                    <svg class="icon sfont16" aria-hidden="true">
                        <use xlink:href="#icon-youjiantouda"></use>
                    </svg>
                    <spring:message code="security.gooaut" />                  
                </h2>
                <c:if test="${isBindGoogle == false }">
                <div class="anquan google">
                    <div class="googleMain">
                      <dl class="clear">
                            <dt class="fl" id="unbindgoogle-code"><div class="form-qrcode" id="qrcode"></div>
                            </dt>
                            <dd>
                               <spring:message code="new.ggtxt" /><span class='cred' id="bindgoogle-key1"></span>
                                <p class="cred mgt15">
		                            <svg class="icon sfont16" aria-hidden="true">
		                                <use xlink:href="#icon-tishi2"></use>
		                            </svg>
		                            <spring:message code="new.tsmsg" />
		                      	</p>
                            </dd>
                        </dl> 
                       
                    </div>
                    <div class="ggEenable">
                    <span id="bindgoogle-key-retype" ></span>
                         <div class="tr_txt">
                     
                             <span class="tr_l"><spring:message code="security.devname" /></span>
                             <span  class="inpBox" id="bindgoogle-device">${device_name }</span>
                        </div>
                        <div class="tr_txt">
                             <span class="tr_l"><spring:message code="new.Keys" /></span>
                            <span class="inpBox" id="bindgoogle-key"></span>
                        </div>                                
                        <div class="tr_txt">
                            <input id="bindgoogle-key-hide" value="" type="hidden">
                             <span class="tr_l"><spring:message code="new.ggcode" /></span>
                             <input type="text" id="bindgoogle-topcode" class="inpBox"  placeholder="GA code:">
                        </div>
                        <!-- 添加人机验证 -无痕验证 -->
						<div class="tr_txt" id="nvc_validate"></div>
                        <div class="tr_txt">
                          <label for="bindgoogle-errortips" class="tr_l tr_ts"></label>
                          <span id="bindgoogle-errortips" class="cred"></span>  
                        </div> 
                        <button class="btn sub ggBtn google_btn" id="bindgoogle-Btn"><spring:message code="security.auth" /></button>
                    </div>
                </div>
                  </c:if>


                    <c:if test="${isBindGoogle ==true}">
                        <div id="unbindgoogle" class="anquan accountBd">
                            <div class="area accoutForm modifyPwd">
                                <div class="aqMain accountBdMain">      
                                    <div class="tr_txt">
                                        <label for="unbindgoogle-topcode" class="tr_l"><spring:message code="financial.goocod" /></label>
                                        <input id="unbindgoogle-topcode" class="inpBox" type="text" autocomplete="off">
                                    </div>
                                    <div class="tr_txt">
                                      <label for="unbindgoogle-errortips" class="tr_l tr_ts"></label>
                                      <span id="unbindgoogle-errortips" class="cred"></span>
                                    </div>
                                    <div class="tr_txt">
                                        <label for="unbindgoogle-Btn" class="tr_l"></label> 
                                        <button id="unbindgoogle-Btn" class="btn sub loginpwd_btn subBtnList"><spring:message code="security.submit" /></button>
                                        
                                    </div>
                                </div>
                          </div>
                        </div>
                    </c:if>


            </div>



        </div>
    </div>
    

        <!-- 谷歌弹窗认证 -->
    <div class="realfixed ngrealModule">
    	<ul>
    		<h3><img src="${oss_url}/static/front2018/images/close@2x.png" class="realclose"></h3>
    		<li>
    			<p>谷歌认证成功获得1POW奖励</p>
    			<a href="/luckydraw/luckydrawIndex.html">参与平台抽奖</a>
    			<button class="googleBtn">获得更多POW</button>
   			</li>
    	</ul>
    </div>
        <!-- 交易弹窗认证 -->
    <div class="realfixed bindtrademodule">
    	<ul>
    		<h3><img src="${oss_url}/static/front2018/images/close@2x.png" class="bindtradeclose"></h3>
    		<li>
    			<p>成功设置交易密码获得1POW奖励</p>
    			<a href="/luckydraw/luckydrawIndex.html">参与平台抽奖</a>
    			<button class="bindtradeBtn">获得更多POW</button>
   			</li>
    	</ul>
    </div>
    <!--蒙层-->
    <div class="realModule bindtrademodule ngrealModule"></div>
</section>


    
<input type="hidden" id="changePhoneCode" value="${fuser.ftelephone }" />


<input type="hidden" id="isEmptyPhone" value="${isBindTelephone ==true?1:0 }" />
<input type="hidden" id="isTradePassword" value="${isTradePassword }" />	<!-- 判断是否为第一次修改交易密码 -->
<input type="hidden" id="isEmptygoogle" value="${isBindGoogle==true?1:0 }" />
<input id="messageType" type="hidden" value="0" />
<input  type="hidden" id="tab" value="${tab}" />
<%@include file="../comm/footer.jsp" %>
<script type="text/javascript" src="${oss_url}/static/front2018/js/comm/msg.js?v=20181126201750"></script>
<script type="text/javascript" src="${oss_url}/static/front2018/js/plugin/jquery.qrcode.min.js?v=20181126201750"></script>
<script type="text/javascript" src="${oss_url}/static/front2018/js/user/user.security.js?v=13"></script>

<script>
  $(document).ready(function(){
    security.loadgoogleAuth();
    var tab=$("#tab").val();
    if(tab!=null&&tab>0){
    	userauth(tab);
    }
  
  })

  function userauth(id){
    $(".tabBtn").hide();
    $('.tabBtn').eq(id).show();
  }

</script>
<script type="text/javascript">
  $(".modifyBtn").click(function(event) {
    $(".toggleShow").css('display', 'block');
  });
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
