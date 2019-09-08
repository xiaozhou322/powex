<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ include file="../comm/include.inc.jsp"%>

<!DOCTYPE html>
<html lang="zh-CN">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>后台系统</title>
<link rel="stylesheet" href="css/common.css?v=20181126201750" />
<link rel="stylesheet" href="css/main.css?v=20181126201750" />
    <link rel="stylesheet" href="css/itemadmin.css?v=20181126201750" />
    <style>
    	body{
		    background: url(images/bgadmin.png) no-repeat;
		    min-width: 1300px;
		    height: 100%;
		    background-size: 100% auto;
    	}
    </style>
    </head>
 <body>

	<input id="locale" value="${pageContext.response.locale}" type="hidden"/>
<div>
        <%@include file="../comm/header.jsp"%>
</div>
	<div class="proCenter">
		<div class="navCenter">
			<ul class="navList">
			<li>
				<a href="currency.html">
					<p><img src="images/bizhong_def.png"></p>
					<p>币种管理</p>
				</a>
			</li>
			<li>
				<a>
					<p><img src="images/shichang_def.png"></p>
					<p>市场管理</p>
				</a>
			</li>
			<li>
				<a>
					<p><img src="images/zichan_def.png"></p>
					<p>资产管理</p>
				</a>
			</li>
		</ul>
		
			<ul class="navList">
				<li>
					<a>
						<p><img src="images/gonggao_def.png"></p>
						<p>公告管理</p>
					</a>
				</li>
				<li>
					<a>
						<p><img src="images/peizhi_sel.png"></p>
						<p>配置管理</p>
					</a>
				</li>
				<li>
					<a>
						<p><img src="images/tongji_def.png"></p>
						<p>统计中心</p>
					</a>
				</li>
			</ul>
		</div>		
	</div>
  </body>
</html>