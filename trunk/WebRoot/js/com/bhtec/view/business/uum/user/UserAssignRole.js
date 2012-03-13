/**
 * 用户分配角色页面
 * @author lianglp
 * @version 1.0
 * @class com.bhtec.view.business.uum.user.UserAssignRole 
 * @date 2010-10-25
 */

Ext.namespace('com.bhtec.view.business.uum.user');
com.bhtec.view.business.uum.user.UserAssignRole = function(config) {
	var userName = config.userName;
	var userId = config.userId;
	var roleUserRefArray1 = config.roleUserRefArray1;
	var defaultRoleNamee = config.defaultRoleNamee;
	var defaultRoleIdd = config.defaultRoleIdd;
	var commWidgets = this;

	var record = commWidgets.textField({
				fieldLabel : '用户名',
				width : 120,
				readOnly : true,
				value : userName
			});
	var assignUserId = new Ext.form.Hidden({
				id : 'assignUserId',
				value : userId
			});
	var defaultRoleName = commWidgets.textField({
				id : 'defaultRoleName',
				fieldLabel : '默认角色',
				width : 120,
				readOnly : true,
				value : defaultRoleNamee
			});
	var defaultRoleId = new Ext.form.Hidden({
				id : 'defaultRoleId',
				value : defaultRoleIdd
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
							layout : 'form',
							items : [defaultRoleName]
						}, defaultRoleId]
			})
	/**
	 * 模块树 panel
	 */
	var treePanel = commWidgets.asyncTreePanel({
				id : 'orgRoleCheckTreeId',
				title : '未选角色',
				rootText : basicConstant.ORGAN_ROOT,
				autoWidth : true,
				border : true,
				height : Ext.getBody().getHeight() * 0.46,
				url : 'userAction!findNextLevelChildNodes.action?isCheckbox=Y',//需要checkbox
				isChecked:false,
				rootVisible : true
			});

	var multiSelPanel = {
							xtype : "multiselect",
							name : "rolemultiselect",
							id : "rolemultiselect",
							dataFields : ["code", "desc"],
							valueField : "code",
							displayField : "desc",
							border : false,
							height : Ext.getBody().getHeight() * 0.45 + 5,
							width : 250,
							data : roleUserRefArray1,
							tbar:[{text: '已选角色'}]
						};

	var selected = function() {
		var mul = getExtCmpById('rolemultiselect');
		var lefttree = getExtCmpById('orgRoleCheckTreeId');
		if (lefttree.getChecked().length > 0) {
			var checkedList = lefttree.getChecked();
			var records = [];
			for (var i = 0; i < checkedList.length; i++) {
				var bol = false;
				var singleNode = checkedList[i];
				var orgRoleId = singleNode.id;
				var index = orgRoleId.indexOf('_');
				var roleId = orgRoleId.substr(0, index);
				for (var j = 0; j < mul.store.getCount(); j++) {
					if (mul.store.getAt(j).get("code") == roleId) {
						bol = true;
						break;
					}
				}
				if (bol == false) {
					var r = new Ext.data.Record({});
					r.set("code", roleId);
					r.set("desc",(singleNode.parentNode.text + '-' + singleNode.text));
					records.push(r);
				}
			}

			mul.store.add(records);
			if (mul.store.getCount() == 1) {
				var roleId = mul.store.getAt(0).get("code");
				var roleName = mul.store.getAt(0).get("desc");
				var defaultRoleId = Ext.getCmp("defaultRoleId");
				defaultRoleId.setValue(roleId);
				var defaultRoleName = Ext.getCmp("defaultRoleName");
				defaultRoleName.setValue(roleName);
			}
		} else {
			warningMesg({
						msg : '请您选择相应角色!'
					})
		}
	}

	var unselected = function() {
		var mul = getExtCmpById('rolemultiselect');
		var indexs = mul.view.getSelectedIndexes();
		if (indexs.length == 0) {
			warningMesg({
						msg : "请您选择角色列表!"
					});
			return;
		}
		var defaultRoleId = Ext.getCmp("defaultRoleId");
		var lefttree = getExtCmpById('orgRoleCheckTreeId');
		var selectValues = mul.getValue();
		var values = selectValues.split(",");
		for (j = 0; j < values.length; j++) {
			var nodet = lefttree.getNodeById(values[j] + '_2');
			if (nodet) {
				nodet.attributes.checked = false;
				nodet.ui.toggleCheck(false);
				if (defaultRoleId == values[j]) {
					Ext.getCmp("defaultRoleId").setValue("");
					Ext.getCmp("defaultRoleName").setValue("");
				}
			}
		}

		for (var i = indexs.length - 1; i > -1; i--) {
			mul.store.removeAt(indexs[i]);
		}

		if (mul.store.getCount() == 1) {
			var roleId = mul.store.getAt(0).get("code");
			var roleName = mul.store.getAt(0).get("desc");
			var defaultRoleId = Ext.getCmp("defaultRoleId");
			defaultRoleId.setValue(roleId);
			var defaultRoleName = Ext.getCmp("defaultRoleName");
			defaultRoleName.setValue(roleName);
		} else if (mul.store.getCount() == 0) {
			var defaultRoleId = Ext.getCmp("defaultRoleId");
			defaultRoleId.setValue('');
			var defaultRoleName = Ext.getCmp("defaultRoleName");
			defaultRoleName.setValue('');
		}
	}

	var setDefaultRole = function() {
		var mulSelect = getExtCmpById('rolemultiselect');
		var indexs = mulSelect.view.getSelectedIndexes();

		if (indexs == null || indexs == "") {
			warningMesg({
						msg : '请您选择相应角色!'
					})
		} else {
			node = mulSelect.store.getAt(indexs[0]);
			var defaultRoleName = Ext.getCmp("defaultRoleName");
			defaultRoleName.setValue(node.data.desc);
			var defaultRoleId = Ext.getCmp("defaultRoleId");
			defaultRoleId.setValue(node.data.code);
		}
	}

	new com.bhtec.view.util.ux.MultiSelect({
		winTitle : '用户分配角色',
		leftPanel : treePanel,
		rightPanel : multiSelPanel,
		headerPanel : headerPanel,
		selected : selected,
		unselected : unselected,
		isShowWindow:true,
		buttons : [{
					xtype : 'button',
					text : '默认角色',
					iconCls : 'table_multiple',
					handler : setDefaultRole
				}, commWidgets.saveButton({
					handler : function(b) {
						var mul = getExtCmpById('rolemultiselect');
						var flag = mul.store.getCount();
						var defaultRoleId = getExtCmpValueById("defaultRoleId");
						if (defaultRoleId == '') {
							warningMesg({
										msg : '请为用户选择一个默认角色!'
									})
						} else if (flag == 0) {
							warningMesg({
										msg : '请为用户分配一个角色!'
									})
						} else {
							var orgRoleIds = '';
							for (var j = 0; j < mul.store.getCount(); j++) {
								var orgRoleId = mul.store.getAt(j).get("code");
								orgRoleIds += orgRoleIds == ""
										? orgRoleId
										: "," + orgRoleId;

							}
							b.setDisabled(true);
							ajaxRequest({
								url : 'userAction!saveRoleUser.action',
								params : {
									userId : getExtCmpValueById('assignUserId'),
									orgRoleIds : orgRoleIds,
									defaultOrgRoleId : defaultRoleId
								},
								callBack : function(returnData) {
									showSucMesg({
										msg : '分配角色保存成功!',
										fn : function(confirm) {
											if ('ok' == confirm) {
												refreshGridList('userGridId');
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
Ext.extend(com.bhtec.view.business.uum.user.UserAssignRole, com.bhtec.view.util.CommonWidgets, {});