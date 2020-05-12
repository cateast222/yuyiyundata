var NewsUUID = null;
layui.use([ 'layer', 'table', 'ax', 'laydate', 'admin' ,'fast'], function() {
	var $ = layui.$,
		$ax = layui.ax,
		layer = layui.layer,
		table = layui.table,
		laydate = layui.laydate,
		admin = layui.admin
		fast = layui.fast;

	var News = {
		tableId : "newsTable"
	};

	// 表格数据初始化
	News.initColumn = function() {
		return [ [
				{
					type : "checkbox"
				},
				{
					title : '序号',
					type : "numbers"
				},
				{
					field : 'uuid',
					hide : true,
					title : '报纸新闻id'
				},
				{
					field : 'title',
					sort : true,
					title : '标题',
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
					width : 100,
					align : 'center',
					title : '版面',
				}, {
					field : 'page_name',
					sort : true,
					width : 100,
					align : 'center',
					title : '频道',
				}, {
					field : 'state_name',
					sort : true,
					width : 80,
					align : 'center',
					title : '状态'
				} ] ];
	};

	// 点击查询按钮
	News.search = function() {
		table.reload(News.tableId, {
			where : {
				'condition': $("#newsCondition").val(),
			},
			page:{
				curr: 1
			}
		});
	};

	// 渲染表格
	var newsTableResult = table.render({
		elem : '#' + News.tableId,
		url : Feng.ctxPath + '/datanews/listFromNews',
		where:{
			'dataNewspaper': fast.getUrlParam('newspaperId')
		},
		page : true,
		height : "full-98",
		cellMinWidth : 100,
		limit : 15,
		limits : [ 15, 30, 50, 100, 200 ],
		cols : News.initColumn()
	});

	// 搜索按钮点击事件
	$('#newsBtnSearch').click(function() {
		News.search();
	});

	// 监听行单击事件
	table.on('row(' + News.tableId + ')', function(obj) { // 点击事件
		cleanData();
		NewsUUID = obj.data.uuid;
		for ( var field in obj.data) {
			switch (field) {
			case "url":
				$("#url").attr("href", obj.data[field]);
				$("#url").attr("title", obj.data[field]);
				break;
			case "front_page":
				var frontPage = obj.data[field] == 1 ? "是" : "否";
				$("#front_page").html(frontPage);
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
