/**
 * viewport头
 * @author lianglp
 * @version 1.0
 * @class com.bhtec.view.viewport.BhtHeader
 * @date 2010-05-30
 */
Ext.namespace('com.bhtec.view.viewport.general');
com.bhtec.view.viewport.general.Header = function(config) {
	
	var headerPanel = {
		xtype : 'panel',
		region:'north',
		height:Ext.getBody().getHeight()*0.15,
		border:true,
		html: "<iframe name='headerifr' frameBorder=0 src=page/html/general_header.html width=100% height=100%></iframe>",
		bbar:config.navigate
	}
	return headerPanel;
}