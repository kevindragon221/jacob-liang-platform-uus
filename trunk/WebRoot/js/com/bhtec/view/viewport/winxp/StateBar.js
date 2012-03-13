/**
 * viewport导航条
 * @author lianglp
 * @version 1.0
 * @class com.bhtec.view.viewport.BhtStateBar
 * @date 2010-05-30
 */
Ext.namespace('com.bhtec.view.viewport.winxp');
com.bhtec.view.viewport.winxp.StateBar = function(config) {
	
	var stateBarPanel = {
		xtype : 'panel',
		region:'south',
		height:Ext.getBody().getHeight()*0.05,
		frame:true,
		html: "<iframe name='statebarifr' frameBorder=0 src='page/html/winxp_statebar.html' width=99.8% height=99.8% ></iframe>"
	}
	
	return stateBarPanel;
}