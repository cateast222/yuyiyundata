var DatasourecUUID = null;
layui.use(['layer','table', 'ax'], function () {
	var $ = layui.$;
	var table = layui.table;
	var $ax = layui.ax;
	var layer = layui.layer;

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
			[
				{
					field: 'uuid',
					hide: true,
					title: '数据源id'
				}, {
					field: 'chsName',
					sort: true,
					title: '名称'
				}, /* {
					field: 'websiteUrl',
					sort: true,
					title: '网址',
					templet: function (d) {
						return '<a title="备注：' + d.remark +
							'" style="color: #01AAED;" href="' +
							d.websiteUrl + '" target="_blank">' +
							d.websiteUrl + '</a>';
					}
				}, */ {
					field: 'newpaperCount',
					sort: true,
					title: '电子报纸数'
				}, {
					field: 'lastAcquTime',
					sort: true,
					title: '最近采集时间'
				}
			]
		];
	};
	Newspaper.initColumn = function () {
		return [
			[{
				field: 'uuid',
				hide: true,
				title: '电子报纸id'
			},
			{
				field: 'chsName',
				sort: true,
				title: '名称',
				templet: function (d) {
					var title = "-";
					if (d.title != "") {
						title = d.title;
					}
					return '<a title="' + title +
						'" style="color: #01AAED;" href="' + d.url +
						'" target="_blank">' + title + '</a>';
				}
			}, {
				field: 'publish',
				sort: true,
				title: '发布时间',
			}, {
				field: 'url',
				sort: true,
				title: 'URL',
				templet: function (d) {
					return '<a title="' + d.url +
						'" style="color: #01AAED;" href="' + d.url +
						'" target="_blank">' + d.url + '</a>';
				}
			}, {
				field: 'cover',
				align: 'center',
				title: '封面',
				templet: function (d) {
					return '<a title = "' + d.fullName + '"><img src = "'
						+ d.cover + '" style = "width:60px;height:100px;"/></a>';
				}
			}, {
				field: 'newsCount',
				sort: true,
				title: '报纸新闻数'
			}, {
				field: 'state',
				sort: true,
				title: '状态'
			}, {
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
		url: Feng.ctxPath + '/datasourec/listFromNewspaper',
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
		limit: 10,
		limits: [10, 20, 50, 100, 200],
		cols: Newspaper.initColumn()
	});

	/**
	 * 点击查询按钮
	 */
	Datasourec.search = function () {
		var queryData = {};
		queryData['condition'] = $("#datasourecBtnSearch").val();
		table.reload(Datasourec.tableId, {
			where: queryData
		});
	};
	Newspaper.search = function () {
		var queryData = {};
		queryData['condition'] = $("#newspaperCondition").val();
		queryData['dataSource'] = DatasourecUUID;
		table.reload(Newspaper.tableId, {
			where: queryData
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
			}
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
		if (DatasourecUUID = null) {
			layer.open({
				title: '提示',
				content: '请先双击信息源进行选中，再新增电子报纸！'
			});
		} else {
			Newspaper.openAddDlg();
		}
	});
	// 搜索按钮点击事件
	$('#datasourecBtnSearch').click(function () {
		Datasourec.search();
	});
	$('#newspaperBtnSearch').click(function () {
		Newspaper.search();
	});

	// 工具条点击事件
	table.on('tool(' + Newspaper.tableId + ')', function (obj) {
		var data = obj.data;
		var layEvent = obj.event;
		if (layEvent === 'testRun') {
			Datasi.onTestRun(data);
		} else if (layEvent === 'testPass') {
			Datasi.onTestPass(data);
		} else if (layEvent === 'testFail') {
			Datasi.onTestFail(data);
		}
	});

	// 监听行单击事件
	table.on('rowDouble(' + Datasourec.tableId + ')', function (obj) {
		DatasourecUUID = obj.data.uuid;
		Newspaper.search();
	});
	/* table.on('row(' + WgEleNewsData.tableId + ')', function (obj) {
		for (var field in obj.data) {
			switch (field) {
				case "url":
					$("#url").attr("href", obj.data[field]);
					$("#url").attr("title", obj.data[field]);
					break;
				case "frontPage":
					var frontPage = obj.data[field] == 1 ? "是" : "否";
					$("#frontPage").html(frontPage);
					break;
				default:
					$("#" + field).html(obj.data[field]);
					break;
			}
		}

	}); */
});
