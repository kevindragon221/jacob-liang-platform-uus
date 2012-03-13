/**
 * 类别字典操作
 * @author lianglp
 * @version 2.0
 * @class com.bhtec.view.business.platform.typedictionary.TypeDictionaryVOp
 * @date 2010-12-21
 */
Ext.namespace('com.bhtec.view.business.platform.typedictionary');
com.bhtec.view.business.platform.typedictionary.TypeDictionaryVOp = function(config){
	var moduleVOp = this;   //父类调用
	var moduleGridId = 'typeDictionaryGridId';//form表单id
	
	/**
	 * 功能区增加修改表单
	 */
	var busiForm = function(configForm){
		var moduleData = configForm.moduleData||'';
		/**
		 * 公用保存设置
		 */
		var saveCommon = function(saveFlag) {
			getExtCmpById('smallTypeEditGridPanelId').stopEditing();// 停止编辑
			if(getExtCmpValueById('oldBigTypeCode') != getExtCmpValueById('bigTypeCode')){
				var syncUrl = 'typeDictionaryAction!findBigTypeCodeIsExist.action';
				var data = syncAjaxReqDecode(syncUrl,'bigTypeCode='+getExtCmpValueById('bigTypeCode'));
				if(data == '')return;
				if(data.bigTypeCodeIsExist == true){
					var configExist = {
						msg:'对不起，大类编码已经存在!'
					};
					warningMesg(configExist);
					moduleVOp.enableSaveButton();//添加保存enable
					return false;
				}
			}
			var smallTypeEditGridPanel = getExtCmpById('smallTypeEditGridPanelId');
			var smallTypeList = new Array();
			var smallTypeIsComplete = true;
			smallTypeEditGridPanel.store.each(function(item){
					if(item.data.smallTypeName == '' || 
					    item.data.smallTypeCode == ''){
					   smallTypeIsComplete = false;
					   return false;
					 }
					smallTypeList.push(item.data);
			});
			
			if(smallTypeIsComplete == false){
				warningMesg({
					    		msg:'对不起，字典小类添写不完整!'
					    	});	
			    moduleVOp.enableSaveButton();//添加保存enable
			}else{
				var configSave = {
					url : saveFlag.url,
					params : {
						status : getExtCmpValueById('status'),
						sysplDicSmallTypeList:Ext.encode(smallTypeList)
					},
					callBack : function(returnData) {
						var configCb = {
							msg : '类别字典保存成功!',
							fn : function(confirm) {
								if ('ok' == confirm) {
									if (saveFlag.save == 'save') {
										refreshGridList(moduleGridId);
										getExtCmpById('typeDicWinId').close();
									} else {
										moduleVOp.enableSaveButton();//添加保存enable
										resetForm(basicConstant.FORM_ID);
										//清空小类grid
										smallTypeEditGridPanel.store.each(function(item){
												smallTypeEditGridPanel.store.remove(item);
										});
										refreshGridList(moduleGridId);
									}
								}
							}
						}
						showSucMesg(configCb);
					}
				}
				submitForm(configSave);
			}
		}
		
		/**
		 * 点击保存按钮调用方法
		 */
		var save = function() {
			var url = 'typeDictionaryAction!saveSysplDicBigType.action';
			if(configForm.modify == true){
				url = 'typeDictionaryAction!modifySysplDicBigType.action';
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
				url:'typeDictionaryAction!saveSysplDicBigType.action',
				save:'saveAdd'
			}
			saveCommon(configSave);
		}
		
		//大类名称
		var oldBigTypeCode = new Ext.form.Hidden({
				id : "oldBigTypeCode",
				value:moduleData.bigTypeCode||''
		})
		
		//大类ID
		var bigTypeId = new Ext.form.Hidden({
				name : "bigTypeId",
				value:moduleData.bigTypeId||''
		});
		
		/**
		 * 公用增加修改删除表单
		 */
		
		//大类名称
		var bigTypeName = moduleVOp.textField({
				name : "bigTypeName",
				maxLength  : 20,
				allowBlank:false,
				fieldLabel : config.bigTypeName,
				value:moduleData.bigTypeName||''
		});
		
		//大类编码
		var bigTypeCode = moduleVOp.textField({
				id:"bigTypeCode",
				name : "bigTypeCode",
				value:moduleData.bigTypeCode||'',
				fieldLabel : config.bigTypeCode,
				maxLength  : 15,
				vtype:'alphanum',
				emptyText:'请输入字母、数字或下划线',
				allowBlank:false,
				style:(moduleData.bigTypeCode||'')==''?'':'background:#DFDFDF',
				readOnly:configForm.modify==undefined?false:true,
				listeners:{
					 render: function(c) {
					 	    if(moduleData.bigTypeCode == undefined)return;
	                            Ext.QuickTips.register({
		                            target: c.getEl(),
		                            text: '此项修改时不能编辑'
	                            });
                        }
				}	
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
		//类别字典大类
		var bigTypeDic = moduleVOp.fieldSet({
			title:'类别字典大类添加',				//窗口title
			columnFields:[bigTypeName,bigTypeCode,status,memo,
						  creator,createDate,bigTypeId,oldBigTypeCode]	//表单第一列
		});
		var moduleStore = new Ext.data.JsonStore({
				fields : ['smallTypeName', 'smallTypeCode'],
				data:moduleData.sysplDicSmallTypes||[]
		});
		//类别字典小类editor
		
		var number = 0;
		var smallTypeEditGridPanel  = moduleVOp.editorGridPanel({
				id:'smallTypeEditGridPanelId',
				gridStore:moduleStore,
				colums:[{   
							header : config.smallTypeName,
							dataIndex : 'smallTypeName',
							width : 150,
							sortable: true,
							editor : moduleVOp.textField({
									maxLength  : 20
							})
						},{
							header : config.smallTypeCode,
							dataIndex : 'smallTypeCode',
							width : 150,
							sortable: true,
							editor : moduleVOp.textField({
									maxLength  : 10,
									emptyText:'请输入字母、数字或下划线',
									vtype:'alphanum'
							})
					}],
			recordObj:function(){
						var SmallType = Ext.data.Record.create([{
							name : 'smallTypeName',
							type : 'string'
						}, {
							name : 'smallTypeCode',
							type : 'string'
						}]);
				
						var smallType = new SmallType({
									num : number++,
									smallTypeName : '',
									smallTypeCode : ''
						});
						return smallType;
					},
			displayTbar:true,
			autoHeight:true,
			width:720,
			border:true
		});
		
		//类别字典小类
		var smallTypeDic = moduleVOp.fieldSet({
			title:'类别字典小类添加',				//窗口title
			customColumnItems:true,
			layout:'form',
			columnFields:[smallTypeEditGridPanel]	//表单第一列
		});
		Ext.apply(configForm,{save:save,saveAdd:saveAdd});
		var paddingLeft = 'padding-left:20px;';
		if(!Ext.isIE)paddingLeft = 'padding-left:200px;';
		//类别字典panel
		var typePanel = moduleVOp.formPanel({
			id:basicConstant.FORM_ID,
			autoScroll:true,
			items:[bigTypeDic,smallTypeDic,
						{
							xtype	  : 'panel',
							layout 	  : 'column',
							border    : false,
							bodyStyle : paddingLeft,
							autoHeight:true,			
							items:[{
										border : false,
										layout : "form",
										columnWidth : 0.2
									},{
										border : false,
										layout : "form",
										columnWidth : 0.15,
										items : [moduleVOp.saveAddButton(configForm)]
									},{
										border : false,
										layout : "form",
										columnWidth : 0.15,
										items : [moduleVOp.saveButton(configForm)]
									},{
										border : false,
										layout : "form",
										columnWidth : 0.15,
										items : [moduleVOp.resetButton(configForm)]
									},{
										border : false,
										layout : "form",
										columnWidth : 0.15,
										items : [moduleVOp.closeButton({winId:moduleVOp.cuvWindowId})]
									},{
										border : false,
										layout : "form",
										columnWidth : 0.2
									}]
						}
			]
		});
		
		moduleVOp.window({
			id:'typeDicWinId',
			title:configForm.title,
			items:typePanel
		});
		//为表单域加回车键
		formEnterEvent();
	};
	
	/**
	 * 点击列表保存，弹出保存页面
	 */
	var saveForm = function(){
		var configSave = {
			title:'类别字典添加'
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
						url:'typeDictionaryAction!findSysplDicBigTypeById.action',
						params:{modViewRecId:modDelRecord.bigTypeId},
						callBack:function(returnData){
								var configForm = {
									title:'类别字典修改',
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
					url:'typeDictionaryAction!findSysplDicBigTypeById.action',
					params:{modViewRecId:modDelRecord.bigTypeId},
					callBack:function(returnData){
						var configForm = {
							title:'类别字典查看',
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
	 * 启用停用操作
	 */
	var disableEnalbeOpt = function(flag,modDelRecord){
		var moduleGridPanel = getExtCmpById(moduleGridId);
		var selModDelRecord = moduleGridPanel.getSelectionModel().getSelected();
		var configdisEnable = {
				url:'typeDictionaryAction!disEnableDicBigType.action',
				noMask:true,
				params:{
					modViewRecId:modDelRecord.bigTypeId,
					disEnableFlag:flag
				},
				callBack:function(returnData){
					var msg = '';
					if(returnData.disEnableBol){
						if(flag == 'enable'){
							msg = '类别字典启用成功!';
						}else{
							msg = '类别字典停用成功!';
						}
						selModDelRecord.set('status', flag);
						moduleStore = moduleGridPanel.store;
						moduleStore.commitChanges();
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
				msg = '此类别字典已经启用!';
			}else{
				msg = '此类别字典已经停用!';
			}
			warningMesg({
				msg:msg
			})
			return;
		}
		
		disableEnalbeOpt(flag,modDelRecord);
	}
	
	return {
			saveForm:saveForm,
			modifyForm:modifyForm,
			viewForm:viewForm,
			disEnable:disEnable
	}
}

Ext.extend(com.bhtec.view.business.platform.typedictionary.TypeDictionaryVOp,
	com.bhtec.view.util.CommonWidgets, {});