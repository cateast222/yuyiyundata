/**
 * 
 */
layui.use(['element', 'laydate', 'form','fast'], function () {
    var $ = layui.jquery,
    form = layui.form,
    laydate = layui.laydate,
    fast = layui.fast;

    var build = $(".build-panel");

    // 搜索
	$('#formBtn').on('click', function() {
		var dataDictUuid = fast.getUrlParam('dataDictUuid');
		var genHtml = '';
        build.find(".layui-form-item").each(function (key, val) {
            var item = $(val).clone();
            item.children('div').children('div').remove();
            item.find('.layui-upload-file').remove();
            genHtml += item.prop('outerHTML') + "\n";
        });
        console.log(genHtml);
		$.ajax({
			type: 'post',
			url: fast.ctxPath + '/datadictconf/editItem',
			data: {'uuid':dataDictUuid,'datas':genHtml},
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
	});
	
	// 关闭页面
	$('#backBtn').click(function() {
		// 获取当前iframe层的索引
		var index = parent.layer.getFrameIndex(window.name);
		// 关闭弹窗
		parent.layer.close(index);
	});

});