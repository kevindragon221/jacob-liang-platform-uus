/**
 * 音乐播放
 * @author lianglp
 * @version 1.0
 * @class com.bhtec.view.util.sound.Sound 
 * @date 2010-08-29
 */

Ext.namespace('com.bhtec.view.util.sound');
com.bhtec.view.util.sound.Sound = function(config) {
	
	//初始化声音类soundManager 
    soundManager = new SoundManager();
    //soundManager.waitForWindowLoad = true;
    soundManager.debugMode = false;
  	soundManager.url = './js/com/bhtec/view/util/sound/swf';
    soundManager.beginDelayedInit();
    soundManager.onload = function() {
        //系统声音
        soundManager.createSound({
            id: 'systemSound',
            url: './js/com/bhtec/view/util/sound/mp3/system.mp3',
            //autoLoad: true,//自动加载
            //multiShot: false,//true 在同一时刻只能有一个频段的声音
            autoPlay: true //自动播放 这个是系统的背景音
            //volume: 100
        });
        //信息音
        soundManager.createSound({
            id: 'msgSound',
            url: './js/com/bhtec/view/util/sound/mp3/msg.mp3'
            //volume: 100
        });
        //加入音
        soundManager.createSound({
            id: 'joinSound',
            url: './js/com/bhtec/view/util/sound/mp3/join.mp3'
            //volume: 100
        });
        
 	}
}