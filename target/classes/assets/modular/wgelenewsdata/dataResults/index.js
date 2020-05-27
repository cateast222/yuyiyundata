var DatasiUUID = 0;
var PubTime = "2019-12-12";
layui.use(['layer', 'table', 'ax', 'laydate'], function() {
	var $ = layui.$;
	var $ax = layui.ax;
	var layer = layui.layer;
	var table = layui.table;
	var laydate = layui.laydate;

	// 渲染时间选择框
	laydate.render({
		elem: '#pubTime'
	});

	// 数据源信息表管理
	var Datasi = {
		tableId: "datasiTable"
	};
	var WgEleNewsData = {
		tableId: "wgEleNewsDataTable"
	};

	// 表格数据初始化
	Datasi.initColumn = function() {
		return [
			[{
				field: 'dsiUuid',
				hide: true,
				title: '数据源信息id'
			}, {
				field: 'dsiName',
				sort: true,
				title: '名称'
			}, {
				field: 'pubtime',
				sort: true,
				title: '发布时间'
			}, {
				field: 'dataVolume',
				sort: true,
				title: '数据量'
			}, {
				field: 'creator',
				sort: true,
				title: '采集人'
			}]
		];
	};
	WgEleNewsData.initColumn = function() {
		return [
			[{
					field: 'uuid',
					hide: true,
					title: '电子报纸新闻id'
				},
				{
					field: 'title',
					sort: true,
					title: '新闻标题',
					templet: function(d) {
						var title = "-";
						if (d.title != "") {
							title = d.title;
						}
						return '<a title="' +
							title +
							'" style="color: #01AAED;" href="' +
							d.url + '" target="_blank">' +
							title + '</a>';
					}
				},
				{
					field: 'page',
					sort: true,
					title: '所属版面',
				},
				{
					field: 'pageName',
					sort: true,
					title: '版面名称',
				},
				{
					field: 'state',
					sort: true,
					title: '状态',
					templet: function(d) {
						return d.state == -2 ? '系统测试数据' :
							d.state == -1 ? '测试数据' :
							d.state == 1 ? '正常数据' :
							d.state == 2 ? '已归档数据' :
							'其他';
					}
				}
			]
		];
	};

	// 点击查询按钮
	Datasi.search = function() {
		var queryData = {};
		queryData['condition'] = $("#datasiCondition").val();
		queryData['pubTime'] = $("#pubTime").val();
		table.reload(Datasi.tableId, {
			where: queryData
		});
	};
	WgEleNewsData.search = function() {
		var queryData = {};
		queryData['condition'] = $("#wgEleNewsDataCondition")
			.val();
		queryData['dsiUuid'] = DatasiUUID;
		queryData['pubTime'] = PubTime;
		table.reload(WgEleNewsData.tableId, {
			where: queryData
		});
	};

	// 渲染表格
	var datasiTableResult = table.render({
		elem: '#' + Datasi.tableId,
		url: Feng.ctxPath + '/wendata/getDateArchive',
		page: true,
		height: "full-98",
		cellMinWidth: 100,
		limit: 15,
		limits: [15, 30, 50, 100, 200],
		cols: Datasi.initColumn()
	});
	var wgEleNewsDataTableResult = table.render({
		elem: '#' + WgEleNewsData.tableId,
		url: Feng.ctxPath + '/wendata/getDateNewslist',
		page: true,
		height: "full-98",
		cellMinWidth: 100,
		limit: 15,
		limits: [15, 30, 50, 100, 200],
		cols: WgEleNewsData.initColumn()
	});

	// 搜索按钮点击事件
	$('#datasiBtnSearch').click(function() {
		Datasi.search();
	});
	$('#wgEleNewsDataBtnSearch').click(function() {
		WgEleNewsData.search();
	});

	// 监听行单击事件
	table.on('rowDouble(' + Datasi.tableId + ')', function(obj) { // 双击事件
		DatasiUUID = obj.data.dsiUuid;
		PubTime = obj.data.pubtime;
		WgEleNewsData.search();
	});
	table.on('row(' + WgEleNewsData.tableId + ')', function(obj) { // 点击事件
		for (var field in obj.data) {
			switch (field) {
				case "url":
					$("#url").attr("href",
						obj.data[field]);
					$("#url").attr("title",
						obj.data[field]);
					break;
				case "frontPage":
					var frontPage = obj.data[field] == 1 ? "是" :
						"否";
					$("#frontPage").html(frontPage);
					break;
				default:
					$("#" + field).html(
						obj.data[field]);
					break;
			}
		}
	});
});
