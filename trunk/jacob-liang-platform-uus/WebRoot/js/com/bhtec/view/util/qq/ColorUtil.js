//随机生成颜色
ColorUtil = {};
ColorUtil.rand = function(){
    var str = "0123456789abcdef";
    var color = "#";
    for (j = 0; j < 6; j++) {
        color = color + str.charAt(Math.random() * str.length);
    }
    return color;
}
