/**
 * 机构管理操作
 * @author lianglp
 * @version 2.0
 * @class com.bhtec.view.business.uum.organ.OrganVOp
 * @date 2010-09-23
 */
Ext.namespace('com.bhtec.view.business.uum.organ');
com.bhtec.view.business.uum.organ.OrganVOp = function(config){
	var moduleVOp = this;   //父类调用
	var moduleGridId = 'organGridId';//form表单id
	
	/**
	 * 功能区增加修改表单
	 */
	var busiForm = function(configForm){
		var moduleData = configForm.moduleData||'';
		/**
		 * 公用保存设置
		 */
		var saveCommon = function(saveFlag) {
			if(getExtCmpValueById('oldOrgName') != getExtCmpValueById('orgSimpleName')){
				var syncUrl = 'organAction!findOrganByOrganName.action';
				var data = syncAjaxReqDecode(syncUrl,'organName='+getExtCmpValueById('orgSimpleName'));
				if(data == '')return;
				if(data.existOrg == true){
					var configExist = {
						msg:'对不起，机构名称已经存在!'
					};
					showSucMesg(configExist);
					moduleVOp.enableSaveButton();//添加保存enable
					return false;
				}
			}
			
			var configSave = {
				url : saveFlag.url,
				params : {
					orgType : getExtCmpValueById('orgType'),
					status : getExtCmpValueById('status')
				},
				callBack : function(returnData) {
					var configCb = {
						msg : '机构保存成功!',
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
								var treePanel = getExtCmpById('treePanelOrganId');
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
			var url = 'organAction!saveOrgan.action';
			if(configForm.modify == true){
				url = 'organAction!modifyOrgan.action';
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
				url:'organAction!saveOrgan.action',
				save:'saveAdd'
			}
			saveCommon(configSave);
		}
		
		
		var oldOrgName = new Ext.form.Hidden({
				id : "oldOrgName",
				value:moduleData.orgSimpleName||''
		})
		
		/**
		 * 公用增加修改删除表单
		 */
		
		//机构名称
		var orgSimpleName = moduleVOp.textField({
				id : "orgSimpleName",
				name : "orgSimpleName",
				allowBlank:false,
				maxLength  : 10,
				fieldLabel : config.orgSimpleName,
				value:moduleData.orgSimpleName||''
		});
		
		//机构全称
		var orgFullName = moduleVOp.textField({
				name : "orgFullName",
				value:moduleData.orgFullName||'',
				maxLength  : 60,
				fieldLabel : config.orgFullName			
		});
		
		//机构ID
		var orgId = new Ext.form.Hidden({
				id : "orgId",
				value:moduleData.orgId||''
		});
		var upOrgNameValue;
		var upOrgIdValue;
		if(moduleData.uporgId != undefined){
					upOrgIdValue = moduleData.uporgId;
					upOrgNameValue = moduleData.upOrgName;
		}else{
			upOrgIdValue = getExtCmpValueById('organTreeId');
			upOrgNameValue = getExtCmpValueById('organTreeNodeNameId');
		}
		//上级机构ID
		var upOrgId = new Ext.form.Hidden({
				id : "uporgId",
				value:upOrgIdValue||''
		});
		
		//机构代码
		var orgCode = moduleVOp.textField({
				name : "orgCode",
				value:moduleData.orgCode||'',
				maxLength  : 10,
				fieldLabel : config.orgCode,
				vtype:'alphanum',
				emptyText:'请输入字母、数字、下划线'
		});
		
		//联系地址1
		var orgAddress1 = moduleVOp.textField({
				name : "orgAddress1",
				value:moduleData.orgAddress1||'',
				maxLength  : 100,
				fieldLabel : config.orgAddress1			
		});
		//联系地址2
		var orgAddress2 = moduleVOp.textField({
				name : "orgAddress2",
				value:moduleData.orgAddress2||'',
				maxLength  : 100,
				fieldLabel : config.orgAddress2		
		});
		//联系电话1
		var orgTel1 = moduleVOp.numberField({
				name : "orgTel1",
				value:moduleData.orgTel1||'',
				maxLength  : 15,
				fieldLabel : config.orgTel1,
				emptyText:'请输入数字',
				allowDecimals : false
		});
		//联系电话2
		var orgTel2 = moduleVOp.numberField({
				name : "orgTel2",
				value:moduleData.orgTel2||'',
				maxLength  : 15,
				fieldLabel : config.orgTel2,
				allowDecimals : false,
				emptyText:'请输入数字'
		});
		//成立日期
		var orgBeginDate = moduleVOp.dateField({
				name : "orgBeginDate",
				value:moduleData.orgBeginDate||'',
				format:'Y-m-d',
				fieldLabel : config.orgBeginDate		
		});
		
		//机构类型
		var orgType = moduleVOp.comboBox({
				id : "orgType",
				fieldLabel : config.orgType,
				store:new Ext.data.JsonStore({
						url:'typeDictionaryAction!findSmallTypeDicByBigTypeCode.action',
						baseParams :{
							bigTypeCode:'organType'
						},
						root:'sysplDicSmallTypeListt',
						autoLoad : true,
						fields 	: ['smallTypeCode','smallTypeName'],
						listeners :{
					        load:function(){
					        	if(moduleData.orgType){
					          		getExtCmpById('orgType').setValue(moduleData.orgType);
					          	}else{
						        	getExtCmpById('orgType').setValue('branch');
					          	}
					        }
					     }
					}),
				valueField 	: 'smallTypeCode',
				displayField: 'smallTypeName',
				allowBlank:false
		});
		
		//机构邮编
		var orgPostal = moduleVOp.numberField({
				name : "orgPostal",
				fieldLabel : config.orgPostal,
				value:moduleData.orgPostal||'',
				allowDecimals : false,
				maxLength  : 6,
				emptyText:'请输入数字'
		});
		
		//法人代表
		var orgLegal = moduleVOp.textField({
				name : "orgLegal",
				fieldLabel : config.orgLegal,
				value:moduleData.orgLegal||'',
				maxLength  : 10
		});
		
		//税务号
		var orgTaxNo = moduleVOp.textField({
				name : "orgTaxNo",
				fieldLabel : config.orgTaxNo,
				maxLength:25,
				vtype:'alphanum',
				value:moduleData.orgTaxNo||'',
				emptyText:'请输入字母、数字、下划线'
		});
		
		//注册登记号
		var orgRegNo = moduleVOp.textField({
				name : "orgRegNo",
				maxLength:25,
				vtype:'alphanum',
				fieldLabel : config.orgRegNo,
				value:moduleData.orgRegNo||'',
				emptyText:'请输入字母、数字、下划线'
		});
		
		var treeWin = function(){
			var url = 'districtAction!findNextLevelChildNodes.action';
			moduleVOp.treeWindow({
						title:'地区树',
						items:moduleVOp.asyncTreePanel({
								rootText:'地区树',
								rootVisible:true,
								url:url,
								clickNode:function(node, e){
									getExtCmpById('orgBelongDist').setValue(node.id);			 
									getExtCmpById('orgBelongDistName').setValue(node.text);
								}
						})
					});
					
			};
		
		var orgBelongDistId = new Ext.form.Hidden({
				id:'orgBelongDist',
				value:moduleData.orgBelongDist||0
		})
		
		//所属地区
		var orgBelongDistName = moduleVOp.triggerField({
				id : 'orgBelongDistName',
				name : 'orgBelongDistName',
				fieldLabel : config.orgBelongDist,
				value:moduleData.orgBelongDistName||'',
				allowBlank:false,
				window:treeWin
		});
		
		//机构状态
		var status = moduleVOp.comboBox({
				id : "status",
				fieldLabel : config.status,
				store:new Ext.data.JsonStore({
						data 	: com.bhtec.util.Data.status,
						fields 	: com.bhtec.util.Data.statusFields
					}),
				valueField 	: 'status',
				displayField: 'statusName',
				value:moduleData.status||'enable',
				allowBlank:false
		});
		//机构树
		var organTreeUrl = 'organAction!findNextLevelChildNodes.action';
		if(moduleData.orgId){//机构修改过滤掉本机构
			organTreeUrl = 'organAction!findNextLevelChildNodes.action?filterOrgId='+moduleData.orgId;
		}
		var treeWin = function(){
			moduleVOp.treeWindow({
						title 	 : '机构树',
						items	 : moduleVOp.asyncTreePanel({
										rootText:basicConstant.ORGAN_ROOT,
										rootVisible:true,
										url:organTreeUrl,
										clickNode:function(node, e){
											getExtCmpById('uporgId').setValue(node.id);			 
											getExtCmpById('upOrgName').setValue(node.text);
										}
								})
					});
			};
		//上级机构
		var upOrgName = moduleVOp.triggerField({
				id : 'upOrgName',
				name : 'upOrgName',
				fieldLabel : config.upOrgId,
				value:upOrgNameValue||'',
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
			columnFields:[orgSimpleName,orgFullName,orgCode,orgLegal,
						  orgAddress1,orgAddress2,orgTel1,orgTel2,
						  orgBeginDate,orgType,orgPostal,orgTaxNo,
						  orgRegNo,orgBelongDistName,status,upOrgName,memo,
						  upOrgId,orgId,creator,createDate,orgBelongDistId],	//表单第一列
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
			title:'机构添加'
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
						url:'organAction!findOrganByOrganId.action',
						params:{modViewRecId:modDelRecord.orgId},
						callBack:function(returnData){
								var configForm = {
									title:'机构修改',
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
					url:'organAction!findOrganByOrganId.action',
					params:{modViewRecId:modDelRecord.orgId},
					callBack:function(returnData){
						var configForm = {
							title:'机构查看',
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
			var configDel = {
				url : 'organAction!deleteModuleById.action',
				params : {
					modViewRecId : recordData.moduleId
				},
				callBack : function(returnData) {
					var configCb = {
						msg : '机构菜单删除成功!',
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
	
	/**
	 * 启用 停用
	 */
	var disEnable = function(flag){
		var modDelRecord = modifyDelSelRecord(moduleGridId);//请选择一条件记录
		if(modDelRecord == '')return;
		var moduleGridPanel = getExtCmpById(moduleGridId);
		var selModDelRecord = moduleGridPanel.getSelectionModel().getSelected();
		if(flag == selModDelRecord.data.status){
			var msg='';
			if(flag == 'enable'){
				msg = '此机构已经启用!';
			}else{
				msg = '此机构已经停用!';
			}
			showSucMesg({
				msg:msg
			})
			return;
		}
		var configdisEnable = {
				url:'organAction!disEnableOrgan.action',
				noMask:true,
				params:{
					modViewRecId:modDelRecord.orgId,
					disEnableFlag:flag
				},
				callBack:function(returnData){
					var disEnableResultFlag = returnData.disEnableResultFlag;
					if(disEnableResultFlag == 1){
						warningMesg({
							msg:'机构已有下级,您不能停用此机构!'
						})
						return;
					}else if(disEnableResultFlag == 2){
						warningMesg({
							msg:'机构已分配角色,您不能停用此机构!如停用,请先将角色删除。'
						})
						return;
					}else if(disEnableResultFlag == 0){
						var msg = '';
						if(flag == 'enable'){
							msg = '机构启用成功!';
						}else{
							msg = '机构停用成功!';
						}
						selModDelRecord.set('status', flag);
						moduleStore = moduleGridPanel.store;
						moduleStore.commitChanges();
						showSucMesg({
							msg:msg
						});
					}
					
				}
		}
		ajaxRequest(configdisEnable);
	}
	
	return {
			saveForm:saveForm,
			delRecord:delRecord,
			modifyForm:modifyForm,
			viewForm:viewForm,
			disEnable:disEnable
	}
}

Ext.extend(com.bhtec.view.business.uum.organ.OrganVOp, com.bhtec.view.util.CommonWidgets, {});