layui.use(['table', 'layer', 'jquery', 'fast'], function() {
	var layer = layui.layer,
		table = layui.table,
		$ = layui.jquery,
		fast = layui.fast;

	var tableIns = table.render({
		elem: '#dataConfigTable',
		method: 'post',
		where: {
			'dataSource': fast.getUrlParam('dataSource')
		},
		url: fast.ctxPath + '/dataconfig/list', //数据接口
		page: true, //开启分页
		toolbar: '#toolBar', //开启工具栏，此处显示默认图标，可以自定义模板，详见文档
		cols: [
			[ //表头
				{
					type: 'checkbox',
					align: 'center',
					fixed: 'left'
				}, 
				{
					field: 'uuid',
					hide: true,
					align: 'center',
					title: 'uuid'
				}, 
				{
					field: 'dataSource',
					hide: true,
					align: 'center',
					title: '数据源'
				}, 
				{
					field: 'dataDict',
					hide: true,
					align: 'center',
					title: '数据字典'
				}, 
				{
					field: 'name',
					align: 'center',
					title: '名称'
				}, 
				{
					field: 'key',
					align: 'center',
					title: '配置K值'
				}, 
				{
					field: 'value',
					align: 'center',
					title: '配置V值',
					templet : function(d) {
						return fast.htmlEncode(d.value);
					}
				}, 
				{
					field: 'summary',
					hide: true,
					align: 'center',
					title: '描述'
				}, 
				{
					field: 'sort',
					align: 'center',
					title: '排序'
				}, 
				{
					field: 'remark',
					hide: true,
					align: 'center',
					title: '备注'
				}, 
				{
					field: 'state',
					hide: true,
					align: 'center',
					title: '状态'
				}, 
				{
					field: 'creator',
					hide: true,
					align: 'center',
					title: '创建者'
				}, 
				{
					field: 'createTime',
					hide: true,
					align: 'center',
					title: '创建时间'
				}, 
				{
					field: 'updateTime',
					hide: true,
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
	table.on('toolbar(dataConfigTable)', function(obj) {
		var checkStatus = table.checkStatus(obj.config.id),
			data = checkStatus.data; //获取选中的数据

		if (obj.event === 'add') {
			// 新增数据
			layer.open({
				type: 2,
				title: "新增配置数据",
				shadeClose: false,
				shade: 0.3,
				area: ["48%", "60%"],
				content: fast.ctxPath + '/dataconfig/add?type=101&dataSource='+fast.getUrlParam('dataSource')
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
						url: fast.ctxPath + '/dataconfig/deleteBatch',
						data: {
							ids: ids
						},
						dataType: 'json',
						success: function(res) {
							layer.msg(res.message, {
								icon: 1
							});
							table.reload('dataConfigTable', {
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

	//监听行工具事件
	table.on('tool(dataConfigTable)', function(obj) { //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
		var data = obj.data //获得当前行数据
			,
			layEvent = obj.event; //获得 lay-event 对应的值

		if (layEvent === 'del') {
			// 单记录删除
			layer.confirm('确定要删除吗？', function() {
				$.ajax({
					type: 'post',
					url: fast.ctxPath + '/dataconfig/delete',
					data: {
						uuid: data.uuid
					},
					dataType: 'json',
					success: function(res) {
						layer.msg(res.message, {
							icon: 1
						});
						table.reload('dataConfigTable', {
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
				title: "修改配置数据",
				shadeClose: false,
				shade: 0.3,
				area: ["48%", "60%"],
				content: fast.ctxPath + '/dataconfig/edit?uuid=' + obj.data.uuid
			});
		}
	});

	// 搜索
	$('#searchBtn').on('click', function() {
		tableIns.reload({
			where: fast.getFormData('dataConfigForm'),
			page: {
				curr: 1
			}
		});
	});

	// 重置搜索条件
	$('#resetBtn').on('click', function() {
		document.getElementById('dataConfigForm').reset();
		tableIns.reload({
			where: fast.getFormData('dataConfigForm'),
			page: {
				curr: 1
			}
		});
	});
	
	// 关闭页面
	$('#backBtn').click(function() {
		// 获取当前iframe层的索引
		var index = parent.layer.getFrameIndex(window.name);
		// 关闭弹窗
		parent.layer.close(index);
	});

});
