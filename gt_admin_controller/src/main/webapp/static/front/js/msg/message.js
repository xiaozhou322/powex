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
		}, "json");
    }
};
$(function() {
	$(".msglook").on("click",
		function() {
		  msg.readMessage(this, $(this).data().value);
	});
});