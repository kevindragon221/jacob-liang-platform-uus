//加载所需dojo类库
dojo.require("dojox.cometd");
dojo.require("dojox.cometd.timestamp");
dojo.require("dojox.cometd.ack");
Ext.namespace('com.bhtec.view.util.qq');

/**
 * 聊天窗口
 * @param {Object} config
 */
com.bhtec.view.util.qq.ChatWin = function(config) {
    //config共有两项属性配置 user client
    if (!config.user) {
        throw 'user can not be null';
        return;
    }
    com.bhtec.view.util.qq.ChatWin.superclass.constructor.call(this, Ext.apply({
        title: '和' + config.user + '聊天中',
        iconCls: 'qq',
        width: 550,
        height: 450,
        id: config.user,
        collapsible: true,
        maximizable: true,
        border: false,
        bodyBorder: false,
        plain: true,
        layout: 'anchor',
        defaultType: 'panel',
        defaults: {
            border: false,
            bodyBorder: false
        },
        items: [{
            anchor: '100% 70%',
            bodyStyle: 'padding:10px;'
        },
        {
            xtype: 'form',
            anchor: '100% 30%',
            hideLabels: true,
            items: [{
                xtype: 'htmleditor',
                value: '',
                anchor: '100% 100%',
                name: 'editbody'
            }]
        }],
        buttons: [{
            text: '发送',
            handler: function() {
                var editor = this.ownerCt.ownerCt.items.items[1].items.items[0];
                var fontColor = this.ownerCt.ownerCt.client.fontColor ? this.ownerCt.ownerCt.client.fontColor: null;
                //本来我的打算是所有信息统一发往后台，再由后台传过来，但发现网路不好时（很有可能 后台传不过来数据），自己发送的信息也看不到了
                //所以我把当前客户发送的信息直接赋给当前客户端了
                this.ownerCt.ownerCt.addMsg(editor.getValue(), this.ownerCt.ownerCt.client.currentUser, fontColor);
                this.ownerCt.ownerCt.client.sendMsg(editor.getValue(), this.ownerCt.ownerCt.user, fontColor);
                editor.reset();
            }
        },
        {
            text: '关闭',
            handler: function() {
//                this.ownerCt.ownerCt.close();
            	Ext.getCmp(config.user).close();
            }
        }]
    },config || {}));
}
Ext.extend(com.bhtec.view.util.qq.ChatWin, Ext.Window, {
    /**
     * 添加聊天信息
     */
    addMsg: function(msg, from, color) {
        if (!color) {
            color = (from != this.user) ? 'red': 'blue';
        }
        //url:jslib/common/TimeFormat.js
        var time = (new Date()).format("yyyy-MM-dd EEE hh:mm:ss");
        var formatmsg = "<div class='_msgtitle' style='color:" + color + "'>" + from + "&nbsp;&nbsp;&nbsp;&nbsp;" + time + "</div><div class='_msg'>" + msg + "</div>";
        this.items.items[0].body.insertHtml("beforeEnd", formatmsg);
        this.items.items[0].body.scroll("bottom", 9999);
    },
    /**
     * 清除输入框的信息
     */
    clearInput: function() {}
    //,
    /**
     * 销毁组件
     */
   /* beforeDestroy: function() {
        if (this.rendered) {

            if (this.tools) {
                for (var k in this.tools) {
                    Ext.destroy(this.tools[k]);
                }
            }
            if (this.header && this.headerAsText) {
                var s;
                if (s = this.header.child('span')) s.remove();
                this.header.update('');
            }
            Ext.each(['header', 'body', 'footer'],
            function(elName) {
                if (this[elName]) {
                    if (typeof this[elName].destroy == 'function') {
                        this[elName].destroy();
                    }
                    else {
                        Ext.destroy(this[elName]);
                    }

                    this[elName] = null;
                    delete this[elName];
                }
            },
            this);
        }
        com.bhtec.view.util.qq.ChatWin.superclass.beforeDestroy.call(this);
    }*/
});

/**
 * 客户端窗口
 * @param {Object} config
 */
com.bhtec.view.util.qq.ClientWin = function(config) {
    if (!config.currentUser) {
        throw 'currentUser can not be null';
        return;
    }
    com.bhtec.view.util.qq.ClientWin.superclass.constructor.call(this, Ext.apply({
        title: '当前用户<br>' + config.currentUser,
        iconCls: 'qq',
        collapsible: true,
        id: config.currentUser,
        x: 0,
        y:0,
        width: 220,
        height: 400,
        closeAction: 'hide',
        constrain: true,
        minimizable:true,
        plain: true,
        border: false,
        bodyBorder: false,
        layout: 'fit',
        tbar: [{
            text: '刷新',
            cls: 'x-btn-text-icon',
            iconCls: 'menu-refresh',
            handler: function() {
				var hasJoined=false;
                Ext.getCmp(config.currentUser).userListPanel.root.eachChild(function(node) {
                    if (node.text == config.currentUser) {
						hasJoined=true;
                        return;
                    }
                });
				
                dojox.cometd.publish("/public/chat", {
                    user: config.currentUser,
                    join: true,
                    action: 'rejoin',
                    chat: config.currentUser + '重新加入'
                });
            }
        }],
        bbar: [{
            text: '进入聊天室',
            cls: 'x-btn-text-icon',
            iconCls: 'menu-teacher',
            handler: function() {
                Ext.getCmp(config.currentUser).joinPublicRoom();
            }
        }]
    },
    config || {}));
}
Ext.extend(com.bhtec.view.util.qq.ClientWin, Ext.Window, {

    /**
     * 初始化事件
     */
    initEvents: function() {
        com.bhtec.view.util.qq.ClientWin.superclass.initEvents.call(this);
        //this.on('beforeclose', this.leave, this);
    },
    /**
     *
     */
    onRender: function(ct, position) {
        com.bhtec.view.util.qq.ClientWin.superclass.onRender.call(this, ct, position);
        this.userListPanel || (this.userListPanel = this.add(new Ext.tree.TreePanel({
            header: false,
            title: '在线用户',
            border: true,
            //rootVisible: false,
            lines: false,
            height: 300,
            width: 210,
            autoScroll: true,
            animate: false,
            root: new Ext.tree.TreeNode({
                expandable: true,
                text: '登录用户',
                expanded: true
            })
        })));
        this.userListPanel.on('dblclick',
        function(node, e) {
//            if ((node.text == this.ownerCt.currentUser) || (node.text == '登录用户')) {
//                return;
//            }
            this.ownerCt.createChatWin(node.text).show();
        });
    },
    /**
     * afterRender常用来初始化
     */
    afterRender: function() {
        com.bhtec.view.util.qq.ClientWin.superclass.afterRender.call(this);
        this.initConnect();
    },
    /**
     *初始化连接
     */
    initConnect: function() {
        //指向后台的servlet
        //127.0.0.1/JingpinkeSystemApp/cometd
        var loc = "cometd";
        dojox.cometd.init(loc);
        this._connected = true;
        dojox.cometd.startBatch();
        //订阅频道
        dojox.cometd.subscribe("/public/chat", this, "getMsg");
        //向频道上发布消息,说明当前用户的加入
        dojox.cometd.publish("/public/chat", {
            user: this.currentUser,
            join: true,
            chat: this.currentUser + " 已加入"
        });
        dojox.cometd.endBatch();
        //对 comet failures 进行处理
        this.cometFail();

    },
    /**
     * 连接失败处理
     */
    cometFail: function() {
        //如果之前已经订阅_meta，则取消订阅
        if (this._meta) {
            dojo.unsubscribe(this._meta, null, null);
        }
        //订阅
        this._meta = dojo.subscribe("/cometd/meta", this,
        function(e) {
            if (e.action == "handshake") {
                if (e.reestablish) {
                    if (e.successful) {
                        //重新连接成功
                        dojox.cometd.subscribe("/public/chat", this, "getMsg");
                        dojox.cometd.publish("/public/chat", {
                            user: this.currentUser,
                            join: true,
                            chat: this.currentUser + " 重新加入"
                        });
                    }
                    this.getMsg({
                        data: {
                            join: true,
                            user: "SERVER",
                            chat: "handshake " + e.successful ? "Handshake OK": "Failed"
                        }
                    });
                }
            }
            else if (e.action == "connect") {
                if (e.successful && !this._connected) {
                    this.getMsg({
                        data: {
                            join: true,
                            user: "SERVER",
                            chat: "reconnected!"
                        }
                    });
                }
                if (!e.successful && this._connected) {
                    this.getMsg({
                        data: {
                            leave: true,
                            user: "SERVER",
                            chat: "disconnected!"
                        }
                    });
                }
                this._connected = e.successful;
            }
        });
    },
    /**
     * 加入聊天室
     */
    joinPublicRoom: function() {
        if (this.publicRoom) {
            this.publicRoom.show();
            return;
        }
        var fontColor = this.fontColor ? this.fontColor: ColorUtil.rand();
        Ext.apply(this, {
            fontColor: fontColor
        });
        this.publicRoom = new com.bhtec.view.util.qq.ChatWin({
            user: 'publicRoom',
            title: '大家一块交流吧<br>当前用户是 ' + this.currentUser,
            iconCls: 'menu-teacher',
            client: this
        });
        this.publicRoom.show();

        //向频道上发布消息,说明当前用户已加入聊天室
        dojox.cometd.publish("/public/chat", {
            user: this.currentUser,
            chat: this.currentUser + " 已加入聊天室",
            fontColor: fontColor
        });
        this.publicRoom.on('beforeclose',
        function() {
            //向频道上发布消息,说明当前用户已离开聊天室
            dojox.cometd.publish("/public/chat", {
                user: this.client.currentUser,
                chat: this.client.currentUser + " 已离开聊天室",
                fontColor: fontColor
            });
            this.client.publicRoom.destroy();
            this.client.publicRoom = null;
            delete this.client.publicRoom;
        });
    },
    /**
     * 向频道上发送消息
     */
    sendMsg: function(text, toUser, fontColor) {
        //信息为空（null或‘’），返回 这个根据实际情况情况确定，但null一定要返回的
        if (!text || !text.length) {
            return false;
        }
        if (toUser && (toUser != 'publicRoom')) {
            //如果为私聊，则转到私聊频道上
            dojox.cometd.publish("/private/chat", {
                room: "/public/chat",
                // This should be replaced by the room name
                user: this.currentUser,
                chat: text,
                //和当前用户要私聊的用户
                peer: toUser
            });
        }
        else {
            //不是私聊，则转到公用频道上
            dojox.cometd.publish("/public/chat", {
                user: this.currentUser,
                chat: text,
                fontColor: fontColor
            });
        }
    },
    /**
     * 获取消息
     */
    getMsg: function(message) {
//        alert('getMsg');
        if (!message.data) {
            return;
        }
        
        //如果数据是数组的形式，则说明得到信息 是 在线人员（这和后台有关）
        if (message.data instanceof Array) {
            //alert(message.data);
            //把当前树中中的人员全部删除
            var root = this.userListPanel.root;
            root.collapse(false, false);
            while (root.firstChild) {
                root.removeChild(root.firstChild).destroy();
            }
            //root.childrenRendered = false;
            //root.loaded = false;
            //if (root.isHiddenRoot()) {
            //  root.expanded = false;
            //}
            //把在线人员添加到树中    
            var i = 0;
            for (i = 0; i < message.data.length; i++) {
                root.appendChild({
                    text: message.data[i],
                    leaf: true,
                    iconCls: (this.currentUser != message.data[i]) ? 'qq-guest': 'qq-host'
                });
            }
            root.expand(false, false);
            root = null;
            delete root;
            //播放声音
            soundManager.play('joinSound');
        }
        else {
            //是加入还是离开
            var state = message.data.join || message.data.leave;
            //当用户刚加入/退出时，会发出已经加入/退出信息，规定为不接受加入/退出信息
            if (state) {
                return;
            }
            //发送信息者 
            var from = message.data.user;
            //如果接到的信息的发送者是当前客户(自己发给自己的)，则返回
            if (from == this.currentUser) {
                return;
            }
            //聊天内容
            // 这一点比较让人郁闷，没想到dojo这么体贴人心，标签都转换好了，可我还得转过来
            var text = message.data.chat;
            text = text.replace(/&lt;/g, '<');
            text = text.replace(/&gt;/g, '>');
            //范围 \private 私聊 \ error 错误 \ 空  公聊
            var scope;
            //私聊
            if ((scope = message.data.scope) && (scope == 'private')) {
                //当用户刚加入/退出时，会发出已经加入/退出信息，在私聊情况下规定为不接受加入/退出信息
                //if (state) {
                //  return;
                //}
                //私聊情况下，如果接到的信息的发送者是当前客户(自己发给自己的)，则返回
                // if (from == this.currentUser) {
                //   return;
                // }
            	var isWin = Ext.getCmp(from);
            	//如果没有相应用户的div及窗口则创建窗口
            	var statebarifr = document.frames("statebarifr");
            	if(statebarifr.document.getElementById(from+'_div') == undefined 
            			&& isWin == undefined){
	            	var qqStatus = statebarifr.document.getElementById('qqstatus');//qq状态div
					var newDiv = statebarifr.document.createElement("div");
					newDiv.setAttribute('id',from+'_div');//创建用户div
					newDiv.style.display = 'inline';
					newDiv.style.float = 'left';//div横排
					qqStatus.appendChild (newDiv);
					//用户图片，图片需要动态加载
					var userImg =  "<img  border=0 width=18 height=18 id='"+from+"_img' style='FILTER: alpha(opacity=100);cursor:hand' " +
						            "class=middle src='../img/navigate/qq.png' title='"+from+"' />";
				    //添加图片     	  
					newDiv.innerHTML = userImg;
					var usrImgObj = statebarifr.document.getElementById(from+'_img');
					var temp = this;//clientWin object
					//qq闪烁
					var qqBlink = function(){
						var obj = usrImgObj;
						if(obj.filters.alpha.opacity==100){
							obj.filters.alpha.opacity=0
						}else{
							obj.filters.alpha.opacity=100
						}	
					}
					setInterval(qqBlink,500);
					
					//播放声音
			        soundManager.play('msgSound');
					//点击闪烁图片事件
					usrImgObj.onclick = function(){
							var win = temp.createChatWin(from);
			                win.show();
			                win.addMsg(text, from);
			                
			                clearInterval(qqBlink.timer);//清除定时调用，每个实例拥有自己的timer
			                win = null;
			                delete win;
			                qqStatus.removeChild(newDiv);//删除用户div
					}
            	}else{
            		var win = this.createChatWin(from);
	                win.show();
	                win.addMsg(text, from);
	                //播放声音
	                soundManager.play('msgSound');
	                win = null;
	                delete win;
            	}
                return;
            }
            //公聊
            if (this.publicRoom) {
                //alert(this.fontColor);
                //alert(message.data.fontColor);
                this.publicRoom.addMsg(text, from, message.data.fontColor);

                //播放声音
                soundManager.play('msgSound');
            }
        }
    },
    /**
     * 用户离开
     */
    leave: function() {
        //alert('leave');
        if (!this.currentUser) {
            return;
        }
        if (this._meta) {
            dojo.unsubscribe(this._meta);
        }
        this._meta = null;
        dojox.cometd.startBatch();
        dojox.cometd.unsubscribe("/public/chat", this, "getMsg");
        dojox.cometd.publish("/public/chat", {
            user: this.currentUser,
            leave: true,
            chat: this.currentUser + "已离开"
        });
        dojox.cometd.endBatch();
        this.currentUser = null;
        dojox.cometd.disconnect();
    },
    /**
     *弹出聊天窗口
     */
    createChatWin: function(user) {
        var win;
        if (win = Ext.getCmp(user)) {
            return win;
        }
        win = new com.bhtec.view.util.qq.ChatWin({
            user: user,
            client: this
        });
        return win;
    },
    /**
     *
     */
    beforeDestroy: function() {
        //离开
        this.leave();
        //关闭所有聊天窗口
        this.userListPanel.root.eachChild(function(node) {
            var win;
            if (win = Ext.getCmp(node.text)) {
                win.close();
            }
            win = null;
            delete win;
        });

        if (this.rendered) {

            if (this.userListPanel) {
                this.remove(this.userListPanel);
            }
            if (this.tools) {
                for (var k in this.tools) {
                    Ext.destroy(this.tools[k]);
                }
            }
            if (this.header && this.headerAsText) {
                var s;
                if (s = this.header.child('span')) s.remove();
                this.header.update('');
            }
            Ext.each(['userListPanel', 'publicRoom', 'header', 'body', 'bottomToolbar'],
            function(elName) {
                if (this[elName]) {
                    if (typeof this[elName].destroy == 'function') {
                        this[elName].destroy();
                    }
                    else {
                        Ext.destroy(this[elName]);
                    }

                    this[elName] = null;
                    delete this[elName];
                }
            },
            this);
        }
        com.bhtec.view.util.qq.ClientWin.superclass.beforeDestroy.call(this);
    }
});
