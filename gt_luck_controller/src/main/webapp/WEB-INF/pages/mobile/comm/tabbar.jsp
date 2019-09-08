<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@include file="include.inc.jsp"%>
<footer class="footer">
    <ul>
        <%-- <a href="/" class="tabbar">
            <img class="img01" src="${oss_url}/static/mobile2018/images/icon1.png" alt="" />
            <img class="img02" src="${oss_url}/static/mobile2018/images/icon1_1.png" alt="" />
            <span><spring:message code="nav.top.home" /></span>
        </a>
        <a href="/trademarket.html" class="tabbar">
            <img class="img01" src="${oss_url}/static/mobile2018/images/icon2.png" alt="" />
            <img class="img02" src="${oss_url}/static/mobile2018/images/icon2_1.png" alt="" />
            <span><spring:message code="nav.top.trade" /></span>
        </a>  
        <a href="/about/index.html?id=2" class="tabbar">
            <img class="img01" src="${oss_url}/static/mobile2018/images/icon3.png" alt="" />
            <img class="img02" src="${oss_url}/static/mobile2018/images/icon3_1.png" alt="" />
            <span><spring:message code="m.security.advisory" /></span>
        </a>      
        <a href="/user/security.html" class="tabbar">
            <img class="img01" src="${oss_url}/static/mobile2018/images/icon4.png" alt="" />
            <img class="img02" src="${oss_url}/static/mobile2018/images/icon4_1.png" alt="" />
            <span><spring:message code="m.security.user" /></span>
        </a>    --%> 
        
         <a href="/" class="tabbar">
            <img class="img01" src="${oss_url}/static/mobile2018/images/exchange/icon1.png" alt="" />
            <img class="img02" src="${oss_url}/static/mobile2018/images/icon1_1.png" alt="" />
            <span><spring:message code="nav.top.home" /></span>
        </a>
        <a href="/projects/projectsIndex.html" class="tabbar">
            <img class="img01" src="${oss_url}/static/mobile2018/images/exchange/xiangmu@3x.png" alt="" />
            <img class="img02" src="${oss_url}/static/mobile2018/images/exchange/xiangfu_s@3x.png" alt="" />
            <span>项目方</span>
        </a>  
        <a href="/community/communityIndex.html?id=2" class="tabbar">
            <img class="img01" src="${oss_url}/static/mobile2018/images/exchange/shequn.png" alt="" />
            <img class="img02" src="${oss_url}/static/mobile2018/images/exchange/shequn_s@3x.png" alt="" />
            <span>社群</span>
        </a>
        <a href="/luckydraw/luckydrawIndex.html?id=2" class="tabbar">
            <img class="img01" src="${oss_url}/static/mobile2018/images/exchange/hudong@3x.png" alt="" />
            <img class="img02" src="${oss_url}/static/mobile2018/images/exchange/huodong_s@3x.png" alt="" />
            <span>活动</span>
        </a>      
        <a href="/user/security.html" class="tabbar">
            <img class="img01" src="${oss_url}/static/mobile2018/images/exchange/416004751226601492.png" alt="" />
            <img class="img02" src="${oss_url}/static/mobile2018/images/icon4_1.png" alt="" />
            <span><spring:message code="m.security.user" /></span>
        </a> 
        <div class="clear"></div>
    </ul>
</footer>

<script type="text/javascript">
  $(document).ready(function(){
    	var url = window.location.href;
    	var hasTab = 0;
    	$('.tabbar').each(function(){
    		$(this).removeClass('active');
    		if($(this).index()>0)
    		{
	    		if(url.indexOf("/projects/projectsIndex.html")!==-1)
	    		{
	    			hasTab = 1;
	    		}else if(url.indexOf("/community/communityIndex.html")!==-1){
	    			hasTab = 2;
	    		}else if(url.indexOf("/luckydraw/")!==-1){
	    			hasTab = 3;
	    		}else if(url.indexOf("/user/")!==-1 || url.indexOf("/financial/")!==-1 || url.indexOf("/platform/")!==-1|| url.indexOf("/user/security.html")!==-1 || url.indexOf("/introl/")!==-1){
	    			hasTab = 4;
	    		}
    		}
    		
    	})
    	$('.tabbar').eq(hasTab).addClass('active');
  })  
</script>