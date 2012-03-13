/**
 * 用户分配行权限
 * @author lianglp
 * @version 1.0
 * @class com.bhtec.view.business.uum.user.UserAssignRowPrivilege 
 * @date 2012-02-13
 */

Ext.namespace('com.bhtec.view.business.uum.user');
com.bhtec.view.business.uum.user.UserAssignRowPrivilege = function(config) {
	var userId = config.userId||0;
	var commWidgets = this;
	//初始化数据
	var records = new Array();
	var treeUrl = 'organAction!findNextLevelChildNodesWithCheckbox.action';
	var privilegeTypeInit = 'org';
	var leftPanelTitle = '未选机构';
	var rightPanelTitle = '已选机构';
	var userNameFieldIsHidden = true;
	var userNameButtonIsHidden = true;
	
	var radioChangeEvent = function(privilegeType,initBol){
		var syncUrl = 'userAction!findSeledRowPrivilegeByUserId.action';
		var returnData = syncAjaxReqDecode(syncUrl,'userId='+userId);
		var privilegeTypeSelected = returnData.privilegeScope;
		var userList = returnData.userList;
		
		//初始化
		if(privilegeType == '' && initBol == true){
			if(privilegeTypeSelected != ''){
				privilegeTypeInit = privilegeTypeSelected;
				if(privilegeTypeInit == 'rol'){
					treeUrl = 'userAction!findNextLevelChildNodes.action?isCheckbox=Y';
					leftPanelTitle = '未选角色';
					rightPanelTitle = '已选角色';
				}else if(privilegeTypeInit == 'usr'){
					leftPanelTitle = '未选用户';
					rightPanelTitle = '已选用户';
					userNameFieldIsHidden = false;
					userNameButtonIsHidden = false;
				}
				for (var i = 0; i < userList.length; i++) {
					var recordArray = new Array();
					recordArray.push(userList[i][0]);
					recordArray.push(userList[i][1]);
					records.push(recordArray);
				}
			}
		}else{
			var dataUrl = '';
			var leftTreePanelRowPriPanel = getExtCmpById('leftTreePanelRowPriId');
			var multiselectRowPriPanel = getExtCmpById('multiselectRowPriPanelId');
			var queryUserName = getExtCmpById('queryUserNameRowPriId');
			if(queryUserName){
				queryUserName.setVisible(false);
			}
			var userQuery = getExtCmpById('userQueryRowPriId');
			if(userQuery){
				userQuery.setVisible(false);
			}
			leftTreePanelRowPriPanel.setRootNode(commWidgets.asyncTreeNode({
													id:0,
													text:basicConstant.ORGAN_ROOT,
													checked:false,
													hidden:false
											}));
			if(privilegeType == 'org'){
				dataUrl = 'organAction!findNextLevelChildNodesWithCheckbox.action';
				leftTreePanelRowPriPanel.setTitle('未选机构');
				multiselectRowPriPanel.setTitle('已选机构');
			}else if(privilegeType == 'rol'){
				dataUrl = 'userAction!findNextLevelChildNodes.action?isCheckbox=Y';
				leftTreePanelRowPriPanel.setTitle('未选角色');
				multiselectRowPriPanel.setTitle('已选角色');
			}else if(privilegeType == 'usr'){
				leftTreePanelRowPriPanel.setTitle('未选用户');
				multiselectRowPriPanel.setTitle('已选用户');
				queryUserName.setVisible(true);
				userQuery.setVisible(true);
				leftTreePanelRowPriPanel.setRootNode(commWidgets.treeNode({
													id:-1,
													text:'用户列表',
													hidden:false,
													expanded : true
											}));
			}
			if(privilegeType != 'usr'){//非用户重新Load
				var loader = new  Ext.tree.TreeLoader({   
		          	dataUrl:dataUrl
		        });
		        loader.on('beforeload',function(treeLoader,node){
							loader.baseParams.modViewRecId = node.attributes.id;
				})
		        leftTreePanelRowPriPanel.loader = loader;
		        leftTreePanelRowPriPanel.root.reload();
			}
	        leftTreePanelRowPriPanel.doLayout();
	        var multiselectRowPri = getExtCmpById('multiselectRowPriId');
		    multiselectRowPri.store.removeAll();
	        //显示对应列表
			if(privilegeType == privilegeTypeSelected){
				 var radioRecords = new Array();
		        for (var i = 0; i < userList.length; i++) {
						var r = new Ext.data.Record({});
						r.set("code", userList[i][0]);
						r.set("desc", userList[i][1]);
						radioRecords.push(r);
				}
		        
				var newmul = multiselectRowPri.store;
				newmul.add(radioRecords);
			}
		}
		
	}
	
	radioChangeEvent('',true);
	var userQuery = function(){
				ajaxRequest({
						url : 'userAction!findUumUserByUserName.action',
						params : {userName:getExtCmpValueById('queryUserNameRowPriId')},
						callBack : function(returnData) {
							var userList = returnData.userList;
							var leftTreePanelRowPri = getExtCmpById('leftTreePanelRowPriId');
							var rootNode = leftTreePanelRowPri.getRootNode();
							var childNotes = rootNode.childNodes;
							for(var i=childNotes.length-1;i>-1;i--){
								rootNode.removeChild(childNotes[i]);
							}
							for(var i=0;i<userList.length;i++){
								var user = userList[i];
								rootNode.appendChild(commWidgets.treeNode({
										id:user[0],
										text:user[1],
										checked:false
								}));
							}
							rootNode.expand(true);
						}
					});
	}
	var userName = commWidgets.textField({
				id : 'queryUserNameRowPriId',
				hidden:userNameFieldIsHidden,
				fieldLabel : '用户名称',
				width : 120,
				enableKeyEvents : true,
				listeners:{
					'keyup' :function(thiz,event){
						if(event.getKey() == 13){
							userQuery();
						}
					}
				}
			});
	
	var headerPanel = commWidgets.panel({
			id:'rowPriHeaderPanelId',
			height : 70,
			autoWidth : true,
			items : [{
					columnWidth : .9,
					baseCls : 'x-plain',
					bodyStyle : 'padding:5px 0 5px 5px',
					layout : 'form',
					items:[commWidgets.radio({
							fieldLabel : '行权限类别',
							id:'rowPrivilege4UserId',
							items:[
		                	{boxLabel: '机构', name: 'rolPri', inputValue: 'org', 
			                	 checked: (privilegeTypeInit=='org'?true:false),
			                	 listeners:{
				                	check:function(thiz,checked){
				                		if(checked)
				                			radioChangeEvent(thiz.inputValue);
				                	}
				                }
		                	},
							{boxLabel: '角色', name: 'rolPri', inputValue: 'rol',
								 checked: (privilegeTypeInit=='rol'?true:false),
			                	 listeners:{
				                	check:function(thiz,checked){
				                		if(checked)
				                			radioChangeEvent(thiz.inputValue);
				                	}
				                 }
			             	},{boxLabel: '用户', name: 'rolPri', inputValue: 'usr',
								 checked: (privilegeTypeInit=='usr'?true:false),
			                	 listeners:{
				                	check:function(thiz,checked){
				                		if(checked)
				                			radioChangeEvent(thiz.inputValue);
				                	}
				             	 }
			              	}]
					})]
				},{
					columnWidth : .5,
					baseCls : 'x-plain',
					bodyStyle : 'padding:5px 0 5px 5px',
					layout : 'form',
					items:[userName]
				},{
					columnWidth : .4,
					baseCls : 'x-plain',
					bodyStyle : 'padding:5px 0 5px 5px',
					layout : 'form',
					items:[{
						xtype:'button',
						id:'userQueryRowPriId',
						hidden:userNameButtonIsHidden,
						text:'查询',
						iconCls:'table_find',
						handler:userQuery
					}]
				}]
		})
	
	
	var leftTreePanel = commWidgets.asyncTreePanel({
				id : 'leftTreePanelRowPriId',
				title : leftPanelTitle,
				rootText : basicConstant.ORGAN_ROOT,
				autoWidth : true,
				border : true,
				height : Ext.getBody().getHeight() * 0.46,
				url : treeUrl,
				isChecked:true,
				rootVisible : true
			});
	
	var multiSelPanel = commWidgets.panel({
							title:rightPanelTitle,
							id : "multiselectRowPriPanelId",
							height : Ext.getBody().getHeight() * 0.45 + 5,
							width : 250,
							items:[{
								xtype : "multiselect",
								name : "orgMultiselect",
								id : "multiselectRowPriId",
								dataFields : ["code", "desc"],
								valueField : "code",
								displayField : "desc",
								border : true,
								height : Ext.getBody().getHeight() * 0.42,
								width : 250,
								data : records
							}]
		});
	
	var selected = function() {
		var mul = getExtCmpById('multiselectRowPriId');
		var lefttree = getExtCmpById('leftTreePanelRowPriId');
		var privilegeType = getExtCmpValueById('rowPrivilege4UserId').inputValue;
		if (lefttree.getChecked().length > 0) {
			var checkedList = lefttree.getChecked();
			var records = [];
			if(privilegeType == 'rol'){
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
			}else{
				if(privilegeType == 'usr' && (checkedList[0].id == 0)){
					warningMesg({
						msg : '请查询相应用户!'
					})
					return;
				}
				for (var i = 0; i < checkedList.length; i++) {
					var bol = false;
					var singleNode = checkedList[i];
					var orgRoleId = singleNode.id;
					for (var j = 0; j < mul.store.getCount(); j++) {
						if (mul.store.getAt(j).get("code") == orgRoleId) {
							bol = true;
							break;
						}
					}
					if (bol == false) {
						var r = new Ext.data.Record({});
						r.set("code", orgRoleId);
						r.set("desc",(singleNode.text));
						records.push(r);
					}
				}
			}
			mul.store.add(records);
		} else {
			warningMesg({
						msg : '请选择左边列表!'
					})
		}
	}

	var unselected = function() {
		var mul = getExtCmpById('multiselectRowPriId');
		var privilegeType = getExtCmpValueById('rowPrivilege4UserId').inputValue;
		var indexs = mul.view.getSelectedIndexes();
		if (indexs.length == 0) {
			warningMesg({
						msg : "请选择右边列表!"
					});
			return;
		}
		var lefttree = getExtCmpById('leftTreePanelRowPriId');
		var selectValues = mul.getValue();
		var values = selectValues.split(",");
		for (j = 0; j < values.length; j++) {
			var nodet = '';
			if(privilegeType == 'rol'){
				nodet = lefttree.getNodeById(values[j] + '_2');
			}else{
				nodet = lefttree.getNodeById(values[j]);
			}
			if (nodet) {
				nodet.attributes.checked = false;
				nodet.ui.toggleCheck(false);
			}
		}

		for (var i = indexs.length - 1; i > -1; i--) {
			mul.store.removeAt(indexs[i]);
		}
	}
	
	var multiPanel = new com.bhtec.view.util.ux.MultiSelect({
		leftPanel : leftTreePanel,
		rightPanel : multiSelPanel,
		headerPanel : headerPanel,
		selected : selected,
		unselected : unselected,
		isShowWindow:false,
		buttons : [commWidgets.saveButton({
					handler : function(b) {
							var rowPrivilege4User = getExtCmpValueById('rowPrivilege4UserId').inputValue;
							if(rowPrivilege4User == undefined){
								warningMesg({
									msg:'请选择行权限类别'
								})
								return;
							}
							var optIdList = new Array();
							var mutiSel = getExtCmpById('multiselectRowPriId');
							for (var j = 0; j < mutiSel.store.getCount(); j++) {
								var orgRoleId = mutiSel.store.getAt(j).get("code");
								optIdList.push(orgRoleId);
							}
							
							var paramsPri = {
								userId : userId,
								privilegeScope:getExtCmpValueById('rowPrivilege4UserId').inputValue
							}
							if(optIdList.length > 0){//判断是否含有模块操作
								Ext.applyIf(paramsPri,{modOptIdList : optIdList})
							}
							b.setDisabled(true);
							ajaxRequest({
								url : 'userAction!saveUserRowPrivilege.action',
								params : paramsPri,
								callBack : function(returnData) {
									askMesg({
										msg : '行权限保存成功,您是否继续分配权限?',
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
	
	return multiPanel;
}
Ext.extend(com.bhtec.view.business.uum.user.UserAssignRowPrivilege, 
	com.bhtec.view.util.CommonWidgets, {});