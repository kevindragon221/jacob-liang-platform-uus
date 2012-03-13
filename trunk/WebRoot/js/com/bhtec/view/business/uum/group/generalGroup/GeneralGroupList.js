/**
 * 普通组列表页面
 * @author lianglp
 * @version 2.0
 * @class com.bhtec.view.business.uum.group.generalGroup.GeneralGroupList
 * @date 2011-1-28
 */
Ext.namespace('com.bhtec.view.business.uum.group.generalGroup');
com.bhtec.view.business.uum.group.generalGroup.GeneralGroupList = function(config){
	var generalGroupName_q = 'generalGroupName_q';
	var moduleGridId = 'generalGroupGridId';
	
	/**
	 * 查询条件
	 */
	var queryCondition = function(){ 
		var queryArr = new Array();
		queryArr.push({
					border : false,
					layout : 'form',
					columnWidth : 0.3,
					items : [com.bhtec.view.util.CommonWidgets.prototype.textField({
								id:generalGroupName_q,
								width:150,
								fieldLabel : config.groupName,
								listeners: {
					                specialkey: function(field, e){
					                    if (e.getKey() == e.ENTER) {
					                       query();
					                    }
					                }
					            }
							})]
				});
		return queryArr;
	}
	/**
	 * 查询操作
	 */
	var query = function(){		
		var configQuery = {
				url : 'generalGroupAction!findGeneralGroupByCon.action',
				params : {
					groupName : getExtCmpValueById(generalGroupName_q)
				},
				callBack : function(returnData) {
					queryFillGridList(moduleGridId,returnData);
				}
			}
			ajaxRequest(configQuery);
	}
	/**
	 * 重置查询
	 */
	var reset = function(){
		resetCmpValueById(generalGroupName_q);
	}
	//grid row expander
	var expander = new Ext.ux.grid.RowExpander({
        tpl : new Ext.Template(
            '<div class="detailData"></div>'
        ) 
    });
    //expand事件
    expander.on("expand",function(expander,record,body,rowIndex){ 
        			var grid = new Ext.grid.GridPanel({
				        store: new Ext.data.JsonStore({
				            fields: ['memberResourceName','groupMemberType'],
				            data: record.data.uumGroupMembers//字典小类
				        }),
				        enableHdMenu : false,
				        cm: new Ext.grid.ColumnModel({				           
				            columns: [
				                new Ext.grid.RowNumberer(),
				                {
				                	header: '<span style=color:blue;>'+config.memberName+' </span>',  
				                	dataIndex: 'memberResourceName',
				                	width : 150,
				                	sortable: false,
				                	renderer:function(value){
				                		return '<span style=color:blue;>'+value+' </span>';
				                	}
				                },{
				                	header: '<span style=color:blue;>'+config.memberType+' </span>',  
				                	dataIndex: 'groupMemberType',
				                	width : 150,
				                	sortable: false,
				                	renderer:function(value){
				                		if(value == 'user'){
				                			return '<span style=color:blue;>用户</span>';
				                		}else{
				                			return '<span style=color:blue;>角色</span>';
				                		}
				                	}
				                }
				            ]
				        }),
				        viewConfig: {
				            forceFit:true
				        },
				        columnLines: true,
				        autoHeight:true,
				        border:true,	
				        iconCls:'icon-grid'
				    });
				    //停止级联选择
				    grid.on({
						'mouseover' : function(e) {
							e.stopPropagation(); 
						},
						'mouseout' : function(e) {
							e.stopPropagation(); 
						}
					});
								
					grid.afterMethod("processEvent", function(n, e) {
						e.stopPropagation();
					});
					new Ext.Panel({
						layout:'fit',
						items:grid,		
						bodyStyle:'padding-left:50px',	
						border:true,		
						autoWidth:true,
						baseCls : 'x-plain', 			
		        		renderTo: Ext.DomQuery.select("div.detailData",body)[0]
					})
			})
	
	/**
	 * 模块列模式
	 */
	var cols = function(){
		var colsArr = new Array();
		colsArr.push(expander);
		colsArr.push({
				dataIndex : 'groupId',
				hidden:true,
				sortable: true 
			});
		colsArr.push({
				header : config.groupName,
				dataIndex : 'groupName',
				width : 150,
				renderer:function(value){
					return '<span ext:qtip="'+value+'">'+value+'</span>';
				},
				sortable: true 
			});
		colsArr.push({
				header : config.memo,
				dataIndex : 'memo',
				width : 150,
				sortable: true,
				renderer:function(value){
					return '<span ext:qtip="'+value+'">'+value+'</span>';
				}
			});
		
		colsArr.push({
				header : config.status,
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
				header : config.creator,
				dataIndex : 'creator',
				width : basicConstant.GRID_COL_WIDTH,
				sortable: true 
			});
		colsArr.push({
				header : config.createDate,
				dataIndex : 'createDate',
				width : 200,
				sortable: true
			});
		
		return colsArr;
	}
	 /**
	  * 模块grid store
	  */
    var moduleStore = new Ext.data.JsonStore({
				fields : ['groupId', 'groupName', 'memo', 
						   'status','creator','createDate','uumGroupMembers'],
				autoLoad : true,
				totalProperty : 'count',
				root : 'uumGroupList',
				url : 'generalGroupAction!findGeneralGroupByCon.action'
			});
			
	
	/**
	 * 为翻页加自定义参数
	 */
    moduleStore.on('beforeload', function(thiz,options) {
    	var new_params = {
						groupName : getExtCmpValueById(generalGroupName_q)
					}; 
		Ext.apply(options.params,new_params); 
	});	
	
	/**
	 * 按钮功能操作
	 */
	var operateFunction = function(optFunLink){
		return function(){
			var opView = basicConstant.DYNAMIC_JS_INSTANCE_MAP.get(config.moduleId+'_'+basicConstant.OP_VIEW);
			if(opView == undefined || opView == null){
				var xmlDoc = basicConstant.DYNAMIC_LOAD_XMLDOC.get(config.moduleId);
				dynamicLoadModuleJs(xmlDoc,basicConstant.OP_VIEW,config.moduleId);
				opView = basicConstant.DYNAMIC_JS_INSTANCE_MAP.get(config.moduleId+'_'+basicConstant.OP_VIEW);
			}
			var moduleForm = eval(opView);
			if(basicConstant.ADD_OPT_LINK == optFunLink){
				moduleForm.saveForm();
			}else if(basicConstant.MODIFY_OPT_LINK == optFunLink){
				moduleForm.modifyForm();
			}else if(basicConstant.ENABLE_OPT_LINK == optFunLink){
				moduleForm.disEnable(basicConstant.ENABLE_OPT_LINK);
			}else if(basicConstant.DISABLE_OPT_LINK == optFunLink){
				moduleForm.disEnable(basicConstant.DISABLE_OPT_LINK);
			}
		}
	}
	/**
	 * 工具栏按钮
	 */	
    var toolbar = function(){
		var frametoolbar = new Array();
		var modOptList = fourthModOpt[config.moduleId];
		for(i=0;i<modOptList.length;i++){
			var modOpt = modOptList[i];
			var handlerFun = operateFunction(modOpt.optFunLink);
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
			currentPosition:basicConstant.UUM+'群组管理->普通组管理'
	}
	
	/**
	 * 列表区
	 */
	var gridListPara = {
			cols:cols(),
			store:moduleStore,
			gridId		:	moduleGridId,
			plugins:expander
	}
	/**
	 * 按钮区
	 */
	var toolbarPara = {
		toolbar		:	toolbar()
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
