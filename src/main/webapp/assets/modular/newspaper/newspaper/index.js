var DatasourecUUID = null;
layui.use(['admin', 'layer', 'table', 'ax'], function () {
	var $ = layui.$;
	var table = layui.table;
	var $ax = layui.ax;
	var layer = layui.layer;
	var admin = layui.admin;

	/**
	 * 数据源信息表管理
	 */
	var Datasourec = {
		tableId: "datasourecTable"
	};
	var Newspaper = {
		tableId: "newspaperTable"
	};

	/**
	 * 初始化表格的列
	 */
	Datasourec.initColumn = function () {
		return [
			[{
				type: "radio"
			}, {
				title: '序号',
				type: "numbers"
			}, {
				field: 'uuid',
				hide: true,
				title: '数据源id'
			}, {
				field: 'chsName',
				sort: true,
				align: 'center',
				title: '名称'
			}, {
				field: 'newpaperCount',
				sort: true,
				width: 120,
				align: 'center',
				title: '电子报纸数'
			}, {
				field: 'lastAcquTime',
				sort: true,
				width: 140,
				align: 'center',
				title: '最新采集时间'
			}
			]
		];
	};
	Newspaper.initColumn = function () {
		return [
			[{
				type: "checkbox"
			}, {
				title: '序号',
				type: "numbers"
			}, {
				field: 'uuid',
				hide: true,
				title: '电子报纸id'
			},
			{
				field: 'chsName',
				sort: true,
				width: 180,
				align: 'center',
				title: '名称',
				toolbar: '#fullNameBar',
			}, {
				field: 'publish',
				sort: true,
				width: 110,
				align: 'center',
				title: '发布时间',
			}, {
				field: 'cover',
				align: 'center',
				title: '封面',
				width: 120,
				align: 'center',
				templet: "#coverImg"
			}, {
				field: 'url',
				sort: true,
				align: 'center',
				title: 'URL',
				templet: function (d) {
					return '<a title="' + d.url +
						'" style="color: #01AAED;" href="' + d.url +
						'" target="_blank">' + d.url + '</a>';
				}
			}, {
				field: 'newsCount',
				sort: true,
				width: 120,
				align: 'center',
				title: '报纸新闻数'
			}, {
				field: 'stateName',
				sort: true,
				width: 80,
				align: 'center',
				title: '状态'
			}, {
				width: 160,
				align: 'center',
				toolbar: '#tableBar',
				title: '操作'
			}
			]
		];
	};

	/**
	 * 渲染表格
	 */
	var datasourecTableResult = table.render({
		elem: '#' + Datasourec.tableId,
		url: Feng.ctxPath + '/datasource/listFromNewspaper',
		page: true,
		height: "full-98",
		cellMinWidth: 100,
		limit: 15,
		limits: [15, 30, 50, 100, 200],
		cols: Datasourec.initColumn()
	});
	var newspaperTableResult = table.render({
		elem: '#' + Newspaper.tableId,
		url: Feng.ctxPath + '/newspaper/listFromNewspaper',
		page: true,
		height: "full-98",
		cellMinWidth: 100,
		limit: 5,
		limits: [5, 10, 20, 50, 100],
		cols: Newspaper.initColumn()
	});

	/**
	 * 点击查询按钮
	 */
	Datasourec.search = function (curr) {
		table.reload(Datasourec.tableId, {
			where: {
				'condition': $("#datasourecCondition").val()
			},
			page : {
				curr : curr// 重新从第 1 页开始
			}
		});
	};
	Newspaper.search = function (curr) {
		table.reload(Newspaper.tableId, {
			where: {
				'condition': $("#newspaperCondition").val(),
				'dataSource': DatasourecUUID
			},
			page : {
				curr : curr// 重新从第 1 页开始
			}
		});
	};

	/**
	 * 弹出添加对话框
	*/
	Newspaper.openAddDlg = function () {
		admin.putTempData('formOk', false);
		top.layui.admin.open({
			type: 2,
			area: ['820px', '650px'],
			offset: 'auto',
			resize: true,
			title: '新增电子报纸',
			content: Feng.ctxPath + '/newspaper/addAndEdit?dataSource=' + DatasourecUUID + '&uuid=',
			end: function () {
				admin.getTempData('formOk');
				Datasourec.search($(".layui-laypage-em:eq(0)").next().html());
				Newspaper.search($(".layui-laypage-em:eq(1)").next().html());
			}
		});
	};

	/**
	 * 点击查看
	 * 
	 * @param data
	 *            点击按钮时候的行数据
	 */
	Newspaper.openCheckDlg = function (data) {
		admin.putTempData('formOk', false);
		top.layui.admin.open({
			type: 2,
			area: ['80%', '88%'],
			offset: 'auto',
			resize: true,
			title: '查看报刊新闻',
			content: Feng.ctxPath + '/newspaper/check?newspaperId=' + data.uuid
		});
	};
	
	/**
	 * 点击编辑
	 * 
	 * @param data
	 *            点击按钮时候的行数据
	 */
	Newspaper.openEditDlg = function (data) {
		admin.putTempData('formOk', false);
		top.layui.admin.open({
			type: 2,
			area: ['820px', '650px'],
			offset: 'auto',
			resize: true,
			title: '编辑电子报纸',
			content: Feng.ctxPath + '/newspaper/addAndEdit?dataSource='
				+ DatasourecUUID + '&uuid=' + data.uuid,
			end: function () {
				admin.getTempData('formOk');
				Datasourec.search($(".layui-laypage-em:eq(0)").next().html());
				Newspaper.search($(".layui-laypage-em:eq(1)").next().html());
			}
		});
	};

	/**
	 * 点击删除
	 * 
	 * @param data 点击按钮时候的行数据
	 */
	Newspaper.onDeleteItem = function (data) {
		var operation = function () {
			var ajax = new $ax(Feng.ctxPath + "/newspaper/delete",
				function (data) {
					Feng.success("删除成功!");
					table.reload(Datasourec.tableId);
					table.reload(Newspaper.tableId);
				}, function (data) {
					Feng.error("删除失败!" + data.responseJSON.message);
				});
			ajax.set("uuid", data.uuid);
			ajax.start();
		};
		Feng.confirm("是否删除?", operation);
	};



	// 添加按钮点击事件
	$('#newspaperBtnAdd').click(function () {
		if (DatasourecUUID == null) {
			layer.open({
				title: '提示',
				content: '请先双击数据源进行选中，再新增电子报纸！'
			});
		} else {
			Newspaper.openAddDlg();
		}
	});
	// 搜索按钮点击事件
	$('#datasourecBtnSearch').click(function () {
		Datasourec.search(1);
	});
	$('#newspaperBtnSearch').click(function () {
		Newspaper.search(1);
	});

	// 工具条点击事件
	table.on('tool(' + Newspaper.tableId + ')', function (obj) {
		var data = obj.data;
		var layEvent = obj.event;
		if (layEvent === 'edit') {
			if (DatasourecUUID == null) {
				layer.open({
					title: '提示',
					content: '请先双击数据源进行选中，再修改电子报纸！'
				});
			} else {
				Newspaper.openEditDlg(data);
			}
		} else if (layEvent === 'delete') {
			Newspaper.onDeleteItem(data);
		}else if (layEvent === 'check') {
			Newspaper.openCheckDlg(data);
		}
	});

	// 监听行单击事件
	table.on('rowDouble(' + Datasourec.tableId + ')', function (obj) {
		DatasourecUUID = obj.data.uuid;
		Newspaper.search(1);
		// (layui—table单击行选中radio与点击 radio选中行	https://blog.csdn.net/zyg1515330502/article/details/94554059)
		selected = obj.data;
		//选中行样式
		obj.tr.addClass('layui-table-click').siblings().removeClass('layui-table-click');
		//选中radio样式
		obj.tr.find('i[class="layui-anim layui-icon"]').trigger("click");
	});
});


