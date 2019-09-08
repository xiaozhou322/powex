<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../comm/include.inc.jsp"%>
<%

%>

<!doctype html>
<html>
<head> 
<%@ include file="../comm/basepath.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name = "viewport" content = "width = device-width, user-scalable = no,initial-scale=0.5,maximum-scale=0.5,minimum-scale=0.5">
<%@include file="../comm/link.inc.jsp" %>
<link href="${oss_url}/static/front/css/index/common.css?v=20181126201750" rel="stylesheet" type="text/css" />
<link href="${oss_url}/static/front/css/index/main.css?v=20181126201750" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${oss_url}/static/front/js/index/main.js?v=20181126201750"></script>

<link rel="stylesheet" href="${oss_url}/static/front/css/api/apidoc.css?v=20181126201750" type="text/css"></link>
</head>
<body>

 


<%@include file="../comm/header.jsp" %>
	<div class="container-full body-main" >
		<div class="container"style="padding:50px 0 50px 0">
			<div class="doc-item">
				<div class="title">行情API</div>
				<div class="description">获取最新市场行情数据</div>
				<div class="list">
					<div class="list-title">
						<span>接口</span><span>描述</span>
					</div>
					<div class="list-list">
						<div class="list-list-title">
							<span>POST /appApi.html?action=depth</span><span>获取深度数据</span>
						</div>
						<div class="list-detail">
							<p>BTC /appApi.html?action=depth&symbol=1</p>
							<div class="title mini">示例</div>
							<pre>
# Request 
POST BTC /appApi.html?action=depth&symbol=1
# Response
{
  "code": 200,
  "msg": "获取深度数据",
  "time": 1486203073756,
  "data": {
    "date": 1478758913,
    "asks": [
      [
        5003.68,
        0.275
      ]
    ],
    "bids": [
      [
        4903.66,
        0.075
      ]
    ]
  }
}</pre>
							<div class="title mini">返回值说明</div>
							<pre>
date : 返回数据时服务器时间
asks : 卖方深度
bids : 买方深度</pre>
							<div class="title mini">请求参数名</div>
							<div class="list-title">
								<span>参数名</span><span>描述</span>
							</div>
							<div class="list-list">
								<span>symbol</span><span>币种：1(比特币对人民币)</span>
							</div>
							<div class="list-list">
								<span>size</span><span>深度：最大支持10(默认10)</span>
							</div>
						</div>
					</div>
					<div class="list-list">
						<div class="list-list-title">
							<span>POST /appApi.html?action=kline</span><span>获取K线数据</span>
						</div>
						<div class="list-detail">
							<p>BTC /appApi.html?action=kline&symbol=1&step=60</p>
							<div class="title mini">示例</div>
							<pre>
# Request 
POST /appApi.html?action=kline&symbol=1&step=60
# Response
{
  "code": 200,
  "msg": "获取K线数据",
  "time": 1486203073756,
  "data": "[[1478746261695,4939.32,4939.97,4938.74,4939.58,446.9005]]"
}</pre>
							<div class="title mini">返回值说明</div>
							<pre>
[
	1478746261695,		时间戳
	4939.32,		开
	4939.97,		高
	4938.74,		低
	4939.58,		收
	446.9005		交易量
]</pre>
							<div class="title mini">请求参数名</div>
							<div class="list-title">
								<span>参数名</span><span>描述</span>
							</div>
							<div class="list-list">
								<span>symbol</span><span>币种：1(比特币对人民币)、ics_cny(小企股)</span>
							</div>
							<div class="list-list">
								<span>step</span><span>60(1m), 60*3(3m),60*5(5m),60*15(15m),60*30(3m),60*60(1h),60*60*2(2h),60*60*4(4h),60*60*6(6h),<br>60*60*12(12h),60*60*24(1d),60*60*24*3(3d),60*60*24*7(1w)</span>
							</div>
						</div>
					</div>
					<div class="list-list">
						<div class="list-list-title">
							<span>POST /appApi.html?action=market</span><span>获取实时行情</span>
						</div>
						<div class="list-detail">
							<p>BTC /appApi.html?action=market&symbol=1</p>
							<div class="title mini">示例</div>
							<pre>
# Request 
POST /appApi.html?action=market&symbol=1
# Response
{
  "code": 200,
  "msg": "获取实时行情",
  "time": 1486203073756,
  "data": {
    "high": 4962.03,
    "vol": 1633159.5247,
    "last": 4899.26,
    "low": 4876,
    "buy": 4898.93,
    "sell": 4899.26
  }
}</pre>
							<div class="title mini">返回值说明</div>
							<pre>
high: 最高价
vol: 成交量(24小时)
last: 最新成交价
low: 最低价
buy: 买一价
sell: 卖一价
</pre>
							<div class="title mini">请求参数名</div>
							<div class="list-title">
								<span>参数名</span><span>描述</span>
							</div>
							<div class="list-list">
								<span>symbol</span><span>币种：1(比特币对人民币)</span>
							</div>
						</div>
					</div>
					<div class="list-list">
						<div class="list-list-title">
							<span>POST /appApi.html?action=trades</span><span>获取最新成交数据</span>
						</div>
						<div class="list-detail">
							<p>BTC /appApi.html?action=trades?&symbol=1</p>
							<div class="title mini">示例</div>
							<pre>
# Request 
POST /appApi.html?action=trades&symbol=1
# Response
{
  "code": 200,
  "msg": "获取最新成交记录",
  "time": 1486203073756,
  "data": [
    {
      "amount": 1.7931,
      "price": 4906.49,
      "id": 1,
      "time": "15:59:24",
      "en_type": "ask",
      "type": "卖出"
    }
}</pre>
							<div class="title mini">返回值说明</div>
							<pre>
amount: 交易数量
price: 交易价格
id: 数据ID
time: 交易时间
en_type: 交易类型 ask/bid
type: 交易类型 卖出/买入
</pre>
							<div class="title mini">请求参数名</div>
							<div class="list-title">
								<span>参数名</span><span>描述</span>
							</div>
							<div class="list-list">
								<span>symbol</span><span>币种：1(比特币对人民币)</span>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="doc-item padding-top-30">
				<div class="title">交易API</div>
				<div class="description">用于快速进行交易</div>
				<div class="list">
					<div class="list-title">
						<span>接口</span><span>描述</span>
					</div>
					<div class="list-list">
						<div class="list-list-title">
							<span>/user/api.html</span><span>API授权方式说明</span>
						</div>
						<div class="list-detail">
							<div class="title mini">说明</div>
							<pre>

> 1. 获取API认证的apiKey和secretKey
申请API即可获得apiKey和secretKey，其中apiKey是提供给API用户的访问密钥，secretKey用于对请求参数签名的私钥。
 注意： 请勿向任何人泄露这两个参数，这两个参数关乎账号安全。
> 2. 生成待签名字符串
用户提交的参数除sign外，都要参与签名。
待签名字符串要求按照参数名进行排序（首先比较所有参数名的第一个字母，按abcd顺序排列，若遇到相同首字母,则看第二个字母, 以此类推。）
例如：对于如下的参数进行签名 string[] parameters={"api_key=c821db84-6fbd-11e4-a9e3-c86000d26d7c"，"symbol=btc_cny","type=buy","price=680","amount=1.0"};
 生成待签名字符串为：amount=1.0&api_key=c821db84-6fbd-11e4-a9e3-c86000d26d7c&price=680&symbol=btc_cny&type=buy
> 3. MD5签名
在MD5签名时,需要私钥secretKey参与签名。
将待签名字符串添加私钥参数生成最终待签名字符串，
例如：amount=1.0&api_key=c821db84-6fbd-11e4-a9e3-c86000d26d7c&price=680&symbol=btc_cny&type=buy&secret_key=secretKey 注意“&secret_key=secretKey” 为签名必传参数。
利用32位MD5算法 对最终待签名字符串进行签名运算,从而得到签名结果字符串(该字符串赋值于参数 sign)，MD5计算结果中字母全部大写。
</pre>
						
						</div>
					</div>
					 
					<div class="list-list">
						<div class="list-list-title">
							<span>POST /appApi.html?action=trade</span><span>委托下单</span>
						</div>
						<div class="list-detail">
							<p>BTC /appApi.html?action=trade&symbol=1&type=buy&amount=1&price=5000</p>
							<div class="title mini">示例</div>
							<pre>
# Request 
POST /appApi.html?action=trade&symbol=1&type=buy&amount=1&price=5000
# Response
{
  "code": 200,
  "msg": "委托成功",
  "time": 1486203073756,
  "data": null
}</pre>
							<div class="title mini">返回值说明</div>
							<pre>
code : 返回码
msg  : 返回消息
time : 时间戳(ms)
data : 携带数据</pre>
							<div class="title mini">请求参数名</div>
							<div class="list-title">
								<span>参数名</span><span>描述</span>
							</div>
							<div class="list-list">
								<span>symbol</span><span>币种：1(比特币对人民币)</span>
							</div>
							<div class="list-list">
								<span>type</span><span>委托类型：0-买单、1-卖单</span>
							</div>
							<div class="list-list">
								<span>amount</span><span>委托数量</span>
							</div>
							<div class="list-list">
								<span>price</span><span>委托价格</span>
							</div>
						</div>
					</div>
					<div class="list-list">
						<div class="list-list-title">
							<span>POST /appApi.html?action=cancel_entrust</span><span>撤单</span>
						</div>
						<div class="list-detail">
							<p>BTC /appApi.html?action=cancel_entrust?id=1</p>
							<div class="title mini">示例</div>
							<pre>
# Request 
POST /appApi.html?action=cancel_entrust?id=1
# Response
{
  "code": 200,
  "msg": "撤单成功",
  "time": 1486203073756,
  "data": null
}</pre>
							<div class="title mini">返回值说明</div>
							<pre>
code : 返回码
msg  : 返回消息
time : 时间戳(ms)
data : 携带数据</pre>
							<div class="title mini">请求参数名</div>
							<div class="list-title">
								<span>参数名</span><span>描述</span>
							</div>
							<div class="list-list">
								<span>id</span><span>订单id</span>
							</div>
						</div>
					</div>
					 
					<div class="list-list">
						<div class="list-list-title">
							<span>POST /appApi.html?action=trade</span><span>委托下单</span>
						</div>
						<div class="list-detail">
							<p>BTC /appApi.html?action=trade&symbol=1&type=buy&amount=1&price=5000</p>
							<div class="title mini">示例</div>
							<pre>
# Request 
POST /appApi.html?action=trade&symbol=1&type=buy&amount=1&price=5000
# Response
{
  "code": 200,
  "msg": "委托成功",
  "time": 1486203073756,
  "data": null
}</pre>
							<div class="title mini">返回值说明</div>
							<pre>
code : 返回码
msg  : 返回消息
time : 时间戳(ms)
data : 携带数据</pre>
							<div class="title mini">请求参数名</div>
							<div class="list-title">
								<span>参数名</span><span>描述</span>
							</div>
							<div class="list-list">
								<span>symbol</span><span>币种：1(比特币对人民币)</span>
							</div>
							<div class="list-list">
								<span>type</span><span>委托类型：0-买单、1-卖单</span>
							</div>
							<div class="list-list">
								<span>amount</span><span>委托数量</span>
							</div>
							<div class="list-list">
								<span>price</span><span>委托价格</span>
							</div>
						</div>
					</div>
					<div class="list-list">
						<div class="list-list-title">
							<span>POST /appApi.html?action=entrust</span><span>委托记录</span>
						</div>
						<div class="list-detail">
							<p>BTC /appApi.html?action=entrust&symbol=1</p>
							<div class="title mini">示例</div>
							<pre>
# Request 
POST /appApi.html?action=entrust&symbol=1
# Response
{
  "code": 200,
  "msg": "查询当前委单",
  "time": 1486203073756,
  "data": null
}</pre>
							<div class="title mini">返回值说明</div>
							<pre>
code : 返回码
msg  : 返回消息
time : 时间戳(ms)
data : 携带数据</pre>
							<div class="title mini">请求参数名</div>
							<div class="list-title">
								<span>参数名</span><span>描述</span>
							</div>
							<div class="list-list">
								<span>symbol</span><span>币种：1(比特币对人民币)</span>
							</div>
						</div>
					</div>
					<div class="list-list">
						<div class="list-list-title">
							<span>POST /appApi.html?action=lastentrust</span><span>查询最新10笔成交</span>
						</div>
						<div class="list-detail">
							<p>BTC /appApi.html?action=lastentrust&symbol=1</p>
							<div class="title mini">示例</div>
							<pre>
# Request 
POST /appApi.html?action=lastentrust&symbol=1
# Response
{
  "code": 200,
  "msg": "查询最新10笔成交委单",
  "time": 1486203073756,
  "data": null
}</pre>
							<div class="title mini">返回值说明</div>
							<pre>
code : 返回码
msg  : 返回消息
time : 时间戳(ms)
data : 携带数据</pre>
							<div class="title mini">请求参数名</div>
							<div class="list-title">
								<span>参数名</span><span>描述</span>
							</div>
							<div class="list-list">
								<span>symbol</span><span>币种：1(比特币对人民币)</span>
							</div>
						</div>
					</div>
					<div class="list-list">
						<div class="list-list-title">
							<span>POST /appApi.html?action=order</span><span>委托记录</span>
						</div>
						<div class="list-detail">
							<p>BTC /appApi.html?action=order&id=1</p>
							<div class="title mini">示例</div>
							<pre>
# Request 
POST /appApi.html?action=order&id=1
# Response
{
  "code": 200,
  "msg": "根本id查委托订单详细信息",
  "time": 1486203073756,
  "data": null
}</pre>
							<div class="title mini">返回值说明</div>
							<pre>
code : 返回码
msg  : 返回消息
time : 时间戳(ms)
data : 携带数据</pre>
							<div class="title mini">请求参数名</div>
							<div class="list-title">
								<span>参数名</span><span>描述</span>
							</div>
							<div class="list-list">
								<span>id</span><span>委托订单id：1</span>
							</div>
						</div>
					</div>
					<div class="list-list">
						<div class="list-list-title">
							<span>POST /appApi.html?action=userinfo</span><span>获取个人资产</span>
						</div>
						<div class="list-detail">
							<p>BTC /appApi.html?action=userinfo</p>
							<div class="title mini">示例</div>
							<pre>
# Request 
POST /appApi.html?action=userinfo
# Response
{
  "code": 200,
  "msg": "成功",
  "time": 1486203073756,
  "data": {
    "frozen": {
      "BTC": 0,
      "ETC": 0,
      "LTC": 0,
      "CNY": 0
    },
    "free": {
      "BTC": 0,
      "ETC": 0,
      "LTC": 0,
      "CNY": 0
    }
  }
}</pre>
							<div class="title mini">返回值说明</div>
							<pre>
code 	: 返回码
msg  	: 返回消息
data 	: 携带数据
free	: 可用资产
frozen	: 冻结资产
asset	: 总资产</pre>
							<div class="title mini">请求参数名</div>
							<div class="list-title">
								<span>参数名</span><span>描述</span>
							</div>
						</div>
					</div>
					 
					
			</div>
			<div class="doc-item padding-top-30">
				<div class="title">提现API</div>
				<div class="description">用于快速进行虚拟币提现</div>
				<div class="list">
					<div class="list-title">
						<span>接口</span><span>描述</span>
					</div>
					
					<div class="list-list">
						<div class="list-list-title">
							<span>POST /appApi.html?action=cancel_withdraw</span><span>取消提现</span>
						</div>
						<div class="list-detail">
							<p>BTC /appApi.html?action=cancel_withdraw&id=1</p>
							<div class="title mini">示例</div>
							<pre>
# Request 
POST /appApi.html?action=cancel_withdraw?id=1
# Response
{
  "code": 200,
  "msg": "取消提现成功",
  "time": 1486203073756,
  "data": null
}</pre>
							<div class="title mini">返回值说明</div>
							<pre>
code : 返回码
msg  : 返回消息
time : 时间戳(ms)
data : 携带数据</pre>
							<div class="title mini">请求参数名</div>
							<div class="list-title">
								<span>参数名</span><span>描述</span>
							</div>
							<div class="list-list">
								<span>withdraw_id</span><span>提币申请id</span>
							</div>
						</div>
					</div>
					
					<div class="list-list">
						<div class="list-list-title">
							<span>POST /appApi.html?action=withdraw</span><span>申请提币</span>
						</div>
						<div class="list-detail">
							<p>BTC /appApi.html?action=withdraw&symbol=1&amount=1&withdrawaddress=123456</p>
							<div class="title mini">示例</div>
							<pre>
# Request 
POST /appApi.html?action=withdraw?symbol=1&amount=1&withdrawaddress=123456
# Response
{
  "code": 200,
  "msg": "提现成功，请等待管理员审核",
  "time": 1486203073756,
  "data": null
}</pre>
							<div class="title mini">返回值说明</div>
							<pre>
code : 返回码
msg  : 返回消息
time : 时间戳(ms)
data : 携带数据</pre>
							<div class="title mini">请求参数名</div>
							<div class="list-title">
								<span>参数名</span><span>描述</span>
							</div>
							<div class="list-list">
								<span>symbol</span><span>币种：1(比特币)</span>
							</div>
							<div class="list-list">
								<span>amount</span><span>提现数量</span>
							</div>
							<div class="list-list">
								<span>withdrawaddress</span><span>认证提现地址</span>
							</div>
						</div>
					</div>
					<div class="list-list">
						<div class="list-list-title">
							<span>POST /appApi.html?action=withdraw_record</span><span>查询虚拟币提现记录</span>
						</div>
						<div class="list-detail">
							<p>BTC /appApi.html?action=withdraw_record?symbol=1</p>
							<div class="title mini">示例</div>
							<pre>
# Request 
POST /appApi.html?action=withdraw_record?symbol=1
# Response
{
  "code": 200,
  "msg": "查询虚拟币提现记录",
  "time": 1486203073756,
  "data": []
}</pre>
							<div class="title mini">返回值说明</div>
							<pre>
code : 返回码
msg  : 返回消息
time : 时间戳(ms)
data : 携带数据</pre>
							<div class="title mini">请求参数名</div>
							<div class="list-title">
								<span>参数名</span><span>描述</span>
							</div>
							<div class="list-list">
								<span>symbol</span><span>币种：1(比特币)</span>
							</div>
						</div>
					</div>
				</div>
			</div>
			
			<div class="doc-item padding-top-30">
				<div class="title">错误代码</div>
				<div class="description">API接口调用错误代码描述</div>
				<div class="list">
					<div class="list-title">
						<span>错误代码</span><span>详细描述</span>
					</div>
					<div class="list-list">
						<div class="list-list-title">
							<span>200</span><span>操作成功</span>
						</div>
					</div>
					<div class="list-list">
						<div class="list-list-title">
							<span>500</span><span>操作失败</span>
						</div>
					</div>
					<div class="list-list">
						<div class="list-list-title">
							<span>10001</span><span>网络错误    </span>
						</div>
					</div>
					<div class="list-list">
						<div class="list-list-title">
							<span>10002</span><span>API不存在 </span>
						</div>
					</div>
					<div class="list-list">
						<div class="list-list-title">
							<span>10003</span><span>参数错误 </span>
						</div>
					</div>
					<div class="list-list">
						<div class="list-list-title">
							<span>10004</span><span>无交易权限 </span>
						</div>
					</div>
					<div class="list-list">
						<div class="list-list-title">
							<span>10005</span><span>无提现权限</span>
						</div>
					</div>
					<div class="list-list">
						<div class="list-list-title">
							<span>10006</span><span>api_key错误</span>
						</div>
					</div>
					<div class="list-list">
						<div class="list-list-title">
							<span>10007</span><span>签名错误</span>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	






<%@include file="../comm/footer.jsp" %>	
<script src="${oss_url}/static/mobile2018/js/fluckydraw/com.js?v=20181126201750"></script>
<script type="text/javascript">
		$(function() {
			$(".list-list-title").on("click", function() {
				$(this).next().slideToggle(500, function() {
					util.lrFixFooter("#allFooter");
				});
			})
		})
	</script>
</body>
</html>