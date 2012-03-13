/**
 * 角色切换
 * @author lianglp
 * @version 2.0
 * @class com.bhtec.view.business.uum.role.RoleChange
 * @date 2010-11-18
 */
Ext.namespace('com.bhtec.view.business.uum.role.RoleChange');
com.bhtec.view.business.uum.role.RoleChange = function(config){
	/**
     *打开主页面 
     */
    var openMainpageWindow = function(userCode_cookie){
		var screenWidth = screen.availWidth;
        var screenHeight = screen.availHeight;
        var variable  = 'r='+multiRoleFlag+'&'+encode64(userCode_cookie)+'#'+userCode_cookie;
		window.open(basicConstant.MAIN_PAGE+'?'+variable, '',"directories=no,status=no,toobar=no,location=no,width="+screenWidth+",height="+screenHeight+",resizable=0,scrollbars=no,left=0,top=0");
		opener=null; 
		self.close();
	}
    /**
     * 用户角色选择
     */
     var roleSelectedWindow = function(){
     	var uumRoleUserRefList = config.uumRoleUserRefList;
		var roleList = new Array();
	   if(uumRoleUserRefList.length > 1){
	   		for(i=0;i<uumRoleUserRefList.length;i++){
	   			var roleUser = uumRoleUserRefList[i];
	   			var orgRoleName = '';
	   			var checked = false;
	   			if(roleUser.isDefault == 'Y'){
	   				orgRoleName = roleUser.organName+'-'+roleUser.roleName+'(默认)';
	   				checked = true;
	   			}else{
	   				orgRoleName = roleUser.organName+'-'+roleUser.roleName;
	   			}
	   			var orgRoleId = roleUser.organId+'-'+roleUser.roleId;
	   			var role = {boxLabel: orgRoleName, name: 'orgRole', inputValue: orgRoleId, checked:checked}
	   			roleList.push(role);
	   		}
	   }else{
	   		warningMesg({
	   			msg:'您目前只有一个角色,无需切换.'
	   		});
	   		return;
	   }
	   var rolWin = new Ext.Window({
				id    : 'roleSelected',
				title : '您拥有以下角色',
				resizable : false,
				width : 300,
				height: 200,
				modal : true,
				autoScroll:true,
				items : [{
				            xtype: 'radiogroup',
				            itemCls: 'x-check-group-alt',
				            id:'rolelistId',
            				columns: 1,
				            items: roleList
						}],
				border 	  : false,
				bodyStyle : 'padding-left:80px;padding-top:10px;'+basicConstant.PAGE_BACKGROUND,
				layout	  : 'fit',
				buttonAlign:'center',
				buttons   : [{
								xtype:'button',
								text:'确定',
								iconCls : 'login',
								handler:function(){
									var checkedRoleRadio = getExtCmpById('rolelistId').getValue();
									var orgRoleName = checkedRoleRadio.boxLabel;
									var organName = orgRoleName.substr(0,orgRoleName.indexOf('-'));
									var roleName = orgRoleName.substr(orgRoleName.indexOf('-')+1);
									var orgRoleId = checkedRoleRadio.inputValue;
									var organId = orgRoleId.substr(0,orgRoleId.indexOf('-'));
									var roleId = orgRoleId.substr(orgRoleId.indexOf('-')+1);
									ajaxRequest({
										url : 'loginAction!changeRole.action',
										noMask : true,
										params : {
											roleId:roleId,
											roleName:roleName.replace('(默认)',''),
											organId:organId,
											organName:organName
										},
										callBack : function(returnData) {
											Ext.getCmp('roleSelected').close();
											openMainpageWindow(userCode_cookie);
										}
									});
								}
							},{
								xtype:'button',
								text:'关闭',
								iconCls : 'close',
								handler:function(){
									this.ownerCt.ownerCt.close();
								}
							}]
			});
			rolWin.show();
	}	
	return {
		roleSelectedWindow:roleSelectedWindow
	}
}