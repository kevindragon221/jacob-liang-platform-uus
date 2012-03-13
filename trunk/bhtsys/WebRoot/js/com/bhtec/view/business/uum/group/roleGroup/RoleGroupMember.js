/**
 * 角色分配角色页面
 * @author lianglp
 * @version 2.0
 * @class com.bhtec.view.business.uum.group.roleGroup.RoleGroupMember 
 * @date 2011-1-28
 */

Ext.namespace('com.bhtec.view.business.uum.group.roleGroup');
com.bhtec.view.business.uum.group.roleGroup.RoleGroupMember = function(config) {
	var commWidgets = this;
	var moduleData = '';
	var uumGroupMembers = config.uumGroupMembers;//已选择角色成员
	var uumGroupMemberArr1 = new Array();
	if(uumGroupMembers){
		for (i = 0; i < uumGroupMembers.length; i++) {
			var uumGroupMemberArr2 = new Array(); 
			uumGroupMemberArr2.push(uumGroupMembers[i].memberResourceId);
			uumGroupMemberArr2.push(uumGroupMembers[i].memberResourceName);
			uumGroupMemberArr1.push(uumGroupMemberArr2);
		}
	}
	//角色查询
	var roleFind =  function(){
			if(getExtCmpValueById('roleGroupOrgId') == undefined){
				warningMesg({
						msg:'对不起，请先选择相应的机构'
					})
				return;
			}
			ajaxRequest({
				url:'roleGroupAction!findRoleByOrgId.action',
				params:{
					organId:getExtCmpValueById('roleGroupOrgId')
				},
				callBack:function(returnData){
					var leftmultiselectRoleGroup = getExtCmpById('leftmultiselectRoleGroup');
					if(leftmultiselectRoleGroup == undefined)return;
					store = leftmultiselectRoleGroup.store;
					store.removeAll();
					var records = [];
					var roleList = returnData.roleList;
					// 动态改变grid数据，gridData包含到json root 中
					for (i = 0; i < roleList.length; i++) {
						var r = new Ext.data.Record({});
						r.set("code", roleList[i].roleId);
						r.set("desc", roleList[i].roleName);
						records.push(r);
					}
					var newmultiselectOrgPost = leftmultiselectRoleGroup.store;
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
								getExtCmpById('roleGroupOrgId').setValue(node.id);			 
								getExtCmpById('roleGroupOrgName').setValue(node.text);
								roleFind();
							}
					   })
		});
	};
	
	//上级机构
	var upOrgName = commWidgets.triggerField({
			id : 'roleGroupOrgName',
			name : 'roleGroupOrgName',
			width:120,
			fieldLabel : '机构',
			value:moduleData.upOrgName||'',
			window:organTreeWin
	});
	
	var uporgId = new Ext.form.Hidden({
		id:'roleGroupOrgId'
	});
	
	
	var headerPanel = commWidgets.panel({
				height : 30,
				items : [{
							columnWidth : .3,
							baseCls : 'x-plain',
							bodyStyle : 'padding:5px 0 5px 5px',
							layout : 'form',
							items : [upOrgName]
						}]
			})
	
	var leftMultiSelPanel = {
							xtype : "multiselect",
							name : "leftmultiselectRoleGroup",
							id : "leftmultiselectRoleGroup",
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
							name : "rightmultiselectRoleGroup",
							id : "rightmultiselectRoleGroup",
							dataFields : ["code", "desc"],
							valueField : "code",
							displayField : "desc",
							border : false,
							height : 300,
							width : 287,
							data : uumGroupMemberArr1||[],
							tbar:[{text: '已选用户'}]
						};

	var selected = function(leftmultiselectRoleGroup,rightmultiselectRoleGroup,flag) {
		var indexs = leftmultiselectRoleGroup.view.getSelectedIndexes();
		if (indexs.length == 0) {
			warningMesg({
						msg : "请您选择相应的角色!"
					});
			return;
		}
		
		var records = new Array();
		if(flag == 'left'){
			for (var i = indexs.length - 1; i > -1; i--) {
				var leftRecorde = leftmultiselectRoleGroup.store.getAt(indexs[i]);//左侧记录
				var leftCodeValue = leftRecorde.get('code');//ID值
				var bol = false;
				for (var j = 0; j < rightmultiselectRoleGroup.store.getCount(); j++) {
					if (rightmultiselectRoleGroup.store.getAt(j).get('code') 
							== leftCodeValue) {
						bol = true;
						break;
					}
				}
				if(bol == false){
					records.push(leftRecorde);
					leftmultiselectRoleGroup.store.removeAt(indexs[i]);
				}
			}
		}else{
			for (var i = indexs.length - 1; i > -1; i--) {
				var leftRecorde = leftmultiselectRoleGroup.store.getAt(indexs[i]);//左侧记录
					leftmultiselectRoleGroup.store.removeAt(indexs[i]);
			}
		}
		rightmultiselectRoleGroup.store.add(records.reverse());
	}
	
	var multiPanel = new com.bhtec.view.util.ux.MultiSelect({
		leftPanel : leftMultiSelPanel,
		rightPanel : rightMultiSelPanel,
		headerPanel : headerPanel,
		selected : function(){
						selected(getExtCmpById('leftmultiselectRoleGroup'),
							getExtCmpById('rightmultiselectRoleGroup'),'left');
				   },
		unselected : function(){
						selected(getExtCmpById('rightmultiselectRoleGroup'),
						getExtCmpById('leftmultiselectRoleGroup'),'right');
			       }
	});
	return multiPanel;
}
Ext.extend(com.bhtec.view.business.uum.group.roleGroup.RoleGroupMember,
com.bhtec.view.util.CommonWidgets, {});