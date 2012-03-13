/**
 * 菜单跳转控制类
 * @author lianglp
 * @version 2.0
 * @class com.bhtec.control.MenuControl
 * @date 2010-07-11
 */
Ext.namespace('com.bhtec.control');
com.bhtec.control.MenuControl = function(config) {
	var moduleId = config.id.trim();
	var belongToSys = config.belongToSys;
	var modPageType = config.modPageType;
	var tabPanel = null;
	if(modPageType == basicConstant.JS){
		var listView = basicConstant.DYNAMIC_JS_INSTANCE_MAP.get(moduleId+'_'+basicConstant.LIST_VIEW);
		if(listView == undefined || listView == null){
			var moduleXmlPath = './js/com/bhtec/view/business/'+belongToSys+'/modulexml/'+moduleId+'.xml';
			var xmlDoc = loadXMLFile(moduleXmlPath);//加载模块xml
			basicConstant.DYNAMIC_LOAD_XMLDOC.put(moduleId,xmlDoc);
			dynamicLoadModuleJs(xmlDoc,basicConstant.LIST_VIEW,moduleId);
			listView = basicConstant.DYNAMIC_JS_INSTANCE_MAP.get(moduleId+'_'+basicConstant.LIST_VIEW);
		}
		var pageList = eval(listView);
		//非列表页面检查
		var isListView = false;
		var notListViewArr = com.bhtec.util.Data.notListViewArr;
		for(var i=0;i<notListViewArr.length;i++){
			var notListModuleId = notListViewArr[i];
			if(moduleId == notListModuleId){
				isListView = true;
				break;
			}
		}
		
		var list = null; 
		if(isListView){//非列表页面
			list = pageList;
		}else if(pageList){//列表页面
		 	list = new com.bhtec.view.util.List(pageList);
		}else{//默认列表页面
			list = new com.bhtec.view.util.List();
		}
		tabPanel = list.formPanel;
	}else if(modPageType == basicConstant.JSP){
		var htmlContent = "<iframe name='listJsp' src=page/jsp/"
							+belongToSys+"/"+moduleId
							+".jsp?moduleId="+moduleId+"&text="+config.text
							+" width=100% height=100%></iframe>";
		tabPanel = {
			xtype : 'panel',
			autoHeight:true,
			autoWidth:true,
			html: htmlContent
		}
	}else if(modPageType == basicConstant.HTML){
		tabPanel = {
			xtype : 'panel',
			autoHeight:true,
			autoWidth:true,
			html: "<iframe name='listHtml' src=page/html/"+belongToSys+"/"+moduleId+".html width=100% height=100%></iframe>"
		}
	}
		
	var addTab = function() {
		//主页面板
		var mainPagePanel = getExtCmpById(basicConstant.MAIN_PAGE_ID);
        mainPagePanel.add({
        	id:config.id+'_tab',
            title: config.text,
            iconCls: config.iconCls,            
            closable:true,
            border:false,
            bodyStyle : basicConstant.PAGE_BACKGROUND,
            items:tabPanel
        }).show();
    }
    
    return {
    	addTab:addTab
    }
   
}