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
 *同步ajax请求 
 * @param {} url地址
 * @return {}
 */
function synchronizeAjaxRequest(url,paras){
	var xmlhttp=null;
	if (window.XMLHttpRequest){// code for Firefox, Opera, IE7, etc.
		xmlhttp=new XMLHttpRequest();
	}else if (window.ActiveXObject){// code for IE6, IE5
		xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
	}	

	if (xmlhttp != null) {
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
				if(checkUserSessionStatus(xmlhttpData))return '';//是否超时
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
	if (window.ActiveXObject) {
		xmlDoc = new ActiveXObject("Msxml2.DOMDocument");
		xmlDoc.async = false;		
		xmlDoc.load(xmlFile);
	} else {
		if (document.implementation && document.implementation.createDocument) {
			xmlDoc = document.implementation.createDocument("text/xml", "",
					null);
			xmlDoc.load(xmlFile);
		} else {
			return null;
		}
	}
	return xmlDoc;
}

/**
 * 根据xpath的到dom对象里的某个元素
 * 
 * @param {} xmlDoc			文档对象
 * @param {} elementPath	文档路径
 * @return {}
 */
function selectSingleNode(xmlDoc, elementPath) {
	if (window.ActiveXObject) {
		return xmlDoc.selectSingleNode(elementPath);
	} else {
		var xpe = new XPathEvaluator();
		var nsResolver = xpe.createNSResolver(xmlDoc.ownerDocument == null
				? xmlDoc.documentElement
				: xmlDoc.ownerDocument.documentElement);
		var results = xpe.evaluate(elementPath, xmlDoc, nsResolver,
				XPathResult.FIRST_ORDERED_NODE_TYPE, null);
		return results.singleNodeValue;
	}
}
/**
 * 获得模块要加载的js
 * @param {} xmlDoc	文档对象
 * @return {}
 */
function getModuleJavaScripts(xmlDoc){
	  var javaScriptsNode = selectSingleNode(xmlDoc,'//javaScripts');
	  var childNodes = javaScriptsNode.childNodes;
	  var javaScripts = new Array();
	  for(i=0;i<childNodes.length;i++){
	  		javaScripts.push(childNodes[i].text);
	  }
	  return javaScripts;
}
/**
 * 获得模块标签列表
 * @param {} xmlDoc	文档对象
 * @return {}
 */
function getModuleLabelList(xmlDoc){
	  var javaScriptsNode = selectSingleNode(xmlDoc,'//fieldNames');
	  var childNodes = javaScriptsNode.childNodes;
	  var moduleLabelList = new Array();
	  for(i=0;i<childNodes.length;i++){
	  		moduleLabelList.push({
	  			fieldName:childNodes[i].attributes[0].value,
	  			fieldLabel:childNodes[i].text
	  		});
	  }
	  return moduleLabelList;
}
/**
 * 获得域的中文名称
 * @param {} xmlDoc			文档对象
 * @param {} fieldName		域名称
 * @return {}
 */
function getFormFieldName(xmlDoc,fieldName){
	 var field = selectSingleNode(xmlDoc,"/module/fieldNames/field[@name='"+fieldName+"']");
	 return field.text;
}

/**
 * 加载模块的js通过XML文件
 * @param {} xmlDoc			文档对象
 */
function loadModuleJs(xmlDoc){
	var javaScripts = getModuleJavaScripts(xmlDoc);
	for(i=0;i<javaScripts.length;i++){
		var responseText = synchronizeAjaxRequest(javaScripts[i]);
		eval(responseText);
	}
}

/**
 * 加载模块的js通过指定的js路径
 * @param {} xmlDoc			文档对象
 */
function loadModuleJsByJsPath(jsPath){
	var responseText = synchronizeAjaxRequest(jsPath);
	eval(responseText);
}

/**
根据xpath的到dom对象里的元素集合
*/
function SelectNodes(xNode, cXPathString){
    if(window.ActiveXObject){
        return xNode.selectNodes(cXPathString);
    }else{
       var xpe = new XPathEvaluator();
       var nsResolver = xpe.createNSResolver( xNode.ownerDocument == null ? xNode.documentElement : xNode.ownerDocument.documentElement);
       	   // check for XPath implementation
		if( document.implementation.hasFeature("XPath", "3.0") ){
		     // prototying the XMLDocument
		      if( !xNode ) { xNode = this; } 
		      var aItems = xpe.evaluate(cXPathString, xNode, nsResolver, 
		                   XPathResult.ORDERED_NODE_SNAPSHOT_TYPE, null)
		      var aResult = [];
		      for( var i = 0; i < aItems.snapshotLength; i++){
		         aResult[i] =  aItems.snapshotItem(i);
		      }
		      return aResult;
		      // prototying the Element
		      if(this.ownerDocument.selectNodes){
		         return xpe.ownerDocument.selectNodes(cXPathString, this);
		      }
		      else{throw "For XML Elements Only";}
		}
       
    }
}

/**  
 * 获取所有节点的内容  
 * @param   {Object}    xml DOM Node or xml Document  
 * @return  {String}  
 */  
function xmlValue(/* Document|Element */node){   
    var val = '';   
    if (node.nodeType == DOM_TEXT_NODE ||   
        node.nodeType == DOM_CDATA_SECTION_NODE ||   
        node.nodeType == DOM_ATTRIBUTE_NODE){   
        val += node.nodeValue;   
    }else if (node.nodeType == DOM_ELEMENT_NODE ||   
              node.nodeType == DOM_DOCUMENT_NODE ||   
              node.nodeType == DOM_DOCUMENT_FRAGMENT_NODE){   
        for (var len = node.childNodes.length, i = 0; i < len; i++){   
            val += arguments.callee(node.childNodes[i]);   
        }   
    }   
    return val;   
}

/**
添加子元素 
xmlObj:xml对象
xpath: 要添加的新元素的父元素的xpath
elementName:元素名称
initValue: 元素初始值
*/
function addElement(xmlObj,xpath,elementName,initValue){
	var element = xmlObj.createElement(elementName);
	var initText = xmlObj.createTextNode(initValue);
		element.appendChild(initText);
	var parEle = SelectSingleNode(xmlObj,xpath);
		parEle.appendChild(element);
}

/**
*创建元素
*/
function createElement(xmlObj,elementName,initValue){
	var element = xmlObj.createElement(elementName);
		element.appendChild(xmlObj.createTextNode(initValue));
	return element;
}

/**
 *关闭窗口时销毁session
 */
function sessionInvalidate(){
	sessionManger.sessionDestroyed();
}
/**
 * 禁用F5键
 * 屏蔽 Ctrl+n
 */       
function document.onkeydown(){ 
	
//	var eventFun = function(event){
      if(
//      	 (event.keyCode==116)||//禁用F5键
      	 (event.ctrlKey && event.keyCode==82)||//屏蔽Ctrl + R
      	 (event.ctrlKey && event.keyCode==78)||//屏蔽 Ctrl+n
//      	 (event.keyCode==8)||//屏蔽退格删除键
      	 (event.keyCode==122)||//屏蔽F11
      	 (event.shiftKey && event.keyCode==121)//屏蔽 shift+F10
      	 ){       
          event.keyCode   =   0;       
          event.cancelBubble   =   true;       
          return   false;       
      }
      if(event.keyCode==113){//F2
      	  var userPageLayoutSetId = getExtCmpById('userPageLayoutSetId');
      	  if(userPageLayoutSetId == undefined){
		      var xmlDoc = loadXMLFile(pageLayoutConstant.XML_FILE);//加载模块xml
			  loadModuleJs(xmlDoc);//动态加载js
			  //模块页面
			  var pageLayoutVOp = new com.bhtec.view.business.commonused.userpagelayout.UserPageLayout({xmlDoc:xmlDoc});
			  pageLayoutVOp.funForm({});
      	  }
		  event.keyCode   =   0;       
          event.cancelBubble   =   true;       
          return   false;
      }else if(event.keyCode==114){//F3键
      	  var userInfoModifyId = getExtCmpById('userInfoModifyId');
      	  if(userInfoModifyId == undefined){
	      	  var xmlDoc = loadXMLFile(userInfoModify.XML_FILE);//加载模块xml
			  loadModuleJs(xmlDoc);//动态加载js
	    	  //模块页面
			  var userInfoModifyForm = new com.bhtec.view.business.commonused.userinfomodify.UserInfoModify({xmlDoc:xmlDoc});
			  userInfoModifyForm.saveForm();
      	  }
		  event.keyCode   =   0;       
          event.cancelBubble   =   true;       
          return   false;       
      }else if(event.keyCode==115){//F4键
      	  var selectedToId = getExtCmpById('selectedToId');
      	  if(selectedToId == undefined){
	      	  loadModuleJsByJsPath(jsfile.COMMON_FUNCTION);//动态加载常用功能JS
			  new com.bhtec.view.business.commonused.commonfuntion.UserCommonFunction();
      	  }
		  event.keyCode   =   0;       
          event.cancelBubble   =   true;       
          return   false; 
      }
//      else if(event.keyCode==116){//F5键
//		  event.keyCode   =   0;       
//          event.cancelBubble   =   true;       
//          return   false; 
//      }
      else if(event.keyCode==117){//F6键
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
		  event.keyCode   =   0;       
          event.cancelBubble   =   true;       
          return   false; 
      }else if(event.keyCode==118){//F7键
    	  askMesg({
    		title:'退出系统',
    		msg:'您确认退出系统?',
    		fn:function(confirm){
    			if(confirm == 'ok')
    				window.close();
    		}
    	  });
		  event.keyCode   =   0;       
          event.cancelBubble   =   true;       
          return   false; 
      }
//     }
      
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
//禁止右键弹出菜单       
function document.oncontextmenu(){ 
      return   false;       
} 
//屏蔽F1帮助 
function window.onhelp(){
	alert('待开发...');
	return false;
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