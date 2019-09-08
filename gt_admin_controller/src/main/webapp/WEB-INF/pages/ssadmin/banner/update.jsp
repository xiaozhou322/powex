<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../comm/include.inc.jsp"%>
<h2 class="contentTitle">修改资讯信息</h2>


<div class="pageContent">

     <form method="post" action="/buluo718admin/updateBanner.html"
		class="pageForm required-validate" enctype="multipart/form-data"
		onsubmit="return iframeCallback(this, dialogAjaxDone);">		
		<div class="pageFormContent nowrap" layoutH="97">
			<dl>
				<dt>标题：</dt>
				<dd>
				    <input type="hidden" name="fid" value="${fbanner.fid}"/>
					<input type="text" name="ftitle" 
						class="required" size="70" value="${fbanner.ftitle}"/>
				</dd>
			</dl>
			
			<dl>
				<dt>类型：</dt>
				<dd>
				    <select type="combox" name="ftype">
						<c:forEach items="${type}" var="t">
							<option value="${t.key}" <c:if test="${t.key eq fbanner.ftype }">selected</c:if> >${t.value}</option>
						</c:forEach>
					</select>
				</dd>
			</dl>
			<dl>
				<dt>图片：</dt>
				<dd>
					<img src="${fbanner.fimgUrl}" height="200px">
					<input type="file" class="inputStyle" value="" name="filedata" id="filedata" />
				</dd>
			</dl>
			
			<dl>
				<dt>移动端图片：</dt>
				<dd>
					<img src="${fbanner.fimgUrlm}"  height="100px" >
					<input type="file" class="inputStyle" value="" name="filedatam" id="filedatam" />
				</dd>
			</dl>
			
			<dl>
				<dt>链接：</dt>
				<dd>
				    <input type="text" class="required" name="furl"  size="70" value="${fbanner.furl}" />
				</dd>
			</dl>
			<dl>
				<dt>中文内容：</dt>
				<dd>
				    <textarea name="fcontent" rows="5" cols="80">${fbanner.fcontent}</textarea>
				</dd>
			</dl>
			<dl>
				<dt>英文内容：</dt>
				<dd>
				    <textarea name="fcontent_en" rows="5" cols="80">${fbanner.fcontent_en}</textarea>
				</dd>
			</dl>
			<dl>
				<dt>置顶显示：</dt>
				<dd>
					
					<c:choose>
						<c:when test="${fbanner.fisding}">
							<input type="checkbox" name="fisding" checked='1' />
						</c:when>
						<c:otherwise>
							<input type="checkbox" name="fisding" />
						</c:otherwise>
					</c:choose>
				</dd>
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
