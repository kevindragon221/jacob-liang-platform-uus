Ext.ns('Ext.ux.form');
Ext.ux.form.TimePickerField=function(config) {
 Ext.ux.form.TimePickerField.superclass.constructor.call(this, config);
}
Ext.extend(Ext.ux.form.TimePickerField, Ext.form.Field, {
    defaultAutoCreate: {tag: 'div'},
 cls: 'x-form-timepickerfield',
 hoursSpinner: null,
 minutesSpinner: null,
 secondsSpinner: null,
 spinnerCfg: {
  width: 40
 },
 spinnerFixBoundries: function(value){
  if(value<this.field.minValue) {
   value=this.field.maxValue;
  }
  if(value>this.field.maxValue) {
   value=this.field.minValue;
  }
  return this.fixPrecision(value);
 },
    onRender: function(ct, position){
        Ext.ux.form.TimePickerField.superclass.onRender.call(this, ct, position);
        this.rendered=false;
  this.date=new Date();
  var values={};
  if(this.value) {
   values=this._valueSplit(this.value);
   this.date.setHours(values.h);
   this.date.setMinutes(values.m);
   this.date.setSeconds(values.s);
   delete this.value;
  }
  else {
   values={h:this.date.getHours(), m:this.date.getMinutes(), s:this.date.getSeconds()};
  }
  var spinnerWrap=Ext.DomHelper.append(this.el, {tag: 'div'});
  var cfg=Ext.apply({}, this.spinnerCfg, {
   renderTo: spinnerWrap,
   readOnly: this.readOnly,
   disabled: this.disabled,
   listeners: {
    spin: {
     fn: this.onSpinnerChange,
     scope: this
    },
    valid: {
     fn: this.onSpinnerChange,
     scope: this
    },
    afterrender: {
     fn: function(spinner) {
      spinner.wrap.applyStyles('float: left');
     },
     single: true
    }
   }
  });

  this.hoursSpinner=new Ext.ux.form.SpinnerField(
   Ext.apply({}, cfg, {
    minValue: 0,
    maxValue: 23,
    cls: 'first',
    value: values.h
   })
  );
  this.minutesSpinner=new Ext.ux.form.SpinnerField(
   Ext.apply({}, cfg, {
    minValue: 0,
    maxValue: 59,
    value: values.m
   })
  );
  this.secondsSpinner=new Ext.ux.form.SpinnerField(
   Ext.apply({}, cfg, {
    minValue: 0,
    maxValue: 59,
    value: values.s
   })
  );
  Ext.DomHelper.append(spinnerWrap, {tag: 'div', cls: 'x-form-clear-left'});
  this.rendered=true;
    },
 _valueSplit: function(v) {
  var split=v.split(':');
  return {
   h: split.length>0 ? split[0] : 0,
   m: split.length>1 ? split[1] : 0,
   s: split.length>2 ? split[2] : 0
  };
 },
 onSpinnerChange: function() {
  if(!this.rendered) {
   return;
  }
  this.fireEvent('change', this, this.getRawValue());
 },
 disable: function() {
  Ext.ux.form.TimePickerField.superclass.disable.call(this);
  this.hoursSpinner.disable();
  this.minutesSpinner.disable();
  this.secondsSpinner.disable();
 },
 enable: function() {
  Ext.ux.form.TimePickerField.superclass.enable.call(this);
  this.hoursSpinner.enable();
  this.minutesSpinner.enable();
  this.secondsSpinner.enable();
 },
 setReadOnly: function(r) {
  Ext.ux.form.TimePickerField.superclass.setReadOnly.call(this, r);
  this.hoursSpinner.setReadOnly(r);
  this.minutesSpinner.setReadOnly(r);
  this.secondsSpinner.setReadOnly(r);
 },
 clearInvalid: function() {
  Ext.ux.form.TimePickerField.superclass.clearInvalid.call(this);
  this.hoursSpinner.clearInvalid();
  this.minutesSpinner.clearInvalid();
  this.secondsSpinner.clearInvalid();
 },
 getRawValue: function() {
  if(!this.hoursSpinner){
   this.date=new Date();
   return {h:this.date.getHours(), m:this.date.getMinutes(), s:this.date.getSeconds()};
  }else{
   return {
    h: this.hoursSpinner.getValue(),
    m: this.minutesSpinner.getValue(),
    s: this.secondsSpinner.getValue()
   };
  }
 },
 setRawValue: function(v) {
  this.hoursSpinner.setValue(v.h);
  this.minutesSpinner.setValue(v.m);
  this.secondsSpinner.setValue(v.s);
 },
 isValid: function(preventMark) {
  return this.hoursSpinner.isValid(preventMark) &&
   this.minutesSpinner.isValid(preventMark) &&
   this.secondsSpinner.isValid(preventMark);
 },
 validate: function() {
  return this.hoursSpinner.validate() &&
   this.minutesSpinner.validate() &&
   this.secondsSpinner.validate();
 },
 getValue: function() {
  var v=this.getRawValue();
  return String.leftPad(v.h, 2, '0')+':'+
      String.leftPad(v.m, 2, '0')+':'+
      String.leftPad(v.s, 2, '0');
 },
 setValue: function(value) {
  if(!this.rendered) {
   this.value=value;
   return;
  }
  value=this._valueSplit(value);
  this.setRawValue(value);
  this.validate();
 }
});
Ext.form.TimePickerField=Ext.ux.form.TimePickerField;
Ext.reg('timepickerfield', Ext.form.TimePickerField);
Ext.ns('Ext.ux.form');
Ext.DateTimePicker = Ext.extend(Ext.DatePicker, {
    timeFormat:'g:i:s A',
    timeLabel:'<span style=font:12px;>时间:</span>',
    timeWidth:100,
    initComponent:function() {
        Ext.DateTimePicker.superclass.initComponent.call(this);
        this.id = Ext.id();
    },
    onRender: function(container,position){
        Ext.DateTimePicker.superclass.onRender.apply(this,arguments);
        var table = Ext.get(Ext.DomQuery.selectNode('table tbody',container.dom));
        var tfEl = Ext.DomHelper.insertBefore(table.last(),{tag:'tr',
            children:[{tag:'td',cls:'x-date-bottom',html:this.timeLabel,style:'width:30;'},{tag:'td',cls:'x-date-bottom ux-timefield', colspan:'2'}]},true);
        this.tf.render(table.child('td.ux-timefield'));
        var p=this.getEl().parent('div.x-layer');
        if (p){
         p.setStyle("height",p.getHeight()+31);
        }
    },
    setValue : function(value){
        var old = this.value;
        if (!this.tf){
            this.tf = new Ext.ux.form.TimePickerField();
            this.tf.ownerCt = this;
        }
        this.value = this.getDateTime(value);
    },
    getDateTime: function(value){
        if (this.tf){
            var dt = new Date();
            var timeval = this.tf.getValue();
            value = Date.parseDate(value.format(this.dateFormat) + ' ' +this.tf.getValue(),this.format);
        }
        return value;
    },
    selectToday : function(){
        if(this.todayBtn && !this.todayBtn.disabled){
            this.value=this.getDateTime(new Date());
            this.fireEvent("select", this, this.value);
        }
    }
});
Ext.reg('datetimepickerfield', Ext.DateTimePicker);
if (parseInt(Ext.version.substr(0, 1), 10) > 2) {
    Ext.menu.DateTimeItem = Ext.DateTimePicker;
    Ext.override(Ext.menu.DateMenu,{
        initComponent: function(){
            this.on('beforeshow', this.onBeforeShow, this);
            if(this.strict = (Ext.isIE7 && Ext.isStrict)){
                this.on('show', this.onShow, this, {single: true, delay: 20});
            }
            Ext.apply(this, {
                plain: true,
                showSeparator: false,
                items: this.picker = new Ext.DatePicker(Ext.apply({
                    internalRender: this.strict || !Ext.isIE,
                    ctCls: 'x-menu-date-item'
                }, this.initialConfig))
            });
            Ext.menu.DateMenu.superclass.initComponent.call(this);
            this.relayEvents(this.picker, ["select"]);
            this.on('select', this.menuHide, this);
            if(this.handler){
                this.on('select', this.handler, this.scope || this);
            }
        }
    });
}else{
    Ext.menu.DateTimeItem = function(config){
        Ext.menu.DateTimeItem.superclass.constructor.call(this, new Ext.DateTimePicker(config), config);
        this.picker = this.component;
        this.addEvents('select');
   
        this.picker.on("render", function(picker){
            picker.getEl().swallowEvent("click");
            picker.container.addClass("x-menu-date-item");
        });
   
        this.picker.on("select", this.onSelect, this);
    };
   
    Ext.extend(Ext.menu.DateTimeItem, Ext.menu.DateMenu, {
        onSelect : function(picker, date){
            this.fireEvent("select", this, date, picker);
            Ext.menu.DateTimeItem.superclass.handleClick.call(this);
        }
    });
}
Ext.menu.DateTimeMenu = function(config){
    Ext.menu.DateTimeMenu.superclass.constructor.call(this, config);
    this.plain = true;
    var di = new Ext.menu.DateTimeItem(config);
    this.add(di);
    this.picker = di;
    this.relayEvents(di, ["select"]);
    this.on('beforeshow', function(){
        if(this.picker){
            this.picker.hideMonthPicker(true);
        }
    }, this);
};
Ext.extend(Ext.menu.DateTimeMenu, Ext.menu.Menu, {
    cls:'x-date-menu',
    beforeDestroy : function() {
        this.picker.destroy();
    },
    hide : function(deep){
        if (this.picker.tf.innerList){
            if ((Ext.EventObject.within(this.picker.tf.innerList)) || (Ext.get(Ext.EventObject.getTarget())==this.picker.tf.innerList))
                return false;
        }
        if(this.el && this.isVisible()){
            this.fireEvent("beforehide", this);
            if(this.activeItem){
                this.activeItem.deactivate();
                this.activeItem = null;
            }
            this.el.hide();
            this.hidden = true;
            this.fireEvent("hide", this);
        }
        if(deep === true && this.parentMenu){
            this.parentMenu.hide(true);
        }
    }
});
Ext.ux.form.DateTimeField = Ext.extend(Ext.form.DateField, {
    dateFormat: 'Y-m-d'
    ,timeFormat: 'H:i:s'
    ,defaultAutoCreate : {tag: "input", type: "text", size: "20", autocomplete: "off"}
    ,initComponent:function() {
        Ext.ux.form.DateTimeField.superclass.initComponent.call(this);
        this.format = this.dateFormat + ' ' + this.timeFormat;
        this.afterMethod('afterRender',function(){
            this.getEl().applyStyles('top:0');
        });
    }
    ,getValue : function(){
        return this.parseDate(Ext.form.DateField.superclass.getValue.call(this)) || '';
    }
    ,onTriggerClick : function(){
        if(this.disabled){
            return;
        }
        if(this.menu == null){
            this.menu = new Ext.menu.DateTimeMenu();
        }
        Ext.apply(this.menu.picker,  {
            minDate : this.minValue,
            maxDate : this.maxValue,
            disabledDatesRE : this.ddMatch,
            disabledDatesText : this.disabledDatesText,
            disabledDays : this.disabledDays,
            disabledDaysText : this.disabledDaysText,
            format : this.format,
            timeFormat: this.timeFormat,
            dateFormat: this.dateFormat,
            showToday : this.showToday,
            minText : String.format(this.minText, this.formatDate(this.minValue)),
            maxText : String.format(this.maxText, this.formatDate(this.maxValue))
        });
        if (this.menuEvents) {
            this.menuEvents('on');
        }
        else {
            this.menu.on(Ext.apply({}, this.menuListeners, {
                scope:this
            }));
        }
        this.menu.picker.setValue(this.getValue() || new Date());
        this.menu.show(this.el, "tl-bl?");
    }
});
Ext.reg('datetimefield', Ext.ux.form.DateTimeField);

Ext.ux.MonthPickerPlugin = function() { 
    var picker; 
    var oldDateDefaults; 

    this.init = function(pk) { 
        picker = pk; 
        picker.onTriggerClick = picker.onTriggerClick.createSequence(onClick); 
        picker.getValue = picker.getValue.createInterceptor(setDefaultMonthDay).createSequence(restoreDefaultMonthDay); 
        picker.beforeBlur = picker.beforeBlur.createInterceptor(setDefaultMonthDay).createSequence(restoreDefaultMonthDay); 
    }; 

    function setDefaultMonthDay() { 
        oldDateDefaults = Date.defaults.d; 
        Date.defaults.d = 1; 
        return true; 
    } 

    function restoreDefaultMonthDay(ret) { 
        Date.defaults.d = oldDateDefaults; 
        return ret; 
    } 

    function onClick(e, el, opt) { 
        var p = picker.menu.picker; 
        p.activeDate = p.activeDate.getFirstDateOfMonth(); 
        if (p.value) { 
            p.value = p.value.getFirstDateOfMonth(); 
        } 

        p.showMonthPicker(); 
         
        if (!p.disabled) { 
            p.monthPicker.stopFx(); 
            p.monthPicker.show(); 

            p.mun(p.monthPicker, 'click', p.onMonthClick, p); 
            p.mun(p.monthPicker, 'dblclick', p.onMonthDblClick, p); 
            p.onMonthClick = p.onMonthClick.createSequence(pickerClick); 
            p.onMonthDblClick = p.onMonthDblClick.createSequence(pickerDblclick); 
            p.mon(p.monthPicker, 'click', p.onMonthClick, p); 
            p.mon(p.monthPicker, 'dblclick', p.onMonthDblClick, p); 
        } 
    } 

    function pickerClick(e, t) { 
        var el = new Ext.Element(t); 
        if (el.is('button.x-date-mp-cancel')) { 
            picker.menu.hide(); 
        } else if(el.is('button.x-date-mp-ok')) { 
            var p = picker.menu.picker; 
            p.setValue(p.activeDate); 
            p.fireEvent('select', p, p.value); 
        } 
    } 

    function pickerDblclick(e, t) { 
        var el = new Ext.Element(t); 
        if (el.parent() 
            && (el.parent().is('td.x-date-mp-month') 
            || el.parent().is('td.x-date-mp-year'))) { 

            var p = picker.menu.picker; 
            p.setValue(p.activeDate); 
            p.fireEvent('select', p, p.value); 
        } 
    } 
}; 

Ext.preg('monthPickerPlugin', Ext.ux.MonthPickerPlugin); 