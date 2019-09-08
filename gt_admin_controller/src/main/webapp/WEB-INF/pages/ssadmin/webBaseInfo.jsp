<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<div class="pageContent">
	
	<form method="post" action="/buluo718admin/saveOrUpdateWebInfo.html" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone)">
		<div class="pageFormContent" layoutH="58">
			<div class="unit">
				<input type="hidden" name="fwebbaseinfo.fid" size="70" value="${webBaseInfo.fid}" />
				<label>网站标题（通用）:</label>
				<textarea name="fwebbaseinfo.ftitle" cols="60" rows="2">${webBaseInfo.ftitle}</textarea>
			</div>
			<div class="unit">
				<label>网站标题（中文）:</label>
				<textarea name="fwebbaseinfo.ftitle_cn" cols="60" rows="2">${webBaseInfo.ftitle_cn}</textarea>
			</div>
			<div class="unit">
				<label>网站关键词（通用）:</label>
				<textarea name="fwebbaseinfo.fkeywords" cols="60" rows="2">${webBaseInfo.fkeywords}</textarea>
			</div>
			<div class="unit">
				<label>网站关键词（中文）:</label>
				<textarea name="fwebbaseinfo.fkeywords_cn" cols="60" rows="2">${webBaseInfo.fkeywords_cn}</textarea>
			</div>
			<div class="unit">
				<label>网站描述（通用）:</label>
				<textarea name="fwebbaseinfo.fdescription" cols="60" rows="2">${webBaseInfo.fdescription}</textarea>
			</div>
			<div class="unit">
				<label>网站描述（中文）:</label>
				<textarea name="fwebbaseinfo.fdescription_cn" cols="60" rows="2">${webBaseInfo.fdescription_cn}</textarea>
			</div>
			<div class="unit">
				<label>备案号:</label>
				<textarea name="fwebbaseinfo.fbeianInfo" cols="60" rows="2">${webBaseInfo.fbeianInfo}</textarea>
			</div>
			<div class="unit">
				<label>业务合作:</label>
				<textarea name="fwebbaseinfo.fsystemMail" cols="60" rows="2">${webBaseInfo.fsystemMail}</textarea>
			</div>
			<div class="unit">
				<label>copyRights:</label>
				<textarea name="fwebbaseinfo.fcopyRights" cols="60" rows="2">${webBaseInfo.fcopyRights}</textarea>
			</div>
			
			<div class="unit">
				<label>谷歌认证码：</label>
					<input
						type="text" name="gcode" maxlength="6" class="required digits" />
			</div>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">保存</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
			</ul>
		</div>
	</form>
	
</div>
