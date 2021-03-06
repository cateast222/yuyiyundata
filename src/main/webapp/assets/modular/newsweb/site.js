layui.use(['layer', 'table', 'ax', 'laydate','admin','fast'], function () {
    var $ = layui.$;
    var $ax = layui.ax;
    var layer = layui.layer;
    var table = layui.table;
    var laydate = layui.laydate;
    var admin = layui.admin;
	var fast= layui.fast;
    /**
     * 
     */
    var Site = {
        tableId: "logTable"   //表格id
    };

    /**
     * 初始化表格的列
     */
    Site.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {title: '序号',align: 'center',type: 'numbers'},
            {field: 'uuid', hide: true, sort: true, title: 'id'},
            {field: 'websiteName', sort: true, title: '媒体名称'},
            {field: 'websiteSubName', sort: true, title: '网站名称'},
            {field: 'websiteSubUrl', sort: true, title: '网址'},
            {field: 'subHost', sort: true, title: '域名',templet: function(d) {
    			return '<a style="color: #01AAED;" lay-event="Jump">' + d.subHost + '</a>';
				}},
            {field: 'proxy', sort: true, title: '境内外'},
            {field: 'language', sort: true, title: '语种'},
            {align: 'center', toolbar: '#tableBar', title: '操作', minWidth: 100},
            {align: 'center', toolbar: '#config', title: '配置', minWidth: 100}
        ]];
    };

    /**
     * 点击查询按钮
     */
    Site.search = function () {
        var queryData = {};
        queryData['websiteName'] = $("#websiteName").val();
        queryData['websiteSubName'] = $("#websiteSubName").val();
        queryData['websiteSubUrl'] = $("#websiteSubUrl").val();
        queryData['subHost'] = $("#subHost").val();
        queryData['proxy'] = $("#proxy").val();
        queryData['state'] = $("#state").val();
        table.reload(Site.tableId, {where: queryData});
    };



    //渲染时间选择框
    laydate.render({
        elem: '#beginTime'
    });

    //渲染时间选择框
    laydate.render({
        elem: '#endTime'
    });

    // 渲染表格
    var tableResult = table.render({
        elem: '#' + Site.tableId,
        url: Feng.ctxPath + '/site/list',
        page: true,
        height: "full-98",
        cellMinWidth: 100,
        cols: Site.initColumn()
    });

    // 添加按钮点击事件
    $('#btnAdd').click(function () {
    	layer.open({
    		type: 2,
            area: ['597px', '520px'],
            title: '添加站点信息',
            content: Feng.ctxPath + '/site/add_prefix',
    	})
    });
    
    // 关闭页面
	$('#back').click(function() {
	// 获取当前iframe层的索引
	var index = parent.layer.getFrameIndex(window.name);
	// 关闭弹窗
	parent.layer.close(index);
	});

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        Site.search();
    });

    // 搜索按钮点击事件
    $('#btnClean').click(function () {
        Site.cleanLog();
    });

    // 工具条点击事件
    table.on('tool(' + Site.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;
        if(layEvent === 'content'){
        	layer.open({
			type: 2, 
			title:'详情配置',
			content: Feng.ctxPath + '/dwc?code=pdpzmb&uuid=' + data.uuid,   
			area: ['450px', '370px'],
			resize:false
			});	     
        }else if(layEvent === 'channel'){
          layer.open({
			type: 2, 
			title:'频道配置',
			content: Feng.ctxPath + '/dwc?code=xqpzmb&uuid=' + data.uuid,    
			area: ['450px', '370px'],
			resize:false
			});	   
        }else if(layEvent === 'detail'){
        	layer.open({
			type: 2, 
			title:'网站编辑',
			content: Feng.ctxPath + '/site/dataEdit?id=' + data.uuid,    
			area: ['450px', '400px'],
			resize:false
			});	
		}else if(layEvent === 'Jump'){
		  layer.open({
			type: 2, 
			content: fast.ctxPath + "/datawebchannel/dataweb?id=" + obj.data.uuid,
			title:'频道管理',
			area: ['100%', '100%'],
			resize:false
		 });
        }else if(layEvent === 'delete'){
          layer.confirm('真的删除行么', function (index) {
        	$.ajax({
        		url:fast.ctxPath + "/site/deleteSite?id=" + obj.data.uuid,       
                method:'post',       
                data:data,        
                dataType:'JSON',         
                success:function(res){       
                     if(res.code=='200'){   
                        //删除这一行
	                    obj.del();
	                    //关闭弹框
	                    layer.close(index);
	                    layer.msg('删除成功！', {icon: 1,time:2000,shade:0.2});
					    table.reload('logTable');   	    
                        }                
                    else {
                    	layer.close(index);   
                    	layer.msg('删除失败！', {icon: 2,time:2000,shade:0.2});
                    	}           
                    }   
                 });             
        	})
        }
    });
});
