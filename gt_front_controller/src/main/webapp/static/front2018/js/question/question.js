var question = {
	questionText : function(focusid) {
		var focusblurid = $(focusid);
		var defval = focusblurid.val();
		focusblurid.focus(function() {
			var thisval = $(this).val();
			if (thisval == defval) {
				$(this).val("");
			}
		});
		focusblurid.blur(function() {
			var thisval = $(this).val();
			if (thisval == "") {
				$(this).val(defval);
			}
		});
	},

	submitQuestion : function() {
		var questiontype = document.getElementById("question-type").value;
		var questiondesc = $("#question-desc").val();
		if (util.trim(questiondesc) == "" || questiondesc.length < 10 || questiondesc == language["comm.error.tips.137"]) {
			desc = language["comm.error.tips.137"];
			util.showerrortips("errortips", desc);
			return;
		}
		var url = "/question/submitQuestion.html?random=" + Math.round(Math.random() * 100);
		var param = {
			questiontype : questiontype,
			questiondesc : questiondesc
		};
		jQuery.post(url, param, function(data) {
			if (data.code == 0) {
				util.layerAlert("", data.msg, 1);				
			} else {
				util.showerrortips("errortips", data.msg);
			}
		}, "json");
	},

	delquestion : function(data) {
		var questionid = $(data).data('question').questionid;
		 util.layerConfirm(language["question.error.tips.1"],
	        function(result) {
	            var url = "/question/delquestion.html?random=" + Math.round(100 * Math.random()),
	            param = {
	            	fid : questionid
	    		};
	            $.post(url, param,
	            function(questionid) {
	                null != questionid && (location.reload(true), layer.close(result))
	            })
		 });
	},
	viewquestion : function(data) {
		var questionid = $(data).data('question').questionid;
		$("#questiondetail"+questionid).modal("show");
	}
};

$(function() {
	/* 文本域 */
	question.questionText("#question-desc");
	/* 删除 */
	$(".delete").click(function() {
		question.delquestion(this);
	});
	$(".view").click(function() {
		question.viewquestion(this);
	});
	/* 提交问题 */
	$("#submitQuestion").on("click", function() {
		question.submitQuestion(false);
	});
	

});