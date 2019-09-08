<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../comm/include.inc.jsp"%>
<h2 class="contentTitle">用户账户详情信息</h2>


<div class="pageContent">
		<div class="pageFormContent nowrap" layoutH="97">
		  <dl>
				<dt>ID：</dt>
				<dd>
					<span>${paytype.id}</span>
				</dd>
			</dl>
			<dl>
				<dt>用户ID：</dt>
				<dd>
					<span>${paytype.fuser.fid}</span>
				</dd>
			</dl>
			<dl>
				<dt>用户登录名：</dt>
				<dd>
					<span>${paytype.fuser.floginName}</span>
				</dd>
			</dl>
			<dl>
				<dt>用户姓名：</dt>
				<dd>
					<span>${paytype.fuser.frealName}</span>
				</dd>
			</dl>
			<dl>
				<dt>支付方式：</dt>
				<dd>
					<span>${paytype.paytypename}</span>
				</dd>
			</dl>
			<dl>
				<dt>真实姓名/昵称：</dt>
				<dd>
					<span>${paytype.realName}</span>
				</dd>
			</dl>			
			<dl>
				<dt>支付账号：</dt>
				<dd>
					<span>${paytype.paymentAccount }</span>
				</dd>
			</dl>
			<dl>
				<dt>二维码：</dt>
				<dd>
					<img src="${paytype.qrCode }" width="100" />
				</dd>
			</dl>
			<dl>
				<dt>所属银行：</dt>
				<dd>
					<span>${paytype.bank }</span>
				</dd>
			</dl>
			<dl>
				<dt>银行支行：</dt>
				<dd>
					<span>${paytype.bankBranch }</span>
				</dd>
			</dl>
			<dl>
				<dt>开户地址：</dt>
				<dd>
					<span>${paytype.remark }</span>
				</dd>
			</dl>
			<dl>
				<dt>是否启用：</dt>
				<dd>
					<span>${paytype.status == 1 ? "启用" : "禁用" }</span>
				</dd>
			</dl>
			<dl>
				<dt>创建时间：</dt>
				<dd>
                      <span>${paytype.createTime}</span>
				</dd>
			</dl>
			<dl>
				<dt>更新时间：</dt>
				<dd>
					<span>${paytype.updateTime }</span>
				</dd>
			</dl>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">关闭</button></div></div></li>
			</ul>
		</div>
</div>

