/**
 * 公用附件页面
 * @author lianglp
 * @version 1.0
 * @class com.bhtec.view.util.Accessory
 * @date 2012-01-11
 */
Ext.namespace('com.bhtec.view.util');
com.bhtec.view.util.Accessory = function(config) {
	//附件数量
	var accessoryCount = 4;
	/**
	 * 附件组件
	 */
	var formId = config.formId||Math.random();
	var accessoryLabelName = config.accessoryLabelName||'附件';//附件标签名
	var accessoryListId = config.accessoryListId||'';//附件列表ID
	var accessoryList = config.accessoryList||[];//已经上传附件
	var filePath = config.filePath||'';//附件存储路径
	var columnFields = new Array();//返回附件列表
	var viewAccessory = config.viewAccessory;//察看隐藏附件删除功能
	/**
	 * 删除附件
	 * @param {附件ID} accessoryId
	 * @param {附件列表ID} accessoryListId
	 */
	var delAccessory = function(accessoryId){
			var accessoryList = document.getElementById(accessoryListId);
			var accessory = document.getElementById(accessoryId+'_div');
			if(accessory != null)
				accessoryList.removeChild(accessory);
	}
	/**
	 * 添加附件信息到附件列表
	 * @param {附件ID} accessoryId
	 * @param {附件URL} accessoryUrl
	 */
	var addAccessoryToList = function(accessoryId,accessoryUrl){
				if(document.getElementById(accessoryId+'_div') == null){
					var suffixFlag = accessoryUrl.lastIndexOf('.');
					var fileNameFlag = accessoryUrl.lastIndexOf('\\');
					var fileName = '';
					if(suffixFlag < 0){
						fileName = accessoryUrl.substr(fileNameFlag+1);
					}else{
						fileName = accessoryUrl.substr(fileNameFlag+1,suffixFlag);
					}
					var suffixName = accessoryUrl.substr(suffixFlag+1);
					var imgPictureUrl = './img/button/withoutformat.png';
					if(suffixName == 'docx' 
						|| suffixName == 'doc'){
							imgPictureUrl = './img/button/word.png';
					}else if(suffixName == 'pdf'){
							imgPictureUrl = './img/button/pdf.png';
					}else if(suffixName == 'pptx' 
						|| suffixName == 'ppt'){
							imgPictureUrl = './img/button/powerpoint.png';
					}else if(suffixName == 'xlsx' 
						|| suffixName == 'xls'){
							imgPictureUrl = './img/button/excel.png';
					}
					var newAccessory =	'<img src="'+imgPictureUrl+'"/>' +fileName+' ';
					//创建新div
					var accessoryList = document.getElementById(accessoryListId);
					var newDiv = document.createElement("div");
					newDiv.setAttribute('id',accessoryId+'_div');//创建用户div
					newDiv.style.display = 'inline';
					newDiv.style.float = 'center';//div横排
					accessoryList.appendChild (newDiv);
					//添加链接     	  
					newDiv.innerHTML = newAccessory;
				}
	}
	/**
	 * 加附件组件控件
	 * @param {附件label} fieldLabel
	 * @param {附件列表 ID} accessoryListId
	 */
	
	var addAccessoryCmp = function(fieldLabel){
				var columnAccessoryField = new Array();
				accessoryCount = accessoryCount++;
				var accessoryCountStr = 'accessoryUpload'+accessoryCount;
				fieldLabel = fieldLabel + accessoryCount;
				var uploadForm = Ext.getCmp(formId);
				var clearButton = {
					xtype:'button',
					text:'清除',
					iconCls:'close',
					handler:function(){
						var afficheAccessoryDom = Ext.getDom(accessoryCountStr);						
					 	afficheAccessoryDom.select();   
						document.selection.clear();
						delAccessory(accessoryCountStr);
					}
				}
				var uploadFileCmp = new Ext.form.TextField({
								id:accessoryCountStr,
								name:'accessoryUpload',
								fieldLabel : fieldLabel,
								maxLength:'5000',
								width:300,
								inputType : "file",
								listeners :{
									'focus':function(field){
										if(field.getValue() != ''){
											addAccessoryToList(accessoryCountStr,field.getValue());
										}
									}
								}
							});
					columnAccessoryField.push({
						border : false,
						layout : "form",
						columnWidth : .51,
						items : uploadFileCmp
					});
					columnAccessoryField.push({
						border : false,
						layout : "form",
						columnWidth : .09,
						items : clearButton
					});
					columnAccessoryField.push({
						border : false,
						layout : "form",
						columnWidth : .4
					});
				uploadForm.items.items[0].add(columnAccessoryField);
				uploadForm.items.items[0].doLayout();//重新布局
	}
	
	//上传附件按钮
	var uploadButton = {
		xtype:'button',
		text:'更多附件',
		iconCls : 'login',
		handler:function(){
			addAccessoryCmp(accessoryLabelName);
		}
	}
	var accessoryDiv = '';
	for(var i=0;i<accessoryList.length;i++){
		var accessoryId = accessoryList[i].accessoryId;
		var accessoryName = accessoryList[i].accessoryName;
		var oldFileName = accessoryName;
		var splitFlag = accessoryName.indexOf('_');
		accessoryName = accessoryName.substr(splitFlag+1);
		var suffixName = accessoryName.substr(accessoryName.lastIndexOf('.')+1);
		var imgPictureUrl = './img/button/withoutformat.png';
		if(suffixName == 'docx' 
			|| suffixName == 'doc'){
				imgPictureUrl = './img/button/word.png';
		}else if(suffixName == 'pdf'){
				imgPictureUrl = './img/button/pdf.png';
		}else if(suffixName == 'pptx' 
			|| suffixName == 'ppt'){
				imgPictureUrl = './img/button/powerpoint.png';
		}else if(suffixName == 'xlsx' 
			|| suffixName == 'xls'){
				imgPictureUrl = './img/button/excel.png';
		}
		var url = "accessoryActionDef!downloadFile.action?accessoryPath="+filePath
				 +"&fileName="+accessoryName
				 +"&oldFileName="+oldFileName
				 +"&accessoryId="+accessoryId;
		var newAccessory = '';
		if(viewAccessory){
			 newAccessory =	
						    "&nbsp;" +
						    "<a href="+url+"><img src='"+imgPictureUrl+"'/>" +accessoryName+"</a>";
		}else{
			 newAccessory =	"<a href=JavaScript:deleteAccessory('"+accessoryId+"','"+accessoryListId+"','"+filePath+"','"+oldFileName+"') >" +
						    "<img src='./img/button/close.png'/></a>&nbsp;" +
						    "<a href="+url+"><img src='"+imgPictureUrl+"'/>" +accessoryName+"</a>";
		}
		accessoryDiv += "<div id='"+accessoryId+"' style='display:inline;float:center;'>"+newAccessory+"</div>";
	}
	//已上传的附件列表
	var accessoryList = {
		xtype:'panel',
		layout:'form',
		border:false,
		items:[{
				xtype:'label',
				html:"附件列表: &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
				+"<div id='"+accessoryListId+"' style='display:inline;float:center;'>"
				+accessoryDiv
				+"</div>"
			}]
	}
	columnFields.push({
					border : false,
					layout : "form",
					columnWidth : 1,
					items : accessoryList
				});
	//附件
	for(var i=1;i<4;i++){
			 var accId = 'accessoryUpload'+i;
			 var afficheAccessory = function(accessoryId){
				 return new Ext.form.TextField({
			 		id:accId,
					name:'accessoryUpload',
					fieldLabel : accessoryLabelName+i,
					maxLength:'5000',
					width:300,
					inputType : "file",
					listeners :{
						'focus':function(field){
							if(field.getValue() != ''){
								addAccessoryToList(accessoryId,field.getValue());
							}
						}
					}
				});
			 }
			//清除附件
			var clearFile = function(accessoryId){
				return function(){
					var afficheAccessoryDom = Ext.getDom(accessoryId);						
				 	afficheAccessoryDom.select();   
					document.selection.clear();
					delAccessory(accessoryId);
				}
			}
			var clearButton = {
				xtype:'button',
				text:'清除',
				iconCls:'close',
				handler:clearFile(accId)
			}
			
			columnFields.push({
				border : false,
				layout : "form",
				columnWidth : .51,
				items : afficheAccessory(accId)
			});
			columnFields.push({
				border : false,
				layout : "form",
				columnWidth : .09,
				items : clearButton
			});
			if(i==1){
				columnFields.push({
					border : false,
					layout : "form",
					columnWidth : .4,
					items : uploadButton
				});
			}else{
				columnFields.push({
					border : false,
					layout : "form",
					columnWidth : .4
				});
			}
	}
	return columnFields;
}
