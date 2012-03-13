/**
 *省市地区操作
 * @author lianglp
 * @version 1.0
 * @class com.bhtec.view.business.platform.district.DistrictVOp
 * @date 2010-12-28
 */
Ext.namespace('com.bhtec.view.business.platform.district');
com.bhtec.view.business.platform.district.DistrictVOp = function(config){
	var xmlDoc = config.xmlDoc;//xml文档标题
	var moduleVOp = this;   //父类调用
	var moduleGridId = 'districtGridId';//form表单id
	
	/**
	 * 功能区增加修改表单
	 */
	var busiForm = function(configForm){
		var moduleData = configForm.moduleData||'';
		/**
		 * 公用保存设置
		 */
		var saveCommon = function(saveFlag) {
			if(getExtCmpValueById('oldDistrictNameId') != getExtCmpValueById('districtName')){
				var syncUrl = 'districtAction!findDistrictNameIsExist.action';
				var data = syncAjaxReqDecode(syncUrl,'districtName='+getExtCmpValueById('districtName'));
				if(data == '')return;
				if(data.existDistrict == true){
					var configExist = {
						msg:'对不起，地区名称已经存在!'
					}
					showSucMesg(configExist);
					moduleVOp.enableSaveButton();//添加保存enable
					return false;
				}
			}
			var configSave = {
				url : saveFlag.url,
				params : {
					districtLevel : getExtCmpValueById('districtLevel'),
					status : getExtCmpValueById('status')
				},
				callBack : function(returnData) {
					var configCb = {
						msg : '地区保存成功!',
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
								var treePanel = getExtCmpById('treePanelDistrictId');
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
			var url = 'districtAction!saveSysplDistrict.action';
			if(configForm.modify == true){
				url = 'districtAction!modifySysplDistrict.action';
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
				url:'districtAction!saveSysplDistrict.action',
				save:'saveAdd'
			}
			saveCommon(configSave);
		}
		
		
		var oldDistrictName = new Ext.form.Hidden({
				id : "oldDistrictNameId",
				value:moduleData.districtName||''
		})
		var upDistrictIdValue;
		var upDistrictValue;
		if(moduleData.upModId != undefined){
			upDistrictIdValue = moduleData.upDistrictId;
			upDistrictValue = moduleData.upDistrict;
		}else{
			upDistrictIdValue = getExtCmpValueById('districtId');
			upDistrictValue = getExtCmpValueById('districtNameId');
		}
		var upDistrictId = new Ext.form.Hidden({
				id : "upDistrictId",
				value:upDistrictIdValue||''
		})
		
		/**
		 * 公用增加修改删除表单
		 */
		
		//地区名称
		var districtName = moduleVOp.textField({
				id : "districtName",
				name : "districtName",
				maxLength  : 10,
				allowBlank:false,
				fieldLabel : config.districtName,
				value:moduleData.districtName||''
		});
		
		//地区代码
		var districtCode = moduleVOp.textField({
				name : "districtCode",
				maxLength  : 10,
				value:moduleData.districtCode||'',
				fieldLabel : config.districtCode,
				vtype:'alphanum'
		});
		
		//地区ID
		var districtId = new Ext.form.Hidden({
				name : "districtId",
				value:moduleData.districtId||''
		});
		
		//地区邮编
		var districtPostal = moduleVOp.numberField({
				name : "districtPostal",
				maxLength  : 6,
				value:moduleData.districtPostal||'',
				fieldLabel : config.districtPostal,
				allowDecimals : false,
				emptyText:'请输入数字'
		});
		
		//电话区号
		var districtTelcode = moduleVOp.numberField({
				name : "districtTelcode",
				value:moduleData.districtTelcode||'',
				fieldLabel : config.districtTelcode,
				maxLength  : 10,
				allowDecimals : false,
				emptyText:'请输入数字'
		});
		
		//地区级别
		var districtLevel = moduleVOp.comboBox({
				id : "districtLevel",
				fieldLabel : config.districtLevel,
				store:new Ext.data.JsonStore({
						url:'typeDictionaryAction!findSmallTypeDicByBigTypeCode.action',
						baseParams :{
							bigTypeCode:'district'
						},
						root:'sysplDicSmallTypeListt',
						autoLoad : true,
						fields 	: ['smallTypeCode','smallTypeName'],
						listeners :{
					        load:function(){
					        	if(moduleData.districtLevel){
					          		getExtCmpById('districtLevel').setValue(moduleData.districtLevel);
					          	}else{
						        	getExtCmpById('districtLevel').setValue('province');
					          	}
					        }
					     } 
					}),
				valueField 	: 'smallTypeCode',
				displayField: 'smallTypeName',
				allowBlank:false
		});
		
		//地区状态
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
			var url = 'districtAction!findNextLevelChildNodes.action';
			if(moduleData.districtId){
				url = 'districtAction!findNextLevelChildNodes.action?filterModuleId='+moduleData.districtId;
			}
			moduleVOp.treeWindow({
						title 	 : '地区树',
						items	 : moduleVOp.asyncTreePanel({
										rootText:'地区树',
										rootVisible:true,
										url:url,
										clickNode:function(node, e){
											getExtCmpById('upDistrictId').setValue(node.id);			 
											getExtCmpById('upDistrictNameId').setValue(node.text);
										}
								})
					});
					
			};
		/**
		 * 地区的上级名称
		 */
		var upDistrict = moduleVOp.triggerField({
				id : 'upDistrictNameId',
				name : 'upDistrictName',
				fieldLabel : config.upDistrict,
				value:upDistrictValue||'',
				allowBlank:false,
				window:treeWin
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
			columnFields:[districtName,districtCode,districtPostal,
						  districtTelcode,districtLevel,status,memo,upDistrict,
						  districtId,creator,createDate,upDistrictId,oldDistrictName],	//表单第一列
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
			title:'省市地区添加'
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
						url:'districtAction!findSysplDistrictByDistrictId.action',
						params:{modViewRecId:modDelRecord.districtId},
						callBack:function(returnData){
								var configForm = {
									title:'省市地区修改',
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
					url:'districtAction!findSysplDistrictByDistrictId.action',
					params:{modViewRecId:modDelRecord.districtId},
					callBack:function(returnData){
						var configForm = {
							title:'省市地区查看',
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
				url:'districtAction!disEnableDistrict.action',
				noMask:true,
				params:{
					modViewRecId:modDelRecord.districtId,
					disEnableFlag:flag
				},
				callBack:function(returnData){
					var msg = '';
					if(returnData.disEnableBol){
						if(flag == 'enable'){
							msg = '省市地区启用成功!';
						}else{
							msg = '省市地区停用成功!';
						}
						selModDelRecord.set('status', flag);
						moduleStore = moduleGridPanel.store;
						moduleStore.commitChanges();
					}else{
						msg = '省市地区已有下级,您不能停用此地区!';
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
				msg = '此地区已经启用!';
			}else{
				msg = '此地区已经停用!';
			}
			showSucMesg({
				msg:msg
			})
			return;
		}
		
		if(flag == 'disable'){
			askMesg({
				msg:'您确认停用此地区吗?',
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
	
	return {
			saveForm:saveForm,
			modifyForm:modifyForm,
			viewForm:viewForm,
			disEnable:disEnable
	}
}

Ext.extend(com.bhtec.view.business.platform.district.DistrictVOp, com.bhtec.view.util.CommonWidgets, {});
