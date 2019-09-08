<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
if (request.getServerName().equals("www.powex.pro")){basePath="https://www.powex.pro";}
if (request.getServerName().equals("admin.gbcax.com")){basePath="https://admin.gbcax.com";}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${requestScope.constant['webinfo'].ftitle }</title>
<link href="${oss_url}/static/ssadmin/js/themes/css/login.css?v=20181126201750" rel="stylesheet" type="text/css" />

<script type="text/javascript">

<c:if test="${error != null}">
alert("${error}") ;
</c:if>

function sendMsg(button){
	var adminname = document.getElementById("name").value;
	var url = "/buluo718admin/sendMsg.html?random=" + Math.round(Math.random() * 100);
	$.post(url, {
		name : adminname
	}, function(data) {
		if (data.code < 0) {
			alert(data.msg);
		} else if (data.code == 0) {
			button.disabled = true;
		}
	}, "json");
}

</script>

<script type="text/javascript" src="${oss_url}/static/front/js/index/jquery-1.11.2.min.js?v=20181126201750"></script>
</head>
<body>
	<div id="login">
		<div id="login_header">
			<h1 class="login_logo">
				<a href="/"><img src="${oss_url}/static/ssadmin/js/themes/default/images/login_logo.gif" /></a>
			</h1>
			<div class="login_headerContent">
				<div class="navList">

				</div>
				<h2 class="login_title"><img src="${oss_url}/static/ssadmin/js/themes/default/images/login_title.png" /></h2>
			</div>
		</div>
		<script type="text/javascript">
			function validate(f){
				f.src = "/servlet/ValidateImageServlet?"+Math.random() ;
			}
		</script>
		<div id="login_content">
			<div class="loginForm">
				<form action="/buluo718admin/submitLogin_btc718.html" method="post">
					<p>
						<label>用户名：</label>
						<input type="text" name="name" id="name" size="20" class="login_input" />
					</p>
					<p>
						<label>密码：</label>
						<input type="password" name="password" size="20" class="login_input" />
					</p>
					<p>
						<label>谷歌验证：</label>
						<input type="text" name="google" size="20" class="login_input" />
					</p>
					<c:if test="${null!=tokenid}">
					<!-- <p>
						<label>短信验证：</label>
						<input type="text" name="phonecode" size="20" class="login_input" /><a href="javascript:void;" onclick="sendMsg(this)">发送短信</a>
					</p> -->
					<input type="hidden" name="tokenid" id="tokenid" value="${tokenid }" />
					</c:if>
					<p>
						<label>验证码：</label>
						<input class="code" type="text" size="5" name="vcode" />
						<span><img src="/servlet/ValidateImageServlet" alt="" width="75" height="24" onclick="validate(this);"/></span>
					</p>
					<div class="login_bar">
						<input class="sub" type="submit" value=" " />
					</div>
				</form>
			</div>
			<div class="login_banner"><img src="${oss_url}/static/ssadmin/js/themes/default/images/login_banner.jpg" /></div>
			<div class="login_main">
				<ul class="helpList">

				</ul>
				<div class="login_inner">
					<p>实时短信提醒，确保用户账户和资金安全</p>
					<p>比特币钱包多层加密，离线存储，保障资产安全 </p>
					<p>HTTPS高级安全加密协议，客户资料全加密传输，防止通过网络泄漏 ……</p>
				</div>
			</div>
		</div>
		<div id="login_footer">
			${requestScope.constant['webinfo'].fcopyRights }
		</div>
	</div>
</body>
</html>