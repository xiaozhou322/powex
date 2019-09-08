var certification = {
	submitRealCertificationForm : function() {
		var btn = document.getElementById("bindrealinfo-Btn");
		btn.disabled = true;
		var realname = document.getElementById("bindrealinfo-realname").value;
		var address = document.getElementById("bindrealinfo-address").value;
		var identitytype = document.getElementById("bindrealinfo-identitytype").value;
		var identityno = document.getElementById("bindrealinfo-identityno").value;
		var ckinfo = document.getElementById("bindrealinfo-ckinfo").checked;
        var filedata = $("#filedata")[0].files[0];
        var filedata2 = $("#filedata2")[0].files[0];
        var subData = new FormData();
		var desc = '';
		// 验证是否同意
		if (!ckinfo) {
			desc = language["certification.error.tips.1"];
			util.showerrortips('certificationinfo-errortips', desc);
			btn.disabled = false;
			return;
		}
		//验证姓名
		if (realname.length > 6 || realname.trim() == "") {
			desc = language["certification.error.tips.2"];
			util.showerrortips('certificationinfo-errortips', desc);
			btn.disabled = false;
			return;
		}
		// 验证证件类型
		if (identitytype != 0) {
			desc = language["certification.error.tips.3"];
			util.showerrortips('certificationinfo-errortips', desc);
			btn.disabled = false;
			return;
		}
		// 验证身份证
		var isIDCard = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
		var re = new RegExp(isIDCard);
		if (!re.test(identityno)) {
			desc = language["certification.error.tips.4"];
			util.showerrortips('certificationinfo-errortips', language["comm.error.tips.119"]);
			btn.disabled = false;
			return false;
		}
        if ("undefined" == typeof filedata){
        	util.showerrortips('certificationinfo-errortips',"请上传身份证正面复印件");
        	btn.disabled = false;
			return false;
        }
        if ("image/png" != filedata.type && "image/jpeg" != filedata.type){
        	util.showerrortips('certificationinfo-errortips', "身份证正面复印件必须为jpg/png格式图片");
        	btn.disabled = false;
			return false;
        }
        if (filedata.size / 1024 > 1024){
        	util.showerrortips('certificationinfo-errortips', "身份证正面复印件不能超过1M");
        	btn.disabled = false;
			return false;
        }
        if ("undefined" == typeof filedata2){
        	util.showerrortips('certificationinfo-errortips',"请上传身份证反面复印件");
        	btn.disabled = false;
			return false;
        }
        if ("image/jpeg" != filedata2.type && "image/png" != filedata2.type){
        	util.showerrortips('certificationinfo-errortips',"身份证反面复印件必须为jpg/png格式图片");
        	btn.disabled = false;
			return false;
        }
        if (filedata2.size / 1024 > 1024){
        	util.showerrortips('certificationinfo-errortips', "身份证反面复印件不能超过1M");
        	btn.disabled = false;
			return false;
        }
		// 隐藏错误消息
		util.hideerrortips('certificationinfo-errortips');
		// 提交信息
		
		subData.append("filedata1", filedata);
		subData.append("filedata2", filedata2);
		subData.append("identityNo", identityno);
		subData.append("identityType", identitytype);
		subData.append("realName", realname);
		var url = "/user/validateIdentity.html?random=" + Math.round(Math.random() * 100);
		$.ajax({
            url: url,
            type: "POST",
            data: subData,
            cache: false,
            contentType: false,
            processData: false,
            dataType: "json",
            success: function(data) {
            	if (data.code == 0) {
    				util.showerrortips('certificationinfo-errortips', data.msg);
    				btn.disabled = false;
    				window.location.reload(false);
    			} else {
    				util.showerrortips('certificationinfo-errortips', data.msg);
    				btn.disabled = false;
    			}
            }
        });

	},
    imgValidation: function(a) {
        switch (a) {
        case 1:
            var b = $("#filedata")[0].files[0];
            if ("undefined" == typeof b) return ! 1;
            break;
        case 2:
            var c = $("#filedata2")[0].files[0];
            if ("undefined" == typeof c) return ! 1;
            break;
        case 3:
            var c = $("#assetsPaper")[0].files[0];
            if ("undefined" == typeof c) return ! 1
        }
        return ! 0
    },
    getFileUrl: function(a) {
        var b;
        return navigator.userAgent.indexOf("MSIE") >= 1 ? b = document.getElementById(a).value: navigator.userAgent.indexOf("Firefox") > 0 ? b = window.URL.createObjectURL(document.getElementById(a).files.item(0)) : navigator.userAgent.indexOf("Chrome") > 0 && (b = window.URL.createObjectURL(document.getElementById(a).files.item(0))),
        b
    },
};

$(function() {
	$("#bindrealinfo-Btn").on("click", function() {
		certification.submitRealCertificationForm(false);
	});
    var a = !1;
    $(".mask", ".assets-apply-content-upload").on("click",
    function() {
        var b = $(this).data().fileid,
        c = $(this).data().imgid,
        d = $(this).data().type,
        e = $(this).data().icon;
        $("#" + b).click(),
        a = !1,
        $("#" + b).on("change",
        function() {
            a || (a = !0, certification.imgValidation(d) ? "3" == d ? ($("#" + e).addClass("worded").removeClass("word"), $("#" + c).html($("#assetsPaper")[0].files[0].name).show()) : ($("#" + e).hide(), $("#" + c).attr("src", certification.getFileUrl(b)).show()) : "3" == d ? ($("#" + e).removeClass("worded").addClass("word"), $("#" + c).html("").hide()) : ($("#" + e).show(), $("#" + c).hide()))
        });
    });
});
