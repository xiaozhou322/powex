<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ include file="../comm/include.inc.jsp"%>

<!doctype html>
<html>
<head>
<%@ include file="../comm/basepath.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="../comm/link.inc.jsp"%>

</head>
<body>

<%@include file="../comm/header.jsp"%>

<section class="l-content">
    <div class="news_banner">
        <img src="${oss_url}/static/front2018/images/news_bg.png" alt="" />
        <div class="news_txt">
            <h1>${type }</h1>
            <span class="news_tag">
                <svg class="icon sfont24" aria-hidden="true">
                    <use xlink:href="#icon-bofang"></use>
                </svg>&nbsp;News Information
            </span>
        </div>
    </div>
    <div class="index_con news_con">
        <div class="coinNav">
            <div class="mg">
                <div class="coin_tab">
                    <ul class="clear tabs clear">
                        <c:forEach items="${articletypes }" var="v">
                        <li class="fl ${id==v.fid?'active':''}">
                            <a href="/service/ourService.html?id=${v.fid }"><span class="s_name">${v.fname }</span></a>
                        </li>
                        </c:forEach>                        
                        </ul>
                </div>
            </div>
        </div>
        <div class="news_main">
            <div class="news_list">
                <div class="mg clear">
                <c:forEach items="${farticles }" var="v">
                    <dl>
                        <dt><a href="${v.url }"><img src="${v.furl }" height="111" width="340" alt="" /></a></dt>
                        <dd>
                            <a href="${v.url }"><h2 class="news_tit">${v.ftitle }</h2></a>
                            <p class="list_txt">
                            ▶ ${v.fcontent_m }
                            </p>
                        </dd>
                    </dl>                    
                  </c:forEach>
                </div>
            </div>
        </div>
        <div class="page">${pagin }</div>
    </div>
</section>


<div class="goTop"><!-- 返回顶部 --></div>

	<%@include file="../comm/footer.jsp"%>
	<script type="text/javascript" src="${oss_url}/static/front2018/js/comm/msg.js?v=20181126201750"></script>
	<script type="text/javascript"
		src="${oss_url}/static/front2018/js/plugin/jquery.qrcode.min.js?v=20181126201750"></script>
</body>
</html>
