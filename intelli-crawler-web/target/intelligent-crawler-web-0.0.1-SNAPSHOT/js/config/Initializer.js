var Initializer = {};

Initializer.initAll = function()
{
	Initializer.initSeedGrid();
	Initializer.initUrlPatternGrid();
	Initializer.initTableGrid();

}
/**
* 初始化入口地址 grid
*/
Initializer.initSeedGrid = function()
{
	BUI.use(['bui/grid','bui/data'],function (Grid,Data) {

    var columns = [{title : '起始url地址',dataIndex :'seedurl',editor : {xtype : 'text',rules:{required:true}}} ],
      //默认的数据
      //data = [{id:'1',seedurl:''}],
      store = new Data.Store({
        data:[]
      }),
      editing = new Grid.Plugins.CellEditing(),
      grid = new Grid.Grid({
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
	BUI.use(['bui/grid','bui/data'],function (Grid,Data) {

    var columns = [{title : '爬取网页url模式',dataIndex :'urlpattern',editor : {xtype : 'text',rules:{required:true}}} ],
      //默认的数据
      //data = [{id:'1',urlpattern:''}],
      store = new Data.Store({
        data:[]
      }),
      editing = new Grid.Plugins.CellEditing(),
      grid = new Grid.Grid({
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

/**
* 数据提取字段信息;
*/
Initializer.initTableGrid=function()
{
  BUI.use(['bui/grid','bui/data'],function (Grid,Data) {

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