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
<meta name = "viewport" content = "width = device-width, initial-scale = 1.0, maximum-scale = 1.0, user-scalable = 0" />
<%@include file="../comm/link.inc.jsp" %>

<link href="${oss_url}/static/mobile2018/css/usdtTrade.css?v=1" rel="stylesheet" type="text/css" />

</head>
<body class="Ubody">
<%@include file="../comm/header.jsp" %>
<header class="tradeTop">  
    <i class="back toback2"></i>
    <h2 class="tit">交易设置</h2>
</header>
<section class="uTrade infoMain mg clear usdtTrade">
        <form id="agentinfoForm">
        <div class="uTrade_r fr">
            <div class="baseForm">
                <h1 class="title">交易设置</h1>
                <div class="baseInfo">
                    <p class="commH">代理商名称</p>
                    <label for=""><input class="commH txt" readonly id="fname" name="fname" type="text"  value="${fagent.fname}"/></label>
                    <p class="commH">USDT钱包数量</p>
                    <label for=""><input class="commH txt" id="totalusdt" name="totalusdt" type="text"  value="${totalusdt}"/></label>
                    <p class="commH">交易账户可用USDT    <a class="fr cblue2" href="javascript:;" style="margin-left:100px" onclick="$('#transinbox').show();">从钱包转入&gt;&gt; </a></p>
                    <label for=""><input class="commH txt"  readonly  type="text"  value="${fagent.usdtwallet}"/></label>
                     <p class="commH">交易账户冻结USDT</p>
                    <label for=""><input class="commH txt"  readonly  type="text"  value="${fagent.frozenusdt}"/></label>
                    <div id="transinbox" style="display:none">
                        <p class="commH">转入数量</p>
                    	<label for=""><input class="commH txt" id="transinnum" name="transinnum" type="text"  value="0"/></label>
                    </div>
                </div>
              
                   
                    <div class="clear"></div>
                </div>
                <button class="tj" onclick="saveAgentInfo()" type="button">提交</button>
            </div>
            </form>
        </div>
    </section>

 

<%@include file="../comm/footer.jsp" %>	

<script type="text/javascript" src="${oss_url}/static/mobile2018/js/plugin/ajaxfileupload.js?v=20181126201750"></script>
   <script type="text/javascript" src="${oss_url}/static/mobile2018/js/plugin/fileCheck.js?v=20181126201750"></script>

<script type="text/javascript" src="${oss_url}/static/mobile2018/js/plugin/jquery.qrcode.min.js?v=20181126201750"></script>
<!-- <script type="text/javascript" src="${oss_url}/static/front/js/plugin/jquery.autocomplete.min.js?v=20181126201750"></script> -->
<script type="text/javascript" src="${oss_url}/static/mobile2018/js/plugin/jquery-migrate-1.4.1.min.js?v=20181126201750"></script>
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
			 util.layerAlert("", "请输入代理商名称", 0);
		}
		var transinnum = $('#transinnum').val();
		var totalusdt = $('#totalusdt').val();
		if(transinnum>totalusdt)
		{
			 util.layerAlert("", "超出钱包数量，无法转入", 0);
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
