/**
 * viewport导航条
 * @author lianglp
 * @version 1.0
 * @class com.bhtec.view.viewport.BhtMainPage
 * @date 2010-05-30
 */

$import('./js/com/bhtec/view/viewport/general/Portal', 'js');
$import('./ext/ux/panel/TabCloseMenu', 'js');
Ext.namespace('com.bhtec.view.viewport.winxp');
com.bhtec.view.viewport.winxp.MainPage = function(config) {
	var mainPagePanel = {
		xtype : 'tabpanel',
		id:basicConstant.MAIN_PAGE_ID,
		enableTabScroll:true,
		region:'center',
		activeTab:0,
		frame:false,
		border: false,
        items:[{
            title: '首页',
            iconCls:'home',
            border: false,
            autoScroll:true,
            layout:'fit',
            items:[new com.bhtec.view.viewport.general.Portal()]
        }],
        plugins: new Ext.ux.TabCloseMenu()
	}
	return mainPagePanel;
}