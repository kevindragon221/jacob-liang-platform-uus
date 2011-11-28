/**
 * 用户分配模块操作页面
 * @author lianglp
 * @version 1.0
 * @class com.bhtec.view.business.uum.user.UserAssignModOpt 
 * @date 2010-10-29
 */

Ext.namespace('com.bhtec.view.business.uum.role');
com.bhtec.view.business.uum.user.UserAssignModOpt = function(config) {
	var userName = config.userName||'';
	var userId = config.userId||0;
	var commWidgets = this;

	var userName = commWidgets.textField({
				id : 'defaultuserName',
				fieldLabel : '用户',
				width : 120,
				readOnly : true,
				value : userName
			});
	
	var headerPanel = commWidgets.panel({
				height : 30,
				autoWidth : true,
				items : [ {
							columnWidth : .5,
							baseCls : 'x-plain',
							bodyStyle : 'padding:5px 0 5px 5px',
							layout : 'form',
							items : [userName]
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
				url:'userAction!userAssignOptPrivilege.action',
				params:{
					userId:userId
				},
				callBack:function(returnData){
				    	var assignedModMenuList = returnData.assignedModMenuList;
				    	loadTree(assignedModMenuList,'rightTreePanelId');
				    	var unassignedModMenuList = returnData.unassignedModMenuList;
				    	loadTree(unassignedModMenuList,'leftTreePanelId');
				}
		}
		ajaxRequest(configFind);
	};
	
	/**
	 * 模块树 panel
	 */
	var leftTreePanel = commWidgets.treePanel({
			id:'leftTreePanelId',
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
		    rootVisible:true,
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
				id:'rightTreePanelId',
				autoWidth : true,
				border : true,
				height : Ext.getBody().getHeight() * 0.46,
				rootVisible:true,
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
		var fromtree = getExtCmpById('leftTreePanelId');
		var totree = getExtCmpById('rightTreePanelId');
		selUnselOpt(fromtree,totree);
	}
	
	var unselected = function() {
		var fromtree = getExtCmpById('rightTreePanelId');
		var totree = getExtCmpById('leftTreePanelId');
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

	new com.bhtec.view.util.ux.MultiSelect({
		leftTitle : '未选模块操作',
		rightTitle : '已选模块操作',
		winTitle : '用户分配模块操作',
		leftPanel : leftTreePanel,
		rightPanel : rightTreePanel,
		headerPanel : headerPanel,
		selected : selected,
		unselected : unselected,
		buttons : [commWidgets.saveButton({
					handler : function() {
							var rightTree = getExtCmpById('rightTreePanelId');
							var rootNode = rightTree.getRootNode();
							var optIdList;
							if(rootNode.childNodes.length != 0){
								optIdList = new Array();
								tradeFuncList(rootNode,optIdList);
							}
							var paramsPri = {
								userId : userId
							}
							if(optIdList){//判断是否含有模块操作
								Ext.applyIf(paramsPri,{modOptIdList : optIdList})
							}
							ajaxRequest({
								url : 'userAction!saveUserOptPrivilege.action',
								params : paramsPri,
								callBack : function(returnData) {
									showSucMesg({
										msg : '用户分配操作权限保存成功!',
										fn : function(confirm) {
											getExtCmpById('selectedToId').close();
										}
									});
								}
							});
					}
				}), commWidgets.closeButton({
							handler : function() {
								this.ownerCt.ownerCt.ownerCt.close();
							}
						})]
	});

}
Ext.extend(com.bhtec.view.business.uum.user.UserAssignModOpt, 
	com.bhtec.view.util.CommonWidgets, {});