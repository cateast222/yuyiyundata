layui.use([ 'form', 'admin', 'ax' ], function() {
	var $ = layui.jquery;
	var $ax = layui.ax;
	var admin = layui.admin;
	var form = layui.form;

	// 获取详情信息，填充表单
//	var ajax = new $ax(Feng.ctxPath + "/dataci/detail?dsiUuid="
//			+ $("#dsiUuid").val() + "&key=" + $("#key").val());
	var ajax = new $ax(Feng.ctxPath + "/datasource/detail");
	ajax.set({
		uuid : $("#uuid").val()
	});
	var result = ajax.start();
	form.val('datasourceForm', result.data);

	// 表单提交事件
	form.on('submit(btnSubmit)', function(data) {
		var ajax = new $ax(Feng.ctxPath + "/datasource/addAndEditItem", function(data) {
			Feng.success("修改成功！");
			// 关掉对话框
			window.location.href = Feng.ctxPath + "/datasource";
		}, function(data) {
			Feng.error("修改失败！" + data.responseJSON.message)
		});
		ajax.set(data.field);
		ajax.start();
		return false;
	});

	// 返回按钮
	$("#backupPage").click(function() {
		window.location.href = Feng.ctxPath + "/datasource";
	});
});