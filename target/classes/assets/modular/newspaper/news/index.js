var NewspaperUUID = null;
var NewsUUID = null;
layui.use(['layer', 'table', 'ax', 'laydate', 'admin'], function() {
	var $ = layui.$;
	var $ax = layui.ax;
	var layer = layui.layer;
	var table = layui.table;
	var laydate = layui.laydate;
	var photos = layui.photos;
	var admin = layui.admin;

	// 渲染时间选择框
	laydate.render({
		elem: '#publish'
	});

	// 数据源信息表管理
	var Newspaper = {
		tableId: "newspaperTable"
	};
	var News = {
		tableId: "newsTable"
	};

	// 表格数据初始化
	Newspaper.initColumn = function() {
		return [
			[{
				type: "radio"
			}, {
				title: '序号',
				type: "numbers"
			}, {
				field: 'uuid',
				hide: true,
				title: '电子报纸id'
			}, {
				field: 'chsName',
				sort: true,
				align: 'center',
				title: '名称'
			}, {
				field: 'publish',
				sort: true,
				width: 110,
				title: '发布时间'
			}, {
				field: 'newsCount',
				sort: true,
				width: 90,
				align: 'center',
				title: '新闻量'
			}, {
				field: 'creator',
				sort: true,
				width: 90,
				align: 'center',
				title: '采集人'
			}]
		];
	};
	News.initColumn = function() {
		return [
			[{
					type: "checkbox"
				},
				{
					title: '序号',
					type: "numbers"
				},
				{
					field: 'uuid',
					hide: true,
					title: '报纸新闻id'
				},
				{
					field: 'title',
					sort: true,
					title: '标题',
					templet: function(d) {
						var title = "-";
						if (d.title != "") {
							title = d.title;
						}
						return '<a title="' + title +
							'" style="color: #01AAED;" href="' + d.url +
							'" target="_blank">' + title + '</a>';
					}
				}, {
					field: 'page',
					sort: true,
					width: 100,
					align: 'center',
					title: '版面',
				}, {
					field: 'page_name',
					sort: true,
					width: 100,
					align: 'center',
					title: '频道',
				}, {
					field: 'state_name',
					sort: true,
					width: 80,
					align: 'center',
					title: '状态'
				}
			]
		];
	};

	// 点击查询按钮
	Newspaper.search = function(curr) {
		table.reload(Newspaper.tableId, {
			where: {
				'condition': $("#newspaperCondition").val(),
				'publish': $("#publish").val()
			},
			page: {
				curr: curr
			}
		});
	};
	News.search = function(curr) {
		table.reload(News.tableId, {
			where: {
				'condition': $("#newsCondition").val(),
				'dataNewspaper': NewspaperUUID
			},
			page: {
				curr: curr
			}
		});
	};

	// 渲染表格
	var newspaperTableResult = table.render({
		elem: '#' + Newspaper.tableId,
		url: Feng.ctxPath + '/newspaper/listFromNews',
		page: true,
		height: "full-98",
		cellMinWidth: 100,
		limit: 15,
		limits: [15, 30, 50, 100, 200],
		cols: Newspaper.initColumn()
	});
	var newsTableResult = table.render({
		elem: '#' + News.tableId,
		url: Feng.ctxPath + '/datanews/listFromNews',
		page: true,
		height: "full-98",
		cellMinWidth: 100,
		limit: 15,
		limits: [15, 30, 50, 100, 200],
		cols: News.initColumn()
	});

	// 搜索按钮点击事件
	$('#newspaperBtnSearch').click(function() {
		Newspaper.search(1);
	});
	$('#newsBtnSearch').click(function() {
		News.search(1);
	});

	// 新增报纸新闻信息
	$('#newsBtnAdd').click(
		function() {
			if (NewspaperUUID != null) {
				admin.putTempData('formOk', false);
				top.layui.admin.open({
					type: 2,
					area: ['1000px', '800px'],
					offset: 'auto',
					resize: true,
					title: '新增电子报纸',
					content: Feng.ctxPath +
						'/datanews/addAndEdit?dataNewspaper=' +
						NewspaperUUID,
					end: function() {
						admin.getTempData('formOk');
						News.search($(".layui-laypage-em:eq(1)").next().html());
						Newspaper.search($(".layui-laypage-em:eq(0)").next().html());
					}
				});
			} else {
				layer.open({
					title: '提示',
					content: '请先双击电子报纸进行选中，再新增报纸新闻！'
				});
			}
		});
	// 修改报纸新闻信息
	$('#newsBtnEdit').click(
		function() {
			if (NewsUUID != null) {
				admin.putTempData('formOk', false);
				top.layui.admin.open({
					type: 2,
					area: ['1000px', '800px'],
					offset: 'auto',
					resize: true,
					title: ' 修改电子报纸',
					content: Feng.ctxPath + '/datanews/addAndEdit?uuid=' +
						NewsUUID,
					end: function() {
						admin.getTempData('formOk');
						News.search($(".layui-laypage-em:eq(1)").next().html());
						Newspaper.search($(".layui-laypage-em:eq(0)").next().html());
					}
				});
			} else {
				layer.open({
					title: '提示',
					content: '请先点击报纸新闻进行选中，再修改报纸新闻！'
				});
			}
		});
	// 删除报纸新闻信息
	$('#newsBtnDelete').click(
		function() {
			if (NewsUUID != null) {
				var operation = function() {
					var ajax = new $ax(Feng.ctxPath + "/datanews/delete",
						function(data) {
							Feng.success("删除成功!");
							var NewsUUID = null;
							News.search(1);
							Newspaper.search(1);
						},
						function(data) {
							Feng.error("删除失败!" +
								data.responseJSON.message);
						});
					ajax.set("uuid", NewsUUID);
					ajax.start();
				};
				Feng.confirm("是否删除?", operation);
			} else {
				layer.open({
					title: '提示',
					content: '请先点击报纸新闻进行选中，再删除报纸新闻！'
				});
			}
		});

	// 监听行单击事件
	table.on('rowDouble(' + Newspaper.tableId + ')', function(obj) { // 双击事件
		NewspaperUUID = obj.data.uuid;
		News.search(1);

		// (layui—table单击行选中radio与点击 radio选中行
		// https://blog.csdn.net/zyg1515330502/article/details/94554059)
		selected = obj.data;
		// 选中行样式
		obj.tr.addClass('layui-table-click').siblings().removeClass(
			'layui-table-click');
		// 选中radio样式
		obj.tr.find('i[class="layui-anim layui-icon"]').trigger("click");
	});
	table.on('row(' + News.tableId + ')', function(obj) { // 点击事件
		cleanData();
		NewsUUID = obj.data.uuid;
		for (var field in obj.data) {
			switch (field) {
				case "url":
					$("#url").attr("href", obj.data[field]);
					$("#url").attr("title", obj.data[field]);
					break;
				case "front_page":
					var frontPage = obj.data[field] == 1 ? "是" : "否";
					$("#front_page").html(frontPage);
					break;
				case "website_pictures":
					var html = '<br/>';
					if (obj.data[field] != "") {
						var arr1 = obj.data[field].split("#")
						$.each(arr1, function(i, val) {
							html += '<img layer-src="' + val + '" src="' + val + '" width="80" height="80">';
						});
					}
					$("#website_pictures").html(html);
					layer.photos({
						photos: '#website_pictures',
						anim: 5 //0-6的选择，指定弹出图片动画类型，默认随机（请注意，3.0之前的版本用shift参数）
					});
					break;
				default:
					$("#" + field).html(obj.data[field]);
					break;
			}
		}
	});

	function cleanData() {
		$("#url").attr("href", "");
		$("#url").attr("title", "");
		$("#front_page").html("否");
		$("#pretitle").html("");
		$("#title").html("");
		$("#subtitle").html("");
		$("#chs_name").html("");
		$("#language_name").html("");
		$("#region").html("");
		$("#page").html("");
		$("#page_name").html("");
		$("#pubtime").html("");
		$("#number").html("");
		$("#channel").html("");
		$("#paper_count").html("");
		$("#editor").html("");
		$("#checker").html("");
		$("#author").html("");
		$("#author_area").html("");
		$("#author_infos").html("");
		/*$("#source").html("");
		$("#keywords").html("");
		$("#p_v").html("");
		$("#r_v").html("");
		$("#o_v").html("");
		$("#c_v").html("");
		$("#l_v").html("");
		$("#t_v").html("");
		$("#d_v").html("");*/
		$("#ha").html("");
		$("#abstracts").html("");
		$("#tag_content").html("");
		$("#website_thumbnail").html("");
		$("#website_pdf").html("");
		$("#website_pictures").html("");
		$("#pictures_description").html("");

	}
});
