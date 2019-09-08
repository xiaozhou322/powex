<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ include file="../comm/include.inc.jsp"%>
<%

%>
<!doctype html>
<html>
<head> 
<%@ include file="../comm/basepath.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="../comm/link.inc.jsp" %>

<link href="${oss_url}/static/front2018/css/usdtTrade.css?v=12" rel="stylesheet" type="text/css" /></head>
<body class=>
<%@include file="../comm/white_header.jsp" %>
<section class="uTrade mg infoMain clear">
        <div class="uTrade_l">
             <%@include file="_leftmenuUser.jsp"%>
        </div>

        <div class="uTrade_r">
            <div class="baseForm">
              <!--   <h1 class="title">交易设置</h1> -->
                <div class="baseInfo baseInfo_2">
                    <p class="commH"><spring:message code="agent.name" /></p>
                    <label for=""><input class="commH txt" readonly id="fname" name="fname" type="text"  value="${fagent.fname}"/></label>
<!--                     <p class="commH">USDT钱包数量</p>
                    <label for=""><input class="commH txt" readonly id="totalusdt" name="totalusdt" type="text"  value="${totalusdt}"/></label> -->
                    <p class="commH p_spectail"><spring:message code="agent.usernum" />USDT    
                    <a href="javascript:;" class="fr to_Enter" onclick="$('.toShow').show();"><spring:message code="agent.tomoney" />
                    <svg class="icon sfont18" aria-hidden="true">
                        <use xlink:href="#icon-youjiantouda"></use>
                    </svg>
                    </a></p>
                    <label for=""><input class="commH txt"  readonly  type="text"  value="${fagent.usdtwallet}"/></label>
                     <p class="commH"><spring:message code="agent.freezing" />USDT</p>
                    <label for=""><input class="commH txt"  readonly  type="text"  value="${fagent.frozenusdt}"/></label>
<!--                     <div id="transinbox" style="display:none">
                        <p class="commH">转入数量</p>
                    	<label for=""><input class="commH txt use_input" id="transinnum" name="transinnum" type="text"  value="0"/></label>
                        <div class="tj_box">
                            <button class="tj" onclick="saveAgentInfo()" type="button">提交</button>
                        </div>
                    </div> -->
                    
                </div>
              
                   
                    <div class="clear"></div>
                </div>
               
            </div>
          
        </div>
    </section>
            <form id="agentinfoForm">
    <div class="baseForm toShow">
        <div class="toShow_con" id="transinbox">
            <p class="commH">USDT<spring:message code="agent.bagnum" /></p>
            <label for=""><input class="commH txt" readonly id="totalusdt" name="totalusdt" type="text"  value="${totalusdt}"/>
            </label>
             <p class="commH"><spring:message code="agent.transfernum" /></p>
            <label for=""><input class="commH txt use_input" id="transinnum" name="transinnum" type="text"  value="0"/></label>
            <div class="tj_box">
                <button class="tj" onclick="saveAgentInfo()" type="button"><spring:message code="security.submit" /></button>
            </div>
            <em class="toshow_close" onclick='$(this).parent().parent().fadeOut(100);'>×</em>
        </div>
    </div>
      </form>
 

<%@include file="../comm/footer.jsp" %>	
<script type="text/javascript" src="${oss_url}/static/front2018/js/index/main.js?v=20181126201750"></script>
<script type="text/javascript" src="${oss_url}/static/front2018/js/plugin/ajaxfileupload.js?v=20181126201750"></script>
   <script type="text/javascript" src="${oss_url}/static/front2018/js/plugin/fileCheck.js?v=20181126201750"></script>
   <script type="text/javascript" src="${oss_url}/static/front2018/js/comm/msg.js?v=20181126201750"></script>
<script type="text/javascript" src="${oss_url}/static/front2018/js/plugin/jquery.qrcode.min.js?v=20181126201750"></script>
<!-- <script type="text/javascript" src="${oss_url}/static/front/js/plugin/jquery.autocomplete.min.js?v=20181126201750"></script> -->
<script type="text/javascript" src="${oss_url}/static/front2018/js/plugin/jquery-migrate-1.4.1.min.js?v=20181126201750"></script>
<script>
    $(".uTrade_l a").click(function(event) {
        $(this).addClass('active').siblings().removeClass('active');
    });

    $(".payStyle p").click(function(event) {
        $(this).addClass('active').siblings().removeClass('active');
        var num=$(this).index();
        $(".pay_con").eq(num).addClass('active').siblings().removeClass('active');
    });
	function saveAgentInfo()
	{
		var fname = $('#fname').val();
		if(fname=='')
		{
			 util.layerAlert("", language["agent.dname"], 0);
		}
		var transinnum = $('#transinnum').val();
		var totalusdt = $('#totalusdt').val();
		if(transinnum>totalusdt)
		{
			 util.layerAlert("", language["agent.camount"], 0);
		}
		var param = $('#agentinfoForm').serialize();
		jQuery.post('/agent/transin.html', param, function (data) {
		    console.log(data);
        	if (data.code == -1) {
				util.layerAlert("", data.msg, 2);
				
			} else {
				util.layerAlert("", data.msg, 1);
			}
        }, "json");
	
	}
    //上传图片
	function uploadImg1() {
	    if (checkFileType('pic1', 'img')) {
	        fileUpload("/common/upload.html", "4", "pic1", "pic1Url", null, null, imgbakc1, "resultUrl");
	    }
	}
	
	function imgbakc1(resultUrl) {
	    $("#wxurl").attr('src',resultUrl);
	    $('label[for="pic1"]').text(language["kyc.tips.1"]);
	    $('.pic1name').text($('#pic1').val().split('\\').pop())
	        .siblings().text(language["kyc.tips.2"]);
	    $('#wxlabel').html('');
	}
	
	function uploadImg2() {
	    if (checkFileType('pic2', 'img')) {
	        fileUpload("/common/upload.html", "4", "pic2", "pic2Url", null, null, imgbakc2, "resultUrl");
	    }
	}
	
	function imgbakc2(resultUrl) {
	    $("#aliurl").attr('src',resultUrl);
	    $('label[for="pic2"]').text(language["kyc.tips.1"]);
	    $('.pic2name').text($('#pic2').val().split('\\').pop())
	       .siblings().text(language["kyc.tips.2"]);
	    $('#alilabel').html('');
	}
</script>    

</body>
</html>
