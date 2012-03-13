/**
 * 编码操作
 * @author lianglp
 * @version 1.0
 * @class com.bhtec.view.business.platform.code.CodeVOp
 * @date 2010-12-01
 */
Ext.namespace('com.bhtec.view.business.platform.code');
com.bhtec.view.business.platform.code.CodeVOp = function(config){
	var moduleVOp = this;   //父类调用
	var moduleGridId = 'codeGridId';//form表单id
	
	/**
	 * 编码增加修改表单
	 */
	var funForm = function(configForm){
		/**
		 * 公用保存设置
		 */
		var saveCommon = function(configSave) {
				if(getExtCmpValueById('codeOldEngNameId') != getExtCmpValueById('codeEngName')){
					var syncUrl = 'codeAction!findSysplCodeByCodeEngName.action';
					var data = syncAjaxReqDecode(syncUrl,'codeEngName='+getExtCmpValueById('codeEngName'));
					if(data == '')return;
					if(data.existCode == true){
						var configExist = {
							msg:'对不起,英文编码名称已经存在!'
						}
						showSucMesg(configExist);
						moduleVOp.enableSaveButton();//添加保存enable
						return false;
					}
				}
				submitForm({
					url : configSave.url,
					params : {
						delimite:getExtCmpValueById('delimite'),
						partNum:getExtCmpValueById('partNum'),
						status:getExtCmpValueById('status'),
						part1:getExtCmpValueById('codeFormat1'),
						part1Con:getExtCmpValueById('codeFormatContentId1'),
						part2:getExtCmpValueById('codeFormat2'),
						part2Con:getExtCmpValueById('codeFormatContentId2'),
						part3:getExtCmpValueById('codeFormat3'),
						part3Con:getExtCmpValueById('codeFormatContentId3'),
						part4:getExtCmpValueById('codeFormat4'),
						part4Con:getExtCmpValueById('codeFormatContentId4')
					},
					callBack : function(returnData) {
						var configCb = {
							msg : '系统编码保存成功!',
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
			var url = 'codeAction!saveSysplCode.action';
			if(configForm.modify == true){
				url = 'codeAction!modifySysplCode.action';
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
				url:'codeAction!saveSysplCode.action',
				save:'saveAdd'
			}
			saveCommon(configSave);
		}
		/**
		 * 公用增加修改删除表单
		 */
		var moduleData = configForm.moduleData||'';
		
		//编码ID
		var codeId = new Ext.form.Hidden({
				name : "codeId",
				value:moduleData.codeId||'0'
		});
		//old english code
		var codeOldEngName = new Ext.form.Hidden({
				id : "codeOldEngNameId",
				value:moduleData.codeEngName||'0'
		})

		//英文编码名称
		var codeEngName = moduleVOp.textField({
				id : "codeEngName",
				name : "codeEngName",
				allowBlank:false,
				fieldLabel : config.codeEngName,
				value:moduleData.codeEngName||'',
				maxLength:20,
				vtype:'alphanum'
		});
		
		//中文编码名称
		var codeName = moduleVOp.textField({
				name : "codeName",
				allowBlank:false,
				fieldLabel : config.codeName,
				value:moduleData.codeName||'',
				maxLength:20
		});
		
		//module tree
		var treeWin = function(){
			var url = 'moduleAction!findNextLevelChildNodes.action';
			moduleVOp.treeWindow({
						title 	 : '编码树',
						items	 : moduleVOp.asyncTreePanel({
										rootText:'编码树',
										rootVisible:true,
										url:url,
										clickNode:function(node, e){
											getExtCmpById('moduleNameId').setValue(node.text);
										}
								})
					});
		};
		//module name
		var moduleName = moduleVOp.triggerField({
				id : 'moduleNameId',
				name : 'moduleName',
				fieldLabel : config.moduleName,
				value:moduleData.moduleName||'',
				allowBlank:false,
				window:treeWin
		});
		//set code effect
		var codeEffectSet = function(){
			var delimite = getExtCmpValueById('delimite')
			var codeEffect = getExtCmpById('codeEffectId');
			var partNum = getExtCmpValueById('partNum');
			var codeFormatContentId1 = formatDate(getExtCmpValueById('codeFormatContentId1'));
			var codeFormatContentId2 = formatDate(getExtCmpValueById('codeFormatContentId2'));
			var codeFormatContentId3 = formatDate(getExtCmpValueById('codeFormatContentId3'));
			var codeFormatContentId4 = formatDate(getExtCmpValueById('codeFormatContentId4'));
			var value;
			switch(partNum){
				case 2:
					value = codeFormatContentId1+delimite+codeFormatContentId2;
				break;
				case 3:
					value = codeFormatContentId1+delimite+
								codeFormatContentId2+delimite+
								codeFormatContentId3;
				break;
				case 4:
					value = codeFormatContentId1+delimite+
								codeFormatContentId2+delimite+
								codeFormatContentId3+delimite+
								codeFormatContentId4;
				break;
				default:
					value = codeFormatContentId1;
			}
			setCmpValueById('codeEffectId',value);
		}
		
		//delimite分隔符
		var delimite = moduleVOp.comboBox({
				id : "delimite",
				fieldLabel : config.delimite,
				store:new Ext.data.JsonStore({
						data 	: com.bhtec.util.Data.delimite,
						fields 	: com.bhtec.util.Data.delimiteFields
					}),
				valueField 	: 'delimite',
				displayField: 'delimiteName',
				value:moduleData.delimite||'-',
				listeners:{
					select:function(){
						codeEffectSet();
					}
				}
		});
		//编码效果
		var codeEffect = moduleVOp.textField({
				id : "codeEffectId",
				name:'codeEffect',
				readOnly:true,
				fieldLabel : config.codeEffect,
				value:moduleData.codeEffect||'',
				maxLength:50
		});
		//diable or enable code format combobox
		var disableCodeFormat = function(partNumArr){
			for(var i=1;i<5;i++){
				getExtCmpById('codeFormat'+i).setDisabled(true);
				getExtCmpById('codeFormatContentId'+i).setDisabled(true);
			}
			for(var j=0,l=partNumArr.length;j<l;j++){
				var index = partNumArr[j];
				getExtCmpById('codeFormat'+index).setDisabled(false);
				getExtCmpById('codeFormatContentId'+index).setDisabled(false);
			}
		}
		//段数
		var partNum = moduleVOp.comboBox({
				id : "partNum",
				fieldLabel : config.partNum,
				store:new Ext.data.JsonStore({
						data 	: com.bhtec.util.Data.partNum,
						fields 	: com.bhtec.util.Data.partNumFields
					}),
				valueField 	: 'partNum',
				displayField: 'partNumName',
				value:moduleData.partNum||'1',
				listeners:{
						select:function(combo,record,index){
							switch(record.data.partNum){
								case 1:
									disableCodeFormat([1]);
								break;
								case 2:
									disableCodeFormat([1,2]);
								break;
								case 3:
									disableCodeFormat([1,2,3]);
								break;
								case 4:
									disableCodeFormat([1,2,3,4]);
								break;
							}
							codeEffectSet();
						}
				}
		});
		//状态
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
		//page column
		var columnFields = new Array();
		columnFields.push({
			border : false,
			layout : "form",
			columnWidth : .5,
			items : [codeEngName,moduleName,status,partNum]
		});
		columnFields.push({
			border : false,
			layout : "form",
			columnWidth : .5,
			items : [codeName,delimite,memo,codeEffect]
		});
		//format code date and system parameters
		var formatDate = function(format){
			if(format == undefined)return '';
			if(format == 'yyyy'){
				d = new Date(); 
				return d.getFullYear();
			}else if(format == 'yyyyMM'){
				d = new Date();
				month = d.getMonth()+1;
				var monthStr = month<10?'0'+month:month+'';
				return d.getFullYear()+''+monthStr;
			}else if(format == 'yyyyMMdd'){
				d = new Date();
				month = d.getMonth()+1;
				var monthStr = month<10?'0'+month:month+'';
				return d.getFullYear()+''+monthStr +''+ d.getDate();
			}else if(format == 'userName'){
				return 'zhangsan';
			}else if(format == 'roleUser'){
				return '管理员~zhangsan';
			}else if(format == 'organRoleUser'){
				return '北京~管理员~zhangsan';
			}else{
				return format;
			}
		}
		
		var codeFormatComboxArr = new Array(); 
		var codeFormatFieldArr = new Array(); 
		var partNumber = moduleData.partNum||0;
		
		for(var i=1;i<5;i++){
			var disabled = true;
			if(partNumber == 0 && i==1){
				disabled = false;
			}else{
				if(i<=partNumber){
					disabled = false;
				}
			}
			
			//编码格式
			var codeFormatSelect = moduleVOp.comboBox({
					id : "codeFormat"+i,
					fieldLabel:i+config.part,
					store:new Ext.data.JsonStore({
							data 	: com.bhtec.util.Data.codeFormat,
							fields 	: com.bhtec.util.Data.codeFormatFields
						}),
					valueField 	: 'codeFormat',
					displayField: 'codeFormatName',
					value:'char',
					disabled:disabled,
					listeners:{
						select:function(combo,record,index){
							var codeFormatValue = record.data.codeFormat;
							var codeFormatId = combo.id;
							var codeFormatContentId = 'codeFormatContentId'+codeFormatId.substr(codeFormatId.length-1);
							var codeFormatContent = getExtCmpById(codeFormatContentId);
							if(codeFormatValue == 'date'){
								store = codeFormatContent.store;
								store.loadData(com.bhtec.util.Data.dateFormat,false);
								codeFormatContent.setEditable(false); 
								codeFormatContent.allowBlank = false;
							}else if(codeFormatValue == 'char'){
								store = codeFormatContent.store;
								store.loadData([],false);
								codeFormatContent.setEditable(true);
								codeFormatContent.allowBlank = false;
							}else if(codeFormatValue == 'number'){
								store = codeFormatContent.store;
								store.loadData(com.bhtec.util.Data.numberFormat,false);
								codeFormatContent.setEditable(false);
								codeFormatContent.allowBlank = false;
							}else if(codeFormatValue == 'sysPara'){
								store = codeFormatContent.store;
								store.loadData(com.bhtec.util.Data.sysParaFormat,false);
								codeFormatContent.setEditable(false);
								codeFormatContent.allowBlank = false;
							}
						}
					}
			});
			
			//中文编码名称
			var codeFormatContent = moduleVOp.comboBox({
					id:'codeFormatContentId'+i,
					fieldLabel : i+config.partCon,
					maxLength:20,
					store:new Ext.data.JsonStore({
						data : [],
						fields : ["codeFormatContentValue", "codeFormatContentName"]
					}),
					valueField 	: 'codeFormatContentValue',
					displayField: 'codeFormatContentName',
					value:'',
					disabled:disabled,
					editable : true,
					allowBlank:false,
					typeAhead:true,
					listeners:{
						select:function(combo){
							codeEffectSet();
						}
					}
					
			});
			codeFormatComboxArr.push(codeFormatSelect);
			codeFormatFieldArr.push(codeFormatContent);
		}
			
		var codeFormatField = moduleVOp.fieldSet({
			title:'编码格式设置',
			columnFields:[codeFormatComboxArr,codeFormatFieldArr]
		})
		columnFields.push({
				border : false,
				layout : "form",
				columnWidth : 1,
				items : codeFormatField
			});
		columnFields.push([codeId,codeOldEngName,creator,createDate]);
		
	    //调用父类方法进行窗口构造
		moduleVOp.cuvWindow({
			title:configForm.title,				//窗口title
			customColumnItems:true,				//是否自定义列表
			columnFields:columnFields,			//表单第一列
			save:save,							//保存按钮调用的方法
			saveAdd:saveAdd,					//保存并添加按钮调用的方法
			modify:configForm.modify,			//窗口判断是否显示保存增加按钮
			allButtonHidden:configForm.allButtonHidden,
			autoScroll:true
		});	
		
	};
	
	/**
	 * 点击列表保存，弹出保存页面
	 */
	var saveForm = function(){
		funForm({
			title:'系统编码添加'
		});
	}
	/**
	 * 点击列表修改，弹出修改页面
	 */
	var modifyForm = function(){
		var modDelRecord = modifyDelSelRecord(moduleGridId);//请选择一条件记录
		if(modDelRecord != ''){
			var configFind = {
					url:'codeAction!findSysplCodeByCodeId.action',
					params:{modViewRecId:modDelRecord.codeId},
					callBack:function(returnData){
						var configForm = {
							title:'系统编码修改',
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
					url:'codeAction!findSysplCodeByCodeId.action',
					params:{modViewRecId:modDelRecord.codeId},
					callBack:function(returnData){
						var configForm = {
							title:'系统编码查看',
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
	 * 启用停用操作
	 */
	var disableEnalbeOpt = function(flag,modDelRecord){
		var moduleGridPanel = getExtCmpById(moduleGridId);
		var selModDelRecord = moduleGridPanel.getSelectionModel().getSelected();
		var configdisEnable = {
				url:'codeAction!disEnableCode.action',
				noMask:true,
				params:{
					modViewRecId:modDelRecord.codeId,
					disEnableFlag:flag
				},
				callBack:function(returnData){
					var msg = '';
					if(returnData.disEnableBol){
						if(flag == 'enable'){
							msg = '编码启用成功!';
						}else{
							msg = '编码停用成功!';
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
				msg = '此编码已经启用!';
			}else{
				msg = '此编码已经停用!';
			}
			showSucMesg({
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

Ext.extend(com.bhtec.view.business.platform.code.CodeVOp, com.bhtec.view.util.CommonWidgets, {});