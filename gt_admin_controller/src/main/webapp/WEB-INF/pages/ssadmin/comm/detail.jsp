<%@ page pageEncoding="UTF-8"%>
<div class="accordion" fillSpace="sidebar">
	<shiro:hasPermission name="user">
		<div class="accordionHeader">
			<h2>
				<span>Folder</span>会员管理
			</h2>
		</div>
		<div class="accordionContent">
			<ul class="tree treeFolder">
				<shiro:hasPermission name="buluo718admin/userList.html">
					<li><a href="/buluo718admin/userList.html" target="navTab"
						rel="userList">会员列表</a>
					</li>
				</shiro:hasPermission>
				<shiro:hasPermission name="buluo718admin/messageList.html">
				<li><a href="/buluo718admin/messageList.html" target="navTab"
						rel="messageList">站内消息列表</a>
					</li>
				</shiro:hasPermission>	
				<shiro:hasPermission name="buluo718admin/scoreRecordList.html">
				<li><a href="/buluo718admin/scoreRecordList.html" target="navTab"
						rel="scoreRecordList">积分奖励明细</a></li>
				</shiro:hasPermission>		
 				<shiro:hasPermission name="buluo718admin/userAuditList.html">
					<li><a href="/buluo718admin/userAuditList.html" target="navTab"
						rel="userAuditList">待审核(身份证)列表</a>
					</li>
					<li><a href="/buluo718admin/userAuditList1.html" target="navTab"
						rel="userAuditList1">待审核(复印件)列表</a>
					</li>
				</shiro:hasPermission>
				<shiro:hasPermission name="buluo718admin/logList.html">
					<li><a href="/buluo718admin/logList.html" target="navTab"
						rel="logList">会员操作日志列表</a>
					</li>
				</shiro:hasPermission>
				<shiro:hasPermission name="buluo718admin/introlinfoList.html">
					<li><a href="/buluo718admin/introlinfoList.html" target="navTab"
						rel="introlinfoList">推广收益列表</a>
					</li>
				</shiro:hasPermission>
				<shiro:hasPermission name="buluo718admin/bankinfoWithdrawList.html">
					<li><a href="/buluo718admin/bankinfoWithdrawList.html"
						target="navTab" rel="bankinfoWithdrawList">会员银行帐户列表</a>
					</li>
				</shiro:hasPermission>
				<shiro:hasPermission name="buluo718admin/virtualaddressWithdrawList.html">
					<li><a href="/buluo718admin/virtualaddressWithdrawList.html"
						target="navTab" rel="virtualaddressWithdrawList">会员虚拟币提现地址列表</a>
					</li>
				</shiro:hasPermission>
				<shiro:hasPermission name="buluo718admin/virtualaddressList.html">
					<li><a href="/buluo718admin/virtualaddressList.html" target="navTab"
						rel="virtualaddressList">会员虚拟币充值地址列表</a>
					</li>
				</shiro:hasPermission>
				<shiro:hasPermission name="buluo718admin/assetList.html">
					<li><a href="/buluo718admin/assetList.html" target="navTab"
						rel="assetList">会员资产记录列表</a>
					</li>
				</shiro:hasPermission>
				<shiro:hasPermission name="buluo718admin/entrustList.html">
					<li><a href="/buluo718admin/entrustList.html" target="navTab"
						rel="entrustList">委托交易列表</a>
					</li>
				</shiro:hasPermission>
				
				<shiro:hasPermission name="buluo718admin/agentlist.html">
					<li><a href="/buluo718admin/agentlist.html"
						target="navTab" rel="agentlist">代理商管理</a>
					</li>
				</shiro:hasPermission>
			</ul>
		</div>
	</shiro:hasPermission>

	<shiro:hasPermission name="article">
		<div class="accordionHeader">
			<h2>
				<span>Folder</span>资讯管理
			</h2>
		</div>
		<div class="accordionContent">
			<ul class="tree treeFolder">
				<shiro:hasPermission name="buluo718admin/articleList.html">
					<li><a href="/buluo718admin/articleList.html" target="navTab"
						rel="articleList">资讯列表</a>
					</li>
				</shiro:hasPermission>
				<shiro:hasPermission name="buluo718admin/articleTypeList.html">
					<li><a href="/buluo718admin/articleTypeList.html" target="navTab"
						rel="articleTypeList">资讯类型</a>
					</li>
				</shiro:hasPermission>
				
				<shiro:hasPermission name="buluo718admin/bannerList.html">
					<li><a href="/buluo718admin/bannerList.html" target="navTab"
						rel="articleList">Banner列表</a>
					</li>
				</shiro:hasPermission>
			</ul>
		</div>
	</shiro:hasPermission>

	<shiro:hasPermission name="capital">
		<div class="accordionHeader">
			<h2>
				<span>Folder</span>虚拟币操作管理
			</h2>
		</div>
		<div class="accordionContent">
			<ul class="tree treeFolder">
				<shiro:hasPermission name="buluo718admin/virtualCoinTypeList.html">
					<li><a href="/buluo718admin/virtualCoinTypeList.html" target="navTab"
						rel="virtualCoinTypeList">虚拟币类型列表</a>
					</li>
				</shiro:hasPermission>
				<shiro:hasPermission name="buluo718admin/tradeMappingList.html">
					<li><a href="/buluo718admin/tradeMappingList.html" target="navTab"
						rel="tradeMappingList">法币类型匹配列表</a>
					</li>
				</shiro:hasPermission>
				<shiro:hasPermission name="buluo718admin/walletAddressList.html">
					<li><a href="/buluo718admin/walletAddressList.html" target="navTab"
						rel="walletAddressList" title="虚拟币可用地址列表">虚拟币可用地址列表</a></li>
				</shiro:hasPermission>

				<shiro:hasPermission name="buluo718admin/virtualCaptualoperationList.html">
					<li><a href="/buluo718admin/virtualCaptualoperationList.html"
						target="navTab" rel="virtualCaptualoperationList">虚拟币操作总表</a></li>
				</shiro:hasPermission>

				<shiro:hasPermission name="buluo718admin/virtualCapitalInList.html">
					<li><a href="/buluo718admin/virtualCapitalInList.html"
						target="navTab" rel="virtualCapitalInList">虚拟币充值列表</a></li>
				</shiro:hasPermission>

				<shiro:hasPermission name="buluo718admin/virtualCapitalOutList.html">
					<li><a href="/buluo718admin/virtualCapitalOutList.html"
						target="navTab" rel="virtualCapitalOutList">待审核虚拟币提现列表</a></li>
				</shiro:hasPermission>

				<shiro:hasPermission name="buluo718admin/virtualCapitalOutSucList.html">
					<li><a href="/buluo718admin/virtualCapitalOutSucList.html"
						target="navTab" rel="virtualCapitalOutSucList">虚拟币成功提现列表</a></li>
				</shiro:hasPermission>
				<shiro:hasPermission name="buluo718admin/virtualCapitalTransferList.html">
					<li><a href="/buluo718admin/virtualCapitalTransferList.html"
						target="navTab" rel="virtualCapitalTransferList">待审核虚拟币转账列表</a></li>
				</shiro:hasPermission>

				<shiro:hasPermission name="buluo718admin/virtualCapitalTransferSucList.html">
					<li><a href="/buluo718admin/virtualCapitalTransferSucList.html"
						target="navTab" rel="virtualCapitalTransferSucList">虚拟币成功转账列表</a></li>
				</shiro:hasPermission>
				<shiro:hasPermission name="buluo718admin/virtualwalletList.html">
					<li><a href="/buluo718admin/virtualwalletList.html" target="navTab"
						rel="virtualwalletList">会员虚拟币列表</a>
					</li>
				</shiro:hasPermission>
				<shiro:hasPermission name="buluo718admin/virtualoperationlogList.html">
					<li><a href="/buluo718admin/virtualoperationlogList.html"
						target="navTab" rel="virtualoperationlogList">虚拟币手工充值列表</a>
					</li>
				</shiro:hasPermission>
				<shiro:hasPermission name="buluo718admin/convertCoinLogsList.html">
					<li><a href="/buluo718admin/convertCoinLogsList.html"
						target="navTab" rel="convertCoinLogsList">虚拟币兑换列表</a>
					</li>
				</shiro:hasPermission>
				<shiro:hasPermission name="buluo718admin/activeCoinLogsList.html">
					<li><a href="/buluo718admin/activeCoinLogsList.html"
						target="navTab" rel="activeCoinLogsList">虚拟币激活列表</a>
					</li>
				</shiro:hasPermission>
				<shiro:hasPermission name="buluo718admin/approCoinLogsList.html">
					<li><a href="/buluo718admin/approCoinLogsList.html"
						target="navTab" rel="approCoinLogsList">虚拟币拨付列表</a>
					</li>
				</shiro:hasPermission>
			</ul>
		</div>
	</shiro:hasPermission>

	<!-- <shiro:hasPermission name="cnycapital">
		<div class="accordionHeader">
			<h2>
				<span>Folder</span>人民币操作管理
			</h2>
		</div>
		<div class="accordionContent">
			<ul class="tree treeFolder">
				<shiro:hasPermission name="buluo718admin/capitaloperationList.html">
					<li><a href="/buluo718admin/capitaloperationList.html"
						target="navTab" rel="capitaloperationList">人民币操作总表</a>
					</li>
				</shiro:hasPermission>
				<shiro:hasPermission name="buluo718admin/capitalInSucList.html">
					<li><a href="/buluo718admin/capitalInSucList.html" target="navTab"
						rel="capitalInSucList">成功充值人民币列表</a>
					</li>
				</shiro:hasPermission>
				<shiro:hasPermission name="buluo718admin/capitalOutSucList.html">
					<li><a href="/buluo718admin/capitalOutSucList.html" target="navTab"
						rel="capitalOutSucList">成功提现人民币列表</a>
					</li>
				</shiro:hasPermission>
				<shiro:hasPermission name="buluo718admin/capitalInList.html">
					<li><a href="/buluo718admin/capitalInList.html" target="navTab"
						rel="capitalInList">待审核人民币充值列表</a>
					</li>
				</shiro:hasPermission>
				<shiro:hasPermission name="buluo718admin/capitalOutList.html">
					<li><a href="/buluo718admin/capitalOutList.html" target="navTab"
						rel="capitalOutList">待审核人民币提现列表</a>
					</li>
				</shiro:hasPermission>
				<shiro:hasPermission name="buluo718admin/walletList.html">
					<li><a href="/buluo718admin/walletList.html" target="navTab"
						rel="walletList">会员人民币列表</a>
					</li>
				</shiro:hasPermission>
				<shiro:hasPermission name="buluo718admin/operationLogList.html">
					<li><a href="/buluo718admin/operationLogList.html" target="navTab"
						rel="operationLogList">人民币手工充值列表</a>
					</li>
				</shiro:hasPermission>
				<shiro:hasPermission name="buluo718admin/withdrawFeesList.html">
					<li><a href="/buluo718admin/withdrawFeesList.html" target="navTab"
						rel="withdrawFeesList" title="人民币提现手续费列表">人民币提现手续费列表</a>
					</li>
				</shiro:hasPermission>
			</ul>
		</div>
	</shiro:hasPermission> -->
	
	<shiro:hasPermission name="usdtcapital">
		<div class="accordionHeader">
			<h2>
				<span>Folder</span>USDT操作管理
			</h2>
		</div>
		<div class="accordionContent">
			<ul class="tree treeFolder">
				<shiro:hasPermission name="buluo718admin/usdtCapitaloperationList.html">
					<li><a href="/buluo718admin/usdtCapitaloperationList.html"
						target="navTab" rel="capitaloperationList">USDT操作总表</a>
					</li>
				</shiro:hasPermission>
				<shiro:hasPermission name="buluo718admin/usdtCapitalInSucList.html">
					<li><a href="/buluo718admin/usdtCapitalInSucList.html" target="navTab"
						rel="capitalInSucList">成功充值USDT列表</a>
					</li>
				</shiro:hasPermission>
				<shiro:hasPermission name="buluo718admin/usdtCapitalOutSucList.html">
					<li><a href="/buluo718admin/usdtCapitalOutSucList.html" target="navTab"
						rel="capitalOutSucList">成功提现USDT列表</a>
					</li>
				</shiro:hasPermission>
				<shiro:hasPermission name="buluo718admin/usdtCapitalInList.html">
					<li><a href="/buluo718admin/usdtCapitalInList.html" target="navTab"
						rel="capitalInList">待审核USDT充值列表</a>
					</li>
				</shiro:hasPermission>
				<shiro:hasPermission name="buluo718admin/usdtCapitalOutList.html">
					<li><a href="/buluo718admin/usdtCapitalOutList.html" target="navTab"
						rel="capitalOutList">待审核USDT提现列表</a>
					</li>
				</shiro:hasPermission>
				<shiro:hasPermission name="buluo718admin/usdtWalletList.html">
					<li><a href="/buluo718admin/usdtWalletList.html" target="navTab"
						rel="walletList">会员USDT列表</a>
					</li>
				</shiro:hasPermission>
				<shiro:hasPermission name="buluo718admin/usdtOperationLogList.html">
					<li><a href="/buluo718admin/usdtOperationLogList.html" target="navTab"
						rel="operationLogList">USDT手工充值列表</a>
					</li>
				</shiro:hasPermission>
				<shiro:hasPermission name="buluo718admin/usdtWithdrawFeesList.html">
					<li><a href="/buluo718admin/usdtWithdrawFeesList.html" target="navTab"
						rel="withdrawFeesList" title="USDT提现手续费列表">USDT提现手续费列表</a>
					</li>
				</shiro:hasPermission>
			</ul>
		</div>
	</shiro:hasPermission>

<shiro:hasPermission name="reward">
		<div class="accordionHeader">
			<h2>
				<span>Folder</span>奖励分红操作管理
			</h2>
		</div>
		<div class="accordionContent">
			<ul class="tree treeFolder">
				<shiro:hasPermission name="buluo718admin/virtualpresalelogList.html">
					<li><a href="/buluo718admin/virtualpresalelogList.html"
						target="navTab" rel="virtualpresalelogList">代币预售列表</a>
					</li>
				</shiro:hasPermission>
				<shiro:hasPermission name="buluo718admin/presalewalletList.html">
					<li><a href="/buluo718admin/presalewalletList.html"
						target="navTab" rel="virtualpresalelogList">预售释放列表</a>
					</li>
				</shiro:hasPermission>
				<shiro:hasPermission name="buluo718admin/snapGtList.html">
					<li><a href="/buluo718admin/snapGtList.html"
						target="navTab" rel="snapGtList">每日G币快照</a>
					</li>
				</shiro:hasPermission>
				<shiro:hasPermission name="buluo718admin/entrustMember.html">
					<li><a href="/buluo718admin/entrustMember.html" target="navTab"
						rel="entrustMember">会员成交额统计</a>
					</li>
					<li><a href="/buluo718admin/entrustMemberMarket.html" target="navTab"
						rel="entrustMemberMarket">交易市场成交额统计</a>
					</li>
					<li><a href="/buluo718admin/entrustMinerList.html" target="navTab"
						rel="entrustMemberMarket">交易挖矿奖励</a>
					</li>
				</shiro:hasPermission>
				<shiro:hasPermission name="buluo718admin/snapEntrustList.html">
					<li><a href="/buluo718admin/snapEntrustList.html"
						target="navTab" rel="snapEntrustList">每日交易量快照</a>
					</li>
				</shiro:hasPermission>
				<shiro:hasPermission name="buluo718admin/snapEntrustIntroList.html">
					<li><a href="/buluo718admin/snapEntrustIntroList.html"
						target="navTab" rel="snapEntrustList">每日经纪人推荐交易量统计</a>
					</li>
				</shiro:hasPermission>
				<shiro:hasPermission name="buluo718admin/snapEntrustIntroList.html">
					<li><a href="/buluo718admin/userProfitLogsList.html"
						target="navTab" rel="userProfitLogsList">用户交易奖励</a>
					</li>
				</shiro:hasPermission>
			</ul>
		</div>
	</shiro:hasPermission>
	
	<shiro:hasPermission name="APP">
		<div class="accordionHeader">
			<h2>
				<span>Folder</span>APP数据管理
			</h2>
		</div>
		<div class="accordionContent">
			<ul class="tree treeFolder">
				<shiro:hasPermission name="buluo718admin/thirdUserList.html">
					<li><a href="/buluo718admin/thirdUserList.html" target="navTab"
						rel="thirdUserList">第三方会员列表</a>
					</li>
				</shiro:hasPermission>
				<shiro:hasPermission name="buluo718admin/feesConvertList.html">
					<li><a href="/buluo718admin/feesConvertList.html"
						target="navTab" rel="feesConvertList">手续费兑换列表</a>
					</li>
				</shiro:hasPermission>
				<shiro:hasPermission name="buluo718admin/drawAccountsList.html">
					<li><a href="/buluo718admin/drawAccountsList.html"
						target="navTab" rel="drawAccountsList">虚拟币划账列表</a>
					</li>
				</shiro:hasPermission>
				<shiro:hasPermission name="buluo718admin/walletToExcUsdtList.html">
					<li><a href="/buluo718admin/walletToExcUsdtList.html"
						target="navTab" rel="walletToExcUsdtList">钱包到交易所USDT统计</a>
					</li>
				</shiro:hasPermission>
				<shiro:hasPermission name="buluo718admin/walletToExcBfscList.html">
					<li><a href="/buluo718admin/walletToExcBfscList.html"
						target="navTab" rel="walletToExcBfscList">钱包到交易所BFSC统计</a>
					</li>
				</shiro:hasPermission>
				<shiro:hasPermission name="buluo718admin/excToWalletBfscList.html">
					<li><a href="/buluo718admin/excToWalletBfscList.html"
						target="navTab" rel="exchangeToWalletList">交易所到钱包BFSC统计</a>
					</li>
				</shiro:hasPermission>
				<shiro:hasPermission name="buluo718admin/bfscDataStatisticList.html">
					<li><a href="/buluo718admin/bfscDataStatisticList.html"
						target="navTab" rel="bfscDataStatisticList">BFSC数据统计列表</a>
					</li>
				</shiro:hasPermission>
			</ul>
		</div>
	</shiro:hasPermission>

	<shiro:hasPermission name="report">
		<div class="accordionHeader">
			<h2>
				<span>Folder</span>报表统计
			</h2>
		</div>
		<div class="accordionContent">
			<ul class="tree treeFolder">
				<shiro:hasPermission name="buluo718admin/userReport.html">
					<li><a href="/buluo718admin/userReport.html" target="navTab"
						rel="userReport">会员注册统计表</a>
					</li>
				</shiro:hasPermission>
				<shiro:hasPermission name="buluo718admin/capitaloperationReport.html">
					<li><a
						href="/buluo718admin/capitaloperationReport.html?type=1&status=3&url=ssadmin//capitaloperationReport"
						target="navTab" rel="capitaloperationReport">人民币充值统计表</a>
					</li>
				</shiro:hasPermission>
				<shiro:hasPermission name="buluo718admin/capitaloperationOutReport.html">
					<li><a
						href="/buluo718admin/capitaloperationReport.html?type=2&status=3&url=ssadmin//capitaloperationOutReport"
						target="navTab" rel="capitaloperationOutReport">人民币提现统计表</a>
					</li>
				</shiro:hasPermission>
				<shiro:hasPermission name="buluo718admin/vcOperationInReport.html">
					<li><a
						href="/buluo718admin/vcOperationReport.html?type=1&status=3&url=ssadmin//vcOperationInReport"
						target="navTab" rel="vcOperationInReport">虚拟币充值统计表</a>
					</li>
				</shiro:hasPermission>
				<shiro:hasPermission name="buluo718admin/vcOperationOutReport.html">
					<li><a
						href="/buluo718admin/vcOperationReport.html?type=2&status=3&url=ssadmin//vcOperationOutReport"
						target="navTab" rel="vcOperationOutReport">虚拟币提现统计表</a>
					</li>
				</shiro:hasPermission>
				<shiro:hasPermission name="buluo718admin/totalReport.html">
					<li><a href="/buluo718admin/totalReport.html" target="navTab"
						rel="totalReport">综合统计表</a>
					</li>
				</shiro:hasPermission>
			</ul>
		</div>
	</shiro:hasPermission>

	<shiro:hasPermission name="question">
		<div class="accordionHeader">
			<h2>
				<span>Folder</span>提问管理
			</h2>
		</div>
		<div class="accordionContent">
			<ul class="tree treeFolder">
				<shiro:hasPermission name="buluo718admin/questionList.html">
					<li><a href="/buluo718admin/questionList.html" target="navTab"
						rel="questionList">提问记录列表</a>
					</li>
				</shiro:hasPermission>
				<shiro:hasPermission name="buluo718admin/questionForAnswerList.html">
					<li><a href="/buluo718admin/questionForAnswerList.html"
						target="navTab" rel="questionList">待回复提问列表</a>
					</li>
				</shiro:hasPermission>
			</ul>
		</div>
	</shiro:hasPermission>
	
	<shiro:hasPermission name="project">
		<div class="accordionHeader">
			<h2>
				<span>Folder</span>项目方管理
			</h2>
		</div>
		<div class="accordionContent">
			<ul class="tree treeFolder">
				<shiro:hasPermission name="buluo718admin/pprojectList.html">
					<li><a href="/buluo718admin/pprojectList.html" target="navTab"
						rel="pprojectList">项目方列表</a>
					</li>
				</shiro:hasPermission>
				<shiro:hasPermission name="buluo718admin/pcointypeList.html">
					<li><a href="/buluo718admin/pcointypeList.html" target="navTab"
						rel="pcointypeList">币种列表</a>
					</li>
				</shiro:hasPermission>
				<shiro:hasPermission name="buluo718admin/pcointypeAuditList.html">
					<li><a href="/buluo718admin/pcointypeAuditList.html"
						target="navTab" rel="pcointypeAuditList">待审核币种列表</a>
					</li>
				</shiro:hasPermission>
				<shiro:hasPermission name="buluo718admin/ptrademappingList.html">
					<li><a href="/buluo718admin/ptrademappingList.html" target="navTab"
						rel="ptrademappingList">交易市场列表</a>
					</li>
				</shiro:hasPermission>
				<shiro:hasPermission name="buluo718admin/ptrademappingAuditList.html">
					<li><a href="/buluo718admin/ptrademappingAuditList.html"
						target="navTab" rel="ptrademappingAuditList">待审核交易市场列表</a>
					</li>
				</shiro:hasPermission>
				<shiro:hasPermission name="buluo718admin/pfeesList.html">
					<li><a href="/buluo718admin/pfeesList.html" target="navTab"
						rel="pfeesList">手续费列表</a>
					</li>
				</shiro:hasPermission>
				<shiro:hasPermission name="buluo718admin/pfeesAuditList.html">
					<li><a href="/buluo718admin/pfeesAuditList.html"
						target="navTab" rel="pfeesAuditList">待审核手续费列表</a>
					</li>
				</shiro:hasPermission>
				<shiro:hasPermission name="buluo718admin/padList.html">
					<li><a href="/buluo718admin/padList.html" target="navTab"
						rel="padList">公告列表</a>
					</li>
				</shiro:hasPermission>
				<shiro:hasPermission name="buluo718admin/padAuditList.html">
					<li><a href="/buluo718admin/padAuditList.html"
						target="navTab" rel="padAuditList">待审核公告列表</a>
					</li>
				</shiro:hasPermission>
				<shiro:hasPermission name="buluo718admin/pdomainList.html">
					<li><a href="/buluo718admin/pdomainList.html" target="navTab"
						rel="pdomainList">项目方域名列表</a>
					</li>
				</shiro:hasPermission>
				<shiro:hasPermission name="buluo718admin/padAuditList.html">
					<li><a href="/buluo718admin/pdomainAuditList.html"
						target="navTab" rel="pdomainAuditList">待审核域名列表</a>
					</li>
				</shiro:hasPermission>
				<shiro:hasPermission name="buluo718admin/pprofitWaitSettleList.html">
					<li><a href="/buluo718admin/pprofitWaitSettleList.html"
						target="navTab" rel="pprofitWaitSettleList">项目方收益待结算列表</a>
					</li>
				</shiro:hasPermission>
				<shiro:hasPermission name="buluo718admin/pprofitSettleList.html">
					<li><a href="/buluo718admin/pprofitSettleList.html"
						target="navTab" rel="pprofitSettleList">项目方收益已结算列表</a>
					</li>
				</shiro:hasPermission>
				<shiro:hasPermission name="buluo718admin/pdepositcointypeList.html">
					<li><a href="/buluo718admin/pdepositcointypeList.html"
						target="navTab" rel="pdepositcointypeList">保证金币种管理</a>
					</li>
				</shiro:hasPermission>
				<shiro:hasPermission name="buluo718admin/pproductList.html">
					<li><a href="/buluo718admin/pproductList.html" target="navTab"
						rel="pproductList">产品列表</a>
					</li>
				</shiro:hasPermission>
				<shiro:hasPermission name="buluo718admin/pproductAuditList.html">
					<li><a href="/buluo718admin/pproductAuditList.html"
						target="navTab" rel="pproductAuditList">待审核产品列表</a>
					</li>
				</shiro:hasPermission>
			</ul>
		</div>
	</shiro:hasPermission>

	<shiro:hasPermission name="system">
		<div class="accordionHeader">
			<h2>
				<span>Folder</span>系统管理
			</h2>
		</div>
		<div class="accordionContent">
			<ul class="tree treeFolder">
				<shiro:hasPermission name="buluo718admin/systemArgsList.html">
					<li><a href="/buluo718admin/systemArgsList.html" target="navTab"
						rel="systemArgsList">系统参数列表</a>
					</li>
				</shiro:hasPermission>
				<shiro:hasPermission name="buluo718admin/linkList.html">
					<li><a href="/buluo718admin/linkList.html" target="navTab"
						rel="linkList">友情链接列表</a>
					</li>
				</shiro:hasPermission>
				<shiro:hasPermission name="buluo718admin/systemBankList.html">
					<li><a href="/buluo718admin/systemBankList.html" target="navTab"
						rel="systemBankList">银行帐户列表</a>
					</li>
				</shiro:hasPermission>
				<shiro:hasPermission name="buluo718admin/aboutList.html">
					<li><a href="/buluo718admin/aboutList.html" target="navTab"
						rel="aboutList">帮助分类列表</a>
					</li>
				</shiro:hasPermission>
				<shiro:hasPermission name="buluo718admin/securityList.html">
					<li><a
						href="/buluo718admin/goSecurityJSP.html?url=ssadmin//securityTreeList&treeId=1"
						target="navTab" rel="securityTreeList">权限列表</a></li>
				</shiro:hasPermission>
				<shiro:hasPermission name="buluo718admin/roleList.html">
					<li><a href="/buluo718admin/roleList.html" target="navTab"
						rel="roleList">角色列表</a></li>
				</shiro:hasPermission>
				<shiro:hasPermission name="buluo718admin/adminList.html">
					<li><a href="/buluo718admin/adminList.html" target="navTab"
						rel="adminList">管理员列表</a>
					</li>
				</shiro:hasPermission>
				<shiro:hasPermission name="buluo718admin/countLimitList.html">
					<li><a href="/buluo718admin/countLimitList.html" target="navTab"
						rel="countLimitList">限制管理列表</a>
					</li>
				</shiro:hasPermission>
				<shiro:hasPermission name="buluo718admin/limittradeList.html">
					<li><a href="/buluo718admin/limittradeList.html" target="navTab"
						rel="limittradeList">限价交易列表</a>
					</li>
				</shiro:hasPermission>
				<!-- <shiro:hasPermission name="buluo718admin/levelSettingList.html">
				<li><a href="/buluo718admin/levelSettingList.html" target="navTab"
						rel="levelSettingList">积分等级设置</a>
					</li>
				</shiro:hasPermission>
				<shiro:hasPermission name="buluo718admin/scoreSettingList.html">	
				<li><a href="/buluo718admin/scoreSettingList.html" target="navTab"
						rel="scoreSettingList">积分奖励设置</a>
					</li>	
				</shiro:hasPermission> -->
			</ul>
		</div>
	</shiro:hasPermission>
	<shiro:hasPermission name="luckdraw">
		<div class="accordionHeader">
			<h2>
				<span>Folder</span>抽奖活动管理
			</h2>
		</div>
		<div class="accordionContent">
			<ul class="tree treeFolder">
				<shiro:hasPermission name="buluo718admin/nperList.html">
					<li><a href="/buluo718admin/nperList.html" target="navTab"
						rel="nperList">抽奖期数列表</a>
					</li>
				</shiro:hasPermission>
				<shiro:hasPermission name="buluo718admin/lotteryList.html">
					<li><a href="/buluo718admin/lotteryList.html" target="navTab"
						rel="lotteryList">抽奖票号列表</a>
					</li>
				</shiro:hasPermission>
			</ul>
		</div>
	</shiro:hasPermission>
	
	<shiro:hasPermission name="activity">
		<div class="accordionHeader">
			<h2>
				<span>Folder</span>活动管理
			</h2>
		</div>
		<div class="accordionContent">
			<ul class="tree treeFolder">
				<shiro:hasPermission name="buluo718admin/activityList.html">
					<li><a href="/buluo718admin/activityList.html?ty=0" target="navTab"
						rel="activityList">未审核活动列表</a>
					</li>
					<li><a href="/buluo718admin/activityList.html?ty=1" target="navTab"
						rel="activityList1">已审核活动列表</a>
					</li>
					<li><a href="/buluo718admin/lotteryLogList.html" target="navTab"
						rel="lotteryLogList">用户中奖列表</a>
					</li>
					
				</shiro:hasPermission>

			</ul>
		</div>
	</shiro:hasPermission>
	<shiro:hasPermission name="OTC">
		<div class="accordionHeader">
			<h2>
				<span>Folder</span>OTC管理
			</h2>
		</div>
		<div class="accordionContent">
			<ul class="tree treeFolder">
				<shiro:hasPermission name="buluo718admin/otcUserPaytypeList.html">
					<li><a href="/buluo718admin/otcUserPaytypeList.html" target="navTab"
						rel="paytypeList">用户账户信息列表</a>
					</li>
				</shiro:hasPermission>
				<shiro:hasPermission name="buluo718admin/advertisementList.html">
					<li><a href="/buluo718admin/advertisementList.html" target="navTab"
						rel="adList">广告列表</a>
					</li>
				</shiro:hasPermission>
				<shiro:hasPermission name="buluo718admin/orderNewList.html">
					<li><a href="/buluo718admin/orderNewList.html" target="navTab"
						rel="orderList">订单列表</a>
					</li>
				</shiro:hasPermission>
				<shiro:hasPermission name="buluo718admin/appealOrderList.html">
					<li><a href="/buluo718admin/appealOrderList.html" target="navTab"
						rel="exporderList">异常订单列表</a>
					</li>
				</shiro:hasPermission>
				<shiro:hasPermission name="buluo718admin/institutionsList.html">
					<li><a href="/buluo718admin/institutionsList.html" target="navTab"
						rel="entrustList">机构商信息列表</a>
					</li>
				</shiro:hasPermission>
				<shiro:hasPermission name="buluo718admin/otcOrderLogsList.html">
					<li><a href="/buluo718admin/otcOrderLogsList.html" target="navTab"
						rel="otcOrderLogsList">订单操作日志列表</a>
					</li>
				</shiro:hasPermission>
				<shiro:hasPermission name="buluo718admin/otcBlackList.html">
					<li><a href="/buluo718admin/otcBlackList.html" target="navTab"
						rel="otcBlackList">黑名单用户列表</a>
					</li>
				</shiro:hasPermission>

			</ul>
		</div>
	</shiro:hasPermission>
</div>