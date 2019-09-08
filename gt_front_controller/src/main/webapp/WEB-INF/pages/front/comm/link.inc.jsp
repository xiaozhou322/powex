<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
<!-- <link href="https://fonts.googleapis.com/css?family=Open+Sans" rel="stylesheet"> -->
<link href="${oss_url}/static/front2018/css/main.css?v=2018102522165126.css" rel="stylesheet">
<meta name="viewport" content="width=device-width, initial-scale=0.3, minimum-scale=0.1, maximum-scale=1, user-scalable=yes"/>
<script type="text/javascript" src="${oss_url}/static/front2018/js/main/jquery-1.11.2.min.js?v=3"></script>
<link rel="stylesheet" href="${oss_url}/static/front2018/css/common.css?v=2017102522165022.css" type="text/css"></link>
<script type="text/javascript" src="${oss_url}/static/front2018/js/plugin/jquery.min.js?v=20181126201750"></script>
<script type="text/javascript" src="${oss_url}/static/front2018/icons/iconfont.js?v=20171025221650.css"></script>
<script type="text/javascript" src="${oss_url}/static/front2018/js/captcha/gt.js?v=20181126201750"></script>
<!-- <script src="https://cdn.bootcss.com/vue/2.4.2/vue.min.js?v=20181126201750"></script>
<script src="https://cdn.bootcss.com/vue-resource/1.5.1/vue-resource.min.js?v=20181126201750"></script> -->