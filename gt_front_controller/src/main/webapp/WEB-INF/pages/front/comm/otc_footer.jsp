<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@include file="include.inc.jsp"%>
<footer class="l-footer">
    <%-- <div class="footer_in mg clear">
        <div class="footerList">
            <div class="f_list fl">
                <h2><spring:message code="nav.bottom.about" /></h2>
                <ul>
                    <li><a href="/about/index.html?id=2"><spring:message code="nav.bottom.about" /></a></li>
                    <li><a href="/about/index.html?id=88"><spring:message code="nav.bottom.contact" /></a></li>
                    <li><a href="/about/index.html?id=149"><spring:message code="help.aboutblock" /></a></li>
                </ul>
            </div>
             <c:choose>
            <c:when test="${pageContext.response.locale eq 'zh_CN' }"> 
            <input type="hidden" name="curlang" id="curlang" value="cn">      
            <div class="f_list fl">
                <h2>新手帮助</h2>
                <ul>
                    <li><a href="/about/index.html?id=57">身份认证</a></li>
                    <li><a href="/about/index.html?id=61">开户指南</a></li>
                    <li><a href="/about/index.html?id=62">找回密码</a></li>
                    <li><a href="/about/index.html?id=63">提现指南</a></li>
                </ul>
            </div>
            </c:when>
           <c:otherwise>
              <div class="f_list fl">
                <h2>Help</h2>
                <ul>
                    <li><a href="/about/index.html?id=57">ID Authentication</a></li>
                    <li><a href="/about/index.html?id=61">Registration Guide</a></li>
                    <li><a href="/about/index.html?id=62">Retrieve password</a></li>
                    <li><a href="/about/index.html?id=63">Withdrawals Guide</a></li>
                </ul>
            </div>
            </c:otherwise>
         </c:choose> 
           <input type="hidden" name="curlang" id="curlang" value="en">             
            <div class="f_list fl">
                <h2><spring:message code="help.coinintro" /></h2>
                <ul>
                    <li><a href="/about/index.html?id=99">Ethereum</a></li>
                    <li><a href="/about/index.html?id=100"> Qtum </a></li>
                    <li><a href="/about/index.html?id=101"> miseGo </a></li>
                    <li><a href="/about/index.html?id=99"><spring:message code="new.more" /></a></li>
                </ul>
            </div>        
            <div class="f_list fl">
                <h2><spring:message code="nav.bottom.contact" /></h2>
                <ul>
                    <li><spring:message code="help.workday" />9:00-21:00</li>
                    <li><spring:message code="help.holiday" />9:00-18:00</li>
                    <li><spring:message code="help.bdmail" />bd@gbcax.com</li>
                    <li><spring:message code="help.customer" />service@gbcax.com</li>
                    <li><spring:message code="help.finan" />finance@gbcax.com</li>
                </ul>
            </div>
            <div class="clear"></div>
       </div>
       <div class="f-links">
           <ul class="clear">
               <li>
                   <svg class="icon links_icon" aria-hidden="true">
                      <use xlink:href="#icon-twitter-circle"></use>
                    </svg>
                   <span class="linksTit">
                       <em>twitter</em>
                      <a href="https://twitter.com/gbcax">https://twitter.com/gbcax</a>
                   </span>
               </li>               
               <li>
                   <svg class="icon links_icon" aria-hidden="true">
                      <use xlink:href="#icon-telegram"></use>
                    </svg>
                   <span class="linksTit">
                       <em>Telegram</em>
                       <a href="http://t.me/gbcaxcom">http://t.me/gbcaxcom</a>
                   </span>
              
               </li>               
               <li>
                   <svg class="icon links_icon" aria-hidden="true">
                      <use xlink:href="#icon-facebook3"></use>
                    </svg>
                   <span class="linksTit">
                       <em>facebook</em>
                       <a href="https://www.facebook.com/gbcax">https://www.facebook.com/gbcax</a>
                   </span>
                 
               </li>               
               <li>
                   <svg class="icon links_icon" aria-hidden="true">
                      <use xlink:href="#icon-weixin1"></use>
                    </svg>
                   <span class="linksTit">
                       <em><spring:message code="agent.wechattit" /></em>
                       <em><spring:message code="agent.wechat" />：bucaige718</em>
                   </span>
                   <span class="linksCodes">
                       <img src="/static/gbcax2018/images/wxcode.png" width="100" alt="" />
                   </span>
               </li>
           </ul>
       </div>
    </div> --%>
    <p class="CopyRight">CopyRight© 2013-2017 WTOEX.com All Rights Reserved</p>
</footer>
<div class="goTop"><!-- 返回顶部 --></div>

<script type="text/javascript" src="${oss_url}/static/front2018/js/plugin/bootstrap.js?v=20171025221650.js"></script>
<script type="text/javascript" src="${oss_url}/static/front2018/js/plugin/layer/layer.js?v=20171025221650.js"></script>
<script type="text/javascript" src="${oss_url}/static/front2018/js/comm/util.js?v=20171025221650.js"></script>
<script type="text/javascript" src="${oss_url}/static/front2018/js/comm/comm.js?v=20171025221650.js"></script>
<script type="text/javascript" src="${oss_url}/static/front2018/js/language/language_<spring:message code="language.title" />.js?v=2017102522162.js"></script>
<script type="text/javascript" src="${oss_url}/static/front2018/js/index/jquery.cookie.js?v=20171026105823.js"></script>
<script type="text/javascript" src="${oss_url}/static/front2018/js/main/main.js?v=4"></script>
