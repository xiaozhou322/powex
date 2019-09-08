

$(function(){
	$('.sl1').each(function(){
		var id = $(this).attr("id")
		var html = '<div class="coinWarp" id="' + $(this).attr("id") + '_box" onclick="hidetabs(this.id);" style="display:none"><div class="coinLitBox coinLitBox2"><ul>';
		$(this).find('option').each(function(){
			html += '<li data-value="'+ $(this).val() +'" id="'+ id + $(this).val() + '" onclick="lsSelect(this.id)">' + $(this).html() + '</li>';
		})
		html += '</ul></div></div>';
		$(".slet").append(html);
	})
}); 

function lsSelect(id){
	var num = id.replace(/[0-9]/ig,"");
	var _val = $("#"+id).data("value");
	var _name = $("#"+id).html();
	$("#"+num + "s").html(_name);
	$("#" + num).val(_val);
}

function sll(num){
	console.log(num);
	alert($("#"+num).val());
}


function lwSelect(id,selectid,cho,coin){
        var text = $("#"+id).html();
        var _val = $("#"+id).data("value");
        $("."+ cho +" span").html(text);
        $("."+ coin).css('bottom', '-100%');
        $("#" + selectid + " option:selected").val(_val);
        $("#" + selectid + " option:selected").html(text); 

    }


function showtab(id){
	 $("#"+ id).css("display","block");
	 $("."+ id).animate({bottom:"0"}, 200)
}

function showtabs(id){
	 $("#"+ id).css("display","block");
	 $("#"+ id).animate({bottom:"0"}, 200)
}

function hidetabs(id){
	 $("#"+ id).css("display","none");
	$("#"+id).css('bottom', '-100%');
}

function hidetab(id){
	$("#"+ id).css("display","none");
	$("."+id).css('bottom', '-100%');
}

