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
		
	}
};
$(function() {
	
	$('#begindate').click(function() {
		var calendar = new LCalendar();
		// WdatePicker({
		// 	el : 'begindate',
		// 	maxDate : '#F{$dp.$D(\'enddate\')}',
		// 	dchanged : function() {
		// 		var d = $dp.cal.newdate['d'];
		// 		var m = $dp.cal.newdate['M'];
		// 		var y = $dp.cal.newdate['y'];
		// 		if (m < 0) {
		// 			m = "07";
		// 		}
		// 		$("#datetype").val(0);
		// 		record.search(y + '-' + m + '-' + d, null);
		// 	}
		// });
		
		 calendar.init({
		    'trigger': '#begindate', //标签id
		    'type': 'date', //date 调出日期选择 datetime 调出日期时间选择 time 调出时间选择 ym 调出年月选择,
		    'minDate': (new Date().getFullYear()-3) + '-' + 1 + '-' + 1, //最小日期
		    'maxDate': (new Date().getFullYear()+3) + '-' + 12 + '-' + 31 //最大日期

		  });

		 // $("#datetype").val(0);
		
		 // record.search(y + '-' + m + '-' + d, null);
	});
	
	$('#enddate').click(function() {
		var calendar = new LCalendar();
		// WdatePicker({
		// 	el : 'enddate',
		// 	minDate : '#F{$dp.$D(\'begindate\')}',
		// 	dchanged : function() {
		// 		var d = $dp.cal.newdate['d'];
		// 		var m = $dp.cal.newdate['M'];
		// 		var y = $dp.cal.newdate['y'];
		// 		if (m < 0) {
		// 			m = "07";
		// 		}
		// 		$("#datetype").val(0);
		// 		record.search(null, y + '-' + m + '-' + d);
		// 	}
		// });
		 calendar.init({
		    'trigger': '#enddate', //标签id
		    'type': 'date', //date 调出日期选择 datetime 调出日期时间选择 time 调出时间选择 ym 调出年月选择,
		    'minDate': (new Date().getFullYear()-3) + '-' + 1 + '-' + 1, //最小日期
		    'maxDate': (new Date().getFullYear()+3) + '-' + 12 + '-' + 31 //最大日期

		  });

	});
	$(".datatime").click(function() {
		$("#datetype").val($(this).data().type);
		alert()
	
	});
	$("#recordType").change(function() {
		record.search();
	});
});