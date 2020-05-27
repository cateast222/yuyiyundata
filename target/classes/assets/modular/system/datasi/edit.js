/**
 * 详情对话框
 */
var DatasiInfoDlg = {
	data : {
		websiteUrl : "",
		websiteName : "",
		platform : "",
		country : "",
		region : "",
		proxy : "",
		state : "",
		remark : "",
		language : ""
	}
};

layui.use([ 'form', 'ax' ], function() {
	var $ = layui.jquery;
	var $ax = layui.ax;
	var form = layui.form;

	// 获取详情信息，填充表单
	var ajax = new $ax(Feng.ctxPath + "/datasi/detail?uuid="
			+ Feng.getUrlParam("uuid"));
	var result = ajax.start();
	form.val('datasiForm', result.data);

	// 表单提交事件
	form.on('submit(btnSubmit)', function(data) {
		var ajax = new $ax(Feng.ctxPath + "/datasi/editItem", function(data) {
			Feng.success("更新成功！");
			window.location.href = Feng.ctxPath + "/datasi";
		}, function(data) {
			Feng.error("更新失败！" + data.responseJSON.message)
		});
		ajax.set(data.field);
		ajax.start();

		return false;
	});

	// 返回按钮
	$("#backupPage").click(function() {
		window.location.href = Feng.ctxPath + "/datasi";
	});
});