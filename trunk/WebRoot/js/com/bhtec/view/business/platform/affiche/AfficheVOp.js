/**
 * 公告操作
 * @author lianglp
 * @version 2.0
 * @class com.bhtec.view.business.platform.affiche.AfficheVOp
 * @date 2010-12-01
 */
Ext.namespace('com.bhtec.view.business.platform.affiche');
com.bhtec.view.business.platform.affiche.AfficheVOp = function(config){
	var moduleVOp = this;   //父类调用
	var moduleGridId = 'afficheGridId';//form表单id
	
	/**
	 * 公告增加修改表单
	 */
	var funForm = function(configForm){
		/**
		 * 公用保存设置
		 */
		var saveCommon = function(configSave) {
				submitForm({
					url : configSave.url,
					params : {
						affichePulish:getExtCmpValueById('affichePulish').inputValue
					},
					callBack : function(returnData) {
						var configCb = {
							msg : '系统公告保存成功!',
							fn : function(confirm) {
								if ('ok' == confirm) {
									if (configSave.save == 'save') {
										refreshGridList(moduleGridId);
										getExtCmpById(moduleVOp.cuvWindowId).close();
									} else {
										moduleVOp.enableSaveButton();//添加保存enable
										resetForm(basicConstant.FORM_ID);
										refreshGridList(moduleGridId);
									}
								}
							}
						}
						showSucMesg(configCb);
					}
				});
		}
		/**
		 * 点击保存按钮调用方法
		 */
		var save = function() {
			var url = 'afficheAction!saveAffiche.action';
			if(configForm.modify == true){
				url = 'afficheAction!modifyAffiche.action';
			}
			var configSave = {
				url:url,
				save:'save'
			}
			saveCommon(configSave);
		}

		/**
		 * 点击保存增加按钮调用方法
		 */
		var saveAdd = function() {
			var configSave = {
				url:'afficheAction!saveAffiche.action',
				save:'saveAdd'
			}
			saveCommon(configSave);
		}
		/**
		 * 公用增加修改删除表单
		 */
		var moduleData = configForm.moduleData||'';
		
		//公告ID
		var afficheId = new Ext.form.Hidden({
				name : "afficheId",
				value:moduleData.afficheId||'0'
		})

		//公告标题
		var afficheTitle = moduleVOp.textField({
				name : "afficheTitle",
				allowBlank:false,
				fieldLabel : config.afficheTitle,
				value:moduleData.afficheTitle||'',
				maxLength:100
		});
		
		//公告有效期
		var afficheInvalidate = moduleVOp.dateField({
				name : "afficheInvalidate",
				fieldLabel : config.afficheInvalidate,
				format:'Y-m-d',
				value:moduleData.afficheInvalidate||'',
				allowBlank:false
		});
		//是否发布
		var affichePulish = moduleVOp.radio({
				id : "affichePulish",
				name : "affichePulish",
				value:moduleData.affichePulish||'',
				fieldLabel : config.affichePulish,
				width:150,
				items:[
					{boxLabel: '是', name: 'publish', inputValue: 0, 
						checked: ((moduleData.affichePulish==0
									||moduleData.affichePulish==undefined)
									?true:false)},
                	{boxLabel: '否', name: 'publish', inputValue: 1,
                		checked: (moduleData.affichePulish==1?true:false)}
                ]
		});
		//公告内容
		var afficheContent = {
			xtype:'htmleditor',
			name:'afficheContent',
			value:moduleData.afficheContent||'',
			fieldLabel : config.afficheContent,
			width: 450,
		    height: 130
		}
		var columnFields = new Array();
		columnFields.push({
			border : false,
			layout : "form",
			columnWidth : .5,
			items : afficheTitle
		});
		columnFields.push({
			border : false,
			layout : "form",
			columnWidth : .5,
			items : afficheInvalidate
		});
		columnFields.push({
			border : false,
			layout : "form",
			columnWidth : 1,
			items : affichePulish
		});
		columnFields.push({
			border : false,
			layout : "form",
			columnWidth : 1,
			items : afficheContent
		});
		
		//公告附件添加
		var afficheAccessory = new com.bhtec.view.util.Accessory({
			formId:basicConstant.FORM_ID,
			accessoryLabelName:'公告附件',
			accessoryListId:'accessoryListId',
			accessoryList:moduleData.accessoryList,
			filePath:'uploadFile/affiche/',
			viewAccessory:configForm.allButtonHidden
		});
		columnFields = columnFields.concat(afficheAccessory);
		columnFields.push(afficheId);
	    //调用父类方法进行窗口构造
		moduleVOp.cuvWindow({
			title:configForm.title,				//窗口title
			customColumnItems:true,				//是否自定义列表
			columnFields:columnFields,			//表单第一列
			save:save,							//保存按钮调用的方法
			saveAdd:saveAdd,					//保存并添加按钮调用的方法
			modify:configForm.modify,			//窗口判断是否显示保存增加按钮
			allButtonHidden:configForm.allButtonHidden,
			fileUpload : true,
			autoScroll:true,
			enctype : 'multipart/form-data'
		});	
		
	};
	
	/**
	 * 点击列表保存，弹出保存页面
	 */
	var saveForm = function(){
		funForm({
			title:'系统公告添加'
		});
	}
	/**
	 * 点击列表修改，弹出修改页面
	 */
	var modifyForm = function(){
		var modDelRecord = modifyDelSelRecord(moduleGridId);//请选择一条件记录
		if(modDelRecord != ''){
			var configFind = {
					url:'afficheAction!findAfficheByAfficheId.action',
					params:{modViewRecId:modDelRecord.afficheId},
					callBack:function(returnData){
						var configForm = {
							title:'系统公告修改',
							moduleData:returnData.model,
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
		var modDelRecord = modifyDelSelRecord(moduleGridId);//请选择一条件记录
		if(modDelRecord != ''){
			var configFind = {
					url:'afficheAction!findAfficheByAfficheId.action',
					params:{modViewRecId:modDelRecord.afficheId},
					callBack:function(returnData){
						var configForm = {
							title:'系统公告查看',
							moduleData:returnData.model,
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
		if (isSelRecord(moduleGridId)) {
			askMesg({
				msg:'您确认删除此记录吗?',
				fn:function(confirm){
					if(confirm == 'ok'){
						var configDel = {
							url : 'afficheAction!deleteAfficheByIds.action',
							params : {
								afficheIdList : getColumnRecordIds('afficheId', moduleGridId)
							},
							callBack : function(returnData) {
								var configCb = {
									msg : '系统公告删除成功!',
									fn : function(confirm) {
										if ('ok' == confirm) {
											refreshGridList(moduleGridId);
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

Ext.extend(com.bhtec.view.business.platform.affiche.AfficheVOp, com.bhtec.view.util.CommonWidgets, {});