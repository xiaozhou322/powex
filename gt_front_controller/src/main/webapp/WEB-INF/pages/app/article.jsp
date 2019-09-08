<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>



<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3c.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>${farticle.ftitle }</title>
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="apple-mobile-web-app-capable" content="yes">
<link rel="stylesheet" type="text/css"
	href="/static/app/css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css"
	href="/static/app/css/btcwatch.mobile.css" />
<script type="text/javascript"
	src="/static/app/js/jquery-1.8.2.min.js"></script>
<script type="text/javascript"
	src="/static/app/js/bootstrap.min.js"></script>
<script type="text/javascript"
	src="/static/app/js/respond.min.js"></script>
<style type="text/css">
img{
max-width:100%;height:auto;
}
</style>
</head>

<body>

	<div class="container">
		<div class="m-news-title">${farticle.ftitle }</div>
		<%-- <div class="m-news-info">
			<ul class="list-inline">
				<li class="m-news-source">来源：<a
					href="javascript:void(0);" target="_blank">${appNameNews }</a>
				</li>
			</ul>
		</div> --%>
	</div>
	<hr />
	<div class="container" style="word-break: break-all">
		<div class="m-news-content">
			<div>
				<style>
.article-content-wrap p {
	margin-top: 10px;
	margin-bottom: 18px;
}

.article-content-wrap blockquote {
	padding: 11px 22px;
	margin: 0 0 22px;
	line-height: 28px;
	border-left: 5px solid #ffcd04;
}
</style>
				${farticle.fcontent }
			</div>
		</div>


		<br />

	</div>

</body>