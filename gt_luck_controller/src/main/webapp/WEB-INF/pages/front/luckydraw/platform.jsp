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
<link href="${oss_url}/static/front/css/index/main.css?v=2" rel="stylesheet" type="text/css" />
<link href="${oss_url}/static/front2018/css/lucky.css?v=20181126201750" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="${oss_url}/static/front2018/js/index/main.js?v=20181126201750"></script>
<script type="text/javascript" src="${oss_url}/static/front2018/js/plugin/layer/layer.js?v=20181126201750"></script>
<style type="text/css">
  .lw-helpCons{background:#ffffff;}
  .lw-heilpTabs{background:#f8f8f8;}
  .lw-helpRight{background:#f8f8f8; border:none;}
  .lw-heilpTabs ul li{border: 1px solid #fff;}
  .lw-helpRight h2{color:#333; font-weight:600;}
  .lw-helpNav{height: 80px; line-height:80px; background: #f8f8f8;border-bottom: 1px solid #e3e3e3;}
  .lw-helpNav a{color:#6f6f6f;}
  .lw-helpNav a:hover{background:none; color:#2269d4;}
  .lw-helpNav a.lw-active{background:#fff; color:#6f6f6f; border-bottom: 1px solid #2269d4;}
</style>
</head>
<body style="background: #F9FBFF;">
<%@include file="../comm/header.jsp" %>
<section class="l-content" >
<div class="platformTop">
	<h3>POWEX.PRO</h3>  
	<h4>全新的数字交易平台，为用户提供最友好的平台使用感，运行安全可靠、服务专业高效</h4>  
	<ul>
		<li>
			<label><img src="${oss_url}/static/front2018/images/exchange/Professional2x.png"></label>
			<span>专业</span>
		</li>
		<li>
			<label><img src="${oss_url}/static/front2018/images/exchange/Efficientplat2x.png"></label>
			<span>高效</span>
		</li>
		<li>
			<label><img src="${oss_url}/static/front2018/images/exchange/Friendly2x.png"></label>
			<span>友好</span>
		</li>
	</ul>
</div>

<div class="platformCen">
	<h3 class="platTit">
		<span>平台特色</span>
		<p><img src="${oss_url}/static/front2018/images/exchange/biaotiplatform.png"></p>
	</h3>
	<div class="clear">
		<ul class="platTerrace">
			<li>
				<h3><span>全新的数字资产平台体系</span></h3>
				<p>	
					用户至上，建立投资者保护基金<br>
					为社群账户提供稳定的价值输出 <br>
					项目方直接管理市场，掌握市场情况
				</p>
				<img src="${oss_url}/static/front2018/images/exchange/pingtai2x.png">
			</li>
			<li>
				<h3><span>多重的安全屏障</span></h3>
				<p>	
					采用冷钱包+多签等机制<br>
					分布式架构和防DDOS攻击系统<br>
					实名认证、谷歌验证等多重安保
				</p>
				<img src="${oss_url}/static/front2018/images/exchange/safeplat2x.png">
			</li>
			<li>
				<h3><span>人性化的服务模式</span></h3>
				<p>	
					多语言支持，多币种支持<br>
					问题处理响应能力迅速<br>
					资产流转快速、交易畅通无阻
				</p>
				<img src="${oss_url}/static/front2018/images/exchange/Human2x.png">
			</li>
			<li>
				<h3><span>可靠的一站式服务</span></h3>
				<p>	
					定期与网络安全领域顶级团队合作<br>
					高效撮合引擎提供快捷交易体验<br>
					交易平台自主研发，拥有产权专利
				</p>
				<img src="${oss_url}/static/front2018/images/exchange/saverplat2x.png">
			</li>
		</ul>
	</div>

	<div class="platUser">
		<h3 class="platTit">
			<span>账户权益</span>
			<p><img src="${oss_url}/static/front2018/images/exchange/platform2x.png"></p>
		</h3>
		<ul>
			<li>
				<span><img src="${oss_url}/static/front2018/images/exchange/pt_user2x.png"></span>
				<h3>普通账户</h3>
				<p>
					申请专属社群<br>
					公开保证金制度<br>
					拥有更多的收益方式<br>
					申请项目账号入驻平台
					
				</p>
			</li>
			<li>
				<span><img src="${oss_url}/static/front2018/images/exchange/sq_userplat2x.png"></span>
				<h3>社群账户</h3>
				<p>
					独立域名配置<br>
					返佣收益<br>
					发布公告<br>
					成为社群管理者
				</p>
			</li>
			<li>
			<span><img src="${oss_url}/static/front2018/images/exchange/xm_userplat2x.png"></span>
				<h3>项目账户</h3>
				<p>
					自助式上币 、市场配置<br>
					独立式域名、返佣收益<br>
					多维度数据展示<br>
					平台的专属扶持政策
				</p>
			</li>
		</ul>
	</div>
	<!-- 平台生态基金 -->
	<div class="platfunds">
		<h3 class="platTit">
			<span>平台生态基金</span>
			<p><img src="${oss_url}/static/front2018/images/exchange/platformbiaoti3.png"></p>
		</h3>
		<div class="platDiv">
			POWEX生态是围绕交易平台业务为核心，全球区块链产业上下游所进行的生态投资与合作而形成的生态系统。POWEX.PRO将全力打造全球生态事业部，负责POWEX全球生态战略的规划以及全球范围内的具体落地执行工作。
		</div>
		<p class="platformplat"><img src="${oss_url}/static/front2018/images/exchange/platformplat2x.png"></p>
	</div>
</div>

</section> 
<%@include file="../comm/footer.jsp" %>	

<script type="text/javascript" src="${oss_url}/static/front2018/js/jquery.rotate.min.js?v=20181126201750"></script>
<script type="text/javascript" src="${oss_url}/static/front2018/js/main/scroll.js?v=20181126201750"></script>

</body>
</html>
