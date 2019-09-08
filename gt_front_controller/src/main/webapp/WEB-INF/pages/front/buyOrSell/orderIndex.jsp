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

	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th>订单号</th>
				<th>币种类型</th>
				<th>订单状态</th>
				<th>备注</th>
			</tr>
		</thead>
		<tbody>
			
				<tr target="sid_user" rel="${jsonObj.orderId}">
					<td>${jsonObj.orderId}</td>
					<td>${jsonObj.coinName}</td>
					<td>${jsonObj.status}</td>
					<td>${jsonObj.statusDesc}</td>
				</tr>
		
		</tbody>
	</table>

</div>
	
	
 
<%@include file="../comm/footer.jsp" %>	
  

</body>
</html>
