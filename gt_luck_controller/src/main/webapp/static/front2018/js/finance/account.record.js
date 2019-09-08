var  record= {
	search : function(begindate, enddate) {
		var url = $("#recordType").val();
		var datetype = $("#datetype").val();
		var begindate = begindate ? begindate : $("#begindate").val();
		var enddate = enddate ? enddate : $("#enddate").val();
		if (datetype > 0) {
			url=url + "&datetype=" + datetype;
		} else {
			url=url + "&datetype=" + datetype + "&begindate=" + begindate + "&enddate=" + enddate;
		}
		window.location.href = url;
		
	},
	/**id 订单ID
	 * type  1;//人民币充值 2;//人民币提现 3;//虚拟币充值 4;//虚拟币提现 5;//USDT购买 6;//USDT兑换
	 */
	

};
$(function() {
	$('#begindate').click(function() {
		WdatePicker({
			el : 'begindate',
			maxDate : '#F{$dp.$D(\'enddate\')}',
			dchanged : function() {
				var d = $dp.cal.newdate['d'];
				var m = $dp.cal.newdate['M'];
				var y = $dp.cal.newdate['y'];
				if (m < 0) {
					m = "07";
				}
				$("#datetype").val(0);
				record.search(y + '-' + m + '-' + d, null);
			}
		});
	});
	$('#enddate').click(function() {
		WdatePicker({
			el : 'enddate',
			minDate : '#F{$dp.$D(\'begindate\')}',
			dchanged : function() {
				var d = $dp.cal.newdate['d'];
				var m = $dp.cal.newdate['M'];
				var y = $dp.cal.newdate['y'];
				if (m < 0) {
					m = "07";
				}
				$("#datetype").val(0);
				record.search(null, y + '-' + m + '-' + d);
			}
		});
	});
	$(".datatime").click(function() {
		$("#datetype").val($(this).data().type);
		record.search();
	});
	$("#recordType").change(function() {
		record.search();
	});
});