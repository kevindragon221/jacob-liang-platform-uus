/**
 * 平台菜单跳转控制类
 * @author lianglp
 * @version 1.0
 * @class com.bhtec.control.MenuControlPlt
 * @date 2010-11-20
 */

Ext.namespace('com.bhtec.control');
com.bhtec.control.MenuControlPlt = function(config) {
	var pageList;//页面列表
	var pageForm;//页面表单
	var moduleId = config.id.trim();
	if(moduleId == mainFrameFunConstant.MODULE_ID){//主页功能区管理
		var xmlDoc = loadXMLFile(mainFrameFunConstant.XML_FILE);//加载模块xml
		if(config.moduleLabel == true){
			var moduleLabelList = getModuleLabelList(xmlDoc);
			return {
				moduleLabelList:moduleLabelList,
				xmlFile:mainFrameFunConstant.XML_FILE
			};
		}
		loadModuleJs(xmlDoc);//动态加载js
		var configModule = {
			xmlDoc:xmlDoc,
			moduleForm:new com.bhtec.view.business.platform.mainframefun.MainFrameFunVOp({xmlDoc:xmlDoc}),
			moduleId:moduleId
		}
		//页面列表
		pageList = new com.bhtec.view.business.platform.mainframefun.MainFrameFunList(configModule);
	}else if(moduleId == moduleConstant.MODULE_ID){//模块菜单管理
		var xmlDoc = loadXMLFile(moduleConstant.XML_FILE);//加载模块xml
		if(config.moduleLabel == true){
			var moduleLabelList = getModuleLabelList(xmlDoc);
			return {
				moduleLabelList:moduleLabelList,
				xmlFile:moduleConstant.XML_FILE
			};
		}
		loadModuleJs(xmlDoc);//动态加载js
		var configModule = {
			xmlDoc:xmlDoc,
			moduleForm:new com.bhtec.view.business.platform.module.ModuleVOp({xmlDoc:xmlDoc}),
			moduleId:moduleId
		}
		//页面列表
		pageList = new com.bhtec.view.business.platform.module.ModuleList(configModule);
	}else if(moduleId == afficheConstant.MODULE_ID){//公告管理
		var xmlDoc = loadXMLFile(afficheConstant.XML_FILE);//加载模块xml
		if(config.moduleLabel == true){
			var moduleLabelList = getModuleLabelList(xmlDoc);
			return {
				moduleLabelList:moduleLabelList,
				xmlFile:afficheConstant.XML_FILE
			};
		}
		loadModuleJs(xmlDoc);//动态加载js
		var configModule = {
			xmlDoc:xmlDoc,
			moduleForm:new com.bhtec.view.business.platform.affiche.AfficheVOp({xmlDoc:xmlDoc}),
			moduleId:moduleId
		}
		//页面列表
		pageList = new com.bhtec.view.business.platform.affiche.AfficheList(configModule);
	}else if(moduleId == moduleOptConstant.MODULE_ID){//模块操作管理
		var xmlDoc = loadXMLFile(moduleOptConstant.XML_FILE);//加载模块xml
		if(config.moduleLabel == true){
			var moduleLabelList = getModuleLabelList(xmlDoc);
			return {
				moduleLabelList:moduleLabelList,
				xmlFile:moduleOptConstant.XML_FILE
			};
		}
		loadModuleJs(xmlDoc);//动态加载js
		var configModule = {
			xmlDoc:xmlDoc,
			moduleForm:new com.bhtec.view.business.platform.moduleopt.ModuleOptVOp({xmlDoc:xmlDoc}),
			moduleId:moduleId
		}
		//页面列表
		pageList = new com.bhtec.view.business.platform.moduleopt.ModuleOptList(configModule);
	}else if(moduleId == systemConfigConstant.MODULE_ID){//系统配置管理
		var xmlDoc = loadXMLFile(systemConfigConstant.XML_FILE);//加载模块xml
		if(config.moduleLabel == true){
			var moduleLabelList = getModuleLabelList(xmlDoc);
			return {
				moduleLabelList:moduleLabelList,
				xmlFile:systemConfigConstant.XML_FILE
			};
		}
		loadModuleJs(xmlDoc);//动态加载js
		pageForm = new com.bhtec.view.business.platform.systemconfig.SystemConfigVOp({xmlDoc:xmlDoc});
	}else if(moduleId == systemParaConstant.MODULE_ID){//系统参数管理
		var xmlDoc = loadXMLFile(systemParaConstant.XML_FILE);//加载模块xml
		if(config.moduleLabel == true){
			var moduleLabelList = getModuleLabelList(xmlDoc);
			return {
				moduleLabelList:moduleLabelList,
				xmlFile:systemParaConstant.XML_FILE
			};
		}
		loadModuleJs(xmlDoc);//动态加载js
		var configModule = {
			xmlDoc:xmlDoc,
			moduleForm:new com.bhtec.view.business.platform.systemparameter.SystemParameterVOp({xmlDoc:xmlDoc}),
			moduleId:moduleId
		}
		//页面列表
		pageList = new com.bhtec.view.business.platform.systemparameter.SystemParameterList(configModule);
	}else if(moduleId == typeDictionaryConstant.MODULE_ID){//类别字典管理
		var xmlDoc = loadXMLFile(typeDictionaryConstant.XML_FILE);//类别字典xml
		if(config.moduleLabel == true){
			var moduleLabelList = getModuleLabelList(xmlDoc);
			return {
				moduleLabelList:moduleLabelList,
				xmlFile:typeDictionaryConstant.XML_FILE
			};
		}
		loadModuleJs(xmlDoc);//动态加载js
		var configModule = {
			xmlDoc:xmlDoc,
			moduleForm:new com.bhtec.view.business.platform.typedictionary.TypeDictionaryVOp({xmlDoc:xmlDoc}),
			moduleId:moduleId
		}
		//页面列表
		pageList = new com.bhtec.view.business.platform.typedictionary.TypeDictionaryList(configModule);
	}else if(moduleId == districtConstant.MODULE_ID){//省市地区管理管理
		var xmlDoc = loadXMLFile(districtConstant.XML_FILE);//省市地区管理xml
		if(config.moduleLabel == true){
			var moduleLabelList = getModuleLabelList(xmlDoc);
			return {
				moduleLabelList:moduleLabelList,
				xmlFile:districtConstant.XML_FILE
			};
		}
		loadModuleJs(xmlDoc);//动态加载js
		var configModule = {
			xmlDoc:xmlDoc,
			moduleForm:new com.bhtec.view.business.platform.district.DistrictVOp({xmlDoc:xmlDoc}),
			moduleId:moduleId
		}
		//页面列表
		pageList = new com.bhtec.view.business.platform.district.DistrictList(configModule);
	}else if(moduleId == loggerConstant.MODULE_ID){//省市地区管理管理
		var xmlDoc = loadXMLFile(loggerConstant.XML_FILE);//省市地区管理xml
		if(config.moduleLabel == true){
			var moduleLabelList = getModuleLabelList(xmlDoc);
			return {
				moduleLabelList:moduleLabelList,
				xmlFile:loggerConstant.XML_FILE
			};
		}
		loadModuleJs(xmlDoc);//动态加载js
		var configModule = {
			xmlDoc:xmlDoc,
			moduleForm:new com.bhtec.view.business.platform.logger.LoggerVOp({xmlDoc:xmlDoc}),
			moduleId:moduleId
		}
		//页面列表
		pageList = new com.bhtec.view.business.platform.logger.LoggerList(configModule);
	}else if(moduleId == codeConstant.MODULE_ID){//省市地区管理管理
		var xmlDoc = loadXMLFile(codeConstant.XML_FILE);//省市地区管理xml
		if(config.moduleLabel == true){
			var moduleLabelList = getModuleLabelList(xmlDoc);
			return {
				moduleLabelList:moduleLabelList,
				xmlFile:codeConstant.XML_FILE
			};
		}
		loadModuleJs(xmlDoc);//动态加载js
		var configModule = {
			xmlDoc:xmlDoc,
			moduleForm:new com.bhtec.view.business.platform.code.CodeVOp({xmlDoc:xmlDoc}),
			moduleId:moduleId
		}
		//页面列表
		pageList = new com.bhtec.view.business.platform.code.CodeList(configModule);
	}else if(moduleId == schedulerConstant.MODULE_ID){//调度管理
		var xmlDoc = loadXMLFile(schedulerConstant.XML_FILE);//调度管理xml
		if(config.moduleLabel == true){
			var moduleLabelList = getModuleLabelList(xmlDoc);
			return {
				moduleLabelList:moduleLabelList,
				xmlFile:schedulerConstant.XML_FILE
			};
		}
		loadModuleJs(xmlDoc);//动态加载js
		var configModule = {
			xmlDoc:xmlDoc,
			moduleForm:new com.bhtec.view.business.platform.scheduler.SchedulerVOp({xmlDoc:xmlDoc}),
			moduleId:moduleId
		}
		//页面列表
		pageList = new com.bhtec.view.business.platform.scheduler.SchedulerList(configModule);
	}
	
    return {
    	pageList:pageList,
    	pageForm:pageForm
    }
   
}