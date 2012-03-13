/**
 * 角色分配模块操作页面
 * @author lianglp
 * @version 1.0
 * @class com.bhtec.view.business.uum.role.RoleAssignModOpt 
 * @date 2010-10-29
 */

Ext.namespace('com.bhtec.view.business.uum.role');
com.bhtec.view.business.uum.role.RoleAssignModOpt = function(config) {
	var roleName = config.roleName||'';
	var roleId = config.roleId||0;
	var commWidgets = this;

	var roleName = commWidgets.textField({
				id : 'defaultRoleName4Role',
				fieldLabel : '角色名称',
				width : 120,
				readOnly : true,
				value : roleName
			});
	
	var headerPanel = commWidgets.panel({
				height : 80,
				autoWidth : true,
				items : [ {
							columnWidth : .5,
							baseCls : 'x-plain',
							bodyStyle : 'padding:5px 0 5px 5px',
							layout : 'form',
							items : [roleName]
						},{
							columnWidth : 1,
							baseCls : 'x-plain',
							bodyStyle : 'padding:5px 0 5px 5px',
							layout : 'form',
							items : [commWidgets.radio({
								fieldLabel : '权限范围',
								id:'optPrivilegeScope4RoleId',
								items:[
				                	{boxLabel: '包含操作', name: 'priScope', inputValue: 'inc', 
				                	 checked: false},
									{boxLabel: '不包含操作', name: 'priScope', inputValue: 'exc',
									 checked: false},
									{boxLabel: '全部(无需分配操作)', name: 'priScope', inputValue: 'all',
									 checked: true}
				                ]
							})]
						}]
			})
		
	// 选中孩子
	var childHasChecked = function(node) {
		var childNodes = node.childNodes;
		if (childNodes || childNodes.length > 0) {
			for (var i = 0; i < childNodes.length; i++) {
				if (childNodes[i].getUI().checkbox.checked)
					return true;
			}
		}
		return false;
	}
			
	// 级联选中父节点
	var parentCheck = function(node, checked) {
		var checkbox = node.getUI().checkbox;
		if (typeof checkbox == 'undefined')
			return false;
		if (!(checked ^ checkbox.checked))
			return false;
		if (!checked && childHasChecked(node))
			return false;
		checkbox.checked = checked;
		node.attributes.checked = checked;
		node.getOwnerTree().fireEvent('check', node, checked);

		var parentNode = node.parentNode;
		if (parentNode !== null) {
			parentCheck(parentNode, checked);
		}
	}
	
	/**
	 * 递归追加结点
	 * 
	 * @param {}
	 *            treeArr
	 * @return {}
	 */
	var loadTree = function(moduleList,treePanelId) {
		var treePanel = getExtCmpById(treePanelId);
		var root = treePanel.getRootNode();
		var treeArr = new Array();
		for (var i = 0; i < moduleList.length; i++) {
			var cnode = commWidgets.treeNode({
						id : moduleList[i].moduleId,
						text : moduleList[i].modName,
						// leaf : false,
						checked : false,
						iconCls : moduleList[i].modImgCls
					});
			var parentNode = treePanel.getNodeById(moduleList[i].upModId);
			if (parentNode == null || parentNode == "undefine") {
				treeArr.push(moduleList[i]);
			} else {
				parentNode.appendChild(cnode);
			}

		}
		if (treeArr.length > 0)
			loadTree(treeArr,treePanelId);
	}
	
	/**
	 * 列表树 数据
	 */
	var treeWinMod = function(){
		
		var configFind = {
				url:'roleAction!roleAssignOptPrivilege.action',
				params:{
					roleId:roleId
				},
				callBack:function(returnData){
				    	var assignedModMenuList = returnData.assignedModMenuList;
				    	loadTree(assignedModMenuList,'rightTreeOptPanel4RoleId');
				    	var unassignedModMenuList = returnData.unassignedModMenuList;
				    	loadTree(unassignedModMenuList,'leftTreeOptPanel4RoleId');
				    	
				    	var privilegeScope = returnData.privilegeScope;
				    	var optPrivilegeScopeRadio = getExtCmpById('optPrivilegeScope4RoleId');//操作范围
				    	optPrivilegeScopeRadio.setValue(privilegeScope);
				    	
				    	var roleRowPrivilege = returnData.roleRowPrivilege;
				    	var rowPrivilegeRadio = getExtCmpById('rowPrivilege4RoleId');//行权限
				    	rowPrivilegeRadio.setValue(roleRowPrivilege);
				}
		}
		ajaxRequest(configFind);
	};
	
	/**
	 * 模块树 panel
	 */
	var leftTreePanel = commWidgets.treePanel({
			id:'leftTreeOptPanel4RoleId',
			title : '未选操作',
			autoWidth : true,
			border : true,
			height : Ext.getBody().getHeight() * 0.46,
			checkNode:function(node, checked) {
								var parentNode = node.parentNode;
								if (parentNode !== null) {
									parentCheck(parentNode, checked);
								}
								node.expand();
								node.attributes.checked = checked;
								node.eachChild(function(child) {
											child.ui.toggleCheck(checked);
											child.attributes.checked = checked;
											child.fireEvent('checkchange', child, checked);
										});
				
						},
		    rootVisible:false,
			rootNode:commWidgets.treeNode({
				id:'0',
				text:'模块树',
				checked:false,
				expanded:true
			})
	});
	
	/**
	 * 模块树 panel
	 */
	var rightTreePanel = commWidgets.treePanel({
				id:'rightTreeOptPanel4RoleId',
				title : '已选操作',
				width : 250,
				border : true,
				height : Ext.getBody().getHeight() * 0.46,
				rootVisible:false,
				rootNode:commWidgets.treeNode({
					id:'0',
					text:'模块树',
					checked:false,
					expanded:true
				}),
				checkNode:function(node, checked) {
									var parentNode = node.parentNode;
									if (parentNode !== null) {
										parentCheck(parentNode, checked);
									}
									node.expand();
									node.attributes.checked = checked;
									node.eachChild(function(child) {
												child.ui.toggleCheck(checked);
												child.attributes.checked = checked;
												child.fireEvent('checkchange', child, checked);
											});
					
							}
			});
			treeWinMod();
	/**
	 * 删除结点
	 * @param	delNode 结点对象
	 */
	var nodeDel = function(delNode) {
		if (delNode != null) {
			if (delNode.isLeaf()) {
				delNode.parentNode.removeChild(delNode);
			} else if (!delNode.hasChildNodes()) {
				delNode.parentNode.removeChild(delNode);
			}
		}
	}
	/**
	 *移动增加结点 
	 *@param	nodeId   结点ID
	 *@param	iconCls  结点样式
	 *@param	fromtree 源树
	 *@param	totree   目标树
	 */
	var moveNode = function(nodeId,iconCls, fromtree, totree) {
		var fromTreeNode = fromtree.getNodeById(nodeId);
		var toTreeRoot = totree.getRootNode();
		var obj = totree.getNodeById(nodeId);
		if (obj == null) {
			pNode = fromTreeNode.parentNode;
			toParentNode = totree.getNodeById(pNode.id);
			if (toParentNode) {
				toParentNode.appendChild(new Ext.tree.TreeNode({
							text : fromTreeNode.text,
							id : fromTreeNode.id,
							iconCls:iconCls,
							checked : false
						}));
			} else {
				toTreeRoot.appendChild(new Ext.tree.TreeNode({
							text : fromTreeNode.text,
							id : fromTreeNode.id,
							iconCls:iconCls,
							checked : false
						}));
			}
		}
	}
	/**
	 * 取消分配操作
	 */
	var selUnselOpt = function(fromtree,totree){
		var checkedNodes = fromtree.getChecked();
		if(checkedNodes.length == 0){
			warningMesg({
				msg:'请选择相应的模块操作!'
			});
			return;
		}
		for (var i = 0; i < checkedNodes.length; i++) {
			var checkedIds = checkedNodes[i].getPath();
			var nodeIds = checkedIds.split(fromtree.pathSeparator);
			for (var j = nodeIds.length - 1; j > 1; j--) {
				moveNode(nodeIds[j],checkedNodes[i].attributes.iconCls, fromtree, totree);
				nodeDel(fromtree.getNodeById(nodeIds[j]));
			}
		}
	}
	
	var selected = function() {
		var fromtree = getExtCmpById('leftTreeOptPanel4RoleId');
		var totree = getExtCmpById('rightTreeOptPanel4RoleId');
		selUnselOpt(fromtree,totree);
	}
	
	var unselected = function() {
		var fromtree = getExtCmpById('rightTreeOptPanel4RoleId');
		var totree = getExtCmpById('leftTreeOptPanel4RoleId');
		selUnselOpt(fromtree,totree);
	}
	
	//递归遍历tree
	var tradeFuncList = function(parent,optIdList){   
	   if(parent.childNodes && parent.childNodes.length>0){	
	       for (var i=0;i<parent.childNodes.length;i++){
	           var childNode = parent.childNodes[i];
               tradeFuncList(childNode,optIdList);
	       }
	   }else{
	   		optIdList.push(parent.id-1000);
	   }
	} 

	var multiPanel = new com.bhtec.view.util.ux.MultiSelect({
		winTitle : '角色分配模块操作',
		leftPanel : leftTreePanel,
		rightPanel : rightTreePanel,
		headerPanel : headerPanel,
		selected : selected,
		unselected : unselected,
		isShowWindow:false,
		buttons:[commWidgets.saveButton({
					handler : function(b) {
						var optPrivilegeScope = getExtCmpValueById('optPrivilegeScope4RoleId');
						if(optPrivilegeScope == null){
							warningMesg({
								msg:'请选择权限范围'
							})
							return;
						}
						var rightTree = getExtCmpById('rightTreeOptPanel4RoleId');
						var rootNode = rightTree.getRootNode();
						var optIdList ;
						if(rootNode.childNodes.length != 0){
							optIdList = new Array();
							tradeFuncList(rootNode,optIdList);
						}
						var paramsPri = {
							roleId : roleId,
							privilegeScope:optPrivilegeScope.inputValue
						}
						if(optIdList){//判断是否含有模块操作
							Ext.applyIf(paramsPri,{modOptIdList : optIdList})
						}
						b.setDisabled(true);
						ajaxRequest({
							url : 'roleAction!saveRoleOptPrivilege.action',
							params : paramsPri,
							callBack : function(returnData) {
								askMesg({
									msg : '模块操作权限保存成功,您是否继续分配权限?',
									fn : function(confirm) {
										if('cancel' == confirm){
											getExtCmpById('rolePriAssignWinId').close();
										}
									}
								});
							}
						});
					}
				}),
				commWidgets.closeButton({
					handler : function() {
						getExtCmpById('rolePriAssignWinId').close();
					}
				})
			]
	});
	
	
	var rowPrivilege = commWidgets.fieldSet({
		autoWidth:true,
		bodyStyle : basicConstant.PAGE_BACKGROUND,
		columnFields:[{
					layout:'form',
					border:false,
					columnWidth:0.6,
					items:[commWidgets.radio({
						fieldLabel : '角色行权限',
						id:'rowPrivilege4RoleId',
						items:[
		                	{boxLabel: '机构', name: 'rolPri', inputValue: 'org', 
		                	 checked: true},
							{boxLabel: '角色', name: 'rolPri', inputValue: 'rol',
							 checked: false},
							{boxLabel: '用户', name: 'rolPri', inputValue: 'usr',
							 checked: false}
		                ]
					})]
				},{
					layout:'form',
					border:false,
					columnWidth:0.3,
					items:[commWidgets.saveButton({
								id:'rowPrivilegeSaveBut',
								handler : function() {
										ajaxRequest({
											url : 'roleAction!saveRoleRowPrivilege.action',
											params : {
												roleId : roleId,
												privilegeScope:getExtCmpValueById('rowPrivilege4RoleId').inputValue
											},
											callBack : function(returnData) {
												askMesg({
														msg : '行权限保存成功,您是否继续分配权限?',
														fn : function(confirm) {
															if('cancel' == confirm){
																getExtCmpById('rolePriAssignWinId').close();
															}
														}
													});
											}
										});
								}
							})]
				}],
		customColumnItems:true,
		title:'行权限分配'
	});
	
	var mainPagePanel = {
		xtype : 'tabpanel',
		enableTabScroll:true,
		activeTab:0,
		frame:false,
		border: true,
		width : Ext.getBody().getWidth() * 0.5,
		height : Ext.getBody().getHeight() * 0.8,
        items:[{
	        	title: '模块操作权限分配',
	        	items:multiPanel
        	},{
        		title: '行权限分配',
        		bodyStyle : 'padding-left:15px;padding-right:15px;',
	        	items:rowPrivilege
	        }]
	}
	
	commWidgets.window({
				id:'rolePriAssignWinId',
				title:'角色分配普通权限',
				items:[mainPagePanel],
				width : Ext.getBody().getWidth() * 0.5,
				height : Ext.getBody().getHeight() * 0.8
	});
	
}
Ext.extend(com.bhtec.view.business.uum.role.RoleAssignModOpt, 
	com.bhtec.view.util.CommonWidgets, {});