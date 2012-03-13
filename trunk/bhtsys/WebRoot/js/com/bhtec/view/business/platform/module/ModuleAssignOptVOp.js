/**
 * 模块分配操作
 * @author lianglp
 * @version 1.0
 * @class com.bhtec.view.business.platform.moduleModuleAssignOptVOp
 * @date 2010-10-28
 */
Ext.namespace('com.bhtec.view.business.platform.module');
com.bhtec.view.business.platform.module.ModuleAssignOptVOp = function(config){
	var commWidgets = this;
	
	var record = commWidgets.textField({
				fieldLabel : '模块名称',
				width : 120,
				readOnly : true,
				value : config.moduleName||''
			});
	var moduleId = new Ext.form.Hidden({
				id : 'moduleId',
				value : config.moduleId
			});
	
	var headerPanel = commWidgets.panel({
				height : 35,
				autoWidth : true,
				items : [{
							columnWidth : .5,
							baseCls : 'x-plain',
							bodyStyle : 'padding:5px 0 5px 5px',
							layout : 'form',
							items : [record]
						}, {
							columnWidth : .5,
							baseCls : 'x-plain',
							bodyStyle : 'padding:5px 0 5px 5px',
							layout : 'form'
						}, moduleId]
			})
	
	var leftMultiSelPanel = {
							xtype : "multiselect",
							name : "leftmultiselect",
							id : "leftmultiselect",
							dataFields : ["code", "desc"],
							valueField : "code",
							displayField : "desc",
							border : false,
							height : Ext.getBody().getHeight() * 0.45 + 5,
							width : 250,
							data : config.unassignedArray1||[],
							tbar:[{text: '未选操作'}]
						};
			
	var rightMultiSelPanel = {
							xtype : "multiselect",
							name : "rightmultiselect",
							id : "rightmultiselect",
							dataFields : ["code", "desc"],
							valueField : "code",
							displayField : "desc",
							border : false,
							height : Ext.getBody().getHeight() * 0.45 + 5,
							width : 255,
							data : config.assignedArray1||[],
							tbar:[{text: '已选操作'}]
						};

	var selected = function(leftmultiselect,rightmultiselect) {
		var indexs = leftmultiselect.view.getSelectedIndexes();
		if (indexs.length == 0) {
			warningMesg({
						msg : "请您选择相应的操作!"
					});
			return;
		}
		var records = new Array();
		
		for (var i = indexs.length - 1; i > -1; i--) {
			records.push(leftmultiselect.store.getAt(indexs[i]));
			leftmultiselect.store.removeAt(indexs[i]);
		}
		rightmultiselect.store.add(records.reverse());
	}
	
	new com.bhtec.view.util.ux.MultiSelect({
		winTitle : '模块分配操作',
		leftPanel : leftMultiSelPanel,
		rightPanel : rightMultiSelPanel,
		headerPanel : headerPanel,
		isShowWindow:true,
		selected : function(){
						selected(getExtCmpById('leftmultiselect'),getExtCmpById('rightmultiselect'));
				   },
		unselected : function(){
						selected(getExtCmpById('rightmultiselect'),getExtCmpById('leftmultiselect'));
			       },
		buttons : [commWidgets.saveButton({
					handler : function(b) {
						var mul = getExtCmpById('rightmultiselect');
						var flag = mul.store.getCount();
						if (flag == 0) {
							warningMesg({
										msg : '请您分配相应的操作!'
									})
						} else {
							var operateIdList = new Array();
							for (var j = 0; j < mul.store.getCount(); j++) {
								var operateId = mul.store.getAt(j).get("code");
								operateIdList.push(operateId);
							}
							b.setDisabled(true);
							ajaxRequest({
								url : 'moduleAction!saveModuleOptRefs.action',
								params : {
									moduleId : getExtCmpValueById('moduleId'),
									operateIdList : operateIdList
								},
								callBack : function(returnData) {
									showSucMesg({
										msg : '分配操作保存成功!',
										fn : function(confirm) {
											if ('ok' == confirm) {
												getExtCmpById('selectedToId').close();
											}
										}
									});
								}
							});
						}
					}
				}), commWidgets.closeButton({
							handler : function() {
								this.ownerCt.ownerCt.ownerCt.close();
							}
						})]
	});
}

Ext.extend(com.bhtec.view.business.platform.module.ModuleAssignOptVOp,
		   com.bhtec.view.util.CommonWidgets, {});