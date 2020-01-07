layui.use(['table', 'ax'],
	function () {
		var $ = layui.$;
		var table = layui.table;
		var $ax = layui.ax;

		/**
		 * 数据源信息表管理
		 */
		var Datasource = {
			tableId: "datasourceTable"
		};

		/**
		 * 初始化表格
		 */
		Datasource.initColumn = function () {
			return [[
				{
					type: 'checkbox'
				},
				{
					field: 'uuid',
					hide: true,
					title: "数据源id"
				},
				{
					field: 'chsName',
					sort: true,
					title: '中文名称',
					templet: function (d) {
						var url = Feng.ctxPath + '/dataci/newspaper?uuid='
							+ d.uuid;
						var color = "#01AAED;";
						if (d.remark != "" && d.state == 2) {
							color = "#FF3333;";
						}
						return '<a title="备注：' + d.remark
							+ '" style="color:' + color
							+ '" href="' + url + '">'
							+ d.chsName + '</a>';
					},
					align: 'center',
					width: 120
				},
				{
					field: 'orgName',
					sort: true,
					title: '原名称',
					align: 'center',
					width: 120
				},
				{
					field: 'platform',
					sort: true,
					title: '平台',
					templet: function (d) {
						var platform = d.platform;
						return platform == 1 ? '电子报纸' :
							platform == 2 ? '新闻网站' :
								platform == 3 ? 'APP' :
									platform == 4 ? '国内QQ' :
										platform == 5 ? '国内微信' :
											platform == 6 ? '国内微博' :
												platform == 7 ? '国外Facebook' :
													platform == 8 ? '国外Twitter' :
														'其他';
					},
					align: 'center',
					width: 120
				},
				{
					field: 'websiteUrl',
					sort: true,
					title: '网站地址',
					templet: function (d) {
						return '<a title="' + d.websiteUrl +
							'" style="color: #01AAED;" href="' +
							d.websiteUrl + '" target="_blank">' +
							d.websiteUrl + '</a>';
					}
				},
				{
					field: 'websiteName',
					sort: true,
					title: '网站名称',
					align: 'center',
					width: 120
				},
				{
					field: 'country',
					sort: true,
					title: '国家',
					align: 'center',
					width: 80
				}, {
					field: 'region',
					sort: true,
					title: '地区'
				}, {
					field: 'proxy',
					sort: true,
					title: '境区',
					templet: function (d) {
						if (d.proxy == 0) {
							return "境内";
						} else if (d.proxy == 1) {
							return "境外";
						} else {
							return "其他";
						}
					},
					align: 'center',
					width: 80
				}, {
					field: 'language',
					sort: true,
					title: '语种',
					align: 'center',
					width: 80
				}, {
					field: 'encoded',
					sort: true,
					title: '编码',
					align: 'center',
					width: 90
				}, {
					field: 'state',
					sort: true,
					title: '状态',
					templet: function (d) {
						if (d.state == 1) {
							return "启用";
						} else if (d.state == 0) {
							return "测试";
						} else if (d.state == 2) {
							return "配置";
						} else if (d.state == 3) {
							return "测试完成";
						} else if (d.state == 4) {
							return "测试通过";
						} else {
							return "弃用";
						}
					},
					align: 'center',
					width: 90
				}, {
					toolbar: '#tableBar',
					title: '操作',
					align: 'center',
					width: 120
				}
			]];
		};

		/**
		 * 点击查询按钮
		 */
		Datasource.search = function () {
			var queryData = {};
			queryData['condition'] = $("#condition").val();
			queryData['state'] = $("#state").val();
			queryData['platform'] = 1;
			table.reload(Datasource.tableId, {
				where: queryData
			});
		};

		// 渲染表格
		var tableResult = table.render({
			elem: '#' + Datasource.tableId,
			url: Feng.ctxPath + '/datasource/list?platform=1',
			page: true,
			height: "full-98",
			cellMinWidth: 100,
			limit: 15,
			limits: [15, 30, 50, 100, 200],
			cols: Datasource.initColumn()
		});


		/**
		 * 弹出添加对话框
		 */
		Datasource.openAddDlg = function () {
			window.location.href = Feng.ctxPath + '/datasource/addAndEditNewspaper?uuid=';
		};

		/**
		 * 点击编辑
		 * 
		 * @param data
		 *            点击按钮时候的行数据
		 */
		Datasource.openEditDlg = function (data) {
			window.location.href = Feng.ctxPath + '/datasource/addAndEditNewspaper?uuid='
				+ data.uuid;
		};

		/**
		 * 点击删除
		 * 
		 * @param data
		 *            点击按钮时候的行数据
		 */
		Datasource.onDeleteItem = function (data) {
			var operation = function () {
				var ajax = new $ax(Feng.ctxPath + "/datasource/delete",
					function (data) {
						Feng.success("删除成功!");
						table.reload(Datasource.tableId);
					}, function (data) {
						Feng.error("删除失败!" + data.responseJSON.message);
					});
				ajax.set("uuid", data.uuid);
				ajax.start();
			};
			Feng.confirm("是否删除?", operation);
		};

		// 搜索按钮点击事件
		$('#btnSearch').click(function () {
			Datasource.search();
		});

		// 添加按钮点击事件
		$('#btnAdd').click(function () {
			Datasource.openAddDlg();
		});

		// 工具条点击事件
		table.on('tool(' + Datasource.tableId + ')', function (obj) {
			var data = obj.data;
			var layEvent = obj.event;

			if (layEvent === 'edit') {
				Datasource.openEditDlg(data);
			} else if (layEvent === 'delete') {
				Datasource.onDeleteItem(data);
			}
		});
	});