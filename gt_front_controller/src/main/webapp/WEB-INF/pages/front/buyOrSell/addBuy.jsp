<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ include file="../comm/include.inc.jsp"%>

<!doctype html>
<html>
<head> 
<%@ include file="../comm/basepath.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="../comm/link.inc.jsp" %>

<!-- <link href="${oss_url}/static/front/css/about/main.css?v=20181126201750" rel="stylesheet" type="text/css" />
 <link href="${oss_url}/static/front/css/index/main.css?v=20181126201750" rel="stylesheet" type="text/css" />-->
<%-- <script type="text/javascript" src="${oss_url}/static/front/js/index/main.js?v=20181126201750"></script> --%>
<script type="text/javascript" src="${oss_url}/static/front/js/user/jquery-1.11.2.min.js?v=20181126201750"></script>
<%-- <script type="text/javascript" src="${oss_url}/static/front/js/user/jquery.SuperSlide.2.1.js?v=20181126201750"></script> --%>
<style type="text/css">

.l-header, .header_in {
    background: #22253b!important;
}
.pageContent{
margin-left: auto;
margin-right: auto;
  margin-top: 50px;
  width:1000px;
}
input,select{
    width: 400px;
    height: 30px;
    border: 1px #76dc87 solid;
    border-radius: 5px;

}
.text{
    width: 400px;
    height: 100px;
    border: 1px #76dc87 solid;
    border-radius: 5px;

}
dt{
width:150px;
float: left;
}
dl{
    margin-top: 30px;
}

.but{
     width: 80px;
    height: 30px;
    border-radius: 5px;
    margin-left: 50px;
 }
 
 .buttonActive{
 
     margin-top: 50px;
    margin-bottom: 50px;
    margin-left: auto;
    margin-right: auto;
    width: 800px;
 }
</style>

</head>
<body>



<%@include file="../comm/header.jsp" %>

<div class="pageContent">

	<form method="post" action="/buyOrSell/saveBuyOrSell.html"
		class="pageForm required-validate" enctype="multipart/form-data"
		onsubmit="return iframeCallback(this, dialogAjaxDone);">	
		<div class="pageFormContent nowrap" layoutH="97">

				<input type="hidden"  name="ad_type" value="1"
						 size="70" />
				
			

			<dl>
				<dt>购买币种：</dt>
				<dd>
				<select name="amount_type">
				 <c:forEach items="${amountTypeMap}" var="st">
					<option value="${st.fid}">${st.fname}</option>
										
				</c:forEach> 
				</select>
				</dd>
			</dl>
			
			<dl>
				<dt>购买总数量：</dt>
				<dd>
					<input type="text" name="total_count"
						 size="70" />
				</dd>
			</dl>
			<dl>
				<dt>机构商ID：</dt>
				<dd>
					<input type="text" name="uid" value="107540"
						 size="70" />
				</dd>
			</dl> 
			<dl>
				<dt>订单号：</dt>
				<dd>

				   <input type="text" name="order_number"
						 size="70" />
				 
				</dd>
			</dl>

			<dl>
				<dt>备注：</dt>
				<dd>
					<textarea class="text" name="remark" rows="20" cols="105">
				</textarea>
			</dl>
			
			
		
		</div>

                 <div class="buttonActive">
					
						<button type="submit" class="but">保存</button>
						<button type="button" class="but">取消</button>		
					</div>
		
	</form>

</div>
	
	
 
<%@include file="../comm/footer.jsp" %>	
  

</body>
</html>
