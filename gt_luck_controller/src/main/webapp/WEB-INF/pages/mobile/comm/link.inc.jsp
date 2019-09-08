<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<c:choose>
<c:when test="${pageContext.response.locale eq 'zh_CN' }">
<title>${requestScope.constant['webinfo'].ftitle_cn }</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="description" content="${requestScope.constant['webinfo'].fdescription_cn }"/>
<meta name="keywords" content="${requestScope.constant['webinfo'].fkeywords_cn }" />
</c:when>
<c:otherwise>
<title>${requestScope.constant['webinfo'].ftitle }</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="description" content="${requestScope.constant['webinfo'].fdescription }"/>
<meta name="keywords" content="${requestScope.constant['webinfo'].fkeywords }" />
</c:otherwise>
</c:choose>
<link rel="icon" href="${oss_url}/favicon.ico" type="image/x-icon" />
<link rel="shortcut icon" href="${oss_url}/favicon.ico" type="image/x-icon" />
<link href="${oss_url}/static/mobile2018/css/base.css?v=11" rel="stylesheet" type="text/css" />
<link href="${oss_url}/static/mobile2018/css/phone.css?v=2211" rel="stylesheet" type="text/css" />
<link href="${oss_url}/static/mobile2018/icons/iconfont.css?v=1" rel="stylesheet" type="text/css" />
<link href="${oss_url}/static/mobile2018/css/swiper.min.css?v=20171228101259.css?v=20181126201750" rel="stylesheet" type="text/css" />
<script>!function(e){function t(a){if(i[a])return i[a].exports;var n=i[a]={exports:{},id:a,loaded:!1};return e[a].call(n.exports,n,n.exports,t),n.loaded=!0,n.exports}var i={};return t.m=e,t.c=i,t.p="",t(0)}([function(e,t){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var i=window;t["default"]=i.flex=function(e,t){var a=e||100,n=t||1,r=i.document,o=navigator.userAgent,d=o.match(/Android[\S\s]+AppleWebkit\/(\d{3})/i),l=o.match(/U3\/((\d+|\.){5,})/i),c=l&&parseInt(l[1].split(".").join(""),10)>=80,p=navigator.appVersion.match(/(iphone|ipad|ipod)/gi),s=i.devicePixelRatio||1;p||d&&d[1]>534||c||(s=1);var u=1/s,m=r.querySelector('meta[name="viewport"]');m||(m=r.createElement("meta"),m.setAttribute("name","viewport"),r.head.appendChild(m)),m.setAttribute("content","width=device-width,user-scalable=no,initial-scale="+u+",maximum-scale="+u+",minimum-scale="+u),r.documentElement.style.fontSize=a/2*s*n+"px"},e.exports=t["default"]}]);
    flex(100, 1);</script>
<script type="text/javascript">
    if(navigator.userAgent.indexOf('UCBrowser') > -1) {
    document.write('<style id="rootFontSize">html{font-size: 100px !important;}</style>')
    }
</script>
<script type="text/javascript">
(function(){
  function resetFontSize () {
    setTimeout(function(){
      // 设置网页字体为默认大小
      WeixinJSBridge.invoke('setFontSizeCallback', {'fontSize': 0});
    },0);
    // 重写设置网页字体大小的事件
    WeixinJSBridge.on('menu:setfont', function () {
      WeixinJSBridge.invoke('setFontSizeCallback', {'fontSize': 0});
    });
  }
  if (typeof WeixinJSBridge === 'undefined') {
    document.addEventListener('WeixinJSBridgeReady', function (e) {
      resetFontSize();
    });
  } else {
    resetFontSize();
  }
})();

</script>
<script type="text/javascript" src="${oss_url}/static/mobile2018/js/jquery-1.11.2.min.js?v=20181126201750"></script>
<script type="text/javascript" src="${oss_url}/static/mobile2018/js/swiper.min.js?v=20181126201750"></script>
<!-- <script type="text/javascript" src="${oss_url}/static/mobile2018/icons/iconfont.js?v=5"></script> -->
<script type="text/javascript" src="${oss_url}/static/mobile2018/js/captcha/gt.js?v=20181126201750"></script>
<style type="text/css">
      @font-face {
         font-family: 'PingFangMedium';
         src: url('${oss_url}/static/mobile2018/fonts/PingFangMedium.ttf'); 
      } 
  </style>