<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>

<!doctype html>
<html>
<head>
<%@ include file="comm/basepath.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="comm/link.inc.jsp"%>
<link rel="stylesheet" href="${oss_url}/static/front/css/index/index.css?v=20181126201750" type="text/css"></link>
</head>
<body class="gray-bg">

<%@include file="comm/header.jsp"%>
	
	
	

	<div class="container-full ">
		<div class="container login-box">
			
			
			<c:if test="${sessionScope.login_user!=null }">
				<div class="login loginsuccess">
					<div class="login-bg loginin"></div>
					<div class="login-cn form-horizontal">
						<div class="form-group">
							<a href="/financial/index.html">
								<h3 class="margin-top-clear font-size-18">${login_user.floginName}</h3>
							</a>
						</div>
						<div class="login-login-cn">
							<div class="form-group">
								<div class="col-xs-12">
									<span class="infobox">
										<span class="info-left">可用人民币</span>
										<span class="info-right">
											￥<ex:DoubleCut
										value="${fwallet.ftotal }" pattern="##.##"
										maxIntegerDigits="15" maxFractionDigits="4" />
										</span>
									</span>
								</div>
								<div class="col-xs-12">
									<span class="infobox">
										<span class="info-left">冻结人民币</span>
										<span class="info-right">
											￥<ex:DoubleCut value="${fwallet.ffrozen }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/>
										</span>
									</span>
								</div>
							</div>
						</div>
						<div class="form-group">
							<div class="col-xs-12">
								<a href="/trade/coin.html" class="btn btn-comin btn-block">进入交易中心</a>
							</div>
						</div>
						<div class="form-group">
							<div class="col-xs-6 btn-right">
								<a href="/account/rechargeCny.html" class="btn btn-addmoney btn-block">充值</a>
							</div>
							<div class="col-xs-6 btn-left">
								<a href="/account/withdrawCny.html" class="btn btn-getmoney btn-block">提现</a>
							</div>
						</div>
					</div>
				</div>
				</c:if>
				
				<c:if test="${sessionScope.login_user==null }">
				<div class="login">
					<div class="login-bg"></div>
					<!-- <div class="login-other-bg"></div> -->
					<div class="login-cn">
						<div class="form-group">
							<h3 class="margin-top-clear" style="font-size: 20px;">登录${requestScope.constant['webName']}</h3>
						</div>
						<div class="form-group">
							<input class="form-control" id="indexLoginName" placeholder="输入邮箱/手机号" type="text" autocomplete="off">
						</div>
						<div class="form-group">
							<input class="form-control" id="indexLoginPwd" placeholder="输入密码" type="password" autocomplete="off">
						</div>
						<div class="form-group has-error">
							<span id="indexLoginTips" class="errortips text-danger help-block"></span>
						</div>
						<div class="form-group">
							<button id="loginbtn" class="btn btn-block btn-danger">
								登录
							</button>
						</div>
						<div class="form-group">
							<a href="/validate/reset.html">忘记密码？</a>
							<a href="/user/register.html" class="pull-right">注册</a>
						</div>
					</div>
					
					<!-- <div class="login-other">
						<a href="/link/qq/call.html">QQ登录</a><span class="split"></span>
						<a href="javascript:weixin();" class="pull-right">微信登录</a>
					</div> -->
				</div>
				</c:if>	
			
		</div>
		<div id="shuffling" class="carousel slide" data-ride="carousel">
			<ol class="carousel-indicators">
				<li data-target="#shuffling" data-slide-to="0" class="active"></li>
				<li data-target="#shuffling" data-slide-to="1"></li>
				<li data-target="#shuffling" data-slide-to="2"></li>
			</ol>
			<div class="carousel-inner">
				<a class="item active" target="_blank" href="${requestScope.constant['bigImageURL1'] }" style="background: url('${requestScope.constant['bigImage1'] }') no-repeat 50% 50%;height: 500px;"></a>
				<a class="item" target="_blank" href="${requestScope.constant['bigImageURL2'] }" style="background: url('${requestScope.constant['bigImage2'] }') no-repeat 50% 50%;height: 500px;"></a>
				<a class="item" target="_blank" href="${requestScope.constant['bigImageURL3'] }" style="background: url('${requestScope.constant['bigImage3'] }') no-repeat 50% 50%;height: 500px;"></a>
			</div>
		</div>
	</div>
	
	
	<div class="container-full notice" style="overflow: hidden;">
		<div class="container text-center" id="newstoplist" style="overflow: hidden; height: 45px; width: auto; color: #ffffff;">
			<div id="newsList">
				<c:forEach items="${requestScope.constant['news']}" var="v">
					<p>
					   <a href="${v.url }" class="notice-item">
							<i class="notice-item-icon"></i>
							${v.ftitle }
						</a>
					</p>
				</c:forEach>
			</div>
		</div>
	</div>
	
	
	
						
				<div class="container-full index market">
					<div class="container">

        
			<div class="trade-navs">
				<div class="trade-tabs">
				<c:forEach var="vv" items="${fMap }" varStatus="vn">
						<span id="${vv.key.fid }_market" class="trade-tab ${vn.index==0?'active':''}" data-key="${vv.key.fid }"  data-max="${fn:length(fMap) }">${vv.key.fShortName }区</span>
				</c:forEach>
				</div>
			</div>
					
						<div class="row market-top text-center">
							<span class="col-xs-2">币种</span>
							 <span class="col-xs-2">最新成交价</span>
							  <span class="col-xs-2">24H成交量</span>
							   <span class="col-xs-2">今日涨跌</span>
							   <span class="col-xs-2 text-center">价格趋势(3日)</span>
							    <span class="col-xs-2"></span>
							   
						</div>
						
		        <c:forEach var="vv" items="${fMap }" varStatus="vn">
		        <c:forEach items="${vv.value }" var="v" varStatus="vs">
						<div class="row market-con ${vv.key.fid }_market_list" style="display: ${vn.index==0?'block':'none'};">				
							
									<a class="text-danger" href="/trade/coin.html?coinType=${v.fid }&tradeType=0">
										<span class="col-xs-2" style="font-size: 15px;">
											<i class="coin-logo" style="background: url(${v.fvirtualcointypeByFvirtualcointype2.furl });background-size:100% 100%; "></i>
											${v.fvirtualcointypeByFvirtualcointype2.fname }
										</span>
									</a>
									<span class="col-xs-2" id="${v.fid }_price">
										--
									</span>
									<span class="col-xs-2" id="${v.fid }_total">
										--
									</span>
									<span class="col-xs-2" id="${v.fid }_rose">-- </span>
									<span class="col-xs-2 text-center">
										<div id="${v.fid }_plot" style="width:100%;height:40px;display: inline-block;float: left;"></div>
									</span>
									<span class="col-xs-2">
										<a class="btn market-trading" href="/trade/coin.html?coinType=${v.fid }&tradeType=0">去交易<i class="triangle-right"></i></a>
									</span>
							
						</div>
						</c:forEach>
				</c:forEach>				
					</div>
				</div>
				
			
	
	<div class="container-full about index">
		<div class="about-bg">
			<div></div>
		</div>
		<div class="container text-center">
			<div class="about-con-box clearfix">
				<div class="col-xs-4 about-item">
					<div class="col-xs-12 about-con">
						<h3 class="text-bigText about-title">安全</h3>
						<span class="about-img security"></span>
						<p class="text-center" style="color:#717171">冷存储、SSL、多重加密 等银行级别安全技术
							十年金融安全经验的安全团队。</p>
					</div>
				</div>
				<div class="col-xs-4 about-item">
					<div class="col-xs-12 about-con">
						<h3 class="text-bigText about-title">可靠</h3>
						<span class="about-img quick"></span>
						<p class="text-center" style="color:#717171">专业级高速撮合引擎 与分布式集群技术
							分布全球数据中心。</p>
					</div>
				</div>
				<div class="col-xs-4 about-item">
					<div class="col-xs-12 about-con">
						<h3 class="text-bigText about-title">用户至上</h3>
						<span class="about-img professional"></span>
						<p class="text-center"  style="margin: 0px; color:#717171">人民币交易免费</p>
						<p class="text-center" style="margin: 0px ; color:#717171">充值提现快速到账，比特币实时到账</p>
						<p class="text-center" style="margin: 0px ; color:#717171">7x24 小时中英双语客服服务</p>



					</div>
				</div>
			</div>
		</div>
	</div>
	

<div class="container-full index news">
		<div class="container">
			<div class="news-items active" style="width: 450px;">
				<div class="news-item-hd text-center">
					<i class="notice-icon"></i>
					<p class="hd1">${articles[0].key.fname}</p>
					<p class="hd2">${articles[0].key.fdescription}</p>
				</div>
				<div class="news-item-cn">
					
					<c:forEach items="${articles[0].value }" var="v" varStatus="n">
						<a class="media" href="/service/article.html?id=${v.fid }">
							<div class="media-left">
									<img src="${v.furl }">
							</div>
							<div class="media-body">
								<h4 class="media-heading">${v.ftitle }</h4>
								${v.fcontent_short }
							</div>
						</a>
					</c:forEach>	
				</div>
			</div>
			
			
			<div class="news-items" style="width: 345px;">
				<div class="news-item-hd text-center">
					<i class="topic-icon"></i>
					<p class="hd1">${articles[1].key.fname}</p>
					<p class="hd2">${articles[1].key.fdescription}</p>
				</div>
				<div class="news-item-cn">
				<c:forEach items="${articles[1].value }" var="v" varStatus="n">	
						<a class="media" href="/service/article.html?id=${v.fid }">
							<div class="media-left">
									<img src="${v.furl }">
							</div>
							<div class="media-body">
								<h4 class="media-heading">${v.ftitle }</h4>
								${v.fcontent_short }
							</div>
						</a>
				</c:forEach>	
				</div>
			</div>
			
			
			<div class="news-items" style="width: 345px;">
				<div class="news-item-hd text-center">
					<i class="reports-icon"></i>
					<p class="hd1">${articles[2].key.fname}</p>
					<p class="hd2">${articles[2].key.fdescription}</p>
				</div>
				<div class="news-item-cn">
					<c:forEach items="${articles[2].value }" var="v" varStatus="n">
						<a class="media" href="/service/article.html?id=${v.fid }">
							<div class="media-left">
									<img src="${v.furl }">
							</div>
							<div class="media-body">
								<h4 class="media-heading">${v.ftitle }</h4>
								${v.fcontent_short }
							</div>
						</a>
                   </c:forEach>
				</div>
			</div>
		</div>
	</div>



	<div class="container-full index contact ">
		<div class="container">
			<div class="contact-con text-center clearfix">
				<div class="top-split"></div>
				<div class="col-xs-2 contact-item">
					<div class="icon-iten">
						<a href="http://wpa.qq.com/msgrd?v=3&uin=${requestScope.constant['serviceQQ'] }&menu=yes" target="_black">
							<i class="icon qq"></i>
						</a>
					</div>
					<p>官方QQ</p>
					<ul>
						<li>
							<a href="http://wpa.qq.com/msgrd?v=3&uin=${requestScope.constant['serviceQQ'] }&menu=yes" target="_black">${requestScope.constant['serviceQQ'] }</a>
						</li>
					</ul>
				</div>
				<div class="col-xs-2 contact-item">
					<div class="icon-iten">
						<i class="icon phone"></i>
					</div>
					<p>24小时热线</p>
					<ul>
						<li>${requestScope.constant['telephone'] }</li>
					</ul>
				</div>
				<div class="col-xs-2 contact-item">
					<div class="icon-iten">
						<i class="icon app"></i>
					</div>
					<p>${requestScope.constant['webName']}APP下载</p>
					<ul>
						<li>安卓、IOS</li>
					</ul>
					<a class="dialog" target="_black" href="/dowload/index.html">
						<i class="dialog-icon"></i>
						<p>点击查看下载APP</p>
					</a>
				</div>
				
				<div class="col-xs-2 contact-item">
					<div class="icon-iten">
						<i class="icon wechat"></i>
					</div>
					<p>官方微信</p>
					<ul>
						<li>${requestScope.constant['weixin'] }</li>
					</ul>
					<div class="dialog">
						<img alt="微信二维码" src="${requestScope.constant['weixinURL'] }">
						<p>扫描关注微信</p>
					</div>
				</div>
				
				<%-- <div class="col-xs-2 contact-item">
					<div class="icon-iten">
						<a href="${requestScope.constant['weiboURL'] }" target="_block">
							<i class="icon weibo"></i>
						</a>
					</div>
					<p>官方微博</p>
					<ul>
						<li>
							<a href="${requestScope.constant['weiboURL'] }" target="_block"> @${requestScope.constant['weiboName'] }</a>
						</li>
					</ul>
					<div class="dialog">
						<img alt="微信二维码" src="${requestScope.constant['weiboImage'] }">
						<p>扫描关注微博</p>
					</div>
				</div> --%>
				
				<div class="col-xs-2 contact-item">
					<div class="icon-iten">
						<i class="icon email"></i>
					</div>
					<p>邮箱地址</p>
					<ul>
						<li>${requestScope.constant['email'] }</li>
					</ul>
				</div>
			</div>
		</div>
	</div>

		<div class="modal modal-custom fade" id="msgdetail" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-mark"></div>
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close btn-modal" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<span class="modal-title" id="exampleModalLabel">${requestScope.constant['webName']}提醒您:</span>
				</div>
				<div class="modal-body">
				<div class="paragraph paragraph_news" style="font-size:14px;text-indent:2em;line-height: 30px;text-align:left;">不投入超过风险承受能力的资金，不投资不了解的数字资产，不听信任何以${requestScope.constant['webName']}名义推荐买币投资的宣传，坚决抵制传销、电信诈骗和洗钱套汇等违法行为。</div> 
				<div class="paragraph paragraph_news" style="font-size:14px;text-indent:2em;line-height: 30px;text-align:left;padding:0 20px"> 近期有部分测试区币种单方面宣传与${requestScope.constant['webName']}进行深度合作，类似宣传均属于虚假宣传，请用户不要听信，${requestScope.constant['webName']}不会与任何此类币种合作。对于违规进行市场活动的币种，${requestScope.constant['webName']}将严肃警告和查处。</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal" style="background-color: #c83935;color: #fff;border-color: #c83935;">我已了解以上风险</button>
				</div>
			</div>
		</div>
	</div>
	
	
	<!--底部开始-->
	<%@include file="comm/footer.jsp" %>
	<input type="hidden" id="errormsg" value= />
<script type="text/javascript" src="${oss_url}/static/front/js/index/index.js?v=20181126201750"></script>
<script type="text/javascript" src="${oss_url}/static/front/js/plugin/jquery.flot.js?v=20181126201750"></script>
</body>
</html>