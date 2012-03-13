/**
 * 用户代理
 * @author lianglp
 * @version 1.0
 * @class com.bhtec.view.business.commonused.useragent.UserAgent 
 * @date 2012-02-16
 */

Ext.namespace('com.bhtec.view.business.commonused.useragent');
com.bhtec.view.business.commonused.useragent.UserAgent = function(config) {
	var userId = config.userId||0;
	var commWidgets = this;
	var privilegeViewRoot = commWidgets.treeNode({
					id:'-1',
					expanded:true
				});
	var moduleTree = commWidgets.treeNode({
		id:'0',
		text:'模块树',
		expanded:true
	})
	privilegeViewRoot.appendChild(moduleTree);
	
	var generateTree = function(rootNode){
		var firstMenuList = firstMenu;//一级list
		if (firstMenuList != '') {
			for (i = 0; i < firstMenuList.length; i++) {
				var moduleId = firstMenuList[i].moduleId;
				var modName = firstMenuList[i].modName;
				var modImgCls = firstMenuList[i].modImgCls;
				var firstNode = commWidgets.treeNode({
							id : moduleId,
							text : modName,
							checked: false,
							iconCls : modImgCls
						});
	
				var secondList = secondMenu[moduleId];// map取值,
				if (secondList && secondList != '') {// 二级菜单
					for (l = 0; l < secondList.length; l++) {
						var moduleIdSec = secondList[l].moduleId;
						var modNameSec = secondList[l].modName;
						var modImgClsSec = secondList[l].modImgCls;
	
						var secondNode = commWidgets.treeNode({
									id : moduleIdSec,
									text : modNameSec,
									checked: false,
									iconCls : modImgClsSec
								});
						var thirdList = thirdMap[moduleIdSec];
						if (thirdList && thirdList != '') {
							for (j = 0; j < thirdList.length; j++) {// 三级菜单
								var modIdThi = thirdList[j].moduleId;
								var modNameThi = thirdList[j].modName;
								var modImgClsThi = thirdList[j].modImgCls;
								var modEnid = thirdList[j].modEnId;
								var thirdNode = commWidgets.treeNode({
											id : modIdThi,
											iconCls : modImgClsThi,
											checked: false,
											text : modNameThi
										});
								var fourthdList = fourthModOpt[modEnid];
								if (fourthdList && fourthdList != '') {
									for (m = 0; m < fourthdList.length; m++) {// 四级菜单
										var modIdFour = fourthdList[m].moduleId;
										var modNameFour = fourthdList[m].modName;
										var modImgFour = fourthdList[m].modImgCls;
										var fourNode = commWidgets.treeNode({
												id : modIdFour,
												iconCls : modImgFour,
												checked: false,
												text : modNameFour
											});
										thirdNode.appendChild(fourNode);
									}
								}
								secondNode.appendChild(thirdNode);
							}
						}
						firstNode.appendChild(secondNode);
					}
				}
				rootNode.appendChild(firstNode);
			}
		}
	}
	//单选按钮事件
	var radioChangeEvent=function(inputValue){
		var userAgentModOptSelPanelId = getExtCmpById('userAgentModOptSelPanelId');
		if(inputValue == 'part'){
			userAgentModOptSelPanelId.setVisible(true);
			var leftTreeOptPanel = getExtCmpById('leftTreeOptPanel4UserId');
			var rootNode = leftTreeOptPanel.getRootNode();
			if(rootNode.hasChildNodes())return;
			generateTree(rootNode);
		}else{
			userAgentModOptSelPanelId.setVisible(false);
		}
	}
	
	var headerPanel = commWidgets.panel({
			height : 40,
			autoWidth : true,
			items : [{
						columnWidth : .9,
						baseCls : 'x-plain',
						bodyStyle : 'padding:5px 0 5px 5px',
						layout : 'form',
						items : [commWidgets.radio({
							fieldLabel : '代理范围',
							id:'userAgentSet4UserId',
							items:[
					            {boxLabel: '代理全部操作', name: 'agentRadio', inputValue: 'all', 
				                	 checked: true,
				                	 listeners:{
					                	check:function(thiz,checked){
					                		if(checked)
					                			radioChangeEvent(thiz.inputValue);
					                	}
					                }
				                },{
								boxLabel: '代理部分操作', name: 'agentRadio', inputValue: 'part',
								 checked: false,
				                	 listeners:{
					                	check:function(thiz,checked){
					                		if(checked)
					                			radioChangeEvent(thiz.inputValue);
					                	}
					            	 }
					            }]
						})]
					}]
		});
	
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
		    rootVisible:true,
			rootNode:commWidgets.treeNode({
				id:'0',
				text:'模块树',
				expanded:true
			})
	});
	
	/**
	 * 模块树 panel
	 */
	var rightTreePanel = commWidgets.treePanel({
				id:'rightTreeOptPanel4UserId',
				title : '已选操作',
				width : 230,
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
		winTitle : '用户代理设置',
		bodyStyle : 'padding-left:10px;',
		leftPanel : leftTreePanel,
		rightPanel : rightTreePanel,
		headerPanel : commWidgets.panel({hieght:0,hidden:true}),
		selected : selected,
		unselected : unselected,
		isShowWindow:false
		
	});
	
	var authorization = function(){
		if(getExtCmpValueById('beginDate') == ''){
			warningMesg({
				msg:'请选择开始时间!'
			})
			return;
		}
		if(getExtCmpValueById('endDate') == ''){
			warningMesg({
				msg:'请选择结束时间!'
			})
			return;
		}
		var optPrivilegeScope = getExtCmpValueById('userAgentSet4UserId');
		if(optPrivilegeScope == null){
			warningMesg({
				msg:'请选择权限范围!'
			})
			return;
		}
		var paramsPri = {
			agentUserId : getExtCmpValueById('userId'),
			beginDate:getExtCmpValueById('beginDate'),
			endDate:getExtCmpValueById('endDate'),
			reason:getExtCmpValueById('reason')
		}
		if(optPrivilegeScope.inputValue == 'part'){//部分操作权限
			var rightTree = getExtCmpById('rightTreeOptPanel4UserId');
			var rootNode = rightTree.getRootNode();
			var optIdList ;
			if(rootNode.childNodes.length != 0){
				optIdList = new Array();
				tradeFuncList(rootNode,optIdList);
			}
			
			if(optIdList){//判断是否含有模块操作
				Ext.applyIf(paramsPri,{modOptIdList : optIdList})
			}
		}else{
			var optIdList = new Array();
			optIdList.push('all')
			Ext.applyIf(paramsPri,{modOptIdList : optIdList})
		}
		
		ajaxRequest({
			url : 'userAgentAction!saveUserAgent.action',
			params : paramsPri,
			callBack : function(returnData) {
				var configCb = {
					msg : '授权成功!',
					fn : function(confirm) {
						getExtCmpById('userId').setValue('');
						getExtCmpById('beginDate').setValue('');
						getExtCmpById('endDate').setValue('');
						getExtCmpById('reason').setValue('');
					}
				}
				showSucMesg(configCb);
			}
		});
	}
	
	var agentAskReply = commWidgets.panel({
			autoHeight:true,
			width : Ext.getBody().getWidth() * 0.43,
			items : [{
						columnWidth : 1,
						layout : 'form',
						border:false,
						items : [commWidgets.comboBox({
							id:'userId',
							fieldLabel:'授权代理人',
							mode : 'remote',
							hideTrigger : true,
							width:140,
							editable:true,
							minChars : 1,
							store : new Ext.data.JsonStore({
								url : 'userAction!findAllUumUserByUserNamePy.action',
								root : 'userList',
								fields : ["userId", "userName"]
							}),
							displayField : 'userName',
							valueField : 'userId',
							typeAhead : false,
							loadingText : '查询中...',
							forceSelection : true,
							hideTrigger : true,
							queryParam : 'userNamePy',
							allowBlank:false,
							listeners : {
								change : function(thiz){
									var store = thiz.store;
									store.reload();
								}
							}
						})]
					},{
						columnWidth : .5,
						layout : 'form',
						border:false,
						items : [{
									id : "beginDate",
									name : "beginDate",
									fieldLabel : '开始时间',
									format:'Y-m-d H:i:s',
									value:'',
									allowBlank:false,
									xtype:'datetimefield'
								}]
					},{
						columnWidth : .5,
						layout : 'form',
						border:false,
						items : [{
									id : "endDate",
									name : "endDate",
									fieldLabel : '结束时间',
									format:'Y-m-d h:i:s',
									value:'',
									allowBlank:false,
									xtype:'datetimefield'
								}]
					},{
						columnWidth : 1,
						layout : 'form',
						border:false,
						items : [commWidgets.textArea({id:'reason',height:30,fieldLabel:'备注说明'})]
					},{
						columnWidth : 1,
						layout : 'form',
						border:false,
						items : headerPanel
					},{
						columnWidth : 1,
		        		id:'userAgentModOptSelPanelId',
		        		border:false,
		        		hidden:true,
		        		items:multiPanel
	        		}],
				buttonAlign : 'center',
			        	buttons : [{
			        		xtype:'button',
			        		text:'授权',
			        		iconCls:'emailgo',
			        		handler:function(){
			        			if(getExtCmpValueById('userId') == undefined
			        					|| getExtCmpValueById('userId') == ''){
			        				warningMesg({
			        					msg:'对不起,代理人不存在!'
			        				})
			        				return;
			        			}else{
			        				ajaxRequest({
										url : 'userAgentAction!hasAgentUser.action',
										params : {
											agentUserId : getExtCmpValueById('userId')
										},
										callBack : function(returnData) {
											var hasUserAgentBol = returnData.hasUserAgentBol;
											if(hasUserAgentBol == true){
												warningMesg({
													msg:'对不起,此用户已被授权!'
												})
												return;
											}else{
												authorization();
											}
										}
									});
			        			}
			        			
			        		}
			        	},
						commWidgets.closeButton({
								handler : function() {
									getExtCmpById('userAgentWinId').close();
								}
							})]
	})
	
	var agentAskListPanel = commWidgets.gridPanel({
			id:'userAgentInfoGridId',
			title	:	'代理信息列表',
			autoHeight 	: 	true, 
   			width : Ext.getBody().getWidth() * 0.46,
   			autoScroll:true,
   			stripeRows : true,
   			viewConfig : new Ext.grid.GridView({
							autoFill : true
						}),
			colums  :   [{
							dataIndex : 'agentId',
							hidden: true 
						},{
							header : '代理人',
							dataIndex : 'userName',
							width : 80,
							sortable: true 
						},{
							header : '开始时间',
							dataIndex : 'beginDate',
							width : basicConstant.GRID_COL_WIDTH+50,
							sortable: true 
						},{
							header : '结束时间',
							dataIndex : 'endDate',
							width : basicConstant.GRID_COL_WIDTH+50,
							sortable: true 
						},{
							header : '备注说明',
							dataIndex : 'reason',
							width : 150,
							renderer:function(value){
								return '<span ext:qtip="'+value+'">'+value+'</span>';
							},
							sortable: true 
						}],
			border  :   true,
			gridStore:  new Ext.data.JsonStore({
							fields : ['agentId','userName','agentUserId', 'beginDate','endDate','reason'],
							autoLoad : true,
							totalProperty : 'count',
							root : 'userAgentList',
							url : 'userAgentAction!findUserAgentByCon.action'
						}),
			columnLines : true,
        	buttons : [{
						xtype:'button',
						iconCls:'userdelete',
						text:'取消代理',
						handler:function(){
							var count = getExtCmpById('userAgentInfoGridId').getSelectionModel().getSelections().length;
							if(count == 0){
								warningMesg({
									msg:'请选择要取消的代理!'
								});
								return;
							}else{
								var agentList = getExtCmpById('userAgentInfoGridId').getSelectionModel().getSelections();
								var agentIdArray = new Array();
								for(var i=0;i<agentList.length;i++){
									agentIdArray.push(agentList[i].data.agentId);
								}
								ajaxRequest({
									url : 'userAgentAction!deleteUserAgent.action',
									params : {
										agentIdArray : agentIdArray
									},
									callBack : function(returnData) {
										var configCb = {
											msg : '取消代理成功!',
											fn : function(confirm) {
												if ('ok' == confirm) {
													refreshGridList('userAgentInfoGridId');
												}
											}
										}
										showSucMesg(configCb);
									}
								});
							}
						}
					},
					commWidgets.closeButton({
							handler : function() {
								getExtCmpById('userAgentWinId').close();
							}
						})
				],
			listeners : {
		    		cellclick:function(grid, rowIndex, columnIndex, e) {
				        var record = grid.getStore().getAt(rowIndex);  // Get the Record
				        ajaxRequest({
							url : 'userAgentAction!userAssignAgentOptPrivilege.action',
							params : {agentId:record.data.agentId},
							callBack : function(returnData) {
								var moduleList = returnData.agentModuleMenuList;
								var privilegeViewPanel = getExtCmpById('privilegeViewId');
								var rootNode = privilegeViewPanel.getRootNode();
								var delNode = privilegeViewPanel.getNodeById(0);
								delNode.parentNode.removeChild(delNode);
								rootNode.appendChild(commWidgets.treeNode({
														id:'0',
														text:'模块树',
														expanded:true
													}));
								if(moduleList == null){//全部权限
									privilegeViewPanel.setTitle('代理人:'+record.data.userName+'(代理全部权限)');
									generateTree(privilegeViewPanel.getNodeById(0));//生成树
									return;
								}else{
									privilegeViewPanel.setTitle('代理人:'+record.data.userName+'(代理部分权限)');
								}
								var loadTree = function(moduleList) {
									var treeArr = new Array();
									for (var i = 0; i < moduleList.length; i++) {
										var cnode = commWidgets.treeNode({
													id : moduleList[i].moduleId,
													text : moduleList[i].modName,
													iconCls : moduleList[i].modImgCls,
													checked: false
												});
										var parentNode = privilegeViewPanel.getNodeById(moduleList[i].upModId);
										if (parentNode == null || parentNode == "undefine") {
											treeArr.push(moduleList[i]);
										} else {
											parentNode.appendChild(cnode);
										}
							
									}
									if (treeArr.length > 0)
										loadTree(treeArr);
								}
								loadTree(moduleList);
								rootNode.expand();
							}
						});
				    }
				}
	});
	
	var privilegeView = commWidgets.treePanel({
			id:'privilegeViewId',
			title : '操作',
			width : Ext.getBody().getWidth() * 0.46,
			border : true,
			height : Ext.getBody().getHeight() * 0.46,
		    rootVisible:false,
			rootNode:privilegeViewRoot
	});
	var mainPagePanel = {
		xtype : 'tabpanel',
		enableTabScroll:true,
		activeTab:0,
		frame:false,
		border: true,
		width : Ext.getBody().getWidth() * 0.47,
		height : Ext.getBody().getHeight() * 0.8,
        items:[{
        		title: '代理授权',
        		bodyStyle : 'padding-left:10px;padding-top:10px;',
	        	items:[agentAskReply]
	         },{
	        	title: '代理取消',
	        	items:[{
	        		bodyStyle : 'padding-left:10px;',
	        		border:false,
	        		items:[agentAskListPanel,privilegeView]
	        	}]
        	}]
	}
	
	commWidgets.window({
				id:'userAgentWinId',
				title:'用户代理',
				layout:'form',
				items:[mainPagePanel],
				width : Ext.getBody().getWidth() * 0.5,
				height : Ext.getBody().getHeight() * 0.87
	});
}
Ext.extend(com.bhtec.view.business.commonused.useragent.UserAgent, 
	com.bhtec.view.util.CommonWidgets, {});