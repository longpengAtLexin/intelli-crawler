var Initializer = {};

Initializer.initAll = function()
{
	Initializer.initSeedGrid();
	Initializer.initUrlPatternGrid();
	Initializer.initProxyGrid();
	Initializer.initTableGrid();
	
}
/**
* 初始化入口地址 grid
*/
Initializer.initSeedGrid = function()
{
	BUI.use(['bui/grid','bui/data','bui/form'],function (Grid,Data,Form) {

		var columns = [{title : '起始url地址',dataIndex :'seedurl',editor : {xtype : 'text',rules:{required:true}}} ],
	      //默认的数据
	      //data = [{id:'1',seedurl:''}],
	      store = new Data.Store({
	        data:[]
	      }),
	      editing = new Grid.Plugins.CellEditing(),
	      grid = new Grid.Grid({
	    	id:"seedgrid",
	        render : '#seedgrid',
	        columns : columns,
	        width : 700,
	        forceFit : true,
	        store : store,
	        plugins : [Grid.Plugins.CheckSelection,editing],
	        tbar:{
	            items : [{
	              btnCls : 'button button-small',
	              text : '<i class="icon-plus"></i>添加',
	              listeners : {
	                'click' : addFunction
	              }
	            },
	            {
	              btnCls : 'button button-small',
	              text : '<i class="icon-remove"></i>删除',
	              listeners : {
	                'click' : delFunction
	              }
	            }]
	        }

	      });
	    grid.render();

	    function addFunction(){
	      var newData = {seedurl :''};
	      store.add(newData);
	      editing.edit(newData,'seedurl'); //添加记录后，直接编辑
	    }

	    function delFunction(){
	      var selections = grid.getSelection();
	      store.remove(selections);
	    }
	    var logEl = $('#log');
	    $('#btnSave').on('click',function(){
	      if(editing.isValid()){
	        var records = store.getResult();
	        logEl.text(BUI.JSON.stringify(records));
	      }
	    });
	  });
}

/**
* 初始化url模式表格;
*/
Initializer.initUrlPatternGrid=function()
{
	BUI.use(['bui/grid','bui/data','bui/form'],function (Grid,Data,Form){

    var columns = [{title : '爬取网页url模式',dataIndex :'urlpattern',editor : {xtype : 'text',rules:{required:true}}} ],
      //默认的数据
      //data = [{id:'1',urlpattern:''}],
      store = new Data.Store({
        data:[]
      }),
      editing = new Grid.Plugins.CellEditing(),
      grid = new Grid.Grid({
    	 id:"urlpatterngrid",
        render : '#urlpatterngrid',
        columns : columns,
        width : 700,
        forceFit : true,
        store : store,
        plugins : [Grid.Plugins.CheckSelection,editing],
        tbar:{
            items : [{
              btnCls : 'button button-small',
              text : '<i class="icon-plus"></i>添加',
              listeners : {
                'click' : addFunction
              }
            },
            {
              btnCls : 'button button-small',
              text : '<i class="icon-remove"></i>删除',
              listeners : {
                'click' : delFunction
              }
            }]
        }

      });
    grid.render();

    function addFunction(){
      var newData = {urlpattern :''};
      store.add(newData);
      editing.edit(newData,'urlpattern'); //添加记录后，直接编辑
    }

    function delFunction(){
      var selections = grid.getSelection();
      store.remove(selections);
    }
    var logEl = $('#log');
    $('#btnSave').on('click',function(){
      if(editing.isValid()){
        var records = store.getResult();
        logEl.text(BUI.JSON.stringify(records));
      }
    });
  });
}

Initializer.initProxyGrid=function()
{
	BUI.use(['bui/grid','bui/data','bui/form'],function (Grid,Data,Form){

    var columns = [{title : '代理Ip',dataIndex :'proxyIp',editor : {xtype : 'text',rules:{required:true}}} ,{title : '代理端口',dataIndex :'proxyPort',editor : {xtype : 'text',rules:{required:true}}} ],
      store = new Data.Store({
        data:[]
      }),
      editing = new Grid.Plugins.CellEditing(),
      grid = new Grid.Grid({
    	 id:"proxygrid",
        render : '#proxygrid',
        columns : columns,
        width : 700,
        forceFit : true,
        store : store,
        plugins : [Grid.Plugins.CheckSelection,editing],
        tbar:{
            items : [{
              btnCls : 'button button-small',
              text : '<i class="icon-plus"></i>添加',
              listeners : {
                'click' : addFunction
              }
            },
            {
              btnCls : 'button button-small',
              text : '<i class="icon-remove"></i>删除',
              listeners : {
                'click' : delFunction
              }
            }]
        }

      });
    grid.render();

    function addFunction(){
      var newData = {proxyIp :''};
      store.add(newData);
      editing.edit(newData,'proxyIp'); //添加记录后，直接编辑
    }

    function delFunction(){
      var selections = grid.getSelection();
      store.remove(selections);
    }
    var logEl = $('#log');
    $('#btnSave').on('click',function(){
      if(editing.isValid()){
        var records = store.getResult();
        logEl.text(BUI.JSON.stringify(records));
      }
    });
  });
}

/**
* 数据提取字段信息;
*/
Initializer.initTableGrid=function()
{
	BUI.use(['bui/grid','bui/data','bui/form'],function (Grid,Data,Form){

    var columns = [{title : '字段名',dataIndex :'fieldname',editor : {xtype : 'text',rules:{required:true}}},
            {title : '字段路径',dataIndex :'fieldpath',editor : {xtype : 'text',rules:{required:true}}},
            {title : '字段描述',dataIndex :'fielddescription',editor : {xtype : 'text',rules:{required:true}}},
            {title : '个数',dataIndex :'counter',width:200,editor : {xtype : 'text'}}
            
          ],
      //默认的数据
      //data = [{id:'1',school:'武汉第一初级中学'} ],
      store = new Data.Store({
        data:[]
      }),
      editing = new Grid.Plugins.CellEditing(),
      grid = new Grid.Grid({
    	 id:"fieldgrid",
        render : '#fieldgrid',
        columns : columns,
        width : 700,
        forceFit : true,
        store : store,
        plugins : [Grid.Plugins.CheckSelection,editing],
        tbar:{
            items : [{
              btnCls : 'button button-small',
              text : '<i class="icon-plus"></i>添加',
              listeners : {
                'click' : addFunction
              }
            },
            {
              btnCls : 'button button-small',
              text : '<i class="icon-remove"></i>删除',
              listeners : {
                'click' : delFunction
              }
            }]
        }

      });
    grid.render();

    function addFunction(){
      var newData = {fieldname :''};
      store.add(newData);
      editing.edit(newData,'fieldname'); //添加记录后，直接编辑
    }

    function delFunction(){
      var selections = grid.getSelection();
      store.remove(selections);
    }
    var logEl = $('#log');
    $('#btnSave').on('click',function(){
      if(editing.isValid()){
        var records = store.getResult();
        logEl.text(BUI.JSON.stringify(records));
      }
    });
  });
}

/*Initializer.initPagingConfig=function()
{
	BUI.use('bui/select',function(Select){
		  
		    var items = [
		          {text:'需分页',value:0},
		          {text:'不需分页',value:1},
		        ],
		        select = new Select.Select({
		          render:'#isPaging',
		          valueField:'#hide',
		          items:items
		        });
		    select.render();
		    select.on('change', function(ev){
		      alert(ev.item);
		    });
		    
		  });
}*/
Initializer.pagingSelectChange= function(obj)
{
	if(obj.value=="0")
	{
		document.getElementById("pagingDetail").style.display = "none";
	}
	else
		document.getElementById("pagingDetail").style.display = "";
}

Initializer.proxySetSelectChange = function(obj)
{
	if(obj.value=="0")
	{
		document.getElementById("proxyDetail").style.display = "none";
	}
	else
		document.getElementById("proxyDetail").style.display = "";
}

Initializer.rigisterSubmitBtnHandler=function()
{
	var form = $('form')[0];
	$(form).on('submit',function(ev) {
	   ev.preventDefault();
    //序列化成对象
    var obj = BUI.FormHelper.serializeToObject(form);
    //显示对象
   
    
    var seedgrid = BUI.getControl( "seedgrid" ) ;
    
    obj.seedurls = [];
    var items = seedgrid.getItems();
    if(items)
    {
    	$.each(items,function(i,n){
    		 obj.seedurls[i] = items[i].seedurl;
    	});
    }
    
    var urlpatterngrid = BUI.getControl( "urlpatterngrid" ) ;
    obj.urlpatterns = [];
    items = urlpatterngrid.getItems() ;
    if(items)
    {
    	$.each(items,function(i,n){
    		 obj.urlpatterns[i] = items[i].urlpattern;
    	});
    }
    if( obj.setProxy == 0) // 不设置代理
    {
    	obj.proxyConfig = null;
    	delete obj.setProxy;
    }
    else // 设置代理;
    {
    	obj.proxyConfig = {};
    	obj.proxyConfig.proxyInfos = [ ];
    	var proxygrid = BUI.getControl( "proxygrid" ) ;
    	items = proxygrid.getItems() ;
    	if(items)
        {
        	$.each(items,function(i,n){
        		obj.proxyConfig.proxyInfos[i] = {};
        		obj.proxyConfig.proxyInfos[i].proxyIp = 	items[i].proxyIp;
        		obj.proxyConfig.proxyInfos[i].proxyPort = items[i].proxyPort;
        	});
        }
    	delete obj.setProxy;
    	/*obj.proxyConfig.proxyInfos = items;*/
    }
    var fieldgrid = BUI.getControl( "fieldgrid" ) ;
    var fields = BUI.JSON.stringify( fieldgrid.getItems() );
    
    obj.tables = [];
    
    table = {};
    table.tablename = obj.tablename;
     delete obj.tablename;
     table.tabledesc = obj.tabledesc;
     delete obj.tabledesc;
     table.numPerPage =  obj.numPerPage;
     delete obj.numPerPage;
     table.extractUrlPattern = obj.extractUrlPattern;
     delete obj.extractUrlPattern;
     table.props =  fieldgrid.getItems();
    
     obj.tables.push(table);
     
     obj.pagingConfig = {};
     obj.pagingConfig.isPaging = obj.isPaging;
     delete obj.isPaging;
     if(obj.pagingConfig.isPaging=="0")
     {
    	 obj.pagingConfig = null;
    	 delete obj.pagingSeedUrl;
    	 delete obj.pagingUrlPattern;
    	 delete obj.pagingNum;
    	 delete obj.stepSize;
    	 delete obj.initPagingNum;
     }else
     {
    	   	obj.pagingConfig.pagingSeedUrl = obj.pagingSeedUrl;
    	     delete obj.pagingSeedUrl;
    	     obj.pagingConfig.pagingUrlPattern = obj.pagingUrlPattern;
    	     delete obj.pagingUrlPattern;
    	     obj.pagingConfig.initPagingNum = obj.initPagingNum;
    	     delete obj.initPagingNum;
    	     obj.pagingConfig.pagingNum = obj.pagingNum;
    	     delete obj.pagingNum;
    	     obj.pagingConfig.stepSize = obj.stepSize;
    	     delete obj.stepSize;
    	     
     }
     // 异步保存配置;
     var configInfo = {};
     configInfo.configInfo =  obj;
     console.log(configInfo);
     $.ajax({
    	   type: "POST",
    	   /*contentType:" application/json",*/
    	   url: "../saveCrawlerConfigInfo",
    	   data: "configInfo="+encodeURIComponent(BUI.JSON.stringify(obj)),
    	  /* data: BUI.JSON.stringify(configInfo),*/
    	   success: function(msg){
    		   if(msg.result == true)
    		   {
    			   alert( "配置保存成功！ " );
    			   Initializer.forwardToList();
    		   }else
    		   {
    			   alert( "配置保存失败,error: "+ msg.error);
    		   }
    	     
    	   }
    	});
  });
}

Initializer.forwardToList = function()
{
	 var tree = parent.BUI.getControl( "mytree" ) ;
	 var item = tree.getItem('4');
	 tree.setSelected(item);
	 var ev = {};
	 ev.item = item;
	 tree.fire('itemclick',ev);
	 
	/* var navTab = parent.BUI.getControl( "tab" ) ; 
	 console.log(navTab);
	 var childTab = navTab.getChild( "12" ) ; 
	 console.log(childTab);
	 navTab.removeChild(childTab); */
}
