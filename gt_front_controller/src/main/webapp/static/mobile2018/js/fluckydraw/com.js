//根据屏幕大小及dpi调整缩放和大小
(function() {
    var scale = 1.0;
    var ratio = 1;
    if (window.devicePixelRatio >= 2) {
        scale *= 0.5;
        ratio *= 2;
    }
    var text = '<meta name="viewport" content="initial-scale=' + scale + ', maximum-scale=' + scale +', minimum-scale=' + scale + ', width=device-width, user-scalable=no" />';
    document.write(text);
    document.documentElement.style.fontSize = 50*ratio + "px";
})();