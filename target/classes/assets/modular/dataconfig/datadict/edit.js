layui.use(['layer', 'jquery', 'form', 'fast'], function() {
	var $ = layui.jquery,
		form = layui.form,
		layer = layui.layer,
		fast = layui.fast;

	//获取详情信息，填充表单
	$.ajax({
		type: 'post',
		url: fast.ctxPath + '/datadict/detail',
		data: {
			uuid: fast.getUrlParam('uuid')
		},
		dataType: 'json',
		success: function(res) {
			form.val('dataDictForm', res.data);
		},
		error: function(XMLHttpRequest, textStatus) {
			layer.msg('获取数据失败', {
				icon: 2
			});
		}
	});

	//监听提交
	form.on('submit(formBtn)', function(data) {
		$.ajax({
			type: 'post',
			url: fast.ctxPath + '/datadict/editItem',
			data: data.field,
			dataType: 'json',
			success: function(data) {
				// 提示信息
				top.layer.msg(data.message, {
					icon: 1
				});
				// 获取当前iframe层的索引
				var index = parent.layer.getFrameIndex(window.name);
				// 关闭弹窗
				parent.layer.close(index);
			},
			error: function(data) {
				Feng.error("修改失败！" + data.responseJSON.message)
			}
		});
		return false;
	});

});
