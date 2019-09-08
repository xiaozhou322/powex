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
<title><spring:message code="m.security.usercontent" /></title>
<style type="text/css">
    .brnnerIndex{
   	    background: url(/static/mobile2018/images/user_bg.png) no-repeat center;
	    background-size: cover;
	    height: 2.3rem;
	    box-shadow: 0px 2px 9px 0px rgba(70, 90, 111, 0.14);
    }
    .loginbtn{
	    margin: 0.19rem 0.3rem 0 0;
	    line-height: 0.3rem;
    }
    .loginbtn .icon-logout,.loginbtn a{color:#fff;}
    .brnnerDl{text-align: center;}
    .brnnerDl .user_name{font-size: 0.28rem;color: #fff;line-height: 0.4rem;}
    .brnnerDl .userId{font-size: 0.28rem;color: #FFFFFF;}
    .imgUser{width: 0.72rem;height: 0.72rem;}
    .securityLi{    border-bottom: 2px solid #f5f7ff;width: 89%;}
    .icon_more{      
        width: 0.15rem;
    height: 0.3rem;
    margin-top: 0.28rem;
    position: absolute;
    right: 0.03rem;}
</style>    
</head>
<body>
<div class="myset" id="securityIndex">
    <div class="brnnerIndex">
	    <div class="clear">
	        <div class="fr loginbtn">
	           <i class="iconfont icon-logout"></i>
	           <a href="/user/logout.html" style=" vertical-align: -0.02rem; "><spring:message code="nav.top.logout" /></a>
	        </div>
	    </div>
	    <div class="clear brnnerDl">
	        <dl class="clear">
	            <dt><img src="${oss_url}/static/mobile2018/images/user.png" class="imgUser" /></dt>
	            <dd>
	                <p class="user_name">${fuser.frealName}</p>
	                <span class='userId'>UID:${fuser.fid }</span>
	            </dd>
	        </dl>
	    </div>
    </div>
    
    <div class="myNav_List">
        <ul>
            <li>
               <!-- <i class="iconfont sfont50 fl icon-zichanxinxi" style="padding-top: 0.12rem;"></i> -->
               <span class="iconImg"><img src="${oss_url}/static/mobile2018/images/iconfont1.png" alt="" /></span>
                <a href="/financial/index.html">
                    <spring:message code="newuser.financial.asset" />
                    <span class="iconmore"><img src="${oss_url}/static/mobile2018/images/icon_more.png" alt="" /></span>
                </a>
            </li>
            <li>
                <span class="iconImg"><img src="${oss_url}/static/mobile2018/images/iconfont2.png" alt="" /></span>
                <a href="/account/record.html?recordType=0">
                    <spring:message code="newuser.financial.records" />
                    <span class="iconmore"><img src="${oss_url}/static/mobile2018/images/icon_more.png" alt="" /></span>
                </a>
            </li>           
            <li>
                <span class="iconImg"><img src="${oss_url}/static/mobile2018/images/iconfont3.png" alt="" /></span>
                <a href="/introl/mydivide.html">
                    <spring:message code="newuser.financial.benefits" />
                    <span class="iconmore"><img src="${oss_url}/static/mobile2018/images/icon_more.png" alt="" /></span>
                </a>
            </li>
           	<c:if test="${fuser.fid==366665}">
            <li>
                <span class="iconImg"><img src="${oss_url}/static/mobile2018/images/iconfont2.png" alt="" /></span>
	                <a href="/feesConvert/queryFeesConvertList.html">手续费结算
                    <span class="iconmore"><img src="${oss_url}/static/mobile2018/images/icon_more.png" alt="" /></span>
                </a>
            </li>
            </c:if>
        </ul>
        <ul>
            <li>
                <span class="iconImg"><img src="${oss_url}/static/mobile2018/images/iconfont4.png" alt="" /></span>
                <a href="/trade/entrust.html?symbol=13&status=0">
                    <spring:message code="agent.orderdetails" />
                    <span class="iconmore"><img src="${oss_url}/static/mobile2018/images/icon_more.png" alt="" /></span>
                </a>
            </li>
            <li>
                <span class="iconImg"><img src="${oss_url}/static/mobile2018/images/iconfont5.png" style="width: 0.34rem;" /></span>
                <a href="/financial/accountcoin.html">
                    <spring:message code="new.recharge" />
                    <span class="iconmore"><img src="${oss_url}/static/mobile2018/images/icon_more.png" alt="" /></span>
                </a>
            </li>
         </ul>

         <ul>   
            <li class="tosecuity">
               <span class="iconImg"><img src="${oss_url}/static/mobile2018/images/iconfont6.png" style=" height: 0.35rem;" /></span>
                <a href="javascript:;">
                    <spring:message code="security.security" />
                    <span class="iconmore"><img src="${oss_url}/static/mobile2018/images/icon_more.png" alt="" /></span>
                </a>
            </li>
             <li>
                <span class="iconImg"><img src="${oss_url}/static/mobile2018/images/iconfont7.png" alt="" /></span>
                <a href="/about/index.html?id=88">
                    <spring:message code="m.security.help" />
                    <span class="iconmore"><img src="${oss_url}/static/mobile2018/images/icon_more.png" alt="" /></span>
                </a>
            </li>
             <li>
                <span class="iconImg"><img src="${oss_url}/static/mobile2018/images/iconfont8.png" alt="" /></span>
                <a href="/platform/platformIndex.html">
                    <spring:message code="about.platform" />
                    <span class="iconmore"><img src="${oss_url}/static/mobile2018/images/icon_more.png" alt="" /></span>
                </a>
            </li>
        </ul>
    </div>
    <%@ include file="../comm/tabbar.jsp"%>
</div>

<!-- 安全中心主页 -->
<div class="security" id="tabarTwo" style="display: none">
    <%-- <header class="tradeTop tradTop_1">
        <span class="back"></span>
        <span><spring:message code="security.security" /></span>
    </header> --%>
    <header class="tradeTop">  
	    <i class="back toback2"></i>
	    <h2 class="tit"><spring:message code="security.security" /></h2>
	</header>
    <div class="anquan_bg"><img src="${oss_url}/static/mobile2018/images/anqun.jpg" alt="" /></div>
    <div class="securityMain">
        <ul>
            <li class="clear" onclick="changeTab('bindphone','<spring:message code="security.bindphone" />');">
            	<i class="iconfont fl sfont50 li_color1 icon-shoujisel"></i>
                <div class="tit fl securityLi">
                    <span><spring:message code="security.bindphone" /></span>
                </div>
                    <img src="${oss_url}/static/mobile2018/images/icon_more.png" class="icon_more" />
            
                 <!--  <a href="javascript:;" class="fr"> <em class='arr'>></em></a> -->
             
            </li>            
            <li class="clear" onclick="changeTab('bindemail','<spring:message code="security.bindemail" />');">
                <i class="iconfont fl sfont50 li_color2 icon-youjian"></i>
                <div class="tit fl securityLi">
                    <span><spring:message code="security.bindemail" /></span>
                </div>
                    <img src="${oss_url}/static/mobile2018/images/icon_more.png" class="icon_more" />
                <!--  <a href="javascript:;" class="fr"> <em class='arr'>></em></a> -->
            </li>  
            </ul>
            <ul>         
            <li class="clear" onclick="window.location.href='/user/realCertification.html'">
                <i class="iconfont fl sfont50 icon-76 li_color3"></i>
                <div class="tit fl securityLi">
                    <span><spring:message code="security.ideaut" /></span>
                </div>
                    <img src="${oss_url}/static/mobile2018/images/icon_more.png" class="icon_more" />
                 <!-- <a href="javascript:;" class="fr"><em class='arr'>></em></a> -->
            </li>            
            <li class="clear" onclick="changeTab('bindgoogle','<spring:message code="security.setgooauth" />');">
                <i class="iconfont fl sfont50 li_color4 icon-gugegoogle114"></i>
                <div class="tit fl securityLi">
                    <span><spring:message code="security.gooaut" /></span>
                </div>
                     <img src="${oss_url}/static/mobile2018/images/icon_more.png" class="icon_more" />
                <!--  <a href="javascript:;" class="fr"> <em class='arr'>></em></a> -->
            </li> 
            </ul>
            <ul>           
            <li class="clear" onclick="changeTab('editLoginpwd','<spring:message code="security.chalogpas" />');">
                <i class="iconfont fl sfont50 li_color5 icon-11"></i>
                <div class="tit fl securityLi">
                    <span><spring:message code="security.logpas" /></span>
                </div>
                    <img src="${oss_url}/static/mobile2018/images/icon_more.png" class="icon_more" />
                <!--  <a href="javascript:;" class="fr"> <em class='arr'>></em></a> -->
            </li>            
            <li class="clear" onclick="changeTab('editTradepwd','<spring:message code="security.chatradingpwd" />');">
                <i class="iconfont fl sfont50 li_color6 icon-11"></i>
                <div class="tit fl securityLi">
                    <span><spring:message code="security.tradingpwd" /></span>
                </div>
                    <img src="${oss_url}/static/mobile2018/images/icon_more.png" class="icon_more" />
                <!--  <a href="javascript:;" class="fr"> <em class='arr'>></em></a> -->
            </li>
        </ul>
    </div>
   <%--  <%@ include file="../comm/tabbar.jsp"%> --%>
</div>

<!-- 修改手机号码 -->
<div class="recharge withdraw" style="display: none;" id="bindphone">
    <header class="tradeTop">
        <span class="soback toback2"></span>
        <span class="tit"></span>

    </header>
    <div class="addAdressCon">
    <c:if test="${isBindTelephone ==false}">
            <div class="withdrawalList">
                    <div class="login_item clear " style="display: none">
                        <label for="" class="login_item_l"><spring:message code="security.areacountry" /></label>
                        <div class="login_item_r">
                        <select class="select select4" id="bindphone-areaCode">
                            <option value="86" selected=""><spring:message code="security.area.chinamainland" /></option>
                        </select>
                        </div>
                    </div>
                    <span id="bindphone-newphone-areacode" class="btn btn-areacode"  style="left:1px;top:1px; height:38px; font-size:14px;display: none">+86</span>               
                  <div class="login_item clear">
                      <label for="" class="login_item_l fl"><spring:message code="security.phone" /></label>
                      <div class="login_item_r fl">
                          <input type="text" id="bindphone-newphone" autocomplete="off" placeholder="<spring:message code="security.phone" />">
                      </div>
                  </div>      
                  <div class="login_item clear">
                      
                      <span class="yzCode"><img src="/servlet/ValidateImageServlet?r=<%=new java.util.Date().getTime() %>" class="dynamicCode btn-imgcode"/></span>
                      <div class="login_item_r fl">
                          <input type="text" id="bindphone-imgcode" autocomplete="off" placeholder="<spring:message code="security.verifycode" />">
                      </div>
                  </div>      
                  <div class="login_item clear relative">
                      <label for="" class="login_item_l fl"><spring:message code="security.smscode" /></label>
                      <div class="login_item_r fl">
                          <input type="text" id="bindphone-msgcode" autocomplete="off" placeholder="<spring:message code="security.smscode" />">
                          <span  id="bindphone-sendmessage" data-msgtype="2" data-tipsid="bindphone-errortips" class="sendCode btn-sendmsg"><spring:message code="financial.send" /></span>
                      </div>
                      
                  </div>
                  <c:if test="${isBindGoogle ==true}">
                    <div class="login_item clear">
                      <label for="" class="login_item_l fl"><spring:message code="security.goocod" /></label>
                      <div class="login_item_r fl">
                          <input type="text" id="bindphone-googlecode" autocomplete="off" placeholder="<spring:message code="security.goocod" />">
                      </div>
                  </div> 
                  </c:if>
                  <!-- 添加人机验证 -无痕验证 -->
				  <div class="login_item clear" id="nvc_validate"></div>
                   <div class="login_item remind clear">
                        <label for="bindphone-errortips" class="login_item_l"></label>
                        <div class="login_item_r"> <span id="bindphone-errortips" class="text-danger colorTit"></span> </div>
                  </div>
                  <button class="btn add_btn" id="bindphone-Btn"><spring:message code="security.submit" /></button>          
            </div>
        </c:if> 
        <c:if test="${isBindTelephone ==true}">
            <div class="withdrawalList" > <!-- 已绑定手机 -->
                  <div class="login_item clear">
                      <label for="" class=" fl"><spring:message code="security.oldphone" /></label>
                      <div class=" fl">
                          <p class="cred">&nbsp;&nbsp;${telNumber}</p>
                      </div>
                  </div>
                  <div class="login_item clear " style="display: none;">
                      <label for="" class="login_item_l"><spring:message code="security.areacountry" /></label>
                      <div class="login_item_r">
                      <select class="select select4" id="bindphone-areaCode">
                          <option value="86" selected><spring:message code="security.area.chinamainland" /></option>
                      </select>
                      </div>
                </div>
                <span id="unbindphone-newphone-areacode" class="btn btn-areacode"  style="left:1px;top:1px; height:38px; font-size:14px;display: none">+86</span> 
                  <div class="login_item clear relative">
                      <label for="" class="login_item_l fl"><spring:message code="security.smscode" /></label>
                      <div class="login_item_r fl">
                        <input type="text" id="unbindphone-msgcode"  autocomplete="off" placeholder="<spring:message code="security.smscode" />">
                        <span id="unbindphone-sendmessage" data-msgtype="3" data-tipsid="unbindphone-errortips" class="sendCode btn-sendmsg"><spring:message code="financial.send" /></span>
                      </div>
                      
                  </div>              
                  <div class="login_item clear">
                      <label for="" class="login_item_l fl"><spring:message code="security.newphone" /></label>
                      <div class="login_item_r fl">
                        <input type="text" id="unbindphone-newphone" autocomplete="off" placeholder="<spring:message code="m.security.phone" />">
                      </div>
                  </div>      
                  <div class="login_item clear">
                      <label for="" class="login_item_l fl"><spring:message code="security.smscode" /></label>
                      <div class="login_item_r fl">
                        <input type="text" id="unbindphone-newmsgcode" autocomplete="off" placeholder="<spring:message code="security.smscode" />">
                        <span id="unbindphone-newsendmessage" data-msgtype="2" data-tipsid="unbindphone-errortips" class="sendCode btn-sendmsg"><spring:message code="financial.send" /></span>
                      </div>
                      
                  </div>
                  <c:if test="${isBindGoogle ==true}">
                    <div class="login_item clear">
                        <label for="unbindphone-googlecode" class="login_item_l"><spring:message code="security.goocod" /></label>
                        <div class="login_item_r fl">
                          <input id="unbindphone-googlecode" type="text" autocomplete="off" placeholder="<spring:message code="security.goocod" />">
                      </div>
                    </div>
                     </c:if>      
                  <div class="login_item clear relative">
                    
                    <div class="login_item_r fl">
                       <label for="unbindphone-googlecode" class="login_item_l"><spring:message code="security.verifycode" /></label>
                        <input type="text" id="unbindphone-imgcode" autocomplete="off" placeholder="<spring:message code="security.verifycode" />">
                        <span class="yzCode bdPhone_yz"><img src="/servlet/ValidateImageServlet?r=<%=new java.util.Date().getTime() %>" class="dynamicCode btn-imgcode"/></span>
                    </div>
                  </div>     
                  <!-- 添加人机验证 -无痕验证 -->
				  <div class="login_item clear" id="nvc_validate"></div>
                  <div class="login_item remind clear">
                        <label for="bindphone-errortips" class="login_item_l"></label>
                        <div class="login_item_r"> <span id="unbindphone-errortips" class="text-danger colorTit"></span> </div>
                  </div>
            
         </div>
         <button class="btn add_btn" id="unbindphone-Btn"><spring:message code="security.submit" /></button>
          </c:if> 
      </div>
      </div>



<!-- 修改邮箱 -->

<div class="recharge withdraw" style="display: none;" id="bindemail">
    <header class="tradeTop">
        <span class="soback toback2"></span>
        <span class="tit"></span>
    </header>
    <div class="addAdressCon">

            <div class="withdrawalList" > 
            <c:if test="${isBindEmail ==true}">   
                  <div class="login_item clear">
                      <label for="" class=" fl"><spring:message code="security.oldemail" /></label>
                      <div class=" fl">
                          <p class="cred">&nbsp;&nbsp;${email}</p>
                      </div>
                  </div>
              </c:if> 
                  <div class="login_item clear relative">
                   <c:choose>
                  <c:when test="${isBindEmail ==true}"> 
                      <label for="" class="login_item_l fl"><spring:message code="m.security.changeemail" /></label>
                  </c:when>
                  <c:otherwise>
                  <label for="" class="login_item_l fl"><spring:message code="m.security.bindemail" /></label>
                  </c:otherwise> 
                  </c:choose>
                      <div class="login_item_r fl">
                          <input type="text" id="bindemail-email" autocomplete="off" placeholder="<spring:message code="m.security.yesemail" />">
                      </div>
                  </div>              
    
            </div>
             <div class="login_item login_item_show clear" style="padding-bottom:0;display: none">
                        <label for="bindemail-errortips" class="login_item_l"></label>
                        <div class="login_item_r"> <span id="bindemail-errortips" class="text-danger"></span> </div>
              </div>   
            <button class="btn add_btn" id="bindemail-Btn"><spring:message code="security.submit" /></button>
    </div>
</div>



<!-- 登陆密码 -->

<div class="recharge withdraw" id="editLoginpwd" style="display: none">
    <header class="tradeTop">
        <span class="soback toback2"></span>
        <span class="tit"></span>
    </header>
    <div class="addAdressCon">
            <div class="withdrawalList">               
                  <div class="login_item clear">
                      <label for="" class="login_item_l fl"><spring:message code="security.curpass" /></label>
                      <div class="login_item_r fl">
                          <input type="password" placeholder="<spring:message code="security.upper" />" id="unbindloginpass-oldpass">
                      </div>
                  </div>      
                  <div class="login_item clear">
                      <label for="" class="login_item_l fl"><spring:message code="security.newlogpass" /></label>
                      <div class="login_item_r fl">
                          <input type="password" id="unbindloginpass-newpass" placeholder="<spring:message code="security.upper" />">
                      </div>
                  </div>      
                  <div class="login_item clear">
                      <label for="" class="login_item_l fl"><spring:message code="security.confirm" /></label>
                      <div class="login_item_r fl">
                          <input type="password" id="unbindloginpass-confirmpass" placeholder="<spring:message code="security.upper" />">
                      </div>
                  </div>
                  <c:if test="${isBindTelephone }">   
                   <div class="login_item clear">
                      <label for="" class="login_item_l fl"><spring:message code="security.smscode" /></label>
                      <div class="login_item_r fl">
                        <input type="text" autocomplete="off" placeholder="<spring:message code="security.smscode" />" id="unbindloginpass-msgcode">
                        <span id="unbindloginpass-sendmessage" data-msgtype="6" data-tipsid="unbindloginpass-errortips" class="sendCode btn-sendmsg"><spring:message code="financial.send" /></span>
                      </div>
                      
                  </div>
                  </c:if>
                  <c:choose>
                    <c:when test="${isBindGoogle ==true}">                    
                  <div class="login_item clear">
                      <label for="" class="login_item_l fl"><spring:message code="security.goocod" /></label>
                      <div class="login_item_r fl">
                          <input id="unbindloginpass-googlecode" type="text" autocomplete="off" placeholder="<spring:message code="security.goocod" />">
                      </div>
                  </div>
                  </c:when>
                 <c:otherwise>
                  <c:if test="${isBindTelephone==false}">
                      <div class="login_item clear">
                              <label for="" class="login_item_l fl"><spring:message code="security.goocod" /></label>
                              <div class="login_item_r fl">
                                  <a class="cblue2" href="/user/security.html?tab=4"><spring:message code="financial.checkgoogle" /></a>
                              </div>
                  </div>
                </c:if>
                  </c:otherwise>
                </c:choose>
     			<!-- 添加人机验证 -无痕验证 -->
				<div class="login_item clear" id="nvc_validate"></div>
            </div> 
            <button class="btn add_btn" id="unbindloginpass-Btn"><spring:message code="security.submit" /></button>
    </div>
</div>



<!-- 修改交易密码 -->

<div class="recharge withdraw" id="editTradepwd" style="display: none">
    <header class="tradeTop">
        <span class="soback toback2" ></span>
        <span class="tit"></span>
    </header>
    <c:if test="${isTradePassword == true }">
    <div class="addAdressCon">
            <div class="withdrawalList">               
                  <div class="login_item clear">
                      <label for="" class="login_item_l fl"><spring:message code="security.curtradpass" /></label>
                      <div class="login_item_r fl">
                          <input type="password" id="unbindtradepass-oldpass" autocomplete="off" placeholder="<spring:message code="security.mustbe" /> ">
                      </div>
                  </div>      
                  <div class="login_item clear">
                      <label for="" class="login_item_l fl"><spring:message code="security.newtrapass" /></label>
                      <div class="login_item_r fl">
                          <input type="password" id="unbindtradepass-newpass" placeholder="<spring:message code="security.mustbe" />">
                      </div>
                  </div>
                  <div class="login_item clear">
                      <label for="" class="login_item_l fl"><spring:message code="security.confirm" /></label>
                      <div class="login_item_r fl">
                          <input type="password" id="unbindtradepass-confirmpass" placeholder="<spring:message code="security.mustbe" />">
                      </div>
                  </div>
                  <c:if test="${isBindTelephone }">   
                   <div class="login_item clear">
                      <label for="" class="login_item_l fl"><spring:message code="security.smscode" /></label>
                      <div class="login_item_r fl">
                        <input type="text" autocomplete="off" placeholder="<spring:message code="security.smscode" />" id="unbindtradepass-msgcode">
                        <span id="unbindtradepass-sendmessage" data-msgtype="7" data-tipsid="unbindtradepass-errortips" class="sendCode btn-sendmsg"><spring:message code="financial.send" /></span>
                      </div>
                      
                  </div>
                  </c:if>
                  <c:choose>
                  <c:when test="${isBindGoogle ==true}">                                 
                  <div class="login_item clear">
                      <label for="" class="login_item_l fl"><spring:message code="security.goocod" /></label>
                      <div class="login_item_r fl">
                          <input type="text" autocomplete="off" placeholder="<spring:message code="security.goocod" />" id="unbindtradepass-googlecode">
                      </div>
                  </div>
                   </c:when>
                <c:otherwise>
                <c:if test="${isBindTelephone==false}">
                         <div class="login_item clear">
                              <label for="" class="login_item_l fl"><spring:message code="security.goocod" /></label>
                              <div class="login_item_r fl">
                                  <a class="cblue2" href="/user/security.html?tab=4"><spring:message code="financial.checkgoogle" /></a>
                              </div>
                        </div>
                    </c:if>
                   </c:otherwise>
                </c:choose>
                <!-- 添加人机验证 -无痕验证 -->
				<div class="login_item clear" id="nvc_validate"></div>     
            </div> 
            <button class="btn add_btn" id="unbindtradepass-Btn"><spring:message code="security.submit" /></button>
    </div>
 </c:if>

<c:if test="${isTradePassword == false }">
     <div class="addAdressCon">
            <div class="withdrawalList">               
                  <div class="login_item clear">
                      <label for="" class="login_item_l fl"><spring:message code="security.settradpass" /></label>
                      <div class="login_item_r fl">
                          <input type="password" id="bindtradepass-newpass" placeholder="<spring:message code="security.mustbe" />">
                      </div>
                  </div>      
                  <div class="login_item clear">
                      <label for="" class="login_item_l fl"><spring:message code="security.contradpass" /></label>
                      <div class="login_item_r fl">
                          <input type="password" id="bindtradepass-confirmpass" placeholder="<spring:message code="security.mustbe" />">
                      </div>
                  </div>
                  <c:if test="${isBindTelephone }">   
                   <div class="login_item clear">
                      <label for="" class="login_item_l fl"><spring:message code="security.smscode" /></label>
                      <div class="login_item_r fl">
                        <input type="text" autocomplete="off" placeholder="<spring:message code="security.smscode" />" id="bindtradepass-msgcode">
                        <span id="bindtradepass-sendmessage" data-msgtype="7" data-tipsid="bindtradepass-errortips" class="sendCode btn-sendmsg"><spring:message code="financial.send" /></span>
                      </div>
                      
                  </div>
                  </c:if>

                   <c:choose>
                  <c:when test="${isBindGoogle ==true}">                           
                   <div class="login_item clear">
                      <label for="" class="login_item_l fl"><spring:message code="security.goocod" /></label>
                      <div class="login_item_r fl">
                          <input type="text" autocomplete="off" placeholder="<spring:message code="security.goocod" />" id="bindtradepass-googlecode">
                      </div>
                  </div>
                  </c:when>
                  <c:otherwise>
                  <c:if test="${isBindTelephone==false}">
                    <div class="login_item clear">
                              <label for="" class="login_item_l fl"><spring:message code="security.goocod" /></label>
                              <div class="login_item_r fl">
                                  <a class="cblue2" href="/user/security.html?tab=4"><spring:message code="financial.checkgoogle" /></a>
                              </div>
                  </div>
                   </c:if> 
                  </c:otherwise>
                  </c:choose>   
                  <!-- 添加人机验证 -无痕验证 -->
				  <div class="login_item clear" id="nvc_validate"></div>  
            </div> 
            <button class="btn add_btn" id="bindtradepass-Btn"><spring:message code="security.submit" /></button>
    </div>
   </c:if>
</div>



<!-- 谷歌认证 -->
<div class="recharge withdraw" id="bindgoogle" style="display: none;">
   <header class="tradeTop" >
        <span class="soback toback2" id="sob"></span>
        <span class="back toback2" style="display: none" id="sotab"></span>
        <span class="tit"></span>
    </header>
    <div style="background: #fff;">
	     <c:if test="${isBindGoogle == false }">
		    <div class="rechargeMain">
		        <div class="cbCode">
		            <div class="code" id="unbindgoogle-code">
		                <div class="form-qrcode" id="qrcode"></div>
		            </div>
		        </div>
		    </div>
		    
		    <div class="ggYz">
	        <div class="yzSteps">
	            <p><spring:message code="new.ggtxt" /><em class='cred' id="bindgoogle-key">1D546R1G5</em></p> 
	        </div>
	        <p class="careTxt cred"><spring:message code="new.tsmsg" /></p>
	         <div class="ggCon">
	            <div class="gg_tr clear"><span class="fl"><spring:message code="security.devname" /></span><em id="bindgoogle-device">${device_name }</em></div>
	            <div class="gg_tr clear"><span class="fl"><spring:message code="security.goocod" /></span><input id="bindgoogle-key-hide" value="" type="hidden"><input class="inptt fl" type="text"  id="bindgoogle-topcode" /></div>
		        <!-- 添加人机验证 -无痕验证 -->
				<div class="gg_tr clear" id="nvc_validate"></div>
	        </div>
	        <div class="gg_btn"><button class="btn" id="bindgoogle-Btn"><spring:message code="security.auth" /></button></div>
	    </div>
	    </c:if>
	
	    <c:if test="${isBindGoogle ==true}">
      
          <div class="login_item clear">
                      <label for="" class="login_item_l fl"><spring:message code="security.goocod" /></label>
                      <div class="login_item_r fl">
                          <input type="password" id="unbindgoogle-topcode" autocomplete="off">
                      </div>
                  </div>
      <div class="gg_btn"><button class="btn" id="unbindgoogle-Btn"><spring:message code="security.submit" /></button></div>
     </c:if>
	</div>
</div>



<input type="hidden" id="changePhoneCode" value="${fuser.ftelephone }" />
<input type="hidden" id="isEmptyPhone" value="${isBindTelephone ==true?1:0 }" />
<input type="hidden" id="isEmptygoogle" value="${isBindGoogle==true?1:0 }" />
<input id="messageType" type="hidden" value="0" />



<%@ include file="../comm/footer.jsp"%>
<script src="${oss_url}/static/mobile2018/js/fluckydraw/com.js?v=20181126201750"></script>
<script type="text/javascript" src="${oss_url}/static/mobile2018/js/msg.js?v=20181126201750"></script>
<script type="text/javascript" src="${oss_url}/static/mobile2018/js/plugin/jquery.qrcode.min.js?v=20181126201750"></script>
<script type="text/javascript" src="${oss_url}/static/mobile2018/js/language/language_<spring:message code="language.title" />.js?v=20171025221650.js"></script>
<script type="text/javascript" src="${oss_url}/static/mobile2018/js/user.security.js?v=123"></script>
<script type="text/javascript">
    //切换语言
    function langs(lang)
    {
      var Url = window.location.href;
      if(Url.indexOf('?') == -1){
        Url = Url + "?"+ lang;
        window.location.href = Url;
        return ;
      }else{
        if(Url.indexOf('lang') == -1){
          Url = Url + "&" + lang;
          window.location.href = Url;
          return ;
        }else{
          if(Url.indexOf(lang) == -1){
            var nums = Url.indexOf("lang=");
            var danx = Url.substring(nums, nums+10);
            Url = Url.replace(danx,lang);
            window.location.href = Url;
            return ;
           
          }else{
            return;
          }
        }
      }

    }
</script>
<script type="text/javascript">
  // $(".securityMain li").each(function(index, el) {
  //     var nums=index*-1.6
  //     $(this).children('i').css('background-position', '0'+nums+'rem');
  // });

  // $(".lw-navList .list").each(function(index, el) {
  //     var nums2=index*-34
  //     $(this).children('i').css('background-position', nums2+'px 0');
  // });
  $(function(){
      var tabnums = window.location.href;
      var nums = tabnums.indexOf("tab=");
      var tab = tabnums.substr(nums+4, 3);
      if(tab == 2){
        $("#securityIndex").css('display','none');
        $("#tabarTwo").css('display','block');
      }
       if(tab == 4){
       $('#securityIndex,#tabarTwo,#sob').css('display','none');
       $('#bindphone,#bindemail,#securityIndex,#editTradepwd,#editLoginpwd').css('display','none');
       $('#bindgoogle').css('display','block');
       $(".tit").html('谷歌验证');
       $("#sotab").css('display','block');
       
     }
  });

  $(".tosecuity,.soback").click(function(){
      window.location.href = "/user/security.html?tab=2";
  });
  function changeTab(val,name){

    $('#securityIndex').css('display','none');
    $("#tabarTwo").css('display','none');
    document.getElementById(val).style.display='block';
    $(".tit").html(name);

  }



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

</body>
</html>
