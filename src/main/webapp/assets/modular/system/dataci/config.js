/**
 * 详情对话框
 */
var DataciInfoDlg = {
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

layui.use([ 'form','admin', 'ax' ], function() {
	var $ = layui.jquery;
	var $ax = layui.ax;
	var admin = layui.admin;
	var form = layui.form;

	// 获取详情信息，填充表单
	var ajax = new $ax(Feng.ctxPath + "/dataci/detail?dsiUuid="
			+ $("#dsiUuid").val() + "&key=" + $("#key").val());
	var result = ajax.start();
	form.val('dataciForm', result.data);

	// 表单提交事件
	form.on('submit(btnSubmit)', function(data) {
		var ajax = new $ax(Feng.ctxPath + "/dataci/addAndEdit", function(data) {
			Feng.success("添加成功！");
			// 传给上个页面，刷新table用
			admin.putTempData('formOk', true);
			// 关掉对话框
			admin.closeThisDialog();
		}, function(data) {
			Feng.error("添加失败！" + data.responseJSON.message)
		});
		ajax.set(data.field);
		ajax.start();
		return false;
	});
});