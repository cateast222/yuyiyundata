var WgEleNewsDataUUID = 0;
var DatasiUUID = 0;
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
	var WgEleNewsData = {
		tableId : "wgEleNewsDataTable"
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
	WgEleNewsData.initColumn = function() {
		return [ [
				{
					field : 'uuid',
					hide : true,
					title : '电子报纸新闻id'
				},
				{
					field : 'title',
					sort : true,
					title : '新闻标题',
					templet : function(d) {
						var title = "-";
						if (d.title != "") {
							title = d.title;
						}
						return '<a title="' + title
								+ '" style="color: #01AAED;" href="' + d.url
								+ '" target="_blank">' + title + '</a>';
					}
				}, {
					field : 'page',
					sort : true,
					title : '所属版面',
				}, {
					field : 'pageName',
					sort : true,
					title : '版面名称',
				}, {
					field : 'pubtime',
					sort : true,
					title : '发布时间',
				} ] ];
	};

	/**
	 * 点击查询按钮
	 */
	Datasi.search = function() {
		var queryData = {};
		queryData['condition'] = $("#datasiCondition").val();
		table.reload(Datasi.tableId, {
			where : queryData
		});
	};
	WgEleNewsData.search = function() {
		var queryData = {};
		queryData['condition'] = $("#wgEleNewsDataCondition").val();
		queryData['dsiUuid'] = DatasiUUID;
		queryData['state'] = -2;
		table.reload(WgEleNewsData.tableId, {
			where : queryData
		});
	};

	/**
	 * 点击数据测试
	 * 
	 * @param data
	 *            点击按钮时候的行数据
	 */
	Datasi.onTestRun = function(data) {
		var ajax = new $ax(Feng.ctxPath + "/wendata/datatestRun",
				function(data) {
					Feng.success("数据开始采集！将在10份中内出结果，请等待稍后查看！");
					table.reload(Datasi.tableId);
				}, function(data) {
					Feng.error("数据测试失败!" + data.responseJSON.message + "!");
				});
		ajax.set("uuid", data.uuid);
		ajax.set("state", 0);
		ajax.start();
	};

	/**
	 * 点击数据测试通过
	 * 
	 * @param data
	 *            点击按钮时候的行数据
	 */
	Datasi.onTestPass = function(data) {
		var ajax = new $ax(Feng.ctxPath + "/wendata/datatestPass", function(
				data) {
			Feng.success("数据测试通过成功！");
			table.reload(Datasi.tableId);
		}, function(data) {
			Feng.error("数据测试通过失败!" + data.responseJSON.message + "!");
		});
		ajax.set("uuid", data.uuid);
		ajax.set("state", 4);
		ajax.start();
	};

	/**
	 * 点击删除
	 * 
	 * @param data
	 *            点击按钮时候的行数据
	 */
	Datasi.onTestFail = function(data) {
		var operation = function() {
			var ajax = new $ax(Feng.ctxPath + "/wendata/datatestFail",
					function(data) {
						Feng.success("数据测试失败，退回成功!");
						table.reload(Datasi.tableId);
					}, function(data) {
						Feng.error("退回失败!" + data.responseJSON.message + "!");
					});
			ajax.set("uuid", data.uuid);
			ajax.set("state", 2);
			ajax.start();
		};
		Feng.confirm("是否退回?", operation);
	};

	// 渲染表格
	var datasiTableResult = table.render({
		elem : '#' + Datasi.tableId,
		url : Feng.ctxPath + '/datasi/datatestlist',
		page : true,
		height : "full-98",
		cellMinWidth : 100,
		limit : 15,
		limits : [ 15, 30, 50, 100, 200 ],
		cols : Datasi.initColumn()
	});
	var wgEleNewsDataTableResult = table.render({
		elem : '#' + WgEleNewsData.tableId,
		url : Feng.ctxPath + '/wendata/list',
		page : true,
		height : "full-98",
		cellMinWidth : 100,
		limit : 15,
		limits : [ 15, 30, 50, 100, 200 ],
		cols : WgEleNewsData.initColumn()
	});

	// 搜索按钮点击事件
	$('#datasiBtnSearch').click(function() {
		Datasi.search();
	});
	$('#wgEleNewsDataBtnSearch').click(function() {
		WgEleNewsData.search();
	});

	// 工具条点击事件
	table.on('tool(' + Datasi.tableId + ')', function(obj) {
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
	table.on('rowDouble(' + Datasi.tableId + ')', function(obj) {
		// console.log(obj.tr) //得到当前行元素对象
		// console.log(obj.data) // 得到当前行数据
		// obj.del(); //删除当前行
		// obj.update(fields) //修改当前行数据
		DatasiUUID = obj.data.uuid;
		WgEleNewsData.search();
	});
	table.on('row(' + WgEleNewsData.tableId + ')', function(obj) {
		// console.log(obj.tr) //得到当前行元素对象
		// console.log(obj.data) // 得到当前行数据
		// obj.del(); //删除当前行
		// obj.update(fields) //修改当前行数据
		for ( var field in obj.data) {
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

	});
});