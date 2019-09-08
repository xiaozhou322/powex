var msg = {
		readMessage: function(obj, fid) {
        var url = "/user/readMessage.html";
		jQuery.post(url, {
			id: fid
		}, function(result) {
			var modal = $("#msgdetail");
			modal.find('.modal-title').html(result.title);
			modal.find('.modal-body').html(result.content);
			modal.modal('show');
			$("#li_"+fid).remove();
		}, "json");
    }
};
$(function() {
	$(".msglook").on("click",function() {
		debugger
		  msg.readMessage(this, $(this).data().value);
	});
});