layui.use(['table', 'layer', 'jquery', 'fast'], function() {
	var layer = layui.layer,
		table = layui.table,
		$ = layui.jquery,
		fast = layui.fast;

	var sysUserTable = table.render({
		elem: '#sysUserTable',
		method: 'post',
		where: {
			'deptId':fast.getUrlParam('deptId'),
			'userName':$('#userName').val()
		},
		url: fast.ctxPath + '/apidataauth/getcustomer', // 数据接口
		limit: 20,
		limits: [20, 50, 100, 200],
		cols: [
			[ // 表头
				{
					type: 'radio'
				}, {
					title: '序号',
					align: 'center',
					type: "numbers"
				}, {
					field: 'userId',
					hide: true,
					align: 'center',
					title: '主键'
				}, {
					field: 'name',
					sort: true,
					align: 'center',
					title: '数据客户'
				}
			]
		]
	});

	var tableIns = table.render({
		elem: '#apiDataAuthTable',
		method: 'post',
		url: fast.ctxPath + '/apidataauth/list', // 数据接口
		page: true, // 开启分页
		toolbar: '#toolBar', // 开启工具栏，此处显示默认图标，可以自定义模板，详见文档
		cols: [
			[ // 表头
				{
					type: 'checkbox',
					align: 'center',
					fixed: 'left'
				}, {
					field: 'uuid',
					hide: true,
					align: 'center',
					title: '主键'
				}, {
					field: 'sysUser',
					align: 'center',
					title: '调取者用户'
				}, {
					field: 'dataSource',
					hide: true,
					align: 'center',
					title: '授权数据源'
				}, {
					field: 'validity',
					align: 'center',
					title: '有效期'
				}, {
					field: 'level',
					hide: true,
					align: 'center',
					title: '级别(备用)'
				}, {
					field: 'remark',
					hide: true,
					align: 'center',
					title: '备注'
				}, {
					field: 'state',
					hide: true,
					align: 'center',
					title: '状态'
				}, {
					field: 'creator',
					hide: true,
					align: 'center',
					title: '创建者'
				}, {
					field: 'createTime',
					hide: true,
					align: 'center',
					title: '创建时间'
				}, {
					field: 'updateTime',
					hide: true,
					align: 'center',
					title: '更新时间'
				}, {
					fixed: 'right',
					width: 150,
					align: 'center',
					toolbar: '#lineBar',
					title: '操作'
				}
			]
		]
	});

	// 监听头工具栏事件
	table.on('toolbar(apiDataAuthTable)', function(obj) {
		var checkStatus = table
			.checkStatus(obj.config.id),
			data = checkStatus.data; // 获取选中的数据

		if (obj.event === 'add') {
			// 新增数据
			layer.open({
				type: 2,
				title: "新增数据",
				shadeClose: false,
				shade: 0.3,
				area: ["80%", "80%"],
				content: fast.ctxPath +
					'/dataAuth/add'
			});
		} else if (obj.event === 'delete') {
			// 批量删除
			if (checkStatus.data.length === 0) {
				layer.msg('请选择要删除的数据', {
					icon: 2
				});
			} else {
				layer.confirm('确定要删除选中数据吗？', function(index) {
					var ids = [];
					$.each(data, function(index, element) {
						ids.push(element.uuid);
					});
					layer.close(index);
					// 异步请求
					$.ajax({
						type: 'post',
						url: fast.ctxPath + '/dataAuth/deleteBatch',
						data: {
							ids: ids
						},
						dataType: 'json',
						success: function(res) {
							layer.msg(res.message, {
								icon: 1
							});
							table.reload('dataAuthTable', {
								page: {
									curr: 1
								}
							});
						}
					});
				});
			}
		}
	});

	// 监听行工具事件
	table.on('tool(apiDataAuthTable)', function(obj) { // 注：tool
		// 是工具条事件名，test
		// 是 table
		// 原始容器的属性
		// lay-filter="对应的值"
		var data = obj.data, // 获得当前行数据
			layEvent = obj.event; // 获得 lay-event 对应的值
		if (layEvent === 'del') {
			// 单记录删除
			layer.confirm('确定要删除吗？', function() {
				$.ajax({
					type: 'post',
					url: fast.ctxPath + '/dataAuth/delete',
					data: {
						uuid: data.uuid
					},
					dataType: 'json',
					success: function(res) {
						layer.msg(res.message, {
							icon: 1
						});
						table.reload('dataAuthTable', {
							page: {
								curr: 1
							}
						});
					}
				});
			});
		} else if (layEvent === 'edit') {
			// 修改
			layer.open({
				type: 2,
				title: "修改数据",
				shadeClose: false,
				shade: 0.3,
				area: ["80%", "80%"],
				content: fast.ctxPath + '/dataAuth/edit?uuid=' + obj.data.uuid
			});
		}
	});

	// 搜索
	$('#searchApiDataAuthBtn').on('click', function() {
		tableIns.reload({
			where: {
				'dataSourceChsName':$('#dataSourceChsName').val()
			},
			page: {
				curr: 1
			}
		});
	});

	// 搜索
	$('#searchSysUserBtn').on('click', function() {
		sysUserTable.reload({
			page: {
				curr: 1
			}
		});
	});

});
