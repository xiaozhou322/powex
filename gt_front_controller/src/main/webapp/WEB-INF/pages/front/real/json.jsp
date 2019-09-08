<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../comm/include.inc.jsp"%>
<%

%>


<!DOCTYPE HTML>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <script type="text/javascript" src="${oss_url}/static/front/js/jquery/jquery-1.8.2.js?v=20181126201750"></script>
    <script type="text/javascript">
      jQuery(function() {

        jQuery.getJSON('/kline/index.html?symbol=${param.symbol}&callback=?', function(data) {
          jQuery('#container').highcharts('StockChart', {
            chart: {
              renderTo: 'container'
            },
            title: {
              text: '交易行情图表'
            },
            xAxis: {
              type: 'datetime'
            },
            yAxis: {
              title: {
                text: '价格'
              }
            },
            navigator: {
              enabled: false
            },
            rangeSelector: {
              buttons: [{
                  type: 'day',
                  count: 1,
                  text: '1天'
                }, {
                  type: 'day',
                  count: 3,
                  text: '3天'
                }, {
                  type: 'day',
                  count: 7,
                  text: '7天'
                }, {
                  type: 'month',
                  count: 1,
                  text: '1月'
                }, {
                  type: 'all',
                  text: '全部'
                }],
              selected: 0
            },
            tooltip: {
              xDateFormat: '%Y-%m-%d %H:00:00'
            },
            series: [{
                name: '单价',
                data: data,
                tooltip: {
                  valueDecimals: 2
                }
              }]
          });
        });
      });</script>
    <style>*{margin:0px;padding:0px;}</style>
  </head>
  <body>

    <!--禁止右键-->
    <script type="text/javascript">
      function clickIE4() {
        if (event.button == 2) {
          return false;
        }
      }

      function clickNS4(e) {
        if (document.layers || document.getElementById && !document.all) {
          if (e.which == 2 || e.which == 3) {
            return false;
          }
        }
      }

      function OnDeny() {
        if (event.ctrlKey || event.keyCode == 78 && event.ctrlKey || event.altKey || event.altKey && event.keyCode == 115) {
          return false;
        }
      }

      if (document.layers) {
        document.captureEvents(Event.MOUSEDOWN);
        document.onmousedown = clickNS4;
        document.onkeydown = OnDeny();
      } else if (document.all && !document.getElementById) {
        document.onmousedown = clickIE4;
        document.onkeydown = OnDeny();
      }

      document.oncontextmenu = new Function("return false");
    </script>




    <script src="${oss_url}/static/front/js/highstock/highstock.js?v=20181126201750"></script>
    <div id="container"></div>
  </body>
</html>
