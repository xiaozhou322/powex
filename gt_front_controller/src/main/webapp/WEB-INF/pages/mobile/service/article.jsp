<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ include file="../comm/include.inc.jsp"%>


<!doctype html>
<html>
<head>
<%@ include file="../comm/basepath.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name = "viewport" content = "width = device-width, user-scalable = no,initial-scale=0.5,maximum-scale=0.5,minimum-scale=0.5">
<style type="text/css">
.tradeTop{
    margin-bottom: 0.2rem;
}

</style>
<%@include file="../comm/link.inc.jsp"%>

<div class="newsDetail">
  <header class="tradeTop">  
      <i class="back toback2"></i>
      <h2 class="tit"><spring:message code="m.security.newsdetail" /></h2>
  </header>
  <section>

      <div class="newsDetail_con">
          <div class="newsDetail_tit">
              <h2 class="tt">${farticle.ftitle }</h2>
              <span class='newsDetail_time'><fmt:formatDate value="${farticle.fcreateDate }" pattern="yyyy-MM-dd"/></span>
          </div>
          <div class="newsDetail_main">
              <p>
                ${farticle.fcontent }
              </p>
          </div>  
          
      </div>
  </section>
</div>
<style type="text/css">
  .GTnotice{width:100%; margin:20px 0; padding:0;}
  .GTnotice .title p{    padding: 23px 0;
    font-size: 34px;
    margin: 0;
    font-weight: bold;}
  .GTnotice table{font-size:14px;}
  .GTform table td{padding-left:12px; line-height:24px;}
  .GTform table td:nth-child(1){width:40%;}
  .GTform table td:nth-child(2){padding-left:20px;}
  .GTform .GTtxt{font-size:14px; line-height:30px; padding-bottom:0;}
  .GTform .GTtxt h3{font-size:18px; padding:20px 0;}
</style>

<%@ include file="../comm/tabbar.jsp"%>
<%@ include file="../comm/footer.jsp"%>
	<script type="text/javascript">
		 $(".backIcon").click(function(event) {
        	window.history.go(-1);
    	});
	</script>
	<script src="${oss_url}/static/mobile2018/js/fluckydraw/com.js?v=20181126201750"></script>
	<script type="text/javascript" src="${oss_url}/static/front/js/comm/msg.js?v=20181126201750"></script>
	<script type="text/javascript" src="${oss_url}/static/front/js/plugin/jquery.qrcode.min.js?v=20181126201750"></script>
</body>
</html>
