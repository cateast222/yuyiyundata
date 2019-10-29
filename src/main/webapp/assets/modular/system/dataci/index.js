/**
 * 
 */
layui.use([ 'table', 'admin', 'ax' ], function() {
	var $ = layui.$;
	var table = layui.table;
	var admin = layui.admin;
	var $ax = layui.ax;

	var Dataci = {
		tableId : "dataciTable"
	};

	Dataci.initColumn = function() {
		return [ [ {
			type : 'checkbox'
		}, {
			field : 'key',
			hide : true,
			title : '配置项'
		}, {
			field : 'summary',
			sort : true,
			title : '配置项名称'
		}, {
			field : 'state',
			sort : true,
			title : '排序'
		}, {
			align : 'center',
			toolbar : '#tableBar',
			title : '操作'
		} ] ];
	};

	Dataci.openEditDlg = function(data) {
		var datasiUuid = $("#datasiUuid").val();
		admin.putTempData('formOk', false);
		top.layui.admin.open({
			type : 2,
			area : [ '820px', '650px' ],
			offset : 'auto',
			resize : true,
			title : '信息配置',
			content : Feng.ctxPath + '/dataci/edit?key=' + data.key+ '&summary=' + data.summary
					+ '&dsiUuid=' + datasiUuid + '&state=' + data.state,
			end : function() {
				admin.getTempData('formOk');
			}
		});
	};

	var tableResult = table.render({
		elem : '#' + Dataci.tableId,
		url : Feng.ctxPath + '/datadi/config',
		page : true,
		height : "full-98",
		cellMinWidth : 100,
		limit : 30,
		limits : [ 30 ],
		cols : Dataci.initColumn()
	});

	// 关闭页面
	$('#btnBack').click(function() {
		window.location.href = Feng.ctxPath + "/datasi";
	});

	table.on('tool(' + Dataci.tableId + ')', function(obj) {
		var data = obj.data;
		var layEvent = obj.event;
		if (layEvent === 'edit') {
			Dataci.openEditDlg(data);
		}
	});
});