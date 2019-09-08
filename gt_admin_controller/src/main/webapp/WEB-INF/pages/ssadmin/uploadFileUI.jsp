
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta http-equiv="pragma" content="no-cache" />
<base target="_self">
<title>文件上传</title>
</head>
<body>
	<h5>文件上传</h5>
	<hr />
	<form id="file_upload_id" name="file_upload_name"
		action="CommonController.jhtml?method=doFileUpload"
		method="post" enctype="multipart/form-data">
		<input type="hidden" name="functionId" value="${functionId}" /> <input
			type="hidden" name="fileType" value="${fileType}" /> <input
			type="hidden" name="maxSize" value="${maxSize}" />
		<div>
			<input type="file" name="file_upload" />
		</div>
		<c:if test="${maxSize!=null}">
			<div style="font: 12">文件最大不能超过${maxSize}MB</div>
		</c:if>
		<c:if test="${fileType!=null}">
			<div style="font: 12">文件格式必须是：${fileType}</div>
		</c:if>
		<div>
			<input type="submit" value="上传" />
		</div>
	</form>
</body>
</html>
