<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ include file="../comm/include.inc.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;if (request.getServerName().equals("www.gbcax.com")){basePath="https://www.gbcax.com";}
%>

<!doctype html>
<html>
<head> 
<base href="<%=basePath%>" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="../comm/link.inc.jsp" %>
<link href="${oss_url}/static/front2018/css/exchange/common.css?v=20181126201750" rel="stylesheet" type="text/css" />
<link href="${oss_url}/static/front2018/css/exchange/main.css?v=1" rel="stylesheet" type="text/css" />
<!-- <link href="${oss_url}/static/front/css/exchange/common.css?v=20181126201750" rel="stylesheet" type="text/css" />
<link href="${oss_url}/static/front/css/exchange/main.css?v=20181126201750" rel="stylesheet" type="text/css" /> -->

</head>


<body class="lw-UstdBody">


<%@include file="../comm/header.jsp" %>


<section  class="lw-UstdBody">
<div class="ustdBtns">  
      <ul>
          <li>
              <a href="/exchange/rechargeUsdt.html">
                  <h1><spring:message code="financial.usdtrecharge" />   </h1>
                  <small>CNY-USDT</small>
              </a>
          </li>            
          <li>
              <a href="/exchange/exchangeUsdt.html">
                  <h1><spring:message code="financial.usdtrewithdrawal.exchange" />   </h1>
                  <small>USDT-CNY</small>
              </a>
          </li>            
          <li>
              <a href="/exchange/recordUsdt.html">
                  <h1><spring:message code="financial.exchangeusdt.viewrec" />   </h1>
              </a>
          </li>
      </ul>
</div>
</section>

</body>
</html>
