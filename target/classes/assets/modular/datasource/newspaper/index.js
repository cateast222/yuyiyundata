layui.use([ 'table', 'ax','layer','fast' ], function() {
	var $ = layui.$,
		table = layui.table,
		layer = layui.layer,
		$ax = layui.ax,
		fast = layui.fast;

	/**
	 * 数据源信息表管理
	 */
	var Datasource = {
		tableId : "datasourceTable"
	};

	/**
	 * 初始化表格
	 */
	Datasource.initColumn = function() {
		return [ [
				{
					type : 'checkbox'
				},
				{
					title : '序号',
					type : "numbers"
				},
				{
					field : 'uuid',
					hide : true,
					title : "数据源id"
				},
				{
					field : 'chs_name',
					sort : true,
					title : '中文名称',
					toolbar : '#sourcedictBar',
					align : 'center',
					width : 120
				},
				{
					field : 'org_name',
					sort : true,
					title : '原名称',
					toolbar : '#dataconfigBar',
					align : 'center',
					width : 120
				},
				{
					field : 'providerName',
					sort : true,
					title : '提供方',
					align : 'center',
					width : 140
				},
				{
					field : 'website_url',
					sort : true,
					title : '网站地址',
					templet : function(d) {
						return '<a title="' + d.website_url
								+ '" style="color: #01AAED;" href="'
								+ d.website_url + '" target="_blank">'
								+ d.website_url + '</a>';
					}
				}, {
					field : 'website_name',
					sort : true,
					title : '网站名称',
					align : 'center',
					width : 120
				}, {
					field : 'countryName',
					sort : true,
					title : '国家',
					align : 'center',
					width : 100
				}, {
					field : 'region',
					sort : true,
					title : '地区'
				}, {
					field : 'proxyName',
					sort : true,
					title : '境区',
					align : 'center',
					width : 70
				}, {
					field : 'languageName',
					sort : true,
					title : '语种',
					align : 'center',
					width : 100
				}, {
					field : 'encodedName',
					sort : true,
					title : '编码',
					align : 'center',
					width : 90
				}, {
					field : 'stateName',
					sort : true,
					title : '状态',
					align : 'center',
					width : 65
				}, {
					toolbar : '#tableBar',
					title : '操作',
					align : 'center',
					width : 130
				} ] ];
	};

	/**
	 * 点击查询按钮
	 */
	Datasource.search = function(curr) {
		table.reload(Datasource.tableId, {
			where : {
				'condition' : $("#condition").val(),
				'state' : $("#state").val(),
				'provider' : $("#provider").val()
			},
			page : {
				curr : curr// 重新从第 1 页开始
			}
		});
	};

	// 渲染表格
	var tableResult = table.render({
		elem : '#' + Datasource.tableId,
		url : Feng.ctxPath + '/datasource/list?platform=1',
		page : true,
		height : "full-98",
		cellMinWidth : 100,
		limit : 15,
		limits : [ 15, 30, 50, 100, 200 ],
		cols : Datasource.initColumn()
	});

	/**
	 * 弹出添加对话框
	 */
	Datasource.openAddDlg = function() {
		layer.open({
			type: 2,
			title: '新增数据',
			shadeClose: false,
			shade: 0.3,
			area: ['45%', '99%'],
			content: fast.ctxPath + '/datasource/addAndEditNewspaper?uuid=',
			end: function() {
				Datasource.search($(".layui-laypage-em:eq(0)").next().html());
			}
		});
	};

	/**
	 * 点击编辑对话框
	 * 
	 * @param data
	 *            点击按钮时候的行数据
	 */
	Datasource.openEditDlg = function(data) {
		layer.open({
			type: 2,
			title: '新增数据',
			shadeClose: false,
			shade: 0.3,
			area: ['45%', '99%'],
			content: fast.ctxPath + '/datasource/addAndEditNewspaper?uuid=' + data.uuid,
			end: function() {
				Datasource.search($(".layui-laypage-em:eq(0)").next().html());
			}
		});
	};
	
	/**
	 * 点击配置数据对话框
	 */
	Datasource.dataconfigDlg = function(data) {
		layer.open({
			type: 2,
			title: '配置数据',
			shadeClose: false,
			shade: 0.3,
			area: ['100%', '100%'],
			content: fast.ctxPath + '/dataconfig?dataSource=' + data.uuid,
			end: function() {
				Datasource.search($(".layui-laypage-em:eq(0)").next().html());
			}
		});
	};
	
	/**
	 * 点击字典数据对话框
	 */
	Datasource.sourcedictDlg = function(data) {
		layer.open({
			type: 2,
			title: '字典数据',
			shadeClose: false,
			shade: 0.3,
			area: ['100%', '100%'],
			content: fast.ctxPath + '/sourcedict?type=111&dataSource=' + data.uuid,
		});
	};

	/**
	 * 点击删除
	 * 
	 * @param data
	 *            点击按钮时候的行数据
	 */
	Datasource.onDeleteItem = function(data) {
		var operation = function() {
			var ajax = new $ax(Feng.ctxPath + "/datasource/delete", function(
					data) {
				Feng.success("删除成功!");
				table.reload(Datasource.tableId);
			}, function(data) {
				Feng.error("删除失败!" + data.responseJSON.message);
			});
			ajax.set("uuid", data.uuid);
			ajax.start();
		};
		Feng.confirm("是否删除?", operation);
	};

	// 搜索按钮点击事件
	$('#btnSearch').click(function() {
		Datasource.search(1);
	});

	// 添加按钮点击事件
	$('#btnAdd').click(function() {
		Datasource.openAddDlg();
	});

	// 工具条点击事件
	table.on('tool(' + Datasource.tableId + ')', function(obj) {
		var data = obj.data;
		var layEvent = obj.event;

		if (layEvent === 'edit') {
			Datasource.openEditDlg(data);
		} else if (layEvent === 'delete') {
			Datasource.onDeleteItem(data);
		}else if (layEvent === 'dataconfig') {
			Datasource.dataconfigDlg(data);
		}else if (layEvent === 'sourcedict') {
			Datasource.sourcedictDlg(data);
		}
	});

	// 添加按钮点击事件
	$('#btnMigration').click(function() {
		window.location.href = Feng.ctxPath + '/datasource/migration';
	});
	
	
});