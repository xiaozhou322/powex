$(function() {
        
        $("#chart-control > button").click(function(){
            $(this).addClass("btn-success").siblings().removeClass("btn-success");
            var time = $(this).attr('data-time');
            kline(time,$('#coinname').val());
        });
        kline("1h",$('#coinname').val());
});


function kline(m,coinname){

    var chart;
	 arr = coinname.split("2");                                                                                                                    
	 if(arr.length > 1){
		 var coin_to = arr[1].toUpperCase( );
		 var coin_from = arr[0].toUpperCase( );
	 }

     Highcharts.setOptions({
    	 global: { useUTC: false  },
        /***/
        colors: ['#F01717', '#416C9E', '#DDDF0D', '#7798BF', '#55BF3B', '#DF5353', '#aaeeee', '#ff0066', '#eeaaee', '#55BF3B', '#DF5353', '#7798BF', '#aaeeee'],
        lang: {
            loading: 'Loading...',
            months: ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月'],
            shortMonths: ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月'],
            weekdays: ['周日', '周一', '周二', '周三', '周四', '周五', '周六'],
            decimalPoint: '.',
            numericSymbols: ['k', 'M', 'G', 'T', 'P', 'E'],
            resetZoom: 'Reset zoom',
            resetZoomTitle: 'Reset zoom level 1:1',
            thousandsSep: ','
        },
        credits: {
            enabled: false,
        }

    });

    if(m == '5m'){
        chart = chart_5m;
    }else if(m == '15m'){
        chart = chart_15m;
    }else if(m == '30m'){
        chart = chart_30m;
    }else if(m == '1h'){
        chart = chart_1h;
    }else if(m == '8h'){
        chart = chart_8h;
    }else if(m == '1d'){
        chart = chart_1d;
    }else{
    	chart = chart_5m;
    }
    
    var data = chart.time_line;
    // split the data set into ohlc and volume
    var ohlc = [],
        volume = [],
        dataLength = data.length;
        
    for (i = 0; i < dataLength; i++) {
        ohlc.push([
            data[i][0]*1000, // the date
            data[i][3], // open
            data[i][5], // high
            data[i][6], // low
            data[i][4] // close
        ]);
        
        volume.push([
            data[i][0]*1000, // the date
            data[i][7] // the volume
        ]);
    };

    // set the allowed units for data grouping
    var groupingUnits = [[
        'week',                         // unit name
        [1]                             // allowed multiples
    ], [
        'month',
        [1, 2, 3, 4, 6]
    ]];

    // create the chart
    $('#container').highcharts('StockChart', {

        xAxis: { type: 'datetime' },
        plotOptions: { candlestick: {color: '#e55600', upColor: '#690'} },
        tooltip: { xDateFormat: '%Y-%m-%d %H:%M %A', color: '#f0f', changeDecimals: 8, borderColor: '#e55600' },
        rangeSelector: {
             buttons: [
                {type: 'minute', count: 60, text: '1h'},
                {type: 'minute', count: 120,text: '2h'},
                {type: 'minute', count: 360,text: '6h'},
                {type: 'minute', count: 720,text: '12h'},
                {type: 'day',    count: 1,  text: '1d'},
                {type: 'week',    count: 1,  text: '1w'},
                {type: 'all', text: '所有'}
            ],
            selected: 6,
            inputEnabled: false
        },
        exporting:{enabled:false,buttons:{exportButton:{enabled:false},printButton:{enabled:true}}},

        credits: {
            text: 'com'
        },

       

        yAxis: [{
            title: {
                text: '价格['+coin_to+']',
                style: { color: '#CC3300' }
            },
            height: 230,
            lineWidth: 2
        }, {
            title: {
                text: '成交量['+coin_from+']',
                style: { color: '#4572A7' }
            },
            top: 275,
            height: 40,
            offset: 0,
            lineWidth: 2
        }],
        
        series: [
            {animation: false, type: 'candlestick',name: '价格['+coin_to+']',data: ohlc,dataGrouping: {units: groupingUnits}},
            {animation: false, type: 'column',color: '#4572A7',name: '成交量['+coin_from+']',data: volume,yAxis: 1,dataGrouping: {units: groupingUnits}
        }]
    });
}