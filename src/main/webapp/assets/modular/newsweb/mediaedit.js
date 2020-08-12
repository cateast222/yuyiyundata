layui.use(['form', 'admin', 'ax', 'laydate','fast'], function () {
	var $ = layui.jquery;
	var $ax = layui.ax;
	var fast = layui.fast;
	var admin = layui.admin;
	var form = layui.form;
	var laydate = layui.laydate;

	// 获取详情信息，填充表单
	var ajax = new $ax(Feng.ctxPath + "/newsweb/selectByidMedia");
	ajax.set({
		uuid: $("#uuid").val(),
	});
	var result = ajax.start();
	  var json =result.data;
	form.val('editMediaForm', json[0]);

	// 表单提交事件
	form.on('submit(btnSubmitA)', function (data) {
		console.log(data)
		$.ajax({
			url:fast.ctxPath + "/newsweb/updateMedia",
			method:'post',
			data:data.field,
			dataType:'JSON',
			success:function(res){
				if(res.code=='200'){
					admin.putTempData('formOk', true);
					// 关掉对话框
					admin.closeThisDialog();
					parent.location.reload();
				}
				else
					layer.msg("修改失败")
					return false;
			},
			error:function (data) {

			}
		}) ;
		return false;
	});

});