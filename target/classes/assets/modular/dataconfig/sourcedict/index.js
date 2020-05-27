layui.use(['form', 'table', 'layer', 'jquery', 'fast'], function() {
	var layer = layui.layer,
		table = layui.table,
		$ = layui.jquery,
		fast = layui.fast;

	var tableIns = table.render({
		elem: '#sourceDictTable',
		method: 'post',
		where: {
			'type': fast.getUrlParam('type'),
			'dataSource': fast.getUrlParam('dataSource')
		},
		url: Feng.ctxPath + '/sourcedict/sourceDictList', // 数据接口
		page: true, // 开启分页
		toolbar: '#toolBar', // 开启工具栏，此处显示默认图标，可以自定义模板，详见文档
		cols: [
			[ // 表头
				{
					type: 'checkbox',
					fixed: 'left'
				},
				{
					field : 'uuid',
					hide : true,
					title : 'id'
				},
				{
					field: 'name',
					sort: true,
					align: 'center',
					title: '名称',
				},
				{
					field: 'code',
					sort: true,
					align: 'center',
					title: '编号'
				},
				{
					field: 'type_name',
					hide: true,
					sort: true,
					align: 'center',
					title: '类型'
				},
				{
					fixed: 'right',
					width: 150,
					align: 'center',
					toolbar: '#lineBar',
					title: '操作'
				}
			]
		]
	});

	var search = function(curr) {
		tableIns.reload({
			where: fast.getFormData('sourceDictForm'),
			page: {
				curr: curr
			}
		});
	}

	// 监听头工具栏事件
	table.on('toolbar(sourceDictTable)', function(obj) {
		var checkStatus = table.checkStatus(obj.config.id),
			data = checkStatus.data; // 获取选中的数据

		if (obj.event === 'add') {
			// 新增数据
			layer.open({
				type: 2,
				title: "新增数据源字典",
				shadeClose: false,
				shade: 0.3,
				area: ["45%", "75%"],
				content: Feng.ctxPath + '/sourcedict/add?parentUuid=1&type=' + fast.getUrlParam('type') + '&dataSource=' +
					fast.getUrlParam('dataSource'),
				end: function() {
					search($(".layui-laypage-em:eq(0)").next().html());
				}
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
						url: Feng.ctxPath + '/sourcedict/deleteBatch',
						data: {
							ids: ids
						},
						dataType: 'json',
						success: function(res) {
							layer.msg(res.message, {
								icon: 1
							});
							search(1);
						}
					});
				});
			}
		}
	});

	// 监听行工具事件
	table.on('tool(sourceDictTable)', function(obj) { // 注：tool
		/**
		 * 是工具条事件名，test
		 * 是 table
		 * 原始容器的属性
		 * lay-filter="对应的值"
		 */
		var data = obj.data, // 获得当前行数据
			layEvent = obj.event; // 获得 lay-event 对应的值

		if (layEvent === 'del') {
			// 单记录删除
			layer.confirm('确定要删除吗？', function() {
				$.ajax({
					type: 'post',
					url: Feng.ctxPath + '/sourcedict/delete',
					data: {
						uuid: data.uuid
					},
					dataType: 'json',
					success: function(res) {
						layer.msg(res.message, {
							icon: 1
						});
						search(1);
					}
				});
			});
		} else if (layEvent === 'edit') {
			// 修改
			layer.open({
				type: 2,
				title: "配置数据",
				shadeClose: false,
				shade: 0.3,
				area: ["45%", "75%"],
				content: Feng.ctxPath + '/sourcedict/edit?dataDict=' + data.dataDict + '&dataSource=' +
				data.dataSource,
				end: function() {
					search($(".layui-laypage-em:eq(0)").next().html());
				}
			});
		}
	});

	// 搜索
	$('#searchBtn').on('click', function() {
		search(1);
	});

	// 重置搜索条件
	$('#resetBtn').on('click', function() {
		document.getElementById('sourceDictForm').reset();
		search(1);
	});

	// 关闭页面
	$('#backBtn').click(function() {
		// 获取当前iframe层的索引
		var index = parent.layer.getFrameIndex(window.name);
		// 关闭弹窗
		parent.layer.close(index);
	});
});
