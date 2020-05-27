layui.use(['form', 'admin', 'ax', 'laydate'], function () {
	var $ = layui.jquery;
	var $ax = layui.ax;
	var admin = layui.admin;
	var form = layui.form;
	var laydate = layui.laydate;

	// 渲染时间选择框
	laydate.render({
		elem: '#publish',
		type: 'datetime'
	});

	// 获取详情信息，填充表单
	var ajax = new $ax(Feng.ctxPath + "/newspaper/detail");
	ajax.set({
		uuid: $("#uuid").val(),
		dataSource: $("#dataSource").val()
	});
	var result = ajax.start();
	form.val('newspaperForm', result.data);

	// 表单提交事件
	form.on('submit(btnSubmit)', function (data) {
		var ajax = new $ax(Feng.ctxPath + "/newspaper/addAndEditItem", function (data) {
			Feng.success("修改成功！");
			// 传给上个页面，刷新table用
			admin.putTempData('formOk', true);
			// 关掉对话框
			admin.closeThisDialog();
		}, function (data) {
			Feng.error("修改失败！" + data.responseJSON.message)
		});
		ajax.set(data.field);
		ajax.start();
		return false;
	});
});