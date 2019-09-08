<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../comm/include.inc.jsp"%>
<h2 class="contentTitle">修改机构商信息</h2>


<div class="pageContent">

	<form method="post" action="/buluo718admin/updateInstitutions.html"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this,dialogAjaxDone)">
		<div class="pageFormContent nowrap" layoutH="97">
			<dl>
				<dt>机构名称</dt>
				<dd>
					<input type="hidden" name="id" value="${fotcinstitutionsinfo.id}" />
					<input type="text" name="institutions_name" value="${fotcinstitutionsinfo.institutions_name}" maxlength="50" class="required"
						size="70" />
				</dd>
			</dl>
			<dl>
				<dt>机构简称</dt>
				<dd>
					<input type="text" name="institutions_short_name" value="${fotcinstitutionsinfo.institutions_short_name}" maxlength="50" class="required"
						size="70" />
				</dd>
			</dl>
			<dl>
				<dt>机构商</dt>
				<dd>
					<input type="hidden" id="userId" name="userLookup.id" value="${userLookup.id == null ? fotcinstitutionsinfo.fuser.fid : userLookup.id}"/>
				    <input type="text" class="required" style="float:left" name="userLookup.floginName" value="${fotcinstitutionsinfo.fuser.ftelephone == null ? fotcinstitutionsinfo.fuser.femail : fotcinstitutionsinfo.fuser.ftelephone}" suggestFields="id,floginName"
				     suggestUrl="buluo718admin/userLookup.html" lookupGroup="userLookup" readonly="readonly"maxlength="50" class="required"
						size="70" />
				    <a class="btnLook" style="display: block;float:right;" href="/buluo718admin/userLookup.html" lookupGroup="userLookup">查找带回</a>
				</dd>
			</dl>
			<dl>
				<dt>机构负责人</dt>
				<dd>
					<input type="text" name="institutions_username" value="${fotcinstitutionsinfo.institutions_username}" maxlength="50" class="required"
						size="70" />
				</dd>
			</dl>
			<dl>
				<dt>机构负责人联系方式</dt>
				<dd>
					<input type="text" name="institutions_user_contact" value="${fotcinstitutionsinfo.institutions_user_contact}" maxlength="50" class="required"
						size="70" />
				</dd>
			</dl>
			<dl>
				<dt>业务人员</dt>
				<dd>
					<input type="text" name="business_people" value="${fotcinstitutionsinfo.business_people}" maxlength="50" class="required"
						size="70" />
				</dd>
			</dl>
			<dl>
				<dt>业务人员联系方式</dt>
				<dd>
					<input type="text" name="business_people_contact" value="${fotcinstitutionsinfo.business_people_contact}" maxlength="50" class="required"
						size="70" />
				</dd>
			</dl>
			<dl>
				<dt>机构状态</dt>
				<dd>
					<select type="combox" name="institutions_status">
					<c:choose>
					<c:when test="${fotcinstitutionsinfo.institutions_status==1}">
					    <option value="1" selected="selected">启用</option>
						<option value="2">禁用</option>
					</c:when>
					<c:otherwise>
					    <option value="1">启用</option>
						<option value="2" selected="selected">禁用</option>
					</c:otherwise>
					</c:choose>						
					</select>
				</dd>
			</dl>
			
			<dl>
				<dt>是否支持卖单自动确认</dt>
				<dd>
					<select type="combox" name="auto_confirm">
					<c:choose>
					<c:when test="${fotcinstitutionsinfo.auto_confirm==1}">
					    <option value="1" selected="selected">是</option>
						<option value="0">否</option>
					</c:when>
					<c:otherwise>
					    <option value="1">是</option>
						<option value="0" selected="selected">否</option>
					</c:otherwise>
					</c:choose>						
					</select>
				</dd>
			</dl>
			<dl style="display:none;">
				<dt>key-md5</dt>
				<dd>
					<input type="text" name="key_md5" value="${fotcinstitutionsinfo.key_md5}" maxlength="50" class="required"
						size="70" value="123"/>
				</dd>
			</dl>
			<dl style="display:none;">
				<dt>key-rsa公钥</dt>
				<dd>
					<input type="text" name="key_rsa" value="${fotcinstitutionsinfo.key_rsa}" maxlength="50" class="required"
						size="70" value="123"/>
				</dd>
			</dl>
			<dl>
				<dt>页面回调地址：</dt>
				<dd>
					<input type="text" name="page_callback_url" value="${fotcinstitutionsinfo.page_callback_url}" maxlength="50" class="required"
						size="70" />
				</dd>
			</dl>
			<dl>
				<dt>服务器回调地址：</dt>
				<dd>
					
					<input type="text" name="server_callback_url" value="${fotcinstitutionsinfo.server_callback_url}" maxlength="50" class="required"
						size="70" />
					<input type="hidden" name="create_time" value="<fmt:formatDate value='${fotcinstitutionsinfo.create_time}' pattern='yyyy-MM-dd HH:mm:ss'/>" />
						
				</dd>
			</dl>
			<dl>
				<dt>首页返回地址：</dt>
				<dd>
					<input type="text" name="index_url" maxlength="50" value="${fotcinstitutionsinfo.index_url}" 
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
