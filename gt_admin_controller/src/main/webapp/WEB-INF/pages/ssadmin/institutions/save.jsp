<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../comm/include.inc.jsp"%>
<h2 class="contentTitle">录入机构商信息</h2>


<div class="pageContent">

	<form method="post" action="/buluo718admin/saveInstitutions.html"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this,dialogAjaxDone)">
		<div class="pageFormContent nowrap" layoutH="97">
			<dl>
				<dt>机构名称</dt>
				<dd>
					<input type="text" name="institutions_name" maxlength="50" class="required"
						size="70" />
				</dd>
			</dl>
			<dl>
				<dt>机构简称</dt>
				<dd>
					<input type="text" name="institutions_short_name" maxlength="50" class="required"
						size="70" />
				</dd>
			</dl>
			<dl>
				<dt>机构商</dt>
				<dd>
					<input type="hidden" id="userId" name="userLookup.id" value="${userLookup.id}"/>
				    <input type="text" class="required" style="float:left" name="userLookup.floginName" value="" suggestFields="id,floginName"
				     suggestUrl="buluo718admin/userLookup.html" lookupGroup="userLookup" readonly="readonly"maxlength="50" class="required"
						size="70" />
				    <a class="btnLook" style="display: block;float:right;" href="/buluo718admin/userLookup.html" lookupGroup="userLookup">查找带回</a>
				</dd>
			</dl>
			<dl>
				<dt>机构负责人</dt>
				<dd>
					<input type="text" name="institutions_username" maxlength="50" class="required"
						size="70" />
				</dd>
			</dl>
			<dl>
				<dt>机构负责人联系方式</dt>
				<dd>
					<input type="text" name="institutions_user_contact" maxlength="50" class="required"
						size="70" />
				</dd>
			</dl>
			<dl>
				<dt>业务人员</dt>
				<dd>
					<input type="text" name="business_people" maxlength="50" class="required"
						size="70" />
				</dd>
			</dl>
			<dl>
				<dt>业务人员联系方式</dt>
				<dd>
					<input type="text" name="business_people_contact" maxlength="50" class="required"
						size="70" />
				</dd>
			</dl>
			<dl>
				<dt>机构状态</dt>
				<dd>
					<select type="combox" name="institutions_status">
						<option value="1">启用</option>
						<option value="2">禁用</option>
					</select>
				</dd>
			</dl>
			
			<dl>
				<dt>是否支持卖单自动确认</dt>
				<dd>
					<select type="combox" name="auto_confirm">
						<option value="0">否</option>
						<option value="1">是</option>
					</select>
				</dd>
			</dl>
			<dl style="display:none;">
				<dt>key-md5</dt>
				<dd>
					<input type="text" name="key_md5" maxlength="50" class="required"
						size="70" value="123"/>
				</dd>
			</dl>
			<dl style="display:none;">
				<dt>key-rsa公钥</dt>
				<dd>
					<input type="text" name="key_rsa" maxlength="50" class="required"
						size="70" value="123"/>
				</dd>
			</dl>
			<dl>
				<dt>页面回调地址：</dt>
				<dd>
					<input type="text" name="page_callback_url" maxlength="50" class="required"
						size="70" />
				</dd>
			</dl>
			<dl>
				<dt>服务器回调地址：</dt>
				<dd>
					<input type="text" name="server_callback_url" maxlength="50" class="required"
						size="70" />
				</dd>
			</dl>
			
			<dl>
				<dt>首页返回地址：</dt>
				<dd>
					<input type="text" name="index_url" maxlength="50" 
						size="70" />
				</dd>
			</dl>
			
			<dl>
				<dt>谷歌认证码：</dt>
				<dd>
					<input
						type="text" id="gcode" name="gcode" maxlength="6" class="required digits" />
				</dd>
			</dl>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive">
						<div class="buttonContent">
							<button type="submit">保存</button>
						</div>
					</div></li>
				<li><div class="button">
						<div class="buttonContent">
							<button type="button" class="close">取消</button>
						</div>
					</div></li>
			</ul>
		</div>
	</form>

</div>


<script type="text/javascript">
function customvalidXxx(element){
	if ($(element).val() == "xxx") return false;
	return true;
}
</script>
