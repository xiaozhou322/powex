var api = {
    submitApi: function(obj) {
        var apiCount = $("#apiCount").val();
        var apinum = $("#apinum").val();
        var tradePwd = $("#tradePwd").val();
        if (parseInt(apiCount) >= parseInt(apinum)){
        	util.showerrortips("errorTips", language["api.error.tips.1"].format(apinum));
        	return;
        }
        var googleCode = $("#apiTotpCode").val();
        var phoneCode = $("#apiPhoneCode").val();
        if(tradePwd == null || tradePwd ==''){
        	util.showerrortips("errorTips", language["api.error.tips.2"]);
        	return;
        }
        if ($("#apiTotpCode").length <= 0 && $("#apiPhoneCode").length <= 0){
        	util.showerrortips("errorTips", language["api.error.tips.3"]);
        	return;
        }
        
        if ($("#apiTotpCode").length > 0 && (googleCode.indexOf(" ") > -1 || 6 != googleCode.length || !/^[0-9]{6}$/.test(googleCode))){
        	util.showerrortips("errorTips", language["api.error.tips.4"]);
        	return;
        }
        if ($("#apiPhoneCode").length > 0 && (phoneCode.indexOf(" ") > -1 || 6 != phoneCode.length || !/^[0-9]{6}$/.test(phoneCode))){
        	util.showerrortips("errorTips", language["api.error.tips.5"]);
        	return;
        }
        
        var url = "/json/addApi.html";
        jQuery.post(url, {
        	tradePwd: tradePwd,
        	phoneCode: phoneCode,
        	totpCode: googleCode,
        	type:$('#type').val(),
        	fip:$('#ip').val()
		}, function(data) {
			if(data.code ==0){
        		$("#accessKey").html(data.partner);
                $("#secretKey").html(data.secret);
                $("#apiModal").modal("show");
        	}else{
        		util.showerrortips("errorTips", data.msg);
        	}
		}, "json");
    },
    deleteApi: function(obj, fid) {
        var url = "/json/cancelApi.html";
        
		jQuery.post(url, {
			id: fid
		}, function(result) {
			util.layerAlert("", result.msg, 1)
		}, "json");
    }
};
$(function() {
    $(".btn-sendmsg").on("click",
    function() {
        var apiCount = $("#apiCount").val();
        var apinum = $("#apinum").val();
        if(parseInt(apiCount) >= parseInt(apinum)){
        	util.showerrortips("errorTips", language["api.error.tips.1"].format(apinum));
        	return;
        }else{
        	msg.sendMsgCode($(this).data().msgtype, $(this).data().tipsid, this.id);
        }
    }),
    $("#apiButton").on("click",
    function() {
        api.submitApi(this)
    }),
    $(".delete-api").on("click",
    function() {
        api.deleteApi(this, $(this).data().fid)
    }),
    $("#modalBtn").on("click",
    function() {
        $("#apiModal").modal("hide"),
        $("#apiModal").on("hidden.bs.modal",
        function() {
            window.location.reload(true)
        })
    })
});