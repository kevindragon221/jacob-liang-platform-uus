/**
 * 用户分配模块操作页面
 * @author lianglp
 * @version 2.0
 * @class com.bhtec.view.business.uum.user.UserAssignModOpt 
 * @date 2010-10-29
 */

Ext.namespace('com.bhtec.view.business.uum.user');
com.bhtec.view.business.uum.user.UserAssignModOpt = function(config) {
	var userName = config.userName||'';
	var userId = config.userId||0;
	var commWidgets = this;

	var userNamePanel = commWidgets.panel({
			autoWidth : true,
			layout:'form',
			items : [commWidgets.textField({
						id : 'defaultuserName',
						fieldLabel : '用户名称',
						width : 120,
						readOnly : true,
						value : userName
					})]
	});
	
	var headerPanel = commWidgets.panel({
			height : 40,
			autoWidth : true,
			items : [{
						columnWidth : .9,
						baseCls : 'x-plain',
						bodyStyle : 'padding:5px 0 5px 5px',
						layout : 'form',
						items : [commWidgets.radio({
							fieldLabel : '权限范围',
							id:'optPrivilegeScope4UserId',
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
	 * 模块树 panel
	 */
	var leftTreePanel = commWidgets.treePanel({
			id:'leftTreeOptPanel4UserId',
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
				id:'rightTreeOptPanel4UserId',
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
				url:'userAction!userAssignOptPrivilege.action',
				params:{
					userId:userId
				},
				callBack:function(returnData){
				    	var assignedModMenuList = returnData.assignedModMenuList;
				    	loadTree(assignedModMenuList,'rightTreeOptPanel4UserId');
				    	var unassignedModMenuList = returnData.unassignedModMenuList;
				    	loadTree(unassignedModMenuList,'leftTreeOptPanel4UserId');
				    	
				    	var privilegeScope = returnData.privilegeScope;
				    	var optPrivilegeScopeRadio = getExtCmpById('optPrivilegeScope4UserId');//操作范围
				    	optPrivilegeScopeRadio.setValue(privilegeScope);
				}
		}
		ajaxRequest(configFind);
	};
	
	treeWinMod();
			
	/**
	 * 取消结点勾选
	 * @param tree 树对象
	 */
	var unChecked = function(tree){
		var nodes=tree.getChecked();
		for(var i=0;i<nodes.length;i++){
			nodes[i].attributes.checked=false;
			nodes[i].ui.toggleCheck(false);
			
		}
	}
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
		var fromtree = getExtCmpById('leftTreeOptPanel4UserId');
		var totree = getExtCmpById('rightTreeOptPanel4UserId');
		selUnselOpt(fromtree,totree);
	}
	
	var unselected = function() {
		var fromtree = getExtCmpById('rightTreeOptPanel4UserId');
		var totree = getExtCmpById('leftTreeOptPanel4UserId');
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
		winTitle : '用户分配模块操作',
		leftPanel : leftTreePanel,
		rightPanel : rightTreePanel,
		headerPanel : headerPanel,
		selected : selected,
		unselected : unselected,
		isShowWindow:false,
		buttons : [commWidgets.saveButton({
					handler : function(b) {
							var optPrivilegeScope = getExtCmpValueById('optPrivilegeScope4UserId');
							if(optPrivilegeScope == null){
								warningMesg({
									msg:'请选择权限范围'
								})
								return;
							}
							var rightTree = getExtCmpById('rightTreeOptPanel4UserId');
							var rootNode = rightTree.getRootNode();
							var optIdList;
							if(rootNode.childNodes.length != 0){
								optIdList = new Array();
								tradeFuncList(rootNode,optIdList);
							}
							var paramsPri = {
								userId : userId,
								privilegeScope:optPrivilegeScope.inputValue
							}
							if(optIdList){//判断是否含有模块操作
								Ext.applyIf(paramsPri,{modOptIdList : optIdList})
							}
							b.setDisabled(true);
							ajaxRequest({
								url : 'userAction!saveUserOptPrivilege.action',
								params : paramsPri,
								callBack : function(returnData) {
									askMesg({
										msg : '模块操作权限保存成功,您是否继续分配权限?',
										fn : function(confirm) {
											if('cancel' == confirm){
												getExtCmpById('userPriAssignWinId').close();
											}
										}
									});
								}
							});
					}
				}),commWidgets.closeButton({
						handler : function() {
							getExtCmpById('userPriAssignWinId').close();
						}
					})]
	});
	
	
	var mainPagePanel = {
		xtype : 'tabpanel',
		enableTabScroll:true,
		activeTab:0,
		frame:false,
		border: true,
		width : Ext.getBody().getWidth() * 0.47,
		height : Ext.getBody().getHeight() * 0.7,
        items:[{
	        	title: '模块操作权限分配',
	        	items:multiPanel
        	},{
        		title: '行权限分配',
        		id:'userRowPriAssignId',
	        	items:[new com.bhtec.view.business.uum.user.UserAssignRowPrivilege({userId:userId})]
	        }]
	}
	
	commWidgets.window({
				id:'userPriAssignWinId',
				title:'用户分配特殊权限',
				layout:'form',
				items:[{
					height : 20,
					border:false,
					items:userNamePanel
					},mainPagePanel],
				width : Ext.getBody().getWidth() * 0.5,
				height : Ext.getBody().getHeight() * 0.8
	});
}
Ext.extend(com.bhtec.view.business.uum.user.UserAssignModOpt, 
	com.bhtec.view.util.CommonWidgets, {});