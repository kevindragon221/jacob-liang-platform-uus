/**
 * 统一用户菜单跳转控制类
 * @author lianglp
 * @version 1.0
 * @class com.bhtec.control.MenuControlUum
 * @date 2010-11-20
 */

Ext.namespace('com.bhtec.control');
com.bhtec.control.MenuControlUum = function(config) {
	var pageList;//页面列表
	var pageForm;//页面表单
	var moduleId = config.id.trim();
	//统一用户
	if(moduleId == organConstant.MODULE_ID){//机构管理
		var xmlDoc = loadXMLFile(organConstant.XML_FILE);//加载模块xml
		if(config.moduleLabel == true){
			var moduleLabelList = getModuleLabelList(xmlDoc);
			return {
				moduleLabelList:moduleLabelList,
				xmlFile:organConstant.XML_FILE
			};
		}
		loadModuleJs(xmlDoc);//动态加载js
		var configModule = {
			xmlDoc:xmlDoc,
			moduleForm:new com.bhtec.view.business.uum.organ.OrganVOp({xmlDoc:xmlDoc}),
			moduleId:moduleId
		}
		//页面列表
		pageList = new com.bhtec.view.business.uum.organ.OrganList(configModule);
	}else if(moduleId == roleConstant.MODULE_ID){//角色管理
		var xmlDoc = loadXMLFile(roleConstant.XML_FILE);//加载模块xml
		if(config.moduleLabel == true){
			var moduleLabelList = getModuleLabelList(xmlDoc);
			return {
				moduleLabelList:moduleLabelList,
				xmlFile:roleConstant.XML_FILE
			};
		}
		loadModuleJs(xmlDoc);//动态加载js
		var configModule = {
			xmlDoc:xmlDoc,
			moduleForm:new com.bhtec.view.business.uum.role.RoleVOp({xmlDoc:xmlDoc}),
			moduleId:moduleId
		}
		//页面列表
		pageList = new com.bhtec.view.business.uum.role.RoleList(configModule);
	}else if(moduleId == roleOrganConstant.MODULE_ID){//角色分配给机构管理
		var xmlDoc = loadXMLFile(roleOrganConstant.XML_FILE);//加载模块xml
		if(config.moduleLabel == true){
			var moduleLabelList = getModuleLabelList(xmlDoc);
			return {
				moduleLabelList:moduleLabelList,
				xmlFile:roleOrganConstant.XML_FILE
			};
		}
		loadModuleJs(xmlDoc);//动态加载js
		var configModule = {
			xmlDoc:xmlDoc,
			moduleForm:new com.bhtec.view.business.uum.role.RoleOrganVOp({xmlDoc:xmlDoc}),
			moduleId:moduleId
		}
		//页面列表
		pageList = new com.bhtec.view.business.uum.role.RoleOrganList(configModule);
	}else if(moduleId == userConstant.MODULE_ID){//角色分配给机构管理
		var xmlDoc = loadXMLFile(userConstant.XML_FILE);//加载模块xml
		if(config.moduleLabel == true){
			var moduleLabelList = getModuleLabelList(xmlDoc);
			return {
				moduleLabelList:moduleLabelList,
				xmlFile:userConstant.XML_FILE
			};
		}
		loadModuleJs(xmlDoc);//动态加载js
		var configModule = {
			xmlDoc:xmlDoc,
			moduleForm:new com.bhtec.view.business.uum.user.UserVOp({xmlDoc:xmlDoc}),
			moduleId:moduleId
		}
		//页面列表
		pageList = new com.bhtec.view.business.uum.user.UserList(configModule);
	}else if(moduleId == userGroupConstant.MODULE_ID){//角色分配给机构管理
		var xmlDoc = loadXMLFile(userGroupConstant.XML_FILE);//加载模块xml
		if(config.moduleLabel == true){
			var moduleLabelList = getModuleLabelList(xmlDoc);
			return {
				moduleLabelList:moduleLabelList,
				xmlFile:userGroupConstant.XML_FILE
			};
		}
		loadModuleJs(xmlDoc);//动态加载js
		var configModule = {
			xmlDoc:xmlDoc,
			moduleForm:new com.bhtec.view.business.uum.group.userGroup.UserGroupVOp({xmlDoc:xmlDoc}),
			moduleId:moduleId
		}
		//页面列表
		pageList = new com.bhtec.view.business.uum.group.userGroup.UserGroupList(configModule);
	}else if(moduleId == roleGroupConstant.MODULE_ID){//角色分配给机构管理
		var xmlDoc = loadXMLFile(roleGroupConstant.XML_FILE);//加载模块xml
		if(config.moduleLabel == true){
			var moduleLabelList = getModuleLabelList(xmlDoc);
			return {
				moduleLabelList:moduleLabelList,
				xmlFile:roleGroupConstant.XML_FILE
			};
		}
		loadModuleJs(xmlDoc);//动态加载js
		var configModule = {
			xmlDoc:xmlDoc,
			moduleForm:new com.bhtec.view.business.uum.group.roleGroup.RoleGroupVOp({xmlDoc:xmlDoc}),
			moduleId:moduleId
		}
		//页面列表
		pageList = new com.bhtec.view.business.uum.group.roleGroup.RoleGroupList(configModule);
	}else if(moduleId == generalGroupConstant.MODULE_ID){//角色分配给机构管理
		var xmlDoc = loadXMLFile(generalGroupConstant.XML_FILE);//加载模块xml
		if(config.moduleLabel == true){
			var moduleLabelList = getModuleLabelList(xmlDoc);
			return {
				moduleLabelList:moduleLabelList,
				xmlFile:generalGroupConstant.XML_FILE
			};
		}
		loadModuleJs(xmlDoc);//动态加载js
		var configModule = {
			xmlDoc:xmlDoc,
			moduleForm:new com.bhtec.view.business.uum.group.generalGroup.GeneralGroupVOp({xmlDoc:xmlDoc}),
			moduleId:moduleId
		}
		//页面列表
		pageList = new com.bhtec.view.business.uum.group.generalGroup.GeneralGroupList(configModule);
	}
	
    return {
    	pageList:pageList,
    	pageForm:pageForm
    }
   
}