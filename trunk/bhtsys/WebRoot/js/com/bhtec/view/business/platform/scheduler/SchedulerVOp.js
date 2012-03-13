/**
 * 调度操作
 * @author lianglp
 * @version 1.0
 * @class com.bhtec.view.business.platform.scheduler.SchedulerVOp
 * @date 2010-01-17
 */
Ext.namespace('com.bhtec.view.business.platform.scheduler');
com.bhtec.view.business.platform.scheduler.SchedulerVOp = function(config){
	var moduleVOp = this;   //父类调用
	var moduleGridId = 'schedulerGridId';//form表单id
	
	/**
	 * 调度增加修改表单
	 */
	var funForm = function(configForm){
		/**
		 * 公用保存设置
		 */
		var saveCommon = function(configSave) {
				if(getExtCmpValueById('jobOldNameId') != getExtCmpValueById('jobName')){
					var syncUrl = 'schedulerAction!findSysplSchedulerJobByJobName.action';
					var data = syncAjaxReqDecode(syncUrl,'jobName='+getExtCmpValueById('jobName'));
					if(data == '')return;
					if(data.existJobName == true){
						var configExist = {
							msg:'对不起,任务名称已经存在!'
						}
						warningMesg(configExist);
						moduleVOp.enableSaveButton();//添加保存enable
						return false;
					}
				}
				submitForm({
					url : configSave.url,
					params : {
						status:getExtCmpValueById('status')
					},
					callBack : function(returnData) {
						var configCb = {
							msg : '任务保存成功!',
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
			var url = 'schedulerAction!saveSysplSchedulerJob.action';
			if(configForm.modify == true){
				url = 'schedulerAction!modifySysplSchedulerJob.action';
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
				url:'schedulerAction!saveSysplSchedulerJob.action',
				save:'saveAdd'
			}
			saveCommon(configSave);
		}
		/**
		 * 公用增加修改删除表单
		 */
		var moduleData = configForm.moduleData||'';
		
		//调度ID
		var jobId = new Ext.form.Hidden({
				name : "jobId",
				value:moduleData.jobId||''
		});
		//old english job
		var jobOldName = new Ext.form.Hidden({
				id : "jobOldNameId",
				value:moduleData.jobName||''
		})

		//英文调度名称
		var jobName = moduleVOp.textField({
				id : "jobName",
				name : "jobName",
				allowBlank:false,
				fieldLabel : config.jobName,
				value:moduleData.jobName||'',
				maxLength:30,
				vtype:'alphanum'
		});
		
		//调度类名称
		var jobClassDescript = moduleVOp.textField({
				name : "jobClassDescript",
				allowBlank:false,
				fieldLabel : config.jobClassDescript,
				value:moduleData.jobClassDescript||'',
				maxLength:100
		});
		
		//时间表达式
		var timeExpress = moduleVOp.textField({
				id:'timeExpress',
				name : 'timeExpress',
				allowBlank:false,
				fieldLabel : config.timeExpress,
				value:moduleData.timeExpress||'',
				maxLength:20
		});
		//开始时间
		var startTime = {
				id : "startTime",
				name : "startTime",
				fieldLabel : config.startTime,
				format:'Y-m-d H:i:s',
				value:moduleData.startTime,
				xtype:'datetimefield',
				width:200
		};
			
		//结束时间
		var endTime = {
				id : "endTime",
				name : "endTime",
				fieldLabel : config.endTime,
				format:'Y-m-d h:i:s',
				value:moduleData.endTime,
				xtype:'datetimefield',
				width:200
		};
		//重复次数
		var repeatTime = moduleVOp.numberField({
				id : "repeatTime",
				name : "repeatTime",
				value:moduleData.repeatTime||'',
				maxLength:2,
				fieldLabel : config.repeatTime,
				allowDecimals : false,
				allowBlank:false
		});
		//时间间隔
		var splitTime = moduleVOp.numberField({
				id : "splitTime",
				name : "splitTime",
				value:moduleData.splitTime||'',
				maxLength:10,
				fieldLabel : config.splitTime,
				allowDecimals : false,
				allowBlank:false
		});
		
		var triggerTypeValue = moduleData.triggerType==undefined?'Simple':moduleData.triggerType;
		//触发器类型
		var triggerType = moduleVOp.radio({
				id : "triggerType",
				name : "triggerType",
				value:triggerTypeValue,
				fieldLabel : config.triggerType,
				width:150,
				items:[
					{boxLabel: 'Simple', name: 'triggerType', inputValue: 'Simple', 
						checked: (triggerTypeValue=='Simple'?true:false),
						listeners:{
							'check':function(checkbox,checked){
								if(checked){
									var jobSimpleSetField = getExtCmpById('jobSimpleSetFieldId');
									var jobCronSetField = getExtCmpById('jobCronSetFieldId');
									jobSimpleSetField.setVisible(true);
									jobCronSetField.setVisible(false);
									getExtCmpById('repeatTime').setDisabled(false);
									getExtCmpById('splitTime').setDisabled(false);
									getExtCmpById('timeExpress').setDisabled(true);
								}
							}
						}
					},
                	{boxLabel: 'Cron', name: 'triggerType', inputValue: 'Cron',
                		checked: (triggerTypeValue=='Cron'?true:false),
                		listeners:{
							'check':function(checkbox,checked){
								if(checked){
									var jobSimpleSetField = getExtCmpById('jobSimpleSetFieldId');
									var jobCronSetField = getExtCmpById('jobCronSetFieldId');
									jobSimpleSetField.setVisible(false);
									jobCronSetField.setVisible(true);
									getExtCmpById('repeatTime').setDisabled(true);
									getExtCmpById('splitTime').setDisabled(true);
									getExtCmpById('timeExpress').setDisabled(false);
								}
							}	
						}	
                	}
                ]
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
				hidden:configForm.modify==true?true:false,
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
			items : [jobName,status,triggerType]
		});
		columnFields.push({
			border : false,
			layout : "form",
			columnWidth : .5,
			items : [jobClassDescript,memo]
		});
		
		var jobFieldForSimple = moduleVOp.fieldSet({
			id:'jobSimpleSetFieldId',
			title:'任务设置',
			columnFields:[startTime,endTime,repeatTime,splitTime],
			listeners:{
				afterrender:function(fieldSet){
					if(moduleData.triggerType=='Cron'){
						fieldSet.setVisible(false);
						getExtCmpById('repeatTime').setDisabled(true);
						getExtCmpById('splitTime').setDisabled(true);
						getExtCmpById('timeExpress').setDisabled(false);
					}
				}
			}
		});
		
		var jobFieldForCron = moduleVOp.fieldSet({
			id:'jobCronSetFieldId',
			title:'任务设置',
			columnFields:[timeExpress],
			listeners:{
				afterrender:function(fieldSet){
					if(moduleData.triggerType==undefined 
						|| moduleData.triggerType=='Simple'){
						fieldSet.setVisible(false);
						getExtCmpById('timeExpress').setDisabled(true);
					}
				}
			}
		});
		
		columnFields.push({
				id:'jobSimpleFieldId',
				border : false,
				layout : "form",
				columnWidth : 1,
				items : jobFieldForSimple
			});
		columnFields.push({
				id:'jobCronFieldId',
				border : false,
				layout : "form",
				columnWidth : 1,
				items : jobFieldForCron
			});
		columnFields.push([jobId,jobOldName,creator,createDate]);
		
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
			title:'系统任务添加'
		});
	}
	/**
	 * 点击列表修改，弹出修改页面
	 */
	var modifyForm = function(){
		var modDelRecord = modifyDelSelRecord(moduleGridId);//请选择一条件记录
		if(modDelRecord != ''){
			var configFind = {
					url:'schedulerAction!findSysplSchedulerJobByJobId.action',
					params:{modViewRecId:modDelRecord.jobId},
					callBack:function(returnData){
						var configForm = {
							title:'任务调度修改',
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
					url:'schedulerAction!findSysplSchedulerJobByJobId.action',
					params:{modViewRecId:modDelRecord.jobId},
					callBack:function(returnData){
						var configForm = {
							title:'任务调度查看',
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
	 * 删除选定记录
	 */
	var deleteRecord = function(){
		var modDelRecord = modifyDelSelRecord(moduleGridId);//请选择一条件记录
		if(modDelRecord != ''){
			askMesg({
				msg:'您确认删除此记录吗?',
				fn:function(confirm){
					if(confirm == 'ok'){
						var configDel = {
								url:'schedulerAction!deleteSysplSchedulerJobByJobId.action',
								params:{modViewRecId:modDelRecord.jobId},
								callBack:function(returnData){
									var configCb = {
										msg : '任务调度删除成功!',
										fn : function(confirm) {
											refreshGridList(moduleGridId);
										}
									}
									showSucMesg(configCb);
								}
						}
						ajaxRequest(configDel);
					}
				}
			});
		}
	}
	
	/**
	 * 启用停用操作
	 */
	var disableEnalbeOpt = function(flag,modDelRecord,url){
		var moduleGridPanel = getExtCmpById(moduleGridId);
		var selModDelRecord = moduleGridPanel.getSelectionModel().getSelected();
		var configdisEnable = {
				url:url,
				noMask:true,
				params:{
					jobName:modDelRecord.jobName,
					modViewRecId:modDelRecord.jobId
				},
				callBack:function(returnData){
					var msg = '';
					if(flag == 'enable'){
						msg = '任务调度启动成功!';
					}else{
						msg = '任务调度停止成功!';
					}
					selModDelRecord.set('status', flag);
					moduleStore = moduleGridPanel.store;
					moduleStore.commitChanges();
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
	 * 启用 停止 任务
	 */
	var startOrStopJob = function(flag){
		var modDelRecord = modifyDelSelRecord(moduleGridId);//请选择一条件记录
		if(modDelRecord == '')return;
		var url = '';
		if(flag == modDelRecord.status){
			var msg='';
			if(flag == 'enable'){
				msg = '此任务调度已经启用!';
			}else{
				msg = '此任务调度已经停止!';
			}
			warningMesg({
				msg:msg
			})
			return;
		}else{
			if(flag == 'enable'){
				url = 'schedulerAction!startJob.action';
			}else{
				url = 'schedulerAction!stopJob.action';
			}
		}
		
		disableEnalbeOpt(flag,modDelRecord,url);
	}
	//start scheduler
	var schedulerStart = function(){
		ajaxRequest({
			url:'schedulerAction!startScheduler.action',
			noMask:true,
			params:{
			},
			callBack:function(returnData){
				var configCb = {
					msg : '调度启用成功!',
					fn : function(confirm) {
						refreshGridList(moduleGridId);
					}
				}
				showSucMesg(configCb);
			}
		});
	}
	
	//stop scheduler
	var schedulerStop = function(){
		ajaxRequest({
			url:'schedulerAction!stopScheduler.action',
			noMask:true,
			params:{
			},
			callBack:function(returnData){
				var configCb = {
					msg : '调度停止成功!',
					fn : function(confirm) {
						refreshGridList(moduleGridId);
					}
				}
				showSucMesg(configCb);
			}
		});
	}
	
	
	return {
			saveForm:saveForm,
			modifyForm:modifyForm,
			viewForm:viewForm,
			schedulerStart:schedulerStart,
			schedulerStop:schedulerStop,
			startOrStopJob:startOrStopJob,
			deleteRecord:deleteRecord
	}
}

Ext.extend(com.bhtec.view.business.platform.scheduler.SchedulerVOp, com.bhtec.view.util.CommonWidgets, {});