layui.use(['layer', 'jquery', 'form', 'fast'], function() {
    	var $ = layui.jquery,
    		form = layui.form,
    		layer = layui.layer,
    		fast = layui.fast;

    	//监听提交
    	form.on('submit(formBtn)', function(data) {
    		data.field.parentUuid = fast.getUrlParam('parentUuid');
    		$.ajax({
    			type: 'post',
    			url: fast.ctxPath + '/datadictconf/addItem',
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
