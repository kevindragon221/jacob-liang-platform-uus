/**
 * qq闪烁
 * @author lianglp
 * @version 1.0
 * @class com.bhtec.view.util.qq.QQBlink 
 * @date 2010-08-29
 */
Ext.namespace('com.bhtec.view.util.qq');
com.bhtec.view.util.qq.QQBlink = function(config) {
	this.timer = 0;
}


com.bhtec.view.util.qq.QQBlink.prototype.blinkOn = function(imgObj){
			var blinkOn2 = 'new com.bhtec.view.util.qq.QQBlink().blinkOnExe('+imgObj.id+')';
			this.timer = setInterval(blinkOn2,500);
}

com.bhtec.view.util.qq.QQBlink.prototype.blinkOnExe = function(imgObj){
	var statebarifr = document.frames("statebarifr");
	alert(statebarifr);
	
		var obj = statebarifr.document.getElementById(imgObj.id);
		if(obj.filters.alpha.opacity==100){
			obj.filters.alpha.opacity=0
		}else{
			obj.filters.alpha.opacity=100
		}	
}