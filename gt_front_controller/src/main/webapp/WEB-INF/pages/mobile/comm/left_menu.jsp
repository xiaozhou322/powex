<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="include.inc.jsp"%>

<!-- 用戶中心 -->
<div class="col-xs-2 leftmenu">
	<ul class="nav nav-pills nav-stacked">
		
		<span class="leftmenu-title"> <i class="lefticon user"></i>
			用户中心 </span>
 		<li class=""><a href="/user/api.html">API访问 </a></li>
 		
		<li class=""><a href="/user/security.html"> 基本信息 </a>
		</li>
		<!-- <li class=""><a href="/user/level.html">积分等级 </a>
		</li> -->
		<li class=""><a href="/user/userloginlog.html?type=1"> 登录记录 </a>
		</li>
		<li class=""><a href="/user/message.html">站内消息 </a></li>
		<li class="">
			<a href="/introl/mydivide.html"> 推广记录 </a>
		</li>
		<li><a href="/user/realCertification.html">实名认证 </a>
		</li>
	</ul>
</div>

<!-- 财务中心 -->
<div class="col-xs-2 leftmenu">
	<ul class="nav nav-pills nav-stacked">
		<span class="leftmenu-title top">
			<i class="lefticon financial"></i>
			财务管理
		</span>
		<li class="">
			<a href="/account/rechargeCny.html"> 充值 </a>
		</li>
		<li class="">
			<a href="/account/withdrawCny.html"> 提现 </a>
		</li>
		<li class="">
			<a href="/financial/index.html"> 个人资产 </a>
		</li>
		<li class="">
			<a href="/account/record.html"> 账单明细 </a>
		</li>
		<li class="">
			<a href="/financial/accountbank.html"> 资金帐号 </a>
		</li>
		<li class="">
			<a href="/financial/assetsrecord.html"> 资产记录 </a>
		</li>
	</ul>
</div>

<!-- 交易中心 -->
<div class="col-xs-2 leftmenu">
	<ul class="nav nav-pills nav-stacked ">
	
		<c:forEach var="v" varStatus="vs" items="${ftrademappings }">
			<span class="leftmenu-title leftmenu-folding top"
					data-folding="trademenu${v.fid }"> <i class="lefticon"
					style="background: url('${v.fvirtualcointypeByFvirtualcointype2.furl }') no-repeat 0 0;background-size:100% 100%;"></i>
					${v.fvirtualcointypeByFvirtualcointype2.fShortName }(${v.fvirtualcointypeByFvirtualcointype1.fShortName })交易 </span>
				<li class=" trademenu${v.fid }" style="display:${ftrademapping.fid!=v.fid?'none':''}"><a
					href="/trade/coin.html?coinType=${v.fid }&tradeType=0"> 买入/卖出 </a></li>
				<li class=" trademenu${v.fid }" style="display:${ftrademapping.fid!=v.fid?'none':''}"><a
					href="/trade/entrust.html?symbol=${v.fid }&status=0"> 委托管理 </a></li>
				<li class=" trademenu${v.fid }" style="display:${ftrademapping.fid!=v.fid?'none':''}"><a
					href="/trade/entrust.html?symbol=${v.fid }&status=1"> 交易记录 </a></li>
		</c:forEach>
	</ul>
</div>

<script type="text/javascript">
var leftpath = window.location.pathname;
	var path = window.location.href.replace('http://'+window.location.host,'') ;
	if(leftpath.startWith("/trade/")){//交易中心特殊处理

	var count = 0 ;
	var isshow = false;
	$(".leftmenu").each(function(){
			var flag = false ;
			$(this).find("a").each(function(){
				if(path.indexOf($(this).attr("href")) != -1){
					$(this).parent().addClass("active") ;
					flag = true ;
					count++ ;
				}
			}) ;
			if(flag == false ){
				$(this).remove();
			}
	}) ;

	//if(count==0){
//		$(".leftmenu").each(function(){
//				var flag = false ;
//				$(this).find("a").each(function(){
//					if($(this).attr("href").startWith(leftpath)){
//						if(isshow == false ){
//							$(this).parent().addClass("active") ;
//						}
//						flag = true ;
//						isshow = true ;
//						count++ ;
//					}
//				}) ;
//				if(flag == false ){
//					$(this).remove();
//				}
//		});
	//}
//				
				
	}else{//除交易中心的其他菜单
	var left = "${leftMenu}";
		$(".leftmenu").each(function(){
				
				var flag = false ;
				$(this).find("a").each(function(){
					if($(this).attr("href").indexOf(leftpath) != -1){
						$(this).parent().addClass("active") ;
						flag = true ;
					}else{
					   if(($(this).attr("href").indexOf("/account/rechargeCny") != -1
					    || $(this).attr("href").indexOf("/account/proxyCode") != -1
					    || $(this).attr("href").indexOf("/account/payCode") != -1)
					     && left == "recharge"){
					      $(this).parent().addClass("active") ;
					      flag = true ;
					   }else  if($(this).attr("href").indexOf("/account/withdrawCny") != -1 && left == "withdraw"){
					      $(this).parent().addClass("active") ;
					      flag = true ;
					   }else  if($(this).attr("href").indexOf("/financial/accountbank") != -1 && left == "accountAdd"){
					      $(this).parent().addClass("active") ;
					      flag = true ;
					   } else  if($(this).attr("href").indexOf("/divide/") != -1  && left == "divide" ){
					      $(this).parent().addClass("active") ;
					      flag = true ;
					   } else  if($(this).attr("href").indexOf("/crowd/") != -1  && left == "mylogs" ){
					      $(this).parent().addClass("active") ;
					      flag = true ;
					   }
					}
				}) ;
				if(flag == false){
					$(this).remove();
				}
		}) ;
	}
</script>