/**
 * 调度列表页面
 * @author lianglp
 * @version 1.0
 * @class com.bhtec.view.business.platform.scheduler.SchedulerList
 * @date 2011-01-17
 */
Ext.namespace('com.bhtec.view.business.platform.scheduler');
com.bhtec.view.business.platform.scheduler.SchedulerList = function(config){
	var xmlDoc = config.xmlDoc;//域描述信息
	var jobName_scheduler_q = 'jobName_scheduler_q';
	var triggerType_scheduler_q = 'triggerType_scheduler_q';
	var schedulerGridId = 'schedulerGridId';
//	+Math.random();
	
	/**
	 * 查询条件
	 */
	var queryCondition = function(){ 
		var queryArr = new Array();
		queryArr.push({
					border : false,
					layout : "form",
					columnWidth : 0.3,
					items : [
						com.bhtec.view.util.CommonWidgets.prototype.textField({
								id:jobName_scheduler_q,
								width:100,
								fieldLabel : getFormFieldName(xmlDoc,'jobName'),
								listeners: {
					                specialkey: function(field, e){
					                    if (e.getKey() == e.ENTER) {
					                       query();
					                    }
					                }
					            }
						})]
				},{
					border : false,
					layout : "form",
					columnWidth : 0.3,
					items : [
						com.bhtec.view.util.CommonWidgets.prototype.radio({
								id : triggerType_scheduler_q,
								fieldLabel : getFormFieldName(xmlDoc,'triggerType'),
								width:150,
								items:[
									{boxLabel: 'Simple', name: 'triggerType_q', inputValue: 'Simple'},
				                	{boxLabel: 'Cron', name: 'triggerType_q', inputValue: 'Cron'}
				                ]
						})
						]
				});
		return queryArr;
	}
	/**
	 * 查询操作
	 */
	var query = function(){	
		var triggerType = getExtCmpValueById(triggerType_scheduler_q)==null?''
						  :getExtCmpValueById(triggerType_scheduler_q).inputValue;
		var configQuery = {
				url : 'schedulerAction!findSchedulerByCon.action',
				params : {
					jobName : getExtCmpValueById(jobName_scheduler_q),
					triggerType :  triggerType
				},
				callBack : function(returnData) {
					queryFillGridList(schedulerGridId,returnData);
				}
			}
			ajaxRequest(configQuery);
	}
	/**
	 * 重置查询
	 */
	var reset = function(){
		resetCmpValueById(jobName_scheduler_q);
		resetCmpValueById(triggerType_scheduler_q);
	}
	
	/**
	 * 列模式
	 */
	var cols = function(){
		var colsArr = new Array();
		colsArr.push({
				dataIndex : 'jobId',
				hidden:true,
				sortable: true 
			});
		colsArr.push({
				header : getFormFieldName(xmlDoc,'jobName'),
				dataIndex : 'jobName',
				width : 120,
				sortable: true 
			});
		colsArr.push({
				header : getFormFieldName(xmlDoc,'jobClassDescript'),
				dataIndex : 'jobClassDescript',
				width : 200,
				sortable: true,
				renderer:function(value){
					return '<span qtip="'+value+'">'+value+'</span>';
				}
			});
		colsArr.push({
				header : getFormFieldName(xmlDoc,'triggerType'),
				dataIndex : 'triggerType',
				width : basicConstant.GRID_COL_WIDTH,
				sortable: true
			});
		colsArr.push({
				header : getFormFieldName(xmlDoc,'status'),
				dataIndex : 'status',
				width : basicConstant.GRID_COL_WIDTH,
				sortable: true,
				renderer:function(data){
					var status = com.bhtec.util.Data.status;
					for(i=0;i<status.length;i++){
						if(data == status[i].status){
							return status[i].statusName;
						}
					}
				}
			});
		colsArr.push({
				header : getFormFieldName(xmlDoc,'memo'),
				dataIndex : 'memo',
				width : 120,
				sortable: true,
				renderer:function(value){
					return '<span qtip="'+value+'">'+value+'</span>';
				}
			});
		colsArr.push({
				header : getFormFieldName(xmlDoc,'creator'),
				dataIndex : 'creator',
				width : basicConstant.GRID_COL_WIDTH,
				sortable: true
			});
		colsArr.push({
				header : getFormFieldName(xmlDoc,'createDate'),
				dataIndex : 'createDate',
				width : 120,
				sortable: true
			});
		return colsArr;
	}
	
	 /**
	  * 主面框架grid store
	  */
	 var store = new Ext.data.JsonStore({
				fields : ['jobId','jobName','jobClassDescript',
			           	  'triggerType','status','memo','creator',
			           	  'createDate'],
				autoLoad : true,
				totalProperty : 'count',
				root : 'jobList',
				url : 'schedulerAction!findSchedulerByCon.action'
	});
	
    /**
	 * 为翻页加自定义参数
	 */
    store.on('beforeload', function(thiz,options) {
    	var new_params = {
					jobName : getExtCmpValueById(jobName_scheduler_q),
					triggerType :  getExtCmpValueById(triggerType_scheduler_q)
		}; 
		Ext.apply(options.params,new_params); 
	});
	/**
	 * 工具栏按钮
	 */				
    var toolbar = function(){
		var frametoolbar = new Array();
		var modOptList = fourthModOpt[config.moduleId];
		if(modOptList == undefined)return;
		for(i=0;i<modOptList.length;i++){
			var modOpt = modOptList[i];
			var handlerFun;
			if(basicConstant.ADD_OPT_LINK == modOpt.optFunLink){
				handlerFun = config.moduleForm.saveForm;
			}else if(basicConstant.MODIFY_OPT_LINK == modOpt.optFunLink){
				handlerFun = config.moduleForm.modifyForm;
			}else if(basicConstant.VIEW_OPT_LINK == modOpt.optFunLink){
				handlerFun = config.moduleForm.viewForm;
			}else if(basicConstant.DELETE_OPT_LINK == modOpt.optFunLink){
				handlerFun = config.moduleForm.deleteRecord;
			}else if('schedulerStart' == modOpt.optFunLink){
				var syncUrl = 'schedulerAction!schedulerStatus.action';
				var data = syncAjaxReqDecode(syncUrl);
				var schedulerCls = 'scheduler_stop';
				if(data.schedulerIsStart == true){
					schedulerCls = 'scheduler_start';
				}
				frametoolbar.push('<span style=color:blue;>当前调度器状态： </span>',
					{id:'schedulerClsId',iconCls:schedulerCls},'-');
				frametoolbar.push('<span style=color:blue;>调度器操作： </span>');
				handlerFun = function(){
					config.moduleForm.schedulerStart();
					getExtCmpById('schedulerClsId').setIconClass('scheduler_start');
				}
			}else if('schedulerStop' == modOpt.optFunLink){
				handlerFun = function(){
					config.moduleForm.schedulerStop();
					getExtCmpById('schedulerClsId').setIconClass('scheduler_stop');
				}
			}else if('jobStart' == modOpt.optFunLink){
				handlerFun = function(){
					config.moduleForm.startOrStopJob(basicConstant.ENABLE_OPT_LINK);
					getExtCmpById('schedulerClsId').setIconClass('scheduler_start');
				};
			}else if('jobStop' == modOpt.optFunLink){
				handlerFun = function(){
					config.moduleForm.startOrStopJob(basicConstant.DISABLE_OPT_LINK);
					getExtCmpById('schedulerClsId').setIconClass('scheduler_stop');
				};
			}
			frametoolbar.push({
				text:modOpt.modName,
				iconCls:modOpt.modImgCls,
				handler:handlerFun
			},'-');
		}
		return frametoolbar;
	};
	/**
	 * 查询区
	 */
	var queryPara = {
			query:query,
			reset:reset,
			queryCondition:queryCondition(),
			queryColWidth:0.2,
			currentPosition:basicConstant.PLTM+'系统管理->系统调度管理'
	}
	/**
	 * 列表区
	 */
	var gridListPara = {
			cols:cols(),
			store:store,
			gridId:schedulerGridId
	}
	/**
	 * 按钮区
	 */
	var toolbarPara = {
		toolbar:toolbar()
	}
	/**
	 * 整个列表
	 */
	var configList = {
			queryPara:queryPara,
			toolbarPara:toolbarPara,
			gridListPara:gridListPara
	}	
	return configList;
}
