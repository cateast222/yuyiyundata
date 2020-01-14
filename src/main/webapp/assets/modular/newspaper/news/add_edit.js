layui.use(['form', 'admin', 'ax', 'layedit'], function () {
	var $ = layui.jquery;
	var $ax = layui.ax;
	var admin = layui.admin;
	var form = layui.form;
	var layedit = layui.layedit;

	//建立编辑器
	var newslayedit = layedit.build('tagContent', {
		height: 400, //设置编辑器高度
		tool: [
			'strong' //加粗
			, 'italic' //斜体
			, 'underline' //下划线
			, 'del' //删除线
			, '|' //分割线
			, 'left' //左对齐
			, 'center' //居中对齐
			, 'right' //右对齐
			, 'link' //超链接
			, 'unlink' //清除链接
			, 'face' //表情
			, 'image' //插入图片
			, 'help' //帮助
		]
	});

	// 获取详情信息，填充表单
	var ajax = new $ax(Feng.ctxPath + "/datanews/detail");
	ajax.set({
		uuid: $("#uuid").val(),
		dataNewspaper: $("#dataNewspaper").val()
	});
	var result = ajax.start();
	form.val('newsForm', result.data);
    layedit.setContent(newslayedit,result.data.tagContent);

	// 表单提交事件
	form.on('submit(btnSubmit)', function (data) {
		var ajax = new $ax(Feng.ctxPath + "/datanews/addAndEditItem", function (data) {
			Feng.success("修改成功！");
			// 传给上个页面，刷新table用
			admin.putTempData('formOk', true);
			// 关掉对话框
			admin.closeThisDialog();
		}, function (data) {
			Feng.error("修改失败！" + data.responseJSON.message)
		});
		// layedit.sync(newslayedit);
		data.field.content = layedit.getText(newslayedit);
		data.field.tagContent = layedit.getContent(newslayedit);
		ajax.set(data.field);
		ajax.start();
		return false;
	});
});