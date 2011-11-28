/**
 * 动态改变树结构
 * @author lianglp
 * @version 1.0
 * @class com.bhtec.view.viewport.winxp
 * @date 2010-08-21
 */
Ext.namespace('com.bhtec.view.viewport.winxp');
com.bhtec.view.viewport.winxp.DynamicChangeTree = function(){
	/**
	 * outlook菜单树
	 */
	var treeMenu = function(menuRoot){
		var treePanel = {
			xtype:'treepanel',
			root:menuRoot,
			autoHeight:true,
			border:false,
			enableDD:false,
			autoScroll : true,
			rootVisible:false,
			lines:false,
			listeners:{
				click:function(node,evt){
					var configNode = {
						id:node.id,
						iconCls:node.attributes.iconCls,
						text:node.text
					};
					//标签面板
					var tab = getExtCmpById(node.id+'_tab');
					if(tab){
						//主页面板
						var mainPagePanel = getExtCmpById('mainPagePanelId');
						mainPagePanel.setActiveTab(tab);
					}else{
						var memuControl = new com.bhtec.control.MenuControl(configNode);
						memuControl.addTab();
					}
				}
			}
		};
		return treePanel;
	}
	/**
	 * 常用功能菜单
	 */
	var commFunModulePanel = function(){
		//常用功能根结点
		var commFunMenuRoot = new Ext.tree.TreeNode({
			text:'root'
		});
		for(i=0;i<assignUumUserCommfunList.length;i++){
			var commFunNode = new Ext.tree.TreeNode({
				id:assignUumUserCommfunList[i].modEnId,
				text:assignUumUserCommfunList[i].modName,
				iconCls:assignUumUserCommfunList[i].modImgCls
			});
			commFunMenuRoot.appendChild(commFunNode);
		}
		var commFunModuleTree = treeMenu(commFunMenuRoot);
		return commFunModuleTree;
		//--------常用功能结束-----------------------------
	}
	
	/**
     * 系统导航栏菜单，系统菜单为动态
     */
    var sysSecThiMenu = function(sysMenuId){
    	var secThiMenuArr = new Array();
    	var commFunModuleTree = commFunModulePanel();//常用功能
    	secThiMenuArr.push({
					xtype:'panel',
			    	frame:true,
			    	title: '常用功能',
			    	iconCls:'usedfuncmaint',
			    	collapsible:true,
			    	animCollapse :true,
			    	collapsed :false,
			    	titleCollapse: true,
			    	autoScroll:true,
			    	items:commFunModuleTree
		});
        
    	var secondList = secondMenu[sysMenuId];//map取值,
    	if(secondList && secondList != ''){//二级菜单
			for(i=0;i<secondList.length;i++){
				var moduleId = secondList[i].moduleId;
				var modName = secondList[i].modName;
				var modImgCls = secondList[i].modImgCls;
				//菜单根结点
				var menuRoot = new Ext.tree.TreeNode({
					text:'root'
				});
				var thirdList = thirdMap[moduleId];
				if(thirdList && thirdList != ''){					
					for(j=0;j<thirdList.length;j++){//三级菜单
						var modEnIdThi = thirdList[j].modEnId;
						var modNameThi = thirdList[j].modName;
						var modImgClsThi = thirdList[j].modImgCls;
						//创建结点
						var node = new Ext.tree.TreeNode({
							id:modEnIdThi,
							iconCls:modImgClsThi,
							text:modNameThi
						});	
						menuRoot.appendChild(node);
					}
				}
				//构造树
				var moduleTree = treeMenu(menuRoot);
				var menuPanel = {
					xtype:'panel',
					autoWidth:true,
			    	frame:true,
			    	title: modName,
			    	iconCls:modImgCls,
			    	collapsible:true,
			    	animCollapse :true,
//			    	collapsed :true,
			    	titleCollapse: true,
			    	items:moduleTree
			    };
				secThiMenuArr.push(menuPanel);
			}
    	}
    	return secThiMenuArr;
    }
    
    return {
    	sysSecThiMenu:sysSecThiMenu,
    	commFunModulePanel:commFunModulePanel
    }
}