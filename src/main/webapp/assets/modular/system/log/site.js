layui.use(['layer', 'table', 'ax', 'laydate','admin','fast'], function () {
    var $ = layui.$;
    var $ax = layui.ax;
    var layer = layui.layer;
    var table = layui.table;
    var laydate = layui.laydate;
    var admin = layui.admin;
	var fast= layui.fast;
    /**
     * 系统管理--操作日志
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
            {field: 'uuid', hide: true, sort: true, title: 'id'},
            {field: 'website_name', sort: true, title: '媒体名称'},
            {field: 'website_sub_name', sort: true, title: '网站名称'},
            {field: 'website_sub_url', sort: true, title: '网址'},
            {field: 'sub_host', sort: true, title: '域名',templet: function(d) {
    			return '<a style="color: #01AAED;" lay-event="Jump">' + d.sub_host + '</a>';
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
        queryData['website_name'] = $("#website_name").val();
        queryData['website_sub_name'] = $("#website_sub_name").val();
        queryData['website_sub_url'] = $("#website_sub_url").val();
        queryData['sub_host'] = $("#sub_host").val();
        queryData['proxy'] = $("#proxy").val();
        queryData['state'] = $("#state").val();
        table.reload(Site.tableId, {where: queryData});
    };

    /**
     * 导出excel按钮
     */
    Site.exportExcel = function () {
        var checkRows = table.checkStatus(Site.tableId);
        if (checkRows.data.length === 0) {
            Feng.error("请选择要导出的数据");
        } else {
            table.exportFile(tableResult.config.id, checkRows.data, 'xls');
        }
    };
    
    

   /* /!**
     * 日志详情
     *!/
    Site.logDetail = function (param) {
       /!* var ajax = new $ax(Feng.ctxPath + "/log/detail/" + param.operationLogId, function (data) {
            Feng.infoDetail("日志详情", data);
        }, function (data) {
            Feng.error("获取详情失败!");
        });
        ajax.start();*!/
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '修改日志',
            content: Feng.ctxPath + '/log/detail/' + param.operationLogId,
            end: function () {
                admin.getTempData('formOk') && table.reload(Role.tableId);
            }
        });
    };

    /!**
     * 清空日志
     *!/
    Site.cleanLog = function () {
        Feng.confirm("是否清空所有日志?", function () {
            var ajax = new $ax(Feng.ctxPath + "/log/delLog", function (data) {
                Feng.success("清空日志成功!");
                Site.search();
            }, function (data) {
                Feng.error("清空日志失败!");
            });
            ajax.start();
        });
    };*/

    /**
     * 弹出添加对话框
     */
    Site.openAddDlg = function () {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            area: ['597px', '673px'],
            title: '添加站点信息',
            content: Feng.ctxPath + '/site/add_prefix',
            end: function () {
                admin.getTempData('formOk') && Site.initTable(Site.tableId);
            }
        });
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
        Site.openAddDlg();
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
        if(layEvent === 'detail'){
        	layer.open({
				type: 2, 
				content: fast.ctxPath + "/site/dataEdit?id=" + obj.data.uuid,
				area: ['450px', '480px'],
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
                    	layer.msg('请先删除频道！', {icon: 2,time:2000,shade:0.2});
                    	}           
                    }   
                 });             
        	})
        }
    });
});
