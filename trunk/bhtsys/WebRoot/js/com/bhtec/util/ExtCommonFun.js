/**
 * 表单重置
 */
function resetForm(formId) {
	Ext.getCmp(formId).form.reset();
}

/**
 * 表单校验
 */
function formValid(formId) {
	var formValidBol = Ext.getCmp(formId).form.isValid();
	return formValidBol;
}

/**
 * debug函数
 * 
 * @param {}
 * mesg alert消息
 * isDebug true false
 */
function debug(mesg,isDebug){
	if(isDebug == undefined)isDebug = true;
	if(basicConstant.DEBUG_FLAG && isDebug){
		Ext.MessageBox.show({
			title:'调示信息',
			msg:'调示信息...',
			prompt:true,
			multiline : true,
			width:300, 
			value:mesg.toString(),
			buttons:Ext.Msg.OK
		});
	}
}

/**
 * 操作成功提示
 */
function showSucMesg(config) {
	Ext.Msg.show({
				title : config.title||'提示',
				msg : config.msg||'',
				width:300,
				buttons : Ext.Msg.OK,
				icon : Ext.MessageBox.INFO,
				fn : config.fn||''
			});
}

/**
 * 操作失败提示
 */
function showFailMesg(config) {
	Ext.Msg.show({
				title : config.title||'提示',
				value : config.msg||'',
				minWidth :config.width||300,
				msg:config.msg||'操作失败!原因:',
				multiline : true,
				defaultTextHeight :30,
				prompt:true,
				buttons : Ext.Msg.OK,
				icon : Ext.MessageBox.ERROR,
				fn : config.fn||''
			});
}

/**
 * 询问提示
 */
function askMesg(config) {
	Ext.Msg.show({
				title : config.title||'提示',
				msg : config.msg||'',
				width:300,
				buttons : Ext.Msg.OKCANCEL,
				icon : Ext.MessageBox.QUESTION,
				fn : config.fn||''
			});
}

/**
 * 警告提示
 */
function warningMesg(config) {
	Ext.Msg.show({
				title : config.title||'提示',
				msg : config.msg||'',
				width:300,
				buttons : Ext.Msg.OK,
				icon : Ext.Msg.WARNING,
				fn : config.fn||''
			});
}

function loadMask(config){
	var loadMask = new Ext.LoadMask(Ext.getBody(), {
									msg : config.msg||"数据处理中,请稍候..."
				});
	return loadMask;
}
/**
 * ajax请求，公用
 */
function ajaxRequest(config) {
	//提交等待提示
	var mask;
	if(config.noMask == undefined){
		mask = loadMask(config);
		mask.show();
	}
	Ext.Ajax.request({
				url : config.url||'',
				params : config.params||'',
				success : function(response, options) {
							var returnData = Ext.util.JSON.decode(response.responseText);
							if(returnData){
								if(returnData.sessionstatus == 'timeout')return;//是否超时
								if(ajaxRequestResult(returnData) == false)return;//操作是否成功
							}
							var callMethod = function(method){        
						        method(returnData);        
						    }
							callMethod(config.callBack);//提交成功回调
							if(config.noMask == undefined)
								mask.hide();
				},
				failure : function(response, options) {
					showFailMesg({
						msg:'请联系管理员.失败类型：'+response.statusText
					});
					if(config.noMask == undefined)
						mask.hide();
				}
			})
}

/**
 * ajax提交返回结果
 * @param {} response
 * @return {Boolean}
 */
function ajaxRequestResult(returnData){
	var failMesg = returnData.failMesg;
	if(failMesg == undefined)return true;
	if(failMesg == 'error'){
		showFailMesg({
			msg:'请联系管理员!'
		});
		return false;
	}else{
		failMesg = failMesg.replace('com.bhtec.exception.ApplicationException:','');
		showFailMesg({
			msg:failMesg
		});
		return false;
	} 
	return true;
}

/**
 * 修改删除页面选择
 */
function modifyDelSelRecord(cmpId) {
	var record = '';
	var count = Ext.getCmp(cmpId).getSelectionModel().getSelections().length;
	if (count == 0 || count > 1) {
		showSucMesg({
						msg:'请选择一条记录!'
					});
		return record;
	}
	return Ext.getCmp(cmpId).getSelectionModel().getSelected().data;
}
/**
 * 操作时判断是否选择了记录
 * @param {} cmpId
 */
function isSelRecord(cmpId) {
	var record = '';
	var count = Ext.getCmp(cmpId).getSelectionModel().getSelections().length;
	if (count == 0 ) {
		showSucMesg({
						msg:'请选择一条或多条记录!'
					});
		return false;
	}
	return true;
}

/**
 * form操作操作
 */
function submitForm(config) {
	var subForm = Ext.getCmp(config.formId||basicConstant.FORM_ID);
	//emptyText 清空
	var submitValues = subForm.getForm().getValues();
	for (var param in submitValues) {
		if (subForm.form.findField(param)
				&& subForm.form.findField(param).emptyText == submitValues[param]) {
			subForm.form.findField(param).setRawValue('');
		}
	}

	if (subForm) {
		if (formValid(config.formId||basicConstant.FORM_ID)) {
			var mask = loadMask({});
			mask.show();
			subForm.form.doAction('submit', {
						url : config.url,
						params : config.params||'',
						clientValidation : true,
						method : 'POST',
						success : function(response, options) {
							var returnData = Ext.util.JSON.decode(options.response.responseText);
							var callMethod = function(method){        
						        method(returnData);        
						    }
							callMethod(config.callBack);//提交成功回调
							mask.hide();
						},
						failure : function(response, options) {
							var returnData = Ext.util.JSON.decode(options.response.responseText);
							if(returnData){
								if(returnData.sessionstatus == 'timeout')return;//是否超时
								ajaxRequestResult(returnData);//操作是否成功
							}else{
								showFailMesg({
									msg:'操作失败!请联系管理员.'
								});
							}
							mask.hide();
						}
					});
		}
	}

}

/**
 * 刷新列表
 */
function refreshGridList(gridId) {
	if(gridId != ''){
		grid = Ext.getCmp(gridId);
		store = grid.store;
		store.reload();
	}
}

/**
 * 查询填充列表
 */
function queryFillGridList(gridId,returnData) {
	if(gridId != ''){
		var gridList = getExtCmpById(gridId);
		var gridStore = gridList.store;
		gridStore.loadData(returnData, false);
	}
}
/**
 * grid获得选择的记录ids
 * @param {} idName
 * @param {} gridId
 * @return {}
 */
function getDelRedIds(idName,gridId){
		var records = Ext.getCmp(gridId).getSelectionModel().getSelections();
		var delRecIds = '';
		for(i=0;i<records.length;i++){
			var recdId = eval('records['+i+'].data.'+idName);
			delRecIds = delRecIds==''?recdId:delRecIds+','+recdId; 
		}
		return delRecIds;
}

/**
 * grid获得选择的记录ids
 * @param {} idName
 * @param {} gridId
 * @return {}list
 */
function getColumnRecordIds(idName,gridId){
		var records = Ext.getCmp(gridId).getSelectionModel().getSelections();
		var colRecIds = new Array();
		for(i=0;i<records.length;i++){
			var recdId = eval('records['+i+'].data.'+idName);
			colRecIds.push(recdId);
		}
		return colRecIds;
}

/**
 * grid获得多选择ids
 * @param {} idName
 * @param {} gridId
 * @return {}
 */
function getMultiSelectedRedIds(idName,gridId){
		var records = Ext.getCmp(gridId).getSelectionModel().getSelections();
		var multiSelectedRedIds = '';
		for(i=0;i<records.length;i++){
			var recdId = eval('records['+i+'].data.'+idName);
			multiSelectedRedIds = multiSelectedRedIds==''?recdId:multiSelectedRedIds+','+recdId; 
		}
		return multiSelectedRedIds;
}

/**
 * 获得ext组件
 */
function getExtCmpById(id){
	return Ext.getCmp(id);
}

/**
 * 获得ext组件值
 */
function getExtCmpValueById(id){
	return Ext.getCmp(id).getValue();
}

/**
 * 将ext组件置空
 */
function resetCmpValueById(id){
	return Ext.getCmp(id).setValue('');
}

/**
 *  根据ext组件id,设置值
 */
function setCmpValueById(id,value){
	return Ext.getCmp(id).setValue(value);
}

/**
 * 格式化日期格式Y-M-D
 * @param {} value
 * @return {}
 */
function formatDateToYMD(value){
		if(value != null){
		     return value.substr(0,10);
		}
}
/**
 * 格式化日期格式Y-M-D H:I:S
 * @param {} value
 * @return {}
 */
function formatDateToYMDHIS(value){
		if(value != null){
			return Ext.util.Format.date(value,'Y-m-d h:i:s');
		}
}

/**
 * 为表单增加回车事件
 * @param {}
 */
function formEnterEvent(event) {
		var all = Ext.DomQuery.select('input[type!=hidden] '); // 查找所有非隐藏的录入向（ext中select都是用input模拟的所以这里不用找select）
		// 遍历并添加enter的监听
		Ext.each(all, function(o, i, all) {
					Ext.get(o).addKeyMap({
						key : 13,
						fn : function() {
							try {
								if((i+1) < all.length){
									var n = Ext.getCmp(all[i+1].id);
									Ext.getCmp(all[i+1].id).focus();
								}
							} catch (e) {
								Ext.getCmp(all[i+1].id).focus();
							}
							return true;
						}
					})
				});
		document.body.focus(); // 使页面获取焦点，否则下面设定默认焦点的功能有时不灵验
	}

function getRootWin(){      
    var win = window;      
    while (win != win.parent){      
        win = win.parent;      
    }      
    return win;      
}
/**
 * 重新登录
 */
function reLogin(){
	getRootWin().close();
    window.open('login.html');
}
/**
 * ajax请求后检查session是否失效
 */
function checkUserSessionStatus(mesg){   
	if(mesg.sessionstatus == 'timeout'){   
		askMesg({title:'提示', 
				 msg:'对不起,会话失效.请重新登录.', 
				 fn:function(confirm){
					if('ok' == confirm)
                     	reLogin();
                 }
		}); 
		return true;
	}   
	return false;
}

//Ext.Ajax.on('requestcomplete',checkUserSession, this);   
function checkUserSession(conn,response,options){ 
	var data = Ext.util.JSON.decode(response.responseText);
	checkUserSessionStatus(data);
} 

Ext.apply(Ext.form.VTypes,{
		dateRange:function(val,field){
			if(field.dateRange){
				var beginId = field.dateRange.begin;
				this.beginField = Ext.getCmp(beginId);
				var endId = field.dateRange.end;
				this.endField = Ext.getCmp(endId);
				var beginDate = this.beginField.getValue();
				var endDate = this.endField.getValue();
			}
			if(beginDate <= endDate){
				return true;
			}else{
				this.beginField.focus();
				this.endField.focus();
				return false;
			}
		},
		dateRangeText:'开始时间不能大于结束时间'
	});
	
/*Ext.Ajax.on('beforerequest',function(){
});*/
