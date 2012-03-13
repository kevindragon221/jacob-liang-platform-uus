/**
 * 用户分配角色页面
 * @author lianglp
 * @version 2.0
 * @class com.bhtec.view.business.uum.user.UserAssignRole 
 * @date 2010-10-25
 */

Ext.namespace('com.bhtec.view.business.uum.group.userGroup');
com.bhtec.view.business.uum.group.userGroup.UserGroupMember = function(config) {
	var fieldNames = config.fieldNames;//描述域
	var commWidgets = this;
	var moduleData = '';
	var uumGroupMembers = config.uumGroupMembers;//已选择用户成员
	var uumGroupMemberArr1 = new Array();
	if(uumGroupMembers){
		for (i = 0; i < uumGroupMembers.length; i++) {
			var uumGroupMemberArr2 = new Array(); 
			uumGroupMemberArr2.push(uumGroupMembers[i].memberResourceId);
			uumGroupMemberArr2.push(uumGroupMembers[i].memberResourceName);
			uumGroupMemberArr1.push(uumGroupMemberArr2);
		}
	}
	//用户查询
	var userFind = function(){
			if(getExtCmpValueById('userGroupOrgId') == undefined){
				warningMesg({
						msg:'对不起，请先选择相应的机构'
					})
				return;
			}
			ajaxRequest({
				url:'userGroupAction!findOrganRoleRefByOrgRolId.action',
				params:{
					organId:getExtCmpValueById('userGroupOrgId'),
					roleId:getExtCmpValueById('userGroupRoleId')==''?'-1':getExtCmpValueById('userGroupRoleId')
				},
				callBack:function(returnData){
					var leftmultiselectUserGroup = getExtCmpById('leftmultiselectUserGroup');
					if(leftmultiselectUserGroup == undefined)return;
					store = leftmultiselectUserGroup.store;
					store.removeAll();
					var records = [];
					var userList = returnData.userList;
					// 动态改变grid数据，gridData包含到json root 中
					for (i = 0; i < userList.length; i++) {
						var r = new Ext.data.Record({});
						r.set("code", userList[i].userId);
						r.set("desc", userList[i].userName);
						records.push(r);
					}
					var newmultiselectOrgPost = leftmultiselectUserGroup.store;
					newmultiselectOrgPost.add(records);
				}
			});
	}
	
	//机构树
	var organTreeWin = function(){
		commWidgets.treeWindow({
			title 	 : '机构树',
			items	 : commWidgets.asyncTreePanel({
							rootText:basicConstant.ORGAN_ROOT,
							rootVisible:true,
							url:'organAction!findNextLevelChildNodes.action',
							clickNode:function(node, e){
								getExtCmpById('userGroupOrgId').setValue(node.id);			 
								getExtCmpById('userGroupOrgName').setValue(node.text);
								getExtCmpById('userGroupRoleId').setValue('');
							}
					   })
		});
	};
	
	//上级机构
	var upOrgName = commWidgets.triggerField({
			id : 'userGroupOrgName',
			name : 'userGroupOrgName',
			width:120,
			fieldLabel : fieldNames.organ,
			value:moduleData.upOrgName||'',
			window:organTreeWin
	});
	//角色
	var role = commWidgets.comboBox({
			id : "userGroupRoleId",
			fieldLabel : fieldNames.role,
			width:120,
			store:new Ext.data.JsonStore({
					url:'userGroupAction!findRoleListByOrganId.action',
					totalProperty:'count',
					root 	: 'roleList',
					fields 	: ['roleId','roleName']
				}),
			mode:'remote',
			valueField 	: 'roleId',
			displayField: 'roleName',
			pageSize:10,
			listWidth:280,
			editable:false,
			listeners:{
				focus :function(combo, record, index){
					if(getExtCmpValueById('userGroupOrgId') == undefined){
						warningMesg({
							msg:'对不起，请先选择相应的机构'
						})
						return;
					}
					var roleStore = combo.store;
					roleStore.removeAll();
					roleStore.on('beforeload', function(thiz,options) {
				    	var new_params = {
										organId:getExtCmpValueById('userGroupOrgId')
									}; 
						Ext.apply(options.params,new_params); 
					});	
					roleStore.reload();
					combo.show();
				},
				select:function(){
					userFind();
				}
			}
	});
	
	var uporgId = new Ext.form.Hidden({
		id:'userGroupOrgId'
	});
	
	var headerPanel = commWidgets.panel({
				height : 30,
				items : [{
							columnWidth : .22,
							baseCls : 'x-plain',
							bodyStyle : 'padding:5px 0 5px 5px',
							layout : 'form',
							items : [upOrgName]
						},{
							columnWidth : .22,
							baseCls : 'x-plain',
							bodyStyle : 'padding:5px 0 5px 5px',
							layout : 'form',
							items : [role]
						}]
			})
	
	var leftMultiSelPanel = {
							xtype : "multiselect",
							name : "leftmultiselectUserGroup",
							id : "leftmultiselectUserGroup",
							dataFields : ["code", "desc"],
							valueField : "code",
							displayField : "desc",
							border : false,
							height : 300,
							width : 287,
							data : [],
							tbar:[{text: '待选用户'}]
						};
			
	var rightMultiSelPanel = {
							xtype : "multiselect",
							name : "rightmultiselectUserGroup",
							id : "rightmultiselectUserGroup",
							dataFields : ["code", "desc"],
							valueField : "code",
							displayField : "desc",
							border : false,
							height : 300,
							width : 287,
							data : uumGroupMemberArr1||[],
							tbar:[{text: '已选用户'}]
						};

	var selected = function(leftmultiselectUserGroup,rightmultiselectUserGroup,flag) {
		var indexs = leftmultiselectUserGroup.view.getSelectedIndexes();
		if (indexs.length == 0) {
			warningMesg({
						msg : "请您选择相应的用户!"
					});
			return;
		}
		
		var records = new Array();
		if(flag == 'left'){
			for (var i = indexs.length - 1; i > -1; i--) {
				var leftRecorde = leftmultiselectUserGroup.store.getAt(indexs[i]);//左侧记录
				var leftCodeValue = leftRecorde.get('code');//ID值
				var bol = false;
				for (var j = 0; j < rightmultiselectUserGroup.store.getCount(); j++) {
					if (rightmultiselectUserGroup.store.getAt(j).get('code') 
							== leftCodeValue) {
						bol = true;
						break;
					}
				}
				if(bol == false){
					records.push(leftRecorde);
					leftmultiselectUserGroup.store.removeAt(indexs[i]);
				}
			}
		}else{
			for (var i = indexs.length - 1; i > -1; i--) {
				var leftRecorde = leftmultiselectUserGroup.store.getAt(indexs[i]);//左侧记录
					leftmultiselectUserGroup.store.removeAt(indexs[i]);
			}
		}
		rightmultiselectUserGroup.store.add(records.reverse());
	}
	
	var multiPanel = new com.bhtec.view.util.ux.MultiSelect({
		leftPanel : leftMultiSelPanel,
		rightPanel : rightMultiSelPanel,
		headerPanel : headerPanel,
		selected : function(){
						selected(getExtCmpById('leftmultiselectUserGroup'),
							getExtCmpById('rightmultiselectUserGroup'),'left');
				   },
		unselected : function(){
						selected(getExtCmpById('rightmultiselectUserGroup'),
						getExtCmpById('leftmultiselectUserGroup'),'right');
			       }
	});
	return multiPanel;
}
Ext.extend(com.bhtec.view.business.uum.group.userGroup.UserGroupMember, com.bhtec.view.util.CommonWidgets, {});