layui.use(['form','table', 'layer', 'jquery','fast'], function() {
	var layer = layui.layer,
		table = layui.table,
		$ = layui.jquery,
		fast = layui.fast;
	
	var tableIns = table.render({
		elem: '#dataDictTable',
		method: 'post',
		where: {'type':$("#type").val()},
		url: Feng.ctxPath + '/datadict/list', // 数据接口
		page: true, // 开启分页
		toolbar: '#toolBar', // 开启工具栏，此处显示默认图标，可以自定义模板，详见文档
		cols: [
			[ // 表头
				{
					type: 'checkbox',
					fixed: 'left'
				},
				{
					field: 'uuid',
					hide: true,
					sort: true,
					align: 'center',
					title: 'uuid'
				},
				/*{
					field: 'type',
					hide: true,
					sort: true,
					align: 'center',
					title: '类型'
				},
				{
					field: 'parentUuid',
					hide: true,
					sort: true,
					align: 'center',
					title: '上级uuid'
				},*/
				{
					field: 'code',
					sort: true,
					align: 'center',
					title: '编号',
					templet : function(d) {
						var url = Feng.ctxPath + '/datadictconf?parentUuid=' + d.uuid;
						return '<a style="color:#01AAED;" href="' + url + '">' + d.code + '</a>';
					}
				},
				{
					field: 'name',
					sort: true,
					align: 'center',
					title: '名称',
					templet : function(d) {
						var url = Feng.ctxPath + '/datadictconf?parentUuid=' + d.uuid;
						return '<a style="color:#01AAED;" href="' + url + '">' + d.name + '</a>';
					}
				},
				{
					field: 'summary',
					sort: true,
					align: 'center',
					title: '描述'
				},
				{
					field: 'datas',
					hide: true,
					sort: true,
					align: 'center',
					title: '数据'
				},
				{
					field: 'sort',
					sort: true,
					align: 'center',
					title: '排序'
				},
				{
					field: 'remark',
					hide: true,
					sort: true,
					align: 'center',
					title: '备注'
				},
				{
					field: 'state',
					sort: true,
					align: 'center',
					title: '状态'
				},
				{
					field: 'creator',
					hide: true,
					sort: true,
					align: 'center',
					title: '创建者'
				},
				{
					field: 'createTime',
					hide: true,
					sort: true,
					align: 'center',
					title: '创建时间'
				},
				{
					field: 'updateTime',
					hide: true,
					sort: true,
					align: 'center',
					title: '更新时间'
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

	// 监听头工具栏事件
	table.on('toolbar(dataDictTable)', function(obj) {
		var checkStatus = table.checkStatus(obj.config.id),
			data = checkStatus.data; // 获取选中的数据

		if (obj.event === 'add') {
			// 新增数据
			layer.open({
				type: 2,
				title: "新增数据字典",
				shadeClose: false,
				shade: 0.3,
				area: ["45%", "75%"],
				content: Feng.ctxPath + '/datadict/add'
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
						url: Feng.ctxPath + '/datadict/deleteBatch',
						data: {
							ids: ids
						},
						dataType: 'json',
						success: function(res) {
							layer.msg(res.message, {
									icon: 1
								});
							table.reload('dataDictTable', {
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
	table.on('tool(dataDictTable)', function(obj) { // 注：tool
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
					url: Feng.ctxPath + '/datadict/delete',
					data: {
						uuid: data.uuid
					},
					dataType: 'json',
					success: function(res) {
						layer.msg(res.message, {
							icon: 1
						});
						table.reload('dataDictTable', {
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
				title: "修改字典数据",
				shadeClose: false,
				shade: 0.3,
				area: ["45%", "75%"],
				content: Feng.ctxPath + '/datadict/edit?uuid=' + obj.data.uuid
			});
		}
	});

	// 搜索
	$('#searchBtn').on('click', function() {
		tableIns.reload({
			where: fast.getFormData('dataDictForm'),
			page: {
				curr: 1
			}
		});
	});

	// 重置搜索条件
	$('#resetBtn').on('click', function() {
		document.getElementById('dataDictForm').reset();
		tableIns.reload({
			where: fast.getFormData('dataDictForm'),
			page: {
				curr: 1
			}
		});
	});	
});