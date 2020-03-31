layui.use(['layer', 'jquery', 'form', 'fast'], function() {
    	var $ = layui.jquery,
    		form = layui.form,
    		layer = layui.layer,
    		fast = layui.fast;

    	//监听提交
    	form.on('submit(formBtn)', function(data) {
//    		data.field.type = $('#type').val();
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
    				// 重载表格
    				parent.layui.table.reload('dataDictConfTable', {
    					page: {
    						curr: 1
    					}
    				});
    			},
    			error: function(data) {
    				Feng.error("修改失败！" + data.responseJSON.message)
    			}
    			/*error: function(XMLHttpRequest, textStatus) {
    				layer.msg('请求失败，系统发生异常', {
    					icon: 2
    				});
    				console.log(textStatus);
    			}*/
    		});
    		return false;
    	});
    });
