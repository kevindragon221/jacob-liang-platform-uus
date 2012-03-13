/**
 * viewport导航条
 * @author lianglp
 * @version 1.0
 * @class com.bhtec.view.viewport.BhtStateBar
 * @date 2010-05-30
 */
$import('./ext/ux/portal/MaximizeTool', 'js');
$import('./ext/ux/portal/Portal', 'js');
$import('./ext/ux/portal/PortalColumn', 'js');
$import('./ext/ux/portal/Portlet', 'js');


Ext.namespace('com.bhtec.view.viewport.general');
com.bhtec.view.viewport.general.Portal = function(config) {
    // create some portlet tools using built in Ext tool ids
    var tools = [{
        id:'close',
        qtip : '关闭',
        handler: function(e, target, panel){
            panel.ownerCt.remove(panel, true);
        }
    }];
    
    var htmlSrc = 'test';
    var portalHeight = Ext.getBody().getHeight()*0.23
	var portal = {
            xtype:'portal',
            region:'center',
            bodyStyle : basicConstant.PAGE_BACKGROUND,
            margins:'35 5 5 0',            
            items:[{
                columnWidth:.33,
                style:'padding:20px 0 10px 10px',
                defaults: {
                	height: portalHeight
                },
                
                items:[{
                    title: '销售业绩曲线图',
                    layout:'fit',
                    tools: tools,
                    items:[{xtype : 'panel',title:'test',html:htmlSrc}]
                },{
                    title: '进货管理',
                    autoScroll:true,
                	tools: tools,	
                    items:[]
                },{
                    title: '待办事宜',
                    tools: tools,
                    html: htmlSrc
                }]
            },{
                columnWidth:.33,
                style:'padding:20px 0 10px 10px',
                defaults: {
                	height: portalHeight
                },
                items:[{
                    title: '商品进价曲线图',
                    tools: tools,
                    html: htmlSrc
                },{
                    title: '出货管理',
                    tools: tools,
                    html: htmlSrc
                },{
                    title: '论坛',
                    tools: tools,
                    html: htmlSrc
                }]
            },{
                columnWidth:.33,
                style:'padding:20px 0 10px 10px',
                defaults: {
                	height: portalHeight
                },
                items:[{
                    title: '日历',
                    tools: tools,
                    html: htmlSrc
                },{
                    title: '短消息',
                    tools: tools,
                    html: htmlSrc
                },{
                    title: '备忘录',
                    tools: tools,
                    html: htmlSrc
                }]
            }]
            
        }
        return portal;
}