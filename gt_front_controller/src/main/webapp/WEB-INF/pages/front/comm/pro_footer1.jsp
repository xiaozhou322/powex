<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@include file="include.inc.jsp"%>
<footer class="l-footer" style="position: absolute;">
    
 	<div class="footer_in mg clear" style="padding-top:50px;">
        <div class="footerList">
            <div class="f_list fl">
                <h2><spring:message code="nav.bottom.about" /></h2>
                <ul>
                    <li><a href="/platform/platformIndex.html"><spring:message code="nav.bottom.about" /></a></li>
                    <li><a href="/about/index.html?id=88"><spring:message code="nav.bottom.contact" /></a></li>
                    <li><a href="/about/index.html?id=149"><spring:message code="help.aboutblock" /></a></li>
                </ul>
            </div>
             <c:choose>
            <c:when test="${pageContext.response.locale eq 'zh_CN' }">    
            <div class="f_list fl">
                <h2>新手帮助</h2>
                <ul>
                    <li><a href="/about/index.html?id=57">身份认证</a></li>
                    <li><a href="/about/index.html?id=61">开户指南</a></li>
                    <li><a href="/about/index.html?id=62">找回密码</a></li>
                    <li><a href="/about/index.html?id=147">提现指南</a></li>
                </ul>
            </div>
            <div class="f_list fl">
                <h2>费率与申明</h2>
                <ul>
                    <li><a href="/about/index.html?id=3">费率说明</a></li>
                    <li><a href="/about/index.html?id=4"> 服务条款 </a></li>
                    <li><a href="/about/index.html?id=4"> 法律申明 </a></li>
                    <li><a href="/about/index.html?id=3"><spring:message code="new.more" /></a></li>
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
                    <li><a href="/about/index.html?id=147">Withdrawals Guide</a></li>
                </ul>
            </div>
            <div class="f_list fl">
                <h2>Fee & Agreement</h2>
                <ul>
                    <li><a href="/about/index.html?id=3">Fees Schedule</a></li>
                    <li><a href="/about/index.html?id=4"> Terms of Service </a></li>
                    <li><a href="/about/index.html?id=4"> Legal declaration</a></li>
                    <li><a href="/about/index.html?id=3"><spring:message code="new.more" /></a></li>
                </ul>
            </div>
            </c:otherwise>
         </c:choose> 
            <div class="f_list fl">
                <h2><spring:message code="nav.bottom.contact" /></h2>
                <ul>
                    <li><spring:message code="help.workday" />9:00-21:00</li>
                    <li><spring:message code="help.holiday" />9:00-18:00</li>
                    <li><spring:message code="help.bdmail" />bd@powex.pro</li>
                    <li><spring:message code="help.safety" />security@powex.pro</li>
                    <li><spring:message code="help.customer" />service@powex.pro</li>
                </ul>
            </div>
            <div class="clear"></div>
       </div>
       <div class="f-links">
           <ul class="clear">
               <li>
                    <svg class="icon links_icon" aria-hidden="true">
                      <use xlink:href="#icon-weixin1"></use>
                    </svg>
                   <span class="linksTit">
                       <em><spring:message code="agent.security" /></em>
                       <em>powex_pro_security</em>
                   </span>
                   <span class="linksCodes">
                       <img src="${oss_url}/static/front2018/images/aqcode.jpg" width="100" alt="" />
                   </span>
               </li>               
               <li>
                  <svg class="icon links_icon" aria-hidden="true">
                      <use xlink:href="#icon-weixin1"></use>
                    </svg>
                   <span class="linksTit">
                       <em><spring:message code="agent.management" /></em>
                       <em>powex_pro_assets</em>
                   </span>
                   <span class="linksCodes">
                       <img src="${oss_url}/static/front2018/images/zccode.jpg" width="100" alt="" />
                   </span>
               </li>               
               <li>
                   <svg class="icon links_icon" aria-hidden="true">
                      <use xlink:href="#icon-weixin1"></use>
                    </svg>
                   <span class="linksTit">
                       <em><spring:message code="agent.business" /></em>
                       <em>powex_pro_business</em>
                   </span>
                   <span class="linksCodes">
                       <img src="${oss_url}/static/front2018/images/swcode.jpg" width="100" alt="" />
                   </span>
                 
               </li>               
               <li>
                  <svg class="icon links_icon" aria-hidden="true">
                      <use xlink:href="#icon-qqdenglu"></use>
                    </svg>
                   <span class="linksTit">
                       <em><spring:message code="agent.qqchattit" /></em>
                       <em>945734366</em>
                   </span>
                   <span class="linksCodes">
                       <img src="${oss_url}/static/front2018/images/qqcode.jpg" width="100" alt="" />
                   </span>
               </li>
           </ul>
       </div>
    </div>

    <p class="CopyRight">CopyRight© 2013-2018 POWEX.PRO All Rights Reserved</p>
</footer>
<div class="goTop"><!-- 返回顶部 --></div>
<c:choose>
<c:when test="${pageContext.response.locale eq 'zh_CN' }"> 
	<input type="hidden" name="curlang" id="curlang" value="cn">  
</c:when>
<c:otherwise>
   <input type="hidden" name="curlang" id="curlang" value="en">
   </c:otherwise>
</c:choose> 
<script type="text/javascript" src="${oss_url}/static/front2018/js/plugin/bootstrap.js?v=20181126100022.js"></script>
<script type="text/javascript" src="${oss_url}/static/front2018/js/plugin/layer/layer.js?v=20181126100022.js"></script>
<script type="text/javascript" src="${oss_url}/static/front2018/js/comm/util.js?v=20181126100022.js"></script>
<script type="text/javascript" src="${oss_url}/static/front2018/js/comm/comm.js?v=20181126100022.js"></script>
<script type="text/javascript" src="${oss_url}/static/front2018/js/language/language_<spring:message code="language.title" />.js?v=20181126100022.js"></script>
<script type="text/javascript" src="${oss_url}/static/front2018/js/index/jquery.cookie.js?v=20181126100022.js"></script>
<script type="text/javascript" src="${oss_url}/static/front2018/js/main/main.js?v=8"></script>
