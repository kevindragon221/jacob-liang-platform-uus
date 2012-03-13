/**
 * 用户分配角色页面
 * @author lianglp
 * @version 2.0
 * @class com.bhtec.view.business.uum.group.generalGroup.GeneralGroupMember 
 * @date 2010-10-25
 */

Ext.namespace('com.bhtec.view.business.uum.group.generalGroup');
com.bhtec.view.business.uum.group.generalGroup.GeneralGroupMember = function(config) {
	var fieldNames = config.fieldNames;//描述域
	var commWidgets = this;
	var moduleData = '';
	var uumGroupMembers = config.uumGroupMembers;//已选择用户成员
	var uumGroupMemberArr1 = new Array();
	if(uumGroupMembers){
		for (i = 0; i < uumGroupMembers.length; i++) {
			var uumGroupMemberArr2 = new Array(); 
			uumGroupMemberArr2.push(uumGroupMembers[i].memberResourceId+'_'+uumGroupMembers[i].groupMemberType);
			uumGroupMemberArr2.push(uumGroupMembers[i].memberResourceName);
			uumGroupMemberArr1.push(uumGroupMemberArr2);
		}
	}
	//用户查询
	var userFind = function(){
			if(getExtCmpValueById('generalGroupOrgId') == undefined){
				warningMesg({
						msg:'对不起，请先选择相应的机构'
					})
				return;
			}
			var memberTypeValue = getExtCmpValueById('memberType').inputValue;
			ajaxRequest({
				url:'generalGroupAction!findOrganRoleRefByOrgRolId.action',
				params:{
					organId:getExtCmpValueById('generalGroupOrgId'),
					roleId:getExtCmpValueById('generalGroupRoleId')==''?'-1':getExtCmpValueById('generalGroupRoleId'),
					groupType:memberTypeValue
				},
				callBack:function(returnData){
					var leftmultiselectGeneralGroup = getExtCmpById('leftmultiselectGeneralGroup');
					if(leftmultiselectGeneralGroup == undefined)return;
					store = leftmultiselectGeneralGroup.store;
					store.removeAll();
					var records = [];
					var userList = returnData.userList;
					// 动态改变grid数据，gridData包含到json root 中
					for (i = 0; i < userList.length; i++) {
						var r = new Ext.data.Record({});
						r.set("code", userList[i].userId+'_'+memberTypeValue);
						r.set("desc", userList[i].userName);
						records.push(r);
					}
					var newmultiselectOrgPost = leftmultiselectGeneralGroup.store;
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
								getExtCmpById('generalGroupOrgId').setValue(node.id);			 
								getExtCmpById('generalGroupOrgName').setValue(node.text);
								getExtCmpById('generalGroupRoleId').setValue('');
								var memberTypeValue = getExtCmpValueById('memberType').inputValue;
								if(memberTypeValue == 'role')
									userFind();
							}
					   })
		});
	};
	
	//上级机构
	var upOrgName = commWidgets.triggerField({
			id : 'generalGroupOrgName',
			name : 'generalGroupOrgName',
			width:120,
			fieldLabel : fieldNames.organ,
			value:moduleData.upOrgName||'',
			window:organTreeWin
	});
	//角色
	var role = commWidgets.comboBox({
			id : "generalGroupRoleId",
			fieldLabel : fieldNames.role,
			width:120,
			store:new Ext.data.JsonStore({
					url:'generalGroupAction!findRoleListByOrganId.action',
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
					if(getExtCmpValueById('generalGroupOrgId') == undefined){
						warningMesg({
							msg:'对不起，请先选择相应的机构'
						})
						return;
					}
					var roleStore = combo.store;
					roleStore.removeAll();
					roleStore.on('beforeload', function(thiz,options) {
				    	var new_params = {
										organId:getExtCmpValueById('generalGroupOrgId')
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
		id:'generalGroupOrgId'
	});
	
	
	//成员类型
	var memberType = commWidgets.radio({
			id : "memberType",
			name : "memberType",
			value:'',
			fieldLabel : fieldNames.memberType,
			width:100,
			items:[
				{boxLabel: '用户', name: 'memberType', inputValue: 'user', 
					checked: true,
					listeners:{
            	 		'check':function(checkbox,checked){
								if(checked){
									getExtCmpById('generalGroupRoleId').setVisible(true);
								}
							}
            	 	}
            	},
            	{boxLabel: '角色', name: 'memberType', inputValue: 'role',
            	 listeners:{
            	 		'check':function(checkbox,checked){
								if(checked){
									getExtCmpById('generalGroupRoleId').setVisible(false);
								}
							}
            	 	}
            	 }
            ]
	});
	var headerPanel = commWidgets.panel({
				height : 30,
				items : [{
							columnWidth : .22,
							baseCls : 'x-plain',
							bodyStyle : 'padding:5px 0 5px 5px',
							layout : 'form',
							items : [memberType]
						},{
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
							name : "leftmultiselectGeneralGroup",
							id : "leftmultiselectGeneralGroup",
							dataFields : ["code", "desc"],
							valueField : "code",
							displayField : "desc",
							border : false,
							height : 300,
							width : 287,
							data : [],
							tbar:[{text: '待选用户'}]
						}	
	
	var rightMultiSelPanel ={
							xtype : "multiselect",
							name : "rightmultiselectGeneralGroup",
							id : "rightmultiselectGeneralGroup",
							dataFields : ["code", "desc"],
							valueField : "code",
							displayField : "desc",
							border : false,
							height : 300,
							width : 287,
							data : uumGroupMemberArr1||[],
							tbar:[{text: '已选用户'}]
						}
	var selected = function(leftmultiselectGeneralGroup,rightmultiselectGeneralGroup,flag) {
		var indexs = leftmultiselectGeneralGroup.view.getSelectedIndexes();
		if (indexs.length == 0) {
			warningMesg({
						msg : "请您选择相应的用户!"
					});
			return;
		}
		
		var records = new Array();
		if(flag == 'left'){
			for (var i = indexs.length - 1; i > -1; i--) {
				var leftRecorde = leftmultiselectGeneralGroup.store.getAt(indexs[i]);//左侧记录
				var leftCodeValue = leftRecorde.get('code');//ID值
				var bol = false;
				for (var j = 0; j < rightmultiselectGeneralGroup.store.getCount(); j++) {
					if (rightmultiselectGeneralGroup.store.getAt(j).get('code') 
							== leftCodeValue) {
						bol = true;
						break;
					}
				}
				if(bol == false){
					records.push(leftRecorde);
					leftmultiselectGeneralGroup.store.removeAt(indexs[i]);
				}
			}
		}else{
			for (var i = indexs.length - 1; i > -1; i--) {
				var leftRecorde = leftmultiselectGeneralGroup.store.getAt(indexs[i]);//左侧记录
					leftmultiselectGeneralGroup.store.removeAt(indexs[i]);
			}
		}
		rightmultiselectGeneralGroup.store.add(records.reverse());
	}
	
	var multiPanel = new com.bhtec.view.util.ux.MultiSelect({
		leftPanel : leftMultiSelPanel,
		rightPanel : rightMultiSelPanel,
		headerPanel : headerPanel,
		selected : function(){
						selected(getExtCmpById('leftmultiselectGeneralGroup'),
							getExtCmpById('rightmultiselectGeneralGroup'),'left');
				   },
		unselected : function(){
						selected(getExtCmpById('rightmultiselectGeneralGroup'),
						getExtCmpById('leftmultiselectGeneralGroup'),'right');
			       }
	});
	return multiPanel;
}
Ext.extend(com.bhtec.view.business.uum.group.generalGroup.GeneralGroupMember, com.bhtec.view.util.CommonWidgets, {});