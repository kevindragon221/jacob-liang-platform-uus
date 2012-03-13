/**
 * 主框架页面操作
 * @author lianglp
 * @version 2.0
 * @class com.bhtec.bussiness.mainFrameMgr.MainFrameVOp
 * @date 2010-07-12
 */
Ext.namespace('com.bhtec.view.business.platform.mainframefun');
com.bhtec.view.business.platform.mainframefun.MainFrameFunVOp = function(config){
	var mainFrameVOp = this;   //父类调用
	var mainFrameFunGridId = 'mainFrameFunGridId';//form表单id
	
	/**
	 * 功能区增加修改表单
	 */
	var funForm = function(configForm){
		/**
		 * 公用保存设置
		 */
		var saveCommon = function(saveFlag) {
				var configSave = {
					url : 'mainFrameFunAction!saveMainFrameFun.action',
					params : {
						funName : getExtCmpValueById('funName'),
						funURI : getExtCmpValueById('funURI')
					},
					callBack : function(returnData) {
						var configCb = {
							msg : '功能区保存成功!',
							fn : function(confirm) {
								if ('ok' == confirm) {
									if (saveFlag == 'save') {
										refreshGridList(mainFrameFunGridId);
										getExtCmpById(mainFrameVOp.cuvWindowId).close();
									} else {
										mainFrameVOp.enableSaveButton();//添加保存enable
										resetForm(basicConstant.FORM_ID);
										refreshGridList(mainFrameFunGridId);
									}

								}
							}
						}
						showSucMesg(configCb);
					}
				}
				submitForm(configSave);
		}
		/**
		 * 点击保存按钮调用方法
		 */
		var save = function() {
			saveCommon('save');
		}

		/**
		 * 点击保存增加按钮调用方法
		 */
		var saveAdd = function() {
			saveCommon('saveAdd');
		}
		/**
		 * 公用增加修改删除表单
		 */
		var mainFrameFun = configForm.mainFrameFun||'';
		
		//隐含功能区名称
		var funNameOld = new Ext.form.Hidden({
				id : "funNameOldId",
				value:mainFrameFun.funName||'withoutvalue'
		})

		//功能区名称
		var funName = mainFrameVOp.comboBox({
				id : "funName",
				allowBlank:false,
				fieldLabel : config.funName,
				value:mainFrameFun.funName||'header',
				store:new Ext.data.JsonStore({
						data 	: com.bhtec.util.Data.funArea,
						fields 	: com.bhtec.util.Data.funAreaFields
					}),
				valueField 	: 'funArea',
				displayField: 'funAreaName',
				allowBlank:false
		});
		
		//funId
		var funId = new Ext.form.Hidden({
				name : "funId",
				value:mainFrameFun.funId||''
		});
		
		//功能区URI
		var funURI = mainFrameVOp.comboBox({
				id : "funURI",
				value:mainFrameFun.funURI||'general',
				fieldLabel : config.funURI,
				store:new Ext.data.JsonStore({
						data 	: com.bhtec.util.Data.pageStyle,
						fields 	: com.bhtec.util.Data.pageStyleFields
					}),
				valueField 	: 'pageStyle',
				displayField: 'pageStyleName',
				allowBlank:false
		});
		//功能描述
		var funURIDesc = mainFrameVOp.textField({
				name : "funURIDesc",
				value:mainFrameFun.funURIDesc||'',
				fieldLabel : config.funURIDesc
		});
		
		//功能区备注
		var funMemo = mainFrameVOp.textField({
				name : "funMemo",
				value:mainFrameFun.funMemo||'',
				fieldLabel : config.funMemo
		});
		//调用父类方法进行窗口构造
		mainFrameVOp.cuvWindow({
			title:configForm.title,				//窗口title
			columnFields:[funName,funURI,funURIDesc,funMemo,
						   		funNameOld,funId],	//表单第二列
			save:save,							//保存按钮调用的方法
			saveAdd:saveAdd,					//保存并添加按钮调用的方法
			modify:configForm.modify,			//窗口判断是否显示保存增加按钮
			allButtonHidden:configForm.allButtonHidden
		});	
	};
	
	/**
	 * 点击列表保存，弹出保存页面
	 */
	var saveForm = function(){
		var configSave = {
			title:'页面功能区添加'
		}
		funForm(configSave);
	}
	/**
	 * 点击列表修改，弹出修改页面
	 */
	var modifyForm = function(){
		var modDelRecord = modifyDelSelRecord(mainFrameFunGridId);//请选择一条件记录
		if(modDelRecord != ''){
			var configFind = {
					url:'mainFrameFunAction!findMainFrameFunByFunId.action',
					params:{modViewRecId:modDelRecord.funId},
					callBack:function(returnData){
						var configForm = {
							title:'页面功能区修改',
							mainFrameFun:returnData.model,
							modify:true
						}
						funForm(configForm);
					}
			}
			ajaxRequest(configFind);
		}
	}
	/**
	 * 点击列表查看，弹出查看页面
	 */
	var viewForm = function(){
		var modDelRecord = modifyDelSelRecord(mainFrameFunGridId);//请选择一条件记录
		if(modDelRecord != ''){
			var configFind = {
					url:'mainFrameFunAction!findMainFrameFunByFunId.action',
					params:{modViewRecId:modDelRecord.funId},
					callBack:function(returnData){
						var configForm = {
							title:'页面功能区查看',
							mainFrameFun:returnData.model,
							allButtonHidden:true
						}
						funForm(configForm);
					}
			}
			ajaxRequest(configFind);
		}
	}
	
	/**
	 * 点击删除按钮调用方法
	 */
	var delRecord = function() {
		if (isSelRecord(mainFrameFunGridId)) {
			askMesg({
				msg:'您确认删除此记录吗?',
				fn:function(confirm){
					if(confirm == 'ok'){
						var configDel = {
							url : 'mainFrameFunAction!deleteMainFrameFun.action',
							params : {
								delRecIds : getDelRedIds('funId', mainFrameFunGridId)
							},
							callBack : function(returnData) {
								var configCb = {
									msg : '功能区删除成功!',
									fn : function(confirm) {
										if ('ok' == confirm) {
											refreshGridList(mainFrameFunGridId);
										}
									}
								}
								showSucMesg(configCb);
							}
						}
						ajaxRequest(configDel);
					}
				}
			})
		}

	}
	
	return {
			saveForm:saveForm,
			delRecord:delRecord,
			modifyForm:modifyForm,
			viewForm:viewForm
	}
}

Ext.extend(com.bhtec.view.business.platform.mainframefun.MainFrameFunVOp, com.bhtec.view.util.CommonWidgets, {});