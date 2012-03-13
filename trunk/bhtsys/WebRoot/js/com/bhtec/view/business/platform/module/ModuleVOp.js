/**
 * 主框架页面操作
 * @author lianglp
 * @version 2.0
 * @class com.bhtec.bussiness.mainFrameMgr.ModuleVOp
 * @date 2010-07-12
 */
Ext.namespace('com.bhtec.view.business.platform.module');
com.bhtec.view.business.platform.module.ModuleVOp = function(config){
	var xmlDoc = config.xmlDoc;//xml文档标题
	var moduleVOp = this;   //父类调用
	var moduleGridId = 'moduleGridId';//form表单id
	
	/**
	 * 功能区增加修改表单
	 */
	var busiForm = function(configForm){
		var moduleData = configForm.moduleData||'';
		/**
		 * 公用保存设置
		 */
		var saveCommon = function(saveFlag) {
			if(getExtCmpValueById('oldModName') != getExtCmpValueById('modName')){
				var syncUrl = 'moduleAction!findModuleByModuleName.action';
				var data = syncAjaxReqDecode(syncUrl,'moduleName='+getExtCmpValueById('modName'));
				if(data == '')return;
				if(data.existModule == true){
					var configExist = {
						msg:'对不起，模块名称已经存在!'
					}
					showSucMesg(configExist);
					moduleVOp.enableSaveButton();//添加保存enable
					return false;
				}
			}
			var configSave = {
				url : saveFlag.url,
				params : {
					belongToSys : getExtCmpValueById('belongToSys'),
					modPageType:getExtCmpValueById('modPageType'),
					modLevel : getExtCmpValueById('modLevel'),
					status : getExtCmpValueById('status')
				},
				callBack : function(returnData) {
					var configCb = {
						msg : '模块菜单保存成功!',
						fn : function(confirm) {
							if ('ok' == confirm) {
								if (saveFlag.save == 'save') {
									refreshGridList(moduleGridId);
									getExtCmpById(moduleVOp.cuvWindowId).close();
								} else {
									moduleVOp.enableSaveButton();//添加保存enable
									resetForm(basicConstant.FORM_ID);
									refreshGridList(moduleGridId);
								}
								//刷新树
								var treePanel = getExtCmpById('treePanelModuleId');
								treePanel.root.reload();
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
			var url = 'moduleAction!saveModule.action';
			if(configForm.modify == true){
				url = 'moduleAction!modifyModule.action';
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
				url:'moduleAction!saveModule.action',
				save:'saveAdd'
			}
			saveCommon(configSave);
		}
		
		var oldModName = new Ext.form.Hidden({
				id : "oldModName",
				value:moduleData.modName||''
		})
		
		var upModIdValue;
		var upModNameValue;
		if(moduleData.upModId != undefined){
					upModIdValue = moduleData.upModId;
					upModNameValue = moduleData.upModName;
		}else{
			upModIdValue = getExtCmpValueById('modTreeId');
			upModNameValue = getExtCmpValueById('modTreeNodeNameId');
		}
		
		var upModId = new Ext.form.Hidden({
				id : "upModId",
				value:upModIdValue||''
		})
		
		/**
		 * 公用增加修改删除表单
		 */
		
		//模块名称
		var modName = moduleVOp.textField({
				id : "modName",
				name : "modName",
				maxLength  : 10,
				allowBlank:false,
				fieldLabel : config.modName,
				value:moduleData.modName||''
		});
		
		//模块英文ID
		var modEnId = moduleVOp.textField({
				name : "modEnId",
				value:moduleData.modEnId||'',
				maxLength  : 40,
				fieldLabel : config.modEnId,
				allowBlank:false,
				vtype:'alphanum'
		});
		
		//模块ID
		var moduleId = new Ext.form.Hidden({
				name : "moduleId",
				value:moduleData.moduleId||''
		});
		
		//模块图片样式
		var modImgCls = moduleVOp.textField({
				name : "modImgCls",
				value:moduleData.modImgCls||'',
				fieldLabel : config.modImgCls,
				vtype:'alphanum'
		});
		
		//模块级别
		var modLevel = moduleVOp.comboBox({
				id : "modLevel",
				fieldLabel : config.modLevel,
				store:new Ext.data.JsonStore({
						data 	: com.bhtec.util.Data.moduleLevel,
						fields 	: com.bhtec.util.Data.moduleLevelFields
					}),
				valueField 	: 'level',
				displayField: 'levelName',
				allowBlank:false,
				value:moduleData.modLevel||'1'
		});
		
		//模块顺序
		var modOrder = moduleVOp.comboBox({
				name : "modOrder",
				fieldLabel : config.modOrder,
				store:new Ext.data.JsonStore({
						data 	: com.bhtec.util.Data.number,
						fields 	: com.bhtec.util.Data.numberFields
					}),
				valueField 	: 'num',
				displayField: 'numName',
				allowBlank:false,
				value:moduleData.modOrder||1
		});
		
		//所属系统
		var belongToSys = moduleVOp.comboBox({
				id : "belongToSys",
				fieldLabel : config.belongToSys,
				store:new Ext.data.JsonStore({
						url:'typeDictionaryAction!findSmallTypeDicByBigTypeCode.action',
						baseParams :{
							bigTypeCode:'systemType'
						},
						root:'sysplDicSmallTypeListt',
						autoLoad : true,
						fields 	: ['smallTypeCode','smallTypeName'],
						listeners :{
					        load:function(){
					        	if(moduleData.belongToSys){
					          		getExtCmpById('belongToSys').setValue(moduleData.belongToSys);
					          	}else{
						        	getExtCmpById('belongToSys').setValue('platform');
					          	}
					        }
					     } 
					}),
				valueField 	: 'smallTypeCode',
				displayField: 'smallTypeName',
				allowBlank:false
		});
		
		//页面类型
		var modPageType = moduleVOp.comboBox({
				id : "modPageType",
				fieldLabel : config.modPageType,
				store:new Ext.data.JsonStore({
						url:'typeDictionaryAction!findSmallTypeDicByBigTypeCode.action',
						baseParams :{
							bigTypeCode:'modPageType'
						},
						root:'sysplDicSmallTypeListt',
						autoLoad : true,
						fields 	: ['smallTypeCode','smallTypeName'],
						listeners :{
					        load:function(){
					        	if(moduleData.modPageType){
					          		getExtCmpById('modPageType').setValue(moduleData.modPageType);
					          	}else{
						        	getExtCmpById('modPageType').setValue('js');
					          	}
					        }
					     } 
					}),
				valueField 	: 'smallTypeCode',
				displayField: 'smallTypeName',
				allowBlank:false
		});
		
		//模块状态
		var status = moduleVOp.comboBox({
				id : "status",
				fieldLabel : config.status,
				store:new Ext.data.JsonStore({
						data 	: com.bhtec.util.Data.status,
						fields 	: com.bhtec.util.Data.statusFields
					}),
				valueField 	: 'status',
				displayField: 'statusName',
				allowBlank:false,
				value:moduleData.status||'enable'
		});
		
		var treeWin = function(){
			var url = 'moduleAction!findNextLevelChildNodes.action';
			if(moduleData.moduleId){
				url = 'moduleAction!findNextLevelChildNodes.action?filterModuleId='+moduleData.moduleId;
			}
			moduleVOp.treeWindow({
						title 	 : '模块树',
						items	 : moduleVOp.asyncTreePanel({
										rootText:'模块树',
										rootVisible:true,
										url:url,
										clickNode:function(node, e){
											getExtCmpById('upModId').setValue(node.id);			 
											getExtCmpById('upModNameId').setValue(node.text);
										}
								})
					});
					
			};
		/**
		 * 模块的上级名称
		 */
		var upModule = moduleVOp.triggerField({
				id : 'upModNameId',
				name : 'upModNameId',
				fieldLabel : config.upModule,
				value:moduleData.upModName||'',
				allowBlank:false,
				window:treeWin,
				value:upModNameValue||''
		});
		
		
		//备注
		var memo = moduleVOp.textField({
				name : "memo",
				value:moduleData.memo||'',
				fieldLabel : config.memo,
				maxLength:50
		});
		
		//创建时间
		var creator = new Ext.form.Hidden({
				name : "creator",
				value:moduleData.creator||''
		});
		//创建人
		var createDate = new Ext.form.Hidden({
				name : "createDate",
				value:moduleData.createDate||''
		});
		
		//调用父类方法进行窗口构造
		moduleVOp.cuvWindow({
			title:configForm.title,				//窗口title
			columnFields:[modName,modEnId,modImgCls,
						  modLevel,modOrder,status,belongToSys,modPageType,upModule,
						  memo,moduleId,creator,createDate,upModId],	//表单第一列
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
			title:'模块菜单添加'
		}
		busiForm(configSave);
	}
	/**
	 * 点击列表修改，弹出修改页面
	 */
	var modifyForm = function(){
		var modDelRecord = modifyDelSelRecord(moduleGridId);//请选择一条件记录
		if(modDelRecord != ''){
				var configFind = {
						url:'moduleAction!findModuleByModuleId.action',
						params:{modViewRecId:modDelRecord.moduleId},
						callBack:function(returnData){
								var configForm = {
									title:'模块菜单修改',
									moduleData:returnData.model,
									modify:true
								}
								busiForm(configForm);
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
					url:'moduleAction!findModuleByModuleId.action',
					params:{modViewRecId:modDelRecord.moduleId},
					callBack:function(returnData){
						var configForm = {
							title:'模块菜单查看',
							moduleData:returnData.model,
							allButtonHidden:true
						}
						busiForm(configForm);
					}
			}
			ajaxRequest(configFind);
		}
	}
	
	/**
	 * 点击删除按钮调用方法
	 */
	var delRecord = function() {
		var recordData = modifyDelSelRecord(moduleGridId);
		if (recordData != '') {
			askMesg({
				msg:'您确认删除此记录吗?',
				fn:function(confirm){
					if(confirm == 'ok'){
						var configDel = {
							url : 'moduleAction!deleteModuleById.action',
							params : {
								modViewRecId : recordData.moduleId
							},
							callBack : function(returnData) {
								var configCb = {
									msg : '模块菜单删除成功!',
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
	/**
	 * 启用停用操作
	 */
	var disableEnalbeOpt = function(flag,modDelRecord){
		var moduleGridPanel = getExtCmpById(moduleGridId);
		var selModDelRecord = moduleGridPanel.getSelectionModel().getSelected();
		var configdisEnable = {
				url:'moduleAction!disEnableModule.action',
				noMask:true,
				params:{
					modViewRecId:modDelRecord.moduleId,
					disEnableFlag:flag
				},
				callBack:function(returnData){
					var msg = '';
					if(returnData.disEnableBol){
						if(flag == 'enable'){
							msg = '模块菜单启用成功!';
						}else{
							msg = '模块菜单停用成功!';
						}
						selModDelRecord.set('status', flag);
						moduleStore = moduleGridPanel.store;
						moduleStore.commitChanges();
					}else{
						msg = '模块菜单已有下级,您不能停用此模块!';
					}
					var configCb = {
						msg : msg,
						fn : function(confirm) {
							
						}
					}
					showSucMesg(configCb);
				}
		}
		ajaxRequest(configdisEnable);
	}
	/**
	 * 启用 停用
	 */
	var disEnable = function(flag){
		var modDelRecord = modifyDelSelRecord(moduleGridId);//请选择一条件记录
		if(modDelRecord == '')return;
		if(flag == modDelRecord.status){
			var msg='';
			if(flag == 'enable'){
				msg = '此模块已经启用!';
			}else{
				msg = '此模块已经停用!';
			}
			showSucMesg({
				msg:msg
			})
			return;
		}
		if(modDelRecord.modLevel != 3){
			warningMesg({
				msg:'对不起,您只能停用第三级菜单!'
			})
			return;
		}
		if(flag == 'disable'){
			askMesg({
				msg:'您确认停用此模块吗?如果停用,此模块下的操作将自动清除,并重登生效。',
				fn:function(confirm){
					if(confirm == 'ok'){
						disableEnalbeOpt(flag,modDelRecord);
					}
				}
			})
		}else{
			disableEnalbeOpt(flag,modDelRecord);
		}
	}
	//模块标签修改
	var moduleLabelOp = function(){
		var modDelRecord = modifyDelSelRecord(moduleGridId);//请选择一条件记录
		if(modDelRecord.modLevel != 3){
			warningMesg({
				msg:'对不起,您只能选择第三级菜单!'
			})
			return;
		}
		var moduleXmlDoc = basicConstant.DYNAMIC_LOAD_XMLDOC.get(modDelRecord.modEnId);
		var moduleXmlPath = './js/com/bhtec/view/business/'+(modDelRecord.belongToSys)
							   +'/modulexml/'+(modDelRecord.modEnId)+'.xml';
		if(moduleXmlDoc == undefined){
			moduleXmlDoc = loadXMLFile(moduleXmlPath);//加载模块xml
		}
		var nodes = moduleXmlDoc.getElementsByTagName("field");
		//模块标签列表
		var colsArray = new Array();//动态构造列
		var moduleLabelArray1 = new Array();//
		for(var i=0;i<nodes.length;i++){
			var fieldEnName = nodes[i].getAttribute('name');
			var fieldValue = '';
			if(Ext.isIE){
				fieldValue = nodes[i].nodeTypedValue;
			}else{
				fieldValue = nodes[i].textContent;
			}
			var modLabel = moduleVOp.textField({
					id : fieldEnName,
					maxLength  : 30,
					allowBlank:false,
					fieldLabel : '模块标签'+(i+1),
					value:fieldValue||''
			});	
			colsArray.push(modLabel);
			moduleLabelArray1.push(fieldEnName);
		}
		
		//调用父类方法进行窗口构造
		moduleVOp.cuvWindow({
			title:'模块标签维护',				//窗口title
			modify:true,
			columnFields:colsArray,			//表单第一列
			save:function(){
				var moduleLabelArray2 = new Array();
				for(i=0;i<moduleLabelArray1.length;i++){
					var moduleLabelArray3 = new Array();
					moduleLabelArray3.push(moduleLabelArray1[i]);
					moduleLabelArray3.push(getExtCmpValueById(moduleLabelArray1[i]));
					moduleLabelArray2.push(moduleLabelArray3);
				}
				ajaxRequest({
					url:'moduleAction!modifyModuleLabel.action',
					params:{
						xmlFile:moduleXmlPath,
						moduleLabelListStr:Ext.encode(moduleLabelArray2)//二维数组
					},
					callBack:function(returnData){
						var configCb = {
							msg : '模块标签修改成功!',
							fn : function(confirm) {
								getExtCmpById(moduleVOp.cuvWindowId).close();
							}
						}
						showSucMesg(configCb);
					}
				})
			}
		});	
		
	}
	return {
			saveForm:saveForm,
			delRecord:delRecord,
			modifyForm:modifyForm,
			viewForm:viewForm,
			disEnable:disEnable,
			moduleLabelOp:moduleLabelOp
	}
}

Ext.extend(com.bhtec.view.business.platform.module.ModuleVOp, com.bhtec.view.util.CommonWidgets, {});