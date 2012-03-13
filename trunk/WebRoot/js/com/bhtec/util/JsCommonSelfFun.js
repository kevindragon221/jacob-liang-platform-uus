/**
 * 引入js css
 * @param {} path	文件路径
 * @param {} type	文件类型
 */
function $import(path, type){
	var scripts = document.getElementsByTagName("script");
	var cssLinks = document.getElementsByTagName("link");
	if(type=="css"){
		for (i = 0; i < cssLinks.length; i++) {
			if(cssLinks[i].href.match(path)){
	 			return;
	 		}
		}	
		document.write("<" + "link href=\""  + path + ".css\" rel=\"stylesheet\" type=\"text/css\"></" + "link>");	
	}else if(type=="js"){
		for (i = 0; i < scripts.length; i++) {
			if(scripts[i].src.match(path)){
	 			return;
	 		}
		}
		document.write("<" + "script src=\"" + path + ".js\"></" + "script>");
	}
}
/**
 * 自定義map
 */
function Map(){
	this.size=0;
	this.keys=new Array();
	this.values=new Array();
	this.get = function(key) {
		for (var i = 0; i < this.size; i++) {
			if (key == this.keys[i]) {
				return this.values[i];
			}
		}
	};
	this.getValueByIndex = function(index) {
		if (index > 0 && index < this.size) {
			return this.values[index];
		} else {
			return null;
		}
	};
	this.getKeyByIndex = function(index) {
		if (index > 0 && index < this.size) {
			return this.keys[index];
		} else {
			return null;
		}
	};
	this.put = function(key, value) {
		for (var i = 0; i < this.size; i++) {
			if (key == this.keys[i]) {
				alert("key is repeat");
				return null;
			}
		}
		this.keys[this.size] = key;
		this.values[this.size] = value;
		this.size++;
	};
	this.remove = function(key) {
		for (var i = 0; i < this.size; i++) {
			if (key == this.keys[i]) {
				for (var j = i; j < this.size; j++) {
					this.keys[j] = this.keys[j + 1];
					this.values[j] = this.values[j + 1];
				}
				break;
			}
		}
		this.size--;
	};
	this.removeAll = function() {
		this.keys = new Array();
		this.values = new Array();
	};
}


/**
 * 同步ajax请求
 * 
 * @param {}
 *            url地址
 * @return {}
 */
function synchronizeAjaxRequest(url,paras){
	var xmlhttp=null;
	if (window.XMLHttpRequest){// code for Firefox, Opera, IE7, etc.
		xmlhttp=new XMLHttpRequest();
	}else if (window.ActiveXObject){// code for IE6, IE5
		xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
	}	
	
	if (xmlhttp != null && url) {		
		xmlhttp.open("GET", url.toString(), false);
		xmlhttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8"); 
		xmlhttp.send(paras);
		if (xmlhttp.responseText != '') {
			return xmlhttp.responseText;
		} else {
			alert('服务器出现错误请稍后再试！');
		}
	} else {
		alert('创建HTTP请求失败！');
	}
};   

/**
 *同步ajax请求 decode
 * @param {} url地址
 * @return {}
 */
function syncAjaxReqDecode(url,paras){
	var xmlhttp=null;
	if (window.XMLHttpRequest){// code for Firefox, Opera, IE7, etc.
		xmlhttp=new XMLHttpRequest();
	}else if (window.ActiveXObject){// code for IE6, IE5
		xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
	}	

	if (xmlhttp != null) {
		xmlhttp.open("POST", url, false);
		xmlhttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8"); 
		xmlhttp.send(paras);
		if (xmlhttp.responseText != '') {
			var xmlhttpData = Ext.util.JSON.decode(xmlhttp.responseText);
			if(xmlhttpData){
//				if(checkUserSessionStatus(xmlhttpData))return '';//是否超时
				if(ajaxRequestResult(xmlhttpData) == false)return '';//操作是否成功
			}
			return xmlhttpData;
		} else {
			alert('服务器出现错误请稍后再试！');
		}
	} else {
		alert('创建HTTP请求失败！');
	}
};   
/**
 * 同步请求session检查
 * @param {} data
 * @return {Boolean}
 */
function syncAjaxReqSessionCheck(data){
	if(data.sessionstatus == 'timeout'){   
		askMesg({title:'确认', 
				 msg:'对不起,会话失效.请重新登录.', 
				 fn:function(confirm){
							if('ok' == confirm)
                             	reLogin();
                    }
		});   
       return false;
	} 
}

/**
 * 加载XML文件 
 * xmlFile	xml文件路径
 *  */
function loadXMLFile(xmlFile) {
	var xmlDoc;
	try{
		if (window.ActiveXObject) {
			xmlDoc = new ActiveXObject("Msxml2.DOMDocument");
		} else {
			if (document.implementation && document.implementation.createDocument) {
				xmlDoc = document.implementation.createDocument("text/xml", "",
						null);
			} else {
				return null;
			}
		}
		xmlDoc.async = false;		
		xmlDoc.load(xmlFile);
	}catch(e){
		try{//Google Chrome   
		   var xmlhttp = new window.XMLHttpRequest();   
		   xmlhttp.open("GET",xmlFile,false);   
		   xmlhttp.send(null);   
		   xmlDoc = xmlhttp.responseXML.documentElement;   
	  	}catch(e){   
	   	   alert(e.message);   
	  	}  
	}
	return xmlDoc;
}

/**
 * 获得描述域字符串
 * @param {} moduleId
 * @param {} xmlDoc
 */
function getFieldNamesJson(moduleId,xmlDoc){
    var resultJson = '"moduleId":"'+moduleId+'"';
	var nodes = xmlDoc.getElementsByTagName("field");
	for(var i=0;i<nodes.length;i++){
		var fieldName = nodes[i].getAttribute('name');
		var fieldValue = '';
		if(Ext.isIE){
			fieldValue = nodes[i].nodeTypedValue;
		}else{
			fieldValue = nodes[i].textContent;
		}
		resultJson = resultJson +',"'+fieldName+'":"'+ fieldValue +'"';
	}
	resultJson = '{'+resultJson+'}';
	return resultJson;
}

/**
 * 通过XML文件动态加载模块的js
 * @param {} xmlDoc		文档对象
 * @param {} viewType	页面视图类型
 */
function dynamicLoadModuleJs(xmlDoc,viewType,moduleId){	
	var jsUrlShareChildNodes = xmlDoc.getElementsByTagName('jsUrlShare');
	for(var i=0;i<jsUrlShareChildNodes.length;i++){
		if(jsUrlShareChildNodes[i].parentNode.nodeName == viewType){
			var jsUrlShare = jsUrlShareChildNodes[i].childNodes[0].nodeValue;
			if(basicConstant.DYNAMIC_LOAD_JS_MAP.get(jsUrlShare) == undefined){
				var responseText = synchronizeAjaxRequest(jsUrlShare);
				eval(responseText);
				basicConstant.DYNAMIC_LOAD_JS_MAP.put(jsUrlShare,jsUrlShare);
			}
		}
	}
	
	var jsUrlChildNodes = xmlDoc.getElementsByTagName('jsUrl');
	for(var i=0;i<jsUrlChildNodes.length;i++){
		if(jsUrlChildNodes[i].parentNode.nodeName == viewType){
			var jsUrl = jsUrlChildNodes[i].childNodes[0].nodeValue;
			var responseText = synchronizeAjaxRequest(jsUrl);
			eval(responseText);
		}
	}
	
	var resultJson = getFieldNamesJson(moduleId,xmlDoc);
	var mainClassChildNodes = xmlDoc.getElementsByTagName('jsMainClassName');
	var jsMainClassInstance = null;
	for(var i=0;i<mainClassChildNodes.length;i++){
		if(mainClassChildNodes[i].parentNode.nodeName == viewType){
			var jsMainClassName = mainClassChildNodes[i].childNodes[0].nodeValue;
			jsMainClassInstance = 'new ' + jsMainClassName + '('+resultJson+')';
			break;
		}
	}
	basicConstant.DYNAMIC_JS_INSTANCE_MAP.put(moduleId+'_'+viewType,jsMainClassInstance);
}

/**
 * 加载模块的js通过指定的js路径
 * @param {} xmlDoc			文档对象
 */
function loadModuleJsByJsPath(jsPath){
	if(basicConstant.DYNAMIC_LOAD_JS_MAP.get(jsPath))return;
	var responseText = synchronizeAjaxRequest(jsPath);
	eval(responseText);
	basicConstant.DYNAMIC_LOAD_JS_MAP.put(jsPath,jsPath);
}

/**
 *关闭窗口时销毁session
 */
function sessionInvalidate(){
//	sessionManger.sessionDestroyed();
}
/**
 * 快捷键功能
 * @param {} key
 */
function shortcutKey(key){
	var F1 = function(){
		alert('完善中...');
	}
	var F2 = function(){
		  var userPageLayoutSetId = getExtCmpById('userPageLayoutSetId');
		  if(userPageLayoutSetId == undefined){
			 	var moduleId = 'pageLayoutMgr';
				var listView = basicConstant.DYNAMIC_JS_INSTANCE_MAP.get(moduleId+'_'+basicConstant.LIST_VIEW);
				if(listView == undefined || listView == null){
					var moduleXmlPath = './js/com/bhtec/view/business/commonused/modulexml/'+moduleId+'.xml';
					var xmlDoc = loadXMLFile(moduleXmlPath);//加载模块xml
					dynamicLoadModuleJs(xmlDoc,basicConstant.LIST_VIEW,moduleId);
					listView = basicConstant.DYNAMIC_JS_INSTANCE_MAP.get(moduleId+'_'+basicConstant.LIST_VIEW);
				}
				//模块页面
				var pageLayoutVOp = eval(listView);
				pageLayoutVOp.funForm();
		  }
	}
	var F3 = function(){
		 var userInfoModifyId = getExtCmpById('userInfoModifyId');
      	 if(userInfoModifyId == undefined){
				var moduleId = 'modifyUserMgr';
		    	var listView = basicConstant.DYNAMIC_JS_INSTANCE_MAP.get(moduleId+'_'+basicConstant.LIST_VIEW);
				if(listView == undefined || listView == null){
					var moduleXmlPath = './js/com/bhtec/view/business/commonused/modulexml/'+moduleId+'.xml';
					var xmlDoc = loadXMLFile(moduleXmlPath);//加载模块xml
					dynamicLoadModuleJs(xmlDoc,basicConstant.LIST_VIEW,moduleId);
					listView = basicConstant.DYNAMIC_JS_INSTANCE_MAP.get(moduleId+'_'+basicConstant.LIST_VIEW);
				}
				//模块页面
				var pageLayoutVOp = eval(listView);
				pageLayoutVOp.saveForm();
      	 }
	}
	var F4 = function(){
		var selectedToId = getExtCmpById('selectedToId');
      	if(selectedToId == undefined){
		    var moduleId = 'userCommonFunMgr';
	    	var listView = basicConstant.DYNAMIC_JS_INSTANCE_MAP.get(moduleId+'_'+basicConstant.LIST_VIEW);
			if(listView == undefined || listView == null){
				var moduleXmlPath = './js/com/bhtec/view/business/commonused/modulexml/'+moduleId+'.xml';
				var xmlDoc = loadXMLFile(moduleXmlPath);//加载模块xml
				dynamicLoadModuleJs(xmlDoc,basicConstant.LIST_VIEW,moduleId);
				listView = basicConstant.DYNAMIC_JS_INSTANCE_MAP.get(moduleId+'_'+basicConstant.LIST_VIEW);
			}
			//模块页面
			eval(listView);
      	}
	}
	var F5 = function(){
		
	}
	var F6 = function(){
		askMesg({
    		title:'注销系统',
    		msg:'您确认注销系统?',
    		fn:function(confirm){
    			if(confirm == 'ok'){
    				window.close();
    				window.open('login.html');
    			}
    		}
    	  });
	}
	var F7 = function(){
		askMesg({
    		title:'退出系统',
    		msg:'您确认退出系统?',
    		fn:function(confirm){
    			if(confirm == 'ok')
    				window.close();
    		}
    	  });
	}
	var F8 = function(){
		ajaxRequest({
			url:'roleAction!obtainRoleListByUserId.action',
			params:{},
			callBack:function(returnData){
					var uumRoleUserRefList = returnData.uumRoleUserRefList;
					loadModuleJsByJsPath(basicConstant.jsfile.ROLE_CHANGE);//动态加载切换角色JS
			    	var roleChange = new com.bhtec.view.business.uum.role.RoleChange({
						        		uumRoleUserRefList:uumRoleUserRefList
						        	});
				    roleChange.roleSelectedWindow();
			}
		});
	}
	
	var F9 = function(){
		var userAgentWinId = getExtCmpById('userAgentWinId');
      	if(userAgentWinId == undefined){
		    var moduleId = 'userAgentMgr';
	    	var listView = basicConstant.DYNAMIC_JS_INSTANCE_MAP.get(moduleId+'_'+basicConstant.LIST_VIEW);
			if(listView == undefined || listView == null){
				var moduleXmlPath = './js/com/bhtec/view/business/commonused/modulexml/'+moduleId+'.xml';
				var xmlDoc = loadXMLFile(moduleXmlPath);//加载模块xml
				dynamicLoadModuleJs(xmlDoc,basicConstant.LIST_VIEW,moduleId);
				listView = basicConstant.DYNAMIC_JS_INSTANCE_MAP.get(moduleId+'_'+basicConstant.LIST_VIEW);
			}
			//模块页面
			eval(listView);
      	}
	}
	switch(key){
		case 'F1':
			F1();
		break;
		case 'F2':
			F2();
		break;
		case 'F3':
			F3();
		break;
		case 'F4':
			F4();
		break;
		case 'F5':
			F5();
		break;
		case 'F6':
			F6();
		break;
		case 'F7':
			F7();
		break;
		case 'F8':
			F8();
		break;
		case 'F9':
			F9();
		break;
	}
}

/**
 * 常用功能菜单
 */
function usedFunMenu(){
	var munuItems = [{
		    	id:'pageLayout',
		        text: '页面布局 (F2)',
		        iconCls:'pagePattern',
		        handler: function(){
		        	shortcutKey('F2');
		        }
    		  },{
		    	id:'modifyUser',
		        text: '修改用户 (F3)',
		        iconCls:'user_edit',
		        handler: function(){
		        	shortcutKey('F3');
		        }
    		  },{
		    	id:'usedFun',
		        text: '常用功能 (F4)',
		        iconCls:'usedfuncmaint',
		        handler: function(){
		        	shortcutKey('F4');
		        }
    		  },{
		    	id:'agentSet',
		        text: '代理设置 (F9)',
		        iconCls:'roleassignmgr',
		        handler: function(){
		        	shortcutKey('F9');
		        }
    		  },{
		    	id:'portalMaint',
		        text: 'portal维护 (F10)',
		        iconCls:'portal',
		        handler: ''
    		  }];
    if(multiRoleFlag == 'y'){
    	munuItems.push({
		    	id:'multiRoleChanged',
		        text: '角色切换 (F8)',
		        iconCls:'rolemgr',
		        handler: function(){
		        	shortcutKey('F8');
		        }
    		  })
    }
    var usedMenu = new Ext.menu.Menu({
    	items:munuItems
    });
    return usedMenu;
}
/**
 * 皮肤切换
 */
function changeSkinMenu(){
	var cookie = new com.bhtec.util.Cookie();
	var changeSkinOp = function(item){
        Ext.util.CSS.swapStyleSheet('theme', 'ext/resources/css/' + item.id + '.css');
        //保存主题
        cookie.saveCookie(userCode_cookie+'_bht.theme', item.id);
    }
	var changeSkinMenu = new Ext.menu.Menu();
	var themes = com.bhtec.util.Data.themes;
    for (var i = 0; i < themes.length; i++){
    	var theme = themes[i];
    	var themeName = theme[1];
    	var themeValue = theme[2];
        changeSkinMenu.add({
        	id:themeValue,
            text: themeName,
            iconCls:'skinSelected',
            handler: changeSkinOp
        });
    }
    
     /**
     * 从cookie中获取主题
     * 后面的小括号表示立即执行该函数
     */
    var readThemeFromCookie = (function() {
        var themeValue = cookie.getCookie(userCode_cookie+'_bht.theme');
        themeValue = themeValue==null?basicConstant.DEFAULT_THEME:themeValue;
        if (themeValue) {
        	var themeValueId = {
        		id:themeValue
        	}
            changeSkinOp(themeValueId);
        }
    }).defer(0);
    return changeSkinMenu;
}
/**
 * 禁用F5键等
 * 屏蔽 Ctrl+n
 */       	
function eventFun(event){
	var   event=document.all?window.event:arguments[0];
      if(
      	 (event.ctrlKey && event.keyCode==82)||//屏蔽Ctrl + R
      	 (event.ctrlKey && event.keyCode==78)||//屏蔽 Ctrl+n
      	 (event.keyCode==8)||//屏蔽退格删除键
      	 (event.keyCode==122)||//屏蔽F11
      	 (event.shiftKey && event.keyCode==121)//屏蔽 shift+F10
      	 ){       
          event.keyCode   =   0;       
          event.cancelBubble   =   true;       
          return   false;       
      }
      
      if(event.keyCode==112){//F1
      	  shortcutKey('F1');
		  event.keyCode   =   0;       
          event.cancelBubble   =   true;       
          return   false;
      }else if(event.keyCode==113){//F2
      	  shortcutKey('F2');
		  event.keyCode   =   0;       
          event.cancelBubble   =   true;       
          return   false;
      }else if(event.keyCode==114){//F3键
      	  shortcutKey('F3');
		  event.keyCode   =   0;       
          event.cancelBubble   =   true;       
          return   false;       
      }else if(event.keyCode==115){//F4键
      	  shortcutKey('F4');
		  event.keyCode   =   0;       
          event.cancelBubble   =   true;       
          return   false; 
      }
      else if(event.keyCode==116){//F5键
		  event.keyCode   =   0;       
          event.cancelBubble   =   true;       
          return   false; 
      }
      else if(event.keyCode==117){//F6键
    	  shortcutKey('F6');
		  event.keyCode   =   0;       
          event.cancelBubble   =   true;       
          return   false; 
      }else if(event.keyCode==118){//F7键
    	  shortcutKey('F7');
		  event.keyCode   =   0;       
          event.cancelBubble   =   true;       
          return   false; 
      }else if(event.keyCode==119){//F8键
    	  shortcutKey('F8');
		  event.keyCode   =   0;       
          event.cancelBubble   =   true;       
          return   false; 
      }else if(event.keyCode==120){//F9键
    	  shortcutKey('F9');
		  event.keyCode   =   0;       
          event.cancelBubble   =   true;       
          return   false; 
      }
      
     /* var baseForm = Ext.getCmp(basicConstant.FORM_ID);
      var eventWin = event;
	  if(baseForm){
	  		askMesg({
	  			msg:'请首先退出当前操作窗口!',
	  			fn:function(confirm){
	  				if('ok' == confirm){
	  					baseForm.ownerCt.close();
	  					eventFun(eventWin);
	  				}else{
	  					return;
	  				}
	  			}
	  		})
	  }*/
}

function deleteAccessory(accessoryId, accessoryListId,accessoryPath,oldFileName){
	ajaxRequest({
		url:'accessoryAction!deleteAccessory.action',
		params:{
			accessoryId:accessoryId,
			accessoryPath:accessoryPath,
			oldFileName:oldFileName
		},
		callBack:function(returnData){
			var accessoryList = document.getElementById(accessoryListId);
			var accessory = document.getElementById(accessoryId);
			if(accessory != null)
				accessoryList.removeChild(accessory);
			showSucMesg({
				msg:'附件删除成功!'
			});
		}
	});
}
function encode64(input) {
	 	var keyStr = "ABCDEFGHIJKLMNOP" +
	                "QRSTUVWXYZabcdef" +
	                "ghijklmnopqrstuv" +
	                "wxyz0123456789";
		input = escape(input);
		var output = "";
		var chr1, chr2, chr3 = "";
		var enc1, enc2, enc3, enc4 = "";
		var i = 0;
		do {
			chr1 = input.charCodeAt(i++);
			chr2 = input.charCodeAt(i++);
			chr3 = input.charCodeAt(i++);
			enc1 = chr1 >> 2;
			enc2 = ((chr1 & 3) << 4) | (chr2 >> 4);
			enc3 = ((chr2 & 15) << 2) | (chr3 >> 6);
			enc4 = chr3 & 63;
			if (isNaN(chr2)) {
				enc3 = enc4 = 64;
			} else if (isNaN(chr3)) {
				enc4 = 64;
			}
			output = output + keyStr.charAt(enc1) + keyStr.charAt(enc2)
					+ keyStr.charAt(enc3) + keyStr.charAt(enc4);
			chr1 = chr2 = chr3 = "";
			enc1 = enc2 = enc3 = enc4 = "";
		} while (i < input.length);
		return output;
	}