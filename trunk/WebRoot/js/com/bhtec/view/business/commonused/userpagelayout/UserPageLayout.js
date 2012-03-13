/**
 * 主框架页面操作
 * @author lianglp
 * @version 1.0
 * @class com.bhtec.bussiness.commonused.userpagelayout.UserPageLayout
 * @date 2010-07-12
 */
Ext.namespace('com.bhtec.view.business.commonused.userpagelayout');
com.bhtec.view.business.commonused.userpagelayout.UserPageLayout = function(config){
	var mainFrameVOp = this;   //父类调用
	/**
     *打开主页面 
     */
    var openMainpageWindow = function(userCode_cookie){
		var screenWidth = screen.availWidth;
        var screenHeight = screen.availHeight;
        var variable = encode64(userCode_cookie)+'#'+userCode_cookie;
		window.open(basicConstant.MAIN_PAGE+'?'+variable, '',"directories=no,status=no,toobar=no,location=no,width="+screenWidth+",height="+screenHeight+",resizable=0,scrollbars=no,left=0,top=0");
		opener=null; 
		self.close();
	}
	/**
	 * 功能区增加修改表单
	 */
	var funForm = function(configForm){
		/**
		 * 点击保存按钮调用方法
		 */
		var save = function() {
			bht_cookie.saveCookie(userCode_cookie+'_'+basicConstant.BHT_COOKIE_HEADER,getExtCmpValueById('headerId'));
			bht_cookie.saveCookie(userCode_cookie+'_'+basicConstant.BHT_COOKIE_NAVIGATE,getExtCmpValueById('navigateId'));
			bht_cookie.saveCookie(userCode_cookie+'_'+basicConstant.BHT_COOKIE_OUTLOOK,getExtCmpValueById('outlookBarId'));
			bht_cookie.saveCookie(userCode_cookie+'_'+basicConstant.BHT_COOKIE_MAINPAGE,getExtCmpValueById('mainPageId'));
			bht_cookie.saveCookie(userCode_cookie+'_'+basicConstant.BHT_COOKIE_STATEBAR,getExtCmpValueById('stateBarId'));
			var configCb = {
				msg : '页面布局设置成功,是否加载?',
				fn : function(confirm) {
					if ('ok' == confirm) {
						openMainpageWindow(userCode_cookie);
					}else{
						getExtCmpById('userPageLayoutSetId').close();
					}
				}
			}
			askMesg(configCb);
		}
		
		var syncUrl = 'userPageLayoutAction!findAllMainFrameFun.action';
		var data = syncAjaxReqDecode(syncUrl);
		if(data == '')return;
		var mainFrameFunMap = data.mainFrameFunMap||'';
		//从cookie中取出页面布局配置信息
		var bht_cookie = new com.bhtec.util.Cookie();
		var bht_cookie_header_layout = bht_cookie.getCookie(userCode_cookie+'_'+basicConstant.BHT_COOKIE_HEADER);
		var bht_cookie_navigate_layout = bht_cookie.getCookie(userCode_cookie+'_'+basicConstant.BHT_COOKIE_NAVIGATE);
		var bht_cookie_outlook_layout = bht_cookie.getCookie(userCode_cookie+'_'+basicConstant.BHT_COOKIE_OUTLOOK);
		var bht_cookie_mainpage_layout = bht_cookie.getCookie(userCode_cookie+'_'+basicConstant.BHT_COOKIE_MAINPAGE);
		var bht_cookie_statebar_layout = bht_cookie.getCookie(userCode_cookie+'_'+basicConstant.BHT_COOKIE_STATEBAR);
		
		var headerValue = bht_cookie_header_layout==null?basicConstant.GENERAL:bht_cookie_header_layout;
		var navigateValue = bht_cookie_navigate_layout==null?basicConstant.GENERAL:bht_cookie_navigate_layout;
		var outlookBarValue = bht_cookie_outlook_layout==null?basicConstant.WINXP:bht_cookie_outlook_layout;
		var mainPageValue = bht_cookie_mainpage_layout==null?basicConstant.GENERAL:bht_cookie_mainpage_layout;
		var stateBarValue = bht_cookie_statebar_layout==null?basicConstant.WINXP:bht_cookie_statebar_layout;
		
		
		//功能区header
		var header = mainFrameVOp.comboBox({
				id : "headerId",
				allowBlank:false,
				fieldLabel : config.header,
				value:headerValue,
				store:new Ext.data.JsonStore({
						data 	: mainFrameFunMap.header,
						fields 	: ['funURI','funURIDesc']
					}),
				valueField 	: 'funURI',
				displayField: 'funURIDesc'
		});
		
		//功能区navigate
		var navigate = mainFrameVOp.comboBox({
				id : "navigateId",
				allowBlank:false,
				fieldLabel : config.navigate,
				value:navigateValue,
				store:new Ext.data.JsonStore({
						data 	: mainFrameFunMap.navigate||[],
						fields 	: ['funURI','funURIDesc']
					}),
				valueField 	: 'funURI',
				displayField: 'funURIDesc'
		});
		
		
		//功能区outlookBar
		var outlookBar = mainFrameVOp.comboBox({
				id : "outlookBarId",
				value:outlookBarValue,
				maxLength:15,
				fieldLabel : config.outlookBar,
				store:new Ext.data.JsonStore({
						data 	: mainFrameFunMap.outlookBar,
						fields 	: ['funURI','funURIDesc']
					}),
				valueField 	: 'funURI',
				displayField: 'funURIDesc'
		});
		//功能区mainPage
		var mainPage = mainFrameVOp.comboBox({
				id : "mainPageId",
				fieldLabel : config.mainPage,
				store:new Ext.data.JsonStore({
						data 	: mainFrameFunMap.mainPage,
						fields 	: ['funURI','funURIDesc']
					}),
				valueField 	: 'funURI',
				displayField: 'funURIDesc',
				value:mainPageValue
		});
		//功能区stateBar
		var stateBar = mainFrameVOp.comboBox({
				id : "stateBarId",
				value:stateBarValue,
				maxLength:15,
				fieldLabel : config.stateBar,
				store:new Ext.data.JsonStore({
						data 	: mainFrameFunMap.stateBar,
						fields 	: ['funURI','funURIDesc']
					}),
				valueField 	: 'funURI',
				displayField: 'funURIDesc'			
		});
		/**
		 * 修改信息列
		 */
		var formCols = [header,navigate,outlookBar,mainPage,stateBar];
		var columnFields = new Array();
		for(i=0;i<formCols.length;i++){
			columnFields.push({
					border : false,
					layout : "form",
					columnWidth : .8,
					items : formCols[i]
			});
		}
		
		var layoutPanel = mainFrameVOp.panel({
			layout:'column',
			bodyStyle : 'padding-left:100px;',
			items:[columnFields]
		})
		/**
		 * 修改信息formpanel
		 */
		var pageLayoutFormPanel = mainFrameVOp.formPanel({
			items:[{
						html:'<img src=./img/navigate/note.png />'
				 },layoutPanel],
			buttonAlign:'center',
			labelWidth:80,
			buttons:[mainFrameVOp.saveButton({
				handler:function(){
					save();
				}
			}),mainFrameVOp.closeButton({
				handler:function(){
					this.ownerCt.ownerCt.ownerCt.close();
				}
			})]
	    });
	    
		mainFrameVOp.window({
			id:'userPageLayoutSetId',
			title:'页面布局设置',				//窗口title
			layout:'fit',
			items:pageLayoutFormPanel,
			width:600,
			height:550
		});	
		//为表单域加回车键
		formEnterEvent();
	};
	
		
	return {
			funForm:funForm
	}
}

Ext.extend(com.bhtec.view.business.commonused.userpagelayout.UserPageLayout, 
com.bhtec.view.util.CommonWidgets, {});