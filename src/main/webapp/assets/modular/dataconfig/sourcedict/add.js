layui.use(['layer', 'jquery', 'form', 'fast'], function() {
	var $ = layui.jquery,
		form = layui.form,
		layer = layui.layer,
		fast = layui.fast;

	//二级下拉
	form.on('select(dict)', function(data) {
		if(data.value!=0){
			$.ajax({
				type: 'POST',
				url: fast.ctxPath + '/datadict/getList',
				data: {
					"parentUuid": data.value
				},
				dataType: 'json',
				success: function(e) {
					var html = '';
					$(e.data).each(function(v, k) {
						html += "<option value='" + k.uuid + "'>" + k.name + "</option>";
					});
					$("#dictConf").html(html); //把遍历的数据放到select表里面
					form.render('select'); //从新刷新了一下下拉框,重新渲染
					$('#dataDict').val('0');
				}
			});
		}else{
			Feng.error("请选择数据字典！")
		}
	});
	form.on('select(dictConf)', function(data) {
		$('#dataDict').val(data.value);
	});

	//监听提交
	form.on('submit(formBtn)', function(data) {
		if($('#dataDict').val()!='0'){
			data.field.dataSource = fast.getUrlParam('dataSource');
			$.ajax({
				type: 'post',
				url: fast.ctxPath + '/sourcedict/addItem',
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
					Feng.error("保存失败！" + data.responseJSON.message)
				}
			});
		}else{
			Feng.error("请选择字典配置！")
		}
		return false;
	});
});
