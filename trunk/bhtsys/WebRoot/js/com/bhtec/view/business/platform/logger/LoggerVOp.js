/**
 * 主框架页面操作
 * @author lianglp
 * @version 2.0
 * @class com.bhtec.bussiness.mainFrameMgr.ModuleVOp
 * @date 2010-07-12
 */
Ext.namespace('com.bhtec.view.business.platform.logger');
com.bhtec.view.business.platform.logger.LoggerVOp = function(config){
	var moduleVOp = this;   //父类调用
	var moduleGridId = 'loggerGridId';
	/**
	 * 点击列表查看，弹出查看页面
	 */
	var viewForm = function(){
		var modDelRecord = modifyDelSelRecord(moduleGridId);//请选择一条件记录
		if(modDelRecord != ''){
			var configFind = {
					url:'loggerAction!findSysplSysOptLogContentById.action',
					params:{modViewRecId:modDelRecord.optId},
					callBack:function(returnData){
						var panel = moduleVOp.formPanel({
							labelWidth:60,
							items:[
								new Ext.form.TextArea({
									value:returnData.model.optContent,
									fieldLabel : config.optContent,
									width:650,
									height:350
								})],
							buttons:[moduleVOp.closeButton({
										handler:function(){
											this.ownerCt.ownerCt.ownerCt.close();
										}
									})]
						});
						moduleVOp.window({
							title:'日志内容查看',
							layout:'fit',
							items:panel
						});
					}
			}
			ajaxRequest(configFind);
		}
	}
	
	
	
	return {
			viewForm:viewForm
	}
}

Ext.extend(com.bhtec.view.business.platform.logger.LoggerVOp,
	com.bhtec.view.util.CommonWidgets, {});