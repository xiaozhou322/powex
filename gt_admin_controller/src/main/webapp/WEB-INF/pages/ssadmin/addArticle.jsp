<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<h2 class="contentTitle">添加资讯信息</h2>


<div class="pageContent">

	<form id="saveForm" name="saveForm" method="post" action="/buluo718admin/saveArticle.html"
		class="pageForm required-validate" enctype="multipart/form-data"
		onsubmit="return iframeCallback(this, dialogAjaxDone);">	
		<div class="pageFormContent nowrap" layoutH="97">
			<dl>
				<dt>资讯标题（通用）：</dt>
				<dd>
					<input type="text" name="ftitle"
						class="required" size="70" />
				</dd>
			</dl>
			<dl>
				<dt>资讯标题（中文）：</dt>
				<dd>
					<input type="text" name="ftitle_cn"
						class="required" size="70" />
				</dd>
			</dl>
			
			<dl>
				<dt>类型：</dt>
				<dd>
					<input type="hidden" name="articleLookup.id" id="articleLookup.id" value="${articleLookup.id}"/>
				    <input type="text" class="required" name="articleLookup.articleType" value="" suggestFields="id,articleType"
				     suggestUrl="buluo718admin/articleTypeLookup.html" lookupGroup="orgLookup" readonly="readonly" size="70"/>
				    <a class="btnLook" href="/buluo718admin/articleTypeLookup.html" lookupGroup="articleLookup">查找带回</a>	
				</dd>
			</dl>
			<dl>
				<dt>图片：</dt>
				<dd>
					<input type="file" class="inputStyle" value="" name="filedata"
						id="filedata" />
				</dd>
			</dl>
			<dl>
				<dt>资讯内容（通用）：</dt>
				<dd>
					<textarea class="editor" name="fcontent" rows="20" cols="105"
						upImgUrl="buluo718admin/upload.html"
						upImgExt="jpg,jpeg,gif,png">
				</textarea>
			</dl>
			
			<dl>
				<dt>资讯内容（中文）：</dt>
				<dd>
					<textarea class="editor" name="fcontent_cn" rows="20" cols="105"
						upImgUrl="buluo718admin/upload.html"
						upImgExt="jpg,jpeg,gif,png">
				</textarea>
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
							<button type="submit" onclick="fillgcode();">保存</button>
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
function fillgcode(){
	var gcode = $("#gcode").val();
	$("#saveForm").attr("action","/buluo718admin/saveArticle.html?gcode="+gcode);
}
</script>
