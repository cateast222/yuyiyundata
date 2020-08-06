layui.use(['form', 'admin', 'ax', 'laydate','fast'], function () {
	var $ = layui.jquery;
	var $ax = layui.ax;
	var fast = layui.fast;
	var admin = layui.admin;
	var form = layui.form;
	var laydate = layui.laydate;
	
	// 表单提交事件
	//form.on('submit(btnSubmit)', function (data) {
//		console.log(data)
//		var ajax = new $ax(Feng.ctxPath + "/cjmediainfo/add", function (data) {
//			Feng.success("修改成功！");
//			// 传给上个页面，刷新table用
//			admin.putTempData('formOk', true);
//			// 关掉对话框
//			admin.closeThisDialog();
//		}, function (data) {
//			Feng.error("添加失败！" + data.responseJSON.message)
//		});
//		ajax.set(data.field);
//		ajax.start();
//		return false;
//	});
	
	form.on('submit(btnSubmit)', function (data) {
		  console.log(data)
		  $.ajax({   
                url:fast.ctxPath + "/cjmediainfo/add",       
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