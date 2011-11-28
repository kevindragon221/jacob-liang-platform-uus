/**
 * viewport头
 * @author lianglp
 * @version 1.0
 * @class com.bhtec.view.viewport.BhtHeader
 * @date 2010-05-30
 */
Ext.namespace('com.bhtec.view.viewport.winxp');
com.bhtec.view.viewport.winxp.Header = function(config) {
	
	var headerPanel = new Ext.Panel({
		xtype : 'panel',
		region:'north',
		height:	130,
		html: "<iframe name='headerifr' src=html/winxp_header.html width=100% height=100%></iframe>",
		bbar:config.navigate
	})

	return headerPanel;
}