/**
 * 系统配置
 * @author lianglp
 * @version 1.0
 * @class com.bhtec.view.business.platform.systemconfig.SystemConfigVOp
 * @date 2010-11-19
 */
Ext.namespace('com.bhtec.view.business.platform.systemconfig');
com.bhtec.view.business.platform.systemconfig.SystemConfigVOp = function(config){
	var xmlDoc = config.xmlDoc;//xml文档标题
	var moduleVOp = this;   //父类调用
	
	/**
	 * 功能区增加修改表单
	 */
	var busiForm = function(configForm){
		var syncUrl = 'systemConfigAction!obtainSystemConfigInfo.action';
		var returnData = syncAjaxReqDecode(syncUrl,'');
		var moduleData = returnData.model;
		
		/**
		 * 系统名称
		 */
		var systemName = function(){ 
				var systemNameFieldId = Math.random();
				var systemNameFieldSet = moduleVOp.fieldSet({
					title:getFormFieldName(xmlDoc,'systemNameSet'),
					customColumnItems:true,
					columnFields:[{
							layout:'form',
							border:false,
							columnWidth:0.6,
							items:[moduleVOp.textField({
								id:systemNameFieldId,
								fieldLabel:'系统名称',
								value:moduleData.systemName||''
							})]
						},{
							layout:'form',
							border:false,
							columnWidth:0.4,
							items:[moduleVOp.saveButton({
									id:Math.random(),
									handler:function(){
										ajaxRequest({
											url : 'systemConfigAction!setSystemConfigInfo.action',
											params:{
												systemName:getExtCmpValueById(systemNameFieldId)
											},
											callBack : function(returnData) {
												showSucMesg({
													msg:'系统名称设置成功!'
												});
												//设置hader的系统名称
												var headerifr = document.frames("headerifr");
												headerifr.document.getElementById('sysTitle').innerHTML 
													= "<center>"+getExtCmpValueById(systemNameFieldId)+"</center>"
											}
										});
									}
								})]
						}]
				});
				return systemNameFieldSet;
		}
		/**
		 * 用户选择窗口
		 */
		var userSelectedWin = function(userAddDelFlag){
				//模块列模式
				var cols = function(){
					var colsArr = new Array();
					colsArr.push({
							header : '用户号',
							dataIndex : 'userCode',
							width : basicConstant.GRID_COL_WIDTH,
							sortable: true 
						});
					colsArr.push({
							header : '用户名',
							dataIndex : 'userName',
							width : basicConstant.GRID_COL_WIDTH,
							sortable: true 
						});
					colsArr.push({
							header : '性别',
							dataIndex : 'userGender',
							width : 40,
							sortable: true,
							renderer:function(data){
								var gender = com.bhtec.util.Data.gender;
								for(i=0;i<gender.length;i++){
									if(data == gender[i].genderId){
										return gender[i].genderDes;
									}
								}
							}
						});
					colsArr.push({
							header : '职位',
							dataIndex : 'userPosition',
							width : basicConstant.GRID_COL_WIDTH,
							renderer:function(value){
								if(value == null)return;
								return '<span qtip="'+value+'">'+value+'</span>';
							},
							sortable: true 
						});
					colsArr.push({
							header : '所属角色',
							dataIndex : 'uumRoleName',
							renderer:function(value){
								return '<span qtip="'+value+'">'+value+'</span>';
							},
							width : 60,
							sortable: true 
						});
					colsArr.push({
							header : '所属机构',
							dataIndex : 'uumOrgName',
							renderer:function(value){
								return '<span qtip="'+value+'">'+value+'</span>';
							},
							width : 60,
							sortable: true 
						});
					return colsArr;
				}
				
				var userSelectGridId = Math.random();
				var listStore = '';
				 /**
				  * 模块grid store
				  */
				if(userAddDelFlag == 'add'){
				    listStore = new Ext.data.JsonStore({
								fields : ['userCode', 'userName','userGender','userPosition','uumRoleName','uumOrgName'],
								autoLoad : true,
								totalProperty : 'count',
								root : 'userList',
								url : 'userAction!findUserForAdminSet.action'
							});
				}else{
					listStore = new Ext.data.JsonStore({
								fields : ['userCode', 'userName','userGender','userPosition','uumRoleName','uumOrgName'],
								autoLoad : true,
								totalProperty : 'systemAdminCount',
								root : 'systemAdminCodeList',
								url : 'userAction!findUserForAdminSet.action'
							});
				}
				
				/**
				 * 为翻页加自定义参数
				 */
			    listStore.on('beforeload', function(thiz,options) {
			    	var new_params = {
									userCode : getExtCmpValueById('userSelUserCodeId'),
									userName : getExtCmpValueById('userSelUserNameId')
								}; 
					Ext.apply(options.params,new_params); 
				});
				/**
				 * 用户查询panel
				 */
				var userQueryPanel = moduleVOp.panel({
					autoWidth:true,
					autoHeight:true,
					items:[{
							border : false,
							layout : 'form',
							columnWidth : 0.4,
							items:moduleVOp.textField({
							   		id:'userSelUserCodeId',
							   		width:80,
							   		fieldLabel:'用户号',
									listeners: {
						                specialkey: function(field, e){
						                    if (e.getKey() == e.ENTER) {
						                       userQuery();
						                    }
						                }
						            }
						   		  })
						   },{
							border : false,
							layout : 'form',
							columnWidth : 0.4,
							items:moduleVOp.textField({
							   		id:'userSelUserNameId',
							   		width:80,
							   		fieldLabel:'用户名',
									listeners: {
						                specialkey: function(field, e){
						                    if (e.getKey() == e.ENTER) {
						                       userQuery();
						                    }
						                }
						            }
						   		  })
						   },{
							border : false,
							layout : 'form',
							columnWidth : 0.2,
							items:{
								xtype:'button',
								text:'查询',
								iconCls:'table_find',
								handler:function(){
									userQuery();
								}
							}
						   }]
				});
				//用户查询
				var userQuery = function(){
						ajaxRequest({
								url : 'userAction!findUserForAdminSet.action',
								params : {
									userCode : getExtCmpValueById('userSelUserCodeId'),
									userName : getExtCmpValueById('userSelUserNameId')
								},
								callBack : function(returnData) {
									queryFillGridList(userSelectGridId,returnData);
								}
						});
				}
				/**
				 * 管理员增加删除
				 */
				var userSelectId = Math.random();
				var userSelectPanel = moduleVOp.formPanel({
						defaults:{
							columnWidth:1,
							border:false,
							labelWidth:50
						},
						items:[{
								items:[{
									xtype:'label',
									height:20,
									style:'font-size:12px',
									cls:'currPosition',
									text:'当前位置:系统管理->系统配置管理->系统管理员设置->用户'+(userAddDelFlag=='add'?'增加':'删除')
							   }]
							},{
								items:userQueryPanel	
							},{
								items:moduleVOp.gridPanel({
									id:userSelectGridId,
									title	:	'用户选择',
									colums  :   cols(),
									border  :   true,
									gridStore:  listStore,
									columnLines : true,
									buttons:[{
												xtype : 'button',
												iconCls : 'table_save',
												text : userAddDelFlag=='add'?'保存':'删除',
												handler : function(){
													//获得多所ID
													var ids = getMultiSelectedRedIds('userCode',userSelectGridId);
													var idArr = ids.split(',');
													var idList = new Array();
													var adminList = new Array();
													if(idArr.length != 0){
														for(i=0;i<idArr.length;i++){
															if(idArr[i] != ''){
																idList.push(idArr[i]);
																var r = new Ext.data.Record();
																r.set("userCode", idArr[i]);
																r.set("userCodeDis", idArr[i]);
																adminList.push(r);
															}
														}
														ajaxRequest({
															url : 'systemConfigAction!setSystemConfigInfo.action',
															params:{
																systemAdminCode:idList,
																userAddDelFlag:userAddDelFlag//增加删除标志
															},
															callBack : function(returnData) {
																showSucMesg({
																	msg:'系统管理员设置成功!'
																});
															}
														});
														var adminListStore = getExtCmpById('adminListComboId').store;
														if(userAddDelFlag=='add'){
															adminListStore.add(adminList);
														}else{
															for(i=0;i<adminList.length;i++){
																var userCode = adminList[i].data.userCode;
																adminListStore = getExtCmpById('adminListComboId').store;
																for(j=0;j<adminListStore.getCount();j++){
																	if(userCode == adminListStore.getAt(j).data.userCode){
																		adminListStore.removeAt(j);
																	}
																}
															}
														}
														//删除项目后清空
														getExtCmpById('adminListComboId').setValue('');
													}
													this.ownerCt.ownerCt.ownerCt.ownerCt.ownerCt.close();
												}
											},moduleVOp.closeButton({
												handler:function(){
													this.ownerCt.ownerCt.ownerCt.ownerCt.ownerCt.close();
												}
											})]
								 })	
							}
						]
				});
				
				moduleVOp.window({
						 title:'用户选择',
						 height:basicConstant.BODY_HEIGHT-190,
						 width:basicConstant.BODY_WIDTH-650,
						 layout:'fit',
						 items:userSelectPanel
				})
		}
		/**
		 * 管理员列表
		 */
		var adminList = function(){ 
			var systemAdminCodeList = moduleData.systemAdminCode;
			var adminListData = new Array();
			var adminListDataDefault = '';
			if(systemAdminCodeList != null){
				for(i=0;i<systemAdminCodeList.length;i++){
					if(i == 0)adminListDataDefault = systemAdminCodeList[i];
					adminListData.push({
						userCode:systemAdminCodeList[i],
						userCodeDis:systemAdminCodeList[i]
					})
				}
			}
			
			var adminField = moduleVOp.fieldSet({
				title:getFormFieldName(xmlDoc,'systemAdminSet'),
				customColumnItems:true,
				columnFields:[{
							layout:'form',
							border:false,
							columnWidth:0.25,
							items:[moduleVOp.comboBox({
								id:'adminListComboId',
								width:80,
								fieldLabel : '管理员列表',
								store:new Ext.data.JsonStore({
										data 	: adminListData,
										fields 	: ['userCode','userCodeDis']
									}),
								valueField 	: 'userCode',
								displayField: 'userCodeDis',
								value:adminListDataDefault
							})]
						},{
							layout:'form',
							border:false,
							columnWidth:0.25,
							items:[moduleVOp.triggerField({
									width:80,
									maxLength:100,
									value:'',
									fieldLabel : '增加管理员',
									window:function(){userSelectedWin('add')}
							})]
						},{
							layout:'form',
							border:false,
							columnWidth:0.25,
							items:[moduleVOp.triggerField({
									width:80,
									maxLength:100,
									value:'',
									fieldLabel : '删除管理员',
									window:function(){userSelectedWin('delete')}
							})]
						}
						]
			});
			return adminField;
		}
		
		/**
		 * 角色切换方式
		 */
		var roleChange = moduleVOp.fieldSet({
			title:getFormFieldName(xmlDoc,'roleChagneSet'),
			customColumnItems:true,
			columnFields:[{
					layout:'form',
					border:false,
					columnWidth:0.6,
					items:[moduleVOp.radio({
						fieldLabel : '角色切换',
						id:'roleChangeRadioId',
						items:[
		                	{boxLabel: '进入系统后切换角色', name: 'roleChange', inputValue: 'insystem', 
		                	 checked: (moduleData.roleChange=='insystem'?true:false)},
							{boxLabel: '登录时切换角色', name: 'roleChange', inputValue: 'login',
							 checked: (moduleData.roleChange=='login'?true:false)}
		                ]
					})]
				},{
					layout:'form',
					border:false,
					columnWidth:0.4,
					items:[moduleVOp.saveButton({
							id:'roleChangeId',
							handler:function(){
								ajaxRequest({
									url : 'systemConfigAction!setSystemConfigInfo.action',
									params:{
										roleChange:getExtCmpValueById('roleChangeRadioId').inputValue
									},
									callBack : function(returnData) {
										showSucMesg({
											msg:'角色切换设置成功!'
										});
									}
								});
							}
						})]
				}]
		})
		
		/**
		 * 日志级别设定
		 */
		var logLevel = moduleVOp.fieldSet({
			title:getFormFieldName(xmlDoc,'logLevelSet'),
			customColumnItems:true,
			columnFields:[{
					layout:'form',
					border:false,
					columnWidth:0.6,
					items:[moduleVOp.radio({
							fieldLabel : '日志级别',
							id:'logLevelRadioId',
							items:[
								{boxLabel: '增、删、改', name: 'logLevel', inputValue: 1,
								 checked: (moduleData.logLevel=='1'?true:false)},
								{boxLabel: '删、改、其它', name: 'logLevel', inputValue: 2,
								 checked: (moduleData.logLevel=='2'?true:false)},
								{boxLabel: '删、其它', name: 'logLevel', inputValue: 3,
								 checked: (moduleData.logLevel=='3'?true:false)},
								{boxLabel: '关闭', name: 'logLevel', inputValue: 0, 
								 checked: (moduleData.logLevel=='0'?true:false)}
			                ]
						})]
				},{
					layout:'form',
					border:false,
					columnWidth:0.4,
					items:[moduleVOp.saveButton({
							id:'logLevelId',
							handler:function(){
								ajaxRequest({
									url : 'systemConfigAction!setSystemConfigInfo.action',
									params:{
										logLevel:getExtCmpValueById('logLevelRadioId').inputValue
									},
									callBack : function(returnData) {
										showSucMesg({
											msg:'日志级别设置成功!'
										});
									}
								});
							}
						})]
				}]
		})
		
		/**
		 * 日志保留天数
		 */
		var logKeepDays = moduleVOp.fieldSet({
			title:getFormFieldName(xmlDoc,'logKeepDays'),
			customColumnItems:true,
			columnFields:[{
					layout:'form',
					border:false,
					columnWidth:0.6,
					items:[moduleVOp.comboBox({
							id : "logKeepDaysId",
							width:100,
							fieldLabel:'保留天数',
							emptyText :'保留天数选择',
							store:new Ext.data.JsonStore({
									data 	: com.bhtec.util.Data.logSaveTime,
									fields 	: com.bhtec.util.Data.logSaveTimeFields
								}),
							valueField 	: 'days',
							displayField: 'daysName',
							value:moduleData.logKeepDays||'-1'
					})]
				},{
					layout:'form',
					border:false,
					columnWidth:0.4,
					items:[moduleVOp.saveButton({
							handler:function(){
								ajaxRequest({
									url : 'systemConfigAction!setSystemConfigInfo.action',
									params:{
										logKeepDays:getExtCmpValueById('logKeepDaysId')
									},
									callBack : function(returnData) {
										showSucMesg({
											msg:'日志保留天数设置成功!'
										});
									}
								});
							}
						})]
				}]
		})
		
		/**
		 * 当前位置
		 */
		var currentPosition = {
			xtype:'label',
			height:20,
			style:'font-size:12px',
			cls:'currPosition',
			text:' 当前位置:系统管理->系统配置管理'
		}
		
		var systemConfigPanel = moduleVOp.panel({
						defaults:{
							columnWidth:1
						},
						autoHeight:true,
						items:[currentPosition,{border:false},
								systemName(),{border:false},
								roleChange,{border:false},
								logLevel,{border:false},
								logKeepDays,{border:false},
								adminList()]
		})
			
		/**
		 * 系统设置表单
		 */
		var systemConfigFormPanel = moduleVOp.formPanel({
				layout:'fit',
				bodyStyle : 'padding:10 40 40 40',
				items:systemConfigPanel,
				buttonAlign:'center',
				labelWidth:70,
				width:basicConstant.BODY_WIDTH-230,
				height:basicConstant.BODY_HEIGHT-182,
				border:true
		   });
		return systemConfigFormPanel;
		
	};
	
	return {
			formPanel:busiForm()
	}
}

Ext.extend(com.bhtec.view.business.platform.systemconfig.SystemConfigVOp, com.bhtec.view.util.CommonWidgets, {});