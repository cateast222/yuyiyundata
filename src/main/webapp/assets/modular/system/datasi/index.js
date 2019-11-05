layui.use([ 'table', 'ax' ],
		function() {
			var $ = layui.$;
			var table = layui.table;
			var $ax = layui.ax;

			/**
			 * 数据源信息表管理
			 */
			var Datasi = {
				tableId : "datasiTable"
			};

			/**
			 * 初始化表格的列
			 */
			Datasi.initColumn = function() {
				return [ [
						{
							type : 'checkbox'
						},
						{
							field : 'uuid',
							hide : true,
							title : '数据源信息id'
						},
						{
							field : 'websiteName',
							sort : true,
							title : '名称',
							templet : function(d) {
								var url = Feng.ctxPath + '/dataci?datasiUuid='
										+ d.uuid;
								return '<a style="color: #01AAED;" href="'
										+ url + '">' + d.websiteName + '</a>';
							}
						}, {
							field : 'websiteUrl',
							sort : true,
							title : '网址'
						}, {
							field : 'platform',
							sort : true,
							title : '平台',
							templet : function(d) {
								if (d.platform == 1) {
									return "电子报纸";
								} else if (d.platform == 2) {
									return "APP新闻";
								} else {
									return "其他";
								}
							}
						}, {
							field : 'country',
							sort : true,
							title : '国家'
						}, {
							field : 'region',
							sort : true,
							title : '地区'
						}, {
							field : 'proxy',
							sort : true,
							title : '境区',
							templet : function(d) {
								if (d.proxy == 0) {
									return "境内";
								} else if (d.proxy == 1) {
									return "境外";
								} else {
									return "其他";
								}
							}
						}, {
							field : 'language',
							sort : true,
							title : '语种'
						}, {
							field : 'state',
							sort : true,
							title : '状态',
							templet : function(d) {
								if (d.state == 1) {
									return "启用";
								} else if(d.state == 0){
									return "测试";
								}else {
									return "弃用";
								}
							}
						}, {
							align : 'center',
							toolbar : '#tableBar',
							title : '操作'
						} ] ];
			};

			/**
			 * 点击查询按钮
			 */
			Datasi.search = function() {
				var queryData = {};
				queryData['condition'] = $("#condition").val();
				// queryData['systemFlag'] = $("#systemFlag").val();
				// queryData['status'] = $("#status").val();
				table.reload(Datasi.tableId, {
					where : queryData
				});
			};

			/**
			 * 弹出添加对话框
			 */
			Datasi.openAddDlg = function() {
				window.location.href = Feng.ctxPath + '/datasi/add';
			};

			/**
			 * 点击编辑
			 * 
			 * @param data
			 *            点击按钮时候的行数据
			 */
			Datasi.openEditDlg = function(data) {
				window.location.href = Feng.ctxPath + '/datasi/edit?uuid='
						+ data.uuid;
			};

			/**
			 * 点击删除
			 * 
			 * @param data
			 *            点击按钮时候的行数据
			 */
			Datasi.onDeleteItem = function(data) {
				var operation = function() {
					var ajax = new $ax(Feng.ctxPath + "/datasi/delete",
							function(data) {
								Feng.success("删除成功!");
								table.reload(Datasi.tableId);
							}, function(data) {
								Feng.error("删除失败!" + data.responseJSON.message
										+ "!");
							});
					ajax.set("uuid", data.uuid);
					ajax.start();
				};
				Feng.confirm("是否删除?", operation);
			};

			// 渲染表格
			var tableResult = table.render({
				elem : '#' + Datasi.tableId,
				url : Feng.ctxPath + '/datasi/list',
				page : true,
				height : "full-98",
				cellMinWidth : 100,
				cols : Datasi.initColumn()
			});

			// 搜索按钮点击事件
			$('#btnSearch').click(function() {
				Datasi.search();
			});

			// 添加按钮点击事件
			$('#btnAdd').click(function() {
				Datasi.openAddDlg();
			});

			// 工具条点击事件
			table.on('tool(' + Datasi.tableId + ')', function(obj) {
				var data = obj.data;
				var layEvent = obj.event;

				if (layEvent === 'edit') {
					Datasi.openEditDlg(data);
				} else if (layEvent === 'delete') {
					Datasi.onDeleteItem(data);
				}
			});
		});