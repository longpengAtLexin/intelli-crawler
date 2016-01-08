var CrawlerTaskList = {};

CrawlerTaskList.init=function()
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
	        render : '#crawlerTaskgrid',
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