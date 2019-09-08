<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@include file="include.inc.jsp"%>

<script type="text/javascript" src="${oss_url}/static/mobile2018/js/jquery-1.11.2.min.js?v=20181126201750"></script>
<script type="text/javascript" src="${oss_url}/static/mobile2018/js/main.js?v=51"></script>

<script type="text/javascript" src="${oss_url}/static/mobile2018/js/layer/layer.js?v=20180203164658.js?v=20181126201750"></script>
<script type="text/javascript" src="${oss_url}/static/front/js/index/jquery.cookie.js?v=20180203164658.js?v=20181126201750"></script>


<script type="text/javascript" src="${oss_url}/static/mobile2018/js/comm/util.js?v=20180203164658.js?v=20181126201750"></script>
<script type="text/javascript" src="${oss_url}/static/mobile2018/js/comm/comm.js?v=20180203164658.js?v=20181126201750"></script>
<script type="text/javascript" src="${oss_url}/static/mobile2018/js/language/language_<spring:message code="language.title" />.js?v=20180203164658.js?v=20181126201750"></script>
<script type="text/javascript">
    function previousPage(){
      window.history.go(-1);
    }
</script>
<script type="text/javascript">
	//获取浏览器页面可见高度和宽度
var _PageHeight = document.documentElement.clientHeight;

var   _PageWidth = document.documentElement.clientWidth;

//计算loading框距离顶部和左部的距离（loading框的宽度为74px，高度为74px）
var _LoadingTop = _PageHeight > 74 ? (_PageHeight - 74) / 2 : 0;

var   _LoadingLeft = _PageWidth > 74 ? (_PageWidth - 74) / 2 : 0;



var _LoadingHtml = '<img id="loadingDiv" src="${oss_url}/static/mobile2018/images/load123.gif?v=1" style="width: 74px;height: 74px;margin-top: 15px;position: fixed;top: ' + _LoadingTop + 'px;left: ' + _LoadingLeft + 'px">';
//呈现loading效果
document.write(_LoadingHtml);
window.onload = function() { 
	var loadingMask = document.getElementById('loadingDiv');
};
//监听加载状态改变
document.onreadystatechange = completeLoading;
//加载状态为complete时移除loading效果
function completeLoading() {  
	if (document.readyState == "complete") { 
		var loadingMask = document.getElementById('loadingDiv'); 
		loadingMask.remove(loadingMask);  
	}
}
</script>
<c:choose>
<c:when test="${pageContext.response.locale eq 'zh_CN' }">
 <input type="hidden" name="curlang" id="curlang" value="cn">
</c:when>
<c:otherwise>
  <input type="hidden" name="curlang" id="curlang" value="en">
</c:otherwise>
</c:choose>