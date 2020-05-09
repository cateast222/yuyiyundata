layui.use(['layer', 'jquery', 'form', 'fast', 'laydate'], function() {
	var $ = layui.jquery,
		form = layui.form,
		layer = layui.layer,
		laydate = layui.laydate,
		fast = layui.fast;

	// 渲染时间选择框
	laydate.render({
		elem: '#validity',
		type: 'datetime',
		value: new Date()
	});

	//获取详情信息，填充表单
	$.ajax({
		type: 'post',
		url: fast.ctxPath + '/apidataauth/detail',
		data: {
			uuid: fast.getUrlParam('uuid')
		},
		dataType: 'json',
		success: function(res) {
			form.val('apiDataAuthForm', res.data);
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
			url: fast.ctxPath + '/apidataauth/editItem',
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
			error: function(XMLHttpRequest, textStatus) {
				layer.msg('请求失败，系统发生异常', {
					icon: 2
				});
				console.log(textStatus);
			}
		});
		return false;
	});
});
