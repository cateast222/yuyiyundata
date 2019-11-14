layui.use([ 'table', 'ax' ], function() {
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
						return '<a title="备注：' + d.remark
								+ '" style="color: #01AAED;" href="'
								+ d.websiteUrl + '" target="_blank">'
								+ d.websiteName + '</a>';
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
		window.location.href = Feng.ctxPath + '/datasi/edit?uuid=' + data.uuid;
	};

	/**
	 * 点击删除
	 * 
	 * @param data
	 *            点击按钮时候的行数据
	 */
	Datasi.onDeleteItem = function(data) {
		var operation = function() {
			var ajax = new $ax(Feng.ctxPath + "/datasi/delete", function(data) {
				Feng.success("删除成功!");
				table.reload(Datasi.tableId);
			}, function(data) {
				Feng.error("删除失败!" + data.responseJSON.message + "!");
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
		limit : 15,
		limits : [ 15, 30, 50, 100, 200 ],
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

		if (layEvent === 'testRun') {
			Datasi.openEditDlg(data);
		} else if (layEvent === 'testPass') {
			Datasi.onDeleteItem(data);
		} else if (layEvent === 'testFail') {
			Datasi.onDeleteItem(data);
		}
	});
});