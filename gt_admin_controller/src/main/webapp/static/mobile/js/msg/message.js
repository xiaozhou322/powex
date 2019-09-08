var msg = {
		readMessage: function(obj, fid) {
        var url = "/user/readMessage.html";
		jQuery.post(url, {
			id: fid
		}, function(result) {
			
		}, "json");
    }
};
$(function() {
	$(".msglook").on("click",
		function() {
		  msg.readMessage(this, $(this).data().value);
	});
});