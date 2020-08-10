var sysUser = -1;
layui.use(['table', 'layer', 'jquery','laydate', 'fast'], function() {
    var layer = layui.layer,
        table = layui.table,
        $ = layui.jquery,
        fast = layui.fast;

    var laydate = layui.laydate;
    $(document).on('click','#addUrl',function(){
        layer.open({
            type: 2,
            content: 'add',
            area: ['450px', '470px'],
            resize:false
        });
    });



    var selectSelf = table.render({
        elem: '#selectSelf',
        method: 'post',
        url: fast.ctxPath + '/newsweb/selectMedia', // 数据接口
        // where: {
        // 	'deptId': fast.getUrlParam('deptId')
        // },
        page: true, // 开启分页
        limit: 10,
        limits: [10, 20, 100, 200],
        cols: [
            [ // 表头
                {
                    type: 'radio'
                }, {
                title: '序号',
                align: 'center',
                type: 'numbers'
                },
                {
                    field: 'webSiteName',
                    sort: true,
                    align: 'center',
                    title: '媒体名称',
                    templet: function(d) {
    				return '<a style="color: #01AAED;" lay-event="Jump">' + d.webSiteName + '</a>';
					}
                },
                {
                    field: 'webSiteUrl',
                    width: 150,
                    align: 'center',
                    sort: true,
                    title: '媒体网址'
                    },
                {
                    field: 'host',
                    width: 150,
                    align: 'center',
                    sort: true,
                    title: '一级域名'
                },
                {
                field: 'country',
                width: 150,
                align: 'center',
                sort: true,
                title: '国家'
                },
                {
                    field: 'region',
                    width: 150,
                    align: 'center',
                    sort: true,
                    title: '省市县'
                },
                {
                    field: 'proxy',
                    width: 150,
                    align: 'center',
                    sort: true,
                    title: '境区',templet: function(d){
                        if (d.proxy=='1'){
                            return '<span >境内</span>';
                        }
                        else if (d.proxy=='2'){
                            return '<span >境外</span>';
                        }

                    }
                },
                {
                    fixed: 'right',
                    width: 200,
                    align: 'center',
                    toolbar: '#lineBar',
                    title: '操作'


                },
                {
                    fixed: 'right',
                    width: 200,
                    align: 'center',
                    toolbar: '#lineBars',
                    title: '配置'


                }


            ]
        ]
    });




    // 监听行工具事件
    table.on('tool(lineBar)', function(obj) { // 注：tool
        // 是工具条事件名，test
        // 是 table
        // 原始容器的属性
        // lay-filter='对应的值'
        var data = obj.data, // 获得当前行数据
            layEvent = obj.event; // 获得 lay-event 对应的值
        if (layEvent === 'edit') {
            // 修改
            layer.open({
                type: 2,
                title: '修改媒体信息',
                shadeClose: false,
                shade: 0.3,
                area: ['450px', '470px'],
                content: fast.ctxPath + '/newsweb/edidUnrevised?uuid='+data.uuid,
                success: function(layero, index) {
                },
                end: function() {
                    //	tableIns.search();
                }
            });
        }else if(layEvent === 'Jump'){
        	 // 跳转
            layer.open({
                type: 2,
                title: '网站管理',
                shadeClose: false,
                shade: 0.3,
                area: ['100%', '100%'],
                content: fast.ctxPath + '/site/website?id='+data.uuid,
                success: function(layero, index) {
                },
                end: function() {
                    //	tableIns.search();
                }
            });
        }else if(layEvent === 'delPer') {
            layer.confirm('真的删除行么', function (index) {
                $.ajax({
                    url: fast.ctxPath + '/newsweb/delMedia', // 数据接口
                    type: "POST",
                    data: {uuid: data.uuid},
                    success: function (msg) {
                        if (msg!=null) {
                            //删除这一行
                            obj.del();
                            //关闭弹框
                            layer.close(index);
                            layer.msg("删除成功", {icon: 6});
                        } else {
                            layer.msg("删除失败", {icon: 5});
                        }
                    }
                });
                return false;
            });
        }
    });


    // 历史记录搜索
    $('#searchSysUserBtn').on('click', function() {
        selectSelf.search();
    });




    //历史记录表格加载
    selectSelf.search = function() {
        selectSelf.reload({
            where: {
                'webSiteName': $('#webSiteName').val(),
                'webSiteUrl': $('#webSiteUrl').val(),
                'host': $('#host').val()
            },
            page: {
                curr: 1
            }
        });
    }























});


