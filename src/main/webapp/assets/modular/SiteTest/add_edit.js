layui.use(['form', 'admin', 'ax', 'laydate','fast'], function () {
	var $ = layui.jquery;
	var $ax = layui.ax;
	var fast = layui.fast;
	var admin = layui.admin;
	var form = layui.form;
	var laydate = layui.laydate;


	// 获取详情信息，填充表单
	var ajax = new $ax(Feng.ctxPath + "/cjmediainfo/selectById");
	ajax.set({
		mediaid: $("#mediaid").val(),
	});
	var result = ajax.start();
	form.val('addEditForm', result.data);

	// 表单提交事件
	//form.on('submit(btnSubmit)', function (data) {
//		var ajax = new $ax(Feng.ctxPath + "/cjmediainfo/update", function (data) {
//			Feng.success("修改成功！");
//			// 传给上个页面，刷新table用
//			admin.putTempData('formOk', true);
//			// 关掉对话框
//			admin.closeThisDialog();
//		}, function (data) {
//			Feng.error("修改失败！" + data.responseJSON.message)
//		});
//		ajax.set(data.field);
//		ajax.start();
//		return false;
//	});
	// 表单提交事件
	form.on('submit(btnSubmit)', function (data) {
		console.log(data)
		  $.ajax({   
                url:fast.ctxPath + "/cjmediainfo/update",       
                method:'post',       
                data:data.field,        
                dataType:'JSON',         
                success:function(res){       
                     if(res.code='0'){       
                        admin.putTempData('formOk', true);
						// 关掉对话框
						admin.closeThisDialog();
						parent.location.reload();
                        }                
                    else            
                        alert(res.msg);   
                    },              
                    error:function (data) {
                        
                    }           
                    }) ;         
                 return false;
            });   
});