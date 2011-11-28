/**
 * 用户登录页面
 * @author lianglp
 * @version 1.0
 * @class com.bhtec.view.login
 * @date 2010-05-26
 * @type String
 */

Ext.namespace('com.bhtec.view');
com.bhtec.view.Login = function(config) {
	var topPanel = {
		xtype : 'panel',
		id : "topPanel",
		bodyStyle : "padding:20px;background-image: url();",
		frame : true,
		border : false,
		autoWidth:true,
		height : 102
	};
	/**
	 * 用户名密码验证
	 */
	var loginValidate = function(userCode,password){
		if(userCode.getValue() == ''){
			warningMesg({
				msg : '请您输入用户名!',
				fn : function(){
					userCode.focus();
				}
			});
			return false;
		}
		
		if(password.getValue() == ''){
			warningMesg({
				msg : '请您输入密码!',
				fn : function(){
					password.focus();
				}
			});
			return false;
		}
	}
	/**
	 * 打开主页
	 */
	var openMainpageWindow = function(userCode_cookie){
		var screenWidth = screen.availWidth;
        var screenHeight = screen.availHeight;
		window.open(basicConstant.MAIN_PAGE+'?userCode='+userCode_cookie, '',"directories=no,status=no,toobar=no,location=no,width="+screenWidth+",height="+screenHeight+",resizable=0,scrollbars=no,left=0,top=0");
		opener=null; 
		self.close();
	}
	/**
	 * 角色选择窗口
	 */
	var roleSelectedWindow = function(uumRoleUserRefList,userId){
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
	   }
	   var rolWin = new Ext.Window({
				id    : 'roleSelected',
				title : '您拥有以下角色,请选择其一',
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
											userId:userId,
											userCode:getExtCmpById('userCode').getValue(),
											roleId:roleId,
											roleName:roleName,
											organId:organId,
											organName:organName
										},
										callBack : function(returnData) {
											Ext.getCmp('roleSelected').close();
											openMainpageWindow(getExtCmpById('userCode').getValue());
										}
									});
								}
							},{
								xtype:'button',
								text:'关闭',
								iconCls : 'close',
								handler:function(){
									Ext.getCmp('loginButtonId').setDisabled(false);
									this.ownerCt.ownerCt.hide();
								}
							}]
			});
			return rolWin;
	}
	/**
	 * 登录相关操作
	 */
	var bottomPanel = {
				xtype : 'form',
				id : "loginPanelId",
				labelPad : 0,
				labelWidth : 60,
				height:163,
				frame : true,
				border : false,
				autoWidth:true,
				bodyStyle : "padding-left:120px;padding-top:30px",
				layout : "form",
				buttonAlign : "center",
				items : [{
								xtype : "textfield",
								id : "userCode",
								fieldLabel : "用户名",
								maxLength:20,
								vtype:'alphanum',
								cls:'username',
								allowBlank:false,
								blankText:'请您输入用户名!',
								width : 150,
								enableKeyEvents : true, 
								listeners:{
									keyup :function(){
										var pwd = Ext.getCmp('password').getValue();
										var uName = Ext.getCmp('userCode').getValue();
										if(pwd != '' && uName != ''){
											Ext.getCmp('loginButtonId').setDisabled(false);
											
										}
									}
								}
							},{
								xtype : "textfield",
								id : "password",
								fieldLabel : "密&nbsp;&nbsp;&nbsp;码",
								maxLength:20,
								vtype:'alphanum',
								inputType : "password",
								allowBlank:false,
								blankText:'请您输入密码!',
								cls:'password',
								iconCls : "password",
								width : 150,
								enableKeyEvents : true,
								listeners:{
									keyup :function(){
										var pwd = Ext.getCmp('password').getValue();
										var uName = Ext.getCmp('userCode').getValue();
										if(pwd != '' && uName != ''){
											Ext.getCmp('loginButtonId').setDisabled(false);
										}
									}
								}
							}
						],
				buttons : [{
							xtype : "button",
							id:'loginButtonId',
							text : "登陆",
							pressed : true,
							iconCls : 'login',
							disabled:true,
							handler : function(){
								if(getExtCmpById('loginPanelId').form.isValid() == false){
									warningMesg({
										msg:'输入项不合法请检查!'
									});
									return ;
								}
								var loginBut = this;
								loginBut.setDisabled(true);
								var userCode = Ext.getCmp('userCode');
								var password = Ext.getCmp('password');
								if(loginValidate(userCode,password) == false){
									loginBut.setDisabled(false);
									return;
								}
								ajaxRequest({
									url : 'loginAction!loginSysFirstDo.action',
									params : {
										userCode:userCode.getValue(),
										password:password.getValue()
									},
									callBack : function(returnData) {
										var returnMesg = returnData.returnMesg;
										var uumRoleUserRefList = returnData.uumRoleUserRefList;
										var userId = returnData.userId;
										var message = '';
										switch(returnMesg){
											case '1':
												message = '对不起,您输入的用户名或密码有误!';
												break;
											case '2':
												message = '对不起,当前用户没有使用系统的权限,或请联系系统管理员!';
												break;
											case '3':
												message = '对不起,当前用户已被停用,或请联系系统管理员!';
												break;
											case '4':
												message = '对不起,当前用户已过有效期,或请联系系统管理员!';
												break;
										}
										if(message != ''){
											loginBut.setDisabled(false);
											warningMesg({
												msg : message,
												fn : function(){
													userCode.focus();
												}
											});
											return false;
										}
										if(returnMesg == '5'){//多个角色
											roleSelectedWindow(uumRoleUserRefList,userId).show();//调出角色选择窗口
										}else{
											openMainpageWindow(userCode.getValue());//打开主页
										}
									}
								});
			                    
							}
						},{
							xtype : "button",
							text : "重置",
							pressed : true,
							iconCls : 'table',
							handler : function() {
								Ext.getCmp('loginPanelId').form.reset();
								Ext.getCmp('loginButtonId').setDisabled(true);
							}
						}]
			};
	var loginPanel = {
		xtype:'panel',
		border:false,
		items : [topPanel, bottomPanel]
	}
	/**
	 * 登录窗口
	 */
	var loginWindow = new Ext.Window({
				id : "loginWindow",
				title : "YLB--登陆",
				width : 500,// 230, //Window宽度
				height : 300,// 137, //Window高度
				resizable : false,  //是否可手动调整Window大小，默认为true 
				closable : false, // 关闭按钮，默认为true
				border:false,
				loyout:'fit',
				items : [loginPanel],
				listeners : {
					show : function() {
						Ext.getCmp("userCode").focus(false, 100); //100 表示延时
					}
				}
			});
	loginWindow.show();
	formEnterEvent();//回车换行
}