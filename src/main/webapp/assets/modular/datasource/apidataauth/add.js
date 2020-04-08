layui.use(['table', 'layer', 'jquery', 'fast', 'ax', 'laydate'], function() {
	var layer = layui.layer,
		table = layui.table,
		$ = layui.jquery,
		$ax = layui.ax,
		laydate = layui.laydate,
		fast = layui.fast;
	
	// 渲染时间选择框
	laydate.render({
		elem: '#validity',
		type: 'datetime',
		value: new Date()
	});
	
	var tableIns = table.render({
		elem: '#datasourceTable',
		method: 'post',
		url: fast.ctxPath + '/apidataauth/getdatasource', // 数据接口
		page: true, // 开启分页
		limit: 20,
		limits: [20, 50, 100, 200],
		toolbar: '#toolBar', // 开启工具栏，此处显示默认图标，可以自定义模板，详见文档
		cols: [
			[ // 表头
				{
					type: 'checkbox'
				}, {
					title: '序号',
					type: "numbers"
				}, {
					field: 'uuid',
					hide: true,
					title: "数据源id"
				}, {
					field: 'chs_name',
					sort: true,
					title: '数据源名称',
					align: 'center',
				}, {
					field: 'platformName',
					sort: true,
					title: '平台',
					align: 'center',
				}
			]
		]
	});
	
	// 数据权限搜索
	$('#searchBtn').on('click', function() {
		tableIns.reload({
			where: {
				'condition':$("#condition").val(),
				'platform':$("#platform").val()
			},
			page: {
				curr: 1
			}
		});
	});
	
	// 监听头工具栏事件
	table.on('toolbar(datasourceTable)', function(obj) {
		var checkStatus = table.checkStatus(obj.config.id),
			data = checkStatus.data; // 获取选中的数据
		var validity = $('#validity').val();
		if (obj.event === 'add') {
			// 批量删除
			if (checkStatus.data.length === 0) {
				layer.msg('请选择要添加的数据源', {
					icon: 2
				});
			} else {
					var ids = [],chsnames = [];
					$.each(data, function(index, element) {
						ids.push(element.uuid);
						chsnames.push(element.chs_name);
						layer.close(index);
					});
					// 异步请求
					$.ajax({
						type: 'post',
						url: fast.ctxPath + '/apidataauth/addItem',
						data: {
							ids: ids,
							chsnames: chsnames,
							validity: validity,
							sysUser: fast.getUrlParam('sysUser')
						},
						dataType: 'json',
						success: function(data) {
		    				// 提示信息
		    				top.layer.msg(data.message, {
		    					icon: 1
		    				});
		    				// 获取当前iframe层的索引
		    				var index = parent.layer.getFrameIndex(window.name);
		    				// 关闭弹窗
		    				parent.layer.close(index);
		    				// 重载表格
		    				parent.layui.tableIns.search();
//		    				parent.layui.table.reload('dataDictConfTable', {
//		    					page: {
//		    						curr: 1
//		    					}
//		    				});
		    			},
		    			error: function(data) {
		    				Feng.error("添加失败！" + data.responseJSON.message)
		    			}
				});
			}
		}
	});
});