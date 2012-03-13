/**
 * 用户管理操作
 * @author lianglp
 * @version 1.0
 * @class com.bhtec.view.business.uum.user.UserVOp
 * @date 2010-09-23
 */
Ext.namespace('com.bhtec.view.business.uum.user');
com.bhtec.view.business.uum.user.UserVOp = function(config){
	var moduleVOp = this;   //父类调用
	var moduleGridId = 'userGridId';//form表单id
	
	/**
	 * 功能区增加修改表单
	 */
	var busiForm = function(configForm){
		var moduleData = configForm.moduleData||'';
		/**
		 * 公用保存设置
		 */
		var saveCommon = function(saveFlag) {
			if(getExtCmpValueById('oldUserCode') != getExtCmpValueById('userCode')){
				var syncUrl = 'userAction!findUserByUserCode.action';
				var data = syncAjaxReqDecode(syncUrl,'userCodee='+getExtCmpValueById('userCode'));
				if(data == '')return;
				if(data.existUser == true){
					var configExist = {
						msg:'对不起，用户号称已经存在!'
					};
					showSucMesg(configExist);
					moduleVOp.enableSaveButton();//添加保存enable
					return false;
				}
			}
			var configSave = {
				url : saveFlag.url,
				params : {
					status : getExtCmpValueById('status'),
					userGender:getExtCmpValueById('userGender').inputValue
				},
				callBack : function(returnData) {
					var configCb = {
						msg : '用户保存成功!',
						fn : function(confirm) {
							if ('ok' == confirm) {
								if (saveFlag.save == 'save') {
									refreshGridList(moduleGridId);
									getExtCmpById(moduleVOp.cuvWindowId).close();
								} else {
									moduleVOp.enableSaveButton();//添加保存enable
									resetForm(basicConstant.FORM_ID);
									refreshGridList(moduleGridId);
								}
							}
						}
					}
					showSucMesg(configCb);
				}
			}
			submitForm(configSave);
		}
		
		/**
		 * 点击保存按钮调用方法
		 */
		var save = function() {
			var url = 'userAction!saveUser.action';
			if(configForm.modify == true){
				url = 'userAction!modifyUser.action';
			}
			var configSave = {
				url:url,
				save:'save'
			}
			saveCommon(configSave);
		}

		/**
		 * 点击保存增加按钮调用方法
		 */
		var saveAdd = function() {
			var configSave = {
				url:'userAction!saveUser.action',
				save:'saveAdd'
			}
			saveCommon(configSave);
		}
		
		
		var oldOrgName = new Ext.form.Hidden({
				id : "oldUserCode",
				value:moduleData.userCode||''
		})
		
		/**
		 * 公用增加修改删除表单
		 */
		
		//用户号
		var userCode = moduleVOp.textField({
				id : "userCode",
				name : "userCode",
				allowBlank:false,
				fieldLabel : config.userCode,
				value:moduleData.userCode||'',
				vtype:'alphanum',
				emptyText:'请输入字母、数字、下划线'
		});
		
		//用户名
		var userName = moduleVOp.textField({
				name : "userName",
				value:moduleData.userName||'',
				allowBlank:false,
				fieldLabel : config.userName
		});
		
		//用户ID
		var userId = new Ext.form.Hidden({
				id : "userId",
				value:moduleData.userId||''
		});
		
		//用户密码
		var userPassword = moduleVOp.textField({
				name : "userPassword",
				value:moduleData.userPassword||configForm.userDefaultPwd,
				inputType:'password',
				allowBlank:false,
				fieldLabel : config.userPassword,
				vtype:'alphanum',
				maxLength:10,
				emptyText:'请输入字母、数字、下划线'
		});
		
		//修改时不显示密码
		var userPasswordMod = new Ext.form.Hidden({
				name : "userPassword",
				value:moduleData.userPassword||''
		});
		var userGenderValue = moduleData.userGender==undefined?0:moduleData.userGender;
		//用户性别
		var userGender = moduleVOp.radio({
				id : "userGender",
				name : "userGender",
				value:userGenderValue,
				fieldLabel : config.userGender,
				width:150,
				items:[
					{boxLabel: '男', name: 'gender', inputValue: 0, 
						checked: (userGenderValue==0?true:false)},
                	{boxLabel: '女', name: 'gender', inputValue: 1,
                		checked: (userGenderValue==1?true:false)}
                ]
		});
		//用户职位
		var userPosition = moduleVOp.textField({
				name : "userPosition",
				value:moduleData.userPosition||'',
				fieldLabel : config.userPosition,
				maxLength:20
		});
		var userPhotoUrl = (moduleData.userPhotoUrl==undefined||moduleData.userPhotoUrl=='')?'./img/business/blank_userphoto.gif':moduleData.userPhotoUrl;
		//用户照片
		var userPhotoView = moduleVOp.textField({
				id:'selectPhotoId',
				name : "selectPhotoId",
				width:150,
				height:100,
				fieldLabel : config.userPhotoUrl,
			    autoCreate : {
				     tag : 'input',
				     type : 'image',
				     src : userPhotoUrl,
				     style : 'filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale);'
			    },
			    tooltip : '单击删除图片',
			    listeners:{
			    	render:function(field){
			    		Ext.get('selectPhotoId').on('mousedown',function(){
			    			askMesg({
			    				msg:'您确定要删除吗?',
			    				fn:function(confirm){
			    					if('ok' == confirm){
				    					var image = getExtCmpById('selectPhotoId').el.dom;
									    image.src = './img/business/blank_userphoto.gif';// 覆盖原来的图片
									    var obj = Ext.getDom('fileUpload');
								     	obj.select();   
										document.selection.clear();
										ajaxRequest({
											url:'userAction!deletePhotoFile.action',
											params:{
												userPhotoUrl:getExtCmpValueById('userPhotoUrl'),
												userId:moduleData.userId||''
											},
											callBack:function(){
												getExtCmpById('userPhotoUrl').setValue('');
											}
										});
			    					}
			    				}
			    			});
			    		})
			    	}
			    }
		});
		
		//用户照片
		var userPhotoUrl = moduleVOp.textField({
				id:'fileUpload',
				name:'fileUpload',
				fieldLabel : config.selectPhoto,
				maxLength:'5000',
				inputType : "file",
				listeners :{
					'focus':function(field,newValue,oldValue ){
						var fileUrlStandard = /\.([jJ][pP][gG]){1}$|\.([jJ][pP][eE][gG]){1}$|\.([gG][iI][fF]){1}$|\.([pP][nN][gG]){1}$|\.([bB][mM][pP]){1}$/;
						var obj = Ext.getDom('fileUpload');//IE8 安全屏蔽
		     		    obj.select();
		     		    var fileUrl = document.selection.createRange().text;
						if(fileUrl != ''){
							//验证文件格式
			                if(fileUrlStandard.test(fileUrl)){
						     	  if (Ext.isIE) {
								       var image = getExtCmpById('selectPhotoId').el.dom;
								       image.src = Ext.BLANK_IMAGE_URL;// 覆盖原来的图片
								       image.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = fileUrl;
							      } else {
							       	   getExtCmpById('selectPhotoId').el.dom.src = fileUrl;
							      }
						     }else{
						     	var obj = Ext.getDom('fileUpload');
						     	obj.select();   
								document.selection.clear(); 
						     	warningMesg({
						     		msg : '对不起,照片格式不正确!'
						     	});
						     }
						}
					}
				}
		});
		
		//用户照片隐含URL
		var uumPhotoUrlHiddenId = new Ext.form.Hidden({
				id : 'userPhotoUrl',
				name: 'userPhotoUrl',
				value:moduleData.userPhotoUrl||''
		});
		
		//用户QQ
		var userQq = moduleVOp.numberField({
				name : "userQq",
				value:moduleData.userQq||'',
				maxLength:20,
				fieldLabel : config.userQq,
				allowDecimals : false,
				emptyText:'请输入数字'
		});
		//用户MSN
		var userMsn = moduleVOp.textField({
				name : "userMsn",
				value:moduleData.userMsn||'',
				fieldLabel : config.userMsn,
				vtype:'email',
				maxLength:30
		});
		
		//用户手机
		var userMobile = moduleVOp.numberField({
				name : "userMobile",
				fieldLabel : config.userMobile,
				value:moduleData.userMobile||'',
				maxLength:20,
				allowDecimals : false ,
				emptyText:'请输入数字'
		});
		
		//用户手机2
		var userMobile2 = moduleVOp.numberField({
				name : "userMobile2",
				fieldLabel : config.userMobile2,
				allowDecimals : false ,
				maxLength:20,
				value:moduleData.userMobile2||'',
				emptyText:'请输入数字'
		});
		
		//办公电话
		var userOfficeTel = moduleVOp.numberField({
				name : "userOfficeTel",
				fieldLabel : config.userOfficeTel,
				value:moduleData.userOfficeTel||'',
				maxLength:20,
				emptyText:'请输入数字'
		});
		
		//家庭电话
		var userFamilyTel = moduleVOp.numberField({
				name : "userFamilyTel",
				fieldLabel : config.userFamilyTel,
				value:moduleData.userFamilyTel||'',
				maxLength:20,
				emptyText:'请输入数字'
		});
		
		//家庭住址
		var userAddress = moduleVOp.textField({
				name : "userAddress",
				fieldLabel : config.userAddress,
				value:moduleData.userAddress||'',
				maxLength:100
		});
		
		//用户邮件
		var userEmail = moduleVOp.textField({
				name : "userEmail",
				fieldLabel : config.userEmail,
				value:moduleData.userEmail||'',
				maxLength:30,
				vtype:'email'
		});
		//用户有效期
		var userAvidate = moduleVOp.dateField({
				name : "userAvidate",
				fieldLabel : config.userAvidate,
				format:'Y-m-d',
				value:moduleData.userAvidate||configForm.userValidate
		});
		
		//用户状态
		var status = moduleVOp.comboBox({
				id : "status",
				fieldLabel : config.status,
				store:new Ext.data.JsonStore({
						data 	: com.bhtec.util.Data.status,
						fields 	: com.bhtec.util.Data.statusFields
					}),
				valueField 	: 'status',
				displayField: 'statusName',
				value:moduleData.status||'enable',
				allowBlank:false
		});
		
		var treeWin = function(){
			moduleVOp.treeWindow({
						title 	 : '机构角色树',
						items	 : moduleVOp.asyncTreePanel({
										rootText:basicConstant.ORGAN_ROOT,
										rootVisible:true,
										url:'userAction!findNextLevelChildNodes.action',
										clickNode:function(node, e){
											var nodeId = node.id;
											var _flag = nodeId.indexOf('_');
											if(nodeId.substr(_flag+1) == '1' || nodeId == '0'){
												warningMesg({
													title:'提示',
													msg:'对不起,请您选择角色!'
												});
												return false;
											}else{
												getExtCmpById('uumOrgRoleId').setValue(nodeId.substr(0,_flag));
												getExtCmpById('uumRoleNameId').setValue((node.parentNode.text)+'-'+node.text);
											}
										}
								})
					});
			};
		
		var uumOrgRoleIdValue;
		var uumRoleName;
		if(moduleData.uumOrgRoleId != undefined){
					uumOrgRoleIdValue = moduleData.uumOrgRoleId;
					uumRoleName = moduleData.uumRoleName;
		}else{
			var userOrgRoleTreeId = getExtCmpValueById('userOrgRoleTreeId');
			var _flag = userOrgRoleTreeId.indexOf('_');
			if(_flag > 0){
				if(userOrgRoleTreeId.substr(_flag+1) == '2'){
					uumOrgRoleIdValue = userOrgRoleTreeId.substring(0,_flag);
					uumRoleName = getExtCmpValueById('userOrgRoleTreeNodeNameId');
				}
			}
		}
		//所属角色
		var uumRole = moduleVOp.triggerField({
				id : 'uumRoleNameId',
				name : "uumRoleName",
				value:uumRoleName||'',
				allowBlank:false,
				fieldLabel : config.uumRole,
				allowBlank:false,
				window:treeWin
		});
		//机构角色ID
		var uumOrgRoleId = new Ext.form.Hidden({
				id : "uumOrgRoleId",
				name : "uumOrgRoleId",
				value:uumOrgRoleIdValue||''
		});
		
		//机构ID
		var belongOrganId = new Ext.form.Hidden({
				id : "belongOrganId",
				name : "belongOrganId",
				value:moduleData.belongOrganId||''
		});
		
		//备注
		var memo = moduleVOp.textField({
				name : "memo",
				value:moduleData.memo||'',
				fieldLabel : config.memo,
				maxLength:50
		});
		
		//创建时间
		var creator = new Ext.form.Hidden({
				name : "creator",
				value:moduleData.creator||''
		});
		
		//创建人
		var createDate = new Ext.form.Hidden({
				name : "createDate",
				value:moduleData.createDate||''
		});
		var formCols = [];
		if(configForm.modify == true){//修改时不会修改所属角色
			formCols = [userPhotoView,userCode,userName,userPasswordMod,
						  userGender,userPosition,userPhotoUrl,userQq,userMsn,
						  userMobile,userMobile2,userOfficeTel,userFamilyTel,userAddress,
						  userEmail,userAvidate,status,memo,
						  uumOrgRoleId,userId,creator,
						  createDate,belongOrganId,uumPhotoUrlHiddenId]
		}else{
			formCols = [userPhotoView,userCode,userName,userPassword,
						  userGender,userPhotoUrl,userPosition,userQq,userMsn,
						  userMobile,userMobile2,userOfficeTel,userFamilyTel,userAddress,
						  userEmail,userAvidate,status,uumRole,memo,
						  uumOrgRoleId,userId,creator,
						  createDate,belongOrganId,uumPhotoUrlHiddenId]
		}
		//调用父类方法进行窗口构造
		moduleVOp.cuvWindow({
			title:configForm.title,				//窗口title
			columnFields:formCols,	//表单第一列
			save:save,							//保存按钮调用的方法
			saveAdd:saveAdd,					//保存并添加按钮调用的方法
			modify:configForm.modify,			//窗口判断是否显示保存增加按钮
			allButtonHidden:configForm.allButtonHidden,
			componentIndex:1,//初始控件索引,获得焦点,
			fileUpload : true,
			winheight:basicConstant.WIN_HEIGHT+50,
			enctype : 'multipart/form-data'
		});	
	};
	
	/**
	 * 点击列表保存，弹出保存页面
	 */
	var saveForm = function(){
		ajaxRequest({
			url:'userAction!obtainUserDefPwdAValidate.action',
			callBack:function(returnData){
				busiForm({
					userDefaultPwd:returnData.userDefaultPwd,
					userValidate:returnData.userValidate,
					title:'用户添加'
				});				
			}
		})
	}
	/**
	 * 点击列表修改，弹出修改页面
	 */
	var modifyForm = function(){
		var modDelRecord = modifyDelSelRecord(moduleGridId);//请选择一条件记录
		if(modDelRecord != ''){
				var configFind = {
						url:'userAction!findUserByUserId.action',
						params:{modViewRecId:modDelRecord.userId},
						callBack:function(returnData){
								var configForm = {
									title:'用户修改',
									moduleData:returnData.model,
									modify:true
								}
								busiForm(configForm);
						}
				}
				ajaxRequest(configFind);
		}
	}
	/**
	 * 点击列表查看，弹出查看页面
	 */
	var viewForm = function(){
		var modDelRecord = modifyDelSelRecord(moduleGridId);//请选择一条件记录
		if(modDelRecord != ''){
			var configFind = {
					url:'userAction!findUserByUserId.action',
					params:{modViewRecId:modDelRecord.userId},
					callBack:function(returnData){
						var configForm = {
							title:'用户查看',
							moduleData:returnData.model,
							allButtonHidden:true
						}
						busiForm(configForm);
					}
			}
			ajaxRequest(configFind);
		}
	}
	
	/**
	 * 点击删除按钮调用方法
	 */
	var delRecord = function() {
		var recordData = modifyDelSelRecord(moduleGridId);
		if (recordData != '') {
			var configDel = {
				url : 'userAction!deleteModuleById.action',
				params : {
					modViewRecId : recordData.moduleId
				},
				callBack : function(returnData) {
					var configCb = {
						msg : '用户菜单删除成功!',
						fn : function(confirm) {
							if ('ok' == confirm) {
								refreshGridList(moduleGridId);
							}
						}
					}
					showSucMesg(configCb);
				}
			}
			ajaxRequest(configDel);
		}

	}
	
	/**
	 * 停用启用操作
	 */
	var disableEnableOpt = function(flag,modDelRecord){
		var moduleGridPanel = getExtCmpById(moduleGridId);
		var selModDelRecord = moduleGridPanel.getSelectionModel().getSelected();
		var configdisEnable = {
				url:'userAction!disEnableUser.action',
				noMask:true,
				params:{
					modViewRecId:modDelRecord.userId,
					disEnableFlag:flag
				},
				callBack:function(returnData){
					var msg = '';
					if(returnData.disEnableBol){
						if(flag == 'enable'){
							msg = '用户启用成功!';
						}else{
							msg = '用户停用成功!此用户将不能在使用本系统。';
						}
						selModDelRecord.set('status', flag);
						moduleStore = moduleGridPanel.store;
						moduleStore.commitChanges();
					}
					var configCb = {
						msg : msg,
						fn : function(confirm) {
							refreshGridList(moduleGridId);//刷新列表
						}
					}
					showSucMesg(configCb);
				}
		}
		ajaxRequest(configdisEnable);
	}
	
	/**
	 * 启用 停用
	 */
	var disEnable = function(flag){
		var modDelRecord = modifyDelSelRecord(moduleGridId);//请选择一条件记录
		if(modDelRecord == '')return;
		if(flag == modDelRecord.status){
			var msg='';
			if(flag == 'enable'){
				msg = '此用户已经启用!';
			}else{
				msg = '此用户已经停用!';
			}
			showSucMesg({
				msg:msg
			})
			return;
		}
		
		if(flag == 'enable'){
			disableEnableOpt(flag,modDelRecord);
		}else{
			askMesg({
				msg:'您确认停用此用户?如停用,此用户将归入无角色用户并自动清除所有权限。',
				fn:function(confirm){
					if('ok' == confirm){
						disableEnableOpt(flag,modDelRecord);
					}
				}
			});
		}
	}
	
	return {
			saveForm:saveForm,
			delRecord:delRecord,
			modifyForm:modifyForm,
			viewForm:viewForm,
			disEnable:disEnable
	}
}

Ext.extend(com.bhtec.view.business.uum.user.UserVOp, com.bhtec.view.util.CommonWidgets, {});