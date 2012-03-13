/**
 * 用户登录页面
 * @author lianglp
 * @version 2.0
 * @class com.bhtec.view.login
 * @date 2010-05-26
 * @type String
 */
Ext.namespace('com.bhtec.view');
com.bhtec.view.Login = function(config) {
	var topPanel = {
		xtype : 'panel',
		id : "topPanel",
		bodyStyle : "background-image: url(./img/jacob_liang.jpg);",
		frame : true,
		border : false,
		autoWidth:true,
		height : 160,
		html:''
	};
	/**
	 * 用户名密码验证
	 */
	var loginValidate = function(userCode,password){
		if(userCode.getValue() == ''){
			Ext.Msg.show({
				title : '提示',
				msg : '请您输入用户名!',
				width:300,
				buttons : Ext.Msg.OK,
				icon : Ext.Msg.WARNING,
				fn : function(){
					userCode.focus();
				}
			});
			return false;
		}
		
		if(password.getValue() == ''){
			Ext.Msg.show({
				title : '提示',
				msg : '请您输入密码!',
				width:300,
				buttons : Ext.Msg.OK,
				icon : Ext.Msg.WARNING,
				fn : function(){
					password.focus();
				}
			});
			
			return false;
		}
	}
	var encode64 = function (input) {
	 	var keyStr = "ABCDEFGHIJKLMNOP" +
	                "QRSTUVWXYZabcdef" +
	                "ghijklmnopqrstuv" +
	                "wxyz0123456789";
		input = escape(input);
		var output = "";
		var chr1, chr2, chr3 = "";
		var enc1, enc2, enc3, enc4 = "";
		var i = 0;
		do {
			chr1 = input.charCodeAt(i++);
			chr2 = input.charCodeAt(i++);
			chr3 = input.charCodeAt(i++);
			enc1 = chr1 >> 2;
			enc2 = ((chr1 & 3) << 4) | (chr2 >> 4);
			enc3 = ((chr2 & 15) << 2) | (chr3 >> 6);
			enc4 = chr3 & 63;
			if (isNaN(chr2)) {
				enc3 = enc4 = 64;
			} else if (isNaN(chr3)) {
				enc4 = 64;
			}
			output = output + keyStr.charAt(enc1) + keyStr.charAt(enc2)
					+ keyStr.charAt(enc3) + keyStr.charAt(enc4);
			chr1 = chr2 = chr3 = "";
			enc1 = enc2 = enc3 = enc4 = "";
		} while (i < input.length);
		return output;
	}
	/**
	 * 打开主页
	 */
	var openMainpageWindow = function(usrCodeCookie){
		var screenWidth = screen.availWidth;
        var screenHeight = screen.availHeight;
        usrCodeCookie = encode64(usrCodeCookie);
        var multiRoleFlag = Ext.getCmp('multiRoleFlagId').getValue();
        usrCodeCookie = 'r='+multiRoleFlag+'&'+encode64(usrCodeCookie)+'#'+usrCodeCookie;
		window.open('loginActionForword!loginActionForword.action?'+usrCodeCookie, '',"directories=no,status=no,toobar=no,location=no,width="+screenWidth+",height="+screenHeight+",resizable=0,scrollbars=no,left=0,top=0");
		opener=null; 
		self.close();
	}
	
	var loginSys = function(){
		var loginBut = Ext.getCmp('loginButtonId');
		loginBut.setDisabled(true);
		var userCode = Ext.getCmp('userCode');
		var password = Ext.getCmp('password');
		if(loginValidate(userCode,password) == false){
			loginBut.setDisabled(false);
			return;
		}
		
		var userRoleLoginHiddenParas = Ext.getCmp('userRoleLoginHiddenParasId');
		var agentUserLoginHiddenParas = Ext.getCmp('agentUserLoginHiddenParasId');
		var loadMask = new Ext.LoadMask(Ext.getBody(), {
									msg : "系统登录中,请稍候..."
				});
		loadMask.show();
		Ext.Ajax.request({
			url : 'loginAction!loginSysFirstDo.action',
			params : {
				userCode:userCode.getValue(),
				password:password.getValue(),
				userInfomationJson:userRoleLoginHiddenParas.getValue(),
				agentUserInfomationJson:agentUserLoginHiddenParas.getValue()
			},
			success : function(response, options) {
					loadMask.hide();
					var returnData = Ext.util.JSON.decode(response.responseText);
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
						Ext.Msg.show({
							title : '提示',
							msg : message,
							width:300,
							buttons : Ext.Msg.OK,
							icon : Ext.Msg.WARNING,
							fn : function(){
								userCode.focus();
							}
						});
						
						return false;
					}
					openMainpageWindow(userCode.getValue());//打开主页
			},
			failure : function(response, options) {
				Ext.Msg.show({
					title : '提示',
					value : '请联系管理员.失败类型：'+response.statusText,
					minWidth :300,
					msg:'操作失败!原因:',
					multiline : true,
					defaultTextHeight :30,
					prompt:true,
					buttons : Ext.Msg.OK,
					icon : Ext.MessageBox.ERROR
				});
				loadMask.hide();
			}
		});
	}
	//多角色值
	var userRoleLoginHiddenParas = {
		xtype:'hidden',
		id:'userRoleLoginHiddenParasId'
	}
	//多角色标志
	var multiRoleFlag = {
		xtype:'hidden',
		id:'multiRoleFlagId'
	}
	//多代理值
	var agentUserLoginHiddenParas = {
		xtype:'hidden',
		id:'agentUserLoginHiddenParasId'
	}
	
	var preLogin = function(){
		var loadMask = new Ext.LoadMask(Ext.getBody(), {
									msg : "预登录中,请稍候..."
				});
		loadMask.show();
		Ext.Ajax.request({
			url : 'loginAction!preLogin.action',
			params : {
				userCode:Ext.getCmp('userCode').getValue()
			},
			success : function(response, options) {
					var returnData = Ext.util.JSON.decode(response.responseText);
					//角色列表
					var uumRoleUserRefList = returnData.uumRoleUserRefList;
					var userRoleList = Ext.getCmp('userRoleId');
					Ext.getCmp('multiRoleFlagId').setValue('');
					if(uumRoleUserRefList != '' && uumRoleUserRefList != null){
						if(uumRoleUserRefList.length > 1){
							Ext.getCmp('multiRoleFlagId').setValue('y');
							var orgRoleId;
							for(var i=0;i<uumRoleUserRefList.length;i++){
					   			var roleUser = uumRoleUserRefList[i];
					   			if(roleUser.isDefault == 'Y'){
					   				roleUser.roleName = roleUser.organName+'-'+roleUser.roleName+'(默认)';
					   				var comboJson = Ext.encode(roleUser);
									Ext.getCmp('userRoleLoginHiddenParasId').setValue(comboJson);
					   			}else{
					   				roleUser.roleName = roleUser.organName+'-'+roleUser.roleName;
					   			}
					   		}
							userRoleList.setVisible(true);
							var store = userRoleList.store;
							store.loadData(uumRoleUserRefList,false);
						}else{
							userRoleList.setVisible(false);
							var comboJson = Ext.encode(uumRoleUserRefList[0]);
							Ext.getCmp('userRoleLoginHiddenParasId').setValue(comboJson);
						}
					}
					var userAgentList = returnData.userAgentList;
					var userAgent = Ext.getCmp('userAgentId');
					if(userAgentList != '' && userAgentList != null && userAgentList.length > 0){
						userAgent.setVisible(true);
						var store = userAgent.store;
						store.loadData(userAgentList,false);
					}else{
						userAgent.setVisible(false);
						Ext.getCmp('agentUserLoginHiddenParasId').setValue('');
					}
					loadMask.hide();
			},
			failure : function(response, options) {
				Ext.Msg.show({
					title : '提示',
					value : '请联系管理员.失败类型：'+response.statusText,
					minWidth :300,
					msg:'操作失败!原因:',
					multiline : true,
					defaultTextHeight :30,
					prompt:true,
					buttons : Ext.Msg.OK,
					icon : Ext.MessageBox.ERROR
				});
			}
		});
	}
	/**
	 * 登录相关操作
	 */
	var bottomPanel = {
		xtype : 'panel',
		id : "loginPanelId",
		labelPad : 0,
		height:180,
		frame : true,
		border : false,
		autoWidth:true,
		bodyStyle : "padding-left:120px;padding-top:30px;background-image: url(./img/bht.png);",
		layout : "column",
		buttonAlign : "center",
	items : [{	columnWidth : 1,
				layout : 'form',
				labelWidth : 60,
				items:[{
					xtype : "textfield",
					id : "userCode",
					fieldLabel : "用户名",
					maxLength:20,
					value:'',
					vtype:'alphanum',
					cls:'username',
					allowBlank:false,
					blankText:'请您输入用户名!',
					width : 120,
					enableKeyEvents : true, 
					listeners:{
						keyup :function(thiz,event){
							var uName = Ext.getCmp('userCode').getValue();
							if(uName != ''){
								if(event.getKey() == 13){
									Ext.getCmp('password').focus();
								}
							}
						},
						blur :function(thiz,event){
							preLogin();
						}
					}
					}]
					},{	columnWidth : 1,
						layout : 'form',
						labelWidth : 60,
						items:[
							{
							xtype : "textfield",
							id : "password",
							fieldLabel : "密&nbsp;&nbsp;&nbsp;码",
							maxLength:20,
							value:'',
							vtype:'alphanum',
							inputType : "password",
							allowBlank:false,
							blankText:'请您输入密码!',
							cls:'password',
							iconCls : "password",
							width : 120,
							enableKeyEvents : true,
							listeners:{
								keyup :function(thiz,event){
									var pwd = Ext.getCmp('password').getValue();
									var uName = Ext.getCmp('userCode').getValue();
									if(pwd != '' && uName != ''){
										var loginBut = Ext.getCmp('loginButtonId');
										loginBut.setDisabled(false);
										if(event.getKey() == 13){
											loginSys();
										}
									}										
								}
							}
				 		   }
					]},
					{	
					    columnWidth : 1,
						layout : 'form',
						labelWidth : 60,
						items:[{
						 	xtype:'combo',
						 	fieldLabel 	: '角色选择',
						 	hidden:true,
						 	width : 125,
						 	mode : 'local',
						 	triggerAction : 'all',
							emptyText : '角色选择...',
						 	id:'userRoleId',
						 	editable:false,
						 	store:new Ext.data.JsonStore({
								data : [],
								fields : ['orgRoleId','roleName']
							}),
							valueField 	: 'orgRoleId',
							displayField: 'roleName',
							listeners:{
								select:function(combo,record,index){
									Ext.getCmp('userAgentId').setValue('');
									Ext.getCmp('agentUserLoginHiddenParasId').setValue('');//代理值清空
									Ext.getCmp('userAgentId').setVisible(false);
									var comboJson = Ext.encode(record.json);
									Ext.getCmp('userRoleLoginHiddenParasId').setValue(comboJson);
								}
							}
					 }]
					 },
					{	columnWidth : 1,
						layout : 'form',
						labelWidth : 60,
						items:[{
					 	xtype:'combo',
					 	fieldLabel 	: '代理选择',
					 	hidden:true,
					 	width : 125,
					 	mode : 'local',
					 	triggerAction : 'all',
						emptyText : '代理用户...',
					 	id:'userAgentId',
					 	editable:false,
					 	store:new Ext.data.JsonStore({
							data : [],
							fields : ['userCode', 'userName']
						}),
						valueField 	: 'userCode',
						displayField: 'userName',
						listeners:{
								select:function(combo,record,index){
									Ext.getCmp('userRoleId').setValue('');
									Ext.getCmp('userRoleId').setVisible(false);
									var comboJson = Ext.encode(record.json);
									Ext.getCmp('agentUserLoginHiddenParasId').setValue(comboJson);
									Ext.getCmp('multiRoleFlagId').setValue('');//选择代理时，多角色为''
								}
							}
					 }]
					 },userRoleLoginHiddenParas,agentUserLoginHiddenParas,multiRoleFlag
				],
		buttons : [{
					xtype : "button",
					id:'loginButtonId',
					text : "登陆",
					pressed : true,
					iconCls : 'login',
					disabled:true,
					handler : loginSys
				},{
					xtype : "button",
					text : "重置",
					pressed : true,
					iconCls : 'table',
					handler : function() {
						Ext.getCmp('userCode').setValue('');
						Ext.getCmp('password').setValue('');
						Ext.getCmp('userRoleId').setValue('');
						Ext.getCmp('userAgentId').setValue('');
						Ext.getCmp('loginButtonId').setDisabled(true);
						Ext.getCmp('userRoleId').setVisible(false);
						Ext.getCmp('userAgentId').setVisible(false);
						Ext.getCmp('agentUserLoginHiddenParasId').setValue('');
						Ext.getCmp('userRoleLoginHiddenParasId').setValue('');
						Ext.getCmp('multiRoleFlagId').setValue('');
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
				title : "jacob_liang系统平台统一用户--登陆",
				width :  435,
				autoHeight:true,
				resizable : false,  
				closable : false, 
				border:false,
				loyout:'fit',
				items : [loginPanel],
				listeners : {
					show : function() {
						Ext.getCmp("userCode").focus(false, 100);
					}
				}
			});
	loginWindow.show();
}