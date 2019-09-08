<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<h2 class="contentTitle">添加众筹信息</h2>

<div class="pageContent">

	<form method="post" action="/buluo718admin/saveSubscription1.html"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this,dialogAjaxDone)">
		<div class="pageFormContent nowrap" layoutH="97">
		      <dl>
				<dt>标题：</dt>
				<dd>
					<input type="text" name="ftitle" maxlength="50"
						class="required" size="70" />
				</dd>
			</dl>
			<dt>支付类型：</dt>
				<dd>
					<select
						type="combox" name="vid1" class="required">
						<c:forEach items="${coins}" var="type">
							<option value="${type.key}">${type.value}</option>
						</c:forEach>
					</select>
				</dd>
			</dl>
			<dl>
				<dt>众筹虚拟币名称：</dt>
				<dd>
					<select type="combox" name="vid" class="required">
						<c:forEach items="${allType}" var="type">
							<option value="${type.fid}">${type.fname}</option>
						</c:forEach>
					</select>
				</dd>
			</dl>
			<dt>解冻类型：</dt>
				<dd>
					<select
						type="combox" name=ffrozenType class="required">
						<c:forEach items="${frozenType}" var="type">
							<option value="${type.key}">${type.value}</option>
						</c:forEach>
					</select>
				</dd>
			</dl>
			<dl>
				<dt>是否开启解冻：</dt>
				<dd>
					<input type="checkbox" name="fisstart" />
					<span>每天0点解冻，按月的每月一号解冻</span>
				</dd>
			</dl>
			<dl>
				<dt>解冻比例：</dt>
				<dd>
					<input type="text" name="frate" class="required number" />
				</dd>
			</dl>
			<dl>
				<dt>是否开放众筹：</dt>
				<dd>
					<input type="checkbox" name="fisopen" />
				</dd>
			</dl>
			<dl>
				<dt>众筹总数量：</dt>
				<dd>
					<input type="text" name="ftotal" class="required number" />
				</dd>
			</dl>
			<dl>
				<dt>价格：</dt>
				<dd>
					<input type="text" name="fprice" class="required number" />
				</dd>
			</dl>
			
			<dl>
				<dt>每人最大众筹数量：</dt>
				<dd>
					<input type="text" name="fbuyCount" class="required digits" /><span>0为无限</span>
				</dd>
			</dl>
			<dl>
				<dt>每人最小众筹数量：</dt>
				<dd>
					<input type="text" name="fminbuyCount" class="required digits" />
				</dd>
			</dl>
			<dl>
				<dt>每人最多众筹次数：</dt>
				<dd>
					<input type="text" name="fbuyTimes" class="required digits" /><span>0为无限</span>
				</dd>
			</dl>
			<dl>
				<dt>开始时间：</dt>
				<dd>
					<input type="text" name="fbeginTime" class="required date"
						readonly="true" dateFmt="yyyy-MM-dd HH:mm:ss" size="40" /> <a
						class="inputDateButton" href="javascript:;">选择</a>
				</dd>
			</dl>
			<dl>
				<dt>结束时间：</dt>
				<dd>
					<input type="text" name="fendTime" class="required date"
						readonly="true" dateFmt="yyyy-MM-dd HH:mm:ss" size="40" /> <a
						class="inputDateButton" href="javascript:;">选择</a>
				</dd>
			</dl>
			<dl>
				<dt>项目介绍：</dt>
				<dd>
					<textarea class="editor" name="fcontent" rows="20" cols="105"
						tools="simple" upImgUrl="buluo718admin/upload.html"
						upImgExt="jpg,jpeg,gif,png">
				</textarea>
			</dl>
			
			<dl>
				<dt>谷歌认证码：</dt>
				<dd>
					<input
						type="text" name="gcode" maxlength="6" class="required digits" />
				</dd>
			</dl>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive">
						<div class="buttonContent">
							<button type="submit">保存</button>
						</div>
					</div>
				</li>
				<li><div class="button">
						<div class="buttonContent">
							<button type="button" class="close">取消</button>
						</div>
					</div>
				</li>
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
