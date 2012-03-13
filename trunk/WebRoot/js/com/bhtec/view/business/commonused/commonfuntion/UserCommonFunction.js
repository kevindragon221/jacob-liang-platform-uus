/**
 * 用户常用功能
 * 
 * @author lianglp
 * @version 1.0
 * @class com.bhtec.view.business.commonused.commonfunction
 * @date 2010-11-25
 */

Ext.namespace('com.bhtec.view.business.commonused.commonfuntion');
com.bhtec.view.business.commonused.commonfuntion.UserCommonFunction = function(
		config) {
	var commWidgets = this;
	var userCommFunArr = new Array();
	for(i=0;i<assignUumUserCommfunList.length;i++){
		var moduleIdNameArr = new Array();
		moduleIdNameArr.push(assignUumUserCommfunList[i].moduleId);
		moduleIdNameArr.push(assignUumUserCommfunList[i].modName);
		userCommFunArr.push(moduleIdNameArr);
	}

	var commFunLabel = {
				xtype:'label',
				text:'请分配您的常用功能菜单：'
			};
	var thirdListAll = new Array(); //所有第三级菜单
	/**
	 * 根据结点
	 */
	var rootNode = function(){
		var rootNode = commWidgets.treeNode({
					id : '0',
					text:'模块树',
					expanded : true
				});
		var firstMenuList = firstMenu;//一级list
		if (firstMenuList != '') {
			for (i = 0; i < firstMenuList.length; i++) {
				var moduleId = firstMenuList[i].moduleId;
				var modName = firstMenuList[i].modName;
				var modImgCls = firstMenuList[i].modImgCls;
				var firstNode = commWidgets.treeNode({
							id : moduleId,
							text : modName,
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
									iconCls : modImgClsSec
								});
						var thirdList = thirdMap[moduleIdSec];
						thirdListAll = thirdList.concat(thirdListAll);//第三级菜单
						if (thirdList && thirdList != '') {
							for (j = 0; j < thirdList.length; j++) {// 三级菜单
								var modIdThi = thirdList[j].moduleId;
								var modNameThi = thirdList[j].modName;
								var modImgClsThi = thirdList[j].modImgCls;
								var checked = false;
								for(m=0;m<assignUumUserCommfunList.length;m++){
									if(assignUumUserCommfunList[m].moduleId == modIdThi){
										checked = true;
										break;
									}
								}
								// 创建结点
								var node = commWidgets.treeNode({
											id : modIdThi,
											iconCls : modImgClsThi,
											text : modNameThi,
											checked:checked
										});
								secondNode.appendChild(node);
							}
						}
						firstNode.appendChild(secondNode);
					}
				}
				rootNode.appendChild(firstNode);
	
			}
		}
		return rootNode;
	}
	/**
	 * 模块树 panel
	 */
	var treePanel = commWidgets.treePanel({
				id : 'commFunMenuTreeId',
				title:'未选菜单',
				autoWidth : true,
				border : true,
				height : Ext.getBody().getHeight() * 0.46,
				rootVisible : true,
				rootNode : rootNode()
			});
	
	var multiSelPanel = {
							xtype : "multiselect",
							name : "commFunMenuMultiSel",
							id : "commFunMenuMultiSel",
							dataFields : ["code", "desc"],
							valueField : "code",
							displayField : "desc",
							border : false,
							height : Ext.getBody().getHeight() * 0.45 + 7,
							width : 283,
							data : userCommFunArr,
							tbar:[{text: '已选菜单'}]
						};

	var selected = function() {
		var mul = getExtCmpById('commFunMenuMultiSel');
		var lefttree = getExtCmpById('commFunMenuTreeId');
		if (lefttree.getChecked().length > 0) {
			var checkedList = lefttree.getChecked();
			var records = [];
			for (var i = 0; i < checkedList.length; i++) {
				var bol = false;
				var singleNode = checkedList[i];
				var moduleId = singleNode.id;
				for (var j = 0; j < mul.store.getCount(); j++) {
					if (mul.store.getAt(j).get("code") == moduleId) {
						bol = true;
						break;
					}
				}
				if (bol == false) {
					var record = new Ext.data.Record({});
					record.set("code", moduleId);
					record.set("desc",singleNode.text);
					records.push(record);
				}
			}

			mul.store.add(records);
		} else {
			warningMesg({
						msg : '请您选择相应菜单!'
					})
		}
	}

	var unselected = function() {
		var mul = getExtCmpById('commFunMenuMultiSel');
		var indexs = mul.view.getSelectedIndexes();
		if (indexs.length == 0) {
			warningMesg({
						msg : "请您选择列表菜单!"
					});
			return;
		}
		var lefttree = getExtCmpById('commFunMenuTreeId');
		var selectValues = mul.getValue();
		var values = selectValues.split(",");
		for (j = 0; j < values.length; j++) {
			var nodet = lefttree.getNodeById(values[j]);
			if (nodet) {
				nodet.attributes.checked = false;
				nodet.ui.toggleCheck(false);
			}
		}

		for (var i = indexs.length - 1; i > -1; i--) {
			mul.store.removeAt(indexs[i]);
		}
	}
	/**
	 * 动态装载常用功能菜单
	 */
	var dynamicLoadCommFun = function(moduleIdList){
		//常用功能数组清0
		assignUumUserCommfunList.splice(0,assignUumUserCommfunList.length);
		for (var j = 0; j < moduleIdList.length; j++) {//构造常用功能菜单从所有三级菜单中
			var moduleMenuId = moduleIdList[j];
			for (i = 0; i < thirdListAll.length; i++) {//所有三级菜单
				var modIdThi = thirdListAll[i].moduleId;
				if(modIdThi == moduleMenuId){
					assignUumUserCommfunList.push(thirdListAll[i]);
				}
			}
		}
		//如果headerMainFrame = winxp,则不显示outlook
		if(basicConstant.WINXP != navigateMainFrame){
			/**
		     * 点击系统菜单改变outlook树结构
		     */
			var leftMenu = getExtCmpById(basicConstant.LEFT_MENU_ID);//outlook菜单
			var commFunPanel = leftMenu.items.items[0];//常用功能panel
			commFunPanel.remove(commFunPanel.items.items[0]);//常用功能的树
		    var dynamicChangeTree = new com.bhtec.view.viewport.general.DynamicChangeTree(outlookBarMainFrame);
		    var commFunModuleTree = dynamicChangeTree.commFunModulePanel();//构造常用功能
		    commFunPanel.add(commFunModuleTree);
		    var viewPort = getExtCmpById(basicConstant.FRAME_VIEW_PORT_ID);//获得viewport
			viewPort.show();//重新show
			viewPort.doLayout();//重新布局
		}else{
			//下拉菜单控件
			var userCommFunMenu = getExtCmpById('userCommFunMenuId');
			userCommFunMenu.menu.items.each(function(item){//删除菜单所有组件
						userCommFunMenu.menu.remove(item);
			});
			var navBar = com.bhtec.view.viewport.winxp.NavBar();
			var commFunModulePanel = navBar.commFunModulePanel();//构造常用功能
			userCommFunMenu.menu.add(commFunModulePanel);
		}
		getExtCmpById('selectedToId').close();
	}
	
	new com.bhtec.view.util.ux.MultiSelect({
		winTitle : '常用功能菜单分配',
		leftPanel : treePanel,
		rightPanel : multiSelPanel,
		headerPanel : commFunLabel,
		selected : selected,
		unselected : unselected,
		isShowWindow:true,
		buttons : [commWidgets.saveButton({
			handler : function(b) {
				var mul = getExtCmpById('commFunMenuMultiSel');
				var flag = mul.store.getCount();
				if (flag == 0) {
					warningMesg({
								msg : '请分配一个常用功能菜单!'
							})
				} else {
					var moduleIdList = new Array();
					for (var j = 0; j < mul.store.getCount(); j++) {
						var moduleMenuId = mul.store.getAt(j).get("code");
						moduleIdList.push(moduleMenuId);
					}
					b.setDisabled(true);
					ajaxRequest({
						url : 'userCommFunAction!saveUserCommFun.action',
						params : {
							moduleIdList : moduleIdList
						},
						callBack : function(returnData) {
							showSucMesg({
								msg : '常用功能菜单分配成功!',
								fn : function(confirm) {
									if ('ok' == confirm) {
										dynamicLoadCommFun(moduleIdList);//动态装载常用功能菜单
									}
								}
							});
						}
					});
				}
			}
		}), commWidgets.closeButton({
					handler : function() {
						this.ownerCt.ownerCt.ownerCt.close();
					}
				})]
	});

}
Ext.extend(com.bhtec.view.business.commonused.commonfuntion.UserCommonFunction,
		com.bhtec.view.util.CommonWidgets, {});