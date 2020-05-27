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

	// 表单提交事件
	form.on('submit(btnSubmit)', function(data) {
		var ajax = new $ax(Feng.ctxPath + "/datasi/addItem", function(data) {
			Feng.success("添加成功！");
			window.location.href = Feng.ctxPath + "/datasi";
		}, function(data) {
			Feng.error("添加失败！" + data.responseJSON.message)
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