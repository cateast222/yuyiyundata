var dict = null;
var dictConf = null;
layui.use(['layer', 'jquery', 'form', 'fast'], function() {
	var $ = layui.jquery,
		form = layui.form,
		layer = layui.layer,
		fast = layui.fast;

	//二级下拉
	form.on('select(dict)', function(data) {
		$.ajax({
			type: 'POST',
			url: fast.ctxPath + '/datadict/getList',
			data: {
				"parentUuid": data.value,
				"type": "102"
			},
			dataType: 'json',
			success: function(e) {
				var html = '';
				$(e.data).each(function(v, k) {
					html += "<option value='" + k.uuid + "'>" + k.name + "</option>";
				});
				$("#dictConf").html('<option value="0">自定义</option>' + html);//把遍历的数据放到select表里面
				form.render('select');//从新刷新了一下下拉框,重新渲染
				dict = e.data;//将数据进行转存
			}
		});
	});	
	form.on('select(dictConf)', function(data) {
		$("#dataDict").val("");$("#key").val("");$("#name").val("");
		$(dict).each(function(v, k) {
			if(data.value==k.uuid){
				$("#dataDict").val(k.uuid);
				$("#key").val(k.code);
				$("#name").val(k.name);
			}
		});
	});

	//监听提交
	form.on('submit(formBtn)', function(data) {
		data.field.dataSource = fast.getUrlParam('dataSource');
		$.ajax({
			type: 'post',
			url: fast.ctxPath + '/dataconfig/addItem',
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
				parent.layui.table.reload('dataConfigTable', {
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
