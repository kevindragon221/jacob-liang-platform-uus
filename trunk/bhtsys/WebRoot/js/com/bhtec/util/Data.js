/**
 * data常用数据
 * @author lianglp
 * @version 1.0
 * @class com.bhtec.util.Data
 * @date 2010-05-30
 */
Ext.namespace('com.bhtec.util.Data');
/**
 * 页面风格样式
 * @type 
 */
com.bhtec.util.Data.themes = [
		[0,'默认风格','ext-all'],
		[1,'深蓝风格','xtheme-slate'],
		[2,'黑色风格','xtheme-black'],
		[3,'银白风格','xtheme-gray'],
		[4,'绿色风格','xtheme-olive'],
		[5,'灰色风格','xtheme-darkgray'],
		[6,'紫色风格','xtheme-purple'],
		[7,'棕色风格','xtheme-human']
]

com.bhtec.util.Data.status = [{
			status 		: 'enable',
			statusName  : '启用'
		}, {
			status 		: 'disable',
			statusName  : '停用'
		}]

com.bhtec.util.Data.statusFields = ['status','statusName']

com.bhtec.util.Data.systemType = [{
			system 	   : 'platform',
			systemName  : '系统平台'
		},{
			system 	   : 'uum',
			systemName  : '统一用户'
		}]

com.bhtec.util.Data.systemTypeFields = ['system','systemName']

com.bhtec.util.Data.modPageType = [{
			pageType 	  : 'js',
			pageTypeName  : 'js'
		},{
			pageType 	  : 'jsp',
			pageTypeName  : 'jsp'
		},{
			pageType 	  : 'html',
			pageTypeName  : 'html'
		},{
			pageType 	  : 'flex',
			pageTypeName  : 'flex'
		}]

com.bhtec.util.Data.modPageTypeFields = ['pageType','pageTypeName']

com.bhtec.util.Data.moduleLevel = [{
			level 	   : '1',
			levelName  : '第一'
		}, {
			level 	   : '2',
			levelName  : '第二'
		}, {
			level 	   : '3',
			levelName  : '第三'
		}]

com.bhtec.util.Data.moduleLevelFields = ['level','levelName']
/**
 * 页面功能区
 * @type 
 */
com.bhtec.util.Data.funArea = [{
			funArea 	 : 'header',
			funAreaName  : '页面头部'
		}, {
			funArea 	 : 'navigate',
			funAreaName  : '页面导航菜单'
		}, {
			funArea 	 : 'outlookBar',
			funAreaName  : '页面左侧菜单'
		}, {
			funArea 	 : 'mainPage',
			funAreaName  : '主页面'
		}, {
			funArea 	 : 'stateBar',
			funAreaName  : '页面状态栏'
		}]

com.bhtec.util.Data.funAreaFields = ['funArea','funAreaName']
/**
 * 页面样式
 * @type 
 */
com.bhtec.util.Data.pageStyle = [{
			pageStyle 	 : 'general',
			pageStyleName  : 'general'
		}, {
			pageStyle 	 : 'winxp',
			pageStyleName  : 'winxp'
		}]

com.bhtec.util.Data.pageStyleFields = ['pageStyle','pageStyleName']

com.bhtec.util.Data.number = [{
			num 	 : 1,
			numName  : '1'
		}, {
			num 	 : 2,
			numName  : '2'
		}, {
			num 	 : 3,
			numName  : '3'
		}, {
			num 	 : 4,
			numName  : '4'
		}, {
			num 	 : 5,
			numName  : '5'
		}, {
			num 	 : 6,
			numName  : '6'
		}, {
			num 	 : 7,
			numName  : '7'
		}, {
			num 	 : 8,
			numName  : '8'
		}, {
			num 	 : 9,
			numName  : '9'
		}]

com.bhtec.util.Data.numberFields = ['num','numName'];
/**
 * 机构类型临时
 * @type 
 */
com.bhtec.util.Data.gender = [{
			genderId   : 0,
			genderDes  : '男'
		}, {
			genderId   : 1,
			genderDes  : '女'
		}]

com.bhtec.util.Data.genderFields = ['genderId','genderDes']

/**
 * 机构类型临时
 * @type 
 */
com.bhtec.util.Data.logSaveTime = [{
			days 	  : -1,
			daysName  : '不设置'
		},{
			days 	  : 3,
			daysName  : '3天'
		}, {
			days 	  : 7,
			daysName  : '7天'
		}, {
			days 	  : 15,
			daysName  : '15天'
		}, {
			days 	  : 30,
			daysName  : '30天'
		}, {
			days 	  : 60,
			daysName  : '60天'
		}, {
			days 	  : 90,
			daysName  : '90天'
		}, {
			days 	  : 180,
			daysName  : '180天'
		}, {
			days 	  : 360,
			daysName  : '360天'
		}]

com.bhtec.util.Data.logSaveTimeFields = ['days','daysName']
//编码段数
com.bhtec.util.Data.partNum = [{
			partNum 	 : 1,
			partNumName  : '1'
		}, {
			partNum 	 : 2,
			partNumName  : '2'
		}, {
			partNum 	 : 3,
			partNumName  : '3'
		}, {
			partNum 	 : 4,
			partNumName  : '4'
		}]

com.bhtec.util.Data.partNumFields = ['partNum','partNumName'];
//分隔符
com.bhtec.util.Data.delimite = [{
			delimite 	 : '-',
			delimiteName  : '-'
		}, {
			delimite 	 : '_',
			delimiteName  : '_'
		}, {
			delimite 	 : ':',
			delimiteName  : ':'
		}, {
			delimite 	 : '/',
			delimiteName  : '/'
		}, {
			delimite 	 : '|',
			delimiteName  : '|'
		}]

com.bhtec.util.Data.delimiteFields = ['delimite','delimiteName'];
//编码格式
com.bhtec.util.Data.codeFormat = [{
			codeFormat 	 : 'char',
			codeFormatName  : '字符'
		}, {
			codeFormat 	 : 'date',
			codeFormatName  : '日期'
		}, {
			codeFormat 	 : 'number',
			codeFormatName  : '序号'
		}, {
			codeFormat 	 : 'sysPara',
			codeFormatName  : '系统参数'
		}]

com.bhtec.util.Data.codeFormatFields = ['codeFormat','codeFormatName'];
//编码-日期格式
com.bhtec.util.Data.dateFormat = [{
			codeFormatContentValue 	 : 'yyyy',
			codeFormatContentName  : '年(yyyy)'
		}, {
			codeFormatContentValue 	 : 'yyyyMM',
			codeFormatContentName  : '年月(yyyyMM)'
		}, {
			codeFormatContentValue 	 : 'yyyyMMdd',
			codeFormatContentName  : '年月日(yyyyMMdd)'
		}]
//编码-序号格式
com.bhtec.util.Data.numberFormat = [{
			codeFormatContentValue 	 : '01',
			codeFormatContentName  : '两位(01)'
		}, {
			codeFormatContentValue 	 : '001',
			codeFormatContentName  : '三位(001)'
		}, {
			codeFormatContentValue 	 : '0001',
			codeFormatContentName  : '四位(0001)'
		}, {
			codeFormatContentValue 	 : '00001',
			codeFormatContentName  : '五位(00001)'
		}, {
			codeFormatContentValue 	 : '000001',
			codeFormatContentName  : '六位(000001)'
		}]
//编码-系统参数格式
com.bhtec.util.Data.sysParaFormat = [{
			codeFormatContentValue 	 : 'userName',
			codeFormatContentName  : '用户名'
		}, {
			codeFormatContentValue 	 : 'roleUser',
			codeFormatContentName  : '角色+用户名'
		}, {
			codeFormatContentValue 	 : 'organRoleUser',
			codeFormatContentName  : '机构+角色+用户名'
		}]
//非列表页面		
com.bhtec.util.Data.notListViewArr = ['sysConMgr']
//24 hours
com.bhtec.util.Data.hour = function(){
	var hours = new Array;
	for(var i=0;i<24;i++){
		var h = {
			hour 	 : i,
			hourName  : i
		}
		hours.push(h);
	}
	return hours;
}
com.bhtec.util.Data.hourFields = ['hour','hourName']
		